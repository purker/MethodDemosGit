package mapping.markers;

import mapping.result.Reference;
import utils.CollectionUtil;
import utils.ReferenceUtil;

/**
 * 141336<br>
 * 1: (Janson, 1987)<br>
 * 2: (Chen & Interian, 2005)<br>
 * 3: (Janson, Luczak, & Rucinski, 2000)<br>
 * 4: (Cocco, Dubois, Mandler, & Monasson, 2003)<br>
 * >4: no example
 */
public class RoundBracketNameAmpYearMarkerStyle extends AbstractMarkerStyle
{
	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		if(CollectionUtil.isNotEmpty(reference.getAuthors()))
		{
			int authorCount = reference.getAuthors().size();
			if(authorCount <= 2)
			{
				sb.append(ReferenceUtil.getConcatinatedLastNamesOfAuthors(reference, " & "));
			}
			else
			{
				sb.append(ReferenceUtil.getConcatinatedLastNamesOfAuthors(reference.getAuthors().subList(0, authorCount - 1), ", "));
				sb.append(", & " + reference.getAuthors().get(authorCount - 1).getLastName());
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
