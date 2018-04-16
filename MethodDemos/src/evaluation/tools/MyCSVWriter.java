package evaluation.tools;

import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import utils.FileCollectionUtil;

public class MyCSVWriter extends AbstractWriter
{
	private static final WriterType WRITERTYPE = WriterType.CSV;
	CSVWriter csvWriter;

	public MyCSVWriter(String csvFile) throws IOException
	{
		FileWriter fileWriter = new FileWriter(FileCollectionUtil.replaceFileExtension(csvFile, getWriterType()));
		csvWriter = new CSVWriter(fileWriter, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
	}

	@Override
	public WriterType getWriterType()
	{
		return WRITERTYPE;
	}

	@Override
	public void writeNext(String[] line)
	{
		csvWriter.writeNext(line);
	}

	@Override
	public void close() throws IOException
	{
		csvWriter.flush();
		csvWriter.close();

	}

}
