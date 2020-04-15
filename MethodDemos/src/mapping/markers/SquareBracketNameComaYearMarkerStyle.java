package mapping.markers;

import mapping.result.Reference;
import utils.CollectionUtil;
import utils.PublicationUtil;

/**
 * 1: [Miksch, 1999a]<br>
 * 2: [Lee and Geller, 2006]<br>
 * >2: [Peleg et al., 2003]
 */
public class SquareBracketNameComaYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if(CollectionUtil.isNotEmpty(reference.getAuthors()))
		{
			sb.append(PublicationUtil.getConcatinatedLastNamesOfAuthors(reference, " and "));
		}
		else
		{
			if(reference.getPublisher() != null)
			{
				sb.append(reference.getPublisher());
			}
			else
				if(reference.getEditors() != null)
				{
					sb.append(reference.getEditors());
				}
		}
		sb.append(", ");
		sb.append(reference.getPublicationYearWithSuffix());
		sb.append("]");
		return sb.toString();
	}

}
