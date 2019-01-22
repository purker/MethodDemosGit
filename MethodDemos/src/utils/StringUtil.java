package utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.grobid.core.utilities.Triple;

import mapping.result.Label;

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

	public static String objectListToString(List<?> objectList, String separator)
	{
		List<String> stringList = objectListToStringList(objectList, separator);

		return StringUtil.notNullJoinedList(stringList, ",");
	}

	public static List<String> objectListToStringList(List<?> list, String separator)
	{
		List<String> stringList = new ArrayList<>();
		for(Object object : list)
		{
			if(object != null)
			{
				if(object instanceof List)
				{
					String listString = notNullJoinedList((List<?>)object, separator);
					stringList.add(listString);
				}
				else
				{
					stringList.add(object.toString());
				}
			}
		}
		return stringList;
	}

	public static String notNullJoinedList(Collection<?> list, String separator)
	{
		return list.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(separator));
	}

	public static List<String> getAsStringList(Collection<?> collection)
	{
		List<String> list = collection.stream().map(Object::toString).collect(Collectors.toList());

		return list;
	}

	public static String getLabelIfPresent(Object key)
	{
		if(key instanceof Label)
			return ((Label)key).getLabel();
		else
			return key.toString();
	}

	/**
	 * as substring but taking into account the length of the string to avoid IndexOutOfBoundsException
	 * 
	 * @param string
	 * @param length
	 * @return
	 */
	public static String substringMaxLength(String string, int length)
	{
		return string.substring(0, Integer.min(length, string.length()));
	}

}
