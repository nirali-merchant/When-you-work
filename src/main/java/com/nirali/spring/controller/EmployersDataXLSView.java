package com.nirali.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.nirali.spring.dao.AdminDAO;
import com.nirali.spring.pojo.Employer;

public class EmployersDataXLSView extends AbstractExcelView {

	@Autowired
	AdminDAO adminDao;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub

		adminDao = new AdminDAO();
		List<Employer> employers = adminDao.getAllEmployers();
		System.out.println("employer excep size= " + employers.size());
		HSSFSheet sheet = workbook.createSheet("EmployerList");
		sheet.setDefaultColumnWidth(12);
		List<String> headers = new ArrayList<String>();
		headers.add("Employer ID");
		headers.add("Company Name");
		headers.add("Email");
		int currentColumn = 0;

		HSSFCellStyle headerStyle = workbook.createCellStyle();
		HSSFFont headerFont = workbook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);
		// POPULATE HEADER COLUMNS
		HSSFRow headerRow = sheet.createRow(0);
		for (String header : headers) {
			HSSFRichTextString text = new HSSFRichTextString(header);
			HSSFCell cell = headerRow.createCell(currentColumn);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(text);
			currentColumn++;
		}

		int row = 1;
		for (Employer employer : employers) {
			getCell(sheet, row, 0).setCellValue(employer.getEmployerID());
			getCell(sheet, row, 1).setCellValue(employer.getCompanyName());
			getCell(sheet, row, 2).setCellValue(employer.getEmailAddress());

			row++;

		}

	}

}
