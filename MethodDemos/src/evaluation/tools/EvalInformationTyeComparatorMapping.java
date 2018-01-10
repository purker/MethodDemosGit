package evaluation.tools;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class EvalInformationTyeComparatorMapping
{
	private static Map<EvalInformationType, Comparator<String>> map = new HashMap<>();
	private static Map<EvalInformationType, RelationComparators> relationMap = new HashMap<>();

	static
	{
		map.put(EvalInformationType.TITLE, EvaluationUtils.swComparator);
		map.put(EvalInformationType.ABSTRACT, (EvaluationUtils.swComparator));
		map.put(EvalInformationType.ABSTRACTGERMAN, (EvaluationUtils.swComparator));
		map.put(EvalInformationType.KEYWORDS, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.AUTHORS, EvaluationUtils.authorComparator);
		map.put(EvalInformationType.EMAILS, EvaluationUtils.emailComparator);
		relationMap.put(EvalInformationType.AUTHOR_EMAILS, new RelationComparators(map.get(EvalInformationType.AUTHORS), map.get(EvalInformationType.EMAILS)));
		map.put(EvalInformationType.AFFILIATIONS, EvaluationUtils.cosineComparator());
		relationMap.put(EvalInformationType.AUTHOR_AFFILIATIONS, new RelationComparators(map.get(EvalInformationType.AUTHORS), map.get(EvalInformationType.AFFILIATIONS)));
		map.put(EvalInformationType.SOURCE, EvaluationUtils.journalComparator);
		map.put(EvalInformationType.VOLUME, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.ISSUE, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.PAGE_FROM, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.PAGE_TO, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.YEAR, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.DOI, EvaluationUtils.defaultComparator);
		map.put(EvalInformationType.SECTIONS, EvaluationUtils.swComparator);
		// map.put( SECTION_LEVELS, EvaluationUtils.d
		// relationMap.put(EvalInformationType.SECTION_REFERENCES, new RelationComparators(map.get(EvalInformationType.SECTIONS)));
		map.put(EvalInformationType.REFERENCES, EvaluationUtils.cosineComparator(.6));
		// map.put(EvalInformationType.REFERENCE_AUTHORS, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_TITLE, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_SOURCE, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_LOCATION, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_PUBLISHER, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_EDITOR, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_DOI, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_URL, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_EDITION, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_VOLUME, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_ISSUE, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_NOTE, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_PAGEFROM, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_PAGETO, EvaluationUtils.);
		// map.put(EvalInformationType.REFERENCE_DATE, EvaluationUtils.);

	}

	public static Comparator<String> getComparatorByType(EvalInformationType evalInformationType)
	{
		return map.get(evalInformationType);
	}

	public static RelationComparators getComparatorsByType(EvalInformationType evalInformationType)
	{
		return relationMap.get(evalInformationType);
	}
}
