<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Access Denied" scope="request" />
<c:set var="moduleName" value="Access Denied" scope="request" />
<c:set var="formId" value="Access Denied" scope="request" />
<c:set var="formName" value="Access Denied" scope="request" />
<c:set var="formAction" value="" scope="request" />
<html>
<head>
<title>${moduleTitle}</title>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />" />
<c:import url="../common/includes/master_includes.jsp" />
</head>
<body class="full-width">

	<section id="container" class="">
		<c:import url="../menu/topMenu.jsp" />

		<section id="main-content">
			<div class="container">
				<section class="wrapper">

					<div class="row">
						<div class="col-lg-12">
							<section class="panel">
								<div class="panel-body">
									<div class="col-md-12">
										<div class="task-progress module_head">
											<h1 align="center">Access Denied</h1>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<div class="task-progress trans_module_head">
												<!-- <h1> 404</h1>											
											 <img alt="" src="../resources/common/images/404.png"> -->
												<div class="col-md-12" align="center"
													style="min-height: 200px;">
													<img alt="" src="../resources/common/images/404.png">
												</div>
											</div>
										</div>
									</div>
								</div>
							</section>

						</div>
					</div>

				</section>
			</div>
		</section>

	</section>


</body>
</html>
