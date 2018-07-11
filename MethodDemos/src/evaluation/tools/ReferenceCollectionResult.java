package evaluation.tools;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import evaluation.EvaluationMode;
import mapping.result.Reference;
import method.Method;

public class ReferenceCollectionResult extends AbstractCollectionResult<Reference>
{

	public ReferenceCollectionResult(List<EvaluationMode> modes, Method method, Collection<EvalInformationType> types) throws IOException
	{
		super(modes, method, types);
	}

	@Override
	protected Comparator<String> getComparator()
	{
		return Comparator.comparingInt(Reference::getPublicationIdFromKeyString).thenComparingInt(Reference::getReferenceIdFromKeyString);
	}

	@Override
	protected CollectionEnum getCollectionEnum()
	{
		return CollectionEnum.REFERENCE;
	}
}
