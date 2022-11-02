<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Templates" scope="request" />
<c:set var="moduleName" value="Templates" scope="request" />
<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="formId" value="templates" scope="request" />
<c:set var="formName" value="templates" scope="request" />
<c:set var="customEditIncludeFile"
	value="../templates/templates_edit_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Templates" scope="request" />

<c:set var="cp_isCanView" scope="request"
	value="${(curPagePerObj.isCanView() && curPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="cp_isCanAdd" scope="request"
	value="${(curPagePerObj.isCanAdd() && curPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="cp_isCanEdit" scope="request"
	value="${(curPagePerObj.isCanEdit() && curPagePerObj.isIs_edit_applicable())?true:false}" />


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
	href="<c:url value='/resources/pms/css/templates.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/templates.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/textAngular-sanitize.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/textAngular.min.js'/>"></script>

</head>
<body ng-controller="templateCtrl" class="full-width" id="setup">
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>