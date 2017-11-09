package mapping.markers;

import org.apache.commons.collections.CollectionUtils;

import mapping.result.Reference;

public class SquareBracketNameYearShortMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(getNames(reference));
		if(reference.getPublicationYear() != null)
		{
			sb.append(reference.getPublicationYear().toString().substring(0, 2));
		}
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
