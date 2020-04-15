package demos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jdom.DocType;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.google.common.collect.Lists;

import method.Method;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.structure.model.BxDocument;
import pl.edu.icm.cermine.structure.model.BxImage;
import pl.edu.icm.cermine.structure.transformers.BxDocumentToTrueVizWriter;
import utils.FileCollectionUtil;

public class CermineDemo extends AbstractDemo
{
	private static final Method METHOD = Method.CERMINE;

	private static final boolean EXTRACT_ZONES = true;
	private static final boolean EXTRACT_IMAGES = true;
	private static final boolean EXTRACT_TEXT = true;
	private static final boolean EXTRACT_TRUEVIZ = true;
	private static final boolean EXTRACT_JATS = true;

	private static final String FILEEXTENSION_TEXT = ".txt";
	private static final String FILEEXTENSION_TRUEVIZ = ".trueviz";
	private static final String FILEEXTENSION_ZONES = ".zone";
	private static final String FILEEXTENSION_IMAGES = ".images";

	public static void main(String[] args) throws Exception
	{
		/*List<File> groundTruthFiles = new ArrayList<>();
		groundTruthFiles.add(Demos.getAllGroundTruthFiles().subList(0, 1));
		groundTruthFiles.add(new File("D:\\output\\methods\\GroundTruthNoSubDir\\TUW-176845.pdf"));*/

		List<String> idList = new ArrayList<>();

		idList.add("205933");
		new CermineDemo().runDemoList(FileCollectionUtil.getAllGroundTruthFilesByIds(idList), Demos.cermineOutputDir);
		// ContentExtractor.main(new String[]
		// {"-path", "D:/output/GroundTruth-subset"});// , "-outputs", "trueviz"});// , "-str", "D:/output/GroundTruth-subset/file.txt"});

	}

	@Override
	String runDemoSingleFile(File inputFile, File outputFile) throws Exception
	{
		ContentExtractor extractor = new ContentExtractor();

		InputStream inputStream = new FileInputStream(inputFile);
		extractor.setPDF(inputStream);
		Element result = extractor.getContentAsNLM();
		String resultString = new XMLOutputter(Format.getPrettyFormat()).outputString(result);

		FileUtils.writeStringToFile(outputFile, resultString, StandardCharsets.UTF_8);

		// adapted from ContentExtractor.main
		if(EXTRACT_IMAGES)
		{
			File imagesOutputFolder = replaceFileExtension(outputFile, FILEEXTENSION_IMAGES);
			List<BxImage> images = extractor.getImages(imagesOutputFolder.getPath());
			FileUtils.forceMkdir(imagesOutputFolder);
			for(BxImage image : images)
			{
				ImageIO.write(image.getImage(), "png", new File(image.getPath()));
			}
			if(Files.list(Paths.get(imagesOutputFolder.getPath())).count() == 0)
			{
				imagesOutputFolder.delete();
			}
		}
		if(EXTRACT_JATS)
		{
			Element jats;
			if(EXTRACT_IMAGES)
			{
				File imagesOutputFolder = replaceFileExtension(outputFile, FILEEXTENSION_IMAGES);
				jats = extractor.getContentAsNLM(imagesOutputFolder.getPath());
			}
			else
			{
				jats = extractor.getContentAsNLM(null);
			}
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			DocType dt = new DocType("article", "-//NLM//DTD JATS (Z39.96) Journal Archiving and Interchange DTD v1.0 20120330//EN", "JATS-archivearticle1.dtd");
			FileUtils.writeStringToFile(outputFile, outputter.outputString(dt), StandardCharsets.UTF_8);
			FileUtils.writeStringToFile(outputFile, "\n", StandardCharsets.UTF_8, true);
			FileUtils.writeStringToFile(outputFile, outputter.outputString(jats), StandardCharsets.UTF_8, true);
		}

		if(EXTRACT_TRUEVIZ)
		{
			BxDocument doc = extractor.getBxDocumentWithSpecificLabels();
			BxDocumentToTrueVizWriter writer = new BxDocumentToTrueVizWriter();
			File truevizFile = replaceFileExtension(outputFile, FILEEXTENSION_TRUEVIZ);
			Writer fw = new OutputStreamWriter(new FileOutputStream(truevizFile), StandardCharsets.UTF_8);
			writer.write(fw, Lists.newArrayList(doc), StandardCharsets.UTF_8);
		}

		if(EXTRACT_ZONES)
		{
			Element text = extractor.getLabelledFullText();
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			File zoneFile = replaceFileExtension(outputFile, FILEEXTENSION_ZONES);
			FileUtils.writeStringToFile(zoneFile, outputter.outputString(text), StandardCharsets.UTF_8);
		}

		if(EXTRACT_TEXT)
		{
			String text = extractor.getRawFullText();
			File txtFile = replaceFileExtension(outputFile, FILEEXTENSION_TEXT);
			FileUtils.writeStringToFile(txtFile, text, StandardCharsets.UTF_8);
		}

		return null;
	}

	private File replaceFileExtension(File outputFile, String extension)
	{
		return new File(outputFile.getParent(), FilenameUtils.removeExtension(outputFile.getName()) + extension);
	}

	private File replaceFileExtension(String outputFile, String extension)
	{
		return replaceFileExtension(new File(outputFile), extension);
	}

	@Override
	protected Method getMethod()
	{
		return METHOD;
	}
}
