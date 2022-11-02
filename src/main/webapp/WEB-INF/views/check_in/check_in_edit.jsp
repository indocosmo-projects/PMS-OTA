<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="PMS" scope="request" />
<c:set var="moduleName" value="Check-In" scope="request" />
<c:set var="formId" value="check_in" scope="request" />
<c:set var="formName" value="check_in" scope="request" />
<c:set var="formAction" value="../checkIn/save" scope="request" />
<c:set var="customEditIncludeFile"
	value="../check_in/check_in_edit_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Check-In" scope="request" />
<%-- <c:set var="backUrl" value="/reservation_test/tools?reservationNo=${checkInSessionResvHdr.resvNo}" scope="request"/> --%>
<c:set var="backUrl" value='${backUrl}' scope="request" />

<c:set var="cr_isCanView" scope="request"
	value="${(curPagePerObj.isCanView() && curPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="cr_isCanAdd" scope="request"
	value="${(curPagePerObj.isCanAdd() && curPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="cr_isCanEdit" scope="request"
	value="${(curPagePerObj.isCanEdit() && curPagePerObj.isIs_edit_applicable())?true:false}" />
<c:set var="cr_isCanDelete" scope="request"
	value="${(curPagePerObj.isCanDelete() && curPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="cr_isCanExecute" scope="request"
	value="${(curPagePerObj.isCanExecute() && curPagePerObj.isIs_execute_applicable())?true:false}" />
<c:set var="cr_isCanExecute" scope="request"
	value="${(curPagePerObj.isCanExport() && curPagePerObj.isIs_export_applicable())?true:false}" />
<html ng-app="pmsApp">
<head>
<script type="text/javascript" language="javascript">
window.resvNo=${checkInSessionResvHdr.resvNo};
window.sumNumRooms=${checkInSessionResvHdr.numRooms};
window.cr_isCanEdit=${cr_isCanEdit};
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
<link href="<c:url value="/resources/pms/css/room_rate.css" />"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in_new.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/components/webcam/webcam.js' />"></script>

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/checkin.js' />"></script>
</head>
<body ng-controller="checkInCtrl as checkIn"
	class="full-width checkinbody" ng-cloak>
	<input type="hidden" value="${count}" id="shiftCount">
	<input type="hidden" value="${dateFormat}" id="dateFormat" />
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>