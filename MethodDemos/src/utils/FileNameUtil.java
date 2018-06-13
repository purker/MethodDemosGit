package utils;

import config.Config;
import method.Method;

public class FileNameUtil
{
	public static String getResultFileNameByMethodAndId(Method method, String pubId)
	{
		return method.getName() + "-TUW-" + pubId + "-xstream.xml";
	}

	public static String getResultFileNameFromID(String pubId)
	{
		return "result-" + Config.publicationPrefix + pubId + "-xstream.xml";
	}

	public static String getCermineFileNameFromID(String pubId)
	{
		return getResultFileNameByMethodAndId(Method.CERMINE, pubId);
	}

	public static String getGrobidFileNameFromID(String pubId)
	{
		return getResultFileNameByMethodAndId(Method.GROBID, pubId);
	}

	public static String getParscitFileNameFromID(String pubId)
	{
		return getResultFileNameByMethodAndId(Method.PARSCIT, pubId);
	}

	public static String getPdfxFileNameFromID(String pubId)
	{
		return getResultFileNameByMethodAndId(Method.PDFX, pubId);
	}

	public static String getOmnipageFileNameFromID(String pubId)
	{
		return "TUW-" + pubId + "-omnipage.xml";
	}

	public static String getPublicationDbFileNameFromID(String pubId)
	{
		return "TUW-" + pubId + "-publicationdb.xml";
	}

	public static String getPdfFileNameFromID(String pubId)
	{
		return "TUW-" + pubId + ".pdf";
	}

}
