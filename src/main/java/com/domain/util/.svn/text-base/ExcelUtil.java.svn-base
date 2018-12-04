package com.domain.util;

import java.io.InputStream;
import java.sql.ResultSet;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {

	/**
	 * 将结果集写入到工作簿的一个sheet中
	 * 
	 * @param rs
	 *            结果集
	 * @param wb
	 *            工作簿
	 * @param headers
	 *            表头
	 * @throws Exception
	 */
	public static void fillExcelData(ResultSet rs, Workbook wb, String[] headers) throws Exception {
		int rowIndex = 0; // 定义一个行索引
		Sheet sheet = wb.createSheet(); // 创建一个sheet
		Row row = sheet.createRow(rowIndex++); // 创建第一行
		for (int i = 0; i < headers.length; i++) {
			row.createCell(i).setCellValue(headers[i]); // 写入表头
		}
		while (rs.next()) { // 循环写入每一行
			row = sheet.createRow(rowIndex++);
			for (int i = 0; i < headers.length; i++) {
				row.createCell(i).setCellValue(rs.getObject(i + 1).toString()); // 注意jdbc中的下标是从1开始
			}
		}
	}

	/**
	 * 利用模版导出数据 1.读入模版 2.读取模版 3.写入数据
	 * 
	 * @param rs
	 * @param templateFileName
	 * @return wb
	 * @throws Exception
	 */
	public static Workbook fillExcelDataByTemplate(ResultSet rs, String templateFileName) throws Exception {
		InputStream inp=ExcelUtil.class.getResourceAsStream("/com/asiainfo/template/"+templateFileName);
		POIFSFileSystem fs=new POIFSFileSystem(inp);
		Workbook wb = new HSSFWorkbook(fs);
		Sheet sheet=wb.getSheetAt(0);
		// 获取列数
		int cellNums=sheet.getRow(0).getLastCellNum();
		int rowIndex=1;
		while(rs.next()){
			Row row=sheet.createRow(rowIndex++);
			for(int i=0;i<cellNums;i++){
				row.createCell(i).setCellValue(rs.getObject(i+1).toString());
			}
		}
		ExcelExtractor ee = new ExcelExtractor((HSSFWorkbook) wb);
		System.out.println(ee.getText());
		return wb;
	}

	/**
	 * 设置格式
	 * @param hssfCell
	 * @return
	 */
	public static String formatCell(HSSFCell hssfCell) {
		if(hssfCell==null){
			return "";
		}else{
			if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_BOOLEAN){
				return String.valueOf(hssfCell.getBooleanCellValue());
			}else if(hssfCell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
				return String.valueOf(hssfCell.getNumericCellValue());
			}else{
				return String.valueOf(hssfCell.getStringCellValue());
			}
		}
	}
}
