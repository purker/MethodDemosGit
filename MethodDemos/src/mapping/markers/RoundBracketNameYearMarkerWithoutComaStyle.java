package mapping.markers;

import mapping.result.Reference;
import utils.PublicationUtil;

/**
 * (Modgil and Bench-Capon 2011)
 */
public class RoundBracketNameYearMarkerWithoutComaStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		String names = PublicationUtil.getConcatinatedLastNamesOfAuthors(reference.getAuthors());

		String pre = null;
		if(names != null)
		{
			pre = names;
		}
		else
		{
			if(reference.getPublisher() != null)
			{
				pre = reference.getPublisher();
			}
			else
				if(reference.getEditors() != null)
				{
					pre = reference.getEditors();
				}
		}
		if(pre != null)
		{
			sb.append(pre);
			sb.append(" ");
		}
		sb.append(reference.getPublicationYear());
		sb.append(")");
		return sb.toString();
	}

}
