package com.nirali.spring.controller;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nirali.spring.dao.SupervisorDAO;
import com.nirali.spring.dao.UserDAO;
import com.nirali.spring.exception.PersonException;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.pojo.StudentStaff;

@Controller
public class ShiftsController {

	@Autowired
	@Qualifier("userDao")
	UserDAO userDao;

	@Autowired
	@Qualifier("supervisorDao")
	SupervisorDAO supervisorDao;

	@RequestMapping(value = "supervisor/assignShifts.htm", method = RequestMethod.POST)
	public ModelAndView assignShifts(HttpServletRequest request, @ModelAttribute("selectedShift") Shifts shift)
			throws Exception {
		try {
			HttpSession session = request.getSession();
			return null;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return new ModelAndView("error", "errorMessage", "error while Assigning shifts");
		}

	}

}
