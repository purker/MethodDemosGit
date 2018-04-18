package mapping.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.StringUtil;

public class Reference extends AbstractMetaPublication
{
	private static final long serialVersionUID = -3747798923009051624L;

	// id is normalized marker in form "ref1"
	private String marker; // "[1]", "[Hun97]", if defined for this method

	private List<ReferenceAuthor> authors = new ArrayList<>();
	private String type; // book, colletion, inproceedings, ...

	public String getMarker()
	{
		return marker;
	}

	public void setMarker(String marker)
	{
		this.marker = marker;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<ReferenceAuthor> getAuthors()
	{
		return authors;
	}

	public void setAuthors(List<ReferenceAuthor> authors)
	{
		this.authors = authors;
	}

	@Override
	public String toString()
	{
		List<Object> list = Arrays.asList(id, marker, title, source, publisher, editors, authors, edition, location, volume, issue, chapter, note, pageFrom, pageTo, publicationDateString, publicationYear, publicationMonth, publicationDay, publicationDate, doi, url, type);

		return StringUtil.objectListToString(list, ",");
	}
}
