package evaluation;
/**
 * This file is part of CERMINE project.
 * Copyright (c) 2011-2016 ICM-UW
 *
 * CERMINE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CERMINE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with CERMINE. If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import evaluation.informationresults.AbstractSingleInformationResult;
import evaluation.informationresults.ListInformationResult;
import evaluation.informationresults.ReferenceInformationResult;
import evaluation.informationresults.RelationInformationResult;
import evaluation.informationresults.RelationInformationResult.StringRelation;
import evaluation.informationresults.SimpleInformationResult;
import evaluation.tools.AbstractCollectionResult;
import evaluation.tools.CollectionEnum;
import evaluation.tools.EvalInformationType;
import evaluation.tools.EvaluationResult;
import evaluation.tools.PublicationCollectionResult;
import evaluation.tools.PublicationIterator;
import evaluation.tools.PublicationPair;
import evaluation.tools.ReferenceCollectionResult;
import evaluation.tools.SetResult;
import evaluation.tools.WriterWrapper;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.FileId;
import mapping.result.KeyStringInterface;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.ReferenceAuthor;
import mapping.result.Section;
import method.Method;
import utils.CollectionUtil;
import utils.FailureUtil;
import utils.FileCollectionUtil;
import utils.PublicationUtil;
import utils.StringUtil;

/**
 * @author Angela
 *
 */
public abstract class SystemEvaluator
{
	protected PublicationIterator iter;

	protected PublicationCollectionResult results;
	protected ReferenceCollectionResult refResults;
	protected Collection<EvaluationMode> modes;

	public SystemEvaluator(Collection<EvalInformationType> types, Collection<EvalInformationType> referenceTypes, List<EvaluationMode> modes)
	{
		System.out.println("Initialize Evaluation: " + getMethod());

		this.results = new PublicationCollectionResult(modes, getMethod(), types);
		this.refResults = new ReferenceCollectionResult(modes, getMethod(), referenceTypes);
		this.modes = modes;
	}

	public SystemEvaluator()
	{
		this(EvalInformationType.getTypesForPublications(), EvalInformationType.getTypesForReferences(), EvaluationMode.getCSVModes());
	}

	protected List<File> getOriginalFiles()
	{
		return FileCollectionUtil.getResultFiles();
	}

	protected List<File> getExtractedFiles()
	{
		return FileCollectionUtil.getResultFilesByMethod(getMethod());
	}

	protected abstract Method getMethod();

	public void evaluate(boolean printResults) throws IOException
	{
		System.out.println("Collecing Files Evaluation: " + getMethod());
		this.iter = new PublicationIterator(getOriginalFiles(), getExtractedFiles());

		System.out.println("Starting Evaluation: " + getMethod() + " (" + this.iter.size() + " files)");
		evaluate(this.iter, printResults);
	}

	protected Collection<EvalInformationType> getTypes()
	{
		return results.getEvalTypes();
	}

	protected Collection<EvalInformationType> getReferenceTypes()
	{
		return refResults.getEvalTypes();
	}

	/**
	 * @param modes
	 *            which outputs should be generated, if empty -> no output (for test purposes)
	 * @param files
	 * @param printResults
	 * @return
	 * @throws IOException
	 */
	public AbstractCollectionResult<?> evaluate(PublicationIterator fileIterator, boolean printResults) throws IOException
	{
		int i = 0;
		for(PublicationPair pair : fileIterator)
		{
			i++;

			Publication origPub = pair.getOriginalPub();
			Publication testPub = pair.getExtractedPub();
			File originalFile = pair.getOriginalFile();

			KeyStringInterface id; // for identification of the result
			if(originalFile != null)
			{
				id = new FileId(origPub, pair.getOriginalFile());
			}
			else
			{
				id = origPub;
			}

			if(modes.contains(EvaluationMode.SYSOUT_DETAILED))
			{
				results.printDocument(id, i);
			}

			for(EvalInformationType type : getTypes())
			{
				AbstractSingleInformationResult<?> result = getResultFromType(type, origPub, testPub);
				result.evaluate();
				results.addResult(origPub, result);

				if(type.equals(EvalInformationType.REFERENCES))
				{
					List<Pair<Reference, Reference>> matchingReferences = ((ReferenceInformationResult)result).getMatchingReferences();

					for(Pair<Reference, Reference> refPair : matchingReferences)
					{
						for(EvalInformationType refType : getReferenceTypes())
						{
							try
							{
								AbstractSingleInformationResult<?> refResult = getResultFromReferenceType(refType, refPair.getLeft(), refPair.getRight());
								// todo löschen if(refResult == null) continue;
								refResult.evaluate();
								refPair.getLeft().setPublicationType(origPub.getPublicationType()); // TODO eventuell schöner, wenn geht
								refResults.addResult(refPair.getLeft(), refResult);

								// System.out.println(refPair.getLeft());
								// System.out.println(refPair.getRight());
								// System.out.println();
							}
							catch(Exception e)
							{
								FailureUtil.failureExit(e, System.err, "reference " + refPair.getKey(), true);
							}
						}
					}
					for(Pair<Reference, Reference> p : ((ReferenceInformationResult)result).getNotMatchingReferences())
					{
						refResults.addNotMatchingReferences(p.getLeft());
					}
					refResults.initializeAllReferences();
				}
			}
		}

		System.out.println("\tEvaluation of PUBLICATIONS");
		results.evaluate();
		if(printResults) results.printResults();

		System.out.println("\tEvaluation of REFERENCES");
		refResults.evaluate();
		if(printResults) refResults.printResults();

		return results;
	}

