<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<div class="panel-body">
		<div class="dtls">

			<form name="serviceTaxForm" novalidate>
				<div class="col-md-12 divRow">
					<div class="col-md-6">
						<div class="col-sm-4">
							<label>Service Tax %</label>
						</div>
						<div class="col-sm-8">
							<input type="text" maxlength="5"
								class="hdrInp form-control form-control-inline input-medium"
								ng-model="srvTax.serviceTax" allow-only-numbers />
						</div>
					</div>
					<div class="col-md-6">
						<div class="col-sm-4">
							<label>Abatement %</label>
						</div>
						<div class="col-sm-8">
							<input type="text" maxlength="5"
								class="hdrInp form-control form-control-inline input-medium"
								ng-model="srvTax.abatement" allow-only-numbers />
						</div>
					</div>
				</div>

				<md-toolbar class="md-theme-light">
				<h2 class="md-toolbar-tools">
					<span>Cess</span>
				</h2>
				</md-toolbar>
				<div class="divRow">
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 1 Name</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30" id="cessName_1"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="srvTax.cess1Name" />
								</div>
							</div>
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 1 %</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="5" id="cessPc_1"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="srvTax.cess1_pc" allow-only-numbers />
								</div>
							</div>
							<div class="col-md-1">
								<div class="row clrBtn">
									<a ng-click="removeCess(1)"><i class="fa fa-trash"
										aria-hidden="true"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 2 Name</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30" id="cessName_2"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="srvTax.cess2Name" />
								</div>
							</div>
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 2 %</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="5" id="cessPc_2"
										class="form-control form-control-inline input-medium"
										ng-model="srvTax.cess2_pc" allow-only-numbers />
								</div>
							</div>
							<div class="col-md-1">
								<div class="row clrBtn">
									<a ng-click="removeCess(2)"><i class="fa fa-trash"
										aria-hidden="true"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 3 Name</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30" id="cessName_3"
										class="form-control form-control-inline input-medium"
										ng-model="srvTax.cess3Name" />
								</div>
							</div>
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 3 %</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="5" id="cessPc_3"
										class="form-control form-control-inline input-medium"
										ng-model="srvTax.cess3_pc" allow-only-numbers />
								</div>
							</div>
							<div class="col-md-1">
								<div class="row clrBtn">
									<a ng-click="removeCess(3)"><i class="fa fa-trash"
										aria-hidden="true"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 4 Name</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30" id="cessName_4"
										class="form-control form-control-inline input-medium"
										ng-model="srvTax.cess4Name" />
								</div>
							</div>
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 4 %</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="5" id="cessPc_4"
										class="form-control form-control-inline input-medium"
										ng-model="srvTax.cess4_pc" allow-only-numbers />
								</div>
							</div>
							<div class="col-md-1">
								<div class="row clrBtn">
									<a ng-click="removeCess(4)"><i class="fa fa-trash"
										aria-hidden="true"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="row">
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 5 Name</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="30" id="cessName_5"
										class="ftrInp form-control form-control-inline input-medium"
										ng-model="srvTax.cess5Name" />
								</div>
							</div>
							<div class="col-md-5">
								<div class="col-sm-4">
									<label>Cess 5 %</label>
								</div>
								<div class="col-sm-8">
									<input type="text" maxlength="5" id="cessPc_5"
										class="form-control form-control-inline input-medium"
										ng-model="srvTax.cess5_pc" allow-only-numbers />
								</div>
							</div>
							<div class="col-md-1">
								<div class="row clrBtn">
									<a ng-click="removeCess(5)"><i class="fa fa-trash"
										aria-hidden="true"></i></a>
								</div>
							</div>
						</div>
					</div>


				</div>
			</form>
		</div>
	</div>
</section>
<section class="panel">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<c:if test="${cp_isCanEdit}">
				<button class="btn btn-success saveInvBtn" ng-click="save()">
					<spring:message code="pms.btn.save"></spring:message>
				</button>
			</c:if>
			<button class="btn btn-default" ng-click="cancel()">
				<spring:message code="pms.btn.cancel"></spring:message>
			</button>
		</div>
	</div>
</section>