package evaluation.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import config.Config;
import utils.CollectionUtil;

public class WriterWrapper extends AbstractWriter
{
	private List<AbstractWriter> writers;

	/**
	 * @param file
	 *            File String with "&lt;fileext&gt;" as placeholder for the fileextension
	 * @throws IOException
	 */
	public WriterWrapper(String file) throws IOException
	{
		List<WriterType> writerTypes = Config.writerTypes;
		writers = new ArrayList<>();
		if(writerTypes.contains(WriterType.CSV))
		{
			writers.add(new MyCSVWriter(file));
		}

		if(writerTypes.contains(WriterType.EXCEL))
		{
			writers.add(new ExcelWriter(file));
		}

		if(CollectionUtil.isEmpty(writerTypes))
		{
			throw new IOException("WriterType not specified");
		}
	}

	@Override
	public void writeNext(String[] line)
	{
		for(AbstractWriter writer : writers)
		{
			writer.writeNext(line);
		}
	}

	@Override
	public void close() throws IOException
	{
		for(AbstractWriter writer : writers)
		{
			writer.close();
		}
	}

}
