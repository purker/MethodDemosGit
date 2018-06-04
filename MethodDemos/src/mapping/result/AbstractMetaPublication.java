package mapping.result;

import java.util.Date;

public abstract class AbstractMetaPublication extends BaseEntity implements KeyStringInterface
{
	private static final long serialVersionUID = 5037061578663513484L;

	protected Id id;
	protected String title;
	protected PublicationType publicationType;
	protected String source;
	protected String edition;
	protected String location;
	protected String volume;
	protected String issue;
	protected String chapter;
	protected String publisher;
	protected String editors;

	protected String pageFrom;
	protected String pageTo;

	protected String publicationDateString; // as in the extraction xml
	protected String publicationYear; // if null, information found in date
	protected String publicationMonth;
	protected String publicationDay;
	protected Date publicationDate;

	protected String note;

	protected String url;
	protected String doi;

	public Id getId()
	{
		return id;
	}

	public void setId(Id id)
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

	public PublicationType getPublicationType()
	{
		return publicationType;
	}

	public void setPublicationType(PublicationType publicationType)
	{
		this.publicationType = publicationType;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public String getEdition()
	{
		return edition;
	}

	public void setEdition(String edition)
	{
		this.edition = edition;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
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

	public String getChapter()
	{
		return chapter;
	}

	public void setChapter(String chapter)
	{
		this.chapter = chapter;
	}

	public String getPublisher()
	{
		return publisher;
	}

	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}

	public String getEditors()
	{
		return editors;
	}

	public void setEditors(String editors)
	{
		this.editors = editors;
	}

	public String getPageFrom()
	{
		return pageFrom;
	}

	public void setPageFrom(String pageFrom)
	{
		this.pageFrom = pageFrom;
	}

	public String getPageTo()
	{
		return pageTo;
	}

	public void setPageTo(String pageTo)
	{
		this.pageTo = pageTo;
	}

	public String getPublicationDateString()
	{
		return publicationDateString;
	}

	public void setPublicationDateString(String publicationDateString)
	{
		this.publicationDateString = publicationDateString;
	}

	public String getPublicationDay()
	{
		return publicationDay;
	}

	public void setPublicationDay(String publicationDay)
	{
		this.publicationDay = publicationDay;
	}

	public String getPublicationMonth()
	{
		return publicationMonth;
	}

	public void setPublicationMonth(String publicationMonth)
	{
		this.publicationMonth = publicationMonth;
	}

	public String getPublicationYear()
	{
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear)
	{
		this.publicationYear = publicationYear;
	}

	public Date getPublicationDate()
	{
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate)
	{
		this.publicationDate = publicationDate;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getDoi()
	{
		return doi;
	}

	public void setDoi(String doi)
	{
		this.doi = doi;
	}

}