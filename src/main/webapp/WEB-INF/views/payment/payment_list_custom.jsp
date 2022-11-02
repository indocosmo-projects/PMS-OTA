<jsp:directive.include file="../common/includes/page_directives.jsp" />
<div class="row" id="summary">
	<input type="hidden" value="${hotelDate}" id="txtHDate" />
	<div class="col-lg-12" ng-app="pmsApp" ng-controller="paymentCtrl"
		ng-cloak>
		<section class="panel">
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="reservation.label.deposit.summary" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span>
			</header>
			<div class="panel-body">
				<div class="dtls">
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="status.label.checkIn" />
							</div>
							<input type="hidden" id="gst" value="${gstno}">
							<input type="hidden" id="nationality" value="${nationality}">
							<input type="hidden" id="state" value="${state}">
							<input type="hidden" id="hdnfolioNo" value="${folioNo}" /> <input
								type="hidden" id="canPay" value="${payable.canPay}" />
							<input type="hidden" id="checkInNo" value="${checkInNo}">
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
		<section class="panel">
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="Payment.label.details" />
				</h1>
			</header>
			<div class="panel-body paymentDtlsDiv">
				<div class="row">
					<div class="col-lg-12">
						<div class="col-md-6">
							<div class="input-group userDtls">
								<span class="input-group-addon" id="sizing-addon2"><i
									class="fa fa-user" aria-hidden="true"></i></span>
								<div class="form-control dtlsInp"
									aria-describedby="sizing-addon2">${name}</div>
							</div>
							<div class="input-group userDtls">
								<span class="input-group-addon" id="sizing-addon2"><i
									class="fa fa-at" aria-hidden="true"></i></span>
								<div class="form-control dtlsInp"
									aria-describedby="sizing-addon2">${email}</div>
							</div>
							<div class="input-group userDtls">
								<span class="input-group-addon" id="sizing-addon2"><i
									class="fa fa-phone" aria-hidden="true"></i></span>
								<div class="form-control dtlsInp"
									aria-describedby="sizing-addon2">${phone}</div>
							</div>
							<div class="input-group userDtls">
								<span class="input-group-addon" id="sizing-addon2"><i
									class="fa fa-envelope-o" aria-hidden="true"></i></span>
								<div class="form-control dtlsInp" id="addr"
									aria-describedby="sizing-addon2">${address}</div>
							</div>
						</div>
						<div class="roomDtls col-md-6">
							<div class="panel panel-primary" id="bgColour">
								<div class="panel-heading" id="bgColour">
									<h3 class="panel-title">Room Details</h3>
								</div>
								<div class="panel-body">
									<div class="col-sm-3">
										<div class="rdhdr">
											<spring:message code="reservation.label.number" />
										</div>
										<div class="rddtl">${roomNo}</div>
									</div>
									<div class="col-sm-4">
										<div class="rdhdr">
											<spring:message code="transaction.label.Roomtype" />
										</div>
										<div class="rddtl">${roomType}</div>
									</div>
									<div class="col-sm-4 paydtls">
										<div class="rdhdr">
											<spring:message code="reservation.label.deposit.totalPayable" />
										</div>
										<%-- <div id="divStatus" class="rddtl">${payable.total}</div> --%>
										<div id="divStatus" class="rddtl">${payable.total}</div>
										<input type="hidden" id="amtpayable" value="${payable.total}">
									</div>
									<div class="col-sm-1 showdtls">
										<a href="#" ng-click="showRateDtls();">Details</a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						<!-- <md-toolbar class="md-theme-light">
						<h2 class="md-toolbar-tools">
							<span>New Payment</span>
						</h2>
						</md-toolbar> -->
						<md-content> <md-list> <md-list-item
							class="md-3-line"> <%-- <div class="md-list-item-text">
							<div class="itemsDiv col-md-2">
								<label><spring:message code="Payment.label.paymentType"></spring:message><span
									class="red">*</span></label>
								<p>
									<select id="slctPaymentType" class="form-control input-sm"
										ng-model="payment.pmtType" ng-change="changePayType()"
										ng-options="x.pTypeId as x.pmType for x in payType">
									</select>
								</p>
							</div>
							<div class="itemsDiv col-md-2">
								<label><spring:message code="Payment.label.paymentMode"></spring:message><span
									class="red">*</span></label>
								<p>
									<select id="slctPaymentMode" class="form-control input-sm"
										ng-model="payment.pmtMode" ng-change="paymentMode()">
										<c:forEach items="${paymentModeEnum}" var="paymentMode">
											<option value="${paymentMode.code}">${paymentMode}</option>
										</c:forEach>
									</select>
								</p>
							</div>

							<div class="itemsDiv col-md-2" id="bankCardTypeDiv">
								<label>Bank Card Types<span class="red">*</span></label>
								<p>
									<select id="bankCardTypeid" class="form-control input-sm"
										ng-model="payment.bankCardType" 
										ng-options="x.id as x.code for x in bankCardTypes"
										ng-change="bankCardTypeSelected()">
									</select>
								</p>
							</div>
							<div class="itemsDiv col-md-2" id="bankName">
								<label>Bank name</label>
								<p>
										<div class="ui-widget">
                                    <input id="bankNames"  ng-model="payment.bankName" ng-disabled="nameBank">
                                     </div>
								</p>
							</div>
							<div class="itemsDiv col-md-2" id="companyName" >
								<label>Company name</label>
								<p>	
										<div class="ui-widget">
                                        <input id="companyNames"  ng-model="payment.companyName" >
                                     </div>
								</p>
 							</div>


							<div class="itemsDiv col-md-2">
								<label><spring:message
										code="reservation.label.deposit.amount"></spring:message><span
									class="red">*</span></label>
								<p>
									<input type="text" id="txtAmount" ng-model="payment.amount"
										class="form-control form-control-inline input-medium"
										disabled="disabled" required />
								</p>
							</div>
							<div class="itemsDiv col-md-2">
								<label>Received From</label>
								<p>
									<input type="text" id="txtRemarks" ng-model="payment.resvFrom"
										class="form-control form-control-inline input-medium" />
								</p>
							</div>
							<div class="itemsDiv col-md-2">
								<label><spring:message code="status.label.remarks"></spring:message></label>
								<p>
									<input type="text" id="txtRemarks" ng-model="payment.remarks"
										class="form-control form-control-inline input-medium" />
								</p>
							</div>
							<div class="itemsDiv payBtn col-md-2">

								<c:choose>
									<c:when test="${payable.canPay}">
										<div ng-if="payment.pmtType!=5">
											<button ng-disabled="submitClick" id="savePaymentBtn"
												type="button" class="btn btn-success" ng-click="save()">
												<i class="fa fa-money" aria-hidden="true"></i> Pay
											</button>
										</div>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${payable.total!=0}">
												<div ng-if="payment.pmtType==5">
													<button ng-disabled="submitClick" id="savePaymentBtn"
														type="button" class="btn btn-success" ng-click="save()">
														<i class="fa fa-money" aria-hidden="true"></i> Pay
													</button>
												</div>
												<label ng-if="payment.pmtType!=5">choose refund
													option</label>
											</c:when>
											<c:otherwise>
												<label>Nothing to pay</label>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</div> --%>







		<input type="hidden" id="corporate" value="${corporate}">

						<div class="md-list-item-text">
							<table class="table table-striped">
								<thead>
									<tr>
										<th class="th_heading"><spring:message
												code="Payment.label.paymentType"></spring:message></th>
										<th class="th_heading"><spring:message
												code="Payment.label.paymentMode"></spring:message></th>
										<th class="th_heading">Source Name</th>

										<th class="th_heading"><spring:message
												code="reservation.label.deposit.amount"></spring:message></th>
										<th class="th_heading">Received From</th>
										<th class="th_heading"><spring:message
												code="status.label.remarks"></spring:message></th>
										<th></th>
									</tr>
								</thead>
								<tbody id="rateDetails">
									<tr class="tr_border" ng-repeat="payment in payments">
										<td><select id="slctPaymentType" class="form-control"
											ng-model="payment.pmtType"
											ng-options="x.pTypeId as x.pmType for x in payType"
											ng-change="amountChange();paymentMode($index)">
										</select></td>
										<td>
										<select id="slctPaymentMode" class="form-control"
											ng-model="payment.pmtMode" ng-change="paymentMode($index)">
												<c:forEach items="${paymentModeEnum}" var="paymentMode">
													<option value="${paymentMode.code}">${paymentMode}</option>
												</c:forEach>
										</select></td>
										<td ng-if="card[$index]"><select id="bankCardTypeid"
											class="form-control" ng-model="payment.bankCardType"
											ng-options="x.id as x.code for x in bankCardTypes"
											ng-change="bankCardTypeSelected()">
										</select></td>
										<td ng-if="bank[$index]"><input class="form-control"
											id="bankNames" ng-model="payment.bankName"
											ng-disabled="nameBank[$index]" placeholder="Bank Name">
										</td>
										<td ng-if="company[$index]"><!-- <input class="form-control"
											id="companyNames" ng-model="payment.companyName"
											placeholder="Company Name"> -->
											<select ng-model="payment.corporateId" class="form-control"
													required>
													<option ng-repeat="trvlCorp in corpList"
														value="{{trvlCorp.id}}">{{trvlCorp.name}}</option>
											</select></td>
										<td><input class="form-control" type="text"
											id="txtAmount" ng-model="payment.amount"
											class="form-control form-control-inline input-medium"
											ng-change="amountChange()" required /></td>
										<td><input class="form-control" type="text"
											id="txtRemarks" ng-model="payment.resvFrom"
											class="form-control form-control-inline input-medium" /></td>
										<td><input class="form-control" type="text"
											id="txtRemarks" ng-model="payment.remarks"
											class="form-control form-control-inline input-medium" /></td>
										<td ng-if="deleteShow[$index]">
											<button class="btn btn-danger" ng-click="remove($index)">
												<i class="fa fa-trash" aria-hidden="true"></i>
											</button>
										</td>
									</tr>
									<tr ng-if="addShow">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td>
											<button class="btn btn-success" ng-click="add()">
												<i class="fa fa-plus" aria-hidden="true"></i>
											</button>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="itemsDiv payBtn col-md-2 pull-right">
								
								<c:choose>
									<c:when test="${payable.canPay}">
										<div ng-if="payment.pmtType!=5">
										<div class="lblAddAmt"><b>Amount to be added:</label><label>{{balance}}</b></div>
											<button ng-disabled="submitClick" id="savePaymentBtn"
												type="button" class="btn btn-success" ng-click="save()">
												<i class="fa fa-money" aria-hidden="true"></i> Pay
											</button>
										</div>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${payable.total!=0.0}">
												<div>
													<button ng-disabled="submitClick" id="savePaymentBtn"
														type="button" class="btn btn-success" ng-click="save()">
														<i class="fa fa-money" aria-hidden="true"></i> Pay
													</button>
												</div>
												<label ng-if="payment.pmtType!=5">choose refund
													option</label>
											</c:when>
											<c:otherwise>
												<label>Nothing to pay</label>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</div>
						</div>















						</md-list-item> </md-list> </md-content>
					</div>
				</div>
			</div>
			<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
				tabindex="-1" id="rateDtlmyModal" class="modal fade">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<div class="modal-header">
							<button aria-hidden="true" data-dismiss="modal" class="close"
								type="button">x</button>
							<h4 class="modal-title">Room Charges And Details</h4>
						</div>
						<div class="modal-body" style="overflow-y: auto;">
							<!-- modal data comes here -->
							<div class="form-horizontal tasi-form">
								<div class="col-sm-12 modalInnerDiv">
									<div class="payInfo">
										<div class="row">
											<div class="col-sm-6 lftDtls">Room Charges</div>
											<div class="col-sm-4 rgtDtls">${payable.roomCharges}</div>
										</div>
										<div class="row">
											<div class="col-sm-6 lftDtls">Other Charges</div>
											<div class="col-sm-4 rgtDtls">${payable.otherCharges}</div>
										</div>
										<div class="row">
											<div class="col-sm-6 lftDtls">Discounts</div>
											<div class="col-sm-4 rgtDtls">${payable.discounts}</div>
										</div>
										<div class="row">
											<div class="col-sm-6 lftDtls">Deposits</div>
											<div class="col-sm-4 rgtDtls">${payable.deposits}</div>
										</div>
										<div class="row">
											<div class="col-sm-6 lftDtls">Taxes</div>
											<div class="col-sm-4 rgtDtls">${payable.tax}</div>
										</div>
										<div class="row">
											<div class="col-sm-6 lftDtls">Service Charges</div>
											<div class="col-sm-4 rgtDtls">${payable.serviceCharge}</div>
										</div>
										<c:if test="${payable.serviceTax ne 0.0}">
											<div class="row">
												<div class="col-sm-6 lftDtls">Service Tax</div>
												<div class="col-sm-4 rgtDtls">${payable.serviceTax}</div>
											</div>
										</c:if>
										<c:if test="${payable.paidIn ne 0.0}">
											<div class="row">
												<div class="col-sm-6 lftDtls">Paid In</div>
												<div class="col-sm-4 rgtDtls">${payable.paidIn}</div>
											</div>
										</c:if>
										<c:if test="${payable.roundAdjust ne 0.0}">
											<div class="row">
												<div class="col-sm-6 lftDtls">Round Adjustment</div>
												<div class="col-sm-4 rgtDtls">${payable.roundAdjust}</div>
											</div>
										</c:if>
										<div class="row total">
											<div class="col-sm-6 lftDtls tot">Total</div>
											<%-- <div class="col-sm-4 rgtDtls">${payable.total}</div> --%>
											
											 <div class="col-sm-4 rgtDtls"><fmt:formatNumber type="number" maxFractionDigits="2" value="${payable.roomCharges+payable.otherCharges+payable.tax+payable.serviceCharge+payable.serviceTax-payable.discounts-payable.deposits}"/></div> 
										</div>
									</div>
								</div>
							</div>
							<!-- modal data comes here -->
						</div>
						<div class="modal-footer">
							<button id="cancelPopUpBtn" type="button"
								class="btn btn-default rbtnClose" ng-click="cancelPopUp()">
								<spring:message code="pms.btn.cancel" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>
<section class="panel">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<!-- 			<button id="btnPrev" class="btn btn-default" type="button"
				onclick="cancel()">Back</button> -->
			<button id="btnNext" class="btn btn-primary" type="button"
				onclick="checkout(${folioBindNo})">
				<spring:message code="checkOut.button.label.checkOut"></spring:message>
			</button>
		</div>
	</div>
</section>
