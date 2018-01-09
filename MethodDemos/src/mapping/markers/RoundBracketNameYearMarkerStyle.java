package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;

/**
 * (Gent & Walsh, 1999; Chen & Interian, 2005)
 */
public class RoundBracketNameYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		String names = ReferenceUtil.getConcatinatedLastNamesOfAuthors(reference);
		if(names != null)
		{
			sb.append(names);
			sb.append(", ");
		}
		else
		{
			if(reference.getPublisher() != null)
			{
				sb.append(reference.getPublisher());
				sb.append(", ");
			}
			else if(reference.getEditor() != null)
			{
				sb.append(reference.getEditor());
				sb.append(", ");
			}
		}
		sb.append(reference.getPublicationYear());
		sb.append(")");
		return sb.toString();
	}

}
