package mapping.markers;

import org.apache.commons.collections.CollectionUtils;

import mapping.result.Reference;

/**
 * "Freining et al. [2002]", "Gallier [2003] or Huth and Ryan [2004]"
 */
public class NameSquareBracketYearMarkerStyle extends AbstractMarkerStyle
{

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(getNames(reference));
		sb.append(" [");
		sb.append(reference.getPublicationYear());
		sb.append("]");
		return sb.toString();
	}

	private String getNames(Reference reference)
	{
		if(!CollectionUtils.isEmpty(reference.getAuthors()))
		{
			return reference.getAuthors().get(0).getLastName();
		}
		else return "";
	}

}
