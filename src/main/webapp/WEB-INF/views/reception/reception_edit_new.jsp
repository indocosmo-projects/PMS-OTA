<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Reception Edit" scope="request" />
<c:set var="moduleName" value="Reception Edit" scope="request" />
<c:set var="backUrl" value="/reception/receptionList" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />

<c:set var="formId" value="reception" scope="request" />
<c:set var="formName" value="reception" scope="request" />
<c:set var="formAction" value="../reception/roomAvailability"
	scope="request" />
<c:set var="formCommandName" value="checkInMain" scope="request" />
<c:set var="customEditIncludeFile"
	value="../reception/reception_edit_new_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Check-In" scope="request" />

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
	href="<c:url value='/resources/pms/css/reception_edit_new.css'/>" />
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tabcontent.js' />"></script>


<c:import url="../common/includes/master_includes.jsp" />
<link href="../resources/common/css/style.css" rel="stylesheet">
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reception_edit_new.js' />"></script>
</head>
<body class="full-width" id="reception"
	ng-controller="receptionEditController as recCtrl">
	<input type="hidden" id="dateFormat" value="${dateFormat}" />
	<c:import url="../common/includes/transaction_edit.jsp" />

</body>
</html>