package mapping.grobid;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import demos.Demos;
import mapping.Mapper;
import mapping.Worker;
import mapping.cermine.ReferenceAuthorNameConcatenationWorker;

public class GROBIDMapper extends Mapper
{
	private static final String METHOD_NAME = "grobid";
	private static final File DIRECTORY_NAME = Demos.grobIdOutputDir;
	public static final String BINDINGFILE = "bindingfiles/binding_grobid.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
		GROBIDMapper grobidMapper = new GROBIDMapper();
		File inputFile = new File("D:/output/methods/grobid/grobid-TUW-240858.xml");
		File outputFile = new File(inputFile.getParentFile(), inputFile.getName().replace(".xml", "-xstream.xml"));

		grobidMapper.unmarshall(inputFile, outputFile);

		// grobidMapper.marshall();
	}

	@Override
	protected String getMethodName()
	{
		return METHOD_NAME;
	}

	@Override
	protected List<? extends Worker> getWorkers()
	{
		return Arrays.asList(new AffiliationWorker(), new AuthorNameConcatenationWorker(), new ReferenceIdReplaceWorker(), new ReferenceAuthorNameConcatenationWorker(), new ReferenceDateWorker());
	}

	@Override
	protected File getDirectory()
	{
		return DIRECTORY_NAME;
	}

}