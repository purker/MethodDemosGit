package mapping.result;

import java.util.List;

public class ReferenceAuthor extends BaseEntity
{
	private static final long serialVersionUID = 3097279412917093379L;

	private String name; // if null, information in first and lastname
	private List<String> firstNames;
	private String lastName;

	public List<String> getFirstNames()
	{
		return firstNames;
	}

	public void setFirstNames(List<String> firstNames)
	{
		this.firstNames = firstNames;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

}
