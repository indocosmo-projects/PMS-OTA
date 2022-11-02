<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">

	<div class="panel-body">
		<div class="col-md-12">


			<div class="cmn_tbl">
				<table class="table table-striped table-hover table-bordered">
					<thead>
						<tr>
							<th>Shift no</th>
							<th>User</th>
							<th>Shift</th>
							<th>Floating Amount</th>
							<th>Shift Opening Date & Time</th>
							<th>Date</th>
							<th>Closing Date & Time</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="shiftlist in ShiftLIst">
							<td>{{shiftlist.shift_id}}</td>
							<td>{{shiftlist.loginusers}}</td>
							<td>{{shiftlist.CodeShift}}</td>
							<td>{{shiftlist.opening_float}}</td>
							<td>{{shiftlist.opening_date | date:dateFormat}}
								{{(shiftlist.opening_time).substring(11,19)}}</td>
							<td>{{shiftlist.opening_date | date:dateFormat}}</td>
							<td>{{shiftlist.closing_date | date:dateFormat}}
								{{(shiftlist.closing_time).substring(11,19)}}</td>
							<td><div class="close_btn_div">
									<button ng-disabled="shouldEnable(shiftlist.closing_time)"
										type="button" class="btn btn-warning"
										ng-click="newCloseShift()">
										Close Shift <i class="fa fa-caret-right" aria-hidden="true"></i>
									</button>
								</div></td>
						</tr>
					</tbody>
				</table>
			</div>

		</div>
	</div>







	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" id="newOpenShiftmyModal" class="modal fade">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Open Shift</h4>
				</div>

				<div class="modal-body" style="overflow-y: auto;">
					<!-- modal data comes here -->
					<div class="form-horizontal tasi-form">
						<div class="form-group">
							<label class="control-label col-md-3"> Hotel Date </label>
							<div class="col-md-6 col-xs-11 shiftal">
								<a class="hotelDate" href="#"><span> <b><fmt:formatDate
												pattern="${dateFormat}" value="${hotelDateEpoch}" /></b>
								</span> </a>

							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-md-3"> User </label>
							<div class="col-md-6 col-xs-11 shiftal">
								<input type="hidden" value="${loginId}" id="loginId">
								<c:out value="${loginName}" />
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-md-3"> Shift <span
								class="red">*</span> </span></label>
							<div class="col-md-6 col-xs-11">
								<select id="shiftsid" class="form-control input-sm"
									ng-model="OpenShift.Shifts"
									ng-options="x.id as x.code for x in Shifts">
									<option value="">SELECT</option>
								</select>
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-md-3"> Password <span
								class="red">*</span></label>
							<div class="col-md-6 col-xs-11" id="msg"
								style="color: red; display: none">Enter password</div>
							<div class="col-md-6 col-xs-11">
								<input type="password" id="passWrd" maxlength="100"
									class="form-control validator form-control-inline input-medium"
									ng-model="passwrd" autocomplete="off" />
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-md-3"> Floating Amount <span
								class="red">*</span>
							</label>
							<div class="col-md-6 col-xs-11" id="msg3"
								style="color: red; display: none">Floating amount cannot
								left empty</div>
							<div class="col-md-6 col-xs-11">
								<input type="text" id="floatingamount" maxlength="100"
									class="form-control validator form-control-inline input-medium"
									ng-model="floatingamount" numbers_only />
							</div>

						</div>
						<div class="modal-footer">
							<input type="hidden" id="baction" value="save"
								ng-model="shift.baction" />
							<button id="cancelProvider" type="button"
								class="btn btn-default rbtnClose" ng-click="cancelPopUpOpen();">
								<spring:message code="pms.btn.cancel" />
							</button>
							<button class="btn btn-success" type="button" ng-model="baction"
								ng-click="saveShift()">
								<spring:message code="pms.btn.save" />
							</button>
						</div>



					</div>
				</div>
			</div>
		</div>
	</div>












	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" id="newCloseShiftmyModal" class="modal fade">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Close Shift</h4>
				</div>

				<div class="modal-body" style="overflow-y: auto;">
					<!-- modal data comes here -->
					<div class="form-horizontal tasi-form">
						<div class="form-group">
							<label class="control-label col-md-3"> Hotel Date </label>
							<div class="col-md-6 col-xs-11">
								<a class="logo shiftal"
									style="float: left; color: black; font-size: 15px;" href="#"><span>
										<b><fmt:formatDate pattern="dd-MMM-yyyy"
												value="${hotelDateEpoch}" /></b>
								</span> </a>
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-md-3 "> User </label>
							<div class="col-md-6 col-xs-11 shiftal">
								{{currentShifts[0].loginuser}}</div>

						</div>

						<div class="form-group">
							<label class="control-label col-md-3 "> Shift </span></label>
							<div class="col-md-6 col-xs-11 shiftal">

								{{currentShifts[0].shiftCode}}</div>

						</div>

						<div class="form-group">
							<label class="control-label col-md-3"> Password <span
								class="red">*</span></label>
							<div class="col-md-6 col-xs-11" id="msg2"
								style="color: red; display: none">Enter password</div>
							<div class="col-md-6 col-xs-11" id="pass">
								<input type="password" id="passWord" maxlength="100"
									class="form-control validator form-control-inline input-medium"
									ng-model="password" ng-change="passWord" />
							</div>

						</div>

						<div class="form-group">
							<label class="control-label col-md-3 "> Floating Amount </label>
							<div class="col-md-6 col-xs-11 shiftal">
								{{currentShifts[0].opening_float}}</div>

						</div>
						<div class="modal-footer">
							<input type="hidden" id="baction" value="saveUpdate"
								ng-model="shift.baction" />
							<button id="cancelProvider" type="button"
								class="btn btn-default rbtnClose" ng-click="cancelPopUpClose();">
								<spring:message code="pms.btn.cancel" />
							</button>
							<button id="saveCloseShiftBtn" type="button"
								class="btn btn-success" ng-click="saveUpdate()"
								value="saveUpdate" ng-model="baction">
								<spring:message code="pms.btn.save" />
							</button>
						</div>



					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	</div>
	</div>
</section>

