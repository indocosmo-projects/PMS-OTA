<jsp:directive.include file="../common/includes/page_directives.jsp" />
<input type="hidden" id="currency" value="${currency}" />
<section class="panel">
	<header class="panel-heading module_caption">
		<h1>
			<spring:message code="nightAudit.label.expectedPostings" />
		</h1>
		<span class="tools pull-right"> <a class="fa fa-chevron-down"
			href="javascript:;"></a>
		</span>
	</header>
	<div class="panel-body postingDtlsDiv">
		<div class="roomList" ng-repeat="x in inHouse">
			<div class="col-lg-12">
				<div class="col-lg-2 innerDiv roomNoDiv">
					<div class="inhslbl">
						<i class="fa fa-bed" aria-hidden="true"></i><span>Room NO</span>
					</div>
					<div class="inhsdata">{{x.roomNum}}</div>
				</div>
				<div class="col-lg-3 innerDiv ratePlanDiv">
					<div class="inhslbl">
						<i class="fa fa-money" aria-hidden="true"></i><span>Rate
							Plan</span>
					</div>
					<div class="inhsdata">{{x.rateCode}}</div>
				</div>
				<div class="col-lg-3 innerDiv nameDiv">
					<div class="inhslbl">
						<i class="fa fa-user" aria-hidden="true"></i><span>Name</span>
					</div>
					<div class="inhsdata">{{x.firstName}}</div>
				</div>
				<div class="col-lg-2 innerDiv postingDiv">
					<div class="inhslbl">
						<i class="fa fa-stack-exchange" aria-hidden="true"></i><span>Today's
							Postings</span>
					</div>
					<div class="inhsdata">{{x.roomCharge}}{{x.inctax === 0 ?
						"(Not Inc)" : "(Inc)"}}</div>
				</div>
				<div class="col-lg-2 innerDiv dtlsDiv">
					<a ng-click="page3ctrl.loadDataTable(x.checkinNo)">Details<i
						class="fa fa-plus" aria-hidden="true"></i></a>
				</div>
			</div>
		</div>
		<div class="chk">

			<md-checkbox ng-model="cnfrmChkBox" ng-init="cnfrmChkBox=false"
				aria-label="Checkbox 1"> Accept All </md-checkbox>

		</div>

		<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
			tabindex="-1" id="txnInfomyModal" class="modal fade">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button aria-hidden="true" data-dismiss="modal" class="close"
							type="button">×</button>
						<h4 class="modal-title">
							<spring:message code="transaction.label.transaction" />
						</h4>
					</div>
					<div class="modal-body" style="overflow-y: auto;">

						<div class="col-md-12 divider text-center">
							<div class="col-xs-4 col-sm-4 col-md-4 emphasis">
								<h4>
									<small><spring:message code="rooms.label.roomNo" /></small>
								</h4>
								<p>
									<strong>{{rate.roomNum}}</strong>
								</p>
							</div>
							<div class="col-xs-4 col-sm-4 col-md-4 emphasis">
								<h4>
									<small><spring:message code="roomRate.label.roomType" /></small>
								</h4>
								<p>
									<strong>{{rate.roomType}}</strong>
								</p>

							</div>
							<div class="col-xs-4 col-sm-4 col-md-4 emphasis">
								<h4>
									<small><spring:message
											code="reservation.label.rateCode" /></small>
								</h4>
								<p>
									<strong>{{rate.rateCode}}</strong>
								</p>
							</div>

						</div>
						<div class="col-lg-12 rateDiv">
							<md-toolbar class="md-theme-light">
							<h4 class="md-toolbar-tools">
								<span>Rate Details</span>
							</h4>
							</md-toolbar>

							<div
								class="col-lg-2 col-md-2 col-xs-2 col-sm-2 innerDiv roomNoDiv">
								<div class="inhslbl">
									<span><spring:message code="nightAudit.btn.roomCharge" /></span>
								</div>
								<div class="inhsdata">{{rate.roomCharge | number:2}}</div>
							</div>
							<div
								class="col-lg-2 col-md-2 col-xs-2 col-sm-2 innerDiv ratePlanDiv">
								<div class="inhslbl">
									<span><spring:message code="reservation.label.tax" /></span>
								</div>
								<div class="inhsdata">{{rate.tax+" "+rate.taxIn}}</div>
							</div>
							<div class="col-lg-3 col-md-3 col-xs-3 col-sm-3 innerDiv nameDiv">
								<div class="inhslbl">
									<span><spring:message code="pms.label.serviceCharge" /></span>
								</div>
								<div class="inhsdata">{{rate.serviceCharge}}</div>
							</div>
							<div
								class="col-lg-2 col-md-2 col-xs-2 col-sm-2 innerDiv postingDiv">
								<div class="inhslbl">
									<span><spring:message code="reservation.label.discount" /></span>
								</div>
								<div class="inhsdata">{{rate.disc+" "+rate.discType}}</div>
							</div>
							<div
								class="col-lg-3 col-md-3 col-xs-3 col-sm-3 innerDiv postingDiv">
								<div class="inhslbl">
									<span><spring:message
											code="nightAudit.btn.nettRoomCharge" /></span>
								</div>
								<div class="inhsdata">{{rate.nettAmount | number:2}}</div>
							</div>
						</div>
						<div class="col-lg-12 rateDiv">
							<md-toolbar class="md-theme-light">
							<h4 class="md-toolbar-tools">
								<span>Todays Postings</span>
							</h4>
							</md-toolbar>
							<div class="row postings" ng-repeat="txn in todaysPostings">
								<div
									class="col-lg-2  col-md-2 col-xs-2 col-sm-2 innerDiv roomNoDiv">
									<div class="inhslbl">
										<span><spring:message code="pms.label.code" /></span>
									</div>
									<div class="inhsdata">{{txn.acc_mst_code}}</div>
								</div>
								<div
									class="col-lg-2  col-md-2 col-xs-2 col-sm-2 innerDiv postingDiv">
									<div class="inhslbl">
										<span> <spring:message code="status.label.baseamount" /></span>
									</div>
									<div class="inhsdata">{{txn.base_amount}}</div>
								</div>
								<div
									class="col-lg-2 col-md-2 col-xs-2 col-sm-2 innerDiv ratePlanDiv">
									<div class="inhslbl">
										<span> <spring:message code="reservation.label.tax" /></span>
									</div>
									<div class="inhsdata">{{txn.tax}}</div>
								</div>
								<div
									class="col-lg-3 col-md-3 col-xs-3 col-sm-3 innerDiv nameDiv">
									<div class="inhslbl">
										<span> <spring:message code="pms.label.serviceCharge" /></span>
									</div>
									<div class="inhsdata">{{txn.service_charge}}</div>
								</div>

								<div
									class="col-lg-3 col-md-3 col-xs-3 col-sm-3 innerDiv postingDiv">
									<div class="inhslbl">
										<span> <spring:message
												code="nightAudit.btn.nettRoomCharge" /></span>
									</div>
									<div class="inhsdata">{{txn.nett_amount<0?txn.nett_amount*-1:txn.nett_amount}}</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button id="cancelrateplan" type="button"
							class="btn btn-default rbtnClose" ng-click="cancelPopUp()">
							<spring:message code="pms.btn.close" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>

</section>
<section class="panel">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<button class="btn btn-success" ng-click="page3ctrl.next()">
				<spring:message code="pms.btn.next"></spring:message>
			</button>
		</div>
	</div>
</section>