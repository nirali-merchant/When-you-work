package com.nirali.spring.validator;

import java.lang.reflect.Field;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.StudentStaff;
import com.nirali.spring.pojo.SupervisorStaff;



public class PersonValidator implements Validator {

	Field[] fields = Person.class.getDeclaredFields();

	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(Person.class) || Employer.class.isAssignableFrom(aClass) || StudentStaff.class.isAssignableFrom(aClass)|| SupervisorStaff.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		Person person  = (Person) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailID", "error.invalid.emailID", "Email Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.invalid.password", "Password Required");
	
	
		
		
	
	
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




	

