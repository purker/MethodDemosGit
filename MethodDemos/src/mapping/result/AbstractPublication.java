package mapping.result;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPublication<AUT extends Author, AFF extends Affiliation, SEC extends Section, CIT extends CitationContext, REF extends AbstractReference> extends BaseEntity
{
	protected String title;
	protected String abstractText;
	protected List<AUT> authors = new ArrayList<>();
	protected List<AFF> affiliations = new ArrayList<>();

	protected Long publicationDate; // if known
	protected String doi;

	protected List<SEC> sections = new ArrayList<>();
	protected List<CIT> citationContexts = new ArrayList<>();
	protected List<REF> references = new ArrayList<>();

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

	public List<AUT> getAuthors()
	{
		return authors;
	}

	public void setAuthors(List<AUT> authors)
	{
		this.authors = authors;
	}

	public List<AFF> getAffiliations()
	{
		return affiliations;
	}

	public void setAffiliations(List<AFF> affiliations)
	{
		this.affiliations = affiliations;
	}

	public Long getPublicationDate()
	{
		return publicationDate;
	}

	public void setPublicationDate(Long publicationDate)
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

	public List<SEC> getSections()
	{
		return sections;
	}

	public void setSections(List<SEC> sections)
	{
		this.sections = sections;
	}

	public List<CIT> getCitationContexts()
	{
		return citationContexts;
	}

	public void setCitationContexts(List<CIT> citationContexts)
	{
		this.citationContexts = citationContexts;
	}

	public List<REF> getReferences()
	{
		return references;
	}

	public void setReferences(List<REF> references)
	{
		this.references = references;
	}

}
