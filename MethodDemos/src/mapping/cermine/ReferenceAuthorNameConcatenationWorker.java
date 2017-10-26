package mapping.cermine;

import org.apache.commons.lang3.StringUtils;

import mapping.Worker;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.ReferenceAuthor;

public class ReferenceAuthorNameConcatenationWorker extends Worker
{

	@Override
	protected void doWork(Publication publication)
	{
		for(Reference reference : publication.getReferences())
		{
			for(ReferenceAuthor author : reference.getAuthors())
			{
				StringBuffer sb = new StringBuffer();
				if(author.getFirstNames() != null)
				{
					sb.append(StringUtils.join(author.getFirstNames(), " "));
					sb.append(" ");
				}
				if(author.getLastName() != null)
				{
					sb.append(author.getLastName());
				}
				author.setName(sb.toString());
			}
		}

	}

}
