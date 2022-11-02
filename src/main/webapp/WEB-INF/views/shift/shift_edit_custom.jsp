<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<div class="panel-body">
		<div id="dataTableDiv" class="col-lg-12">
			<div>
				<div class="form-group align-btn-rgt topAddButtonDiv" id="listPos">
					<c:if test="${cp_isCanAdd}">
						<%-- <button type="button" class="btn btn-warning"
							ng-click="newShift()">
							New Shift <i class="fa fa-plus"></i>
						</button>
						  <c:if test="${cp_isCanAdd}"> --%>
						<button type="button" class="btn btn-primary"
							ng-click="newShift()" id="btnShowAddModal">
							<i class="fa fa-plus"></i> NEW
						</button>
					</c:if>
				</div>
				<div class="cmn_tbl">
					<table id="entry-grid" datatable="" dt-options="dtOptions"
						dt-instance="dtInstance" dt-columns="dtColumns"
						style="width: 100%;" class="angDataTable table table-hover"></table>
				</div>
				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
					tabindex="-1" id="newShiftmyModal" class="modal fade">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
							<div class="modal-header">
								<button aria-hidden="true" data-dismiss="modal" class="close"
									type="button">×</button>
								<h4 class="modal-title">Shift</h4>
							</div>
							<div class="modal-body" style="overflow-y: auto;">
								<!-- modal data comes here -->
								<div class="form-horizontal tasi-form">
									<div class="form-group">
										<label class="control-label col-md-3"> Code <span
											class="red">*</span></label>
										<div class="col-md-6 col-xs-11">
											<input type="text" id="code" maxlength="15" id="txtCode"
												ng-disabled="shift.disbl"
												class="form-control validator form-control-inline input-medium"
												ng-model="shift.code" />
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
											<input type="text" id="name" maxlength="40"
												class="form-control validator form-control-inline input-medium"
												ng-model="shift.name" />
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
												ng-model="shift.description" />
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-3"
											style="padding-top: 35px;"> Shift Starts at <span
											class="red">*</span>
										</label>
										<div class="col-md-3 col-xs-11">
											<div uib-timepicker ng-model="shift.startTime"
												ng-change="changed()" hour-step="hstep" minute-step="mstep"
												second-step="sstep" show-meridian="ismeridian"></div>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-3"
											style="padding-top: 35px;"> Shift ends at <span
											class="red">*</span>
										</label>

										<div class="col-md-3 col-xs-11">
											<div uib-timepicker ng-model="shift.endTime"
												ng-change="changed()" hour-step="hstep" minute-step="mstep"
												second-step="sstep" show-meridian="ismeridian"></div>
										</div>
									</div>
								</div>
								<!-- modal data comes here -->

							</div>


							<div class="modal-footer">
								<input type="hidden" id="baction" value="save"
									ng-model="shift.baction" />
								<button id="cancelProvider" type="button"
									class="btn btn-default rbtnClose" ng-click="cancelPopUp();">
									<spring:message code="pms.btn.cancel" />
								</button>
								<button id="saveShiftBtn" type="button" class="btn btn-success"
									ng-click="save()">
									<spring:message code="pms.btn.save" />
								</button>
								<c:if test="${cp_isCanDelete}">
									<button id=deleteShiftBtn type="button" class="btn btn-warning"
										ng-click="deleteShift()">
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