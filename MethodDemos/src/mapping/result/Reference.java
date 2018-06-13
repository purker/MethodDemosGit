package mapping.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import config.Config;
import utils.StringUtil;

public class Reference extends AbstractMetaPublication
{
	private static final long serialVersionUID = -420773869366946479L;

	private String referenceIdString; // as in original extracted version grobid: "b0", "b1", ...

	// id is normalized marker in form "ref1"
	private String marker; // "[1]", "[Hun97]", if defined for this method

	private List<ReferenceAuthor> authors = new ArrayList<>();
	private String type; // book, colletion, inproceedings, ...
	private String referenceText; // only used for pdfx, where the references arn't splitted in specific fields

	private Publication publication; // enclosing publication

	public String getReferenceIdString()
	{
		return referenceIdString;
	}

	public void setReferenceIdString(String referenceIdString)
	{
		this.referenceIdString = referenceIdString;
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
		try
		{
			return new Integer(keyString.substring(4, 10));
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public Integer getPublicationId()
	{
		return getPublication().getPublicationId();
	}

}
