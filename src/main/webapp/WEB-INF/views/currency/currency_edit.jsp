<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Currency" scope="request" />
<c:set var="formId" value="currency" scope="request" />
<c:set var="formName" value="currency" scope="request" />
<c:set var="formAction" value="../currency/save" scope="request" />
<c:set var="formCommandName" value="currency" scope="request" />
<c:set var="moduleName" value="Currency" scope="request" />
<c:set var="cp_isCanView" scope="request"
	value="${(curPagePerObj.isCanView() && curPagePerObj.isIs_view_applicable())?true:false}" />
<c:set var="cp_isCanAdd" scope="request"
	value="${(curPagePerObj.isCanAdd() && curPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="cp_isCanEdit" scope="request"
	value="${(curPagePerObj.isCanEdit() && curPagePerObj.isIs_edit_applicable())?true:false}" />
<c:set var="cp_isCanDelete" scope="request"
	value="${(curPagePerObj.isCanDelete() && curPagePerObj.isIs_add_applicable())?true:false}" />
<c:set var="cp_isCanExecute" scope="request"
	value="${(curPagePerObj.isCanExecute() && curPagePerObj.isIs_execute_applicable())?true:false}" />
<c:set var="cp_isCanExecute" scope="request"
	value="${(curPagePerObj.isCanExport() && curPagePerObj.isIs_export_applicable())?true:false}" />

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/currency_edit.js' />"></script>
<link href="../resources/common/css/style.css" rel="stylesheet" />
<div class="modal-dialog">
	<div class="modal-content">
		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="currencyId" />
			<form:hidden path="dateFormat" id="dateFormat" />
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">&times;</button>
				<c:choose>
					<c:when test="${currency.id==0}">
						<h4 class="modal-title">Add ${moduleName}</h4>
					</c:when>
					<c:otherwise>
						<h4 class="modal-title">Edit ${moduleName}</h4>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="modal-body modal_body_tabs">
				<header class="panel-heading ">
					<ul class="nav nav-tabs">
						<li class="active"><a aria-expanded="true" data-toggle="tab"
							href="#general">General</a></li>
						<li class=""><a aria-expanded="false" data-toggle="tab"
							href="#more">Details</a></li>
					</ul>
				</header>
				<div class="panel-body">
					<div class="tab-content">
						<div id="general" class="tab-pane active">
							<div class="form-horizontal tasi-form">
								<!--  tab 1 content -->

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.code"></spring:message><span class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="form-control form-control-inline input-medium code validator refresh_currency"
											maxlength="15" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtcode_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="txtcode_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.name"></spring:message><span class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="name" id="txtname"
											cssClass="validator form-control form-control-inline input-medium refresh_currency"
											maxlength="50" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtname_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="txtname_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.description"></spring:message></label>
									<div class="col-md-6 col-xs-11">
										<form:textarea path="description" maxlength="65"
											cssClass=" form-control form-control-inline input-medium refresh_currency"
											id="description" />
									</div>
								</div>
							</div>
						</div>

						<div id="more" class="tab-pane">
							<div class="form-horizontal tasi-form">
								<!-- tab 2  content-->

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="currency.label.symbol"></spring:message> </label>
									<div class="col-md-6 col-xs-11">
										<form:input path="symbol"
											cssClass=" form-control form-control-inline input-medium refresh_currency refresh_currency"
											name="symbol" id="symbol" maxlength="3" />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="currency.label.fraction"></spring:message></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="fractionName"
											cssClass=" form-control form-control-inline input-medium refresh_currency refresh_currency"
											id="fraction" maxlength="15" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="currency.label.fractionSymbol">
										</spring:message> </label>
									<div class="col-md-6 col-xs-11">
										<form:input path="fractionSymbol"
											cssClass=" form-control form-control-inline input-medium refresh_currency"
											id="frac_symb" maxlength="3" />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="currency.label.decimalPlaces"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="decimalPlaces" id="dec_plc"
											cssClass="validator form-control form-control-inline input-medium pms_text_numbers refresh_currency"
											maxlength="2" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="dec_plc_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="dec_plc_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="currency.label.exchangeRate"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="exchangeRate" id="exch_rate"
											cssClass="validator form-control form-control-inline input-medium refresh_currency pms_text_numbers"
											maxlength="8" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="exch_rate_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="exch_rate_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="currency.label.updatedDate"></spring:message><span
										class="red">*</span> </label>
									<div class="col-md-6 col-xs-11">
										<form:input path="updatedDate" id="upd_date"
											cssClass="validator form-control form-control-inline input-medium date-picker refresh_currency" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="upd_date_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="upd_date_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="save_btn" class="btn btn-success" type="button"
					onclick="save()">
					<spring:message code="pms.btn.submit"></spring:message>
				</button>
				<input id="refresh_btn" class="btn btn-warning" type="button"
					onclick="refreshData()"
					value="<spring:message code="pms.btn.reset"></spring:message>" />


				<c:if test="${cp_isCanDelete}">
					<button id="delete_btn" class="btn btn-danger" type="button"
						onclick="deleteData()">
						<i class="fa fa-trash-o "></i>
						<spring:message code="pms.btn.delete"></spring:message>
					</button>
				</c:if>


				<button id="back_btn" data-dismiss="modal" class="btn btn-default"
					type="button">
					<spring:message code="pms.btn.back"></spring:message>
				</button>
			</div>
		</form:form>
	</div>
</div>
<!-- alert_model - code exist-->

<div class="modal fade" id="alert_modal" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Alert</h4>
			</div>
			<div class="modal-body">
				<p>Code exists</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="alert_modal_close">Close</button>
			</div>
		</div>
	</div>
</div>