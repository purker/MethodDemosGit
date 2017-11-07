package mapping.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Publication extends BaseEntity
{
	private static final long serialVersionUID = 8605009499117151049L;

	private String id;
	private String title;
	private String abstractText;
	private String abstractTextGerman;
	private List<Author> authors = new ArrayList<>();
	private List<Affiliation> affiliations = new ArrayList<>();

	private Long publicationYear;
	private Long publicationMonth;
	private Long publicationDay;
	private Date publicationDate; // if known
	private String doi;

	private List<Section> sections = new ArrayList<>();
	private List<CitationContext> citationContexts = new ArrayList<>();
	private List<Reference> references = new ArrayList<>();

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

	public String getAbstractText()
	{
		return abstractText;
	}

	public void setAbstractText(String abstractText)
	{
		this.abstractText = abstractText;
	}

	public String getAbstractTextGerman()
	{
		return abstractTextGerman;
	}

	public void setAbstractTextGerman(String abstractTextGerman)
	{
		this.abstractTextGerman = abstractTextGerman;
	}

	public List<Author> getAuthors()
	{
		return authors;
	}

	public void setAuthors(List<Author> authors)
	{
		this.authors = authors;
	}

	public List<Affiliation> getAffiliations()
	{
		return affiliations;
	}

	public void setAffiliations(List<Affiliation> affiliations)
	{
		this.affiliations = affiliations;
	}

	public Long getPublicationYear()
	{
		return publicationYear;
	}

	public void setPublicationYear(Long publicationYear)
	{
		this.publicationYear = publicationYear;
	}

	public Long getPublicationMonth()
	{
		return publicationMonth;
	}

	public void setPublicationMonth(Long publicationMonth)
	{
		this.publicationMonth = publicationMonth;
	}

	public Long getPublicationDay()
	{
		return publicationDay;
	}

	public void setPublicationDay(Long publicationDay)
	{
		this.publicationDay = publicationDay;
	}

	public Date getPublicationDate()
	{
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate)
	{
		this.publicationDate = publicationDate;
	}

	public String getDoi()
	{
		return doi;
	}

	public void setDoi(String doi)
	{
		this.doi = doi;
	}

	public List<Reference> getReferences()
	{
		return references;
	}

	public void setReferences(List<Reference> references)
	{
		this.references = references;
	}

	public List<Section> getSections()
	{
		return sections;
	}

	public void setSections(List<Section> sections)
	{
		this.sections = sections;
	}

	public List<CitationContext> getCitationContexts()
	{
		return citationContexts;
	}

	public void setCitationContexts(List<CitationContext> citationContexts)
	{
		this.citationContexts = citationContexts;
	}

}
