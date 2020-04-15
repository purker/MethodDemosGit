package mapping.grobid;

import java.util.ArrayList;
import java.util.Collection;

import mapping.Worker;
import mapping.result.Affiliation;
import mapping.result.Publication;
import utils.CollectionUtil;
import utils.PublicationUtil;

/**
 * Sets publication.affiliations to collection of all author.publications
 */
public class AffiliationCollectorWorker extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		Collection<Affiliation> affiliations = PublicationUtil.getDistinctAffiliations(publication);

		if(CollectionUtil.isNotEmpty(affiliations))
		{
			publication.setAffiliations(new ArrayList<>(affiliations));
		}
	}
}
