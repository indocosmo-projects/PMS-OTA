<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Season Calendar" scope="request" />
<c:set var="moduleName" value="Season Calendar" scope="request" />
<c:set var="formId" value="seasoncalendar" scope="request" />
<c:set var="formName" value="seasoncalendar" scope="request" />
<c:set var="formAction" value="" scope="request" />
<c:set var="masterListHeaderName" value="Season" scope="request" />

<c:set var="backUrl" value="/season" scope="request" />

<html>
<head>
<title>${moduleTitle}</title>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />" />


<c:import url="../common/includes/master_includes.jsp" />

<style>
* {
	box-sizing: border-box;
}

ul {
	list-style-type: none;
}

body {
	font-family: Verdana, sans-serif;
}

.month {
	padding: 1px 1px;
	width: 100%;
	background: #1abc9c;
}

.month ul {
	margin: 0;
	padding: 0;
}

.month ul li {
	color: white;
	font-size: 15px;
	text-transform: uppercase;
	letter-spacing: 3px;
}

.month .prev {
	float: left;
	padding-top: 10px;
}

.month .next {
	float: right;
	padding-top: 10px;
}

.weekdays {
	margin: 0;
	padding: 10px 0;
	background-color: #ddd;
}

.weekdays li {
	display: inline-block;
	width: 13.6%;
	color: #666;
	text-align: center;
}

.days {
	padding: 10px 0;
	background: #eee;
	margin: 0;
	min-height: 170px;
}

.days li {
	list-style-type: none;
	display: inline-block;
	width: 13.6%;
	text-align: center;
	margin-bottom: 5px;
	font-size: 12px;
	color: #777;
}

.days li .active {
	padding: 5px;
	background: #1abc9c;
	color: white !important
}

.circle {
	background: black;
	border-radius: 100%;
	color: white;
	padding: 3px;
}

/* Add media queries for smaller screens */
@media screen and (max-width:720px) {
	.weekdays li, .days li {
		width: 13.1%;
	}
	.month {
		min-height: 300px;
	}
}

@media screen and (max-width: 420px) {
	.weekdays li, .days li {
		width: 12.5%;
	}
	.days li .active {
		padding: 2px;
	}
}

@media screen and (max-width: 290px) {
	.weekdays li, .days li {
		width: 12.2%;
	}
}
</style>



</head>
<body class="full-width">

	<section id="container" class="">
		<c:import url="../menu/topMenu.jsp" />

		<section id="main-content">
			<div class="container">
				<section class="wrapper">
					<input type="hidden" value="${dateFormat}" id="dateFormat">
					<input type="hidden" id="hotelDate" value="${hotelDate}">


					<div class="row">

						<div class="col-lg-12">

							<section class="panel">
								<header class="panel-heading module_caption">
									<h1>${moduleName}</h1>

									<!-- 	<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span> -->
									<a href="<c:url value='${backUrl}' />"
										class="btn btn-success pull-right backbutna"> <i
										class="fa fa-arrow-left backbtntop"> <small> <spring:message
													code="pms.btn.backtop" /></small></i>
									</a>
									<div class="row" style="margin: 10px 0 0 0px;">
										<c:forEach items="${seasonhdr}" var="shdr">

											<div class="col-md-1"
												style="background-color:${shdr.colorCode}; margin: 0 3px 0 3px;color:#ffffff;border-radius:30px;font-size:14px;">
												${shdr.code}</div>

										</c:forEach>
										<div class="col-md-3"
											style="margin: 0 3px 0 3px; border-radius: 30px; font-size: 14px; border-color: solid 1px black;">
											<span class="circle"> &nbsp; &nbsp;</span> Excluded season
											days
										</div>
									</div>



								</header>

								<div class="panel-body">
									<div class="dtls">
										<!--  o -->


										<div class="resv-card">


											<div class="row">




												<c:forEach items="${monthObj}" var="element">

													<!--  january -->
													<div class="col-md-3 resv-col-in">
														<div class="month">
															<ul>
																<!-- <li class="prev">?</li>
                                  <li class="next">?</li> -->
																<li style="text-align: center"><c:choose>
																		<c:when test="${element.key == 0}">January</c:when>
																		<c:when test="${element.key == 1}">February</c:when>
																		<c:when test="${element.key == 2}">March</c:when>
																		<c:when test="${element.key == 3}">April</c:when>
																		<c:when test="${element.key == 4}">May</c:when>
																		<c:when test="${element.key == 5}">June</c:when>
																		<c:when test="${element.key == 6}">July</c:when>
																		<c:when test="${element.key == 7}">august</c:when>
																		<c:when test="${element.key == 8}">September</c:when>
																		<c:when test="${element.key == 9}">October</c:when>
																		<c:when test="${element.key == 10}">November</c:when>
																		<c:when test="${element.key == 11}">December</c:when>

																		<c:otherwise>undefined</c:otherwise>
																	</c:choose> <br> <!--  <span style="font-size:18px">2016</span> -->
																</li>
															</ul>
														</div>
														<!-- <ul class="weekdays">
  <li>Mo</li>
  <li>Tu</li>
  <li>We</li>
  <li>Th</li>
  <li>Fr</li>
  <li>Sa</li>
  <li>Su</li>
</ul>  -->

														<ul class="days">
															<c:forEach items="${element.value}" var="days">

																<li><c:choose>

																		<c:when test="${ days.value  == 'black'}">

																			<span class="circle"> ${days.key} </span>


																		</c:when>
																		<c:otherwise>
																			<span style=" color:${days.value}">${days.key}</span>
																		</c:otherwise>
																	</c:choose></li>

															</c:forEach>
														</ul>




													</div>
													<!--  january close -->


												</c:forEach>






												<%-- <c:forEach items="${monthObj}" var="element"> 
      <div class="col-md-4 resv-col-in">
          Month: ${element.key}  <br>
          
          <c:forEach items="${element.value}" var="days"> 
          
             ${days.value} ${days.key}
           
          </c:forEach>
          
     </div>
</c:forEach> --%>



												<div class="col-md-4 resv-col-in"></div>
											</div>


										</div>



										<!--  <div class="list-group col-md-12">
    
hgfh
   
</div> -->





										<!--  c  -->
									</div>

								</div>
							</section>
						</div>
					</div>


					<!-- custom page is included here -->
				</section>
			</div>
		</section>

	</section>


</body>
</html>
