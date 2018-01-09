import java.awt.Desktop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.itextpdf.text.pdf.PdfReader;

import demos.Demos;
import factory.PublicationFactory;
import mapping.Worker;
import mapping.cermine.ReferenceAuthorNameConcatenationWorker;
import mapping.grobid.AuthorNameConcatenationWorker;
import mapping.markers.AbstractMarkerStyle;
import mapping.markers.NameSquareBracketYearMarkerStyle;
import mapping.markers.RoundBracketNameYearMarkerStyle;
import mapping.markers.RoundBracketNameYearMarkerWithoutComaStyle;
import mapping.markers.RoundBracketShortNameYearMarkerWithoutComaStyle2;
import mapping.markers.SquareBracketNameYearMarkerStyle;
import mapping.markers.SquareBracketNameYearShortMarkerStyle;
import mapping.markers.SquareBracketNumberedMarkerStyle;
import mapping.markers.SuperscriptNumberedMarkerStyle;
import mapping.result.AbstractAuthor;
import mapping.result.AbstractMetaPublication;
import mapping.result.Affiliation;
import mapping.result.Author;
import mapping.result.Publication;
import mapping.result.Reference;
import mapping.result.ReferenceAuthor;
import mapping.result.ReferenceCitation;
import mapping.result.Section;
import utils.CollectionUtil;
import utils.FileNameUtil;
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
	static File file8 = new File("D:/output/methods/resultsource/result-TUW-140048-xstream.xml");
	static File file9 = new File("D:/output/methods/resultsource/result-TUW-140253-xstream.xml");
	static File resultFileDirectory = new File("D:/Java/git/MethodDemosGit/MethodDemos/output/result");
	static File result10_141336 = new File("D:/output/methods/resultsource/result-TUW-141336-xstream.xml");
	static File result11_141618 = new File("D:/output/methods/resultsource/result-TUW-141618-xstream.xml");
	static File result12_141758 = new File("D:/output/methods/resultsource/result-TUW-141758-xstream.xml");
	static File result12_141758cermine = new File(Demos.cermineOutputDir, "cermine-TUW-141758-xstream.xml");
	static File result13_168482 = new File("D:/output/methods/resultsource/result-TUW-168482-xstream.xml");
	static File result13_168482pdfx = new File(Demos.pdfxOutputDir, "pdfx-TUW-168482.xml");
	static File groundTruthDiplomarbeiten = new File("D:\\output\\2017-08-06 17.04.36.036 c52c68fa-4732-44fa-b392-e1fdbff324d8\\Diplom- und Masterarbeiten");
	static File result14_182414 = new File("D:/output/methods/resultsource/result-TUW-182414-xstream.xml");
	static File result15_182899 = new File("D:/output/methods/resultsource/result-TUW-182899-xstream.xml");
	static File result16_191715 = new File("D:/output/methods/resultsource/result-TUW-191715-xstream.xml");
	static File result17_192724 = new File("D:/output/methods/resultsource/result-TUW-192724-xstream.xml");
	public static List<String> alreadyDone = getResultPubIdsFromDirectory();
	static File file18_194085 = new File("D:/output/methods/resultsource/result-TUW-194085-xstream.xml");
	static File file19_194660 = new File("D:/output/methods/resultsource/result-TUW-194660-xstream.xml");
	private static File file20_197422pdfx = new File("D:/output/methods/pdfx/pdfx-TUW-197422.xml");
	private static File file20_197422 = new File("D:/output/methods/resultsource/result-TUW-197422-xstream.xml");
	private static File file21_197852 = new File("D:/output/methods/resultsource/result-TUW-197852-xstream.xml");
	private static File file22_198400 = new File("D:/output/methods/resultsource/result-TUW-198400-xstream.xml");
	private static File file23_198408 = new File("D:/output/methods/resultsource/result-TUW-198408-xstream.xml");
	private static File file23_198408grobid = new File("D:/output/methods/grobid/grobid-TUW-198408-xstream.xml");
	private static File file24_200748 = new File("D:/output/methods/resultsource/result-TUW-200748-xstream.xml");
	private static File file25_200950 = new File("D:/output/methods/resultsource/result-TUW-200950-xstream.xml");
	private static File file25_200950pdfx = new File("D:/output/methods/pdfx/pdfx-TUW-200950.xml");
	private static File file26 = new File("D:\\Java\\git\\MethodDemosGit\\MethodDemos\\output\\result\\result-TUW-189842-xstream.xml");
	private static File groundTruthNoSubdir = new File("D:\\output\\methods\\GroundTruthNoSubDir");
	private static File file27_200745 = new File("D:/output/methods/grobid/grobid-TUW-200745-xstream.xml");
	private static File file28_247743 = new File("D:/output/methods/grobid/grobid-TUW-247743-xstream.xml");
	private static File file29_202034 = new File("D:/output/methods/resultsource/result-TUW-202034-xstream.xml");
	private static File file30_203924 = new File("D:/output/methods/resultsource/result-TUW-203924-xstream.xml");
	private static File file31_204724 = new File("D:/output/methods/resultsource/result-TUW-204724-xstream.xml");
	private static File file32_213513 = new File("D:/output/methods/resultsource/result-TUW-213513-xstream.xml");
	private static File file32_213513jaxb = new File("D:/output/methods/resultsource/result-TUW-213513-jaxb.xml");
	private static File file33_216744 = new File("D:/output/methods/resultsource/result-TUW-216744-xstream.xml");
	private static File file34 = new File("D:/output/methods/resultsource/result-TUW-217971-xstream.xml");
	private static File file35 = new File("D:/output/methods/resultsource/result-TUW-226016-xstream.xml");
	private static File file35pdfx = new File("D:/output/methods/pdfx/pdfx-TUW-226016.xml");
	private static File file36 = new File("D:/output/methods/resultsource/result-TUW-228620-xstream.xml");
	private static File file37 = new File("D:\\output\\methods\\grobid 4.3.0 no consolidation\\grobid-TUW-245336-xstream.xml");
	private static File file38 = new File("D:/output/methods/resultsource/result-TUW-251544-xstream.xml");
	private static File file39 = new File("D:/output/methods/resultsource/result-TUW-255712-xstream.xml");
	private static File file40 = new File("D:/output/methods/resultsource/result-TUW-174216-xstream.xml");
	private static File file40pdfx = new File("D:/output/methods/pdfx/pdfx-TUW-174216.xml");
	private static File file41 = new File("D:/output/methods/resultsource/result-TUW-175428-xstream.xml");
	private static File file42 = new File("D:/output/methods/resultsource/result-TUW-177140-xstream.xml");
	private static File groundTruth = new File("D:\\Java\\git\\MethodDemosGit\\MethodDemos\\docs\\groundtruth");
	private static List<String> idList = Arrays.asList("137078", "138011", "138447", "138544", "138547", "139299", "139761", "139769", "139781", "139785", "140047", "140048", "140229", "140253", "140308", "140533", "140867", "140895", "140983", "141024", "141065", "141121", "141140", "141336", "141618", "141758", "168222", "168482", "169511", "172697", "174216", "175428", "176087", "177140", "179146", "180162", "181199", "182414", "182899", "185321", "185441", "186227", "189842", "191715", "191977", "192724", "194085", "194561", "194660", "197422", "197852", "198400", "198401", "198405", "198408", "200745", "200748", "200948", "200950", "200959", "201066", "201160", "201167", "202034", "225252", "202824", "203409", "203924", "201821", "204724", "205557", "205933", "213513", "216744", "217690", "217971", "221215", "223906", "223973", "226000", "226016", "228620", "231707", "233317", "233657", "236063", "236120", "237297", "240858", "245336", "245799", "247301", "247741", "247743", "251544", "252847", "255712", "256654", "257397", "257870");

	public static void main(String[] args) throws Exception
	{
		// checkAllGroundTruthAreInIdList();
		checkAllGroundTruthFilesExist(idList);
		// checkXStreamFiles(resultFileDirectory);
		// findNotProcessedPDFXFiles(Demos.pdfxOutputDir);
		// printPageCountForPdfs(getNotAlreadyDone(new File("D:\\output\\old\\Ground Truth old\\Publikationsart Beiträge aus Tagungsbänden")));
		// setRefCounter(file42, -1, null, true, false);
		// replaceReferenceMarkersWithIdsInSections(file40);
		// setReferenceMarkerByPdfxFile(file40pdfx, file40);
		// setMarkersByReferenceStyle(file39);
		// replaceReferenceMarkersWithIdsInSections(file39);

		//
		// setRefCounter(file39, 1, 14, false, true);
		// setRefCounter(file39, 1, 8, false, true);
		// setRefCounter(file39, 1, 4, false, true);
		// splitFirstNames(file39);
		// searchBracketContentFromText(new File("text.txt"));
		// replaceReferenceMarkersWithIdsInSections(file38);
		// doGrobIdAndMoveToResults();
		// printRefAuthorYearAndNumberForSections(file37);
		// printRefAuthorYearAndNumber(file37);
		// replaceReferenceMarkersWithIdsInSections(file36);
		// setMarkersByReferenceStyle(file36);
		// printRefAuthorYearAndNumber(file36);
		// changeSectionReferenceIdsToIdsWithMarker(file36);
		// setRefCounter(file36, 1, 39, true, true);
		// setRefCounter(file36, -1, 27, true, true);
		// changeSourceToTitle(file36);
		// replaceReferenceMarkersWithIdsInSections(file35);
		// setReferenceMarkerByPdfxFile(file35pdfx, file35);
		// setRefCounter(file35, 1, 12, false, true);
		// changeSourceToTitle(file35);
		// setRefCounter(file35, 1, 7, false, true);
		// rotateReferenceNames2(file35, 4);
		// setRefCounter(file35, -124, null, true, true);
		// changeSourceToTitle(file34);
		// rotateReferenceNames(file33_216744, 5);
		// setRefCounter(file33_216744, -1);
		// setAffiliationsNull(resultFileDirectory);
		// xStreamToJaxbXML(resultFileDirectory);
		// setAffiliationId(resultFileDirectory, 1);
		// checkAuthorAffiliationsEqualPublicationAffiliations(resultFileDirectory);
		// addDotToReferenceFirstNames(file32_213513);
		// replaceReferenceMarkersWithIdsInSections(file31_204724);
		// setMarkersByReferenceStyle(file31_204724);
		// printRefAuthorYearAndNumber(file31_204724);
		// adaptCermineFirstnames(file31_204724);
		// setKeywords(resultFileDirectory);
		// setRefCounter(file30_203924, (-1), 16);
		// setRefCounter(file30_203924, (1), 10);
		// setNamesNull(resultFileDirectory);
		// checkNameEqualsFirstLast(resultFileDirectory);
		// rewriteXStreamFiles(resultFileDirectory);
		// setRefCounter(file29_202034, (-1));
		// replaceReferenceMarkersWithIdsInSections(file28_247743);
		// setRefCounter(file27_200745, (-1), 12);
		// setRefCounter(file27_200745, (-6), 10);
		// changeSourceToTitle(file27_200745);
		// rotateReferenceNames(file27_200745);
		// checkFirstNameContains(resultFileDirectory);
		// setAffiliationsNull(resultIn);
		// rewriteXStreamFiles(resultFileDirectory);
		// useWorker(resultFileDirectory, new AffiliationCollectorWorker());
		// useWorkerFile(file26, new AffiliationCollectorWorker());
		// checkXMLsAsInput(resultFileDirectory);
		// checkDateStrings(resultFileDirectory);
		// replaceReferenceMarkersWithIdsInSections(file25_200950);
		// setRefCounter(file25_200950, (1), 12);
		// setReferenceMarkerByPdfxFile(file25_200950pdfx, file25_200950);

		// rotateReferenceNames2(file25_200950);
		// changeSourceToTitle(file24_200748);
		// rotateReferenceNames(file24_200748);
		// replaceReferenceMarkersWithIdsInSections(file23_198408);
		// String markers = printAllFirstLastNames(file23_198408grobid);
		// setMarkersByString(file23_198408, "Alves2001 Alves2003 Andrews2005 Basili1987 Basili1996 Doerner2006 Ehrgott2000 Ehrgott2004 Kontio1995 Kunda2003 Lozanotello2002 Maiden1998 Maiden2002
		// Martinsons1998 Medaglia2006 Morisio2002 Navarrete2005 Ncube2002 Neubauer2006 Ruhe2002 Ruhe2003 Ryan2004 Torchiano2004 Tran1997 Stummer2003 Stummer2005 Wanyama2005", " ");
		// setRefCounter(file23_198408, (-1), 4);
		// setRefCounter(file23_198408, (1), null);
		// rotateReferenceNames2(file23_198408, 13);
		// setRefCounter(file22_198400, (-1), 26);
		// setRefCounter(file22_198400, (-1), 25);
		// setRefCounter(file22_198400, (-1), 23);
		// setRefCounter(file22_198400, (9), 10);
		// setRefCounter(file22_198400, (-1), 8);
		// changeSourceToTitle(file22_198400);
		// replaceReferenceMarkersWithIdsInSections(file21_197852);
		// replaceReferenceMarkersWithIdsInSections(file20_197422);
		// removeMarkersFromIds(result17_192724);
		// changeSectionReferenceIdsToIdsWithMarker(file20_197422);
		// setReferenceAuthorToCamelcase(file20_197422);
		// setSourceByTitle(file20_197422);
		// createFirstAndLastNameFromName(file20_197422);
		// setRefId(file20_197422);
		// setReferenceMarkerByPdfxFile(file20_197422pdfx, file20_197422);
		// setRefCounter(file19_194660, (-4), null);
		// changeSectionReferenceIdsToIdsWithMarker(file18_194085);

		// removeMarkersFromIds(result17_192724);
		// changeSectionReferenceIdsToIdsWithMarker(result17_192724);
		// setRefCounter(result16_191715, (-1), 26);
		// setRefCounter(result16_191715, (-1), 28);
		// setRefCounter(result16_191715, (1), 26);
		// setRefCounter(result16_191715, (1), 25);
		// setRefCounter(result16_191715, (-1), 8);

		// vorher oben nach unten, drüber immer aktuelles oben dazu
		// Publication p = XStreamUtil.convertFromXML(file, Publication.class);
		//
		// publicationToFile();
		// getPublicationFromFile2();
		// addPackageNames();
		// removePackageNames();
		// getPublicationFromFile3();

		// checkSerialization(file);
		// setRefCounter(file4, (1));
		// setReferenceMarkerByPdfxFile(file5, file4);
		// concatNames(file4);
		// xpathSampleStackoverflowQuestion();
		// printPageCountForPdfs(Demos.getAllGroundTruthFiles());
		// setRefCounter(file6, (-40));
		// setReferenceMarkerByPdfxFile(file7, file8);
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
		// changeSectionReferenceIdsToReferenceCitationsFile(result12_141758);
		// setRefCounter(result13_168482, (-1), 2);
		// setRefCounter(result13_168482, (1), 10);
		// setRefCounter(result13_168482, (5), 5);
		// setReferenceMarkerByPdfxFile(result13_168482pdfx, result13_168482);
		// setReferenceCitationByMarker(result13_168482);
		// setSectionReferenceIdsFromReferenceCitationKeys(result13_168482);
		// getPdfsOrderedByPageCount();
		// setSectionReferenceIdsFromReferenceIdsAsMarkers(result14_182414);
		// setRefCounter(result15_182899, (2), null);
		// changeSectionReferenceIdsToIdsWithMarker(result15_182899);
		// removeMarkersFromIds(result15_182899);
	}

	/**
	 * Nachzuholende omnipages: 200745 OR 200948 OR 225252 OR 201821 OR 247743
	 */
	private static void checkAllGroundTruthFilesExist(List<String> idList)
	{
		for(String pubId : idList)
		{
			File file = new File(resultFileDirectory, FileNameUtil.getResultFileNameFromID(pubId));
			if(!file.exists()) System.out.println("Exists: " + file.exists() + " " + file);
		}

		for(String pubId : idList)
		{
			File file = new File(groundTruth, FileNameUtil.getPdfFileNameFromID(pubId));
			if(!file.exists()) System.out.println("Exists: " + file.exists() + " " + file);

			file = new File(groundTruth, FileNameUtil.getOmnipageFileNameFromID(pubId));
			if(!file.exists()) System.out.println("Exists: " + file.exists() + " " + file);

			file = new File(groundTruth, FileNameUtil.getPublicationDbFileNameFromID(pubId));
			if(!file.exists()) System.out.println("Exists: " + file.exists() + " " + file);
		}

	}

	private static void checkAllGroundTruthAreInIdList()
	{
		for(File file : groundTruth.listFiles())
		{
			String pubId = PublicationUtil.getIdFromFileNameWithoutPrefix(file);

			System.out.println(pubId + ": " + idList.contains(pubId));
		}

		for(File file : FileUtils.listFiles(Demos.inputDir, null, true))
		{
			String pubId = PublicationUtil.getIdFromFileNameWithoutPrefix(file);

			System.out.println(pubId + ": " + idList.contains(pubId));
		}

	}

	private static void findNotProcessedPDFXFiles(File pdfxOutputDir) throws IOException
	{
		for(File file : pdfxOutputDir.listFiles())
		{
			String string = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
			if(string.contains("504 Gateway Timeout"))
			{
				System.out.println(PublicationUtil.getIdFromFile(file));
			}
		}

	}

	private static Collection<File> getNotAlreadyDone(File file)
	{
		return Arrays.asList(file.listFiles(new FilenameFilter()
		{

			@Override
			public boolean accept(File file, String name)
			{
				return name.endsWith(".pdf") && !alreadyDone.contains(PublicationUtil.getIdFromFile(file));
			}
		}));
	}

	private static void searchBracketContentFromText(File file) throws IOException
	{
		System.out.println(file.getAbsolutePath());
		String string = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		List<String> refs = new ArrayList<>();

		Matcher m = Pattern.compile("\\((.*?)\\)").matcher(string);
		while(m.find())
		{
			String group = m.group(1);
			// System.out.println(group);
			refs.addAll(Arrays.asList(group.split("; ")));

		}
		for(String string2 : refs)
		{
			System.out.println(string2);
		}

	}

	private static void setAffiliationId(File directory, int i)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				Set<Affiliation> affiliations = new LinkedHashSet<>();
				if(publication.getAuthors() != null)
				{
					for(Author author : publication.getAuthors())
					{
						if(author.getAffiliations() != null)
						{
							for(Affiliation affiliation : author.getAffiliations())
							{
								boolean newItem = affiliations.add(affiliation);
								System.out.println(file.getName() + " " + affiliation.getId() + " " + newItem);
								if(newItem)
								{
									int oldId = new Integer(affiliation.getId().replaceAll("aff", ""));
									affiliation.setId("aff" + (oldId + 1));
								}
							}
						}
					}
				}
				XStreamUtil.convertToXmL(publication, file, System.out, true);
				// if(affiliations.size() > 2)
				// {
				// Desktop.getDesktop().open(file);
				// return;
				// }
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}

	}

	private static void checkAuthorAffiliationsEqualPublicationAffiliations(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				Set<Affiliation> authorAff = new HashSet<>();
				Set<Affiliation> publicAff = new HashSet<>();

				for(Author author : publication.getAuthors())
				{
					if(author.getAffiliations() != null)
					{
						authorAff.addAll(author.getAffiliations());
					}
				}
				if(publication.getAffiliations() != null)
				{
					publicAff.addAll(publication.getAffiliations());
				}
				if(publicAff.size() > 0)
				{
					SetView<Affiliation> bla = Sets.intersection(authorAff, publicAff);

					System.out.println(file + " is equal: " + (authorAff.size() == bla.size()));
					// System.out.println(authorAff);
					// System.out.println(publicAff);
					// System.out.println(bla);
					// return;
				}
				else
				{
					System.out.println(file + " empty");
				}
				// XStreamUtil.convertToXmL(publication, file, System.out, true);
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}
	}

	private static void xStreamToJaxbXML(File directory) throws JAXBException
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);
				// Publication publication = new ResultMapperXStreamStyle().unmarshall(file);

				// new ResultMapper().marshall(publication, file);
				XStreamUtil.convertToXmL(publication, file, System.out, true);
				break;
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}
	}

	private static void addDotToReferenceFirstNames(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		// Setzt die Marker an den Referencen in den Referencen
		for(Reference reference : publication.getReferences())
		{
			for(ReferenceAuthor author : reference.getAuthors())
			{
				if(CollectionUtil.isNotEmpty(author.getFirstNames()))
				{
					for(ListIterator<String> iterator = author.getFirstNames().listIterator(); iterator.hasNext();)
					{
						String string = iterator.next();
						if(string.length() == 1)
						{
							iterator.set(string + ".");
						}
					}
				}
			}
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);
	}

	private static void setMarkersByReferenceStyle(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);
		AbstractMarkerStyle markerStyle = markerStyleMap.get(publication.getId());

		if(markerStyle == null)
		{
			System.err.println("markerStyle not set for " + file);
		}

		// Setzt die Marker an den Referencen in den Referencen
		for(Reference reference : publication.getReferences())
		{
			if(markerStyle != null)
			{
				String marker = markerStyle.getMarkerString(reference);
				// if(marker.startsWith("[,"))
				// {
				// marker = "[" + reference.getSource() + ", " + (StringUtils.isEmpty(reference.getNote()) ? reference.getPublicationYear() : reference.getNote()) + "]";
				// }
				reference.setMarker(marker);
			}
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);

	}

	private static void setKeywords(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				for(ListIterator<Section> iterator = publication.getSections().listIterator(); iterator.hasNext();)
				{
					Section section = iterator.next();

					if(section.getTitle().toLowerCase().contains("keyword"))
					{
						System.out.println(section.getLevel());

						publication.setKeywords(String.join("; ", section.getLevel().split(", ")));
						iterator.remove();
						XStreamUtil.convertToXmL(publication, file, System.err, true);
						Desktop.getDesktop().open(file);
						return;
					}

				}

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
	 * Opposite: useWorker(..., new AffiliationCollectorWorker());
	 * 
	 * @param directory
	 */
	private static void setAffiliationsNull(File directory)
	{
		for(File file : directory.listFiles())
		// new FilenameFilter()
		// {
		//
		// @Override
		// public boolean accept(File dir, String name)
		// {
		// return name.endsWith("xstream.xml");
		// }
		// }))
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				publication.setAffiliations(null);

				XStreamUtil.convertToXmL(publication, file, System.err, true);
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}

	}

	private static void checkDateStrings(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				String publicationDateString = publication.getPublicationDateString();
				String day = publication.getPublicationDay();
				String month = publication.getPublicationMonth();
				String year = publication.getPublicationYear();
				boolean isEqual = checkDateString(publicationDateString, day, month, year);

				if(!isEqual) System.out.println(file);
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}
	}

	private static boolean checkDateString(String dateString, String day, String month, String year)
	{
		if(dateString != null)
		{
			String yearFromString = null;
			String monthFromString = null;
			String dayFromString = null;
			// "2011"
			if(dateString.length() == 4)
			{
				yearFromString = new String(dateString);
			}
			// "2011-05"
			else
				if(dateString.length() == 7)
				{
					yearFromString = new String(dateString.substring(0, 4));
					monthFromString = new String(dateString.substring(5, 7));
				}
				// "2011-05-01"
				else
					if(dateString.length() == 10)
					{
						yearFromString = new String(dateString.substring(0, 4));
						monthFromString = new String(dateString.substring(5, 7));
						dayFromString = new String(dateString.substring(8, 10));
						// System.out.println(publication.getId());
						// System.out.println(reference.getTitle());
						// System.out.println(reference.getPublicationYear() + "-" + reference.getPublicationMonth() + " " + reference.getPublicationDay());
					}
			boolean isEqual = myEqual(yearFromString, year) && myEqual(monthFromString, month) && myEqual(dayFromString, day);
			return isEqual;
		}
		else
			return year == null && month == null && day == null;
	}

	private static boolean myEqual(String dayFromString, String day)
	{
		if(dayFromString == null)
		{
			return day == null;
		}
		else
			return dayFromString.equals(day);
	}

	private static void rotateReferenceNames2(File file)
	{
		rotateReferenceNames2(file, null);
	}

	private static void setMarkersByString(File file, String markersString, String splitter)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		List<Reference> references = publication.getReferences();
		String[] markers = markersString.split(splitter);
		int x = 0;
		for(Reference reference : references)
		{
			reference.setMarker("[" + markers[x++] + "]");
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);
	}

	private static String printAllFirstLastNames(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		Map<String, String> map = getReferenceMarkerIdMap(publication);
		StringBuffer sb = new StringBuffer();

		for(Reference reference : publication.getReferences())
		{
			if(CollectionUtil.isNotEmpty(reference.getAuthors()))
			{
				String lastName = reference.getAuthors().get(0).getLastName();
				sb.append(lastName + " ");
				System.out.println(lastName);
			}
		}
		return sb.toString();

	}

	private static void changeSourceToTitle(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		Map<String, String> map = getReferenceMarkerIdMap(publication);

		for(Reference reference : publication.getReferences())
		{
			if(StringUtils.isEmpty(reference.getTitle()))
			{
				reference.setTitle(reference.getSource());
				reference.setSource(null);
			}
		}
		XStreamUtil.convertToXmL(publication, file, System.out, true);
	}

	private static void replaceReferenceMarkersWithIdsInSections(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		Map<String, String> map = getReferenceMarkerIdMap(publication);

		for(Section section : publication.getSections())
		{
			if(CollectionUtil.isNotEmpty(section.getReferenceIds()))
			{
				for(ListIterator<String> iterator = section.getReferenceIds().listIterator(); iterator.hasNext();)
				{
					String string = iterator.next();

					string = "[" + string + "]";

					String refId = map.get(string);

					if(refId == null)
					{
						System.err.println("no id for marker " + string + " found");
						iterator.set("TODO:" + string);
					}
					else
					{
						iterator.set(refId);
					}
				}
			}
		}
		XStreamUtil.convertToXmL(publication, file, System.out, true);
	}

	private static void createFirstAndLastNameFromName(File file)
	{
		// Publication publication = XStreamUtil.convertFromXML(file, Publication.class);
		//
		// for(Reference reference : publication.getReferences())
		// {
		// for(ReferenceAuthor author : reference.getAuthors())
		// {
		// List<String> firstNames = new ArrayList<>();
		// for(ListIterator<String> iterator = Arrays.asList(author.name.split(" ")).listIterator(); iterator.hasNext();)
		// {
		// String string = iterator.next();
		// if(iterator.hasNext())
		// {
		// firstNames.add(string);
		// }
		// else
		// {
		// author.setLastName(string);
		// }
		// }
		// author.setFirstNames(firstNames);
		// author.name = null;
		// }
		// }
		//
		// XStreamUtil.convertToXmL(publication, file, System.out, true);
		System.err.println("wieder einkommentieren");
	}

	private static void setSourceByTitle(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		for(Reference reference : publication.getReferences())
		{
			String title = reference.getTitle();
			if(title != null && title.indexOf(".") != -1)
			{
				String titleNew = title.substring(0, title.indexOf("."));
				String sourceNew = title.substring(title.indexOf(".") + 1, title.length());
				reference.setTitle(titleNew);
				reference.setSource(sourceNew);
			}
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);

	}

	private static void setReferenceAuthorToCamelcase(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		for(Reference reference : publication.getReferences())
		{
			for(ReferenceAuthor author : reference.getAuthors())
			{
				String lastName = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, author.getLastName());
				author.setLastName(lastName);
				System.out.println(lastName);

			}
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);

	}

	private static void setRefId(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		Integer x = 1;
		for(Reference reference : publication.getReferences())
		{
			reference.setId(ReferenceUtil.getRefIdFromNumber(x++));
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);

	}

	/*
	 * opposite to changeSectionReferenceIdsToIdsWithMarker
	 */
	private static void removeMarkersFromIds(File file)
	{

		try
		{
			Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

			for(Section section : publication.getSections())
			{
				if(section.getReferenceIds() != null)
				{
					for(ListIterator<String> iterator = section.getReferenceIds().listIterator(); iterator.hasNext();)
					{
						String referenceId = iterator.next();
						try
						{
							iterator.set(referenceId.substring(0, referenceId.indexOf("-")));
						}
						catch(Exception e)
						{
							System.out.println();
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

	private static void setSectionReferenceIdsFromReferenceIdsAsMarkers(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		Map<String, String> map = getReferenceMarkerIdMap(publication);

		for(Section section : publication.getSections())
		{
			if(CollectionUtils.isNotEmpty(section.getReferenceIds()))
			{
				List<String> newIds = new ArrayList<>();
				for(String id : section.getReferenceIds())
				{
					String newId = map.get(id);
					if(newId == null)
					{
						System.err.println(id);
					}
					newIds.add(new String(newId));
				}
				section.getReferenceIds().clear();
				section.getReferenceIds().addAll(newIds);
			}
		}

		XStreamUtil.convertToXmL(publication, file, System.err, true);

	}

	private static Map<String, Reference> getReferenceMap(Publication publication)
	{
		Map<String, Reference> map = new HashMap<>();
		for(Reference reference : publication.getReferences())
		{
			map.put(reference.getId(), reference);
		}
		return map;
	}

	private static Map<String, String> getReferenceMarkerIdMap(Publication publication)
	{
		Map<String, String> map = new HashMap<>();
		for(Reference reference : publication.getReferences())
		{
			map.put(reference.getMarker(), reference.getId());
		}
		return map;
	}

	private static Map<String, String> getReferenceIdMarkerMap(Publication publication)
	{
		Map<String, String> map = new HashMap<>();
		for(Reference reference : publication.getReferences())
		{
			map.put(reference.getId(), reference.getMarker());
		}
		return map;
	}

	/**
	 * all masterthesis that are not in the ground truth
	 */
	private static void getPdfsOrderedByPageCount() throws IOException
	{
		List<File> list = Arrays.asList(groundTruthDiplomarbeiten.listFiles(new FilenameFilter()
		{

			@Override
			public boolean accept(File dir, String name)
			{
				return name.endsWith(".pdf");
			}
		}));

		Map<String, File> fileMap = list.stream().collect(Collectors.toMap(file -> PublicationUtil.getIdFromFile(file), item -> item));

		for(File file : Demos.getAllGroundTruthFiles())
		{
			File f = fileMap.remove(PublicationUtil.getIdFromFile(file));
			// System.out.println(f);
		}
		printPageCountForPdfs(fileMap.values());
	}

	/**
	 * Setzt Section.referenceCitation.id by Section.referenceCitation.marker mit Reference.marker
	 */
	private static void setReferenceCitationByMarker(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		Map<String, String> map = new HashMap<>();
		for(Reference reference : publication.getReferences())
		{
			map.put(reference.getMarker(), reference.getId());
		}

		for(Section section : publication.getSections())
		{
			if(CollectionUtils.isNotEmpty(section.getReferenceCitations()))
			{
				for(ReferenceCitation referenceCitation : section.getReferenceCitations())
				{
					referenceCitation.setReferenceId(map.get(referenceCitation.getMarker()));
				}
			}
		}

		XStreamUtil.convertToXmL(publication, file, System.err, true);

	}

	/**
	 * Splits Firstname in multiple Firstnames "A. H." to "A.", "H."<br>
	 * Only for Cermine.
	 * 
	 * @param file
	 */
	private static void adaptCermineFirstnames(File file)
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
						author.getFirstNames().addAll(Arrays.asList(s.split(" ")));
					}

				}

			}
		}
		XStreamUtil.convertToXmL(publication, file, System.err, true);
	}

	/**
	 * Splits Firstname with whitespace
	 * 
	 * @param file
	 */
	private static void splitFirstNames(File file)
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
						for(ListIterator<String> iterator = author.getFirstNames().listIterator(); iterator.hasNext();)
						{
							String s = iterator.next();
							if(s.contains(" "))
							{
								List<String> singleNames = Arrays.asList(s.split(" "));

								iterator.remove();
								for(String string : singleNames)
								{
									iterator.add(string);
								}
							}
						}
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
				else
					if(!sub.equals("and"))
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
		setRefCounter(file, i, null, true, true);
	}

	/**
	 * @param file
	 * @param i
	 * @param fromIndex
	 *            from witch index (based on 1)
	 * @param changeReferences
	 *            change ids in reference.id
	 * @param sectionReferences
	 *            change ids of sections.referenceIds
	 */
	private static void setRefCounter(File file, Integer i, Integer fromIndex, boolean sectionReferences, boolean changeReferences)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		// references references
		if(changeReferences)
		{
			List<Reference> references = publication.getReferences();
			references.forEach(p -> p.setId(setId(p.getId(), i, fromIndex)));
		}

		// section references
		if(sectionReferences)
		{
			for(Section section : publication.getSections())
			{
				try
				{
					if(section.getReferenceIds() != null)
					{
						for(final ListIterator<String> iterator = section.getReferenceIds().listIterator(); iterator.hasNext();)
						{
							final String refString = iterator.next();
							iterator.set(setId(refString, i, fromIndex));
						}
						if(section.getReferenceCitations() == null)
						{
							section.setReferenceCitations(new ArrayList<>());
						}
						for(ReferenceCitation referenceCitation : section.getReferenceCitations())
						{
							String refString = referenceCitation.getReferenceId();
							referenceCitation.setReferenceId(setId(refString, i, fromIndex));
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		XStreamUtil.convertToXmL(publication, file, System.out, true);
	}

	private static void rotateReferenceNames(File file)
	{
		rotateReferenceNames(file, null);
	}

	/**
	 * <pre>
		  <authors>
	        <ReferenceAuthor>
	          <firstNames>
	            <string>Feremans</string>
	          </firstNames>
	        </ReferenceAuthor>
	        <ReferenceAuthor>
	          <firstNames>
	            <string>C</string>
	          </firstNames>
	        </ReferenceAuthor>
	      </authors>
	 * </pre>
	 * 
	 * to
	 * 
	 * <pre>
	 	  <authors>
		    <ReferenceAuthor>
		      <firstNames>
		        <string>C</string>
		      </firstNames>
		      <lastName>Feremans</lastName>
		    </ReferenceAuthor>
		  </authors>
	 * </pre>
	 * 
	 **/
	private static void rotateReferenceNames(File file, Integer from)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		for(Reference reference : publication.getReferences())
		{
			if(CollectionUtils.isNotEmpty(reference.getAuthors()))
			{
				Integer refNumber = ReferenceUtil.getReferenceIdNumber(reference);

				if(from == null || (from != null && refNumber != null && refNumber >= from))
				{
					int authorCount = reference.getAuthors().size();

					// set lastname of firstauthor to his lastname
					ReferenceAuthor firstAuthor = reference.getAuthors().get(0);
					if(CollectionUtil.isNotEmpty(firstAuthor.getFirstNames()))
					{
						firstAuthor.setLastName(firstAuthor.getFirstNames().get(0));
					}
					for(int i = 0; (i + 1) < authorCount; i++)
					{
						ReferenceAuthor referenceAuthor1 = reference.getAuthors().get(i);
						ReferenceAuthor referenceAuthor2 = reference.getAuthors().get(i + 1);

						if(referenceAuthor2.getFirstNames() != null)
						{
							referenceAuthor1.setFirstNames(new ArrayList<>(referenceAuthor2.getFirstNames()));
						}
					}
					ReferenceAuthor lastAuthor = reference.getAuthors().get(authorCount - 1);
					if(StringUtils.isEmpty(lastAuthor.getLastName()))
					{
						reference.getAuthors().remove(authorCount - 1);
					}
				}

			}
		}

		XStreamUtil.convertToXmL(publication, file, System.out, true);

	}

	/**
	 * <pre>
	 *  <ReferenceAuthor>
	      <firstNames>
	        <string>]</string>
	        <string>N</string>
	      </firstNames>
	      <lastName>Maiden2002</lastName>
	    </ReferenceAuthor>
	    <ReferenceAuthor>
	      <firstNames>
	        <string>H</string>
	      </firstNames>
	      <lastName>Maiden</lastName>
	    </ReferenceAuthor>
	    <ReferenceAuthor>
	      <lastName>Ncube</lastName>
	    </ReferenceAuthor>
	 * </pre>
	 * 
	 * to
	 * 
	 * <pre>
	 *  <ReferenceAuthor>
	      <firstNames>
	        <string>N</string>
	      </firstNames>
	      <lastName>Maiden</lastName>
	    </ReferenceAuthor>
	    <ReferenceAuthor>
	      <firstNames>
	        <string>H</string>
	      </firstNames>
	      <lastName>Ncube</lastName>
	    </ReferenceAuthor>
	 * </pre>
	 * 
	 * @param file
	 * @param from
	 */
	private static void rotateReferenceNames2(File file, Integer from)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		for(Reference reference : publication.getReferences())
		{
			if(CollectionUtils.isNotEmpty(reference.getAuthors()))
			{
				Integer refNumber = ReferenceUtil.getReferenceIdNumber(reference);

				if(from == null || (from != null && refNumber != null && refNumber >= from))
				{
					int authorCount = reference.getAuthors().size();

					// set lastname of firstauthor to his lastname
					ReferenceAuthor firstAuthor = reference.getAuthors().get(0);
					if(CollectionUtil.isNotEmpty(firstAuthor.getFirstNames()))
					{
						firstAuthor.getFirstNames().remove(0);
					}
					for(int i = 0; (i + 1) < authorCount; i++)
					{
						ReferenceAuthor referenceAuthor1 = reference.getAuthors().get(i);
						ReferenceAuthor referenceAuthor2 = reference.getAuthors().get(i + 1);

						if(referenceAuthor2.getLastName() != null)
						{
							referenceAuthor1.setLastName(referenceAuthor2.getLastName());
						}
					}
					ReferenceAuthor lastAuthor = reference.getAuthors().get(authorCount - 1);
					if(CollectionUtil.isEmpty(lastAuthor.getFirstNames()))
					{
						reference.getAuthors().remove(authorCount - 1);
					}
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

	private static void changeSectionReferenceIdsToReferenceCitationsFileWithMarker(File file)
	{
		try
		{
			Publication publication = XStreamUtil.convertFromXML(file, Publication.class);
			AbstractMarkerStyle markerStyle = markerStyleMap.get(publication.getId());

			// Setzt die Marker an den Referencen in den Referencen
			for(Reference reference : publication.getReferences())
			{
				if(markerStyle != null)
				{
					String marker = markerStyle.getMarkerString(reference);
					// if(marker.startsWith("[,"))
					// {
					// marker = "[" + reference.getSource() + ", " + (StringUtils.isEmpty(reference.getNote()) ? reference.getPublicationYear() : reference.getNote()) + "]";
					// }
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

	private static void changeSectionReferenceIdsToIdsWithMarker(File file)
	{
		try
		{
			Publication publication = XStreamUtil.convertFromXML(file, Publication.class);
			AbstractMarkerStyle markerStyle = markerStyleMap.get(publication.getId());

			// Setzt die Marker an den Referencen in den Referencen
			for(Reference reference : publication.getReferences())
			{
				if(markerStyle != null)
				{
					String marker = markerStyle.getMarkerString(reference);
					// if(marker.startsWith("[,"))
					// {
					// marker = "[" + reference.getSource() + ", " + (StringUtils.isEmpty(reference.getNote()) ? reference.getPublicationYear() : reference.getNote()) + "]";
					// }
					reference.setMarker(marker);
				}
			}

			for(Section section : publication.getSections())
			{
				if(section.getReferenceIds() != null)
				{
					for(ListIterator<String> iterator = section.getReferenceIds().listIterator(); iterator.hasNext();)
					{
						String referenceId = iterator.next();
						if(markerStyle != null)
						{
							int referenceIndex = ReferenceUtil.getReferenceIdNumber(referenceId) - 1;
							Reference reference = publication.getReferences().get(referenceIndex);

							if(!reference.getId().equals(referenceId))
							{
								System.err.println("wrong indices");
							}
							iterator.set(referenceId + "-" + reference.getMarker());

							// ReferenceCitation referenceCitation = new ReferenceCitation(referenceId, reference.getMarker());
							// section.getReferenceCitations().add(referenceCitation);
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
		if(!resultFileDirectory.exists())
		{
			System.out.println(resultFileDirectory.toString() + "does not exist");
		}
		List<String> ids = Arrays.asList(resultFileDirectory.listFiles()).stream().map(f -> PublicationUtil.getIdFromFile(f)).collect(Collectors.toList());

		return ids;
	}

	private static List<String> getAllGroundTruthFileIds()
	{
		List<String> ids = Demos.getAllGroundTruthFiles().stream().map(f -> PublicationUtil.getIdFromFile(f)).collect(Collectors.toList());

		return ids;
	}

	private static void changeSectionReferenceIdsToReferenceCitations(File directory)
	{

		for(File file : directory.listFiles())
		{
			changeSectionReferenceIdsToReferenceCitationsFileWithMarker(file);

		}

	}

	private static void doGrobIdAndMoveToResults() throws IOException, JAXBException
	{
		// Demos.executeDemos();

		List<File> xStreamFiles = Demos.getAllFilesFromDirectories(Demos.grobIdOutputDir, "-xstream.xml");

		for(File file : xStreamFiles)
		{
			if(!alreadyDone.contains(PublicationUtil.getIdFromFile(file)))
			{
				File newFile = new File("D:/output/methods/resultsource", file.getName().replace("grobid", "result"));

				newFile.delete();
				FileUtils.moveFile(file, newFile);
				System.out.println(newFile + " " + new Boolean(file.renameTo(newFile)).toString());
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
		if(!directory.isDirectory())
		{
			System.err.println("only for directory");
		}
		for(File file : directory.listFiles())
		{
			useWorkerFile(file, worker);
		}
	}

	private static void useWorkerFile(File file, Worker worker)
	{
		AbstractMetaPublication publication = XStreamUtil.convertFromXML(file, Publication.class);
		// worker.doWork(publication);
		XStreamUtil.convertToXmL(publication, file, System.out, true);
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

	private static void checkFirstNameContains(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				for(Author author : publication.getAuthors())
				{
					if(author.getFirstNames() != null)
					{
						for(String firstName : author.getFirstNames())
						{
							if(firstName.contains(" "))
							{
								System.out.println(firstName + "  " + author.getLastName() + " " + file);
								Desktop.getDesktop().open(file);
								Desktop.getDesktop().open(getCorrespondingPDF(file));
								break;
							}
						}
					}
				}
				for(Reference reference : publication.getReferences())
				{
					if(reference.getAuthors() != null)
					{
						for(ReferenceAuthor author : reference.getAuthors())
						{
							if(author.getFirstNames() != null)
							{
								for(String firstName : author.getFirstNames())
								{
									if(firstName.contains(" "))
									{
										System.out.println(firstName + "  " + author.getLastName() + " " + file);
										Desktop.getDesktop().open(file);
										Desktop.getDesktop().open(getCorrespondingPDF(file));
										break;
									}
								}
							}
						}
					}
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

	public static File getCorrespondingPDF(File file)
	{
		String id = PublicationUtil.getIdFromFile(file);

		return new File(groundTruthNoSubdir, id + ".pdf");
	}

	private static void rewriteXStreamFiles(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				AbstractMetaPublication publication = XStreamUtil.convertFromXML(file, Publication.class);

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

	private static void checkXStreamFiles(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				AbstractMetaPublication publication = XStreamUtil.convertFromXML(file, Publication.class);

				System.out.println("ok: " + publication.getId());
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}
	}

	private static void checkNameEqualsFirstLast(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				for(AbstractAuthor author : PublicationUtil.getAllAuthors(publication))
				{
					String concatedName = PublicationUtil.getNameFromAuthor(author);
					String name = author.name;

					if(name != null && (!concatedName.equals(name)))
					{
						System.out.println(file);
						System.out.println(concatedName);
						System.out.println(name);
					}
					if(name != null)
					{
						System.out.println(file + new Boolean(concatedName.equals(name)).toString());
					}
				}

				if(CollectionUtil.isNotEmpty(publication.getReferences()))
				{
					for(Reference reference : publication.getReferences())
					{
						if(CollectionUtil.isNotEmpty(reference.getAuthors()))
						{
							for(ReferenceAuthor referenceAuthor : reference.getAuthors())
							{
								referenceAuthor.name = null;
							}
						}
					}
				}
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
				break;
			}
		}
	}

	private static void setNamesNull(File directory)
	{
		for(File file : directory.listFiles())
		{
			try
			{
				Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

				if(CollectionUtil.isNotEmpty(publication.getAuthors()))
				{
					for(Author author : publication.getAuthors())
					{
						author.name = null;
					}
				}

				if(CollectionUtil.isNotEmpty(publication.getReferences()))
				{
					for(Reference reference : publication.getReferences())
					{
						if(CollectionUtil.isNotEmpty(reference.getAuthors()))
						{
							for(ReferenceAuthor referenceAuthor : reference.getAuthors())
							{
								referenceAuthor.name = null;
							}
						}
					}
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

	/**
	 * Prints References ordered and their index
	 */
	private static void printRefAuthorYearAndNumber(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		AbstractMarkerStyle markerStyle = markerStyleMap.get(publication.getId());
		TreeMap<String, String> map = new TreeMap<>();

		for(Reference reference : publication.getReferences())
		{
			String refString = markerStyle.getMarkerString(reference); // + reference.getNote();
			String refIdString = "[" + ReferenceUtil.getReferenceIdNumber(reference) + "] ";

			String s = map.put(refString, refIdString);
			// if(s != null)
			// {
			// System.err.println(refString + "\t" + s);
			// }
		}
		for(Entry<String, String> entry : map.entrySet())
		{
			System.out.println(entry.getValue() + " " + entry.getKey());
			// System.out.println(entry.getKey() + " " + entry.getValue());
		}

	}

	/**
	 * Prints References ordered and their index
	 */
	private static void checkPrintReferenceIdsInOrder(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		int x = 1;
		for(Reference reference : publication.getReferences())
		{
			System.out.println(ReferenceUtil.getReferenceIdNumber(reference) + new Boolean(ReferenceUtil.getReferenceIdNumber(reference) == x).toString());

			x++;
		}

	}

	/**
	 * Prints References ordered and their index
	 */
	private static void printRefAuthorYearAndNumberForSections(File file)
	{
		Publication publication = XStreamUtil.convertFromXML(file, Publication.class);

		Map<String, Reference> map = getReferenceMap(publication);
		AbstractMarkerStyle markerStyle = markerStyleMap.get(publication.getId());

		for(Section section : publication.getSections())
		{
			System.out.println(section.getTitle());
			for(String referenceId : section.getReferenceIds())
			{
				Reference reference = map.get(referenceId);
				String refString = markerStyle.getMarkerString(reference); // + reference.getNote();

				System.out.println("\t" + refString);
			}
		}

	}

	private static void printPageCountForPdfs(Collection<File> collection) throws IOException
	{
		int pagesCount = 0;
		Map<String, Integer> map = new TreeMap<>();

		for(File file : collection)
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
			JAXBContext jc = (JAXBContext)JAXBContextFactory.createContext(new Class[]{Publication.class}, properties);
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

	private static void setReferenceMarkerByPdfxFile(File pdfxFileWithMarkers, File resultFile) throws Exception
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
			if(s.contains("] "))
			{
				String marker = s.substring(0, s.indexOf("] ")) + "]";
				System.out.println(s);
				markers.add(marker);
				System.out.println(marker);
			}
		}

		Publication publication = XStreamUtil.convertFromXML(resultFile, Publication.class);

		List<Reference> references = publication.getReferences();
		for(Reference reference : references)
		{
			Integer referenceNumber = ReferenceUtil.getReferenceIdNumber(reference);
			if(referenceNumber != null)
			{
				reference.setMarker(markers.get(referenceNumber - 1));
			}
		}

		XStreamUtil.convertToXmL(publication, resultFile, System.out, true);

	}

	private static String setId(String id, Integer i, Integer fromIndex)
	{
		if(id == null) return null;
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
		AbstractMetaPublication p = XStreamUtil.convertFromXML(file, Publication.class);
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
		AbstractMetaPublication p = XStreamUtil.convertFromXML(file3, Publication.class);

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
		AbstractMetaPublication p2 = PublicationFactory.createPublication();

		XStreamUtil.convertToXmL(p2, file2, System.out, true);

	}

	private static void getPublicationFromFile2()
	{
		AbstractMetaPublication p = XStreamUtil.convertFromXML(file2, Publication.class);

	}

	static Map<String, AbstractMarkerStyle> markerStyleMap = new HashMap<>();
	static
	{
		markerStyleMap.put("TUW-137078", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-138011", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-138447", new SuperscriptNumberedMarkerStyle());
		markerStyleMap.put("TUW-138544", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-138547", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-139299", new SquareBracketNameYearMarkerStyle());
		markerStyleMap.put("TUW-139761", new SquareBracketNameYearShortMarkerStyle());// new SquareBracketNameYearShortMarkerStyle());
		markerStyleMap.put("TUW-139769", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-139781", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-139785", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-140047", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-140048", new SquareBracketNameYearShortMarkerStyle());
		markerStyleMap.put("TUW-140229", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-140253", new RoundBracketNameYearMarkerStyle());
		markerStyleMap.put("TUW-140308", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-140533", new NameSquareBracketYearMarkerStyle());
		markerStyleMap.put("TUW-140867", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-140895", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-140983", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-141024", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-141065", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-141121", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-141140", new RoundBracketNameYearMarkerStyle());
		markerStyleMap.put("TUW-141336", new RoundBracketNameYearMarkerStyle());
		markerStyleMap.put("TUW-141618", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-141758", new SquareBracketNameYearMarkerStyle());
		markerStyleMap.put("TUW-168222", new SquareBracketNumberedMarkerStyle());
		markerStyleMap.put("TUW-182899", new RoundBracketNameYearMarkerStyle());

		markerStyleMap.put("TUW-192724", new SquareBracketNameYearMarkerStyle());
		markerStyleMap.put("TUW-194085", new NameSquareBracketYearMarkerStyle());
		markerStyleMap.put("TUW-204724", new RoundBracketNameYearMarkerStyle());
		markerStyleMap.put("TUW-228620", new RoundBracketNameYearMarkerWithoutComaStyle());
		markerStyleMap.put("TUW-245336", new NameSquareBracketYearMarkerStyle());
		markerStyleMap.put("TUW-255712", new RoundBracketShortNameYearMarkerWithoutComaStyle2());
	}
}
