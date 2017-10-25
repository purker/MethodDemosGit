package mapping;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import mapping.result.AbstractPublication;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.CitationContext;
import mapping.result.AbstractReference;
import mapping.result.Section;

public class SectionReferenceWorker extends Worker<AbstractPublication<Author, Affiliation, Section, CitationContext, AbstractReference>>
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
	protected void doWork(AbstractPublication<Author, Affiliation, Section, CitationContext, AbstractReference> publication)
	{
		Map<String, AbstractReference> referenceMap = publication.getReferences().stream().collect(Collectors.toMap(AbstractReference::getId, Function.identity()));

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
					AbstractReference reference = referenceMap.get(referenceId);
					section.getReferences().add(reference);
				}
			}
		}

	}

}
