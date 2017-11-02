package mapping.grobid;

import java.util.ArrayList;
import java.util.List;

import mapping.Worker;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.Section;

/**
 * Changes b0, b1, ... to ref1, ref2, ...
 *
 */
public class ReferenceIdReplaceWorker extends Worker
{
	@Override
	protected void doWork(Publication publication)
	{
		for(Section section : publication.getSections())
		{
			List<String> newReferenceIds = new ArrayList<>();

			for(String referenceId : section.getReferenceIds())
			{
				if(referenceId != null)
				{
					newReferenceIds.add("ref" + (new Integer(referenceId.replaceFirst("#b", "")) + 1));
				}
			}
			section.setReferenceIds(newReferenceIds);
		}
		List<Reference> references = publication.getReferences();
		references.forEach(p -> p.setId("ref" + (new Integer(p.getId().replaceFirst("b", "")) + 1)));
	}
}
