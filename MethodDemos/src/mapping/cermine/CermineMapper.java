package mapping.cermine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import demos.Demos;
import mapping.Mapper;
import mapping.SectionLayerWorker;
import mapping.SectionReferenceWorker;
import mapping.Worker;
import mapping.grobid.AffiliationCollectorWorker;
import method.Method;

public class CermineMapper extends Mapper
{
	private static final Method METHOD = Method.CERMINE;
	private static final File DIRECTORY_NAME = Demos.cermineOutputDir;
	public static final String BINDINGFILE = "bindingfiles/binding_cermine.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
		// List<File> files = new ArrayList<>();
		// files.add(new File("D:\\output\\methods\\cermine\\cermine-TUW-176845.xml"));
		//
		new CermineMapper().unmarshallFiles();

		// Publication publication = PublicationFactory.createPublication();
		// new CermineMapper().marshall(publication, System.out);
	}

	@Override
	protected Method getMethod()
	{
		return METHOD;
	}

	@Override
	public Boolean getIgnoreDTD()
	{
		return true;
	}

	@Override
	protected List<? extends Worker> getWorkers()
	{
		List<Worker> workers = new ArrayList<>();
		workers.add(new AffiliationCollectorWorker());
		workers.add(new AuthorNameCorrectionWorker());
		workers.add(new AffiliationNameCorrectionWorker());
		workers.add(new SectionLevelWorker());
		workers.add(new SectionLayerWorker());
		workers.add(new SectionReferenceWorker());

		return workers;
	}

	@Override
	protected File getDirectory()
	{
		return DIRECTORY_NAME;
	}

}
