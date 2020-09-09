package evaluation.tools;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import utils.FailureUtil;

public class EvalInformationTypeComparatorMapping
{
	private static Map<EvalInformationType, Comparator<String>> map = new HashMap<>();
	private static Map<EvalInformationType, RelationComparators> relationMap = new HashMap<>();

	static
	{
		map.put(EvalInformationType.TITLE, EvaluationUtils.swComparator);
		map.put(EvalInformationType.PUBLICATIONTYPE, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.ABSTRACT, (EvaluationUtils.swComparator));
		map.put(EvalInformationType.KEYWORDS, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.AUTHORS, EvaluationUtils.authorComparator);
		map.put(EvalInformationType.EMAILS, EvaluationUtils.emailComparator);
		relationMap.put(EvalInformationType.AUTHOR_EMAILS, new RelationComparators(map.get(EvalInformationType.AUTHORS), "authors", map.get(EvalInformationType.EMAILS), "emails"));
		map.put(EvalInformationType.AFFILIATIONS, EvaluationUtils.cosineComparator());
		relationMap.put(EvalInformationType.AUTHOR_AFFILIATIONS, new RelationComparators(map.get(EvalInformationType.AUTHORS), "authors", map.get(EvalInformationType.AFFILIATIONS), "affiliations"));
		map.put(EvalInformationType.SOURCE, EvaluationUtils.journalComparator);
		map.put(EvalInformationType.VOLUME, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.ISSUE, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.PAGE_FROM, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.PAGE_TO, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.DATE, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.DOI, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.SECTIONS, EvaluationUtils.swComparator);
		map.put(EvalInformationType.REFERENCES, EvaluationUtils.cosineComparator(.6));

		relationMap.put(EvalInformationType.SECTION_LEVELS, new RelationComparators(map.get(EvalInformationType.SECTIONS), "sections", EvaluationUtils.exactComparator, "level-exact"));
		relationMap.put(EvalInformationType.SECTION_REFERENCES, new RelationComparators(map.get(EvalInformationType.SECTIONS), "sections", map.get(EvalInformationType.REFERENCES), "references-default"));
		// map.put(EvalInformationType.REFERENCE_AUTHORS, EvaluationUtils.);

		map.put(EvalInformationType.REFERENCE_ID, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_MARKER, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_TITLE, EvaluationUtils.swComparator);
		// TODOmap.put(EvalInformationType.REFERENCE_PUBLICATIONTYPE, EvaluationUtils.);
		map.put(EvalInformationType.REFERENCE_SOURCE, EvaluationUtils.journalComparator);
		map.put(EvalInformationType.REFERENCE_PUBLISHER, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_EDITOR, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_AUTHORS, EvaluationUtils.authorComparator);
		map.put(EvalInformationType.REFERENCE_EDITION, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_VOLUME, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_ISSUE, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_CHAPTER, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_NOTE, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_PAGES, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_PAGEFROM, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_PAGETO, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_LOCATION, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_DATE, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_DOI, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.REFERENCE_URL, EvaluationUtils.defaultComparator);

	}

	public static Comparator<String> getComparatorByType(EvalInformationType type)
	{
		Comparator<String> comp = map.get(type);

		if(comp == null)
		{
			FailureUtil.exit("comparator for type \"" + type + "\" is not defined (in class " + EvalInformationTypeComparatorMapping.class.getSimpleName() + ")");
		}

		return comp;
	}

	public static RelationComparators getComparatorsByType(EvalInformationType type)
	{
		RelationComparators comps = relationMap.get(type);

		if(comps == null)
		{
			FailureUtil.exit("comparator for type \"" + type + "\" is not defined (in class " + EvalInformationTypeComparatorMapping.class.getSimpleName() + ")");
		}
		return comps;
	}
}
