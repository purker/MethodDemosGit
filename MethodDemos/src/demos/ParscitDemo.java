package demos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import config.Config;
import method.Method;
import utils.ExecUtil;
import utils.FileCollectionUtil;

public class ParscitDemo extends AbstractDemo
{
	private static final Method METHOD = Method.PARSCIT;
	private static final boolean INPUT_IN_XML_FORMAT = true; // true=xml, false=txt

	// "cmd.exe /c citeExtract.pl C:/Users/Angela/git/ParsCit/demodata/sample1.txt"
	private static final String PARSCIT_HOME = "D:/Java/git/ParsCit/bin";
	private static final List<String> command = Arrays.asList("cmd.exe", "/c", "citeExtract.pl", "-m", "extract_all");

	public static void main(String[] args) throws IOException
	{
		List<String> idList = new ArrayList<>();
		// List<File> groundTruthFiles = Demos.getAllGroundTruthFilesAsTxt().subList(0, 1);

		idList.add("205933");
		new ParscitDemo().runDemoList(FileCollectionUtil.getAllGroundTruthFilesOmnipageById(idList), Demos.parsCitOutputDir);
	}

	@Override
	String runDemoSingleFile(File inputFile, File outputFile) throws Exception
	{
		// TODO if(outputFile.exists()) return null;
		// execute cileExtract.pl command
		// String in = "C:/Users/Angela/git/ParsCit/demodata/sample1.txt";

		List<String> commandWithParameters = new ArrayList<>(command);
		if(INPUT_IN_XML_FORMAT)
		{
			commandWithParameters.add("-i");
			commandWithParameters.add("xml");
		}
		commandWithParameters.add(inputFile.getAbsolutePath());
		commandWithParameters.add(outputFile.getAbsolutePath());
		Process p = ExecUtil.execInWorkingDir(new File(PARSCIT_HOME), commandWithParameters);
		String errorString = ExecUtil.getErrorText(p);

		return errorString;
	}

	@Override
	protected Method getMethod()
	{
		return METHOD;
	}

	@Override
	public String createOutputFileName(File inputFile)
	{
		// parscit-TUW-137078-omnipage-xstream.xml
		String outputFileName = super.createOutputFileName(inputFile);

		// remove "-omnipage" from "parscit-TUW-137078-omnipage-xstream.xml"
		outputFileName = outputFileName.replace("-omnipage", "");
		return outputFileName;
	}
}
