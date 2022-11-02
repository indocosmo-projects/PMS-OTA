<jsp:directive.include file="../common/includes/page_directives.jsp" />
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
		value="Rooms Pending : {{roomsNotAvail-totalRoomsAssigned}}" />
</div>
<div class="row">
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">

			<div class="panel-body">
				<div class="dtls summury">
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.searchFor.arrivalOn" />
							</div>
							<div class="col-md-12 s-body">{{resvHdr.minArrDate |
								date:dateFormat}}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.searchFor.rooms" />
							</div>
							<div class="col-md-12 s-body">{{resvHdr.numRooms}}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.searchFor.nights" />
							</div>
							<div class="col-md-12 s-body">{{resvHdr.numNights}}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.searchFor.bookingBy" />
							</div>
							<div ng-if="resvHdr.corporateId!=0" class="col-md-12 s-body">{{resvHdr.corporateName}}</div>
							<div ng-if="resvHdr.corporateId==0" class="col-md-12 s-body">FIT</div>
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
			<div class="panel-body">
				<md-tabs md-dynamic-height md-border-bottom
					md-selected="data.selectedIndex"
					md-align-tabs="{{data.bottom ? 'bottom' : 'top'}}"> <md-tab
					label="Search" ng-disabled="data.firstLocked">
				<div class="dateSearchDiv">
					<div class="row">
						<div class="col-md-3">
							<h4>Present Arrival Date</h4>
							<md-datepicker ng-model="resvHdr.minArrDate"
								md-placeholder="Enter date" disabled></md-datepicker>
						</div>
						<div class="col-md-3">
							<h4>New Arrival Date</h4>
							<md-datepicker ng-model="newArrivalDate"
								ng-change="arrivalDateChange();" md-placeholder="Enter date"
								md-min-date="arrivalNewMinDate" md-open-on-focus></md-datepicker>
						</div>
						<!-- <div class="col-md-3 avail_btn">
							<md-button class="md-raised md-primary"
								ng-click="arrivalDateChange();">Check Availability</md-button>
						</div> -->
					</div>
				</div>
				<div class="datatableDiv" ng-if="contentLoaded">
					<div class="info-bar-wrapper row div-padding">
						<div class="info ng-scope">
							<div class="info-bar">
								<div class="col-lg-12 info-bar-msg">PLAN</div>
							</div>
						</div>
					</div>
					<div class="plan_table_div cmn_tbl">
						<table datatable="" dt-options="changectrl.dtOptions"
							dt-columns="changectrl.dtColumns"
							dt-instance="changectrl.dtInstance" class="row-border hover"></table>
					</div>
					<div class="statusDiv">
						<div ng-show="showStatus">
							<div class="status col-md-5">
								<span class="status_text_not_avail" ng-if="changeInRoomType"><strong>{{roomsNotAvail}}</strong>
									Rooms are not available on {{newArrivalDate|date:dateFormat}}</span> <span
									class="status_text_avail"
									ng-if="!changeInRoomType && !changeInRatePlan"> Rooms
									are available </span> <span class="status_text_not_avail"
									ng-if="changeInRatePlan"> Rate plan not available</span>
							</div>
							<div ng-show="changeInRoomType" class="col-md-3 pull-right">
								<md-button class="md-raised md-warn"
									ng-click="checkAvailability();">Check Room
								Availability</md-button>
							</div>
						</div>
					</div>
					<div class="disc_table_div" ng-show="showDiscTable">
						<div class="info-bar-wrapper row div-padding">
							<div class="info ng-scope">
								<div class="info-bar">
									<div class="col-lg-12 info-bar-msg">DISCOUNT</div>
								</div>
							</div>
						</div>
						<table datatable="" dt-options="changectrl.dtOptionsDisc"
							dt-columns="changectrl.dtColumnsDisc"
							dt-instance="changectrl.dtInstanceDisc" class="row-border hover"></table>
					</div>
				</div>
				</md-tab> <md-tab id="tab1" ng-if="check_avail"
					ng-disabled="data.secondLocked"> <md-tab-label>Room
				Details</md-tab-label> <md-tab-body>

				<div id="mainWrapper">
					<div class="rooms-view-selected">
						<div rooms-view-selected="" rooms-data="rooms"
							view-type="defaultType" class="ng-isolate-scope">
							<div class="rooms-wrapper">
								<div class="rooms-grid ng-scope">
									<div class="md-padding card_list_div_main">

										<div class="card_list_div"
											ng-repeat="roomtype in roomTypesAvailable">
											<md-card
												md-theme="{{ showDarkTheme ? 'dark-blue' : 'default' }}"
												md-theme-watch> <md-card-title> <md-card-title-text>
											<span class="md-headline">{{roomtype.roomTypeCode}}</span> <span
												class="md-subhead">{{roomtype.availRoom}} Rooms
												Available</span>

											<div class="occupDiv">
												<label ng-if="roomtype.occ1">S</label> <label
													ng-if="roomtype.occ2">D</label> <label
													ng-if="roomtype.occ3">T</label> <label
													ng-if="roomtype.occ4">Q</label>
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

											</md-card-title-text> <md-card-title-media>
											<div class="md-media-lg card-media roomTypeCard">
												<img ng-src="{{roomtype.image}}" alt="...">
											</div>
											</md-card-title-media> </md-card-title> </md-card>
										</div>
									</div>
								</div>
								<div class="bottom_div"></div>
							</div>
						</div>
					</div>
				</div>
				</md-tab-body> </md-tab> <md-tab id="tabRateSummary" ng-disabled="data.thirdLocked">
				<md-tab-label>SUMMARY</md-tab-label> <md-tab-body>
				<div class="col-md-6">

					<div class="guestDtlDiv formDiv row md-inline-form">
						<div class="sumDateDiv row">
							<div class="col-md-6">
								<div class="col-sm-7">
									<label>Check In &nbsp; &nbsp; &nbsp;:</label>
								</div>
								<div class="col-sm-5">{{newArrivalDate | date:dateFormat}}</div>
							</div>
							<div class="col-md-7">
								<div class="col-sm-7">
									<label>Check Out &nbsp; :</label>
								</div>
								<!-- <div class="col-sm-5">{{resvHdr.maxDepartDate |
						date:dateFormat}}</div> -->
								<div class="col-sm-5" id="dateMar">{{newDapartDate |
									date:dateFormat}}</div>

							</div>
						</div>
						<md-content layout-padding>
						<div>

							<md-input-container class="md-block"> <label>Special
								Requests</label> <input enabled ng-model="resvHdr.specialRequests">
							</md-input-container>

							<md-input-container class="md-block"> <label>Remarks</label>
							<input enabled ng-model="resvHdr.remarks"> </md-input-container>
						</div>
						</md-content>
					</div>

				</div>
				<div class="col-md-6 reservSummary">

					<div layout="column">
						<md-card> <md-card-content>

						<div class="sumRateByRoomTypeDiv row">
							<md-toolbar layout="row" class="md-blue-3">
							<div class="md-toolbar-tools" id="bgColour">
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
								<div class="col-md-3" style="text-align: right">
									${currencySymbol} &nbsp; {{rateArr.total}}</div>
							</div>

							<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-list> </md-content>
						</div>


						<div class="sumDiscountDiv row" id="discountCheck"
							style="display: none;">
							<md-toolbar layout="row" class="md-blue-3">
							<div class="md-toolbar-tools" id="bgColour">
								<span>Discounts</span>
							</div>
							</md-toolbar>
							<md-content class="discountListDiv"> <md-list>
							<md-list-item class="md-1-line"
								ng-repeat="discDisp in discDispList">
							<div class="md-list-item-text rateByRoomList">
								<div class="col-md-8">
									<label>{{discDisp.code}}</label>
								</Div>
								<div class="col-md-3">{{discDisp.value}}</div>
							</div>
							<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-list> </md-content>
						</div>


						<div class="sumFinalRateDiv row">
							<md-toolbar layout="row" class="md-blue-3">
							<div class="md-toolbar-tools" id="bgColour">
								<span class="fontType">Rate Summary</span>
							</div>
							</md-toolbar>
							<md-list> <md-list-item class="md-1-line">
							<div class="md-list-item-text rateByRoomList">
								<div class="col-md-8">
									<label>Sub Total</label>
								</div>
								<div class="col-md-3" style="text-align: right">${currencySymbol}
									&nbsp; {{rateSummary.subTotal}}</div>
							</div>
							<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
							<div class="md-list-item-text rateByRoomList">
								<div class="col-md-8">
									<label>Total Service Charge</label>
								</div>
								<div class="col-md-3" style="text-align: right">${currencySymbol}
									&nbsp; {{rateSummary.totalSCharge}}</div>
							</div>
							<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line"
								ng-if="rateSum.discTotal!=0">
							<div class="md-list-item-text rateByRoomList">
								<div class="col-md-8">
									<label>Total Disc</label>
								</div>
								<div class="col-md-3" style="text-align: right">${currencySymbol}
									&nbsp; {{rateSummary.discTotal}}</div>
							</div>
							<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
							<div class="md-list-item-text rateByRoomList">
								<div class="col-md-8">
									<label>Total Tax</label>
								</div>
								<div class="col-md-3" style="text-align: right">${currencySymbol}
									&nbsp; {{rateSummary.taxTotal}}</div>
							</div>
							<md-divider></md-divider> </md-list-item> <md-list-item class="md-1-line">
							<%-- <div class="md-list-item-text rateByRoomList">
					<div class="col-md-8">
						<label>Total Service Tax</label>
					</div>
					<div class="col-md-3">{{rateSummary.sTaxTotal}}
						${currencySymbol}</div>
				</div> --%>
							<div class="md-list-item-text rateByRoomList">
								<div class="col-md-8">
									<label>Total Cost</label>
								</div>
								<div class="col-md-3" style="text-align: right">${currencySymbol}
									&nbsp; {{rateSummary.grantTotal}} (Incl.Taxes)</div>
							</div></md-list>
						</div>

						</md-card-content> </md-card>
						<!-- <div class="bottom_div">
				<md-button class="md-raised md-primary" ng-click="save()" type="submit">Submit</md-button>
			</div> -->
					</div>
				</div>
				</md-tab-body> </md-tab> </md-tabs>
			</div>
		</section>
	</div>
