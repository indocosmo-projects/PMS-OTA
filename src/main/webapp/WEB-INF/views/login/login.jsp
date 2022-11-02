<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="formAction" value="login" />
<c:set var="formCommandName" value="userForm" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="Mosaddek">
<!--     <link rel="shortcut icon" href="img/favicon.png">
 -->
 
 <!-- Reach Hotel -->
 <!--  <link rel="shortcut icon"
	href="resources/common/images/favicon_reachH_logo.ico">   -->
 
 <!-- nikko -->	
 <!-- <link rel="shortcut icon"
	href="resources/common/images/favicon_niko_logo.ico">  -->
 <!-- Reach Residency  -->
 <!-- <link rel="shortcut icon"
	href="resources/common/images/favicon_reachR_logo.ico">  -->
 
 	<!-- olessia -->
<!--  <link rel="shortcut icon"
	href="resources/common/images/favicon_olessia_logo.ico"> -->


	

	
	

<title>PMS</title>

<!-- Bootstrap core CSS -->
<link href="<c:url value="/resources/common/css/bootstrap.min.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/common/css/bootstrap-reset.css"/>"
	rel="stylesheet">
<!--external css-->
<link
	href="<c:url value="/resources/common/css/font/font-awesome/css/font-awesome.css"/>"
	rel="stylesheet" />
<link
	href="<c:url value="/resources/common/css/jquery.easy-pie-chart.css"/>"
	rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet"
	href="<c:url value="/resources/common/css/owl.carousel.css"/>"
	type="text/css">

<!--right slidebar-->
<link href="<c:url value="/resources/common/css/slidebars.css"/>"
	rel="stylesheet">

<!-- Custom styles for this template -->

<link href="<c:url value="/resources/common/css/style.css"/>"
	rel="stylesheet">
<link href="<c:url value="/resources/common/css/style-responsive.css"/>"
	rel="stylesheet" />

<!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-body">

	<div class="container">
		<%-- <form:form action="${formAction}" autocomplete="on" method="post" commandName="${formCommandName}" class=" form-signin"> --%>
		<div class="form-signin">
			<c:import url="login_custom.jsp" />
		</div>
		<%-- </form:form> --%>
	</div>
	<!-- js placed at the end of the document so the pages load faster -->
	<script src="<c:url value="/resources/common/js/jquery.js"/>"></script>
	<script src="<c:url value="/resources/common/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/pms/js/angularctrl/login.js"/>"></script>

</body>
</html>






