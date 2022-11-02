<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@page import="com.indocosmo.pms.enumerator.common.MealPlans"%>
<c:set var="mealPlans" value="<%=MealPlans.values()%>"></c:set>
<div class="divid" ng-controller="reservationCtrl  as ctrl" ng-cloak>
	<div class="selectedRoomsStatus row" ng-show="showAssignedWidget">

		<div class="floatingWidgetDtls" data-slide-toggle="fBox"
			data-slide-toggle-duration="500">
			<uib-accordion close-others="oneAtATime">
			<div uib-accordion-group class="panel-default roomtypeNameHdr"
				ng-repeat="assignedType in AssignedRoomData" is-open="!status.open">
				<uib-accordion-heading>
				{{assignedType.roomTypeCode}} : Assigned - {{assignedType.total}} <i
					class="pull-right glyphicon"
					ng-class="{'glyphicon-chevron-right': status.open}"></i> </uib-accordion-heading>
				<div ng-repeat="assgd in assignedType.assigned">
					<div class="fw_code_hdr">{{assgd.rateCode}} : {{assgd.value}}</div>
					<label class="fw_label" ng-if="assgd.occ1!==undefined">S
						:{{assgd.occ1}}</label> <label class="fw_label"
						ng-if="assgd.occ2!==undefined">D :{{assgd.occ2}}</label> <label
						class="fw_label" ng-if="assgd.occ3!==undefined">T
						:{{assgd.occ3}}</label> <label class="fw_label"
						ng-if="assgd.occ4!==undefined">Q :{{assgd.occ4}}</label>
				</div>
			</div>
			</uib-accordion>
		</div>
		<input type="button" class="floatingButton" ng-click="fBox=!fBox"
			value="Rooms Pending : {{resv.hdr.numRooms-totalRoomsAssigned}}" />
	</div>
