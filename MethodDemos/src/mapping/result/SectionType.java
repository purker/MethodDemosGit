package mapping.result;

import java.util.Arrays;
import java.util.List;

public enum SectionType
{
	ABSTRACT(Arrays.asList("abstract")),
	INTRODUCTION(Arrays.asList("deo:Introduction", "introduction")),
	KEYWORDS(Arrays.asList("keywords", "general terms", "categories and subject descriptors")),
	MOTIVATION(Arrays.asList("deo:Motivation")),
	BACKGROUND(Arrays.asList("deo:Background", "background")),
	RELATEDWORK(Arrays.asList("related work", "deo:RelatedWork")),
	METHOD(Arrays.asList("method", "DoCO:Section", "deo:Methods")),
	RESULT(Arrays.asList("deo:Results")),
	CONCLUSIONS(Arrays.asList("conclusions", "deo:Conclusion")),
	REFERENCES(Arrays.asList("references")),

	ACKNOWLEDGEMENTS(Arrays.asList("deo:Acknowledgements", "acknowledgements")),
	MATERIALS(Arrays.asList("deo:Materials")),
	EVALUATION(Arrays.asList("evaluation", "deo:Evaluation")),
	DISCUSSION(Arrays.asList("deo:Discussion", "discussions")),
	FUTUREWORK(Arrays.asList("deo:FutureWork"));

	private List<String> synonyms;

	private SectionType(List<String> synonyms)
	{
		this.synonyms = synonyms;
	}

	public static SectionType getEnumBySynonym(String synonym)
	{
		for(SectionType sectionType : SectionType.values())
		{
			if(sectionType.synonyms.contains(synonym))
			{
				return sectionType;
			}
		}
		return null;
	}
}
