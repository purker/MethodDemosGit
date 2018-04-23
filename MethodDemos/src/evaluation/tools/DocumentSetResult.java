package evaluation.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import config.Config;
import evaluation.EvaluationMode;
import mapping.result.Publication;
import mapping.result.PublicationType;
import method.Method;
import utils.FileCollectionUtil;
import utils.PublicationUtil;
import utils.XStreamUtil;

public class DocumentSetResult
{
	private final Collection<EvalInformationType> evalTypes;

	// <id, publication>
	private Map<String, Publication> publications = new HashMap<>();

	// <id, <type, value>>
	private final Map<String, Map<EvalInformationType, SingleInformationDocResult<?>>> detailedResults = new TreeMap<>();

	// <type, value>
	private SetResult<EvalInformationType> perType = null;

	// <id, value>
	private SetResult<String> perId = null;

	// <publicationType, value>
	private SetResult<PublicationType> perPublicationType = null;

	// for overall evaluation
	private final EvaluationResult documentResult = new EvaluationResult();

	private final Method method;

	private AbstractWriter csvPerIdAndEvalTypeWriter;
	private List<EvaluationMode> modes;

	public DocumentSetResult(List<EvaluationMode> modes, Method method, Collection<EvalInformationType> types) throws IOException
	{
		this.method = method;
		this.evalTypes = types;
		this.modes = modes;

		// if(modes.contains(EvaluationMode.CSV_PER_FILE))
		perId = new SetResult<>(Config.CSVperIdFile, method, "File");
		// if(modes.contains(EvaluationMode.CSV_PER_PUBLICATIONTYPE))
		perPublicationType = new SetResult<>(Config.CSVperPublicationTypeFile, method, "PublicationType");
		// if(modes.contains(EvaluationMode.CSV_PER_EVALUTATIONTYPE))
		perType = new SetResult<>(Config.CSVperEvalTypeFile, method, "EvaluationType");
		// if(modes.contains(EvaluationMode.CSV_PER_FILE_AND_EVALUATIONTYPE))
		{
			String writerFile = FileCollectionUtil.getFileByMethod(Config.CSVperFileAndEvalTypeFile, method);
			csvPerIdAndEvalTypeWriter = new WriterWrapper(writerFile);

			List<String> headers = new ArrayList<>();
			headers.add("path");
			headers.add("PDF");
			headers.add("original");
			headers.add("extracted");
			headers.add("PublicationType");
			headers.addAll(evalTypes.stream().map(Object::toString).collect(Collectors.toList()));
			headers.add("Precision");
			headers.add("Recall");
			headers.add("F1");
			csvPerIdAndEvalTypeWriter.writeNext(headers);
		}

	}

	public void addResult(String id, SingleInformationDocResult<?> result, Publication publication)
	{
		if(id == null)
		{
			System.err.println("id of publication has to be set");
			return;
		}
		if(detailedResults.get(id) == null)
		{
			detailedResults.put(id, new EnumMap<EvalInformationType, SingleInformationDocResult<?>>(EvalInformationType.class));
		}
		detailedResults.get(id).put(result.getType(), result);
		publications.put(id, publication);
	}

	public void evaluate()
	{
		// foreach publication in map
		for(Map.Entry<String, Map<EvalInformationType, SingleInformationDocResult<?>>> result : detailedResults.entrySet())
		{
			String id = result.getKey();
			Map<EvalInformationType, SingleInformationDocResult<?>> values = result.getValue();
			Publication publication = publications.get(id);

			for(EvalInformationType type : evalTypes)
			{
				SingleInformationDocResult<?> sResult = values.get(type);
				// if(sResult == null)
				// {
				// throw new IllegalArgumentException("Document " + result.getKey() + " does not contain result for " + type);
				// }
				if(type == null)
				{
					throw new IllegalArgumentException("EvalInformationType for publication (id=" + id + ") not set");
				}
				if(publication.getPublicationType() == null)
				{
					throw new IllegalArgumentException("PublicationType for publication (id=" + id + ") not set");
				}
				perType.addResult(type, sResult);
				perId.addResult(id, sResult);
				perPublicationType.addResult(publication.getPublicationType(), sResult);
				documentResult.addResult(sResult);
			}
		}

		perType.evaluate();
		perId.evaluate();
		perPublicationType.evaluate();
		documentResult.evaluate();
	}