<input  type="button" value=" BACK " ng-click=" backPreviousTab()" class="mybtn">
	<md-content class="md-padding mainTabContent"> <md-tabs
		md-dynamic-height md-border-bottom md-selected="data.selectedIndex"
		md-align-tabs="{{data.bottom ? 'bottom' : 'top'}}"> <md-tab
		id="tab1" ng-disabled="data.firstLocked"> <md-tab-label>SEARCH</md-tab-label>
	<md-tab-body>
	<div class="col-lg-12 col-md-12 col-sm-12 search-div lft_mrg_zrw">
		<div
			class="col-lg-12 col-md-12 col-sm-12 xs-left-right-padding lft_mrg_zrw">
			<div
				class="col-lg-8 col-md-8 col-sm-12 xs-left-right-padding details_div lft_mrg_zrw">

				<div class="md-inline-form">
					<md-content layout-padding class="searchFormDiv lft_mrg_zrw"
						style="height: 350px;">
					<div>

						<div layout="row"
							class="new_rsvrn_srch_form date_div new_rsrvtion_tble_div">

							<table>
								<thead>
									<tr>
										<td class="tableLabel">Arrival date</td>
										<td class="tableLabel">Depart date</td>
										<td class="tableLabel">Nights</td>
										<!-- <td class="tableLabel">Cutoff date</td> -->
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>

											<div class="col-sm-12">

												<div class="input-group date" id="dateTimePickerArrival">
													<input type='text' ng-model="resv.hdr.minArrDate"
														class="form-control" ng-change="changeNight()" required
														id="datetimepicker" />
														
													<!-- <span class="input-group-addon"> <span
														class="glyphicon glyphicon-calendar"></span> -->
													</span>
													<!-- <mdc-datetime-picker required ng-model="resv.hdr.minArrDate"
														ng-change="changeNight()" class="form-control"
														md-min-date="initial.cutoffminDate" md-open-on-focus></mdc-datetime-picker> -->

												</div>
											</div>
										</td>
										<td>

											<div class="col-sm-12">
												<div class="input-group date" id="dateTimePickerDeparture">
													<input type='text' ng-model="resv.hdr.maxDepartDate"
														class="form-control" ng-change="changeNight()"
														id="datetimepickerDept" required
														min-date="initial.chOutminDate"
														max-date="initial.chOutmaxDate" />
													<!--  <span
														class="input-group-addon"> <span
														class="glyphicon glyphicon-calendar"></span> -->
													<!-- <md-datepicker ng-change="changeNight()"
														class="form-control" ng-model="resv.hdr.maxDepartDate"
														md-min-date="initial.chOutminDate"
														md-max-date="initial.chOutmaxDate" md-open-on-focus></md-datepicker> -->
												</div>
											</div>
										</td>
										<td><div class="col-sm-12">
												<input class="form-control" type="number" step="any"
													ng-model="resv.hdr.nights" ng-change="changeCheckout()"
													limit-to="${maxNight}" min="1" max="${maxNight}" required />
											</div></td>
									</tr>
								</tbody>
								<thead>
									<tr>
										<td class="tableLabel">Room</td>
										<td class="tableLabel">Adults</td>
										<td class="tableLabel">Children</td>
										<td class="tableLabel">Infants</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><div class="col-sm-12">
												<input required type="number" step="any"
													class="form-control " ng-model="resv.hdr.numRooms" min="1"
													limit-to="${maxRooms}" max="${maxRooms}" />
											</div></td>
										<td><div class="col-sm-12">
												<input required type="number" step="any"
													class="form-control" ng-model="resv.hdr.numAdults" min="1"
													max="200" limit-to="200" />
											</div></td>
										<td><div class="col-sm-12">
												<input type="number" step="any" class="form-control"
													ng-model="resv.hdr.numChildren" min="0" max="200"
													limit-zero="200" />
											</div></td>
										<td><div class="col-sm-12">
												<input type="number" step="any" class="form-control"
													ng-model="resv.hdr.numInfants" min="0" max="200"
													limit-zero="200" />
											</div></td>
									</tr>
								</tbody>
								<thead>
									<tr>
										<td class="tableLabel">Payment Source</td>
										<td class="tableLabel">Type</td>
										<td class="tableLabel" ng-hide="disableTa">Travel Agent</td>
										<td class="tableLabel" ng-hide="disableCorp">Corporate</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<div class="col-sm-12">
												<select class="form-control"
													ng-model="resv.hdr.payment_source" id="payment_source"
													required ng-change="changePaymentSource()">
													<option ng-repeat="type in payment_type"
														value="{{type.id}}">{{type.name}}</option>
												</select>
											</div>
										</td>
										<td>
											<div class="col-sm-12">
												<select id="resvType" ng-model="resv.hdr.resvType"
													ng-change="getTaCorpList()"
													ng-disabled="disableCorporate || disableTaName"
													class="form-control" required>
													<option ng-repeat="type in checkin_type"
														value="{{type.id}}">{{type.name}}</option>
												</select>
											</div>
										</td>
										<td ng-hide="disableTa" colspan="2">
											<div class="col-sm-12">
												<select ng-model="trCrp.tvlcrpId" class="form-control"
													required>
													<option ng-repeat="trvlag in trCrp.ta"
														value="{{trvlag.id}}">{{trvlag.name}}</option>
												</select>
											</div>
										</td>

										<td ng-hide="disableCorp" colspan="2">
											<div class="col-sm-12">
												<select ng-model="trCrp.tvlcrpId" class="form-control"
													required>
													<option ng-repeat="trvlCorp in trCrp.corp"
														value="{{trvlCorp.id}}">{{trvlCorp.name}}</option>
												</select>
											</div>
										</td>
									</tr>
								</tbody>

								<thead>
									<tr>
										<td class="tableLabel">Cut-Off date:</td>
										<!-- adding meal plan -->
										<td class="tableLabel">Meal Plan</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><div class="col-sm-12">
												<div class="date calenderControls">

													<!--  <input type="text" ng-model="resv.hdr.cutOffDate" disabled
																	class="form-control datepicker" size="16"> -->
													<md-datepicker ng-model="resv.hdr.cutOffDate"
														class="form-control" md-min-date="initial.cutoffminDate"
														ng-disabled="true" md-max-date="initial.cutoffmaxDate"
														md-open-on-focus></md-datepicker>
												</div>
											</div> <input type="hidden" id="totalavailrooms"
											value="${totalAvailableRooms}" /></td>
										<td>
											<div class="col-sm-12">
												<select ng-model="resv.hdr.mealPlan" class="form-control"
													required>
													<c:forEach items="${mealPlans}" var="mealPlans">
														<option value="${mealPlans.getPlanId()}">${mealPlans}</option>
													</c:forEach>
												</select>
											</div>
										</td>
									</tr>
								</tbody>
							</table>

						</div>
						<div ng-if="showRoomMsg" id="divShowRoomMsg">Number of rooms
							must be less than or equal to available
							rooms(${totalAvailableRooms}).</div>
						</td>

					</div>
					</md-content>
				</div>
			</div>
			<div
				class="col-lg-4 col-md-4 col-sm-12 col-xs-12 search-calendar-div hidden-sm hidden-xs">
				<div class="form-group">

					<div class="daterange_picker" id="daterange_pickerDiv">
						<input type="hidden" id="date-range12" size="40" value="">

						<div id="date-range12-container" style="width: 456px;"></div>

					</div>
				</div>

			</div>
		</div>
		<div class="row">
			<!-- <md-button class="md-raised md-primary pull-right chkAvailabilityBtn"
				id="chkAvailabilityBtn" ng-click="checkAvailability()">NEXT</md-button>
				 -->
			<md-button class="md-raised md-primary pull-right"
				ng-click="checkAvailability()">Next <i
				class="fa fa-chevron-circle-right" aria-hidden="true"></i></md-button>

			<md-button class="md-raised md-warn pull-left availabilityCalBtn"
				ng-click="showCalender()">Availability Calender</md-button>
		</div>
	</div>

	</md-tab-body> </md-tab> <md-tab id="tabRoomRate" ng-disabled="data.secondLocked"> <md-tab-label>{{data.secondLabel}}</md-tab-label>
	<md-tab-body>

	<div selected-rooms-info="" class="ng-isolate-scope">
		<div class="previousInfo row">
			<div class="room-info-bar col-lg-12">
				<div class="col-lg-2 room-info-bar-msg">
					<label>Arrival On : {{resv.hdr.minArrDate |
						date:dateFormat}}</label>
				</div>
				<div class="col-lg-2 room-info-bar-msg" ng-cloak>
					<label>Departure On : {{resv.hdr.maxDepartDate |
						date:dateFormat}} </label>
				</div>

				<div class="col-lg-8 room-info-bar-msg">
					<label>Rooms & Details : {{resv.hdr.numRooms}} Rooms :
						{{resv.hdr.numAdults}} Adults + {{resv.hdr.numChildren}} Children
						+ {{resv.hdr.numInfants}} Infants </label>
				</div>

			</div>
		</div>
		<!-- 	<div class="room-info-bar-wrapper row div-padding">
			<div class="room-info ng-scope">
				<div class="room-info-bar">
					<div class="col-lg-12 room-info-bar-msg">SELECT ROOM TYPE
						&amp; ASSIGN ROOMS</div>
				</div>
			</div>
		</div> -->

		<div id="mainWrapper">
			<div class="rooms-view-selected">
				<div rooms-view-selected="" rooms-data="rooms"
					view-type="defaultType" class="ng-isolate-scope">
					<div class="rooms-wrapper">
						<div class="rooms-grid ng-scope">
							<div class="md-padding card_list_div_main">

								<div class="card_list_div"
									ng-repeat="roomtype in roomTypesAvailable | orderBy:'displayOrder'">
									<md-card
										md-theme="{{ showDarkTheme ? 'dark-blue' : 'default' }}"
										md-theme-watch> <md-card-title> <md-card-title-text>
									<span class="md-headline">{{roomtype.roomTypeName}}</span> <!-- <span class="md-headline">{{roomtype.roomTypeCode}}</span>  -->
									<span class="md-subhead">{{roomtype.availRoom}} Rooms
										Available</span>

									<div class="occupDiv">
										<label ng-if="roomtype.occ1">S</label> <label
											ng-if="roomtype.occ2">D</label> <label ng-if="roomtype.occ3">T</label>
										<label ng-if="roomtype.occ4">Q</label>
									</div>
									<div class="asdDiv">
										<div ng-repeat="assignedType in AssignedRoomData">
											<label
												ng-if="assignedType.roomTypeCode==roomtype.roomTypeCode">Assigned
												: {{assignedType.total}}</label>
										</div>
									</div>
									<div class="selctBtn">
										<md-button class="md-raised md-primary"
											ng-click="showDetails(roomtype.roomTypeCode,roomtype.roomTypeId,roomtype.availRoom)">Select</md-button>
									</div>

									</md-card-title-text> <!-- <md-card-title-media>
									<div class="md-media-lg card-media roomTypeCard">
										<img src="{{roomtype.image}}" alt="..." class="heightDsgn" />
									</div>
									</md-card-title-media> --> </md-card-title> </md-card>
								</div>
							</div>


						</div>
						<div class="bottom_div">
							<md-button class="md-raised md-primary" ng-click="finalPage();">Next
							<i class="fa fa-chevron-circle-right" aria-hidden="true"></i></md-button>

						</div>
					</div>
				</div>
			</div>
		</div>


	</div>

	</md-tab-body> </md-tab> <md-tab id="tabFinal" ng-disabled="data.thirdLocked"> <md-tab-label>Guest
	Details & Review</md-tab-label> <md-tab-body>

	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" id="myModal" class="modal fade">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">GUEST HISTORY</h4>
				</div>
				<div class="modal-body" style="overflow-y: auto;">

					<div class="table-responsive">
						<!-- modal data comes here -->
						<table class="table table-fixed">
							<thead id="table_head">
								<tr>

									<th></th>
									<th>Guest Name</th>
									<th>Mobile</th>
									<th>Email</th>
									<th>Address</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="data in customerList">

									<td><input type="radio" name="inlineRadioOptions"
										id="inlineRadio1" ng-model="$parent.radioData"
										value="{{$index}}" /></td>

									<td class="filterable-cell">{{data.salutation}}
										{{data.first_name}} {{data.Last_name}}</td>
									<td class="filterable-cell">{{data.phone}}</td>
									<td class="filterable-cell">{{data.mail}}</td>
									<td class="filterable-cell">{{data.address}}</td>
								</tr>

							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						style="margin-right: 5px;"
						ng-click="copyData(customerList[radioData],customerIndex)">Select
						Guest</button>

				</div>

			</div>
		</div>
	</div>



	<div class="previousInfo row">
		<div class="room-info-bar col-lg-12">
			<div class="col-lg-2 room-info-bar-msg">
				<label>Arrival On : {{resv.hdr.minArrDate |
					date:dateFormat}}</label>
			</div>
			<div class="col-lg-2 room-info-bar-msg">
				<label>Departure On : {{resv.hdr.maxDepartDate |
					date:dateFormat}}</label>
			</div>

			<div class="col-lg-8 room-info-bar-msg">
				<label>Rooms & Details : {{resv.hdr.numRooms}} Rooms :
					{{resv.hdr.numAdults}} Adults + {{resv.hdr.numChildren}} Children +
					{{resv.hdr.numInfants}} Infants</label>
			</div>

		</div>
	</div>

	<div class="finalPage">
	
		<form name="guestForm"
			ng-submit="guestForm.$valid && saveReservation()" autocomplete="off">
			<div class="col-lg-12 reser_form">
				<div class="col-md-12">
					<div>
						<h3>Reservation Details</h3>

						<!-- Customer History -->

						<!-- 	<div class="bottom_div"> -->

						<div>
							<md-button class="md-raised md-primary" ng-if="is_old_customer"
								ng-click="getCustomerDetails()" id="custHistBtn">Customer
							History</md-button>
						</div>
						<!-- 	</div> -->

					</div>

					<div class="guestDtlDiv formDiv row md-inline-form">
						<!-- <div class="guest_history_btn">
							<md-button class="md-raised md-warn">Pick From
							History</md-button>
						</div> -->
						<md-content layout-padding>
						<div>
							<div layout="row"
								class="rowStyle  new_rsvrn_srch_form col-sm-12 div_room_dtls">

								<div class="col-sm-2 div_payment_source">
									<label class="control-label col-sm-1 title">Title</label>
									<div class="">
										<select class="form-control col-sm-1"
											ng-model="resv.hdr.selectedSalutation" required>
											<option ng-repeat="salutation in names" ng-value="salutation">{{salutation}}</option>
										</select>
									</div>
								</div>
								<div class="col-sm-6 div_payment_source">
									<label class="control-label col-sm-10">First name<span
										class="red">*</span></label>
									<div class="col-sm-12 first_name_div">
										<input id="first_name" class="form-control col-sm-1"
											onclick="this.focus();this.select()" required
											ng-model="resv.hdr.resvByFirstName" maxlength="50">

										<button type="button"
											ng-if="resv.hdr.resvByFirstName.length > 0"
											class="search_button btn reception"
											ng-click="loadCustomerData()"></button>
									</div>
								</div>
								<div class="col-sm-5 div_payment_source">
									<label class="control-label col-sm-10 title">Last Name
										</label>
									<div class="last_name_div">
										<input type="text" id="last_name" required
											class="form-control col-sm-1"
											onclick="this.focus();this.select()"
											ng-model="resv.hdr.resvByLastName" maxlength="50">
									</div>
								</div>
								<!-- <!-- </div> -->
								<!-- required onclick="this.focus();this.select()"....mail
									 -->
								<!-- onclick="this.focus();this.select()"...phone
																		required  -->
								<!-- <div layout="row" 
								class="rowStyle  new_rsvrn_srch_form col-sm-12 div_room_dtls">-->

								<!-- for niko  -->
								<div class="col-sm-7 div_payment_source email_div">
									<label class="control-label col-sm-2 email_label">Email</label>
									<div class="email_div_new_reserv">
										<input ng-model="resv.hdr.resvByMail"  type="email"
											ng-pattern="/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/"
											class="form-control" title="eg.abc@gmail.com">
										<button type="button" class="search_button btn reception"
											ng-if="resv.hdr.resvByMail.length > 4"
											ng-click="simpleSearchByMail()">Search</button>
									</div>
								</div>

								<!-- for Bahrain -->
								<!-- <div class="col-sm-7 div_payment_source email_div">
									<label class="control-label col-sm-2 email_label">Email</label>
									<div class="email_div_new_reserv">
										<input ng-model="resv.hdr.resvByMail" type="email"
											ng-pattern="/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/"
											class="form-control" title="eg.abc@gmail.com">
										<button type="button" class="search_button btn reception"
											ng-if="resv.hdr.resvByMail.length > 4"
											ng-click="simpleSearchByMail()">Search</button>
									</div>
								</div> -->
								<!-- for niko  -->
								<div class="col-sm-6 div_payment_source">
									<label class="control-label col-sm-4 phonerecpNum">Phone<span
										class="red">*</span></label>
									<div class="col-sm-12 first_name_div" id="phone_new_div_reserv">
										<!-- <input type="text" ng-model="resv.hdr.resvByPhone"
											maxlength="10" pattern="[0-9]{10}" 
											class="form-control" title="enter valid mobile number"> -->
											
											<input ng-model="resv.hdr.resvByPhone" maxlength="15"name="phone" class="form-control" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" 
														required>


										<button type="button"
											ng-if="resv.hdr.resvByPhone.length==10 ||resv.hdr.resvByPhone.length==12"
											class="search_button btn reception"
											ng-click="simpleSearchReservation()">Search</button>
									</div>
								</div>
								<!-- for bahrain -->
								<!-- <div class="col-sm-6 div_payment_source">
									<label class="control-label col-sm-4 phonerecpNum">Phone</label>
									<div class="col-sm-12 first_name_div" id="phone_new_div_reserv">
										<input type="text" ng-model="resv.hdr.resvByPhone"											
											required
										class="form-control"
											title="enter valid mobile number">


										<button type="button"
											ng-if="resv.hdr.resvByPhone.length==10 ||resv.hdr.resvByPhone.length==12"
											class="search_button btn reception"
											ng-click="simpleSearchReservation()">Search</button>
									</div>
								</div>  -->
							</div>

							<!-- comp -->

							<div class="form-row">


								<div class="form-group col-md-2">
									<label class="control-label col-sm-2 title">DOB</label>
									<div class=" dob_div">
										<md-datepicker class="form-control"  ng-model="resv.hdr.dob"
											max-date="dtmax" id="dobSelected" md-open-on-focus  ></md-datepicker>

									</div>
								</div>
								<div class="form-group col-md-4">
									<label class="control-label col-sm-4 title address">Address<span
										class="red">*</span></label>
									<div class="address_div" id="addressDiv">
										<textarea type="text" ng-model="resv.hdr.resvByAddress"
											onclick="this.focus();this.select()" maxlength="200"
											class="form-control" required></textarea>
									</div>
								</div>

								<div class="form-group col-md-3">
									<!-- <label class="control-label col-md-3 company">Company</label>

									<div class="company_div_new_reserv">
										<input id="first_name" class="form-control col-sm-1"
											ng-model="resv.hdr.resvByFirstName" maxlength="50">


									</div> -->

									<label class="control-label col-md-3 company">Country</label><span
										class="red">*</span>

									<div class="company_div_new_reserv">
										<md-input-container> <md-select
											class="md_slect_div_edit" ng-model="resv.hdr.nationality"
											placeholder="country"
											ng-change="loadState(resv.hdr.nationality)" required>
										<md-option ng-repeat="country in countryList "
											ng-value="country.name">{{country.name}}</md-option> </md-select> </md-input-container>


									</div>
								</div>

								<div class="form-group col-md-3">
									<!-- <label class="control-label col-md-3 label_designation">Designation</label>
									<div class="col-sm-12 company_div" id="company_reserv">
										<input id="designation" class="form-control"
											ng-model="resv.hdr.resvByDesignation" maxlength="50"> -->

									<label class="control-label col-md-3 label_designation">State</label><span
										class="red">*</span>
									<div class="col-sm-12 company_div" id="state_reserv">
										<md-input-container> <md-select
											class="md_slect_div_edit" ng-model="resv.hdr.state"
											placeholder="state" required> <md-option
											ng-repeat="state in stateList" ng-value="state.name">{{state.name}}</md-option>
										</md-select> </md-input-container>
									</div>
								</div>
							</div>


							<!-- 	<div layout="row"
			class="rowStyle  new_rsvrn_srch_form col-sm-12 div_room_dtls">

			<div class="col-sm-4 div_payment_source">
				<label class="control-label col-sm-4 title">DOB</label>
				<div class="col-sm-12 dob_div">
					<md-datepicker  class="form-control"
						ng-model="resv.hdr.dob"	 md-open-on-focus></md-datepicker>
				</div>
			</div>

			<div class="col-sm-8 div_payment_source">
				<label class="control-label col-sm-4 title">Address<span
					class="red">*</span></label>
				<div class="col-sm-12 address_div"  id="addressDiv">
					<textarea type="text" ng-model="resv.hdr.resvByAddress"
						onclick="this.focus();this.select()" maxlength="200"
						class="form-control"></textarea>
				</div>
			</div>
			
					<div class="col-sm-6 div_payment_source">
									<label class="control-label col-sm-10">Company</label>
									<div class="col-sm-12 first_name_div">
										<input id="first_name" class="form-control col-sm-1"
											ng-model="resv.hdr.resvByFirstName" maxlength="50">

									</div>
								</div>
								
								
							<div class="col-sm-6 div_payment_source">
									<label class="control-label col-sm-10">Designation</label>
									<div class="col-sm-12 first_name_div">
										<input id="first_name" class="form-control col-sm-1"
											ng-model="resv.hdr.resvByFirstName" maxlength="50">

									</div>
								</div> 
		</div> -->

							<div class="form-row col-md-12">



								<div class="form-group col-md-3">
									<label class="control-label col-md-6 title arriving_from">Arriving
										From</label>
									<div class="arriving_div" id="arrivingDiv">
										<input id="arriving" class="form-control col-md-6"
											ng-model="resv.hdr.resvByArriving"
											onclick="this.focus();this.select()" maxlength="50">
									</div>
								</div>

								<div class="form-group col-md-3">
									<label class="control-label col-md-8 proceeding">Proceeding
										To</label>

									<div class="proceeding_div_new_reserv">
										<input id="proceeding" class="form-control col-md-6"
											ng-model="resv.hdr.resvByProceeding" maxlength="50">


									</div>
								</div>

								<div class="col-sm-6 div_reserved">
									<label class="control-label col-sm-4 title reserved_by">Reserved
										By<span class="red">*</span>
									</label>
									<div class="reserved_div">
										<input type="text" ng-model="resv.hdr.resvFor" id="reserv_for"
											onclick="this.focus();this.select()" maxlength="50" required
											class="form-control">
									</div>
								</div>

							</div>


							<div class="form-row col-md-12">



								<div class="form-group col-md-3">
									<label class="control-label col-md-6 title arriving_from">Company</label>
									<div class="arriving_div" id="arrivingDiv">
										<input id="company" class="form-control col-md-6"
											ng-model="resv.hdr.resvByCompany"
											onclick="this.focus();this.select()" maxlength="50">
									</div>
								</div>

								<div class="form-group col-md-3">
									<label class="control-label col-md-8 proceeding">Designation</label>

									<div class="proceeding_div_new_reserv">
										<input id="designation" class="form-control col-md-6"
											ng-model="resv.hdr.resvByDesignation" maxlength="50">


									</div>
								</div>

								<div class="col-sm-6 div_reserved">
									<label class="control-label col-sm-4 title reserved_by">Remarks</label>

									<div class="address_div" id="remarks">
										<textarea ng-model="resv.hdr.remarks"
											onclick="this.focus();this.select()" class="form-control"></textarea>
									</div>

								</div>

							</div>

							<!-- 
							<div layout="row"
								class="rowStyle  new_rsvrn_srch_form col-sm-12 div_room_dtls">
								<div class="col-sm-12 div_payment_source">
									<label class="control-label col-sm-4 title">Reserved By<span
										class="red">*</span></label>
									<div class="address_div">
										<input type="text" ng-model="resv.hdr.resvFor" id="reserv_for"
											onclick="this.focus();this.select()" maxlength="50" required
											class="form-control">
									</div>
								</div>
							</div> -->
							<!-- 		<div layout="row"
								class="rowStyle  new_rsvrn_srch_form col-sm-12 div_room_dtls">
							<div class="col-sm-6 div_payment_source remarks">
									<label class="control-label col-sm-4 title remarks">Remarks</label>
									<div class="address_div">
										<textarea ng-model="resv.hdr.remarks"
											onclick="this.focus();this.select()" class="form-control"></textarea>
									</div>
								</div>
							</div> -->
							<!-- 		<div layout="row"
								class="rowStyle  new_rsvrn_srch_form col-sm-12 div_room_dtls">
								<div class="col-sm-12 div_payment_source">
									<label class="control-label col-sm-4 title">Remarks</label>
									<div class="address_div">
										<textarea ng-model="resv.hdr.remarks"
											onclick="this.focus();this.select()" class="form-control"></textarea>
									</div>
								</div>
							</div> -->

							<div layout="row"
								class="rowStyle  new_rsvrn_srch_form col-sm-12 div_room_dtls"
								ng-if="is_old_customer">
								<div class="col-sm-6 div_payment_source">
									<label class="control-label col-sm-6 title">Last Visit</label>
									<div class="">
										<input class="form-control" ng-model="last_visit" readonly />
									</div>
								</div>
								<div class="col-sm-6 div_payment_source">
									<label class="control-label col-sm-6 title">Visits#</label>
									<div class="">
										<input class="form-control" ng-model="no_visit" readonly />
									</div>
								</div>
							</div>
							<div layout="row"
								class="rowStyle  new_rsvrn_srch_form col-sm-12 div_room_dtls">

								<div class="col-md-6" id="genderMar">
									<label class="control-label col-md-6">Gender</label><span
										class="red" id="gender_red">*</span>
									<div class="new_chk_in_lft_div_tbl_input">
										<div class="gender col-sm-6">

											<md-input-container class="md-block chInp" flex-gt-sm>
											<md-radio-group required ng-model="resv.hdr.gender">
											<div class="col-md-6">
												<md-radio-button value="Male" id="maleRadio" name="gender"
													class="md-primary" checked='true'>Male</md-radio-button>
											</div>
											<div class="col-md-3">
												<md-radio-button name="gender" id="femaleRadio"
													value="Female">Female</md-radio-button>
											</div>
											</md-radio-group> </md-input-container>
										</div>
									</div>
								</div>
                              
                              		<div class="gust_name">
										<label class="new_chk_in_lft_div_tbl_label">Guest name</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<!-- <div class="col-sm-12"> -->
												<!-- <div class="row"> -->
													<md-input-container class="guest_name" flex-gt-sm>
													<input ng-model="resv.hdr.guestName" type="text"
														maxlength="50"> </md-input-container>
												<!-- </div> -->
											<!-- </div> -->
										</div>
									</div>
									
								<div class="col-sm-12 div_payment_source chk_pkpup">
									<label class="control-label col-md-3 title pickup_label"
										hide="true">Pickup Needed</label>
									<div class="col-sm-12 pickupBox" hide="true">
										<md-checkbox ng-model="pickupReq" ng-change="loadProviders()"
											id="pickNeeded"> </md-checkbox>
									</div>
								</div>
							</div>
		</form>
		
	</div></md-content>
