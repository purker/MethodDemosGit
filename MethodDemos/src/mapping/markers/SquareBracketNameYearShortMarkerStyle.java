package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;

public class SquareBracketNameYearShortMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(ReferenceUtil.getConcatinatedLastNamesOfAuthors(reference));
		if(reference.getPublicationYear() != null)
		{
			sb.append(reference.getPublicationYear().toString().substring(0, 2));
		}
		sb.append("]");
		return sb.toString();
	}

}
