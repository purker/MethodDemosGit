package evaluation.tools;

import java.io.IOException;

public abstract class AbstractWriter
{
	public WriterType getWriterType()
	{
		return null;
	}

	public abstract void writeNext(String[] line);

	public abstract void close() throws IOException;

}
