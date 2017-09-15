import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public abstract class AbstractDemo
{
	/**
	 * Iterates over files and invokes runDemo for them. Also measures the execution time.
	 * 
	 * @param files
	 * @param outputFolder
	 * @throws IOException
	 */
	public void runDemo(List<File> files, File outputFolder) throws IOException
	{
		int i = 0;
		for(File inputFile : files)
		{
			long start = System.currentTimeMillis();
			float elapsed;

			File outputFile = new File(outputFolder, FilenameUtils.removeExtension(inputFile.getName()) + ".xml");
			File errorFile = new File(outputFolder, FilenameUtils.removeExtension(inputFile.getName()) + ".err.txt");
			try
			{
				System.out.print("Processing file " + outputFile);
				String errorString = runDemo(inputFile, outputFile);

				// write result xml to outputfolder
				if(errorString != null && !errorString.isEmpty())
				{
					FileUtils.writeStringToFile(errorFile, errorString, StandardCharsets.UTF_8);
				}
			}
			finally
			{
				long end = System.currentTimeMillis();
				elapsed = (end - start) / 1000F;
			}

			i++;
			int percentage = i * 100 / files.size();
			System.out.print(" | " + Math.round(elapsed) + "s");
			System.out.printf(" | %3d%% done (%3d out of %3d)\n", percentage, i, files.size());

		}
	}

	abstract String runDemo(File inputFile, File outputFile) throws IOException;
}
