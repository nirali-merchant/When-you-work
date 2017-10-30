package com.nirali.spring.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nirali.spring.dao.StudentDAO;
import com.nirali.spring.dao.UserDAO;
import com.nirali.spring.pojo.Availability;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.validator.AvailabilityValidator;
import com.nirali.spring.validator.StudentStaffValidator;

@Controller
public class AvailabilityController {

	@Autowired
	@Qualifier("studentDao")
	StudentDAO studentDao;

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("availabilityValidator")
	AvailabilityValidator availabilityValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(availabilityValidator);
	}

	@RequestMapping(value = "student/giveAvailability.htm", method = RequestMethod.GET)
	public ModelAndView showAvailabilityForm(HttpServletRequest request,
			@ModelAttribute("availability") Availability avail) throws Exception {
		HttpSession session = request.getSession();

		System.out.println("startTime= " + avail.getStartTime());
		return new ModelAndView("studentstaff-availability", "avail", new Availability());
	}

	@RequestMapping(value = "student/giveAvailability.htm", method = RequestMethod.POST)
	public ModelAndView procesAvailabilityForm(HttpServletRequest request,
			@ModelAttribute("availability") Availability avail, BindingResult result) throws Exception {

		HttpSession session = request.getSession();

		System.out.println("inside shifts controller");
		System.out.println("startTime= " + avail.getStartTime());
		System.out.println("****Date=" + avail.getDate());

		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		System.out.println("***" + startTime);
		System.out.println("***" + endTime);

		DateFormat sdf = new SimpleDateFormat("kk:mm:ss");
		Date start = sdf.parse(startTime);

		System.out.println("**datestart" + start);
		avail.setStartTime(start);

		Date end = sdf.parse(endTime);
		System.out.println("**dateend" + end);
		avail.setEndTime(end);
		session.setAttribute("invalidAvailSet", "false");
		session.setAttribute("posted", "false");
		if (avail.getEndTime().before(avail.getStartTime()) || avail.getEndTime().equals(avail.getStartTime())) {

			session.setAttribute("invalidAvailSet", "true");
			return new ModelAndView("studentstaff-availability", "avail", new Availability());
		}

		Long pid = (Long) session.getAttribute("pid");
		Person p = userDao.getPerson(pid);

		Availability a = new Availability();
		a.setDate(a.getDate());

		StudentStaff ss = new StudentStaff();
		ss.setEmailID(p.getEmailID());
		ss.setEmployer(p.getEmployer());
		ss.setFirstName(p.getFirstName());
		ss.setLastName(p.getLastName());
		ss.setPassword(p.getPassword());
		ss.setRole(p.getRole());
		ss.setPersonID(p.getPersonID());
		Set<StudentStaff> ssList = new HashSet<StudentStaff>();
		ssList.add(ss);
		avail.setStudentStaff(ssList);

		boolean result1 = studentDao.checkPostedAvailability(avail.getDate(), avail.getStartTime(), avail.getEndTime(),
				pid);
		System.out.println("result1= " + result1);
		if (result1) {
			studentDao.setAvail(avail);
		} else {
			session.setAttribute("posted", "true");
			return new ModelAndView("studentstaff-availability", "avail", new Availability());
		}

		return new ModelAndView("add-availability", "availability", avail);
	}

	@RequestMapping(value = "student/viewAvailability.htm", method = RequestMethod.GET)
	public ModelAndView processViewAvailability(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Long pid = (Long) session.getAttribute("pid");
		
		// View Availability for days starting 7 days prior
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date newDate = cal.getTime();
		System.out.println("new Date=" + newDate);
		
		Set<Availability> availList = studentDao.getAvail(pid, newDate);
		return new ModelAndView("student-viewAvailability", "availList", availList);
	}

}
