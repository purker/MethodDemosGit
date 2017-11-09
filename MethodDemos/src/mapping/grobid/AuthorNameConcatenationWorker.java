package mapping.grobid;

import mapping.Worker;
import mapping.result.Author;
import mapping.result.Publication;

public class AuthorNameConcatenationWorker extends Worker
{

	@Override
	public void doWork(Publication publication)
	{
		for(Author author : publication.getAuthors())
		{
			StringBuffer sb = new StringBuffer();

			for(String firstName : author.getFirstNames())
			{
				if(firstName != null)
				{
					sb.append(firstName);
					sb.append(" ");
				}
			}
			if(author.getLastName() != null)
			{
				sb.append(author.getLastName());
			}
			// TODO einkomment author.setName(sb.toString());
		}

	}

}
