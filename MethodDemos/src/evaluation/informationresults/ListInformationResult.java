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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import config.Config;
import evaluation.tools.EvalInformationType;
import evaluation.tools.EvalInformationTypeComparatorMapping;
import utils.CollectionUtil;
import utils.StringUtil;

public class ListInformationResult extends AbstractSingleInformationResult<List<String>>
{
	public ListInformationResult(EvalInformationType type)
	{
		this.type = type;
		this.comp = EvalInformationTypeComparatorMapping.getComparatorByType(type);
	}

	/**
	 * @param type
	 * @param origPub
	 * @param testPub
	 * @param toListMethod
	 * @param toString
	 */
	public <B, T> ListInformationResult(EvalInformationType type, B origPub, B testPub, Function<B, List<T>> toListMethod, Function<T, String> toString)
	{
		this(type, toListMethod.apply(origPub), toListMethod.apply(testPub), toString, null);
	}

	public <B, T> ListInformationResult(EvalInformationType type, B origPub, B testPub,
			Function<B, List<T>> toListMethod, Function<T, String> toString, Predicate<T> filter) {
		this(type, toListMethod.apply(origPub), toListMethod.apply(testPub), toString, filter);
	}

	public <B, T> ListInformationResult(EvalInformationType type, List<T> origList, List<T> testList,
			Function<T, String> toString, Predicate<T> filter)
	{
		this(type);

		List<String> orig = new ArrayList<>();
		for (T obj1 : CollectionUtil.emptyIfNull(origList, filter))
		{
			String s = toString.apply(obj1);
			if(StringUtil.isNotEmpty(s))
			{
				orig.add(s);
			}
		}
		List<String> test = new ArrayList<>();
		for (T obj2 : CollectionUtil.emptyIfNull(testList, filter))
		{
			String s = toString.apply(obj2);
			if(StringUtil.isNotEmpty(s))
			{
				test.add(s);
			}
		}

		this.expectedValue = orig;
		this.extractedValue = test;
	}

	// TODO delete
	// public void print(int mode, String name)
	// {
	// if(mode == 0)
	// {
	// System.out.println("");
	// System.out.println("Expected " + name + ":");
	// for(String expected : expectedValue)
	// {
	// System.out.println(" " + expected);
	// }
	// System.out.println("Extracted " + name + ":");
	// for(String extracted : extractedValue)
	// {
	// System.out.println(" " + extracted);
	// }
	// System.out.printf("Precision: %4.2f%n", getPrecision());
	// System.out.printf("Recall: %4.2f%n", getRecall());
	// }
	// if(mode == 1)
	// {
	// if(!hasExtracted() && !hasExpected())
	// {
	// System.out.print("null");
	// }
	// else
	// if(!hasExtracted() || !hasExpected())
	// {
	// System.out.print("0");
	// }
	// else
	// {
	// System.out.print(getF1());
	// }
	// System.out.print(",");
	// }
	// }

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

		if(hasExpected()) recall = new BigDecimal(correctCount).divide(new BigDecimal(expectedValue.size()), Config.bigDecimalScale, Config.bigDecimalRoundingMode);
		if(hasExtracted()) precision = new BigDecimal(correctCount).divide(new BigDecimal(extractedValue.size()), Config.bigDecimalScale, Config.bigDecimalRoundingMode);
		correct = hasExpected() && hasExtracted() && new BigDecimal(1).compareTo(getF1()) == 0;
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
