
<jsp:directive.include file="../common/includes/page_directives.jsp" />

<c:set var="moduleTitle" value="SystemSettings" scope="request" />
<c:set var="formId" value="systemSettings" scope="request" />
<c:set var="formName" value="SystemSettings" scope="request" />
<c:set var="formAction" value="../systemSettings/save" scope="request" />
<c:set var="formCommandName" value="settings" scope="request" />
<c:set var="moduleName" value="System Settings" scope="request" />
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


<c:set var="rp_isCanView" scope="request"
	value="${(ratePagePerObj.isCanView() && ratePagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="rp_isCanAdd" scope="request"
	value="${(ratePagePerObj.isCanAdd() && ratePagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="rp_isCanEdit" scope="request"
	value="${(ratePagePerObj.isCanEdit() && ratePagePerObj.isIs_edit_applicable())?true:false}" />
<c:set var="rp_isCanDelete" scope="request"
	value="${(ratePagePerObj.isCanDelete() && ratePagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="rp_isCanExecute" scope="request"
	value="${(ratePagePerObj.isCanExecute() && ratePagePerObj.isIs_execute_applicable())?true:false}" />
<c:set var="rp_isCanExecute" scope="request"
	value="${(ratePagePerObj.isCanExport() && ratePagePerObj.isIs_export_applicable())?true:false}" />
<input type="hidden" value="${hotelDate}" id="hotelDate" />

<html>
<head>
<title>System Settings</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">

<c:import url="../common/includes/master_includes.jsp" />
<script type="text/javascript">
	var currency = ${currencyListJson};// getting currency details as JSON
	currency = JSON.parse(JSON.stringify(currency));
</script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/systemsettings_edit.js'></c:url>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/systemSettings.css' />">




<link rel="stylesheet"
	href="../resources/common/css/jquery.scrolltop.css">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->





</head>

