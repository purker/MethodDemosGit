package evaluation.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.opencsv.CSVWriter;

import config.Config;
import evaluation.EvaluationMode;
import evaluation.informationresults.SingleInformationResult;
import mapping.result.AbstractMetaPublication;
import mapping.result.KeyStringInterface;
import mapping.result.PublicationType;
import method.Method;
import utils.FailureUtil;
import utils.FileCollectionUtil;
import utils.PublicationUtil;

public abstract class AbstractCollectionResult<T extends AbstractMetaPublication>
{
	// TODO final generell in der klasse notwendig?
	protected final Collection<EvalInformationType> evalTypes;

	// <id, publication> or <id, reference>
	protected Map<String, T> elements = new TreeMap<>(getComparator());

	// <id, <type, value>>
	protected final Map<String, Map<EvalInformationType, SingleInformationResult<?>>> detailedResults = new TreeMap<>(getComparator());

	// <type, value>
	protected SetResult<EvalInformationType> perType = null;

	// <id, value>
	protected SetResult<String> perId = null;

	// <publicationType, value>
	protected SetResult<PublicationType> perPublicationType = null;

	// for overall evaluation
	protected final EvaluationResult documentResult = new EvaluationResult();

	protected final Method method;

	protected WriterWrapper csvPerIdAndEvalTypeWriter;
	protected List<EvaluationMode> modes;

