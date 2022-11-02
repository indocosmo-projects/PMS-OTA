
<jsp:directive.include file="../common/includes/page_directives.jsp" />
<div class="row" id="summary">
	<input type="hidden" value="${hotelDate}" id="txtHDate" /> <input
		type="hidden" id="dateFormat" value="${dateFormat}">
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<div class="panel-body">
				<div class="dtls summury">
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="status.label.checkIn" />
							</div>
							<input type="hidden" id="hdnfolioNo" value="${folioNo}" /> <input
								type="hidden" id="checkInNo" value="${checkInNo}">
							<div id="reser_id" class="col-md-12 s-body">${checkInNo}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.label.deposit.arivalDate" />
							</div>
							<div id="" class="col-md-12 s-body">
								<fmt:formatDate pattern="${dateFormat}" value="${checkInDate}" />
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.label.folioBalance" />
							</div>
							<div id="divNights" class="col-md-12 s-body">${folioBalance}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.label.deposit.departDate" />
							</div>
							<div id="divCorporateTA" class="col-md-12 s-body">
								<fmt:formatDate pattern="${dateFormat}" value="${departDate}" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<section class="panel padding_bottom_zrw">
			<input type="hidden" id="gst" value="${gstno}"> <input
				type="hidden" id="nationality" value="${nationality}"> <input
				type="hidden" id="state" value="${state}">
				<input type="hidden" id="txn_edit_after_payment" value="${txn_edit_after_payment}" /> 
			<input type="hidden" id="isFullySettled" value="${isFullySettled}" /> 
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="transaction.label.transaction" />
				</h1>
				<!-- <span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span> -->
			</header>
			<div class="panel-body paymentDtlsDiv">
				<div class="row">
					<div class="col-lg-12">
						<div class="detail_card col-md-5">
							<div class="row dtlHdr bg_colo_text" id="bgColour">
								<spring:message code="checkin.label.reservedBy" />
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="dtl_label">
										<spring:message code="checkin.label.name" />
									</div>
									<div class="resv-txt">${name}</div>
								</div>
								<div class="row">
									<div class="dtl_label">
										<spring:message code="checkin.label.email" />
									</div>
									<div class="resv-txt">${email}</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="dtl_label">
										<spring:message code="reservation.label.phone" />
									</div>
									<div class="resv-txt">${phone}</div>
								</div>
								<div class="row">
									<div class="dtl_label">
										<spring:message code="reservation.label.address" />
									</div>
									<div class="resv-txt">${address}</div>
								</div>
							</div>
						</div>
						<div class="col-md-2"></div>
						<div class="detail_card col-md-5">
							<div class="row dtlHdr bg_colo_text" id="bgColour">
								<spring:message code="Payment.label.roomDetails" />
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="dtl_label">
										<spring:message code="reservation.label.number" />
									</div>
									<div class="resv-txt">${roomNo}</div>
								</div>
								<div class="row">
									<div class="dtl_label">
										<spring:message code="transaction.label.Roomtype" />
									</div>
									<div class="resv-txt">${roomType}</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="row">
									<div class="dtl_label">
										<spring:message code="confirm.label.totaldeposits" />
									</div>
									<div class="resv-txt">${totalDeposit}</div>
								</div>
								<div class="row">
									<div class="dtl_label">
										<spring:message code="reservation.label.roomcharge" />
									</div>

									<c:forEach items="${RoomChargeCheckIn}" var="roomCheck">
										<table>
											<tr>
												<td><c:out value="${roomCheck.roomCharge}"></c:out></td>
											</tr>
										</table>
									</c:forEach>



								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="dataTableDiv" class="col-lg-12">
					<div>
						<div class="form-group paymentBtn align-btn-rgt topAddButtonDiv">
							<c:if test="${cp_isCanAdd && isFullySettled==false}">
								<button type="button" class="btn btn-warning"
									ng-click="newPosting()">
									New Posting <i class="fa fa-plus"></i>
								</button>
							</c:if>
						</div>
						 <table id="entry-grid" datatable="" dt-options="dtOptions"
							dt-instance="dtInstance" dt-columns="dtColumns"
							style="width: 100%;" class="angDataTable table table-hover"></table> 

						<div aria-hidden="true" aria-labelledby="myModalLabel"
							role="dialog" tabindex="-1" id="newPostingmyModal"
							class="modal fade">
							<div class="modal-dialog modal-md">
								<div class="modal-content">
									<div class="modal-header">
										<button aria-hidden="true" data-dismiss="modal" class="close"
											type="button">x</button>
										<h4 class="modal-title">Transaction Posting</h4>
									</div>
									<div class="modal-body" style="overflow-y: auto;">
										<!-- modal data comes here -->
										<div class="form-horizontal tasi-form">
										
										<div class="form-group">
												<label class="control-label col-md-3">Date</label>
												
												
												<div class="col-md-4 col-xs-11">
												<div class="calenderControls">
													<md-datepicker required ng-model="posting.txnDate" id = "txnDate"
														 class="form-control"
														md-open-on-focus></md-datepicker>
												</div>
											</div>
												
												
										</div>
										
											<div class="form-group">
												<label class="control-label col-md-3"><spring:message
														code="status.label.txncode"></spring:message></label>
												<div class="col-md-6 col-xs-11">
													<select id="slctTxnType"
														class="form-control input-sm disableDel"
														ng-model="posting.txnType" ng-change="amountChange()">
														<c:forEach items="${accountMaster}" var="accMst">
															<option data-taxInc="${accMst.is_tax_included}"
																value="${accMst.id}">${accMst.code}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-1 col-xs-11 tick_green">
													<span id="amt_check" class="switch-right"
														style="display: none;"><i class=" fa fa-check"></i></span>
													<span id="amt_warning" class="switch-right warning_red"
														style="display: none;"><i class="fa fa-warning"></i></span>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-md-3"><spring:message
														code="status.label.postas"></spring:message></label>
												<div class="col-md-6 col-xs-11">
													<select id="slctTxnMode" class="form-control input-sm"
														ng-model="posting.txnMode" ng-change="amountChange()"
														ng-options="x.id as x.code for x in postMode">
													</select>
												</div>
											</div>

											<div class="form-group">
												<label class="control-label col-md-3"> <spring:message
														code="reservation.label.deposit.amount"></spring:message>
													<span class="red">*</span>
												</label>

												<div class="col-md-5 col-xs-11 Amount">
													<input id="amt" ng-change="amountChange()"
														class="form-control validator align-right"
														ng-model="posting.amount" maxlength="6">
													<!-- 														 ng-keypress="numericalValidation($event)"
 -->
												</div>

												<div class="col-md-1 col-xs-11 tick_green">
													<span id="amt_check" class="switch-right"
														style="display: none;"><i class=" fa fa-check"></i></span>
													<span id="amt_warning" class="switch-right warning_red"
														style="display: none;"><i class="fa fa-warning"></i></span>
												</div>

											</div>

											<!-- base amount -->
											<div class="form-group">
												<label class="control-label col-md-3"> <spring:message
														code="status.label.baseamount"></spring:message>
												</label>
												<div class="col-md-5 col-xs-11 bseAmount">
													<input type="text" id="bse_amt"
														class="form-control  align-right" disabled="disabled"
														ng-model="charges.baseAmnt">
												</div>
											</div>
											<c:if
												test="${systemSettings.tax1Name ne null && systemSettings.tax1Name ne ''}">
												<div class="form-group">
													<label class="control-label col-md-3">${systemSettings.tax1Name}</label>
													<div class="col-md-3 col-xs-11">
														<input type="text" id="tax1_id"
															class="form-control form-control-inline input-medium align-right"
															disabled="disabled" ng-model="charges.tax1amount">
													</div>
													<div class="col-md-1 col-xs-11">
														<span id="tax1_pc" class="tax_1" ng-model="charges.tax2pc">{{charges.tax2pc}}</span>%
													</div>
												</div>
											</c:if>
											<c:if
												test="${systemSettings.tax2Name ne null && systemSettings.tax2Name ne ''}">
												<div class="form-group">
													<label class="control-label col-md-3">${systemSettings.tax2Name}</label>
													<div class="col-md-3 col-xs-11">
														<input type="text" id="tax2_id"
															class="form-control form-control-inline input-medium align-right"
															disabled="disabled" ng-model="charges.tax2amount">
													</div>
													<div class="col-md-1 col-xs-11">
														<span id="tax1_pc" class="tax_2" ng-model="charges.tax2pc">{{charges.tax2pc}}</span>%
													</div>
												</div>
											</c:if>
											<c:if
												test="${systemSettings.tax3Name ne null && systemSettings.tax3Name ne ''}">
												<div class="form-group">
													<label class="control-label col-md-3">${systemSettings.tax3Name}</label>
													<div class="col-md-3 col-xs-11">
														<input type="text" id="tax3_id"
															class="form-control form-control-inline input-medium align-right"
															disabled="disabled" ng-model="charges.tax3amount">
													</div>
													<div class="col-md-1 col-xs-11">
														<span id="tax1_pc" class="tax_3" ng-model="charges.tax3pc">{{charges.tax3pc}}</span>%
													</div>
												</div>
											</c:if>
											<c:if
												test="${systemSettings.tax4Name ne null && systemSettings.tax4Name ne ''}">
												<div class="form-group">
													<label class="control-label col-md-3">${systemSettings.tax2Name}</label>
													<div class="col-md-3 col-xs-11">
														<input type="text" id="tax4_id"
															class="form-control form-control-inline input-medium align-right"
															disabled="disabled" ng-model="charges.tax4amount">
													</div>
													<div class="col-md-1 col-xs-11">
														<span id="tax1_pc" class="tax_4" ng-model="charges.tax4pc">{{charges.tax4pc}}</span>%
													</div>
												</div>
											</c:if>
											<c:if test="${systemSettings.serviceCharge}">
												<div class="form-group">
													<label class="control-label col-md-3">Service
														Charge</label>
													<div class="col-md-3 col-xs-11">
														<input type="text"
															class="form-control form-control-inline input-medium align-right"
															disabled="disabled" ng-model="charges.serviceCharge">
													</div>
													<div class="col-md-1 col-xs-11">
														<span class="tax_2" ng-model="charges.serviceChargePc">{{charges.serviceChargePc}}</span>%
													</div>

												</div>
											</c:if>
											<div class="form-group">
												<label class="control-label col-md-3">Nett Amount </label>
												<div class="col-md-5 col-xs-11 bseAmount">
													<input type="text" id="nett_amt"
														class="form-control  align-right" disabled="disabled"
														ng-model="charges.nettAmount">
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-md-3">Is Adjust</label>
												<div class="col-md-6 col-xs-11">
													<select id="slctTxnMode"
														class="form-control input-sm disableDel"
														ng-model="posting.isAdjust"
														ng-options="x.id as x.code for x in isAdjust">
													</select>
												</div>
											</div>
											<div class="form-group">
												<label class="control-label col-md-3"><spring:message
														code="status.label.remarks"></spring:message></label>
												<div class="col-md-6 col-xs-11">
													<textarea
														class="form-control form-control-inline input-medium disableDel"
														id="remarks" ng-model="posting.remarks" maxlength="100">{{posting.remarks}}</textarea>
												</div>
											</div>
										</div>
										<!-- modal data comes here -->
									</div>
									<div class="modal-footer">
										<input type="hidden" id="baction" value="save"
											ng-model="posting.baction" />
										<button id="cancelrateplan" type="button" 
											class="btn btn-default rbtnClose" ng-click="cancelPopUp();">
											<spring:message code="pms.btn.cancel" />
										</button>
										<%-- <button id="savePostingBtn" type="button"
											class="btn btn-success" ng-disabled="postTransactionBtn" ng-click="save()">
											<spring:message code="pms.btn.save" />
										</button> --%>
										<button id="savePostingBtn" type="button"
											class="btn btn-success" ng-click="save()">
											<spring:message code="pms.btn.save" />
										</button>
										<c:if test="${cp_isCanDelete}">
											<button id="deletePostingBtn" type="button"
												class="btn btn-warning" ng-click="deletePosting()">
												<spring:message code="pms.btn.delete" />
											</button>
										</c:if>
									</div>
								</div>
							</div>
						</div>
						
		<!-- 	posAccMstNameEditModel		 -->	
		
		
		<div aria-hidden="true" aria-labelledby="myModalLabel"
							role="dialog" tabindex="-1" id="newPostingmyModalPOS"
							class="modal fade">
							<div class="modal-dialog modal-md">
								<div class="modal-content">
									<div class="modal-header">
										<button aria-hidden="true" data-dismiss="modal" class="close"
											type="button">x</button>
										<h4 class="modal-title">Account Name</h4>
									</div>
									<div class="modal-body" style="overflow-y: auto;">
										<!-- modal data comes here -->
										<div class="form-horizontal tasi-form">
										
									<div class="form-group">
												<label class="control-label col-md-3"><spring:message
														code="status.label.txncode"></spring:message></label>
												<div class="col-md-6 col-xs-11">
													<select id="posAccMStName"
														class="form-control input-sm "
														ng-model="posAccMStName" >
														<option value="" selected disabled hidden>select</option>
														<c:forEach items="${posAccountMaster}" var="accMstPos">
															
															<option 
																value="${accMstPos.name}">${accMstPos.name}</option>
														</c:forEach>
													</select>
												</div>
												<div class="col-md-1 col-xs-11 tick_green">
													<span id="amt_check" class="switch-right"
														style="display: none;"><i class=" fa fa-check"></i></span>
													<span id="amt_warning" class="switch-right warning_red"
														style="display: none;"><i class="fa fa-warning"></i></span>
												</div>
											</div>
										
											
											
										</div>
										<!-- modal data comes here -->
									</div>
									<div class="modal-footer">
										<input type="hidden" id="baction" value="save"
											ng-model="posting.baction" />
										
										<button id="savePostingBtn" type="button"
											class="btn btn-success" ng-click="savePosAccNAme()">
											<spring:message code="pms.btn.save" />
										</button>
										
									</div>
								</div>
							</div>
						</div>
		
						
						



					</div>
				</div>
			</div>

		</section>

	</div>
</div>
<%-- <section class="panel padding_bottom_zrw">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<button id="btnPrev" class="btn btn-default" type="button"
				ng-click="previousPage()">
				<spring:message code="checkin.button.label.back"></spring:message>
			</button>
			<c:if test="${checkOutFolioBindNo!=0}">

				<button id="btnCheckOut" class="btn btn-primary" type="button"
					ng-click="goToCheckOut(${checkOutFolioBindNo})">
					<spring:message code="checkOut.button.label.checkOut"></spring:message>
				</button>
			</c:if>
		</div>


	</div>
</section> --%>

