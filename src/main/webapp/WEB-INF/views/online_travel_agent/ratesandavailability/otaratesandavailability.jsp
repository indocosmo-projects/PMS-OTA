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

<link href="<c:url value="/resources/pms/css/online_travel_agent/otareservation.css"/>"
	rel="stylesheet">
</head>

<body>
	<div>
		<c:import url="../common/navbar.jsp" />
	</div>


	<div>
		<div class="row">
			<div class="col-sm-2 col-md-2" style="overflow : auto;">
				<div class="verticalnav" >

					<ul class="nav flex-column" >
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-updateroominventorylist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Room Inventory(U)</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-updatelinearratelist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Linear Rate</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-updatenonlinearratelist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Non Linear Rate</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-retrieveroomratessdlist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Room Rates(SD)</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-updatemaxnightslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Max Nights</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-updateminnightslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Min Nights</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-updatestopselllist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">StopSell</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-updatecloseonarrivallist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Close On Arrival</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-updatecloseondeparturelist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Close On Departure</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-retrieveroominventorylist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Room Inventory(R)</a></button></li>
						
						<li class="nav-item"><button class="btn btn-outline" id="btn-retrieveroomrateslist" style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Room Rates</a></button></li>
						
					</ul>
				</div>
			</div>
			<div class="col-sm-10 col-md-10 datatable">

				<c:import url="../ratesandavailability/ratesandavailabilityList.jsp" />

			</div>

		</div>
	</div>
	
	
	<script type="text/javascript"
			src="<c:url value='/resources/pms/js/online_travel_agent/otaratesandavailability.js' />"></script>
	
</body>
</html>
