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

		String firstNames = author.getFirstNamesAsString();
		if(StringUtil.isNotEmpty(firstNames))
		{
			sb.append(firstNames);
			sb.append(" ");
		}
		if(StringUtil.isNotEmpty(author.getLastName()))
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
	 * @param joinedBy
	 * @return
	 */
	public static String getConcatinatedLastNamesOfAuthors(List<? extends AbstractAuthor> authors, String joinedBy)
	{
		if(CollectionUtil.isNotEmpty(authors))
		{
			if(authors.size() < 3)
			{
				return ReferenceUtil.getConcatinatedLastNamesOfAuthors(authors, joinedBy);
			}
			else
			{
				return getLastNameOfFirstAuthorEtAl(authors);
			}
		}

		return "";
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

	public static String getConcatinatedLastNamesOfAuthors(Reference reference, String joinedBy)
	{
		return getConcatinatedLastNamesOfAuthors(reference.getAuthors(), joinedBy);
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

	/**
	 * @param keyString
	 *            "TUW-123456" or "TUW-123456-ref1"
	 * @return 123456
	 */
	public static Integer getPublicationIdFromKeyString(String keyString)
	{
		return new Integer(getPublicationIdFromKeyStringToString(keyString));
	}

	/**
	 * @param keyString
	 *            "TUW-123456" or "TUW-123456-ref1"
	 * @return "123456"
	 */
	public static String getPublicationIdFromKeyStringToString(String keyString)
	{
		return keyString.substring(4, 10);
	}

	public static Publication getPublicationFromFile(File file)
	{
		return XStreamUtil.convertFromXML(file, Publication.class);
	}

	/**
	 * @param pubId
	 *            without prefix "TUW-"
	 * @return
	 */
	public static Publication getPublicationFromId(String pubId)
	{
		return getPublicationFromFile(FileCollectionUtil.getGroundTruthResultFileById(pubId));
	}

	/**
	 * There has to be at least one author
	 * 
	 * @param reference
	 * @return
	 */
	public static String getLastNameOfFirstAuthorEtAl(Reference reference)
	{
		return getLastNameOfFirstAuthorEtAl(reference.getAuthors());
	}

	/**
	 * There has to be at least one author
	 * 
	 * @param authors
	 * @return
	 */
	public static String getLastNameOfFirstAuthorEtAl(List<? extends AbstractAuthor> authors)
	{
		return getLastNameOfFirstAuthor(authors) + " et al.";
	}

	/**
	 * There has to be at least one author
	 * 
	 * @param list
	 * @return
	 */
	public static String getLastNameOfFirstAuthor(List<? extends AbstractAuthor> list)
	{
		String lastNameofFirstAuthor = list.get(0).getLastName();
		if(StringUtil.isNotEmpty(lastNameofFirstAuthor))
			return lastNameofFirstAuthor.replaceAll("-", "");
		else
			return "";
	}

	/**
	 * requires authors.size>1
	 * 
	 * @param reference
	 * @return
	 */
	public static boolean lastAuthorIsEtAl(Reference reference)
	{
		return reference.getAuthors().get(reference.getAuthors().size() - 1).isEtAlAuthor();
	}
}