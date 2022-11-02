<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel padding_bottom_zrw">
	<!-- <header class="panel-heading module_caption">
		<h1>
			<spring:message code="reservation.label.deposit.summary" />
		</h1>
		<span class="tools pull-right"> <a class="fa fa-chevron-down"
			href="javascript:;"></a>
		</span>
	</header>-->
	<div class="panel-body">
		<div class="dtls summury">
			<div class="col-md-3 col-sm-3 s-col ">
				<div class="summury_div">
					<div class="col-md-12 s-header">
						<spring:message code="nightAudit.label.expectedArrivals" />
					</div>
					<div id="reser_id" class="col-md-12 s-body">${expArrival}</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 s-col">
				<div class="summury_div">
					<div class="col-md-12 s-header">
						<spring:message code="nightAudit.label.expectedDeparts" />
					</div>
					<div id="" class="col-md-12 s-body">${expDepart}</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 s-col">
				<div class="summury_div">
					<div class="col-md-12 s-header">
						<spring:message code="nightAudit.label.noShow" />
					</div>
					<div id="divNights" class="col-md-12 s-body">${noShow}</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-3 s-col">
				<div class="summury_div">
					<div class="col-md-12 s-header">
						<spring:message code="nightAudit.label.inHouse" />
					</div>
					<div id="divCorporateTA" class="col-md-12 s-body">${inHouse}</div>
				</div>
			</div>
		</div>
	</div>
</section>
<section class="panel padding_bottom_zrw">
	<header class="panel-heading module_caption">
		<h1>
			<spring:message code="nightAudit.label.preNightAuditChecklist" />
		</h1>
		<span class="tools pull-right"> <a class="fa fa-chevron-down"
			href="javascript:;"></a>
		</span>
	</header>
	<div class="panel-body roomDtlsDiv">

		<ul class="nav nav-tabs tab_design_cmn">
			<li class="active"><a data-toggle="tab" href="#DEPARTURE">EXPECTED
					DEPARTURE</a></li>
			<li><a data-toggle="tab" href="#ARRIVALS">EXPECTED ARRIVALS</a></li>
		</ul>
		<div class="tab-content">
			<div id="DEPARTURE" class="tab-pane fade in active">
				<div id="dataTableDiv">
					<div class="cmn_tbl">
						<!-- <table id="entry-grid" datatable=""
												dt-options="ctrlreq.dtOptions"
												dt-instance="ctrlreq.dtInstance"
												dt-columns="ctrlreq.dtColumns" 
												class="angDataTable table table-hover"></table> -->
						<table id="entry-grid" datatable=""
							dt-options="auditCtr.dtOptions" dt-instance="auditCtr.dtInstance"
							dt-columns="auditCtr.dtColumns"
							style="width: 100%; background-color: #00A8B3;"
							class="angDataTable table table-hover tblExtends">
						</table>

					</div>
				</div>
			</div>
			<div id="ARRIVALS" class="tab-pane fade">
				<div id="dataTableDiv">
					<div class="cmn_tbl">
						<!-- <table id="entry-grid" datatable=""
												dt-options="ctrlreq.dtOptions1"
												dt-instance="ctrlreq.dtInstance1"
												dt-columns="ctrlreq.dtColumns1"
												class="angDataTable table table-hover"></table>
 -->
						<table id="entry-grid" datatable=""
							dt-options="auditCtr.dtOptions1"
							dt-instance="auditCtr.dtInstance1"
							dt-columns="auditCtr.dtColumns1"
							style="width: 100%; background-color: #00A8B3;"
							class="angDataTable table table-hover">
						</table>
					</div>
				</div>
			</div>
		</div>


		<!-- 	<div ng-cloak>
			<md-content> <md-tabs md-dynamic-height md-border-bottom>
			<md-tab label="Expected Departure"> <md-content
				class="md-padding">
			<div
				id="dataTableDiv">
				<table id="entry-grid" datatable="" dt-options="auditCtr.dtOptions"
					dt-instance="auditCtr.dtInstance" dt-columns="auditCtr.dtColumns"
					style="width: 100%;background-color: #00A8B3;" class="angDataTable table table-hover tblExtends"></table>

				--------- expected checkout modal ----------
				
			</div>
			</md-content> </md-tab> <md-tab label="Expected Arrivals"> <md-content class="md-padding">
			<div id="dataTableDiv">
				<table id="entry-grid" datatable="" dt-options="auditCtr.dtOptions1"
					dt-instance="auditCtr.dtInstance1" dt-columns="auditCtr.dtColumns1"
					style="width: 100%;background-color: #00A8B3;" class="angDataTable table table-hover"></table>
					
				
			</div>
			</md-content> </md-tab> </md-tabs> </md-content>
		</div> -->
	</div>