</div>
<div class="new_rsvrn_srch_form col-sm-12 div_room_dtls"
	ng-if="pickupReq">
	<div class="row col-sm-6 pkup_div_group">
		<div class="col-sm-6 div_payment_source">
			<label class="control-label title">Pickup Date<span
				class="red">*</span></label>
			<div class="">
				<md-datepicker ng-model="pickUp.pickupDate" class="form-control"
					md-placeholder="Pickup Date" md-min-date="pickupMinDate"></md-datepicker>
			</div>
		</div>
		<div class="col-sm-6 div_payment_source">
			<label>Pickup Time<span class="red">*</span></label>
			<!-- <div uib-timepicker ng-model="pickUp.pickupTime"
					ng-change="changed()" hour-step="hstep" minute-step="mstep"
					show-meridian="ismeridian"></div>
					 -->
			<div class="form-group">
				<div class='input-group date' id='datetimepicker3'>
					<input id="pickuptime" class="form-control"
						ng-model="pickUp.pickupTime" ng-change="changed()"
						hour-step="hstep" minute-step="mstep" show-meridian="ismeridian" />
					<span class="input-group-addon"> <span
						class="glyphicon glyphicon-time"></span>
					</span>
				</div>
			</div>
			<script type="text/javascript">
				$(function() {
					$('#datetimepicker3').datetimepicker({
						format : 'LT'
					});
				});
			</script>

			<!-- 		<div class="input-group date" id="pickup_time">
                                                            <input type="text" class="form-control ng-pristine ng-valid ng-empty ng-touched"
                                                            ng-change="changed()"  ng-model="pickUp.pickupTime" id="pickup_time_txt" aria-invalid="false" style="">
                                                             <span class="input-group-addon">
                                                             <span class="glyphicon glyphicon-time"></span>
                                                             </span>
                                                   </div> -->
		</div>
	</div>
	<div class="row col-sm-6 pkup_div_group">
		<div class="col-sm-6 div_payment_source">
			<label class="control-label title">Location<span class="red">*</span></label>
			<div class="">
				<input required class="form-control"
					onclick="this.focus();this.select()" ng-model="pickUp.location">
			</div>
		</div>
		<div class="col-sm-6 div_payment_source">
			<label class="control-label title">Seats<span class="red">*</span></label>
			<div class="">
				<input required id="seats_no" type="number" step="any"
					class="form-control" ng-model="pickUp.seats" min="1" max="100" />
			</div>
		</div>
	</div>
	<div class="row col-sm-6 pkup_div_group">
		<div class="col-sm-6 div_payment_source">
			<label class="control-label title provider_label">Provider</label>
			<div class="">
				<select class="form-control" ng-model="pickUp.pickupProvider">
					<option ng-repeat="pvdr in providers" value="{{pvdr.id}}">{{pvdr.name}}</option>
				</select>
			</div>
		</div>
		<div class="col-sm-6 div_payment_source">
			<label class="control-label title provider_label">Remarks</label>
			<div class="">
				<input ng-model="pickUp.pickupRemarks" class="form-control"
					onclick="this.focus();this.select()">
			</div>
		</div>
	</div>
