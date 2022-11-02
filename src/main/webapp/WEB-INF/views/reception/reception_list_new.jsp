<jsp:directive.include file="../common/includes/page_directives.jsp" />
<!DOCTYPE html>
<html lang="en">
<c:set var="mainDivid" value="card_content_main_div" scope="request" />
<c:set var="mainDivclass" value="card_style" scope="request" />
<c:set var="formId" value="reservation_list" scope="request" />
<c:set var="formName" value="reservation_list" scope="request" />
<c:set var="moduleName" value="Reception" scope="request" />
<c:set var="backUrl" value="/dashboard" scope="request" />
<c:set var="backBtnStatusVal" value="1" scope="request" />

<c:set var="insertFunction" value="addCheckIn()" scope="request" />
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

<c:set var="dp_isCanView" scope="request"
	value="${(depPagePerObj.isCanView() && depPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="pay_isCanView" scope="request"
	value="${(payPagePerObj.isCanView() && payPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="post_isCanView" scope="request"
	value="${(postPagePerObj.isCanView() && postPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="chk_isCanView" scope="request"
	value="${(chkOutPagePerObj.isCanView() && chkOutPagePerObj.isIs_view_applicable())?true:false}" />
 
<script type="text/javascript">


	window.count = $
	{
		count
	}
</script>
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

	window.dp_isCanView = $
	{
		dp_isCanView
	}
	window.pay_isCanView = $
	{
		pay_isCanView
	}
	window.post_isCanView = $
	{
		post_isCanView
	}
	window.chk_isCanView = $
	{
		chk_isCanView
	}
</script>
<head>
<meta charset="ISO-8859-1">
</head>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/reception_list_new.js' />"></script>
<link
	href="<c:url value="/resources/common/components/reception_reservation_card/css/reservation_card.css" />"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/common/css/status_color_code.css'></c:url>"
	rel="stylesheet">
<link href="<c:url value="/resources/pms/css/reception_list.css" />"
	rel="stylesheet" />
