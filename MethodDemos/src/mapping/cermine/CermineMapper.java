package mapping.cermine;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import mapping.Mapper;
import mapping.Worker;

public class CermineMapper extends Mapper
{
	private static final String METHOD_NAME = "cermine";
	public static final String BINDINGFILE = "bindingfiles/binding_cermine.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
		CermineMapper cermineMapper = new CermineMapper();
		File inputFile = new File("D:\\output\\Cermine\\cermine-TUW-139794.xml");
		File outputFile = new File("D:/output/Cermine/cermine-TUW-139794-result.xml");

		cermineMapper.unmarshall(inputFile, outputFile);

		cermineMapper.marshall();
	}

	@Override
	protected String getMethodName()
	{
		return METHOD_NAME;
	}

	@Override
	public Boolean getIgnoreDTD()
	{
		return true;
	}

	@Override
	protected List<? extends Worker> getWorkers()
	{
		return Arrays.asList(new AuthorNameConcatenationWorker(), new ReferenceAuthorNameConcatenationWorker());
	}
}
