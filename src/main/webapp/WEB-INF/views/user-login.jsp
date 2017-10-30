<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Login</title>
</head>
<body>
<h4>Login</h4>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<table>
		<form:form commandName="person" method="post" action="home.htm">
			<tr>
				<td>Username :</td>
				<td><form:input type="email" placeholder="Email Address" path="emailID" required= "true"
						 /><font
					color="red"><form:errors path="emailID" /></font><br /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><form:password path="password" /> <font color="red"><form:errors
							path="password" required="true"/></font><br></td>
			<tr>
				<td><input type="submit" value="Login" /></td>
			</tr>
		</form:form>
	</table>





	<p>
	
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<a href='${contextPath}/user/home.htm'> Home Page</a>
	</p>
</body>
</html>