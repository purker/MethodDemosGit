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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.rockymadden.stringmetric.similarity.RatcliffObershelpMetric;

import pl.edu.icm.cermine.tools.TextUtils;
import pl.edu.icm.cermine.tools.distance.CosineDistance;
import pl.edu.icm.cermine.tools.distance.SmithWatermanDistance;
import scala.Option;

/**
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 */
public class EvaluationUtils
{
	public static final double minRatcliffObershelpSimilarity = 0.95;

	public static double compareStringsSW(String expectedText, String extractedText)
	{
		List<String> expectedTokens = TextUtils.tokenize(expectedText.trim());
		List<String> extractedTokens = TextUtils.tokenize(extractedText.trim());
		SmithWatermanDistance distanceFunc = new SmithWatermanDistance(.0, .0);
		double distance = distanceFunc.compare(expectedTokens, extractedTokens);
		return 2 * distance / (expectedTokens.size() + extractedTokens.size());
	}

	public static List<String> removeLeadingZerosFromDate(List<String> strings)
	{
		List<String> ret = new ArrayList<>();
		for(String string : strings)
		{
			String[] parts = string.split("\\s");
			if(parts.length > 1)
			{
				List<String> newDate = new ArrayList<>();
				for(String part : parts)
				{
					newDate.add(part.replaceFirst("^0+(?!$)", ""));
				}
				ret.add(StringUtils.join(newDate, " "));
			}
			else
			{
				ret.add(string);
			}
		}
		return ret;
	}

	public static boolean isSubsequence(String str, String sub)
	{
		if(sub.isEmpty())
		{
			return true;
		}
		if(str.isEmpty())
		{
			return false;
		}
		if(str.charAt(0) == sub.charAt(0))
		{
			return isSubsequence(str.substring(1), sub.substring(1));
		}
		return isSubsequence(str.substring(1), sub);
	}

	public static final Comparator<String> defaultComparator = new Comparator<String>()
	{
		@Override
		public int compare(String t1, String t2)
		{
			return t1.trim().replaceAll(" +", " ").compareToIgnoreCase(t2.trim().replaceAll(" +", " "));
		}
	};

	public static Comparator<String> cosineComparator()
	{
		return cosineComparator(0.7);
	}

	public static Comparator<String> cosineComparator(final double threshold)
	{
		return new Comparator<String>()
		{

			@Override
			public int compare(String t1, String t2)
			{
				if(new CosineDistance().compare(TextUtils.tokenize(t1), TextUtils.tokenize(t2)) > threshold)
				{
					return 0;
				}
				return t1.compareToIgnoreCase(t2);
			}
		};
	}

	public static final Comparator<String> swComparator = new Comparator<String>()
	{
		@Override
		public int compare(String t1, String t2)
		{
			String t1Norm = t1.replaceAll("[^a-zA-Z]", "");
			String t2Norm = t2.replaceAll("[^a-zA-Z]", "");
			if(compareStringsSW(t1, t2) >= .9 || (!t1Norm.isEmpty() && t1Norm.equals(t2Norm)))
			{
				return 0;
			}
			return t1.compareToIgnoreCase(t2);
		}
	};

	public static final Comparator<String> authorComparator = new Comparator<String>()
	{
		@Override
		public int compare(String t1, String t2)
		{
			if(authorReplace(t1).equals(authorReplace(t2)))
			{
				return 0;
			}
			return t1.trim().compareToIgnoreCase(t2.trim());
		}
	};

	public static String authorReplace(String s)
	{
		return s.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z]", "");
	}

	public static final Comparator<String> emailComparator = new Comparator<String>()
	{

		@Override
		public int compare(String t1, String t2)
		{
			if(t1 == null)
			{
				return (t2 == null) ? 0 : 1;
			}
			else
			{
				if(t2 == null) return 1;

				String t1Norm = t1.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z0-9@]", "").replaceFirst("^e.?mail:? *", "");
				String t2Norm = t2.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z0-9@]", "").replaceFirst("^e.?mail:? *", "");

				if(t1Norm.equals(t2Norm))
				{
					return 0;
				}
				return t1.trim().compareToIgnoreCase(t2.trim());
			}
		}
	};

	public static final Comparator<String> journalComparator = new Comparator<String>()
	{

		@Override
		public int compare(String t1, String t2)
		{
			if(EvaluationUtils.isSubsequence(t1.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z]", ""), t2.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z]", "")))
			{
				return 0;
			}
			return t1.trim().compareToIgnoreCase(t2.trim());
		}
	};

	/**
	 * Adapted from {@link org.grobid.core.utilities.TextUtilities#getLevenshteinDistance(String, String)}
	 * 
	 * Levenshtein distance between two strings
	 *
	 * @param expectedText
	 *            the first string to be compared.
	 * @param textractedText
	 *            the second string to be compared.
	 * @return Returns the Levenshtein distance.
	 */
	public static final Comparator<String> levenshteinComparator = new Comparator<String>()
	{

		@Override
		public int compare(String expectedText, String extractedText)
		{
			Double similarity = 0.;
			if((expectedText.length() > 0) && (extractedText.length() > 0))
			{
				Option<Object> similarityObject = RatcliffObershelpMetric.compare(expectedText, extractedText);
				if((similarityObject != null) && (similarityObject.get() != null)) similarity = (Double)similarityObject.get();
			}

			System.err.println(similarity);
			if(similarity >= minRatcliffObershelpSimilarity)
			{
				return 0;
			}
			else
				return -1;
		}
	};

	public static Comparator<String> headerComparator(final Comparator<String> comp)
	{
		return new Comparator<String>()
		{

			@Override
			public int compare(String t1, String t2)
			{
				List<String> t1Lines = Lists.newArrayList(t1.split("\n"));
				List<String> t2Lines = Lists.newArrayList(t2.split("\n"));
				if(t1Lines.size() != t2Lines.size())
				{
					return -1;
				}
				for(int i = 0; i < t1Lines.size(); i++)
				{
					if(t1Lines.get(i).equals(t2Lines.get(i)))
					{
						continue;
					}
					String trimmed1 = t1Lines.get(i).trim();
					String trimmed2 = t2Lines.get(i).trim();
					if(t1Lines.get(i).length() - trimmed1.length() != t2Lines.get(i).length() - trimmed2.length())
					{
						return -1;
					}
					if(comp.compare(trimmed1, trimmed2) != 0)
					{
						return -1;
					}
				}
				return 0;
			}
		};
	}

	public static final Comparator<String> exactComparator = new Comparator<String>()
	{
		@Override
		public int compare(String expectedText, String extractedText)
		{
			return Objects.equals(expectedText, extractedText) ? 0 : 1;
		}
	};

}
