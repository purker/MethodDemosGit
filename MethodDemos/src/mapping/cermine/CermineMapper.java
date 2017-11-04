package mapping.cermine;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import demos.Demos;
import mapping.Mapper;
import mapping.SectionReferenceWorker;
import mapping.Worker;

public class CermineMapper extends Mapper
{
	private static final String METHOD_NAME = "cermine";
	private static final File DIRECTORY_NAME = Demos.cermineOutputDir;
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

		// Publication publication = PublicationFactory.createPublication();
		// cermineMapper.marshall(publication);
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
		return Arrays.asList(new SectionReferenceWorker(), new ReferenceAuthorNameConcatenationWorker());
	}

	@Override
	protected File getDirectory()
	{
		return DIRECTORY_NAME;
	}
}
