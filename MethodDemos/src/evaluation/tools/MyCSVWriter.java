package evaluation.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class MyCSVWriter extends AbstractWriter
{
	com.opencsv.CSVWriter csvWriter;

	public MyCSVWriter(File csvFile) throws IOException
	{
		csvWriter = new com.opencsv.CSVWriter(new FileWriter(csvFile), ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
	}

	@Override
	public void writeNext(String[] line)
	{
		csvWriter.writeNext(line);
	}

	@Override
	public void writeNext(String[] line, boolean applyQuotesToAll)
	{
		csvWriter.writeNext(line, applyQuotesToAll);

	}

	@Override
	public void close() throws IOException
	{
		csvWriter.flush();
		csvWriter.close();

	}

}