</div>
</div>
<%-- 
commenting gana--%>
<div class="col-md-12 reservSummary">
	<div>
		<h3>Reservation Summary</h3>
	</div>

	<div layout="column">
		<md-card> <md-card-content>
		<div class="sumDateDiv row">
			<div class="col-md-6">
				<div class="col-sm-7 divWidth">
					<label>Check In :</label>
				</div>
				<div class="col-sm-5 divWidth">{{resv.hdr.minArrDate |
					date:dateFormat}}</div>
			</div>
			<div class="col-md-6">
				<div class="col-sm-7 divWidth">
					<label>Check Out :</label>
				</div>
				<div class="col-sm-5 divWidth">{{resv.hdr.maxDepartDate |
					date:dateFormat}}</div>
			</div>
		</div>
		<div class="sumRateByRoomTypeDiv row">
			<md-toolbar layout="row" class="md-blue-3" id="bgColour">
			<div class="md-toolbar-tools">
				<span class="fontType">Selected Room Types</span>
			</div>
			</md-toolbar>
			<md-content class="roomTypeListDiv"> <md-list>
			<md-list-item class="md-1-line"
				ng-repeat="rateArr in rateSummary.rateArray">
			<div class="md-list-item-text rateByRoomList">
				<div class="col-md-8">
					<label>{{rateArr.roomType}}</label><span class="md-subhead">{{rateArr.numRooms}}
						Rooms x {{rateArr.numNights}} Nights</span>
				</div>
				<div class="col-md-3 ng-binding" style="text-align: right">${currencySymbol}&nbsp;{{rateArr.total}}
				</div>
			</div>

			<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-list> </md-content>
		</div>


		<div class="sumDiscountDiv row">
			<md-toolbar layout="row" class="md-blue-3" id="bgColour">
			<div class="md-toolbar-tools">
				<span class="fontType"> Discounts</span>
			</div>
			</md-toolbar>
			<md-content class="discountListDiv"> <md-list>
			<md-list-item class="md-1-line" ng-repeat="discDisp in discDispList">
			<div class="md-list-item-text rateByRoomList">
				<div class="col-md-8">
					<label>{{discDisp.code}}({{discDisp.discountFor == 1? "ROOM":"FOOD" }})</label>
				</Div>
				<div class="col-md-3">{{discDisp.value}}</div>
					<div class="col-md-1 delete_room_btn ng-scope"  ng-click="deleteDiscountAplied(discDisp.id)" role="button">
						<i class="fa fa-trash" aria-hidden="true"></i>
					</div>
			</div>
			<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-list> </md-content>
			<md-button class="md-warn discBtn" ng-click="showDiscounts()">Show
			Discounts</md-button>
		</div>


		<div class="sumFinalRateDiv row">
			<md-toolbar layout="row" class="md-blue-3" id="bgColour">
			<div class="md-toolbar-tools">
				<span class="fontType">Rate Summary</span>
			</div>
			</md-toolbar>
			<md-list> <md-list-item class="md-1-line">
			<div class="md-list-item-text rateByRoomList">
				<div class="col-md-8">
					<label>Sub Total</label>
				</div>
				<div class="col-md-3 ng-binding" style="text-align: right;">
					${currencySymbol} &nbsp; {{rateSummary.subTotal}}</div>
			</div>
			<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
			<div class="md-list-item-text rateByRoomList">
				<div class="col-md-8">
					<label>Total Service Charge</label>
				</div>
				<div class="col-md-3 ng-binding" style="text-align: right;">
					${currencySymbol}&nbsp;{{rateSummary.totalSCharge}}</div>
			</div>
			<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line"
				ng-if="rateSum.discTotal!=0">
			<div class="md-list-item-text rateByRoomList">
				<div class="col-md-8">
					<label>Total Discount</label>
				</div>
				<div class="col-md-3 ng-binding" style="text-align: right;">
					${currencySymbol}&nbsp;{{rateSummary.discTotal}}</div>
			</div>
			<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
			<div class="md-list-item-text rateByRoomList">
				<div class="col-md-8">
					<label>Total Tax</label>
				</div>
				<div class="col-md-3 ng-binding" style="text-align: right;">
					${currencySymbol}&nbsp;{{rateSummary.taxTotal}}</div>
			</div>
			<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
			<div class="md-list-item-text rateByRoomList">
				<div class="col-md-8">
					<label>Total Service Tax</label>
				</div>
				<div class="col-md-3 ng-binding" style="text-align: right;">
					${currencySymbol}&nbsp;{{rateSummary.sTaxTotal}}</div>
			</div>
			<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
			<div class="md-list-item-text rateByRoomList">
				<div class="col-md-8">
					<label>Total Cost</label>
				</div>
				<div class="col-md-3 ng-binding" style="text-align: right;">
					${currencySymbol}&nbsp;{{rateSummary.grantTotal}} (Incl.Taxes)</div>
			</div></md-list>
		</div>

		</md-card-content> </md-card>
		<div class="bottom_div">
			<md-button class="md-raised md-primary" ng-disabled="submit_click"
				type="submit">Submit</md-button>
		</div>
	</div>
