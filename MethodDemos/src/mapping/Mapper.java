package mapping;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;

import mapping.result.AbstractPublication;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.Section;
import utils.XStreamUtil;

/**
 * Converts xml output of Cermine to ResultPublication (with JAXB) and saves resulting pojo as xml (serialized with XStream, not JAXB)
 */
public abstract class Mapper<P extends AbstractPublication<?, ?, ?, ?, ?>>
{
	protected JAXBContext jc;
	protected Boolean ignoreDTD = false;

	public Mapper(Class<?>[] clazzes)
	{
		Map<String, Object> properties = new HashMap<String, Object>(1);
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, getBindingFile());

		try
		{
			jc = (JAXBContext)JAXBContextFactory.createContext(clazzes, properties);
			// jc = (JAXBContext)JAXBContextFactory.createContext("mapping.result", ObjectFactory.class.getClassLoader(), properties);
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}
	}

	protected List<Worker<?>> getWorkers()
	{
		return new ArrayList<>();
	}

	/**
	 * @param inputDir
	 *            all xml-files with {methodName} will be mapped
	 * @throws JAXBException
	 */
	public void unmarshallFiles(File inputDir)
	{
		List<File> inputFiles = Arrays.asList(inputDir.listFiles(new FilenameFilter()
		{
			@Override
			public boolean accept(File file, String fileName)
			{
				return fileName.startsWith(getMethodName()) && fileName.endsWith(".xml");
			}
		}));
		unmarshallFiles(inputFiles);
	}

	public void unmarshallFiles(List<File> inputFilesXML)
	{
		for(File inputFile : inputFilesXML)
		{
			File outputFile = new File(inputFile.getParentFile(), inputFile.getName().replace(".xml", "-xstream.xml"));
			File errorFile = new File(inputFile.getParentFile(), inputFile.getName().replace(".xml", "-err.xml"));
			try
			{
				unmarshall(inputFile, outputFile);
			}
			catch(Exception e)
			{
				try
				{
					e.printStackTrace(new PrintStream(errorFile));
				}
				catch(IOException e1)
				{
					System.err.println("Error writing errorFile for " + inputFile);
					e.printStackTrace();
				}
			}
		}
	}

	protected void unmarshall(File inputFileXML, File outputFileObjectAsXML) throws JAXBException, XMLStreamException
	{
		JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
		P publication;
		if(getIgnoreDTD())
		{
			XMLInputFactory xif = XMLInputFactory.newFactory();
			xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
			XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(inputFileXML));
			publication = (P)unmarshaller.unmarshal(xsr);
		}
		else
		{
			publication = (P)unmarshaller.unmarshal(inputFileXML);
		}

		for(Worker worker : getWorkers())
		{
			worker.doWork(publication);
		}

		Publication p = castToPublication(publication);
		XStreamUtil.convertToXmL(p, outputFileObjectAsXML, System.out, false);
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

	private Publication castToPublication(P publication)
	{
		Publication p = new Publication();
		try
		{
			PropertyUtils.copyProperties(p, publication);
		}
		catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
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

		Section section = new Section();
		// section.setId("id");
		// section.setTitle("sectiontitle");
		// section.setReferenceIds(Arrays.asList("referenceID"));
		// section.setType("ref");
		publication.setSections(Arrays.asList(section));

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

	protected abstract String getMethodName();

	public Boolean getIgnoreDTD()
	{
		return ignoreDTD;
	}
}
