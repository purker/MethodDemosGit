package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import mapping.result.AbstractAuthor;
import mapping.result.AbstractMetaPublication;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Id;
import mapping.result.Publication;
import mapping.result.Reference;

public class PublicationUtil
{

	public static String getIdFromFileWithoutPrefix(File file)
	{
		String id = getNumberFromIdAsString(file.getName());
		return id;
	}

	public static Id getPublicationIdFromFileNameAsInteger(File file)
	{
		return getPublicationIdFromFileNameAsInteger(file.getName());
	}

	public static Id getPublicationIdFromFileNameAsInteger(String fileName)
	{
		Id id = getNumberFromIdAsInteger(fileName);
		return id;
	}

	/**
	 * "TUW-1" -> "1"<br>
	 * "ref-1" -> "1"
	 * 
	 * @param id
	 * @return
	 */
	public static String getNumberFromIdAsString(String id)
	{
		String idNumber = id.replaceAll("\\D+", "");
		return idNumber;
	}

	/**
	 * "TUW-1" -> 1<br>
	 * "ref-1" -> 1
	 * 
	 * @param id
	 * @return
	 */
	public static Id getNumberFromIdAsInteger(String id)
	{
		Integer idNumber = new Integer(id.replaceAll("\\D+", ""));
		return new Id(idNumber);
	}

	public static String getPublicationIdFromFileName(String fileName)
	{
		String id = getNumberFromIdAsString(fileName);
		return "TUW-" + id;
	}

	public static String getIdFromFile(File file)
	{
		return getPublicationIdFromFileName(file.getName());
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
			else
				if(authors.size() == 2)
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
			else
				if(authors.size() == 2)
				{
					return authors.get(0).getLastName() + " and " + authors.get(1).getLastName();
				}
				else
					if(authors.size() == 3)
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

	public static Collection<Affiliation> getDistinctAffiliations(Publication publication)
	{
		Set<Affiliation> affiliations = new LinkedHashSet<>();
		for(Author author : publication.getAuthors())
		{
			if(author.getAffiliations() != null)
			{
				for(Affiliation affiliation : author.getAffiliations())
				{
					affiliations.add(affiliation);
				}
			}
		}
		return affiliations;
	}
}
