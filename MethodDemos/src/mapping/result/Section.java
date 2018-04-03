package mapping.result;

import java.util.ArrayList;
import java.util.List;

public class Section extends BaseEntity
{
	private static final long serialVersionUID = 9061069036137135298L;

	private String id;
	private String level;
	private Integer layer;
	private String title;
	private String type;
	private SectionType typeEnum;
	private List<String> referenceIds = new ArrayList<>();
	private List<ReferenceCitation> referenceCitations = new ArrayList<>();
	// private List<Reference> references = new ArrayList<>();

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public Integer getLayer()
	{
		return layer;
	}

	public void setLayer(Integer layer)
	{
		this.layer = layer;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public SectionType getTypeEnum()
	{
		return typeEnum;
	}

	public void setTypeEnum(SectionType typeEnum)
	{
		this.typeEnum = typeEnum;
	}

	public List<String> getReferenceIds()
	{
		return referenceIds;
	}

	public void setReferenceIds(List<String> referenceIds)
	{
		this.referenceIds = referenceIds;
	}

	public List<ReferenceCitation> getReferenceCitations()
	{
		return referenceCitations;
	}

	public void setReferenceCitations(List<ReferenceCitation> referenceCitations)
	{
		this.referenceCitations = referenceCitations;
	}

	// public List<Reference> getReferences()
	// {
	// return references;
	// }
	//
	// public void setReferences(List<Reference> references)
	// {
	// this.references = references;
	// }

}
