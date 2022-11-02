<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Cancellation" scope="request" />
<c:set var="moduleName" value="Cancellation" scope="request" />
<c:set var="formId" value="resvCancel" scope="request" />
<c:set var="formName" value="resvCancel" scope="request" />
<c:set var="formAction" value="../reservation_test/resvCancellation"
	scope="request" />
<c:set var="formCommandName" value="resvCancellation" scope="request" />
<c:set var="customEditIncludeFile"
	value="../reservation_test/reservation_cancellation_custom.jsp"
	scope="request" />
<c:set var="backUrl"
	value="/reservation_test/tools?reservationNo=${resHdr.resvNo}"
	scope="request" />
<html>
<head>
<script type="text/javascript" language="javascript">

	window.reservationNo = ${resHdr.resvNo}
	/* {
		resHdr.resvNo
	} */
	window.depositAmnt = ${resHdr.depositAmount}
	/* {
		resHdr.depositAmount
	}; */
	function DisableBackButton() {
		window.history.forward()
	}
	DisableBackButton();
	window.onload = DisableBackButton;
	window.onpageshow = function(evt) {
		if (evt.persisted)
			DisableBackButton()
	};
	window.onunload = function() {
		void (0)
	};
</script>
<title>${moduleTitle}</title>
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/reservation_cancel.css' />" />
<c:import url="../common/includes/master_includes.jsp" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/reservation_cancel.js' />"></script>
</head>
<body class="full-width" id="reservation">
	<input type="hidden" id="reservationNo" value="${resHdr.resvNo}" />
	<c:import url="../menu/topMenu.jsp" />
	<form:form id="${formId}" name="${formName}"
		commandName="${formCommandName}" action="${formAction}" method="POST">
		<c:import url="../common/includes/transaction_edit.jsp" />
	</form:form>

</body>