package mapping.cermine;

import java.io.File;
import java.util.ArrayList;
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
		List<File> files = new ArrayList<>();
		files.add(new File("D:\\output\\methods\\cermine\\cermine-TUW-176845.xml"));

		new CermineMapper().unmarshallFiles(files);

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
		return Arrays.asList(new SectionReferenceWorker());
	}

	@Override
	protected File getDirectory()
	{
		return DIRECTORY_NAME;
	}
}
