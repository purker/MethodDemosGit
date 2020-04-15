package mapping.grobid;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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
	public void doWork(Publication publication)
	{
		List<Affiliation> existedList = new ArrayList<>();
		for(Author author : publication.getAuthors())
		{
			if(author.getAffiliations() != null)
			{
				for(final ListIterator<Affiliation> iterator = author.getAffiliations().listIterator(); iterator.hasNext();)
				{
					final Affiliation affiliation = iterator.next();

					// equals of Affiliation doesn't include id
					int index = existedList.indexOf(affiliation);
					if(index != -1)
					{
						Affiliation existedBefore = existedList.get(index);

						// discard old Affiliation
						iterator.set(existedBefore);
					}
					else
					{
						publication.getAffiliations().add(affiliation);
						existedList.add(affiliation);
					}
				}
			}
		}
	}
}
