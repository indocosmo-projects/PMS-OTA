<jsp:directive.include file="../common/includes/page_directives.jsp" />
<input type="hidden" value="${resvNo}" id="resvNum" />
<input type="hidden" id="dateFormat" value="${dateFormat}">
<input type="hidden" id="timeFormat" value="${timeFormat}">
<input type="hidden" id="hotelDate" value="${hotelDate}">

<section class="panel padding_bottom_zrw">
	<%-- 	<header class="panel-heading module_caption">
		<h1>
			<spring:message code="systemSetting.label.reservation" />
		</h1>
		<span class="tools pull-right"> <a class="fa fa-chevron-down"
			href="javascript:;"></a>
		</span>
	</header> --%>
	<div class="btnbackshift" id="btnback">
		<%-- <a href="<c:url value='${backUrl}' />" class="btn btn-success pull-right backbutna">	
										   <i class="fa fa-arrow-left backbtntop"  > <small> <spring:message code="pms.btn.backtop" /></small></i> 
										 </a>	 --%>
		<a href="<c:url value='../reservation_test/reservationList' />"
			class="ios-back-button shiftback"
			data-text="<spring:message code="pms.btn.backtop" />"></a>
		<button type="button" class="btn btn-warning backshift"
			style="display: none;"
			ng-click="cancelDeposit(${depositFrom},${reservationId });"
			ng-disabled="openshifBtnAction">
			<i class="fa fa-plus"></i>
		</button>


	</div>
	<div class="panel-body mainDiv">
		<md-toolbar class="md-theme-light" style="z-index: 0;">
		<h4 class="md-toolbar-tools">
			<span>Guest Details</span>
		</h4>
		</md-toolbar>
		<md-content class="mainContent \"> <md-list> <md-list-item
			class="md-3-line">
		<div class="md-list-item-text resvContent">

			<div class="col-md-12">
				<div class="col-md-6 dateDtls">
					<div class="datePanels row">
						<div class="col-sm-6">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Reservation Date</h3>
								</div>
								<div class="panel-body">{{rDtl.resvDate |
									date:dateFormat}}</div>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Arrival Date</h3>
								</div>
								<div class="panel-body">{{formatDate(rDtl.arrDate) | date:dateFormat}} {{formatDate(rDtl.arrDate) | date:timeFormat}}</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="roomDtls col-sm-12">
							<div class="panel panel-primary" id="roomdetMar">
								<div class="panel-heading">
									<h3 class="panel-title">Room Details</h3>
								</div>
								<div class="panel-body">
									<div class="col-sm-2">
										<div class="rdhdr">Rooms</div>
										<div class="rddtl">{{rDtl.numRooms}}</div>
									</div>
									<div class="col-sm-2">
										<div class="rdhdr">Nights</div>
										<div class="rddtl">{{rDtl.numNights}}</div>
									</div>
									<div class="col-sm-4">
										<div class="rdhdr">Status</div>
										<div id="divStatus" class="rddtl">{{rDtl.status}}</div>
									</div>


									<div class="col-sm-4">



										<div class="rdhdr">Cut Off Date</div>

										<!--  style="{{showConfirm ? 'display:none' : ''}}" -->
										<div>
											<a ng-click="loadCutOffDate()"
												ng-href="{{showConfirm ? '' : '#foo'}}"
												ng-class="{disabled: showConfirm}" style="color: red">
												{{rDtl.cutOffDate | date:dateFormat}} <span
												class="btn-label" style="color: #69b19a"> <i
													class="fa fa-calendar" aria-hidden="true"></i></span>
											</a>



											<!-- <a href="#"> {{rDtl.cutOffDate}}</a> -->

										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<label>Reserved For</label>
					<div class="input-group userDtls">
						<span class="input-group-addon" id="sizing-addon2"><i
							class="fa fa-user" aria-hidden="true"></i></span>
						<div class="form-control dtlsInp" aria-describedby="sizing-addon2">{{rDtl.resvByName}}</div>
					</div>
					<div class="input-group userDtls">
						<span class="input-group-addon" id="sizing-addon2"><i
							class="fa fa-at" aria-hidden="true"></i></span>
						<div class="form-control dtlsInp" aria-describedby="sizing-addon2">{{rDtl.resvByMail}}</div>
					</div>
					<div class="input-group userDtls">
						<span class="input-group-addon" id="sizing-addon2"><i
							class="fa fa-phone" aria-hidden="true"></i></span>
						<div class="form-control dtlsInp" aria-describedby="sizing-addon2">{{rDtl.resvByPhone}}</div>
					</div>
					<div class="input-group userDtls">
						<span class="input-group-addon" id="sizing-addon2"><i
							class="fa fa-envelope-o" aria-hidden="true"></i></span>
						<div class="form-control dtlsInp" aria-describedby="sizing-addon2"
							style="height: 60px">{{rDtl.resvByAddress}}</div>
					</div>
					<label id="resvTop">Reserved By</label>
					<div class="input-group userDtls">
						<span class="input-group-addon" id="sizing-addon2"><i
							class="fa fa-user" aria-hidden="true"></i></span>
						<div class="form-control dtlsInp" aria-describedby="sizing-addon2">{{rDtl.reservedFor}}</div>
					</div>
					<label id="resvTop">Special request</label>
					<div class="input-group userDtls">
						<span class="input-group-addon" id="sizing-addon2"><i
							class="fa fa-user" aria-hidden="true"></i></span>
						<div class="form-control dtlsInp" aria-describedby="sizing-addon2">{{rDtl.specialrequest}}</div>
					</div>
				</div>
			</div>
		</div>
		<md-divider ng-if="!$last"></md-divider> </md-list-item> <md-list-item
			class="md-2-line bottomList">
		<div class="md-list-item-text bottomMenu">
			<div class="menuBtns">
			
			<button type="button" ng-disabled="!showConfirm"
					class="btn btn-labeled btn-success menuButtons btnTools"
					id="printBookingVoucher" ng-click="printBookingVoucher()">
					<span class="btn-label"><i class="fa fa-file-text-o"
						aria-hidden="true"></i></span>BOOKING VOUCHER
				</button>

				<button type="button"
					class="btn btn-labeled btn-success menuButtons btnTools"
					id="printGrcBtn" ng-click="printGrc()">
					<span class="btn-label"><i class="fa fa-file-text-o"
						aria-hidden="true"></i></span>GRC
				</button>

				<c:if test="${rp_isCanView}">
					<button type="button"
						class="btn btn-labeled btn-success menuButtons btnTools"
						id="pickUpBtn" ng-disabled="showpickup" ng-click="loadPickup()">
						<span class="btn-label"><i class="fa fa-car"
							aria-hidden="true"></i></span>Pickup
					</button>
				</c:if>

				<c:if test="${dep_isCanView}">
					<button type="button"
						class="btn btn-labeled btn-success menuButtons"
						ng-disabled="showDeposit" ng-click="deposit()">
						<span class="btn-label"><i class="fa fa-stack-exchange"
							aria-hidden="true"></i></span>Deposit
					</button>
				</c:if>
				<c:if test="${rp_isCanEdit}">
					<button type="button"
						class="btn btn-labeled btn-success menuButtons"
						ng-disabled="showChangeArr" ng-click="changeResvDate()">
						<span class="btn-label"><i class="fa fa-calendar"
							aria-hidden="true"></i></span>Change Arr Date
					</button>
					<button type="button"
						class="btn btn-labeled btn-success menuButtons"
						ng-disabled="showEdit" ng-click="editResv()">
						<span class="btn-label"><i class="fa fa-pencil"
							aria-hidden="true"></i></span>Edit
					</button>
					<button type="button"
						class="btn btn-labeled btn-success menuButtons"
						ng-disabled="showNoshow" ng-click="noshow()">
						<span class="btn-label"><i class="fa fa-question-circle"
							aria-hidden="true"></i></span>No-show
					</button>
					<button type="button"
						class="btn btn-labeled btn-success menuButtons"
						ng-disabled="showConfirm" ng-click="confirm()">
						<span class="btn-label"><i class="fa fa-check-circle-o"
							aria-hidden="true"></i></span>Confirm
					</button>
				</c:if>
				<c:if test="${rp_isCanDelete}">
					<button type="button"
						class="btn btn-labeled btn-success menuButtons"
						ng-disabled="showCancel" ng-click="cancel()">
						<span class="btn-label"><i class="fa fa-times-circle-o"
							aria-hidden="true"></i></span>Cancel
					</button>
				</c:if>
				<c:if test="${chk_isCanView}">
					<button type="button"
						class="btn btn-labeled btn-success menuButtons"
						ng-disabled="showCheckIn" ng-click="checkIn()">
						<span class="btn-label"><i class="fa fa-key"
							aria-hidden="true"></i></span>CheckIn
					</button>
				</c:if>




			</div>
		</div>
		</md-list-item> </md-list> </md-content>
	</div>
