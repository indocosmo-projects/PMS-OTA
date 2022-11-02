<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Transaction Transfer" scope="request" />
<c:set var="moduleName" value="Transaction Transfer" scope="request" />

<c:set var="backUrl" value="/reception/receptionList" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />

<c:set var="formId" value="transaction_transfer" scope="request" />
<c:set var="formName" value="transaction_transfer" scope="request" />
<c:set var="customEditIncludeFile"
	value="../transaction/transaction_transfer_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Transaction Transfer"
	scope="request" />
<c:set var="folioNum" value="${folioNo}" scope="request" />
<c:set var="folioBindNum" value="${folioBindNo}" scope="request" />
<script type="text/javascript">
window.count=${count};
</script>

<html ng-app="pmsApp">
<head>
<title>${moduleTitle}</title>
<script type="text/javascript" language="javascript">
window.folioNo=${folioNum};
window.folioBindNo=${folioBindNum};

function DisableBackButton() {
window.history.forward()
}
DisableBackButton();
window.onload = DisableBackButton;
window.onpageshow = function(evt) { if (evt.persisted) DisableBackButton() }
window.onunload = function() { void (0) }
</script>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/js/angular/css/angular_autocomplete.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/payment.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in.css' />" />
<c:import url="../common/includes/master_includes.jsp" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/txn_transfer.js' />"></script>
<link href="<c:url value="/resources/pms/css/transaction.css" />"
	rel="stylesheet" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/angular_autocomplete.js' />"></script>
</head>
<body class="full-width" id="tools">
	<input type="hidden" id="dateFormat" value="${dateFormat}" />
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>
