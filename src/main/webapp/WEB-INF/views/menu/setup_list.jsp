<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Setup" scope="request" />
<c:set var="moduleName" value="Setup" scope="request" />
<c:set var="formId" value="discount" scope="request" />
<c:set var="formName" value="discount" scope="request" />
<c:set var="formAction" value="../discount/save" scope="request" />
<c:set var="formCommandName" value="discount" scope="request" />
<c:set var="customEditIncludeFile" value="../menu/setup_list_edit.jsp"
	scope="request" />
<c:set var="masterListHeaderName" value="Setup" scope="request" />
<%--  <c:set var="insertFunction" value="addDiscount()" scope="request"/>  --%>
<html>
<head>
<title>${moduleTitle}</title>
<link rel="shortcut icon"
	href="/resources/common/images/logos_${companyN}/favicon_niko_logo.ico">
<%-- <link href="<c:url value="/resources/pms/css/dicount.css" />" rel="stylesheet" /> --%>
<c:import url="../common/includes/master_includes.jsp" />
<%-- <script type="text/javascript" src="<c:url value='/resources/pms/js/discount_edit.js' />"></script> --%>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
</head>
<body class="full-width">
	<form:form>
		<c:import url="../common/includes/master_setup_list.jsp" />
	</form:form>