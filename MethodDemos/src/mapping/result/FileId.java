package mapping.result;

import java.io.File;

public class FileId implements KeyStringInterface
{
	private Publication publication;
	private File file;

	public FileId(Publication publication, File file)
	{
		this.publication = publication;
		this.file = file;
	}

	@Override
	public String getKeyString()
	{
		return file.getPath();
	}

	@Override
	public Integer getPublicationId()
	{
		return publication.getId().getId();
	}
}