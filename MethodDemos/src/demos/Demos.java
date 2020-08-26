package demos;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import config.Config;
import evaluation.Evaluators;
import mapping.cermine.CermineMapper;
import mapping.grobid.GROBIDMapper;
import mapping.parscit.ParsCitMapper;
import mapping.pdfx.PDFXMapper;
import misc.Duration;
import misc.DurationEnum;
import misc.SetDateAndGrobidVersion;
import train.Training;
import utils.ExecUtil;
import utils.FileCollectionUtil;
import utils.StringUtil;

/**
 * Runs all Demos with Ground Truth files from inputDir
 *
 */
public class Demos
{
	public static File inputDir = Config.groundTruth;

	public static File cermineOutputDir = Config.cermineOutputDir;
	public static File grobIdOutputDir = Config.grobidOutputDir;
	public static File parsCitOutputDir = Config.parsCitOutputDir;
	public static File pdfxOutputDir = Config.pdfxOutputDir;
	public static File[] outputDirs = {cermineOutputDir, grobIdOutputDir, parsCitOutputDir, pdfxOutputDir};

	static File allOutputDir = new File("D:/output/methods/all");

	public static void main(String[] args) throws Exception
	{
		executeDemos();
	}

	public static void executeDemos() throws IOException, JAXBException
	{
		Duration.addStart(DurationEnum.ALL);

		List<String> idList = Config.groundTruthIds;
		// List<String> idList = Arrays.asList("228620");

		boolean runDemos = true;
		boolean runCermineDemo = false;
		boolean runGrobidDemo = true;
		boolean runParsCitDemo = false;
		boolean runPdfxDemo = false;
		boolean runCermineMapper = true;
		boolean runGrobidMapper = true;
		boolean runParsCitMapper = true;
		boolean runPdfxMapper = true;

		boolean startEvaluation = true;
		
		List<File> groundTruthFiles = FileCollectionUtil.getAllGroundTruthFilesByIds(idList);
		List<File> groundTruthFilesOmnipage = FileCollectionUtil.getAllGroundTruthFilesOmnipageById(idList);

		if(runDemos)
		{
			// pdf -> xml
			if(runCermineDemo) cleanOrCreateDirectory(cermineOutputDir);
			if(runGrobidDemo) cleanOrCreateDirectory(grobIdOutputDir);
			if(runParsCitDemo) cleanOrCreateDirectory(parsCitOutputDir);
			if(runPdfxDemo) cleanOrCreateDirectory(pdfxOutputDir);
			
			if(runCermineDemo) new CermineDemo().runDemoList(groundTruthFiles, cermineOutputDir);
			if(runGrobidDemo)
			{
				try (GrobidDemo grobidDemo = new GrobidDemo())
				{
					grobidDemo.runDemoList(groundTruthFiles, grobIdOutputDir);
				}
				SetDateAndGrobidVersion.replaceDateAndGrobidVersion();
			}
			if(runParsCitDemo) new ParscitDemo().runDemoList(groundTruthFilesOmnipage, parsCitOutputDir);
			if(runPdfxDemo) new PdfxDemo().runDemoList(groundTruthFiles, pdfxOutputDir);			
		}

		if(runCermineMapper) deleteResultAndErrorFiles(cermineOutputDir);
		if(runGrobidMapper) deleteResultAndErrorFiles(grobIdOutputDir);
		if(runParsCitMapper) deleteResultAndErrorFiles(parsCitOutputDir);
		if(runPdfxMapper) deleteResultAndErrorFiles(pdfxOutputDir);

		// xml -> xstream.xml
		if(runCermineMapper) new CermineMapper().unmarshallFilesWithId(idList);
		if(runGrobidMapper) new GROBIDMapper().unmarshallFilesWithId(idList);
		if(runParsCitMapper) new ParsCitMapper().unmarshallFilesWithId(idList);
		if(runPdfxMapper) new PDFXMapper().unmarshallFilesWithId(idList);

		// Evaluation
		if(startEvaluation) Evaluators.main(null);

		Duration.addEnd(DurationEnum.ALL);

		if(StringUtil.isNotEmpty(Config.archiveDirName)) {
			File archiveDir = new File(Config.archiveDir, Config.archiveDirName);
			Training.copyExtractedAndStatisticsToArchive(false, archiveDir);
			writeRevisionToFile(new File("."), new File(archiveDir.getPath(), "git revision - MethodDemos.txt"));
			writeRevisionToFile(new File(Config.pGrobidOrigin), new File(archiveDir.getPath(), "git revision - grobid.txt"));			
		}
	}
	
	private static void writeRevisionToFile(File workingDir, File file) throws IOException {
		Process p1 = ExecUtil.exec(Arrays.asList("git", "branch", "--show-current"));
		FileUtils.writeStringToFile(file, ExecUtil.getOutputText(p1), StandardCharsets.UTF_8);
		Process p2 = ExecUtil.exec(Arrays.asList("git", "rev-parse", "HEAD"));
		FileUtils.writeStringToFile(file, ExecUtil.getOutputText(p2), StandardCharsets.UTF_8, true);
		Process p3 = ExecUtil.exec(Arrays.asList("git", "config", "--get", "remote.origin.url"));
		FileUtils.writeStringToFile(file, ExecUtil.getOutputText(p3), StandardCharsets.UTF_8, true);
	}

	private static void cleanOrCreateDirectory(File outputDir) throws IOException
	{
		if(outputDir.exists())
		{
			FileUtils.cleanDirectory(outputDir);
		}
		else
		{
			outputDir.mkdirs();
		}

	}

	private static List<File> getFileById(String string)
	{
		List<File> groundTruthFiles = getAllFilesAlsoFromSubDirectories(inputDir, string + ".pdf");
		return groundTruthFiles;
	}

	private static void deleteResultAndErrorFiles(File directory)
	{
		List<File> resultOrErrorFiles = Arrays.stream(directory.listFiles()).filter(file -> file.getName().endsWith("mapping.errxml") || file.getName().endsWith("-xstream.xml")).collect(Collectors.toList());
		for(File file : resultOrErrorFiles)
		{
			file.delete();
		}
	}

	private static List<File> getAllGroundTruthFilesAsOmnipage()
	{
		List<File> groundTruthFiles = getAllFilesFromDirectories(inputDir, "-omnipage.xml");
		return groundTruthFiles;
	}

	public static List<File> getAllGroundTruthFiles()
	{
		List<File> groundTruthFiles = getAllFilesAlsoFromSubDirectories(inputDir, ".pdf");
		return groundTruthFiles;
	}

	// public static List<File> getAllGroundTruthFilesAsTxt()
	// {
	// List<File> groundTruthFiles = getAllFilesFromDirectories(inputDirTxt, ".txt");
	// return groundTruthFiles;
	// }

	public static List<File> getAllFilesAlsoFromSubDirectories(File inputFolder, String extension)
	{
		return Arrays.stream(inputFolder.listFiles()).flatMap(file -> Arrays.stream(file.listFiles())).filter(file -> file.getName().endsWith(extension)).collect(Collectors.toList());
	}

	public static List<File> getAllFilesFromDirectories(File inputFolder, String extension)
	{
		return Arrays.stream(inputFolder.listFiles()).filter(file -> file.getName().endsWith(extension)).collect(Collectors.toList());
	}

}