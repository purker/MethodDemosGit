package mapping.markers;

import mapping.result.Reference;
import utils.ReferenceUtil;
import utils.StringUtil;

/**
 * 1 author: [And02]<br>
 * 2-4 authors: [CC77]<br>
 * >5 authors && stopAfter3Authors: [BCC+03]<br>
 * >5 authors && !stopAfter3Authors: [BCCDKW]<br>
 *
 */
public class SquareBracketNameShortYearShortMarkerStyle extends AbstractMarkerStyle
{
	private boolean stopAfter3Authors = true;

	public SquareBracketNameShortYearShortMarkerStyle()
	{}

	public SquareBracketNameShortYearShortMarkerStyle(boolean stopAfter3Authors)
	{
		this.stopAfter3Authors = stopAfter3Authors;
	}

	@Override
	public String getMarkerString(Reference reference)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		String s = ReferenceUtil.getShortNamesOfAuthors(reference, stopAfter3Authors);
		if(StringUtil.isNotEmpty(s))
		{
			sb.append(s);
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
