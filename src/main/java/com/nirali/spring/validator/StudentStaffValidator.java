package com.nirali.spring.validator;

import java.lang.reflect.Field;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nirali.spring.dao.UserDAO;
import com.nirali.spring.pojo.Availability;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.ShiftTracker;
import com.nirali.spring.pojo.StudentStaff;


@Component
public class StudentStaffValidator implements Validator {
	Field[] fields = StudentStaff.class.getDeclaredFields();
	
	UserDAO userdao = new UserDAO();

	public boolean supports(Class aClass) {
		return aClass.equals(StudentStaff.class) || Availability.class.isAssignableFrom(aClass)|| ShiftTracker.class.isAssignableFrom(aClass);
	}

	public void validate(Object obj, Errors errors) {
		StudentStaff studentstaff = (StudentStaff) obj;
		System.out.println("inside this");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.invalid.firstname", "First Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "error.invalid.lastName", "Last Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailID",  "error.invalid.emailID", "Email Address Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employer", "error.invalid.employer", "employer required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employer.companyName", "error.invalid.employer.companyName", "Select an employer");

		
		System.out.println("outside this");	
		
		// check if user exists
		
		if(studentstaff.getEmployer().getCompanyName().equalsIgnoreCase("0"))
		{
			errors.rejectValue("employer.companyName", "error.invalid.employer.companyName", "Select an employer");
		}
		
		
		boolean result = userdao.checkUniqueEmail(studentstaff.getEmailID(), studentstaff.getEmployer().getCompanyName());
		if(!result)
		{
			errors.rejectValue("emailID", "error.emailID", "This email Address is already registered");
		
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
	
	
	


	

