package mapping.cermine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mapping.Worker;
import mapping.result.Publication;
import mapping.result.Section;
import utils.StringUtil;

/**
 * Changes Section.title="1 Introduction" to Section.title="Introduction" and Section.level="1"
 *
 */
public class SectionLevelWorker extends Worker
{
	@Override
	public void doWork(Publication publication)
	{
		try
		{
			for(Section section : publication.getSections())
			{
				if(StringUtil.isNotEmpty(section.getTitle()))
				{
					// Gruppen von Zahlen mit Punkt ("1", "11.1.")Leerzeichen(Zeichen)
					Pattern pattern = Pattern.compile("(^\\d{1}[\\d*\\.?]*) (.*)");
					Matcher matcher = pattern.matcher(section.getTitle());

					if(matcher.find()) // only first match
					{
						String sectionLevel = matcher.group(1);
						String sectionTitel = matcher.group(2);

						if(StringUtil.isNotEmpty(sectionLevel) || StringUtil.isNotEmpty(sectionTitel))
						{
							section.setLevel(sectionLevel);
							section.setTitle(sectionTitel);
						}
					}
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
