package evaluation.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import evaluation.EvaluationMode;
import evaluation.informationresults.SingleInformationResult;
import method.Method;
import utils.FailureUtil;
import utils.FileCollectionUtil;
import utils.FormatingUtil;
import utils.StringUtil;

/**
 *
 * @param <T>
 *            for each distinct key of type <T> the average value of its EvaluationResults will be calculated
 */
public class SetResult<T>
{
	// TODO private
	public Map<T, EvaluationResult> map = new LinkedHashMap<>();

	private Double averagePrecision;
	private Double averageRecall;
	private Double averageF1;

	private WriterWrapper writer;

	public SetResult(EvaluationMode evaluationMode, String methodFile, Method method, CollectionEnum setResultEnum, List<EvaluationMode> modes)
	{
		boolean createFile = modes.contains(evaluationMode);
		if(createFile)
		{
			String file = FileCollectionUtil.getFileByMethodAndSetResultType(methodFile, setResultEnum, method);
			try
			{
				this.writer = new WriterWrapper(file);
			}
			catch(IOException e)
			{
				FailureUtil.failureExit(e, System.err, "not able to create writer for file " + file, true);
			}

			String[] headers = {evaluationMode.getKeyLabel(), "Precision", "Recall", "F1"};
			writer.writeNext(headers);
		}
	}

	void addResult(T key, SingleInformationResult<?> sResult)
	{
		EvaluationResult result = map.get(key);
		if(result == null)
		{
			result = new EvaluationResult();
			map.put(key, result);
		}
		result.addResult(sResult);
	}

	void evaluate()
	{
		for(EvaluationResult result : map.values())
		{
			result.evaluate();
		}

		averagePrecision = getAverage(map.values().stream().map(f -> f.getAveragePrecision()).collect(Collectors.toList()));
		averageRecall = getAverage(map.values().stream().map(f -> f.getAverageRecall()).collect(Collectors.toList()));
		averageF1 = getAverage(map.values().stream().map(f -> f.getAverageF1()).collect(Collectors.toList()));
	}

	private Double getAverage(Collection<Double> values)
	{
		double avgSum = 0;
		int avgCount = 0;
		for(Double f : values)
		{
			if(!f.isNaN())
			{
				avgSum += f;
				avgCount++;
			}
		}
		return FormatingUtil.round(avgSum / avgCount);
	}

	public EvaluationResult getResultForKey(Object key)
	{
		return map.get(key);
	}

	public Double getAveragePrecision()
	{
		return averagePrecision;
	}

	public Double getAverageRecall()
	{
		return averageRecall;
	}

	public Double getAverageF1()
	{
		return averageF1;
	}

	public String getAveragePrecisionFormated()
	{
		return FormatingUtil.formatDouble(getAveragePrecision());
	}

	public String getAverageRecallFormated()
	{
		return FormatingUtil.formatDouble(getAverageRecall());
	}

	public String getAverageF1Formated()
	{
		return FormatingUtil.formatDouble(getAverageF1());
	}

	public void printKeyEntry(T key)
	{
		if(key == null)
		{
			System.out.println("key is null");
		}
		if(getResultForKey(key) == null)
		{
			System.out.println("no value for key " + key + " found");
		}
		String precision = getResultForKey(key).getAveragePrecisionFormatted();
		String recall = getResultForKey(key).getAverageRecallFormatted();
		String f1 = getResultForKey(key).getAverageF1Formatted();

		System.out.printf("%28s: precision: %6s" + ", recall: %6s" + ", F1: %6s\n", key, precision, recall, f1);
		// System.out.printf("%20s: precision: %6.2f" + ", recall: %6.2f" + ", F1: %6.2f\n", type, getPrecisionFormated(type), getRecallFormated(type), getF1Formated(type));
	}

	public void printKeyValueCSV(T key)
	{
		String precision = getResultForKey(key).getAveragePrecisionFormatted();
		String recall = getResultForKey(key).getAverageRecallFormatted();
		String f1 = getResultForKey(key).getAverageF1Formatted();
		String[] s = {key.toString(), precision, recall, f1};

		writer.writeNext(s);
	}

	public void printKeyEntryCSV(T key)
	{
		EvaluationResult evaluationResult = getResultForKey(key);

		List<String> columns = new ArrayList<>();
		columns.add(StringUtil.getLabelIfPresent(key));
		columns.addAll(getStatisticValues(evaluationResult));

		writer.writeNext(columns);

	}

	public void printSummaryCSV() throws IOException
	{
		List<String> columns = new ArrayList<>();
		columns.add("Average");
		columns.addAll(getStatisticValuesSummary());

		writer.writeNext(columns);

		writer.close();
	}

	public Collection<String> getStatisticValuesSummary()
	{
		String precision = getAveragePrecisionFormated();
		String recall = getAverageRecallFormated();
		String f1 = getAverageF1Formated();

		return Arrays.asList(precision, recall, f1);
	}

	public Set<T> getKeysSet()
	{
		return map.keySet();
	}

	public List<String> getStatisticValues(EvaluationResult evaluationResult)
	{
		String precision = evaluationResult.getAveragePrecisionFormatted();
		String recall = evaluationResult.getAverageRecallFormatted();
		String f1 = evaluationResult.getAverageF1Formatted();

		return Arrays.asList(precision, recall, f1);
	}

	public void printCSVStatistics() throws IOException
	{
		for(T key : getKeysSet())
		{
			// TODO löschen System.out.println(key);
			printKeyEntryCSV(key);
		}
		printSummaryCSV();
	}
}
