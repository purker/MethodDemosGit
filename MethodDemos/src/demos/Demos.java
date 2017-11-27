package demos;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import mapping.cermine.CermineMapper;
import mapping.grobid.GROBIDMapper;
import mapping.parscit.ParsCitMapper;
import mapping.pdfx.PDFXMapper;

/**
 * Runs all Demos with Ground Truth files
 *
 */
public class Demos
{
	private static final boolean USE_SPECIFIC_OUTPUTDIR = true;

	public static File inputDir = new File("D:/output/GroundTruth");
	// public static File inputDirTxt = new File("D:/output/methods/Cermine");
 
	public static File cermineOutputDir = new File("D:/output/methods/cermine");
	public static File grobIdOutputDir = new File("D:/output/methods/grobid");
	public static File parsCitOutputDir = new File("D:/output/methods/parscit");
	public static File pdfxOutputDir = new File("D:/output/methods/pdfx");
	public static File[] outputDirs =
	{cermineOutputDir, grobIdOutputDir, parsCitOutputDir, pdfxOutputDir};

	static File allOutputDir = new File("D:/output/methods/all");

	public static void main(String[] args) throws Exception
	{
		executeDemos();
	}

	public static void executeDemos() throws IOException, JAXBException
	{
		boolean runDemos = true;
		boolean runCermineDemo = false;
		boolean runGrobidDemo = false;
		boolean runParsCitDemo = false;
		boolean runPdfxDemo = true;
		boolean runCermineMapper = false;
		boolean runGrobidMapper = false;
		boolean runParsCitMapper = false;
		boolean runPdfxMapper = true;
		List<File> groundTruthFiles = getFileById("203924");
		// List<File> groundTruthFiles = getAllGroundTruthFiles();// .subList(0, 1);
		List<File> groundTruthFilesOmnipage = getAllGroundTruthFilesAsOmnipage();// .subList(0, 11);

		if(!USE_SPECIFIC_OUTPUTDIR)
		{
			if(runDemos)
			{
				FileUtils.cleanDirectory(allOutputDir);
			}
			deleteResultAndErrorFiles(allOutputDir);

			cermineOutputDir = allOutputDir;
			grobIdOutputDir = allOutputDir;
			parsCitOutputDir = allOutputDir;
			pdfxOutputDir = allOutputDir;
		}
		else
		{
			if(runDemos)
			{
				// if(runCermineDemo) FileUtils.cleanDirectory(cermineOutputDir);
				// if(runGrobidDemo) FileUtils.cleanDirectory(grobIdOutputDir);
				// if(runParsCitDemo) FileUtils.cleanDirectory(parsCitOutputDir);
				// if(runPdfxDemo) FileUtils.cleanDirectory(pdfxOutputDir);
			}
			if(runCermineMapper) deleteResultAndErrorFiles(cermineOutputDir);
			if(runGrobidMapper) deleteResultAndErrorFiles(grobIdOutputDir);
			if(runParsCitMapper) deleteResultAndErrorFiles(parsCitOutputDir);
			if(runPdfxMapper) deleteResultAndErrorFiles(pdfxOutputDir);
		}

		if(runDemos)
		{
			if(runCermineDemo) new CERMINEDemo().runDemo(groundTruthFiles, cermineOutputDir);
			if(runGrobidDemo) new GROBIDDemo().runDemo(groundTruthFiles, grobIdOutputDir);
			if(runParsCitDemo) new ParsCitDemo().runDemo(groundTruthFilesOmnipage, parsCitOutputDir); // uses txt output from Cermine
			if(runPdfxDemo) new PDFXDemo().runDemo(groundTruthFiles, pdfxOutputDir);
		}

		if(runCermineMapper) new CermineMapper().unmarshallFiles(cermineOutputDir);
		if(runGrobidMapper) new GROBIDMapper().unmarshallFiles(grobIdOutputDir);
		if(runParsCitMapper) new ParsCitMapper().unmarshallFiles(parsCitOutputDir);
		if(runPdfxMapper) new PDFXMapper().unmarshallFiles(pdfxOutputDir);
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
		List<File> groundTruthFiles = getAllFilesAlsoFromSubDirectories(inputDir, "-omnipage.xml");
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
