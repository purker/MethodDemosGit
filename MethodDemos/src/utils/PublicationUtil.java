package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import mapping.result.AbstractAuthor;
import mapping.result.AbstractMetaPublication;
import mapping.result.Publication;
import mapping.result.Reference;

public class PublicationUtil
{

	public static String getIdFromFileNameWithoutPrefix(File file)
	{
		String id = getIdFromFileNameWithoutPrefix(file.getName());
		return id;
	}

	public static String getIdFromFileNameWithoutPrefix(String fileName)
	{
		String id = fileName.replaceAll("\\D+", "");
		return id;
	}

	public static String getIdFromFileName(String fileName)
	{
		String id = getIdFromFileNameWithoutPrefix(fileName);
		return "TUW-" + id;
	}

	public static String getIdFromFile(File file)
	{
		return getIdFromFileName(file.getName());
	}

	public static String getNameFromAuthor(AbstractAuthor author)
	{
		StringBuffer sb = new StringBuffer();

		if(CollectionUtil.isNotEmpty(author.getFirstNames()))
		{
			for(String firstName : author.getFirstNames())
			{
				if(firstName != null)
				{
					sb.append(firstName);
					sb.append(" ");
				}
			}
		}
		if(author.getLastName() != null)
		{
			sb.append(author.getLastName());
		}
		return sb.toString();
	}

	public static String concatinateAllAuthorNames(List<? extends AbstractAuthor> authors)
	{
		StringBuffer sb = new StringBuffer();

		if(authors != null)
		{
			for(Iterator<? extends AbstractAuthor> iterator = authors.iterator(); iterator.hasNext();)
			{
				AbstractAuthor author = iterator.next();
				sb.append(getNameFromAuthor(author));

				if(iterator.hasNext())
				{
					sb.append(", ");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param authors
	 *            if 1 author: name<br>
	 *            if 2 authors: name and name<br>
	 *            if >2 authors: name et al.
	 * @return
	 */
	public static String getConcatinatedLastNamesOfAuthors(List<? extends AbstractAuthor> authors)
	{
		if(CollectionUtil.isNotEmpty(authors))
		{
			if(authors.size() == 1)
			{
				return authors.get(0).getLastName();
			}
			else if(authors.size() == 2)
			{
				return authors.get(0).getLastName() + " and " + authors.get(1).getLastName();
			}
			else
			{
				return authors.get(0).getLastName() + " et al.";
			}
		}

		return null;
	}

	/**
	 * @param authors
	 *            if 1 author: name<br>
	 *            if 2 authors: name and name<br>
	 *            if 3 authors: name, name, and name<br>
	 *            if >3 authors: name et al.
	 * @return
	 */
	public static String getConcatinatedLastNamesOfAuthors2(List<? extends AbstractAuthor> authors)
	{
		if(CollectionUtil.isNotEmpty(authors))
		{
			if(authors.size() == 1)
			{
				return authors.get(0).getLastName();
			}
			else if(authors.size() == 2)
			{
				return authors.get(0).getLastName() + " and " + authors.get(1).getLastName();
			}
			else if(authors.size() == 3)
			{
				return authors.get(0).getLastName() + ", " + authors.get(1).getLastName() + ", and " + authors.get(2).getLastName();
			}
			else
			{
				return authors.get(0).getLastName() + " et al.";
			}
		}

		return null;
	}

	public static List<AbstractAuthor> getAllAuthors(Publication publication)
	{
		List<AbstractAuthor> authors = new ArrayList<>();

		authors.addAll(publication.getAuthors());
		authors.addAll(publication.getReferences().stream().flatMap(r -> (r.getAuthors() != null) ? (r.getAuthors().stream()) : null).collect(Collectors.toList()));

		return authors;
	}

	public static String getConcatinatedPages(AbstractMetaPublication publication)
	{
		String pageFrom = publication.getPageFrom();
		String pageTo = publication.getPageTo();

		StringBuffer sb = new StringBuffer();

		if(pageFrom != null)
		{
			sb.append(pageFrom);
		}
		if(pageFrom != null && pageTo != null)
		{
			sb.append("-");
		}
		if(pageTo != null)
		{
			sb.append(pageTo);
		}

		return sb.toString();
	}

	public static String getConcatinatedLastNamesOfAuthors(Reference reference)
	{
		return getConcatinatedLastNamesOfAuthors(reference.getAuthors());
	}
}
