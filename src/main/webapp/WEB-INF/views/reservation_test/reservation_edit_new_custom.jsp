<jsp:directive.include file="../common/includes/page_directives.jsp" />
<script type="text/javascript"
	src="https://cdn.rawgit.com/AlphaGit/ng-pattern-restrict/master/src/ng-pattern-restrict.min.js"></script>
<div ng-cloak>
	<div class="selectedRoomsStatus row" ng-show="showAssignedWidget">
		<div class="floatingWidgetDtls" data-slide-toggle="fBox"
			data-slide-toggle-duration="500">
			<uib-accordion close-others="oneAtATime">
			<div uib-accordion-group class="panel-default roomtypeNameHdr"
				ng-repeat="assignedType in AssignedRoomData" is-open="status.open">
				<uib-accordion-heading>
				{{assignedType.roomTypeCode}} : Assigned - {{assignedType.total}} <i
					class="pull-right glyphicon"
					ng-class="{'glyphicon-chevron-down': status.open, 'glyphicon-chevron-right': !status.open}"></i>
				</uib-accordion-heading>
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
	<md-content class="md-padding mainTabContent"> <md-tabs
		md-dynamic-height md-border-bottom md-selected="data.selectedIndex"
		md-align-tabs="{{data.bottom ? 'bottom' : 'top'}}"> <md-tab
		ng-if="resv_edit" id="tab1" ng-disabled="data.firstLocked"> <md-tab-label>Reservation
	Details</md-tab-label> <md-tab-body>
	<div class="col-lg-12 col-md-12 col-sm-12 search-div">
		<form name="hdrForm" ng-submit="hdrForm.$valid && getroomDetails()">
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-6">
						<div class="md-inline-form">
							<md-content layout-padding class="searchFormDiv">
							<div>
								<div layout-gt-xs="row">
									<md-input-container class="searchFormInput">
									<label>Nights</label> <input required type="number" step="any"
										ng-model="resv.hdr.numNights" disabled /> </md-input-container>
									<md-input-container class="searchFormInput">
									<label>Room</label> <input required type="number" step="any"
										ng-model="resv.hdr.numRooms" disabled /> </md-input-container>
									<md-input-container class="searchFormInput">
									<md-button class="md-primary" aria-label="add roms"
										ng-click="addRoom($event)"> <i class="fa fa-plus"
										aria-hidden="true"></i> Rooms </md-button> </md-input-container>
								</div>
								<div layout="row">


									<md-input-container class="searchFormInput" flex="50">
									<label>Adults</label> <input required type="number" step="any"
										ng-model="resv.hdr.numAdults" min="1" max="200" limit-to="200" />
									</md-input-container>

									<md-input-container class="searchFormInput" flex="50">
									<label>Children</label> <input type="number" step="any"
										ng-model="resv.hdr.numChildren" min="0" max="200"
										limit-zero="200" /> </md-input-container>

									<md-input-container class="searchFormInput" flex="50">
									<label>Infants</label> <input type="number" step="any"
										ng-model="resv.hdr.numInfants" min="0" max="200"
										limit-zero="200" /> </md-input-container>
								</div>
							</div>
							</md-content>
						</div>
					</div>
					<div
						class="col-lg-6 col-md-56 col-sm-6 col-xs-12 search-calendar-div">
						<div class="form-group">
							<md-content layout-padding class="searchFormDiv">
							<div layout-gt-xs="row">
								<md-input-container class="searchFormInput">
								<label>Arrival date</label> <md-datepicker
									ng-model="resv.hdr.minArrDate" disabled></md-datepicker> </md-input-container>
								<md-input-container> <md-button
									class="md-warn" ng-click="changeArrival()">Change
								Arrival Date</md-button></md-input-container>
							</div>
							<div layout-gt-xs="row">
								<md-input-container class="searchFormInput">
								<label>Depart date</label> <md-datepicker
									ng-model="resv.hdr.maxDepartDate" disabled></md-datepicker> </md-input-container>
								<md-input-container> <label>Cutoff
									date</label> <md-datepicker ng-model="resv.hdr.cutOffDate" disabled></md-datepicker>
								</md-input-container>
							</div>
							</md-content>
						</div>

					</div>
				</div>
				<div class="row">
					<div class="col-lg-6 col-md-6 col-sm-6">
						<md-content layout-padding>
						<div>
							<div layout-gt-sm="row">
								<md-input-container class="md-block" flex-gt-sm>
								<label>First name</label> <input required
									onclick="this.focus();this.select()"
									ng-model="resv.hdr.resvByFirstName"> </md-input-container>

								<md-input-container class="md-block" flex-gt-sm>
								<label>Last Name</label> <input
									onclick="this.focus();this.select()"
									ng-model="resv.hdr.resvByLastName"> </md-input-container>
							</div>

							<div layout-gt-sm="row">
								<md-input-container class="md-block" flex-gt-sm>
								<!-- 2246 digna 20180628 begin --> <label>Email </label> <input
									ng-model="resv.hdr.resvByMail" type="email"
									onclick="this.focus();this.select()" 
									ng-pattern="/^$|^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/" >
								</md-input-container>

								<md-input-container class="md-block" flex-gt-sm>
								<label>Phone</label> <!-- <input ng-model="resv.hdr.resvByPhone" ng-pattern="[0-9]{10}"
									onclick="this.focus();this.select()"
									 /> -->
									 <input ng-model="resv.hdr.resvByPhone" maxlength="15"name="phone" class="form-control" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" 
														required>
									  </md-input-container>
							</div>
							<md-input-container class="md-block"> <label>Address</label>
							<input ng-model="resv.hdr.resvByAddress"
								onclick="this.focus();this.select()"> </md-input-container>

							<md-input-container class="md-block"> <label>Reserved by
								</label> <input ng-model="resv.hdr.resvFor"
								onclick="this.focus();this.select()" required> </md-input-container>


						</div>
						</md-content>
					</div>
					<div class="col-lg-6 col-md-6 col-sm-6">
						<md-content layout-padding>
						<div>


							<md-input-container class="md-block"> <md-checkbox
								name="tos" ng-model="trCrp.travelCorp"
								ng-change="getTaCorpList()"> Reserved By Travel
							Agent/ Corp </md-checkbox>
							<div ng-if="trCrp.travelCorp">
								<div class="trvlDiv">
									<div class="new">
										{{trvlGroup}}
										<md-radio-group ng-model="trCrp.trvlGroup"
											ng-change="getTaCorpList();"> <md-radio-button
											value="trvl" class="md-primary">Travel
										Agent</md-radio-button> <md-radio-button class="md-primary" value="corp">
										Corporate </md-radio-button> </md-radio-group>
									</div>

									<!-- <md-input-container class="searchFormInput" flex="70" id="ta_width"
										ng-if="trCrp.trvlGroup=='trvl'"> <label>Travel
										Agent</label> <md-select ng-model="trCrp.tvlcrpId" 
										 class="md_slect_div_edit" required>
									<md-option ng-repeat="trvlag in trCrp.ta" value="{{trvlag.id}}">{{trvlag.name}}</md-option>
									</md-select> </md-input-container> -->
									<div class="col-sm-4" id="ta_width"
										ng-if="trCrp.trvlGroup=='trvl'">
										<label>Travel Agent</label> <select ng-model="trCrp.tvlcrpId"
											style="margin-top: 25px;" class="form-control" required>
											<option ng-repeat="trvlag in trCrp.ta" value="{{trvlag.id}}">{{trvlag.name}}</option>
										</select>
									</div>
									<div class="col-sm-4" id="ta_width"
										ng-if="trCrp.trvlGroup=='corp'">
										<label>Corporate</label>
										<!-- <md-select ng-model="trCrp.tvlcrpId" class="md_slect_div_edit" required> <md-option
										ng-repeat="trvlCorp in trCrp.corp" value="{{trvlCorp.id}}">{{trvlCorp.name}}</md-option>
									</md-select> -->
										<select ng-model="trCrp.tvlcrpId" class="form-control"
											style="margin-top: 25px;" required>
											<option ng-repeat="trvlCorp in trCrp.corp"
												value="{{trvlCorp.id}}">{{trvlCorp.name}}</option>
										</select>
									</div>

								</div>
							</div>
							</md-input-container>
						</div>
						</md-content>
					</div>

				</div>
				<div class="row">
					<div class="row availabilityCalenderDiv pull-right">
						<md-button class="md-raised md-primary" id="chkAvail"
							type="submit" ng-click="guestPag();">Next</md-button>
					</div>
				</div>
			</div>
		</form>
	</div>
	</md-tab-body> </md-tab> <md-tab id="tab1" ng-if="resv_edit" ng-disabled="data.secondLocked"
		ng-click="getroomDetails()"> <md-tab-label>Room
	Details</md-tab-label> <md-tab-body>
	<form name="guestForm" ng-submit="guestForm.$valid && finalPage()">
		<input type="hidden" id="gForm" value="{{guestForm.$dirty}}" />
		<div ng-if="data.selectedIndex==1"
			ng-repeat="roomDtl in resv.room track by $index" class="room_dtl_div">
			<md-subheader class="md-no-sticky availRatePlans" id="bgColour"
				ng-if="roomDtl.roomTypeId!=resv.room[$index-1].roomTypeId">{{roomDtl.roomTypeCode}}

			<div class="edit_tag pull-right">
				<md-menu md-offset="0 -7"> <i
					class="fa fa-pencil aria-hidden="
					true"
				ng-click="loadAvailableRoomTypes(roomDtl.roomTypeId);$mdMenu.open($event)">&nbsp
					change</i> <md-menu-content width="6"> <md-menu-item
					ng-repeat="availRoomTypes in roomTypesAvailable"> <md-button
					ng-disabled="availRoomTypes.roomTypeId==checkin.hdr.roomTypeId"
					ng-click="showDetails(availRoomTypes.roomTypeCode,availRoomTypes.roomTypeId,availRoomTypes.availRoom,2)">
				<span md-menu-align-target>{{availRoomTypes.roomTypeCode}}</span> <span
					class="available_span"> Available:
					{{availRoomTypes.availRoom}}</span> </md-button> </md-menu-item> </md-menu-content> </md-menu>

			</div>
			</md-subheader>
			<md-subheader class="md-no-sticky ratePlanSubHdr" id="bgColour"
				ng-if="roomDtl.rateId!=resv.room[$index-1].rateId">{{roomDtl.rateCode}}
			<div class="edit_tag pull-right"
				ng-click="showRoomRates(roomDtl.rateId,roomDtl.occupancy,roomDtl.roomTypeCode)">
				<i class="fa fa-pencil" aria-hidden="true"> &nbsp change</i>
			</div>
			</md-subheader>
			<div class="guest_box">
				<div class="checkIn_div ps_rltv">
					<div class="delete_room_btn" ng-if="!roomDtl.isDeleted"
						ng-click="deleteRoom(roomDtl.rowIndex)">
						<i class="fa fa-trash" aria-hidden="true"></i>
					</div>
					<div class="delete_room_btn" ng-if="roomDtl.isDeleted">room
						deleted</div>
					<md-content> <md-tabs md-dynamic-height
						md-border-bottom> <md-tab
						label="Guest Info : {{$index+1}}" ng-disabled="roomDtl.isDeleted">
					<button class="button md-raised md-warn md-button md-ink-ripple"
						ng-if="!roomDtl.isDeleted" type="button"
						id="sameasabove-{{$index}}" ng-click="sameasabove($index)">
						<span class="ng-scope"> Same As Above </span>
					</button>
					<md-content ng-if="!roomDtl.isDeleted" class="formTab"
						class="md-padding">
					<div class="col-md-12 {{roomDtl.isDeleted?'room_deleted':''}}">
						<div class="col-md-9">
							<div layout-gt-sm="row" class="input_div">
								<div>
									<label>Title</label>
									<md-input-container class="md-block chInp title">
									<select ng-model="roomDtl.salutation"
										ng-options="salute for salute in salutations"
										class="form-control">
									</select> </md-input-container>
								</div>
								<div>
									<label>First name<span class="red">*</span></label>
									<div class="first_name">
										<md-input-container class="md-block chInp"
											style="width: 100%;" flex-gt-sm>
										<div>
											<input required onclick="this.focus();this.select()"
												ng-model="roomDtl.firstName" ng-disabled="roomDtl.isDeleted"
												type="text" required>
										</div>
										</md-input-container>
									</div>
								</div>
								<div class="lNameTab">
									<label>Last Name</label>
									<md-input-container class="md-block chInp" flex-gt-sm>
									<div class="last_name">
										<input ng-disabled="roomDtl.isDeleted" type="text"
											onclick="this.focus();this.select()"
											ng-model="roomDtl.lastName" />
									</div>
									</md-input-container>
								</div>
								<div class="guestTab">
									<label>Guest name</label>
									<md-input-container class="md-block chInp" flex-gt-sm>
									<div class="address">
										<input ng-model="roomDtl.guestName"
											ng-disabled="roomDtl.isDeleted" maxlength="50">
									</div>
									</md-input-container>
								</div>
							</div>

							<div layout-gt-sm="row" class="input_div" id="emailRow">
								<div>
									<label>Email</label>
									<md-input-container class="md-block chInp" flex-gt-sm>
									<div class="email_div">
										<input ng-disabled="roomDtl.isDeleted"
											onclick="this.focus();this.select()" ng-model="roomDtl.email"
											 type="email"
											ng-pattern="/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/"
											class="form-control" title="eg.abc@gmail.com">
									</div>
									</md-input-container>
								</div>
								<div>
									<label>Phone</label>
									<md-input-container class="md-block chInp" flex-gt-sm>
									<div class="last_name">
										<input ng-disabled="roomDtl.isDeleted" maxlength="15"
											onclick="this.focus();this.select()" ng-model="roomDtl.phone"
											>
									</div>
									</md-input-container>
								</div>
								<div>
									<label>Address<span class="red">*</span></label>
									<md-input-container class="md-block chInp" flex-gt-sm>
									<div class="address">
										<input ng-disabled="roomDtl.isDeleted"
											onclick="this.focus();this.select()"
											ng-model="roomDtl.address" required>
									</div>
									</md-input-container>
								</div>
							</div>


							<!-- <div layout-gt-sm="row" class="input_div">
							<md-input-container class="md-block chInp" ng-if="roomDtl.nationality!=''&& roomDtl.nationality!=null" flex-gt-sm>
							<label>Nationality</label> <input ng-disabled="roomDtl.isDeleted"
								ng-model="roomDtl.nationality"> </md-input-container>
							<div ng-controller="reservationEditCtrl as ctrl" layout="column" id="autoCom2" ng-if="roomDtl.nationality==''|| roomDtl.nationality==null">
								  <md-content class="md-padding">
								    <form ng-submit="$event.preventDefault()">
								    <md-autocomplete  ng-model="roomDtl.nationality"
								          ng-disabled="ctrl.isDisabled"
								          md-no-cache="ctrl.noCache"
								          md-selected-item="ctrl.selectedItem"
								          md-search-text-change="ctrl.searchTextChange(ctrl.searchText)"
								          md-search-text="ctrl.searchText"
								          md-selected-item-change="ctrl.selectedItemChange(item)"
								          md-items="item in ctrl.querySearch(ctrl.searchText)"
								          md-item-text="item.display"
								          md-min-length="0"
								          placeholder="Nationality">
								        <md-item-template>
								          <span md-highlight-text="ctrl.searchText" md-highlight-flags="^i">{{item.display}}</span>
								        </md-item-template>
								        <md-not-found>
								          No states matching "{{ctrl.searchText}}" were found.
								          <a ng-click="ctrl.newState(ctrl.searchText)">Create a new one</a>
								        </md-not-found>
								      </md-autocomplete>
							</div>
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>Passport No.</label> <input onclick="this.focus();this.select()"
								ng-disabled="roomDtl.isDeleted" ng-model="roomDtl.passportNo"></md-input-container>

							<md-input-container class="md-block chInp"
								ng-if="roomDtl.passportNo!=null && roomDtl.passportNo!=''" flex-gt-sm>
							<label>Expiry</label> <md-datepicker required ng-disabled="roomDtl.isDeleted"
								ng-model="roomDtl.passportExpiryOn" md-placeholder="Enter date"
								md-open-on-focus></md-datepicker>
						</div> -->

							<div layout-gt-sm="row" class="div_nationality">
								<div>
									<label>Nationality<span class="red">*</span></label>
									<md-input-container class="md-block chInp">
									<form ng-submit="$event.preventDefault()">
										<div class="nationality">
											<md-select ng-model="roomDtl.nationality"
												class="md_slect_div_edit"
												ng-change="loadState($index,roomDtl.nationality)" required>
											<md-option ng-repeat="country in countryList "
												ng-value="country.name">{{country.name}}</md-option> </md-select>
										</div>
									</md-input-container>

									<span id="country_error" ng-if="!roomDtl.nationality.length">
										Please select country</span>
								</div>

								<div id="stateTab">
									<label>State<span class="red">*</span></label>
									<md-input-container class="md-block chInp">
									<div class="state">
										<md-select ng-model="roomDtl.state" class="md_slect_div_edit"
											id="state" required> <md-option
											ng-repeat="state in stateList[$index]" ng-value="state.name">{{state.name}}</md-option>
										</md-select>
									</div>
									<span id="state_error" ng-if="!roomDtl.state.length">
										Please select state</span> </md-input-container>
								</div>
								<div>

									<label>VAT Account No:</label>
									<md-input-container class="md-block chInp" flex-gt-sm>
									<div class="address">
										<input ng-disabled="roomDtl.isDeleted"											
											ng-model="roomDtl.gstno">
									</div>
									</md-input-container>
								</div>

							</div>

							<div layout-gt-sm="row" class="div_nationality">
								<div class="adult_class">
									<label id="adults_position">Adults<span class="red">*</span></label>
									<md-input-container class="md-block chInp" flex="50">
									<div class="count">
										<input required type="number" step="any"
											ng-model="roomDtl.numAdults" min="1" max="200" limit-to="200" />
									</div>
									</md-input-container>
								</div>
								<div class="adult_class">
									<label id="children_position">Children</label>
									<md-input-container class="md-block chInp" id="marChild"
										flex="50">
									<div class="count">
										<input type="number" step="any" ng-model="roomDtl.numChildren"
											min="0" max="200" limit-zero="200" />
									</div>
									</md-input-container>
								</div>
								<div class="adult_class" id="infant_id">
									<label id="infant_position">Infants</label>
									<md-input-container class="md-block chInp" id="marInfant"
										flex="50">
									<div class="infant">
										<input type="number" step="any" ng-model="roomDtl.numInfants"
											min="0" max="200" limit-zero="200" />
									</div>
									</md-input-container>
								</div>
								<div class="col-sm-7" id="passportTab">
									<label class="">Passport No.</label>
									<div class="last_name">
										<md-input-container class="md-block chInp"
											style="width: 100%;" "flex-gt-sm> <input
											ng-model="roomDtl.passportNo"></md-input-container>
									</div>
								</div>
								<div class="col-sm-9" id="expiryTab">
									<label class="lbl_expiry">Expiry</label>
									<div id="expiry_position">
										<md-input-container class="md-block chInp" flex-gt-sm>
										<md-datepicker ng-disabled="roomDtl.isDeleted"
											ng-model="roomDtl.passportExpiryOn"
											md-placeholder="Enter date"></md-datepicker></md-input-container>
									</div>
								</div>
							</div>

							<div layout-gt-sm="row" class="input_div">
								<div class="col-sm-12">
									<label>Gender<span class="red">*</span></label>
									<md-input-container class="md-block chInp" flex-gt-sm>
									<md-radio-group ng-disabled="roomDtl.isDeleted"
										ng-model="roomDtl.gender" required>
									<div class="col-md-6">
										<md-radio-button value="Male" class="md-primary">Male</md-radio-button>
									</div>
									<div class="col-md-6">
										<md-radio-button value="Female">Female</md-radio-button>
									</div>
									<span id="gender_error" ng-if="!roomDtl.gender.length">
										Please select gender</span> </md-radio-group> </md-input-container>
								</div>
								<div class="col-sm-9">
									<label>Remarks</label>
									<div class="remarks">
										<md-input-container class="md-block chInp" flex-gt-sm>
										<input ng-disabled="roomDtl.isDeleted"
											onclick="this.focus();this.select()"
											ng-model="roomDtl.remarks"> </md-input-container>
									</div>
								</div>
							</div>
						</div>
						<div class="col-md-3 romnumDiv">
							<div class="row roomAssign">
								<div class="room_assigned">Room Assigned :
									{{roomDtl.roomNumber !=""?roomDtl.roomNumber:'Not Assigned'}}</div>
								<md-button class="md-raised md-warn room_assign_btn"
									ng-click="getVacantRooms(roomDtl.roomTypeId,roomDtl.rowIndex)">{{roomDtl.roomNumber
								!=""?'Change':'Assign'}} Room <i class="fa fa-bed"
									aria-hidden="true"></i></md-button>

							</div>
						</div>
						<!-- 					<div class="row applyall"><button class="md-raised md-primary md-button md-ink-ripple"
						 type="button" id="apply_all" ng-click="applyall($index)" hidden = "hidden" ><span class="ng-scope"  >
						Apply all </span></button></div> -->

						<div class="row applyall"
							ng-if="$index == 0 || showApplyAll($index)">
							<button
								class="button md-raised md-primary md-button md-ink-ripple"
								type="button" id="applyall" ng-click="applyall($index)">
								<span class="ng-scope"> Apply all </span>
							</button>
						</div>

					</div>
					</md-content> <md-content ng-show="roomDtl.isDeleted">
					<div class="roomDeleted">ROOM DELETED</div>
					</md-content> </md-tab> </md-tabs> </md-content>
				</div>
			</div>
		</div>

		<div class="row availabilityCalenderDiv pull-right">
			<button class="button buttonForm">
				<md-button class="md-raised md-primary" id="finalPage" type="submit"
					ng-click="alertFinal()">Next</md-button>
			</button>
		</div>
	</form>
	</md-tab-body> </md-tab> <!--2073 digna 20180626 begin --> <md-tab id="tabRateSummary"
		ng-disabled="data.fourthLocked" ng-if="data.selectedIndex==2">
	<!--2073 digna 20180626 end --> <md-tab-label>SUMMARY</md-tab-label> <md-tab-body>
	<div class="col-md-6">

		<div class="guestDtlDiv formDiv row md-inline-form">
			<div class="sumDateDiv row">
				<div class="col-md-6">
					<div class="col-sm-5">
						<label>Check In :</label>
					</div>
					<div class="col-sm-1">{{resv.hdr.minArrDate |
						date:'MM/dd/yyyy'}}</div>
				</div>
				<div class="col-md-8">
					<div class="col-sm-4">
						<label>Check Out :</label>
					</div>
					<div class="col-sm-5" style="margin-left: -12px;">{{resv.hdr.maxDepartDate
						| date:'MM/dd/yyyy'}}</div>
				</div>
			</div>
			<md-content layout-padding>
			<div>

				<md-input-container class="md-block"> <label>Special
					Requests</label> <input ng-model="resv.hdr.specialRequests"
					onclick="this.focus();this.select()"> </md-input-container>

				<md-input-container class="md-block"> <label>Remarks</label>
				<input ng-model="resv.hdr.remarks"
					onclick="this.focus();this.select()"> </md-input-container>
			</div>
			</md-content>
		</div>

	</div>
	<div class="col-md-6 reservSummary">

		<div layout="column">
			<md-card> <md-card-content>

			<div class="sumRateByRoomTypeDiv row">
				<md-toolbar layout="row" class="md-blue-3"
					style="background: #00A8B3;">
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
					<div class="col-md-3  ng-binding" style="text-align: right;">${currencySymbol}&nbsp;{{rateArr.total}}
					</div>
				</div>

				<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-list> </md-content>
			</div>


			<div class="sumDiscountDiv row">
				<md-toolbar layout="row" class="md-blue-3"
					style="background: #00A8B3;">
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
				<md-toolbar layout="row" class="md-blue-3"
					style="background: #00A8B3;">
				<div class="md-toolbar-tools">
					<span>Rate Summary</span>
				</div>
				</md-toolbar>
				<md-list> <md-list-item class="md-1-line">
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Sub Total</label>
					</div>
					<div class="col-md-3 ng-binding" style="text-align: right;">${currencySymbol}&nbsp;{{rateSummary.subTotal}}
					</div>
				</div>
				<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Service Charge</label>
					</div>
					<div class="col-md-3 ng-binding" style="text-align: right;">${currencySymbol}&nbsp;{{rateSummary.totalSCharge}}
					</div>
				</div>
				<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line"
					ng-if="rateSum.discTotal!=0">
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Disc</label>
					</div>
					<div class="col-md-3 ng-binding" style="text-align: right;">${currencySymbol}&nbsp;{{rateSummary.discTotal}}
					</div>
				</div>
				<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
				<div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Tax</label>
					</div>
					<div class="col-md-3 ng-binding" style="text-align: right;">${currencySymbol}&nbsp;{{rateSummary.taxTotal}}
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
					<div class="col-md-3 ng-binding" style="text-align: right;">${currencySymbol}{{rateSummary.grantTotal}}
						(Incl.Txs)</div>
				</div></md-list>
			</div>

			</md-card-content> </md-card>
			<div class="bottom_div">
				<md-button class="md-raised md-primary" ng-disabled="submit_click"
					ng-click="saveReservation()" type="submit">Submit</md-button>
			</div>
		</div>
	</div>
	</md-tab-body> </md-tab> <md-tab id="tabRoomRate" ng-if="add_room"> <md-tab-label>Add
	Rooms</md-tab-label> <md-tab-body>
	<div selected-rooms-info="" class="ng-isolate-scope">
		<div class="room-info-bar-wrapper row div-padding">
			<div class="room-info ng-scope">
				<div class="room-info-bar">
					<div class="col-lg-12 room-info-bar-msg">SELECT ROOM TYPE
						&amp; ASSIGN ROOMS</div>
				</div>
			</div>
		</div>

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
									<span class="">{{roomtype.availRoom}} Rooms Available</span>

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
							<md-button class="md-raised md-primary" ng-click="saveRooms();">Add
							Rooms <i class="fa fa-chevron-circle-right" aria-hidden="true"></i></md-button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	</md-tab-body> </md-tab> </md-tabs> </md-content>
