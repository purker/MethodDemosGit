package mapping.grobid;

import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;

import mapping.result.Reference;

public class PageScopeReadTransformer extends AbstactAttributeTransformer<Reference>
{
	private static final long serialVersionUID = -5805592045562412135L;

	@Override
	public Object buildAttributeValueWithClassObject(Record record, Reference object, Session session)
	{
		if(record == null) return null;

		for(DatabaseField field : mapping.getFields())
		{

			String recordString = (String)record.get(field);
			if(recordString != null)
			{
				try
				{
					Long value = new Long(recordString);
					if(field.getName().contains("from") || field.getName().contains("text"))
					{
						object.setPageFrom(value);
					}
					else if(field.getName().contains("to"))
					{
						object.setPageTo(value);
					}
				}
				catch(NumberFormatException e)
				{
					System.err.println("couldn't parse value " + recordString + " for " + field);
				}
			}

		}
		return null;

	}

}