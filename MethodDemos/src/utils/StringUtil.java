package utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class StringUtil
{
	public static boolean isEmpty(String s)
	{
		if(s == null) return true;
		return s.isEmpty();
	}

	public static boolean isNotEmpty(String s)
	{
		return !isEmpty(s);
	}

	public static String getAllValuesOfObject(Object object)
	{
		return Arrays.stream(object.getClass().getDeclaredFields()).filter(f -> f.getType() == String.class).map(f ->
		{
			try
			{
				return (String)f.get(object);
			}
			catch(IllegalAccessException e)
			{
				return null;
			}
		}).filter(Objects::nonNull).collect(Collectors.joining(" "));
	}
}
