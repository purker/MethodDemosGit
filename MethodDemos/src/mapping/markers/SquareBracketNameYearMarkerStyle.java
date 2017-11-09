package mapping.markers;

import org.apache.commons.collections.CollectionUtils;

import mapping.result.Reference;

/**
 * (Gent & Walsh, 1999; Chen & Interian, 2005)
 */
public class SquareBracketNameYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(getNames(reference));
		sb.append(", ");
		sb.append(reference.getPublicationYear());
		sb.append("]");
		return sb.toString();
	}

	private String getNames(Reference reference)
	{
		if(!CollectionUtils.isEmpty(reference.getAuthors()))
		{
			return reference.getAuthors().get(0).getLastName();
		}
		else return "";
	}

}
