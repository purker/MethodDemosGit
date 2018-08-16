package config;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import evaluation.tools.WriterType;

public interface Config
{
	File groundTruth = new File("output/groundtruth");
	File groundTruthResults = new File("output/result/");

	File cermineOutputDir = new File("output/extracted/cermine");
	File grobidOutputDir = new File("output/extracted/grobid");
	File parsCitOutputDir = new File("output/extracted/parscit");
	File pdfxOutputDir = new File("output/extracted/pdfx");

	File loggingDir = new File("output/");

	String pGrobidHome = "D:/Java/git/grobid-0.5.1/grobid-home";
	String pGrobidProperties = pGrobidHome + "/config/grobid.properties";

	String xStreamFileExtension = "-xstream.xml";

	// String cermineFileExtension = xStreamFileExtension;
	// String grobidFileExtension = xStreamFileExtension;
	String extractedFileExtension = ".xml";
	// String pdfxFileExtension = xStreamFileExtension;

	// <method> = cermine | grobid | parscit | pdfx
	// <fileext> = csv | xlsx
	// <type> = publication | reference
	// <evaltype> = ABSTRACT | AFFILIATIONS | ...
	//
	String statisticsFolder = "output/statistics/";
	String CSVperIdFile = statisticsFolder + "<method>-per-id-<type>-statistics.<fileext>";
	String CSVperFileAndEvalTypeFile = statisticsFolder + "<method>-per-id-and-evaltype-<type>-statistics.<fileext>";
	String CSVperPublicationTypeFile = statisticsFolder + "<method>-per-publicationtype-<type>-statistics.<fileext>";
	String CSVperEvalTypeFile = statisticsFolder + "<method>-per-evaltype-<type>-statistics.<fileext>";
	String CSVperFileWithEvalTypeValueFile = statisticsFolder + "singlevalues/<method>-<evaltype>-<type>-statistics.<fileext>";

	DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00");
	int decimalPlaces = 2;

	List<String> groundTruthIds = Arrays.asList("137078", "138011", "138447", "138544", "138547", "139299", "139761", "139769", "139781", "139785", "140047", "140048", "140229", "140253", "140308", "140533", "140867", "140895", "140983", "141024", "141065", "141121", "141140", "141336", "141618", "141758", "168222", "168482", "169511", "172697", "174216", "175428", "176087", "177140", "179146", "180162", "181199", "182414", "182899", "185321", "185441", "186227", "189842", "191715", "191977", "192724", "194085", "194561", "194660", "197422", "197852", "198400", "198401", "198405", "198408", "200745", "200748", "200948", "200950", "200959", "201066", "201160", "201167", "202034", "225252", "202824", "203409", "203924", "201821", "204724", "205557", "205933", "213513", "216744", "217690", "217971", "221215", "223906", "223973", "226000", "226016", "228620", "231707", "233317", "233657", "236063", "236120", "237297", "240858", "245336", "245799", "247301", "247741", "247743", "251544", "252847", "255712", "256654", "257397", "257870");
	List<WriterType> writerTypes = Arrays.asList(WriterType.CSV, WriterType.EXCEL);

	String publicationPrefix = "TUW-";
	String referencePrefix = "ref";

	File allPerEvalTypePublicationFormatedArrows = new File(Config.statisticsFolder + "all-per-evaltype-publication-statistics (formated - arrows).xlsx");
	File allPerEvalTypePublicationFormatedBars = new File(Config.statisticsFolder + "all-per-evaltype-publication-statistics (formated - bars).xlsx");
	File allPerEvalTypePublicationFormatedColors = new File(Config.statisticsFolder + "all-per-evaltype-publication-statistics (formated - colors).xlsx");

	File allPerEvalTypeReferenceFormatedArrows = new File(Config.statisticsFolder + "all-per-evaltype-reference-statistics (formated - arrows).xlsx");
	File allPerEvalTypeReferenceFormatedBars = new File(Config.statisticsFolder + "all-per-evaltype-reference-statistics (formated - bars).xlsx");
	File allPerEvalTypeReferenceFormatedColors = new File(Config.statisticsFolder + "all-per-evaltype-reference-statistics (formated - colors).xlsx");

	File allPerEvalTypePublication = new File(Config.statisticsFolder + "all-per-evaltype-publication-statistics.xls");
	File allPerEvalTypeReference = new File(Config.statisticsFolder + "all-per-evaltype-reference-statistics.xls");

	File allBestPerEvalTypePublication = new File(Config.statisticsFolder + "all-best-method-per-evaltype-publication-statistics.xlsx");
	File allBestPerEvalTypeReference = new File(Config.statisticsFolder + "all-best-method-per-evaltype-reference-statistics.xlsx");

	File allDeltaPublication = new File(Config.statisticsFolder + "all-delta-precision-recall-publication-statistics.xlsx");
	File allDeltaReference = new File(Config.statisticsFolder + "all-delta-precision-recall-reference-statistics.xlsx");

	// training
	String trainingInput = "output/training/input";
	String trainingOutput = "output/training/output";
}