	public void printTotalSummary()
	{
		// TODO
		// System.out.printf("Average precision\t%4.2f%n", getFormatedAveragePrecision());
		// System.out.printf("Average recall \t%4.2f%n", getFormatedAverageRecall());
		// System.out.printf("Average F1 score \t%4.2f%n", getFormatedAvergageF1());

		System.out.println("\nMacro average - type");
		System.out.printf("Average precision\t%4.2f%n", perType.getAveragePrecision());
		System.out.printf("Average recall   \t%4.2f%n", perType.getAverageRecall());
		System.out.printf("Average F1 score \t%4.2f%n", perType.getAverageF1());

		System.out.println("\nMacro averages - publication type");
		System.out.printf("Average precision\t%4.2f%n", perPublicationType.getAveragePrecision());
		System.out.printf("Average recall   \t%4.2f%n", perPublicationType.getAverageRecall());
		System.out.printf("Average F1 score \t%4.2f%n", perPublicationType.getAverageF1());

		System.out.println("\nMicro average");
		System.out.printf("Average precision\t%4.2f%n", documentResult.getAveragePrecision());
		System.out.printf("Average recall   \t%4.2f%n", documentResult.getAverageRecall());
		System.out.printf("Average F1 score \t%4.2f%n", documentResult.getAverageF1());
	}

	public void printDocument(String doc, int i)
	{
		Map<EvalInformationType, SingleInformationDocResult<?>> docResults = detailedResults.get(doc);
		System.out.println("");
		System.out.println(">>>>>>>>> " + i);
		System.out.println(doc);
		for(EvalInformationType type : evalTypes)
		{
			SingleInformationDocResult<?> docResult = docResults.get(type);
			if(docResult != null)// TODO <-- if entfernen war nur zum testen, evtl mit throw wie in csv
			// throw new IllegalArgumentException("Document " + result.getKey() + " does not contain result for " + type);
			{
				if(!docResult.getCorrect())
				{
					System.out.println("");
					System.out.println(type);
					docResult.prettyPrint();
				}
			}
		}
		System.out.println("");
	}

	public void printDocumentCSV(String doc, int i, CSVWriter csvWriter)
	{
		Map<EvalInformationType, SingleInformationDocResult<?>> docResults = detailedResults.get(doc);
		System.out.println("");
		System.out.println(">>>>>>>>> " + i);
		System.out.println(doc);
		for(EvalInformationType type : evalTypes)
		{
			if(type != EvalInformationType.DOI) continue;
			SingleInformationDocResult<?> docResult = docResults.get(type);

			List<String> line = new ArrayList<>();
			line.add("=HYPERLINK(\"" + new File(doc).getAbsolutePath() + "\")");
			if(docResult.hasExpected())
			{
				line.add(docResult.getExpected().toString());
			}
			else
			{
				line.add("");
			}
			if(docResult.hasExtracted())
			{
				line.add(docResult.getExtracted().toString());
			}
			else
			{
				line.add("");
			}
			line.add(docResult.getCorrect().toString());

			csvWriter.writeNext(line.stream().toArray(size -> new String[size]));
		}
		System.out.println("");
	}

	public void printCSVPerId() throws IOException
	{
		for(Map.Entry<String, Map<EvalInformationType, SingleInformationDocResult<?>>> result : detailedResults.entrySet())
		{
			String id = result.getKey();
			Map<EvalInformationType, SingleInformationDocResult<?>> docResults = result.getValue();

			List<String> line = new ArrayList<>();

			Publication publication = XStreamUtil.convertFromXML(new File(id), Publication.class);

			line.add(PublicationUtil.getIdFromFile(new File(id)));
			line.add("=HYPERLINK(\"" + FileCollectionUtil.getPdfFileById(PublicationUtil.getIdFromFileNameWithoutPrefix(id)).getAbsolutePath() + "\")");
			line.add("=HYPERLINK(\"" + new File(id).getAbsolutePath() + "\")");
			line.add("=HYPERLINK(\"" + FileCollectionUtil.getResultFilesByMethodAndId(method, PublicationUtil.getIdFromFileNameWithoutPrefix(id)).getAbsolutePath() + "\")");
			line.add(publication.getPublicationType().name());
			for(EvalInformationType type : evalTypes)
			{
				line.add(docResults.get(type).getF1X100AsString());
			}

			line.add(perId.getResultForKey(id).getAveragePrecisionFormatted());
			line.add(perId.getResultForKey(id).getAverageRecallFormatted());
			line.add(perId.getResultForKey(id).getAverageF1Formatted());

			csvPerIdAndEvalTypeWriter.writeNext(line);
		}
		csvPerIdAndEvalTypeWriter.close();
	}

