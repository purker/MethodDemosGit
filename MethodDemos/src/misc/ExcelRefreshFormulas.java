package misc;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import config.Config;

public class ExcelRefreshFormulas
{
	public static void main(String[] args)
	{
		refreshReferences();
	}

	public static void refreshReferences()
	{
		Map<File, File> files = new HashMap<>();
		files.put(Config.allPerEvalTypePublicationFormatedArrows, Config.allPerEvalTypePublication);
		files.put(Config.allPerEvalTypePublicationFormatedBars, Config.allPerEvalTypePublication);
		files.put(Config.allPerEvalTypePublicationFormatedColors, Config.allPerEvalTypePublication);

		files.put(Config.allPerEvalTypeReferenceFormatedArrows, Config.allPerEvalTypeReference);
		files.put(Config.allPerEvalTypeReferenceFormatedBars, Config.allPerEvalTypeReference);
		files.put(Config.allPerEvalTypeReferenceFormatedColors, Config.allPerEvalTypeReference);

		files.put(Config.allBestPerEvalTypePublication, Config.allPerEvalTypePublicationFormatedArrows);
		files.put(Config.allBestPerEvalTypeReference, Config.allPerEvalTypeReferenceFormatedArrows);

		files.put(Config.allDeltaPublication, Config.allPerEvalTypePublication);
		files.put(Config.allDeltaReference, Config.allPerEvalTypeReference);

		for(Entry<File, File> entry : files.entrySet())
		{
			File file = entry.getKey();
			File refFile = entry.getValue();

			try
			{
				Workbook wb = WorkbookFactory.create(file);
				Workbook refWb = WorkbookFactory.create(refFile);

				// Create a FormulaEvaluator to use
				FormulaEvaluator mainWorkbookEvaluator = wb.getCreationHelper().createFormulaEvaluator();

				// Track the workbook references
				Map<String, FormulaEvaluator> workbooks = new HashMap<>();
				// Add this workbook
				workbooks.put(file.getName(), mainWorkbookEvaluator);
				// Add two others
				workbooks.put(refFile.getName().replaceAll(" ", "%20"), refWb.getCreationHelper().createFormulaEvaluator());

				File refRefFile = files.get(refFile);
				if(refRefFile != null)
				{
					Workbook refRefWb = WorkbookFactory.create(refFile);
					workbooks.put(refRefFile.getName().replaceAll(" ", "%20"), refRefWb.getCreationHelper().createFormulaEvaluator());
				}

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
}
