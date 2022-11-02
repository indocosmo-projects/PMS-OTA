<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Reservation" scope="request" />
<c:set var="moduleName" value="Reservation" scope="request" />
<c:set var="formId" value="Reservation" scope="request" />
<c:set var="formName" value="Reservation" scope="request" />
<c:set var="customEditIncludeFile"
	value="../reservation_test/reservation_tools_custom.jsp"
	scope="request" />
<c:set var="masterListHeaderName" value="Night Audit" scope="request" />
<c:set var="rp_isCanView" scope="request"
	value="${(reservationPagePerObj.isCanView() && reservationPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="rp_isCanAdd" scope="request"
	value="${(reservationPagePerObj.isCanAdd() && reservationPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="rp_isCanEdit" scope="request"
	value="${(reservationPagePerObj.isCanEdit() && reservationPagePerObj.isIs_edit_applicable())?true:false}" />
<c:set var="rp_isCanDelete" scope="request"
	value="${(reservationPagePerObj.isCanDelete() && reservationPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="rp_isCanExecute" scope="request"
	value="${(reservationPagePerObj.isCanExecute() && reservationPagePerObj.isIs_execute_applicable())?true:false}" />
<c:set var="rp_isCanExecute" scope="request"
	value="${(reservationPagePerObj.isCanExport() && reservationPagePerObj.isIs_export_applicable())?true:false}" />

<c:set var="chk_isCanView" scope="request"
	value="${(resCheckInPagePerObj.isCanView() && resCheckInPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="chk_isCanAdd" scope="request"
	value="${(resCheckInPagePerObj.isCanAdd() && resCheckInPagePerObj.isIs_add_applicable())?true:false}" />

<c:set var="dep_isCanView" scope="request"
	value="${(deopsitPagePerObj.isCanView() && deopsitPagePerObj.isIs_view_applicable())?true:false}" />

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
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in.css' />" />
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reservation_tools.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/dateformat.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/moment-with-locales.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/bootstrap-datetimepicker.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reservation_tools.js' />"></script>

</head>
<body class="full-width" ng-controller="resvDtlController"
	ng-init="getReservGuestData('${resvNo}')" ng-cloak>
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>
