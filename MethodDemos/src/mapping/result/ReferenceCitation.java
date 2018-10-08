package mapping.result;

public class ReferenceCitation
{
	private String referenceId;
	private String marker;

	public ReferenceCitation()
	{}

	public ReferenceCitation(String referenceId, String marker)
	{
		this.referenceId = referenceId;
		this.marker = marker;
	}

	public String getReferenceId()
	{
		return referenceId;
	}

	public void setReferenceId(String referenceId)
	{
		this.referenceId = referenceId;
	}

	public String getMarker()
	{
		return marker;
	}

	public void setMarker(String marker)
	{
		this.marker = marker;
	}

}
