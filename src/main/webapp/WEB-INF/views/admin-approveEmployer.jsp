<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Approve Employer</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>

	
<script>

$(document).ready(function() {
	$(".approve").click(function(event) {
		//alert("ELEM: " + $(this).html());
		event.preventDefault();
		$spanelem = $(this);
		var employerID = $(this).parent().parent().data('employerid');
		//alert(employerID);
		$.post("${pageContext.request.contextPath}/admin/approve.htm", {
			employerid : employerID
		}).done(function() {
			//alert("DATA LOADED:");
			//var obj = jQuery.parseJSON(serverdata);
			//var obj = serverdata;				
			//alert(obj.shiftid);
			$spanelem.html("Approved");
			//$spanelem.htm("<span class="disapprove">Disapprove</span>")
			//$("#shiftDetails").text(obj.shiftID);
		});
	});

});
</script>


<script>
$(document).ready(function() {
	$(".details").click(function(event) {
		//alert("ELEM: " + $(this).html());
		event.preventDefault();
		$spanelem = $(this);
		var employerID = $(this).parent().parent().data('employerid');
		//alert(employerID);
		//alert($spanelem);
		$.post("${pageContext.request.contextPath}/admin/details.htm", {
			employerid : employerID
		}).done(function(serverdata) {
			//alert("DATA LOADED:");
			var obj = jQuery.parseJSON(serverdata);
			//	var obj = serverdata;				
			//alert(obj);
			//alert()
			//alert("show");
			
			//alert(obj.companyName);
			//alert(obj.emailID);
			//$spanelem.html("Approved");
			//${obj}.appendTo("#employerDetails");
			$("#employerPhoneNo").html();
			$("#employerPhoneNo").text(obj.PhoneNo);
			$("#employerEmailID").text(obj.emailID);
			$("#employerAddress").text(obj.Address);
		});
	});

});
</script>

</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<h4><ul><a style="block:inline; " href="${contextPath}/user/home.htm">Home Page</a>        <a href="${contextPath}/user/logout.htm" class = "btn">Logout</a></ul></h4>

<c:choose>
	<c:when test="${fn:length(pendingEmployers) gt 0}">
	<table border= "1">
		<tr>
			<th>Employer</th>
		</tr>
		<c:forEach var="employer" items="${pendingEmployers}">
			<tr data-employerid=${employer.employerID}>
			
				<td>
					${employer.companyName}
				</td>
				<td><span  class="approve" style=" display: block;
    width: 115px;
    height: 25px;
    background: #4E9CAF;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;">Approve </span></td>
				<td><span  class="details" style=" display: block;
    width: 115px;
    height: 25px;
    background: #4E9CAF;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;">View Details</span></td>
				<input type="hidden" value="${employer.employerID}" name="hiddenEmployerID">
			</tr>
		</c:forEach>


	</table>
	
	</c:when>
	<c:otherwise>
		<p>No Pending Approvals</p>
	
	</c:otherwise>
</c:choose>
	
	
	<p id="employerPhoneNo"></p>
	<p id="employerEmailID"></p>
	<p id="employerAddress"></p>



</body>
</html>