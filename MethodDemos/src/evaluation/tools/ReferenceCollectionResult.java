package evaluation.tools;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import evaluation.EvaluationMode;
import mapping.result.Reference;
import method.Method;

public class ReferenceCollectionResult extends AbstractCollectionResult<Reference>
{
	private Map<String, Reference> notMatchingReferences = new TreeMap<>(getComparator());
	private Map<String, Reference> allReferences = new TreeMap<>(getComparator());

	public ReferenceCollectionResult(List<EvaluationMode> modes, Method method, Collection<EvalInformationType> types)
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

	public void addNotMatchingReferences(Reference reference)
	{
		notMatchingReferences.put(checkNotNullGetKeyString(reference), reference);
	}

	public void initializeAllReferences()
	{
		allReferences.putAll(elements);
		allReferences.putAll(notMatchingReferences);
	}

	public Map<String, Reference> getAllReferences()
	{
		return allReferences;
	}
}
