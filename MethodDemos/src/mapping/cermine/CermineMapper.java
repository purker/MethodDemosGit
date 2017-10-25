package mapping.cermine;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import mapping.Mapper;
import mapping.SectionReferenceWorker;
import mapping.Worker;

public class CermineMapper extends Mapper
{
	public CermineMapper()
	{
		super(new Class[]
		{CerminePublication.class});
	}

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
		File inputFile = new File("D:/output/Cermine/cermine-TUW-139299.xml");
		File outputFile = new File(inputFile.getParentFile(), inputFile.getName().replace(".xml", "-xstream.xml"));

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
	protected List<Worker<?>> getWorkers()
	{
		return Arrays.asList(new AuthorNameConcatenationWorker(), new SectionReferenceWorker(), new ReferenceAuthorNameConcatenationWorker());
	}
}
