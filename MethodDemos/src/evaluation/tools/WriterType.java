package evaluation.tools;

public enum WriterType
{
	CSV("csv"), EXCEL("xls");

	private String fileExtension;

	private WriterType(String fileExtension)
	{
		this.fileExtension = fileExtension;
	}

	public String getFileExtension()
	{
		return this.fileExtension;
	}

	public String replace(String file)
	{
		return file.replace("<fileext>", fileExtension);
	}
}
