package mapping.grobid;

import mapping.Worker;
import mapping.result.AbstractMetaPublication;
import mapping.result.Publication;
import mapping.result.Reference;

/**
 * Shared for Publication and Publications<br>
 * Sets date variables
 */
public class DateWorkerGrobid extends Worker
{

	@Override
	public void doWork(Publication publication)
	{
		setDateValuesFromDateString(publication);
		for(Reference reference : publication.getReferences())
		{
			setDateValuesFromDateString(reference);
		}

	}

	private void setDateValuesFromDateString(AbstractMetaPublication pubOrRef)
	{
		String dateString = pubOrRef.getPublicationDateString();
		if(dateString != null)
		{
			// "2011"
			if(dateString.length() == 4)
			{
				pubOrRef.setPublicationYear(new String(dateString));
			}
			// "2011-05"
			else
				if(dateString.length() == 7)
				{
					pubOrRef.setPublicationYear(new String(dateString.substring(0, 4)));
					pubOrRef.setPublicationMonth(new String(dateString.substring(5, 7)));
				}
				// "2011-05-01"
				else
					if(dateString.length() == 10)
					{
						pubOrRef.setPublicationYear(new String(dateString.substring(0, 4)));
						pubOrRef.setPublicationMonth(new String(dateString.substring(5, 7)));
						pubOrRef.setPublicationDay(new String(dateString.substring(8, 10)));
						// System.out.println(publication.getId());
						// System.out.println(reference.getTitle());
						// System.out.println(reference.getPublicationYear() + "-" + reference.getPublicationMonth() + " " + reference.getPublicationDay());
					}
		}

	}

}
