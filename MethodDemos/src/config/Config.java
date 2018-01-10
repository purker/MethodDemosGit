package config;

import java.io.File;

public interface Config
{
	File groundTruth = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/groundtruth");
	File groundTruthResults = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result/");

	File cermineOutputDir = new File("D:/output/methods/cermine");
	File grobIdOutputDir = new File("D:/output/methods/grobid");
	File parsCitOutputDir = new File("D:/output/methods/parscit");
	File pdfxOutputDir = new File("D:/output/methods/pdfx");

	String xStreamFileExtension = "-xstream.xml";

	// String cermineFileExtension = xStreamFileExtension;
	// String grobidFileExtension = xStreamFileExtension;
	String parscitFileExtension = ".xml";
	// String pdfxFileExtension = xStreamFileExtension;

	File cermineCSVFile = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/statistics/cermine-statistics.csv");
	File grobIdCSVFile = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/statistics/grobid-statistics.csv");
	File parscitCSVFile = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/statistics/parscit-statistics.csv");
	File pdfxCSVFile = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/statistics/pdfx-statistics.csv");

}
