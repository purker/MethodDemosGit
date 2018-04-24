package evaluation.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import config.Config;
import utils.FileCollectionUtil;

public class ExcelWriter extends AbstractWriter
{
	private static final WriterType WRITERTYPE = WriterType.EXCEL;

	private static final String patternNonDecimal = "0";
	private static String patternDecimal;

	static
	{
		patternDecimal = StringUtils.repeat("0", Config.decimalPlaces);
		if(!patternDecimal.equals(""))
			patternDecimal = "0." + patternDecimal;
		else
			patternDecimal = patternNonDecimal;
	}

	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private File file;

	private int rowNum = 0;
	private int columnCount = 0;

	private HSSFCellStyle styleDefault;
	private HSSFCellStyle styleNumberNonDecimal;
	private HSSFCellStyle styleNumberDecimal;

	public ExcelWriter(String fileName)
	{
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet("Tabelle 1");
		this.file = new File(FileCollectionUtil.replaceFileExtension(fileName, getWriterType()));

		// // XSSF=xlsx version
		// POIXMLProperties props = workbook.getProperties();
		// PackagePropertiesPart ppropsPart = props.getCoreProperties().getUnderlyingProperties();
		// ppropsPart.setCreatedProperty(new Nullable<>(new Date(0))); // for same creationdate, that files with same data are equal

		// HSSF=xls version
		workbook.createInformationProperties();
		SummaryInformation props = workbook.getSummaryInformation();
		props.setCreateDateTime(new Date(0)); // for same creationdate, that files with same data are equal

		styleDefault = workbook.createCellStyle(); // Font and alignment
		styleDefault.setVerticalAlignment(VerticalAlignment.TOP);

		styleNumberNonDecimal = workbook.createCellStyle();
		styleNumberNonDecimal.cloneStyleFrom(styleDefault);
		styleNumberNonDecimal.setDataFormat(workbook.createDataFormat().getFormat(patternNonDecimal));

		styleNumberDecimal = workbook.createCellStyle();
		styleNumberDecimal.cloneStyleFrom(styleDefault);
		styleNumberDecimal.setDataFormat(workbook.createDataFormat().getFormat(patternDecimal));

	}

	@Override
	public WriterType getWriterType()
	{
		return WRITERTYPE;
	}

	@Override
	public void writeNext(Collection<String> line)
	{
		Row row = sheet.createRow(rowNum++);
		int colNum = 0;
		for(String field : line)
		{
			Cell cell = row.createCell(colNum++);

			if(field == null)
			{
				cell.setCellValue("null");
			}
			else
			{
				CellStyle style = styleDefault;

				try
				{
					String valueString = field.replace(",", ".");
					double value = Double.parseDouble(valueString);

					if(valueString.contains("."))
					{
						style = styleNumberDecimal;
					}
					else
					{
						style = styleNumberNonDecimal;
					}

					cell.setCellType(CellType.NUMERIC);
					cell.setCellValue(value);
				}
				catch(NumberFormatException e)
				{
					// TODO ?etwas unschön
					if(field.startsWith("=HYPERLINK"))
					{
						cell.setCellFormula(field.substring(1));
					}
					else
					{
						cell.setCellValue(field);
						style.setWrapText(true);
					}
				}

				cell.setCellStyle(style);
			}
		}
		columnCount = Integer.max(row.getLastCellNum(), row.getLastCellNum());
	}

	@Override
	public void close() throws IOException
	{
		IntStream.range(0, columnCount).forEach((columnIndex) -> sheet.autoSizeColumn(columnIndex));
		// IntStream.range(0, rowNum).forEach((columnIndex) -> sheet.aut(columnIndex));

		workbook.write(new FileOutputStream(file));
		workbook.close();

	}

}
