package mapping.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractReference extends BaseEntity
{
	protected String id;
	protected String title;
	protected String source;
	protected String type; // book, colletion, inproceedings, ...

	protected List<ReferenceAuthor> authors = new ArrayList<>();

	protected String doi;

	protected String volume;
	protected String issue;
	protected Long pageFrom;
	protected Long pageTo;

	protected Long publicationYear; // if null, information found in date
	protected Date publicationDate;

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

	public Long getYear()
	{
		return publicationYear;
	}

	public void setYear(Long year)
	{
		this.publicationYear = year;
	}

	public Long getPublicationYear()
	{
		return publicationYear;
	}

	public void setPublicationYear(Long publicationYear)
	{
		this.publicationYear = publicationYear;
	}

	public String getDoi()
	{
		return doi;
	}

	public void setDoi(String doi)
	{
		this.doi = doi;
	}

	public Date getPublicationDate()
	{
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate)
	{
		this.publicationDate = publicationDate;
	}

}
