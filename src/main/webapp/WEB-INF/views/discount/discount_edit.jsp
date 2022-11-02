<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Discount" scope="request" />
<c:set var="moduleName" value="Discount" scope="request" />
<c:set var="formId" value="discount" scope="request" />
<c:set var="formName" value="discount" scope="request" />
<c:set var="formAction" value="../discount/save" scope="request" />
<c:set var="formCommandName" value="discount" scope="request" />
<c:set var="customEditIncludeFile"
	value="../discount/discount_edit_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Discount" scope="request" />
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
	src="<c:url value='/resources/pms/js/discount_edit.js' />"></script>
<link href="<c:url value="/resources/pms/css/dicount.css" />"
	rel="stylesheet" />
<div class="modal-dialog">
	<div class="modal-content">
		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="discountId" />
			<form:hidden path="dateFormat" id="dateFormat" />
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<c:choose>
					<c:when test="${discount.id==0}">
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
							href="#more">More</a></li>
					</ul>
				</header>
				<div class="panel-body">
					<div class="tab-content">
						<div id="general" class="tab-pane active">
							<div class="form-horizontal tasi-form">
								<div class="form-group">
									<form:label path="Code" cssClass="control-label col-md-3">
										<spring:message code="pms.label.code"></spring:message>
										<span class="red">*</span>
									</form:label>
									<div class="col-md-6 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="form-control form-control-inline input-medium validator refresh_discount"
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
									<form:label path="Name" cssClass="control-label col-md-3">
										<spring:message code="pms.label.name"></spring:message>
										<span class="red">*</span>
									</form:label>
									<div class="col-md-6 col-xs-11">
										<form:input path="name" id="txtname"
											cssClass="form-control form-control-inline input-medium validator refresh_discount"
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
									<form:label path="Description"
										cssClass="control-label col-md-3">
										<spring:message code="pms.label.description"></spring:message>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:textarea path="description"
											cssClass="form-control form-control-inline input-medium  refresh_discount"
											id="txtdescription" maxlength="100" />
									</div>
								</div>
								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="pms.label.validityPeriod"></spring:message></span>
								</div>
								<div class="form-group">
									<form:label path="ValidFrom" cssClass="control-label col-md-3">
										<spring:message code="pms.placeholder.validityDateFrom"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-4 col-xs-11">
										<spring:message code="discount.placeholder.dateFrom"
											var="placeHolderFrom"></spring:message>
										<form:input path="validFrom" id="valid_from"
											cssClass="datepicker  form-control form-control-inline input-medium validator refresh_discount"
											placeHolder="      ${placeHolderFrom}" autocomplete="off"
											readonly="true" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="valid_from_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="valid_from_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<form:label path="ValidFrom" cssClass="control-label col-md-3">
										<spring:message code="pms.placeholder.validityDateTo"></spring:message>
									</form:label>

									<div class="col-md-4 col-xs-11">
										<spring:message code="discount.placeholder.dateTo"
											var="placeHolderTo"></spring:message>
										<form:input path="validTo" id="valid_to"
											cssClass="datepicker form-control form-control-inline input-medium refresh_discount additional"
											placeholder="        ${placeHolderTo}" autocomplete="off"
											readonly="true" />
									</div>
									<div class="col-md-1 col-xs-11 refrsh_sectn">
										<a id="reset_to_date" href="#" title="reset"
											data-toggle="tooltip" data-placement="top"> <img
											class="fieldReset"
											src="../resources/pms/images/waste-bin.svg" /></a>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="valid_to_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="valid_to_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
							</div>
						</div>
						<div id="more" class="tab-pane">
							<div class="form-horizontal tasi-form">
								<div class="form-group">
									<form:label path="discType" cssClass="control-label col-md-4">
										<spring:message code="discount.label.discountType"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:select path="discType" id="disc_type"
											cssClass="form-control input-sm m-bot15 validator refresh_discount">
											<form:options items="${discountType}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="disc_type_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="disc_type_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								
								<div class="form-group">
									<form:label path="discType" cssClass="control-label col-md-4">
										<spring:message code="discount.label.discountFor"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:select path="discountFor" id="discount_for"
											cssClass="form-control input-sm m-bot15 validator refresh_discount">
											<form:options items="${discountFor}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="discount_for_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="discount_for_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group alter_with_plan">
									<form:label path="rateId" cssClass="control-label col-md-4">
										<spring:message code="discount.label.ratePlan"></spring:message>
										<span class="red">*</span>
									</form:label>
									<div class="col-md-6 col-xs-11">
										<form:select path="rateId" id="rate_plan"
											cssClass="form-control input-sm m-bot15 validator refresh_discount">
											<form:options items="${rateHdrList}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="rate_plan_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="rate_plan_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group alter_with_general">
									<form:label path="isOpen" cssClass="control-label col-md-4">
										<spring:message code="discount.label.openDiscount"></spring:message>
										<span class="red">*</span>
									</form:label>
									<div class="col-md-6 col-xs-11">
										<form:select path="isOpen" id="isopen"
											cssClass="form-control input-sm m-bot15 validator refresh_discount"
											disabled="true">
											<form:options items="${openOptions}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="is_open_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="is_open_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<form:label path="isPc" cssClass="control-label col-md-4">
										<spring:message code="discount.label.calculationType"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:select path="isPc" id="calc_mode"
											cssClass="form-control input-sm m-bot15 validator additional refresh_discount">
											<form:options items="${calculationType}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="calc_mode_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="calc_mode_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group" id="disc_amt_section">
									<form:label path="DiscAmount" cssClass="control-label col-md-4">
										<spring:message code="discount.label.discountAmount"></spring:message>
										<span class="red">*</span>
									</form:label>
									<div class="col-md-6 col-xs-11">
										<form:input path="discAmount" id="disc_amount"
											cssClass="form-control form-control-inline input-medium refresh_discount additional"
											maxlength="8" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="disc_amount_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="disc_amount_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group" id="disc_pc_section">
									<form:label path="DiscPc" cssClass="control-label col-md-4">
										<spring:message code="discount.label.discountPercentage"></spring:message>
										<span class="red">*</span>
									</form:label>
									<div class="col-md-6 col-xs-11">
										<form:input path="discPc" id="disc_pc" maxlength="3"
											cssClass="form-control form-control-inline input-medium  refresh_discount additional" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="disc_pc_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="disc_pc_warning" class="switch-right warning_red"
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
					onclick="saveData()">
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

<!-- modal -->
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
