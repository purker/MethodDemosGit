package evaluation.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import config.Config;
import utils.FileCollectionUtil;

public class ExcelWriter extends AbstractWriter
{
	private static final WriterType WRITERTYPE = WriterType.EXCEL;

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private File file;

	private int rowNum = 0;
	private int columnCount = 0;

	public ExcelWriter(String fileName)
	{
		this.workbook = new XSSFWorkbook();
		this.sheet = workbook.createSheet("Tabelle 1");
		this.file = new File(FileCollectionUtil.replaceFileExtension(fileName, getWriterType()));
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
				CellStyle style = workbook.createCellStyle(); // Font and alignment
				style.setVerticalAlignment(VerticalAlignment.TOP);

				try
				{
					String valueString = field.replace(",", ".");
					double value = Double.parseDouble(valueString);

					String pattern = "0";
					if(valueString.contains("."))
					{
						String decimalPattern = StringUtils.repeat("0", Config.decimalPlaces);
						if(!decimalPattern.equals(""))
						{
							pattern += "." + decimalPattern;
						}
					}

					style.setDataFormat(workbook.createDataFormat().getFormat(pattern));
					cell.setCellType(CellType.NUMERIC);
					cell.setCellValue(value);
				}
				catch(NumberFormatException e)
				{
					// TODO ?etwas unsch�n
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

		FileOutputStream outputStream = new FileOutputStream(file);
		workbook.write(outputStream);
		workbook.close();

	}

}
