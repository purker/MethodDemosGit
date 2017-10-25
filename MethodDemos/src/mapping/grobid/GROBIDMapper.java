package mapping.grobid;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import mapping.Mapper;
import mapping.SectionReferenceWorker;
import mapping.Worker;
import mapping.cermine.AuthorNameConcatenationWorker;
import mapping.cermine.ReferenceAuthorNameConcatenationWorker;
import mapping.result.GROBIDPublication;

public class GROBIDMapper extends Mapper<GROBIDPublication>
{
	public GROBIDMapper()
	{
		super(new Class[]
		{GROBIDPublication.class});
	}

	private static final String METHOD_NAME = "grobid";
	public static final String BINDINGFILE = "bindingfiles/binding_grobid.xml";

	@Override
	protected String getBindingFile()
	{
		return BINDINGFILE;
	}

	public static void main(String[] args) throws Exception
	{
		GROBIDMapper grobidMapper = new GROBIDMapper();
		File inputFile = new File("D:/output/Grobid/grobid-TUW-137078.xml");
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
	protected List<Worker<?>> getWorkers()
	{
		return Arrays.asList(new AffiliationWorker(), new AuthorNameConcatenationWorker(), new SectionReferenceWorker(true), new ReferenceAuthorNameConcatenationWorker(), new ReferencePageWorker());
	}

}