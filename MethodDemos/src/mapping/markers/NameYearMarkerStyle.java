package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;

/**
 * (Gent & Walsh, 1999; Chen & Interian, 2005)
 */
public class NameYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(ReferenceUtil.getConcatinatedLastNamesOfAuthors(reference));
		sb.append(", ");
		sb.append(reference.getPublicationYear());
		sb.append(")");
		return sb.toString();
	}

}
