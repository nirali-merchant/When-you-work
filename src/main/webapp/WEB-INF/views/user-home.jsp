<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Home</title>
</head>
<body>

	<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<a href='${contextPath}/user/logout.htm'> Logout</a>
	<h4>Hi, ${person.firstName}</h4>
	
	<c:if test="${person.role.roleName == 'Supervisor'}">
		<p>Employer : ${employer.companyName}</p>
		
		<a href="${contextPath}/supervisor/allotShifts.htm">Allot shifts</a>
		<a href="${contextPath}/supervisor/deleteShifts.htm">Delete shifts</a>
		<a href="${contextPath}/user/viewEmployees.htm">View Employees</a>
		<!-- <a href="${contextPath}/supervisor/todaysStaff.htm">View Todays employees</a> -->

	</c:if>

	<c:if test="${person.role.roleName == 'Student'}">
		<p>Employer : ${employer.companyName}</p>
		

		<a href="${contextPath}/student/giveAvailability.htm">Set your Availability</a><br>
		<a href="${contextPath}/student/viewAvailability.htm">View your Availability</a><br>
		<a href="${contextPath}/student/viewSchedule.htm">View Schedule</a><br>
		<a href="${contextPath}/student/pickShifts.htm">Pick up Shifts</a><br>
		<a href="${contextPath}/user/viewEmployees.htm">View CoWorkers</a><br>


	</c:if>
	
	<c:if test="${person.role.roleName == 'Admin'}">
		
		

		<a href="${contextPath}/admin/insertRoles.htm">Add Roles</a><br>
		<a href="${contextPath}/admin/deleteRoles.htm">Delete Roles</a><br>
		<a href="${contextPath}/admin/approveEmployer.htm">Approve Employer</a><br>
	<!--  	<a href="${contextPath}/discontinueEmployer.htm">Discontinue Employer</a><br>-->
		<a href="${contextPath}/admin/employees.htm">View Employees Excel</a><br>
		

	</c:if>
	
	
	
	<p>

	
		
	</p>
</body>
</html>