<body class="full-width" id="reception" ng-app="pmsApp"
	ng-controller="receptionCtrl" ng-cloak>
	<input type="hidden" value="${count}" id="shiftCount">
	<input type="hidden" value="${dateFormat}" id="dateFormat" />
	<input type="hidden" value="${currencySymbol}" id="currencySymbol" />
	<input type="hidden" value="${hotelDate}" id="hotelDate" />
	<input type="hidden" value="${longStay}" id="longStayDays">
	<section id="container" class="">
		<c:import url="../menu/topMenu.jsp"></c:import>
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
												data-text="<spring:message code="pms.btn.backtop" />">
											</a> --%>
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
													<!-- serach_div start-->
													<div class="serach_div" id="simpleSearchTxt">
														<input type="text" id="commnSearchTxt"
															ng-model="search.comnSearchValue" class="comnSearchtxt"
															ng-if="!search.advSearch && selectedIndex==1" /> <input
															type="text" id="commnSearchTxt"
															ng-model="searchInHouse.comnSearchValue"
															class="comnSearchtxt"
															ng-keypress="searchOnEnter($event.keyCode)"
															ng-if="!searchInHouse.advSearch && selectedIndex==0" />
														<div ng-if="selectedIndex==1">
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
															<div ng-if="search.resvStatus.searchable"
																id="resv_status" class="inputTags ng-scope">
																reservationStatus : {{search.resvStatus.searchValue}}<span
																	class="inputclose"
																	ng-click="tagInputClose('resvStatus')" role="button"
																	tabindex="0">x</span>
															</div>
														</div>
														<div ng-if="selectedIndex==0">
															<div ng-if="searchInHouse.roomNumber.searchable"
																id="roomNum" class="inputTags ng-scope">
																roomNumber : {{searchInHouse.roomNumber.searchValue}}<span
																	class="inputclose"
																	ng-click="tagInputClose('roomNumber')" role="button"
																	tabindex="0">x</span>
															</div>
															<div ng-if="searchInHouse.customerName.searchable"
																id="custName" class="inputTags ng-scope">
																customerName :
																{{searchInHouse.customerName.searchValue}}<span
																	class="inputclose"
																	ng-click="tagInputClose('customerName')" role="button"
																	tabindex="0">x</span>
															</div>
															<div ng-if="searchInHouse.checkoutDate.searchable"
																id="checkOut_date" class="inputTags ng-scope">
																CheckOut Date : {{searchInHouse.checkoutDate.searchValue
																|date:'yyyy-MM-dd'}}<span class="inputclose"
																	ng-click="tagInputClose('checkOut_date')" role="button"
																	tabindex="0">x</span>
															</div>
														</div>
													</div>
													<!-- serach_div close-->

													<!-- search button div start -->
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
													<!-- search button div end -->

													<!-- search_fom_div start -->
													<div style="display: none;" id="search_fom_div"
														class="search_fom_div">
														<!-- main_search_fom_div start -->
														<div class="main_search_fom_div">

															<!-- main_search_fom_div_close_btn start -->
															<div class="main_search_fom_div_close_btn">
																<a id="search_form_close" href="#"></a>
															</div>
															<!-- main_search_fom_div_close_btn end -->
															<section class="panel">
																<div class="panel-body">
																	<form method="get"
																		class="advance_search_form form drp_dwn_form"
																		id="loginForm" action="">
																		<md-content layout-padding class="searchContainer">
																		<div id="reservation_search" ng-if="selectedIndex==1">
																			<div class="search_div_con_list">
																				<label>Reserved By:</label>
																				<md-input-container class="md-block">
																				<input ng-model="search.resvBy.searchValue">
																				</md-input-container>
																			</div>
																			<div class="search_div_con_list">
																				<label>Reservation Status:</label>
																				<md-input-container class="md-block">

																				<input ng-model="search.resvStatus.searchValue">
																				</md-input-container>
																			</div>
																			<div class="search_div_con_list">
																				<label>Reserv Date:</label>
																				<md-input-container
																					class="md-input-container md-datepicker-floating-label">
																				<md-datepicker
																					ng-model="search.resvDate.searchValue"
																					md-open-on-focus> </md-datepicker> </md-input-container>
																			</div>
																		</div>
																		<div id="inhouse_search" ng-if="selectedIndex==0">
																			<div class="search_div_con_list">
																				<label>Room Number :</label>
																				<md-input-container class="md-block">
																				<input type="text"
																					ng-model="searchInHouse.roomNumber.searchValue">
																				</md-input-container>
																			</div>
																			<div class="search_div_con_list">
																				<label>customerName :</label>
																				<md-input-container class="md-block">
																				<input
																					ng-model="searchInHouse.customerName.searchValue">
																				</md-input-container>
																			</div>
																			<div class="search_div_con_list">
																				<label>CheckoutDate:</label>
																				<md-input-container
																					class="md-input-container md-datepicker-floating-label"
																					id="chkoutwid"> <md-datepicker
																					ng-model="searchInHouse.checkoutDate.searchValue"
																					md-open-on-focus> </md-datepicker> </md-input-container>
																			</div>
																		</div>
																		</md-content>
																		<div class="advance_search_content_sub_div">
																			<div>
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
														<!-- main_search_fom_div end -->
													</div>
													<!-- search_fom_div end -->
												</div>
											</div>
											<div class="form-group col-md-2">
												<select class="form-control m-bot15"
													ng-model="sort.sortColumn" ng-change="sortColumnChange()"
													id="reservation_list_sort_ascdsc">
													<option value="checkin_no" ng-if="selectedIndex==0">Arrival
													</option>
													<option value="room_number" ng-if="selectedIndex==0">Room
														#</option>
													<option value="customer_name" ng-if="selectedIndex==0">Customer
														Name</option>
													<option value="resv_no" ng-if="selectedIndex==1">Reservation
														Number</option>
													<option value="resv_date" ng-if="selectedIndex==1">Reservation
														Date</option>
													<option value="reserved_by" ng-if="selectedIndex==1">Reserved
														By</option>
													<option value="resv_status_xlt" ng-if="selectedIndex==1">Reservation
														Status</option>
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
														ng-click="loadRoomDetails()">Room List </md-button>
												</div>
											</div>
											<div class="col-md-2" style="text-align: right;">
												<div class="form-inline">
													<c:if test="${cp_isCanAdd}">
														<div class="form-group tab_add_btn">
															<div class="form-group tab_add_btn">
																<md-button class="md-raised md-warn" ng-click="add()">
																<spring:message code="reception.label.add" /></md-button>
															</div>
														</div>
													</c:if>
												</div>
											</div>
										</div>
									</div>
									<div class="col-md-12 ">

										<ul class="nav nav-tabs tab_design_cmn" id="tabBackground">
											<li class="active"><a data-toggle="tab"
												ng-click="selectHouse()" href="#INHOUSE">IN HOUSE</a></li>
											<li><a data-toggle="tab" ng-click="selectExpected()"
												href="#EXPECTEDARRIVALS">EXPECTED ARRIVALS</a></li>
										</ul>
										<div class="tab-content">
											<div id="INHOUSE" class="tab-pane fade in active">
												<div id="dataTableDiv">
													<div class="cmn_tbl">
														<table
															class="table table-striped table-hover table-bordered">
															<thead>
																<tr>
																	<th class="roomBar">Room #</th>
																	<th class="nameBar">Name</th>
																	<th class="arrbar">Arr. Date</th>
																	<th style="width: 100px;">Arr. Time</th>
																	<th class="arrbar">Actual Dep. Date</th>
																	<th class="arrbar">Expected Dep. Date</th>
																	<th style="width: 120px;">Folio Balance</th>

																	<!-- <th ng-if="Erecp.corporateName!=null && recp.corporateName!=''"
															ng-disabled="recp.corporateName!=null && recp.corporateName!=''">
																Corporate</th> -->
																	<th style="width: 120px;" ng-hide="false">GRC</th>
																	<th class="icon_th">Edit</th>

																	<th class="icon_th">Deposit</th>

																	<th class="icon_th">Posting</th>
																	<th class="icon_th">Preview</th>
																	<th class="icon_th" style="width: 92px;">Check-Out</th>
																	 <!--  <th class="icon_th">Print Invoice</th>  -->
																	<th class="icon_th">Invoice Options</th>
																	<!-- <th class="icon_th">Invoice Mail</th>  -->

																</tr>
															</thead>
															<tbody>



																<tr ng-repeat=" recp in recpList.card">
																	<td
																		ng-class="{'condition_check_seven' : recp.checkinStatus === 7, 'condition_check_five' : recp.checkinStatus === 5 &&recp.stayedDays === -1,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<div ng-if="recp.checkinStatus === 5 && recp.stayedDays === -1">
																			<span class="recRoomNo">{{recp.roomNumber}}</span>
																		</div>
																		<div ng-if="recp.checkinStatus === 5 && recp.stayedDays === 1">
																			<span class="recRoomYello">{{recp.roomNumber}}</span>
																		</div>
																		<div ng-if="recp.checkinStatus === 7">
																			<span class="recRoomNored">
																				{{recp.roomNumber}}</span>
																		</div>
																	</td>
																	<td
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		{{recp.customerName }}</td>

																	<td
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		{{recp.arrDate | date : '${dateFormat}'}}</td>
																	<td
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		{{recp.arrTime}}</td>
																	<td
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		{{recp.actDepartDate | date :'${dateFormat}'}}</td>
																	<td
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		{{recp.departDate | date :'${dateFormat}'}}</td>
																	<td class="nmrc_rgt_algn"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		{{recp.folioBalance}}</td>
																	<!--  <td ng-if="recp.corporateName!=null && recp.corporateName!=''"
															ng-disabled="recp.corporateName!=null && recp.corporateName!=''">
																<div class="row">
																	<div class="resv-txt resv-txt-btm">
																		{{recp.corporateName}}
																	</div>
																</div>
															</td> -->

																	<!-- GRC -->
																	<td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}" ng-hide="false">
																		<!-- <div class="row"> -->
																		<button class="icon_button"
																			ng-disabled="recp.checkinStatus!= 5"
																			ng-click="printGrcForm(recp.checkinNo);" class="btn-lg buttonstyle">
																			<i class="fa fa-file-text-o" aria-hidden="true"></i>
																		</button>
																	</td>


																	<td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<div class="row">
																			<button class="icon_button"
																				ng-click="editCheckin(recp.checkinNo,recp.checkinStatus);"
																				class="btn-lg buttonstyle">
																				<i class="fa fa-pencil" aria-hidden="true"></i>
																			</button>
																		</div>
																	</td>

																	<td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<div class="row">
																			<button class="icon_button"
																				ng-disabled="recp.checkinStatus!= 5"
																				ng-click="depositreception(recp.checkinNo);"
																				class="btn-lg buttonstyle">
																				<i class="fa fa-database" aria-hidden="true"></i>
																			</button>
																		</div>
																	</td>

																	<td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<div class="row">
																			<button class="icon_button"
																				ng-disabled="recp.checkinStatus!= 5"
																				ng-click="posting(recp.checkinNo);">
																				<i class="fa fa-paperclip" aria-hidden="true"></i>
																			</button>
																		</div>
																	</td>
																	<!-- <td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<div class="row">
																			<button class=" icon_button"
																				ng-disabled="recp.checkinStatus!= 5"
																				ng-click="previewPrint(recp.folioNo);">
																				<i class="fa fa-file-text-o" aria-hidden="true"></i>
																			</button>
																		</div>
																	</td> -->
																	
																	<td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<div class="row">
																			<button class=" icon_button"
																				ng-disabled="recp.checkinStatus!= 5"
																				ng-click="previewPrintSeparate(recp.folioNo);">
																				<i class="fa fa-file-text-o" aria-hidden="true"></i>
																			</button>
																		</div>
																	</td>
																	 <td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<div class="row">
																			<button type="button" class=" icon_button"
																				ng-disabled="recp.checkinStatus!= 5"
																				ng-click="checkout(recp.folioBindNo);">
																				<i class="fa fa-key" aria-hidden="true"></i>
																			</button>
																		</div>
																	</td> 

																	<!-- <td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<div class="row">
																		<button class="icon_button"
																			ng-disabled="recp.checkinStatus!= 7"
																			ng-click="printOut(recp.folioNo);"
																			class="btn-lg buttonstyle">
																			<i class="fa fa-print" aria-hidden="true"></i>
																		</button>
																	</td> -->
																	
																	<td class="icon_td coloradd"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<!-- <div class="row"> -->
																		<button class="icon_button"
																			ng-disabled="recp.checkinStatus!= 7"
																			ng-click="printOutSeparate(recp.folioNo);"
																			class="btn-lg buttonstyle">
																			<i class="fa fa-print" aria-hidden="true"></i>
																		</button>
																	</td>
																	
																	<!-- invoice mail sending -->
                                                                 <!--   <td class="icon_td"
																		ng-class="{'check_status' : recp.checkinStatus === 7,'check_status_longStay':recp.checkinStatus === 5 && recp.stayedDays === 1}">
																		<button class="icon_button" 
																			ng-disabled="recp.checkinStatus!= 7"
																			ng-click="mailConfirm(recp.folioNo);"
																			class="btn-lg buttonstyle">
																			<i class="fa fa-envelope" aria-hidden="true"></i>
																		</button>
																	</td> -->

																</tr>

															</tbody>
														</table>

													</div>
												</div>
											</div>
											<div id="EXPECTEDARRIVALS" class="tab-pane fade">
												<div id="dataTableDiv">
													<div class="cmn_tbl">
														<table
															class="table table-striped table-hover table-bordered">
															<thead>
																<tr>
																	<th>Resv#</th>
																	<th>Arrival</th>
																	<th>Nights</th>
																	<th>Rooms</th>
																	<th class="tabResv">Resv. For</th>
																	<th class="tabResv">Resv. By</th>
																	<th>Status</th>
																	<th>Resv. On</th>
																	<th>Deposit</th>
																	<th>Task</th>
																</tr>
															</thead>
															<tbody>
																<tr ng-repeat="resv in resvList.card">
																	<td class="resvno">
																		<div class="resv-txt">
																			<a
																				href="../reservation_test/reservationEdit?reservationNo={{resv.resvNo}}">{{resv.resvNo}}
																			</a>
																		</div>
																	</td>
																	<td>
																		<div class="resv-txt">{{resv.arrivalDate |
																			date:dateFormat}}</div>
																	</td>
																	<td class="nmrc_cntr_algn">{{resv.numNights}} <!-- <div class="row">
																<div class="resv-txt">{{resv.numNights}}</div>
															</div> -->
																	</td>
																	<td class="nmrc_cntr_algn">{{resv.numRooms}} <!-- <div class="row">
																<div class="resv-txt">{{resv.numRooms}}</div>
															</div> -->
																	</td>
																	<td>
																		<div class="resv-txt" id="tabMaxWid">{{resv.resvFor}}</div>
																	</td>
																	<td>
																		<div class="resv-txt" id="tabMaxWid">{{resv.reservedBy}}</div>
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
																		</div> <!-- <div class="row"
															ng-if="resv.corporateName != null && resv.corporateName != ''">
															<div class="resv-txt">{{resv.corporateName}}</div>
														</div> -->
																	</td>
																	<td>
																		<div class="resv-txt">{{resv.resvDate |
																			date:dateFormat}}</div>
																	</td>
																	<!-- 	<td class="icon_button">
															<div class="row">
																<div class="resv-txt resv-txt-btm">{{resv.folioBalance}}</div>
															</div>
														</td> -->
																	<td class="nmrc_rgt_algn">{{resv.folioBalance}}</td>

																	<td class="nmrc_cntr_algn"><a href="#"
																		ng-click="tools(resv.resvNo);"
																		class="btn-lg buttonstyle"> <i class="fa fa-tasks"
																			aria-hidden="true"></i>
																	</a></td>
																</tr>
															</tbody>
														</table>
													</div>
												</div>
											</div>
										</div>






















										<!-- 	<div ng-cloak>
								<md-content>
									 <md-tabs md-dynamic-height md-selected="selectedIndex"
									 id="recpListTab" md-border-bottom>
										<md-tab label="IN-HOUSE">
											
										</md-tab>
									<md-tab label="EXPECTED ARRIVALS">
										<div class="adv-table editable-table">
											<table class="table table-striped table-hover table-bordered">
												<thead>
													<tr>
														<th>Resv#</th>
														<th>Arrival</th>
														<th>Nights</th>
														<th>Rooms</th>
														<th>Resv. For</th>
														<th>Resv. By</th>
														<th>Status</th>
														<th>Resv. On</th>
														<th>Deposit</th>
														<th>Task</th>
													</tr>
												</thead>
												<tbody>
													<tr ng-repeat="resv in resvList.card">
														<td>
															<div class="row">
																<div class="resv-txt">{{resv.resvNo}}</div>
															</div>
														</td>
														<td>
															<div class="row">
																<div class="resv-txt">{{resv.arrivalDate |
																date:dateFormat}}
																</div>
															</div>
														</td>
														<td class="nmrc_cntr_algn"> {{resv.numNights}}
															<div class="row">
																<div class="resv-txt">{{resv.numNights}}</div>
															</div>
														</td>
														<td class="nmrc_cntr_algn">{{resv.numRooms}}
															<div class="row">
																<div class="resv-txt">{{resv.numRooms}}</div>
															</div>
														</td>
														<td>
															<div class="row">
																<div class="resv-txt">{{resv.resvFor}}</div>
															</div>
														</td>
														<td>
															<div class="row">
																<div class="resv-txt">{{resv.reservedBy}}</div>
															</div>
														</td>
														<td>
														<div class="row">
															<div class="resv-txt txt-status {{resv.statusClass}}">{{resv.resvStatusXlt}}</div>
														</div>
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
														<div class="row"
															ng-if="resv.corporateName != null && resv.corporateName != ''">
															<div class="resv-txt">{{resv.corporateName}}</div>
														</div>
														</td>
														<td>
															<div class="row">
																<div class="resv-txt">{{resv.resvDate |
																	date:dateFormat}}</div>
															</div>
														</td>
														<td class="icon_button">
															<div class="row">
																<div class="resv-txt resv-txt-btm">{{resv.folioBalance}}</div>
															</div>
														</td>
														<td class="nmrc_rgt_algn">
																		{{resv.folioBalance}}
															</td>
															
														<td class="nmrc_cntr_algn">
															<a href="#" ng-click="tools(resv.resvNo);"
															class="btn-lg buttonstyle"> 
																<i class="fa fa-tasks" aria-hidden="true"></i>
															</a>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</md-tab>
									</md-tabs>
								</md-content>
							</div> -->
									</div>
								</div>
								<!-- </section>
			</div>
					page row
					</div>
					
					<div class="row">
						<div class="col-lg-12">
							<section class="panel"> -->



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






















								<div class="modal-body modal_content_div">

									<div class="modal-tp-view">



										<div class="hs_filter col-md-4">
											<div class="col-md-4 col-sm-4 col-xs-4">
												<md-checkbox ng-change="filterFunction()"
													ng-model="hk_filter.dirty" class="md-primary"
													aria-label="Checkbox 1">DIRTY</md-checkbox>
											</div>
											<div class="col-md-4 col-sm-4 col-xs-4">
												<md-checkbox ng-change="filterFunction()"
													ng-model="hk_filter.clean" class="md-primary"
													aria-label="Checkbox 1">CLEAN</md-checkbox>
											</div>
											<div class="col-md-4 col-sm-4 col-xs-4">
												<md-checkbox ng-change="filterFunction()"
													ng-model="hk_filter.cleaning" class="md-primary"
													aria-label="Checkbox 1">CLEANING</md-checkbox>
											</div>
										</div>


										<div class="col-md-8">
											<div class="roomType col-md-3">Room Type</div>
											<div class="roomOption col-md-4">
												<select class="form-control selectOption" ng-model="rmType"
													ng-change="loadRoom()">
													<option ng-repeat="type in roomTypesList"
														ng-value="type.id">{{type.name}}</option>
												</select>
											</div>
											<div class="col-md-1">Floor</div>
											<div class="floorOption col-md-4 ">
												<select class="form-control selectfloorOption"
													ng-model="floor" ng-change="loadRoom()">
													<option ng-repeat="type in floorList" value="{{type.id}}">{{type.name}}</option>
												</select>

											</div>

										</div>



									</div>

									<div class="room_sub_hdr row">
										<div class="room_info occ_stat col-md-6">
											<span class="dtl_span">OCCUPIED</span> <span
												class="color_span occupied"></span> <span class="dtl_span">VACANT</span>
											<span class="color_span vacant"></span> <span
												class="dtl_span">OUT OF INV</span> <span
												class="color_span outOfInv"></span>
										</div>
										<div class="room_info hk_stat col-md-6">
											<div class="hk_status_label col-md-3 col-sm-3 col-xs-3">
												<i class="fa fa-bed" aria-hidden="true"></i> HK
											</div>
											<div class="hk_status_colors col-md-9 col-sm-9 col-xs-9">
												<span class="dtl_span">DIRTY</span> <span
													class="color_span dirty"></span> <span class="dtl_span">CLEAN</span>
												<span class="color_span cleaned"></span> <span
													class="dtl_span">CLEANING</span> <span
													class="color_span cleaning"></span>
											</div>
										</div>
									</div>



























									<div class="grid_div grid_table_con" style="overflow-y: auto;">
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
	
	<!-- mail sending modal -->
	<!-- Modal -->
<div class="modal fade" id="mailConfirmModal" tabindex="-1" role="dialog" aria-labelledby="mailConfirmModalTitle"
  aria-hidden="true">

  <!-- Add .modal-dialog-centered to .modal-dialog to vertically center the modal -->
  <div class="modal-dialog modal-dialog-centered" role="document">


    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        ...
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Save changes</button>
      </div>
    </div>
  </div>
</div>
	<!-- end -->
	<jsp:directive.include file="../reception/confirm_print.jsp" />
	<jsp:directive.include file="../reception/confirm_print2.jsp" />
	<jsp:directive.include file="../reception/confirm_print3.jsp" />
</body>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/hover-dropdown.js' />"></script>
	<script type="text/javascript"
	src="<c:url value='/resources/common/js/download/download.js' />"></script>
</html>