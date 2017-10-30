package com.nirali.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.nirali.spring.dao.SupervisorDAO;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.ShiftTracker;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.pojo.SupervisorStaff;

public class StaffDataXLSView extends AbstractExcelView {

	@Autowired
	SupervisorDAO supervisorDao;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub

		HttpSession session = req.getSession();
		supervisorDao = new SupervisorDAO();
		Long pid = (Long) session.getAttribute("pid");
		SupervisorStaff s = supervisorDao.getSupervisor(pid);
		Employer e = s.getEmployer();
		List<ShiftTracker> shifts = supervisorDao.getTodaysEmployees(e.getEmployerID());
		System.out.println("employer excep size= " + shifts.size());
		HSSFSheet sheet = workbook.createSheet("Todays Staff");
		sheet.setDefaultColumnWidth(12);
		List<String> headers = new ArrayList<String>();
		headers.add("Person ID");
		// headers.add("FirstName");
		// headers.add("Last Name");
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
		for (ShiftTracker st : shifts) {
			List<StudentStaff> ss = (List<StudentStaff>) st.getStudentStaff();

			for (StudentStaff sstaff : ss) {
				getCell(sheet, row, 0).setCellValue(sstaff.getFirstName());
			}
			row++;

		}

	}

}
