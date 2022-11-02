<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Edit Reservation" scope="request" />
<c:set var="backUrl"
	value="/reservation_test/tools?reservationNo=${reservationNo}"
	scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="moduleName" value="Edit Reservation" scope="request" />
<c:set var="customEditIncludeFile"
	value="../reservation_test/reservation_edit_new_custom.jsp"
	scope="request" />
<c:set var="masterListHeaderName" value="Reservation" scope="request" />
<html ng-app="pmsApp">
<head>
<title>${moduleTitle}</title>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tabcontent.js' />"></script>

<c:import url="../common/includes/master_includes.jsp" />
<%-- <link rel="stylesheet"
	href="<c:url value='/resources/common/components/dashboard/css/theme-default.css' />" /> --%>


<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reception_new.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reservation_edit_new.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in_new.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/calendar.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/components/date_range_picker/js/moment.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reservation_edit.js' />"></script>

<link rel="stylesheet"
	href="<c:url value='/resources/common/components/date_range_picker/jq/daterangepicker.css'/>" />
<script type="text/javascript"
	src="<c:url value='/resources/common/components/date_range_picker/jq/jquery.daterangepicker.js' />"></script>



</head>
<body class="full-width" id="reservation"
	ng-controller="reservationEditCtrl" ng-cloak>
	<input type="hidden" id="hotelDate" value="${hotelDate}" />
	<input type="hidden" id="currency" value="${currencySymbol}" />
	<input type="hidden" id="dateFormat" value="${dateFormat}" />
	<input type="hidden" id="maxNights" value="${maxNight}" />
	<input type="hidden" id="reservationNo" value="${reservationNo}" />

	<c:import url="../common/includes/transaction_edit.jsp" />

</body>
</html>
