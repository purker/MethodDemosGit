package mapping.result;

import java.util.ArrayList;
import java.util.List;

public class Publication extends BaseEntity
{
	private static final long serialVersionUID = 8605009499117151049L;

	private String id;
	private String title;
	private String abstractText;
	private String abstractTextGerman;
	private String keywords; // with "; " as delimiter
	private List<Author> authors = new ArrayList<>();
	private List<Affiliation> affiliations = new ArrayList<>();

	private String source;
	private String edition;
	private String volume;
	private String issue;

	private String pageFrom;
	private String pageTo;

	private String publicationYear;
	private String publicationMonth;
	private String publicationDay;
	private String publicationDateString; // if known
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

	public String getKeywords()
	{
		return keywords;
	}

	public void setKeywords(String keywords)
	{
		this.keywords = keywords;
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

	public String getPublicationYear()
	{
		return publicationYear;
	}

	public void setPublicationYear(String publicationYear)
	{
		this.publicationYear = publicationYear;
	}

	public String getPublicationMonth()
	{
		return publicationMonth;
	}

	public void setPublicationMonth(String publicationMonth)
	{
		this.publicationMonth = publicationMonth;
	}

	public String getPublicationDay()
	{
		return publicationDay;
	}

	public void setPublicationDay(String publicationDay)
	{
		this.publicationDay = publicationDay;
	}

	public String getPublicationDateString()
	{
		return publicationDateString;
	}

	public void setPublicationDateString(String publicationDateString)
	{
		this.publicationDateString = publicationDateString;
	}

	public String getDoi()
	{
		return doi;
	}

	public void setDoi(String doi)
	{
		this.doi = doi;
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

	public List<Reference> getReferences()
	{
		return references;
	}

	public void setReferences(List<Reference> references)
	{
		this.references = references;
	}

}
