package mapping.cermine;

import mapping.Worker;
import mapping.result.Author;
import mapping.result.Publication;
import utils.StringUtil;

/**
 * Moxy doesn't extract the name of the author correct and adds the id of the corresponding affiliation in front of the name<br>
 * Workaround: remove them
 *
 * TODO only needed vor Cermine?
 */
public class AuthorNameCorrectionWorker extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		// Map<String, Reference> referenceMap = publication.getReferences().stream().collect(Collectors.toMap(Reference::getId, Function.identity()));

		for(Author author : publication.getAuthors())
		{
			if(StringUtil.isNotEmpty(author.getFullName()))
			{
				// TODO delete
				// Pattern p = Pattern.compile("^\\d+"); // the pattern to search for
				// Matcher m = p.matcher(author.getFullName());
				//
				// if(m.lookingAt())
				// {
				// System.out.println(author.getFullName());
				author.setName(author.getFullName().replaceFirst("\\d+", ""));
				System.out.println(author.getFullName());
				// }
			}
		}

	}

}