	public AbstractCollectionResult(List<EvaluationMode> modes, Method method, Collection<EvalInformationType> types)
	{
		this.method = method;
		this.evalTypes = types;
		this.modes = modes;

		perId = new SetResult<>(EvaluationMode.CSV_PER_FILE, Config.CSVperIdFile, method, getCollectionEnum(), modes);
		perPublicationType = new SetResult<>(EvaluationMode.CSV_PER_PUBLICATIONTYPE, Config.CSVperPublicationTypeFile, method, getCollectionEnum(), modes);
		perType = new SetResult<>(EvaluationMode.CSV_PER_EVALUTATIONTYPE, Config.CSVperEvalTypeFile, method, getCollectionEnum(), modes);
		if(modes.contains(EvaluationMode.CSV_PER_FILE_AND_EVALUATIONTYPE))
		{
			String file = Config.CSVperFileAndEvalTypeFile;

			String writerFile = FileCollectionUtil.getFileByMethodAndSetResultType(file, getCollectionEnum(), method);
			try
			{
				csvPerIdAndEvalTypeWriter = new WriterWrapper(writerFile);
			}
			catch(IOException e)
			{
				FailureUtil.failureExit(e, System.err, "not able to create writer for file " + writerFile, true);
			}

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

	protected abstract CollectionEnum getCollectionEnum();

	public Collection<EvalInformationType> getEvalTypes()
	{
		return evalTypes;
	}

	public void addResult(T element, SingleInformationResult<?> result)
	{
		String id = checkNotNullGetKeyString(element);
		if(detailedResults.get(id) == null)
		{
			detailedResults.put(id, new EnumMap<EvalInformationType, SingleInformationResult<?>>(EvalInformationType.class));
		}
		detailedResults.get(id).put(result.getType(), result);
		elements.put(id, element);
	}

	protected String checkNotNullGetKeyString(T element)
	{
		KeyStringInterface idElement = element;
		if(idElement == null)
		{
			FailureUtil.exit("id of element (publication or reference) has to be set");
		}
		String id = idElement.getKeyString();
		return id;
	}

	public Set<String> getIdSet()
	{
		return elements.keySet();
	}

	public void evaluate()
	{
		// foreach publication in map
		for(Map.Entry<String, Map<EvalInformationType, SingleInformationResult<?>>> result : detailedResults.entrySet())
		{
			String id = result.getKey();
			Map<EvalInformationType, SingleInformationResult<?>> values = result.getValue();
			T publication = elements.get(id);

			for(EvalInformationType type : evalTypes)
			{
				SingleInformationResult<?> sResult = values.get(type);
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
		Map<EvalInformationType, SingleInformationResult<?>> docResults = detailedResults.get(id.getKeyString());
		System.out.println("");
		System.out.println(">>>>>>>>> " + i);
		System.out.println(id);
		for(EvalInformationType type : evalTypes)
		{
			SingleInformationResult<?> docResult = docResults.get(type);
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
		Map<EvalInformationType, SingleInformationResult<?>> docResults = detailedResults.get(doc);
		System.out.println("");
		System.out.println(">>>>>>>>> " + i);
		System.out.println(doc);
		for(EvalInformationType type : evalTypes)
		{
			if(type != EvalInformationType.DOI) continue;
			SingleInformationResult<?> docResult = docResults.get(type);

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
		for(Map.Entry<String, Map<EvalInformationType, SingleInformationResult<?>>> result : detailedResults.entrySet())
		{
			String id = result.getKey();
			Map<EvalInformationType, SingleInformationResult<?>> docResults = result.getValue();

			List<String> lines = new ArrayList<>();

			lines.add(id);
			String publicationId = PublicationUtil.getNumberFromIdAsString(id);

			AbstractMetaPublication publication = elements.get(id);
			lines.add("=HYPERLINK(\"" + FileCollectionUtil.getPdfFileById(publicationId).getAbsolutePath() + "\")");
			lines.add("=HYPERLINK(\"" + FileCollectionUtil.getGroundTruthResultFileById(publicationId).getAbsolutePath() + "\")");
			lines.add("=HYPERLINK(\"" + FileCollectionUtil.getResultFilesByMethodAndId(method, publicationId).getAbsolutePath() + "\")");
			lines.add(publication.getPublicationType().name());
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
		if(modes.contains(EvaluationMode.CSV_PER_EVALUTATIONTYPE))
		{
			System.out.println(EvaluationMode.CSV_PER_EVALUTATIONTYPE);
			getPerType().printCSVStatistics();
		}
		if(modes.contains(EvaluationMode.CSV_PER_PUBLICATIONTYPE))
		{
			System.out.println(EvaluationMode.CSV_PER_PUBLICATIONTYPE);
			getPerPublicationType().printCSVStatistics();
		}
		if(modes.contains(EvaluationMode.CSV_PER_FILE))
		{
			System.out.println(EvaluationMode.CSV_PER_FILE);
			getPerId().printCSVStatistics();
		}
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
		if(modes.contains(EvaluationMode.CSV_PER_FILE_WITH_EVALUATIONTYPEVALUE))
		{
			System.out.println(EvaluationMode.CSV_PER_FILE_WITH_EVALUATIONTYPEVALUE);

			for(EvalInformationType type : perType.getKeysSet())
			{
				String writerFile = FileCollectionUtil.replaceMethodAndTypeAndSetResultEnum(Config.CSVperFileWithEvalTypeValueFile, method, type, getCollectionEnum());
				WriterWrapper writer = new WriterWrapper(writerFile);

				List<String> headers = new ArrayList<>();
				headers.add("path");
				headers.add("PDF");
				headers.add("original");
				headers.add("extracted");
				headers.add("original value");
				headers.add("extracted value");
				headers.add("Precision");
				headers.add("Recall");
				headers.add("F1");
				writer.writeNext(headers);

				for(String id : detailedResults.keySet())
				{
					SingleInformationResult<?> result = detailedResults.get(id).get(type);

					String publicationId = PublicationUtil.getPublicationIdFromKeyStringToString(id);
					String pdfPath = "=HYPERLINK(\"" + FileCollectionUtil.getPdfFileById(publicationId).getAbsolutePath() + "\")";
					String originalPath = "=HYPERLINK(\"" + FileCollectionUtil.getGroundTruthResultFileById(publicationId).getAbsolutePath() + "\")";
					String extractedPath = "=HYPERLINK(\"" + FileCollectionUtil.getResultFilesByMethodAndId(method, publicationId).getAbsolutePath() + "\")";
					String expected = result.getExpectedAsString();
					String extracted = result.getExtractedAsString();
					String precision = result.getPrecisionX100AsString();
					String recall = result.getRecallX100AsString();
					String f1 = result.getF1X100AsString();
					String[] s = {id, pdfPath, originalPath, extractedPath, expected, extracted, precision, recall, f1};

					writer.writeNext(s);
				}
				writer.close();
			}
		}
	}

	public SetResult<?> getSetResultByMode(EvaluationMode mode)
	{
		switch(mode)
		{
			case CSV_PER_FILE:
				return perId;
			case CSV_PER_PUBLICATIONTYPE:
				return perPublicationType;
			case CSV_PER_EVALUTATIONTYPE:
				return perType;
			default:
				FailureUtil.exit("mode not supported");
				return null;
		}
	}

	public Map<String, T> getElements()
	{
		return elements;
	}

	public Map<String, T> getAllElements()
	{
		return elements;
	}

}
