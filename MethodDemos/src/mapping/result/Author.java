package mapping.result;

import java.util.ArrayList;
import java.util.List;

public class Author extends BaseEntity
{
	private static final long serialVersionUID = -7444267954223367935L;

	private String name; // if null, information in first and lastname
	private List<String> firstNames;
	private String lastName;

	private String email;

	private String type;
	private ArrayList<Affiliation> affiliations;

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

	public ArrayList<Affiliation> getAffiliations()
	{
		return affiliations;
	}

	public void setAffiliations(ArrayList<Affiliation> affiliations)
	{
		this.affiliations = affiliations;
	}

}
