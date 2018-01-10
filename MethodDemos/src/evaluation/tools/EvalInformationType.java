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
	REFERENCE_AUTHORS(REFERENCES),
	REFERENCE_TITLE(REFERENCES),
	REFERENCE_SOURCE(REFERENCES),
	REFERENCE_LOCATION(REFERENCES),
	REFERENCE_PUBLISHER(REFERENCES),
	REFERENCE_EDITOR(REFERENCES),
	REFERENCE_DOI(REFERENCES),
	REFERENCE_URL(REFERENCES),
	REFERENCE_EDITION(REFERENCES),
	REFERENCE_VOLUME(REFERENCES),
	REFERENCE_ISSUE(REFERENCES),
	REFERENCE_NOTE(REFERENCES),
	REFERENCE_PAGES(REFERENCES),
	REFERENCE_PAGEFROM(PAGES),
	REFERENCE_PAGETO(PAGES),
	REFERENCE_DATE(REFERENCES);

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

}
