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
					Discount Details
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
									<!-- <div class="col-sm-1 showdtls">
										<a href="#" ng-click="showRateDtls();">Details</a>
									</div> -->
								</div>
							</div>
							<div id="disc_remarks">
								<div class="form-group">
									<label class="control-label col-md-2">Reason<span class="red">*</span></label>
									  <div class="col-md-6">
										<input type="text" cssClass="validator form-control form-control-inline input-medium refresh_department"
											class="form-control form-control-inline input-medium "
											id="disc_reason" maxlength= "100" name="disc_reason" required ng-model="DiscRemarks">
									   </div>
										<div class="col-md-1 col-xs-11 tick_green">
										 <span
										   id="disc_reason_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
											</div>
										</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-lg-12">
						
						<md-content> <md-list> <md-list-item
							class="md-3-line">
							
		<input type="hidden" id="corporate" value="${corporate}">

						<div class="md-list-item-text">
							<table class="table table-striped">
								<thead>
									<tr>
										<th class="th_heading">Txn Date</th>
										<th class="th_heading">Transaction</th>
										<th class="th_heading">Base Amount</th>
										<th class="th_heading">Discount %</th>
										<th class="th_heading">Discount Amount</th>
										<th></th>
									</tr>
								</thead>
								<tbody id="discountDetails">
									<tr class="tr_border" ng-repeat="discount in discounts">
										
										<td><input class="form-control" type="text"
											id="discountCode" ng-model="discount.txn_date"
											class="form-control form-control-inline input-medium"
											ng-disabled = "true" required /></td>
										<td><input class="form-control" type="text"
											id="discountCode" ng-model="discount.acc_mst_code"
											class="form-control form-control-inline input-medium"
											ng-disabled = "true" required /></td>
										<%-- <td><select id="slctPaymentMode" class="form-control" ng-disabled = "true" 
											ng-model="discount.discountFor" ng-change="paymentMode($index)">
												<c:forEach items="${discountForEnum}" var="discountFor">
													<option value="${discountFor}">${discountFor}</option>
												</c:forEach>
										</select></td> --%>
									
										<td><input class="form-control" type="text"
											id="totalAmount" ng-model="discount.baseAmount"
											class="form-control form-control-inline input-medium"
											ng-disabled="true" required /></td>
										<td>
										
									
													<div class="col-sm-2 ">
														<md-checkbox id="isDiscount" ng-disabled="true"
															ng-checked="discount.isPcDiscount" ng-click = "changeDiscPc(discount)"
															></md-checkbox>
													</div>
													<div class="col-sm-8" >
														<!-- <label>%</label> -->
														<input class="form-control" type="text" ng-disabled="true"
											id="txtRemarks" ng-model="discount.discPC" ng-change="changeDiscAmt(discount)"
											class="form-control form-control-inline input-medium" />
													</div>
													
												
											
										
										</td>
										<td><input class="form-control" type="text" ng-disabled="true"
											id="discAmt" ng-model="discount.discAmount"
											class="form-control form-control-inline input-medium" /></td>
										
										 <td> 
											<button class="btn btn-danger" ng-click="DeleteDisCount($index)">
												<i class="fa fa-trash" aria-hidden="true"></i>
											</button>
										</td> 
									</tr>
									<!-- <tr ng-if="addShow">
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
									</tr> -->
								</tbody>
							</table>
							<div class="itemsDiv payBtn col-md-2 pull-right">
		
											<button  id="savePaymentBtn" ng-disabled = "discountSubmit"
												type="button" class="btn btn-success" ng-click="saveDiscount()">
												<i class="fa fa-money" aria-hidden="true"></i> Submit
											</button>
										
									
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
											<div class="col-sm-4 rgtDtls">${payable.roomCharges+payable.otherCharges+payable.tax+payable.serviceCharge+payable.serviceTax-payable.discounts-payable.deposits}</div>
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
