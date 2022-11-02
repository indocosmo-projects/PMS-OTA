<jsp:directive.include file="../common/includes/page_directives.jsp" />

<!DOCTYPE html>
<html lang="en">
<c:set var="formId" value="corporate_rate_list" scope="request" />
<c:set var="formName" value="corporate_rate_list" scope="request" />
<c:set var="formAction" value="getCorporateRate" scope="request" />
<c:set var="moduleName" value="Corporate TA Rate" scope="request" />
<c:set var="insertFunction" value="addCorporateRate()" scope="request" />
<c:set var="searchPageURL"
	value="../../corporate_ta_rates/corporate_ta_rates_search.jsp"
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
	window.cp_isCanView = $
	{
		cp_isCanView
	}
	window.cp_isCanAdd = $
	{
		cp_isCanAdd
	}
	window.cp_isCanEdit = $
	{
		cp_isCanEdit
	}
</script>
<head>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<link href="../resources/common/css/style.css" rel="stylesheet">
<link href="../resources/pms/css/corporate_ta_rates.css"
	rel="stylesheet">
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_grid_common.js' />"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />">
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/corporate_ta_rates_list.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/corporate_ta_rates_edit.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
</head>

<body class="full-width" ng-app="masterApp"
	ng-controller="masterController" id="setup">
	<c:import url="../common/includes/master_list.jsp" />
	<script type="text/javascript"
		src="<c:url value='/resources/common/js/hover-dropdown.js' />"></script>
</body>

</html>