package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.thoughtworks.xstream.XStream;

public class XStreamUtil
{
	public static void convertToXmL(Object object, File file, PrintStream out, boolean exitOnError)
	{
		try
		{
			XStream xStream = getXStream();
			xStream.aliasPackage("", "mapping.result");
			// xStream.setMode(XStream.ID_REFERENCES);
			xStream.toXML(object, new FileOutputStream(file));
		}
		catch(FileNotFoundException e)
		{
			FailureUtil.failureExit(e, out, "exit program - failure writing publication xml file", exitOnError);
		}

	}

	private static XStream getXStream()
	{
		XStream xStream = new XStream();
		xStream.aliasPackage("", "jaxb");
		return xStream;
	}

	public static <T> T convertFromXML(File file, Class<T> clazz)
	{
		XStream xStream = getXStream();
		return clazz.cast(xStream.fromXML(file));
	}
}
