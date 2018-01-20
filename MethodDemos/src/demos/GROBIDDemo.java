package demos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.grobid.core.engines.Engine;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.mock.MockContext;
import org.grobid.core.utilities.GrobidProperties;

import method.Method;

/**
 * http://grobid.readthedocs.io/en/latest/Grobid-java-library/
 *
 */
public class GROBIDDemo extends AbstractDemo
{
	private static final Method METHOD = Method.GROBID;

	private static final String GROBID_DIRECTORY = "C:/Users/Angela/git/";
	private static final String pGrobidHome = GROBID_DIRECTORY + "grobid/grobid-home";
	private static final String pGrobidProperties = GROBID_DIRECTORY + "grobid/grobid-home/config/grobid.properties";

	private boolean consolidate = false;
	private boolean consolidateCitations = consolidate;
	private boolean consolidateHeader = consolidate;

	public static void main(String[] args) throws IOException
	{
		List<File> groundTruthFiles = new ArrayList<>();
		// groundTruthFiles.add(Demos.getAllGroundTruthFiles().subList(0, 1));
		groundTruthFiles.add(new File("D:\\output\\methods\\GroundTruthNoSubDir\\TUW-247743.pdf"));
		// groundTruthFiles.add(Demos.getAllGroundTruthFiles().subList(0, 1));
		// groundTruthFiles.add(new File("D:\\output\\methods\\GroundTruthNoSubDir\\TUW-200745.pdf"));
		// groundTruthFiles.add(Demos.getAllGroundTruthFiles().subList(0, 1));
		// groundTruthFiles.add(new File("D:\\output\\methods\\GroundTruthNoSubDir\\TUW-200948.pdf"));

		new GROBIDDemo().runDemo(groundTruthFiles, Demos.grobIdOutputDir);
		// new GROBIDDemo().runDemoInBatch("D:/TU/Masterarbeit/Papers/Methoden/", Demos.grobIdOutputDir.getPath());
		// new GROBIDDemo().runDemoInBatch("D:/output/GroundTruth-subset", grobIdOutputDir.getPath());
	}

	public void runDemoInBatch(String inputDir, String outputDir)
	{
		boolean consolidateCitations = false;
		boolean consolidateHeader = false;
		try
		{
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

	/*
	 * https://github.com/kermitt2/grobid-example
	 */
	@Override
	String runDemo(File inputFile, File outputFile) throws IOException
	{
		try
		{
			MockContext.setInitialContext(pGrobidHome, pGrobidProperties);
			GrobidProperties.getInstance();

			Engine engine = GrobidFactory.getInstance().createEngine();

			GrobidAnalysisConfig config = GrobidAnalysisConfig.builder().consolidateHeader(consolidateHeader).consolidateCitations(consolidateCitations).build();
			String resultString = engine.fullTextToTEI(inputFile, config);
			FileUtils.writeStringToFile(outputFile, resultString, StandardCharsets.UTF_8);

			System.out.println(inputFile);

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

	@Override
	protected Method getMethod()
	{
		return METHOD;
	}
}
