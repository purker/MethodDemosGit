package mapping.parscit;

import mapping.Worker;
import mapping.result.Publication;
import mapping.result.Section;
import mapping.result.SectionType;

public class SectionTypeNormalizerWorker extends Worker<Publication>
{

	@Override
	protected void doWork(Publication publication)
	{
		for(Section section : publication.getSections())
		{
			SectionType typeEnum = SectionType.getEnumBySynonym(section.getType());
			if(typeEnum == null)
			{
				System.out.println("no enum for synonym: " + section.getType());
			}
			section.setTypeEnum(typeEnum);
		}

	}

}
