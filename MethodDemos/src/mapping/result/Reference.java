package mapping.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reference extends BaseEntity
{
	private static final long serialVersionUID = -3747798923009051624L;

	private String id; // normalized marker in form "ref1"
	private String marker; // "[1]", "[Hun97]", if defined for this method
	private String title;
	private String source;
	private String location;
	private String publisher;
	private String editor;
	private String type; // book, colletion, inproceedings, ...

	private List<ReferenceAuthor> authors = new ArrayList<>();

	private String doi;
	private String url;

	private String edition;
	private String volume;
	private String issue;
	private String note;

	private Long pageFrom;
	private Long pageTo;

	private String publicationDateString; // as in the extraction xml
	private Long publicationDay;
	private Long publicationMonth;
	private Long publicationYear; // if null, information found in date
	private Date publicationDate;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMarker()
	{
		return marker;
	}

	public void setMarker(String marker)
	{
		this.marker = marker;
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

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getPublisher()
	{
		return publisher;
	}

	public void setPublisher(String publisher)
	{
		this.publisher = publisher;
	}

	public String getEditor()
	{
		return editor;
	}

	public void setEditor(String editor)
	{
		this.editor = editor;
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

	public String getEdition()
	{
		return edition;
	}

	public void setEdition(String edition)
	{
		this.edition = edition;
	}

	public String getDoi()
	{
		return doi;
	}

	public void setDoi(String doi)
	{
		this.doi = doi;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
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

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
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

	public String getPublicationDateString()
	{
		return publicationDateString;
	}

	public void setPublicationDateString(String publicationDateString)
	{
		this.publicationDateString = publicationDateString;
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

	public Date getPublicationDate()
	{
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate)
	{
		this.publicationDate = publicationDate;
	}

}
