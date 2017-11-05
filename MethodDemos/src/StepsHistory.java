import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import demos.Demos;
import factory.PublicationFactory;
import mapping.cermine.ReferenceAuthorNameConcatenationWorker;
import mapping.grobid.AuthorNameConcatenationWorker;
import mapping.result.Affiliation;
import mapping.result.Publication;
import mapping.result.Reference;
import utils.XStreamUtil;

public class StepsHistory
{
	static File file = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result/result-TUW-137078-xstream.xml");
	static File file2 = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result/result-TUW-137078-xstream2.xml");
	static File file3 = new File(Demos.grobIdOutputDir, "grobid-TUW-137078-xstream.xml");
	static File file4 = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result/result-TUW-139761-xstream.xml");
	static File file5 = new File(Demos.pdfxOutputDir, "pdfx-TUW-139761.xml");

	public static void main(String[] args) throws Exception
	{
		// Publication p = XStreamUtil.convertFromXML(file, Publication.class);
		//

		// publicationToFile();
		// getPublicationFromFile2();

		// addPackageNames();
		// removePackageNames();
		// getPublicationFromFile3();

		// checkSerialization(file);

		// setRefCounter(file4, (1));

		// setRefMarker(file5);

		// concatNames(file4);

		xpathSampleStackoverflowQuestion();
	}

	private static void xpathSampleStackoverflowQuestion() throws UnsupportedEncodingException
	{
		String xml = "<affiliation key='aff0'>" + "	<orgName type='department'>Institut für Computer Graphik und Algorithmen</orgName>" + "	<orgName type='institution'>Technischen Universität Wien</orgName>" + "</affiliation>";
		String binding = "<?xml version=\"1.0\"?><xml-bindings    xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xsi:schemaLocation=\"http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd\"    package-name=\"mapping.result\">    <xml-schema        namespace=\"\"        element-form-default=\"QUALIFIED\"        prefix=\"\"/>    <java-types>        <java-type name=\"Affiliation\">            <xml-root-element name=\"affiliation\"/>            <java-attributes>                <xml-attribute java-attribute=\"id\" name=\"key\"/>                <xml-element java-attribute=\"institution\" xml-path=\"orgName[@type='institution']/text()\" />	           	<xml-element java-attribute=\"department\" xml-path=\"orgName[@type='department']/text()\" />                <xml-element java-attribute=\"country\" xml-path=\"address/country/text()\" />                <xml-element java-attribute=\"countryCode\" xml-path=\"address/country/@key\" />            </java-attributes>        </java-type>    </java-types></xml-bindings>";

		Map<String, Object> properties = new HashMap<String, Object>(1);
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, new StringReader(binding));

		try
		{
			JAXBContext jc = (JAXBContext)JAXBContextFactory.createContext(new Class[]
			{Publication.class}, properties);
			JAXBUnmarshaller unmarshaller = jc.createUnmarshaller();
			Affiliation affiliation = (Affiliation)unmarshaller.unmarshal(new StringReader(xml));
			System.out.println(affiliation);
		}
		catch(JAXBException e)
		{
			e.printStackTrace();
		}

	}

	private static void concatNames(File file42)
	{
		Publication publication = XStreamUtil.convertFromXML(file4, Publication.class);

		new AuthorNameConcatenationWorker().doWork(publication);
		new ReferenceAuthorNameConcatenationWorker().doWork(publication);

		XStreamUtil.convertToXmL(publication, file4, System.out, true);

	}

	private static void setRefMarker(File file) throws Exception
	{
		List<String> markers = new ArrayList<>();

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(file);
		XPath xpath = XPathFactory.newInstance().newXPath();
		XPathExpression expr = xpath.compile("//ref/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList)result;
		for(int i = 0; i < nodes.getLength(); i++)
		{
			String s = nodes.item(i).getTextContent();
			String marker = s.substring(0, s.indexOf(" "));
			System.out.println(s);
			markers.add(marker);
		}

		Publication publication = XStreamUtil.convertFromXML(file4, Publication.class);

		List<Reference> references = publication.getReferences();
		references.forEach(p -> p.setMarker(markers.get(new Integer(p.getId().replace("ref", "")) - 1)));

		XStreamUtil.convertToXmL(publication, file4, System.out, true);

	}

	private static void setRefCounter(File file, Integer i)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		List<Reference> references = publication.getReferences();
		references.forEach(p -> p.setId(setId(p.getId(), i)));

		XStreamUtil.convertToXmL(publication, file, System.out, true);
	}

	private static String setId(String id, Integer i)
	{
		Integer idAsInteger = new Integer(id.replaceFirst("ref", ""));
		String s = "ref" + (idAsInteger + i);
		return s;
	}

	private static void checkSerialization(File file) throws IOException
	{
		Publication p = XStreamUtil.convertFromXML(file, Publication.class);
		System.out.println(p);
	}

	private static void removePackageNames() throws IOException
	{
		List<File> files = Demos.getAllFilesFromDirectories(Demos.grobIdOutputDir, "-xstream.xml");
		for(File file : files)
		{
			List<String> strings = FileUtils.readLines(file, StandardCharsets.UTF_8);
			List<String> stringsNew = new ArrayList<>();

			for(String string : strings)
			{
				string = string.replace("mapping.result", "");

				stringsNew.add(string);
			}
			FileUtils.writeLines(file, stringsNew, false);
		}
	}

	private static void getPublicationFromFile3()
	{
		Publication p = XStreamUtil.convertFromXML(file3, Publication.class);

		System.out.println(p);
	}

	private static void addPackageNames() throws IOException
	{
		List<File> files = Demos.getAllFilesFromDirectories(Demos.grobIdOutputDir, "-xstream.xml");
		for(File file : files)
		{
			List<String> strings = FileUtils.readLines(file, StandardCharsets.UTF_8);
			List<String> stringsNew = new ArrayList<>();

			for(String string : strings)
			{
				string = string.replace("Publication>", "mapping.result.Publication>");
				string = string.replace("Author>", "mapping.result.Author>");
				string = string.replace("Affiliation>", "mapping.result.Affiliation>");
				string = string.replace("Section>", "mapping.result.Section>");
				string = string.replace("Reference>", "mapping.result.Reference>");

				stringsNew.add(string);
			}
			FileUtils.writeLines(file, stringsNew, false);
		}

	}

	private static void publicationToFile()
	{
		Publication p2 = PublicationFactory.createPublication();

		XStreamUtil.convertToXmL(p2, file2, System.out, true);

	}

	private static void getPublicationFromFile2()
	{
		Publication p = XStreamUtil.convertFromXML(file2, Publication.class);

	}
}
