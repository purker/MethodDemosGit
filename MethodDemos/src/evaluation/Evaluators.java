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

package evaluation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import evaluation.tools.EvalInformationType;
import pl.edu.icm.cermine.evaluation.exception.EvaluationException;

public class Evaluators
{
	private static ArrayList<EvalInformationType> types;

	public static void main(String[] args)
	{
		try
		{
			// List<EvaluationMode> modes = Arrays.asList(EvaluationMode.values());

			List<EvaluationMode> modes = new ArrayList<>();
			modes.add(EvaluationMode.CSV_PER_FILE_WITH_EVALUATIONTYPEVALUE);

			// Demos.executeDemos();
			evaluateMethods(modes, getTypes());
			// evaluateMethods(EvaluationMode.CSV, getTypes());
			// evaluateMethods(EvaluationMode.CSV_SUMMARY, getTypes());
		}
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void evaluateMethods(List<EvaluationMode> modes, ArrayList<EvalInformationType> types) throws EvaluationException, IOException
	{
		new CermineXStreamFileEvaluator(types).evaluate(modes);
		new GrobidXStreamFileEvaluator(types).evaluate(modes);
		new ParscitXStreamFileEvaluator(types).evaluate(modes);
		new PdfxXStreamFileEvaluator(types).evaluate(modes);
		System.out.println("Evaluation finished");
	}

	public static ArrayList<EvalInformationType> getTypes()
	{
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
		// types.add(EvalInformationType.REFERENCE_AUTHORS);
		// types.add(EvalInformationType.REFERENCE_TITLE);
		// types.add(EvalInformationType.REFERENCE_SOURCE);
		// types.add(EvalInformationType.REFERENCE_LOCATION);
		// types.add(EvalInformationType.REFERENCE_PUBLISHER);
		// types.add(EvalInformationType.REFERENCE_EDITOR);
		// types.add(EvalInformationType.REFERENCE_DOI);
		// types.add(EvalInformationType.REFERENCE_URL);
		// types.add(EvalInformationType.REFERENCE_EDITION);
		// types.add(EvalInformationType.REFERENCE_VOLUME);
		// types.add(EvalInformationType.REFERENCE_ISSUE);
		// types.add(EvalInformationType.REFERENCE_NOTE);
		// types.add(EvalInformationType.REFERENCE_PAGEFROM);
		// types.add(EvalInformationType.REFERENCE_PAGETO);
		// types.add(EvalInformationType.REFERENCE_DATE);

		// return Lists.newArrayList(EvalInformationType.TITLE, EvalInformationType.ABSTRACT, EvalInformationType.ABSTRACTGERMAN, EvalInformationType.KEYWORDS, EvalInformationType.AUTHORS//
		// EvalInformationType.AFFILIATIONS,
		// // EvalInformationType.AUTHOR_AFFILIATIONS,
		// , EvalInformationType.EMAILS, EvalInformationType.AUTHOR_EMAILS,
		// // EvalInformationType.JOURNAL,
		// // EvalInformationType.VOLUME,
		// // EvalInformationType.ISSUE,
		// // EvalInformationType.PAGES,
		// EvalInformationType.YEAR, // EvalInformationType.DOI,
		// EvalInformationType.REFERENCES);

		return types;
	}

	public static ArrayList<EvalInformationType> getTypes2()
	{
		types = new ArrayList<>();
		types.add(EvalInformationType.AUTHOR_AFFILIATIONS);

		return types;
	}
}
