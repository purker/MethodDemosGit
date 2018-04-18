package utils;

import java.util.ArrayList;
import java.util.List;
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

	public static String objectListToString(List<Object> objectList, String separator)
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

	public static String notNullJoinedList(List<?> list, String separator)
	{
		return list.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(separator));
	}

}
