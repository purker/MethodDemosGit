package mapping.result;

public class Author extends BaseEntity
{
	private String name; // if null, information in first and lastname
	private String firstName;
	private String lastName;

	private String email;

	private String type;
	private String affiliationId; // if null, information in affiliation
	private Affiliation affiliation;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getAffiliationId()
	{
		return affiliationId;
	}

	public void setAffiliationId(String affiliationId)
	{
		this.affiliationId = affiliationId;
	}

	public Affiliation getAffiliation()
	{
		return affiliation;
	}

	public void setAffiliation(Affiliation affiliation)
	{
		this.affiliation = affiliation;
	}

}
