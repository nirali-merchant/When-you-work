<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Assign Shifts</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
	<form action="${contextPath}/supervisor/assign-shifts.htm" method="post">
		<p>Date : ${stringDate}</p>
		<br /> <select name ="students" multiple>
			<option value="0" label="--- Select ---" /></option>
			<c:forEach var="ss" items="${sessionScope.availableStuds}">
				<option value="${ss.personID}">${ss.firstName}</option>
			</c:forEach>
		</select> <input type="submit" value="Assign" />
	</form>

	
	

 <p> <a href = "${contextPath}/user/home.htm">Home Page</a>
</body>
</html>