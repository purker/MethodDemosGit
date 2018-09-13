package mapping.markers;

import mapping.result.Reference;

/**
 * @author Angela
 *
 */
public class SuperscriptNumberedMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		return reference.getIdString();
	}

}
