<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Payment" scope="request" />
<c:set var="moduleName" value="Payment" scope="request" />
<c:set var="backUrl" value="/reception/receptionList" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="formId" value="payment" scope="request" />
<c:set var="formName" value="payment" scope="request" />
<c:set var="customEditIncludeFile"
	value="../payment/discount_list_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Payment" scope="request" />
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
<c:set var="cp_isCanExecute" scope="request"
	value="${(curPagePerObj.isCanExport() && curPagePerObj.isIs_export_applicable())?true:false}" />
<script type="text/javascript">
window.cp_isCanEdit=${cp_isCanEdit};
window.folioBindNo=${folioBindNo};
console.log("cp_isCanAdd:"+window.cp_isCanEdit);
window.payment=${payable.total};
function DisableBackButton() {
window.history.forward()
}
DisableBackButton();
window.onload = DisableBackButton;
window.onpageshow = function(evt) { if (evt.persisted) DisableBackButton() }
window.onunload = function() { void (0) }
</script>
<html>
<head>
<title>Discount Details</title>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/js/angular/css/angular_autocomplete.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in.css' />" />
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/payment.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/payment.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/angular_autocomplete.js' />"></script>
</head>
<body class="full-width" id="reception">
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>
