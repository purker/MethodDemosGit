package mapping.pdfx;

import java.util.List;

import mapping.Worker;
import mapping.result.Id;
import mapping.result.Publication;
import mapping.result.Reference;
import utils.FailureUtil;

public class ReferenceIdInitializerPdfx extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		try
		{
			List<Reference> references = publication.getReferences();
			for(Reference r : references)
			{
				r.setId(new Id(r.getReferenceIdString()));
			}
		}
		catch(Exception e)
		{
			FailureUtil.failureExit(e, System.err, this.getClass().getName(), true);
		}
	}
}
