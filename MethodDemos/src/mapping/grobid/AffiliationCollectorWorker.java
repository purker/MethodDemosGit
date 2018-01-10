package mapping.grobid;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import mapping.Worker;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import utils.CollectionUtil;

/**
 * Sets publication.affiliations to collection of all author.publications
 */
public class AffiliationCollectorWorker extends Worker
{
	@Override
	protected void doWork(Publication publication)
	{
		Set<Affiliation> affiliations = new LinkedHashSet<>();
		for(Author author : publication.getAuthors())
		{
			if(author.getAffiliations() != null)
			{
				for(Affiliation affiliation : author.getAffiliations())
				{
					affiliations.add(affiliation);
				}
			}
		}
		if(CollectionUtil.isNotEmpty(affiliations))
		{
			publication.setAffiliations(new ArrayList<>(affiliations));
		}
	}
}
