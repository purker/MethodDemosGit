import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.ExecUtil;

public class ParsCitDemo extends AbstractDemo
{
	// "cmd.exe /c citeExtract.pl C:/Users/Angela/git/ParsCit/demodata/sample1.txt"
	private static final List<String> command = Arrays.asList("cmd.exe", "/c", "C:/Users/Angela/git/ParsCit/bin/citeExtract.pl");

	public static void main(String[] args) throws IOException
	{
		List<File> groundTruthFiles = Demos.getAllGroundTruthFiles().subList(0, 1);
		new ParsCitDemo().runDemo(groundTruthFiles, Demos.parsCitOutputDir);
	}

	@Override
	String runDemo(File inputFile, File outputFile) throws IOException
	{
		// execute cileExtract.pl command
		String in = "C:/Users/Angela/git/ParsCit/demodata/sample1.txt";

		List<String> commandWithParameters = new ArrayList<>(command);
		commandWithParameters.add(in.toString());
		commandWithParameters.add(outputFile.toString());
		String err = ExecUtil.exec(commandWithParameters);

		return err;
	}

}
