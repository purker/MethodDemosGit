package mapping.result;

import java.util.ArrayList;
import java.util.List;

public class Publication extends AbstractMetaPublication
{
	private static final long serialVersionUID = 8605009499117151049L;

	private PublicationType publicationType;
	private String abstractText;
	private String abstractTextGerman;
	private Integer maxSectionLayer;

	// private String keywords; // with "; " as delimiter
	private List<String> keywords = new ArrayList<>();
	private List<Author> authors = new ArrayList<>();
	private List<Affiliation> affiliations = new ArrayList<>();

	private List<Section> sections = new ArrayList<>();
	private List<CitationContext> citationContexts = new ArrayList<>();
	private List<Reference> references = new ArrayList<>();

	public PublicationType getPublicationType()
	{
		return publicationType;
	}

	public void setPublicationType(PublicationType publicationType)
	{
		this.publicationType = publicationType;
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

	public Integer getMaxSectionLayer()
	{
		return maxSectionLayer;
	}

	public void setMaxSectionLayer(Integer maxSectionLayer)
	{
		this.maxSectionLayer = maxSectionLayer;
	}

	public List<String> getKeywords()
	{
		return keywords;
	}

	public void setKeywords(List<String> keywords)
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
