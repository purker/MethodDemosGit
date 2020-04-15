package misc;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.pdf.PdfReader;

public class PdfPageCounter
{
	public static void main(String[] args) throws IOException
	{
		File file = new File("output/groundtruth/");
		printPagesOfPdfFilesInDirectory(file);
	}

	private static void printPagesOfPdfFilesInDirectory(File dir) throws IOException
	{
		Collection<File> files = FileUtils.listFiles(dir, new String[]{"pdf"}, true);

		for(File file : files)
		{
			PdfReader reader = new PdfReader(file.getPath());
			int pages = reader.getNumberOfPages();

			System.out.println(file.getName() + "\t" + pages);
		}
	}
}
