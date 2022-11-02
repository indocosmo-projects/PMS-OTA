<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel padding_bottom_zrw">

	<div class="panel-body usersDiv">
		<div class="form-group newGroupBtn align-btn-rgt topAddButtonDiv">

			<c:if test="${cp_isCanAdd}">
				<button type="button" class="btn btn-warning" ng-click="newUser()">
					Add User <i class="fa fa-plus"></i>
				</button>
			</c:if>

		</div>
		<div class="cmn_tbl">
			<table id="entry-grid" datatable="" dt-options="userCtrl.dtOptions"
				dt-instance="userCtrl.dtInstance" dt-columns="userCtrl.dtColumns"
				style="width: 100%;" class="angDataTable table table-hover"></table>
		</div>

		<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
			tabindex="-1" id="usermyModal" class="modal fade">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button aria-hidden="true" data-dismiss="modal" class="close"
							type="button">×</button>
						<h4 class="modal-title">New User</h4>
					</div>
					<div class="modal-body" style="overflow-y: auto;">
						<!-- modal data comes here -->
						<div class="form-horizontal tasi-form">
							<div class="form-group">
								<label class="control-label col-md-3">Login Id<span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">
									<input type="text" id="logId" ng-disabled="user.disbl"
										ng-model="user.logId" maxlength="19"
										class="validator form-control form-control-inline input-medium"
										required />
								</div>
								<div class="col-md-1 col-xs-11 tick_green">
									<span id="logId_warning" class="switch-right warning_red"
										style="display: none;"><i class="fa fa-warning"></i></span>
								</div>
							</div>
							<div class="form-group" ng-show="user.showpass">
								<label class="control-label col-md-3">Password<span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">
									<input type="text" id="txtUserPassword"
										ng-model="user.password"
										class="validator form-control form-control-inline input-medium"
										required />
								</div>
								<div class="col-md-1 col-xs-11 tick_green">
									<span id="txtUserPassword_check" class="switch-right"
										style="display: none;"><i class=" fa fa-check"></i></span> <span
										id="txtUserPassword_warning" class="switch-right warning_red"
										style="display: none;"><i class="fa fa-warning"></i></span>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">User Group<span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">
									<select id="slctUserGroup" class="form-control input-sm"
										ng-model="user.userGroup"
										ng-options="x.id as x.code for x in userGroups">
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">Status</label>
								<div class="col-md-6 col-xs-11 statusClass">
									<div class="slideThree">
										<input type="checkbox" id="slideThree" ng-model="user.status"
											name="check" /> <label for="slideThree"></label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">Name<span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">
									<input type="text" id="txtName" ng-model="user.name"
										maxlength="19"
										class="validator form-control form-control-inline input-medium"
										required />
								</div>
								<div class="col-md-1 col-xs-11 tick_green">
									<span id="txtName_check" class="switch-right"
										style="display: none;"><i class=" fa fa-check"></i></span> <span
										id="txtName_warning" class="switch-right warning_red"
										style="display: none;"><i class="fa fa-warning"></i></span>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">Email<span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">
									<input type="text" id="txtEmail" ng-model="user.email"
										class="validator form-control form-control-inline input-medium"
										required />
								</div>
								<div class="col-md-1 col-xs-11 tick_green">
									<span id="txtEmail_check" class="switch-right"
										style="display: none;"><i class=" fa fa-check"></i></span> <span
										id="txtEmail_warning" class="switch-right warning_red"
										style="display: none;"><i class="fa fa-warning"></i></span>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3">Is Cashier</label>
								<div class="col-md-6 col-xs-11 statusClass">

									<div class="input-group" id="is_cashier">

										<md-checkbox type="checkbox" ng-model="user.isCashier"
											ng-true-value="true" ng-false-value="false"
											ng-disabled="disable_all" aria-label="setasnotcashier"></md-checkbox>




									</div>


								</div>
								<!-- modal data comes here -->
							</div>
							<div class="modal-footer">
								<input type="hidden" id="baction" value="save"
									ng-model="uGroup.baction" />
								<button id="cancelrateplan" type="button"
									class="btn btn-default rbtnClose" ng-click="cancelPopUp()">
									<spring:message code="pms.btn.cancel" />
								</button>
								<button id="savePaymentBtn" type="button"
									class="btn btn-success" ng-click="save()">
									<spring:message code="pms.btn.save" />
								</button>
								<button id="savePaymentBtn" type="button"
									class="btn btn-warning" ng-click="reset()">
									<spring:message code="pms.btn.reset" />
								</button>
								<c:if test="${cp_isCanDelete}">
									<button id="delete_btn" class="btn btn-danger" type="button"
										ng-click="deleteUser()">
										<i class="fa fa-trash-o "></i>
										<spring:message code="pms.btn.delete" />
									</button>
								</c:if>

							</div>
						</div>
					</div>
				</div>
			</div>
</section>
<section class="panel padding_bottom_zrw">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<button class="btn btn-default" ng-click="cancel()">
				<spring:message code="pms.btn.cancel"></spring:message>
			</button>
		</div>
	</div>
</section>