package mapping.result;

import java.util.ArrayList;
import java.util.List;

public class Publication extends AbstractMetaPublication
{
	private static final long serialVersionUID = 8605009499117151049L;
	public static final String KEYWORD_DELIMITER = "; ";

	private String abstractText;
	private String abstractTextGerman;
	private String keywords; // with "; " as delimiter
	private List<Author> authors = new ArrayList<>();
	private List<Affiliation> affiliations = new ArrayList<>();

	private List<Section> sections = new ArrayList<>();
	private List<CitationContext> citationContexts = new ArrayList<>();
	private List<Reference> references = new ArrayList<>();

	@Override
	public String getTitle()
	{
		return title;
	}

	@Override
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

	@Override
	public String getSource()
	{
		return source;
	}

	@Override
	public void setSource(String source)
	{
		this.source = source;
	}

	@Override
	public String getEdition()
	{
		return edition;
	}

	@Override
	public void setEdition(String edition)
	{
		this.edition = edition;
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
