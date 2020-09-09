package misc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.ListUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import config.Config;

public class ExcelRefreshFormulas
{
	static Map<File, List<File>> files = new HashMap<>();

	static
	{
		List<File> methodPub = new ArrayList<>();
		methodPub.add(new File(Config.statisticsFolder, "cermine-per-evaltype-publication-statistics.xls"));
		methodPub.add(new File(Config.statisticsFolder, "grobid-per-evaltype-publication-statistics.xls"));
		methodPub.add(new File(Config.statisticsFolder, "parscit-per-evaltype-publication-statistics.xls"));
		methodPub.add(new File(Config.statisticsFolder, "pdfx-per-evaltype-publication-statistics.xls"));
		methodPub.add(Config.allPerEvalTypePublication);
		

		List<File> methodRef = new ArrayList<>();
		methodRef.add(new File(Config.statisticsFolder, "cermine-per-evaltype-reference-statistics.xls"));
		methodRef.add(new File(Config.statisticsFolder, "grobid-per-evaltype-reference-statistics.xls"));
		methodRef.add(new File(Config.statisticsFolder, "parscit-per-evaltype-reference-statistics.xls"));
		methodRef.add(new File(Config.statisticsFolder, "pdfx-per-evaltype-reference-statistics.xls"));
		methodRef.add(Config.allPerEvalTypePublication);
		
		// key file is dependent from value files
		files.put(Config.allPerEvalTypePublicationFormatedArrows, ListUtils.union(methodPub, Arrays.asList(Config.allPerEvalTypePublication)));
		files.put(Config.allPerEvalTypePublicationFormatedBars, ListUtils.union(methodPub, Arrays.asList(Config.allPerEvalTypePublication)));
		files.put(Config.allPerEvalTypePublicationFormatedColors, ListUtils.union(methodPub, Arrays.asList(Config.allPerEvalTypePublication)));

		files.put(Config.allPerEvalTypeReferenceFormatedArrows, ListUtils.union(methodRef, Arrays.asList(Config.allPerEvalTypeReference)));
		files.put(Config.allPerEvalTypeReferenceFormatedBars, ListUtils.union(methodRef, Arrays.asList(Config.allPerEvalTypeReference)));
		files.put(Config.allPerEvalTypeReferenceFormatedColors, ListUtils.union(methodRef, Arrays.asList(Config.allPerEvalTypeReference)));

		files.put(Config.allBestPerEvalTypePublication, ListUtils.union(methodPub, Arrays.asList(Config.allPerEvalTypePublicationFormatedArrows)));
		files.put(Config.allBestPerEvalTypeReference, ListUtils.union(methodRef, Arrays.asList(Config.allPerEvalTypeReferenceFormatedArrows)));

		files.put(Config.allDeltaPublication, ListUtils.union(methodPub, Arrays.asList(Config.allPerEvalTypePublication)));
		files.put(Config.allDeltaReference, ListUtils.union(methodRef, Arrays.asList(Config.allPerEvalTypeReference)));

		List<File> grobidFiles = new ArrayList<>();
		grobidFiles.add(new File(Config.statisticsFolder, "grobid-per-evaltype-publication-statistics.xls"));
		grobidFiles.add(new File(Config.statisticsFolder, "grobid-per-id-publication-statistics.xls"));
		grobidFiles.add(new File(Config.statisticsFolder, "grobid-per-evaltype-reference-statistics.xls"));
		grobidFiles.add(new File(Config.statisticsFolder, "grobid-per-id-reference-statistics.xls"));
		files.put(Config.grobidOverview, grobidFiles);
	}

	public static void main(String[] args)
	{
		refreshReferences();
	}

	public static void refreshReferences()
	{
		for(Entry<File, List<File>> entry : files.entrySet())
		{
			File file = entry.getKey();
			List<File> refFiles = entry.getValue();

			try (Workbook wb = WorkbookFactory.create(file))
			{
				// Create a FormulaEvaluator to use
				FormulaEvaluator mainWorkbookEvaluator = wb.getCreationHelper().createFormulaEvaluator();

				// Track the workbook references
				Map<String, FormulaEvaluator> workbooks = new HashMap<>();
				// Add this workbook
				workbooks.put(file.getName(), mainWorkbookEvaluator);

				addToWorkbook(refFiles, workbooks);

				// Attach them
				mainWorkbookEvaluator.setupReferencedWorkbooks(workbooks);

				// Evaluate
				mainWorkbookEvaluator.evaluateAll();
			}
			catch(Exception e)
			{
				System.out.println(file);
				e.printStackTrace();
			}
		}
	}

	private static void addToWorkbook(List<File> refFiles, Map<String, FormulaEvaluator> workbooks) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		if(refFiles == null) return;
		for(File refFile : refFiles)
		{
			Workbook refWb = WorkbookFactory.create(refFile);
			workbooks.put(refFile.getName().replaceAll(" ", "%20"), refWb.getCreationHelper().createFormulaEvaluator());

			List<File> refRefFiles = files.get(refFile);
			addToWorkbook(refRefFiles, workbooks);
		}
	}
}