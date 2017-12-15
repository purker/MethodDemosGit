package mapping.result;

import java.util.ArrayList;
import java.util.List;

public class Author extends AbstractAuthor
{
	private static final long serialVersionUID = -7444267954223367935L;

	private String email;

	private String type;
	private List<Affiliation> affiliations = new ArrayList<>();

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public List<Affiliation> getAffiliations()
	{
		return affiliations;
	}

	public void setAffiliations(List<Affiliation> affiliations)
	{
		this.affiliations = affiliations;
	}

}
