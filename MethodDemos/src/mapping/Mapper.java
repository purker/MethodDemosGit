package mapping;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;

import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import utils.XStreamUtil;

/**
 * Converts xml output of Cermine to ResultPublication (with JAXB) and saves resulting pojo as xml (serialized with XStream, not JAXB)
 */
public abstract class Mapper
{
	protected JAXBContext jc;

	public Mapper()
	{
		Map<String, Object> properties = new HashMap<String, Object>(1);
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, getBindingFile());

		try
		{
			jc = (JAXBContext)JAXBContextFactory.createContext(new Class[]
			{Publication.class}, properties);
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
	}

	protected void unmarshall(File inputFileXML, File outputFileObjectAsXML) throws JAXBException
	{
		JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
		Publication publication = (Publication)unmarshaller.unmarshal(inputFileXML);

		XStreamUtil.convertToXmL(publication, outputFileObjectAsXML, System.out, false);
		// System.out.println(publication.getTitle());
		// System.out.println(publication.getAuthors().get(0).getEmail());
		// System.out.println(publication.getAuthors().get(0).getLastName());
		//
		// System.out.println(ReflectionToStringBuilder.toString(publication, ToStringStyle.MULTI_LINE_STYLE));
		System.out.println(publication);
		// System.out.println(ReflectionToStringBuilder.toString(publication.getAuthors(), ToStringStyle.MULTI_LINE_STYLE));
		// System.out.println(ReflectionToStringBuilder.toString(publication.getReferences(), ToStringStyle.MULTI_LINE_STYLE));

		// publication.setTitle("blub");
		// List<Author> authors = new ArrayList<>();
		// Author a = new Author();
		// a.setFirstName("bl");
		// a.setLastName("last");
		// a.setEmail("blub@cob");
		//
		// Reference r = new Reference();
		// r.setTitle("referencetitle");
		// r.setPageFrom(1L);
		// r.setPageTo(2L);
		//
		// authors.add(a);
		//
		// publication.setAuthors(authors);
		// publication.setReferences(Arrays.asList(r));
		// TODO split name in lastname firstname

		// JAXBMarshaller marshaller = jc.createMarshaller();
		// marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
		// marshaller.marshal(publication, System.out);

	}

	/**
	 * Only for test of mappings
	 * 
	 * @param inputFileXML
	 * @param outputFileObjectAsXML
	 * @throws JAXBException
	 */
	protected void marshall() throws JAXBException
	{
		Publication publication = new Publication();

		publication.setTitle("title");
		Author author = new Author();
		author.setFirstName("first");
		author.setLastName("last");
		author.setEmail("blub@cob");
		author.setType("author");
		publication.setAuthors(Arrays.asList(author));

		Affiliation affiliation = new Affiliation();
		affiliation.setInstitution("TU Wien");
		affiliation.setCountry("Austria");
		affiliation.setCountryCode("AT");
		publication.setAffiliations(Arrays.asList(affiliation));

		Reference reference = new Reference();
		reference.setTitle("referencetitle");
		reference.setPageFrom(1L);
		reference.setPageTo(2L);

		publication.setReferences(Arrays.asList(reference));

		JAXBMarshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(publication, System.out);

	}

	protected abstract String getBindingFile();
}
