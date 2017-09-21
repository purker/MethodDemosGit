import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.exec.ExecuteException;

import utils.ExecUtil;

public class PDFXDemo extends AbstractDemo
{
	private static final String METHOD_NAME = "pdfx";

	// Usage:
	// curl --data-binary @"/path/to/my.pdf" -H "Content-Type: application/pdf" -L "http://pdfx.cs.man.ac.uk"
	// Example:
	// curl -s --data-binary @'D:\output\GroundTruth\BeiträgeausTagungsbänden\TUW-137078.pdf' -H 'Content-Type: application/pdf' -L 'http://pdfx.cs.man.ac.uk' -o 'D:\output\PDFX\TUW-137078.xml'
	public static final List<String> command = Arrays.asList("curl", "-s", "--data-binary", "path(getsreplaced)", "-H", "\"Content-Type: application/pdf\"", "-L", "\"http://pdfx.cs.man.ac.uk\"", "-o");

	public static void main(String[] args) throws ExecuteException, IOException
	{
		List<File> groundTruthFiles = Demos.getAllGroundTruthFiles().subList(0, 1);
		new PDFXDemo().runDemo(groundTruthFiles, Demos.pdfxOutputDir);
	}

	@Override
	String runDemo(File inputFile, File outputFile) throws IOException
	{
		// execute curl pdfx command
		List<String> commandWithParameters = new ArrayList<>(command);
		commandWithParameters.set(3, "@\"" + inputFile.toString() + "\"");
		commandWithParameters.add("\"" + outputFile.toString() + "\"");
		String errorString = ExecUtil.exec(commandWithParameters);
		log(getMethodName() + ": " + String.join(" ", commandWithParameters));
		return errorString;
	}

	@Override
	String getMethodName()
	{
		return METHOD_NAME;
	}
}
