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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import evaluation.tools.AbstractSingleInformationDocResult;
import evaluation.tools.DocumentSetResult;
import evaluation.tools.EvalInformationType;
import evaluation.tools.ListInformationResult;
import evaluation.tools.PublicationIterator;
import evaluation.tools.PublicationPair;
import evaluation.tools.RelationInformationResult;
import evaluation.tools.RelationInformationResult.StringRelation;
import evaluation.tools.SimpleInformationResult;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.Section;
import method.Method;
import pl.edu.icm.cermine.evaluation.exception.EvaluationException;
import utils.CollectionUtil;
import utils.FileCollectionUtil;

/**
 * @author Angela
 *
 */
public abstract class SystemEvaluator
{
	protected PublicationIterator iter;
	protected Collection<EvalInformationType> types;

	public SystemEvaluator()
	{
		this.iter = new PublicationIterator(getOriginalFiles(), getExtractedFiles());
	}

	public SystemEvaluator(Collection<EvalInformationType> types)
	{
		this();
		this.types = types;
	}

	protected List<File> getOriginalFiles()
	{
		return FileCollectionUtil.getResultFiles();
	}

	protected abstract List<File> getExtractedFiles();

	public void evaluate(List<EvaluationMode> modes) throws EvaluationException, IOException
	{
		evaluate(modes, iter);
	}

	protected Collection<EvalInformationType> getTypes()
	{
		return this.types;
	}

	/**
	 * @param modes
	 *            which outputs should be generated, if empty -> no output (for test purposes)
	 * @param files
	 * @return
	 * @throws EvaluationException
	 * @throws IOException
	 */
	public DocumentSetResult evaluate(List<EvaluationMode> modes, PublicationIterator files) throws EvaluationException, IOException
	{
		DocumentSetResult results = new DocumentSetResult(modes, getMethod(), getTypes());

		int i = 0;
		for(PublicationPair pair : files)
		{
			i++;

			Publication origPub = pair.getOriginalPub();
			Publication testPub = pair.getExtractedPub();
			File originalFile = pair.getOriginalFile();

			String id; // for identification of the result
			if(originalFile != null)
			{
				id = pair.getOriginalFile().getPath();
			}
			else
			{
				id = origPub.getId();
			}

			for(EvalInformationType type : getTypes())
			{
				AbstractSingleInformationDocResult<?> result = getResultFromType(type, origPub, testPub);
				results.addResult(id, result, origPub);
			}

			if(modes.contains(EvaluationMode.SYSOUT_DETAILED))
			{
				results.printDocument(id, i);
			}
		}

		results.evaluate();

		results.printResults();

		return results;
	}

