<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@page import="com.indocosmo.pms.enumerator.ReservationStatus"%>
<c:set var="resvStatus" value="<%=ReservationStatus.values()%>"></c:set>
<!DOCTYPE html>
<html lang="en">
<c:set var="mainDivid" value="card_content_main_div" scope="request" />
<c:set var="mainDivclass" value="card_style" scope="request" />
<c:set var="formId" value="reservation_list" scope="request" />
<c:set var="formName" value="reservation_list" scope="request" />
<c:set var="formAction" value="getRoomRate" scope="request" />
<c:set var="moduleName" value="Reservation" scope="request" />

<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />
<c:set var="hotelDateDsply" value="hotelDateDisplay" scope="session" />
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

<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<script type="text/javascript">
	window.count = $
	{
		count
	}
</script>

<head>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reservation_list_new.js' />"></script>
<link
	href="<c:url value="/resources/common/components/reception_reservation_card/css/reservation_card.css" />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/common/css/status_color_code.css'></c:url>"
	rel="stylesheet">
<link href="<c:url value="/resources/pms/css/reservation_list.css" />"
	rel="stylesheet" />
</head>

<body class="full-width" id="reservation" ng-app="pmsApp"
	ng-controller="resvCtrl" ng-cloak>
	<input type="hidden" value="${count}" id="shiftCount">
	<input type="hidden" value="${dateFormat}" id="dateFormat" />
	<input type="hidden" id="hotelDate" value="${hotelDate}">
	<input type="hidden" value="${currencySymbol}" id="currencySymbol" />
	<input type="hidden" value="${timeFormat}" id="timeFormat" />

	<section id="container" class="">
		<c:import url="../menu/topMenu.jsp" />

		<section id="main-content">
			<div class="">
				<section class="wrapper">

					<!-- page start-->
					<div class="row">
						<div class="col-lg-12">
							<section class="panel">
								<div class="panel-body">
									<div class="col-md-12 main_content_div">
										<div class="task-progress module_head">
											<%-- <a href="<c:url value='${backUrl}' />"
												class="ios-back-button"
												data-text="<spring:message code="pms.btn.backtop" />"></a> --%>
											<div class="main_com_headding" style="width: 50%">
												<h1>${moduleName}</h1>
											</div>

										</div>
									</div>
									<div class="col-sm-12">
										<div class="row">
											<div class="col-md-5">
												<div
													class="input-group date form_datetime-adv form-control search_box_div">
													<div class="serach_div" id="simpleSearchTxt">
														<input type="text" id="resvSearchTxt"
															ng-keypress="searchOnEnter($event.keyCode)"
															ng-model="search.comnSearchValue" class="comnSearchtxt"
															ng-if="!search.advSearch" />
														<div ng-if="search.resvBy.searchable" id="reserved_by"
															class="inputTags ng-scope">
															reservedBy : {{search.resvBy.searchValue}}<span
																class="inputclose" ng-click="tagInputClose('resvBy')"
																role="button" tabindex="0">x</span>
														</div>
														<div ng-if="search.resvDate.searchable" id="resv_date"
															class="inputTags ng-scope">
															reservationDate : {{search.resvDate.searchValue
															|date:'yyyy-MM-dd'}}<span class="inputclose"
																ng-click="tagInputClose('resvDate')" role="button"
																tabindex="0">x</span>
														</div>
														<div ng-if="search.arrDate.searchable" id="arr_date"
															class="inputTags ng-scope">
															arrivalDate : {{search.arrDate.searchValue
															|date:'yyyy-MM-dd'}}<span class="inputclose"
																ng-click="tagInputClose('arrDate')" role="button"
																tabindex="0">x</span>
														</div>
														<div ng-if="search.resvStatus.searchable" id="resv_status"
															class="inputTags ng-scope">
															reservationStatus : {{search.resvStatus.searchValue}}<span
																class="inputclose"
																ng-click="tagInputClose('resvStatus')" role="button"
																tabindex="0">x</span>
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
																		<div>
																			<div class="search_div_con_list">
																				<label>Reserved By:</label>
																				<md-input-container class="md-block">
																				<input ng-model="search.resvBy.searchValue">
																				</md-input-container>
																			</div>
																			<div class="search_div_con_list">
																				<label>Reservation Status:</label>
																				<!-- <md-input-container class="md-block" >
																		
																		 <input
																			ng-model="search.resvStatus.searchValue"> </md-input-container> -->
																				<div class="col-sm-5 resvStat">
																					<select class="form-control"
																						ng-model="search.resvStatus.searchValue">
																						<c:forEach items="${resvStatus}" var="resvStatus">
