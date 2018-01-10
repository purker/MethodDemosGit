package evaluation.tools;

import java.util.Comparator;

public class RelationComparators
{
	private Comparator<String> comparator1;
	private Comparator<String> comparator2;

	public RelationComparators()
	{}

	public RelationComparators(Comparator<String> comparator1, Comparator<String> comparator2)
	{
		super();
		this.comparator1 = comparator1;
		this.comparator2 = comparator2;
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

}
