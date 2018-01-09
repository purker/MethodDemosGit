package utils;

import java.util.stream.Collectors;

import mapping.result.Reference;

public class ReferenceUtil
{
	public static Integer getReferenceIdNumber(Reference reference)
	{
		return getReferenceIdNumber(reference.getId());

	}

	public static String getConcatinatedLastNamesOfAuthors(Reference reference)
	{
		if(CollectionUtil.isEmpty(reference.getAuthors()))
		{
			return null;
		}
		return reference.getAuthors().stream().map(a -> a.getLastName()).collect(Collectors.joining(", "));
	}

	public static Integer getReferenceIdNumber(String referenceId)
	{
		if(referenceId == null) return null;
		return new Integer(referenceId.replace("ref", ""));
	}

	public static String getRefIdFromNumber(Integer x)
	{
		return "ref" + x;
	}

}
