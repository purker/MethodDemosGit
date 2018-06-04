package mapping.markers;

import mapping.result.Reference;

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
		sb.append(reference.getId());
		sb.append("]");
		return sb.toString();
	}

}
