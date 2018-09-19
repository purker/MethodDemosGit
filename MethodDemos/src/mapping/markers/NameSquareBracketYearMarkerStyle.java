package mapping.markers;

import mapping.result.Reference;
import utils.PublicationUtil;

/**
 * 1-2: "Gallier [2003] or Huth and Ryan [2004]"<br>
 * >=3: "Freining et al. [2002]", "(see Ganglbauer et al. [2013] for detailed discussion)"
 */
public class NameSquareBracketYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(PublicationUtil.getConcatinatedLastNamesOfAuthors(reference, " and "));
		sb.append(" [");
		sb.append(reference.getPublicationYear());
		sb.append("]");
		return sb.toString();
	}

}
