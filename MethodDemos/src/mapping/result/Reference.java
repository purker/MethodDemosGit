package mapping.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringStyle;

import utils.StringUtil;

public class Reference extends AbstractMetaPublication
{
	private static final long serialVersionUID = -3747798923009051624L;

	// id is normalized marker in form "ref1"
	private String marker; // "[1]", "[Hun97]", if defined for this method

	private List<ReferenceAuthor> authors = new ArrayList<>();
	private String type; // book, colletion, inproceedings, ...

	public String getMarker()
	{
		return marker;
	}

	public void setMarker(String marker)
	{
		this.marker = marker;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<ReferenceAuthor> getAuthors()
	{
		return authors;
	}

	public void setAuthors(List<ReferenceAuthor> authors)
	{
		this.authors = authors;
	}

	@Override
	public String toString()
	{
		return StringUtil.getAllValuesOfObject(this);

		// return new ReflectionToStringBuilder(this, new ToStringStyle()
		// {
		// private static final long serialVersionUID = -6243515731657791041L;
		//
		// @SuppressWarnings("unchecked")
		// @Override
		// public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail)
		// {
		// // boolean isEmptyList = value instanceof List;
		// // System.out.println(isEmptyList + " " + fieldName + isFullDetail(fullDetail));
		//
		// if(value != null)
		// {
		// if(fieldName.equals("authors"))
		// {
		// if(!((List<?>)value).isEmpty())
		// {
		// // appendFieldStart(buffer, fieldName);
		// value = PublicationUtil.concatinateAllAuthorNames((List<ReferenceAuthor>)value);
		// appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
		// appendFieldEnd(buffer, fieldName);
		// }
		// }
		// else
		// {
		// // appendFieldStart(buffer, fieldName);
		// appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
		// appendFieldEnd(buffer, fieldName);
		// }
		// }
		// }
		//
		// @Override
		// public void appendStart(StringBuffer buffer, Object object)
		// {
		//
		// }
		//
		// @Override
		// public void appendEnd(StringBuffer buffer, Object object)
		// {
		//
		// }
		// }).toString();
	}

	public final static class NotNullToStringStyle extends ToStringStyle
	{
		public static ToStringStyle NOT_NULL_STYLE = new NotNullToStringStyle();

		private static final long serialVersionUID = 1L;

		/**
		 * <p>
		 * Use the static constant rather than instantiating.
		 * </p>
		 */
		NotNullToStringStyle()
		{
			super();
			this.setFieldSeparator("  ");
			this.setFieldSeparatorAtStart(true);
			this.setContentEnd(".");
		}

		/**
		 * <p>
		 * Ensure <code>Singleton</code> after serialization.
		 * </p>
		 *
		 * @return the singleton
		 */
		private Object readResolve()
		{
			return NOT_NULL_STYLE;
		}

		@Override
		public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail)
		{
			if(value != null)
			{
				appendFieldStart(buffer, fieldName);
				appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
				appendFieldEnd(buffer, fieldName);
			}
		}
	}

}
