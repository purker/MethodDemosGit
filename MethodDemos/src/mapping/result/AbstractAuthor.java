package mapping.result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.CollectionUtil;
import utils.PublicationUtil;
import utils.StringUtil;

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

	public String getFirstNamesAsString()
	{

		StringBuffer sb = new StringBuffer();
		if(CollectionUtil.isNotEmpty(getFirstNames()))
		{
			for(Iterator<String> iterator = getFirstNames().iterator(); iterator.hasNext();)
			{
				String firstName = iterator.next();

				if(StringUtil.isNotEmpty(firstName))
				{
					sb.append(firstName);
					if(iterator.hasNext()) sb.append(" ");
				}
			}
		}
		return sb.toString();
	}
}
