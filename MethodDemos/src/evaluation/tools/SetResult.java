package evaluation.tools;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import method.Method;
import utils.FileCollectionUtil;
import utils.FormatingUtil;

/**
 *
 * @param <T>
 *            for each distinct key of type <T> the average value of its EvaluationResults will be calculated
 */
public class SetResult<T>
{
	private Map<T, EvaluationResult> map = new HashMap<>();

	private Double averagePrecision;
	private Double averageRecall;
	private Double averageF1;

	private WriterWrapper writer;

	public SetResult(String methodFile, Method method, String keyLabel) throws IOException
	{
		String file = FileCollectionUtil.getFileByMethod(methodFile, method);
		this.writer = new WriterWrapper(file);

		String[] headers = {keyLabel, "Precision", "Recall", "F1"};
		writer.writeNext(headers);
	}

	void addResult(T key, SingleInformationDocResult<?> sResult)
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

	public EvaluationResult getResultForKey(T key)
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

		String precision = evaluationResult.getAveragePrecisionFormatted();
		String recall = evaluationResult.getAverageRecallFormatted();
		String f1 = evaluationResult.getAverageF1Formatted();
		String[] s = {key.toString(), precision, recall, f1};

		writer.writeNext(s);
	}

	public void printSummaryCSV() throws IOException
	{
		String[] s = {"", getAveragePrecisionFormated(), getAverageRecallFormated(), getAverageF1Formated()};
		writer.writeNext(s);

		writer.close();
	}

	public Set<T> getKeysSet()
	{
		return map.keySet();
	}
}
