package mapping.grobid;

import java.io.File;

import mapping.Mapper;

public class GROBIDMapper extends Mapper
{
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
		File outputFile = new File("D:/output/all/grobid-TUW-137078-xstreamobject.xml");

		grobidMapper.unmarshall(inputFile, outputFile);

		grobidMapper.marshall();
	}

}