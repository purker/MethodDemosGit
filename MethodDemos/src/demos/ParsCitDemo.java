package demos;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.ExecUtil;

public class ParsCitDemo extends AbstractDemo
{
	private static final String METHOD_NAME = "parscit";

	// "cmd.exe /c citeExtract.pl C:/Users/Angela/git/ParsCit/demodata/sample1.txt"
	private static final String PARSCIT_HOME = "C:/Users/Angela/git/ParsCit/bin";
	private static final List<String> command = Arrays.asList("cmd.exe", "/c", "citeExtract.pl", "-m", "extract_all");

	public static void main(String[] args) throws IOException
	{
		List<File> groundTruthFiles = Demos.getAllGroundTruthFilesAsTxt().subList(0, 1);
		new ParsCitDemo().runDemo(groundTruthFiles, Demos.parsCitOutputDir);
	}

	@Override
	String runDemo(File inputFile, File outputFile) throws IOException
	{
		// execute cileExtract.pl command
		// String in = "C:/Users/Angela/git/ParsCit/demodata/sample1.txt";

		List<String> commandWithParameters = new ArrayList<>(command);
		commandWithParameters.add(inputFile.toString());
		commandWithParameters.add(outputFile.toString());
		String err = ExecUtil.execInWorkingDir(new File(PARSCIT_HOME), commandWithParameters);

		return err;
	}

	@Override
	String getMethodName()
	{
		return METHOD_NAME;
	}

	@Override
	public String createOutputFileName(File inputFile)
	{
		String outputFileName = super.createOutputFileName(inputFile);

		// remove "cermine-" from "cermine-TUW-137078"
		outputFileName = outputFileName.substring(outputFileName.indexOf("-") + 1);
		return outputFileName;
	}
}
