package mapping.result;

public class Id
{
	private Integer id;

	public Id()
	{
		// for JAXB: The class mapping.result.Id requires a zero argument constructor or a specified factory method.
	}

	public Id(Integer id)
	{
		this.id = id;
	}

	public Id(String id)
	{
		this.id = new Integer(id);
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

}