	private AbstractSingleInformationResult<?> getResultFromReferenceType(EvalInformationType type, Reference reference, Reference reference2)
	{
		switch(type)
		{
			case REFERENCE_ID:
				return new SimpleInformationResult(type, reference, reference2, Reference::getIdString);
			case REFERENCE_MARKER:
				return new SimpleInformationResult(type, reference, reference2, Reference::getMarker);

			case REFERENCE_TITLE:
				return new SimpleInformationResult(type, reference, reference2, Reference::getTitle);

			// TODO case REFERENCE_PUBLICATIONTYPE:
			// return new SimpleInformationResult(type, reference, reference2, )Reference::getPublication));
			case REFERENCE_SOURCE:
				return new SimpleInformationResult(type, reference, reference2, Reference::getSource);
			case REFERENCE_PUBLISHER:
				return new SimpleInformationResult(type, reference, reference2, Reference::getPublisher);
			case REFERENCE_EDITOR:
				return new SimpleInformationResult(type, reference, reference2, Reference::getEditors);
			case REFERENCE_AUTHORS:
				return new ListInformationResult(type, reference, reference2, Reference::getAuthors, ReferenceAuthor::toString);
			case REFERENCE_EDITION:
				return new SimpleInformationResult(type, reference, reference2, Reference::getEdition);
			case REFERENCE_VOLUME:
				return new SimpleInformationResult(type, reference, reference2, Reference::getVolume);
			case REFERENCE_ISSUE:
				return new SimpleInformationResult(type, reference, reference2, Reference::getIssue);
			case REFERENCE_CHAPTER:
				return new SimpleInformationResult(type, reference, reference2, Reference::getChapter);
			case REFERENCE_NOTE:
				return new SimpleInformationResult(type, reference, reference2, Reference::getNote);
			case REFERENCE_PAGEFROM:
				return new SimpleInformationResult(type, reference, reference2, Reference::getPageFrom);
			case REFERENCE_PAGETO:
				return new SimpleInformationResult(type, reference, reference2, Reference::getPageTo);
			case REFERENCE_LOCATION:
				return new SimpleInformationResult(type, reference, reference2, Reference::getLocation);
			case REFERENCE_DATE:
				return new SimpleInformationResult(type, reference, reference2, Reference::getPublicationDateString);
			case REFERENCE_DOI:
				return new SimpleInformationResult(type, reference, reference2, Reference::getDoi);
			case REFERENCE_URL:
				return new SimpleInformationResult(type, reference, reference2, Reference::getUrl);

			default:
				FailureUtil.exit("referencetype " + type + " not known");
				return null;
		}
	}

