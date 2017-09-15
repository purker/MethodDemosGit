import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.grobid.core.engines.Engine;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.mock.MockContext;
import org.grobid.core.utilities.GrobidProperties;

/**
 * http://grobid.readthedocs.io/en/latest/Grobid-java-library/
 *
 */
public class GROBIDDemo extends AbstractDemo
{
	boolean consolidateCitations = false;
	boolean consolidateHeader = false;

	public static void main(String[] args)
	{
		List<File> groundTruthFiles = Demos.getAllGroundTruthFiles().subList(0, 1);
		new GROBIDDemo().runDemoInBatch("D:/TU/Masterarbeit/Papers/Methoden/", Demos.grobIdOutputDir.getPath());
	}

	public void runDemoInBatch(String inputDir, String outputDir)
	{
		boolean consolidateCitations = false;
		boolean consolidateHeader = false;
		try
		{
			String directory = "C:/Users/Angela/git/";
			String pGrobidHome = directory + "grobid/grobid-home";
			String pGrobidProperties = directory + "grobid/grobid-home/config/grobid.properties";

			MockContext.setInitialContext(pGrobidHome, pGrobidProperties);
			GrobidProperties.getInstance();

			Engine engine = GrobidFactory.getInstance().createEngine();

			int tei = engine.batchProcessFulltext(inputDir, outputDir, consolidateHeader, consolidateCitations);
			System.out.println(tei);
		}
		catch(Exception e)
		{
			// If an exception is generated, print a stack trace
			e.printStackTrace();
		}
		finally
		{
			try
			{
				MockContext.destroyInitialContext();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	String runDemo(File inputFile, File outputFile) throws IOException
	{
		try
		{
			String homeDir = System.getProperty("user.home");

			String directory = "C:/Users/Angela/git/";
			String pGrobidHome = directory + "grobid/grobid-home";
			String pGrobidProperties = directory + "grobid/grobid-home/config/grobid.properties";

			MockContext.setInitialContext(pGrobidHome, pGrobidProperties);
			GrobidProperties.getInstance();

			Engine engine = GrobidFactory.getInstance().createEngine();

			GrobidAnalysisConfig config = GrobidAnalysisConfig.builder().consolidateHeader(consolidateHeader).consolidateCitations(consolidateCitations).build();
			String resultString = engine.fullTextToTEI(inputFile, config);
			FileUtils.writeStringToFile(outputFile, resultString, StandardCharsets.UTF_8);

			return null;
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
		finally
		{
			try
			{
				MockContext.destroyInitialContext();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
