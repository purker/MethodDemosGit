package evaluation.tools;

import java.math.BigDecimal;

import config.Config;
import evaluation.informationresults.SingleInformationResult;
import utils.FormatingUtil;

public class EvaluationResult
{
	private Integer hasExpectedCount = 0;

	private BigDecimal precisionSum = new BigDecimal(0);
	private BigDecimal recallSum = new BigDecimal(0);
	private BigDecimal f1Sum = new BigDecimal(0);

	private int precisionCount = 0;
	private int recallCount = 0;
	private int f1Count = 0;

	private BigDecimal averagePrecision;
	private BigDecimal averageRecall;
	private BigDecimal averageF1;

	void addResult(SingleInformationResult<?> sResult)
	{
		if(sResult != null) // TODO voriges if verwenden, entfernen war nur zum Testen
		{
			if(sResult.hasExpected())
			{
				hasExpectedCount++;
			}
			if(sResult.getPrecision() != null)
			{
				precisionSum = precisionSum.add(sResult.getPrecision());
				precisionCount++;
			}
			if(sResult.getRecall() != null)
			{
				recallSum = recallSum.add(sResult.getRecall());
				recallCount++;
			}
			if(sResult.getF1() != null)
			{
				f1Sum = f1Sum.add(sResult.getF1());
				f1Count++;
			}
		}
	}

	void evaluate()
	{
		if (precisionCount != 0) {
			averagePrecision = FormatingUtil.x100AndRound(precisionSum.divide(new BigDecimal(precisionCount), Config.bigDecimalScale, Config.bigDecimalRoundingMode));
		}
		if (recallCount != 0) {
			averageRecall = FormatingUtil.x100AndRound(recallSum.divide(new BigDecimal(recallCount), Config.bigDecimalScale, Config.bigDecimalRoundingMode));
		}
		if (f1Count != 0) {
			averageF1 = FormatingUtil.x100AndRound(f1Sum.divide(new BigDecimal(f1Count), Config.bigDecimalScale, Config.bigDecimalRoundingMode));
		}
	}

	public Integer getHasExpectedCount()
	{
		return hasExpectedCount;
	}

	public void setHasExpectedCount(Integer hasExpectedCount)
	{
		this.hasExpectedCount = hasExpectedCount;
	}

	public BigDecimal getAveragePrecision()
	{
		return averagePrecision;
	}

	public String getAveragePrecisionFormatted()
	{
		return FormatingUtil.formatBigDecimal(averagePrecision);
	}

	public BigDecimal getAverageRecall()
	{
		return averageRecall;
	}

	public String getAverageRecallFormatted()
	{
		return FormatingUtil.formatBigDecimal(averageRecall);
	}

	public BigDecimal getAverageF1()
	{
		return averageF1;
	}

	public String getAverageF1Formatted()
	{
		return FormatingUtil.formatBigDecimal(averageF1);
	}

}
