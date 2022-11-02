<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleName" value="House Keeping" scope="request" />
<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="formId" value="hk" scope="request" />
<c:set var="formName" value="houseKeeping" scope="request" />
<c:set var="customEditIncludeFile" value="../house_keeping/hkEdit.jsp"
	scope="request" />
<c:set var="masterListHeaderName" value="House Keeping Page"
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
</script>
<html ng-app="pmsApp">
<head>
<script type="text/javascript" language="javascript">
		function disableBackButton(){
			window.history.forward();
		}
		disableBackButton();
		window.onload=disableBackButton;
		window.onpageshow = function(evt) { if (evt.persisted) disableBackButton()};
		window.onunload= function() { void (0) };
		 
		
		</script>
<title>${moduleName}</title>
<c:import url="../common/includes/master_includes.jsp"></c:import>
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/hk.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/hk.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reservation_tools.js'/>"></script>  <!--  added new line-->
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/style.css' />" />

</head>
<body class="full-width" ng-controller="hkController as hkCtrl" id="hk">
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>

</html>