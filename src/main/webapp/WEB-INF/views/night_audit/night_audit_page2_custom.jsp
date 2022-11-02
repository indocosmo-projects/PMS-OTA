<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<header class="panel-heading module_caption">
		<h1>
			<spring:message code="nightAudit.label.changeHotelDate" />
		</h1>
	</header>
	<div class="panel-body changeDateDiv">
		<div class="col-md-12">
			<div class="col-md-6">
				<div class="panel panel-primary oldHDatePanel">
					<div class="panel-heading">
						<spring:message code="nightAudit.label.currentHotelDate" />
					</div>
					<div class="panel-body">
						<input type="hidden" value="${hotelDate}" id="currHdate" />
						<md-datepicker ng-model="myDate" md-placeholder="Enter date"
							disabled></md-datepicker>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<spring:message code="nightAudit.label.newHotelDate" />
					</div>
					<div class="panel-body">
						<md-datepicker ng-model="newHotDate" md-placeholder="Enter date"></md-datepicker>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12">
			<md-checkbox ng-model="accptDate" class="chkAccpetHotelDate"
				aria-label="Checkbox 1">
			<spring:message code="nightAudit.label.confimHotelDate" /></md-checkbox>
		</div>
	</div>
</section>
<section class="panel">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<button class="btn btn-success" ng-click="next()">
				<spring:message code="pms.btn.next"></spring:message>
			</button>
		</div>
	</div>
</section>