</div>
</div>
</form>
</div>


</md-tab-body>
</md-tab>
</md-tabs>
</md-content>



<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="assignRoomsmyModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Assign Rooms</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<!-- modal data comes here -->
				<md-content> <md-content class="md-padding">
				<md-subheader class="md-no-sticky availRatePlans">Available
				Rate Plans</md-subheader> <uib-accordion close-others="oneAtATime">

				<div uib-accordion-group class="panel-default roomtypeNameHdr"
					ng-repeat="rate in roomRates" is-open="!status.open">
					<uib-accordion-heading> {{rate.rateCode}} <i
						class="pull-right glyphicon"
						ng-class="{'glyphicon-chevron-right': status.open}"></i> </uib-accordion-heading>
					<div class="rateOccupancy">



						<div class="form-row col-md-12">
							<div class="form-group col-md-3" ng-if="rate.occ1">
								<div class="row">
									<label class="rate_label col-md-3">Single</label>
									<%-- <div class="rate">${currencySymbol}&nbsp;{{rate.totalOcc1Rate}}
									<input type="text" value="${currencySymbol}&nbsp;{{rate.totalOcc1Rate}}">
									</div> --%>
									<label class="rate_label col-md-3 rate">Rate</label>
								</div>
								<div class="form-group col-md-2 available_room">

									<input required type="number" step="any"
										limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
										max="{{roomMax}}"
										class="form-control form-control-inline input-medium assgnRoom"
										id="{{rate.rateCode}}occ1" data-rateid="{{rate.rateId}}"
										data-rateCode="{{rate.rateCode}}"
										data-roomTypeCode="{{rate.roomTypeCode}}"
										data-roomtypeid="{{rate.roomTypeId}}" ng-model="rate.occ1Val"
										data-occ="occ1" min="0" max="4999" ng-pattern="/^1234$/" />
								</div>
								<div class="form-group col-md-4 rate_room">
									<!--  for niko -->
									<%-- <input type="text" class="form-control form-control-inline" value="${currencySymbol}&nbsp;{{rate.totalOcc1Rate}}"> --%>
									<!--  for Behrain -->
									<input type="text" class="form-control form-control-inline"
										value="{{rate.totalOcc1Rate}}">
								</div>
							</div>
							<div class="form-group col-md-3" ng-if="rate.occ2">
								<div class="row">
									<label class="rate_label col-md-3">Double</label> <label
										class="rate_label col-md-3 rate">Rate</label>
									<%-- <div class="rate">${currencySymbol}&nbsp;{{rate.totalOcc2Rate}} --%>
								</div>
								<!-- </div> -->
								<div class="form-group col-md-2 available_room">
									<input required type="number" step="any"
										limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
										class="form-control form-control-inline input-medium assgnRoom"
										data-rateCode="{{rate.rateCode}}"
										data-roomTypeCode="{{rate.roomTypeCode}}"
										data-rateid="{{rate.rateId}}" ng-model="rate.occ2Val"
										data-roomtypeid="{{rate.roomTypeId}}" data-occ="occ2"
										id="{{rate.rateCode}}occ2" min="0" max="4999"
										ng-pattern="/^1234$/" />
								</div>
								<div class="form-group col-md-4 rate_room">
									<!-- for niko -->
									<input type="text" class="form-control form-control-inline"
										value="${currencySymbol}&nbsp;{{rate.totalOcc2Rate}}">
									<!-- for behrain -->
									<!-- <input type="text" class="form-control form-control-inline" value="{{rate.totalOcc2Rate}}">  -->
								</div>
							</div>
							<div class="form-group col-md-3" ng-if="rate.occ3">
								<div class="row">
									<label class="rate_label" id="triple_room">Triple</label> <label
										class="rate_label rate">Rate</label>
									<%-- <div class="rate">${currencySymbol}&nbsp;{{rate.totalOcc3Rate}} --%>
								</div>
								<!-- </div> -->
								<div class="form-group col-md-2 available_room">
									<input required type="number" step="any"
										limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
										class="form-control form-control-inline input-medium assgnRoom"
										data-rateCode="{{rate.rateCode}}"
										data-roomTypeCode="{{rate.roomTypeCode}}"
										data-rateid="{{rate.rateId}}" ng-model="rate.occ3Val"
										data-roomtypeid="{{rate.roomTypeId}}" data-occ="occ3"
										id="{{rate.rateCode}}occ3" min="0" max="4999"
										ng-pattern="/^1234$/" />
								</div>
								<div class="form-group col-md-4 rate_room">
									<!-- for niko -->
									<input type="text" class="form-control form-control-inline"
										value="${currencySymbol}&nbsp;{{rate.totalOcc3Rate}}">

									<!-- for behrain -->
									<!--  <input type="text" class="form-control form-control-inline" value="{{rate.totalOcc3Rate}}"> -->
								</div>
							</div>
							<div class="col-sm-3" ng-if="rate.occ4">
								<div class="row">
									<label class="rate_label">Quad</label>
									<div class="rate">${currencySymbol}&nbsp;{{rate.totalOcc4Rate}}
									</div>
								</div>
								<div class="row">
									<input required type="number" step="any"
										limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
										class="form-control form-control-inline input-medium assgnRoom"
										data-rateCode="{{rate.rateCode}}"
										data-roomTypeCode="{{rate.roomTypeCode}}"
										data-rateid="{{rate.rateId}}" ng-model="rate.occ4Val"
										data-roomtypeid="{{rate.roomTypeId}}" data-occ="occ4"
										id="{{rate.rateCode}}occ4" min="0" max="4999"
										ng-pattern="/^1234$/" />
								</div>
							</div>


						</div>


					</div>
				</div>
				</uib-accordion> </md-content> </md-tab><!--  <md-tab label="Picture"> <md-content class="md-padding">


					<div id="carousel-example-generic" class="carousel slide"
						data-ride="carousel">
						Indicators
						<ol class="carousel-indicators">
							<li data-target="#carousel-example-generic" data-slide-to="0"
								class="active"></li>
							<li data-target="#carousel-example-generic" data-slide-to="1"></li>
							<li data-target="#carousel-example-generic" data-slide-to="2"></li>
						</ol>

						Wrapper for slides
						<div class="carousel-inner" role="listbox">
							<div class="item active" ng-if="roomTypeImages.image1!=null">
								<img src="{{roomTypeImages.image1}}" alt="...">
								<div class="carousel-caption">...</div>
							</div>
							<div class="item" ng-if="roomTypeImages.image2!=null">
								<img src="{{roomTypeImages.image2}}" alt="...">
								<div class="carousel-caption">...</div>
							</div>
							<div class="item " ng-if="roomTypeImages.image3!=null">
								<img src="{{roomTypeImages.image3}}" alt="...">
								<div class="carousel-caption">...</div>
							</div>
							<div class="item" ng-if="roomTypeImages.image4!=null">
								<img src="{{roomTypeImages.image4}}" alt="...">
								<div class="carousel-caption">...</div>
							</div>
						</div>
 --> <!-- Controls --> <!-- <a class="left carousel-control" href="#carousel-example-generic"
							role="button" data-slide="prev"> <span
							class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a> <a class="right carousel-control"
							href="#carousel-example-generic" role="button" data-slide="next">
							<span class="glyphicon glyphicon-chevron-right"
							aria-hidden="true"></span> <span class="sr-only">Next</span>
						</a> -->
			</div>


			<!-- </md-content> </md-tab> <md-tab label="Features"> <md-content class="md-padding">
					<h1 class="md-display-2">Features</h1>
					<p>Integer turpis erat,</p>

					</md-content> </md-tab>  </md-tabs> </md-content>
				</div> -->
			<div class="modal-footer">
				<button id="contExpDepartBtn" type="button" class="btn btn-success"
					ng-click="assignRooms()">Assign</button>
				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose"
					ng-click="cancelRoomAssignPopUp();">
					<spring:message code="pms.btn.cancel" />
				</button>

			</div>
		</div>
	</div>
