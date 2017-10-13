package mapping.grobid;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import mapping.AffiliationWorker;
import mapping.Mapper;
import mapping.Worker;

public class GROBIDMapper extends Mapper
{
	private static final String METHOD_NAME = "grobid";
	public static final String BINDINGFILE = "bindingfiles/binding_grobid.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
		GROBIDMapper grobidMapper = new GROBIDMapper();
		File inputFile = new File("D:/output/all/grobid-TUW-137078.xml");
		File outputFile = new File("D:/output/Grobid/grobid-TUW-137078-xstreamobject.xml");

		grobidMapper.unmarshall(inputFile, outputFile);

		grobidMapper.marshall();
	}

	@Override
	protected String getMethodName()
	{
		return METHOD_NAME;
	}

	@Override
	protected List<? extends Worker> getWorkers()
	{
		return Arrays.asList(new AffiliationWorker());
	}

}