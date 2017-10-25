package mapping.cermine;

import mapping.Worker;
import mapping.result.AbstractPublication;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.CitationContext;
import mapping.result.AbstractReference;
import mapping.result.Section;

public class AuthorNameConcatenationWorker extends Worker<AbstractPublication<Author, Affiliation, Section, CitationContext, AbstractReference>>
{

	@Override
	protected void doWork(AbstractPublication<Author, Affiliation, Section, CitationContext, AbstractReference> publication)
	{
		for(Author author : publication.getAuthors())
		{
			StringBuffer sb = new StringBuffer();

			if(author.getFirstName() != null)
			{
				sb.append(author.getFirstName());
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
