package mapping.grobid;

import mapping.Worker;
import mapping.result.Author;
import mapping.result.Publication;
import utils.PublicationUtil;

public class AuthorNameConcatenationWorker extends Worker
{

	@Override
	public void doWork(Publication publication)
	{
		for(Author author : publication.getAuthors())
		{
			String name = PublicationUtil.getNameFromAuthor(author);
			// TODO einkomment author.setName();
		}

	}

}
