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
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import evaluation.tools.EvalInformationType;
import mapping.result.Publication;
import mapping.result.Reference;

public class ReferenceInformationResult extends ListInformationResult
{
	private List<Pair<Reference, Reference>> matchingReferences = new ArrayList<>();
	private List<Pair<Reference, Reference>> notMatchingReferences = new ArrayList<>();
	private List<Reference> expectedReferences;
	private List<Reference> extractedReferences;
	private Publication publication;

	public ReferenceInformationResult(EvalInformationType type, List<Reference> expectedReferences, List<Reference> extractedReferences, Publication publication)
	{
		super(type, expectedReferences, extractedReferences, Reference::toString);

		this.expectedReferences = expectedReferences;
		this.extractedReferences = extractedReferences;
		this.publication = publication;
	}

	@Override
	public int equalExpectedAndExtractedValueCount()
	{
		if(hasExpected() && hasExtracted())
		{
			List<Reference> tmp = new ArrayList<>(expectedReferences);
			external: for(Reference partExt : extractedReferences)
			{
				for(Reference partExp : tmp)
				{
					if(comp.compare(partExp.toString(), partExt.toString()) == 0)
					{
						matchingReferences.add(Pair.of(partExp, partExt));
						tmp.remove(partExp);
						continue external;
					}
					else
					{
						notMatchingReferences.add(Pair.of(partExp, partExt));
					}
				}
			}
		}
		return matchingReferences.size();
	}

	public List<Pair<Reference, Reference>> getMatchingReferences()
	{
		return matchingReferences;
	}

	public List<Pair<Reference, Reference>> getNotMatchingReferences()
	{
		return notMatchingReferences;
	}
}
