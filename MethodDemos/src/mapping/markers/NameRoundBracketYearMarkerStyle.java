package mapping.markers;

import mapping.result.Reference;
import utils.PublicationUtil;

/**
 * 1: Gallier (2003)<br>
 * 2: Huth and Ryan (2004)<br>
 * 3: Dolev, Dwork, and Stockmeyer (1987)<br>
 */
public class NameRoundBracketYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(PublicationUtil.getConcatinatedLastNamesOfAuthors2(reference.getAuthors()));
		sb.append(" (");
		sb.append(reference.getPublicationYear());
		sb.append(")");
		return sb.toString();
	}

}
