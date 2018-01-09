package utils;

public class FileNameUtil
{

	public static String getResultFileNameFromID(String pubId)
	{
		return "result-TUW-" + pubId + "-xstream.xml";
	}

	public static String getGrobidFileNameFromID(String pubId)
	{
		return "grobid-TUW-" + pubId + "-xstream.xml";
	}

	public static String getCermineFileNameFromID(String pubId)
	{
		return "cermine-TUW-" + pubId + "-xstream.xml";
	}

	public static String getParscitFileNameFromID(String pubId)
	{
		return "parscit-TUW-" + pubId + "-xstream.xml";
	}

	public static String getPdfFileNameFromID(String pubId)
	{
		return "TUW-" + pubId + ".pdf";
	}

	public static String getOmnipageFileNameFromID(String pubId)
	{
		return "TUW-" + pubId + "-omnipage.xml";
	}

	public static String getPublicationDbFileNameFromID(String pubId)
	{
		return "TUW-" + pubId + "-publicationdb.xml";
	}

	public static String getPdfxFileNameFromID(String pubId)
	{
		return "pdfx-TUW-" + pubId + "-xstream.xml";
	}

}
