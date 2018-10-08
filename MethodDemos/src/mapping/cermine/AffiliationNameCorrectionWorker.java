package mapping.cermine;

import mapping.Worker;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import utils.StringUtil;

/**
 * Moxy doesn't extract the name of the Affiliation correct and adds the id of the affiliation in front of the name<br>
 * Workaround: remove them
 *
 * TODO only needed vor Cermine?
 */
public class AffiliationNameCorrectionWorker extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		// Map<String, Reference> referenceMap = publication.getReferences().stream().collect(Collectors.toMap(Reference::getId, Function.identity()));

		for(Author author : publication.getAuthors())
		{
			for(Affiliation affiliation : author.getAffiliations())
			{
				if(StringUtil.isNotEmpty(affiliation.getInstitution()))
				{
					affiliation.setInstitution(affiliation.getInstitution().replaceFirst("\\d+", ""));

				}
			}
		}

	}

}
