<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="ERROR" scope="request" />
<c:set var="moduleName" value="ERROR" scope="request" />
<c:set var="formAction" value="" scope="request" />
<head>
<title>${moduleTitle}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/components/dashboard/css/theme-default.css' />" />
</head>
<body class="full-width">
<div class="error-container">
             <div class="error-code">500</div>
            <div class="error-text">Internal server error</div>
            <div class="error-subtext">The server encountered an internal error or misconfiguration and was unable to complete your request.</div>  
            <div class="error-actions">                                
                <div class="row">
                    <div class="col-md-6">
                        <button class="btn btn-info btn-block btn-lg" onClick="document.location.href = '../';">Back to dashboard</button>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-primary btn-block btn-lg" onClick="history.back();">Previous page</button>
                    </div>
                </div>                                
            </div>
        </div>    
</body>
</html>
