<jsp:directive.include file="../common/includes/page_directives.jsp" />
<input type="hidden" id="currency" value="${currency}" />
<section class="panel">
	<div class="panel-body" id="postingDisplayDiv">
		<md-toolbar class="md-theme-light">
		<h2 class="md-toolbar-tools">
			<span>Postings</span>
		</h2>
		</md-toolbar>

		<md-content class="postContents"> <md-list
			ng-repeat="folioNos in outputFolio"> <md-toolbar
			layout="row" class="md-hue-3">
		<div class="md-toolbar-tools subToolBar">
			<span>Room No: {{folioNos.roomName}}</span>
		</div>
		</md-toolbar> <md-list-item class="md-3-line" ng-repeat="txnl in  txnList"
			ng-if='txnl.folioNo==folioNos.folioNo'>
		<div class="md-list-item-text">
			<div class="itemsDiv col-md-2">
				<label>Txn Date</label>
				<p>{{txnl.txnDate | date:'${dateFormat}'}}</p>
			</div>
			<div class="itemsDiv col-md-2">
				<label>Txn Type</label>
				<p>{{txnl.accCode}}</p>
			</div>
			<div class="itemsDiv col-md-2">
				<label>Base Amount</label>
				<p>{{txnl.baseAmount}}</p>
			</div>
			<div class="itemsDiv col-md-2">
				<label>Tax</label>
				<p>{{txnl.tax}} {{txnl.incTax === 0 ?"(Not Inc)" : "(Inc)"}}</p>
			</div>
			<div class="itemsDiv col-md-2">
				<label>Service Charge</label>
				<p>{{txnl.serviceCharge}}</p>
			</div>
			<div class="itemsDiv col-md-2">
				<label>Nett Amount</label>
				<p>{{txnl.nettAmount< 0?-txnl.nettAmount:txnl.nettAmount}}</p>
			</div>
		</div>
		<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-list> </md-content>
	</div>
</section>
<section class="panel">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<button class="btn btn-success" ng-click="next()">
				<spring:message code="reservation.label.save"></spring:message>
			</button>
		</div>
	</div>
</section>