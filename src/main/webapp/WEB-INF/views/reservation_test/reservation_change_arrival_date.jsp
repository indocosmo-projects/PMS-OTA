<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Change Arrival Date" scope="request" />
<c:set var="backUrl"
	value="/reservation_test/tools?reservationNo=${reservationNo}"
	scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="moduleName" value="Change Arrival Date" scope="request" />
<c:set var="customEditIncludeFile"
	value="../reservation_test/reservation_change_arrival_date_custom.jsp"
	scope="request" />
<c:set var="masterListHeaderName" value="Change Arrival" scope="request" />
<html ng-app="pmsApp">
<head>

<title>${moduleTitle}</title>

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tabcontent.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reception_new.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/components/date_range_picker/js/moment.min.js' />"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/change_arrival.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/change_arrival.js' />"></script>
</head>
<body class="full-width" id="changeArrival"
	ng-controller="changeArrCtrl as changectrl" ng-cloak>
	<input type="hidden" id="hotelDate" value="${hotelDate}" />
	<input type="hidden" id="currency" value="${currencySymbol}" />
	<input type="hidden" id="dateFormat" value="${dateFormat}" />
	<input type="hidden" id="maxNights" value="${maxNight}" />
	<input type="hidden" id="resvNo" value="${reservationNo}" />
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>
