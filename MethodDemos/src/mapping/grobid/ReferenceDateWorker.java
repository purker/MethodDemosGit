package mapping.grobid;

import mapping.Worker;
import mapping.result.Publication;
import mapping.result.Reference;

/**
 * Sets publication date variables
 */
public class ReferenceDateWorker extends Worker
{

	@Override
	protected void doWork(Publication publication)
	{
		for(Reference reference : publication.getReferences())
		{
			// System.out.println(reference.getPublicationDateString());
			String dateString = reference.getPublicationDateString();
			if(dateString != null)
			{
				// "2011"
				if(dateString.length() == 4)
				{
					reference.setPublicationYear(new Long(dateString));
				}
				// "2011-05"
				else if(dateString.length() == 7)
				{
					reference.setPublicationYear(new Long(dateString.substring(0, 4)));
					reference.setPublicationMonth(new Long(dateString.substring(5, 7)));
				}
				// "2011-05-01"
				else if(dateString.length() == 10)
				{
					reference.setPublicationYear(new Long(dateString.substring(0, 4)));
					reference.setPublicationMonth(new Long(dateString.substring(5, 7)));
					reference.setPublicationDay(new Long(dateString.substring(8, 10)));
					// System.out.println(publication.getId());
					// System.out.println(reference.getTitle());
					// System.out.println(reference.getPublicationYear() + "-" + reference.getPublicationMonth() + " " + reference.getPublicationDay());
				}
			}
		}

	}

}
