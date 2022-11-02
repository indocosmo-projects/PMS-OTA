<jsp:directive.include file="../common/includes/page_directives.jsp" />
<!DOCTYPE html>
<html lang="en">
<c:set var="formId" value="corporate_list" scope="request" />
<c:set var="formName" value="corporate_list" scope="request" />
<c:set var="formAction" value="getCorporate" scope="request" />
<c:set var="moduleName" value="Corporate" scope="request" />
<c:set var="insertFunction" value="addCorporate()" scope="request" />
<c:set var="searchPageURL" value="../../corporate/corporate_search.jsp"
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
<!-- search page -->
<head>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<link href="../resources/common/css/style.css" rel="stylesheet">
<link href="<c:url value="/resources/pms/css/corporate.css" />"
	rel="stylesheet" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_grid_common.js' />"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />">
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/corporate_list.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/corporate_edit.js'/>"></script>
</head>
<body class="full-width" ng-app="masterApp"
	ng-controller="masterController" id="setup">
	<c:import url="../common/includes/master_list.jsp" />
</body>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/hover-dropdown.js' />"></script>
</html>


