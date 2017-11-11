import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.persistence.jaxb.JAXBContext;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.JAXBUnmarshaller;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.itextpdf.text.pdf.PdfReader;

import demos.Demos;
import factory.PublicationFactory;
import mapping.Worker;
import mapping.cermine.ReferenceAuthorNameConcatenationWorker;
import mapping.grobid.AuthorNameConcatenationWorker;
import mapping.markers.AbstractMarkerStyle;
import mapping.markers.NameSquareBracketYearMarkerStyle;
import mapping.markers.RoundBracketNameYearMarkerStyle;
import mapping.markers.SquareBracketNameYearMarkerStyle;
import mapping.markers.SquareBracketNameYearShortMarkerStyle;
import mapping.markers.SquareBracketNumberedMarkerStyle;
import mapping.markers.SuperscriptNumberedMarkerStyle;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.ReferenceAuthor;
import mapping.result.ReferenceCitation;
import mapping.result.Section;
import utils.PublicationUtil;
import utils.ReferenceUtil;
import utils.XStreamUtil;

/**
 * @author Angela
 *
 */
public class StepsHistory
{
	static File file1 = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result/result-TUW-137078-xstream.xml");
	static File file2 = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result/result-TUW-137078-xstream2.xml");
	static File file3 = new File(Demos.grobIdOutputDir, "grobid-TUW-137078-xstream.xml");
	static File file4 = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result/result-TUW-139761-xstream.xml");
	static File file5 = new File(Demos.pdfxOutputDir, "pdfx-TUW-139761.xml");
	static File file6 = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result/result-TUW-139785-xstream.xml");
	static File file7 = new File(Demos.pdfxOutputDir, "pdfx-TUW-140048.xml");
	static File file8 = new File("D:/output/methods/result/result-TUW-140048-xstream.xml");
	static File file9 = new File("D:/output/methods/result/result-TUW-140253-xstream.xml");
	static File resultFileDirectory = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result");
	static File result10_141336 = new File("D:/output/methods/result/result-TUW-141336-xstream.xml");
	static File result11_141618 = new File("D:/output/methods/result/result-TUW-141618-xstream.xml");
	static File result12_141758 = new File("D:/output/methods/result/result-TUW-141758-xstream.xml");
	static File result12_141758cermine = new File(Demos.cermineOutputDir, "cermine-TUW-141758-xstream.xml");

	static List<String> alreadyDone = getResultPubIdsFromDirectory();

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

		// setRefMarker(file5, file4);

		// concatNames(file4);

		// xpathSampleStackoverflowQuestion();

		// countPagesGroundTruth();

		// setRefCounter(file6, (-40));

		// setRefMarker(file7, file8);

		// setRefCounter(file9, (-1));

		// printRefAuthorYearAndNumber(result_141336);

		// affiliationsToAffiliationList(resultFileDirectory);

		// useWorker(resultFileDirectory, new AffiliationWorker());

		// checkGet();

		// rewriteXStreamFiles(resultFileDirectory);

		// ATTENTION uses Demo where parameters have to be adapted
		// doGrobIdAndMoveToResults();

		// changeSectionReferenceIdsToReferenceCitations(resultFileDirectory);

		// changeSectionReferenceIdsToReferenceCitationsFile(result10_141336);

		// setSectionReferenceIdsFromReferenceCitationKeys(result_141336);

		// printResultFiles(resultFileDirectory);

		// rotateReferenceNames(result11_141618);

		// setRefCounter(result11_141618, (-1), 26);

		// changeSectionReferenceIdsToReferenceCitationsFile(result12_141758);

		// setRefCounter(result12_141758, (19), null);

		// splitNames("Wolkov, H., Constine, L., Yahalom, J., Chauvenet, A., Hoppe, R., Abrams,R., Deming, R., Mendenhall, N., Morris, D., Ng, A., Hudson, M., Winter, J., and Mauch, P.");

		// adaptCerminePublication(result12_141758cermine);

		// printRefAuthorYearAndNumber(result12_141758);

		// setSectionReferenceIdsFromReferenceCitationKeys(result12_141758);

