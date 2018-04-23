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

/**
 * @author Dominika Tkaczyk (d.tkaczyk@icm.edu.pl)
 * @param <T>
 *            result type
 */
public interface SingleInformationDocResult<T>
{
	void evaluate();

	EvalInformationType getType();

	boolean hasExpected();

	boolean hasExtracted();

	T getExpected();

	T getExtracted();

	String getExpectedAsString();

	String getExtractedAsString();

	void setExpected(T expected);

	void setExtracted(T extracted);

	Double getPrecision();

	Double getRecall();

	Double getF1();

	Boolean getCorrect();

	void prettyPrint();

	String getPrecisionAsString() throws IOException;

	String getPrecisionX100AsString() throws IOException;

	String getRecallAsString() throws IOException;

	String getRecallX100AsString() throws IOException;

	String getF1X100AsString() throws IOException;

	String getF1AsString() throws IOException;

}
