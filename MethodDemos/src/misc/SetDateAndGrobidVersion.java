package misc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.xml.sax.SAXException;

import utils.FileCollectionUtil;

public class SetDateAndGrobidVersion
{
	// private static final String dateReplace = new Date().toString();
	// private static final String versionReplace = getPropertyFromFile("version");

	private static final String gradleProperties = "D:\\Java\\git\\grobid-gradle\\gradle.properties";

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException
	{
		List<File> files = FileCollectionUtil.getGrobidExtractedFiles();

		String dateReplace = "2018-07-30T19:54+0200";
		// String dateReplace = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ").format(new Date());

		System.out.println(dateReplace);

		for(File file : files)
		{
			List<String> s = FileUtils.readLines(file, StandardCharsets.UTF_8);
			Collection<String> newLines = new ArrayList<>();

			for(String string : s)
			{
				if(string.startsWith("xsi:schemaLocation="))
				{
					newLines.add("xsi:schemaLocation=\"http://www.tei-c.org/ns/1.0 D:\\Java\\git\\grobid-gradle\\grobid-home/schemas/xsd/Grobid.xsd\"");
				}
				else
					if(string.trim().startsWith("<application "))
					{

						newLines.add(String.format("\t\t\t\t<application version=\"%s\" ident=\"GROBID\" when=\"%s\">", "0.5.1", dateReplace));
					}
					else
					{
						newLines.add(string);
					}
			}
			FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), newLines);
		}
	}

	// private static String getPropertyFromFile(String propertyName)
	// {
	// Configuration configuration;
	// try
	// {
	// return null;
	// // configuration = new PropertiesConfiguration(gradleProperties);
	// // return configuration.getString(propertyName);
	// }
	// catch(ConfigurationException e)
	// {
	// FailureUtil.exit("more than one node found");
	// return null;
	// }
	// }
}
