<jsp:directive.include file="../../common/includes/page_directives.jsp" />
<jsp:directive.include file="../common/script.jsp" />
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
 

<title>PMS</title>

<!-- Bootstrap core CSS -->

<!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body class="login-body">

	<div >
			<%-- <form:form action="${formAction}" autocomplete="on" method="post" commandName="${formCommandName}" class=" form-signin"> --%>
			<div >
				<c:import url="online_travelagent_custom_login.jsp" />
			</div>
			<%-- </form:form> --%>
		</div>

	
	
	<script type="text/javascript"
			src="<c:url value='/resources/pms/js/online_travel_agent/online_travel_agent.js' />"></script>
	
	
	
	
</body>
</html>






