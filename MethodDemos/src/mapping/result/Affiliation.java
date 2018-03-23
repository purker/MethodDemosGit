package mapping.result;

public class Affiliation extends BaseEntity
{
	private static final long serialVersionUID = -3698496426249593692L;

	private String id;
	private String institution;
	private String department;
	private String country;
	private String countryCode;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getInstitution()
	{
		return institution;
	}

	public void setInstitution(String institution)
	{
		this.institution = institution;
	}

	public String getDepartment()
	{
		return department;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getCountryCode()
	{
		return countryCode;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((department == null) ? 0 : department.hashCode());
		result = prime * result + ((institution == null) ? 0 : institution.hashCode());
		return result;
	}

	/*
	 * On purpose without id
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Affiliation other = (Affiliation)obj;
		if(country == null)
		{
			if(other.country != null) return false;
		}
		else
			if(!country.equals(other.country)) return false;
		if(countryCode == null)
		{
			if(other.countryCode != null) return false;
		}
		else
			if(!countryCode.equals(other.countryCode)) return false;
		if(department == null)
		{
			if(other.department != null) return false;
		}
		else
			if(!department.equals(other.department)) return false;
		if(institution == null)
		{
			if(other.institution != null) return false;
		}
		else
			if(!institution.equals(other.institution)) return false;
		return true;
	}

	public String getRawText()
	{
		return id + " " + institution + " " + department + " " + country + " " + countryCode;
	}

}
