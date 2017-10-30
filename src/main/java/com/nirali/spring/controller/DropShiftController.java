package com.nirali.spring.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.junit.runner.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nirali.spring.dao.AdminDAO;
import com.nirali.spring.dao.DAO;
import com.nirali.spring.dao.StudentDAO;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.ShiftTracker;
import com.nirali.spring.pojo.Shifts;

//import com.google.gson.Gson;

@Controller
public class DropShiftController extends DAO {

	@Autowired
	@Qualifier("studentDao")
	StudentDAO studentDao;

	@Autowired
	@Qualifier("adminDao")
	AdminDAO adminDao;

	// On clicking the drop button the shift gets dropped
	@RequestMapping(value = "student/drop.htm", method = RequestMethod.POST)
	@ResponseBody
	public String initiateDrop(HttpServletRequest request, @RequestParam(value = "shiftid") Long shift) {

		HttpSession session = request.getSession();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("shift from ajax" + shift);
		Long pid = (Long) session.getAttribute("pid");
		String sql = "from ShiftTracker where shiftID = :shift and personID = :pid";
		Query q = getSession().createQuery(sql);
		q.setLong("shift", shift);
		q.setLong("pid", pid);
		ShiftTracker st = (ShiftTracker) q.uniqueResult();
		System.out.println("shifId to be dropped=" + shift);
		System.out.println("person whse shift is to be dropped=" + pid);
		begin();
		st.setDropped(true);
		getSession().save(st);
		commit();
		// return null;
		// System.out.println("*** shift1: " + gson.toJson(st));
		// String jsonInString = gson.toJson(st);
		// System.out.println("*** shift2: " + gson.toJson(st));
		return "{\"shiftid\":" + st.getSwappedShiftID() + ",\"studid\":" + st.getSwappedWithStudentID() + "}";
	}

	// On clicking the acknowledge button, acknowledge the shift
	@RequestMapping(value = "student/acknowledge.htm", method = RequestMethod.POST)
	@ResponseBody
	public String acknowledgeSchedule(HttpServletRequest request, @RequestParam(value = "shiftid") Long shiftID)
			throws Exception {
		HttpSession session = request.getSession();
		Long pid = (Long) session.getAttribute("pid");
		ShiftTracker st = studentDao.getShiftToBeAcknowledged(shiftID, pid);
		System.out.println("after query execution");
		System.out.println(st.getSwappedShiftID());
		System.out.println(st.getSwappedWithStudentID());
		return "{\"shiftid\":" + st.getSwappedShiftID() + ",\"studid\":" + st.getSwappedWithStudentID() + "}";
	}

	// Approve the employers
	@RequestMapping(value = "admin/approve.htm", method = RequestMethod.POST)
	@ResponseBody
	public String initiateApproval(HttpServletRequest request, @RequestParam(value = "employerid") Long employerID) {

		HttpSession session = request.getSession();
		System.out.println("shift from ajax" + employerID);
		Employer e = adminDao.getEmployer(employerID);
		adminDao.getApproval(e.getEmployerID());
		System.out.println("db changde");
		try {
			Email email = new SimpleEmail();
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("temporarywebtools2017@gmail.com", "temporary"));
			email.setHostName("smtp.gmail.com");// if a server is capable of
												// sending email.
			email.setSSL(true);// setSSLOnConnect(true);
			email.setFrom("temporarywebtools2017@gmail.com");
			email.setSubject("Registration to When you work");
			email.setMsg("Your request has been approved. Welcome to When you Work ! ");
			email.addTo(e.getEmailAddress());
			email.setTLS(true);// startTLS.enable.true
			email.send();
			System.out.println("When you Work confirmation email");
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			// return new ModelAndView("error", "errorMessage", "error while
			// login");
		}
		return "e";
	}

	@RequestMapping(value = "admin/details.htm", method = RequestMethod.POST)
	@ResponseBody
	public String viewDetails(HttpServletRequest request, @RequestParam(value = "employerid") Long employerID)
			throws Exception {
		HttpSession session = request.getSession();
		Employer e = adminDao.getEmployer(employerID);
		return "{\"PhoneNo\":" + "\"" + e.getPhoneNo() + "\"" + "," + "\"emailID\":" + "\"" + e.getEmailAddress() + "\""
				+ "," + "\"Address\":" + "\"" + e.getAddress() + "\"" + "}";
	}

	@RequestMapping(value = "student/swapShiftDetails", method = RequestMethod.POST)
	@ResponseBody
	public String swapShiftDetails(HttpServletRequest request, @RequestParam(value = "shift1id") Long shiftID)
			throws Exception {

		System.out.println("InsideswapShiftDetails");
		HttpSession session = request.getSession();
		Shifts s = studentDao.getShiftByID(shiftID);
		return "{\"Date\":" + "\"" + s.getDate() + "\"" + "," + "\"StartTime\":" + "\"" + s.getStartTime() + "\"" + ","
				+ "\"EndTime\":" + "\"" + s.getEndTime() + "\"" + "}";
	}

	@RequestMapping(value = "student/approveSwapShift.htm", method = RequestMethod.POST)
	@ResponseBody
	public void approveSwapShift(HttpServletRequest request, @RequestParam(value = "shift1id") Long initiatorShiftid,
			@RequestParam(value = "shift2id") Long initiatorPersonid,
			@RequestParam(value = "shift3id") Long swappedShiftid,
			@RequestParam(value = "shift4id") Long swappedPersonid) throws Exception {

		System.out.println("InsideswapShiftDetails");
		HttpSession session = request.getSession();

		studentDao.getSwappedInitiatorShift(initiatorShiftid, initiatorPersonid);
		studentDao.getSwappedSwappedWithShift(swappedShiftid, swappedPersonid);
		studentDao.swapActualShifts(initiatorShiftid, initiatorPersonid, swappedShiftid, swappedPersonid);
	}

	@RequestMapping(value = "student/denySwapShift.htm", method = RequestMethod.POST)
	@ResponseBody
	public void denySwapShift(HttpServletRequest request, @RequestParam(value = "shift1id") Long initiatorShiftid,
			@RequestParam(value = "shift2id") Long initiatorPersonid,
			@RequestParam(value = "shift3id") Long swappedShiftid,
			@RequestParam(value = "shift4id") Long swappedPersonid) throws Exception {

		System.out.println("InsideswapShiftDetails");
		HttpSession session = request.getSession();

		studentDao.denyswapActualShifts(initiatorShiftid, initiatorPersonid, swappedShiftid, swappedPersonid);
	}

}
