package evaluation.tools;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractWriter
{
	public WriterType getWriterType()
	{
		return null;
	}

	public abstract void writeNext(Collection<String> lines);

	public abstract void close() throws IOException;

	public void writeNext(String[] lines)
	{
		writeNext(Arrays.asList(lines));
	}

}
