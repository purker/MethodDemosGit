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

import java.io.IOException;

import utils.FormatingUtil;

/**
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 * @param <T>
 *            result type
 */
public abstract class AbstractSingleInformationDocResult<T> implements SingleInformationDocResult<T>
{
	protected EvalInformationType type;

	protected T expectedValue;
	protected T extractedValue;

	protected Double precision;
	protected Double recall;

	protected abstract boolean checkValueEmpty(T value);

	@Override
	public boolean hasExpected()
	{
		return expectedValue != null && !checkValueEmpty(expectedValue);
	}

	@Override
	public boolean hasExtracted()
	{
		return extractedValue != null && !checkValueEmpty(extractedValue);
	}

	@Override
	public String getPrecisionAsString() throws IOException
	{
		if(!hasExtracted()) return "none extracted value";
		return FormatingUtil.roundAndFormat(getPrecision());
	}

	@Override
	public String getPrecisionX100AsString() throws IOException
	{
		if(!hasExtracted()) return "none extracted value";
		return FormatingUtil.roundAndFormatX100(getPrecision());
	}

	@Override
	public String getRecallAsString() throws IOException
	{
		if(!hasExpected()) return "none expected";
		return FormatingUtil.roundAndFormat(getRecall());
	}

	@Override
	public String getRecallX100AsString() throws IOException
	{
		if(!hasExpected()) return "none expected";
		return FormatingUtil.roundAndFormatX100(getRecall());
	}

	@Override
	public String getF1X100AsString() throws IOException
	{
		if(!hasExtracted() && !hasExpected())
		{
			return "NA";
		}
		else
		{
			if(!hasExtracted() || !hasExpected())
			{
				return "0";
			}
			else
			{
				return FormatingUtil.roundAndFormatX100(getF1());
			}
		}
	}

	@Override
	public String getF1AsString() throws IOException
	{
		if(!hasExtracted() && !hasExpected())
		{
			return "NA";
		}
		else
		{
			if(!hasExtracted() || !hasExpected())
			{
				return "0";
			}
			else
			{
				return FormatingUtil.roundAndFormat(getF1());
			}
		}
	}

	@Override
	public Double getF1()
	{
		if(getPrecision() == null && getRecall() == null)
		{
			return null;
		}
		if(getPrecision() == null || getRecall() == null || getPrecision() + getRecall() == 0)
		{
			return 0.;
		}
		if(getPrecision() == Double.NaN || getRecall() == Double.NaN)
		{
			return Double.NaN;
		}
		return 2 * getPrecision() * getRecall() / (getPrecision() + getRecall());
	}

	@Override
	public EvalInformationType getType()
	{
		return type;
	}

	@Override
	public T getExpected()
	{
		return expectedValue;
	}

	@Override
	public void setExpected(T expectedValue)
	{
		this.expectedValue = expectedValue;
	}

	@Override
	public T getExtracted()
	{
		return extractedValue;
	}

	@Override
	public void setExtracted(T extractedValue)
	{
		this.extractedValue = extractedValue;
	}
}
