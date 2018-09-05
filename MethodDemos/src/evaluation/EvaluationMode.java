package evaluation;

import java.util.Arrays;
import java.util.List;

import config.Config;

public enum EvaluationMode
{
	CSV_PER_EVALUTATIONTYPE(Config.CSVperEvalTypeFile, "EvaluationType"),
	CSV_PER_PUBLICATIONTYPE(Config.CSVperPublicationTypeFile, "PublicationType"),
	CSV_PER_ID(Config.CSVperIdFile, "File"),
	CSV_PER_ID_AND_EVALUATIONTYPE(Config.CSVperIdAndEvalTypeFile, null),
	CSV_PER_ID_WITH_EVALUATIONTYPEVALUE(Config.CSVperIdWithEvalTypeValueFile, null),

	SYSOUT_DETAILED(null, null),
	SYSOUT_SUMMARY(null, null);

	private String statisticsFile;
	private String keyLabel;

	private EvaluationMode(String statisticsFile, String keyLabel)
	{
		this.statisticsFile = statisticsFile;
		this.keyLabel = keyLabel;
	}

	public String getStatisticsFile()
	{
		return statisticsFile;
	}

	public void setStatisticsFile(String statisticsFile)
	{
		this.statisticsFile = statisticsFile;
	}

	public String getKeyLabel()
	{
		return keyLabel;
	}

	public void setKeyLabel(String keyLabel)
	{
		this.keyLabel = keyLabel;
	}

	public static List<EvaluationMode> getCSVModes()
	{
		return Arrays.asList(CSV_PER_ID, CSV_PER_PUBLICATIONTYPE, CSV_PER_EVALUTATIONTYPE, CSV_PER_ID_AND_EVALUATIONTYPE, CSV_PER_ID_WITH_EVALUATIONTYPEVALUE);
	}
}