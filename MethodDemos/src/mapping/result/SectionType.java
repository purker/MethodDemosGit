package mapping.result;

import java.util.Arrays;
import java.util.List;

public enum SectionType
{
	ABSTRACT(Arrays.asList("abstract")), METHOD(Arrays.asList("method")), CONCLUSIONS(Arrays.asList("conclusions")), REFERENCES(Arrays.asList("references"));

	private List<String> synonyms;

	private SectionType(List<String> synonyms)
	{
		this.synonyms = synonyms;
	}

	public static SectionType getEnumBySynonym(String synonym)
	{
		for(SectionType sectionType : SectionType.values())
		{
			if(sectionType.synonyms.contains(synonym.toLowerCase()))
			{
				return sectionType;
			}
		}
		return null;
	}
}
