<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employer Registration</title>
</head>
<body>
	<h4>Employer Registration</h4>
	<table>
		<form:form commandName="employer" method="post">
			<tr>
				<td>Company Name:</td>
				<td><form:input path="companyName" /><font color="red"><form:errors
							path="companyName" /></font></td>
			</tr>

			<tr>
				<td>Email Address :</td>
				<td><form:input type= "email" path="emailAddress"  /><font color="red"><form:errors
							path="emailAddress" /></font></td>
			</tr>
			<tr>
				<td>Address:</td>
				<td><form:input path="address" /><font color="red"><form:errors
							path="address" /></font></td>
			</tr>
			<tr>
				<td>Phone Number :</td>
				<td><form:input path="phoneNo" maxlength="10" pattern ="[0-9-]*"/><font color="red"><form:errors
							path="phoneNo" /></font></td>
			</tr>
			<tr>
			<td><input type="submit" value="Register Now" /></td>
			</tr>
		</form:form>
	</table>
	<p>
		<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
 <p> <a href = "${contextPath}/user/home.htm">Home Page</a>
	</p>
</body>
</html>