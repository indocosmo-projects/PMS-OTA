<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@ page import="com.indocosmo.pms.enumerator.CommunicationGroups"%>
<c:set var="moduleTitle" value="Communication" scope="request" />
<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="moduleName" value="Communication" scope="request" />
<c:set var="formId" value="communication" scope="request" />
<c:set var="formName" value="communication" scope="request" />
<c:set var="masterListHeaderName" value="Communication" scope="request" />
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
<script type="text/javascript">
	window.cp_isCanEdit = ${cp_isCanEdit};
	
	function DisableBackButton() {
		window.history.forward()
		}
		DisableBackButton();
		window.onload = DisableBackButton;
		window.onpageshow = function(evt) { if (evt.persisted) DisableBackButton() }
		window.onunload = function() { void (0) }
</script>

<html ng-app="pmsApp">
<head>
<title>${moduleTitle}</title>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/js/angular/css/jquery.dataTables.min.css'/>" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/check_in.css' />" />
<c:import url="../common/includes/master_includes.jsp" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/communication.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/communication_list.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/textAngular-sanitize.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/textAngular.min.js'/>"></script>

</head>
<body class="full-width" id="commntn"
	ng-controller="communicationCtrl as commtn">
	<section id="container" class="">
		<c:import url="../menu/topMenu.jsp" />
		<section id="main-content">
			<input type="hidden" id="hotelDate" value="${hotelDate}" />
			<div class="">
				<section class="wrapper">
					<!-- page start-->
					<div class="row">
						<div class="col-lg-12">
							<section class="panel">
								<div class="panel-body">
									<div class="row">
										<div class="col-md-12">
											<div class="task-progress trans_module_head">

												<c:if test="${backBtnStatusVal==1}">
													<a href="<c:url value='${backUrl}' />"
														class="ios-back-button"
														data-text="<spring:message code="pms.btn.backtop" />"></a>
												</c:if>
												<div style="width: 50%">
													<h1>${moduleName}</h1>
												</div>
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>
					<section class="panel">
						<header class="panel-heading module_caption">
							<h1>
								<spring:message
									code="communication.label.communicationCriterias" />
							</h1>
						</header>
						<div class="panel-body">
							<div class="parametersDiv">
								<div class="row">
									<div class="col-md-4">
										<label class="control-label col-sm-6">Communication
											Group</label>
										<div class="col-sm-6">
											<select id="slctTxnType" ng-change="showCriterias()"
												class="form-control input-sm" ng-model="comList.group">
												<c:forEach items="${CommunicationGroups}" var="group">
													<option value="${group.code}">${group}</option>
												</c:forEach>
											</select>
										</div>
									</div>


								</div>

								<div class="criteriaDiv">
									<div class="row" ng-show="showresvn">
										<div class="col-md-10 resvParam">
											<label class="control-label col-sm-2">Reserved</label>

											<div class="col-sm-8">

												<div class="row">
													<div class="col-sm-12">
														<div class="col-sm-4">
															<input type="radio" class="with-font" name="resvdOn"
																ng-model="comList.resvtn" id="cutOff" value="cutoff" /><label
																class="new_one" for="cutOff"></label>
															<div class="right_side">Cutoff Date Before (Days)</div>
														</div>

														<div class="col-sm-4">
															<input type="text" ng-model="comList.resvCutOff"
																ng-disabled="comList.resvtn == 'on'" id="cutoff_txt"
																class="form-control form-control-inline input-medium"
																numbers-only />
														</div>
													</div>
												</div>

												<div class="row">
													<div class="col-sm-12">
														<div class="col-sm-4">
															<input type="radio" class="with-font" name="resvdOn"
																ng-model="comList.resvtn" id="arrvlOn" value="on" /><label
																class="new_one" for="arrvlOn"></label>
															<div class="right_side">Arrival On</div>
														</div>
														<div class="col-sm-4">
															<div class="communication_date_picker">
																<md-datepicker ng-model="comList.resvArrvlOn"
																	md-min-date="minDate" md-placeholder="Enter date"
																	ng-disabled="comList.resvtn == 'cutoff'"></md-datepicker>
															</div>
														</div>
													</div>
												</div>

											</div>
										</div>
									</div>

									<div class="row" ng-show="showinhouse">
										<div class="col-md-10 inhouseParam">
											<label class="control-label col-sm-3">In House</label>
											<div class="col-sm-9">
												<div class="row">Retrieve In House List</div>
											</div>
										</div>
									</div>

									<div class="row" ng-show="showcheckout">
										<div class="col-md-10 checkoutParam">
											<label class="control-label col-sm-3">Check Out</label>
											<div class="col-sm-9">
												<div class="row">
													<div class="col-sm-12">
														<div class="col-sm-4">
															<input type="radio" class="with-font" name="checkOut"
																ng-model="comList.checkOut" id="checkoutOn" value="on" /><label
																class="new_one" for="checkoutOn"></label>
															<div class="right_side">Check Out On</div>
														</div>

														<div class="col-sm-4 chkout_on">
															<md-datepicker ng-model="comList.checkOutOn"
																md-max-date="maxDate" md-placeholder="Enter date"
																ng-disabled="comList.checkOut == 'btwn'"></md-datepicker>
														</div>
													</div>
												</div>

												<div class="row">
													<div class="col-sm-12">
														<div class="col-sm-4">
															<input type="radio" class="with-font" name="checkOut"
																ng-model="comList.checkOut" id="checkoutBfr"
																value="btwn" /><label class="new_one" for="checkoutBfr"></label>
															<div class="right_side">Check Out Between</div>
														</div>
														<div class="col-sm-8">
															<div class="col-sm-5">
																<md-datepicker ng-model="comList.checkOutBefore1"
																	md-max-date="maxDate" md-placeholder="Enter date"
																	ng-disabled="comList.checkOut == 'on'"></md-datepicker>
															</div>
															<label class="col-sm-2 btwn_lbl">And</label>
															<div class="col-sm-5">
																<md-datepicker ng-model="comList.checkOutBefore2"
																	md-max-date="maxDate" md-placeholder="Enter date"
																	ng-disabled="comList.checkOut == 'on'"></md-datepicker>
															</div>

														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="getDataBtn">
										<button class="btn btn-primary" ng-click="loadTable()">Get
											Data</button>
									</div>
								</div>


							</div>

							<div class="dataTableDiv">
								<div ng-show="resvtnDataTable">
									<section class="panel">
										<div class="panel-body">
											<table id="entry-grid" datatable=""
												dt-options="commtn.dtOptions"
												dt-instance="commtn.dtInstance"
												dt-columns="commtn.dtColumns" style="width: 100%;"
												class="angDataTable table table-hover"></table>
										</div>
									</section>
								</div>

								<div ng-show="recptnDataTable">
									<section class="panel">
										<div class="panel-body">
											<table id="entry-grid" datatable=""
												dt-options="commtn.dtOptions1"
												dt-instance="commtn.dtInstance1"
												dt-columns="commtn.dtColumns1" style="width: 100%;"
												class="angDataTable table table-hover"></table>
										</div>
									</section>
								</div>

							</div>

						</div>
					</section>
					<section class="panel">
						<div class="panel-body">
							<div class="align-btn-rgt">
								<button class="btn btn-default" ng-click="cancel()">
									<spring:message code="pms.btn.cancel"></spring:message>
								</button>
							</div>
						</div>
					</section>
				</section>
			</div>
		</section>
</body>
</html>