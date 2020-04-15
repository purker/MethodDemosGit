package misc;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import mapping.result.Publication;
import utils.FileCollectionUtil;
import utils.PublicationUtil;

public class PdfReferenceCounter
{
	public static void main(String[] args) throws IOException
	{
		File file = new File("output/result/");
		printPagesOfPdfFilesInDirectory(file);
	}

	private static void printPagesOfPdfFilesInDirectory(File dir) throws IOException
	{
		Collection<File> files = FileCollectionUtil.getResultFiles();

		for(File file : files)
		{
			Publication p = PublicationUtil.getPublicationFromFile(file);

			System.out.println(PublicationUtil.getIdFromFile(file) + "\t" + p.getReferences().size());
		}
	}
}
