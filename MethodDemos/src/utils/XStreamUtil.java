package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.reflect.FieldUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.FieldDictionary;
import com.thoughtworks.xstream.converters.reflection.SortableFieldKeySorter;
import com.thoughtworks.xstream.converters.reflection.SunUnsafeReflectionProvider;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.TypePermission;

import mapping.IdConverter;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.ReferenceAuthor;
import mapping.result.ReferenceCitation;
import mapping.result.Section;

public class XStreamUtil
{
	private static final String[] referenceFieldOrder = new String[]{"serialVersionUID", "id", "referenceIdString", "marker", "title", "publicationType", "source", "publisher", "editors", "authors", "edition", "location", "volume", "issue", "chapter", "note", "pageFrom", "pageTo", "publicationDateString", "publicationYear", "publicationYearSuffix", "publicationMonth", "publicationDay", "publicationDate", "doi", "url", "type", "referenceText", "publication"};

	public static void convertToXml(Object object, File file, PrintStream out, boolean exitOnError)
	{
		try
		{
			convertToXml(object, new FileOutputStream(file), out, exitOnError);
		}
		catch(FileNotFoundException e)
		{
			FailureUtil.failureExit(e, out, "exit program - failure writing publication xml file", exitOnError);
		}

	}

	public static void convertToXml(Object object, OutputStream stream, PrintStream out, boolean exitOnError)
	{
		try
		{
			XStream xStream = getXStream();
			xStream.toXML(object, stream);
		}
		catch(Exception e)
		{
			FailureUtil.failureExit(e, out, "exit program - failure writing publication xml file", exitOnError);
		}

	}

	private static XStream getXStream()
	{
		SortableFieldKeySorter sorter = new SortableFieldKeySorter();
		// sorter.registerFieldOrder(Reference.class, new String[]
		// {"serialVersionUID", "id", "marker", "authors", "title", "editor", "source", "edition", "publisher", "location", "volume", "issue", "pageFrom", "pageTo", "publicationDateString",
		// "publicationDay", "publicationMonth", "publicationYear", "publicationDate", "doi", "type", "url", "note"});
		sorter.registerFieldOrder(Reference.class, referenceFieldOrder);

		checkAllFieldsMapped();
		XStream xStream = new XStream(new SunUnsafeReflectionProvider(new FieldDictionary(sorter)), new DomDriver(StandardCharsets.UTF_8.name()));

		xStream.setMode(XStream.SINGLE_NODE_XPATH_ABSOLUTE_REFERENCES);

		xStream.alias("Publication", Publication.class);
		xStream.alias("Author", Author.class);
		xStream.alias("Affiliation", Affiliation.class);
		xStream.alias("Section", Section.class);
		xStream.alias("ReferenceCitation", ReferenceCitation.class);
		xStream.alias("Reference", Reference.class);
		xStream.alias("ReferenceAuthor", ReferenceAuthor.class);

		xStream.registerConverter(new IdConverter());

		// allowed classes, otherwise com.thoughtworks.xstream.security.ForbiddenClassException
		XStream.setupDefaultSecurity(xStream);
		xStream.addPermission(new TypePermission()
		{
			@Override
			public boolean allows(Class type)
			{
				return type.getPackage().getName().startsWith("mapping.result");
			}
		});

		return xStream;
	}

	private static void checkAllFieldsMapped()
	{
		List<String> fields = FieldUtils.getAllFieldsList(Reference.class).stream().map(f -> f.getName()).collect(Collectors.toList());

		fields.removeAll(Arrays.asList(referenceFieldOrder));

		if(CollectionUtil.isNotEmpty(fields))
		{
			FailureUtil.exit("fields " + StringUtil.notNullJoinedList(fields, ",") + " of Class Reference not in XStreamUtil.referenceFieldOrder");
		}

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
