<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Dashboard" scope="request" />
<c:set var="moduleName" value="Dashboard" scope="request" />
<c:set var="formId" value="Dashboard" scope="request" />
<c:set var="formName" value="Dashboard" scope="request" />
<c:set var="formAction" value="" scope="request" />
<c:set var="reservationUrl" value="/reservation_test/reservationList"
	scope="request" />
<c:set var="hkUrl" value="/housekeeping/houseKeepingList"
	scope="request"></c:set>
<c:set var="requestUrl" value="/requestCheckin/listaddon"
	scope="request"></c:set>
<c:set var="checkoutUrl" value="/reception/receptionList"
	scope="request"></c:set>
<c:set var="dataList" value="${} " />
<%-- <c:set var="customEditIncludeFile" value="../dashboard/dashboard_index_custom.jsp" scope="request" /> --%>
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
<c:set var="cp_isCanExport" scope="request"
	value="${(curPagePerObj.isCanExport() && curPagePerObj.isIs_export_applicable())?true:false}" />


<c:set var="rp_isCanView" scope="request"
	value="${(requestPerObj.isCanView() && requestPerObj.isIs_view_applicable())?true:false}" />
<c:set var="rp_isCanAdd" scope="request"
	value="${(requestPerObj.isCanAdd() && requestPerObj.isIs_add_applicable())?true:false}" />
<c:set var="rp_isCanEdit" scope="request"
	value="${(requestPerObj.isCanEdit() && requestPerObj.isIs_edit_applicable())?true:false}" />
<c:set var="rp_isCanDelete" scope="request"
	value="${(requestPerObj.isCanDelete() && requestPerObj.isIs_add_applicable())?true:false}" />
<c:set var="rp_isCanExecute" scope="request"
	value="${(requestPerObj.isCanExecute() && requestPerObj.isIs_execute_applicable())?true:false}" />
<c:set var="rp_isCanExport" scope="request"
	value="${(requestPerObj.isCanExport() && requestPerObj.isIs_export_applicable())?true:false}" />
<html ng-app="dashboardApp">
<head>
<!-- <link rel="shortcut icon"
	href="/resources/common/images/favicon_niko_logo.ico"> -->

<title>${moduleTitle}</title>
<c:import url="../common/includes/master_includes.jsp" />
<link rel="shortcut icon"
	href="resources/common/images/logos_${companyN}/favicon_niko_logo.ico">