</div>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="availDiscountsmyModal" class="modal fade">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Available Discounts</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<div layout="column" class="md-inline-form">

					<div>

						<div class="discTypeDiv row">
							<md-radio-group ng-model="disc.group"
								ng-change="changeDiscGroup()">
							<div class="col-md-3">

								<md-radio-button value="general" class="md-primary">General</md-radio-button>
							</div>
							<div class="col-md-3">
								<md-radio-button value="plan" class="md-primary">Plan
								Based </md-radio-button>
							</div>
							</md-radio-group>
						</div>
						<div class="discountsList row">
							<md-subheader class="md-no-sticky availRatePlans">Applicable
							Discounts</md-subheader>
							<div class="generalDiscDiv" ng-if="disc.group=='general'">
								<md-content> <md-list> <md-radio-group
									ng-model="disc.generalDisc"> <md-list-item
									class="md-1-line" ng-repeat="general in availDiscounts.general">
								<div class="md-list-item-text rateByRoomList">
									<div class="col-md-6">
										<md-radio-button class="rad_{{general.id}}" data-discountFor = {{general.discountFor}}
											data-isOpen={{general.isOpen}}
											data-discCode="{{general.code}}"
											data-discValue="{{general.isPc ?general.discPc:general.discAmount}} {{general.isPc ? '%' : currency }}"
											value="{{general.id}}" class="md-primary">
										<label>{{general.code}}</label> <span class="md-subhead">(
											{{general.name}} )</span></md-radio-button>
									</div>
									<div class="col-md-2">
										<label>{{general.discountFor == 1? "ROOM":"FOOD" }}</label>
									</div>
									<div class="col-md-4" ng-if="!general.isOpen">{{general.isPc
										?general.discPc:general.discAmount}} {{general.isPc ? "%" :
										currency }}</div>
									<div class="col-md-4 openDiscDiv" ng-if="general.isOpen">
										<md-input-container class="md-block" flex-gt-xs>
										<label>discount value ( {{general.isPc ? "%" :
											currency }} )</label> <input id="open{{general.id}}"
											value="{{general.isPc ?general.discPc:general.discAmount}}">
										</md-input-container>
									</div>
								</div>
								<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-radio-group></md-list> </md-content>
							</div>

							<div class="planDiscDiv" ng-if="disc.group=='plan'">

								<md-content> <md-list> <md-radio-group
									ng-model="disc.planDisc"> <md-list-item
									class="md-1-line" ng-repeat="plan in availDiscounts.plan">
								<div class="md-list-item-text rateByRoomList">
									<div class="col-md-8">
										<md-checkbox class="discPlanCheckBox chk_{{plan.id}}"
											aria-label="Checkbox 1" data-discCode="{{plan.code}}" data-discountFor = {{plan.discountFor}}
											data-discValue="{{plan.isPc?plan.discPc:plan.discAmount}} {{plan.isPc ? '%' : currency }}"
											ng-model="disc.planBased[plan.id]"
											ng-true-value="{{plan.rateId}}" ng-false-value="">
										<label>{{plan.code}}</label> <span class="md-subhead">(
											{{plan.name}} )</span> </md-checkbox>
									</div>
									<div class="col-md-2">
										<label>{{plan.discountFor == 1? "ROOM":"FOOD" }}</label>
									</div>
									<div class="col-md-2">{{plan.isPc
										?plan.discPc:plan.discAmount}} {{plan.isPc ? "%" : currency}}</div>
								</div>
								<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-radio-group></md-list> </md-content>
							</div>

						</div>
					</div>


				</div>
			</div>
			<div class="modal-footer">
				<button id="contExpDepartBtn" type="button" class="btn btn-success"
					ng-click="applyDiscount()">Apply</button>
				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose" ng-click="cancelDiscPopUp();">
					<spring:message code="pms.btn.cancel" />
				</button>

			</div>
		</div>
	</div>
