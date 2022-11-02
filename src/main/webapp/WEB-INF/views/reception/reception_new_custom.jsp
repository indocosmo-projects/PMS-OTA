<jsp:directive.include file="../common/includes/page_directives.jsp" />

<div class="divid"ng-controller="receptionCtrl as ctl" ng-cloak>
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
			value="Rooms Pending : {{checkin.hdr.numRooms-totalRoomsAssigned}}" />
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
				class="col-lg-7 col-md-7 col-sm-12 xs-left-right-padding details_div lft_mrg_zrw">

				<div class="md-inline-form">
					<md-content layout-padding class="searchFormDiv lft_mrg_zrw"
						style="height: 250px;">
					<div>

						<div layout="row"
							class="new_rsvrn_srch_form date_div new_rsrvtion_tble_div">

							<table>
								<thead>
									<tr>
										<td class="tableLabel">Arrival date</td>
										<td class="tableLabel">Depart date</td>
										<!-- <td class="tableLabel">Cutoff date</td> -->
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<div class="col-sm-12">
												<div class="calenderControls">
													<!-- <md-datepicker required ng-model="checkin.hdr.minArrDate"
														ng-change="changeNight()" class="form-control"
														md-min-date="initial.cutoffminDate" md-open-on-focus></md-datepicker> -->
													<md-datepicker required ng-model="checkin.hdr.minArrDate"
														ng-change="changeNight()" class="form-control"
														md-min-date="hotelDate" md-open-on-focus ng-disabled="true" md-date-locale="mylocale" ></md-datepicker>
												</div>
											</div>
										</td>
										<td>
											<div class="col-sm-12">
												<div class="calenderControls">
													<!-- <md-datepicker required
														ng-model="checkin.hdr.maxDepartDate"
														ng-change="changeNight()" class="form-control"
														md-min-date="initial.cutoffminDate" md-open-on-focus>
													</md-datepicker> -->
													<md-datepicker required
														ng-model="checkin.hdr.maxDepartDate"
														ng-change="changeNight()" class="form-control"
														md-min-date="hotelDate" md-open-on-focus md-date-locale="mylocale">
													</md-datepicker>
												</div>
											</div>
										</td>

									</tr>
								</tbody>
								<thead>
									<tr>

										<td class="tableLabel">Nights</td>
										<td class="tableLabel">Rooms</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>
											<div class="col-sm-12">
												<input required type="number" step="any"
													ng-model="checkin.hdr.nights" class="form-control"
													ng-change="changeCheckout()" limit-to="${maxNight}" min="1"
													max="${maxNight}" ng-pattern="/[0-9]{1,5}$/" />
											</div>
										</td>
										<td><div class="col-sm-12">
												<input required type="number" step="any"
													class="form-control" ng-model="checkin.hdr.numRooms"
													min="1" limit-to="${maxRoom}" max="${maxRoom}" />
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
										<td><div class="col-sm-12">
												<select class="form-control"
													ng-change="changePaymentSource()"
													ng-model="checkin.hdr.payment_source" required>
													<option ng-repeat="type in payment_type"
														value="{{type.id}}">{{type.name}}</option>
												</select>
											</div></td>
										<td><div class="col-sm-12">
												<select id="resvType" ng-model="checkin.hdr.type"
													ng-disabled="disableCorporate || disableTaName"
													ng-change="getTaCorpList()" class="form-control" required>
													<option ng-repeat="type in checkin_type"
														value="{{type.id}}">{{type.name}}</option>
												</select>
											</div></td>
										<td ng-hide="disableTa">
											<div class="col-sm-12">
												<select ng-model="trCrp.tvlcrpId" class="form-control"
													required>
													<option ng-repeat="trvlag in trCrp.ta"
														value="{{trvlag.id}}">{{trvlag.name}}</option>
												</select>
											</div>
										</td>
										<td ng-hide="disableCorp">
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
							</table>
						</div>
					</div>
				</div>
			</div>
			<div
				class="col-lg-5 col-md-5 col-sm-12 col-xs-12 search-calendar-div hidden-sm hidden-xs">
				<div class="form-group">

					<div class="daterange_picker" id="daterange_pickerDiv">
						<input type="hidden" id="date-range12" size="40" value="">

						<div id="date-range12-container" style="width: 456px;"></div>

					</div>
				</div>

			</div>
		</div>
		<div class="row">
			<md-button class="md-raised md-warn" ng-click="showCalender()">Availability
			Calender</md-button>

			<div class="checkAvailability formDiv row pull-right">
				<!-- <input type="button" class="btn btn-primary" id="chkAvail"
					ng-click="checkAvailability()" value="NEXT" /> -->
				<md-button class="md-raised md-primary"
					ng-click="checkAvailability()">Next <i
					class="fa fa-chevron-circle-right" aria-hidden="true"></i></md-button>
			</div>
		</div>
	</div>




	</md-tab-body> </md-tab> <md-tab id="tabRoomRate" ng-disabled="data.secondLocked"> <md-tab-label>ROOMS
	& RATES</md-tab-label> <md-tab-body>
	<div selected-rooms-info="" class="ng-isolate-scope">
		<div class="previousInfo row">
			<div class="room-info-bar col-lg-12">
				<div class="col-lg-2 room-info-bar-msg">
					<label>Arrival</label>:{{checkin.hdr.minArrDate | date:dateFormat}}
				</div>
				<div class="col-lg-2 room-info-bar-msg">
					<label>Departure</label>:{{checkin.hdr.maxDepartDate |
					date:dateFormat}}
				</div>

				<div class="col-lg-8 room-info-bar-msg">
					<label>Rooms & Details</label>:{{checkin.hdr.numRooms}} Rooms :
					{{checkin.hdr.numAdults}} Adults + {{checkin.hdr.numChildren}}
					Children + {{checkin.hdr.numInfants}} Infants
				</div>
			</div>
		</div>
		<!-- <div class="room-info-bar-wrapper row div-padding">
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
									<!-- <span class="md-headline">{{roomtype.roomTypeCode}}</span> -->
									<span class="md-headline">{{roomtype.roomTypeName}}</span> <span
										class="md-subhead">{{roomtype.availRoom}} Rooms
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

									</md-card-title-text> <md-card-title-media> <!-- <div class="md-media-lg card-media roomTypeCard">
										<img src="{{roomtype.image}}" alt="..." class="heightDsgn" />
									</div> --> </md-card-title-media> </md-card-title> </md-card>
								</div>
							</div>
						</div>
						<div class="bottom_div">
							<md-button class="md-raised md-primary" ng-click="guestPage();">Next
							<i class="fa fa-chevron-circle-right" aria-hidden="true"></i></md-button>

						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	</md-tab-body> </md-tab> <md-tab id="tabGuestDetails" ng-disabled="data.thirdLocked">
	<md-tab-label>GUEST DETAILS</md-tab-label> <md-tab-body>



	<!-- 
	 <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
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
									<th>Guest Name</th>
									<th>Mobile</th>
									<th>Email</th>
									<th>Address</th>
									<th>GST No</th>
								</tr>
							</thead>


							<tr ng-repeat="data in customerList">
								<td><input type="radio" name="inlineRadioOptions"
									id="inlineRadio1" ng-model="$parent.radioData"
									value="{{$index}}"/></td>
								<td>{{data.salutation}} {{data.first_name}} {{data.first_name}}</td>
								<td>{{data.phone}}</td>
								<td>{{data.mail}}</td>
								<td>{{data.address}}</td>
								<td>{{data.gstno}}</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						ng-click="copyData(customerList[radioData],customerIndex)">Select Guest
						</button>
				</div>
			</div>
		</div>
	</div> -->

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
									<th>VAT Account No</th>
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
									<td class="filterable-cell">{{data.gstno}}</td>
								</tr>

							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						ng-click="copyData(customerList[radioData],customerIndex)">Select
						Guest</button>

				</div>

			</div>
		</div>
	</div>


	<div class="previousInfo row">
		<div class="room-info-bar col-lg-12">
			<div class="col-lg-2 room-info-bar-msg">
				<label>Arrival</label>:{{checkin.hdr.minArrDate | date:dateFormat}}
			</div>
			<div class="col-lg-2 room-info-bar-msg">
				<label>Departure</label>:{{checkin.hdr.maxDepartDate |
				date:dateFormat}}
			</div>

			<div class="col-lg-8 room-info-bar-msg">
				<label>Rooms & Details</label>:{{checkin.hdr.numRooms}} Rooms :
				{{checkin.hdr.numAdults}} Adults + {{checkin.hdr.numChildren}}
				Children + {{checkin.hdr.numInfants}} Infants
			</div>
		</div>
	</div>
	<form name="guestForm" ng-submit="guestForm.$valid && finalPage()"
		autocomplete="off">
		<div class="guestDetailAssign">
			<div ng-repeat="assignedData in roomListData">
				<md-subheader class="md-no-sticky availRatePlans"
					ng-if="roomListData[$index-1].roomTypeCode!=assignedData.roomTypeCode">{{assignedData.roomTypeCode}}</md-subheader>
				<div class="guest_box">
					<div class="checkIn_div">
						<md-content> <md-tabs md-dynamic-height
							md-selected="selectedGuestInfo" md-border-bottom> <md-tab
							label="Guest Info : {{$index+1}}">

						<div class="hsitory_btn">
							<div class="new_chk_in_lft_div_tbl_input">
								<div class="col-sm-12">
									<div class="row">
										<md-button class="md-raised md-primary"
											ng-if="is_old_customer($index)" type="button"
											id="customerHist-{{$index}}" ng-click="customerHist($index)">
										<span class="ng-scope"> Guest History</span> </md-button>
									</div>
								</div>
							</div>
						</div>
						<button class="md-raised md-warn md-button md-ink-ripple"
							type="button" id="sameasabove-{{$index}}"
							ng-click="sameasabove($index)">
							<span class="ng-scope"> Same As Above </span>
						</button>
						<md-content class="formTab" class="md-padding">

						<div class="reception_left_div">


							<!-- customer  -->
							<div class="new_chk_in_lft_div_tbl">
								<div class="new_chk_in_lft_div_tbl_full_width_row">
									<div class="salutation_div">
										<label class="new_chk_in_lft_div_tbl_label">Title</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container> <select
														ng-model="assignedData.salutation"
														ng-options="salute for salute in salutations"
														class="form-control salutation">
													</select> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="first_name">
										<label class="new_chk_in_lft_div_tbl_label">First name<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container>

													<div
														class="last_btn_input rcption_new_custo_first_input_div">
														<input required onclick="this.focus();this.select()"
															ng-model="assignedData.firstName" maxlength="50"
															class=" rcption_new_custo_first_input"> <input
															type="button" ng-if="assignedData.firstName.length > 0"
															type="text"
															class="search_button btn reception reception_page_frst_name_search"
															ng-click="loadCustomerData($index)" />
													</div>
													</md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="last_name">
										<label class="new_chk_in_lft_div_tbl_label">Last name</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input ng-model="assignedData.lastName" type="text"
														maxlength="50"> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="gust_name">
										<label class="new_chk_in_lft_div_tbl_label">Guest name</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input ng-model="assignedData.guestName" type="text"
														maxlength="50"> </md-input-container>
												</div>
											</div>
										</div>
									</div>

								</div>


								<div class="new_chk_in_lft_div_tbl_full_width_row">
									<div class="email_div">
										<label class="new_chk_in_lft_div_tbl_label">Email
										
											</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container
														class="md-block chInp reception_page_email" flex-gt-sm>
													<!-- <input ng-model="assignedData.email"
														onclick="this.focus();this.select()"
														ng-pattern="/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/"
														required class="new_chk_email"> --> <input type="email"
														ng-model="assignedData.email"
														ng-pattern="/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/"
														class="new_chk_email" > </md-input-container>
													<div class="email_search_div">
														<button type="button"
															class="search_button btn reception reception_page_email_search email_search_btn"
															ng-click="simpleSearchByMail($index)">Search</button>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="phone_div">
										<label class="new_chk_in_lft_div_tbl_label">Phone<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container
														class="md-block chInp reception_page_phone" flex-gt-sm>
													<input ng-model="assignedData.phone" maxlength="15"
														onclick="this.focus();this.select()" only-digits required>
													<input type="button"
														class="search_button btn reception  reception_page_phone_search"
														ng-click="simpleSearchReception($index)" /> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="addree_div">
										<label class="new_chk_in_lft_div_tbl_label">Address<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container
														class="md-block chInp  reception_page_address" flex-gt-sm>
													<input ng-model="assignedData.address" maxlength="200"
														required> </md-input-container>
												</div>
											</div>
										</div>
									</div>


								</div>



								<div class="new_chk_in_lft_div_tbl_full_width_row">
									<div class="country">
										<label class="new_chk_in_lft_div_tbl_label">Country<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container> <!-- <form ng-submit="$event.preventDefault()"> -->
													<md-select ng-model="assignedData.nationality"
														class="md_slect_div_edit" name="provinceOrState"
														ng-change="loadState($index,assignedData.nationality)"
														required> <md-option
														ng-repeat="country in countryList "
														ng-value="country.name">{{country.name}}</md-option> </md-select> </md-input-container>
													<!-- <span ng-cloak="" class="error ng-cloak" ng-if="!guestForm.provinceOrState.$valid">Please select an option</span> -->
													<div style="color: red" ng-show="showCountryError">Please
														select a country</div>
												</div>
											</div>
										</div>
									</div>
									<div class="phone_div">
										<label class="new_chk_in_lft_div_tbl_label">State<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container> <md-select
														ng-model="assignedData.state" class="md_slect_div_edit"
														required> <md-option
														ng-repeat="state in stateList[$index]"
														ng-value="state.name">{{state.name}}</md-option> </md-select> </md-input-container>
													<div style="color: red" ng-show="showStateError">Please
														select a state</div>
												</div>
											</div>
										</div>
									</div>
									<!-- for niko  -->
									<div class="gst_num_div" id="margst">
										<label class="new_chk_in_lft_div_tbl_label" id="gstLabel">GST
										</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container
														class="md-block chInp reception_page_gst" flex-gt-sm>
													<input ng-model="assignedData.gstno" maxlength="15"
														pattern="^([0][1-9]|[1-2][0-9]|[3][0-5])([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+$"
														onclick="this.focus();this.select()"> </md-input-container>
												</div>
											</div>
										</div>
									</div> 

                                <!-- for Bahrain -->
                                <!--  <div class="gst_num_div" id="margst">
										<label class="new_chk_in_lft_div_tbl_label" id="gstLabel">VAT Account No
										</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container
														class="md-block chInp reception_page_gst" flex-gt-sm>
													<input ng-model="assignedData.gstno"							
														onclick="this.focus();this.select()"> </md-input-container>
												</div>
											</div>
										</div>
									</div> -->

								</div>


								<div class="new_chk_in_lft_div_tbl_full_width_row">

									<div class="adults_class">
										<label class="new_chk_in_lft_div_tbl_label">Adults<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="searchFormInput" flex="50">
													<input required type="number" step="any"
														ng-model="assignedData.numAdults" min="1" max="200"
														limit-to="200" /> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="children_class">
										<label class="new_chk_in_lft_div_tbl_label">Children</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="searchFormInput" flex="50">
													<input type="number" step="any"
														ng-model="assignedData.numChildren" min="0" max="200"
														limit-zero="200" /> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="infant_class">
										<label class="new_chk_in_lft_div_tbl_label">Infants</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="searchFormInput" id="marInfant"
														flex="50"> <input type="number"
														step="any" ng-model="assignedData.numInfants" min="0"
														max="200" limit-zero="200" /> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="psprt">
										<label class="new_chk_in_lft_div_tbl_label">ID
											</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container
														class="md-block chInp reception_page_passport_number"
														flex-gt-sm> <input
														ng-model="assignedData.passportNo"></md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="psprt_expry" ng-if="assignedData.passportNo">
										<label class="new_chk_in_lft_div_tbl_label">Expiry<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<!-- <span id="tooltip" data-tooltip="tooltip text"></span> -->
													<md-input-container class="md-block chInp" flex-gt-sm>
													<md-datepicker ng-model="assignedData.passportExpiryOn"
														md-placeholder="Enter date" md-open-on-focus></md-datepicker></md-input-container>
													<div style="color: red" ng-show="showTooltip">Please
														enter expiry date</div>
												</div>
											</div>
										</div>
									</div>


								</div>


								<div class="new_chk_in_lft_div_tbl_full_width_row">
									<div class="gender_div" id="genderMar">
										<label class="new_chk_in_lft_div_tbl_label" id="gendLabel">Gender</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<md-input-container class="md-block chInp" flex-gt-sm>
												<md-radio-group required ng-model="assignedData.gender"
													class="reception_page_gnder">
												<div class="col-md-6">
													<md-radio-button value="Male" class="md-primary">Male</md-radio-button>
												</div>
												<div class="col-md-6">
													<md-radio-button value="Female">Female</md-radio-button>
												</div>
												</md-radio-group> </md-input-container>
											</div>
										</div>
									</div>
									<div class="chkn_remarks_div">
										<label class="new_chk_in_lft_div_tbl_label">Remarks</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row" id="remarkMar">
													<md-input-container
														class="md-block chInp reception_page_remrk" flex-gt-sm>
													<input ng-model="assignedData.remarks"
														onclick="this.focus();this.select()"> </md-input-container>
												</div>
											</div>
										</div>
									</div>



								</div>
							</div>

						</div>
						<div class="reception_right_div filesDiv">
							<div class="row camRow">
								<md-subheader class="md-primary">Take Photo</md-subheader>
								<section layout="row" layout-sm="column"
									layout-align="center center" layout-wrap>
									<div class="image_dip_div" ng-if="files.imageUploaded">
										<img src="{{files.imageSrc}}" width="120"
											alt="no image.. please capture" />
									</div>
									<canvas id="snapshot_{{$index}}" width="100" height="100"></canvas>
									<md-button ng-click="openCaptureDiv($index)"
										class="md-raised md-primary">Capture </md-button>
								</section>
								<div class="captDiv disp_capt" id="divCapture_{{$index}}">
									<div id="weberror" class="alert alert-error ng-scope"
										ng-show="webcamError" style="">
										<span>Webcam could not be started. please give access
											to it !!</span>
									</div>
									<webcam ng-if="divCapture" channel="channel"
										on-streaming="onSuccess()" on-error="onError(err)"
										on-stream="onStream(stream)"></webcam>
									<md-button class="md-raised md-warn" ng-click="makeSnapshot()">take
									picture <i class="fa fa-camera" aria-hidden="true"></i></md-button>
								</div>
							</div>
							<div class="row fileRow">
								<md-subheader class="md-primary">Upload Proof</md-subheader>
								<md-input-container class="md-block chInp facilInput" flex-gt-sm>
								<input file-model="assignedData.idproof" type="file"> </md-input-container>
								<div class="preFile" ng-if="files.proofChosen">
									<label>Previous File:</label><span>{{files.proofName}}</span>
								</div>
							</div>
							<div class="row roomAssign">
								<div class="room_assigned">Assigned :
									{{assignedData.roomNumber !=""?assignedData.roomNumber:'Not
									Assigned'}}</div>
								<md-button class="md-raised md-warn"
									ng-click="getVacantRooms(assignedData.roomTypeId,$index)">Assign
								Room <i class="fa fa-bed" aria-hidden="true"></i></md-button>

							</div>
						</div>

						<!-- <div class="row applyall"><button class="md-raised md-primary md-button md-ink-ripple"
						 type="button" id="applyall" ng-click="applyall($index)" hidden="hidden"><span class="ng-scope" >
						Apply all </span></button></div> -->
						<div class="row applyall" ng-if="$index == 0">
							<button class="md-raised md-primary md-button md-ink-ripple"
								type="button" id="applyall" ng-click="applyall($index)">
								<span class="ng-scope"> Apply all </span>
							</button>
						</div>

						</md-content> </md-tab> </md-tabs> </md-content>
					</div>
				</div>
			</div>

		</div>
		<div class="bottom_div">
			<md-button class="md-raised md-primary" type="submit"
				ng-click="next()">Next <i
				class="fa fa-chevron-circle-right" aria-hidden="true"></i></md-button>
		</div>
	</form>
	</md-tab-body> </md-tab> <md-tab id="tabRateSummary" ng-disabled="data.fourthLocked">
	<md-tab-label>SUMMARY</md-tab-label> <md-tab-body>
	<div class="col-md-6">

		<div class="guestDtlDiv formDiv row md-inline-form">
			<div class="sumDateDiv row">
				<div class="col-md-6">
					<div class="col-sm-5">
						<label>Check In :</label>
					</div>
					<div class="col-sm-5">{{checkin.hdr.minArrDate |
						date:dateFormat}}</div>
				</div>
				<div class="col-md-8">
					<div class="col-sm-4">
						<label>Check Out :</label>
					</div>
					<div class="col-sm-5" style="margin-left: -12px;">{{checkin.hdr.maxDepartDate
						| date:dateFormat}}</div>
				</div>
			</div>
			<md-content layout-padding>
			<div>

				<md-input-container class="md-block"> <label>Special
					Requests</label> <input ng-model="checkin.hdr.specialRequests"
					onclick="this.focus();this.select()"> </md-input-container>

				<md-input-container class="md-block"> <label>Remarks</label>
				<input ng-model="checkin.hdr.remarks"
					onclick="this.focus();this.select()"> </md-input-container>
			</div>
			</md-content>
		</div>

	</div>
	<div class="col-md-6 reservSummary">

		<div layout="column">
			<md-card> <md-card-content>

			<div class="sumRateByRoomTypeDiv row">
				<md-toolbar layout="row" class="md-blue-3">
				<div class="md-toolbar-tools">
					<span>Selected Room Types</span>
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
					<div class="col-md-3 ng-binding" style="text-align: right">
						${currencySymbol}&nbsp;{{rateArr.total}}</div>
				</div>

				<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-list> </md-content>
			</div>


			<div class="sumDiscountDiv row">
				<md-toolbar layout="row" class="md-blue-3">
				<div class="md-toolbar-tools">
					<span>Discounts</span>
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
				<md-toolbar layout="row" class="md-blue-3">
				<div class="md-toolbar-tools">
					<span>Rate Summary</span>
				</div>
				</md-toolbar>
				<md-list> <md-list-item class="md-1-line">
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Sub Total</label>
					</div>
					<div class="col-md-3" style="text-align: right">${currencySymbol}&nbsp;{{rateSummary.subTotal}}
					</div>
				</div>
				<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Service Charge</label>
					</div>
					<div class="col-md-3" style="text-align: right">${currencySymbol}&nbsp;{{rateSummary.totalSCharge}}
					</div>
				</div>
				<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line"
					ng-if="rateSum.discTotal!=0">
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Disc</label>
					</div>
					<div class="col-md-3" style="text-align: right">${currencySymbol}&nbsp;{{rateSummary.discTotal}}
					</div>
				</div>
				<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Tax</label>
					</div>
					<div class="col-md-3" style="text-align: right">${currencySymbol}&nbsp;{{rateSummary.taxTotal}}
					</div>
				</div>
				<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
				<%-- <div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Service Tax</label>
					</div>
					<div class="col-md-3">{{rateSummary.sTaxTotal}}
						${currencySymbol}</div>
				</div> --%> <!-- 				<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
 -->
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Cost</label>
					</div>
					<div class="col-md-3" style="text-align: right">${currencySymbol}&nbsp;{{rateSummary.grantTotal}}
						(Incl.Txs)</div>
				</div></md-list>
			</div>

			</md-card-content> </md-card>
			<div class="bottom_div">
				<md-button class="md-raised md-primary" ng-disabled="submit_click" 
					ng-click="saveReception()" type="submit">Submit</md-button>
			</div>
		</div>
	</div>
	</md-tab-body> </md-tab> </md-tabs> </md-content>
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
					<md-content> <md-tabs md-border-bottom
						class="roomRateTab"> <md-tab label="Rates"> <md-content
						class="md-padding"> <md-subheader
						class="md-no-sticky availRatePlans">Available Rate
					Plans</md-subheader> <uib-accordion close-others="oneAtATime">

					<div uib-accordion-group class="panel-default roomtypeNameHdr"
						ng-repeat="rate in roomRates" is-open="!status.open">
						<uib-accordion-heading> {{rate.rateCode}}
						<i class="pull-right glyphicon"
							ng-class="{'glyphicon-chevron-right': status.open}"
							ng-click="occupancyRate(rate.rateId,rate.roomTypeCode)"></i> </uib-accordion-heading>
						<div class="rateOccupancy">
							<div class="col-md-12">
								<div class="row">
									<div class="col-md-1">

										<label>Day</label>
									</div>

									<div class="col-md-1" ng-if="rate.occ1">
										<label>Single</label>
									</div>
									<div class="col-md-2" ng-if="rate.occ1">
										<label> ${currencySymbol}&nbsp;{{rate.totalOcc1Rate}}</label>
									</div>
									<div class="col-md-1" ng-if="rate.occ2">
										<label>Double</label>
									</div>
									<div class="col-md-2" ng-if="rate.occ2">
										<label>${currencySymbol}&nbsp;{{rate.totalOcc2Rate}}</label>
									</div>
									<div class="col-md-1" ng-if="rate.occ3">
										<label>Triple</label>
									</div>
									<div class="col-md-2" ng-if="rate.occ3">
										<label> ${currencySymbol}&nbsp;{{rate.totalOcc3Rate}}</label>
									</div>
									<div class="col-md-1" ng-if="rate.occ4">
										<label>Quad</label>
									</div>
									<div class="col-md-1" ng-if="rate.occ4">
										<label> ${currencySymbol}&nbsp;{{rate.totalOcc4Rate}}</label>
									</div>
								</div>
							</div>





							<div class="col-md-12"
								ng-repeat="occupancyRates in roomRatesOccupancy">
								<div class="row" ng-if="rate.rateId==occupancyRates.rateId">
									<div class="col-md-1">
										<div>
											{{count}} <label class="dayWidth">{{occupancyRates.numNights}}</label>
										</div>
									</div>
									<div class="col-md-1" ng-if="rate.occ1">
										<div class="row">
											<input required type="number" step="any" style="height: 27px;"
												limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
												class="form-control form-control-inline input-medium assgnRoom numRoomOccupancy1"
												id="{{occupancyRates.rateCode}}occ1"
												data-rateid="{{occupancyRates.rateId}}"
												data-rateCode="{{occupancyRates.rateCode}}"
												data-roomTypeCode="{{occupancyRates.roomTypeCode}}"
												data-roomtypeid="{{occupancyRates.roomTypeId}}"
												ng-model="occupancyRates.occ1Val" data-occ="occ1" min="0" 
												max="4999" ng-pattern="/^1234$/" numbers-only />
												<!-- ng-disabled="!($index==0)&& !($index% {{nextPlanIndex}}==0)" -->
												
											<!-- </div> -->
											<!-- <div class="col-md-4" style="background-color:red;"> -->

											<!-- </div> -->
										</div>
									</div>
									<div class="col-md-2" ng-if="rate.occ1">
										<md-input-container ng-if="rate.isOpen==true"
											class="containerwidth"> <input
											
											style="height: 27px !important; width: 100% !important; margin-top: 3px;"
											id="{{occupancyRates.rateCode}}occ1" class="assgnRate occ1" ng-keyup="getTotalRate($index)"
											data-index="{{$index}}" type="text" ng-value="occupancyRates.occ1Rate"> </md-input-container>
											<!-- ng-disabled="!($index==0)&& !($index% {{nextPlanIndex}}==0)" -->
											
										<md-input-container ng-if="rate.isOpen==false"
											class="containerwidth"> <input
											ng-click="changeRate($index)"
											style="height: 27px !important; width: 100% !important; margin-top: 3px;"
											id="{{occupancyRates.rateCode}}occ1" class="assgnRate occ1"
											data-index="{{$index}}"type="text" ng-value="occupancyRates.occ1Rate" readonly>
										</md-input-container>
										<!-- 	ng-disabled="!($index==0)&& !($index% {{nextPlanIndex}}==0)" -->
											
									</div>
									<div class="col-md-1" ng-if="rate.occ2">
										<div class="row">
											<!-- <div class="col-md-2" style="margin-left: -45px;"> -->
											<input required type="number" step="any" style="height: 27px;"
												limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
												class="form-control form-control-inline input-medium assgnRoom numRoomOccupancy2"
												data-rateCode="{{occupancyRates.rateCode}}"
												data-roomTypeCode="{{occupancyRates.roomTypeCode}}"
												data-rateid="{{occupancyRates.rateId}}"
												ng-model="occupancyRates.occ2Val"
												data-roomtypeid="{{occupancyRates.roomTypeId}}"
												data-occ="occ2" id="{{occupancyRates.rateCode}}occ2" min="0"
												max="4999" ng-pattern="/^1234$/" numbers-only />
												<!-- ng-disabled="!($index==0)&& !($index% {{nextPlanIndex}}==0)" -->
												
											<!-- </div> -->

											<!-- </div> -->
										</div>
									</div>
									<div class="col-md-2" ng-if="rate.occ2">
										<!-- <div class="col-md-4" > -->
										<md-input-container ng-if="rate.isOpen==true"
											class="containerwidth"> <input
											ng-click="changeRate($index)"
											style="height: 27px !important; width: 100% !important; margin-top: 3px;"
											id="{{occupancyRates.rateCode}}occ2" class="assgnRate occ2"
											data-index="{{$index}}"
										
											type="text" ng-value="occupancyRates.occ2Rate"> </md-input-container>
												<!-- ng-disabled="!($index==0)&& !($index% {{nextPlanIndex}}==0)" -->
										<md-input-container ng-if="rate.isOpen==false"
											class="containerwidth"> <input
											ng-click="changeRate($index)"
											style="height: 27px !important; width: 100% !important; margin-top: 3px;"
											id="{{occupancyRates.rateCode}}occ2" class="assgnRate occ2"
											data-index="{{$index}}"										
											type="text" ng-value="occupancyRates.occ2Rate" readonly>
												<!-- ng-disabled="!($index==0)&& !($index% {{nextPlanIndex}}==0)" -->
										</md-input-container>
									</div>
									<div class="col-md-1" ng-if="rate.occ3">
										<div class="row">
											<!-- <div class="col-md-2" style="margin-left: -45px;"> -->
											<input required type="number" step="any" style="height: 27px;"
												limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
												class="form-control form-control-inline input-medium assgnRoom numRoomOccupancy3"
												data-rateCode="{{occupancyRates.rateCode}}"
												data-roomTypeCode="{{occupancyRates.roomTypeCode}}"
												data-rateid="{{occupancyRates.rateId}}"
												ng-model="occupancyRates.occ3Val"
												data-roomtypeid="{{occupancyRates.roomTypeId}}"
												data-occ="occ3" id="{{occupancyRates.rateCode}}occ3" min="0"
												max="4999" ng-pattern="/^1234$/"											
												numbers-only />
												<!-- 	ng-disabled="!($index==0)&& !($index% {{nextPlanIndex}}==0)" -->
											<!-- </div> -->

											<!-- </div> -->

										</div>
									</div>
									<div class="col-md-2" ng-if="rate.occ3">
										<!-- <div class="col-md-4"> -->
										<md-input-container ng-if="rate.isOpen==true"
											class="containerwidth"> <input
											ng-click="changeRate($index)"
											style="height: 27px !important; width: 100% !important; margin-top: 3px;"
											id="{{occupancyRates.rateCode}}occ3" class="assgnRate occ3"
											data-index="{{$index}}"											
											type="text" ng-value="occupancyRates.occ3Rate"> </md-input-container>
										<md-input-container ng-if="rate.isOpen==false"
											class="containerwidth"> <input
											ng-click="changeRate($index)"
											style="height: 27px !important; width: 100% !important; margin-top: 3px;"
											id="{{occupancyRates.rateCode}}occ3" class="assgnRate occ3"
											data-index="{{$index}}"											
											type="text" ng-value="occupancyRates.occ3Rate" readonly>
										</md-input-container>
									</div>
									<div class="col-md-1" ng-if="rate.occ4">
										<div class="row">
											<!-- <div class="col-md-2" style="margin-left: -45px;"> -->
											<input required type="number" step="any" style="height: 27px;"
												limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
												class="form-control form-control-inline input-medium assgnRoom numRoomOccupancy4"
												data-rateCode="{{occupancyRates.rateCode}}"
												data-roomTypeCode="{{occupancyRates.roomTypeCode}}"
												data-rateid="{{occupancyRates.rateId}}"
												ng-model="occupancyRates.occ4Val"
												data-roomtypeid="{{occupancyRates.roomTypeId}}"
												data-occ="occ4" id="{{occupancyRates.rateCode}}occ4" min="0"
												max="4999" ng-pattern="/^1234$/"												
												numbers-only />

											<!-- </div> -->

										</div>
									</div>

									<div class="col-md-1" ng-if="rate.occ4">
										<!-- <div class="col-md-4"> -->
										<md-input-container ng-if="rate.isOpen==true"
											class="containerwidth"> <input
											ng-click="changeRate($index)"
											style="height: 27px !important; width: 100% !important; margin-top: 3px;"
											id="{{occupancyRates.rateCode}}occ4" class="assgnRate occ4"
											data-index="{{$index}}"										
											type="text" ng-value="occupancyRates.occ4Rate"> </md-input-container>
										<md-input-container ng-if="rate.isOpen==false"
											class="containerwidth"> <input
											ng-click="changeRate($index)"
											style="height: 27px !important; width: 100% !important; margin-top: 3px;"
											id="{{occupancyRates.rateCode}}occ4" class="assgnRate occ4"
											data-index="{{$index}}"										
											type="text" ng-value="occupancyRates.occ4Rate" readonly>
										</md-input-container>
										<!-- </div> -->
									</div>

								</div>

							</div>


							<%-- <div class="col-md-12">
								<div class="col-sm-3" ng-if="rate.occ1">
									<div class="row">
										<md-input-container ng-if="rate.isOpen==true"> <label class="rate_label">Single</label>
										<input  id="{{rate.rateCode}}occ1" class="assgnRate" type="text" ng-click="currencyNeed1(rate.rateId,rate.totalOcc1Rate)" ng-value="rate.totalOcc1Rate">${currencySymbol}
										</md-input-container>
										<md-input-container ng-if="rate.isOpen==false"> <label class="rate_label">Single</label>
										<input  id="{{rate.rateCode}}occ1" class="assgnRate" type="text" ng-click="currencyNeed1(rate.rateId,rate.totalOcc1Rate)" ng-value="rate.totalOcc1Rate" readonly>${currencySymbol}
										</md-input-container>
									</div>
									<div class="row">
										<input required type="number" step="any"
											limit-room="{{availableRooms}}" max-rooms="{{roomMax}}"
											class="form-control form-control-inline input-medium assgnRoom"
											id="{{rate.rateCode}}occ1" data-rateid="{{rate.rateId}}"
											data-rateCode="{{rate.rateCode}}"
											data-roomTypeCode="{{rate.roomTypeCode}}"
											data-roomtypeid="{{rate.roomTypeId}}" ng-model="rate.occ1Val"
											data-occ="occ1" min="0" max="4999" ng-pattern="/^1234$/" />
									</div>
								</div>
								<div class="col-sm-3" ng-if="rate.occ2">
									<div class="row">
										<md-input-container ng-if="rate.isOpen==true"> <label class="rate_label">Double</label>
										<input id="{{rate.rateCode}}occ2" class="assgnRate" type="text" ng-click="currencyNeed2(rate.rateId,rate.totalOcc2Rate)" ng-value="rate.totalOcc2Rate">${currencySymbol}
										</md-input-container>
										<md-input-container ng-if="rate.isOpen==false"> <label class="rate_label">Double</label>
										<input id="{{rate.rateCode}}occ2" class="assgnRate" type="text" ng-click="currencyNeed2(rate.rateId,rate.totalOcc2Rate)" ng-value="rate.totalOcc2Rate" readonly>${currencySymbol}
										</md-input-container>
									</div>

									<div class="row">
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
								</div>
								<div class="col-sm-3" ng-if="rate.occ3">
									<div class="row">
										<md-input-container ng-if="rate.isOpen==true"> <label class="rate_label">Trile</label>
										<input id="{{rate.rateCode}}occ3" class="assgnRate" type="text" ng-click="currencyNeed3(rate.rateId,rate.totalOcc3Rate)" ng-value="rate.totalOcc3Rate">${currencySymbol}
										</md-input-container>
										<md-input-container ng-if="rate.isOpen==false"> <label class="rate_label">Trile</label>
										<input id="{{rate.rateCode}}occ3" class="assgnRate" type="text" ng-click="currencyNeed3(rate.rateId,rate.totalOcc3Rate)" ng-value="rate.totalOcc3Rate" readonly>${currencySymbol}
										</md-input-container>
									</div>

									<div class="row">
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
								</div>
								<div class="col-sm-3" ng-if="rate.occ4">
									<div class="row">
										<md-input-container ng-if="rate.isOpen==true"> <label class="rate_label">Quad</label>
										<input  id="{{rate.rateCode}}occ4" class="assgnRate" type="text" ng-click="currencyNeed4(rate.rateId,rate.totalOcc4Rate)" ng-value="rate.totalOcc4Rate" 
										>${currencySymbol}
										</md-input-container>
										<md-input-container ng-if="rate.isOpen==false"> <label class="rate_label">Quad</label>
										<input  id="{{rate.rateCode}}occ4" class="assgnRate" type="text" ng-click="currencyNeed4(rate.rateId,rate.totalOcc4Rate)" ng-value="rate.totalOcc4Rate" 
										readonly>${currencySymbol}
										</md-input-container>
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


 --%>

						</div>
					</div>
					</uib-accordion> </md-content> </md-tab> <!--  <md-tab label="Picture"> <md-content class="md-padding"> -->


					<!-- 	<div id="carousel-example-generic" class="carousel slide"
						data-ride="carousel">
						Indicators
						<ol class="carousel-indicators">
							<li data-target="#carousel-example-generic" data-slide-to="0"
								class="active"></li>
							<li data-target="#carousel-example-generic" data-slide-to="1"></li>
							<li data-target="#carousel-example-generic" data-slide-to="2"></li>
						</ol> --> <!-- Wrapper for slides --> <!-- 		<div class="carousel-inner" role="listbox">
							<div ng-if="roomTypeImages.image1!=null" class="item active">
								<img src="{{roomTypeImages.image1}}" alt="...">
								<div class="carousel-caption">...</div>
							</div>
							<div ng-if="roomTypeImages.image2!=null"
								class="item {{roomTypeImages.image1===null?'active':''}}">
								<img src="{{roomTypeImages.image2}}" alt="...">
								<div class="carousel-caption">...</div>
							</div>
							<div ng-if="roomTypeImages.image3!=null"
								class="item {{(roomTypeImages.image1===null && roomTypeImages.image2===null)?'active':''}}">
								<img src="{{roomTypeImages.image3}}" alt="...">
								<div class="carousel-caption">...</div>
							</div>
							<div ng-if="roomTypeImages.image4!=null"
								class="item {{(roomTypeImages.image1===null && roomTypeImages.image2===null && roomTypeImages.image3===null)?'active':''}}">
								<img src="{{roomTypeImages.image4}}" alt="...">
								<div class="carousel-caption">...</div>
							</div>
						</div> --> <!-- Controls --> <a class="left carousel-control"
						href="#carousel-example-generic" role="button" data-slide="prev">
						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a> <a class="right carousel-control" href="#carousel-example-generic"
						role="button" data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>


				<!-- </md-content> </md-tab> -->
				<!-- <md-tab label="Features"> <md-content class="md-padding">
					<h1 class="md-display-2">Features</h1>
					<p>Integer turpis erat,</p>

					</md-content> </md-tab>  -->
				<!-- </md-tabs> </md-content> -->
				<!-- </div> -->
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
		tabindex="-1" id="roomAssignmyModal" class="modal fade">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Available Rooms</h4>
				</div>
				<div class="modal-body" style="overflow-y: auto;">


					<div class="roomsDiv">
						<md-content style="max-height: 240px;">
						<section>
							<md-subheader class="md-primary">Rooms
							{{roomTypeCode}} <label class="asgnd_room_label">Assigned
								: {{roomDetails.roomNumber}}</label></md-subheader>
							<!-- ----------ROOM DETAILS TABLE---------- -->
							<table>
								<tr ng-repeat="available in availableRooms"
									ng-if="checkAssigned(available.number)">

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
							</table>
						</section>
						</md-content>

						<div class="roomFeaturesDiv">
							<md-content style="height: 125px;">
							<section>
								<md-subheader class="md-primary">Features</md-subheader>
								<div ng-repeat="avail in availableRooms"
									ng-if="avail.number==roomDetails.roomNumber">
									<div class="col-sm-6" ng-repeat="ftr in avail.roomFeatureList ">
										<label><i class="fa fa-hand-o-right"
											aria-hidden="true"></i> {{ftr.feature}}</label>
									</div>
								</div>
							</section>
							</md-content>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button id="assignroomBtn" type="button" class="btn btn-success"
						ng-click="assignRoomNumber()">Assign</button>
					<button id="cancelExpDepartBtn" type="button"
						class="btn btn-default rbtnClose"
						ng-click="cancelRoomNumberPopUp();">
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
										class="md-1-line"
										ng-repeat="general in availDiscounts.general">
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
												aria-label="Checkbox 1" data-discCode="{{plan.code}}"
												data-discValue="{{plan.isPc?plan.discPc:plan.discAmount}} {{plan.isPc ? '%' : currency }}"
												ng-model="disc.planBased[plan.id]"
												ng-true-value="{{plan.rateId}}" ng-false-value="">
											<label>{{plan.code}}</label> <span class="md-subhead">(
												{{plan.name}} )</span> </md-checkbox>
										</div>
										<div class="col-md-4">{{plan.isPc
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
						class="btn btn-default rbtnClose"
						ng-click="cancelCalenderPopUp();">
						<spring:message code="pms.btn.cancel" />
					</button>

				</div>
			</div>
		</div>
	</div>


	<!-- customer History -->

	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" id="customerDetailsModel" class="modal fade">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Guest History</h4>
				</div>
				<div class="modal-body" style="overflow-y: auto;">
					<div class="col-md-12">
						<div class="col-md-2">
							<label style="font-size: medium;"><b>Guest Name :</b></label>
						</div>
						<div class="col-md-6">
							<label style="font-size: medium;"><b>{{customerData.salutation}}
									{{customerData.firstName}} {{customerData.lastName}}</b></label>
						</div>
						<div class="col-md-4"></div>
					</div>
					<!-- modal data comes here -->
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
