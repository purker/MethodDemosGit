package mapping.grobid;

import mapping.Worker;
import mapping.result.Publication;
import mapping.result.Reference;

public class ReferencePageWorker extends Worker
{

	@Override
	protected void doWork(Publication publication)
	{
		for(Reference reference : publication.getReferences())
		{
			if(reference.getPageSingle() != null)
			{
				reference.setPageFrom(reference.getPageSingle());
			}
		}

	}

}
