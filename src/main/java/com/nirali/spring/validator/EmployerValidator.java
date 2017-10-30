package com.nirali.spring.validator;

import java.lang.reflect.Field;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.StudentStaff;

public class EmployerValidator implements Validator{

	Field[] fields = Employer.class.getDeclaredFields();
	
	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(Employer.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		Employer employer  = (Employer) obj;
		boolean result;
		System.out.println("fm");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "error.invalid.companyname", "Company Name Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "error.invalid.address", "Address Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNo", "error.invalid.phoneNo", "PhoneNo Required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "error.invalid.emailAddress", "Email Address Required");
		
		System.out.println("fileds size="+ fields.length);
		
		Object value = null;
		for(Field field : fields)
		{	
			field.setAccessible(true);
			 String name = field.getName();
			 try {
				 value = field.get(obj);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
			}
			 System.out.println("value= "+ value.toString());
			 result = checkMaliciousContent(value.toString());
			 if(result)
			 {
				 errors.rejectValue( name, "error."+name ,"Malicious content found");
			 }
			 
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
