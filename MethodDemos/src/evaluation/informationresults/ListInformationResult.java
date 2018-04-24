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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import evaluation.tools.EvalInformationType;
import evaluation.tools.EvalInformationTypeComparatorMapping;
import evaluation.tools.EvaluationUtils;
import utils.StringUtil;

public class ListInformationResult extends AbstractSingleInformationDocResult<List<String>>
{
	private Comparator<String> comp = EvaluationUtils.defaultComparator;

	public ListInformationResult(EvalInformationType type, List<String> expectedValue, List<String> extractedValue)
	{
		this.expectedValue = expectedValue;
		this.extractedValue = extractedValue;
		this.type = type;
		this.comp = EvalInformationTypeComparatorMapping.getComparatorByType(type);
	}

	public void print(int mode, String name)
	{
		if(mode == 0)
		{
			System.out.println("");
			System.out.println("Expected " + name + ":");
			for(String expected : expectedValue)
			{
				System.out.println("    " + expected);
			}
			System.out.println("Extracted " + name + ":");
			for(String extracted : extractedValue)
			{
				System.out.println("    " + extracted);
			}
			System.out.printf("Precision: %4.2f%n", getPrecision());
			System.out.printf("Recall: %4.2f%n", getRecall());
		}
		if(mode == 1)
		{
			if(!hasExtracted() && !hasExpected())
			{
				System.out.print("null");
			}
			else
				if(!hasExtracted() || !hasExpected())
				{
					System.out.print("0");
				}
				else
				{
					System.out.print(getF1());
				}
			System.out.print(",");
		}
	}

	@Override
	public void prettyPrint()
	{
		System.out.println("Expected " + type + ":");
		for(String expected : expectedValue)
		{
			System.out.println("    " + expected);
		}
		System.out.println("Extracted " + type + ":");
		for(String extracted : extractedValue)
		{
			System.out.println("    " + extracted);
		}
		System.out.printf("Precision: %4.2f%n", getPrecision());
		System.out.printf("Recall: %4.2f%n", getRecall());
	}

	@Override
	protected boolean checkValueEmpty(List<String> value)
	{
		return value.isEmpty();
	}

	@Override
	public String getExpectedAsString()
	{
		return StringUtil.notNullJoinedList(getExpected(), "\n");
	}

	@Override
	public String getExtractedAsString()
	{
		return StringUtil.notNullJoinedList(getExtracted(), "\n");
	}

	@Override
	public void evaluate()
	{
		int correctCount = equalExpectedAndExtractedValueCount();

		if(hasExpected()) recall = (double)correctCount / expectedValue.size();
		if(hasExtracted()) precision = (double)correctCount / extractedValue.size();
		correct = hasExpected() && hasExtracted() && new Double(1).equals(getF1());
	}

	public int equalExpectedAndExtractedValueCount()
	{
		int correctCount = 0;

		if(hasExpected() && hasExtracted())
		{
			List<String> tmp = new ArrayList<>(expectedValue);
			external: for(String partExt : extractedValue)
			{
				for(String partExp : tmp)
				{
					if(comp.compare(partExp, partExt) == 0)
					{
						++correctCount;
						tmp.remove(partExp);
						continue external;
					}
				}
			}
		}
		return correctCount;
	}
}