	private AbstractSingleInformationResult<?> getResultFromType(EvalInformationType type, Publication origPub, Publication testPub)
	{
		switch(type)
		{
			case TITLE:
				return new SimpleInformationResult(type, origPub, testPub, Publication::getTitle);
				
			case PUBLICATIONTYPE:
				return new SimpleInformationResult(type, origPub, testPub, p -> (p.getPublicationType() != null ? p.getPublicationType().name() : null));

			case ABSTRACT:
				return new SimpleInformationResult(type, origPub, testPub, Publication::getAbstractText);

			case KEYWORDS:
				return new ListInformationResult(type, origPub, testPub, Publication::getKeywords, String::toString);

			case AUTHORS:
				return new ListInformationResult(type, origPub, testPub, Publication::getAuthors, Author::toString);

			case AFFILIATIONS:
				return new ListInformationResult(type, origPub, testPub, Publication::getAffiliations, Affiliation::toString);

			case AUTHOR_AFFILIATIONS:
				Set<StringRelation> relOrig = new HashSet<>();
				for(Author author : CollectionUtil.emptyIfNull(origPub.getAuthors()))
				{
					for(Affiliation aff : CollectionUtil.emptyIfNull(author.getAffiliations()))
					{
						relOrig.add(new StringRelation(author.toString(), aff.toString()));
					}
				}
				Set<StringRelation> relTest = new HashSet<>();
				for(Author author : CollectionUtil.emptyIfNull(testPub.getAuthors()))
				{
					for(Affiliation aff : CollectionUtil.emptyIfNull(author.getAffiliations()))
					{
						relTest.add(new StringRelation(author.toString(), aff.toString()));
					}

				}
				return new RelationInformationResult(type, relOrig, relTest);

			case EMAILS:
			{
				return new ListInformationResult(type, origPub, testPub, Publication::getAuthors, Author::getEmail);
			}
			case AUTHOR_EMAILS:
				Set<StringRelation> emailsOrig = new HashSet<>();
				for(Author author : origPub.getAuthors())
				{
					if(author.toString() != null && author.getEmail() != null)
					{
						emailsOrig.add(new StringRelation(author.toString(), author.getEmail()));
					}
				}
				Set<StringRelation> emailsTest = new HashSet<>();
				for(Author author : testPub.getAuthors())
				{
					if(author.toString() != null && author.getEmail() != null)
					{
						emailsTest.add(new StringRelation(author.toString(), author.getEmail()));
					}
				}
				return new RelationInformationResult(type, emailsOrig, emailsTest);

			case SOURCE:
				return new SimpleInformationResult(type, origPub, testPub, Publication::getSource);

			case VOLUME:
				return new SimpleInformationResult(type, origPub, testPub, Publication::getVolume);

			case ISSUE:
				return new SimpleInformationResult(type, origPub, testPub, Publication::getIssue);

			case PAGE_FROM:
				return new SimpleInformationResult(type, origPub, testPub, Publication::getPageFrom);

			case PAGE_TO:
				return new SimpleInformationResult(type, origPub, testPub, Publication::getPageTo);
				
			case DATE:
				return new SimpleInformationResult(type, origPub, testPub, PublicationUtil::getTransientDate);

			case DOI:
				return new SimpleInformationResult(type, origPub, testPub, Publication::getDoi);

			case SECTIONS:
				// Sections which are Acknowledgements are ignored
				return new ListInformationResult(type, origPub, testPub, Publication::getSections, Section::getTitle, null);//s -> s != null && !s.getTitle().matches(Section.acknowledgementRegex));

			case SECTION_LEVELS:
				Set<StringRelation> headersOrig = new HashSet<>();
				for(Section section : origPub.getSections())
				{
					if(section.getTitle() != null && section.getLevel() != null)
					{
						headersOrig.add(new StringRelation(section.getTitle(), section.getLevel()));
					}
				}
				Set<StringRelation> headersTest = new HashSet<>();
				for(Section section : testPub.getSections())
				{
					if(section.getTitle() != null && section.getLevel() != null)
					{
						headersTest.add(new StringRelation(section.getTitle(), section.getLevel()));
					}
				}
				return new RelationInformationResult(type, headersOrig, headersTest);

			case SECTION_REFERENCES:
				Set<StringRelation> sectionReferencesOrig = new HashSet<>();
				for(Section section : origPub.getSections())
				{
					if(section.getTitle() != null && CollectionUtil.isNotEmpty(section.getReferenceIds()))
					{
						String referenceIds = String.join(" ", section.getReferenceIds());
						sectionReferencesOrig.add(new StringRelation(section.getTitle(), referenceIds));
					}
				}
				Set<StringRelation> sectionReferencesTest = new HashSet<>();
				for(Section section : testPub.getSections())
				{
					if(section.getTitle() != null && CollectionUtil.isNotEmpty(section.getReferenceIds()))
					{
						String referenceIds = String.join(" ", section.getReferenceIds());
						sectionReferencesTest.add(new StringRelation(section.getTitle(), referenceIds));
					}
				}
				return new RelationInformationResult(type, sectionReferencesOrig, sectionReferencesTest);

			case REFERENCES:
				return new ReferenceInformationResult(type, origPub.getReferences(), testPub.getReferences(), origPub);

			default:
				FailureUtil.exit("type not known");
				return null;
		}
	}

	public static void printOverallStatistics(List<EvaluationMode> modes, SystemEvaluator... evaluators) throws IOException
	{
		for(EvaluationMode mode : Arrays.asList(EvaluationMode.CSV_PER_EVALUTATIONTYPE, EvaluationMode.CSV_PER_ID, EvaluationMode.CSV_PER_PUBLICATIONTYPE))
		{
			if(modes.contains(mode))
			{
				System.out.println(mode);

				printOverallStatisticsForElements(evaluators, mode, CollectionEnum.PUBLICATION);
				printOverallStatisticsForElements(evaluators, mode, CollectionEnum.REFERENCE);
			}
		}
	}

