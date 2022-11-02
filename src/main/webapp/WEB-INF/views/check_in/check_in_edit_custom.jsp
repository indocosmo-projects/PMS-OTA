<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@ page import="com.indocosmo.pms.enumerator.ReservationStatus"%>
<input type="hidden" value="${dateFormat}" id="dateFormat">

<div class="row" id="divID">
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="checkin.label.summary" />
				</h1>
				<!-- <span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span> -->
			</header>
			<div class="panel-body">

				<div class="dtls summury">
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.reservation" />
							</div>
							<div class="col-md-12 s-body">${checkInSessionResvHdr.resvNo}</div>
							<input type="hidden" id="resvNo"
								value="${checkInSessionResvHdr.resvNo}" />
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.reservedDate" />
							</div>
							<div class="col-md-12 s-body">
								<fmt:formatDate value="${checkInSessionResvHdr.resvDate}"
									pattern="${dateFormat}" />
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.status" />
							</div>
							<div id="divNights" class="col-md-12 s-body">
								<c:choose>
									<c:when
										test="${checkInSessionResvHdr.status==ReservationStatus.CHECKIN.code}">
										<spring:message code="status.label.checkIn" />
									</c:when>
									<c:when
										test="${checkInSessionResvHdr.status==ReservationStatus.PARTCHECKIN.code}">
										<spring:message code="status.label.partCheckIn" />
									</c:when>
									<c:when
										test="${checkInSessionResvHdr.status==ReservationStatus.CONFIRMED.code}">
										<spring:message code="status.label.confirmed" />
									</c:when>
									<c:when
										test="${checkInSessionResvHdr.status==ReservationStatus.UNCONFIRMED.code}">
										<spring:message code="status.label.unconfirmed" />
									</c:when>
									<c:when
										test="${checkInSessionResvHdr.status==ReservationStatus.CHECKOUT.code}">
										<spring:message code="status.label.checkOut" />
									</c:when>
									<c:when
										test="${checkInSessionResvHdr.status==ReservationStatus.PARTCHECKOUT.code}">
										<spring:message code="status.label.partCheckOut" />
									</c:when>
									<c:when
										test="${checkInSessionResvHdr.status==ReservationStatus.CANCELLED.code}">
										<spring:message code="status.label.cancelled" />
									</c:when>
								</c:choose>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="nightAudit.label.expectedDeparts" />
							</div>
							<div id="divCorporateTA" class="col-md-12 s-body">
								<fmt:formatDate value="${checkInSessionResvHdr.maxDepartDate}"
									pattern="${dateFormat}" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<div class="btnbackshift" id="btnbackcheckIn">
				<%-- <a href="<c:url value='${backUrl}' />" class="btn btn-success pull-right backbutna">	
										   <i class="fa fa-arrow-left backbtntop"  > <small> <spring:message code="pms.btn.backtop" /></small></i> 
										 </a>	 --%>
				<a href="<c:url value='${backUrl}' />"
					class="ios-back-button shiftback"
					data-text="<spring:message code="pms.btn.backtop" />"></a>
				<button type="button" class="btn btn-warning backshift"
					style="display: none;"
					ng-click="cancelDeposit(${depositFrom},${reservationId });"
					ng-disabled="openshifBtnAction">
					<i class="fa fa-plus"></i>
				</button>


			</div>
			<%-- 	<header class="panel-heading module_caption">
				<h1>
					<spring:message code="checkin.label.roomsAndGuest" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span>
			</header> --%>
			<div class="panel-body">
				<div class="col-lg-12">
					<div class="md-padding guestDtls">
						<div layout="row">
							<div class="col-md-2">
								<div class="dtl_label">
									<spring:message code="reservation.label.reservedFor" />
								</div>
								<div class="resv-txt">${checkInSessionResvHdr.resvByFirstName}</div>
							</div>

							<div class="col-md-2">
								<div class="dtl_label">
									<spring:message code="checkin.label.reservedBy" />
								</div>
								<div class="resv-txt">${checkInSessionResvHdr.resvFor}</div>
							</div>
							<div class="col-md-3">
								<div class="dtl_label">
									<spring:message code="checkin.label.phone" />
								</div>
								<div class="resv-txt">${checkInSessionResvHdr.resvByPhone}</div>
							</div>
							<div class="col-md-3">
								<div class="dtl_label">
									<spring:message code="checkin.label.email" />
								</div>
								<div class="resv-txt">${checkInSessionResvHdr.resvByMail}</div>
							</div>
							<div class="col-md-2">
								<div class="dtl_label">
									<spring:message code="reservation.label.deposit.totalDeposits" />
								</div>
								<div class="resv-txt">${checkInSessionResvHdr.depositAmount}</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-lg-12 check_in_section">
					<md-content> <md-tabs md-dynamic-height
						md-border-bottom> <md-tab label="RESERVATION DETAILS">
					<md-content class="md-padding">
					<div class="check_in_section">
						<div class="col-md-6 col-sm-6">
							<md-grid-list md-cols="1" md-cols-sm="2" md-cols-md="3"
								md-cols-gt-md="6" md-row-height-gt-md="1:1" md-row-height="4:2"
								md-gutter="8px" md-gutter-gt-sm="4px"> <md-grid-tile
								ng-repeat="chin in checkinData" ng-if="chin.status!=2"
								md-rowspan="{{chin.tile.span.row}}"
								md-colspan="{{chin.tile.span.col}}" md-colspan-sm="1"
								md-colspan-xs="1"
								ng-style="{'background': chin.tile.background}"
								ng-class="chin.tile.background" class="rsvrtn_dtls"
							    ng-disabled="chin.status==5" ng-click="showDetails(chin,this)">
							<md-grid-tile-header>
							<div class="tile_room_type_code">
								<span>{{chin.room_type_code}}</span>
							</div>
							<div class="tile_room_number">
								<span>{{chin.room_number!=null?chin.room_number:"- -"}}</span>
							</div>
							</md-grid-tile-header>
							<div class="col-md-12" id="checkInDtls">
								<div class="row">
									<div class="col-md-2">
										<spring:message code="discount.label.ratePlan"></spring:message>
									</div>
									<div class="col-md-2 checkInGrid">{{chin.rate_code}}</div>
								</div>
								<div class="row" style="padding-top: 4px">
									<div class="col-md-2">
										<spring:message code="checkin.label.name"></spring:message>
									</div>
									<div class="col-md-2 checkInGrid">
										{{chin.first_name}}&nbsp; {{chin.last_name}}</div>
								</div>
								<div class="row" style="padding-top: 4px">
									<div class="col-md-2">
										<spring:message code="checkin.label.phone"></spring:message>
									</div>
									<div class="col-md-2 checkInGrid">{{chin.phone}}</div>
								</div>
							</div>
							<md-grid-tile-footer>
							<div class="tile_footer_edit">
								<md-checkbox ng-if="chin.status!=5&&chin.status!=7" dir="rtl"
									ng-model="selected[chin.resv_room_no]" aria-label="Checkbox 1"
									 ng-disabled="chin.status==5">Choose
								for Check-In</md-checkbox>
								<span class="res_stat-checkin" ng-if="chin.status==5">IN-HOUSE</span>
								<span class="res_stat-checkout" ng-if="chin.status==7">CHECK-OUT</span>
							</div>
							</md-grid-tile-footer> </md-grid-tile> </md-grid-list>
						</div>
						<div class="col-md-4 col-sm-4">
							<div class="room_rate_details" >

								<md-toolbar class="md-theme-light rate_sub_hdr">
								<h2 class="md-toolbar-tools" id="bgColour">
									<span>Room Details</span>
								</h2>
								</md-toolbar>
								<div class="show_info" ng-show="!showDetail">
									<span>click one Type room for details</span>
									
									<div class="row cust_data"
												ng-if="customerData.status!=5 && customerData.status!=7 && multipleType!=1">
												<div class="col-md-6 edit_checkin">
													<md-button class="md-warn"
														ng-click="editCheckin(customerData)" > <i
														class="fa fa-pencil-square" aria-hidden="true"></i> edit</md-button>
												</div>
											</div>
								</div>
								<div class="payInfo" ng-show="showDetail">

									<div layout="column">
										<md-card> <md-card-content>
										<div class="sumFinalRateDiv row">
											<div class="row cust_data">
												<div class="col-md-6 col-sm-6 col-xs-6">
													<label>Name</label>
												</div>
												<div class="col-md-6 col-sm-6 col-xs-6" id="worWrp">{{customerData.first_name}}&nbsp;
													{{customerData.last_name}}</div>
											</div>
											<div class="row cust_data">
												<div class="col-md-6 col-sm-6 col-xs-6">
													<label>Room Type</label>
												</div>
												<div class="col-md-6 col-sm-6 col-xs-6">{{customerData.room_type_code}}</div>
											</div>
											<div class="row cust_data">
												<div class="col-md-6 col-sm-6 col-xs-6">
													<label>Room Number</label>
												</div>
												<div class="col-md-6 col-sm-6 col-xs-6">{{customerData.room_number!=null?customerData.room_number:"-
													-"}}</div>
											</div>
											<div class="row cust_data"
												ng-if="customerData.status!=5 && customerData.status!=7">
												<div class="col-md-6 edit_checkin">
													<md-button class="md-warn"
														ng-click="editCheckin(customerData)"> <i
														class="fa fa-pencil-square" aria-hidden="true"></i> edit</md-button>
												</div>
											</div>
										</div>

										<div class="sumFinalRateDiv row">
											<md-toolbar layout="row" class="md-blue-3">
											<div class="md-toolbar-tools">
												<span>Rate Summary</span>
											</div>
											</md-toolbar>
											<md-list> <md-list-item class="md-1-line">
											<div class="md-list-item-text rateByRoomList">
												<div class="col-md-6">
													<label>Sub Total</label>
												</div>
												<div class="col-md-6" style="text-align: right;">{{rateSummary.subTotal}}
													${currencySymbol}</div>
											</div>
											<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
											<div class="md-list-item-text rateByRoomList">
												<div class="col-md-6">
													<label>Total Service Charge</label>
												</div>
												<div class="col-md-6" style="text-align: right;">{{rateSummary.totalSCharge}}
													${currencySymbol}</div>
											</div>
											<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line"
												ng-if="rateSum.discTotal!=0">
											<div class="md-list-item-text rateByRoomList">
												<div class="col-md-6">
													<label>Total Disc</label>
												</div>
												<div class="col-md-6" style="text-align: right;">{{rateSummary.discTotal}}
													${currencySymbol}</div>
											</div>
											<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
											<div class="md-list-item-text rateByRoomList">
												<div class="col-md-6">
													<label>Total Tax</label>
												</div>
												<div class="col-md-6" style="text-align: right;">
													{{rateSummary.taxTotal}} ${currencySymbol}</div>
											</div>
											<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
											<%-- <div class="md-list-item-text rateByRoomList">
													<div class="col-md-6">
														<label>Total Service Tax</label>
													</div>
													<div class="col-md-6">{{rateSummary.sTaxTotal}}
														${currencySymbol}</div>
												</div> --%> <!-- 												<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
 -->
											<div class="md-list-item-text rateByRoomList">
												<div class="col-md-6">
													<label>Total Cost</label>
												</div>
												<div class="col-md-6" style="text-align: right;">{{rateSummary.grantTotal}}
													${currencySymbol} (Incl.Txs)</div>
											</div></md-list>
										</div>

										</md-card-content> </md-card>
									</div>

								</div>
							</div>
						</div>
					</div>
					</md-content> </md-tab> </md-tabs> </md-content>
				</div>
			</div>
		</section>
	</div>
</div>

<!--popup div  for check in room details-->








<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="roomEditModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">

			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" ng-click=clodeModal()
								aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Customers</h4>
						</div>
						<div class="modal-body">
							<div class="table-responsive">
								<table class="table table-striped">
									<thead>
										<tr>
											<th></th>
											<th></th>
											<th>First name</th>
											<th>Last name</th>
											<th>Gender</th>
											<th>Email</th>
											<th>Phone No</th>
											<th>Address</th>
											<th>Country</th>
											<th>State</th>
											<th>ID</th>
											<th>Expiry</th>
											<th>VAT Account No</th>
										</tr>
									</thead>


									<tr ng-repeat="data in customerList">
										<td><input type="radio" name="inlineRadioOptions"
											id="inlineRadio1" ng-model="$parent.radioData"
											value="{{$index}}"></td>
										<td>{{data.salutation}}</td>
										<td>{{data.first_name}}</td>
										<td>{{data.last_name}}</td>
										<td>{{data.gender}}</td>
										<td>{{data.mail}}</td>
										<td>{{data.phone}}</td>
										<td>{{data.address}}</td>
										<td>{{data.nationality}}</td>
										<td>{{data.state}}</td>
										<td>{{data.passport_no}}</td>
										<td>{{data.passport_expiry_on}}</td>
										<td>{{data.gstno}}</td>
									</tr>
								</table>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								ng-click=clodeModal()>Close</button>
							<button type="button" class="btn btn-primary"
								ng-click="copyData(customerList[radioData])">Select
								Guest</button>
						</div>
					</div>
				</div>
			</div>



			<form name="userCheckinForm" ng-submit="userCheckinForm.$valid">
				<div class="modal-header">
					<h4 class="modal-title">EDIT-ROOM DETAILS</h4>
				</div>
				<div class="modal-body">
					<!-- modal data comes here -->
					<div id="checkIn_div">
						<md-content> <md-tabs md-dynamic-height
							md-border-bottom  md-selected="selectedIndex"> <md-tab label="Guest" ng-disabled="disbleforMultiple"> <md-content
							class="formTab" class="md-padding"> <!-- 		<div class="col-md-9">
				<div layout-gt-sm="row" class="input_div">
							<md-input-container>
									<select ng-model="roomDetails.salutation" ng-options="salute for salute in salutations" class="salute">
									</select>
								</md-input-container>
								<md-input-container>
								 <label>First name</label> <input ng-pattern="/^[A-Za-z ]+$/" required
									onclick="this.focus();this.select()"
									ng-model="roomDetails.firstName" maxlength="50">
								</md-input-container>
									<input type="button"  ng-if="roomDetails.firstName.length > 0" class="search_btn_room_edit btn reception"
													ng-click="loadCustomerData()"/>	
								<md-input-container class="md-block chInp" flex-gt-sm>
								<label>Last Name</label> <input 
									ng-model="roomDetails.lastName" maxlength="20"> </md-input-container>
									<md-input-container class="md-block chInp" flex-gt-sm>
								<label>Guest Name</label> <input 
									ng-model="roomDetails.guestName" maxlength="20"> </md-input-container>
							</div> 


	
							
							

							<div layout-gt-sm="row" class="input_div">
								<md-input-container class="md-block chInp" flex-gt-sm>
							
								<label>Email</label> <input ng-model="roomDetails.email" required
									ng-pattern="/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/"> </md-input-container>
									<button  class="search_btn_room_edit btn" ng-click="simpleSearchByMail()">Search</button>
								<md-input-container class="md-block chInp" flex-gt-sm>
								<label>Phone</label> <input ng-model="roomDetails.phone" required
									ng-pattern="/^[0-9]{10}$|^[0-9]{12}$/"></md-input-container>
									<input type="button"  ng-if="roomDetails.phone.length==10 || roomDetails.phone.length==12" class="search_button btn reception"
										style="height: 48px;"	ng-click="simpleSearchReception()"/>
							
								<md-input-container class="md-block chInp" flex-gt-sm>
								<label>Address</label> <input ng-model="roomDetails.address"
									required> </md-input-container>
							</div>
						
					
							
							
							<div layout-gt-sm="row" class="input_div">
								
								<md-input-container>
										 <md-select  ng-model="roomDetails.nationality"  placeholder="country" ng-change="loadState(roomDetails.nationality)"  required>
										    <md-option ng-repeat="country in countryList " ng-value="country.name" >{{country.name}}</md-option>
										 </md-select>
								</md-input-container>
			
								<md-input-container>
									<md-select  ng-model="roomDetails.state" placeholder="State" required>
										   <md-option ng-repeat="state in stateList" ng-value="state.name">{{state.name}}</md-option>
										    <md-option ng-repeat="state.name for state in stateList track by state.name" ng-value="state.name">{{state.name}}</md-option>
										 </md-select>
									</md-input-container>
								
								
								
								<md-input-container class="md-block chInp" flex-gt-sm>
								<label>Passport No.</label> <input
									ng-model="roomDetails.passportNo"></md-input-container>
								<md-input-container class="md-block chInp" ng-if="roomDetails.passportNo!=''" flex-gt-sm>
								<label>Expiry</label> <md-datepicker 
									ng-model="roomDetails.passportExpiryOn"></md-datepicker> </md-input-container>
									<md-input-container class="md-block chInp" ng-if="roomDetails.passportNo!='' && roomDetails.passportNo!=undefined"  flex-gt-sm>
								<label>Expiry</label>
								<md-datepicker required ng-model="roomDetails.passportExpiryOn" md-placeholder="Enter date" md-open-on-focus></md-datepicker>
							</div>
		
							
							
			
				
							
							<div layout="row">
								<md-input-container class="searchFormInput" flex="50">
								<label>ADULTS</label> <input required type="number" step="any"
									ng-model="roomDetails.numAdults" min="1" max="200"
									limit-to="200" /> </md-input-container>

								<md-input-container class="searchFormInput" flex="50">
								<label>CHILDRENS</label> <input type="number" step="any"
									ng-model="roomDetails.numChildren" min="0" max="200"
									limit-zero="200" /> </md-input-container>

								<md-input-container class="searchFormInput" flex="50">
								<label>INFANTS</label> <input type="number" step="any"
									ng-model="roomDetails.numInfants" min="0" max="200"
									limit-zero="200" /> </md-input-container>
							</div>

		
		
							
							
							<div layout-gt-sm="row" class="input_div">
								<md-input-container class="md-block chInp" flex-gt-sm>
								<label>Remarks</label> <input ng-model="roomDetails.remarks">
								</md-input-container>
								<md-input-container class="md-block gstn" flex-gt-sm>
								<label>GST No:</label> <input ng-model="roomDetails.gstno">
								</md-input-container>
								<md-input-container class="md-block chInp" flex-gt-sm>
								<md-radio-group required ng-model="roomDetails.gender">
								<div class="col-md-3">
									<md-radio-button value="Male" class="md-primary">Male</md-radio-button>
								</div>
								<div class="col-md-3 female">
									<md-radio-button value="Female">Female</md-radio-button>
								</div>
								</md-radio-group> </md-input-container>
							</div>
		
							
							
							<div layout-gt-sm="row" ng-if="is_old_customer()">
								<div class="col-sm-5">
								
								<label class="col-sm-4">Last Visit:</label> <label id="last_visit"></label>
							</div>
							<div class="col-sm-5">
								
								<label class="col-sm-4">No Visit:</label> <label id="no_visit"> </label> 	</div>
							</div>
						</div> -->



						<div class="col-md-9">
							<div cass="row">
								<div class="new_chk_in_lft_div_tbl">
									<div class="new_chk_in_lft_div_tbl_full_width_row">
										<div class="salutation_div">
											<label class="new_chk_in_lft_div_tbl_label">&nbsp;</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-12">
													<div class="row">
														<md-input-container> <select
															ng-model="roomDetails.salutation"
															class="md_slect_div_edit" id="sal_high"
															ng-options="salute for salute in salutations"
															class="salute">
														</select> </md-input-container>
													</div>
												</div>
											</div>
										</div>
										<div class="first_name">
											<label class="new_chk_in_lft_div_tbl_label">First
												name<span class="req_star_red">*</span>
											</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container> <input
															ng-pattern="/^[A-Za-z ]+$/" required type="text"
															onclick="this.focus();this.select()"
															ng-model="roomDetails.firstName" maxlength="50">
														</md-input-container>
														<input type="button"
															class="search_btn_room_edit btn reception"
															ng-if="roomDetails.firstName.length > 0"
															ng-click="loadCustomerData()" />
													</div>
												</div>
											</div>
										</div>
										<div class="last_name">
											<label class="new_chk_in_lft_div_tbl_label">Last name</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block chInp" flex-gt-sm>
														<input ng-model="roomDetails.lastName" maxlength="20">
														</md-input-container>
													</div>
												</div>
											</div>
										</div>
										<div class="new_guest_name">
											<label class="new_chk_in_lft_div_tbl_label">Guest
												name</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block chInp" flex-gt-sm>
														<input ng-model="roomDetails.guestName" maxlength="20">
														</md-input-container>
													</div>
												</div>
											</div>
										</div>

									</div>


									<div class="new_chk_in_lft_div_tbl_full_width_row">
										<div class="email_div">
											<label class="new_chk_in_lft_div_tbl_label">Email</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9" id="email_width">
													<div class="row">
														<md-input-container class="md-block chInp" flex-gt-sm>

														<input ng-model="roomDetails.email"  type="email"
															ng-pattern="/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/"
															class="form-control" title="eg.abc@gmail.com"> </md-input-container>
														<button class="search_btn_room_edit btn" id="email_search"
															ng-if="roomDetails.phone.length==10"
															ng-click="simpleSearchByMail()">Search</button>
													</div>
												</div>
											</div>
										</div>
										<div class="phone_div">
											<label class="new_chk_in_lft_div_tbl_label">Phone</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block chInp" flex-gt-sm>
														<input ng-model="roomDetails.phone" 
															maxlength="10" pattern="[0-9]{10}"></md-input-container>
														<input type="button" id="phone_search"
															ng-if="roomDetails.phone.length==10"
															class="search_button btn reception"
															ng-click="simpleSearchReception()" />
													</div>
												</div>
											</div>
										</div>
										<div class="new_address_div">
											<label class="new_chk_in_lft_div_tbl_label">Address</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block chInp" flex-gt-sm>
														<input ng-model="roomDetails.address" type="text" required>
														</md-input-container>
													</div>
												</div>
											</div>
										</div>


									</div>


									<div class="new_chk_in_lft_div_tbl_full_width_row">
										<div class="email_div">
											<label class="new_chk_in_lft_div_tbl_label">Country</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container> <md-select
															ng-model="roomDetails.nationality"
															class="md_slect_div_edit"
															ng-change="loadState(roomDetails.nationality)" required>
														<md-option ng-repeat="country in countryList "
															ng-value="country.name"> {{country.name}}</md-option> </md-select> <span
															id="country_error"
															ng-if="!roomDetails.nationality.length"> Please
															select country</span> </md-input-container>
													</div>
												</div>
											</div>
										</div>
										<div class="phone_div">
											<label class="new_chk_in_lft_div_tbl_label">State</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block chInp">
														<md-select ng-model="roomDetails.state"
															class="md_slect_div_edit" required> <md-option
															ng-repeat="state in stateList" ng-value="state.name">{{state.name}}</md-option>
														<md-option
															ng-repeat="state.name for state in stateList track by state.name"
															ng-value="state.name">{{state.name}}</md-option> </md-select> <span
															id="state_error" ng-if="!roomDetails.state.length">
															Please select state</span> </md-input-container>
													</div>
												</div>
											</div>
										</div>
										<div class="gst_number">
											<label class="new_chk_in_lft_div_tbl_label">GST
												Number</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block gstn" flex-gt-sm>
														<input ng-model="roomDetails.gstno"> </md-input-container>
													</div>
												</div>
											</div>
										</div>


									</div>




									<div class="new_chk_in_lft_div_tbl_full_width_row">

										<div class="adults_div">
											<label class="new_chk_in_lft_div_tbl_label">Adults</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-12">
													<div class="row">
														<md-input-container class="searchFormInput" flex="50">
														<input required type="number" step="any"
															ng-model="roomDetails.numAdults" min="1" max="200"
															limit-to="200" /> </md-input-container>

													</div>
												</div>
											</div>
										</div>
										<div class="children_div">
											<label class="new_chk_in_lft_div_tbl_label" id="childMar">Children</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-12">
													<div class="row">
														<md-input-container class="searchFormInput" flex="50">
														<input type="number" step="any"
															ng-model="roomDetails.numChildren" min="0" max="200"
															limit-zero="200" /> </md-input-container>
													</div>
												</div>
											</div>
										</div>
										<div class="Infant_div">
											<label class="new_chk_in_lft_div_tbl_label" id="infantMar">Infants</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-12">
													<div class="row">
														<md-input-container class="searchFormInput" flex="50">
														<input type="number" step="any"
															ng-model="roomDetails.numInfants" min="0" max="200"
															limit-zero="200" /> </md-input-container>
													</div>
												</div>
											</div>
										</div>
										<div class="new_gender_div">
											<label class="new_chk_in_lft_div_tbl_label">Gender</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-12">

													<md-input-container class="md-block chInp" flex-gt-sm>
													<md-radio-group required ng-model="roomDetails.gender">
													<div class="col-md-6">
														<md-radio-button value="Male" class="md-primary"
															ng-checked="true">Male</md-radio-button>
													</div>
													<div class="col-md-6">
														<md-radio-button value="Female">Female</md-radio-button>
													</div>
													</md-radio-group> </md-input-container>


												</div>
											</div>
										</div>


									</div>


									<div class="new_chk_in_lft_div_tbl_full_width_row">
										<div class="email_div">
											<label class="new_chk_in_lft_div_tbl_label">Remarks</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block chInp" flex-gt-sm>
														<input ng-model="roomDetails.remarks"> </md-input-container>
													</div>
												</div>
											</div>
										</div>
										<div class="phone_div">
											<label class="new_chk_in_lft_div_tbl_label">Passport
												No.</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block chInp" flex-gt-sm>
														<input ng-model="roomDetails.passportNo"> </md-input-container>
													</div>
												</div>
											</div>
										</div>
										<div class="new_expiry">
											<label class="new_chk_in_lft_div_tbl_label">Expiry</label>
											<div class="new_chk_in_lft_div_tbl_input">
												<div class="col-sm-9">
													<div class="row">
														<md-input-container class="md-block chInp" flex-gt-sm>

														<md-datepicker ng-model="roomDetails.passportExpiryOn">
														</md-datepicker> </md-input-container>

													</div>
												</div>
											</div>
										</div>


									</div>


								</div>
							</div>
						</div>
























						<!-- 					<div class="col-md-6 filesDiv">
							<div class="row camRow">
								<md-subheader class="md-primary">Take Photo</md-subheader>
								<section layout="row" layout-sm="column"
									layout-align="center center" layout-wrap>
									<div class="image_dip_div" ng-if="files.imageUploaded">
										<img src="{{files.imageSrc}}" width="205" height="160"
											alt="no image.. please capture" />
									</div>
									<canvas id="snapshot" width="100" height="100"></canvas>
									<md-button ng-click="openCaptureDiv()"
										class="md-raised md-primary">Capture </md-button>
								</section>
								<div class="captDiv" ng-if="divCapture">
									<div class="alert alert-error ng-scope" ng-show="webcamError"
										style="">
										<span>Webcam could not be started. please give access
											to it !!</span>
									</div>
									<webcam channel="channel" on-streaming="onSuccess()"
										on-error="onError(err)" on-stream="onStream(stream)"></webcam>
									<md-button class="md-raised md-warn" ng-click="makeSnapshot()">take
									picture <i class="fa fa-camera" aria-hidden="true"></i></md-button>
								</div>
							</div>
							<div class="row fileRow">
								<md-subheader class="md-primary">Upload Proof</md-subheader>
								<md-input-container class="md-block chInp facilInput" flex-gt-sm>
								<input file-model="idproof" type="file"> </md-input-container>
								<div class="preFile" ng-if="files.proofChosen">
									<label>Previous File:</label><span>{{files.proofName}}</span>
								</div>
							</div>
						</div>
						 --> </md-content> </md-tab> <md-tab label="Room" > <md-content
							class="md-padding roomTab">
						<div class="roomDiv">
							<div class="row roomDtlsDiv">
								<div class="col-md-12">
									<div class="col-sm-6 roomsDiv">
										<md-content style="max-height: 240px;">
										<section>
														<md-subheader class="md-primary">Rooms
											{{roomTypeCode}} <label class="asgnd_room_label">Assigned
												: {{roomDetails.roomNumber}}</label></md-subheader>
											<!-- ----------ROOM DETAILS TABLE---------- -->
											
											<table>
												<tr ng-repeat="available in availableRooms"
													ng-if="checkAssigned(available.number) &&  countnochecked==1"">
				
													<td><md-radio-group ng-model="roomDetails.roomNumber">
				
														<md-radio-button class="md-primary rcptn_nw_rad"
															ng-disabled="Statusroom(available)"
															aria-label="{{ available.name}}" value="{{available.number}}">
														</md-radio-button></td>
													<td ng-if="available.room_status=='Vacant'"><label>{{available.name}}</label></td>
													<td><span class="room_no_span"> No : {{
															available.number}} </span></td>
				
													<td ng-if="available.room_status=='Vacant'"><span
														class="room_no_span"> {{available.hk1_status}} </span></td>
													<td ng-if="available.room_status=='Vacant'"><span
														class="room_no_span">
															<button class="md-raised stat_chng_btn"
																ng-click="changeRoomStatus(available.id);available.hk1_status='CLEAN'"
																ng-show="available.hk1_status!='CLEAN'">Change
																Status</button>
													</span></td>
				
													<td ng-if="available.room_status!='Vacant'"><label>{{
															available.name}}</label></td>
													<td ng-if="available.room_status!='Vacant'"><span
														class="room_no_span">No : {{ available.number}} </span></td>
													<td ng-if="available.room_status!='Vacant'"><span
														class="room_no_span"> {{ available.room_status}} </span></td>
													</md-radio-group>
												</tr>
												
												<tr ng-repeat="available in availableRooms"
													ng-if="checkAssigned(available.number) &&  countnochecked>1"">
				
													<td><md-checkbox ng-disabled="Statusroom(available)" ng-change = "countRoomCheck(available.number)"ng-model="roomSelected[available.number]">{{facility.name}}</md-checkbox></td>
													<td ng-if="available.room_status=='Vacant'"><label>{{available.name}}</label></td>
													<td><span class="room_no_span"> No : {{
															available.number}} </span></td>
				
													<td ng-if="available.room_status=='Vacant'"><span
														class="room_no_span"> {{available.hk1_status}} </span></td>
													<td ng-if="available.room_status=='Vacant'"><span
														class="room_no_span">
															<button class="md-raised stat_chng_btn"
																ng-click="changeRoomStatus(available.id);available.hk1_status='CLEAN'"
																ng-show="available.hk1_status!='CLEAN'">Change
																Status</button>
													</span></td>
				
													<td ng-if="available.room_status!='Vacant'"><label>{{
															available.name}}</label></td>
													<td ng-if="available.room_status!='Vacant'"><span
														class="room_no_span">No : {{ available.number}} </span></td>
													<td ng-if="available.room_status!='Vacant'"><span
														class="room_no_span"> {{ available.room_status}} </span></td>
													
												</tr>
												
											</table>
											<!-- <md-list> <md-radio-group
												ng-model="roomDetails.roomNumber"> <md-list-item
												ng-repeat="available in availableRooms"
												ng-if="checkAssigned(available.number) &&  countnochecked==1">
												
												 <md-radio-button
												class="md-primary" ng-disabled="Statusroom(available)"
												aria-label="{{ available.name}}"
												value="{{available.number}}"></md-radio-button>
												

											<p ng-if="available.room_status=='Vacant'"
												style="margin-top: -9px;">

												<label>{{ available.name}}</label> <span
													class="room_no_span"> No : {{ available.number}} </span> <span
													class="room_no_span"> {{ available.hk1_status}} </span>
											</p>


											<p ng-if="available.room_status!='Vacant'">

												<label>{{ available.name}}</label> <span
													class="room_no_span"> No : {{ available.number}} </span> <span
													class="room_no_span"> {{ available.room_status}} </span>
											</p>

											</md-list-item> </md-radio-group> <md-list>
											 -->
								
										</section>
										</md-content>

										<div class="roomFeaturesDiv">
											<md-content style="height: 125px;">
											<section>
												<md-subheader class="md-primary">Features</md-subheader>
												<div ng-repeat="avail in availableRooms"
													ng-if="avail.number==roomDetails.roomNumber">
													<div class="col-sm-6"
														ng-repeat="ftr in avail.roomFeatureList ">
														<label><i class="fa fa-hand-o-right"
															aria-hidden="true"></i> {{ftr.feature}}</label>
													</div>
												</div>
											</section>
											</md-content>
										</div>
									</div>

									<%-- <div class="facilityList"
										ng-repeat="facility in availableFacilities"
										ng-if="facility.facilityType == ${fcltyTypes.code}">
										<div class="col-md-3 fcchkBox">
											<md-checkbox ng-model="facilitySelected[facility.id]">{{facility.name}}</md-checkbox>
										</div>
										<div class="row col-md-9">
											<div class="row">
												<div class="col-md-8">
												</div>
									 --%>
									
									
									
								</div>
							</div>

						</div>
						</md-content> </md-tab> <md-tab label="Facilities" ng-disabled="disbleforMultiple">
						<div class="facilityDiv">
							<md-content class="md-padding roomTab"> <md-content
								style="height: 300px;"> <c:forEach var="fcltyTypes"
								items="${facilityTypes}">
								<section>
									<md-subheader class="md-primary">${fcltyTypes.name}</md-subheader>
									<div class="facilityList"
										ng-repeat="facility in availableFacilities"
										ng-if="facility.facilityType == ${fcltyTypes.code}">
										<div class="col-md-3 fcchkBox">
											<md-checkbox ng-model="facilitySelected[facility.id]">{{facility.name}}</md-checkbox>
										</div>
										<div class="row col-md-9">
											<div class="row">
												<div class="col-md-8">
													<md-radio-group ng-model="facility.need"
														ng-disabled="!facilitySelected[facility.id]">
													<md-radio-button value="daily" class="md-primary">Daily</md-radio-button>
													<md-radio-button value="ondate"> On Date
													</md-radio-button> </md-radio-group>
													<md-datepicker ng-model="facility.needDate"
														ng-if="facility.need=='ondate'"
														ng-disabled="!facilitySelected[facility.id]"
														md-placeholder="Enter date"></md-datepicker>
												</div>
												<div class="col-md-3">
													<div uib-timepicker ng-model="facility.needTime"
														class="facility_timePick"
														ng-disabled="!facilitySelected[facility.id]"
														ng-change="changed()" hour-step="hstep"
														minute-step="mstep" show-meridian="ismeridian"></div>
												</div>
											</div>


											<div class="row">
												<div class="col-md-6">
													<md-input-container class="facilInput" flex="70">
													<label>Provider</label> <md-select
														ng-disabled="!facilitySelected[facility.id]"
														ng-model="facility.provider"> <md-option
														ng-repeat="pvdr in providers" value="{{pvdr.id}}">{{pvdr.name}}</md-option></md-select>
													</md-input-container>
												</div>
												<div class="col-md-6">
													<md-input-container class="md-block facilInput" flex-gt-sm>
													<label>Remarks</label> <input
														ng-disabled="!facilitySelected[facility.id]"
														ng-model="facility.remarks"> </md-input-container>
												</div>
											</div>
										</div>
										<md-divider ng-if="!$last"></md-divider>
									</div>
								</section>

							</c:forEach> </md-content> </md-content>
						</div>
						</md-tab> </md-tabs> </md-content>


					</div>
					<!-- modal data comes here -->
				</div>
				<div class="modal-footer ">
					<button id="popUpsaveButn" type="submit"
						class="button btn btn-success" ng-click="updateRoomData()">
						<spring:message code="pms.label.save" />
					</button>

					<button id="popUpbackButn" type="button"
						class="btn btn-default rbtnClose" data-dismiss="modal"
						ng-click="cancelPopUp()">
						<spring:message code="pms.btn.close" />
					</button>
				</div>
			</form>
		</div>
	</div>
</div>


<!-- popup div  for check in room details end-->

<div class="row">
	<div class="col-md-12">
		<section class="panel">
			<div class="panel-body">
				<div class="row footer_pagination_main_div">
					<div class="modal-footer align-btn-rgt ">
						<%-- 	<button id="backButn" class="btn btn-back btn-default"
							type="button">
							<i class="fa fa-arrow-left" aria-hidden="true"></i>
							<spring:message code="pms.btn.back"></spring:message>
						</button> --%>
						<c:if test="${cr_isCanAdd}">
							<button id="saveDataButn" ng-click="saveData()"
								class="btn btn-success" type="button"
								ng-disabled="checkinSubmit">
								<i class="fa fa-sign-in" aria-hidden="true"> </i>
								<spring:message code="checkin.button.label.checkinGuest"></spring:message>
							</button>
						</c:if>

					</div>
				</div>
			</div>
		</section>
	</div>
</div>

<!-- rate modal  -->

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="rateModal" class="modal fade ">
	<!-- modal data comes here -->
	<div id="content"></div>
	<!-- modal data comes here -->
</div>
<!-- rate modal  -->