	public SetResult<EvalInformationType> getPerType()
	{
		return perType;
	}

	public SetResult<String> getPerId()
	{
		return perId;
	}

	public SetResult<PublicationType> getPerPublicationType()
	{
		return perPublicationType;
	}

	public EvaluationResult getDocumentResult()
	{
		return documentResult;
	}

	public void printResults() throws IOException
	{
		if(modes.contains(EvaluationMode.CSV_PER_FILE_AND_EVALUATIONTYPE))
		{
			System.out.println(EvaluationMode.CSV_PER_FILE_AND_EVALUATIONTYPE);

			printCSVPerId();
		}
		if(modes.contains(EvaluationMode.SYSOUT_DETAILED) || modes.contains(EvaluationMode.SYSOUT_SUMMARY))
		{
			System.out.println(EvaluationMode.SYSOUT_DETAILED + " " + EvaluationMode.SYSOUT_SUMMARY);

			System.out.println("==== Summary (" + detailedResults.keySet().size() + " docs)====");
			System.out.println("\n                    ---------- EvaluationTypes ----------");
			for(EvalInformationType type : evalTypes)
			{
				perType.printKeyEntry(type);
			}
			System.out.println("\n                    ---------- PublicationTypes ----------");
			for(PublicationType publicationType : perPublicationType.getKeysSet())
			{
				perPublicationType.printKeyEntry(publicationType);
			}
			printTotalSummary();
		}

		if(modes.contains(EvaluationMode.CSV_PER_EVALUTATIONTYPE))
		{
			System.out.println(EvaluationMode.CSV_PER_EVALUTATIONTYPE);
			for(EvalInformationType type : evalTypes)
			{
				getPerType().printKeyEntryCSV(type);
			}
			getPerType().printSummaryCSV();
		}

		if(modes.contains(EvaluationMode.CSV_PER_PUBLICATIONTYPE))
		{
			System.out.println(EvaluationMode.CSV_PER_PUBLICATIONTYPE);

			for(PublicationType publicationType : perPublicationType.getKeysSet())
			{
				getPerPublicationType().printKeyEntryCSV(publicationType);
			}
			getPerPublicationType().printSummaryCSV();
		}

		if(modes.contains(EvaluationMode.CSV_PER_FILE))
		{
			System.out.println(EvaluationMode.CSV_PER_FILE);

			for(String file : detailedResults.keySet())
			{
				getPerId().printKeyEntryCSV(file);
			}
			getPerId().printSummaryCSV();
		}
		if(modes.contains(EvaluationMode.CSV_PER_FILE_WITH_EVALUATIONTYPEVALUE))
		{
			System.out.println(EvaluationMode.CSV_PER_FILE_WITH_EVALUATIONTYPEVALUE);

			for(EvalInformationType type : perType.getKeysSet())
			{
				String writerFile = FileCollectionUtil.replaceMethodAndType(Config.CSVperFileWithEvalTypeValueFile, method, type);
				WriterWrapper writer = new WriterWrapper(writerFile);

				for(String file : detailedResults.keySet())
				{
					SingleInformationDocResult<?> result = detailedResults.get(file).get(type);

					String path = PublicationUtil.getIdFromFile(new File(file));
					String pdfPath = "=HYPERLINK(\"" + FileCollectionUtil.getPdfFileById(PublicationUtil.getIdFromFileNameWithoutPrefix(file)).getAbsolutePath() + "\")";
					String originalPath = "=HYPERLINK(\"" + new File(file).getAbsolutePath() + "\")";
					String extractedPath = "=HYPERLINK(\"" + FileCollectionUtil.getResultFilesByMethodAndId(method, PublicationUtil.getIdFromFileNameWithoutPrefix(file)).getAbsolutePath() + "\")";
					String expected = result.getExpectedAsString();
					String extracted = result.getExtractedAsString();
					String precision = result.getPrecisionAsString();
					String recall = result.getRecallAsString();
					String f1 = result.getF1AsString();
					String[] s = {path, pdfPath, originalPath, extractedPath, expected, extracted, precision, recall, f1};

					writer.writeNext(s);
				}
				writer.close();
			}
		}

	}

}
