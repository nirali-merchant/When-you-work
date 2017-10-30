package com.nirali.spring.validator;

import java.lang.reflect.Field;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nirali.spring.pojo.Employer;
import com.nirali.spring.pojo.Person;
import com.nirali.spring.pojo.Role;

public class AdminValidator implements Validator{

	Field[] fields = Role.class.getDeclaredFields();
	boolean result = false;
	@Override
	public boolean supports(Class aClass) {
		return aClass.equals(Role.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		Role role  = (Role) obj;
		System.out.println("fm");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleName", "error.invalid.roleName", "Role name cannot be blank");
		
		if(role.getRoleName().equalsIgnoreCase("0"))
		{
			errors.rejectValue("roleName", "error.invalid.roleName", "Select the role to be deleted");
		}
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
	


