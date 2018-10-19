package misc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.FileUtils;

import utils.FailureUtil;

public class ReplaceNewlines
{
	private static final String LINE_ENDING = "\n";

	public static void main(String[] args) throws IOException
	{
		// replaceNewlines(FileCollectionUtil.getCermineResultFiles());
		// replaceNewlines(FileCollectionUtil.getGrobidResultFiles());
		// replaceNewlines(FileCollectionUtil.getParscitResultFiles());
		// replaceNewlines(FileCollectionUtil.getPdfxResultFiles());
	}

	public static void replaceNewlines(Collection<File> files) throws IOException
	{
		for(File file : files)
		{
			replaceNewlines(file);
		}
	}

	public static void replaceNewlines(File file)
	{
		try
		{
			// System.out.println(file);
			StringBuilder sb = new StringBuilder();
			List<String> lines;

			lines = FileUtils.readLines(file, StandardCharsets.UTF_8);

			for(ListIterator<String> iterator = new ArrayList<>(lines).listIterator(); iterator.hasNext();)
			{
				String string = iterator.next();
				if(iterator.hasNext())
					sb.append(string + LINE_ENDING);
				else
					sb.append(string);
			}

			FileUtils.writeStringToFile(file, sb.toString(), StandardCharsets.UTF_8);// StandardCharsets.UTF_8.name(), false);// , lines, "\r\n");
			// FileUtils.writeLines(file, lines, "\r\n");
		}
		catch(IOException e)
		{
			FailureUtil.failureExit(e, System.out, "", true);
		}
	}

}
