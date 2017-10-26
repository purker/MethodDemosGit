package mapping.result;

import java.util.ArrayList;
import java.util.List;

public class Reference extends BaseEntity
{
	private String id;
	private String title;
	private String source;
	private String type; // book, colletion, inproceedings, ...

	private List<ReferenceAuthor> authors = new ArrayList<>();

	private String doi;

	private String volume;
	private String issue;
	private Long pageFrom;
	private Long pageTo;
	private Long pageSingle;

	private String publicationString; // as in the extraction xml
	private Long publicationDay;
	private Long publicationMonth;
	private Long publicationYear; // if null, information found in date
	private Long publicationDate;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
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

	public String getDoi()
	{
		return doi;
	}

	public void setDoi(String doi)
	{
		this.doi = doi;
	}

	public String getVolume()
	{
		return volume;
	}

	public void setVolume(String volume)
	{
		this.volume = volume;
	}

	public String getIssue()
	{
		return issue;
	}

	public void setIssue(String issue)
	{
		this.issue = issue;
	}

	public Long getPageFrom()
	{
		return pageFrom;
	}

	public void setPageFrom(Long pageFrom)
	{
		this.pageFrom = pageFrom;
	}

	public Long getPageTo()
	{
		return pageTo;
	}

	public void setPageTo(Long pageTo)
	{
		this.pageTo = pageTo;
	}

	public Long getPageSingle()
	{
		return pageSingle;
	}

	public void setPageSingle(Long pageSingle)
	{
		this.pageSingle = pageSingle;
	}

	public String getPublicationString()
	{
		return publicationString;
	}

	public void setPublicationString(String publicationString)
	{
		this.publicationString = publicationString;
	}

	public Long getPublicationDay()
	{
		return publicationDay;
	}

	public void setPublicationDay(Long publicationDay)
	{
		this.publicationDay = publicationDay;
	}

	public Long getPublicationMonth()
	{
		return publicationMonth;
	}

	public void setPublicationMonth(Long publicationMonth)
	{
		this.publicationMonth = publicationMonth;
	}

	public Long getPublicationYear()
	{
		return publicationYear;
	}

	public void setPublicationYear(Long publicationYear)
	{
		this.publicationYear = publicationYear;
	}

	public Long getPublicationDate()
	{
		return publicationDate;
	}

	public void setPublicationDate(Long publicationDate)
	{
		this.publicationDate = publicationDate;
	}

}
