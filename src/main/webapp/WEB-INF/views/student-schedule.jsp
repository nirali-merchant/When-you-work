<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Schedule</title>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
	<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>

<script>
	$(document).ready(function() {
		$(".drop").click(function(event) {
			//alert("ELEM: " + $(this).html());
			event.preventDefault();
			$spanelem = $(this);
			var shiftID = $(this).parent().parent().data('shiftid');
			//alert(shiftID);
			$.post("${pageContext.request.contextPath}/student/drop.htm", {
				shiftid : shiftID
			}).done(function(serverdata) {
				//alert("DATA LOADED:");
				var obj = jQuery.parseJSON(serverdata);
				//var obj = serverdata;				
				//alert(obj.shiftid);
				$spanelem.html("Dropped");
				//$("#shiftDetails").text(obj.shiftID);
			});
		});

	});
</script>
<script>
	$(document).ready(function() {
		$(".acknowledge").click(function(event) {
			//alert("ELEM: " + $(this).html());
			event.preventDefault();
			$spanelem = $(this);
			var shiftID = $(this).parent().parent().data('shiftid');
			//alert(shiftID);

			$.post("${pageContext.request.contextPath}/student/acknowledge.htm", {
				"shiftid" : shiftID
			}).done(function(serverdata) {
			//		alert("DATA LOADED:");
				//	alert(serverdata);
				//var obj = jQuery.parseJSON(serverdata);
				//var obj = serverdata;				
				//alert(obj.shiftid);
				$spanelem.html("Acknowledged");
				//$("#shiftDetails").text(obj.shiftID);
			});
		});

	});
</script>
<script>
$(document).ready(function() {
	$(".swapShiftDetails").click(function(event) {
		//alert("ELEM: " + $(this).html());
		event.preventDefault();
		//alert()
		$spanelem = $(this);
		var shift1ID = $(this).parent().data('shift1id');
		//alert(shift1ID);
		//alert($spanelem)
		$.post("${pageContext.request.contextPath}/student/swapShiftDetails.htm", {
			shift1id : shift1ID
		}).done(function(serverdata) {
		//	alert("DATA LOADED:");
			var obj = jQuery.parseJSON(serverdata);
			//	var obj = serverdata;				
			//alert(obj);
			//alert()
			//alert("show");
			
			//alert(obj.companyName);
			//alert(obj.emailID);
			
			//$spanelem.html("Approved");
			//${obj}.appendTo("#employerDetails");
			$("#swappedShiftDetails").html();
			$("#swappedShiftDate").text(obj.Date);
			$("#swappedShiftStartTime").text(obj.StartTime);
			$("#swappedShiftEndTime").text(obj.EndTime);
			
			
		});
	});

});
</script>

<script>
$(document).ready(function() {
	$(".approveSwapShift").click(function(event) {
		//alert("ELEM: " + $(this).html());
		event.preventDefault();
		//alert()
		$spanelem = $(this);
		var shift1ID = $(this).parent().data('shift1id');
		var shift2ID = $(this).parent().data('shift2id');
		var shift3ID = $(this).parent().data('shift3id');
		var shift4ID = $(this).parent().data('shift4id');
		//var initiatorShiftID = $(this).parent().data('initiatorShiftid');
		//var initiatorPersonid = $(this).parent().data('initiatorPersonid');
		//var swappedShiftid = $(this).parent().data('swappedShiftid');
		//var swappedPersonid = $(this).parent().data('swappedPersonid');
		//alert(shift1ID);
		//alert(shift2ID);
		//alert(shift3ID);
		//alert(shift4ID);
		//alert(initiatorShiftID);
		//alert(initiatorPersonid);
	//alert(swappedShiftid)
		//alert(swappedPersonid)
		$.post("${pageContext.request.contextPath}/student/approveSwapShift.htm", {
			shift1id : shift1ID,
			shift2id : shift2ID,
			shift3id : shift3ID,
			shift4id : shift4ID
			//initiatorShiftid : initiatorShiftid, 
			//initiatorPersonid : initiatorPersonid,
		//	swappedShiftid : swappedShiftid, 
			//swappedPersonid : swappedPersonid
		})
		.done(function() {
		//	alert("DATA LOADED:");
			//var obj = jQuery.parseJSON(serverdata);
			//	var obj = serverdata;				
			//alert(obj);
			//alert()
			//alert("show");
			
			//alert(obj.companyName);
			//alert(obj.emailID);
			
			$spanelem.html("Approved");
			//${obj}.appendTo("#employerDetails");
			
			
			
		});
	});

});
</script>


