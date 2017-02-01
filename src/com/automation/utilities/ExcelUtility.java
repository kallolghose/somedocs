package com.automation.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

public class ExcelUtility 
{
	private static HashMap<String,CodeUpdateDataClass> codeHM = new HashMap<String,CodeUpdateDataClass>();
	
	public static void readExcelToHM(String excelPath)
	{
		try
		{
			FileInputStream file = new FileInputStream(new File("howtodoinjava_demo.xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(file); 
			XSSFSheet sheet = workbook.getSheet("Mapping");
			Iterator<Row> itr = sheet.iterator();
			while(itr.hasNext())
			{
				Row row = itr.next();
				CodeUpdateDataClass data = new CodeUpdateDataClass();
				data.setTag(row.getCell(0).getStringCellValue());
				data.setViolation(row.getCell(1).getStringCellValue());
				data.setCode_Change(row.getCell(2).getStringCellValue());
				data.setOperation(row.getCell(3).getStringCellValue());
				data.setFile(row.getCell(4).getStringCellValue());
				codeHM.put(data.getTag(), data);
			}
			workbook.close();
		}
		catch(Exception e)
		{
			System.out.println("[ExcelUtility  - readExcel()] : Exception - " + e.getMessage());
			e.printStackTrace();
		}
	}
	
}
