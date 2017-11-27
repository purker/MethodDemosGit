package mapping.result;

import java.util.List;

public class AbstractAuthor extends BaseEntity
{
	private static final long serialVersionUID = -2302044320347655573L;

	// TODO delete or to private
	public String name; // if null, information in first and lastname
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
