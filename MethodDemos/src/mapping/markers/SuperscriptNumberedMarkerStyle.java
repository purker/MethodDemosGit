package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;

/**
 * @author Angela
 *
 */
public class SuperscriptNumberedMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		return ReferenceUtil.getReferenceIdNumber(reference).toString();
	}

}
