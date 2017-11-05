package mapping.grobid;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mapping.Worker;
import mapping.result.Publication;
import mapping.result.Reference;

/**
 * If note contains "edition", set edition number. publication.note -> publication.edition
 */
public class ReferenceEditionWorker extends Worker
{
	private static Map<String, List<String>> synoyms = new HashMap<>();

	static
	{
		synoyms.put("1", Arrays.asList("1.", "1st", "first"));
		synoyms.put("2", Arrays.asList("2.", "2nd", "second", "2rd")); // 2rd was in test files
		synoyms.put("3", Arrays.asList("3.", "3rd", "third"));
		synoyms.put("4", Arrays.asList("4.", "4th", "fourth"));
		synoyms.put("5", Arrays.asList("5.", "5th", "fifth"));
		synoyms.put("6", Arrays.asList("6.", "6th", "fifth"));
		synoyms.put("7", Arrays.asList("7.", "7h", "fifth"));
		synoyms.put("8", Arrays.asList("8.", "8th", "eigth"));
		synoyms.put("9", Arrays.asList("9.", "9th", "ninth"));
		synoyms.put("10", Arrays.asList("10.", "10th", "tenth"));
		synoyms.put("11", Arrays.asList("11.", "11th", "eleventh"));
		synoyms.put("12", Arrays.asList("12.", "12th", "twelfth"));
	}

	@Override
	protected void doWork(Publication publication)
	{
		for(Reference reference : publication.getReferences())
		{
			if(reference.getNote() != null && reference.getNote().contains("edition"))
			{
				for(Entry<String, List<String>> synonymEntry : synoyms.entrySet())
				{
					boolean breakOuterFor = false;
					for(String synonym : synonymEntry.getValue())
					{
						if(reference.getNote().startsWith(synonym))
						{
							reference.setEdition(synonymEntry.getKey());
							reference.setNote(null);
							breakOuterFor = true;
							break;
						}
					}
					if(breakOuterFor) break;
				}
			}
		}

	}

}
