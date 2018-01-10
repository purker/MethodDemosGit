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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

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
import pl.edu.icm.cermine.evaluation.exception.EvaluationException;
import utils.CollectionUtil;
import utils.FileCollectionUtil;

public abstract class SystemEvaluator
{
	private static final EvaluationMode EVALUATION_MODE = EvaluationMode.CSV;

	public static final String CSV_DELIMITER = ";";

	protected PublicationIterator iter;

	public SystemEvaluator()
	{
		this.iter = new PublicationIterator(getOriginalFiles(), getExtractedFiles());
	}

	protected List<File> getOriginalFiles()
	{
		return FileCollectionUtil.getResultFiles();
	}

	protected abstract List<File> getExtractedFiles();

	public void evaluate() throws EvaluationException, IOException
	{
		EvaluationMode mode = EVALUATION_MODE;
		evaluate(mode, iter);
	}

	protected ArrayList<EvalInformationType> getTypes()
	{
		return Evaluators.getTypes();
	}

	public DocumentSetResult evaluate(EvaluationMode mode, PublicationIterator files) throws EvaluationException, IOException
	{
		DocumentSetResult results = new DocumentSetResult(getTypes());

		// only for csv mode
		File csvFile = getCSVFile();
		FileWriter csvWriter = new FileWriter(csvFile);

		if(mode == EvaluationMode.CSV)
		{
			csvWriter.write("path" + CSV_DELIMITER + StringUtils.join(getTypes(), CSV_DELIMITER) + "\n");
		}

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
				switch(type)
				{
					case TITLE:
						results.addResult(id, new SimpleInformationResult(type, origPub.getTitle(), testPub.getTitle()));
						break;
					case ABSTRACT:
						results.addResult(id, new SimpleInformationResult(type, origPub.getAbstractText(), testPub.getAbstractText()));
						break;
					case ABSTRACTGERMAN:
						results.addResult(id, new SimpleInformationResult(type, origPub.getAbstractTextGerman(), testPub.getAbstractTextGerman()));
						break;
					case KEYWORDS:
						List<String> origKeywords = new ArrayList<>();
						List<String> testKeywords = new ArrayList<>();

						if(origPub.getKeywords() != null)
						{
							origKeywords = Arrays.asList(origPub.getKeywords().split(Publication.KEYWORD_DELIMITER));
						}
						if(testPub.getKeywords() != null)
						{
							testKeywords = Arrays.asList(testPub.getKeywords().split(Publication.KEYWORD_DELIMITER));
						}
						results.addResult(id, new ListInformationResult(type, origKeywords, testKeywords));
						break;
					case AUTHORS:
						List<String> authorOrig = new ArrayList<>();
						for(Author author : origPub.getAuthors())
						{
							authorOrig.add(author.getName());
						}
						List<String> authorTest = new ArrayList<>();
						for(Author author : testPub.getAuthors())
						{
							authorTest.add(author.getName());
						}
						results.addResult(id, new ListInformationResult(type, authorOrig, authorTest));
						break;
					case AFFILIATIONS:
						List<String> affOrig = new ArrayList<>();
						for(Affiliation aff : CollectionUtil.emptyIfNull(origPub.getAffiliations()))
						{
							affOrig.add(aff.getRawText());
						}
						List<String> affTest = new ArrayList<>();
						for(Affiliation aff : CollectionUtil.emptyIfNull(testPub.getAffiliations()))
						{
							affTest.add(aff.getRawText());
						}
						results.addResult(id, new ListInformationResult(type, affOrig, affTest));
						break;
					case AUTHOR_AFFILIATIONS:
						Set<StringRelation> relOrig = new HashSet<>();
						for(Author author : CollectionUtil.emptyIfNull(origPub.getAuthors()))
						{
							for(Affiliation aff : CollectionUtil.emptyIfNull(author.getAffiliations()))
							{
								relOrig.add(new StringRelation(author.getName(), aff.getRawText()));
							}
						}
						Set<StringRelation> relTest = new HashSet<>();
						for(Author author : CollectionUtil.emptyIfNull(testPub.getAuthors()))
						{
							for(Affiliation aff : CollectionUtil.emptyIfNull(author.getAffiliations()))
							{
								relTest.add(new StringRelation(author.getName(), aff.getRawText()));
							}

						}
						results.addResult(id, new RelationInformationResult(type, relOrig, relTest));
						break;
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
						results.addResult(id, new ListInformationResult(type, emailsOrig, emailsTest));
					}
						break;
					case AUTHOR_EMAILS:
						Set<StringRelation> emailsOrig = new HashSet<>();
						for(Author author : origPub.getAuthors())
						{
							emailsOrig.add(new StringRelation(author.getName(), author.getEmail()));
						}
						Set<StringRelation> emailsTest = new HashSet<>();
						for(Author author : testPub.getAuthors())
						{
							emailsTest.add(new StringRelation(author.getName(), author.getEmail()));
						}
						results.addResult(id, new RelationInformationResult(type, emailsOrig, emailsTest));
						break;
					case SOURCE:
						results.addResult(id, new SimpleInformationResult(type, origPub.getSource(), testPub.getSource()));
						break;
					case VOLUME:
						results.addResult(id, new SimpleInformationResult(type, origPub.getVolume(), testPub.getVolume()));
						break;
					case ISSUE:
						results.addResult(id, new SimpleInformationResult(type, origPub.getIssue(), testPub.getIssue()));
						break;
					case PAGE_FROM:
						results.addResult(id, new SimpleInformationResult(type, origPub.getPageFrom(), testPub.getPageFrom()));
						break;
					case PAGE_TO:
						results.addResult(id, new SimpleInformationResult(type, origPub.getPageTo(), testPub.getPageTo()));
						break;
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
						results.addResult(id, new SimpleInformationResult(type, origYear, testYear));
						break;
					case DOI:
						results.addResult(id, new SimpleInformationResult(type, origPub.getDoi(), testPub.getDoi()));
						break;
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
						results.addResult(id, new ListInformationResult(type, headerOrig, headerTest));
						break;
					case SECTION_LEVELS:
						Set<StringRelation> headersOrig = new HashSet<>();
						for(Section section : origPub.getSections())
						{
							headersOrig.add(new StringRelation(String.valueOf(section.getLevel()), section.getTitle()));
						}
						Set<StringRelation> headersTest = new HashSet<>();
						for(Section section : testPub.getSections())
						{
							headersTest.add(new StringRelation(String.valueOf(section.getLevel()), section.getTitle()));
						}
						results.addResult(id, new RelationInformationResult(type, headersOrig, headersTest));
						break;
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
						results.addResult(id, new ListInformationResult(type, origRefs, testRefs));

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
						// break;
						// }
						// }

						break;
					case REFERENCE_TITLE:
						results.addResult(id, new SimpleInformationResult(type, origPub.getTitle(), testPub.getTitle()));
						break;
				}
			}

			if(mode == EvaluationMode.CSV)
			{
				results.printCSV(id, csvWriter, CSV_DELIMITER);
			}
			else
				if(mode == EvaluationMode.SYSOUT_DETAILED)
				{
					results.printDocument(id, i);
				}
		}

		results.evaluate();

		if(mode != EvaluationMode.CSV)
		{
			System.out.println("==== Summary (" + files.size() + " docs)====");
			for(EvalInformationType type : getTypes())
			{
				results.printTypeSummary(type);
			}
			results.printTotalSummary();
		}
		return results;
	}

	public abstract File getCSVFile();

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
