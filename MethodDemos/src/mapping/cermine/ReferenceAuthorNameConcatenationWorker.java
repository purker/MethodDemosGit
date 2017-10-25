package mapping.cermine;

import org.apache.commons.lang3.StringUtils;

import mapping.Worker;
import mapping.result.AbstractPublication;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.CitationContext;
import mapping.result.AbstractReference;
import mapping.result.ReferenceAuthor;
import mapping.result.Section;

public class ReferenceAuthorNameConcatenationWorker extends Worker<AbstractPublication<Author, Affiliation, Section, CitationContext, AbstractReference>>
{

	@Override
	protected void doWork(AbstractPublication<Author, Affiliation, Section, CitationContext, AbstractReference> publication)
	{
		for(AbstractReference reference : publication.getReferences())
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
