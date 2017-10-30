<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Availability</title>
</head>
<body>
<h4>Your Availability</h4>
<c:choose>
            <c:when test="${empty requestScope.availList}">
                <h3>No Availability Found !</h3>                
        </c:when>
        
        <c:otherwise>
            <table border="1">
                <tr>
               		<th>Date</th>
               		<th>StartTime</th>
                    <th>EndTme</th>
                    
                    
                    
                </tr>
             <c:forEach items="${requestScope.availList}" var="avail">
                 <tr>
                
                 <td>${avail.date}</td>
                     <td> ${avail.startTime}</td>
            <td> ${avail.endTime}</td>
          
           
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