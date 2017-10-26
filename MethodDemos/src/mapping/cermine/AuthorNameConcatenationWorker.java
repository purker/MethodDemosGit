package mapping.cermine;

import mapping.Worker;
import mapping.result.Author;
import mapping.result.Publication;

public class AuthorNameConcatenationWorker extends Worker
{

	@Override
	protected void doWork(Publication publication)
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
