package utils;

import java.util.Collection;
import java.util.Collections;

public class CollectionUtil
{
	public static boolean isEmpty(Collection<?> collection)
	{
		return collection == null || collection.size() == 0;
	}

	public static boolean isNotEmpty(Collection<?> collection)
	{
		return !isEmpty(collection);
	}

	public static <T> Collection<T> emptyIfNull(Collection<T> iterable)
	{
		return iterable == null ? Collections.<T> emptyList() : iterable;
	}
}
