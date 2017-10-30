<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Employees</title>
</head>
<body>
<h4>Employee Details</h4>
<c:choose>
            <c:when test="${empty requestScope.userList}">
                <h3>No Users Found !</h3>                
        </c:when>
        
        <c:otherwise>
            <table border="1">
                <tr>
               		<th>Photo</th>
               		<th>Role</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    
                    
                </tr>
             <c:forEach items="${requestScope.userList}" var="user">
                 <tr>
                 <td> <img height="150" width="150" src="${user.filename}" /></td>
                 <td>${user.role.roleName}</td>
                     <td> ${user.firstName}</td>
            <td> ${user.lastName}</td>
            <td> ${user.emailID}</td>
           
            </tr>
        </c:forEach>
            </table>
        </c:otherwise>
       </c:choose>
       <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
 		<p> <a href = "${contextPath}/user/home.htm">Home Page</a>
 		<p> <a href = "${contextPath}/user/logout.htm">Logout</a>
 
</body>
</html>