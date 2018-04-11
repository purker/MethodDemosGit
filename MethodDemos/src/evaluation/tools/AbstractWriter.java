package evaluation.tools;

import java.io.File;
import java.io.IOException;

import config.Config;

public abstract class AbstractWriter
{
	public abstract void writeNext(String[] array);

	public abstract void writeNext(String[] line, boolean applyQuotesToAll);

	public static AbstractWriter createWriter(File file) throws IOException
	{
		WriterType writerType = Config.writerType;
		if(writerType.equals(WriterType.CSV))
		{
			return new MyCSVWriter(file);
		}
		if(writerType.equals(WriterType.EXCEL))
		{
			return new ExcelWriter(file);
		}
		throw new IOException("WriterType not specified");
	}

	public abstract void close() throws IOException;

}
