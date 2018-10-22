package misc;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
		files.put(Config.allPerEvalTypePublicationFormatedArrows, Arrays.asList(Config.allPerEvalTypePublication));
		files.put(Config.allPerEvalTypePublicationFormatedBars, Arrays.asList(Config.allPerEvalTypePublication));
		files.put(Config.allPerEvalTypePublicationFormatedColors, Arrays.asList(Config.allPerEvalTypePublication));

		files.put(Config.allPerEvalTypeReferenceFormatedArrows, Arrays.asList(Config.allPerEvalTypeReference));
		files.put(Config.allPerEvalTypeReferenceFormatedBars, Arrays.asList(Config.allPerEvalTypeReference));
		files.put(Config.allPerEvalTypeReferenceFormatedColors, Arrays.asList(Config.allPerEvalTypeReference));

		files.put(Config.allBestPerEvalTypePublication, Arrays.asList(Config.allPerEvalTypePublicationFormatedArrows));
		files.put(Config.allBestPerEvalTypeReference, Arrays.asList(Config.allPerEvalTypeReferenceFormatedArrows));

		files.put(Config.allDeltaPublication, Arrays.asList(Config.allPerEvalTypePublication));
		files.put(Config.allDeltaReference, Arrays.asList(Config.allPerEvalTypeReference));

		files.put(Config.grobidOverview, Arrays.asList(new File(Config.statisticsFolder, "grobid-per-evaltype-publication-statistics.xls"), new File(Config.statisticsFolder, "grobid-per-id-publication-statistics.xls"), new File(Config.statisticsFolder, "grobid-per-evaltype-reference-statistics.xls"), new File(Config.statisticsFolder, "grobid-per-id-reference-statistics.xls")));
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

			try
			{
				Workbook wb = WorkbookFactory.create(file);

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

				wb.close();
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
