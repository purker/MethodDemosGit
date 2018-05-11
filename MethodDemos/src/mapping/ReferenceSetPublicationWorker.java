package mapping;

import java.util.List;

import mapping.result.Publication;
import mapping.result.Reference;

public class ReferenceSetPublicationWorker extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		List<Reference> references = publication.getReferences();
		for(Reference r : references)
		{
			r.setPublication(publication);
		}
	}
}
