<jsp:directive.include file="../common/includes/page_directives.jsp" />
<!DOCTYPE html>
<html lang="en">
<!-- 
Change the following 3 variable values 
formId: ID of the JSP form
formName: Name of the JSP form
formAction: Corresponding method name in the controller class
 -->
<c:set var="moduleName" value="Tax" scope="request" />
<c:set var="formId" value="tax_list" />
<c:set var="formName" value="tax_list" />
<c:set var="formAction" value="getTax" />
<c:set var="insertFunction" value="addTax()" scope="request" />
<c:set var="searchPageURL" value="../../tax/tax_search.jsp"
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
window.cp_isCanView=${cp_isCanView}
window.cp_isCanAdd=${cp_isCanAdd}
window.cp_isCanEdit=${cp_isCanEdit}
</script>
<head>


<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.js' />"></script>

<c:import url="../common/includes/master_includes.jsp" />
<link href="../resources/common/css/style.css" rel="stylesheet">

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tax_list.js' />"></script>
<%-- <script type="text/javascript" src="<c:url value='/resources/common/js/pms_jqgrid_common.js' />"></script> --%>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tax_edit.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_grid_common.js' />"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />">

</head>

<body class="full-width" ng-app="masterApp"
	ng-controller="masterController" id="setup">
	<c:import url="../common/includes/master_list.jsp" />

</body>
<%-- <script type="text/java script" src="<c:url value='/resources/common/js/common-scripts.js' />"></script>    --%>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/hover-dropdown.js' />"></script>



<script>
        $(document).ready(function() {
            $('#search_form_down').click(function() {
                $("#search_fom_div").css("display", "block");
            })
            $('#search_form_close').click(function() {
                $("#search_fom_div").css("display", "none");
            })
        })
    </script>
</html>
