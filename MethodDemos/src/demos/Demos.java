package demos;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

/**
 * Runs all Demos with Ground Truth files
 *
 */
public class Demos
{
	private static final boolean USE_SPECIFIC_OUTPUTDIR = false;

	private static File inputDir = new File("D:/output/GroundTruth");
	private static File inputDirTxt = new File("D:/output/Cermine");

	static File cermineOutputDir = new File("D:/output/Cermine");
	static File grobIdOutputDir = new File("D:/output/Grobid");
	static File parsCitOutputDir = new File("D:/output/ParsCit");
	static File pdfxOutputDir = new File("D:/output/PDFX");

	static File allOutputDir = new File("D:/output/all");

	public static void main(String[] args) throws IOException
	{
		List<File> groundTruthFiles = getAllGroundTruthFiles().subList(0, 1);

		FileUtils.cleanDirectory(allOutputDir);
		FileUtils.cleanDirectory(cermineOutputDir);
		FileUtils.cleanDirectory(grobIdOutputDir);
		FileUtils.cleanDirectory(parsCitOutputDir);
		FileUtils.cleanDirectory(pdfxOutputDir);

		if(!USE_SPECIFIC_OUTPUTDIR)
		{
			cermineOutputDir = allOutputDir;
			grobIdOutputDir = allOutputDir;
			parsCitOutputDir = allOutputDir;
			pdfxOutputDir = allOutputDir;

			inputDirTxt = allOutputDir;
		}

		new CERMINEDemo().runDemo(groundTruthFiles, cermineOutputDir);
		new GROBIDDemo().runDemo(groundTruthFiles, grobIdOutputDir);
		new ParsCitDemo().runDemo(getAllGroundTruthFilesAsTxt(), parsCitOutputDir); // uses txt output from Cermine
		new PDFXDemo().runDemo(groundTruthFiles, pdfxOutputDir);

	}

	public static List<File> getAllGroundTruthFiles()
	{
		List<File> groundTruthFiles = getAllFilesAlsoFromSubDirectories(inputDir, ".pdf");
		return groundTruthFiles;
	}

	public static List<File> getAllGroundTruthFilesAsTxt()
	{
		List<File> groundTruthFiles = getAllFilesFromDirectories(inputDirTxt, ".txt");
		return groundTruthFiles;
	}

	public static List<File> getAllFilesAlsoFromSubDirectories(File inputFolder, String extension)
	{
		return Arrays.stream(inputFolder.listFiles()).flatMap(file -> Arrays.stream(file.listFiles())).filter(file -> file.getName().endsWith(extension)).collect(Collectors.toList());
	}

	public static List<File> getAllFilesFromDirectories(File inputFolder, String extension)
	{
		return Arrays.stream(inputFolder.listFiles()).filter(file -> file.getName().endsWith(extension)).collect(Collectors.toList());
	}
}
