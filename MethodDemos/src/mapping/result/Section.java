package mapping.result;

import java.util.List;

public class Section
{
	private String id;
	private String title;
	private List<String> referenceIds;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	private List<Section> subsections;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public List<Section> getSubsections()
	{
		return subsections;
	}

	public void setSubsections(List<Section> subsections)
	{
		this.subsections = subsections;
	}

	public List<String> getReferenceIds()
	{
		return referenceIds;
	}

	public void setReferenceIds(List<String> referenceIds)
	{
		this.referenceIds = referenceIds;
	}

}
