package mapping.grobid;

import org.eclipse.persistence.mappings.foundation.AbstractTransformationMapping;
import org.eclipse.persistence.mappings.transformers.FieldTransformer;
import org.eclipse.persistence.sessions.Session;

public class PageScopeWriteTransformer implements FieldTransformer
{
	private static final long serialVersionUID = -8425609418052860495L;
	private AbstractTransformationMapping mapping;

	@Override
	public void initialize(AbstractTransformationMapping mapping)
	{
		this.mapping = mapping;
	}

	@Override
	public Object buildFieldValue(Object instance, String fieldName, Session session)
	{
		return null;
	}

}