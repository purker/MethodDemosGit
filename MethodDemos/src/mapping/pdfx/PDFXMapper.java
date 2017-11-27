package mapping.pdfx;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import demos.Demos;
import mapping.Mapper;
import mapping.Worker;
import mapping.parscit.SectionTypeNormalizerWorker;

public class PDFXMapper extends Mapper
{
	private static final String METHOD_NAME = "pdfx";
	private static final File DIRECTORY_NAME = Demos.pdfxOutputDir;
	public static final String BINDINGFILE = "bindingfiles/binding_pdfx.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
		List<File> files = new ArrayList<>();
		files.add(new File("D:\\output\\methods\\pdfx\\pdfx-TUW-247743.xml"));

		new PDFXMapper().unmarshallFiles(files);

		// grobidMapper.marshall(PublicationFactory.createPublication());
	}

	@Override
	protected String getMethodName()
	{
		return METHOD_NAME;
	}

	@Override
	protected List<? extends Worker> getWorkers()
	{
		return Arrays.asList(new SectionTypeNormalizerWorker());
	}

	@Override
	protected File getDirectory()
	{
		return DIRECTORY_NAME;
	}
}