package mapping.result;

import java.util.ArrayList;

public class Author extends AbstractAuthor
{
	private static final long serialVersionUID = -7444267954223367935L;

	private String email;

	private String type;
	private ArrayList<Affiliation> affiliations;

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
