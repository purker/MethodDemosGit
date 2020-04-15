package misc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

import utils.FileCollectionUtil;

public class SetDateAndGrobidVersion
{
	// private static final String dateReplace = new Date().toString();
	// private static final String versionReplace = getPropertyFromFile("version");
	// private static final String gradleProperties = "D:\\Java\\git\\grobid-gradle\\gradle.properties";

	public static void main(String[] args) throws Exception
	{
		replaceDateAndGrobidVersion();
	}

	public static void replaceDateAndGrobidVersion() throws IOException
	{
		List<File> files = FileCollectionUtil.getGrobidExtractedFiles();

		String grobidHomeReplace = "C:\\Users\\Angela\\git\\grobid\\grobid-home/schemas/xsd/Grobid.xsd\"";
		String versionReplace = "0.4.5-dummy";
		String dateReplace = "2017-12-29T00:16+0000";

		for(File file : files)
		{
			List<String> s = FileUtils.readLines(file, StandardCharsets.UTF_8);
			Collection<String> newLines = new ArrayList<>();

			for(String string : s)
			{
				if(string.startsWith("xsi:schemaLocation="))
				{
					newLines.add("xsi:schemaLocation=\"http://www.tei-c.org/ns/1.0 " + grobidHomeReplace);
				}
				else
					if(string.trim().startsWith("<application "))
					{

						newLines.add(String.format("\t\t\t\t<application version=\"%s\" ident=\"GROBID\" when=\"%s\">", versionReplace, dateReplace));
					}
					else
					{
						newLines.add(string);
					}
			}
			FileUtils.writeLines(file, StandardCharsets.UTF_8.name(), newLines);
		}

		System.out.println("Done");
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