<%-- 																							<%-- <option value="${resvStatus.code}">${resvStatus.reservationStatus()}</option> --%> 
																						<option value="${resvStatus.reservationStatus()}">${resvStatus.reservationStatus()}</option> 
																						</c:forEach>
																					</select>
																				</div>
																			</div>

																			<div class="search_div_con_list">

																				<label>Reserv Date:</label>
																				<md-input-container
																					class="md-input-container md-datepicker-floating-label">
																				<md-datepicker
																					ng-model="search.resvDate.searchValue"
																					md-max-date="hotelDate" md-open-on-focus></md-datepicker>
																				</md-input-container>
																			</div>
																			<div class="search_div_con_list">
																				<label>Arrival Date:</label>
																				<md-input-container
																					class="md-input-container md-datepicker-floating-label"
																					id="chkoutwid"> <md-datepicker
																					ng-model="search.arrDate.searchValue"
																					md-min-date="search.resvDate.searchValue"
																					md-open-on-focus></md-datepicker> </md-input-container>
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
											<div class="form-group col-md-2">
												<select class="form-control m-bot15"
													ng-model="sort.sortColumn" ng-change="sortColumnChange()"
													id="reservation_list_sort_ascdsc">
													<option value="resv_no">Reservation Number</option>
													<option value="resv_date">Reservation Date</option>
													<option value="arr_date">Arrival Date</option>
													<option value="reserved_by">Reserved By</option>
													<option value="resv_status_xlt">Reservation Status</option>
												</select>
											</div>
											<div class="col-md-1">
												<button type="button" class="btn btn-primary list_sort"
													ng-click="sortFun();">
													<i class="fa fa-sort-amount-desc" aria-hidden="true"></i>
												</button>
											</div>
											<div class="col-sm-2 room-list_main_div">
												<div class="room_list_btn room_list_button">
													<md-button class="md-raised md-primary"
														ng-click="loadRoomDetails()">Room List</md-button>
												</div>
											</div>
											<div class="col-md-2" style="text-align: right;">
												<div class="form-inline">

													<%--  ${cp_isCanAdd}  --%>

													<c:if test="${cp_isCanAdd}">
														<div class="form-group tab_add_btn">
															<div class="form-group tab_add_btn newreservation">
																<md-button class="md-raised md-warn" ng-click="add()">
																<spring:message code="reservation.label.add" /></md-button>
															</div>
														</div>
													</c:if>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="adv-table editable-table">
											<table class="table table-striped table-hover table-bordered">
												<thead>
													<tr>
														<th>Resv#</th>
														<th>Arrival</th>
														<th>Nights</th>
														<th>Rooms</th>
														<th style="width: 20%; max-width: 270px;">Resv. By</th>
														<th style="width: 20%; max-width: 270px;">Resv. For</th>
														<th>Status</th>
														<th>Resv. On</th>
														<th>Deposit</th>
														<th>Days Left</th>
														<th width="35">Task</th>
														<!-- <th>CONTACT</th>
													<th>TASKS</th> -->
													</tr>
												</thead>
												<tbody>
													<tr ng-repeat="resv in resvList.card">
														<td class="resvno">
															<div class="resv-txt">
																<a
																	href="../reservation_test/reservationEdit?reservationNo={{resv.resvNo}}">{{resv.resvNo}}</a>
															</div>
														</td>
														<td>
															<div class="resv-txt">
															
																{{formatDate(resv.arrivalDate) | date:dateFormat}} {{formatDate(resv.arrivalDate) | date:timeFormat}} 
																

															</div>
														</td>
														<td>
															<div class="resv-txt">{{resv.numNights}}</div>
														</td>
														<td>
															<div class="resv-txt">{{resv.numRooms}}</div>
														</td>
														<td>
															<div class="resv-txt" style="max-width: 270px;">{{resv.resvFor}}</div>
														</td>
														<td>
															<div class="resv-txt" style="max-width: 270px;">{{resv.reservedBy}}</div>
														</td>
														<td>
															<div class="resv-txt txt-status {{resv.statusClass}}">{{resv.resvStatusXlt}}</div>
															<div
																ng-if="resv.statusClass!='res_stat-cancelled' && resv.statusClass!='res_stat-noshow'">
																<div ng-repeat="resvStatusNew in resvStatusCheck">
																	<div ng-if="resvStatusNew==resv.resvNo">
																		<a href="#" ng-click="resvStatus(resv.resvNo)">Details</a>
																		<span ng-if="resvStatusNew==resv.resvNo"><i
																			class="fa fa-star" id="star"></i></span>
																	</div>
																</div>
															</div>
															<div
																ng-if="resv.corporateName != null && resv.corporateName != ''">
																<div class="resv-txt">{{resv.corporateName}}</div>
															</div>
														</td>
														<td>
															<div class="resv-txt">{{resv.resvDate |
																date:dateFormat}}</div>
														</td>
														<td class="td_button nmrc_rgt_algn">
															<div class="resv-txt resv-txt-btm">{{resv.folioBalance}}</div>
														</td>
														<td class="nmrc_cntr_algn">

															<div class="resv-txt resv-txt-btm">{{resv.daysLeft}}</div>
														</td>
														<!-- <td class="nmrc_cntr_algn">
														<div class="btn-group pull-right open">
															<button type="button" class="btn dropdown-toggle"
																data-toggle="dropdown" aria-expanded="true"
																ng-click="determineDropDirection()">
																Click<span class="caret"></span>
															</button>
															<ul class="dropdown-menu dropdown-menu12 pull-right"
																role="menu" style="">
																<li>First</li>
																<li>Second</li>
																<li>Third</li>
																<li>fourth</li>
																<li>Fifth</li>
															</ul>
														</div>
														<div class="dropdown">
															<button class="dropbtn"
																ng-mouseover="determineDropDirection()">
																<i class="fa fa-bars" aria-hidden="true"></i>
															</button>
															<div
																class="dropdown-content dropdown_menu_tbl dropdown-menu12"
																id="drp_dwn_menu_tble_lst">
																
																 <a
																	href="../reservation/resvConfirm?reservationId={{resv.resvNo}}"><span
																	class="menu_icon"><i
																		class="fa fa-check-circle-o" aria-hidden="true"></i></span>Confirm</a>
																 <a
																	href="../checkIn/checkInEdit?resvId={{resv.resvNo}}"
																	class="drpdwn_border"><span class="menu_icon"><i
																		class="fa fa-key" aria-hidden="true"></i></span>Check In</a>
																<a
																	href="../reservation_test/reservationNoShow?reservationNo={{resv.resvNo}}"><span
																	class="menu_icon"><i
																		class="fa fa-question-circle" aria-hidden="true"></i></span>No
																	Show</a>
																 <a
																	href="../reservation_test/resvCancellation?resrvId={{resv.resvNo}}"><span
																	class="menu_icon"><i
																		class="fa fa-times-circle-o" aria-hidden="true"></i></span>Cancel</a>
																 <a
																	href="../deposit/depositEdit?reservationId={{resv.resvNo}}"
																	class="drpdwn_border"><span class="menu_icon"><i
																		class="fa fa-stack-exchange" aria-hidden="true"></i></span>Deposit</a>
																							
																<a href="#" ng-click="communication(resv.resvNo);"
																	class="drpdwn_border"><span class="menu_icon"><i
																		class="fa fa-phone" aria-hidden="true"></i></span>Contact</a> 
																
																<a ng-click="loadPickup(resv.resvNo)"
																	data-toggle="modal" data-target="#pickupModal"
																	class="drpdwn_border"><span class="menu_icon"><i
																		class="fa fa-car" aria-hidden="true"></i></span>Pick Up</a> 
																		
															</div>
														</div>
													</td> -->
														<!-- <td class="td_button"><a href="#"
														ng-click="communication(resv.resvNo);"
														class="btn-lg buttonstyle"> <i class="fa fa-phone"
															aria-hidden="true"></i>
													</a></td>-->
														<td class="nmrc_cntr_algn"><a href="#"
															ng-click="tools(resv.resvNo);"
															class="btn-lg buttonstyle tble_btn_new"> <i
																class="fa fa-tasks" aria-hidden="true"></i>
														</a> <!---<select class="tble_btn_new_select">
    <option>Apples</option>
    <option selected>Pineapples</option>
    <option>Chocklate</option>
    <option>Pancakes</option>
