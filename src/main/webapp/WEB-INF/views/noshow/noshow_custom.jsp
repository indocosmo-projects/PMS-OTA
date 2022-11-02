<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@ page import="com.indocosmo.pms.enumerator.ReservationStatus"%>

<div class="row">
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
							<div class="col-md-12 s-body">${reservationNo}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.reservedDate" />
							</div>
							<div class="col-md-12 s-body">{{resv.hdr.resvDate |
								date:dateFormat}}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.status" />
							</div>
							<div id="divNights" class="col-md-12 s-body">
								<div
									ng-if="resv.hdr.status==${ReservationStatus.CONFIRMED.code}">
									<spring:message code="status.label.confirmed" />
								</div>
								<div
									ng-if="resv.hdr.status=='${ReservationStatus.CHECKIN.code}'">
									<spring:message code="status.label.checkIn" />
								</div>
								<div
									ng-if="resv.hdr.status=='${ReservationStatus.PARTCHECKIN.code}'">
									<spring:message code="status.label.partCheckIn" />
								</div>
								<div ng-if="resv.hdr.status=='${ReservationStatus.NOSHOW.code}'">
									<spring:message code="status.label.noShow" />
								</div>
								<div
									ng-if="resv.hdr.status=='${ReservationStatus.UNCONFIRMED.code}'">
									<spring:message code="status.label.unconfirmed" />
								</div>
								<div
									ng-if="resv.hdr.status=='${ReservationStatus.CHECKOUT.code}'">
									<spring:message code="status.label.checkOut" />
								</div>
								<div
									ng-if="resv.hdr.status=='${ReservationStatus.PARTCHECKOUT.code}'">
									<spring:message code="status.label.partCheckOut" />
								</div>
								<div
									ng-if="resv.hdr.status=='${ReservationStatus.CANCELLED.code}'">
									<spring:message code="status.label.cancelled" />
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.searchFor.arrivalOn" />
							</div>
							<div class="col-md-12 s-body">{{resv.hdr.minArrDate |
								date:dateFormat}}</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>
<form name="guestForm" ng-submit="guestForm.$valid && saveNoShow()">
	<div class="row">
		<div class="col-lg-12">
			<section class="panel">
				<header class="panel-heading module_caption">
					<h1>Room Details</h1>
				</header>
				<div class="panel-body">

					<div class="select_all_div">
						<div class="col-md-3">
							<md-checkbox ng-checked="selectAll" ng-click="toggleAll()"
								aria-label="select all">Set all rooms to no-show</md-checkbox>
						</div>
						<div class="col-md-4">
							<md-input-container class="reason_input"> <label>Reason</label>
							<input ng-disabled="!selectAll" ng-required="selectAll"
								ng-model="noShowCommonReason"> </md-input-container>
						</div>
					</div>
					<div class="room_dtls_div">
						<div ng-repeat="roomDtl in resv.room track by $index"
							class="room_dtl_div">
							<md-subheader class="md-no-sticky availRatePlans"
								ng-if="roomDtl.roomTypeId!=resv.room[$index-1].roomTypeId">{{roomDtl.roomTypeCode}}
							</md-subheader>
							<md-subheader class="md-no-sticky ratePlanSubHdr" id="bgColour"
								ng-if="roomDtl.rateId!=resv.room[$index-1].rateId">{{roomDtl.rateCode}}
							</md-subheader>
							<div class="guest_box">
								<div class="room_contents">
									<div class="col-md-12 ">
										<div class="col-md-2">
											<div class="row">
												<!-- <md-checkbox ng-if="roomDtl.roomStatus==0 || roomDtl.roomStatus==3 || roomDtl.roomStatus==1" ng-checked="roomDtl.isNoShow"
													ng-click="toggle(roomDtl)" ng-disabled="roomDtl.roomStatus==3">set as no-show</md-checkbox> -->
												<md-checkbox
													ng-if="roomDtl.roomStatus==0 || roomDtl.roomStatus==1"
													ng-checked="roomDtl.isNoShow" ng-click="toggle(roomDtl)">set
												as no-show</md-checkbox>
												<span class="res_stat-checkin" ng-if="roomDtl.roomStatus==5">IN-HOUSE</span>
												<span style="color: #cb6932" ng-if="roomDtl.roomStatus==7">CHECK-OUT</span>
												<span class="res_stat_cancel" ng-if="roomDtl.roomStatus==2">CANCELLED</span>
												<span class="res_stat-noshow" ng-if="roomDtl.roomStatus==3">NO-SHOW</span>
											</div>
										</div>
										<div class="col-md-10">
											<div class="row">
												<div class="col-md-2 col-sm-2">
													<md-input-container class="reason_input">
													<label>Reason</label> <input ng-if="roomDtl.isNoShow"
														ng-model="roomDtl.noshowReason"> </md-input-container>
												</div>
												<div class="col-md-2 col-sm-2">
													<label>Room Number</label>
													<div class="dtlContent">{{roomDtl.roomNumber
														!=""?roomDtl.roomNumber:'Not Assigned'}}</div>
												</div>
												<div class="col-md-3 col-sm-3 ">
													<label>Name</label>
													<div class="dtlContent">{{roomDtl.firstName}}&nbsp;{{roomDtl.lastName}}</div>
												</div>
												<div class="col-md-3 col-sm-3 ">
													<label>Email</label>
													<div class="dtlContent">{{roomDtl.email}}</div>
												</div>
												<div class="col-md-2 col-sm-2 ">
													<label>Phone</label>
													<div class="dtlContent">{{roomDtl.phone}}</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
					<c:if test="${resHdr.depositAmount!=0}">
						<div ng-if="selectAll==true">
							<div class="row">
								<div class="form-group">
									<label class="control-label col-md-3 deposit">Forefeit</label>
									<div class="col-md-6 col-xs-11 statusClass deposit">
										<input type="checkbox" id="slideThree" ng-model="isfine"
											name="check"
											class="ng-untouched ng-valid ng-dirty ng-valid-parse ng-not-empty deposit"
											aria-invalid="false" style=""> <label
											for="slideThree"></label>
									</div>
								</div>
							</div>
							<div class="row">

								<div class="form-group" ng-show="isfine">
									<label class="control-label col-md-3 deposit"> Deposit
									</label>
									<div class="col-md-6 col-xs-11">
										<input type="text" maxlength="200" id="deposit"
											class="form-control form-control-inline input-medium validator ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-maxlength deposit"
											ng-model="deposit" disabled="disabled">
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group" ng-show="isfine">
									<label class="control-label col-md-3 deposit">
										Percentage(%) <span class="red">*</span>
									</label>
									<div class="col-md-6 col-xs-11">
										<input type="text" maxlength="3" id="percentage"
											class="form-control form-control-inline input-medium validator deposit"
											ng-model="percentage" ng-change="calcPenality()" />
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group" ng-show="isfine">
									<label class="control-label col-md-3 deposit"> Penality</label>
									<div class="col-md-6 col-xs-11" id="penalty">
										<p class="depositpenal">{{penalty}}</p>
									</div>
								</div>
							</div>
						</div>
					</c:if>
				</div>
			</section>
		</div>
	</div>

	<div class="row">
		<div class="col-md-12">
			<section class="panel">
				<div class="panel-body">
					<div class="row footer_pagination_main_div">
						<div class="align-btn-rgt ">
							<button id="backButn" class="btn btn-success" type="submit"
								ng-disabled="noShowDisable">No-Show</button>
						</div>
					</div>
				</div>
			</section>
		</div>
	</div>
</form>