<body class="full-width" ng-app="systemsettings"
	ng-controller="systemsettingsController" id="setup">

	<!--   <body class="full-width" oncontextmenu="return false;"> -->

	<c:import url="../menu/topMenu.jsp" />
	<form:form id="${formId}" name="${formName}"
		commandName="${formCommandName}" action="${formAction}" method="POST">

		<form:hidden path="id" />
		<input type="hidden" id="dateFormat" value="${dateFormat}">
		<input id="hdnBaseCurrencyId" type="hidden"
			value="${settings.baseCurrencyId}">

		<section id="container" class="">
			<!--main content start-->
			<section id="main-content">
				<div class="">
					<section class="wrapper">

						<!-- page start-->
						<div class="row">
							<div class="col-lg-12">
								<section class="panel">
									<header class="panel-heading module_caption">
										<h1>System Settings</h1>

										<%-- <div class="backmainbtn">
										 <a href="<c:url value='/dashboard' />"  class="ios-back-button" data-text="<spring:message code="pms.btn.backtop" />"></a>
										 
										</div> --%>

										<!-- <span class="tools pull-right"> <a
											class="fa fa-chevron-down" href="javascript:;"></a>
										</span> -->

										<!-- <span class="glyphicon glyphicon-arrow-left pull-right" aria-hidden="true"></span> -->

										<!-- <button type="button" class="btn btn-success" >Back</button> -->




									</header>

									<div id="divSystemSettingsBody">


										<div aria-hidden="true" aria-labelledby="myModalLabel"
											role="dialog" tabindex="-1" id="passwordModal"
											class="modal fade">
											<div class="modal-dialog modal-md">
												<div class="modal-content">
													<div class="modal-header">
														<button aria-hidden="true" data-dismiss="modal"
															class="close" type="button">x</button>
														<h4 class="modal-title">Enter Password</h4>
													</div>

													<div class="modal-body" style="overflow-y: auto;">
														<!-- modal data comes here -->
														<div class="form-horizontal tasi-form">
															<div class="form-group">
																<label class="control-label col-md-2"> User </label>
																<div class="col-md-6 col-xs-11 shiftal">
																	<input type="hidden" value="${loginId}" id="loginId">
																	<c:out value="${loginName}" />
																</div>

															</div>
															<div class="form-group">
																<label class="control-label col-md-2"> Password
																	<span id="redMargin" class="red">*</span>
																</label>
																<div class="col-md-6 col-xs-11" id="msg"
																	style="color: red; display: none">Enter password</div>
																<div class="col-md-6 col-xs-11">
																	<input type="password" id="passWrd" maxlength="100"
																		class="form-control  form-control-inline input-medium"
																		ng-model="passwrd" autocomplete="off" />
																</div>

															</div>
															<div class="modal-footer">
																<button id="cancelProvider" type="button"
																	class="btn btn-default rbtnClose"
																	ng-click="cancelPopUpOpen();">
																	<spring:message code="pms.btn.cancel" />
																</button>
																<button class="btn btn-success" type="button"
																	ng-click="addRaw()">
																	<spring:message code="pms.btn.save" />
																</button>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<!-- SYSTEM -->
										<div class="informatition_form_sub_title" ng-click="toggle1()">
											<span class="title"><spring:message
													code="systemSetting.label.system" /></span>
										</div>
										<div class="panel-body" ng-show="show1">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"> <spring:message
															code="systemSetting.label.productionMode" />
													</label>
													<div class="col-md-3">
														<form:checkbox path="productionMode" class="checkbox"
															id="productionMode" />
														<label for="productionMode"></label>
													</div>
												</div>
											</div>
										</div>

										<!-- COMPANY -->
										<div class="informatition_form_sub_title" ng-click="toggle2()">
											<span class="title"><spring:message
													code="systemSetting.label.company"></spring:message></span>
										</div>
										<div class="panel-body" ng-show="show2">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.companyName" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtCompanyName" type="text" maxlength="100"
															class="form-control form-control-inline input-medium"
															value="${company.companyName}" />
													</div>
													<!-- <div class="col-md-1 col-xs-11 tick_green">
														<span id="txtSmtpServer_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtSmtpServer_warning"
															class="switch-right warning_red"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div> -->
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.buildingName" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtBuildingName" type="text" maxlength="100"
															class="form-control form-control-inline input-medium"
															value="${company.buildingName}" />
													</div>
													<!-- <div class="col-md-1 col-xs-11 tick_green">
														<span id="txtSmtpPort_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtSmtpPort_warning"
															class="switch-right warning_red"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div> -->
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.street" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtstreet" type="text" maxlength="100"
															class="form-control form-control-inline input-medium"
															value="${company.streetName}" />
													</div>
													<!-- <div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMailId_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMailId_warning"
															class="switch-right warning_red"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div> -->
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.city" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtcity" type="text" maxlength="100"
															class="form-control form-control-inline input-medium"
															value="${company.cityName}" />
													</div>
													<!-- <div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMailPassword_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMailPassword_warning"
															class="switch-right warning_red"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div> -->
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.State" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtState" type="text" maxlength="100"
															class="form-control form-control-inline input-medium"
															value="${company.stateName}" />
													</div>
													<!-- <div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMailPassword_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMailPassword_warning"
															class="switch-right warning_red"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div> -->
												</div>




												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.country" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtcountry" type="text" maxlength="100"
															class="form-control form-control-inline input-medium"
															value="${company.countryName}" />
													</div>
													<!-- 	<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMailPassword_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMailPassword_warning"
															class="switch-right warning_red"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div> -->
												</div>

												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.gstNo" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtgstNo" type="text" maxlength="100"
															class="form-control form-control-inline input-medium"
															value="${company.gstNo}" />
													</div>
													<!-- <div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMailPassword_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMailPassword_warning"
															class="switch-right warning_red"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div> -->
												</div>
												
												<div class="form-group" >
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.email" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtemail" type="text" maxlength="30"
															class="form-control form-control-inline input-medium"
															value="${company.email}" />
													</div>
												</div>
												
												
												
												<div class="form-group" >
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.companyurl" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txturl" type="text" maxlength="30"
															class="form-control form-control-inline input-medium"
															value="${company.url}" />
													</div>
												</div>
												
												<div class="form-group" >
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.phone" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtphone" type="text" maxlength="30"
															class="form-control form-control-inline input-medium"
															value="${company.phone}" />
													</div>
												</div>
												
												<div class="form-group" ng-hide="true">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.imageFolder" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtlogoFolder" type="text" maxlength="30"
															class="form-control form-control-inline input-medium"
															value="${company.logoFolder}" ng-disabled="true"/>
													</div>
												</div>
												
												
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.longStay" /></label>
													<div class="col-md-3 col-xs-11">
														<input id="txtlongStay" type="Number" maxlength="200"
															class="form-control form-control-inline input-medium"
															value="${company.longStay}" />
													</div>
												</div>

										<div class="resv_confirm_title">
											<span class="title"><spring:message
													code="systemSetting.label.resvConfirmRpt"></spring:message></span>
										</div>
										
										<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.resvConfirmRptHead" /></label>
													<div class="col-md-10 col-xs-11">
														<input id="txtresvConfirmHead" type="text" maxlength="200"
															class="form-control form-control-inline input-medium"
															value="${company.resvConfirmHead}" />
													</div>
												</div>
												
										<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.resvConfirmRptText1" /></label>
													<div class="col-md-10 col-xs-11">
														<input id="txtresvConfirmText1" type="text" maxlength="200"
															class="form-control form-control-inline input-medium"
															value="${company.resvConfirmText1}" />
													</div>
												</div>
								<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.resvConfirmRptText2" /></label>
													<div class="col-md-10 col-xs-11">
														<input id="txtresvConfirmText2" type="text" maxlength="200"
															class="form-control form-control-inline input-medium"
															value="${company.resvConfirmText2}" />
													</div>
												</div>	
								<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.resvConfirmRptText3" /></label>
													<div class="col-md-10 col-xs-11">
														<input id="txtresvConfirmText3" type="text" maxlength="200"
															class="form-control form-control-inline input-medium"
															value="${company.resvConfirmText3}" />
													</div>
												</div>	
												
								<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.resvConfirmRptText4" /></label>
													<div class="col-md-10 col-xs-11">
														<input id="txtresvConfirmText4" type="text" maxlength="200"
															class="form-control form-control-inline input-medium"
															value="${company.resvConfirmText4}" />
													</div>
												</div>	
								<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.resvConfirmRptText5" /></label>
													<div class="col-md-10 col-xs-11">
														<input id="txtresvConfirmText5" type="text" maxlength="200"
															class="form-control form-control-inline input-medium"
															value="${company.resvConfirmText5}" />
													</div>
												</div>										

											</div>
											<input type="hidden" id="companyAsJson" name="companyAsJson" />
										</div>


										<!-- BUSINESS -->
										<div class="informatition_form_sub_title" ng-click="toggle3()">
											<span class="title"><spring:message
													code="systemSetting.label.business" /></span>


										</div>
										<div class="panel-body" ng-show="show3">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"> <spring:message
															code="systemSetting.label.businessYearStartIn" /> <span
														class="red">*</span>
													</label>
													<div class="col-md-3 col-xs-11">
														<form:select id="ddlFinYearStartIn"
															class="form-control input-sm m-bot15 "
															path="finYearStartIn">
															<form:options items="${months}"></form:options>
														</form:select>
													</div>
													<div class="col-md-3 col-xs-11 tick_green">
														<span id="ddlFinYearStartIn_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="ddlFinYearStartIn_warning"
															class="switch-right warning_red" style="display: none;"><i
															class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"> <spring:message
															code="systemSetting.label.weekStartsOn" /> <span
														class="red">* </span>
													</label>
													<div class="col-md-3 col-xs-11">
														<form:select id="ddlWeekStartsOn"
															class="form-control input-sm m-bot15 validator"
															path="weekStartOn">
															<form:options items="${weeks}"></form:options>
														</form:select>
													</div>
													<div class="col-md-3 col-xs-11 tick_green">
														<span id="ddlWeekStartsOn_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="ddlWeekStartsOn_warning"
															class="switch-right warning_red" style="display: none;"><i
															class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"> <spring:message
															code="systemSetting.label.baseCurrency" /> <span
														class="red">* </span>
													</label>
													<div class="col-md-3 col-xs-11">
														<form:select id="cmbCurrency"
															class="form-control input-sm m-bot15 validator"
															path="baseCurrencyId">
														</form:select>
													</div>

													<div class="col-md-3 col-xs-11 tick_green">
														<strong id="divCurrencyDescription"></strong>
													</div>

													<div class="col-md-1 col-xs-11 tick_green">
														<span id="cmbCurrency_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="cmbCurrency_warning"
															class="switch-right warning_red" style="display: none;"><i
															class="fa fa-warning"></i></span>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-md-2"> <spring:message
															code="systemSetting.label.decimalPlaces" />
													</label>
													<div class="col-md-1 col-xs-11">
														<input type="text" id="txtDecimalPlaces"
															class="form-control form-control-inline input-medium"
															disabled="disabled" />
													</div>
												</div>
											</div>
										</div>


										<!-- TARIFF -->
										<div class="informatition_form_sub_title" ng-click="toggle4()">
											<span class="title"><spring:message
													code="systemSetting.label.tariff" /> </span>
										</div>
										<div class="panel-body" ng-show="show4">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"> <spring:message
															code="systemSetting.label.weeklySpecialDays" /> <form:hidden
															id="hdnWeeklySpecialDays" path="weeklySpecialDays" />
													</label>
													<div class="col-md-6 col-xs-12">
														<div class="directory-list weekly_special_day">
															<ul>
																<li><form:checkbox id="chkSun" data-day="SUNDAY"
																		path="sun" cssClass="weekSDays" /> <label
																	for="chkSun"></label><strong><spring:message
																			code="systemSetting.label.sun" /></strong></li>
																<li><strong><spring:message
																			code="systemSetting.label.mon" /></strong> <form:checkbox
																		id="chkMon" data-day="MONDAY" path="mon"
																		cssClass="weekSDays" /> <label for="chkMon"></label>
																</li>
																<li><strong><spring:message
																			code="systemSetting.label.tue" /></strong> <form:checkbox
																		id="chkTue" data-day="TUESDAY" path="tue"
																		cssClass="weekSDays" /> <label for="chkTue"></label>
																</li>
																<li><strong><spring:message
																			code="systemSetting.label.wed" /></strong> <form:checkbox
																		id="chkWed" data-day="WEDNSDAY" path="wed"
																		cssClass="weekSDays" /> <label for="chkWed"></label>
																</li>
																<li><strong><spring:message
																			code="systemSetting.label.thu" /></strong> <form:checkbox
																		id="chkThu" data-day="THURSDAY" path="thu"
																		cssClass="weekSDays" /> <label for="chkThu"></label>
																</li>
																<li><strong><spring:message
																			code="systemSetting.label.fri" /></strong> <form:checkbox
																		id="chkFri" data-day="FRIDAY" path="fri"
																		cssClass="weekSDays" /> <label for="chkFri"></label>
																</li>
																<li><strong><spring:message
																			code="systemSetting.label.sat" /></strong> <form:checkbox
																		id="chkSat" data-day="SATURDAY" path="sat"
																		cssClass="weekSDays" /> <label for="chkSat"></label>
																</li>
															</ul>
														</div>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"> <spring:message
															code="systemSetting.label.tariffIncludeTaxes" />
													</label>
													<div class="col-md-3">
														<form:checkbox path="tariffIncludeTax" id="isTaxInclude" />
														<label for="isTaxInclude"></label>
													</div>
												</div>
											</div>
										</div>


										<!-- FORMATS -->
										<div class="informatition_form_sub_title" ng-click="toggle5()">
											<span class="title"><spring:message
													code="systemSetting.label.formats" /></span>
										</div>
										<div class="panel-body" ng-show="show5">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"> Date Display
														Format <span class="red">* </span>
													</label>
													<div class="col-md-3 col-xs-11">
														<form:hidden path="dateFormat" />
														<input id="txtDateFormat" type="text"
															class="form-control validator" disabled="disabled"
															value="${settings.dateFormat}" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span class="switch-right"><i class=" fa fa-check"></i></span>
													</div>
													<div class="col-md-3 col-xs-11 tick_green">
														<strong>(E.g 24-08-2015)</strong>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2">Time Display
														Format <span class="red">* </span>
													</label>
													<div class="col-md-3 col-xs-11">
														<form:hidden path="timeFormat" />
														<input id="txtTimeFormat" type="text"
															class="form-control validator" disabled="disabled"
															value="${settings.timeFormat}" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span class="switch-right"><i class=" fa fa-check"></i></span>
													</div>
													<div class="col-md-3 col-xs-11 tick_green">
														<strong>(E.g 02:00 AM)</strong>
													</div>
												</div>
											</div>
										</div>



										<!-- Service Charge -->
										<div class="informatition_form_sub_title" ng-click="toggle6()">
											<span class="title"> SERVICE CHARGE</span>
										</div>
										<div class="panel-body" ng-show="show6">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"> Service
														Charge Is Applicable </label>
													<div class="col-md-1 col-xs-11">


														<form:checkbox path="serviceCharge" class="checkbox"
															id="serviceCharge" />
														<label style="margin-top: 6px;" for="serviceCharge"></label>


													</div>

												</div>



											</div>
										</div>

										<!-- Service Charge close -->


										<!-- Service Tax -->
										<%-- 	<div class="informatition_form_sub_title">
											<span class="title"> SERVICE TAX</span>
										</div>
                                        <div class="panel-body">
											<div class="form-horizontal tasi-form">
											
											 
											
											
											
											
											
												<div class="form-group">
													<label class="control-label col-md-2"> Service Tax Is Applicable </label>
													<div class="col-md-1 col-xs-11">
													
													
													   <form:checkbox path="serviceTaxIncl" class="checkbox"
															id="serviceTaxIncl" />
														<label for="serviceTaxIncl"></label>
													
														 
													</div>
													 
													
													 
												</div> 
												
												
												
										    </div>											
									    </div>

 --%>
										<!-- Service Tax close -->





										<!-- TAXATION -->
										<div class="informatition_form_sub_title" ng-click="toggle7()">
											<span class="title"><spring:message
													code="systemSetting.label.taxation" /></span>
										</div>
										<div class="panel-body" ng-show="show7">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"> <strong><spring:message
																code="systemSetting.label.intName" /></strong>
													</label>
													<div class="col-md-1 col-xs-11">
														<strong><spring:message
																code="systemSetting.label.sel" /></strong>
													</div>
													<div class="col-md-3 col-xs-11">
														<strong><spring:message
																code="systemSetting.label.userDefinedName" /></strong>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.tax1" /></label>
													<div class="col-md-1 col-xs-11">
														<input type="checkbox"
															${settings.tax1Name != ''? 'checked' : ''}
															onchange="enableOrDisableTaxText(this, 'txtTax1Name', '${settings.tax1Name}');"
															id="checkTax1" /> <label for="checkTax1"></label>
													</div>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtTax1Name"
															cssClass="form-control form-control-inline input-medium"
															disabled="${settings.tax1Name == '' ? true : false}"
															maxlength="15" path="tax1Name" />
													</div>
												</div>

												<%-- <div class="form-group">
													<label class="control-label col-md-2"> Service charge is applicable</label>
													<div class="col-md-1 col-xs-11">
														<input type="checkbox"
															${settings.tax1Name != ''? 'checked' : ''}
															onchange="enableOrDisableTaxText(this, 'txtTax1Name', '${settings.tax1Name}');"
															id="checkTax1" /> <label for="checkTax1"></label>
													</div>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtTax1Name"
															cssClass="form-control form-control-inline input-medium"
															disabled="${settings.tax1Name == '' ? true : false}"
															maxlength="15" path="tax1Name" />
													</div>
												</div> --%>


												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.tax2" /></label>
													<div class="col-md-1 col-xs-11">
														<input type="checkbox"
															${settings.tax2Name != ''? 'checked' : ''}
															onchange="enableOrDisableTaxText(this, 'txtTax2Name', '${settings.tax2Name}');"
															id="checkTax2" /> <label for="checkTax2"></label>
													</div>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtTax2Name"
															cssClass="form-control form-control-inline input-medium"
															disabled="${settings.tax2Name == '' ? true : false}"
															maxlength="15" path="tax2Name" />
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.tax3" /></label>
													<div class="col-md-1 col-xs-11">
														<input type="checkbox"
															${settings.tax3Name != ''? 'checked' : ''}
															onchange="enableOrDisableTaxText(this, 'txtTax3Name', '${settings.tax3Name}');"
															id="checkTax3" /> <label for="checkTax3"></label>
													</div>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtTax3Name"
															cssClass="form-control form-control-inline input-medium"
															disabled="${settings.tax3Name == '' ? true : false}"
															maxlength="15" path="tax3Name" />
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.tax4" /></label>
													<div class="col-md-1 col-xs-11">
														<input type="checkbox"
															${settings.tax4Name != ''? 'checked' : ''}
															onchange="enableOrDisableTaxText(this, 'txtTax4Name', '${settings.tax4Name}');"
															id="checkTax4" /> <label for="checkTax4"></label>
													</div>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtTax4Name"
															cssClass="form-control form-control-inline input-medium"
															disabled="${settings.tax4Name == '' ? true : false}"
															maxlength="15" path="tax4Name" />
													</div>
												</div>

											</div>
										</div>


										<!-- ROUNDING -->
										<div class="informatition_form_sub_title" ng-click="toggle8()">
											<span class="title"><spring:message
													code="systemSetting.label.rounding" /></span>
										</div>
										<div class="panel-body" ng-show="show8">
											<div class="form-horizontal">
												<label class="control-label col-md-2"> <spring:message
														code="systemSetting.label.finalBillRounding" /> <span
													class="red">* </span>
												</label>
												<div class="form-group col-lg-3">
													<form:select class="form-control input-sm m-bot15"
														path="billRounding">
														<form:options items="${billRoundingList}"></form:options>
													</form:select>
												</div>
											</div>
										</div>


										<!-- RESERVATION -->
										<div class="informatition_form_sub_title" ng-click="toggle9()">
											<span class="title"><spring:message
													code="systemSetting.label.reservation" /></span>
										</div>
										<div class="panel-body" ng-show="show9">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.confirmBookingsBefore" /> <span
														class="red">*</span> </label>
													<div class="col-md-1 col-xs-11">
														<form:input
															class="pms_text_numbers form-control form-control-inline input-medium validator"
															id="txtConfBefore" path="confirmBefore" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtConfBefore_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtConfBefore_warning"
															class="switch-right warning_red" style="display: none;"><i
															class="fa fa-warning"></i></span>
													</div>
													<div class="col-md-3 col-xs-11 tick_green"
														id="dialogMargin">
														<strong><spring:message
																code="systemSetting.label.daysBeforeTheArrivalDate" /></strong>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.maxRoomsPerBooking" /> <span
														class="red">* </span> </label>
													<div class="col-md-1 col-xs-11">
														<form:input
															class="pms_text_numbers form-control form-control-inline input-medium validator"
															id="txtMaxRmPerBkng" path="maxRoomsPerBooking"
															maxlength="3" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMaxRmPerBkng_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMaxRmPerBkng_warning"
															class="switch-right warning_red" style="display: none;"><i
															class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.maxNightsPerBooking" /><span
														class="red">* </span> </label>
													<div class="col-md-1 col-xs-11">
														<form:input
															class="pms_text_numbers form-control form-control-inline input-medium validator"
															id="txtMaxNghtsPerBkng" path="maxNightsPerBooking"
															maxlength="3" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMaxNghtsPerBkng_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMaxNghtsPerBkng_warning"
															class="switch-right warning_red" style="display: none;"><i
															class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.sendNotificationOnReservation" /><span
														class="red">* </span> </label>
													<div class="col-md-6 col-xs-12">
														<div
															class="directory-list weekly_special_day notification">

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="mailNotifyReservation"
																		cssClass="systemsettings_sms_checkbox"
																		id="mailReserve" />
																	<label for="mailReserve"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong><spring:message code="pms.label.email" /></strong>
																</div>
																<%-- <div class="col-md-4">
																	<form:checkbox path="mailNotifyReservation"
																		cssClass="systemsettings_sms_checkbox"
																		id="mailReserve" />
																	<label for="mailReserve"></label>
																</div> --%>
															</div>
															<div class="col-md-3 col-xs-2">
																<div class="col-md-4 ">
																	<form:checkbox path="smsNotifyReservation"
																		cssClass="systemsettings_sms_checkbox" id="smsReserve" />
																	<label for="smsReserve"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong> <spring:message code="pms.label.sms" /></strong>
																</div>
																<%-- <div class="col-md-4 ">
																	<form:checkbox path="smsNotifyReservation"
																		cssClass="systemsettings_sms_checkbox" id="smsReserve" />
																	<label for="smsReserve"></label>
																</div> --%>
															</div>
														</div>
													</div>
												</div>


												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.sendNotificationOnCutoffDate" /><span
														class="red">* </span> </label>
													<div class="col-md-6 col-xs-12">
														<div
															class="directory-list weekly_special_day notification">
															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="mailNotifyCutoffDate"
																		cssClass="systemsettings_sms_checkbox"
																		id="mailCutoffDate" />
																	<label for="mailCutoffDate"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong><spring:message code="pms.label.email" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="mailNotifyCutoffDate"
																	cssClass="systemsettings_sms_checkbox" id="mailCutoffDate" />
																<label for="mailCutoffDate"></label>
															</div> --%>
															</div>

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="smsNotifyCutoffDate"
																		cssClass="systemsettings_sms_checkbox"
																		id="smsCutoffDate" />
																	<label for="smsCutoffDate"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong> <spring:message code="pms.label.sms" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="smsNotifyCutoffDate"
																	cssClass="systemsettings_sms_checkbox" id="smsCutoffDate" />
																<label for="smsCutoffDate"></label>
															</div> --%>
															</div>

														</div>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.sendNotificationOnConfirmation" /><span
														class="red">* </span> </label>
													<div class="col-md-6 col-xs-12">
														<div
															class="directory-list weekly_special_day notification">
															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="mailNotifyConfirmation"
																		cssClass="systemsettings_sms_checkbox"
																		id="mailConfirm" />
																	<label for="mailConfirm"></label>
																</div>

																<div class="col-md-5 width-auto">
																	<strong><spring:message code="pms.label.email" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="mailNotifyConfirmation"
																	cssClass="systemsettings_sms_checkbox" id="mailConfirm" />
																<label for="mailConfirm"></label>
															</div> --%>
															</div>

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="smsNotifyConfirmation"
																		cssClass="systemsettings_sms_checkbox" id="smsConfirm" />
																	<label for="smsConfirm"></label>
																</div>

																<div class="col-md-5 width-auto">
																	<strong> <spring:message code="pms.label.sms" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="smsNotifyConfirmation"
																	cssClass="systemsettings_sms_checkbox" id="smsConfirm" />
																<label for="smsConfirm"></label>
															</div> --%>
															</div>

														</div>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.sendNotificationOnCancellation" /><span
														class="red">* </span> </label>
													<div class="col-md-6 col-xs-12">
														<div
															class="directory-list weekly_special_day notification">

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="mailNotifyCancellation"
																		cssClass="systemsettings_sms_checkbox" id="mailCancel" />
																	<label for="mailCancel"></label>
																</div>

																<div class="col-md-5 width-auto">
																	<strong><spring:message code="pms.label.email" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="mailNotifyCancellation"
																	cssClass="systemsettings_sms_checkbox" id="mailCancel" />
																<label for="mailCancel"></label>
															</div> --%>
															</div>

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="smsNotifyCancellation"
																		cssClass="systemsettings_sms_checkbox" id="smsCancel" />
																	<label for="smsCancel"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong> <spring:message code="pms.label.sms" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="smsNotifyCancellation"
																	cssClass="systemsettings_sms_checkbox" id="smsCancel" />
																<label for="smsCancel"></label>
															</div> --%>
															</div>

														</div>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.sendNotificationOnNoShow" /><span
														class="red">* </span> </label>
													<div class="col-md-6 col-xs-12">
														<div
															class="directory-list weekly_special_day notification">

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="mailNotifyNoShow"
																		cssClass="systemsettings_sms_checkbox" id="mailNoShow" />
																	<label for="mailNoShow"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong><spring:message code="pms.label.email" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="mailNotifyNoShow"
																	cssClass="systemsettings_sms_checkbox" id="mailNoShow" />
																<label for="mailNoShow"></label>
															</div> --%>
															</div>

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="smsNotifyNoShow"
																		cssClass="systemsettings_sms_checkbox" id="smsNoShow" />
																	<label for="smsNoShow"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong> <spring:message code="pms.label.sms" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="smsNotifyNoShow"
																	cssClass="systemsettings_sms_checkbox" id="smsNoShow" />
																<label for="smsNoShow"></label>
															</div> --%>
															</div>

														</div>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.sendNotificationOnCheckIn" /><span
														class="red">* </span> </label>
													<div class="col-md-6 col-xs-12">
														<div
															class="directory-list weekly_special_day notification">

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="mailNotifyCheckIn"
																		cssClass="systemsettings_sms_checkbox"
																		id="mailCheckIn" />
																	<label for="mailCheckIn"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong><spring:message code="pms.label.email" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="mailNotifyCheckIn"
																	cssClass="systemsettings_sms_checkbox" id="mailCheckIn" />
																<label for="mailCheckIn"></label>
															</div> --%>
															</div>

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="smsNotifyCheckIn"
																		cssClass="systemsettings_sms_checkbox" id="smsCheckIn" />
																	<label for="smsCheckIn"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong> <spring:message code="pms.label.sms" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="smsNotifyCheckIn"
																	cssClass="systemsettings_sms_checkbox" id="smsCheckIn" />
																<label for="smsCheckIn"></label>
															</div> --%>
															</div>

														</div>
													</div>
												</div>

												<div class="form-group">
													<label class="control-label col-md-3"><spring:message
															code="systemSetting.label.sendNotificationOnCheckOut" /><span
														class="red">* </span> </label>
													<div class="col-md-6 col-xs-12">
														<div
															class="directory-list weekly_special_day notification">

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="mailNotifyCheckOut"
																		cssClass="systemsettings_sms_checkbox"
																		id="mailCheckOut" />
																	<label for="mailCheckOut"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong><spring:message code="pms.label.email" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="mailNotifyCheckOut"
																	cssClass="systemsettings_sms_checkbox" id="mailCheckOut" />
																<label for="mailCheckOut"></label>
															</div> --%>
															</div>

															<div class="col-md-3 col-xs-2">
																<div class="col-md-4">
																	<form:checkbox path="smsNotifyCheckOut"
																		cssClass="systemsettings_sms_checkbox"
																		id="smsCheckOut" />
																	<label for="smsCheckOut"></label>
																</div>
																<div class="col-md-5 width-auto">
																	<strong> <spring:message code="pms.label.sms" /></strong>
																</div>
																<%-- <div class="col-md-4">
																<form:checkbox path="smsNotifyCheckOut"
																	cssClass="systemsettings_sms_checkbox" id="smsCheckOut" />
																<label for="smsCheckOut"></label>
															</div> --%>
															</div>

														</div>
													</div>
												</div>

											</div>
										</div>


										<!-- CHECKIN/CHECKOUT -->
										<%-- <div class="informatition_form_sub_title">
										<span class="title"><spring:message
												code="systemSetting.label.checkinOrCheckout" /></span>
									</div>
									<div class="panel-body">
										<div class="form-horizontal tasi-form">
											<div class="form-group">
												<label class="control-label col-md-2"> <spring:message
														code="systemSetting.label.checkInTime" /><span
													class="red">*</span>
												</label>
												<div class="col-md-2 col-xs-11">
													<form:input type="time" id="txtCheckInTime"
														class="form-control validator" path="fmtdCheckInTime" />
												</div>
												<div class="col-md-1 col-xs-11 tick_green">
													<span id="txtCheckInTime_check" style="display: none;"
														class="switch-right"><i class=" fa fa-check"></i></span> <span
														id="txtCheckInTime_warning"
														class="switch-right warning_red" style="display: none;"><i
														class="fa fa-warning"></i></span>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-md-2"> <spring:message
														code="systemSetting.label.checkOutTime" /><span
													class="red">*</span>
												</label>
												<div class="col-md-2 col-xs-11">
													<form:input type="time" id="txtCheckOutTime"
														class="form-control validator" path="fmtdCheckOutTime" />
												</div>
												<div class="col-md-1 col-xs-11 tick_green">
													<span id="txtCheckOutTime_check" style="display: none;"
														class="switch-right"><i class=" fa fa-check"></i></span> <span
														id="txtCheckOutTime_warning"
														class="switch-right warning_red" style="display: none;"><i
														class="fa fa-warning"></i></span>
												</div>
											</div>
										</div>
									</div> --%>


										<!-- RATE PERIODS -->
										<%-- <div class="informatition_form_sub_title">
										<span class="title"><spring:message
												code="systemSetting.label.ratePeriods" /></span>
									</div>
									<div class="panel-body">
										<div class="row pms-tbl-head">
											<div style="display: none;" class="col-sm-1">Id</div>
											<div style="display: none;" class="col-sm-1">Add/Delete</div>
											<div class="col-sm-3">
												<spring:message code="pms.label.code" />
											</div>
											<div class="col-sm-3">
												<spring:message code="pms.label.from" />
											</div>
											<div class="col-sm-3">
												<spring:message code="pms.label.to" />
											</div>
											<div class="col-sm-1">
											   <c:if test="${rp_isCanDelete}">
									                 <spring:message code="pms.label.delete" /> 
                                                </c:if> 
												
											</div>
										</div>
										<div id="ratePeriodDiv">
											<c:forEach var="ratePeriod" items="${ratePeriods}"
												varStatus="status">
												<div id="rtPrdOldRow${status.index}" class="row pms-tbl-row">
													<div class="col-sm-1 tbl-cell" style="display: none;">${ratePeriod.id}</div>
													<!-- To identify whether the row is deleted(D), new(A) or the same old(NC) row -->
													<div class="col-sm-1 tbl-cell" style="display: none;"
														id='rtPrdOldRowAddDel${status.index}'>NC</div>
													<div class="col-sm-3 tbl-cell">${ratePeriod.code}</div>
													<div class="col-sm-3 tbl-cell">
														<fmtS:formatDate type="date"
															value="${ratePeriod.fromDate}" pattern="${dateFormat}" />
													</div>
													<div class="col-sm-3 tbl-cell">
														<fmtS:formatDate type="date" value="${ratePeriod.toDate}"
															pattern="${dateFormat}" />
													</div>
													<div class="col-sm-1 tbl-cell">
													
													  <c:if test="${rp_isCanDelete}">
									                   <button type="button" class="btn btn-danger btn-xs"
															ng-click="deleteRatePeriod('rtPrdOldRow${status.index}','rtPrdOldRowscode${ratePeriod.code}','rtPrdOldRowAddDel${status.index}', false)">
															<i class="fa fa-trash-o "></i>
														</button>
   
                                                      </c:if>
														

													</div>
												</div>
											</c:forEach>
										</div>

										<!-- To pass the value to controller -->
										 <input type="hidden" id="ratePeriodAsJson"
											name="ratePeriodAsJson" /> 
											<input type="hidden" id="companyAsJson"
											name="companyAsJson" />
										<div class="pms_row_bottom_align">
											<div class="col-sm-12" align="right">
											
											     <c:if test="${rp_isCanAdd}">
									               <input type="button" class="button-next  btn btn-info"
													ng-click="addRatePeriodRow();" id="addRate" md-disabled="isDisabled"
													value="<spring:message code="pms.label.add" />">
   
                                                 </c:if>
											
												
											</div>
										</div>
									</div> --%>
										<!-- FINANCIAL YEAR -->

										<div class="informatition_form_sub_title"
											ng-click="toggle10()">
											<span class="title">FINANCIAL YEAR</span>
										</div>
										<div class="panel-body" ng-show="show10">
											<div class="row pms-tbl-head">
												<div style="display: none;" class="col-sm-1">Id</div>
												<!-- <div style="display: none;" class="col-sm-1">Add/Delete</div> -->
												<div class="col-sm-3">CODE</div>
												<div class="col-sm-2">FROM</div>
												<div class="col-sm-2">TO</div>
												<div class="col-sm-2">Use CODE</div>
												<div class="col-sm-2">Use PREFIX</div>
											</div>
											<div id="finYearDiv">
												<c:forEach var="finYear" items="${finYears}"
													varStatus="status">
													<div class="row pms-tbl-row">
														<div class="col-sm-1 tbl-cell" style="display: none;">${finYear.id}</div>
														<div class="col-sm-3 tbl-cell">${finYear.finCode}</div>
														<div class="col-sm-2 tbl-cell">
															<fmtS:formatDate type="date" value="${finYear.fromDate}"
																pattern="${dateFormat}" />
														</div>
														<div class="col-sm-2 tbl-cell">
															<fmtS:formatDate type="date" value="${finYear.toDate}"
																pattern="${dateFormat}" />
														</div>
														<%-- <div class="col-sm-2 tbl-cell">${finYear.useCode}</div>
													<div class="col-sm-2 tbl-cell">${finYear.usePrefix}</div> --%>
														<div class="col-sm-2 tbl-cell">
															<input type="checkbox"
																${finYear.useCode == true ? 'checked' : ''} disabled />
															<label for="finYear"></label>
														</div>
														<div class="col-sm-2 tbl-cell">
															<input type="checkbox"
																${finYear.usePrefix == true? 'checked' : ''} disabled />
															<label for="finYear"></label>
														</div>

													</div>
												</c:forEach>
											</div>

											<!-- To pass the value to controller -->
											<input type="hidden" id="finYearAsJson" name="finYearAsJson" />
											<input type="hidden" id="companyAsJson" name="companyAsJson" />
											<div class="pms_row_bottom_align">
												<div class="col-sm-12" align="right">


													<input type="button" class="button-next  btn btn-info"
														ng-click="addFinancialYearRow();" id="addFinYear"
														ng-if="${isAdmin}"
														value="<spring:message code="pms.label.add" />">




												</div>
											</div>
										</div>







										<!-- AGING AR SETTINGS -->


										<%--                                      <div class="informatition_form_sub_title" ng-click="toggle10()">
										<span class="title">AGING AR SETTINGS</span>
									</div>
									<div class="panel-body" ng-show="show10">
										<div class="row pms-tbl-head">
											<div style="display: none;" class="col-sm-1">Id</div>
											<!-- <div style="display: none;" class="col-sm-1">Add/Delete</div> -->
											<div class="col-sm-3">
												<spring:message code="pms.label.code" />
											</div>
											<div class="col-sm-2">
												<spring:message code="pms.label.from" />
											</div>
											<div class="col-sm-2">
												<spring:message code="pms.label.to" />
											</div>
											<div class="col-sm-2">
												Use CODE
											</div>
											<div class="col-sm-2">
											   USE PREFIX
												
											</div>
										</div>
										<div id="finYearDiv">
											<c:forEach var="finYear" items="${finYears}"
												varStatus="status">
												<div class="row pms-tbl-row">
												    <div class="col-sm-1 tbl-cell" style="display: none;">${finYear.id}</div>
													<div class="col-sm-3 tbl-cell">${finYear.finCode}</div>
													<div class="col-sm-2 tbl-cell">
														<fmtS:formatDate type="date"
															value="${finYear.fromDate}" pattern="${dateFormat}" />
													</div>
													<div class="col-sm-2 tbl-cell">
														<fmtS:formatDate type="date" value="${finYear.toDate}"
															pattern="${dateFormat}" />
													</div>
													<div class="col-sm-2 tbl-cell">${finYear.useCode}</div>
													<div class="col-sm-2 tbl-cell">${finYear.usePrefix}</div>
													<div class="col-sm-2 tbl-cell"><input type="checkbox"
															  ${finYear.useCode == true ? 'checked' : ''} disabled />
															  <label for="finYear"></label>
															</div>
													<div class="col-sm-2 tbl-cell"><input type="checkbox" 
															${finYear.usePrefix == true? 'checked' : ''} disabled />
															<label for="finYear"></label></div>
															
												</div>
											</c:forEach>
										</div>

										<!-- To pass the value to controller -->
										 <input type="hidden" id="finYearAsJson"
											name="finYearAsJson" /> 
											<input type="hidden" id="companyAsJson"
											name="companyAsJson" />
										<div class="pms_row_bottom_align">
											<div class="col-sm-12" align="right">
											
											    
									               <input type="button" class="button-next  btn btn-info"
													ng-click="addFinancialYearRow();" id="addFinYear" ng-if="${isAdmin}"
													value="<spring:message code="pms.label.add" />">
   
                                                
											
												
											</div>
										</div>
									</div>
 --%>






										<!-- MAIL/SMS -->
										<div class="informatition_form_sub_title"
											ng-click="toggle11()">
											<span class="title"><spring:message
													code="systemSetting.label.mailSettings" /> </span>
										</div>
										<div class="panel-body" ng-show="show11">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.smtpServer" /></label>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtSmtpServer" maxlength="100"
															class="form-control form-control-inline input-medium mail-form "
															path="smtpServer" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtSmtpServer_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtSmtpServer_warning"
															class="switch-right warning_red msg-mail-form"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.smtpPort" /></label>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtSmtpPort" maxlength="10"
															class="form-control form-control-inline input-medium mail-form "
															path="smtpPort" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtSmtpPort_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtSmtpPort_warning"
															class="switch-right warning_red msg-mail-form"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.mailId" /></label>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtMailId" maxlength="50"
															class="form-control form-control-inline input-medium mail-form "
															path="smtpSUserId" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMailId_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMailId_warning"
															class="switch-right warning_red msg-mail-form"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="pms.label.password" /></label>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtMailPassword" maxlength="50"
															class="form-control form-control-inline input-medium mail-form "
															path="smtpPassword" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtMailPassword_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtMailPassword_warning"
															class="switch-right warning_red msg-mail-form"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div>
												</div>

											</div>
										</div>


										<div class="informatition_form_sub_title"
											ng-click="toggle12()">
											<span class="title"><spring:message
													code="systemSetting.label.smsSettings" /></span>
										</div>
										<div class="panel-body" ng-show="show12">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="systemSetting.label.webService" /></label>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtWebService" maxlength="100"
															class="form-control form-control-inline input-medium sms-form "
															path="smsWebService" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtWebService_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtWebService_warning"
															class="switch-right warning_red msg-sms-form"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="pms.label.userId" /></label>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtUserId" maxlength="50"
															class="form-control form-control-inline input-medium sms-form "
															path="smsUserId" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtUserId_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtUserId_warning"
															class="switch-right warning_red msg-sms-form"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-2"><spring:message
															code="pms.label.password" /></label>
													<div class="col-md-3 col-xs-11">
														<form:input id="txtSmsPassword" maxlength="50"
															type="password"
															class="form-control form-control-inline input-medium sms-form "
															path="smsPassword" />
													</div>
													<div class="col-md-1 col-xs-11 tick_green">
														<span id="txtSmsPassword_check" style="display: none;"
															class="switch-right"><i class=" fa fa-check"></i></span>
														<span id="txtSmsPassword_warning"
															class="switch-right warning_red msg-sms-form"
															style="display: none;"><i class="fa fa-warning"></i></span>
													</div>
												</div>
											</div>
										</div>


										<div class="panel-body">
											<div class="form-horizontal tasi-form">
												<div class="form-group">
													<div class="col-lg-10">

														<c:if test="${cp_isCanAdd}">
															<button type="submit" class="btn btn-success"
																onclick="return saveData();">
																<spring:message code="pms.label.save" />
															</button>

														</c:if>


														<button type="button" class="btn btn-default"
															onclick="window.location.href='../systemSettings/settings'">
															<spring:message code="pms.btn.back" />
														</button>
													</div>
												</div>
											</div>
										</div>
									</div>

								</section>
							</div>
						</div>
						<!-- page end-->
					</section>
				</div>
			</section>
			<!--main content end-->
		</section>

	</form:form>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!--   <script src="http://code.jquery.com/jquery-3.1.0.min.js"></script> -->
	<!-- Include all compiled plugins (below), or include individual files as needed -->


	<!--  <script type="text/javascript"
	src="/pms/resources/common/js/jquery.scrolltop.js"></script>
 -->
	<script>
        (function($){

            $.scrolltop({
                template: '<i class="fa fa-chevron-up"></i>',
                class: 'custom-scrolltop'
            });

        })(jQuery);

    </script>


</body>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/hover-dropdown.js' />"></script>

</html>