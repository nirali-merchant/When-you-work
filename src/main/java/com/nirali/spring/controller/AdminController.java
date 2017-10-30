package com.nirali.spring.controller;

import java.util.List;

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
import org.springframework.web.servlet.View;

import com.nirali.spring.dao.AdminDAO;
import com.nirali.spring.dao.EmployerDAO;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Role;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.validator.AdminValidator;
import com.nirali.spring.validator.EmployerValidator;

@Controller
public class AdminController {

	@Autowired
	@Qualifier("adminDao")
	AdminDAO adminDao;
	
	 @Autowired
	  @Qualifier("AdminValidator")
	 AdminValidator adminValidator;
	 
	 @InitBinder private void initBinder(WebDataBinder binder) {
		  binder.setValidator(adminValidator);
	 }
	 
	 
	@RequestMapping(value = "admin/insertRoles.htm", method = RequestMethod.GET)
	public ModelAndView insertRoles()
	{
		return new ModelAndView("admin-manageRoles", "role" ,new Role());
	}
	
	
	@RequestMapping(value = "admin/insertRoles.htm", method = RequestMethod.POST)
	public ModelAndView manageRoles(HttpServletRequest request, @ModelAttribute("role") Role role,
			BindingResult result) throws Exception
	{
		adminValidator.validate(role, result);
		if (result.hasErrors()) { return new
		ModelAndView("admin-manageRoles", "role", role); }
		 
		
		List<Role> roles = adminDao.checkRole(role.getRoleName());
		if(roles.isEmpty())
		{
			adminDao.insertRoles(role.getRoleName());
		}
		else
		{
			return new ModelAndView("error", "errorMessage", "This Role Already exists");
		}
		return new ModelAndView("user-home", "role" ,new Role());
	}
	
	@RequestMapping(value = "admin/deleteRoles.htm", method = RequestMethod.GET)
	public ModelAndView deleteRoles(HttpServletRequest request, @ModelAttribute("role") Role role,
			BindingResult result) throws Exception
	{
		
		HttpSession session= request.getSession();
		
		List<Role> roles = adminDao.getRoles();
		session.setAttribute("roles", roles);
		return new ModelAndView("admin-deleteRole", "roles", roles);
	}
	
	
	@RequestMapping(value = "admin/deleteRoles.htm", method = RequestMethod.POST)
	public ModelAndView handleDeleteRoles(HttpServletRequest request, @ModelAttribute("role") Role role,
			BindingResult result) throws Exception
	{
		HttpSession session= request.getSession();
		adminValidator.validate(role, result);
		if (result.hasErrors()) { 
			List<Role> roles = (List<Role>) session.getAttribute("roles");
			return new
		ModelAndView("admin-deleteRole", "roles", roles); }
		
		adminDao.deleteRole(role.getRoleName());
		System.out.println("role deleted");
		
		return new ModelAndView("user-home");
	}

	@RequestMapping(value = "admin/approveEmployer.htm", method = RequestMethod.GET)
	public ModelAndView approveEmployer(HttpServletRequest request) throws Exception
	{
		HttpSession session = request.getSession();
		List<Employer> pendingEmployers = adminDao.getPendingEmployers();
		session.setAttribute("pendingEmployers",pendingEmployers);
		
		return new ModelAndView("admin-approveEmployer");
	}
	
	
	@RequestMapping(value="admin/employees.htm", method=RequestMethod.GET)    
	public ModelAndView employersData(){
	    View view = new EmployersDataXLSView();
	    return new ModelAndView(view);
	}
	
	
	@RequestMapping(value = "admin/discontinueEmployer.htm", method = RequestMethod.GET)
	public ModelAndView discontinueEmployer(HttpServletRequest request) throws Exception
	{
		HttpSession session = request.getSession();
		List<Employer> approvedEmployers = adminDao.getApprovedEmployers();
		session.setAttribute("approvedEmployers",approvedEmployers);
		
		return new ModelAndView("admin-disapproveEmployer");
	}	
}
