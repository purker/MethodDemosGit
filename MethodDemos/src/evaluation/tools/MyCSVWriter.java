package evaluation.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import com.opencsv.CSVWriter;

import utils.FileCollectionUtil;

public class MyCSVWriter extends AbstractWriter
{
	private static final WriterType WRITERTYPE = WriterType.CSV;
	private CSVWriter csvWriter;

	public MyCSVWriter(String csvFile) throws IOException
	{
		FileWriter fileWriter = new FileWriter(FileCollectionUtil.replaceFileExtension(csvFile, getWriterType()));
		csvWriter = new CSVWriter(fileWriter, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
	}

	@Override
	public WriterType getWriterType()
	{
		return WRITERTYPE;
	}

	@Override
	public void writeNext(Collection<String> line)
	{
		writeNext(line.stream().toArray(String[]::new));
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
