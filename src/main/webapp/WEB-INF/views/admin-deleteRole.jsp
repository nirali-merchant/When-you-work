<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete Roles</title>
</head>
<body>
	<table>
		<form:form commandName="role" method="post">
			<tr>
				<td><form:select path="roleName">
						<form:option value="0" label="Select" />
						<c:forEach var="role" items="${roles}">
							<c:if test="${role.roleName ne 'Admin'}">
							<form:option value="${role.roleName}"></form:option>
							</c:if>
						</c:forEach>
					</form:select> <font color="red"><form:errors path="roleName" /></font></td>
			</tr>
			<tr>
				<td><input type="submit" value="Submit"></td>
			</tr>
		</form:form>



	</table>

</body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
 <p> <a href = "${contextPath}/user/home.htm">Home Page</a>
 <p> <a href = "${contextPath}/user/logout.htm">Logout</a>
</html>