</section>
<%-- <section class="panel">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<button class="btn btn-default" ng-click="cancelPage()"><i class="fa fa-arrow-left" aria-hidden="true"></i> <spring:message code="card.label.backbutton"></spring:message> </button>
		</div>
	</div>
</section> --%>


<style>
#cutOffDateModal {
	padding-top: 250px;
	z-index: 1;
}

#md-datepicker-calendar-pane {
	z-index: 2;
}

.input-group .form-control {
	z-index: 0;
}
</style>

<!--  bootstrap mpdel cutOffDateModal start  -->
<div aria-hidden="true" aria-labelledby="cutOffDateModal" role="dialog"
	tabindex="-1" id="cutOffDateModal" class="modal ">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Cut off date</h4>
			</div>

			<form name="cutOffForm">
				<div class="modal-body">
					<div class="row">
						<div class="col-md-3 col-sm-3 s-col" style="padding-top: 8px;">
							Cut Off Date :</div>
						<div class="col-md-5 col-sm-5 s-col">

							<md-datepicker ng-model="cutOffDate" id="cutOffDate"
								md-placeholder="Enter date" md-min-date="minDate"
								ng-required="true" md-max-date="maxDate" md-open-on-focus></md-datepicker>
						</div>

						<div class="col-md-2 col-sm-2 s-col">
							<button id="popUpsaveButn" type="button" class="btn btn-success"
								style="float: right;" ng-click="updateCutOffDate()"
								ng-disabled="!cutOffForm.$dirty">Update</button>
						</div>

						<div class="col-md-2 col-sm-2 s-col">
							<button aria-hidden="true" data-dismiss="modal"
								class="btn btn-default rbtnClose" type="button">Cancel</button>
						</div>

					</div>


					<!-- modal data comes here -->
				</div>
			</form>
			<!-- <div class="modal-footer">
				<button id="popUpsaveButn" type="button" class="btn btn-success"
					onclick="updateRatePlan()">
					Save
				</button>
				
				<button aria-hidden="true" data-dismiss="modal" class="btn btn-default rbtnClose" type="button">Cancel</button>
				
				 
			</div> -->
		</div>
	</div>
