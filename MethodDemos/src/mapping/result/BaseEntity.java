package mapping.result;

import java.io.Serializable;

import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BaseEntity implements Serializable
{
	private static final long serialVersionUID = -1315650541479592103L;

	@Override
	public String toString()
	{
		return new ReflectionToStringBuilder(this, new MultilineRecursiveToStringStyle()).toString();
		// return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
