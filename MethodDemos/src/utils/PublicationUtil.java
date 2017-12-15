package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.CharMatcher;

import mapping.result.AbstractAuthor;
import mapping.result.AbstractMetaPublication;
import mapping.result.Publication;

public class PublicationUtil
{

	public static String getIdFromFileName(String fileName)
	{
		String id = CharMatcher.DIGIT.retainFrom(fileName);
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
}