</div>
<!--  bootstrap mpdel cutOffDateModal close  -->



<style>
#pickupModal {
	padding-top: 50px;
	z-index: 1;
}

#md-datepicker-calendar-pane {
	z-index: 2;
}

.input-group .form-control {
	z-index: 0;
}
</style>


<!--  bootstrap mpdel cutOffDateModal start  -->
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
				<!--  <div class="row">	 
			        <div class="col-md-2 col-sm-2 s-col" > sd</div>

                   
                  
               </div>   -->


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
										<label class="control-label"> Pickup Date<span
											class="red">*</span></label> <br>
										<md-datepicker ng-model="pickupDate" id="pickupDate"
											md-placeholder="Enter date" md-min-date="arrivalNewMinDate"
											ng-required="true" md-max-date="departDate"></md-datepicker>
										<!-- <input type="text" class="form-control code-font-size required" daterange-pickerbft 
							name="received_date" id="pickupDate" ng-model="pickupDate"   ng-required="true" 
							
							placeholder=""> -->



										<!--   <input id="pickup_date" name="pickup_date"
											                                    class="form-control form-control-inline input-medium validator validator_fit datepicker"
											                                    readonly="readonly" size="10" value="" />	  -->
									</div>

									<div class="col-md-6 col-xs-11">
										<label class="control-label"> Pickup Time<span
											class="red">*</span></label> <br>
										<div class='input-group date' id='pickup_time'>
											<input type='text' class="form-control" ng-model="pickupTime"
												id="pickup_time_txt" /> <span class="input-group-addon">
												<span class="glyphicon glyphicon-time"></span>
											</span>
										</div>
									</div>


								</div>


								<div class="form-group">
									<div class="col-md-6 col-xs-11">
										<label class="control-label"> Pickup location<span
											class="red">*</span></label> <br> <input id="pickup_location"
											name="pickup_location"
											class="form-control form-control-inline input-medium validator"
											size="10" ng-model="pickupLocation" />
									</div>

									<div class="col-md-6 col-xs-11">
										<label class="control-label">No.Packs<span class="red">*</span></label>
										<br> <input id="pickup_seats" name="pickup_seats"
											type="number" ng-model="pickupSeats"
											class="form-control validator" ng-pattern="/^[0-9]*$/">
									</div>
								</div>

								<div class="form-group">
									<div class="col-md-6 col-xs-11">
										<label class="control-label"> Pickup remarks<span
											class="red">*</span></label> <br> <input id="pickup_remarks"
											name="pickup_remarks"
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

				<button id="delete_btn" class="btn btn-danger" type="button"
					ng-show="pickupDeleteBtn" ng-click="deleteConfirm()">
					<i class="fa fa-trash-o "></i>
					<spring:message code="pms.btn.delete"></spring:message>
				</button>


				<button aria-hidden="true" data-dismiss="modal"
					class="btn btn-default rbtnClose" type="button">Cancel</button>


			</div>
		</div>
	</div>
</div>
<!--  bootstrap mpdel cutOffDateModal close  -->



