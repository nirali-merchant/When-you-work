<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Registration</title>
</head>
<body>
<h4>Student Registration </h4>
	<form:form commandName="studentStaff" method="post"
		enctype="multipart/form-data">
		<table>
			<tr>
				<td>First Name:</td>
				<td><form:input path="firstName" pattern ="[A-Za-z]*"/><font color="red">
					<form:errors path="firstName" /></font><br /></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><form:input path="lastName" pattern ="[A-Za-z]*" oninvalid="setCustomValidity('User character between A-z only')" onchange="try{setCustomValidity('')}catch(e){}"/>
					<font color="red"><form:errors path="lastName" /></font><br />
			</tr>
			<tr>
				<td>Email:</td>
				<td><form:input type="email"  path="emailID" />
					<font color="red"><form:errors path="emailID" /></font><br /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><form:password path="password" />
				<font color="red"><form:errors path="password" /></font><br/></td>
			</tr>
			<tr>
				<td>Employer:</td>
				<td><form:select path="employer.companyName">
						<form:option value="0" label="Select" />
						<c:forEach var="emp" items="${employers}">
							<form:option value="${emp.companyName}"></form:option>
						</c:forEach>
					</form:select><font color="red"><form:errors
							path="employer.companyName" /></font>
					
			</tr>
			<tr>
				<td>Select photo:</td>
				<td><input type="file" name="photo" required="true" /><br /></td>
			</tr>
			<tr>
				<td>Role:</td>
				<td>${roleName}</td>
			</tr>

			<tr>
				<td><input type="submit" value="Register Now" /></td>
			</tr>
		</table>
	</form:form>













	<p>
	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<p> <a href = "${contextPath}/user/home.htm">Home Page</a>
		
	</p>
</body>
</html>