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

package evaluation.informationresults;

import java.util.Comparator;

import evaluation.tools.EvalInformationType;
import evaluation.tools.EvalInformationTypeComparatorMapping;

/**
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public class SimpleInformationResult extends AbstractSingleInformationDocResult<String>
{
	private Comparator<String> comp;

	public SimpleInformationResult(EvalInformationType type, String expectedValue, String extractedValue)
	{
		this.expectedValue = expectedValue;
		this.extractedValue = extractedValue;
		this.correct = null;
		this.type = type;
		this.comp = EvalInformationTypeComparatorMapping.getComparatorByType(type);
	}

	@Override
	public void prettyPrint()
	{
		System.out.println("Expected " + type + ": " + expectedValue);
		System.out.println("Extracted " + type + ": " + extractedValue);
		System.out.println("Correct: " + (getCorrect() ? "yes" : "no"));
	}

	@Override
	protected boolean checkValueEmpty(String value)
	{
		return value.isEmpty();
	}

	@Override
	public String getExpectedAsString()
	{
		return getExpected();
	}

	@Override
	public String getExtractedAsString()
	{
		return getExtracted();
	}

	@Override
	public void evaluate()
	{
		int correctCount = 0;
		if(hasExpected() && hasExtracted())
		{
			correctCount = equalExpectedAndExtractedValueCount();
		}
		correct = (correctCount == 1);

		if(hasExtracted()) precision = getCorrect() ? 1. : 0.;
		if(hasExpected()) recall = getCorrect() ? 1. : 0.;
	}

	public int equalExpectedAndExtractedValueCount()
	{
		if(comp.compare(expectedValue, extractedValue) == 0)
			return 1;
		else
			return 0;
	}

}
