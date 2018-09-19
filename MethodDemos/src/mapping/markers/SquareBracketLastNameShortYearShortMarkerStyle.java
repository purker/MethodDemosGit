package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;
import utils.StringUtil;

/**
 * x authors: [LAS02]<br>
 */
public class SquareBracketLastNameShortYearShortMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		String s = ReferenceUtil.getFirst3LettersOfFirstAuthorLastName(reference);
		if(StringUtil.isNotEmpty(s))
		{
			sb.append(s.toUpperCase());
		}
		else
		{
			if(StringUtil.isNotEmpty(reference.getPublisher()))
			{
				sb.append(StringUtil.substringMaxLength(reference.getPublisher(), 3));
			}
		}

		if(reference.getPublicationYear() != null)
		{
			sb.append(reference.getPublicationYear().toString().substring(2, 4));
			if(StringUtil.isNotEmpty(reference.getPublicationYearSuffix()))
			{
				sb.append(reference.getPublicationYearSuffix());
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
