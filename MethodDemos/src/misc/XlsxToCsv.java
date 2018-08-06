package misc;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;

import config.Config;
import utils.FormatingUtil;

public class XlsxToCsv
{
	public static void convertToCsv() throws Exception
	{
		List<File> files = new ArrayList<>();
		files.add(new File(Config.statisticsFolder + "all-best-method-per-evaltype-publication-statistics.xlsx"));
		files.add(new File(Config.statisticsFolder + "all-best-method-per-evaltype-reference-statistics.xlsx"));
		files.add(new File(Config.statisticsFolder + "all-delta-precision-recall-publication-statistics.xlsx"));
		files.add(new File(Config.statisticsFolder + "all-delta-precision-recall-reference-statistics.xlsx"));

		for(File file : files)
		{
			writeAsCsv(file);
		}
	}

	private static void writeAsCsv(File file) throws Exception
	{
		File newFile = new File(file.getParent(), file.getName().replaceAll(".xlsx", ".csv"));
		try (FileInputStream inp = new FileInputStream(file))
		{
			Workbook wb = WorkbookFactory.create(inp);

			for(int i = 0; i < wb.getNumberOfSheets(); i++)
			{
				String s = echoAsCSV(wb.getSheetAt(i), wb);

				FileUtils.writeStringToFile(newFile, s, StandardCharsets.UTF_8);
			}
		}
	}

	public static String echoAsCSV(Sheet sheet, Workbook wb)
	{
		StringBuilder sb = new StringBuilder();

		Row row = null;
		for(int i = 0; i <= sheet.getLastRowNum(); i++)
		{
			row = sheet.getRow(i);
			if(row != null)
			{
				for(int j = 0; j < row.getLastCellNum(); j++)
				{
					Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

					if(cell != null)
					{
						String cellValue;
						if(cell.getCellType() == CellType.STRING.getCode())
						{
							cellValue = cell.getStringCellValue();
						}
						else
						{
							cellValue = ((XSSFCell)cell).getRawValue();

							if(cellValue.contains("."))
							{
								cellValue = FormatingUtil.formatDouble(new Double(cellValue));
							}
						}
						sb.append("\"" + cellValue + "\";");
					}
					else
						sb.append(";");
				}
			}
			sb.append("\r\n");
		}

		return sb.toString();
	}
}
