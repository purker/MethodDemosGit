package mapping.grobid;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Set;

import mapping.Worker;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import utils.CollectionUtil;

/**
 * Map authors with different affiliations to same affiliation if they are equal
 * 
 * <pre>
 * author1.affiliation = aff0
 * author2.affiliation = aff1
 * 
 * if aff0.equals(aff1) -> delete aff1 and update reference, to get distinct affiliation list
 * 
 * author1.affiliation = aff0
 * author2.affiliation = aff0
 * </pre>
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
				for(final ListIterator<Affiliation> iterator = author.getAffiliations().listIterator(); iterator.hasNext();)
				{
					final Affiliation affiliation = iterator.next();
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
