package evaluation.tools;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import config.Config;
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

	private BigDecimal averagePrecision;
	private BigDecimal averageRecall;
	private BigDecimal averageF1;

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

	private BigDecimal getAverage(Collection<BigDecimal> values)
	{
		BigDecimal avgSum = new BigDecimal(0);
		int avgCount = 0;
		for(BigDecimal f : values)
		{
			if(f != null && f.compareTo(new BigDecimal(-1)) != 0)
			{
				avgSum = avgSum.add(f);
				avgCount++;
			}
		}
		return FormatingUtil.round(avgSum.divide(new BigDecimal(avgCount), Config.bigDecimalScale, Config.bigDecimalRoundingMode));
	}

	public EvaluationResult getResultForKey(Object key)
	{
		return map.get(key);
	}

	public BigDecimal getAveragePrecision()
	{
		return averagePrecision;
	}

	public BigDecimal getAverageRecall()
	{
		return averageRecall;
	}

	public BigDecimal getAverageF1()
	{
		return averageF1;
	}

	public String getAveragePrecisionFormated()
	{
		return FormatingUtil.formatBigDecimal(getAveragePrecision());
	}

	public String getAverageRecallFormated()
	{
		return FormatingUtil.formatBigDecimal(getAverageRecall());
	}

	public String getAverageF1Formated()
	{
		return FormatingUtil.formatBigDecimal(getAverageF1());
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

	public void printKeyEntryCSV(T key, boolean printExpectedCount)
	{
		EvaluationResult evaluationResult = getResultForKey(key);

		List<String> columns = new ArrayList<>();
		columns.add(StringUtil.getLabelIfPresent(key));
		columns.addAll(getStatisticValues(evaluationResult));
		if(printExpectedCount) columns.add(evaluationResult.getHasExpectedCount().toString());

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
		printCSVStatistics(true);
	}

	public void printCSVStatistics(boolean printExpectedCount) throws IOException
	{
		for(T key : getKeysSet())
		{
			printKeyEntryCSV(key, printExpectedCount);
		}
		printSummaryCSV();
	}
}
