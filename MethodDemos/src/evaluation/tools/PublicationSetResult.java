package evaluation.tools;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import evaluation.EvaluationMode;
import mapping.result.AbstractMetaPublication;
import mapping.result.Publication;
import method.Method;
import utils.FileCollectionUtil;
import utils.PublicationUtil;

public class PublicationSetResult extends AbstractSetResult<Publication>
{
	public PublicationSetResult(List<EvaluationMode> modes, Method method, Collection<EvalInformationType> types) throws IOException
	{
		super(modes, method, types);
	}

	@Override
	protected void addCustomElementColumns(String id, List<String> lines)
	{
		AbstractMetaPublication publication = elements.get(id);
		lines.add("=HYPERLINK(\"" + FileCollectionUtil.getPdfFileById(PublicationUtil.getIdFromFileNameWithoutPrefix(id)).getAbsolutePath() + "\")");
		lines.add("=HYPERLINK(\"" + new File(id).getAbsolutePath() + "\")");
		lines.add("=HYPERLINK(\"" + FileCollectionUtil.getResultFilesByMethodAndId(method, PublicationUtil.getIdFromFileNameWithoutPrefix(id)).getAbsolutePath() + "\")");
		lines.add(publication.getPublicationType().name());
	}

}
