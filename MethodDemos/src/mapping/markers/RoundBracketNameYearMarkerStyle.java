package mapping.markers;

import mapping.result.Reference;
import utils.CollectionUtil;
import utils.PublicationUtil;
import utils.ReferenceUtil;

/**
 * 1: (Puntigam, 1997)<br>
 * 2: (Boyapati and Rinard, 2001)<br>
 * >2: (Milner et al., 1992)
 */
public class RoundBracketNameYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		if(CollectionUtil.isNotEmpty(reference.getAuthors()))
		{
			if(reference.getAuthors().size() <= 2)
			{
				sb.append(ReferenceUtil.getConcatinatedLastNamesOfAuthors(reference, " and "));

			}
			else
			{
				sb.append(PublicationUtil.getLastNameOfFirstAuthorEtAl(reference));
			}
			sb.append(", ");
		}
		else
		{
			if(reference.getPublisher() != null)
			{
				sb.append(reference.getPublisher());
				sb.append(", ");
			}
			else
				if(reference.getEditors() != null)
				{
					sb.append(reference.getEditors());
					sb.append(", ");
				}
		}
		sb.append(reference.getPublicationYear());
		sb.append(")");
		return sb.toString();
	}

}
