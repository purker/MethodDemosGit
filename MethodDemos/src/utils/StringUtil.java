package utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
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

	public static String getAllValuesOfObject(Object object)
	{
		if(object == null)
		{
			return "null";
		}

		Field[] declaredFields = object.getClass().getDeclaredFields();

		System.out.println(Arrays.toString(declaredFields));
		String s = Arrays.stream(object.getClass().getDeclaredFields()).filter(f -> f.getType() == String.class).map(f ->
		{
			try
			{
				f.setAccessible(true);
				System.err.print(f.getName());
				System.err.println(": " + f.get(object));
				return (String)f.get(object);
			}
			catch(IllegalAccessException e)
			{
				return null;
			}
		}).filter(Objects::nonNull).collect(Collectors.joining(" "));
		System.err.println(s);
		return Arrays.stream(object.getClass().getDeclaredFields()).filter(f -> f.getType() == String.class).map(f ->
		{
			try
			{
				f.setAccessible(true);
				return (String)f.get(object);
			}
			catch(IllegalAccessException e)
			{
				return null;
			}
		}).filter(Objects::nonNull).collect(Collectors.joining("|"));
	}

	public static String getListAsStrings(List<?> list) throws IOException
	{
		if(list instanceof List)
		{
			StringBuffer sb = new StringBuffer();
			sb.append("\"");
			for(Iterator<?> iterator = ((List<?>)list).iterator(); iterator.hasNext();)
			{
				Object o = iterator.next();

				sb.append(getAllValuesOfObject(o));
				if(iterator.hasNext())
				{
					sb.append("\n");
				}
			}
			sb.append("\"");
			return sb.toString();
		}
		else
		{
			throw new IOException("no list");
		}
	}

	public static String notNullJoinedList(List<String> list, String separaptor)
	{
		return list.stream().filter(Objects::nonNull).collect(Collectors.joining(separaptor));
	}
}
