package mapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mapping.result.Publication;
import mapping.result.Section;
import utils.StringUtil;

/**
 * Sets layer of each Section (Section.layer)
 * 
 * <br>
 * e.g.<br>
 * 1. -> Sectionlayer 1<br>
 * 1.1.1 -> Sectionlayer 3
 * 
 * and the maximum Sectionlayer of all Sections (Publication.maxSectionLayer)
 */
public class SectionLayerWorker extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		try
		{
			Integer maxSectionLayer = 0;
			for(Section section : publication.getSections())
			{
				if(StringUtil.isNotEmpty(section.getLevel()))
				{
					// Gruppen von Zahlen mit optionalem Punkt ("1", "11.1.")Leerzeichen(Zeichen)
					Pattern pattern = Pattern.compile("([\\dA-Z]+\\.?)");
					Matcher matcher = pattern.matcher(section.getLevel());

					int count = 0;
					while(matcher.find())
					{
						count++;
					}
					if(maxSectionLayer < count)
					{
						maxSectionLayer = count;
					}

					System.out.println(section.getLevel() + " " + count);
					section.setLayer(count);
				}

			}
			publication.setMaxSectionLayer(maxSectionLayer);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
