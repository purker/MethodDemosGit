package mapping.markers;

import mapping.result.Reference;
import utils.PublicationUtil;
import utils.StringUtil;

/**
 * 1: (acatech 2011)<br>
 * 2: (Modgil and Bench-Capon 2011)<br>
 * >=3: (Riemer et al. 2009)
 */
public class RoundBracketNameYearMarkerWithoutComaStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		String names = PublicationUtil.getConcatinatedLastNamesOfAuthors(reference.getAuthors(), " and ");

		String pre = null;
		if(StringUtil.isNotEmpty(names))
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
				else
					if(reference.getTitle() != null)
					{
						pre = reference.getTitle();
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
