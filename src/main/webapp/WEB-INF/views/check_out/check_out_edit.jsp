<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="PMS" scope="request" />
<c:set var="moduleName" value="Check-Out" scope="request" />
<%-- <c:if test="${backUrl}">
	<c:set var="backUrl" value="/reception/receptionList" scope="request" />
</c:if> --%>
<c:choose>
	<c:when test="${prevPageUrl !=null}">
		<c:set var="backUrl" value="${prevPageUrl}" scope="request" />

	</c:when>
	<c:otherwise>
		<c:set var="backUrl" value="/reception/receptionList" scope="request" />

	</c:otherwise>
</c:choose>
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="formId" value="check_out" scope="request" />
<c:set var="formName" value="check_out" scope="request" />
<c:set var="formAction" value="../checkOut/save" scope="request" />
<c:set var="customEditIncludeFile"
	value="../check_out/check_out_edit_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Check-Out" scope="request" />
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
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_out.css' />" />
<link
	href="<c:url value="/resources/common/css/angular-bootstrap-toggle.min.css" />"
	rel="stylesheet" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular-bootstrap-toggle.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/checkout.js' />"></script>
</head>
<body class="full-width" ng-controller="checkOutController"
	id="reception" ng-cloak>
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>