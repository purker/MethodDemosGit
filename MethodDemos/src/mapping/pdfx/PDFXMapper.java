package mapping.pdfx;

import java.io.File;
import java.util.List;

import mapping.Mapper;
import mapping.Worker;

public class PDFXMapper extends Mapper
{
	private static final String METHOD_NAME = "pdfx";
	public static final String BINDINGFILE = "bindingfiles/binding_pdfx.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
		PDFXMapper grobidMapper = new PDFXMapper();
		File inputFile = new File("D:/output/all/pdfx-TUW-137078.xml");
		File outputFile = new File("D:/output/all/pdfx-TUW-137078-xstreamobject.xml");

		grobidMapper.unmarshall(inputFile, outputFile);

		grobidMapper.marshall();
	}

	@Override
	protected String getMethodName()
	{
		return METHOD_NAME;
	}

	@Override
	protected List<? extends Worker> getWorkers()
	{
		// TODO Auto-generated method stub
		return null;
	}
}