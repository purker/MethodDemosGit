package evaluation.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import com.opencsv.CSVWriter;

public class MyCSVWriter extends AbstractWriter
{
	private static final WriterType WRITERTYPE = WriterType.CSV;
	private CSVWriter csvWriter;

	public MyCSVWriter(String csvFile) throws IOException
	{
		csvFile = getWriterType().replace(csvFile);
		FileWriter fileWriter = new FileWriter(csvFile);
		csvWriter = new CSVWriter(fileWriter, ';', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.RFC4180_LINE_END);
	}

	@Override
	public WriterType getWriterType()
	{
		return WRITERTYPE;
	}

	@Override
	public void writeNext(Collection<String> line)
	{
		Collection<String> newline = new ArrayList<>();
		for(String string : line)
		{
			if(string != null)
				newline.add(string.replace("\n", "\r\n"));
			else
				newline.add(null);
		}
		writeNext(newline.stream().toArray(String[]::new));
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
