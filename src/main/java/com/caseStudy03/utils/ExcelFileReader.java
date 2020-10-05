package com.caseStudy03.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader {
	public static Object[][] readExcel(String filePath) {
		File file = new File(filePath);
		DataFormatter objDefaultFormat = new DataFormatter();
		Map<String, String> datamap = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		FormulaEvaluator objFormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
		XSSFSheet sheet = wb.getSheetAt(0);
		try {
			wb.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int lastRowNum = sheet.getLastRowNum();
		Object[][] obj = new Object[lastRowNum][1];
		Iterator<Row> rowIterator = sheet.rowIterator();
		int i = 0;
		datamap = new HashMap<String, String>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				String rowName = cellIterator.next().getStringCellValue();
				//System.out.print("row : "+rowName);
				Cell cellValue = cellIterator.next();
				objFormulaEvaluator.evaluate(cellValue);
				String cellValueStr = objDefaultFormat.formatCellValue(cellValue, objFormulaEvaluator);
				//System.out.println("value : "+cellValueStr);
				datamap.put(rowName, cellValueStr);
			}
			try {
				obj[i][0] = datamap;
			} catch (Exception e) {
			}
			i++;
		}
		return new Object[][] { { datamap } };
	}
}