</div>

<div class="panel">
	<div class="panel-body">
		<div class="row">
			<div class="col-md-12" align="right">
				<div ng-if="!check_avail">
					<%-- <button id="btnPrev" class="btn btn-default" type="button"
							ng-click="newOpenShift()">
							<spring:message code="pms.button.back"></spring:message>
						</button> --%>
					<input id="btnReload" type="button" class="btn btn-danger"
						value="Reset" onclick="window.location.reload()">
					<!-- 			  <input id="btnCancel" ng-click="backFunction();" type="button" class="btn btn-default" value="Back">
 -->
					<input id="btnNext" type="button" class="btn btn-success"
						ng-click="getRoomRateDetails();"
						ng-if="!changeInRoomType && data.thirdLocked" value="Next">
					<input id="btnNext" type="button" class="btn btn-success"
						ng-click="checkAvailability();"
						ng-if="changeInRoomType && data.thirdLocked" value="Next">
					<input id="btnNext" type="button" class="btn btn-success"
						ng-disabled="submit_click" ng-click="save();"
						ng-if="!data.thirdLocked" value="Save">
				</div>
				<div ng-if="check_avail">
					<md-button class="md-raised md-primary"
						ng-click="changeToNewRoom();">Next <i
						class="fa fa-chevron-circle-right" aria-hidden="true"></i></md-button>
				</div>
			</div>
		</div>
	</div>
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
					ng-repeat="rate in roomRates" is-open="status.open">
					<uib-accordion-heading> {{rate.rateCode}} <i
						class="pull-right glyphicon"
						ng-class="{'glyphicon-chevron-down': status.open, 'glyphicon-chevron-right': !status.open}"></i>
					</uib-accordion-heading>
					<div class="rateOccupancy">
						<div class="col-md-12">
							<div class="col-sm-3" ng-if="rate.occ1">
								<div class="row">
									<label class="rate_label">Single</label>
									<div class="rate" style="text-align: right">${currencySymbol}
										&nbsp; {{rate.totalOcc1Rate}}</div>
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
									<div class="rate" style="text-align: right">${currencySymbol}
										&nbsp; {{rate.totalOcc2Rate}}</div>
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
									<div class="rate" style="text-align: right">${currencySymbol}
										&nbsp; {{rate.totalOcc3Rate}}</div>
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
									<div class="rate" style="text-align: right">${currencySymbol}
										&nbsp; {{rate.totalOcc4Rate}}</div>
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
				</uib-accordion> </md-content> </md-tab> <md-tab label="Picture"> <md-content class="md-padding">


				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel">
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0"
							class="active"></li>
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner" role="listbox">
						<div ng-if="roomTypeImages.image1!=null" class="item active">
							<img ng-src="{{roomTypeImages.image1}}" alt="...">
							<div class="carousel-caption">...</div>
						</div>
						<div ng-if="roomTypeImages.image2!=null"
							class="item {{roomTypeImages.image1===null?'active':''}}">
							<img ng-src="{{roomTypeImages.image2}}" alt="...">
							<div class="carousel-caption">...</div>
						</div>
						<div ng-if="roomTypeImages.image3!=null"
							class="item {{(roomTypeImages.image1===null && roomTypeImages.image2===null)?'active':''}}">
							<img ng-src="{{roomTypeImages.image3}}" alt="...">
							<div class="carousel-caption">...</div>
						</div>
						<div ng-if="roomTypeImages.image4!=null"
							class="item {{(roomTypeImages.image1===null && roomTypeImages.image2===null && roomTypeImages.image3===null)?'active':''}}">
							<img ng-src="{{roomTypeImages.image4}}" alt="...">
							<div class="carousel-caption">...</div>
						</div>
					</div>

					<!-- Controls -->
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


				</md-content> </md-tab> <md-tab label="Features"> <md-content class="md-padding">
				<h1 class="md-display-2">Features</h1>
				<p>Integer turpis erat,</p>

				</md-content> </md-tab> </md-tabs> </md-content>
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
	tabindex="-1" id="availDiscountsmyModal" class="modal fade">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">�</button>
				<h4 class="modal-title">Available Discounts</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<div layout="column" class="md-inline-form">
					<div>
						<div class="discountsList row">
							<md-subheader class="md-no-sticky availRatePlans">Applicable
							Discounts</md-subheader>
							<div class="planDiscDiv">

								<md-content> <md-list> <md-radio-group
									ng-model="planDisc"> <md-list-item
									class="md-1-line" ng-repeat="plan in availDiscounts.plan">
								<div class="md-list-item-text rateByRoomList">
									<div class="col-md-8">
										<md-checkbox class="discPlanCheckBox chk_{{plan.id}}"
											aria-label="Checkbox 1" ng-checked="plan.selected"
											ng-click="toggle(plan)"> <label>{{plan.code}}</label>
										<span class="md-subhead">( {{plan.name}} )</span> </md-checkbox>
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