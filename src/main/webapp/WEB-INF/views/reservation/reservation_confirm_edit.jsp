<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Confirmation" scope="request" />
<c:set var="moduleName" value="Confirmation" scope="request" />
<c:set var="formId" value="resvConfirmation" scope="request" />
<c:set var="formName" value="resvConfirmation" scope="request" />
<c:set var="formAction" value="../reservation/resvConfirm"
	scope="request" />
<c:set var="formCommandName" value="resvConfirm" scope="request" />
<c:set var="customEditIncludeFile"
	value="../reservation/reservation_confirm_custom.jsp" scope="request" />

<html>
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
	href="<c:url value='/resources/pms/css/reservation_confirm.css' />" />

<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/payment.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />">
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />" />

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tabcontent.js' />"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in.css' />" />


<c:import url="../common/includes/master_includes.jsp" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_grid_common.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/reservation_confirm.js' />"></script>

<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/1.2.26/angular.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reservation_confirm.js' />"></script>

</head>
<body class="full-width" id="reservation">
	<c:import url="../menu/topMenu.jsp" />
	<form:form id="${formId}" name="${formName}"
		commandName="${formCommandName}" action="${formAction}" method="POST">
		<c:import url="../common/includes/transaction_edit.jsp" />

	</form:form>
</body>