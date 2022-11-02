<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<div class="panel-body">
		<div id="dataTableDiv" class="col-lg-12">
			<div>
				<div class="form-group align-btn-rgt topAddButtonDiv">
					<c:if test="${cp_isCanAdd}">
						<button type="button" class="btn btn-warning"
							ng-click="newFacility()">
							New Facility <i class="fa fa-plus"></i>
						</button>
					</c:if>
				</div>
				<div class="cmn_tbl">
					<table id="entry-grid" datatable="" dt-options="dtOptions"
						dt-instance="dtInstance" dt-columns="dtColumns"
						style="width: 100%;" class="angDataTable table table-hover"></table>
				</div>

				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
					tabindex="-1" id="newFacilitiesmyModal" class="modal fade">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
							<div class="modal-header">
								<button aria-hidden="true" data-dismiss="modal" class="close"
									type="button">x</button>
								<h4 class="modal-title">Facility</h4>
							</div>
							<div class="modal-body" style="overflow-y: auto;">
								<!-- modal data comes here -->
								<div class="form-horizontal tasi-form">
									<div class="form-group">
										<label class="control-label col-md-3"> Code <span
											class="red">*</span></label>
										<div class="col-md-6 col-xs-11">
											<input type="text" id="code" maxlength="15" id="txtCode"
												ng-disabled="facility.disbl"
												class="form-control validator form-control-inline input-medium"
												ng-model="facility.code" />
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
												ng-model="facility.name" />
										</div>
										<div class="col-md-1 col-xs-11 tick_green">
											<span id="name_check" class="switch-right"
												style="display: none;"><i class=" fa fa-check"></i></span> <span
												id="name_warning" class="switch-right warning_red"
												style="display: none;"><i class="fa fa-warning"></i></span>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-3"> Description </label>
										<div class="col-md-6 col-xs-11">
											<input type="text" maxlength="200"
												class="form-control form-control-inline input-medium"
												ng-model="facility.description" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3">Facility Type</label>
										<div class="col-md-6 col-xs-11">
											<select id="slctTxnType" class="form-control input-sm"
												ng-model="facility.facilityType">
												<c:forEach items="${facilityTypes}" var="facility">
													<option value="${facility.code}">${facility.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-3">Payment</label>
										<div class="col-md-6 col-xs-11 statusClass">
											<div class="slideThree">
												<input type="checkbox" id="slideThree"
													ng-model="facility.isPayable" name="check" /> <label
													for="slideThree"></label>
											</div>
										</div>
									</div>

									<div class="form-group" ng-if="facility.isPayable">
										<label class="control-label col-md-3"> Amount <span
											class="red">*</span></label>
										<div class="col-md-6 col-xs-11">
											<input type="text" min="0" maxlength="8" id="amount"
												class="form-control form-control-inline input-medium validator"
												ng-model="facility.amount"
												onkeypress="return numericalValidation($event)" numbers-only
												required />
										</div>
										<div class="col-md-1 col-xs-11 tick_green">
											<span id="amount_check" class="switch-right"
												style="display: none;"><i class=" fa fa-check"></i></span> <span
												id="amount_warning" class="switch-right warning_red"
												style="display: none;"><i class="fa fa-warning"></i></span>
										</div>
									</div>

									<div class="form-group" ng-if="facility.isPayable">
										<label class="control-label col-md-3"> Account <span
											class="red">*</span></label>
										<div class="col-md-6 col-xs-11" ng-click="clickOutSide()">

											<md-select ng-model="facility.accMstId"
												placeholder="select-account"
												class="md-no-underline form-control form-control-inline input-medium acc_select required"
												required md-no-asterisk="false"> <md-option
												ng-repeat="acc in acclist" ng-value="acc.id">{{acc.code}}</md-option>

											</md-select>
										</div>
										<div class="col-md-1 col-xs-11 tick_green">
											<span id="acc_check" class="switch-right"
												style="display: none;"><i class=" fa fa-check"></i></span> <span
												id="acc_warning" class="switch-right warning_red"
												style="display: none;"><i class="fa fa-warning"></i></span>
										</div>
									</div>
								</div>
								<!-- modal data comes here -->
							</div>
							<div class="modal-footer">
								<input type="hidden" id="baction" value="save"
									ng-model="facility.baction" />
								<button id="cancelProvider" type="button"
									class="btn btn-default rbtnClose" ng-click="cancelPopUp();">
									<spring:message code="pms.btn.cancel" />
								</button>
								<button id="saveFacilityBtn" type="button"
									class="btn btn-success" ng-click="save()">
									<spring:message code="pms.btn.save" />
								</button>
								<c:if test="${cp_isCanDelete}">
									<button id=deleteFacilityBtn type="button"
										class="btn btn-warning" ng-click="deleteFacility()">
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