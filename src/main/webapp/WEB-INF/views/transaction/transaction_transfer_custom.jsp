<jsp:directive.include file="../common/includes/page_directives.jsp" />

<section class="panel padding_bottom_zrw">
	<header class="panel-heading module_caption">
		<h1>
			<spring:message code="Payment.label.roomDetails" />
		</h1>
		<!-- <span class="tools pull-right"> <a class="fa fa-chevron-down"
			href="javascript:;"></a>
		</span> -->
	</header>
	<div class="panel-body paymentDtlsDiv">
		<div class="form-group" ng-controller="transferController"
			id="searchDiv">
			<label class="control-label col-sm-2 label_div" ng-if="sourceSelect"><spring:message
					code="transaction.label.sourceRoom"></spring:message><span
				class="red">*</span></label>

			<div class="col-sm-3 select_div" ng-if="sourceSelect">
				<select class="form-control" ng-model="selectedFolio"
					ng-options="obj.folioNO as obj.roomName for obj in datas"
					ng-change="search(selectedFolio)">

				</select>
			</div>

		</div>
		<div ng-controller="transferDataTable as showCase" class="form-group"
			ng-cloak>
			<div id="dataTableDiv">
				<div class="cmn_tbl">
					<table id="entry-grid" datatable="" dt-options="showCase.dtOptions"
						dt-instance="showCase.dtInstance" dt-columns="showCase.dtColumns"
						style="width: 100%;" class="angDataTable table table-hover"></table>
				</div>
			</div>
			<div class="form-group transaction_btm_buttn_div" id="destinationDiv">
				<label class="control-label col-sm-2 label_div"><spring:message
						code="transaction.label.destinationRoom"></spring:message><span
					class="red">*</span></label>
				<div class="col-sm-3 select_div">

					<select class="form-control" ng-model="selectedDataD"
						ng-options="obj as obj.roomName for obj in destDatas">
					</select>
				</div>
				<div class="col-sm-2 col-xs-11">
					<button id="transferBtn" type="button" class="btn btn-success"
						ng-click="transfer()">
						Transfer <i class="fa fa-exchange" aria-hidden="true"></i>
					</button>
				</div>

			</div>
		</div>

	</div>

</section>
<section class="panel padding_bottom_zrw">
	<div class="panel-body"></div>
</section>