package mapping.pdfx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import config.Config;
import demos.Demos;
import mapping.Mapper;
import mapping.ReferenceSetPublicationWorker;
import mapping.Worker;
import mapping.parscit.SectionTypeNormalizerWorker;
import method.Method;

public class PDFXMapper extends Mapper
{
	private static final Method METHOD = Method.PDFX;
	private static final File DIRECTORY_NAME = Demos.pdfxOutputDir;
	public static final String BINDINGFILE = "bindingfiles/binding_pdfx.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
//		List<File> files = new ArrayList<>();
//		files.add(new File("D:\\output\\methods\\pdfx\\pdfx-TUW-247743.xml"));
//
//		new PDFXMapper().unmarshallFiles(files);
//
//		// grobidMapper.marshall(PublicationFactory.createPublication());

		List<String> idList = Config.groundTruthIds;
		new PDFXMapper().unmarshallFilesWithId(idList);
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
		workers.add(new ReferenceIdInitializerPdfx());
		workers.add(new SectionTypeNormalizerWorker());

		return workers;
	}

	@Override
	protected File getDirectory()
	{
		return DIRECTORY_NAME;
	}
}