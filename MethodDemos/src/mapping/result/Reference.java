package mapping.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import config.Config;
import utils.PublicationUtil;
import utils.StringUtil;

public class Reference extends AbstractMetaPublication
{
	private static final long serialVersionUID = -420773869366946479L;

	private String grobidReferenceIdString; // as in original extracted version grobid: "b0", "b1", ...

	// id is normalized marker in form "ref1"
	private String marker; // "[1]", "[Hun97]", if defined for this method

	private List<ReferenceAuthor> authors = new ArrayList<>();
	private String type; // book, colletion, inproceedings, ...
	private String referenceText; // only used for pdfx, where the references arn't splitted in specific fields

	private Publication publication; // enclosing publication

	private String publicationYearSuffix; // a-z if more than one similar references with same year

	public String getGrobidReferenceIdString()
	{
		return grobidReferenceIdString;
	}

	public void setReferenceIdString(String grobidReferenceIdString)
	{
		this.grobidReferenceIdString = grobidReferenceIdString;
	}

	public String getMarker()
	{
		return marker;
	}

	public void setMarker(String marker)
	{
		this.marker = marker;
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

	public String getReferenceText()
	{
		return referenceText;
	}

	public void setReferenceText(String referenceText)
	{
		this.referenceText = referenceText;
	}

	public Publication getPublication()
	{
		return publication;
	}

	public void setPublication(Publication publication)
	{
		this.publication = publication;
	}

	@Override
	public String toString()
	{
		if(referenceText != null)
		{
			return referenceText;
		}
		else
		{
			List<Object> list = Arrays.asList(getRefString(), marker, title, source, publisher, editors, authors, edition, location, volume, issue, chapter, note, pageFrom, pageTo, publicationDateString, publicationYear, publicationMonth, publicationDay, publicationDate, doi, url, type);

			return StringUtil.objectListToString(list, ",");
		}
	}

	public String getRefString()
	{
		return Config.referencePrefix + id.getId();
	}

	@Override
	public String getKeyString()
	{
		return Config.publicationPrefix + getPublication().getId().getId() + "-" + Config.referencePrefix + +id.getId();
	}

	public static Integer getReferenceIdFromKeyString(String keyString)
	{
		return new Integer(keyString.substring(14, keyString.length()));
	}

	public static Integer getPublicationIdFromKeyString(String keyString)
	{
		return PublicationUtil.getPublicationIdFromKeyString(keyString);
	}

	@Override
	public Integer getPublicationId()
	{
		return getPublication().getPublicationId();
	}

	public String getPublicationYearSuffix()
	{
		return publicationYearSuffix;
	}

	public void setPublicationYearSuffix(String publicationYearSuffix)
	{
		this.publicationYearSuffix = publicationYearSuffix;
	}

	public String getPublicationYearWithSuffix()
	{
		StringBuffer sb = new StringBuffer();
		if(StringUtil.isNotEmpty(getPublicationYear()))
		{
			sb.append(getPublicationYear());
		}
		if(StringUtil.isNotEmpty(getPublicationYearSuffix()))
		{
			sb.append(getPublicationYearSuffix());
		}
		return sb.toString();
	}

}
