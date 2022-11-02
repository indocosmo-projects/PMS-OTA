<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleName" value="Account Master" scope="request" />
<c:set var="moduleTitle" value="accountMaster" scope="request" />
<c:set var="formId" value="accountMaster" scope="request" />
<c:set var="formName" value="accountMaster" scope="request" />
<c:set var="formAction" value="../accountMaster/save" scope="request" />
<c:set var="formCommandName" value="accountMaster" scope="request" />
<c:set var="customEditIncludeFile"
	value="../account_master/account_edit_custom.jsp" scope="request" />

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
<script>

window.cp_isCanView=${cp_isCanView}
window.cp_isCanAdd=${cp_isCanAdd}
window.cp_isCanEdit=${cp_isCanEdit}


</script>

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/account_master_edit.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<div class="modal-dialog">
	<div class="modal-content">
		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">

			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="accountmaster_id" />

			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">&times;</button>
				<c:choose>
					<c:when test="${accountMaster.id==0}">
						<h4 class="modal-title">Add ${moduleName}</h4>
					</c:when>
					<c:otherwise>
						<h4 class="modal-title">Edit ${moduleName}</h4>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="modal-body">
				<div class="panel-body">
					<div class="tab-content">
						<div class="tab-pane active">
							<div class="form-horizontal tasi-form">

								<div class="form-group">
									<label class="control-label col-md-4"><spring:message
											code="pms.label.code"></spring:message><span class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="form-control form-control-inline input-medium validator refresh_account"
											maxlength="15" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtcode_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtcode_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-4"><spring:message
											code="pms.label.name"></spring:message><span class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="name" id="txtname"
											cssClass="validator form-control form-control-inline input-medium refresh_account"
											maxlength="50" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtname_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtname_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-4"><spring:message
											code="pms.label.description"></spring:message></label>
									<div class="col-md-6 col-xs-11">
										<form:textarea path="description"
											cssClass="form-control form-control-inline input-medium refresh_account"
											id="desciption" maxlength='100' />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-4"><spring:message
											code="accountMaster.label.txnType"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:select path="sysdef_acc_type_id" id="sysDefAcc"
											cssClass="form-control input-sm m-bot15 validator refresh_account"
											onchange="withOutTax();">

											<form:options items="${typesList}" id="taxlist" />
										</form:select>

									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="sysDefAcc_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="sysDefAcc_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group" id="tax_code">
									<label class="control-label col-md-4"><spring:message
											code="accountMaster.label.taxCode"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:select path="tax_id" id="tax"
											cssClass="form-control input-sm m-bot15 validator refresh_account">

											<form:options items="${taxList}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="tax_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="tax_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>





								<div class="form-group" id="service_chargediv"
									style='display: ${isServiceChargeIncluded == false ? "none" : ""};'>
									<label class="control-label col-md-4"> Service Charge %
										<span class="red">*</span>
									</label>
									<div class="col-md-6 col-xs-11">

										<form:input path="serviceCharge" id="service_charge"
											cssClass="form-control form-control-inline input-medium refresh_discount additional validator"
											maxlength="3" onkeypress="return isNumber(event)" />

									</div>



									<div class="col-md-1 col-xs-11 tick_green">
										<span id="service_charge_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="service_charge_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

                             <!-- hssn code -->
                               <div class="form-group" id="hsn_code">
								<label class="control-label col-md-4"><spring:message
											code="accountMaster.label.hssnCode"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-6 col-xs-11">

										<form:input path="hsn_code" id="hsn_code" 
											cssClass="form-control form-control-inline" oninput="this.value = this.value.replace(/[^0-9]+/g, '');"
											maxlength="15" /> 
											

									</div>
								</div>
                                <!-- end -->
                                
								<div class="form-group" id="credit_days">
									<label class="control-label col-md-4"> Credit days </label>
									<div class="col-md-6 col-xs-11">

										<form:input path="creditDays" id="creditDays"
											cssClass="form-control form-control-inline input-medium refresh_discount additional validator"
											maxlength="3" />

									</div>
								</div>



								<div class="form-group" id="tax_inc">
									<label class="control-label col-md-4"><spring:message
											code="accountMaster.label.taxInc"></spring:message> </label>
									<div class="col-md-6 col-xs-11">
										<form:checkbox id="chkIsTaxInc" class="chk_inc"
											path="is_tax_included" />
										<label for="chkIsTaxInc"></label>
									</div>

								</div>

							</div>
						</div>
					</div>
				</div>

			</div>

			<div class="modal-footer">
				<button id="save_btn" class="btn btn-success" type="button"
					onclick="saveDatas()">
					<spring:message code="pms.btn.submit"></spring:message>
				</button>
				<input id="refresh_btn" class="btn btn-warning" type="button"
					onclick="refreshData()"
					value="<spring:message code="pms.btn.reset"></spring:message>" />
				<button id="delete_btn" class="btn btn-danger" type="button"
					onclick="deleteData()">
					<i class="fa fa-trash-o "></i>
					<spring:message code="pms.btn.delete"></spring:message>
				</button>
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