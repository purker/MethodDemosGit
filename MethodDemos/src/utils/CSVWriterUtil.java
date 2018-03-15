package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public class CSVWriterUtil
{

	public static CSVWriter createCSVWriter(File csvFile) throws IOException
	{
		return new CSVWriter(new FileWriter(csvFile), ';', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
	}
}
