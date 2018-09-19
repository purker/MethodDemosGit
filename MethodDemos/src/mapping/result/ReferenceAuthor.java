package mapping.result;

public class ReferenceAuthor extends AbstractAuthor
{
	private static final long serialVersionUID = 3097279412917093379L;

	public boolean isEtAlAuthor()
	{
		return "et al.".equals(getFullName());
	}
}
