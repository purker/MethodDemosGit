package mapping;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBMarshaller;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;

import com.google.common.base.CharMatcher;

import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.Section;
import utils.XStreamUtil;

/**
 * Converts xml output of Cermine to ResultPublication (with JAXB) and saves resulting pojo as xml (serialized with XStream, not JAXB)
 */
public abstract class Mapper
{
	protected JAXBContext jc;
	protected Boolean ignoreDTD = false;

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

	protected List<? extends Worker> getWorkers()
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
			// id="TUW-000000"
			String id = getIdFromFileName(inputFile.getName());
			File outputFile = getOutputFile(id);
			File errorFile = getErrorFile(id);
			try
			{
				unmarshall(inputFile, outputFile);
			}
			catch(Exception e)
			{
				try
				{
					e.printStackTrace();
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

	private String getIdFromFileName(String fileName)
	{
		String id = CharMatcher.DIGIT.retainFrom(fileName);
		return "TUW-" + id;
	}

	private File getErrorFile(String id)
	{
		return new File(getDirectory(), getMethodName() + "-" + id + "-mapping.errxml");
	}

	private File getOutputFile(String id)
	{
		return new File(getDirectory(), getMethodName() + "-" + id + "-xstream.xml");
	}

	protected void unmarshall(File inputFileXML, File outputFileObjectAsXML) throws JAXBException, XMLStreamException
	{
		JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
		Publication publication;
		if(getIgnoreDTD())
		{
			XMLInputFactory xif = XMLInputFactory.newFactory();
			xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
			XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(inputFileXML));
			publication = (Publication)unmarshaller.unmarshal(xsr);
		}
		else
		{
			publication = (Publication)unmarshaller.unmarshal(inputFileXML);
		}
		publication.setId(inputFileXML.getName());

		for(Worker worker : getWorkers())
		{
			worker.doWork(publication);
		}

		XStreamUtil.convertToXmL(publication, outputFileObjectAsXML, System.out, false);

		// System.out.println(publication);
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
		reference.setPublicationDate(new Date());

		publication.setReferences(Arrays.asList(reference));

		JAXBMarshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(publication, System.out);

	}

	protected abstract String getBindingFile();

	protected abstract String getMethodName();

	protected abstract File getDirectory();

	public Boolean getIgnoreDTD()
	{
		return ignoreDTD;
	}
}
