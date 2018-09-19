package mapping.grobid;

import java.util.ArrayList;
import java.util.List;

import config.Config;
import mapping.Worker;
import mapping.result.Id;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.Section;
import utils.FailureUtil;

/**
 * Changes b0, b1, ... to ref1, ref2, ...
 *
 */
public class ReferenceIdReplaceWorkerGrobid extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		try
		{
			for(Section section : publication.getSections())
			{
				List<String> newReferenceIds = new ArrayList<>();

				for(String referenceId : section.getReferenceIds())
				{
					if(referenceId != null)
					{
						try
						{
							newReferenceIds.add(Config.referencePrefix + (new Integer(referenceId.replaceFirst("#b", "")) + 1));
						}
						catch(NumberFormatException e)
						{
							System.err.println(e.getMessage());
						}
					}
				}
				section.setReferenceIds(newReferenceIds);
			}
			List<Reference> references = publication.getReferences();
			for(Reference r : references)
			{
				r.setId(new Id(new Integer(r.getGrobidReferenceIdString().replaceFirst("b", "")) + 1));
			}
		}
		catch(Exception e)
		{
			FailureUtil.failureExit(e, System.err, this.getClass().getName(), true);
		}
	}
}
