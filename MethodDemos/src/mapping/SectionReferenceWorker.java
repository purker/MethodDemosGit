package mapping;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.Section;

public class SectionReferenceWorker extends Worker
{
	private boolean replaceDiamond;

	public SectionReferenceWorker(boolean replaceDiamond)
	{
		this.replaceDiamond = replaceDiamond;
	}

	public SectionReferenceWorker()
	{
		this.replaceDiamond = false;
	}

	@Override
	protected void doWork(Publication publication)
	{
		Map<String, Reference> referenceMap = publication.getReferences().stream().collect(Collectors.toMap(Reference::getId, Function.identity()));

		for(Section section : publication.getSections())
		{
			for(String referenceId : section.getReferenceIds())
			{
				if(referenceId != null)
				{
					if(replaceDiamond)
					{
						referenceId = referenceId.replaceFirst("#", "");
					}
					Reference reference = referenceMap.get(referenceId);
					section.getReferences().add(reference);
				}
			}
		}

	}

}
