package mapping.grobid;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import demos.Demos;
import factory.PublicationFactory;
import mapping.Mapper;
import mapping.ReferenceSetPublicationWorker;
import mapping.Worker;
import method.Method;
import utils.XStreamUtil;

public class GROBIDMapper extends Mapper
{
	private static final Method METHOD = Method.GROBID;
	private static final File DIRECTORY_NAME = Demos.grobIdOutputDir;
	private static final String BINDINGFILE = "bindingfiles/binding_grobid.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
		GROBIDMapper grobidMapper = new GROBIDMapper();
		File inputFile = new File("D:/output/methods/grobid/grobid-TUW-198408.xml");
		File outputFile = new File(inputFile.getParentFile(), inputFile.getName().replace(".xml", "-xstream.xml"));

		// grobidMapper.unmarshall(inputFile, outputFile);

		// grobidMapper.marshall(PublicationFactory.createPublication(), System.out);

		XStreamUtil.convertToXmL(PublicationFactory.createPublication(), System.out, System.out, true);
	}

	@Override
	protected Method getMethod()
	{
		return METHOD;
	}

	@Override
	protected List<? extends Worker> getWorkers()
	{
		List<Worker> workers = new ArrayList<>();
		workers.add(new ReferenceSetPublicationWorker());
		workers.add(new AffiliationWorker());
		/* TODO dort auch wieder einkommentieren new AuthorNameConcatenationWorker(), */
		workers.add(new ReferenceIdReplaceWorkerGrobid());
		workers.add(new ReferenceEditionWorker());
		workers.add(new DateWorkerGrobid());

		return workers;
	}

	@Override
	protected File getDirectory()
	{
		return DIRECTORY_NAME;
	}

}