<script>
$(document).ready(function() {
	$(".denySwapShift").click(function(event) {
		alert("ELEM: " + $(this).html());
		event.preventDefault();
		//alert()
		$spanelem = $(this);
		var shift1ID = $(this).parent().data('shift1id');
		var shift2ID = $(this).parent().data('shift2id');
		var shift3ID = $(this).parent().data('shift3id');
		var shift4ID = $(this).parent().data('shift4id');
		//var initiatorShiftID = $(this).parent().data('initiatorShiftid');
		//var initiatorPersonid = $(this).parent().data('initiatorPersonid');
		//var swappedShiftid = $(this).parent().data('swappedShiftid');
		//var swappedPersonid = $(this).parent().data('swappedPersonid');
		//alert(shift1ID);
		//alert(shift2ID);
		//alert(shift3ID);
		//alert(shift4ID);
		//alert(initiatorShiftID);
		//alert(initiatorPersonid);
	//alert(swappedShiftid)
		//alert(swappedPersonid)
		$.post("${pageContext.request.contextPath}/student/denySwapShift.htm", {
			shift1id : shift1ID,
			shift2id : shift2ID,
			shift3id : shift3ID,
			shift4id : shift4ID
			//initiatorShiftid : initiatorShiftid, 
			//initiatorPersonid : initiatorPersonid,
		//	swappedShiftid : swappedShiftid, 
			//swappedPersonid : swappedPersonid
		})
		.done(function() {
		//	alert("DATA LOADED:");
			//var obj = jQuery.parseJSON(serverdata);
			//	var obj = serverdata;				
			//alert(obj);
			//alert()
			//alert("show");
			
			//alert(obj.companyName);
			//alert(obj.emailID);
			
			$spanelem.html("Denied");
			//${obj}.appendTo("#employerDetails");			
		});
	});

});
</script>











</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h4><ul><a style="block:inline " href="${contextPath}/user/home.htm">Home Page</a>     <a href="${contextPath}/user/logout.htm" >Logout</a></ul></h4>

<h4>Schedule</h4>


	<c:choose>
		<c:when test="${fn:length(shiftTrackerList) gt 0}">

			<table border ="1">
				<tr>
					<th>Date</th>
					<th>StartTime</th>
					<th>EndTime</th>
				</tr>
				<c:forEach var="stud" items="${shiftTrackerList}">
					<c:forEach var="shift" items="${stud.shifts} }">
						<form action="swap.htm" method="post">
							<tr data-shiftid=${stud.shifts.shiftID}>
								<input type="hidden" name="hiddenShiftID" value="${stud.shifts.shiftID}">
								<td><p>${stud.shifts.date}</p></td>
								<td><p>${stud.shifts.startTime}</p></td>
								<td><p>${stud.shifts.endTime}</p></td>

								<c:choose>
									<c:when test="${(stud.acknowledged=='false')&& (stud.dropped != 'true')}">
										<td><p>Can't drop without Acknowledging</p></td>
									</c:when>
									<c:when test="${stud.swapped=='true'}">
										
										<td><p>Can't drop while approval is pending </p></td>
									</c:when>
									
									<c:when test="${(stud.swapped == false) && (stud.swappedShiftID !=0) }">
									<td><p>Cannot drop while pending approval</p></td>
									</c:when>
									<c:when test="${stud.dropped == 'true' }">
										<td><p>Dropped</p></td>
									</c:when>
									<c:otherwise>
											<td><span  class= "drop " style=" display: block;
    width: 115px;
    height: 25px;
    background: #4E9CAF;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;" >Drop Shift</span></td>
									</c:otherwise>
								</c:choose>

								<c:choose>
									<c:when test="${(stud.acknowledged=='false')&& (stud.dropped != 'true')}">
										<td><span  class = "acknowledge" style=" display: block;
    width: 115px;
    height: 25px;
    background: #4E9CAF;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;">Acknowledge Shift</span></td>
									</c:when>
									<c:otherwise>
										<td><p>Acknowledged</p></td>
									</c:otherwise>
								</c:choose>
								<input type="hidden" value="${stud.shifts.shiftID}" name="hiddenShiftID">
								<c:choose>
									<c:when test="${stud.swapped=='true'}">
										
										<td><p>Pending Approval </p></td>
									</c:when>
									<c:when test="${(stud.swapped == false) && (stud.swappedShiftID !=0) }">
										<td data-shift1id=${stud.swappedShiftID}
											data-shift2id=${stud.swappedWithStudentID}
											data-shift3id=${stud.shifts.shiftID} 
											 data-shift4id=${stud.studentStaff.personID}>
										    <span class= "approveSwapShift" style=" display: block;
    width: 115px;
    height: 25px;
    background: #4E9CAF;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;"><p>Approve Swap </p></span>
										</td>
										<td data-shift1id=${stud.swappedShiftID}
											data-shift2id=${stud.swappedWithStudentID}
											data-shift3id=${stud.shifts.shiftID} 
											 data-shift4id=${stud.studentStaff.personID}>
										    <span class = "denySwapShift" style=" display: block;
    width: 115px;
    height: 25px;
    background: #4E9CAF;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;"><p>Deny Swap </p></span>
										</td>
										<td data-shift1id=${stud.swappedShiftID} ><span class="swapShiftDetails" style=" display: block;
    width: 115px;
    height: 25px;
    background: #4E9CAF;
    padding: 10px;
    text-align: center;
    border-radius: 5px;
    color: white;
    font-weight: bold;">Swap Shift Details</span></td>
								
										</c:when>
									<c:when test="${stud.acknowledged == 'true'}">
									
										<td><input type="submit" value="Swap"  class="swap"></td>
									</c:when>
									<c:otherwise>
										<td>Can't Swap without acknowledging</td>
									</c:otherwise>

								</c:choose>


							</tr>
							
							
						</form>
					</c:forEach>
				</c:forEach>
			</table>

				



		</c:when>
		<c:otherwise>
			<h4>No Upcoming shifts</h4>
		</c:otherwise>
		
	




	</c:choose>
	
	<p id="swappedShiftDetails"></p>
	<p id="swappedShiftDate"></p>
	<p id="swappedShiftStartTime"></p>
	<p id="swappedShiftEndTime"></p>
		


		
	
	</body>
</html>