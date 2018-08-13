package mapping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
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

import mapping.result.Publication;
import method.Method;
import utils.FailureUtil;
import utils.FileCollectionUtil;
import utils.PublicationUtil;
import utils.XStreamUtil;

/**
 * Converts xml output of specific method to Publication (with JAXB) and saves resulting pojo as xml (serialized with XStream, not JAXB)
 */
public abstract class Mapper
{
	private static final boolean OVERRIDE_EXISTING = true; // only map file, if output file not existing
	protected JAXBContext jc;
	protected Boolean ignoreDTD = false;

	public Mapper()
	{
		Map<String, Object> properties = new HashMap<>(1);
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, getBindingFile());

		try
		{
			jc = (JAXBContext)JAXBContextFactory.createContext(new Class[]{Publication.class}, properties);
		}
		catch(JAXBException e)
		{
			FailureUtil.failureExit(e, System.err, "initialisation of JAXBContext", true);
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
		unmarshallFiles(FileCollectionUtil.getExtractedFiles(inputDir, this.getMethod()));
	}

	private File getErrorFile(String id)
	{
		return new File(getDirectory(), getMethod().getName() + "-" + id + "-mapping.errxml");
	}

	private File getOutputFile(String id)
	{
		return new File(getDirectory(), getMethod().getName() + "-" + id + "-xstream.xml");
	}

	protected void unmarshallToXmlFile(File inputFileXML, File outputFileObjectAsXML) throws JAXBException, XMLStreamException, FileNotFoundException
	{
		if(!OVERRIDE_EXISTING && outputFileObjectAsXML.exists())
		{
			System.out.println("already exists: " + outputFileObjectAsXML);
			return;
		}
		Publication publication = unmarshallFile(inputFileXML);

		XStreamUtil.convertToXmL(publication, outputFileObjectAsXML, System.out, true);

		// System.out.println(publication);
	}

	public Publication unmarshallFile(File inputFileXML) throws JAXBException, XMLStreamException, FileNotFoundException
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
		publication.setIdFromFileName(inputFileXML);

		for(Worker worker : getWorkers())
		{
			worker.doWorkCatchException(publication);
		}

		return publication;
	}

	public void unmarshallFiles()
	{
		unmarshallFiles(FileCollectionUtil.getExtractedFiles(getMethod()));
	}

	public void unmarshallFilesWithId(List<String> idList)
	{
		unmarshallFiles(FileCollectionUtil.getExtractedFiles(getMethod(), idList));
	}

	public void unmarshallFiles(List<File> extractedFiles)
	{
		for(File inputFile : extractedFiles)
		{
			// id="TUW-000000"
			String id = PublicationUtil.getIdFromFile(inputFile);
			File outputFile = getOutputFile(id);
			File errorFile = getErrorFile(id);
			try
			{
				unmarshallToXmlFile(inputFile, outputFile);
			}
			catch(Exception e)
			{
				try
				{
					System.out.println(inputFile);
					// Desktop.getDesktop().open(inputFile);
					e.printStackTrace(new PrintStream(errorFile));
					FailureUtil.failureExit(e, System.err, "Error unmarshalling file " + inputFile, true);
				}
				catch(IOException e1)
				{
					FailureUtil.failureExit(e, System.err, "Error writing errorFile for " + inputFile, true);
				}
			}
		}

	}

	/**
	 * Only for test of mappings
	 * 
	 * @param out
	 * 
	 * @param inputFileXML
	 * @param outputFileObjectAsXML
	 * @throws JAXBException
	 */
	protected void marshall(Publication publication, OutputStream out) throws JAXBException
	{
		JAXBMarshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(publication, out);
	}

	public void marshall(Publication publication, File file) throws JAXBException
	{
		JAXBMarshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(JAXBMarshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(publication, file);
	}

	protected abstract String getBindingFile();

	protected abstract Method getMethod();

	protected abstract File getDirectory();

	public Boolean getIgnoreDTD()
	{
		return ignoreDTD;
	}
}
