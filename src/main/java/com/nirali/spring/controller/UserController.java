package com.nirali.spring.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.servlet.ModelAndView;

import com.nirali.spring.dao.EmployerDAO;
import com.nirali.spring.dao.UserDAO;
import com.nirali.spring.exception.EmployerException;
import com.nirali.spring.exception.PersonException;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.pojo.SupervisorStaff;
import com.nirali.spring.validator.EmployerValidator;
import com.nirali.spring.validator.PersonValidator;
import com.nirali.spring.validator.StudentStaffValidator;

@Controller
public class UserController {

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("employerDao")
	EmployerDAO employerDao;

	@Autowired
	@Qualifier("PersonValidator")
	PersonValidator personValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(personValidator);

	}

	@RequestMapping(value = "user/login.htm", method = RequestMethod.GET)
	public ModelAndView showLoginForm(HttpServletRequest request, @ModelAttribute("person") Person person,
			BindingResult result) throws Exception {
		System.out.print("login");

		HttpSession session = request.getSession();
		return new ModelAndView("user-login", "Person", new Person());
	}

	@RequestMapping(value = "user/home.htm", method = RequestMethod.GET)
	public ModelAndView handleredirectHome(HttpServletRequest request, @ModelAttribute("person") Person person,
			BindingResult result) throws Exception {

		String roleName = null;
		System.out.println("inside user home get");
		HttpSession session = (HttpSession) request.getSession();
		Person p = null;
		try {
			Long pid = (Long) session.getAttribute("pid");
			System.out.println("pid=" + pid);

			// If session is null
			if (pid == null) {
				return new ModelAndView("index");
			}
			// Get Person
			p = userDao.getPerson(pid);
			if (!p.getRole().getRoleName().equalsIgnoreCase("Admin")) {
				Employer e = p.getEmployer();
				session.setAttribute("employer", e);
			}

			Role r = p.getRole();
			if (pid != null) {
				if (r != null) {
					roleName = p.getRole().getRoleName();
				}
			}

			session.setAttribute("role", r);
			session.setAttribute("person", p);
			return new ModelAndView("user-home", "person", p);
		} catch (Exception e) {
			// System.out.println("inside catch");
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return new ModelAndView("error", "errorMessage", "error while logn");
		}
	}

	@RequestMapping(value = "user/home.htm", method = RequestMethod.POST)
	public ModelAndView handleUserLogin(HttpServletRequest request, @ModelAttribute("person") Person person,
			BindingResult result) throws Exception {
		HttpSession session = (HttpSession) request.getSession();
		Person p = null;
		try {

			personValidator.validate(person, result);
			if (result.hasErrors()) {
				return new ModelAndView("user-login", "person", person);
			}

			if (person == null) {
				person = (Person) session.getAttribute("sessionPerson");
			}
			// Get Person
			p = userDao.login(person);

			// If Person is not found
			if (p == null) {
				System.out.println("EmailId/Password does not exist");
				session.setAttribute("errorMessage", "UserName/Password does not exist");
				return new ModelAndView("error", "errorMessage", "error while login");
			}
			session.setAttribute("pid", p.getPersonID());
			session.setAttribute("sessionPerson", p);
			System.out.println("*******" + p.getFirstName());
			Employer e = p.getEmployer();
			if (e != null) {
				session.setAttribute("eid", e.getEmployerID());
			}
			Role r = p.getRole();

			// Save sessionRoles to know the Role of the person Logged in
			String sessionRoleName = null;
			if (r != null) {
				sessionRoleName = r.getRoleName();
			}
			session.setAttribute("sessionRole", sessionRoleName);
			System.out.println("###############" + sessionRoleName + "###################");
			System.out.println("%%%%%%%%%%" + r.getRoleName());

			session.setAttribute("role", r);
			session.setAttribute("employer", e);
			session.setAttribute("person", p);

			return new ModelAndView("user-home", "person", p);
		} catch (PersonException e) {
			System.out.println("Exception: " + e.getMessage());
			session.setAttribute("errorMessage", "error while login");
			return new ModelAndView("error", "errorMessage", "Incorrect username and/or password");
		}
	}

	@RequestMapping(value = "user/viewEmployees.htm", method = RequestMethod.GET)
	public ModelAndView viewEmployees() {
		List<Person> userList = userDao.getEmployees();
		return new ModelAndView("student-viewEmployees", "userList", userList);
	}

	// Mapping for redirecting to Access denied Pages
	@RequestMapping(value = "user/notAuthorized.htm", method = RequestMethod.GET)
	public ModelAndView notAuthorized(HttpServletRequest request) {
		HttpSession session = (HttpSession) request.getSession();

		Long pid = (Long) session.getAttribute("pid");
		if (pid == null) {
			session.invalidate();
		}

		return new ModelAndView("errorPage");
	}

}
