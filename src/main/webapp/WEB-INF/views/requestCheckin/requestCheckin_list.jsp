<jsp:directive.include file="../common/includes/page_directives.jsp" />
<!DOCTYPE html>
<html lang="en">
<c:set var="mainDivid" value="card_content_main_div" scope="request" />
<c:set var="mainDivclass" value="card_style" scope="request" />
<c:set var="formId" value="reservation_list" scope="request" />
<c:set var="formName" value="reservation_list" scope="request" />
<c:set var="moduleName" value="Request" scope="request" />
<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="insertFunction" value="addRequest()" scope="request" />
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
<!-- <script type="text/javascript">
	window.count = $
	{
		count
	}
</script> -->
<script>
	window.cp_isCanView = $
	{
		cp_isCanView
	}
	window.cp_isCanAdd = $
	{
		cp_isCanAdd
	}
	window.cp_isCanEdit = $
	{
		cp_isCanEdit
	}
	window.cp_isCanDelete = $
	{
		cp_isCanDelete
	}
</script>
<head>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/angular/multidateSelection/multipleDatePicker.min.js'/> "></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/moment-with-locales.js'/> "></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_grid_common.js' />"></script>
<link
	href="<c:url value="/resources/common/js/angular/multidateSelection/multipleDatePicker.css" />"
	rel="stylesheet" />
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/requestCheckin.js' />"></script>
<link
	href="<c:url value="/resources/common/components/reception_reservation_card/css/reservation_card.css" />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/common/css/status_color_code.css'></c:url>"
	rel="stylesheet">

