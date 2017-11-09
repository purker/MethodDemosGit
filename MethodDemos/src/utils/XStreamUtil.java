package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.ReferenceAuthor;
import mapping.result.ReferenceCitation;
import mapping.result.Section;

public class XStreamUtil
{
	public static void convertToXmL(Object object, File file, PrintStream out, boolean exitOnError)
	{
		try
		{
			XStream xStream = getXStream();
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
		XStream xStream = new XStream(new DomDriver(StandardCharsets.UTF_8.name()));
		xStream.setMode(XStream.SINGLE_NODE_XPATH_ABSOLUTE_REFERENCES);

		xStream.alias("Publication", Publication.class);
		xStream.alias("Author", Author.class);
		xStream.alias("Affiliation", Affiliation.class);
		xStream.alias("Section", Section.class);
		xStream.alias("ReferenceCitation", ReferenceCitation.class);
		xStream.alias("Reference", Reference.class);
		xStream.alias("ReferenceAuthor", ReferenceAuthor.class);

		return xStream;
	}

	public static <T> T convertFromXML(File file, Class<T> clazz)
	{
		XStream xStream = getXStream();
		return clazz.cast(xStream.fromXML(file));
	}

	public static <T> T convertFromString(String string, Class<T> clazz)
	{
		XStream xStream = getXStream();
		return clazz.cast(xStream.fromXML(string));
	}
}
