package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;

/**
 * [1]
 */
public class SquareBracketNumberedMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append(ReferenceUtil.getReferenceIdNumber(reference.getId()));
		sb.append("]");
		return sb.toString();
	}

}
