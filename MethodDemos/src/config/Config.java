package config;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public interface Config
{
	File groundTruth = new File("output/groundtruth");
	File groundTruthResults = new File("output/result/");

	File cermineOutputDir = new File("output/extracted/cermine");
	File grobidOutputDir = new File("output/extracted/grobid");
	File parsCitOutputDir = new File("output/extracted/parscit");
	File pdfxOutputDir = new File("output/extracted/pdfx");

	String xStreamFileExtension = "-xstream.xml";

	// String cermineFileExtension = xStreamFileExtension;
	// String grobidFileExtension = xStreamFileExtension;
	String parscitFileExtension = ".xml";
	// String pdfxFileExtension = xStreamFileExtension;

	String CSVperIdFile = "output/statistics/<method>-statistics-per-id.csv";
	String CSVperFileAndEvalTypeFile = "output/statistics/<method>-statistics-per-id-and-evaltype.csv";
	String CSVperPublicationTypeFile = "output/statistics/<method>-statistics-per-publicationtype.csv";
	String CSVperEvalTypeFile = "output/statistics/<method>-statistics-per-evaltype.csv";

	DecimalFormat decimalFormatter = new DecimalFormat("#,###.00");
	int decimalPlaces = 2;

	List<String> groundTruthIds = Arrays.asList("137078", "138011", "138447", "138544", "138547", "139299", "139761", "139769", "139781", "139785", "140047", "140048", "140229", "140253", "140308", "140533", "140867", "140895", "140983", "141024", "141065", "141121", "141140", "141336", "141618", "141758", "168222", "168482", "169511", "172697", "174216", "175428", "176087", "177140", "179146", "180162", "181199", "182414", "182899", "185321", "185441", "186227", "189842", "191715", "191977", "192724", "194085", "194561", "194660", "197422", "197852", "198400", "198401", "198405", "198408", "200745", "200748", "200948", "200950", "200959", "201066", "201160", "201167", "202034", "225252", "202824", "203409", "203924", "201821", "204724", "205557", "205933", "213513", "216744", "217690", "217971", "221215", "223906", "223973", "226000", "226016", "228620", "231707", "233317", "233657", "236063", "236120", "237297", "240858", "245336", "245799", "247301", "247741", "247743", "251544", "252847", "255712", "256654", "257397", "257870");

}
