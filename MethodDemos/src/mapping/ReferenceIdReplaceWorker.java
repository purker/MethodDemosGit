package mapping;

import java.util.List;

import config.Config;
import mapping.result.Id;
import mapping.result.Publication;
import mapping.result.Reference;
import utils.FailureUtil;

//TODO delete class
public class ReferenceIdReplaceWorker extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		try
		{
			List<Reference> references = publication.getReferences();
			for(Reference r : references)
			{
				r.setId(new Id(new Integer(r.getGrobidReferenceIdString().replaceFirst(Config.referencePrefix, ""))));
			}
		}
		catch(Exception e)
		{
			FailureUtil.failureExit(e, System.err, this.getClass().getName(), true);
		}
	}
}
