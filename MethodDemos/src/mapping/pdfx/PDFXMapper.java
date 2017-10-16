package mapping.pdfx;

import java.io.File;

import mapping.Mapper;

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
		File inputFile = new File("D:/output/PDFX/pdfx-TUW-137078.xml");
		File outputFile = new File("D:/output/PDFX/pdfx-TUW-137078-xstream.xml");

		grobidMapper.unmarshall(inputFile, outputFile);

		grobidMapper.marshall();
	}

	@Override
	protected String getMethodName()
	{
		return METHOD_NAME;
	}

}