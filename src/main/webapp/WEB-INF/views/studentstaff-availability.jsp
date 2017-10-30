<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.js"></script>


<script>
	$(function() {
		$("#date").datepicker({ minDate: 0 ,maxDate: "+14D"});
	});
	
	
</script>
<script>

$(document).ready(function(){
    $('input.timepicker').timepicker({timeFormat: 'H:mm:ss '});
});
</script>

<title>Student Staff Availability</title>
</head>
<body>
	<h4>Set your Availability</h4>
	
	
	
	
	<table>
		<form:form method="post" modelAttribute="availability">
			<tr>
				<td>Date:</td>
				<td><form:input path="date" required = "true"/></td>
				<c:if test="${posted == 'true'}">
					<td>	<p font color= "red">This availability has  already posted or the availability time overlaps with your existing availability</p></td>
	
				</c:if>
			<tr>
				<td>StartTime:</td>
				<td><form:input path="startTime" class="timepicker" required= "true"/>
				</td>
				<c:if test="${invalidAvailSet == 'true'}">
					<td>	<p font color= "red">Start time cannot be greater or equal to End Time</p></td>
	
				</c:if>
				
				
				
			</tr>
			<tr>
				<td>EndTime:</td>
				<td><form:input path="endTime" class="timepicker" required= "true"/></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
			<tr>
			<td>
				
			</td>
			</tr>

			<!--  StartTime:<form:input path ="startTime" type = "time"/>-->
			<!-- StartTime:<form:input path ="startTime"/> -->




		</form:form>




	</table>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<p> <a href = "${contextPath}/user/home.htm">Home Page</a>
<p> <a href = "${contextPath}/user/logout.htm">Logout</a>
</body>
</html>