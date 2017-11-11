package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;

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
		sb.append(ReferenceUtil.getConcatinatedLastNamesOfAuthors(reference));
		sb.append(", ");
		if(reference.getNote() != null)
		{
			sb.append(reference.getNote());
		}
		else
		{
			sb.append(reference.getPublicationYear());
		}
		sb.append("]");
		return sb.toString();
	}

}
