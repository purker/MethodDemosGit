package evaluation.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
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
import utils.CSVWriterUtil;
import utils.FileCollectionUtil;
import utils.PublicationUtil;
import utils.XStreamUtil;

public class DocumentSetResult
{
	private final Collection<EvalInformationType> evalTypes;

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

	CSVWriter fieldDiffWriter = CSVWriterUtil.createCSVWriter(new File("output/statistics/test.csv"));

	private CSVWriter csvPerIdAndEvalTypeWriter;
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
			File csvFile = FileCollectionUtil.getFileByMethod(Config.CSVperFileAndEvalTypeFile, method);
			csvPerIdAndEvalTypeWriter = CSVWriterUtil.createCSVWriter(csvFile);

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
			csvPerIdAndEvalTypeWriter.writeNext(headers.stream().toArray(String[]::new));
		}

	}

	public void addResult(String doc, SingleInformationDocResult<?> result)
	{
		if(detailedResults.get(doc) == null)
		{
			detailedResults.put(doc, new EnumMap<EvalInformationType, SingleInformationDocResult<?>>(EvalInformationType.class));
		}
		detailedResults.get(doc).put(result.getType(), result);
	}

	public void evaluate()
	{
		for(Map.Entry<String, Map<EvalInformationType, SingleInformationDocResult<?>>> result : detailedResults.entrySet())
		{
			String id = result.getKey();
			Map<EvalInformationType, SingleInformationDocResult<?>> values = result.getValue();
			Publication publication = XStreamUtil.convertFromXML(new File(id), Publication.class);

			for(EvalInformationType type : evalTypes)
			{
				SingleInformationDocResult<?> sResult = values.get(type);
				// if(sResult == null)
				// {
				// throw new IllegalArgumentException("Document " + result.getKey() + " does not contain result for " + type);
				// }
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
				if(!docResult.isCorrect())
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
			line.add(new Boolean(docResult.isCorrect()).toString());

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
				line.add(docResults.get(type).printCSV(csvPerIdAndEvalTypeWriter));
			}

			line.add(perId.getResultForKey(id).getAveragePrecisionFormatted());
			line.add(perId.getResultForKey(id).getAverageRecallFormatted());
			line.add(perId.getResultForKey(id).getAverageF1Formatted());

			csvPerIdAndEvalTypeWriter.writeNext(line.stream().toArray(String[]::new), true);
		}
		csvPerIdAndEvalTypeWriter.flush();
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
			printCSVPerId();
		}
		if(modes.contains(EvaluationMode.SYSOUT_DETAILED) || modes.contains(EvaluationMode.SYSOUT_SUMMARY))
		{
			System.out.println("==== Summary (" + detailedResults.keySet().size() + " docs)====");
			System.out.println("\n                    ---------- EvaluationTypes ----------");
			for(EvalInformationType type : evalTypes)
			{
				perType.printKeyEntry(type);
			}
			System.out.println("\n                    ---------- PublicationTypes ----------");
			for(PublicationType publicationType : PublicationType.values())
			{
				perPublicationType.printKeyEntry(publicationType);
			}
			printTotalSummary();
		}

		if(modes.contains(EvaluationMode.CSV_PER_EVALUTATIONTYPE))
		{
			for(EvalInformationType type : evalTypes)
			{
				getPerType().printKeyEntryCSV(type);
			}
			getPerType().printSummaryCSV();
		}

		if(modes.contains(EvaluationMode.CSV_PER_PUBLICATIONTYPE))
		{
			for(PublicationType publicationType : PublicationType.values())
			{
				getPerPublicationType().printKeyEntryCSV(publicationType);
			}
			getPerPublicationType().printSummaryCSV();
		}

		if(modes.contains(EvaluationMode.CSV_PER_FILE))
		{
			for(String file : detailedResults.keySet())
			{
				getPerId().printKeyEntryCSV(file);
			}
			getPerId().printSummaryCSV();
		}
	}

}
