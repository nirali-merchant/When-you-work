<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pick Shifts</title>

</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:choose>
		<c:when test="${fn:length(droppedShifts) gt 0}">
			<p>Dropped Shifts</p>
			<table border="1">
				<tr>
					<th>Date</th>
					<th>StartTime</th>
					<th>EndTime</th>
				</tr>

				<c:forEach var="drop" items="${droppedShifts}">
					<tr>
						<form action="${contextPath}/student/pickDroppedShifts.htm" method="post">
							<td>${drop.shifts.date}</td>
							<td>${drop.shifts.startTime}</td>
							<td>${drop.shifts.endTime}</td>
							<td><input type="submit" value="Pick Shift" /></td> <input
								type="hidden" value="${drop.shifts.shiftID}"
								name="hiddenDropShiftID" /> <input type="hidden"
								value="${drop.studentStaff.personID}" name="hiddenDropPersonID" />
						</form>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<h4>No Dropped Shifts</h4>
		</c:otherwise>
	</c:choose>


	<c:if
		test="${(fn:length(openShifts) gt 0) || (fn:length(remainingOpenShifts) gt 0)}">
		<p>Open Shift</p>
		<table border="1">
			<tr>
				<th>Date</th>
				<th>StartTime</th>
				<th>EndTime</th>
			</tr>
			</c:if>
			<c:choose>
				<c:when test="${fn:length(openShifts) gt 0}">



					<c:forEach var="open" items="${openShifts}">
						<tr>
							<form action="${contextPath}/student/pickOpenShift.htm" method="post">
								<td>${open.shifts.date}</td>
								<td>${open.shifts.startTime}</td>
								<td>${open.shifts.endTime}</td>
								<td><input type="submit" value="Pick Shift" /></td>
								<td><input type="hidden" value="${open.shifts.shiftID}"
									name="hiddenOpenShiftID" /></td>
								<td><input type="hidden"
									value="${open.studentStaff.personID}" name="hiddenOpenPersonID" /></td>
							</form>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>

			<c:choose>
				<c:when test="${fn:length(remainingOpenShifts) gt 0}">
					<c:forEach var="ropen" items="${remainingOpenShifts}">

						<tr>
							<form action="${contextPath}/student/pickOpenShift.htm" method="post">
								<td>${ropen.date}</td>
								<td>${ropen.startTime}</td>
								<td>${ropen.endTime}</td>
								<td><input type="submit" value="Pick Shift" /> <input
									type="hidden" value="${ropen.shifts.shiftID}"
									name="hiddenOpenShiftID" />
							</form>
						</tr>
					</c:forEach>
		</table>
		</c:when>
		</c:choose>




		
		<p>
			<a href="${contextPath}/user/home.htm">Home Page</a>
		</p>
		<p>
			<a href="${contextPath}/user/logout.htm">Logout</a>
		</p>
</body>
</html>