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

import mapping.result.Label;

/**
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public enum EvalInformationType implements Label
{
	TITLE("Title"),
	PUBLICATIONTYPE("Publicationtype"),
	ABSTRACT("Abstract"),
	ABSTRACTGERMAN("Abstract (german)"),
	KEYWORDS("Keywords"),
	AUTHORS("Authors"),
	EMAILS("Emails"),
	AUTHOR_EMAILS("Author-Emails"),
	AFFILIATIONS("Affiliations"),
	AUTHOR_AFFILIATIONS("Author-Affiliations"),
	SOURCE("Source"),
	VOLUME("Volume"),
	ISSUE("Issue"),
	PAGES("Pages"),
	PAGE_FROM(PAGES, "Page from"),
	PAGE_TO(PAGES, "Page to"),
	LOCATION("Location"),
	DATE("Date"),
	DOI("Doi"),
	SECTIONS("Sections"),
	SECTION_LEVELS("Section-Levels"),
	SECTION_REFERENCES("Section-References"),
	REFERENCES("References"),
	REFERENCE_ID(REFERENCES, "Id"),
	REFERENCE_MARKER(REFERENCES, "Marker"),
	REFERENCE_TITLE(REFERENCES, "Title"),
	REFERENCE_PUBLICATIONTYPE(REFERENCES, "PublicationType"),
	REFERENCE_SOURCE(REFERENCES, "Source"),
	REFERENCE_PUBLISHER(REFERENCES, "Publisher"),
	REFERENCE_EDITOR(REFERENCES, "Editor"),
	REFERENCE_AUTHORS(REFERENCES, "Authors"),
	REFERENCE_EDITION(REFERENCES, "Edition"),
	REFERENCE_VOLUME(REFERENCES, "Volume"),
	REFERENCE_ISSUE(REFERENCES, "Issue"),
	REFERENCE_CHAPTER(REFERENCES, "Chapter"),
	REFERENCE_NOTE(REFERENCES, "Note"),
	REFERENCE_PAGES(REFERENCES, "Pages"),
	REFERENCE_PAGEFROM(REFERENCE_PAGES, "Page from"),
	REFERENCE_PAGETO(REFERENCE_PAGES, "Page to"),
	REFERENCE_LOCATION(REFERENCES, "Location"),
	REFERENCE_DATE(REFERENCES, "Date"),
	REFERENCE_DOI(REFERENCES, "Doi"),
	REFERENCE_URL(REFERENCES, "Url");

	private String label;
	private EvalInformationType mainType;

	private EvalInformationType(String label)
	{
		this.label = label;
	}

	private EvalInformationType(EvalInformationType mainType, String label)
	{
		this.label = label;
		this.mainType = mainType;
	}

	@Override
	public String getLabel()
	{
		return label;
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
		types.add(EvalInformationType.PUBLICATIONTYPE);
		types.add(EvalInformationType.ABSTRACT);
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
		types.add(EvalInformationType.LOCATION);
		types.add(EvalInformationType.DATE);
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
		types.add(EvalInformationType.REFERENCE_ID);
		types.add(EvalInformationType.REFERENCE_MARKER);
		types.add(EvalInformationType.REFERENCE_TITLE);
		// TODO ?types.add(EvalInformationType.REFERENCE_PUBLICATIONTYPE);
		types.add(EvalInformationType.REFERENCE_SOURCE);
		types.add(EvalInformationType.REFERENCE_PUBLISHER);
		types.add(EvalInformationType.REFERENCE_EDITOR);
		types.add(EvalInformationType.REFERENCE_AUTHORS);
		types.add(EvalInformationType.REFERENCE_EDITION);
		types.add(EvalInformationType.REFERENCE_VOLUME);
		types.add(EvalInformationType.REFERENCE_ISSUE);
		types.add(EvalInformationType.REFERENCE_CHAPTER);
		types.add(EvalInformationType.REFERENCE_NOTE);
		// TODO types.add(EvalInformationType.REFERENCE_PAGES);
		types.add(EvalInformationType.REFERENCE_PAGEFROM);
		types.add(EvalInformationType.REFERENCE_PAGETO);
		types.add(EvalInformationType.REFERENCE_LOCATION);
		types.add(EvalInformationType.REFERENCE_DATE);
		types.add(EvalInformationType.REFERENCE_DOI);
		types.add(EvalInformationType.REFERENCE_URL);

		// TODO error message when not implemented
		return types;
	}

}
