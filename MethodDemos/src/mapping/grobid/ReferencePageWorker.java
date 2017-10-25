package mapping.grobid;

import mapping.Worker;
import mapping.result.GROBIDPublication;
import mapping.result.GROBIDReference;

public class ReferencePageWorker extends Worker<GROBIDPublication>
{

	@Override
	protected void doWork(GROBIDPublication publication)
	{
		for(GROBIDReference reference : publication.getReferences())
		{
			if(reference.getPageSingle() != null)
			{
				reference.setPageFrom(reference.getPageSingle());
			}
		}

	}

}
