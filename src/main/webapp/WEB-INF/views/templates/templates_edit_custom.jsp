<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@ page import="com.indocosmo.pms.enumerator.CommunicationPurposes"%>
<%@ page import="com.indocosmo.pms.enumerator.CommunicationGroups"%>
<section class="panel">
	<div class="panel-body">
		<!-- <div class="dtls"> -->
		<div>
			<div ng-cloak>
				<md-content> <md-tabs md-dynamic-height
					md-border-bottom> <md-tab label="invoice"> <md-content
					class="md-padding">

				<form name="invoiceForm" novalidate>
					<div class="headerBox">
						<h2 class="headerStyle">
							<span>Header</span>
						</h2>
					</div>
					<div class="col-md-12 divRow">
						<div class="col-md-4">
							<div class="row">
								<div class="col-sm-4">
									<label>Line 1</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="hdrInp form-control form-control-inline input-medium"
										ng-model="invoice.hdrLine1" />
								</div>
							</div>

						</div>
						<div class="col-md-4">
							<div class="row">
								<div class="col-sm-4">
									<label>Line 2</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="hdrInp form-control form-control-inline input-medium"
										ng-model="invoice.hdrLine4" />
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="row">
								<div class="col-sm-4">
									<label>Line 3</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="hdrInp form-control form-control-inline input-medium"
										ng-model="invoice.hdrLine3" />
								</div>
							</div>
						</div>
						<div class="col-sm-8">

							<div class="row">
								<div class="col-sm-2">
									<label>Line 4</label>
								</div>
								<div class="col-sm-10">
									<input type="text" maxlength="30"
										class="hdrInp form-control form-control-inline input-medium"
										ng-model="invoice.hdrLine2" />
								</div>
							</div>
						</div>
					</div>

					<div class="headerBox">
						<h2 class="headerStyle">
							<span>Footer</span>
						</h2>
					</div>
					<div class="col-md-12 divRow">
						<div class="col-md-4">

							<div class="row">
								<div class="col-sm-4">
									<label>Line 1</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol1Line1" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 2</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol1Line2" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 3</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol1Line3" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 4</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol1Line4" />
								</div>
							</div>
						</div>
						<div class="col-md-4">

							<div class="row">
								<div class="col-sm-4">
									<label>Line 1</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol2Line1" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 2</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol2Line2" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 3</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol2Line3" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 4</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol2Line4" />
								</div>
							</div>
						</div>
						<div class="col-md-4">

							<div class="row">
								<div class="col-sm-4">
									<label>Line 1</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol3Line1" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 2</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol3Line2" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 3</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol3Line3" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<label>Line 4</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="invoice.ftrCol3Line4" />
								</div>
							</div>
						</div>
					</div>

					<div class="headerBox">
						<h2 class="headerStyle">
							<span>Signature</span>
						</h2>
					</div>
					<div class="col-md-12 divRow">
						<div class="col-md-6">
							<div class="col-sm-4">
								<label>Line 1</label>
							</div>
							<div class="col-sm-8">
								<input type="text" maxlength="30"
									class="ftrInp form-control form-control-inline input-medium"
									ng-model="invoice.signLine1" />
							</div>
						</div>
						<div class="col-md-6">
							<div class="col-sm-4">
								<label>Line 2</label>
							</div>
							<div class="col-sm-8">
								<input type="text" maxlength="30"
									class="ftrInp form-control form-control-inline input-medium"
									ng-model="invoice.signLine2" />
							</div>
						</div>
					</div>
					<div class="col-md-12 divRow">
						<div class="includeDiv col-sm-5">
							<md-checkbox ng-model="invoice.isHFIncl" aria-label="Checkbox 1">
							<label>Include Header And Footer</label></md-checkbox>
						</div>
						<div class="includeDiv col-sm-4">
							<md-checkbox ng-model="invoice.isTaxsumryincl"
								aria-label="Checkbox 2"> <label>Include
								Tax Summary</label></md-checkbox>
						</div>
						<div class="col-md-3">
							<c:if test="${cp_isCanEdit}">
								<button class="btn btn-success saveInvBtn" ng-click="save()">
									<spring:message code="pms.btn.save"></spring:message>
								</button>
						</div>
						</c:if>
					</div>
				</form>
				</md-content> </md-tab> <md-tab label="e-mail"> <md-content class="md-padding">

				<div class="newEmailBtnDiv" id="btnMargin">
					<button class="btn btn-warning" ng-click="newEmailTemplate()">Add
						New</button>
				</div>

				<div class="cmn_tbl">
					<table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th>CODE</th>
								<th>PURPOSE</th>
								<th>GROUP</th>
								<th>SUBJECT</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="email in emailTmp">
								<td><a ng-click="showEmailPop(email)">{{email.code}}</a></td>
								<td>{{email.purposeCode}}</td>
								<td>{{email.groupCode}}</td>
								<td>{{email.subject}}</td>
							</tr>
						</tbody>
					</table>
				</div>
				</md-content> </md-tab> <md-tab label="sms"> <md-content class="md-padding">
				<div class="newEmailBtnDiv" id="btnMargin">
					<button class="btn btn-warning" ng-click="newSmsTemplate()">Add
						New</button>
				</div>
				<div class="cmn_tbl">
					<table class="table table-striped table-hover table-bordered">
						<thead>
							<tr>
								<th>CODE</th>
								<th>PURPOSE</th>
								<th>GROUP</th>
								<th>CONTENT</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="sms in smsTmp">
								<td><a ng-click="showSmsPop(sms)">{{sms.code}}</a></td>
								<td>{{sms.purposeCode}}</td>
								<td>{{sms.groupCode}}</td>
								<td>{{sms.content}}</td>
							</tr>
						</tbody>
					</table>
				</div>
				</md-content> </md-tab> </md-tabs> </md-content>
			</div>
		</div>
	</div>

	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" id="newEmailTemplmyModal" class="modal fade">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">×</button>
					<h4 class="modal-title">Email Template</h4>
				</div>
				<div class="modal-body" style="overflow-y: auto;">
					<!-- modal data comes here -->
					<div class="form-horizontal tasi-form">

						<div class="row">
							<div class="col-md-4">
								<label class="control-label col-sm-3"> Code</label>
								<div class="col-sm-9">
									<input type="text" id="code" maxlength="15" id="txtCode"
										class="form-control validator form-control-inline input-medium"
										ng-model="emailTempl.code" />
								</div>
							</div>

							<div class="col-md-4">
								<label class="control-label col-sm-3">Purpose</label>
								<div class="col-sm-9">
									<select id="slctTxnType" class="form-control input-sm"
										ng-model="emailTempl.purpose">
										<c:forEach items="${CommunicationPurposes}" var="purpose">
											<option value="${purpose.code}">${purpose}</option>
										</c:forEach>
									</select>
								</div>
							</div>


							<div class="col-md-4">
								<label class="control-label col-sm-3">Group</label>
								<div class="col-sm-9">
									<select id="slctEmailGroup" class="form-control input-sm"
										ng-model="emailTempl.group">
										<c:forEach items="${CommunicationGroups}" var="group">
											<option value="${group.code}">${group}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<div class="row subj">
							<div class="col-md-6">
								<label class="control-label col-md-3">Subject</label>
								<div class="col-md-9">
									<input type="text" id="subject" maxlength="50" id="txtSubject"
										class="form-control validator form-control-inline input-medium"
										ng-model="emailTempl.subject" />
								</div>
							</div>
						</div>
						<div class="textEditorDiv">
							<form name="mailContentFrm">
								<div class="form-group">
									<text-angular name="textBox" class="templateText"
										ng-model="emailTempl.content" required></text-angular>
								</div>
							</form>
						</div>
					</div>
					<!-- modal data comes here -->
				</div>
				<div class="modal-footer">

					<button id="cancelEmailTemp" type="button"
						class="btn btn-default rbtnClose" ng-click="cancelEmailPopUp();">
						<spring:message code="pms.btn.cancel" />
					</button>

					<button id="saveEmailTemp" type="button" class="btn btn-success"
						ng-click="saveEmail()">
						<spring:message code="pms.btn.save" />
					</button>
					<button id="deleteEmailTemp" type="button" class="btn btn-danger"
						ng-click="deleteEmail()">
						<spring:message code="pms.btn.delete" />
					</button>
				</div>
			</div>
		</div>
	</div>



	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" id="newSmsTemplmyModal" class="modal fade">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">×</button>
					<h4 class="modal-title">Sms Template</h4>
				</div>
				<div class="modal-body" style="overflow-y: auto;">
					<!-- modal data comes here -->
					<div class="form-horizontal tasi-form">


						<div class="form-group">
							<label class="control-label col-sm-3"> Code</label>
							<div class="col-sm-6">
								<input type="text" id="code" maxlength="15" id="txtCode"
									class="form-control validator form-control-inline input-medium"
									ng-model="smsTempl.code" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3">Purpose</label>
							<div class="col-sm-6">
								<select id="slctTxnType" class="form-control input-sm"
									ng-model="smsTempl.purpose">
									<c:forEach items="${CommunicationPurposes}" var="purpose">
										<option value="${purpose.code}">${purpose}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3">Group</label>
							<div class="col-sm-6">
								<select id="slctSmsGroup" class="form-control input-sm"
									ng-model="smsTempl.group">
									<c:forEach items="${CommunicationGroups}" var="group">
										<option value="${group.code}">${group}</option>
									</c:forEach>
								</select>
							</div>
						</div>


						<div class="form-group">
							<label class="control-label col-sm-3">Content</label>
							<div class="col-sm-6">
								<textarea id="txtContent" ng-model="smsTempl.content" required></textarea>
							</div>
						</div>
					</div>
					<!-- modal data comes here -->
				</div>
				<div class="modal-footer">

					<button id="cancelSmsTemp" type="button"
						class="btn btn-default rbtnClose" ng-click="cancelSmsPopUp();">
						<spring:message code="pms.btn.cancel" />
					</button>

					<button id="saveSmsTemp" type="button" class="btn btn-success"
						ng-click="saveSms()">
						<spring:message code="pms.btn.save" />
					</button>
					<button id="deleteSmsTemp" type="button" class="btn btn-danger"
						ng-click="deleteSms()">
						<spring:message code="pms.btn.delete" />
					</button>
				</div>
			</div>
		</div>
	</div>
</section>