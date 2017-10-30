<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NEU OnCampus Job Scheduler</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <H3>When you Work:Online Employee Scheduler for Northeastern University</H3>
    <a href = "${contextPath}/user/employerRegistration.htm">New Employer</a></br>
    <a href = "${contextPath}/user/studentRegistration.htm">New Student</a></br>
    <a href = "${contextPath}/user/supervisorRegistration.htm">New Supervisor</a></br>
    <a href = "${contextPath}/user/login.htm">Login</a></br>
</body>
</html>