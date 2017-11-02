package mapping.grobid;

import org.eclipse.persistence.mappings.foundation.AbstractTransformationMapping;
import org.eclipse.persistence.mappings.transformers.AttributeTransformer;
import org.eclipse.persistence.sessions.Record;
import org.eclipse.persistence.sessions.Session;

public abstract class AbstactAttributeTransformer<T> implements AttributeTransformer
{
	private static final long serialVersionUID = 8323176513604684388L;
	protected AbstractTransformationMapping mapping;

	@Override
	public void initialize(AbstractTransformationMapping mapping)
	{
		this.mapping = mapping;
	}

	@Override
	public Object buildAttributeValue(Record record, Object object, Session session)
	{
		return buildAttributeValueWithClassObject(record, (T)object, session);
	}

	public abstract Object buildAttributeValueWithClassObject(Record record, T object, Session session);
}
