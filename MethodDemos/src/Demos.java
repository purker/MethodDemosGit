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
	private static File inputDir = new File("D:/output/GroundTruth");

	static File cermineOutputDir = new File("D:/output/Cermine");
	static File grobIdOutputDir = new File("D:/output/Grobid");
	static File parsCitOutputDir = new File("D:/output/ParsCit");
	static File pdfxOutputDir = new File("D:/output/PDFX");

	public static void main(String[] args) throws IOException
	{
		List<File> groundTruthFiles = getAllGroundTruthFiles().subList(0, 1);

		FileUtils.cleanDirectory(cermineOutputDir);
		FileUtils.cleanDirectory(grobIdOutputDir);
		FileUtils.cleanDirectory(parsCitOutputDir);
		FileUtils.cleanDirectory(pdfxOutputDir);

		new GROBIDDemo().runDemoInBatch("D:/output/GroundTruth-subset", grobIdOutputDir.getPath());
		new GROBIDDemo().runDemoInBatch("D:/TU/Masterarbeit/Papers/Methoden/", grobIdOutputDir.getPath());

		// new CERMINEDemo().runDemo(groundTruthFiles, cermineOutputDir);
		// new GROBIDDemo().runDemo(groundTruthFiles, grobIdOutputDir);
		new ParsCitDemo().runDemo(groundTruthFiles, parsCitOutputDir);
		new PDFXDemo().runDemo(groundTruthFiles, pdfxOutputDir);
	}

	public static List<File> getAllGroundTruthFiles()
	{
		List<File> groundTruthFiles = getAllFilesFromDirectories(inputDir, ".pdf");
		return groundTruthFiles;
	}

	public static List<File> getAllFilesFromDirectories(File inputFolder, String extension)
	{
		return Arrays.stream(inputFolder.listFiles()).flatMap(file -> Arrays.stream(file.listFiles())).filter(file -> file.getName().endsWith(extension)).collect(Collectors.toList());
	}
}
