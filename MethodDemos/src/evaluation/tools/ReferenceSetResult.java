package evaluation.tools;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import evaluation.EvaluationMode;
import evaluation.informationresults.AbstractSingleInformationDocResult;
import mapping.result.Publication;
import mapping.result.Reference;
import method.Method;

public class ReferenceSetResult extends AbstractSetResult<Reference>
{

	public ReferenceSetResult(List<EvaluationMode> modes, Method method, Collection<EvalInformationType> types) throws IOException
	{
		super(modes, method, types);
	}

	@Override
	protected void addCustomElementColumns(String id, List<String> lines)
	{
		// add nothing
	}

	public String getIdFromElement(Publication pub, Reference ref)
	{
		return pub.getId() + "-" + ref.getId();
	}

	public void addResult(Publication pub, AbstractSingleInformationDocResult<?> refResult, Reference ref)
	{
		addResult(getIdFromElement(pub, ref), refResult, ref);
	}

}
