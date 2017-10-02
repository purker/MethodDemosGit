package mapping.result;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "export")
public class Export
{
	@XmlElement(name = "publikation")
	private List<Publication> publications;

	@XmlPath(value = "statistik/anzahl_pubs/text()")
	private Integer publicationCount;

	@XmlPath(value = "export_info/text()")
	private Integer exportInfo;

	public Integer getPublicationCount()
	{
		return publicationCount;
	}

	public void setPublicationCount(Integer publicationCount)
	{
		this.publicationCount = publicationCount;
	}

	public Integer getExportInfo()
	{
		return exportInfo;
	}

	public void setExportInfo(Integer exportInfo)
	{
		this.exportInfo = exportInfo;
	}

	public List<Publication> getPublications()
	{
		return publications;
	}

	public void setPublications(List<Publication> publications)
	{
		this.publications = publications;
	}
}
