<jsp:directive.include file="../../common/includes/page_directives.jsp" />
<jsp:directive.include file="../common/script.jsp" />
<c:set var="formAction" value="login" />
<c:set var="formCommandName" value="userForm" />

<c:set var="cp_isCanView" scope="request"
	value="${(curPagePerObj.isCanView() && curPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="cp_isCanAdd" scope="request"
	value="${(curPagePerObj.isCanAdd() && curPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="cp_isCanEdit" scope="request"
	value="${(curPagePerObj.isCanEdit() && curPagePerObj.isIs_edit_applicable())?true:false}" />
<c:set var="cp_isCanDelete" scope="request"
	value="${(curPagePerObj.isCanDelete() && curPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="cp_isCanExecute" scope="request"
	value="${(curPagePerObj.isCanExecute() && curPagePerObj.isIs_execute_applicable())?true:false}" />

<c:set var="dp_isCanView" scope="request"
	value="${(depPagePerObj.isCanView() && depPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="pay_isCanView" scope="request"
	value="${(payPagePerObj.isCanView() && payPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="post_isCanView" scope="request"
	value="${(postPagePerObj.isCanView() && postPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="chk_isCanView" scope="request"
	value="${(chkOutPagePerObj.isCanView() && chkOutPagePerObj.isIs_view_applicable())?true:false}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Mosaddek">
<!--     <link rel="shortcut icon" href="img/favicon.png">
 -->
 
<title>PMS</title>

<link href="<c:url value="/resources/pms/css/online_travel_agent/otabooking.css"/>"
	rel="stylesheet">
</head>

<body>
	<div>
		<c:import url="../common/navbar.jsp" />
	</div>


	<div>
		<div class="row">
			<div class="col-sm-2 col-md-2" style="overflow : auto;">
				<div class="verticalnav divheight" >

					<ul class="nav flex-column" >
						<li class="nav-item"><button class="btn btn-outline " id="btn-checkavailabilitylist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Check Availability</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-retrieveallbookinglist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">All Booking</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-retrievesinglebookinglist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Single Booking</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-bookingreceivednotificationlist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Booking Received</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-arrivalslist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Arrivals</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-departureslist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Departures</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-chargetoroomlist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Charge To Room</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-chargeonroomlist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Charge On Room</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-posreceiptnolist" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">POS Receipt No </a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-postroominfolist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Room Information</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-postroominfospecificlist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Room Information(S)</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-roomsalesdatalist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Room Sales Data</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-roomscalendarlist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Rooms Calendar</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-physicalroomslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Physical Rooms </a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-checkincheckoutlist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Checkin - Checkout </a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-reservationdetailslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Reservation Details </a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-historicalbookingslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Historical Bookings </a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-createabookinglist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Create A Booking </a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-postcreatebookingactionslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Booking Actions</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-bookingbasedonparamslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Booking(Parameters)</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-readabookinglist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Read A Booking</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-cancelabookinglist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Cancel A Booking</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-autosynclist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">AutoSync</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-guestdatalist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Guest Data</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-addpaymentlist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Add Payment</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-guestprofilelist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Guest Profile</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-guestcheckinlist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Guest Check In</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-roomassignmentlist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Room Assignment</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-guestcheckoutlist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Guest Check Out</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-listofbillslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">List Of Bills</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-transactiondetailslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Transaction Details</a></button></li>
						
					</ul>
				</div>
			</div>
			<div class="col-sm-10 col-md-10 datatable ">

				<c:import url="../bookings/bookinglist.jsp" />

			</div>

		</div>
	</div>
	
	
	<script type="text/javascript"
			src="<c:url value='/resources/pms/js/online_travel_agent/otabooking.js' />"></script>
	
</body>
</html>
