package mapping.result;

import java.util.List;

public class Author extends BaseEntity
{
	private static final long serialVersionUID = -7444267954223367935L;

	private String name; // if null, information in first and lastname
	protected List<String> firstNames;
	protected String lastName;

	protected String email;

	protected String type;
	protected Affiliation affiliation;

	public String getLastName()
	{
		return lastName;
	}

	public List<String> getFirstNames()
	{
		return firstNames;
	}

	public void setFirstNames(List<String> firstNames)
	{
		this.firstNames = firstNames;
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

	public Affiliation getAffiliation()
	{
		return affiliation;
	}

	public void setAffiliation(Affiliation affiliation)
	{
		this.affiliation = affiliation;
	}

}
