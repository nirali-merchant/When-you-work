package com.nirali.spring.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.stream.events.EndDocument;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nirali.spring.dao.StudentDAO;
import com.nirali.spring.dao.UserDAO;
import com.nirali.spring.pojo.Availability;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.OpenShifts;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.ShiftTracker;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.validator.StudentStaffValidator;

@Controller
public class StudentController {

	@Autowired
	@Qualifier("studentDao")
	StudentDAO studentDao;

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("studentStaffValidator")
	StudentStaffValidator studentStaffValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(studentStaffValidator);
	}

	// ***********************************************************************
	// New Student Registration
	// ***********************************************************************
	@RequestMapping(value = "user/studentRegistration.htm", method = RequestMethod.GET)
	public ModelAndView showStudentRegistrationForm(HttpServletRequest request,
			@ModelAttribute("studentStaff") StudentStaff studentstaff) throws Exception {
		System.out.print("reigster Student");
		HttpSession session = request.getSession();

		// get the list of employers
		List<Employer> employers = studentDao.getEmployersList();
		session.setAttribute("employers", employers);
		String roleName = "Student";
		session.setAttribute("roleName", roleName);
		return new ModelAndView("student-registration", "studentStaff", new StudentStaff());
	}

	@RequestMapping(value = "user/studentRegistration.htm", method = RequestMethod.POST)
	public ModelAndView handleStudentRegistrationForm(HttpServletRequest request,
			@ModelAttribute("studentStaff") StudentStaff studentstaff, BindingResult result) throws Exception {

		HttpSession session = request.getSession();

		// Populate List of Employers
		List<Employer> employers = studentDao.getEmployersList();
		session.setAttribute("employers", employers);

		studentStaffValidator.validate(studentstaff, result);
		if (result.hasErrors()) {
			return new ModelAndView("student-registration", "studentStaff", studentstaff);
		}
		try {

			System.out.print("registerNewStudent");
			Employer emp = studentDao.getEmployer(studentstaff.getEmployer().getCompanyName());
			String employer = request.getParameter("employer");

			/// **********session.setAttribute("sessionEmployer", employer);
			//// employer = (String) session.getAttribute("sessionEmployer");

			// Employer emp = studentDao.getEmployer(employer);

			studentstaff.setEmployer(emp);
			String r = "Student";

			// Get Role Object
			Role role = studentDao.getRole(r);
			studentstaff.setRole(role);
			// request.getSession().setAttribute("role", r);
			StudentStaff s = null;

			// Upload Profile Photo
			try {
				File directory;
				String path = "E:\\";
				studentstaff.setFilename("album");
				directory = new File(path + "\\" + studentstaff.getFilename());
				boolean temp = directory.exists();
				if (!temp) {
					temp = directory.mkdir();
				}
				if (temp) {
					// We need to transfer to a file
					CommonsMultipartFile photoInMemory = studentstaff.getPhoto();

					String fileName = photoInMemory.getOriginalFilename();
					// could generate file names as well

					File localFile = new File(directory.getPath(), fileName);

					// move the file from memory to the file

					photoInMemory.transferTo(localFile);
					studentstaff.setFilename(localFile.getPath());
					System.out.println("File is stored at" + localFile.getPath());
					System.out.print("registerNewUser");

				} else {
					System.out.println("Failed to create directory!");
				}

			} catch (IllegalStateException e) {
				System.out.println("*** IllegalStateException: " + e.getMessage());
			}

			s = studentDao.registerStudent(studentstaff);

			// Send an Email on Successful Registration

			try {
				Email email = new SimpleEmail();
				email.setSmtpPort(465);
				email.setAuthenticator(new DefaultAuthenticator("temporarywebtools2017@gmail.com", "temporary"));
				email.setHostName("smtp.gmail.com");// if a server is capable of
													// sending email.
				email.setSSL(true);// setSSLOnConnect(true);
				email.setFrom("temporarywebtools2017@gmail.com");
				email.setSubject("Successful registration to When you Work!!!");
				email.setMsg("This is system generated mail, do not reply to this email.");
				email.addTo(studentstaff.getEmailID());
				email.setTLS(true);// startTLS.enable.true
				email.send();
				System.out.println("EMAIL SENT TO STUDENT!!");
			} catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				return new ModelAndView("error", "errorMessage", "error while sening Email to Student");
			}
			return new ModelAndView("user-registration", "studentStaff", s);
		} catch (HibernateException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while registration");
		}
	}

	@RequestMapping(value = "student/viewSchedule.htm", method = RequestMethod.GET)
	public ModelAndView showSchedule(HttpServletRequest request,
			@ModelAttribute("shiftTracker") ShiftTracker shiftTracker, BindingResult result) throws Exception {

		String open = "false";
		HttpSession session = request.getSession();

		Long pid = (Long) session.getAttribute("pid");

		// Get all the shifts assigned
		List<ShiftTracker> stList = studentDao.getShifts(pid);

		Set<Shifts> studentShiftList = new HashSet<Shifts>();
		List<ShiftTracker> stsList = null;
		stsList = studentDao.getSwappedShifts(pid);
		for (ShiftTracker st : stsList) {
			if (st.isOpen()) {
				open = "true";

			}
			if (st.isSwapped()) {
				stsList = studentDao.getAllShifts(st.getShifts().getShiftID());

			}
			System.out.println("swapped list size=" + stsList.size());
			Shifts s = st.getShifts();
			studentShiftList.add(s);
		}
		session.setAttribute("open", open);
		session.setAttribute("shiftTrackerList", stList);
		session.setAttribute("studentShiftList", studentShiftList);

		return new ModelAndView("student-schedule");
	}

	@RequestMapping(value = "student/pickShifts.htm", method = RequestMethod.GET)
	public ModelAndView pick(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();

		Long pid = (Long) session.getAttribute("pid");
		Long eid = (Long) session.getAttribute("eid");
		
		// Get List of Dropped Shifts
		List<ShiftTracker> droppedShifts = studentDao.getDroppedShifts(pid, eid);

		// Get List of Open Shifts
		List<ShiftTracker> openShifts = studentDao.getOpenShifts(pid, eid);
		System.out.println("openShifts=" + openShifts.size());

		// Get List of Open ShifTs from OpenShifts Table
		Set<OpenShifts> remainingOpenShifts = studentDao.getRemainingOpenShifts(eid, pid);
		System.out.println("remainingOpenShifts=" + remainingOpenShifts.size());

		session.setAttribute("droppedShifts", droppedShifts);
		session.setAttribute("openShifts", openShifts);
		session.setAttribute("remainingOpenShifts", remainingOpenShifts);
		return new ModelAndView("student-pickshift");
	}

	@RequestMapping(value = "student/pickOpenShift.htm", method = RequestMethod.POST)
	public ModelAndView pickOpenShift(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Long shiftID = (long) 0;
		Long personID = (long) 0;
		Long pid = (long) 0;
		Long eid = (long) 0;
		try {

			// Get the Shift id and Person id of the shift posted
			String shiftID1 = request.getParameter("hiddenOpenShiftID");
			// System.out.println(shiftID1);
			String personid1 = request.getParameter("hiddenOpenPersonID");
			System.out.println("PERSONID=" + personid1);
			shiftID = Long.parseLong(shiftID1);

			pid = (Long) session.getAttribute("pid");
			eid = (Long) session.getAttribute("eid");

			personID = Long.parseLong(request.getParameter("hiddenOpenPersonID"));

		} catch (NumberFormatException ex) {
			System.out.println("number format exception");
		}
		System.out.println("shiftid =" + shiftID);
		System.out.println("personid=" + personID);
		System.out.println("pid=" + pid);

		// If no person is assigned means the open shifts are from the Open
		// Shifts Table
		if (personID == 0) {
			// Assign Open shift to student
			studentDao.getSelectedRemainingOpenShift(shiftID, pid);
		} else {
			// Assign OpenShift to the student
			ShiftTracker newShift = studentDao.getSelectedOpenShift(shiftID, personID, pid);
		}

		// code to refresh pickshifts page
		List<ShiftTracker> droppedShifts = studentDao.getDroppedShifts(pid, eid);
		List<ShiftTracker> openShifts = studentDao.getOpenShifts(pid, eid);
		Set<OpenShifts> remainingOpenShifts = studentDao.getRemainingOpenShifts(eid, pid);

		session.setAttribute("droppedShifts", droppedShifts);
		session.setAttribute("openShifts", openShifts);
		session.setAttribute("remainingOpenShifts", remainingOpenShifts);

		return new ModelAndView("student-pickshift");
	}

	@RequestMapping(value = "student/pickDroppedShifts.htm", method = RequestMethod.POST)
	public ModelAndView pickDroppedShift(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Long shiftID = (long) 0;
		Long personID = (long) 0;
		Long pid = (long) 0;
		Long eid = (long) 0;
		try {
			String shiftID1 = request.getParameter("hiddenDropShiftID");
			String personid1 = request.getParameter("hiddenDropPersonID");
			System.out.println("PERSONID=" + personid1);
			shiftID = Long.parseLong(shiftID1);
			personID = Long.parseLong(request.getParameter("hiddenDropPersonID"));
			pid = (Long) session.getAttribute("pid");
			eid = (Long) session.getAttribute("eid");
		} catch (NumberFormatException ex) {
			System.out.println("number format exception");
		}
		System.out.println("shiftid =" + shiftID);
		System.out.println("personid=" + personID);
		System.out.println("pid=" + pid);

		// Get List of Dropped Shift
		ShiftTracker newShift = studentDao.getSelectedDropShift(shiftID, personID, pid);

		// code to refresh pickshifts page

		List<ShiftTracker> droppedShifts = studentDao.getDroppedShifts(pid, eid);
		List<ShiftTracker> openShifts = studentDao.getOpenShifts(pid, eid);
		session.setAttribute("droppedShifts", droppedShifts);
		session.setAttribute("openShifts", openShifts);

		return new ModelAndView("student-pickshift");
	}

	@RequestMapping(value = "student/swap.htm", method = RequestMethod.POST)
	public ModelAndView swap(HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession();
			Long swappwdShiftID = Long.parseLong(request.getParameter("hiddenShiftID"));

			session.setAttribute("swappedShiftID", swappwdShiftID);
			System.out.println("The shift which is being swapped=" + request.getParameter("hiddenShiftID"));

			Long pid = (Long) session.getAttribute("pid");
			Long eid = (Long) session.getAttribute("eid");
			List<ShiftTracker> shifts = studentDao.getAcknowledgedShifts(pid, eid);
			System.out.println("Acknowledged shifts received" + shifts.size());
			session.setAttribute("shifts", shifts);

			return new ModelAndView("student-swap", "shifts", shifts);
		} catch (HibernateException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while  Swapping shifts");
		}

	}

	@RequestMapping(value = "student/swapThisShift.htm", method = RequestMethod.POST)

	public ModelAndView swapShift(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		Long swappwdByShiftID = Long.parseLong(request.getParameter("hiddenSwappedID"));
		Long swappedShiftID = (Long) session.getAttribute("swappedShiftID");
		// Long swappedByPersonID = (Long)
		// session.getAttribute("hiddenSwappedPersonID");
		Long pid = (Long) session.getAttribute("pid");
		Long swappedByPersonID = Long.parseLong(request.getParameter("hiddenSwappedPersonID"));

		System.out.println("swappwdByShiftID=" + swappwdByShiftID);
		System.out.println("swappedShiftID=" + swappedShiftID);
		System.out.println("swappedByPersonID=" + swappedByPersonID);
		System.out.println("pid=" + pid);

		ShiftTracker st = studentDao.swap(swappedShiftID, pid, swappwdByShiftID, swappedByPersonID);
		System.out.println(
				"Result from dao shift for which swapping is to be done=" + st.getStudentStaff().getPersonID() + "**");
		ShiftTracker swappedst = studentDao.swapShift(swappedShiftID, pid, swappwdByShiftID, swappedByPersonID);
		System.out.println("swapped with student id=" + swappedst.getSwappedWithStudentID());
		session.setAttribute("swappedst", swappedst);

		// code for viewing schedule again
		String open = "false";
		List<ShiftTracker> stList = studentDao.getShifts(pid);
		Set<Shifts> studentShiftList = new HashSet<Shifts>();
		for (ShiftTracker sst : stList) {
			if (sst.isOpen()) {
				open = "true";
			}
			Shifts s = sst.getShifts();
			studentShiftList.add(s);
		}
		session.setAttribute("open", open);
		session.setAttribute("shiftTrackerList", stList);
		session.setAttribute("studentShiftList", studentShiftList);

		return new ModelAndView("student-schedule");
	}

}
