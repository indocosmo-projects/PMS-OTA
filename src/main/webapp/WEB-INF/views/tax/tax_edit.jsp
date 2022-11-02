<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleName" value="Tax" scope="request" />
<c:set var="formId" value="tax" scope="request" />
<c:set var="formName" value="tax" scope="request" />
<c:set var="formAction" value="../TaxHdr/save" scope="request" />
<c:set var="formCommandName" value="tax" scope="request" />

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

<link href="<c:url value="/resources/pms/css/tax.css" />"
	rel="stylesheet" />
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/tax_edit.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>

<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="hiddenIdTaxHdr" cssClass="hdrDivClass" />
			<input type="hidden" value="${dateFormat}" id="dateFormat" />
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<c:choose>
					<c:when test="${tax.id==0}">
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
							href="#details">Tax Details</a></li>
					</ul>
				</header>
				<div class="panel-body">
					<div class="tab-content">
						<div class="tab-pane active" id="general">
							<div class="form-horizontal tasi-form">

								<!-- code -->
								<div class="form-group">

									<form:label path="Code" class="control-label col-md-3">
										<spring:message code="pms.label.code"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="form-control form-control-inline input-medium validator refresh_tax "
											maxlength="15" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtcode_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="txtcode_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<!-- name -->
								<div class="form-group">

									<form:label path="Name" class="control-label col-md-3">
										<spring:message code="pms.label.name"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:input path="name" id="txtname"
											cssClass="form-control form-control-inline input-medium validator refresh_tax"
											maxlength="50" />

									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtname_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="txtname_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>

								</div>

								<!--description -->
								<div class="form-group">

									<form:label path="Description" class="control-label col-md-3">
										<spring:message code="pms.label.description">
										</spring:message>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:textarea path="description"
											cssClass="form-control form-control-inline input-medium refresh_tax"
											id="description" maxlength="100" />
									</div>
								</div>

								<div class="form-group">

									<form:label path="indicator" class="control-label col-md-3">
										<spring:message code="pms.label.tax_indicator">
										</spring:message>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:input path="indicator"
											cssClass="form-control form-control-inline input-medium refresh_tax"
											id="indicator" maxlength="2" />
									</div>
								</div>

							</div>
						</div>

						<!-- first tab ends -->
						<div id="details" class="tab-pane">
							<div id="taxDetail_able_rror" class='tableValCls'></div>
							<div class="informatition_form_sub_title">
								<span class="title"><spring:message
										code="tax.label.settings" /> </span>
							</div>
							<div id="divTaxSettings" class="table-responsive">
								<table id="tblTax" class="table table-bordered tax_list_tbl">
									<thead class=txTableHeader>
										<tr>
											<th class="first-col">
												<div class="tax_head_date">
													<spring:message code="tax.add.validFrom" />
												</div>
											</th>
											<th>
												<div class="taxOne" id="tax1Setting" title="Tax 1">%</div>
											</th>
											<th>
												<div class=" taxTwo" id="tax2Setting" title="Tax 2"></div>
											</th>
											<th>
												<div class=" taxThree" id="tax3Setting" title="Tax 3"></div>
											</th>
											<th>
												<div class=" taxFour" id="tax4Setting" title="Tax 4"></div>
											</th>
											<th>
												<button id="btnAddTaxRow" type="button"
													class="btn label label-info label-mini" title="Add row"
													data-toggle="tooltip" data-placement="top">
													<i class="fa fa-plus"></i>
												</button>
											</th>
										</tr>
									</thead>
									<tbody id="tbodyTaxSettings">
										<c:set var="taxDetailRowCount" value="${countTaxDetail}" />
										<c:choose>
											<c:when test="${taxDetailRowCount!=0}">
												<c:forEach var="item" items="${tax.taxDetails}"
													varStatus="count">
													<tr id="rowTax${count.index}" class="tax-row old-row">
														<td class="first-col"><form:hidden
																path="taxDetails[${count.index}].id"
																value="${taxDetails.id}" /> <form:hidden
																path="taxDetails[${count.index}].isDeleted"
																class="divClass" id="hdnRevnDel${count.index}" /> <form:input
																path="taxDetails[${count.index}].validFrom"
																id="dateRow${count.index}"
																class="datepicker divClass validTable input_valid_date hasDatepicker" />
														</td>
														<td class="tax1"><form:input
																path="taxDetails[${count.index}].tax1Pc"
																id="tax1Row${count.index}"
																class="taxTableText divClass taxOne validTable class1forloop"
																onkeypress="return isNumberKey(event)" maxlength="9" />
														</td>
														<td class="tax2"><form:input
																path="taxDetails[${count.index}].tax2Pc"
																id="tax2Row${count.index}"
																class="taxTableText divClass taxTwo validTable "
																onkeypress="return isNumberKey(event)" maxlength="9" />
														</td>
														<td class="tax3"><form:input
																path="taxDetails[${count.index}].tax3Pc"
																id="tax3Row${count.index}"
																class="taxTableText divClass taxThree validTable "
																onkeypress="return isNumberKey(event)" maxlength="9" />
														</td>
														<td class="tax4"><form:input
																path="taxDetails[${count.index}].tax4Pc"
																id="tax4Row${count.index}"
																class="taxTableText divClass taxFour validTable "
																onkeypress="return isNumberKey(event)" maxlength="9" />
														</td>
														<td class="delete-btn">
															<button type="button" class="btn btn-danger btn-xs"
																id="del${count.index}"
																onclick="deleteRow(this,'old-row')">
																<i class="fa fa-trash-o "></i>
															</button>
														</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr id="rowTax0" class="tax-row new-row">
													<td class="first-col"><form:input
															path="taxDetails[0].validFrom"
															cssClass="datepicker divClass input_valid_date validTable"
															id="dateRow0" /></td>
													<td class="tax1"><form:input
															path="taxDetails[0].tax1Pc"
															cssClass="taxTableText divClass taxOne validTable "
															id="tax1Row0" onkeypress="return isNumberKey(event)"
															maxlength="5" /></td>
													<td class="tax2"><form:input
															path="taxDetails[0].tax2Pc"
															cssClass="taxTableText divClass taxTwo validTable "
															id="tax2Row0" onkeypress="return isNumberKey(event)"
															maxlength="5" /></td>
													<td class="tax3"><form:input
															path="taxDetails[0].tax3Pc"
															cssClass="taxTableText divClass taxThree validTable "
															id="tax3Row0" onkeypress="return isNumberKey(event)"
															maxlength="5" /></td>
													<td class="tax4"><form:input
															path="taxDetails[0].tax4Pc"
															cssClass="taxTableText divClass taxFour validTable "
															id="tax4Row0" onkeypress="return isNumberKey(event)"
															maxlength="5" /></td>
													<td class="delete-btn">
														<button type="button" class="btn btn-danger btn-xs"
															id="del0" onclick="deleteRow(this,'new-row')">
															<i class="fa fa-trash-o "></i>
														</button>
													</td>
												</tr>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
							<!-- tax second tab -->

						</div>
						<!--second tab ends-->

					</div>
				</div>
				<!-- BUTTONS -->

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
						onclick="deleteData('hiddenIdTaxHdr')">
						<i class="fa fa-trash-o "></i>
						<spring:message code="pms.btn.delete"></spring:message>
					</button>
				</c:if>



				<button id="back_btn" data-dismiss="modal" class="btn btn-default"
					type="button">
					<spring:message code="pms.btn.back"></spring:message>
				</button>

			</div>
			<!-- modal div closing -->

		</form:form>
	</div>
</div>
<!-- Room feature already exist-->

<div class="modal fade" id="delete_modal" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Alert</h4>
			</div>
			<div class="modal-body">
				<p>Feature already exists</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" id="delete_close">Close</button>
			</div>
		</div>
	</div>
</div>