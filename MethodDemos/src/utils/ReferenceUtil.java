package utils;

import java.util.List;
import java.util.stream.Collectors;

import mapping.result.AbstractAuthor;
import mapping.result.Reference;
import mapping.result.ReferenceAuthor;

public class ReferenceUtil
{
	public static String getConcatinatedLastNamesOfAuthors(Reference reference, String joinedBy)
	{
		return getConcatinatedLastNamesOfAuthors(reference.getAuthors(), joinedBy);
	}

	public static String getConcatinatedLastNamesOfAuthors(List<? extends AbstractAuthor> authors, String joinedBy)
	{
		if(CollectionUtil.isEmpty(authors))
		{
			return "";
		}
		return authors.stream().map(a -> a.getLastName()).collect(Collectors.joining(joinedBy));
	}

	/**
	 * 1 author: [And02]<br>
	 * 2-4 authors: [CC77]<br>
	 * >5 authors && stopAfter3Authors: [BCC+03]<br>
	 * >5 authors && !stopAfter3Authors: [BCCDKW]<br>
	 *
	 * @param reference
	 * @param stopAfter3Authors
	 * @return
	 */
	public static String getShortNamesOfAuthors(Reference reference, boolean stopAfter3Authors)
	{
		StringBuffer sb = new StringBuffer();
		if(CollectionUtil.isEmpty(reference.getAuthors())) return sb.toString();
		int authorCount = reference.getAuthors().size();
		if(authorCount == 1)
		{
			sb.append(StringUtil.substringMaxLength(reference.getAuthors().get(0).getLastName(), 3));
		}
		else
		{
			if(PublicationUtil.lastAuthorIsEtAl(reference))
			{
				for(ReferenceAuthor author : reference.getAuthors())
				{
					if(!author.isEtAlAuthor())
					{
						String lastName = author.getLastName();
						sb.append(StringUtil.substringMaxLength(lastName, 1));
					}
				}
				sb.append("+");
			}
			else
				if(authorCount > 1 && authorCount < 5)
				{
					for(ReferenceAuthor author : reference.getAuthors())
					{
						String lastName = author.getLastName();
						sb.append(StringUtil.substringMaxLength(lastName, 1));
					}
				}
				else
				{
					if(authorCount >= 5)
					{
						int x = 0;
						for(ReferenceAuthor author : reference.getAuthors())
						{
							if(((stopAfter3Authors && x < 3) || !stopAfter3Authors) && !author.isEtAlAuthor())
							{
								String lastName = author.getLastName();
								sb.append(StringUtil.substringMaxLength(lastName, 1));
							}
							else
								break;
							x++;
						}
						if(stopAfter3Authors) sb.append("+");
					}
				}
		}
		return sb.toString();
	}

	public static String getFirst3LettersOfFirstAuthorLastName(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		if(reference.getAuthors().size() > 0)
		{
			sb.append(StringUtil.substringMaxLength(reference.getAuthors().get(0).getLastName(), 3));
		}

		return sb.toString();
	}
}
