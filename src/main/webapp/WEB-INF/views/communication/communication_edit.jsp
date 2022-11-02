<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Communication" scope="request" />
<c:set var="moduleName" value="Communication" scope="request" />
<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="backUrl" value="/communication/list" scope="request" />
<c:set var="formId" value="communication_edit" scope="request" />
<c:set var="formName" value="communication_edit" scope="request" />
<c:set var="customEditIncludeFile"
	value="../communication/communication_edit_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Communication" scope="request" />

<c:set var="cp_isCanView" scope="request"
	value="${(curPagePerObj.isCanView() && curPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="cp_isCanAdd" scope="request"
	value="${(curPagePerObj.isCanAdd() && curPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="cp_isCanEdit" scope="request"
	value="${(curPagePerObj.isCanEdit() && curPagePerObj.isIs_edit_applicable())?true:false}" />
<script>
window.cp_isCanView=${cp_isCanView};
window.cp_isCanAdd=${cp_isCanAdd};
window.cp_isCanEdit=${cp_isCanEdit};
</script>



<html ng-app="pmsApp">
<head>
<script type="text/javascript" language="javascript">
function DisableBackButton() {
window.history.forward()
}
DisableBackButton();
window.onload = DisableBackButton;
window.onpageshow = function(evt) { if (evt.persisted) DisableBackButton() }
window.onunload = function() { void (0) }
var purposeEnum={};
<c:forEach items="${CommunicationPurposes}" var="purpose">
purposeEnum[${purpose.code}]='${purpose.name}';
</c:forEach>
window.comtnPurposeEnum=purposeEnum;
var purposeEnumCommunication=[];
<c:forEach items="${CommunicationPurposes}" var="purposeEmail">
purposeEnumCommunication[${purposeEmail.code}]='${purposeEmail.name}';
</c:forEach>
window.comtnPurposeEnumEmail=purposeEnumCommunication;
</script>
<title>${moduleTitle}</title>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in.css' />" />
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/user.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/communication.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/communication_edit.js' />"></script>

<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/textAngular-sanitize.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/textAngular.min.js'/>"></script>





</head>
<body ng-controller="commtnEditCtrl as commtnEdit" class="full-width">
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>