</section>
<section class="panel padding_bottom_zrw"
	ng-controller="auditPage1Controller">
	<div class="panel-body">
		<div class="align-btn-rgt">

			<button class="btn btn-default" ng-click="cancel()">
				<spring:message code="pms.btn.cancel"></spring:message>
			</button>
			<button class="btn btn-success" ng-click="ExtendStay()"
				ng-disabled="stayExtnd">Extend Stay</button>
			<button class="btn btn-success" ng-click="next()">
				<spring:message code="pms.btn.next"></spring:message>
			</button>

		</div>
	</div>
</section>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="expCheckoutmyModal" class="modal fade">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">
					<spring:message code="reservation.label.guestAndRoom" />
				</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<!-- modal data comes here -->
				<div class="form-horizontal tasi-form">
					<div class="col-sm-12 modalInnerDiv">
						<div class="col-xs-12 col-sm-6">
							<div class="userDtls">
								<i class="fa fa-user" aria-hidden="true"></i>{{exp.name}}
							</div>
							<div class="userDtls">
								<i class="fa fa-envelope" aria-hidden="true"></i>{{exp.email}}
							</div>
							<div class="userDtls">
								<i class="fa fa-phone" aria-hidden="true"></i>{{exp.phone}}
							</div>
							<div class="userDtls">
								<i class="fa fa-map-marker" aria-hidden="true"></i>{{exp.address}}
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 text-center">
							<div class="panel panel-primary popUpRightPanel">
								<div class="panel-heading">
									<h4 class="panel-tittle">
										<strong class="roomHdr">{{exp.roomType}}</strong><strong
											class="roomHdr">{{exp.roomNo}}</strong>
									</h4>
								</div>
								<div class="panel-body">
									<div class="row" style="padding: 0 2px;">
										<table class="table">
											<tbody>
												<tr>
													<td class="control-label"><spring:message
															code="discount.label.ratePlan" /></td>
													<td>{{exp.ratePlan}}</td>
												</tr>
												<tr>
													<td class="control-label"><spring:message
															code="reservation.label.occupancy" /></td>
													<td>{{exp.occupancy}}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-xs-12 divider text-center">
						<div class="col-xs-12 col-sm-4 emphasis">
							<h4>
								<strong><spring:message code="card.label.arrivaldate" /></strong>
							</h4>
							<p>
								<small>{{exp.arrDate | date:dateFormat}}</small>
							</p>

						</div>
						<div class="col-xs-12 col-sm-4 emphasis">
							<h4>
								<strong><spring:message
										code="reservation.label.deposit.departDate" /></strong>
							</h4>
							<p>
								<small>{{exp.depDate | date:dateFormat}}</small>
							</p>
						</div>
						<div class="col-xs-12 col-sm-4 emphasis">
							<h4>
								<strong><spring:message
										code="reservation.label.folioBalance" /></strong>
							</h4>
							<p>
								<small>{{exp.folioBal}}</small>
							</p>
						</div>
					</div>

				</div>
			</div>
			<div class="modal-footer">
				<button id="contExpDepartBtn" type="button" class="btn btn-success"
					ng-click="auditCtr.goToCheckOut()">
					<spring:message code="nightAudit.btn.gotoCheckOut" />
				</button>
				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose" ng-click="cancelEDPopUp();">
					<spring:message code="pms.btn.cancel" />
				</button>

			</div>
		</div>
	</div>
