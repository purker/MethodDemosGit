package evaluation.tools;

import java.util.Comparator;

public class RelationComparators
{
	private Comparator<String> comparator1;
	private Comparator<String> comparator2;
	private String comparator1Name;
	private String comparator2Name;

	public RelationComparators()
	{}

	public RelationComparators(Comparator<String> comparator1, String comparator1Name, Comparator<String> comparator2, String comparator2Name)
	{
		super();
		this.comparator1 = comparator1;
		this.comparator2 = comparator2;
		this.comparator1Name = comparator1Name;
		this.comparator2Name = comparator2Name;
	}

	public Comparator<String> getComparator1()
	{
		return comparator1;
	}

	public void setComparator1(Comparator<String> comparator1)
	{
		this.comparator1 = comparator1;
	}

	public Comparator<String> getComparator2()
	{
		return comparator2;
	}

	public void setComparator2(Comparator<String> comparator2)
	{
		this.comparator2 = comparator2;
	}

	public String getComparator1Name()
	{
		return comparator1Name;
	}

	public String getComparator2Name()
	{
		return comparator2Name;
	}

}
