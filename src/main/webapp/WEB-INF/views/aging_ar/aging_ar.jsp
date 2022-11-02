<jsp:directive.include file="../common/includes/page_directives.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/jquery.js' />"></script>
<c:import url="../common/includes/master_includes.jsp" />
<link href="../resources/common/css/style.css" rel="stylesheet">
<link href="<c:url value="/resources/pms/css/aging_ar.css" />"
	rel="stylesheet" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />">
<script type="text/javascript"
	src="<c:url value="/resources/pms/js/angularctrl/aging_ar.js" />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
</head>
<body class="full-width" ng-app="agingApp" id="settle"
	ng-controller="agingARController" id="setup" ng-init="getAgingARList()">
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
												<h1>AR AGING</h1>
											</div>
											<div class="pymt_dt_div">
												<div class="col-md-2" id="custName">Customer name</div>
												<div>

													<input type="text" class="form-control" name="corporate"
														id="searchBox" ng-model="corporate" /> <input
														type="button" value="Print" id="printBtn"
														ng-click="reportPrint()" />
												</div>


												<!--    <div class="col-sm-2"> 
								
								<input type="button" value="PRINT DETAILS" ng-click="print()"/>
							
								</div>  -->

											</div>
											<div class="adv-table editable-table">
												<table ng-init="agingARList.total ={}"
													class="table table-striped table-hover table-bordered">
													<thead>
														<tr>
															<td>#</td>
															<td class="centerAlign">Customer Name</td>
															<td class="centerAlign">Outstanding Total</td>
															<td class="centerAlign" id="borderColor" colspan="3">0-30
																Days</td>
															<td></td>
															<td class="centerAlign" id="borderColor" colspan="3">31-60
																Days</td>
															<td></td>
															<td class="centerAlign" id="borderColor" colspan="3">61-90
																Days</td>
															<td></td>
															<td class="centerAlign" id="borderColor" colspan="3">
																>90 Days</td>
															<td></td>
														</tr>
													</thead>
													<tr ng-repeat="agingAR in agingARList | filter:corporate">
														<td>{{$index+1}}</td>
														<td>{{agingAR.corporate_name}}</td>
														<td 
															ng-init="agingARList.total.balance = agingARList.total.balance + agingAR.balance"
															class="alignRight"><div>{{agingAR.balance
																| number:2}}</div></td>

														<td ng-value="agingAR.balance30" colspan="3"
															ng-init="agingARList.total.balance30 = agingARList.total.balance30 + agingAR.balance30"
															class="alignRight" id="borderWhite"><div
																ng-if="agingAR.balance30 != 0">{{agingAR.balance30
																| number:2}}</div></td>
														<td class="btnWidth">
															<div ng-if="agingAR.balance30 != 0" class="btn_dtl"
																ng-click="getInvoiceDetails($index,1)"
																data-toggle="modal" data-target="#modalInvoiceDtls">
																<i class="fa fa-ellipsis-h" aria-hidden="true"></i>
															</div>
														</td>
														<td ng-value="agingAR.balance60" colspan="3"
															ng-init="agingARList.total.balance60 = agingARList.total.balance60 + agingAR.balance60"
															class="alignRight" id="borderWhite"><div
																ng-if="agingAR.balance60 != 0">{{agingAR.balance60
																| number:2}}</div></td>
														<td class="btnWidth">
															<div ng-if="agingAR.balance60 != 0" class="btn_dtl"
																ng-click="getInvoiceDetails($index,2)"
																data-toggle="modal" data-target="#modalInvoiceDtls">
																<i class="fa fa-ellipsis-h" aria-hidden="true"></i>
															</div>
														</td>
														<td ng-value="agingAR.balance90" colspan="3"
															ng-init="agingARList.total.balance90 = agingARList.total.balance90 + agingAR.balance90"
															class="alignRight" id="borderWhite"><div
																ng-if="agingAR.balance90 != 0">{{agingAR.balance90
																| number:2}}</div></td>
														<td class="btnWidth">
															<div ng-if="agingAR.balance90" class="btn_dtl"
																ng-click="getInvoiceDetails($index,3)"
																data-toggle="modal" data-target="#modalInvoiceDtls">
																<i class="fa fa-ellipsis-h" aria-hidden="true"></i>
															</div>
														</td>
														<td ng-value="agingAR.balance120" colspan="3"
															ng-init="agingARList.total.balance120 = agingARList.total.balance120 + agingAR.balance120"
															class="alignRight" id="borderWhite"><div
																ng-if="agingAR.balance120 != 0">{{agingAR.balance120
																| number:2}}</div></td>
														<td class="btnWidth">
															<div ng-if="agingAR.balance120 != 0" class="btn_dtl"
																ng-click="getInvoiceDetails($index,4)"
																data-toggle="modal" data-target="#modalInvoiceDtls">
																<i class="fa fa-ellipsis-h" aria-hidden="true"></i>
															</div>
														</td>

													</tr>
													<tr class="amntBg">
														<td></td>
														<td id="amount" class="alignRight" >Total Outstanding</td>
														<td id="amount" class="alignRight">{{agingARList.total.balance
															| number:2}}</td>
														<td colspan="3" id="amount" class="alignRight">{{agingARList.total.balance30
															| number:2}}</td>
														<td></td>
														<td colspan="3" id="amount" class="alignRight">{{agingARList.total.balance60
															| number:2}}</td>
														<td></td>
														<td colspan="3" id="amount" class="alignRight">{{agingARList.total.balance90
															| number:2}}</td>
														<td></td>
														<td colspan="3" id="amount" class="alignRight">{{agingARList.total.balance120
															| number:2}}</td>
														<td></td>
													</tr>
												</table>
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
	<!-- Invoice Details Modal -->


	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
		tabindex="-1" id="modalInvoiceDtls" class="modal fade">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">Outstanding Details</h4>
				</div>
				<div class="modal-body" id="modalBox">
					<div class="col-md-12">
						<div>
							<label class="custName">Customer Name: {{corporateName}}</label>
						</div>
						<div class="col-md-4"></div>
					</div>
					<!-- modal data comes here -->
					<div class="table_main_div"></div>
					<div class="table table-striped table-div">
						<div class=" table-div-thead">
							<div class=" table-div-tr">
								<div class=" table-div-th centerAlign col-sm-3">Invoice
									Date</div>
								<div class=" table-div-th centerAlign col-sm-3">Invoice No</div>
								<div class=" table-div-th centerAlign col-sm-3">Age</div>
								<div class=" table-div-th alignRight col-sm-3">Outstanding</div>
							</div>
						</div>
						<div class=" table-div-tbody">
							<div class=" table-div-tr" ng-repeat="x in invoiceDetails">

								<div class=" table-div-td centerAlign col-sm-3">{{x.invoice_date}}</div>
								<div class=" table-div-td centerAlign col-sm-3">{{x.invoice_no}}</div>
								<div class=" table-div-td centerAlign col-sm-3">{{x.days}}</div>
								<div class=" table-div-td alignRight col-sm-3"
									ng-init="invoiceDetails.total.amount = invoiceDetails.total.amount + x.amount">
									{{x.amount | number:2}}</div>
							</div>

						</div>
						<div class="table_footer">
							<div class=" table-div-tr amntBg">
								<div class=" table-div-td col-sm-3">&nbsp;</div>
								<div class=" table-div-td col-sm-3">&nbsp;</div>
								<div class=" table-div-td centerAlign col-sm-3" id="amount">Total</div>
								<div class=" table-div-td alignRight col-sm-3" id="amount">{{invoiceDetails.total.amount
									| number:2}}</div>
							</div>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

				</div>
			</div>
		</div>
	</div>




</body>
</html>