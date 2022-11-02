<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="New Reservation" scope="request" />
<c:set var="backUrl" value="/reservation_test/reservationList"
	scope="request" />
<c:set var="backBtnStatusVal" value="0" scope="request" />
<c:set var="moduleName" value="New Reservation" scope="request" />
<c:set var="customEditIncludeFile"
	value="../reservation_test/reservation_new_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Reservation" scope="request" />
<html ng-app="pmsApp">
<head>
<script type="text/javascript" language="javascript">
	function DisableBackButton() {
		window.history.forward()
	}
	DisableBackButton();
	window.onload = DisableBackButton;
	window.onpageshow = function(evt) {
		if (evt.persisted)
			DisableBackButton()
	}
	window.onunload = function() {
		void (0)
	}
</script>
<title>${moduleTitle}</title>
 	<script src="<c:url value='/resources/common/js/jquery.min.js' />"></script>  
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tabcontent.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reservation_new.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/calendar.css' />" />
<link rel="stylesheet" href="https://jqueryvalidation.org/files/demo/site-demos.css">
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
<script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<!--  <script src="https://code.angularjs.org/1.2.1/angular.min.js"></script> -->
<script type="text/javascript"
	src="<c:url value='/resources/common/js/bootstrap-datetimepicker.js' />"></script>
<!-- <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script> -->
<script type="text/javascript"
	src="<c:url value='/resources/common/js/bootstrap-timepicker.js' />"></script>

<link rel="stylesheet"
	href="<c:url value='/resources/common/css/datetimepicker.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/jquery.datetimepicker.css"' />" />
<link rel="stylesheet"
	href="<c:url value='common/css/bootstrap.min.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/timepicker.css' />" />
<%-- <link rel="stylesheet"
	href="<c:url value='common/css/bootstrap-datetimepicker.min.css' />" /> --%>

<script type="text/javascript"
	src="<c:url value='/resources/common/components/date_range_picker/js/moment.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reservation_new.js' />"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/common/components/date_range_picker/jq/daterangepicker.css'/>" />
<script type="text/javascript"
	src="<c:url value='/resources/common/components/date_range_picker/jq/jquery.daterangepicker.js' />"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.0/jquery.validate.js"></script>


<%-- <script type="text/javascript" src="<c:url value='/resources/common/js/bootstrap-datetimepicker.min.js' />"></script> --%>
<%-- <script  type="text/javascript"
	src="<c:url value='/resources/common/js/angular-material-datetimepicker.js' />"></script>></script> --%>
<%-- < <script type="text/javascript"
	src="<c:url value='/resources/common/js/moment.min.js' />"></script> --%>

</head>
<body class="full-width" id="reservation">
	<input type="hidden" id="hotelDate" value="${hotelDate}" />
	<input type="hidden" id="dateFormat" value="${dateFormat}" />
	<input type="hidden" id="currency" value="${currencySymbol}" />
	<input type="hidden" id="maxNights" value="${maxNight}" />
	<input type="hidden" id="confirmbefore" value="${confirmbefore}">
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.datetimepicker.full.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.datetimepicker.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.datetimepicker.full.min.js' />"></script>
</html>
