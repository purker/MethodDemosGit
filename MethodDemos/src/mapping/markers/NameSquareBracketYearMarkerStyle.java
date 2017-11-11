package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;

/**
 * "Freining et al. [2002]", "Gallier [2003] or Huth and Ryan [2004]"
 */
public class NameSquareBracketYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(ReferenceUtil.getConcatinatedLastNamesOfAuthors(reference));
		sb.append(" [");
		sb.append(reference.getPublicationYear());
		sb.append("]");
		return sb.toString();
	}

}
