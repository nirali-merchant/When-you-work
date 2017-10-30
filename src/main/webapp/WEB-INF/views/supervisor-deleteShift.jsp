<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script> 
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="/resources/css/jquery.timepicker.css">
<script>
$(function() {
	  $("#date").datepicker(); 
	});
</script>

<title>Delete Shifts</title>
</head>
<body>

<table>
		<form:form commandName="shifts" method="post" modelAttribute="shifts">
		<tr>
			<td>Date:</td>
			<td><form:input path="date" /><br /></td>
		</tr>
		<tr>
			<td>StartTime:</td>
			<td><form:input path="startTime" /></td>
		</tr>
		<tr>
			<td>EndTime:</td>
			<td><form:input path="endTime" /></td>
		</tr>
		<tr>
			<td><input type="submit" value="Delete" /></td>
		</tr>
</form:form>
	</table>


  




    
    
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<a href='${contextPath}/user/home.htm'> Home Page</a><br/><br/>
	<a href='${contextPath}/user/logout.htm'>Logout</a>
   
</body>
</html>