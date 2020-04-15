package mapping.markers;

import mapping.result.Reference;
import utils.PublicationUtil;

/**
 * [Alves2001]
 *
 */
public class SquareBracketNameYearMarkerWithoutWhitespaceStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(PublicationUtil.getLastNameOfFirstAuthor(reference.getAuthors()));
		if(reference.getPublicationYear() != null)
		{
			sb.append(reference.getPublicationYear().toString());
		}
		sb.append("]");
		return sb.toString();
	}

}
