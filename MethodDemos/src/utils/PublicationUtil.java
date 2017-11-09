package utils;

import com.google.common.base.CharMatcher;

public class PublicationUtil
{

	public static String getIdFromFileName(String fileName)
	{
		String id = CharMatcher.DIGIT.retainFrom(fileName);
		return "TUW-" + id;
	}
}