</select>---></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>

								</div>
								<div class="panel-body footer_div">
									<div class="limit_drop col-md-3">
										<div class="list_info">Showing {{totalItems}} of
											{{bigTotalItems}} Items</div>
										<select ng-model="pagination.limit"
											class="selectpicker form-control" ng-change="loadDataList()">
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
						</div>
					</div>
					<div aria-hidden="true" aria-labelledby="myModalLabel"
						role="dialog" tabindex="-1" id="roomsmyModal" class="modal fade">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div class="modal-header">
									<button aria-hidden="true" data-dismiss="modal" class="close"
										type="button">X</button>
									<h4 class="modal-title">ROOMS</h4>
								</div>
								<div class="modal-body">
									<div class="room_sub_hdr row">
										<div class="room_info occ_stat col-md-6">
											<span class="dtl_span">OCCUPIED</span> <span
												class="color_span occupied"></span> <span class="dtl_span">VACANT</span>
											<span class="color_span vacant"></span> <span
												class="dtl_span">OUT OF INV</span> <span
												class="color_span outOfInv"></span>
										</div>

										<div class="room_info hk_stat col-md-6">
											<div class="hk_status_label col-md-2 col-sm-2 col-xs-2">
												<i class="fa fa-bed" aria-hidden="true"></i> HK
											</div>
											<div class="hk_status_colors col-md-10 col-sm-10 col-xs-10">
												<span class="dtl_span">DIRTY</span> <span
													class="color_span dirty"></span> <span class="dtl_span">CLEAN</span>
												<span class="color_span cleaned"></span> <span
													class="dtl_span">CLEANING</span> <span
													class="color_span cleaning"></span>
											</div>
										</div>
									</div>

									<div class="grid_div" style="overflow-y: auto;">
										<md-grid-list md-cols="1" md-cols-sm="2" md-cols-md="3"
											md-cols-gt-md="6" md-row-height-gt-md="1:1"
											md-row-height="4:2" md-gutter="8px" md-gutter-gt-sm="4px">
										<md-grid-tile ng-repeat="room in roomDtlList"
											md-rowspan="{{room.tile.span.row}}"
											md-colspan="{{room.tile.span.col}}" md-colspan-sm="1"
											md-colspan-xs="1"
											ng-style="{'background': room.tile.background}"
											ng-class="room.tile.background"> <md-grid-tile-header>
										<div class="tile_room_type_code">
											<div class="room_grid_header_left">{{room.roomTypeCode}}</div>
											<div
												class="room_grid_header_right {{room.hkStatus==1?'dirty':''}} {{room.hkStatus==2?'cleaning':''}} {{room.hkStatus==3?'cleaned':''}}">
												<i class="fa fa-bed" aria-hidden="true"></i>
											</div>
										</div>
										</md-grid-tile-header>
										<div class="tile_details">{{room.roomNumber}}</div>
										<md-grid-tile-footer>
										<div class="tile_footer_edit"
											ng-if="room.hkStatus==1 && room.checkinStatus==7">
											<span class="room_grid_footer_label">Last Checkout</span><span
												class="room_grid_footer_desc">{{room.actualDepartDate
												| date:'yyyy-MM-dd'}}</span>
										</div>
										<div class="tile_footer_edit" ng-if="room.occStatus==1">
											<span class="room_grid_footer_label">CheckIn Date</span><span
												class="room_grid_footer_desc">{{room.arrivalDate |
												date:'yyyy-MM-dd'}}</span>
										</div>
										<div class="tile_footer_edit"
											ng-if="room.hkStatus!=1 && room.occStatus==2">VACANT</div>
										
											<div class="tile_footer_edit"
												ng-if="room.hkStatus!=1 && room.resvStatus==1 && room.occStatus==2">RESERVED</div>
										</md-grid-tile-footer> </md-grid-tile> </md-grid-list>
									</div>

								</div>
								<div class="modal-footer">
									<button id="cancelExpDepartBtn" type="button"
										class="btn btn-default rbtnClose"
										ng-click="cancelRoomPopUp();">
										<spring:message code="pms.btn.cancel" />
									</button>

								</div>
							</div>
						</div>
					</div>
				</section>
			</div>
		</section>
	</section>
	<div aria-hidden="true" aria-labelledby="pickupModal" role="dialog"
		tabindex="-1" id="pickupModal" class="modal ">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Pickup Details</h4>
				</div>
				<div class="modal-body">
					<div class="panel-body " id="cutoffdateTxt">
						<div class="form-horizontal tasi-form">
							<form name="myForm">

								<div class="form-group">

									<div class="col-md-3 col-xs-11">
										<label class="control-label"> Is pickup needed </label>
									</div>
									<div class="col-md-2 col-xs-11">
										<input id="pickupNeeded" name="pickupNeeded"
											ng-click="pickupNeededChange()" ng-model="pickupNeeded"
											ng-checked="pickupNeeded" class="checkbox" type="checkbox"
											value="true"> <label for="pickupNeeded"></label>
									</div>
								</div>

								<div id="pickup_div">
									<div class="form-group">
										<div class="col-md-6 col-xs-11">
											<label class="control-label"> Pickup Date</label> <br>
											<md-datepicker ng-model="pickupDate" id="pickupDate"
												md-placeholder="Enter date" md-min-date="arrivalNewMinDate"
												ng-required="true" md-max-date="departDate"></md-datepicker>
										</div>

										<div class="col-md-6 col-xs-11">
											<label class="control-label"> Pickup Time</label> <br>
											<div class='input-group date' id='pickup_time'>
												<input type='text' class="form-control"
													ng-model="pickupTime" id="pickup_time_txt" /> <span
													class="input-group-addon"> <span
													class="glyphicon glyphicon-time"></span>
												</span>
											</div>
										</div>


									</div>


									<div class="form-group">
										<div class="col-md-6 col-xs-11">
											<label class="control-label"> Pickup location</label> <br>

											<input id="pickup_location" name="pickup_location"
												class="form-control form-control-inline input-medium validator"
												size="10" ng-model="pickupLocation" />
										</div>

										<div class="col-md-6 col-xs-11">
											<label class="control-label">Pickup seats</label> <br> <input
												id="pickup_seats" name="pickup_seats" type="number"
												ng-model="pickupSeats" class="form-control validator">
										</div>
									</div>

									<div class="form-group">
										<div class="col-md-6 col-xs-11">
											<label class="control-label"> Pickup remarks</label> <br>

											<input id="pickup_remarks" name="pickup_remarks"
												class="form-control form-control-inline input-medium validator"
												size="10" ng-model="pickupRemarks" />
										</div>


									</div>

								</div>
							</form>
						</div>

						<!--  close  -->
					</div>



				</div>
				<div class="modal-footer">

					<button id="popUpsaveButn" type="button" class="btn btn-success"
						ng-click="updatePickUp()" ng-disabled="!myForm.$dirty">
						Update</button>

					<button aria-hidden="true" data-dismiss="modal"
						class="btn btn-default rbtnClose" type="button">Cancel</button>


				</div>
			</div>
		</div>
	</div>



	<!-- 	<div aria-hidden="true" aria-labelledby="depositModal" role="dialog"
		tabindex="-1" id="depositModal" class="modal ">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" 
						class="close"type="button">x
					</button>
					<h4 class="modal-title">
						New Deposit
					</h4>
				</div>
				<div class="modal-body">
					<div class="panel-body " id="depositeTxt">
						<div class="form-horizontal tasi-form">
							<form name="depositeForm">
								<div class="form-group">
									<div class="col-md-3 col-xs-11">
										<label class="control-label">Mode</label>
									</div>
									<div class="col-md-2 col-xs-11">
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div> -->




	<!-- edit arrival modal -->
	<div aria-hidden="true" aria-labelledby="editArrModal" role="dialog"
		tabindex="-1" id="editArrModal" class="modal ">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Edit Arrival</h4>
				</div>
				<div class="modal-body">
					<div class="panel-body " id="cutoffdateTxt">
						<div class="form-horizontal tasi-form">
							<form name="myForm">

								<div class="form-group">
									<label class="control-label col-sm-3">New Arrival Date
									</label>
									<div class="col-sm-4">
										<input type="text" class="form-control"
											ng-model="currentArrDate">
									</div>
								</div>
								<div class="form-group">

									<label class="control-label col-sm-3">New Arrival Date
									</label>
									<md-input-container class="calenderControls">
									<md-datepicker required ng-model="newArrDate"
										ng-change="changeNight()" md-min-date="initial.cutoffminDate"
										md-open-on-focus></md-datepicker> </md-input-container>
								</div>
							</form>
						</div>

						<!--  close  -->
					</div>



				</div>
				<div class="modal-footer">

					<button id="popUpsaveButn" type="button" class="btn btn-success"
						ng-click="updateArrDate()" ng-disabled="!myForm.$dirty">
						Apply</button>

					<button aria-hidden="true" data-dismiss="modal"
						class="btn btn-default rbtnClose" type="button">Cancel</button>


				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/hover-dropdown.js' />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/pms/js/angularctrl/reservation_tools.js" />"></script>
</html>