</div>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="calendermyModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Availability Calendar</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;"
				ng-controller="calCtrl as showCase">

				<div class="tableHdrArrow">
					<div class="arrowLeft">
						<a ng-click="calGoLeft()"><i
							class="fa fa-caret-left cal-arrow" aria-hidden="true"></i></a>
					</div>
					<div class="arrowRight">
						<a ng-click="calGoRight()"><i
							class="fa fa-caret-right cal-arrow" aria-hidden="true"></i></a>
					</div>
				</div>
				<table class="table">
					<thead>
						<tr>
							<th class="tblHdr">Room Type</th>
							<th ng-repeat="dt in dateArray" class="tblHdr dateHdr">{{dt.day}}</br>{{dt.date
								| date:'dd-MMM-yy'}}
							</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="(key,type) in roomTypes" class="cal_tr">
							<td class="cal_roomtype cal_td">{{type}}</td>
							<td class="cal_tbldata cal_td"
								ng-repeat="calData in calendarData"
								ng-if="calData.roomTypeId==key">{{calData.availRoom}}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">

				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose" ng-click="cancelCalenderPopUp();">
					<spring:message code="pms.btn.cancel" />
				</button>

			</div>
		</div>
	</div>
</div>





<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="customerDetailsModel" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Customer History</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<!-- modal data comes here -->
				<div class="col-md-12">
					<div class="col-md-2">
						<label style="font-size: medium;"><b>Guest Name :</b></label>
					</div>
					<div class="col-md-6">
						<label style="font-size: medium;"><b>
								{{customerData.salutation}} {{customerData.firstName}}
								{{customerData.lastName}}</b></label>
					</div>
					<div class="col-md-4"></div>
				</div>
				<table class="table table-striped">
					<thead>
						<tr>

							<th>Arrival</th>
							<th>Room Type</th>
							<th>Room</th>
							<th>Nights</th>
							<th>Departure</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="Customer in customerDetails">

							<td>{{Customer.arrivalDate}}</td>
							<td>{{Customer.roomType}}</td>
							<td>{{Customer.roomNumber}}</td>
							<td>{{Customer.numNights}}</td>
							<td>{{Customer.deptDate}}</td>
						</tr>

					</tbody>
				</table>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

			</div>
		</div>
	</div>
