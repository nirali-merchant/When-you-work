package com.nirali.spring.validator;

import java.lang.reflect.Field;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.Shifts;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.pojo.SupervisorStaff;

public class SupervisorStaffValidator implements Validator{
	Field[] fields = SupervisorStaff.class.getDeclaredFields();
	
	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(SupervisorStaff.class)
				|| Employer.class.isAssignableFrom(aClass)|| Shifts.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		SupervisorStaff supervisorstaff  = (SupervisorStaff) obj;
		System.out.println("fm");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.firstName", "First Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.lastName", "Last Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailID", "error.invalid.emailID", "Email Address Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password cannot be blank");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employer.companyName", "error.invalid.employer.companyName", "Select an employer");
		
		
		if(supervisorstaff.getEmployer().getCompanyName().equalsIgnoreCase("0"))
		{
			errors.rejectValue("employer.companyName", "error.invalid.employer.companyName", "Select an employer");
		}
		
		
	
	}
	
	
	public boolean checkMaliciousContent(String attribute)
	{
		boolean result;

		if(attribute.matches("<[^>]*>") ||
				attribute.matches("[\\&;`'\\\\\\|\"*?~<>^\\(\\)\\[\\]\\{\\}\\$\\x00]") ||
				attribute.matches("\r") ||
				attribute.matches("\t") ||
				attribute.matches( "\\s+"))
				{
					result = true;
					
				}
		else
		{
			result = false;
		}
		return result;

	}
	
	 
	
	

}
