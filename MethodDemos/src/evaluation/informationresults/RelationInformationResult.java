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
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import config.Config;
import evaluation.tools.EvalInformationType;
import evaluation.tools.EvalInformationTypeComparatorMapping;
import evaluation.tools.RelationComparators;
import utils.FailureUtil;
import utils.StringUtil;

/**
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public class RelationInformationResult extends AbstractSingleInformationResult<Set<RelationInformationResult.StringRelation>>
{
	private Comparator<String> comp2;

	public RelationInformationResult(EvalInformationType type, Set<StringRelation> expected, Set<StringRelation> extracted)
	{
		RelationComparators relationComparators = EvalInformationTypeComparatorMapping.getComparatorsByType(type);

		this.type = type;
		this.comp = relationComparators.getComparator1();
		this.comp2 = relationComparators.getComparator2();
		this.expectedValue = expected;
		this.extractedValue = extracted;
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
			List<StringRelation> tmp = new ArrayList<>(expectedValue);
			external: for(StringRelation partExt : extractedValue)
			{
				for(StringRelation partExp : tmp)
				{
					if(comp.compare(partExt.element1, partExp.element1) == 0 && comp2.compare(partExt.element2, partExp.element2) == 0)
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

	@Override
	public void prettyPrint()
	{
		System.out.println("");
		System.out.println("Expected " + type + ":");
		for(StringRelation expected : expectedValue)
		{
			System.out.println("    " + expected);
		}
		System.out.println("Extracted " + type + ":");
		for(StringRelation extracted : extractedValue)
		{
			System.out.println("    " + extracted);
		}
		System.out.printf("Precision: %4.2f%n", getPrecision());
		System.out.printf("Recall: %4.2f%n", getRecall());
	}

	public static class StringRelation
	{
		private String element1;
		private String element2;

		public StringRelation(String element1, String element2)
		{
			this.element1 = element1;
			this.element2 = element2;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(obj == null)
			{
				return false;
			}
			if(getClass() != obj.getClass())
			{
				return false;
			}
			final StringRelation other = (StringRelation)obj;
			if((this.element1 == null) ? (other.element1 != null) : !this.element1.equals(other.element1))
			{
				return false;
			}
			return (this.element2 == null) ? (other.element2 == null) : this.element2.equals(other.element2);
		}

		@Override
		public int hashCode()
		{
			int hash = 7;
			hash = 67 * hash + (this.element1 != null ? this.element1.hashCode() : 0);
			hash = 67 * hash + (this.element2 != null ? this.element2.hashCode() : 0);
			return hash;
		}

		@Override
		public String toString()
		{
			return element1 + " --- " + element2;
		}
	}

	@Override
	protected boolean checkValueEmpty(Set<StringRelation> value)
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

}