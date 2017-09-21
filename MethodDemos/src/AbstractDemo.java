import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import utils.SimplestFormatter;

public abstract class AbstractDemo
{
	protected static Logger logger = Logger.getLogger(AbstractDemo.class.getName());

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

			String outputFileName = createOutputFileName(inputFile);
			File outputFile = new File(outputFolder, getFileNamePrefix() + outputFileName + ".xml");
			File errorFile = new File(outputFolder, getFileNamePrefix() + outputFileName + ".err.txt");
			try
			{
				log(getMethodName() + ": start processing file " + inputFile);
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
			log(String.format(getMethodName() + ": " + Math.round(elapsed) + "s" + " | %3d%% done (%3d out of %3d)\n", percentage, i, files.size()));
		}
	}

	void log(String string)
	{
		logger.info(string);
	}

	private String getFileNamePrefix()
	{
		return getMethodName() + "-";
	}

	static
	{
		try
		{
			FileHandler fileHandler = new FileHandler(Demos.allOutputDir + "/result.log", false);
			fileHandler.setFormatter(new SimplestFormatter());
			logger.addHandler(fileHandler);
		}
		catch(SecurityException | IOException e)
		{
			e.printStackTrace();
		}
	}

	public String createOutputFileName(File inputFile)
	{

		String inputFileName = FilenameUtils.removeExtension(inputFile.getName());
		return inputFileName;
	}

	abstract String getMethodName();

	/**
	 * @param inputFile
	 * @param outputFile
	 * @return null or error text if an exception occurs
	 * @throws IOException
	 */
	abstract String runDemo(File inputFile, File outputFile) throws IOException;
}