		changeSectionReferenceIdsToReferenceCitationsFile(result12_141758);
	}

	private static void adaptCerminePublication(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		for(Reference reference : publication.getReferences())
		{
			if(CollectionUtils.isNotEmpty(reference.getAuthors()))
			{
				for(ReferenceAuthor author : reference.getAuthors())
				{
					if(CollectionUtils.isNotEmpty(author.getFirstNames()))
					{
						String s = author.getFirstNames().get(0);
						author.getFirstNames().clear();
						author.getFirstNames().addAll(Arrays.asList(s.replace(".", "").split(" ")));
					}

				}

			}
		}
		XStreamUtil.convertToXmL(publication, file, System.err, true);
	}

	private static void splitNames(String string)
	{
		String[] strings = string.split(",");

		List<ReferenceAuthor> authors = new ArrayList<>();

		ReferenceAuthor a = new ReferenceAuthor();
		a.setLastName(strings[0]);

		List<String> subList = Arrays.asList(strings).subList(1, strings.length);
		for(String s : subList)
		{
			String[] nameList = s.trim().replace(".", "").split(" ");
			for(String sub : nameList)
			{
				if(sub.length() == 1)
				{
					if(a.getFirstNames() == null)
					{
						a.setFirstNames(new ArrayList<>());
					}
					a.getFirstNames().add(sub);
				}
				else if(!sub.equals("and"))
				{
					authors.add(a);
					a = new ReferenceAuthor();
					a.setLastName(sub);
				}
			}
		}

		authors.add(a);

		XStreamUtil.convertToXmL(authors, System.err, System.err, true);
	}

	private static void setRefCounter(File file, int i)
	{
		setRefCounter(file, i, null);

	}

	/**
	 * @param file
	 * @param i
	 * @param fromIndex
	 *            from witch index (based on 1)
	 */
	private static void setRefCounter(File file, Integer i, Integer fromIndex)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		// references references
		// List<Reference> references = publication.getReferences();
		// references.forEach(p -> p.setId(setId(p.getId(), i, fromIndex)));

		// section references
		for(Section section : publication.getSections())
		{
			if(section.getReferenceIds() != null && section.getLevel() != null && section.getLevel().equals("1.2"))
			{
				for(final ListIterator<String> iterator = section.getReferenceIds().listIterator(); iterator.hasNext();)
				{
					final String refString = iterator.next();
					iterator.set(setId(refString, i, fromIndex));
				}
				if(section.getReferenceCitations() == null) section.setReferenceCitations(new ArrayList<>());
				for(ReferenceCitation referenceCitation : section.getReferenceCitations())
				{
					String refString = referenceCitation.getReferenceId();
					referenceCitation.setReferenceId(setId(refString, i, fromIndex));
				}
			}
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);
	}

	private static void rotateReferenceNames(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		for(Reference reference : publication.getReferences())
		{
			if(CollectionUtils.isNotEmpty(reference.getAuthors()))
			{
				int authorCount = reference.getAuthors().size();

				// set lastname of firstauthor to his lastname
				ReferenceAuthor firstAuthor = reference.getAuthors().get(0);
				if(CollectionUtils.isNotEmpty(firstAuthor.getFirstNames()))
				{
					firstAuthor.getFirstNames().clear();
					firstAuthor.setLastName(firstAuthor.getFirstNames().get(0));
				}
				for(int i = 0; (i + 1) < authorCount; i++)
				{
					ReferenceAuthor referenceAuthor1 = reference.getAuthors().get(i);
					ReferenceAuthor referenceAuthor2 = reference.getAuthors().get(i + 1);

					referenceAuthor1.setFirstNames(new ArrayList<>(referenceAuthor2.getFirstNames()));
				}
				ReferenceAuthor lastAuthor = reference.getAuthors().get(authorCount - 1);
				if(StringUtils.isEmpty(lastAuthor.getLastName()))
				{
					reference.getAuthors().remove(authorCount - 1);
				}

			}
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);

	}

	private static void setSectionReferenceIdsFromReferenceCitationKeys(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		for(Section section : publication.getSections())
		{
			if(section.getReferenceCitations() != null)
			{
				section.getReferenceIds().clear();
				section.setReferenceIds(section.getReferenceCitations().stream().map(ReferenceCitation::getReferenceId).collect(Collectors.toList()));
			}
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);

	}

	private static void changeSectionReferenceIdsToReferenceCitationsFile(File file)
	{
		try
		{
			Publication publication = XStreamUtil.convertFromXML(file, Publication.class);
			AbstractMarkerStyle markerStyle = map.get(publication.getId());

			// Setzt die Marker an den Referencen in den Referencen
			for(Reference reference : publication.getReferences())
			{
				if(markerStyle != null)
				{
					String marker = markerStyle.getMarkerString(reference);
					if(marker.startsWith("[,"))
					{
						marker = "[" + reference.getSource() + ", " + (StringUtils.isEmpty(reference.getNote()) ? reference.getPublicationYear() : reference.getNote()) + "]";
					}
					reference.setMarker(marker);
				}
			}

			for(Section section : publication.getSections())
			{
				if(section.getReferenceIds() != null)
				{
					section.setReferenceCitations(new ArrayList<ReferenceCitation>());
					for(String referenceId : section.getReferenceIds())
					{
						if(markerStyle != null)
						{
							int referenceIndex = ReferenceUtil.getReferenceIdNumber(referenceId) - 1;
							Reference reference = publication.getReferences().get(referenceIndex);

							ReferenceCitation referenceCitation = new ReferenceCitation(referenceId, reference.getMarker());
							section.getReferenceCitations().add(referenceCitation);
						}
					}
				}
			}

			XStreamUtil.convertToXmL(publication, file, System.out, true);
		}
		catch(

		Exception e)
		{
			System.out.println(file);
			e.printStackTrace();
			return;
		}

	}

	private static void printResultFiles(File directory)
	{
		for(File file : directory.listFiles())
		{
			System.out.println(file);
		}

	}

	private static List<String> getResultPubIdsFromDirectory()
	{
		List<String> ids = Arrays.asList(resultFileDirectory.listFiles()).stream().map(f -> PublicationUtil.getIdFromFileName(f.getName())).collect(Collectors.toList());

		return ids;
	}

	private static void changeSectionReferenceIdsToReferenceCitations(File directory)
	{

		for(File file : directory.listFiles())
		{
			changeSectionReferenceIdsToReferenceCitationsFile(file);

		}

	}

	private static void doGrobIdAndMoveToResults() throws IOException, JAXBException
	{
		Demos.executeDemos();

		List<File> xStreamFiles = Demos.getAllFilesFromDirectories(Demos.grobIdOutputDir, "-xstream.xml");

		for(File file : xStreamFiles)
		{
			if(!alreadyDone.contains(PublicationUtil.getIdFromFileName(file.getName())))
			{
				File newFile = new File("D:/output/methods/result", file.getName().replace("grobid", "result"));

				file.renameTo(newFile);
			}
		}

	}

	private static void checkGet()
	{
		Affiliation affiliation = PublicationFactory.createAffiliation();
		Affiliation affiliation2 = SerializationUtils.clone(affiliation);

		ArrayList<Affiliation> list = new ArrayList<>();
		list.add(affiliation);
		list.add(affiliation2);

		affiliation.setId("2");
		affiliation2.setId("3");

		System.out.println(affiliation);
		System.out.println(affiliation2);

		System.out.println(list.indexOf(affiliation2));

	}

	private static void useWorker(File directory, Worker worker)
	{
		for(File file : directory.listFiles())
		{
			Publication publication = XStreamUtil.convertFromXML(file, Publication.class);
			// worker.doWork(publication);
			XStreamUtil.convertToXmL(publication, file, System.out, true);

		}

	}

	private static void affiliationsToAffiliationList(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				for(Author author : publication.getAuthors())
				{
					// author.setAffiliations(new ArrayList(Arrays.asList(author.affiliation)));
					// author.affiliation = null;
				}
				XStreamUtil.convertToXmL(publication, file, System.out, true);
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}
	}

	private static void rewriteXStreamFiles(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				XStreamUtil.convertToXmL(publication, file, System.out, true);
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}
	}

	/**
	 * Prints References ordered and their index
	 */
	private static void printRefAuthorYearAndNumber(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		TreeMap<String, String> map = new TreeMap<>();
		Integer x = 1;
		for(Reference reference : publication.getReferences())
		{
			String ref = new RoundBracketNameYearMarkerStyle().getMarkerString(reference) + reference.getNote();
			String s = map.put(ref, "[" + ReferenceUtil.getReferenceIdNumber(reference.getId()) + "] ");
			if(s != null)
			{
				System.err.println(ref + " " + s);
			}
			x++;
		}
		for(Entry<String, String> entry : map.entrySet())
		{
			System.out.println(entry.getValue() + " " + entry.getKey());
		}

	}

	private static void countPagesGroundTruth() throws IOException
	{
		List<File> files = Demos.getAllGroundTruthFiles();
		int pagesCount = 0;
		Map<String, Integer> map = new TreeMap<>();

		for(File file : files)
		{
			PdfReader reader = new PdfReader(file.getPath());
			int pages = reader.getNumberOfPages();

			map.put(file.getName(), pages);
			pagesCount += pages;

		}
		for(Entry<String, Integer> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		System.out.println(pagesCount);
	}

	private static void xpathSampleStackoverflowQuestion() throws UnsupportedEncodingException
	{
		String xml = "<affiliation key='aff0'>" + "	<orgName type='department'>Institut für Computer Graphik und Algorithmen</orgName>" + "	<orgName type='institution'>Technischen Universität Wien</orgName>" + "</affiliation>";
		String binding = "<?xml version=\"1.0\"?><xml-bindings    xmlns=\"http://www.eclipse.org/eclipselink/xsds/persistence/oxm\"    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"    xsi:schemaLocation=\"http://www.eclipse.org/eclipselink/xsds/eclipselink_oxm_2_4.xsd\"    package-name=\"mapping.result\">    <xml-schema        namespace=\"\"        element-form-default=\"QUALIFIED\"        prefix=\"\"/>    <java-types>        <java-type name=\"Affiliation\">            <xml-root-element name=\"affiliation\"/>            <java-attributes>                <xml-attribute java-attribute=\"id\" name=\"key\"/>                <xml-element java-attribute=\"institution\" xml-path=\"orgName[@type='institution']/text()\" />	           	<xml-element java-attribute=\"department\" xml-path=\"orgName[@type='department']/text()\" />                <xml-element java-attribute=\"country\" xml-path=\"address/country/text()\" />                <xml-element java-attribute=\"countryCode\" xml-path=\"address/country/@key\" />            </java-attributes>        </java-type>    </java-types></xml-bindings>";

		Map<String, Object> properties = new HashMap<>(1);
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

	private static void concatNames(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		new AuthorNameConcatenationWorker().doWork(publication);
		new ReferenceAuthorNameConcatenationWorker().doWork(publication);

		XStreamUtil.convertToXmL(publication, file, System.out, true);

	}

	private static void setRefMarker(File pdfxFileWithMarkers, File xStream) throws Exception
	{
		List<String> markers = new ArrayList<>();

		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(pdfxFileWithMarkers);
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

		Publication publication = XStreamUtil.convertFromXML(xStream, Publication.class);

		List<Reference> references = publication.getReferences();
		references.forEach(p -> p.setMarker(markers.get(new Integer(p.getId().replace("ref", "")) - 1)));

		XStreamUtil.convertToXmL(publication, xStream, System.out, true);

	}

	private static String setId(String id, Integer i, Integer fromIndex)
	{
		Integer idAsInteger = new Integer(id.replaceFirst("ref", ""));
		if(fromIndex == null || idAsInteger >= fromIndex)
		{
			String s = "ref" + (idAsInteger + i);
			return s;
		}
		else
		{
			return id; // change nothing
		}
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

	static Map<String, AbstractMarkerStyle> map = new HashMap<>();
	static
	{
		map.put("TUW-137078", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-138011", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-138447", new SuperscriptNumberedMarkerStyle());
		map.put("TUW-138544", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-138547", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-139299", new SquareBracketNameYearMarkerStyle());
		map.put("TUW-139761", new SquareBracketNameYearShortMarkerStyle());// new SquareBracketNameYearShortMarkerStyle());
		map.put("TUW-139769", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-139781", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-139785", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-140047", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-140048", new SquareBracketNameYearShortMarkerStyle());
		map.put("TUW-140229", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-140253", new RoundBracketNameYearMarkerStyle());
		map.put("TUW-140308", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-140533", new NameSquareBracketYearMarkerStyle());
		map.put("TUW-140867", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-140895", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-140983", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-141024", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-141065", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-141121", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-141140", new RoundBracketNameYearMarkerStyle());
		map.put("TUW-141336", new RoundBracketNameYearMarkerStyle());
		map.put("TUW-141618", new SquareBracketNumberedMarkerStyle());
		map.put("TUW-141758", new SquareBracketNameYearMarkerStyle());
		map.put("TUW-168222", new SquareBracketNumberedMarkerStyle());
	}
}
