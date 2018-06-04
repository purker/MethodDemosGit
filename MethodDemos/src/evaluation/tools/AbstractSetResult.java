package evaluation.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import config.Config;
import evaluation.EvaluationMode;
import evaluation.informationresults.SingleInformationDocResult;
import mapping.result.AbstractMetaPublication;
import mapping.result.KeyStringInterface;
import mapping.result.PublicationType;
import method.Method;
import utils.FileCollectionUtil;
import utils.PublicationUtil;

public abstract class AbstractSetResult<T extends AbstractMetaPublication>
{
	protected final Collection<EvalInformationType> evalTypes;

	// <id, publication> or <id, reference>
	protected Map<String, T> elements = new HashMap<>();

	// <id, <type, value>>
	protected final Map<String, Map<EvalInformationType, SingleInformationDocResult<?>>> detailedResults = new TreeMap<>(getComparator());

	// <type, value>
	protected SetResult<EvalInformationType> perType = null;

	// <id, value>
	protected SetResult<String> perId = null;

	// <publicationType, value>
	protected SetResult<PublicationType> perPublicationType = null;

	// for overall evaluation
	protected final EvaluationResult documentResult = new EvaluationResult();

	protected final Method method;

	protected AbstractWriter csvPerIdAndEvalTypeWriter;
	protected List<EvaluationMode> modes;

	public AbstractSetResult(List<EvaluationMode> modes, Method method, Collection<EvalInformationType> types) throws IOException
	{
		this.method = method;
		this.evalTypes = types;
		this.modes = modes;

		perId = new SetResult<>(Config.CSVperIdFile, method, modes.contains(EvaluationMode.CSV_PER_FILE), "File");
		perPublicationType = new SetResult<>(Config.CSVperPublicationTypeFile, method, modes.contains(EvaluationMode.CSV_PER_PUBLICATIONTYPE), "PublicationType");
		perType = new SetResult<>(Config.CSVperEvalTypeFile, method, modes.contains(EvaluationMode.CSV_PER_EVALUTATIONTYPE), "EvaluationType");
		if(modes.contains(EvaluationMode.CSV_PER_FILE_AND_EVALUATIONTYPE))
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

	protected abstract Comparator<String> getComparator();

	public void addResult(T element, SingleInformationDocResult<?> result)
	{
		KeyStringInterface idElement = element;
		if(idElement == null)
		{
			System.err.println("id of publication has to be set");
			return;
		}
		String id = idElement.getKeyString();
		if(detailedResults.get(id) == null)
		{
			detailedResults.put(id, new EnumMap<EvalInformationType, SingleInformationDocResult<?>>(EvalInformationType.class));
		}
		detailedResults.get(id).put(result.getType(), result);
		elements.put(id, element);
	}

	public void evaluate()
	{
		// foreach publication in map
		for(Map.Entry<String, Map<EvalInformationType, SingleInformationDocResult<?>>> result : detailedResults.entrySet())
		{
			String id = result.getKey();
			Map<EvalInformationType, SingleInformationDocResult<?>> values = result.getValue();
			T publication = elements.get(id);

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
				perId.addResult(id, sResult);
				perPublicationType.addResult(publication.getPublicationType(), sResult);
				perType.addResult(type, sResult);
				documentResult.addResult(sResult);
			}
		}

		perId.evaluate();
		perPublicationType.evaluate();
		perType.evaluate();
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

	public void printDocument(KeyStringInterface id, int i)
	{
		Map<EvalInformationType, SingleInformationDocResult<?>> docResults = detailedResults.get(id.getKeyString());
		System.out.println("");
		System.out.println(">>>>>>>>> " + i);
		System.out.println(id);
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

			List<String> lines = new ArrayList<>();

			lines.add(id);
			addCustomElementColumns(id, lines);
			for(EvalInformationType type : evalTypes)
			{
				lines.add(docResults.get(type).getF1X100AsString());
			}

			lines.add(perId.getResultForKey(id).getAveragePrecisionFormatted());
			lines.add(perId.getResultForKey(id).getAverageRecallFormatted());
			lines.add(perId.getResultForKey(id).getAverageF1Formatted());

			csvPerIdAndEvalTypeWriter.writeNext(lines);
		}
		csvPerIdAndEvalTypeWriter.close();
	}

	protected abstract void addCustomElementColumns(String id, List<String> lines);

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

				for(String id : detailedResults.keySet())
				{
					SingleInformationDocResult<?> result = detailedResults.get(id).get(type);

					String pdfPath = "=HYPERLINK(\"" + FileCollectionUtil.getPdfFileById(PublicationUtil.getNumberFromIdAsString(id)).getAbsolutePath() + "\")";
					String originalPath = "=HYPERLINK(\"" + FileCollectionUtil.getGroundTruthResultFileById(id).getAbsolutePath() + "\")";
					String extractedPath = "=HYPERLINK(\"" + FileCollectionUtil.getResultFilesByMethodAndId(method, PublicationUtil.getNumberFromIdAsString(id)).getAbsolutePath() + "\")";
					String expected = result.getExpectedAsString();
					String extracted = result.getExtractedAsString();
					String precision = result.getPrecisionAsString();
					String recall = result.getRecallAsString();
					String f1 = result.getF1AsString();
					String[] s = {id, pdfPath, originalPath, extractedPath, expected, extracted, precision, recall, f1};

					writer.writeNext(s);
				}
				writer.close();
			}
		}

	}

}
