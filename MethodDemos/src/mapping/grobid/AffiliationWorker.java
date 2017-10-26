package mapping.grobid;

import java.util.HashMap;
import java.util.Map;

import mapping.Worker;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;

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
public class AffiliationWorker extends Worker
{
	@Override
	protected void doWork(Publication publication)
	{
		Map<Affiliation, Affiliation> idMap = new HashMap<>(); // because there is no method for a set, to get element by equals
		for(Author author : publication.getAuthors())
		{
			// equals of Affiliation doesn't include id
			Affiliation existedBefore = idMap.get(author.getAffiliation());
			if(existedBefore != null)
			{
				// discard old Affiliation
				author.setAffiliation(existedBefore);
			}
			else
			{
				publication.getAffiliations().add(author.getAffiliation());
				idMap.put(author.getAffiliation(), author.getAffiliation());
			}
		}
	}
}
