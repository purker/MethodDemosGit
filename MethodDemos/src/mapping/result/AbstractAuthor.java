package mapping.result;

import java.util.ArrayList;
import java.util.List;

import utils.PublicationUtil;

public class AbstractAuthor extends BaseEntity
{
	private static final long serialVersionUID = -2302044320347655573L;

	// TODO delete or to private
	public String name; // if null, information in first and lastname
	private List<String> firstNames = new ArrayList<>();
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

	public String getFullName()
	{
		if(name != null)
		{
			return name;
		}
		return PublicationUtil.getNameFromAuthor(this);
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return getFullName();
	}
}
