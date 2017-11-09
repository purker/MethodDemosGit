package utils;

import java.util.stream.Collectors;

import mapping.result.Reference;

public class ReferenceUtil
{
	public static Integer getReferenceIdNumber(String referenceId)
	{
		return new Integer(referenceId.replace("ref", ""));
	}

	public static String getConcatinatedLastNamesOfAuthors(Reference reference)
	{
		return reference.getAuthors().stream().map(a -> a.getLastName()).collect(Collectors.joining(", "));
	}

}
