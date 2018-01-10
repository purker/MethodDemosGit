/**
 * This Publication is part of CERMINE project.
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

import mapping.result.Publication;

/**
 * Adapted from pl.edu.icm.cermine.evaluation.tools.NlmPair
 */
public class PublicationPair
{
	private File originalFile;
	private File extractedFile;

	private Publication originalPub;
	private Publication extractedPub;

	public PublicationPair(Publication originalPub, Publication extractedPub)
	{
		this.originalPub = originalPub;
		this.extractedPub = extractedPub;
	}

	public PublicationPair(File originalFile, File extractedFile, Publication originalPub, Publication extractedPub)
	{
		this.originalFile = originalFile;
		this.extractedFile = extractedFile;
		this.originalPub = originalPub;
		this.extractedPub = extractedPub;
	}

	public Publication getOriginalPub()
	{
		return originalPub;
	}

	public void setOriginalPub(Publication originalPub)
	{
		this.originalPub = originalPub;
	}

	public Publication getExtractedPub()
	{
		return extractedPub;
	}

	public void setExtractedPub(Publication extractedPub)
	{
		this.extractedPub = extractedPub;
	}

	public File getOriginalFile()
	{
		return originalFile;
	}

	public void setOriginalFile(File originalFile)
	{
		this.originalFile = originalFile;
	}

	public File getExtractedFile()
	{
		return extractedFile;
	}

	public void setExtractedFile(File extractedFile)
	{
		this.extractedFile = extractedFile;
	}

}
