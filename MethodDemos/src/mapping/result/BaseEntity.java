package mapping.result;

import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BaseEntity
{

	@Override
	public String toString()
	{
		return new ReflectionToStringBuilder(this, new MultilineRecursiveToStringStyle()).toString();
		// return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
