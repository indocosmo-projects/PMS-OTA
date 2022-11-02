<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Facility Provider" scope="request" />
<c:set var="moduleName" value="Facility Provider" scope="request" />
<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="formId" value="facilityProvider" scope="request" />
<c:set var="formName" value="facilityProvider" scope="request" />
<c:set var="formAction" value="getFacility Provider" scope="request" />
<c:set var="formCommandName" value="facilityProvider" scope="request" />
<c:set var="customEditIncludeFile"
	value="../facility_provider/facility_provider_edit_custom.jsp"
	scope="request" />
<c:set var="masterListHeaderName" value="Facility Provider"
	scope="request" />

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

<script>
window.cp_isCanView=${cp_isCanView};
window.cp_isCanAdd=${cp_isCanAdd};
window.cp_isCanEdit=${cp_isCanEdit};
window.cp_isCanDelete=${cp_isCanDelete};
var fTypeEnum={};
<c:forEach items="${facilityTypes}" var="facility">
fTypeEnum[${facility.code}]='${facility.name}';
</c:forEach>
window.facilityTypeEnums=fTypeEnum;
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
</script>
<title>${moduleTitle}</title>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/facility_provider.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/facility_provider.js' />"></script>
</head>
<body ng-controller="facilityCtrl" class="full-width" id="tools">
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>
</html>