package evaluation.tools;

public enum CollectionEnum
{
	PUBLICATION("publication"), REFERENCE("reference");

	private static String toReplace = "<type>";
	private String name;

	private CollectionEnum(String replaceString)
	{
		this.name = replaceString;
	}

	public String getReplaceString()
	{
		return name;
	}

	public void setReplaceString(String replaceString)
	{
		this.name = replaceString;
	}

	public String replace(String file)
	{
		// TODO delete if(this.equals(PUBLICATION)) return file.replace(toReplace, "");
		return file.replace(toReplace, name);
	}

}
