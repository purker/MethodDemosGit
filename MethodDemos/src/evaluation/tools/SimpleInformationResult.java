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

import java.util.Comparator;

/**
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public class SimpleInformationResult extends AbstractSingleInformationDocResult<String>
{
	private Boolean correct;
	private Comparator<String> comp;

	public SimpleInformationResult(EvalInformationType type, String expectedValue, String extractedValue)
	{
		this.expectedValue = expectedValue;
		this.extractedValue = extractedValue;
		this.correct = null;
		this.type = type;
		this.comp = EvalInformationTyeComparatorMapping.getComparatorByType(type);
	}

	@Override
	public boolean isCorrect()
	{
		if(correct != null)
		{
			return correct;
		}
		correct = hasExpected() && hasExtracted() && comp.compare(expectedValue, extractedValue) == 0;
		return correct;
	}

	@Override
	public Double getPrecision()
	{
		if(!hasExtracted())
		{
			return null;
		}
		return isCorrect() ? 1. : 0.;
	}

	@Override
	public Double getRecall()
	{
		if(!hasExpected())
		{
			return null;
		}
		return isCorrect() ? 1. : 0.;
	}

	@Override
	public void prettyPrint()
	{
		System.out.println("Expected " + type + ": " + expectedValue);
		System.out.println("Extracted " + type + ": " + extractedValue);
		System.out.println("Correct: " + (isCorrect() ? "yes" : "no"));
	}

	@Override
	protected boolean checkValueEmpty(String value)
	{
		return value.isEmpty();
	}

}
