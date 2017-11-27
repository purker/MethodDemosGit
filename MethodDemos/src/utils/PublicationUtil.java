package utils;

import java.io.File;

import com.google.common.base.CharMatcher;

public class PublicationUtil
{

	public static String getIdFromFileName(String fileName)
	{
		String id = CharMatcher.DIGIT.retainFrom(fileName);
		return "TUW-" + id;
	}

	public static String getIdFromFile(File file)
	{
		return getIdFromFileName(file.getName());
	}
}
