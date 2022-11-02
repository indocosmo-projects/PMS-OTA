<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Reservation No-Show" scope="request" />
<c:set var="backUrl"
	value="/reservation_test/tools?reservationNo=${reservationNo}"
	scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="moduleName" value="Reservation  No-Show" scope="request" />
<c:set var="customEditIncludeFile" value="../noshow/noshow_custom.jsp"
	scope="request" />
<c:set var="masterListHeaderName" value="NoShow" scope="request" />
<html ng-app="pmsApp">
<head>
<script type="text/javascript" language="javascript">
window.depositAmnt=window.depositAmnt=${resHdr.depositAmount};
function DisableBackButton() {
window.history.forward()
}
DisableBackButton();
window.onload = DisableBackButton;
window.onpageshow = function(evt) { if (evt.persisted) DisableBackButton() }
window.onunload = function() { void (0) }
</script>
<title>${moduleTitle}</title>
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/noshow.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/noshow.js' />"></script>
</head>
<body class="full-width" id="reservation" ng-controller="noshow_ctrl">
	<input type="hidden" id="hotelDate" value="${hotelDate}" />
	<input type="hidden" id="currency" value="${currencySymbol}" />
	<input type="hidden" id="reservationNo" value="${reservationNo}" />
	<input type="hidden" id="dateFormat" value="${dateFormat}" />
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>
