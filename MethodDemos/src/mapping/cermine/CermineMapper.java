package mapping.cermine;

import java.io.File;

import mapping.Mapper;

public class CermineMapper extends Mapper
{
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
		File outputFile = new File("D:/output/all/cermine-TUW-137078-xstreamobject.xml");

		cermineMapper.unmarshall(inputFile, outputFile);

		cermineMapper.marshall();
	}

}
