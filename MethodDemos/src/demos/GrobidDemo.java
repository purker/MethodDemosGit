package demos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.grobid.core.engines.Engine;
import org.grobid.core.engines.config.GrobidAnalysisConfig;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.main.GrobidHomeFinder;
import org.grobid.core.utilities.GrobidProperties;

import config.Config;
import method.Method;
import misc.SetDateAndGrobidVersion;
import utils.FailureUtil;
import utils.FileCollectionUtil;

/**
 * http://grobid.readthedocs.io/en/latest/Grobid-java-library/
 *
 */
public class GrobidDemo extends AbstractDemo implements AutoCloseable
{
	private static final Method METHOD = Method.GROBID;

	private Engine engine;
	private GrobidAnalysisConfig config;

	private int consolidate = 0;
	private int consolidateCitations = consolidate;
	private int consolidateHeader = consolidate;

	public GrobidDemo()
	{
		engine = initEngine();
		config = GrobidAnalysisConfig.builder().consolidateHeader(consolidateHeader).consolidateCitations(consolidateCitations).build();
	}

	public static void main(String[] args) throws IOException
	{
		List<File> groundTruthFiles = new ArrayList<>();

		int index = 16;
		List<String> idList = Config.groundTruthIds;
		// List<String> idList = Arrays.asList("141758");
		groundTruthFiles.addAll(FileCollectionUtil.getAllGroundTruthFilesByIds(idList));

		new GrobidDemo().runDemoList(groundTruthFiles, Demos.grobIdOutputDir);
		SetDateAndGrobidVersion.replaceDateAndGrobidVersion();
		// new GROBIDDemo().runDemoInBatch("D:/TU/Masterarbeit/Papers/Methoden/", Demos.grobIdOutputDir.getPath());
		// new GROBIDDemo().runDemoInBatch("D:/output/GroundTruth-subset", grobIdOutputDir.getPath());
	}

	/*
	 * https://github.com/kermitt2/grobid-example
	 */
	@Override
	String runDemoSingleFile(File inputFile, File outputFile) throws Exception
	{
		System.out.println(inputFile);

		String resultString = engine.fullTextToTEI(inputFile, config);
		FileUtils.writeStringToFile(outputFile, resultString, StandardCharsets.UTF_8);

		return null;
	}

	public static Engine initEngine()
	{
		init();

		return GrobidFactory.getInstance().createEngine();
	}

	public static void init()
	{
		try
		{
			GrobidHomeFinder grobidHomeFinder = new GrobidHomeFinder(Arrays.asList(Config.pGrobidHome));
			GrobidProperties.getInstance(grobidHomeFinder);
		}
		catch(Exception e)
		{
			FailureUtil.failureExit(e, System.out, "error initializing grobid context", true);
		}
	}

	@Override
	protected Method getMethod()
	{
		return METHOD;
	}

	@Override
	public void close() throws IOException
	{
		engine.close();
	}

}
