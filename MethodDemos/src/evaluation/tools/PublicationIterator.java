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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import mapping.result.Publication;
import utils.PublicationUtil;
import utils.XStreamUtil;

/**
 * Adapted from NlmIterator
 */
public class PublicationIterator implements Iterable<PublicationPair>
{
	private List<PublicationPair> entries = new ArrayList<>();

	public PublicationIterator(Publication pub1, Publication pub2)
	{
		entries.add(new PublicationPair(pub1, pub2));
	}

	public PublicationIterator(List<File> originalFiles, List<File> extractedFiles)
	{
		Collections.sort(originalFiles);

		// <id, file>
		Map<String, File> extractedFileMap = extractedFiles.stream().collect(Collectors.toMap(f -> PublicationUtil.getIdFromFile(f), Function.identity()));

		for(File originalFile : originalFiles)
		{
			String pubId = PublicationUtil.getIdFromFile(originalFile);

			File extractedFile = extractedFileMap.get(pubId);

			if(extractedFile != null)
			{
				try
				{
					Publication originalPub = getPublicationFromFile(originalFile);
					Publication extractedPub = getPublicationFromFile(extractedFile);
					entries.add(new PublicationPair(originalFile, extractedFile, originalPub, extractedPub));
				}
				catch(Exception e)
				{
					System.out.println(originalFile);
				}
			}
			else
			{
				throw new IllegalArgumentException("No extracted version of file " + originalFile + " found!");
			}

		}
	}

	public int size()
	{
		return entries.size();
	}

	@Override
	public Iterator<PublicationPair> iterator()
	{
		return entries.iterator();
	}

	protected Publication getPublicationFromFile(File file)
	{
		return XStreamUtil.convertFromXML(file, Publication.class);
	}

}
