<jsp:directive.include file="../common/includes/page_directives.jsp" />
<div class="row" id="divID" ng-app="resvCancel"
	ng-controller="resvCancelController">
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<div class="btnbackshift" id="btnback">
				<%-- <a href="<c:url value='${backUrl}' />" class="btn btn-success pull-right backbutna">	
										   <i class="fa fa-arrow-left backbtntop"  > <small> <spring:message code="pms.btn.backtop" /></small></i> 
										 </a>	 --%>
				<a href="<c:url value='${backUrl}' />"
					class="ios-back-button shiftback"
					data-text="<spring:message code="pms.btn.backtop" />"> </a>
				<button type="button" class="btn btn-warning backshift"
					style="display: none;" ng-click="newOpenShift()"
					ng-disabled="openshifBtnAction">
					<i class="fa fa-plus"></i>
				</button>

			</div>






			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="checkin.label.summary" />
				</h1>
				<!-- 	<span class="tools pull-right"> <a class="fa fa-chevron-down"
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
							<div id="reser_id" class="col-md-12 s-body">
								${resHdr.resvNo}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.reservedDate" />
							</div>
							<div id="" class="col-md-12 s-body">
								<fmt:formatDate pattern="${dateFormat}"
									value="${resHdr.resvDate}" />
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.searchFor.arrivalOn" />
							</div>
							<div id="divNights" class="col-md-12 s-body">
								<fmt:formatDate pattern="${dateFormat}"
									value="${resHdr.minArrDate}" />
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.reservedBy" />
							</div>
							<div id="divCorporateTA" class="col-md-12 s-body">
								<c:choose>
									<c:when test="${resHdr.corporateName !=null}">
								${resHdr.corporateName}
							</c:when>
									<c:otherwise>
										<spring:message code="checkin.label.fit" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>

	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<header class="panel-heading module_caption">
				<h1>Room Details</h1>
				<!-- <span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span> -->
			</header>
			<div class="panel-body">
				<div class="select_all_div">
					<div class="col-md-3">
						<md-checkbox ng-checked="selectAll" ng-click="toggleAll()"
							aria-label="select all">Select all rooms for
						cancel</md-checkbox>
					</div>
					<div class="col-md-4" id="resonComn" ng-if="selectAll">
						<md-input-container class="reason_input"> <label>Reason</label>
						<input ng-disabled="!selectAll" ng-required="selectAll"
							ng-model="cancelCommonReason" id="cancelCom" class="validator">
						</md-input-container>
					</div>
					<div class="col-md-1" id="checkValid">
						<span id="cancelCom_check" class="switch-right"
							style="display: none; color: #41cac0;"><i
							class=" fa fa-check"></i></span> <span id="cancelCom_warning"
							class="switch-right warning_red" style="display: none;"><i
							class="fa fa-warning"></i></span>
					</div>
				</div>
				<div class="room_dtls_div">
					<div ng-repeat="roomDtl in resv.room track by $index"
						class="room_dtl_div">
						<md-subheader class="md-no-sticky availRatePlans" id="bgColour"
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
											<!-- <md-checkbox ng-checked="roomDtl.roomStatus"
													ng-click="toggle(roomDtl)" ng-if="roomDtl.roomStatus!=3 && roomDtl.roomStatus!=5 && roomDtl.roomStatus!=7" ng-disabled="roomDtl.roomStatus==2">Select for cancel</md-checkbox> -->
											<md-checkbox ng-checked="roomDtl.roomStatus"
												ng-click="toggle(roomDtl)"
												ng-if="roomDtl.roomStatus!=3 && roomDtl.roomStatus!=5 && roomDtl.roomStatus!=7 && roomDtl.roomStatus!=2">Select
											for cancel</md-checkbox>
											<span class="res_stat-checkin" ng-if="roomDtl.roomStatus==5">IN-HOUSE</span>
											<span style="color: #cb6932" ng-if="roomDtl.roomStatus==7">CHECK-OUT</span>
											<span class="res_stat-noshow" ng-if="roomDtl.roomStatus==3">NO-SHOW</span>
											<span class="res_stat_cancel" ng-if="roomDtl.roomStatus==2">CANCELLED</span>

										</div>
									</div>
									<div class="col-md-10">
										<div class="row">
											<div class="col-md-2 col-sm-2">
												<md-input-container class="reason_input">
												<label>Reason</label> <input ng-if="roomDtl.roomStatus"
													ng-model="roomDtl.cancelReason"> </md-input-container>
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
										aria-invalid="false" style=""> <label for="slideThree"></label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="form-group" ng-show="isfine">
								<label class="control-label col-md-3 deposit"> Deposit </label>
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
								<label class="control-label col-md-3 deposit"> Penalty</label>
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

	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<div class="panel-body">
				<div class="row footer_pagination_main_div">
					<div class="align-btn-rgt">
						<%-- 	<button id="btnPrev" class="btn btn-default" type="button"
							ng-click="backToList();">
							<spring:message code="card.label.backbutton"></spring:message>
						</button> --%>
						<button id="baction" class="btn btn-success" type="button"
							ng-click="Cancellation();" value="cancel" ng-model="baction"
							ng-disabled="cancelDisable">
							<spring:message code="cancel.label.cancelreservation"></spring:message>
						</button>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>