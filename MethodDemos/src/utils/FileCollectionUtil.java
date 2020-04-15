package utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import config.Config;
import demos.Demos;
import evaluation.tools.CollectionEnum;
import evaluation.tools.EvalInformationType;
import evaluation.tools.WriterType;
import method.Method;

/**
 * getResultFiles(): xstream.xml in output/result/ </br>
 * getResultFilesByMethod(Method method): xstream.xml in output/extracted/<method>/
 */
public class FileCollectionUtil
{

	public static List<File> getResultFiles()
	{
		File directory = Config.groundTruthResults;

		checkIfContainsFiles(directory);

		return Arrays.asList(directory.listFiles());
	}

	/**
	 * @param method
	 * @return xstream file for method
	 */
	public static List<File> getResultFilesByMethod(Method method)
	{
		File directory = method.getResultDirectory();

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

	/**
	 * @return xstream file for method
	 */
	public static List<File> getCermineResultFiles()
	{
		return getResultFilesByMethod(Method.CERMINE);
	}

	/**
	 * @return xstream file for method
	 */
	public static List<File> getGrobidResultFiles()
	{
		return getResultFilesByMethod(Method.GROBID);
	}

	/**
	 * @return xstream file for method
	 */
	public static List<File> getParscitResultFiles()
	{
		return getResultFilesByMethod(Method.PARSCIT);
	}

	/**
	 * @return xstream file for method
	 */
	public static List<File> getPdfxResultFiles()
	{
		return getResultFilesByMethod(Method.PDFX);
	}

	public static List<File> getCermineExtractedFiles()
	{
		return getExtractedFilesByMethod(Method.CERMINE);
	}

	public static List<File> getGrobidExtractedFiles()
	{
		return getExtractedFilesByMethod(Method.GROBID);
	}

	public static List<File> getParscitExtractedFiles()
	{
		return getExtractedFilesByMethod(Method.PARSCIT);
	}

	public static List<File> getPdfxExtractedFiles()
	{
		return getExtractedFilesByMethod(Method.PDFX);
	}

	public static List<File> getExtractedFilesByMethod(Method method)
	{
		File directory = method.getResultDirectory();

		checkIfContainsFiles(directory);

		List<File> files = Arrays.asList(directory.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(Config.extractedFileExtension) && !name.endsWith(Config.xStreamFileExtension);
			}
		}));
		return files;
	}

	public static File getCermineResultFileById(String pubId)
	{
		return getResultFilesByMethodAndId(Method.CERMINE, pubId);
	}

	public static File getGrobidResultFileById(String pubId)
	{
		return getResultFilesByMethodAndId(Method.GROBID, pubId);
	}

	public static File getParscitResultFileById(String pubId)
	{
		return getResultFilesByMethodAndId(Method.PARSCIT, pubId);
	}

	public static File getPdfxResultFileById(String pubId)
	{
		return getResultFilesByMethodAndId(Method.PDFX, pubId);
	}

	public static File getResultFilesByMethodAndId(Method method, String pubId)
	{
		File file = new File(method.getResultDirectory(), FileNameUtil.getResultFileNameByMethodAndId(method, pubId));
		return file;
	}

	public static File getGroundTruthResultFileById(String pubId)
	{
		File file = new File(Config.groundTruthResults, FileNameUtil.getResultFileNameFromID(pubId));
		return file;
	}

	public static File getPdfFileById(String pubId)
	{
		File file = new File(Config.groundTruth, FileNameUtil.getPdfFileNameFromID(pubId));
		return file;
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
			throw new IllegalArgumentException("provided directory contains no data: " + directory);
		}

	}

	public static List<String> getFilesIdsWithoutPrefix(List<File> files)
	{
		List<String> ids = files.stream().map(f -> PublicationUtil.getIdFromFileWithoutPrefix(f)).collect(Collectors.toList());

		return ids;
	}

	public static String replaceFileExtension(String file, WriterType writerType)
	{
		String replaced = writerType.replace(file);

		return replaced;
	}

	public static List<File> getExtractedFiles(Method method)
	{
		return getExtractedFiles(method.getResultDirectory(), method);
	}

	public static List<File> getExtractedFiles(File file, Method method)
	{
		List<File> list = Arrays.asList(file.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File file, String fileName)
			{
				// e.g. "cermine-TUW-137078.xml"
				return fileName.matches(method.getName() + "-TUW-\\d{6}.xml");
			}
		}));

		return list;
	}

	public static String getFileByMethodAndSetResultType(String file, CollectionEnum setResultEnum, Method method)
	{
		file = setResultEnum.replace(file);
		file = method.replace(file);

		return file;
	}

	public static String replaceMethodAndTypeAndSetResultEnum(String file, Method method, EvalInformationType type, CollectionEnum setResultType)
	{
		file = method.replace(file);
		file = type.replace(file);
		file = setResultType.replace(file);

		return file;
	}

	public static Collection<File> getStatisticsCSVFiles()
	{
		Collection<File> list = FileUtils.listFiles(new File(Config.statisticsFolder), new String[]{"csv"}, true);

		return list;
	}

	public static List<File> getExtractedFiles(Method method, List<String> idList)
	{
		File directory = method.getResultDirectory();

		checkIfContainsFiles(directory);

		List<File> files = Arrays.asList(directory.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File file)
			{
				String id = PublicationUtil.getIdFromFileWithoutPrefix(file);
				return idList.contains(id) && file.getName().endsWith(Config.extractedFileExtension) && !file.getName().endsWith(Config.xStreamFileExtension);
			}
		}));
		return files;
	}

	public static List<File> getAllGroundTruthFilesByIds(List<String> idList)
	{
		List<File> list = new ArrayList<>();

		for(String pubId : idList)
		{
			String fileName = FileNameUtil.getPdfFileNameFromID(pubId);
			File file = new File(Demos.inputDir, fileName);

			list.add(file);
		}

		return list;
	}

	public static List<File> getAllGroundTruthFilesOmnipageById(List<String> idList)
	{
		List<File> list = new ArrayList<>();

		for(String pubId : idList)
		{
			String fileName = FileNameUtil.getOmnipageFileNameFromID(pubId);
			File file = new File(Demos.inputDir, fileName);

			list.add(file);
		}

		return list;
	}
}