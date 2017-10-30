<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet"
	href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script
	src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>

<script>
	$(function() {
		$("#date").datepicker({
			minDate : 0,
			maxDate : "+14D"
		});
	});
</script>
<script>
	$(document).ready(function() {
		$('input.timepicker').timepicker({
			timeFormat : 'H:mm:ss '
		});
	});
</script>


<title>Allot Shifts</title>
</head>

<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	
<h4><ul><a style="block:inline " href="${contextPath}/user/home.htm">Home Page</a>    <a href="${contextPath}/user/logout.htm" >Logout</a></ul></h4>
<h4>Assign Shifts</h4>
	<table>
		<form:form commandName="shifts" method="post" modelAttribute="shifts">
			<c:if test="${shiftSpan =='true'}">
				<tr>
					<td><p style="font color:red">Shift Span cannot be more than 2 hrs</p></td>
				</tr>	
			</c:if>	
			<tr>
				<td>Date:</td>
				<td><form:input path="date" required= "true" /><br /></td>
				<c:if test="${posted == 'true'}">
					<td>
						<p style="font color:red">This shift has already been posted</p>
					</td>

				</c:if>
			</tr>
			<tr>
				<td>StartTime:</td>
				<td><form:input class="timepicker" path="startTime" required="true" /></td>
				<c:if test="${invalidAvailSet == 'true'}">
					<td>
						<p style="font color :red">Start time cannot be greater or equal to
							End Time</p>
					</td>
				</c:if>
			</tr>
			<tr>
				<td>EndTime:</td>
				<td><form:input class="timepicker" path="endTime" required="true" /></td>
			</tr>
			<tr>
				<td>No. of Employees for this Shift:</td>
				<td><form:input path="employeeCount" required="true" pattern="[1-9]" oninvalid="setCustomValidity('Must be greater than 0')" onchange="try{setCustomValidity('')}catch(e){}" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</form:form>
	</table>
		
</body>
</html>