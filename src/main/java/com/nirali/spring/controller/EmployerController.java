package com.nirali.spring.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.hibernate.HibernateException;
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
import org.springframework.web.servlet.ModelAndView;

import com.nirali.spring.dao.EmployerDAO;
import com.nirali.spring.dao.StudentDAO;
import com.nirali.spring.dao.UserDAO;
import com.nirali.spring.exception.EmployerException;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.pojo.SupervisorStaff;
import com.nirali.spring.validator.EmployerValidator;

@Controller
public class EmployerController {

	@Autowired
	@Qualifier("studentDao")
	StudentDAO studentDao;

	@Autowired
	@Qualifier("employerDao")
	EmployerDAO employerDao;

	
	  @Autowired
	  @Qualifier("EmployerValidator")
	  EmployerValidator employerValidator;


	
	  @InitBinder private void initBinder(WebDataBinder binder) {
	  binder.setValidator(employerValidator);
	  
	  }
	 

	@RequestMapping(value = "user/employerRegistration.htm", method = RequestMethod.GET)
	public ModelAndView showEmployerRegistrationForm() throws Exception {
		System.out.print("registerEmployer");
		String roleName = "Supervisor";
		return new ModelAndView("employer-registration", "employer", new Employer());
	}

	@RequestMapping(value = "user/employerRegistration.htm", method = RequestMethod.POST)
	public ModelAndView handleEmployerRegistrationForm(HttpServletRequest request,
			@ModelAttribute("employer") Employer employer, BindingResult result) throws Exception {
		System.out.println("handle registrtion");


		employerValidator.validate(employer, result);
		if (result.hasErrors()) { return new
		ModelAndView("employer-registration", "employer", employer); }
		 
		System.out.println("*****a" + employer.getCompanyName());

		try {

			System.out.println("handle registrtion");

			Employer e = employerDao.registerEmployer(employer);

			try {
				Email email = new SimpleEmail();
				email.setSmtpPort(465);
				email.setAuthenticator(new DefaultAuthenticator("temporarywebtools2017@gmail.com", "temporary"));
				email.setHostName("smtp.gmail.com");// if a server is capable of
													// sending email.
				email.setSSL(true);// setSSLOnConnect(true);
				email.setFrom("temporarywebtools2017@gmail.com");
				email.setSubject("Registration to When you work");
				email.setMsg("This is system generated mail, do not reply to this email.");
				email.addTo(e.getEmailAddress());
				email.setTLS(true);// startTLS.enable.true
				email.send();
				System.out.println("Email sent to new Employer!!");
			} catch (Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
				return new ModelAndView("error", "errorMessage", "error while login");
			}

			System.out.println("outside dao");
			request.getSession().setAttribute("employer", e);

			return new ModelAndView("employer-home", "employer", e);

		} catch (EmployerException e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while registration");
		}

	}

}
