package mapping;

import config.Config;
import mapping.result.Publication;
import mapping.result.Section;

public class SectionReferenceWorker extends Worker
{
	protected boolean replaceDiamond;

	public SectionReferenceWorker(boolean replaceDiamond)
	{
		this.replaceDiamond = replaceDiamond;
	}

	public SectionReferenceWorker()
	{
		this.replaceDiamond = false;
	}

	@Override
	public void doWork(Publication publication)
	{
		// Map<String, Reference> referenceMap = publication.getReferences().stream().collect(Collectors.toMap(Reference::getId, Function.identity()));

		for(Section section : publication.getSections())
		{
			for(String referenceId : section.getReferenceIds())
			{
				if(referenceId != null)
				{
					if(replaceDiamond)
					{
						referenceId = referenceId.replaceFirst("#b", Config.referencePrefix);
					}
					// Reference reference = referenceMap.get(referenceId);
					// section.getReferences().add(reference);
				}
			}
		}

	}

}
