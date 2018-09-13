package mapping.markers;

import mapping.result.Reference;

/**
 * "1."
 */
public class NumberedMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(reference.getIdString());
		sb.append(".");
		return sb.toString();
	}

}
