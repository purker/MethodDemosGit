package utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import config.Config;

public class FileCollectionUtil
{

	public static List<File> getResultFiles()
	{
		File directory = Config.groundTruthResults;

		checkIfContainsFiles(directory);

		return Arrays.asList(directory.listFiles());
	}

	public static List<File> getCermineResultFiles()
	{
		File directory = Config.cermineOutputDir;

		checkIfContainsFiles(directory);

		List<File> files = Arrays.asList(directory.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(Config.xStreamFileExtension);
			}
		}));
		return files;
	}

	public static List<File> getGrobidResultFiles()
	{
		File directory = Config.grobIdOutputDir;

		checkIfContainsFiles(directory);

		List<File> files = Arrays.asList(directory.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(Config.xStreamFileExtension);
			}
		}));
		return files;
	}

	public static List<File> getParscitXmlFiles()
	{
		File directory = Config.parsCitOutputDir;

		checkIfContainsFiles(directory);

		List<File> files = Arrays.asList(directory.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(Config.parscitFileExtension);
			}
		}));
		return files;
	}

	public static List<File> getParscitResultFiles()
	{
		File directory = Config.parsCitOutputDir;

		checkIfContainsFiles(directory);

		List<File> files = Arrays.asList(directory.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(Config.xStreamFileExtension);
			}
		}));
		return files;
	}

	public static List<File> getPdfxResultFiles()
	{
		File directory = Config.pdfxOutputDir;

		checkIfContainsFiles(directory);

		List<File> files = Arrays.asList(directory.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(Config.xStreamFileExtension);
			}
		}));
		return files;
	}

	private static void checkIfContainsFiles(File directory)
	{
		if(!directory.exists())
		{
			throw new IllegalArgumentException("provided input file must exist: " + directory);
		}
		if(!directory.isDirectory())
		{
			throw new IllegalArgumentException("provided input file must be a directory: " + directory);
		}
		if(directory.list().length == 0)
		{
			throw new IllegalArgumentException("provided directory can't be empty: " + directory);
		}

	}

	public static List<String> getFilesIdsWithoutPrefix(List<File> files)
	{
		List<String> ids = files.stream().map(f -> PublicationUtil.getIdFromFileWithoutPrefix(f)).collect(Collectors.toList());

		return ids;
	}
}
