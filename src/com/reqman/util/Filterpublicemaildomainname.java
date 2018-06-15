package com.reqman.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.UploadedFile;

import com.reqman.vo.NewrequestVo;
import com.reqman.vo.PublicemaildomainVo;
import com.reqman.vo.requestNoteVo;

public class Filterpublicemaildomainname {
	
/*	
public static void readXLSFile() throws IOException
	
	{
		InputStream ExcelFileToRead = new FileInputStream("C:/Users/user/Desktop/category.xls");
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);

		HSSFSheet sheet=wb.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;

		Iterator rows = sheet.rowIterator();

		while (rows.hasNext())
		{
			row=(HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
		
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
	
	}
	*/
	
	
	@SuppressWarnings("rawtypes")
	public static List<PublicemaildomainVo> readXLSXFile(UploadedFile excelfile) throws Exception {
		List<PublicemaildomainVo> domainnameList = new ArrayList<PublicemaildomainVo>();
		PublicemaildomainVo publicemaildomainVo = null;
		XSSFSheet sheet = null;
		XSSFRow row;
		XSSFCell cell;
		try {

			String filename = excelfile.getFileName() != null ? excelfile.getFileName() : "";

			InputStream ExcelFileToRead = new FileInputStream(filename);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

			// XSSFWorkbook test = new XSSFWorkbook();
			if (wb != null) {
				sheet = wb.getSheetAt(0);
			}
			if (sheet != null) {

				Iterator rows = sheet.rowIterator();

				while (rows.hasNext()) {
					row = (XSSFRow) rows.next();
					Iterator cells = row.cellIterator();
					while (cells.hasNext()) {
						cell = (XSSFCell) cells.next();

						if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
							String name = cell.getStringCellValue();

							publicemaildomainVo = new PublicemaildomainVo();
							publicemaildomainVo.setName(name);
							publicemaildomainVo.setStatus(true);

						}

					}
					domainnameList.add(publicemaildomainVo);
					System.out.println("domainlist--" + domainnameList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

		return domainnameList;
	}

}
