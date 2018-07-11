package evaluation.tools;

import evaluation.informationresults.SingleInformationResult;
import utils.FormatingUtil;

public class EvaluationResult
{
	private Double precisionSum = 0.;
	private Double recallSum = 0.;
	private Double f1Sum = 0.;

	private Double precisionCount = 0.;
	private Double recallCount = 0.;
	private Double f1Count = 0.;

	private Double averagePrecision;
	private Double averageRecall;
	private Double averageF1;

	void addResult(SingleInformationResult<?> sResult)
	{
		if(sResult != null) // TODO voriges if verwenden, entfernen war nur zum Testen
		{
			if(sResult.getPrecision() != null)
			{
				precisionSum += sResult.getPrecision();
				precisionCount++;
			}
			if(sResult.getRecall() != null)
			{
				recallSum += sResult.getRecall();
				recallCount++;
			}
			if(sResult.getF1() != null)
			{
				f1Sum += sResult.getF1();
				f1Count++;
			}
		}
	}

	void evaluate()
	{
		averagePrecision = FormatingUtil.x100AndRound(precisionSum / precisionCount);
		averageRecall = FormatingUtil.x100AndRound(recallSum / recallCount);
		averageF1 = FormatingUtil.x100AndRound(f1Sum / f1Count);
	}

	public Double getAveragePrecision()
	{
		return averagePrecision;
	}

	public String getAveragePrecisionFormatted()
	{
		return FormatingUtil.formatDouble(averagePrecision);
	}

	public Double getAverageRecall()
	{
		return averageRecall;
	}

	public String getAverageRecallFormatted()
	{
		return FormatingUtil.formatDouble(averageRecall);
	}

	public Double getAverageF1()
	{
		return averageF1;
	}

	public String getAverageF1Formatted()
	{
		return FormatingUtil.formatDouble(averageF1);
	}

}
