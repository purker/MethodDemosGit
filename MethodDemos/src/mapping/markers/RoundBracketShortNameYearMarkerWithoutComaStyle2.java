package mapping.markers;

import mapping.result.Reference;
import utils.PublicationUtil;

/**
 * (Bench-Capon, Prakken, and Sartor 2009)
 */
public class RoundBracketShortNameYearMarkerWithoutComaStyle2 extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		String names = PublicationUtil.getConcatinatedLastNamesOfAuthors2(reference.getAuthors());

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
			else if(reference.getEditor() != null)
			{
				pre = reference.getEditor();
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
