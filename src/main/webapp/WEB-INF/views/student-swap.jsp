<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Swap</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		$(".swapthisShift").on('click',function(){
			var shiftID = $(this).parent().parent().data('shiftid');
			alert(shiftID);
				$.post("swapThisShift.htm",{shiftid : shiftID})
					.done(function(serverdata){
						alert("DATA LOADED:");
						var obj = jQuery.parseJSON(serverdata);
						alert(obj.shiftID);	
						$("#shiftDetails").text(obj.shiftID);
					});
		});
		
	});
	
	
</script>
</head>
<body>

<c:choose>
	<c:when test="${fn:length(shifts) gt 0}">
	<h4>Swap with either of the shift</h4>





<table border="1">
	<tr>
		<th>Date</th>
		<th>Start Time</th>
		<th>End Time</th>
		<th>Person</th>
	</tr>
	<c:forEach var="shifts" items="${shifts}">
	<form action="swapThisShift.htm" method="post">
	<tr data-shiftid=${shifts.shifts.shiftID}>
		<td>${shifts.shifts.date}</td>
		<td>${shifts.shifts.startTime}</td>
		<td>${shifts.shifts.endTime}</td>
		<td>${shifts.studentStaff.firstName}</td>
		
		<input type="hidden" value="${shifts.studentStaff.personID}" name="hiddenSwappedPersonID">
		<input type="hidden" value="${shifts.shifts.shiftID}" name="hiddenSwappedID">				
		<td><input type="submit" value="Swap with this shift"></td>
	</tr>
	</form>
	</c:forEach>
</table>
	
	
	
	
	
	
	
	
	</c:when>
	<c:otherwise>
		<h4>No Shifts to swap</h4>
	
	
	
	
	</c:otherwise>





</c:choose>



<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
 <p> <a href = "${contextPath}/user/home.htm">Home Page</a>
  <p> <a href = "${contextPath}/user/logout.htm">Logout</a>

</body>
</html>