<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<div class="panel-body">
		<div id="dataTableDiv" class="col-lg-12">
			<div>
				<div class="form-group align-btn-rgt topAddButtonDiv">
					<c:if test="${cp_isCanAdd}">
						<button type="button" class="btn btn-warning"
							ng-click="newProvider()">
							New Provider <i class="fa fa-plus"></i>
						</button>
					</c:if>
				</div>
				<div class="cmn_tbl">
					<table id="entry-grid" datatable="" dt-options="dtOptions"
						dt-instance="dtInstance" dt-columns="dtColumns"
						style="width: 100%;" class="angDataTable table table-hover"></table>
				</div>

				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
					tabindex="-1" id="newProvidermyModal" class="modal fade">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
							<div class="modal-header">
								<button aria-hidden="true" data-dismiss="modal" class="close"
									type="button">x</button>
								<h4 class="modal-title">Facility Provider</h4>
							</div>
							<div class="modal-body" style="overflow-y: auto;">
								<!-- modal data comes here -->
								<div class="form-horizontal tasi-form">
									<div class="form-group">
										<label class="control-label col-md-3"> Code <span
											class="red">*</span></label>
										<div class="col-md-6 col-xs-11">
											<input type="text" id="code" maxlength="15" id="txtCode"
												ng-disabled="facilityProvider.disbl"
												class="form-control validator form-control-inline input-medium"
												ng-model="facilityProvider.code" />
										</div>
										<div class="col-md-1 col-xs-11 tick_green">
											<span id="code_check" class="switch-right"
												style="display: none;"><i class=" fa fa-check"></i></span> <span
												id="code_warning" class="switch-right warning_red"
												style="display: none;"><i class="fa fa-warning"></i></span>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-3"> Name <span
											class="red">*</span></label>
										<div class="col-md-6 col-xs-11">
											<input type="text" id="name" maxlength="100"
												class="form-control validator form-control-inline input-medium"
												ng-model="facilityProvider.name" />
										</div>
										<div class="col-md-1 col-xs-11 tick_green">
											<span id="name_check" class="switch-right"
												style="display: none;"><i class=" fa fa-check"></i></span> <span
												id="name_warning" class="switch-right warning_red"
												style="display: none;"><i class="fa fa-warning"></i></span>
										</div>
									</div>


									<div class="form-group">
										<label class="control-label col-md-3">Facility Type</label>
										<div class="col-md-6 col-xs-11">
											<select id="slctTxnType" class="form-control input-sm"
												ng-model="facilityProvider.facilityType">
												<c:forEach items="${facilityTypes}" var="facility">
													<option value="${facility.code}">${facility.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3"> Email <span
											class="red">*</span></label>
										<div class="col-md-6 col-xs-11">
											<input type="email" maxlength="100" id="email"
												class="form-control form-control-inline input-medium  mail-form validator"
												ng-model="facilityProvider.email" />


										</div>
										<div class="col-md-1 col-xs-11 tick_green">
											<span id="email_check" class="switch-right"
												style="display: none;"><i class=" fa fa-check"></i></span> <span
												id="email_warning" class="switch-right warning_red"
												style="display: none;"><i class="fa fa-warning"></i></span>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3"> Phone<span
											class="red">*</span>
										</label>




										<div class="col-md-6 col-xs-11">
											<input type="text" id="phone" maxlength="15"
												class="form-control validator form-control-inline input-medium"
												ng-model="facilityProvider.phone" numbers-only />
										</div>
										<div class="col-md-1 col-xs-11 tick_green">
											<span id="phone_check" class="switch-right"
												style="display: none;"><i class=" fa fa-check"></i></span> <span
												id="phone_warning" class="switch-right warning_red"
												style="display: none;"><i class="fa fa-warning"></i></span>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-3"> Description </label>
										<div class="col-md-6 col-xs-11">
											<input type="text" maxlength="200"
												class="form-control form-control-inline input-medium"
												ng-model="facilityProvider.description" />
										</div>
									</div>

								</div>
								<!-- modal data comes here -->
							</div>
							<div class="modal-footer">
								<input type="hidden" id="baction" value="save"
									ng-model="facilityProvider.baction" />
								<button id="cancelProvider" type="button"
									class="btn btn-default rbtnClose" ng-click="cancelPopUp();">
									<spring:message code="pms.btn.cancel" />
								</button>
								<button id="saveProviderBtn" type="button"
									class="btn btn-success" ng-click="save()">
									<spring:message code="pms.btn.save" />
								</button>
								<c:if test="${cp_isCanDelete}">
									<button id="deleteProviderBtn" type="button"
										class="btn btn-warning" ng-click="deleteProvider()">
										<spring:message code="pms.btn.delete" />
									</button>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


	</div>
</section>