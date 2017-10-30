<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
</head>
<body>

<h1>Error Page</h1>
<p>${errorMessage}</p>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:if test="${person.personID == 0}">
<p><a href = "login.htm">Login</a></br></p>
<p> <a href = "${contextPath}/user/home.htm">Home Page</a></p>
</c:if>
<c:if test="${person.personID != 0}">
<p> <a href = "${contextPath}/user/home.htm">Home Page</a>
<p> <a href = "${contextPath}/user/logout.htm">Logout</a>
</c:if>
</body>
</html>