	private static void printOverallStatisticsForElements(SystemEvaluator[] evaluators, EvaluationMode mode, CollectionEnum setResultEnum) throws IOException
	{
		if(!Arrays.asList(EvaluationMode.CSV_PER_EVALUTATIONTYPE, EvaluationMode.CSV_PER_ID, EvaluationMode.CSV_PER_PUBLICATIONTYPE).contains(mode))
		{
			FailureUtil.exit("mode " + mode + " not supported");
		}

		String file = FileCollectionUtil.getFileByMethodAndSetResultType(mode.getStatisticsFile(), setResultEnum, Method.ALL);
		WriterWrapper writer = new WriterWrapper(file);

		// headers
		List<String> headers = new ArrayList<>();
		headers.add("");
		headers.add(Method.CERMINE.getPrintName());
		headers.add("");
		headers.add("");
		headers.add(Method.GROBID.getPrintName());
		headers.add("");
		headers.add("");
		headers.add(Method.PARSCIT.getPrintName());
		headers.add("");
		headers.add("");
		headers.add(Method.PDFX.getPrintName());
		headers.add("");
		headers.add("");
		writer.writeNext(headers);

		List<String> valueNames = Arrays.asList("Precision", "Recall", "F1");
		headers = new ArrayList<>();
		headers.add("");
		headers.addAll(valueNames);
		headers.addAll(valueNames);
		headers.addAll(valueNames);
		headers.addAll(valueNames);
		writer.writeNext(headers);

		Collection<?> elements;
		AbstractCollectionResult<?> firstCollectionResult = evaluators[0].getCollectionResultByCollectionEnum(setResultEnum);
		if(mode.equals(EvaluationMode.CSV_PER_ID))
		{
			elements = firstCollectionResult.getAllElements().keySet();
		}
		else
		{
			// !!! only allowed for CSV_PER_PUBLICATIONTYPE, CSV_PER_EVALUTATIONTYPE
			elements = firstCollectionResult.getSetResultByMode(mode).getKeysSet();
		}
		// SINGLE lines for each element
		for(Object key : elements)
		{
			List<String> columns = new ArrayList<>();
			columns.add(StringUtil.getLabelIfPresent(key));
			for(SystemEvaluator evaluator : evaluators)
			{
				AbstractCollectionResult<?> abstractSetResult = evaluator.getCollectionResultByCollectionEnum(setResultEnum);
				SetResult<?> setResult = abstractSetResult.getSetResultByMode(mode);
				EvaluationResult evaluationResult = setResult.getResultForKey(key);

				if(evaluationResult != null)
				{
					// {precision, recall, f1}
					List<String> statisticValues = setResult.getStatisticValues(evaluationResult);
					columns.addAll(statisticValues);
				}
				else
				{
					List<String> notMatched = Arrays.asList("notMatched", "notMatched", "notMatched");
					columns.addAll(notMatched);
				}
			}
			writer.writeNext(columns);
		}

		// SUMMARY Line
		List<String> summaryLine = new ArrayList<>();
		summaryLine.add("Average");
		for(SystemEvaluator evaluator : evaluators)
		{
			AbstractCollectionResult<?> abstractSetResult = evaluator.getCollectionResultByCollectionEnum(setResultEnum);
			SetResult<?> setResult = abstractSetResult.getSetResultByMode(mode);

			// {precision, recall, f1}
			Collection<String> statisticValues = setResult.getStatisticValuesSummary();
			summaryLine.addAll(statisticValues);
		}
		writer.writeNext(summaryLine);
		writer.close();
	}

	public AbstractCollectionResult<?> getCollectionResultByCollectionEnum(CollectionEnum setResultEnum)
	{
		AbstractCollectionResult<?> abstractSetResult = null;
		if(setResultEnum.equals(CollectionEnum.PUBLICATION))
		{
			abstractSetResult = this.results;
		}
		else
			if(setResultEnum.equals(CollectionEnum.REFERENCE))
			{
				abstractSetResult = this.refResults;
			}
			else
			{
				FailureUtil.exit("SetResultEnum not supported");
			}

		return abstractSetResult;
	}

	// public void process(String[] args) throws EvaluationException
	// {
	// if(args.length != 4 && args.length != 5)
	// {
	// System.out.println("Usage: " + this.getClass().getSimpleName() + " <input orig dir> <input extract dir> <orig extension> <extract extension> <mode>");
	// return;
	// }
	// File originalDirectory = new File(args[0]);
	// File extractedDirectory = new File(args[1]);
	// String origExt = args[2];
	// String extrExt = args[3];
	//
	// EvaluationMode mode = EvaluationMode.SYSOUT_DETAILED;
	// if(args.length == 5 && args[4].equals("csv"))
	// {
	// mode = EvaluationMode.CSV;
	// }
	// if(args.length == 5 && args[4].equals("q"))
	// {
	// mode = EvaluationMode.SYSOUT_SUMMARY;
	// }
	//
	// PublicationIterator2 iter = new PublicationIterator2(originalDirectory, extractedDirectory, origExt, extrExt);
	// evaluate(mode, iter);
	// }

}
