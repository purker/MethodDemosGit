/**
 * This file is part of CERMINE project.
 * Copyright (c) 2011-2016 ICM-UW
 *
 * CERMINE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CERMINE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with CERMINE. If not, see <http://www.gnu.org/licenses/>.
 */

package evaluation.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public enum EvalInformationType
{
	TITLE,
	ABSTRACT,
	ABSTRACTGERMAN,
	KEYWORDS,
	AUTHORS,
	EMAILS,
	AUTHOR_EMAILS,
	AFFILIATIONS,
	AUTHOR_AFFILIATIONS,
	SOURCE,
	VOLUME,
	ISSUE,
	PAGES,
	PAGE_FROM(PAGES),
	PAGE_TO(PAGES),
	YEAR,
	DOI,
	SECTIONS,
	SECTION_LEVELS,
	SECTION_REFERENCES,
	REFERENCES,
	REFERENCE_MARKER(REFERENCES),
	REFERENCE_TITLE(REFERENCES),
	REFERENCE_PUBLICATIONTYPE(REFERENCES),
	REFERENCE_SOURCE(REFERENCES),
	REFERENCE_PUBLISHER(REFERENCES),
	REFERENCE_EDITOR(REFERENCES),
	REFERENCE_AUTHORS(REFERENCES),
	REFERENCE_EDITION(REFERENCES),
	REFERENCE_LOCATION(REFERENCES),
	REFERENCE_VOLUME(REFERENCES),
	REFERENCE_ISSUE(REFERENCES),
	REFERENCE_CHAPTER(REFERENCES),
	REFERENCE_NOTE(REFERENCES),
	REFERENCE_PAGES(REFERENCES),
	REFERENCE_PAGEFROM(PAGES),
	REFERENCE_PAGETO(PAGES),
	REFERENCE_DATE(REFERENCES),
	REFERENCE_DOI(REFERENCES),
	REFERENCE_URL(REFERENCES);

	private EvalInformationType mainType;

	private EvalInformationType()
	{}

	private EvalInformationType(EvalInformationType mainType)
	{
		this.mainType = mainType;
	}

	public static List<EvalInformationType> getSubTypes(EvalInformationType mainType, List<EvalInformationType> list)
	{
		List<EvalInformationType> subTypes = new ArrayList<>();
		for(EvalInformationType type : list)
		{
			if(type.mainType == mainType)
			{
				subTypes.add(type);
			}
		}
		return subTypes;

	}

	public String replace(String file)
	{
		return file.replace("<evaltype>", this.name());
	}

	public static List<EvalInformationType> getTypesForPublications()
	{
		List<EvalInformationType> types;
		types = new ArrayList<>();
		types.add(EvalInformationType.TITLE);
		types.add(EvalInformationType.ABSTRACT);
		types.add(EvalInformationType.ABSTRACTGERMAN);
		types.add(EvalInformationType.KEYWORDS);
		types.add(EvalInformationType.AUTHORS);
		types.add(EvalInformationType.EMAILS);
		types.add(EvalInformationType.AUTHOR_EMAILS);
		types.add(EvalInformationType.AFFILIATIONS);
		types.add(EvalInformationType.AUTHOR_AFFILIATIONS);
		types.add(EvalInformationType.SOURCE);
		types.add(EvalInformationType.VOLUME);
		types.add(EvalInformationType.ISSUE);
		types.add(EvalInformationType.PAGE_FROM);
		types.add(EvalInformationType.PAGE_TO);
		types.add(EvalInformationType.YEAR);
		types.add(EvalInformationType.DOI);
		types.add(EvalInformationType.SECTIONS);
		types.add(EvalInformationType.SECTION_LEVELS);
		types.add(EvalInformationType.SECTION_REFERENCES);
		types.add(EvalInformationType.REFERENCES);

		return types;
	}

	public static List<EvalInformationType> getTypesForReferences()
	{
		List<EvalInformationType> types = new ArrayList<>();
		types.add(EvalInformationType.REFERENCE_MARKER);
		types.add(EvalInformationType.REFERENCE_TITLE);
		// TODO ?types.add(EvalInformationType.REFERENCE_PUBLICATIONTYPE);
		types.add(EvalInformationType.REFERENCE_SOURCE);
		types.add(EvalInformationType.REFERENCE_PUBLISHER);
		types.add(EvalInformationType.REFERENCE_EDITOR);
		types.add(EvalInformationType.REFERENCE_AUTHORS);
		types.add(EvalInformationType.REFERENCE_EDITION);
		types.add(EvalInformationType.REFERENCE_LOCATION);
		types.add(EvalInformationType.REFERENCE_VOLUME);
		types.add(EvalInformationType.REFERENCE_ISSUE);
		types.add(EvalInformationType.REFERENCE_CHAPTER);
		types.add(EvalInformationType.REFERENCE_NOTE);
		// TODOtypes.add(EvalInformationType.REFERENCE_PAGES);
		types.add(EvalInformationType.REFERENCE_PAGEFROM);
		types.add(EvalInformationType.REFERENCE_PAGETO);
		types.add(EvalInformationType.REFERENCE_DATE);
		types.add(EvalInformationType.REFERENCE_DOI);
		types.add(EvalInformationType.REFERENCE_URL);

		// TODO error message when not implemented
		return types;
	}

}