</div>

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
					<uib-accordion-heading> {{rate.rateCode}} <i
						class="pull-right glyphicon"
						ng-class="{'glyphicon-chevron-right': status.open}"></i> </uib-accordion-heading>
					<div class="rateOccupancy">
						<div class="col-md-12">
							<div class="col-sm-3" ng-if="rate.occ1">
								<div class="row">
									<label class="rate_label">Single</label>
									<div class="rate">{{rate.totalOcc1Rate}}
										${currencySymbol}</div>
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
									<label class="rate_label">Double</label>
									<div class="rate">{{rate.totalOcc2Rate}}
										${currencySymbol}</div>
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
									<label class="rate_label">Tripple</label>
									<div class="rate">{{rate.totalOcc3Rate}}
										${currencySymbol}</div>
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
									<label class="rate_label">Quad</label>
									<div class="rate">{{rate.totalOcc4Rate}}
										${currencySymbol}</div>
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
				</uib-accordion> </md-content></md-tab> <!-- <md-tab label="Picture"> <md-content class="md-padding">


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
							<img ng-src="{{roomTypeImages.image1}}" alt="...">
							<div class="carousel-caption">...</div>
						</div>
						<div class="item" ng-if="roomTypeImages.image2!=null">
							<img ng-src="{{roomTypeImages.image2}}" alt="...">
							<div class="carousel-caption">...</div>
						</div>
						<div class="item " ng-if="roomTypeImages.image3!=null">
							<img ng-src="{{roomTypeImages.image3}}" alt="...">
							<div class="carousel-caption">...</div>
						</div>
						<div class="item" ng-if="roomTypeImages.image4!=null">
							<img ng-src="{{roomTypeImages.image4}}" alt="...">
							<div class="carousel-caption">...</div>
						</div>
					</div>

					Controls
					<a class="left carousel-control" href="#carousel-example-generic"
						role="button" data-slide="prev"> <span
						class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
						<span class="sr-only">Previous</span>
					</a> <a class="right carousel-control" href="#carousel-example-generic"
						role="button" data-slide="next"> <span
						class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
						<span class="sr-only">Next</span>
					</a>
				</div>


				</md-content> </md-tab> --> <!-- <md-tab label="Features"> <md-content class="md-padding">
				<h1 class="md-display-2">Features</h1>
				<p>Integer turpis erat,</p>

				</md-content> </md-tab> </md-tabs> </md-content> -->
			</div>
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
						<!-- ----------------------------------------------- -->
						<!-- <md-list> <md-radio-group
							ng-model="roomDetails.roomNumber"> <md-list-item
							ng-repeat="available in availableRooms"
							ng-if="checkAssigned(available.number)"> <md-radio-button
							class="md-primary rcptn_nw_rad"
							ng-disabled="Statusroom(available)"
							aria-label="{{ available.name}}" value="{{available.number}}"></md-radio-button>
						<p ng-if="available.room_status=='Vacant'">

							<label>{{ available.name}}</label> <span class="room_no_span">
								No : {{ available.number}} </span> <span class="room_no_span">
								{{ available.hk1_status}} </span>
						</p>


						<p ng-if="available.room_status!='Vacant'">

							<label>{{ available.name}}</label> <span class="room_no_span">
								No : {{ available.number}} </span> <span class="room_no_span">
								{{ available.room_status}} </span>
						</p>
						</md-list-item> </md-radio-group> </md-list> -->
					</section>
					</md-content>

					<div class="roomFeaturesDiv">
						<md-content style="height: 125px;">
						<section>
							<md-subheader class="md-primary">Features</md-subheader>
							<div ng-repeat="avail in availableRooms"
								ng-if="avail.number==roomDetails.roomNumber">
								<div class="col-sm-6" ng-repeat="ftr in avail.roomFeatureList ">
									<label><i class="fa fa-hand-o-right" aria-hidden="true"></i>
										{{ftr.feature}}</label>
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
	tabindex="-1" id="ratePlanmyModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Available Rate Plans</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<div class="col-md-12">
					<div class="col-sm-6 roomsDiv">
						<md-content style="max-height: 350px;">
						<section>
							<md-subheader class="md-primary">Rate Plans<span
								ng-if="checkin.hdr.roomTypeCode.length>0">
								[{{checkin.hdr.roomTypeCode}}] </span></md-subheader>
							<md-list> <md-radio-group
								ng-model="roomDetails.ratePlanId"> <md-list-item
								ng-repeat="(key,roomrate) in roomRates">
							<p>
								<label>{{ roomrate.rateCode}}</label>
							</p>
							<md-radio-button class="md-primary"
								aria-label="{{ roomrate.rateCode}}" value="{{roomrate.rateId}}"></md-radio-button>
							</md-list-item> </md-radio-group> </md-list>
						</section>
						</md-content>
					</div>

					<div class="col-sm-4 col-md-6 pull-right occupancy_div">
						<div class="roomFeaturesDiv">
							<md-content>
							<section>
								<md-subheader class="md-primary">Occupancy</md-subheader>
								<div ng-repeat="(key,rate) in roomRates"
									ng-if="rate.rateId==roomDetails.ratePlanId">

									<md-list> <md-radio-group
										ng-model="roomDetails.occupancy"> <md-list-item
										ng-if="rate.occ1">
									<p>
										<label>Single <span class="occRate">{{rate.totalOcc1Rate}}
												${currencySymbol}</span></label>
									</p>
									<md-radio-button class="md-primary" aria-label="Single"
										value="1"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ2">
									<p>
										<label>Double <span class="occRate">{{rate.totalOcc2Rate}}
												${currencySymbol}</span></label>
									</p>
									<md-radio-button class="md-primary" aria-label="Double"
										value="2"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ3">
									<p>
										<label>Tripple <span class="occRate">{{rate.totalOcc3Rate}}
												${currencySymbol}</span></label>
									</p>
									<md-radio-button class="md-primary" aria-label="Tripple"
										value="3"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ4">
									<p>
										<label>Quad <span class="occRate">{{rate.totalOcc4Rate}}
												${currencySymbol}</span></label>
									</p>
									<md-radio-button class="md-primary" aria-label="Quad" value="4"></md-radio-button>
									</md-list-item> </md-radio-group> </md-list>
								</div>

							</section>
							</md-content>
						</div>

					</div>
				</div>


			</div>
			<div class="modal-footer">
				<button id="assignroomBtn" type="button" class="btn btn-success"
					ng-click="assignRoomRate()">Assign</button>
				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose" ng-click="cancelRatePlanPopUp();">
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
							<md-radio-group ng-model="disc.group">
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
										<label>discout value ( {{general.isPc ? "%" : currency
											}} )</label> <input id="open{{general.id}}"
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
											ng-model="disc.planBased[plan.id]" data-discountFor = {{plan.discountFor}}
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