<title>${moduleTitle}</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/components/dashboard/css/theme-default.css' />" />
</head>
<body class="full-width" id="dashboard" ng-controller="dashboardCtrl">
	<c:import url="../menu/topMenu.jsp" />
	<input type="hidden" value="${hotelDate}" id="hotelDate" />
	<!-- START PAGE CONTAINER -->
	<div class="page-container page-navigation-toggled page-container-wide">

		<!-- PAGE CONTENT -->
		<div class="page-content">

			<!-- PAGE CONTENT WRAPPER -->
			<div class="page-content-wrap">

				<!--  <button type="button" class="btn btn-warning mb-control" data-box="#message-box-warning">Warning</button> -->

				<!--Request processing modal -->
				<div class="message-box message-box animated fadeIn"
					id="process_box">
					<div class="mb-container">
						<div class="mb-middle">
							<div class="mb-title">
								<span class="fa fa-puzzle-piece"></span> Process Request
							</div>
							<div class="mb-content">
								<div class="proc_box_div">
									<span class="contacts-title">Room</span>
									<p>{{reqToProcess.room_number}}</p>
								</div>
								<div class="proc_box_div">
									<span class="contacts-title">Request</span>
									<p>{{reqToProcess.facility_name}}</p>
								</div>
								<div class="proc_box_div">
									<span class="contacts-title">Time</span>
									<p>{{reqToProcess.chreq_req_time |date:'h:mm:ss a'}}</p>
								</div>
								<div class="proc_box_div">
									<span class="contacts-title">Remarks</span>
									<p>{{reqToProcess.chreq_req_remarks}}</p>
								</div>
								<div class="proc_box_div">
									<span class="contacts-title">Payment</span>
									<p>{{reqToProcess.payment}}
										{{reqToProcess.facility_is_payable ? '('+
										reqToProcess.facility_amount+ ' ${currencySymbol} )': ''}}</p>
								</div>
								<div class="proc_box_div">
									<span class="contacts-title">Status</span>
									<p>{{reqToProcess.process_status ? 'Processed' :
										(reqToProcess.setAsInactive?'Inactive Today':'Not
										Processed')}}</p>
								</div>
								<div class="proc_box_div">
									<span class="contacts-title">Request Status</span>
									<p>{{reqToProcess.is_req_completed ? 'Completed' : 'Not
										Completed'}}</p>
								</div>
							</div>
							<div class="mb-footer">

								<div ng-if="reqToProcess.canProcess && ${rp_isCanExecute}">
									<label class="col-md-2 control-label">Remarks</label>
									<div class="col-md-4">
										<input type="text" class="form-control"
											placeholder="process remarks" ng-model="process.remarks" />
									</div>
								</div>
								<button ng-if="reqToProcess.canProcess && ${rp_isCanExecute}"
									class="btn btn-info btn-lg col-md-4" ng-click="processSave()">Process
									Request</button>
								<button
									class="btn btn-default btn-lg pull-right mb-control-close">Close</button>
							</div>
						</div>
					</div>
				</div>
				<!-- START WIDGETS -->
				<div class="row">
					<div class="col-md-3">


						<div class="widget widget-warning widget-item-icon">
							<div class="widget-item-left">
								<span class="fa fa-building"></span>
							</div>
							<div class="widget-data" ng-cloak>

								<div class="widget-title" ng-cloak>
									<c:out value="${companyName}"></c:out>
								</div>
								<div class="widget-subtitle" ng-cloak>
									<c:out value="${buildingName}" />
								</div>
								<div class="widget-subtitle" ng-cloak>
									<c:out value="${streetName} ${stateName}" />
								</div>
								<div class="widget-subtitle" ng-cloak>
									GST # :
									<c:out value="${gstNo}" />
								</div>

							</div>
						</div>
						<!-- END WIDGET SLIDER -->

					</div>

					<div class="col-md-3">

						<div class="widget widget-warning widget-item-icon">
							<div class="widget-item-left">
								<span class="fa fa-envelope"></span>
							</div>
							<div class="widget-data" ng-cloak>
								<div class="widget-int num-count" ng-cloak>{{counts.req_not_processed_count}}</div>
								<div class="widget-title" ng-cloak>Unprocessed Requests</div>
								<div class="widget-subtitle" ng-cloak>In your request box</div>
							</div>
						</div>

					</div>

					<div class="col-md-3">

						<!-- START WIDGET REGISTRED -->
						<div class="widget widget-warning widget-item-icon">
							<!-- 							onclick="location.href='pages-address-book.html';"
 -->
							<div class="widget-item-left">
								<span class="fa fa-home"></span>
							</div>
							<div class="widget-data" ng-cloak>
								<div class="widget-int num-count" ng-cloak>{{counts.inhouse_today}}</div>
								<div class="widget-title" ng-cloak>Rooms Engaged</div>
								<div class="widget-subtitle" ng-cloak>On your Hotel</div>
							</div>
						</div>
						<!-- END WIDGET REGISTRED -->

					</div>

					<div class="col-md-3">

						<!-- START WIDGET CLOCK -->
						<!-- <div class="widget widget-info widget-padding-sm">
 -->
						<div class="widget widget-warning widget-item-icon">
							<div class="widget-item-left">
								<span class="fa fa-clock-o"></span>
							</div>
							<div class="widget-data" ng-cloak>
								<div class="widget-int plugin-clock">00:00</div>
								<div class="widget-title plugin-date date">Loading...</div>
							</div>
						</div>
						<!-- END WIDGET CLOCK -->

					</div>

				</div>
				<!-- END WIDGETS -->

				<div class="col-md-8">

					<div class="row">


						<div class="col-md-12">
							<!-- START LAST 10 RESERVATION BLOCK -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="panel-title-box">
										<!-- <h3>Checkout</h3> -->
										<h3>
											<a href="<c:url value='${checkoutUrl}' />">Checkout</a>
										</h3>
										<span>Today's expected Checkout</span>
									</div>
								</div>

								<div class="panel-body panel-body-table lastResv">
									<div class="tab-content">
										<div
											class="list-group list-group-contacts border-bottom push-down-10">
											<div class="nothing_status" ng-if="departshow">No
												checkout today</div>
											<div
												class="list-group-item contacts_content_main contact_content_head"
												ng-if="departToday.length!= 0">
												<div class="checkout_contacts_content" ng-cloak>
													<span class="contacts-title">Name</span>
												</div>
												<div class="contacts_content" ng-cloak>
													<span class="contacts-title">Arrival Date</span>
												</div>
												<div class="contacts_content" ng-cloak>
													<span class="contacts-title">Room</span>
												</div>
												<div class="contacts_content" ng-cloak>
													<span class="contacts-title">Phone</span>
												</div>
												<div class="contacts_content" ng-cloak>
													<span class="contacts-title">Folio Balance</span>
												</div>
											</div>

											<div class="main_con_body">
												<div class="scrool_con_body">

													<a ng-repeat="depart in departToday"
													href="${rootPath}/reception/receptionList"
														class="list-group-item contacts_content_main">
														<div class="contacts_content start_col" ng-cloak>
															<p ng-if="depart.firstName!=null">{{depart.firstName}}
																{{depart.lastName}}</p>
															<p ng-if="depart.firstName==null"></p>
														</div>

														<div class="contacts_content" ng-cloak>
															<p ng-if="depart.arrivalDate != null"
																style="margin-left: 10px;">{{depart.arrivalDate |
																date:'dd-MM-yyyy'}}</p>
															<p ng-if="depart.arrivalDate == null"></p>
														</div>


														<div class="contacts_content" ng-cloak>
															<p ng-if="depart.roomName !=null"
																style="margin-left: 15px;">{{depart.roomName}}</p>
															<p ng-if="depart.roomName == null"></p>
														</div>
														<div class="contacts_content" ng-cloak>
															<p ng-if="depart.phone !=null">{{depart.phone}}</p>
															<p ng-if="depart.phone == null"></p>
														</div>
														<div class="contacts_content end_col" ng-cloak>
															<p ng-if="depart.folioBal !=null"
																style="text-align: right;">{{depart.folioBal}}</p>
															<p ng-if="depart.folioBal == null"></p>
														</div>
													</a>

												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- END USERS ACTIVITY BLOCK -->
						</div>


					</div>
					<div class="row">
						<div class="col-md-12">
							<!-- START LAST 10 RESERVATION BLOCK -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="panel-title-box">
										<!-- <h3>Reservations</h3> -->
										<h3>
											<a href="<c:url value='${reservationUrl}' />">Reservations</a>
										</h3>
										<!-- <span>Latest Reservations</span> -->
									</div>
								</div>
								<div class="tab_design_main_div">
									<ul class="nav nav-tabs req_nav_tab tab_design_cmn"
										id="homeBackground">
										<li class="active"><a data-toggle="tab"
											href="#today_resv">Today</a></li>
										<li><a data-toggle="tab" href="#last_ten_resv">Last
												10</a></li>

										<li><a data-toggle="tab" href="#cutoff_date"
											class="active">Cut Off date within 5 days</a></li>
										<li><a data-toggle="tab" href="#checkin">Check-In
												within 2 days</a></li>
									</ul>
									<div class="panel-body panel-body-table">
										<div class="tab-content">
											<div id="last_ten_resv">
												<div
													class="list-group list-group-contacts border-bottom push-down-10">
													<div class="nothing_status" ng-if="resvshow">No
														Reservations available</div>
													<div
														class="main_con_heading_div list-group-item contacts_content_main contact_content_head"
														ng-if="lastReservation.length!= 0">
														<div class="contacts_content start_col" ng-cloak>
															<span class="contacts-title">Resv By</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Reserved On</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Arrival Date</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Depart Date</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Rooms</span>
														</div>
														<div class="contacts_content end_col" ng-cloak>
															<span class="contacts-title">Status</span>
														</div>
													</div>
													<div class="main_con_body">
														<div class="scrool_con_body">
															<a ng-repeat="lastRsv in lastReservation"
															href="${rootPath}/reservation_test/tools?reservationNo={{lastRsv.resv_no}}"
																class="list-group-item contacts_content_main"> <!-- <div class="list-group-status status-away"></div>  -->
																<div class="contacts_content start_col" ng-cloak>
																	<!-- 															<span class="contacts-title">Resv By</span>
 -->
																	<p ng-if="lastRsv.reserved_by!=null">{{lastRsv.reserved_by}}</p>
																	<p ng-if="lastRsv.reserved_by==null"></p>
																</div>

																<div class="contacts_content" ng-cloak>
																	<!-- <span class="contacts-title">Reserved On</span> -->
																	<p>{{lastRsv.resv_date | date:'dd-MM-yyyy'}}</p>
																</div>
																<div class="contacts_content" ng-cloak>
																	<!-- <span class="contacts-title">Arrival Date</span> -->
																	<p>{{lastRsv.arr_date | date:'dd-MM-yyyy'}}</p>
																</div>

																<div class="contacts_content" ng-cloak>
																	<!-- <span class="contacts-title">Depart Date</span> -->
																	<p>{{lastRsv.depart_date | date:'dd-MM-yyyy'}}</p>
																</div>
																<div class="contacts_content" ng-cloak>

																	<p style="margin-left: 21px;">{{lastRsv.num_rooms}}</p>

																</div>
																<div class="contacts_content end_col" ng-cloak>
																	<p>{{lastRsv.resv_status}}</p>
																</div>
															</a>
														</div>
													</div>
												</div>
											</div>
											<div id="today_resv" class="active">
												<div
													class="list-group list-group-contacts border-bottom push-down-10">
													<div class="nothing_status" ng-if="resvtodayshow">No
														Reservations today</div>
													<div
														class="list-group-item contacts_content_main contact_content_head"
														ng-if="reservationToday.length!= 0">
														<div class="contacts_content start_col" ng-cloak>
															<span class="contacts-title">Resv By</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Arrival Date</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Depart Date</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Rooms</span>
														</div>
														<div class="contacts_content end-col" ng-cloak>
															<span class="contacts-title">Status</span>
														</div>
													</div>

													<div class="main_con_body">
														<div class="scrool_con_body">
															<a ng-repeat="todayRsv in reservationToday"
															href="${rootPath}/reservation_test/tools?reservationNo={{lastRsv.resv_no}}"
																class="list-group-item contacts_content_main"> <!-- <div class="list-group-status status-away"></div> -->
																<div class="contacts_content start_col" ng-cloak>
																	<p>{{todayRsv.reserved_by}}</p>
																</div>
																<div class="contacts_content" ng-cloak>
																	<p>{{todayRsv.arr_date | date:'dd-MM-yyyy'}}</p>
																</div>
																<div class="contacts_content" ng-cloak>
																	<p>{{todayRsv.depart_date | date:'dd-MM-yyyy'}}</p>
																</div>
																<div class="contacts_content" ng-cloak>
																	<p style="margin-left: 21px;">{{todayRsv.num_rooms}}</p>
																</div>
																<div class="contacts_content end-col" ng-cloak>
																	<!-- <span class="contacts-title">Status</span> -->
																	<p>{{todayRsv.resv_status}}</p>
																</div>
															</a>

														</div>
													</div>
												</div>
											</div>
											<div id="cutoff_date">
												<div
													class="list-group list-group-contacts border-bottom push-down-10">
													<div class="nothing_status" ng-if="cutoffshow">No
														Reservation available for confirmation</div>
													<div
														class="list-group-item contacts_content_main contact_content_head"
														ng-if="cutoffwithin5day.length!= 0">
														<div class="contacts_content start_col">
															<span class="contacts-title">Resv By</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Cut-Off Date</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Arrival Date</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Depart Date</span>
														</div>
														<div class="contacts_content" ng-cloak>
															<span class="contacts-title">Rooms</span>
														</div>
														<div class="contacts_content end_col" ng-cloak>
															<span class="contacts-title">Status</span>
														</div>
													</div>
													<div class="main_con_body">
														<div class="scrool_con_body">
															<a ng-repeat="cutoff in cutoffwithin5day"
															href="${rootPath}/reservation_test/tools?reservationNo={{cutoff.resv_no}}"
																class="list-group-item contacts_content_main"> <!-- <div class="list-group-status status-away"></div> -->
																<div class="contacts_content start_col">
																	<p>{{cutoff.reserved_by}}</p>
																</div>

																<div class="contacts_content" ng-cloak>
																	<!-- <span class="contacts-title">CutOff Date</span> -->
																	<p>{{cutoff.cut_off_date | date:'dd-MM-yyyy'}}</p>
																</div>
																<div class="contacts_content" ng-cloak>
																	<p>{{cutoff.arr_date | date:'dd-MM-yyyy'}}</p>
																</div>

																<div class="contacts_content" ng-cloak>
																	<p>{{cutoff.depart_date | date:'dd-MM-yyyy'}}</p>
																</div>
																<div class="contacts_content" ng-cloak>
																	<p style="margin-left: 25px;">{{cutoff.num_rooms}}</p>
																</div>
																<div class="contacts_content end_col" ng-cloak>
																	<p>{{cutoff.resv_status}}</p>
																</div>
															</a>
														</div>
													</div>
												</div>
											</div>
											<div id="checkin">
												<div
													class="list-group list-group-contacts border-bottom push-down-10">
													<div class="nothing_status" ng-if="checkinshow">No
														check-in available for 2 days</div>
													<div
														class="list-group-item contacts_content_main contact_content_head"
														ng-if="checkinwithin2days.length!= 0">
														<div class="contacts_content start_col">
															<span class="contacts-title" ng-cloak>Resv By</span>
														</div>
														<div class="contacts_content">
															<span class="contacts-title" ng-cloak>Arrival Date</span>
														</div>
														<div class="contacts_content">
															<span class="contacts-title" ng-cloak>Depart Date</span>
														</div>
														<div class="contacts_content">
															<span class="contacts-title" ng-cloak>Rooms</span>
														</div>
														<div class="contacts_content end_col">
															<span class="contacts-title" ng-cloak>Status</span>
														</div>
													</div>

													<div class="main_con_body">
														<div class="scrool_con_body">

															<a ng-repeat="checkin_data in checkinwithin2days"
															href="${rootPath}/reservation_test/tools?reservationNo={{checkin_data.resv_no}}"
																class="list-group-item contacts_content_main"> <!-- <div class="list-group-status status-away"></div> -->
																<div class="contacts_content start_col">
																	<!-- <span class="contacts-title" ng-cloak>Resv By</span> -->
																	<p ng-if="checkin_data.reserved_by!=null">{{checkin_data.reserved_by}}</p>
																	<p ng-if="checkin_data.reserved_by==null"></p>
																</div>
																<div class="contacts_content">
																	<!-- <span class="contacts-title" ng-cloak>Arrival Date</span> -->
																	<p>{{checkin_data.arr_date | date:'dd-MM-yyyy'}}</p>
																</div>
																<div class="contacts_content">
																	<!-- <span class="contacts-title" ng-cloak>Depart Date</span> -->
																	<p>{{checkin_data.depart_date | date:'dd-MM-yyyy'}}</p>
																</div>
																<div class="contacts_content">
																	<!-- <span class="contacts-title" ng-cloak>Rooms</span> -->
																	<p>{{checkin_data.num_rooms}}</p>
																</div>
																<div class="contacts_content end_col">
																	<!-- <span class="contacts-title" ng-cloak>Status</span> -->
																	<p>{{checkin_data.resv_status}}</p>
																</div>
															</a>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- END USERS ACTIVITY BLOCK -->
						</div>


					</div>

					<div class="row">
						<div class="col-md-12">

							<!-- START USERS ACTIVITY BLOCK -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="panel-title-box">
										<h3>Reservations</h3>
										<span>Confirmation vs Cancellation</span>
									</div>
									<ul class="panel-controls" style="margin-top: 2px;">
										<li></li>
									</ul>
								</div>
								<div class="panel-body padding-0">
									<div class="chart-holder" id="dashboard-bar-1"
										style="height: 200px;"></div>
								</div>
							</div>
							<!-- END USERS ACTIVITY BLOCK -->

						</div>

					</div>


					<div class="row">
						<div class="col-md-4">
							<!-- Reservation summary -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="panel-title-box">
										<h3>Check-In</h3>
										<span>Today Vs Yesterday</span>
									</div>
								</div>
								<div class="panel-body padding-0">
									<div class="chart-holder" id="dashboard-donut-checkinToday"
										style="height: 200px;"></div>
								</div>
							</div>


						</div>

						<div class="col-md-4">

							<!-- Reservation summary -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="panel-title-box">
										<h3>Check-In</h3>
										<span>Last 30 days Vs Previoust 30 days</span>
									</div>
								</div>
								<div class="panel-body padding-0">
									<div class="chart-holder"
										id="dashboard-donut-checkinlast30days" style="height: 200px;"></div>
								</div>
							</div>
							<!-- END PROJECTS BLOCK -->

						</div>
						<div class="col-md-4">

							<!-- Reservation summary -->
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="panel-title-box">
										<h3>Check Out</h3>
										<span>Today Vs Yesterday</span>
									</div>
								</div>
								<div class="panel-body padding-0">
									<div class="chart-holder" id="dashboard-donut-checkoutToday"
										style="height: 200px;"></div>
								</div>
							</div>
							<!-- END PROJECTS BLOCK -->

						</div>
					</div>






				</div>
				<div class="col-md-4">
					<div class="row">
						<!-- LIST GROUP WITH BADGES -->
						<div class="panel panel-default">
							<div class="panel-heading ui-draggable-handle">
								<!-- <h3 class="panel-title">gggg Today (for 1
									night)</h3> -->
								<h3>
									<a href="<c:url value='${hkUrl}' />">Room Availability</a> -
									Today <label>${totalAvailableRooms}</label>
					
							</h3>
							</div>
							<div class="panel-body" ng-cloak>
								<ul class="list-group border-bottom room_avail_list">
									<li class="list-group-item"
										ng-repeat="avail in counts.avail_room_list"><span
										class="rm_type"><b>{{avail.roomTypeCode}} </b></span><span
										class="badge {{$index%2==0 ? 'badge-info' : 'badge-info'}}">{{avail.availRoom}}</span></li>
								</ul>
							</div>
						</div>
						<!-- END LIST GROUP WITH BADGES -->
					</div>

					<div class="row">
						<div class="panel panel-default">
							<div class="panel-heading" ng-cloak>
								<h3>
									<a href="<c:url value='${hkUrl}' />">Room Status</a> - Today
								</h3>
								<!-- <h3 class="panel-title">Room Status - Today</h3> -->
								<label class="room_stat_label pull-right"
									style="margin-top: -30px;">Total Rooms:
									{{counts.total_rooms_in_inventory + counts.outofinventory}}</label>
							</div>
							<div class="panel-body" ng-cloak>
								<div id="chart-10" style="height: 250px;">
									<svg></svg>
								</div>
								<span class="list-group-item"><span
									class="fa fa-eye dues_icon"></span> Rooms expected to be
									occupied for night<span class="badge badge-danger">{{counts.blocked_rooms}}</span></span>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="panel panel-default">
							<div class="panel-heading">

								<h3 class="panel-title">
									<span class="glyphicon glyphicon-thumbs-down"></span> Pending
									task
									<!-- <small>...</small> -->
								</h3>
							</div>

							<div class="panel-body">
								<a href="${rootPath}/reservation_test/reservationList"
									class="list-group-item"><span
									class="fa fa-clock-o dues_icon"></span>&nbsp; Reservation
									Confirmation <span class="badge badge-danger" ng-cloak>{{counts.resv_confirmation_pending}}</span></a>
								<a href="${rootPath}/reception/receptionList" class="list-group-item"><span
									class="fa fa-clock-o dues_icon"></span>&nbsp;To Check-In Today<span
									class="badge badge-default" ng-cloak>{{counts.checkin_pending}}</span></a></a>
								<a href="${rootPath}/reception/receptionList" class="list-group-item"><span
									class="fa fa-clock-o dues_icon"></span>&nbsp; To Check Out
									Today<span class="badge badge-default" ng-cloak>{{counts.checkout_pending}}</span></a>
							</div>
						</div>

						<!-- END PROJECTS BLOCK -->

					</div>
					<div class="row">
						<div class="panel panel-default" ng-cloak>
							<div class="panel-body">
								<div class="panel-heading" ng-cloak>
									<div class="panel-title-box" ng-cloak>
										<!-- <h3>Requests</h3> -->
										<h3>
											<a href="<c:url value='${requestUrl}' />">Requests</a>
										</h3>
										<!-- <span>{{newAddonCount}} requests to serve</span> -->

									</div>

									<span class="badge badge-warning req_hdr"
										style="margin-right: 5px; margin-top: 3px;">{{newAddonCount}}</span>
									<a id="addon_refresh" class=" req_refresh"><span
										class="fa fa-refresh"></span></a>
								</div>
								<ul class="nav nav-tabs req_nav_tab">
									<li class="active"><a data-toggle="tab" href="#rq_today">Requests
											Today <span class="badge badge-danger req_hdr_today ">{{newAddonCountToday}}</span>
									</a></li>
									<li><a data-toggle="tab" href="#rq_tomorrow" ng-cloak>Requests
											Tomorrow <span class="badge badge-danger req_hdr_today "
											ng-cloak>{{newAddonCountTomorrow}}</span>
									</a></li>
								</ul>

								<div class="panel-body panel-body-table requests">

									<div class="tab-content">
										<div id="rq_today" class="active" ng-cloak>
											<div
												class="list-group list-group-contacts border-bottom push-down-10">
												<div
													class="list-group-item contacts_content_main contact_content_head">
													<!-- ng-if="{{newAddonCountToday}}!= 0"> -->
													<div class="addon_content" ng-cloak>
														<span class="contacts-title">Time</span>
													</div>
													<div class="addon_content start_col" ng-cloak>
														<span class="contacts-title">Request</span>
													</div>
													<div class="addon_content ac_room" ng-cloak>
														<span class="contacts-title">Room</span>
													</div>
													<div class="addon_content" ng-cloak>
														<span class="contacts-title">Payment</span>
													</div>
													<div class="addon_content end_col" ng-cloak>
														<span class="contacts-title">Status</span>
													</div>
												</div>

												<div class="main_con_body">
													<div class="scrool_con_body">

														<a ng-repeat="(key,addons) in addonNewListToday"
															ng-disabled="true"
															class="list-group-item contacts_content_main"> <!-- <div class="list-group-status status-away"></div> -->
															<div class="addon_content start_col" ng-cloak>
																<!-- <span class="contacts-title">Time</span> -->
																<p>{{addons.chreq_req_time |date:'h:mm:ss a'}}</p>
															</div>

															<div class="addon_content" ng-cloak>
																<!-- <span class="contacts-title">Request</span> -->
																<p>{{addons.facility_name}}</p>
															</div>
															<div class="addon_content ac_room" ng-cloak>
																<!-- <span class="contacts-title">Room</span> -->
																<p>{{addons.room_number}}</p>
															</div>
															<div class="addon_content" ng-cloak>
																<!-- <span class="contacts-title">Payment</span> -->
																<p>{{addons.payment}}</p>
															</div>
															<div class="addon_content" ng-cloak>
																<!-- <span class="contacts-title">Status</span> -->
																<p class="addon_status" ng-bind-html="addons.status"></p>
															</div>

														</a>
													</div>
												</div>
											</div>
										</div>

										<div id="rq_tomorrow">
											<div
												class="list-group list-group-contacts border-bottom push-down-10">
												<div
													class="list-group-item contacts_content_main contact_content_head">
													<!-- ng-if="{{newAddonCountTomorrow}}!= 0"> -->
													<div class="addon_content" ng-cloak>
														<span class="contacts-title">Time</span>
													</div>
													<div class="addon_content" ng-cloak>
														<span class="contacts-title">Request</span>
													</div>
													<div class="addon_content" ng-cloak>
														<span class="contacts-title">Room</span>
													</div>
													<div class="addon_content" ng-cloak>
														<span class="contacts-title">Payment</span>
													</div>
												</div>
												<div class="main_con_body">
													<div class="scrool_con_body">

														<a ng-repeat="addons in addonNewListTomorrow" href=""
															ng-disabled="true"
															class="list-group-item contacts_content_main"> <!-- <div class="list-group-status status-away"></div> -->
															<div class="addon_content" ng-cloak>
																<!-- <span class="contacts-title">Time</span> -->
																<p>{{addons.chreq_req_time |date:'h:mm:ss a'}}</p>
															</div>

															<div class="addon_content" ng-cloak>
																<!-- <span class="contacts-title">Request</span> -->
																<p>{{addons.facility_name}}</p>
															</div>
															<div class="addon_content" ng-cloak>
																<!-- <span class="contacts-title">Room</span> -->
																<p>{{addons.room_number}}</p>
															</div>
															<div class="addon_content" ng-cloak>
																<!-- <span class="contacts-title">Payment</span> -->
																<p>{{addons.payment}}</p>
															</div>
														</a>
													</div>
												</div>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>


				</div>

				<!-- test -->

				<!-- test end -->

				<div class="row">
					<div class="col-md-4" style="display: none">

						<!-- START PROJECTS BLOCK -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="panel-title-box">
									<h3>Channel Booking</h3>
									<span>Last 30 days</span>
								</div>
							</div>
							<div class="panel-body panel-body-table">

								<div class="table-responsive">
									<div class="progress">
										<div class="progress-bar progress-bar-success"
											role="progressbar" aria-valuenow="40" aria-valuemin="0"
											aria-valuemax="100" style="width: 80%">40%</div>
									</div>
									<div class="progress">
										<div class="progress-bar progress-bar-info" role="progressbar"
											aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
											style="width: 20%">20%</div>
									</div>
									<div class="progress">
										<div class="progress-bar progress-bar-warning"
											role="progressbar" aria-valuenow="60" aria-valuemin="0"
											aria-valuemax="100" style="width: 60%">60%</div>
									</div>
									<div class="progress">
										<div class="progress-bar progress-bar-danger"
											role="progressbar" aria-valuenow="80" aria-valuemin="0"
											aria-valuemax="100" style="width: 80%">80%</div>
									</div>

									<div class="progress">
										<div class="progress-bar progress-bar-colorful"
											role="progressbar" aria-valuenow="40" aria-valuemin="0"
											aria-valuemax="100" style="width: 40%">75%</div>
									</div>
								</div>

							</div>
						</div>
						<!-- END PROJECTS BLOCK -->

					</div>


					<div class="col-md-4" style="display: none">

						<!-- START SALES & EVENTS BLOCK -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="panel-title-box">
									<h3>Sales & Event</h3>
									<span>Event "Purchase Button"</span>
								</div>
								<ul class="panel-controls" style="margin-top: 2px;">
									<li><a href="#" class="panel-fullscreen"><span
											class="fa fa-expand"></span></a></li>
									<li><a href="#" class="panel-refresh"><span
											class="fa fa-refresh"></span></a></li>
									<li class="dropdown"><a href="#" class="dropdown-toggle"
										data-toggle="dropdown"><span class="fa fa-cog"></span></a>
										<ul class="dropdown-menu">
											<li><a href="#" class="panel-collapse"><span
													class="fa fa-angle-down"></span> Collapse</a></li>
											<li><a href="#" class="panel-remove"><span
													class="fa fa-times"></span> Remove</a></li>
										</ul></li>
								</ul>
							</div>
							<div class="panel-body padding-0">
								<div class="chart-holder" id="dashboard-line-1"
									style="height: 200px;"></div>
							</div>
						</div>
						<!-- END SALES & EVENTS BLOCK -->

					</div>
				</div>
				<div style="display: none">
					<!-- START DASHBOARD CHART -->
					<div class="chart-holder" id="dashboard-area-1"
						style="height: 200px;"></div>
					<div class="block-full-width"></div>
					<!-- END DASHBOARD CHART -->
				</div>
			</div>
			<!-- END PAGE CONTENT WRAPPER -->
		</div>
		<!-- END PAGE CONTENT -->
	</div>
	<!-- END PAGE CONTAINER -->

	<!-- MESSAGE BOX-->
	<!-- END MESSAGE BOX-->

	<!-- START SCRIPTS -->
	<!-- START PLUGINS -->
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/jquery/jquery.min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/jquery/jquery-ui.min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/bootstrap/bootstrap.min.js' />"></script>
	<!-- END PLUGINS -->

	<!-- START THIS PAGE PLUGINS-->
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/icheck/icheck.min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/mcustomscrollbar/jquery.mCustomScrollbar.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/scrolltotop/scrolltopcontrol.js' />"></script>

	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/morris/raphael-min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/morris/morris.min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/rickshaw/d3.v3.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/nvd3/lib/d3.v3.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/nvd3/nv.d3.min.js' />"></script>

	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/rickshaw/rickshaw.min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/jvectormap/jquery-jvectormap-world-mill-en.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/bootstrap/bootstrap-datepicker.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/owl/owl.carousel.min.js' />"></script>

	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/moment.min.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/daterangepicker/daterangepicker.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins/fullcalendar/fullcalendar.min.js' />"></script>
	<!-- END THIS PAGE PLUGINS-->



	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/plugins.js' />"></script>
	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/actions.js' />"></script>

	<script type="text/javascript"
		src="<c:url value='/resources/common/components/dashboard/js/dashboard.js' />"></script>
	<!-- END TEMPLATE -->
	<!-- END SCRIPTS -->
</body>
</html>