package mapping.result;

import java.util.Date;
import java.util.List;

public class Publication extends BaseEntity
{
	private String title;

	private String abstractText;

	private List<Author> authors;

	private List<Affiliation> affiliations;

	private Long publicationYear;
	private Date publicationDate; // if known

	private String doi;

	private List<Reference> references;

	private List<Section> sections;

	private List<CitationContext> citationContexts;

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
