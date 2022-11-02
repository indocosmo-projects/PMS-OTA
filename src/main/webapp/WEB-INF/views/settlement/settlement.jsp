<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@page import="com.indocosmo.pms.enumerator.SettlementMode"%>
<c:set var="settlementMode" value="<%=SettlementMode.values()%>"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<link href="../resources/common/css/style.css" rel="stylesheet">
<link href="<c:url value="/resources/pms/css/settlement.css" />"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />">
<script type="text/javascript"
	src="<c:url value="/resources/pms/js/angularctrl/settlement.js" />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
</head>
<body class="full-width" ng-app="settlement"
	ng-controller="settlementController" id="settlement">
	<input type="hidden" id="hotelDate" value="${hotelDate}" />
	<section id="container" class="">
		<c:import url="../menu/topMenu.jsp" />
		<section id="main-content">
			<div class="">
				<section class="wrapper">
					<!-- page start-->
					<div class="row">
						<div class="col-lg-12">
							<section class="panel padding_bottom_zrw">
								<div class="panel-body">
									<div class="col-md-12">
										<div class="task-progress module_head">
											<div class="col-sm-12">
												<h1>CREDITORS PAYMENT</h1>
											</div>
											<div class="pymt_dt_div">
												<label class="pymt_dt_lbl">Start Date</label>
												<div class="col-sm-2" id="dateWidth">
													<md-datepicker class="form-control" ng-model="startDate"
														ng-change="getSettlementList(startDate,endDate,searchCorp)"></md-datepicker>
												</div>
												<label class="pymt_dt_lbl">End Date</label>
												<div class="col-sm-2" id="dateWidth">
													<md-datepicker class="form-control" ng-model="endDate"
														ng-change="getSettlementList(startDate,endDate,searchCorp)"></md-datepicker>
												</div>
												<label class="pymt_dt_lbl">Corporate</label>
												<div class="col-sm-2" id="dateWidth">
													<select ng-model="searchCorp" class="form-control"
														ng-change="getSettlementList(startDate,endDate,searchCorp)"
														required>
														<option ng-repeat="corp in corpList" value="{{corp.id}}">{{corp.name}}</option>
													</select>
												</div>
												<button class="btn btn-md btn-primary pull-right"
													data-toggle="modal" data-target="#newPaymentModal"
													ng-click="newPayment()">New Payment</button>
											</div>
											<div class="adv-table editable-table">
												<table
													class="table table-striped table-hover table-bordered">
													<thead>
														<tr>
															<th style="width: 30px;">Voucher No</th>
															<th style="width: 95px;">Date</th>
															<th style="width: 250px;">Customer</th>
															<th>Mode</th>
															<th>Ref No</th>
															<th style="width: 95px;">Ref Date</th>
															<th style="width: 170px;">Ref Name</th>
															<th style="width: 85px;">Amount</th>
															<th style="width: 50px;">Alloc Amount</th>
															<th style="width: 145px;"></th>
														</tr>
													</thead>
													<tbody>
														<tr ng-repeat="set in settlementList"
															ng-click="editPayment($index, $event)">
															<td>{{set.voucher_no}}</td>
															<td>{{set.payment_date}}</td>
															<td>{{set.corporate_name}}</td>
															<td>{{set.payment_mode}}</td>
															<td>{{set.reference_no}}</td>
															<td>{{set.reference_date}}</td>
															<td>{{set.reference_name}}</td>
															<td class="alignRight">{{set.payment_amount}}</td>
															<td class="alignRight">{{set.allocate_amount}}</td>
															<td class="td_alloc"><div class="td_alloc col-sm-7">
																	<button class="btn btn-sm btn-info td_alloc"
																		data-toggle="modal" data-target="#allocationModal"
																		ng-click="allocateAmount($index)">Allocate</button>
																</div>
																<div class="col-sm-1 td_alloc">
																	<button class="btn btn-sm btn-info td_alloc"
																		ng-click="printReceipt(set.voucher_no)">
																		<i class="fa fa-print" aria-hidden="true"></i>
																	</button>
																</div></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<div class="row footer_pagination_main_div">
											<div class="col-lg-3">
												<div class="dataTables_info col-lg-8 footer_pagination">Showing
													{{totalItems}} of {{bigTotalItems}} Items</div>
												<div class="col-lg-4">
													<select ng-model="pagination.limit"
														class="selectpicker form-control page_limit"
														ng-change="changeLimit(pagination.limit)">
														<option value="5">5</option>
														<option value="10">10</option>
														<option value="20">20</option>
														<option value="50">50</option>
														<option value="100">100</option>
													</select>
												</div>
											</div>
											<div class="pagination_div col-md-7 pull-right">
												<ul uib-pagination total-items="bigTotalItems"
													ng-model="bigCurrentPage" max-size="maxSize"
													items-per-page="itemsPerPage" ng-change="pageChanged()"
													class="pagination-sm" boundary-links="true" rotate="false"></ul>
											</div>
										</div>
									</div>
								</div>
							</section>

						</div>
					</div>
				</section>
			</div>
		</section>
	</section>
	<!-- New Payment Modal -->
	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" class="modal fade" id="newPaymentModal">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">New Payment</h4>
				</div>
				<div class="modal-body" id="modalbody">
					<input type="hidden" ng-model="id">
					<!-- <div class="col-sm-8 input_div">
						<label id="" class="col-sm-4">Voucher No: </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" ng-model="voucherNo"
							 disabled>
						</div>
					</div> -->
					<div class="col-sm-8 input_div">
						<label id="" class="col-sm-4">Date<span class="red">*</span>
						</label>
						<div class="col-sm-5" id="dateWidth">
							<md-datepicker class="form-control" ng-model="date" ng-change=""></md-datepicker>
						</div>
					</div>
					<div class="col-sm-10 input_div">
						<label id="" class="col-sm-3">Corporate<span class="red">*</span>
						</label>
						<div class="col-sm-8 corp_div">
							<select ng-model="corporate" class="form-control"
								ng-change="changeCorporate(corporate)" required>
								<option ng-repeat="corp in corpList" value="{{corp.id}}"
									ng-disabled="corp.id==0">{{corp.name}}</option>
							</select> <label>Outstanding: {{outstanding}}</label>
						</div>
					</div>
					<div class="col-sm-8 input_div">
						<label id="" class="col-sm-4">Amount<span class="red">*</span>
						</label>
						<div class="col-sm-8">
							<input type="text" class="form-control alignRight"
								ng-model="payment_amount">
						</div>
					</div>
					<div class="col-sm-10 input_div">
						<label id="" class="col-sm-3">Mode<span class="red">*</span>
						</label>
						<div class="col-sm-8 corp_div">
							<select ng-change="changePaymentMode($index)"
								class="form-control" ng-model="paymentMode">
								<c:forEach items="${settlementMode}" var="settlementMode">
									<option value="${settlementMode.code}">${settlementMode.getSettlementMode()}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-8 input_div">
						<label id="" class="col-sm-4">Ref No </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" ng-model="refNo">
						</div>
					</div>
					<div class="col-sm-8 input_div">
						<label id="" class="col-sm-4">Ref Date </label>
						<div class="col-sm-5" id="dateWidth">
							<md-datepicker class="form-control" ng-model="refDate"></md-datepicker>
						</div>
					</div>
					<div class="col-sm-10 input_div">
						<label id="" class="col-sm-3">Ref Name </label>
						<div class="col-sm-8 corp_div">
							<input type="text" class="form-control" ng-model="refName">
						</div>
					</div>
					<div class="col-sm-12 input_div">
						<label id="" class="col-sm-2">Remarks </label>
						<div class="col-sm-9 rmrks_div">
							<input type="text" class="form-control" ng-model="remarks">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-md btn-warning pull-left"
						ng-click="resetPayment()">Reset</button>
					<button class="btn btn-md btn-primary pull-left"
						ng-click="saveNew()">Save</button>
					<button class="btn btn-md pull-right" data-dismiss="modal"
						class="close">Back</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Invoice Allocation Modal -->

	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" class="modal fade" id="allocationModal">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Invoice Allocation</h4>
				</div>
				<div class="modal-body" id="modalbodyInvoiceAllocation">
					<input type="hidden" ng-model="headerId">
					<div class="col-sm-8">
						<label id="custName" class="col-sm-5">Customer Name: </label> <label>{{customerName}}</label>
					</div>
					<div class="col-sm-8">
						<label id="custName" class="col-sm-5">Date: </label> <label>{{allocDate}}</label>
					</div>
					<div class="col-sm-8">
						<label id="custName" class="col-sm-5">Voucher: </label> <label>{{voucher}}</label>
					</div>
					<div class="col-sm-8">
						<label id="custName" class="col-sm-5">Amount: </label> <label>{{paymentAmount}}</label>
					</div>
					<div ng-show="disableMsg">
						<label>Settlement amount can not be zero</label>
					</div>
					<div>
						<table class="table table-striped table-hover table-bordered">
							<thead>
								<tr>
									<th style="width: 49px;" class="align_chk"><md-checkbox
											ng-model="chk_all" class="chk_center" ng-change="selectAll()">
										</md-checkbox></th>
									<th style="width: 130px;">Invoice Date</th>
									<th style="width: 105px;">Invoice No</th>
									<th style="width: 49px;">Days</th>
									<th>Invoice Amt</th>
									<th style="width: 130px;">Paid Amt</th>
									<th style="width: 108px;">Balance Amt</th>
									<th style="width: 180px;">Settlement</th>
								</tr>
							</thead>
						</table>
						<div class="tbody_scroll">
							<table class="table table-striped table-hover table-bordered">
								<tbody>
									<tr ng-repeat="invDtls in invoiceList">
										<td class="align_chk" style="width: 49px;"><md-checkbox
												class="chk_center" ng-model="invDtls.chk_inv"
												ng-change="selectOneInv($index)"> </md-checkbox></td>
										<td style="width: 130px;">{{invDtls.invoice_date}}</td>
										<td style="width: 105px;">{{invDtls.invoice_no}}</td>
										<td style="width: 49px;">{{invDtls.days}}</td>
										<td class="alignRight" style="width: 118px;">{{invDtls.invoiceAmount}}</td>
										<td class="alignRight" style="width: 130px;">{{invDtls.paidAmount}}</td>
										<td class="alignRight" style="width: 108px;">{{invDtls.balance}}</td>
										<td><input type="text" ng-model="invDtls.settle_amount"
											class="form-control alignRight"
											ng-keyup="getTotalPayment($index, $event)"
											ng-disabled="invDtls.disableNewPayment"></td>
									</tr>
								</tbody>
							</table>
						</div>
						<table class="table table-striped table-hover table-bordered">
							<tbody>
								<tr>
									<td id="borderLine"></td>
									<td id="borderLine"></td>
									<td id="borderLine"></td>
									<td id="borderLine" class="alignRight" style="width: 285px;">TOTAL:</td>
									<td id="borderLine" class="alignRight" style="width: 118px;">{{totalInvAmt}}</td>
									<td id="borderLine" class="alignRight" style="width: 130px;">{{totalPaid}}</td>
									<td id="borderLine" class="alignRight">{{totalPayable}}</td>
									<td id="borderLine" class="alignRight" style="width: 180px;">{{totalPayment}}</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<div class="modal-footer">
					<div class="pull-right">
						<div class="col-sm-6">
							<button class="btn btn-md btn-primary"
								ng-click="saveAllocation()">Allocate</button>
						</div>
						<div class="col-sm-5">
							<button class="btn btn-md btn-primary pull-right"
								data-dismiss="modal" ng-click="">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Receipt Print Modal -->

	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" class="modal fade" id="receiptPrintModal">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Receipt Print</h4>
				</div>
				<div class="modal-body" id=""></div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>
</body>
</html>