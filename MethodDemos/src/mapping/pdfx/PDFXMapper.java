package mapping.pdfx;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import demos.Demos;
import factory.PublicationFactory;
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
		PDFXMapper grobidMapper = new PDFXMapper();
		File inputFile = new File("D:/output/PDFX/pdfx-TUW-194561.xml");
		File outputFile = new File("D:/output/PDFX/pdfx-TUW-194561-xstream.xml");

		grobidMapper.unmarshall(inputFile, outputFile);

		grobidMapper.marshall(PublicationFactory.createPublication());
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