</div>
<!-- End to create Button for customer History -->
</div>
<style>
.mybtn
{    position: absolute;
    right: 0;
    z-index: 1;
    margin-right: 30px;
    top: 20px;
    background-color:rgba(63,81,181);
     color:rgba(255, 255, 255, 0.87); 
     box-sizing: border-box;
     border: none;
}

.divid
{
position: relative;
}
</style>

<script type="text/javascript">
	$(function() {
		$("#datetimepicker").datetimepicker({
			minDate : 0
		});
		$("#datetimepickerDept").datetimepicker({
			minDate : 0
		});

		/* $("#dobSelected").datepicker({
			 maxDate: new Date() 
		}); */
	});
	

	/* $(function() {
		$('#datetimepicker').datetimepicker({
			/* format : 'LT' */
	/* icons : {
		time : "fa fa-clock-o",
		date : "fa fa-calendar",
		up : "fa fa-arrow-up",
		down : "fa fa-arrow-down"
	}
	
	});
	});
	 */
	/* 	$(function() {
	 $('#dateTimePickerDeparture').datetimepicker({
	 icons : {
	 time : "fa fa-clock-o",
	 date : "fa fa-calendar",
	 up : "fa fa-arrow-up",
	 down : "fa fa-arrow-down"
	 }

	 }); */
	/* }); */
</script>
