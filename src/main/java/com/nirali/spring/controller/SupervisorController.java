package com.nirali.spring.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.nirali.spring.dao.SupervisorDAO;
import com.nirali.spring.dao.StudentDAO;
import com.nirali.spring.pojo.Availability;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.OpenShifts;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.ShiftTracker;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.pojo.SupervisorStaff;
import com.nirali.spring.validator.EmployerValidator;
import com.nirali.spring.validator.SupervisorStaffValidator;

@Controller
public class SupervisorController {

	@Autowired
	@Qualifier("supervisorDao")
	SupervisorDAO supervisorDao;

	@Autowired
	@Qualifier("studentDao")
	StudentDAO studentDao;

	@Autowired
	@Qualifier("supervisorStaffValidator")
	SupervisorStaffValidator supervisorStaffValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(supervisorStaffValidator);

	}

	@Autowired
	ServletContext servletContext;

	// **************************************************************************************
	// New Supervisor Registration
	// **************************************************************************************
	@RequestMapping(value = "user/supervisorRegistration.htm", method = RequestMethod.GET)
	public ModelAndView showSupervisorRegistrationForm(HttpServletRequest request,
			@ModelAttribute("supervisorstaff") SupervisorStaff supervisorstaff, BindingResult Result) throws Exception {
		System.out.print("reigster Supervisor");
		HttpSession session = request.getSession();

		// get the list of employers
		List<Employer> employers = studentDao.getEmployersList();
		session.setAttribute("employers", employers);
		String roleName = "Supervisor";
		session.setAttribute("roleName", roleName);

		return new ModelAndView("supervisor-registration", "supervisorstaff", new SupervisorStaff());
	}

	@RequestMapping(value = "user/supervisorRegistration.htm", method = RequestMethod.POST)
	public ModelAndView handleSupervisortRegistrationForm(HttpServletRequest request,
			@ModelAttribute("supervisorstaff") SupervisorStaff supervisorstaff, BindingResult result) throws Exception {

		HttpSession session = request.getSession();

		supervisorStaffValidator.validate(supervisorstaff, result);
		if (result.hasErrors()) {
			return new ModelAndView("supervisor-registration", "supervisorstaff", supervisorstaff);
		}

		System.out.println("Inside supervisor registration post comtroller");
		System.out.println(supervisorstaff.getFirstName());
		System.out.println(supervisorstaff.getLastName());
		System.out.println(supervisorstaff.getFilename());
		try {
			System.out.println("Employer company nme" + supervisorstaff.getEmployer().getCompanyName());
			System.out.print("registerNewSupervisor");
			String employer = request.getParameter("employer");

			// Get the Employer
			Employer emp = studentDao.getEmployer(supervisorstaff.getEmployer().getCompanyName());
			System.out.println("dlkf" + emp.getEmployerID());
			supervisorstaff.setEmployer(emp);

			// Get the Role
			String r = "Supervisor";
			Role role = studentDao.getRole(r);
			supervisorstaff.setRole(role);
			System.out.println("Role in Supervisor= " + role.getRoleID());
			request.getSession().setAttribute("role", r);

			try {

				File directory;
				String path = "E:\\";
				supervisorstaff.setFilename("album");
				directory = new File(path + "\\" + supervisorstaff.getFilename());
				boolean temp = directory.exists();
				if (!temp) {
					temp = directory.mkdir();
				}
				if (temp) {
					// We need to transfer to a file
					CommonsMultipartFile photoInMemory = supervisorstaff.getPhoto();

					String fileName = photoInMemory.getOriginalFilename();
					// could generate file names as well

					File localFile = new File(directory.getPath(), fileName);

					// move the file from memory to the file
					photoInMemory.transferTo(localFile);
					supervisorstaff.setFilename(localFile.getPath());
					System.out.println("File is stored at" + localFile.getPath());
					System.out.print("registerNewUser");

				} else {
					System.out.println("Failed to create directory!");
				}

			} catch (IllegalStateException e) {
				System.out.println("*** IllegalStateException: " + e.getMessage());
			}
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
				email.addTo(supervisorstaff.getEmailID());
				email.setTLS(true);// startTLS.enable.true
				email.send();
				System.out.println("EMAIL SENT TO SUPERVISOR");
			} catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				return new ModelAndView("error", "errorMessage", "Error while sending an Email");
			}

			//Save the supervisor
			SupervisorStaff s = supervisorDao.registerSupervisor(supervisorstaff);
			return new ModelAndView("user-registration", "supervisorstaff", s);

		} catch (HibernateException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "Error while Supervisor registration");
		}
	}

	@RequestMapping(value = "supervisor/allotShifts.htm", method = RequestMethod.GET)
	public ModelAndView showAllocateShiftsForm(HttpServletRequest request, @ModelAttribute("shifts") Shifts shifts)
			throws Exception {
		System.out.println("show Allocate Shifts form");

		try {
			return new ModelAndView("supervisor-allotShifts", "shifts", shifts);

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while Assigning shifts");
		}
	}

	@RequestMapping(value = "supervisor/allotShifts.htm", method = RequestMethod.POST)
	public ModelAndView allocateShifts(HttpServletRequest request, @ModelAttribute("shifts") Shifts shifts,
			BindingResult result) throws Exception {

		try {

			Date newDate = shifts.getDate();
			String stringDate = newDate.toString();
			System.out.println("String date=" + stringDate);
			stringDate = stringDate.substring(0, 10);

			HttpSession session = request.getSession();

			System.out.println("inside Allocate Shifts");
			// Get Date
			String date = request.getParameter("date");
			System.out.println("Date=" + date);
			System.out.println("newDate= " + newDate);

			// Get Time and convert it
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			System.out.println("***" + startTime);
			System.out.println("***" + endTime);
			DateFormat sdf = new SimpleDateFormat("kk:mm:ss");
			Date start = sdf.parse(startTime);
			System.out.println("**datestart" + start);
			shifts.setStartTime(start);
			Date end = sdf.parse(endTime);
			System.out.println("**dateend" + end);
			shifts.setEndTime(end);

			// Set sessions for showing specific validation
			session.setAttribute("invalidAvailSet", "false");
			session.setAttribute("posted", "false");
			session.setAttribute("shiftSpan", "false");

			// Allow shiift span of only 2 hours
			long diff = shifts.getEndTime().getTime() - shifts.getStartTime().getTime();
			diff = diff / (60 * 60 * 1000) % 24;
			if (diff > 2 || diff < 2) {
				System.out.println("diff=" + diff);
				session.setAttribute("shiftSpan", "true");
				return new ModelAndView("supervisor-allotShifts", "shifts", shifts);
			}

			Long eid = (Long) session.getAttribute("eid");

			// Check whether the shift has already been posted
			boolean shiftPosted = supervisorDao.checkPostedShift(newDate, start, end, eid);
			if (shiftPosted) {
				session.setAttribute("posted", "true");
				return new ModelAndView("supervisor-allotShifts", "shifts", shifts);
			}
			// Get list of students who are available for the shift posted
			List<Availability> availabilityList = supervisorDao.getStudents(newDate, start, end);
			Set<StudentStaff> availableStuds = new HashSet<StudentStaff>();
			for (Availability a : availabilityList) {
				Set<StudentStaff> tempSet = a.getStudentStaff();
				System.out.println("studentStaff= " + tempSet.size());
				for (StudentStaff s : tempSet) {

					availableStuds.add(s);
				}

			}
			System.out.println("Avaialble studs list made");
			// Get students for only this employer
			Set<StudentStaff> newStudList = supervisorDao.getThisEmployerStudents(availableStuds, eid);
			System.out.println("availableStuds=" + newStudList.size());
			session.setAttribute("stringDate", stringDate);
			session.setAttribute("date", newDate);
			session.setAttribute("startTime", start);
			session.setAttribute("endTime", end);
			session.setAttribute("availableStuds", newStudList);

			// Total number of employees required for this shift
			int totalEmployeesRequired = shifts.getEmployeeCount();
			session.setAttribute("totalEmployeesRequired", totalEmployeesRequired);
			return new ModelAndView("supervisor-selectEmployee", "studentstaff", newStudList);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while assigning Shifts");
		}
	}

	@RequestMapping(value = "supervisor/assign-shifts.htm", method = RequestMethod.POST)
	public ModelAndView assignShifts(HttpServletRequest request) throws Exception {

		try {
			HttpSession session = request.getSession();
			Shifts shifts = new Shifts();
			int openShiftCount = 0;
			Set<StudentStaff> ssSet = null;
			Date date = (Date) session.getAttribute("date");
			Date startTime = (Date) session.getAttribute("startTime");
			Date endTime = (Date) session.getAttribute("endTime");

			System.out.println("start= " + startTime);
			System.out.println("end= " + endTime);

			shifts.setDate(date);
			shifts.setStartTime(startTime);
			shifts.setEndTime(endTime);

			int totalEmployeesRequired = (Integer) session.getAttribute("totalEmployeesRequired");

			System.out.println("te" + totalEmployeesRequired);

			shifts.setEmployeeCount(totalEmployeesRequired);

			Long pid = (Long) session.getAttribute("pid");

			System.out.println("pid=" + pid);

			// get Supervisor Object
			SupervisorStaff supervisorStaff = supervisorDao.getSupervisor(pid);
			shifts.setSupervisorStaff(supervisorStaff);

			// If no student availability matches the shft timing
			if (request.getParameterValues("students") != null) {
				String answers[] = request.getParameterValues("students");
				System.out.println("dropdown = " + answers.length);

				if (answers.length > totalEmployeesRequired) {
					return new ModelAndView("error", "errorMessage",
							"Only" + totalEmployeesRequired + "employees required for this shift");
				}

				openShiftCount = totalEmployeesRequired - answers.length;

				ssSet = new HashSet<StudentStaff>();
				// ShiftTracker st = new ShiftTracker();
				for (String ans : answers) {
					Long id = Long.parseLong(ans);
					System.out.println("ans=" + ans);
					StudentStaff ss = new StudentStaff();
					StudentStaff s = supervisorDao.getStudent(id);
					ss = s;
					ssSet.add(s);
				}
				supervisorDao.assignStudentShifts(shifts);

				ShiftTracker st = null;
				System.out.println("StudentList=" + ssSet.size());
				for (StudentStaff s : ssSet) {
					st = new ShiftTracker();
					st.setShifts(shifts);
					st.setStudentStaff(s);
					st.setOpen(true);
					supervisorDao.assignEachStudentShift(st);
				}
			}
			/// Post the required shifts for which no student is available in open shifts
			else {
				supervisorDao.assignStudentShifts(shifts);
				openShiftCount = totalEmployeesRequired - 0;
			}

			for (int i = 0; i < openShiftCount; i++) {
				System.out.println("Inside adding reamaining open shifts");
				OpenShifts os = new OpenShifts();
				os.setShifts(shifts);
				os.setDate(shifts.getDate());
				os.setStartTime(shifts.getStartTime());
				os.setEndTime(shifts.getEndTime());
				// os.setOpen(true);

				supervisorDao.assignEachOpenShift(os);

			}
			// supervisorDao.assignEachStudentShift(st);

			return new ModelAndView("assign-moreShifts");
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while assigning shifts");
		}
	}

	@RequestMapping(value = "supervisor/deleteShifts.htm", method = RequestMethod.GET)
	public ModelAndView showdeleteShiftsForm(HttpServletRequest request, @ModelAttribute("shifts") Shifts shifts,
			BindingResult result) throws Exception {
		System.out.println("inside get controller");

		return new ModelAndView("supervisor-deleteShift");

	}

	@RequestMapping(value = "supervisor/deleteShifts.htm", method = RequestMethod.POST)
	public ModelAndView deleteShifts(HttpServletRequest request, @ModelAttribute("shifts") Shifts shifts,
			BindingResult result) throws Exception {
		try {
			Date newDate = shifts.getDate();
			HttpSession session = request.getSession();
			System.out.println("inside Allocate Shifts");
			String date = request.getParameter("date");
			System.out.println("Date=" + date);
			// Date newDate =new SimpleDateFormat("MM/DD/yyyy").parse(date);
			System.out.println("newDate= " + newDate);

			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			System.out.println("***" + startTime);
			System.out.println("***" + endTime);

			DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
			Date start = sdf.parse(startTime);

			System.out.println("**datestart" + start);
			shifts.setStartTime(start);
			Date end = sdf.parse(endTime);
			System.out.println("**dateend" + end);
			shifts.setEndTime(end);

			System.out.println("Inside controller");
			System.out.println("strttime=" + shifts.getStartTime());
			System.out.println("endTime=" + shifts.getEndTime());

			// Get that shift
			List<Shifts> shiftList = supervisorDao.getShift(shifts.getDate(), shifts.getStartTime(),
					shifts.getEndTime());
			System.out.println(shiftList.size());
			for (Shifts shift : shiftList) {
				System.out.println("shift id= " + shift.getShiftID());
				supervisorDao.deleteShift(shift);
			}
			System.out.println("shift deleted");
			return new ModelAndView("delete-moreShifts");

		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while deleting shfts");
		}
	}

	@RequestMapping(value = "supervisor/todaysStaff.htm", method = RequestMethod.GET)
	public ModelAndView StaffDatXlsView() {
		View view = new StaffDataXLSView();
		return new ModelAndView(view);
	}

}
