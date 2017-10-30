package com.nirali.spring.validator;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nirali.spring.pojo.Availability;
import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Person;

public class AvailabilityValidator implements Validator{
	
	Field[] fields = Availability.class.getDeclaredFields();
	
	
	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(Availability.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		Availability avail  = (Availability) obj;
		System.out.println("fm");
		
		Date date = avail.getDate();
		Date startTime = avail.getStartTime();
		Date endTime = avail.getEndTime();
		
		if(endTime.before(startTime))
		{
			errors.rejectValue("startTime", "error.startTime", "StartTime cannot be after endTime");
			
		}
		
		boolean result;
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
