package demos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;

import config.Config;
import mapping.cermine.CermineMapper;
import mapping.grobid.GROBIDMapper;
import mapping.parscit.ParsCitMapper;
import mapping.pdfx.PDFXMapper;

/**
 * Runs all Demos with Ground Truth files from inputDir
 *
 */
public class Demos
{
	private static final boolean USE_SPECIFIC_OUTPUTDIR = true;

	public static File inputDir = Config.groundTruth;

	// public static File inputDir = new File("D:/output/nosubdir");
	// public static File inputDirTxt = new File("D:/output/methods/Cermine");

	public static File cermineOutputDir = Config.cermineOutputDir;
	public static File grobIdOutputDir = Config.grobIdOutputDir;
	public static File parsCitOutputDir = Config.parsCitOutputDir;
	public static File pdfxOutputDir = Config.pdfxOutputDir;
	public static File[] outputDirs = {cermineOutputDir, grobIdOutputDir, parsCitOutputDir, pdfxOutputDir};
	public static File loggingDir = new File("D:/output/methods");

	static File allOutputDir = new File("D:/output/methods/all");

	public static void main(String[] args) throws Exception
	{
		executeDemos();
	}

	public static void executeDemos() throws IOException, JAXBException
	{
		List<String> idList = Arrays.asList("137078", "138011", "138447", "138544", "138547", "139299", "139761", "139769", "139781", "139785", "140047", "140048", "140229", "140253", "140308", "140533", "140867", "140895", "140983", "141024", "141065", "141121", "141140", "141336", "141618", "141758", "168222", "168482", "169511", "172697", "174216", "175428", "176087", "177140", "179146", "180162", "181199", "182414", "182899", "185321", "185441", "186227", "189842", "191715", "191977", "192724", "194085", "194561", "194660", "197422", "197852", "198400", "198401", "198405", "198408", "200745", "200748", "200948", "200950", "200959", "201066", "201160", "201167", "202034", "225252", "202824", "203409", "203924", "201821", "204724", "205557", "205933", "213513", "216744", "217690", "217971", "221215", "223906", "223973", "226000", "226016", "228620", "231707", "233317", "233657", "236063", "236120", "237297", "240858", "245336", "245799", "247301", "247741", "247743", "251544", "252847", "255712", "256654", "257397", "257870");
		// List<String> idList = Arrays.asList("200745", "200948", "225252", "201821", "247743");

		boolean runDemos = true;
		boolean runCermineDemo = false;
		boolean runGrobidDemo = false;
		boolean runParsCitDemo = false;
		boolean runPdfxDemo = false;
		boolean runCermineMapper = false;
		boolean runGrobidMapper = false;
		boolean runParsCitMapper = true;
		boolean runPdfxMapper = false;
		// List<File> groundTruthFiles = getFileById("226016");
		List<File> groundTruthFiles = getAllGroundTruthFilesByIds(idList);
		List<File> groundTruthFilesOmnipage = getAllGroundTruthFilesAsOmnipage(idList);

		// set directories and clean
		if(!USE_SPECIFIC_OUTPUTDIR)
		{
			if(runDemos)
			{
				cleanOrCreateDirectory(allOutputDir);
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
				if(runCermineDemo) cleanOrCreateDirectory(cermineOutputDir);
				if(runGrobidDemo) cleanOrCreateDirectory(grobIdOutputDir);
				// if(runParsCitDemo) cleanOrCreateDirectory(parsCitOutputDir);
				// if(runPdfxDemo) cleanOrCreateDirectory(pdfxOutputDir);
			}
			if(runCermineMapper) deleteResultAndErrorFiles(cermineOutputDir);
			if(runGrobidMapper) deleteResultAndErrorFiles(grobIdOutputDir);
			// if(runParsCitMapper) deleteResultAndErrorFiles(parsCitOutputDir);
			if(runPdfxMapper) deleteResultAndErrorFiles(pdfxOutputDir);
		}
		if(runDemos)
		{
			if(runCermineDemo) new CERMINEDemo().runDemo(groundTruthFiles, cermineOutputDir);
			if(runGrobidDemo) new GROBIDDemo().runDemo(groundTruthFiles, grobIdOutputDir);
			if(runParsCitDemo) new ParsCitDemo().runDemo(groundTruthFilesOmnipage, parsCitOutputDir);
			if(runPdfxDemo) new PDFXDemo().runDemo(groundTruthFiles, pdfxOutputDir);
		}

		if(runCermineMapper) new CermineMapper().unmarshallFilesWithId(cermineOutputDir, idList);
		if(runGrobidMapper) new GROBIDMapper().unmarshallFilesWithId(grobIdOutputDir, idList);
		if(runParsCitMapper) new ParsCitMapper().unmarshallFilesWithId(parsCitOutputDir, idList);
		if(runPdfxMapper) new PDFXMapper().unmarshallFilesWithId(pdfxOutputDir, idList);

	}

	private static List<File> getAllGroundTruthFilesAsOmnipage(List<String> idList)
	{
		List<File> list = new ArrayList<>();

		for(String string : idList)
		{
			String fileName = "TUW-" + string + "-omnipage.xml";
			File file = new File(inputDir, fileName);
			System.out.println(file + " " + file.exists());
			list.add(file);
		}

		return list;
	}

	static List<File> getAllGroundTruthFilesByIds(List<String> idList)
	{
		List<File> list = new ArrayList<>();

		for(String string : idList)
		{
			String fileName = "TUW-" + string + ".pdf";
			File file = new File(inputDir, fileName);
			System.out.println(file + " " + file.exists());
			list.add(file);
		}

		return list;
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