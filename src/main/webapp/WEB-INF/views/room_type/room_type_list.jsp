<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleName" value="Room Type" scope="request" />
<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="formId" value="roomType" scope="request" />
<c:set var="formName" value="roomType" scope="request" />
<c:set var="customEditIncludeFile"
	value="../room_type/room_type_edit.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Room Type Page" scope="request" />

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
	/* 	window.cp_isCanView = $
	 {
	 cp_isCanView
	 };
	 window.cp_isCanAdd = $
	 {
	 cp_isCanAdd
	 };
	 window.cp_isCanEdit = $
	 {
	 cp_isCanEdit
	 };
	 window.cp_isCanDelete = $
	 {
	 cp_isCanDelete
	 }; */
	window.cp_isCanView = true;
	window.cp_isCanAdd = true;
	window.cp_isCanEdit = true;
	window.cp_isCanDelete = true;
</script>
<html ng-app="roomTypeTestApp">
<head>
<script type="text/javascript" language="javascript">
	function disableBackButton() {
		window.history.forward();
	}
	disableBackButton();
	window.onload = disableBackButton;
	window.onpageshow = function(evt) {
		if (evt.persisted)
			disableBackButton()
	};
	window.onunload = function() {
		void (0)
	};
</script>
<title>${moduleName}</title>
<c:import url="../common/includes/master_includes.jsp"></c:import>
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/roomTypeTest.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/roomType.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/bootstrap-fileupload.js'/>"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/bootstrap-fileupload.css' />" />

</head>
<body class="full-width" ng-controller="roomTypeController" id="setup"
	ng-cloak>
	<c:import url="../common/includes/transaction_edit.jsp" />
</body>

</html>