</div>

<!----------- No Show modal ------------>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="expArrivalmyModal" class="modal fade">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">
					<spring:message code="reservation.label.guestAndRoom" />
				</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<!-- modal data comes here -->
				<div class="form-horizontal tasi-form">
					<div class="col-sm-12 modalInnerDiv">
						<div class="col-xs-12 col-sm-6">
							<div class="userDtls">
								<i class="fa fa-user" aria-hidden="true"></i>{{expArr.name}}
							</div>
							<div class="userDtls">
								<i class="fa fa-envelope" aria-hidden="true"></i>{{expArr.email}}
							</div>
							<div class="userDtls">
								<i class="fa fa-phone" aria-hidden="true"></i>{{expArr.phone}}
							</div>
							<div class="userDtls">
								<i class="fa fa-map-marker" aria-hidden="true"></i>{{expArr.address}}
							</div>
						</div>
						<div class="col-xs-12 col-sm-6 text-center">
							<div class="panel panel-primary popUpRightPanel">
								<div class="panel-heading">
									<h4 class="panel-tittle">
										<strong>{{expArr.roomType}}</strong><strong class="roomHdr">{{expArr.roomNo}}</strong>
									</h4>
								</div>
								<div class="panel-body">
									<div class="row" style="padding: 0 2px;">
										<table class="table">
											<tbody>
												<tr>
													<td class="control-label"><spring:message
															code="discount.label.ratePlan" /></td>
													<td>{{expArr.ratePlan}}</td>
												</tr>
												<tr>
													<td class="control-label"><spring:message
															code="reservation.label.occupancy" /></td>
													<td>{{expArr.occupancy}}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-xs-12 divider text-center">
						<div class="col-xs-12 col-sm-4 emphasis">
							<h4>
								<strong><spring:message code="card.label.bookingdate" /></strong>
							</h4>
							<p>
								<small>{{expArr.bookDate}}</small>
							</p>

						</div>
						<div class="col-xs-12 col-sm-4 emphasis">
							<h4>
								<strong><spring:message code="card.label.arrivaldate" /></strong>
							</h4>
							<p>
								<small>{{expArr.arrDate}}</small>
							</p>
						</div>
						<div class="col-xs-12 col-sm-4 emphasis">
							<h4>
								<strong><spring:message
										code="reservation.searchFor.bookingBy" /></strong>
							</h4>
							<p>
								<small>{{expArr.ResvByname}}</small>
							</p>
						</div>
					</div>
				</div>
				<!-- modal data comes here -->
			</div>
			<div class="modal-footer">
				<input type="hidden" ng-model="expArr.resvRoomNo" />
				<button id="expArrBtn" type="button" class="btn btn-success"
					ng-click="auditCtr.setNoShow()">
					<spring:message code="nightAudit.btn.setNoShow" />
				</button>
				<button type="button" ng-if="expArr.status==1 || expArr.status==4"
					class="btn btn-success" ng-click="confirmCheckIn()">
					<spring:message code="nightAudit.btn.gotoChecIn" />
				</button>
				<button type="button" ng-if="expArr.status==0"
					class="btn btn-success" ng-click="cancelResv()" id="cancelBtn">
					<spring:message code="cancel.label.cancelreservation" />
				</button>
				<button id="cancelNoShowBtn" type="button"
					class="btn btn-default rbtnClose" ng-click="cancelEAPopUp()">
					<spring:message code="pms.btn.cancel" />
				</button>
			</div>
		</div>
	</div>
</div>