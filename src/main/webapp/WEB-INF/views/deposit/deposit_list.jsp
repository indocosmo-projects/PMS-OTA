

<jsp:directive.include file="../common/includes/page_directives.jsp" />


<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="myModal" class="modal fade">

	<div id="content">

		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>

					<h4 class="modal-title">Deposit List</h4>
				</div>

				<div class="panel-body scroll-mxm">
					<input type="hidden" id="posthidden" value="1">
					<div class="cmn_tbl">
						<table id="tblTxnLst" class="col-md-12">
							<thead>
								<tr>
									<td
										class="col-md-2 pymnt-border d_grid_sortable_col d_grid_sortable_col_none"
										ng-click="sortType = 'txn_date'; sortReverse = !sortReverse">

										<!--   
									ng-show="sortType == 'txn_date' && !sortReverse"  class="d_grid_sortable_col d_grid_sortable_col_none"
									
									ng-show="sortType == 'txn_date' && sortReverse"  class="d_grid_sortable_col d_grid_sortable_col_none"
									 --> Posted On
									</td>
									<td
										class="col-md-2 pymnt-border d_grid_sortable_col d_grid_sortable_col_none"
										ng-click="sortType = 'txn_time'; sortReverse = !sortReverse">Txn
										Time</td>
									<td
										class="col-md-2 pymnt-border d_grid_sortable_col d_grid_sortable_col_none"
										ng-click="sortType = 'amount'; sortReverse = !sortReverse">Amount</td>
									<td class="col-md-6 pymnt-border">Reference</td>
									<td class="col-md-6 pymnt-border">Received From</td>
									<td class="col-md-6 pymnt-border">Print</td>
								</tr>
							</thead>

							<tbody>
								<tr
									data-ng-repeat="entry in depositList | orderBy:sortType:sortReverse | filter:searchFish"" >
									<td class='control-label pymnt-border-rit'>{{
										entry.txn_date | date : dateFormat }}</td>
									<td class='control-label pymnt-border-rit'>{{
										timeFormat(entry.txn_time) | date: "HH:mm"}}</td>
									<td class='control-label pymnt-border-rit amount-align'>{{
										entry.amount}}</td>
									<td class='control-label pymnt-border-rit '>{{
										entry.remarks}}</td>
									<td class='control-label pymnt-border-rit '>{{
										entry.receivedForm}}</td>
									<td class='control-label pymnt-border-rit '>
										<button type="button" class=" resv-btn recp-btn-printout"
											ng-click="print($index);"></button>
									</td>
								</tr>

							</tbody>

						</table>
					</div>
					<div class="col-sm-6">
						<div class="row">
							Total no of transactions : <span style="color: red"> {{
								depositList.length}} </span>
						</div>
						<div class=""></div>
					</div>
				</div>
				<div class="modal-footer">
					<button id="back_btn" data-dismiss="modal" class="btn btn-default"
						type="button">
						<spring:message code="pms.btn.back"></spring:message>
					</button>
				</div>
			</div>
		</div>

	</div>
</div>