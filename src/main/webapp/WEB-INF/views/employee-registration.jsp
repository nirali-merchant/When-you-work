<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
      <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
   <form:form commandName="person" method="post"  >
      
    First Name:<form:input path ="firstName"/><br/>
	Last Name:<form:input path ="lastName"/><br/>
	email:<form:input path="emailID"/><br/>
	Password : <form:password path = "password" /><br/>
	Employer: <form:select path="employer">
                <form:option value="0" label="--- Select ---" />
                <c:forEach var="emp" items="${employers}">
                    <form:option value="${emp.companyName}"></form:option>
                </c:forEach>
     </form:select>
     Role: <form:select path="role">
                <form:option value="0" label="--- Select ---" />
                <c:forEach var="rol" items="${roles}">
                    <form:option value="${rol.roleName}"></form:option>
                </c:forEach>
     </form:select>
	<input type="submit" value="Register Now"/>
    </form:form>
     <p><a href='user/logout.htm'>Home Page</a></p>
</body>
</html>