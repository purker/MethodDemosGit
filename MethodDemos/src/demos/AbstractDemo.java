package demos;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import config.Config;
import method.Method;
import utils.PublicationUtil;
import utils.SimplestFormatter;

public abstract class AbstractDemo
{
	protected static Logger logger = Logger.getLogger(AbstractDemo.class.getName());
	private static final boolean OVERRIDE_EXISTING = true; // only map file, if output file not existing

	/**
	 * Iterates over files and invokes runDemo for them. Also measures the execution time.
	 * 
	 * @param files
	 * @param outputFolder
	 * @throws IOException
	 */
	public void runDemoList(List<File> files, File outputFolder) throws IOException
	{
		int i = 0;
		for(File inputFile : files)
		{
			long start = System.currentTimeMillis();
			float elapsed;

			String outputFileName = createOutputFileName(inputFile);
			File outputFile = new File(outputFolder, getFileNamePrefix() + outputFileName + ".xml");
			File errorFile = new File(outputFolder, getFileNamePrefix() + outputFileName + "-demo.errtxt");
			try
			{
				log(getMethodName() + ": start processing file " + inputFile);

				// Lambda Runnable
				// Runnable task = () ->
				{
					String errorString;
					try
					{
						if(!OVERRIDE_EXISTING && outputFile.exists())
						{
							System.out.println("already exists: " + outputFile);
							continue;
						}
						errorString = runDemoSingleFile(inputFile, outputFile);
						// write result xml to outputfolder
						if(errorString != null && !errorString.isEmpty())
						{
							FileUtils.writeStringToFile(errorFile, errorString, StandardCharsets.UTF_8);
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
						try
						{
							e.printStackTrace(new PrintStream(errorFile));
						}
						catch(IOException e1)
						{
							System.err.println("Error writing errorFile for " + inputFile);
							e.printStackTrace();
						}
					}

				} ;

				// start the thread
				// new Thread(task).start();

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

	protected String getMethodName()
	{
		return getMethod().getName();
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
			FileHandler fileHandler = new FileHandler(Config.loggingDir + "/result.log", true);
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

	protected abstract Method getMethod();

	/**
	 * @param inputFile
	 * @param outputFile
	 * @return null or error text if an exception occurs
	 * @throws IOException
	 */
	abstract String runDemoSingleFile(File inputFile, File outputFile) throws IOException;

	public void runDemoListWithinIdList(List<File> groundTruthFiles, File outputDir, List<String> idList) throws IOException
	{
		List<File> files = groundTruthFiles.stream().filter(f -> idList.contains(PublicationUtil.getIdFromFileWithoutPrefix(f))).collect(Collectors.toList());
		runDemoList(files, outputDir);

	}

}
