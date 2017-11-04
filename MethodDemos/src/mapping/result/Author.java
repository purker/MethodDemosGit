package mapping.result;

public class Author extends BaseEntity
{
	private static final long serialVersionUID = -7444267954223367935L;

	protected String name; // if null, information in first and lastname
	protected String firstName;
	protected String lastName;

	protected String email;

	protected String type;
	protected Affiliation affiliation;

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

	public Affiliation getAffiliation()
	{
		return affiliation;
	}

	public void setAffiliation(Affiliation affiliation)
	{
		this.affiliation = affiliation;
	}

}
