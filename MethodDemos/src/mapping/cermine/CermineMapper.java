package mapping.cermine;

import java.io.File;

import mapping.Mapper;

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
		File inputFile = new File("D:/output/all/cermine-TUW-137078.xml");
		File outputFile = new File("D:/output/Cermine/cermine-TUW-137078-result.xml");

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

}
