package evaluation.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentSetResult
{
	private final List<EvalInformationType> evalTypes;

	private final Map<String, Map<EvalInformationType, SingleInformationDocResult<?>>> results;

	private final Map<EvalInformationType, Double> precision = new EnumMap<>(EvalInformationType.class);
	private final Map<EvalInformationType, Double> recall = new EnumMap<>(EvalInformationType.class);
	private final Map<EvalInformationType, Double> f1 = new EnumMap<>(EvalInformationType.class);

	private Double averagePrecision;
	private Double averageRecall;
	private Double averageF1;

	public DocumentSetResult(List<EvalInformationType> types)
	{
		this.evalTypes = types;
		results = new HashMap<>();
	}

	public void addResult(String doc, SingleInformationDocResult<?> result)
	{
		if(results.get(doc) == null)
		{
			results.put(doc, new EnumMap<EvalInformationType, SingleInformationDocResult<?>>(EvalInformationType.class));
		}
		results.get(doc).put(result.getType(), result);
	}

	public void evaluate()
	{
		precision.clear();
		recall.clear();
		f1.clear();

		Map<EvalInformationType, Integer> precisionCount = new EnumMap<>(EvalInformationType.class);
		Map<EvalInformationType, Integer> recallCount = new EnumMap<>(EvalInformationType.class);
		Map<EvalInformationType, Integer> f1Count = new EnumMap<>(EvalInformationType.class);

		for(EvalInformationType type : evalTypes)
		{
			precision.put(type, 0.);
			recall.put(type, 0.);
			f1.put(type, 0.);
			precisionCount.put(type, 0);
			recallCount.put(type, 0);
			f1Count.put(type, 0);
		}

		for(Map.Entry<String, Map<EvalInformationType, SingleInformationDocResult<?>>> result : results.entrySet())
		{
			for(EvalInformationType type : evalTypes)
			{
				SingleInformationDocResult<?> sResult = result.getValue().get(type);
				// if(sResult == null)
				// {
				// throw new IllegalArgumentException("Document " + result.getKey() + " does not contain result for " + type);
				// }
				if(sResult != null) // TODO voriges if verwenden, entfernen war nur zum Testen
				{
					if(sResult.getPrecision() != null)
					{
						precision.put(type, precision.get(type) + sResult.getPrecision());
						precisionCount.put(type, precisionCount.get(type) + 1);
					}
					if(sResult.getRecall() != null)
					{
						recall.put(type, recall.get(type) + sResult.getRecall());
						recallCount.put(type, recallCount.get(type) + 1);
					}
					if(sResult.getF1() != null)
					{
						f1.put(type, f1.get(type) + sResult.getF1());
						f1Count.put(type, f1Count.get(type) + 1);
					}
				}
			}
		}
		for(EvalInformationType type : evalTypes)
		{
			precision.put(type, precision.get(type) / precisionCount.get(type));
			recall.put(type, recall.get(type) / recallCount.get(type));
			f1.put(type, f1.get(type) / f1Count.get(type));
		}

		double avgPrecisionSum = 0;
		int avgPrecisionCount = 0;
		for(Double prec : precision.values())
		{
			if(!prec.isNaN())
			{
				avgPrecisionSum += prec;
				avgPrecisionCount++;
			}
		}
		averagePrecision = avgPrecisionSum / avgPrecisionCount;

		double avgRecallSum = 0;
		int avgRecallCount = 0;
		for(Double rec : recall.values())
		{
			if(!rec.isNaN())
			{
				avgRecallSum += rec;
				avgRecallCount++;
			}
		}
		averageRecall = avgRecallSum / avgRecallCount;

		double avgF1Sum = 0;
		int avgF1Count = 0;
		for(Double f : f1.values())
		{
			if(!f.isNaN())
			{
				avgF1Sum += f;
				avgF1Count++;
			}
		}
		averageF1 = avgF1Sum / avgF1Count;
	}

	public void printTotalSummary()
	{
		System.out.printf("Average precision\t%4.2f%n", 100 * averagePrecision);
		System.out.printf("Average recall   \t%4.2f%n", 100 * averageRecall);
		System.out.printf("Average F1 score \t%4.2f%n", 100 * averageF1);
	}

	public void printTypeSummary(EvalInformationType type)
	{
		System.out.printf("%20s: precision: %6.2f" + ", recall: %6.2f" + ", F1: %6.2f\n", type, getPrecision(type) == null ? -1. : 100 * getPrecision(type), getRecall(type) == null ? -1. : 100 * getRecall(type), getF1(type) == null ? -1. : 100 * getF1(type));
	}

	public void printDocument(String doc, int i)
	{
		Map<EvalInformationType, SingleInformationDocResult<?>> docResults = results.get(doc);
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

	public void printCSV(String doc, FileWriter csvWriter, String csvDelimiter) throws IOException
	{
		Map<EvalInformationType, SingleInformationDocResult<?>> docResults = results.get(doc);
		csvWriter.write(doc);
		for(EvalInformationType type : evalTypes)
		{
			csvWriter.write(csvDelimiter);
			docResults.get(type).printCSV(csvWriter);
		}
		csvWriter.write("\n");
	}

	public Double getF1(EvalInformationType type)
	{
		return f1.get(type);
	}

	public Double getPrecision(EvalInformationType type)
	{
		return precision.get(type);
	}

	public Double getRecall(EvalInformationType type)
	{
		return recall.get(type);
	}

	public Double getAveragePrecision()
	{
		return averagePrecision;
	}

	public void setAveragePrecision(Double averagePrecision)
	{
		this.averagePrecision = averagePrecision;
	}

	public Double getAverageRecall()
	{
		return averageRecall;
	}

	public void setAverageRecall(Double averageRecall)
	{
		this.averageRecall = averageRecall;
	}

	public Double getAverageF1()
	{
		return averageF1;
	}

	public void setAverageF1(Double averageF1)
	{
		this.averageF1 = averageF1;
	}

}
