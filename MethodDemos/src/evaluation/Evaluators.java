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
import java.util.Arrays;
import java.util.List;

import evaluation.tools.EvalInformationType;
import pl.edu.icm.cermine.evaluation.exception.EvaluationException;

public class Evaluators
{

	public static void main(String[] args)
	{
		try
		{
			// List<EvaluationMode> allModes = Arrays.asList(EvaluationMode.values());
			//
			// List<EvaluationMode> modes = new ArrayList<>();
			// modes.add(EvaluationMode.CSV_PER_FILE_WITH_EVALUATIONTYPEVALUE);

			// Demos.executeDemos();
			// evaluateMethods(modes, getTypes(), getReferenceTypes());
			evaluateMethods(Arrays.asList(EvaluationMode.CSV_PER_FILE), EvalInformationType.getTypesForPublications(), EvalInformationType.getTypesForReferences());
			// evaluateMethods(EvaluationMode.CSV, getTypes());
			// evaluateMethods(EvaluationMode.CSV_SUMMARY, getTypes());
			// evaluateMethods(Arrays.asList(EvaluationMode.CSV_PER_FILE_WITH_EVALUATIONTYPEVALUE), Arrays.asList(EvalInformationType.AUTHORS));
		}
		catch(Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void evaluateMethods(List<EvaluationMode> modes, List<EvalInformationType> types, List<EvalInformationType> referenceTypes) throws EvaluationException, IOException
	{
		SystemEvaluator cermine = new CermineXStreamFileEvaluator(types, referenceTypes, modes);
		SystemEvaluator grobid = new GrobidXStreamFileEvaluator(types, referenceTypes, modes);
		SystemEvaluator parscit = new ParscitXStreamFileEvaluator(types, referenceTypes, modes);
		SystemEvaluator pdfx = new PdfxXStreamFileEvaluator(types, referenceTypes, modes);

		cermine.evaluate();
		grobid.evaluate();
		parscit.evaluate();
		pdfx.evaluate();

		SystemEvaluator.printOverallStatistics(modes, cermine, grobid, parscit, pdfx);
		System.out.println("Evaluation finished");
	}

}
