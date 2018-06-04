package utils;

import java.util.stream.Collectors;

import mapping.result.Reference;

public class ReferenceUtil
{
	public static String getConcatinatedLastNamesOfAuthors(Reference reference)
	{
		if(CollectionUtil.isEmpty(reference.getAuthors()))
		{
			return null;
		}
		return reference.getAuthors().stream().map(a -> a.getLastName()).collect(Collectors.joining(", "));
	}
}
