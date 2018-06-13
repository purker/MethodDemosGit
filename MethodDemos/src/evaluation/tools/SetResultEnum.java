package evaluation.tools;

public enum SetResultEnum
{
	PUBLICATION("publication"), REFERENCES("reference");

	private static String toReplace = "<type>";
	private String name;

	private SetResultEnum(String replaceString)
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