<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/requestCheckin.css' />" />
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js'/>"></script>
</head>
<body class="full-width" id="requestCheckin" ng-app="pmsApp"
	ng-controller="requestChkinCtrl as ctrlreq" ng-cloak>
	<input type="hidden" value="${dateFormat}" id="dateFormat" />
	<section id="container" class="">
		<c:import url="../menu/topMenu.jsp" />
		<input type="hidden" value="${hotelDate}" id="hotelDate" />
		<section id="main-content">
			<div class="">
				<section class="wrapper">
					<!-- page start-->
					<div class="row">
						<div class="col-lg-12">
							<section class="panel padding_bottom_zrw">
								<div class="panel-body">
									<div class="col-md-12">
										<div class="task-progress module_head">
											<a href="<c:url value='${backUrl}' />"
												class="ios-back-button"
												data-text="<spring:message code="pms.btn.backtop" />"></a>
											<div style="width: 50%">
												<h1>${moduleName}</h1>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div
											class="input-group date form_datetime-adv form-control search_box_div">
											<div class="form-control serach_div">
												<input type="text" id="reqSearchTxt"
													ng-model="search.comnSearchValue" class="comnSearchtxt"
													ng-if="!search.advSearch && selectedIndex==1" /> <input
													type="text" id="reqSearchText"
													ng-model="searchUnprocedTab.comnSearchValue"
													class="comnSearchtxt"
													ng-keypress="searchOnEnter($event.keyCode)"
													ng-if="!searchUnprocedTab.advSearch && selectedIndex==0" />

												<div ng-if="selectedIndex==1">
													<div ng-if="search.roomNo.searchable" id="roomNum"
														class="inputTags ng-scope">
														Room Number : {{search.roomNo.searchValue}}<span
															class="inputclose" ng-click="tagInputClose('roomNum')"
															role="button" tabindex="0">x</span>
													</div>
													<div ng-if="search.reqDate.searchable" id="reqdate"
														class="inputTags ng-scope">
														Request Date : {{search.reqDate.searchValue
														|date:'yyyy-MM-dd'}}<span class="inputclose"
															ng-click="tagInputClose('reqdate')" role="button"
															tabindex="0">x</span>
													</div>
													<div ng-if="search.reqStatus.searchable" id="reqstatus"
														class="inputTags ng-scope">
														requestStatus : {{search.reqStatus.searchValue}}<span
															class="inputclose" ng-click="tagInputClose('reqstatus')"
															role="button" tabindex="0">x</span>
													</div>
												</div>
												<div ng-if="selectedIndex==0">
													<div ng-if="searchUnprocedTab.roomNo.searchable"
														id="roomNum" class="inputTags ng-scope"
														ng-disabled="roomnumberUnprocessed">
														roomNumber : {{searchUnprocedTab.roomNo.searchValue}}<span
															class="inputclose" ng-click="tagInputClose('roomNumber')"
															role="button" tabindex="0">x</span>
													</div>
													<div ng-if="searchUnprocedTab.reqStatus.searchable"
														id="reqStatusunpro" class="inputTags ng-scope">
														requestStatus :
														{{searchUnprocedTab.reqStatus.searchValue}}<span
															class="inputclose"
															ng-click="tagInputClose('reqStatusunpro')" role="button"
															tabindex="0">x</span>
													</div>
												</div>
											</div>
											<div class="input-group-btn tools clear_btn">
												<div class="search_claear_btn_main_div">
													<button class="search_claear_btn"
														ng-click="searchBoxClear()">
														<i class="fa fa-times"></i>
													</button>
												</div>
												<div id="search_form_down"
													class="btn date-reset drop_dwn_btn">
													<i class="fa fa-angle-down down_form"></i>
												</div>
												<button type="button" class="search_button btn"
													ng-click="simpleSearch()">Search</button>
											</div>
											<div style="display: none;" id="search_fom_div"
												class="search_fom_div">
												<div class="main_search_fom_div">
													<div class="main_search_fom_div_close_btn">
														<a id="search_form_close" href="#"></a>
													</div>
													<section class="panel">
														<div class="panel-body">
															<form method="get"
																class="advance_search_form form drp_dwn_form"
																id="loginForm" action="">
																<md-content layout-padding class="searchContainer">
																<div id="reservation_search" ng-if="selectedIndex==1">
																	<div layout-gt-xs="row">
																		<md-input-container class="md-block">
																		<label>Room Num:</label> <input
																			ng-model="search.roomNo.searchValue"> </md-input-container>
																		<md-input-container class="md-block">
																		<label>Status:</label> <input
																			ng-model="search.reqStatus.searchValue"> </md-input-container>
																	</div>
																	<div layout-gt-xs="row">
																		<md-input-container> <label>Request
																			Date:</label> <md-datepicker
																			ng-model="search.reqDate.searchValue"
																			md-open-on-focus></md-datepicker> </md-input-container>
																	</div>
																</div>

																<div id="inhouse_search" ng-if="selectedIndex==0">
																	<div layout-gt-xs="row">
																		<md-input-container class="md-block">
																		<label>Room Number :</label> <input
																			ng-model="searchUnprocedTab.roomNo.searchValue">
																		</md-input-container>
																		<md-input-container class="md-block">
																		<label>Request Status:</label> <input
																			ng-model="searchUnprocedTab.reqStatus.searchValue">
																		</md-input-container>
																	</div>

																</div>

																</md-content>

																<div class="advance_search_content_sub_div">
																	<div>
																		<!-- <button type="submit" class="btn btn-danger">Submit</button> -->
																		<button class="advance_search_btn btn btn-danger"
																			type="button" ng-click="advanceSearch()">Search</button>
																		<button type="button" id="search_form_cancel"
																			class="btn btn-default" onclick="">Close</button>
																	</div>
																</div>
																<div
																	onclick="javascript:document.getElementById('loginBox').style.display='none';$('#loginButton').removeClass('active');"
																	id="close" class="advance_search_close_div">
																	<span class="advance_serach_close_btn"></span>
																</div>
															</form>
														</div>
													</section>
												</div>
											</div>

										</div>
									</div>

									<div class="col-md-6 newreq" style="text-align: right;">

										<div class="form-inline">

											<c:if test="${cp_isCanAdd}">
												<div class="form-group tab_add_btn">
													<div class="form-group tab_add_btn">
														<md-button class="md-raised md-warn" ng-click="newAddon()">New
														Request</md-button>
													</div>
											</c:if>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>

					<section class="panel">
						<header class="panel-heading module_caption">
							<h1>In-House Request-List</h1>
							<!-- <span class="tools pull-right"> <a
								class="fa fa-chevron-down" href="javascript:;"></a>
							</span> -->
						</header>
						<div class="panel-body roomDtlsDiv">
							<!-- <div ng-cloak>
								<md-content> <md-tabs md-dynamic-height 
								 md-selected="selectedIndex" id="requestTab"
									md-border-bottom> <md-tab label="UNPROCESSED">
								<md-content class="md-padding">
								<div id="dataTableDiv">
									<div class="cmn_tbl"><table id="entry-grid" datatable=""
										dt-options="ctrlreq.dtOptions"
										dt-instance="ctrlreq.dtInstance"
										dt-columns="ctrlreq.dtColumns" style="width: 100%;"
										class="angDataTable table table-hover"></table></div>

									--------- unprocessed modal ----------

								</div>
								</md-content> </md-tab> <md-tab label="ALL-REQUESTS"> <md-content
									class="md-padding">
								<div id="dataTableDiv">
									<div class="cmn_tbl"><table id="entry-grid" datatable=""
										dt-options="ctrlreq.dtOptions1"
										dt-instance="ctrlreq.dtInstance1"
										dt-columns="ctrlreq.dtColumns1" style="width: 100%;"
										class="angDataTable table table-hover"></table></div>



								</div>
								</md-content> </md-tab> </md-tabs> </md-content>
							</div> -->

							<div class="tab_design_main_div">
								<ul class="nav nav-tabs tab_design_cmn">
									<li class="active"><a data-toggle="tab"
										href="#UNPROCESSED" ng-click="selectUnprocessed()">UNPROCESSED</a></li>
									<li><a data-toggle="tab" href="#ALL-REQUESTS"
										ng-click="selectAllRequest()">ALL REQUESTS</a></li>

								</ul>

								<div class="tab-content">
									<div id="UNPROCESSED" class="tab-pane fade in active">
										<div id="dataTableDiv">
											<div class="cmn_tbl">
												<table id="entry-grid" datatable=""
													dt-options="ctrlreq.dtOptions"
													dt-instance="ctrlreq.dtInstance"
													dt-columns="ctrlreq.dtColumns"
													class="angDataTable table table-hover"></table>
											</div>
										</div>

									</div>
									<div id="ALL-REQUESTS" class="tab-pane fade">
										<div id="dataTableDiv">
											<div class="cmn_tbl">
												<table id="entry-grid" datatable=""
													dt-options="ctrlreq.dtOptions1"
													dt-instance="ctrlreq.dtInstance1"
													dt-columns="ctrlreq.dtColumns1"
													class="angDataTable table table-hover"></table>
											</div>
										</div>

									</div>
								</div>
							</div>
						</div>

						<div class="panel-body footer_div">
							<div class="limit_drop col-md-3">
								<div class="list_info">Showing {{totalItems}} of
									{{bigTotalItems}} Items</div>
								<select ng-model="pagination.limit"
									class="selectpicker form-control" ng-change="getSearchData()">
									<option value="5">5</option>
									<option value="10">10</option>
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
								</select>

							</div>
							<div class="pagination_div col-md-9">
								<ul uib-pagination total-items="bigTotalItems"
									ng-model="bigCurrentPage" max-size="maxSize"
									items-per-page="itemsPerPage" ng-change="pageChanged()"
									class="pagination-sm" boundary-links="true" rotate="false"></ul>
							</div>
						</div>

					</section>
					<div aria-hidden="true" aria-labelledby="myModalLabel"
						role="dialog" tabindex="-1" id="requestAddNewmyModal"
						class="modal fade">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div id="outerModal">
									<div class="modal-header">
										<button aria-hidden="true" data-dismiss="modal" class="close"
											type="button">x</button>
										<h4 class="modal-title">New Request</h4>
									</div>

									<div class="searchDiv">
										<label class="col-md-2"><spring:message
												code="request.label.roomno" /> <span class="red">*</span></label>

										<div class="col-md-4 rnum">
											<div class="col-md-10">
												<select id="roomnumber"
													class="form-control input-sm validator"
													ng-disabled="roomNumberDisbl" ng-model="selectedIcons"
													ng-change="showHideDiv(selectedIcons)"
													ng-options="x.checkin_no as x.room_number for x in roomLists">
													<option value="">SELECT</option>
												</select>
											</div>
											<div class="col-md-1">
												<span id="roomnumber_check" class="switch-right"
													style="display: none; color: #41cac0;"><i
													class=" fa fa-check"></i></span> <span id="roomnumber_warning"
													class="switch-right warning_red" style="display: none;"><i
													class="fa fa-warning"></i></span>
											</div>
										</div>
										<div class="col-md-6">
											<div class="col-md-4 room_dtl" ng-if="numberroom!=null">
												<i class="fa fa-bed" aria-hidden="true"></i> {{numberroom}}
											</div>
											<div class="col-md-4 room_dtl" ng-if="first_name!=null">
												<i class="fa fa-user" aria-hidden="true"></i> {{first_name}}
											</div>
											<div class="col-md-4 room_dtl" ng-if="phone!=null">
												<i class="fa fa-mobile" aria-hidden="true"></i> {{phone}}
											</div>
										</div>
									</div>



									<div class="row">
										<label class="col-md-2 facility"><spring:message
												code="request.label.facility" /> <span class="red">*</span></label>

										<div class="col-md-4 facility">
											<div class="col-md-8" id="widthAlign">
												<select id="facilities"
													class="form-control input-sm validator"
													ng-disabled="roomNumberDisbl" ng-model="facilityId"
													ng-options="x.id as x.code for x in facilitiesList">
													<option value="">SELECT</option>
												</select>
											</div>
											<div class="col-md-2">
												<span id="facilities_check" class="switch-right"
													style="display: none; color: #41cac0;"><i
													class=" fa fa-check"></i></span> <span id="facilities_warning"
													class="switch-right warning_red" style="display: none;"><i
													class="fa fa-warning"></i></span>
											</div>
										</div>




										<div class="col-md-4 facility providerFacility">
											<md-input-container class="facilInput" flex="70">

											<label>Facility Provider</label> <md-select
												ng-click="clickOutSide()" ng-model="provider"
												md-disable-backdrop> <md-option
												ng-repeat="pvdr in providers" ng-value="pvdr.id">
											{{pvdr.code}}</md-option> </md-select> </md-input-container>
										</div>

										<!-- <div class="col-md-4 facility providerFacility">
											<label>Facility Provider</label>
											<md-input-container class="facilInput">
											<select ng-model="provider"
												ng-options="prov.name for prov in providers"
												class="form-control">
											</select> </md-input-container>
										</div> -->


									</div>



									<div class="row">
										<div class="col-md-2 facility">
											<label>Request Type</label>
										</div>
										<div class="col-md-3 facility">
											<select id="ddlRequestType" ng-disabled="roomNumberDisbl"
												class="form-control input-sm">
												<option value=1>ON DATE</option>
												<option value=0>DAILY</option>
											</select>
										</div>
										<div class="col-md-3 facility" id="enterDate">
											<md-datepicker ng-disabled="roomNumberDisbl"
												ng-model="reqDate" md-placeholder="Enter date"
												md-min-date="hotelDate" md-max-date="minDeparture"
												md-open-on-focus></md-datepicker>
										</div>

										<div class="col-md-3 facility" id="Todate">
											<md-datepicker ng-model="reqTodate" md-placeholder="To Date"
												ng-click="disToDate()" md-min-date="minArr"
												md-max-date="minDeparture" md-open-on-focus></md-datepicker>
										</div>
									</div>

									<div class="row" id="multiCalandrInactive"
										style="display: none;">
										<div class="col-md-12 dateInAct">
											<div
												class="input-group date form_datetime-adv form-control search_box_div"
												id="dateSearch">
												<div class="row">
													<div class="col-md-4 pull-right" id="inctvDates">
														<div class="input-group-btn tools clear_btn">
															<div class="search_claear_btn_main_div">
																<!-- <button class="search_claear_btn"
																ng-click="dateBoxClear()">
																<i class="fa fa-times"></i>
															</button> -->
															</div>
															<div id="search_form_down"
																class="btn date-reset drop_dwn_btn pull-right">
																<button type="button" id="down"
																	ng-click="bottondownRequest()" class="down_form">
																	Inactive Dates <span><i class="fa fa-angle-down"></i></span>
																</button>
																<button type="button" id="up"
																	ng-click="bottonhideRequest()" class="down_form"
																	style="display: none;">
																	Inactive Dates <span><i class="fa fa-angle-up"></i></span>
																</button>
															</div>
														</div>
													</div>
													<div class="col-md-9">
														<div class="inputRequest" id="dateReqstInput"></div>
													</div>
												</div>
												<div style="display: none;" id="search_fom_div_Request"
													class="search_fom_div">
													<div class="main_search_fom_div">
														<div class="main_search_fom_div_close_btn">
															<a id="search_form_close" href="#"></a>
														</div>
														<section class="panel">
															<div class="panel-body">
																<div class="reqwidth">
																	<form method="get"
																		class="advance_search_form form drp_dwn_form"
																		id="loginForm" action="">
																		<md-content layout-padding class="searchContainer">

																		<div id="inhouse_search">
																			<div layout-gt-xs="row">
																				<div class="row">
																					<div class="col-md-12 facilityrequest">
																						<multiple-date-picker class="multiselection"
																							sunday-first-day="true"
																							enable-select-month="true" change-year-past="5"
																							change-year-future="10"
																							disable-days-before="reqDate"
																							disable-days-after="reqTodate"
																							ng-model="myArrayOfDates"></multiple-date-picker>
																					</div>
																				</div>
																			</div>
																		</div>
																		</md-content>

																		<!-- <div
																		onclick="javascript:document.getElementById('loginBox').style.display='none';$('#loginButton').removeClass('active');"
																		id="close" class="advance_search_close_div">
																		<span class="advance_serach_close_btn"></span>
																	</div> -->
																	</form>
																</div>
															</div>
														</section>
													</div>
												</div>
											</div>
										</div>
									</div>


									<div class="row">

										<label class="col-md-2 facility time"><spring:message
												code="request.label.requestTime" /> <span class="red"></span></label>

										<div class="col-md-3 clock">
											<div uib-timepicker ng-model="reqTime" hour-step="hstep"
												minute-step="mstep" show-meridian="ismeridian"></div>
										</div>
										<div class="col-md-2 facility remarks">
											<md-input-container class="md-block facilInput" flex-gt-sm>
											<label><spring:message code="request.label.remarks" />
												<span class="red"></span></label> <input ng-model="reqRemarks"
												id="remarksId"></md-input-container>
										</div>


										<div class="col-md-3 facility">
											<select id="ddlStatusType">

												<option value=0>Inactive</option>
												<option value=1>Processed</option>
												<option value=2>Unprocessed</option>
											</select>
										</div>
										<div class="col-md-3 pull-right">
											<button type="button" ng-click="AddOnList()" id="reqlistbtn"
												class="md-raised md-warn md-button md-ink-ripple">Add
												To Request List</button>
										</div>
									</div>

									<section class="panel" id="dataTableShow">
										<header class="panel-heading module_caption" id="hdrNew">
											<h3>Request List</h3>
											<span class="tools pull-right"> <a
												class="fa fa-chevron-down" href="javascript:;"></a>
											</span>
										</header>
										<div class="panel-body roomDtlsDiv">
											<div ng-cloak>
												<div class="row">
													<div class="col-md-12">
														<div id="dataTableDiv">
															<table id="entry-grid" datatable=""
																dt-options="ctrlreq.dtOptions2"
																dt-instance="ctrlreq.dtInstance2"
																dt-columns="ctrlreq.dtColumns2" style="width: 100%;"
																class="angDataTable table table-hover"></table>
														</div>
													</div>
												</div>
											</div>
										</div>
									</section>

									<div class="modal-footer">
										<button id="contRequestBtn" type="button"
											class="btn btn-success addbtn" ng-click="saveAddonRequest()">Save</button>
										<button id="cancelExpDepartBtn" type="button"
											class="btn btn-default rbtnClose"
											ng-click="cancelAddNewPopUp();">
											<spring:message code="pms.btn.cancel" />
										</button>
										<button id="deleteRequestBtn" type="button"
											class="btn btn-warning reqstCancel pull-right"
											ng-click="deleteRequest()">Delete</button>
										<button id="contRequestUpdateBtn" type="button"
											class="btn btn-success addbtn" ng-click="saveAddonUpdate()">Update</button>

									</div>
								</div>
							</div>
						</div>
					</div>

				</section>
			</div>
		</section>
	</section>
</body>

<script type="text/javascript"
	src="<c:url value='/resources/common/js/hover-dropdown.js' />"></script>
</html>