	private AbstractSingleInformationDocResult<?> getResultFromType(EvalInformationType type, Publication origPub, Publication testPub) throws EvaluationException
	{
		switch(type)
		{
			case TITLE:
				return new SimpleInformationResult(type, origPub.getTitle(), testPub.getTitle());

			case ABSTRACT:
				return new SimpleInformationResult(type, origPub.getAbstractText(), testPub.getAbstractText());

			case ABSTRACTGERMAN:
				return new SimpleInformationResult(type, origPub.getAbstractTextGerman(), testPub.getAbstractTextGerman());

			case KEYWORDS:
				List<String> origKeywords = new ArrayList<>();
				List<String> testKeywords = new ArrayList<>();

				if(origPub.getKeywords() != null)
				{
					origKeywords = origPub.getKeywords();
				}
				if(testPub.getKeywords() != null)
				{
					testKeywords = testPub.getKeywords();
				}
				return new ListInformationResult(type, origKeywords, testKeywords);

			case AUTHORS:
				List<String> authorOrig = new ArrayList<>();
				for(Author author : origPub.getAuthors())
				{
					authorOrig.add(author.getFullName());
				}
				List<String> authorTest = new ArrayList<>();
				for(Author author : testPub.getAuthors())
				{
					authorTest.add(author.getFullName());
				}
				return new ListInformationResult(type, authorOrig, authorTest);

			case AFFILIATIONS:
				List<String> affOrig = new ArrayList<>();
				for(Affiliation aff : CollectionUtil.emptyIfNull(origPub.getAffiliations()))
				{
					affOrig.add(aff.getComparisonText());
				}
				List<String> affTest = new ArrayList<>();
				for(Affiliation aff : CollectionUtil.emptyIfNull(testPub.getAffiliations()))
				{
					affTest.add(aff.getComparisonText());
				}
				return new ListInformationResult(type, affOrig, affTest);

			case AUTHOR_AFFILIATIONS:
				Set<StringRelation> relOrig = new HashSet<>();
				for(Author author : CollectionUtil.emptyIfNull(origPub.getAuthors()))
				{
					for(Affiliation aff : CollectionUtil.emptyIfNull(author.getAffiliations()))
					{
						relOrig.add(new StringRelation(author.getFullName(), aff.getComparisonText()));
					}
				}
				Set<StringRelation> relTest = new HashSet<>();
				for(Author author : CollectionUtil.emptyIfNull(testPub.getAuthors()))
				{
					for(Affiliation aff : CollectionUtil.emptyIfNull(author.getAffiliations()))
					{
						relTest.add(new StringRelation(author.getFullName(), aff.getComparisonText()));
					}

				}
				return new RelationInformationResult(type, relOrig, relTest);

			case EMAILS:
			{
				List<String> emailsOrig = new ArrayList<>();
				List<String> emailsTest = new ArrayList<>();
				for(Author author : origPub.getAuthors())
				{
					if(author.getEmail() != null)
					{
						emailsOrig.add(author.getEmail());
					}
				}
				for(Author author : testPub.getAuthors())
				{
					if(author.getEmail() != null)
					{
						emailsTest.add(author.getEmail());
					}
				}
				return new ListInformationResult(type, emailsOrig, emailsTest);

			}
			case AUTHOR_EMAILS:
				Set<StringRelation> emailsOrig = new HashSet<>();
				for(Author author : origPub.getAuthors())
				{
					if(author.getFullName() != null && author.getEmail() != null)
					{
						emailsOrig.add(new StringRelation(author.getFullName(), author.getEmail()));
					}
				}
				Set<StringRelation> emailsTest = new HashSet<>();
				for(Author author : testPub.getAuthors())
				{
					if(author.getFullName() != null && author.getEmail() != null)
					{
						emailsTest.add(new StringRelation(author.getFullName(), author.getEmail()));
					}
				}
				return new RelationInformationResult(type, emailsOrig, emailsTest);

			case SOURCE:
				return new SimpleInformationResult(type, origPub.getSource(), testPub.getSource());

			case VOLUME:
				return new SimpleInformationResult(type, origPub.getVolume(), testPub.getVolume());

			case ISSUE:
				return new SimpleInformationResult(type, origPub.getIssue(), testPub.getIssue());

			case PAGE_FROM:
				return new SimpleInformationResult(type, origPub.getPageFrom(), testPub.getPageFrom());

			case PAGE_TO:
				return new SimpleInformationResult(type, origPub.getPageTo(), testPub.getPageTo());

			case YEAR:
				String origYear = null;
				if(origPub.getPublicationYear() != null)
				{
					origYear = origPub.getPublicationYear();
				}
				String testYear = null;
				if(testPub.getPublicationYear() != null)
				{
					testYear = origPub.getPublicationYear();
				}
				return new SimpleInformationResult(type, origYear, testYear);

			case DOI:
				return new SimpleInformationResult(type, origPub.getDoi(), testPub.getDoi());

			case SECTIONS:
				List<String> headerOrig = new ArrayList<>();
				for(Section section : origPub.getSections())
				{
					headerOrig.add(section.getTitle());
				}
				List<String> headerTest = new ArrayList<>();
				for(Section section : testPub.getSections())
				{
					headerTest.add(section.getTitle());
				}
				return new ListInformationResult(type, headerOrig, headerTest);

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
					if(CollectionUtil.isNotEmpty(section.getReferenceIds()))
					{
						String referenceIds = String.join(" ", section.getReferenceIds());
						sectionReferencesOrig.add(new StringRelation(section.getTitle(), referenceIds));
					}
				}
				Set<StringRelation> sectionReferencesTest = new HashSet<>();
				for(Section section : testPub.getSections())
				{
					if(CollectionUtil.isNotEmpty(section.getReferenceIds()))
					{
						String referenceIds = String.join(" ", section.getReferenceIds());
						sectionReferencesTest.add(new StringRelation(section.getTitle(), referenceIds));
					}
				}
				return new RelationInformationResult(type, sectionReferencesOrig, sectionReferencesTest);

			case REFERENCES:
				List<String> origRefs = new ArrayList<>();
				for(Reference entry : origPub.getReferences())
				{
					origRefs.add(entry.toString());
				}
				List<String> testRefs = new ArrayList<>();
				for(Reference entry : testPub.getReferences())
				{
					testRefs.add(entry.toString());
				}
				return new ListInformationResult(type, origRefs, testRefs);

			// List<EvalInformationType> subTypes = EvalInformationType.getSubTypes(EvalInformationType.REFERENCES, getTypes());
			// DocumentSetResult referenceResults = new DocumentSetResult(subTypes);
			// for(Reference reference : subTypes)
			// {
			//
			// }
			// for(EvalInformationType subType : subTypes)
			// {
			// switch(subType)
			// {
			// case REFERENCE_TITLE:
			// referenceResults.addResult(reference., new SimpleInformationResult(type, origPub.getTitle(), testPub.getTitle()));
			//
			// }
			// }

			case REFERENCE_TITLE:
				return new SimpleInformationResult(type, origPub.getTitle(), testPub.getTitle());

			default:
				throw new EvaluationException("type not known");
		}
	}

	abstract Method getMethod();

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
