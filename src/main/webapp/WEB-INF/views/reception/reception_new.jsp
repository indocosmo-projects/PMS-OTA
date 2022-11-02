<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="New CheckIn" scope="request" />
<%-- <c:set var="backUrl" value="reception/receptionList" scope="request"/>
 --%>
<c:set var="backUrl" value="receptionList" scope="request" />
<c:set var="backBtnStatusVal" value="0" scope="request" />
<c:set var="moduleName" value="New Check In" scope="request" />
<c:set var="customEditIncludeFile"
	value="../reception/reception_new_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Check In" scope="request" />
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
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tabcontent.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in_new.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reception_new.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/calendar.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reservation_new.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reception_new.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/components/webcam/webcam.js' />"></script>
	

</head>
<body class="full-width" id="reception">
	<input type="hidden" id="hotelDate" value="${hotelDate}" />
	<input type="hidden" id="currency" value="${currencySymbol}" />
	<input type="hidden" id="dateFormat" value="${dateFormat}" />
	<input type="hidden" id="maxNights" value="${maxNight}" />
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>