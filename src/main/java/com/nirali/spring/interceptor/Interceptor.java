package com.nirali.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter {
	 String errorPage;

	    public String getErrorPage() {
	        return errorPage;
	    }

	    public void setErrorPage(String errorPage) {
	        this.errorPage = errorPage;
	    }

	    @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	            throws Exception {
	        System.out.println("----------------------");
	        HttpSession session = (HttpSession) request.getSession();
	        System.out.println("--"+session.getAttribute("sessionRole"));
	        System.out.println("uri="+request.getRequestURI());
	       if(session.getAttribute("sessionRole") == null){
	            if((request.getRequestURI().contains("admin/"))||
	                    (request.getRequestURI().contains("student/")) || (request.getRequestURI().contains("supervisor/")) )
	            {
	                System.out.println("in interceptor");
	                System.out.println("1 -false");
	                response.sendRedirect(errorPage);
	                return false;
	            }
	            System.out.println("in interceptor");
	            return true;
	        }
	       
	        if(session.getAttribute("sessionRole") != null){
	            System.out.println("in interceptor");
	            if((request.getRequestURI().contains("admin") && session.getAttribute("sessionRole").equals("Admin")) ||
	                    (request.getRequestURI().contains("student") && session.getAttribute("sessionRole").equals("Student"))||
	                    (request.getRequestURI().contains("supervisor") && session.getAttribute("sessionRole").equals("Supervisor"))
	                    ||(request.getRequestURI().contains("user") && session.getAttribute("sessionRole").equals("user")))
	            {
	                System.out.println("in interceptor");
	                return true;
	            }
	          /*  if(request.getRequestURI().contains("logout") || request.getRequestURI().contains("home") ||  request.getRequestURI().contains("viewEmployees") )
	            {
	            	 System.out.println("inside logout interceptor");
	            	return true;
	            }*/
	            
	            if(request.getRequestURI().contains("user"))
	            {
	            	return true;
	            }
	        }

	        System.out.println("NOT VALID!!");
	        response.sendRedirect(errorPage);
	        System.out.println("1 -false");
	        return false;
	    }
}
