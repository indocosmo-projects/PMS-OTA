<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Corporate TA Rates" scope="request" />
<c:set var="formId" value="corporateTaRate" scope="request" />
<c:set var="formName" value="corporateTaRate" scope="request" />
<c:set var="formAction" value="../corporateTaRate/save" scope="request" />
<c:set var="formCommandName" value="corporateRate" scope="request" />
<c:set var="moduleName" value="Corporate TA Rates" scope="request" />
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

<!-- Modal -->
<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">

			<input type="hidden" id="dateFormat" value="${dateFormat}">
			<form:hidden path="id" id="hdnId" />
			<form:hidden path="encryption" id="hdnEncryption" />
			<form:hidden path="corporateId" id="hdnCorporateId" />
			<form:hidden path="isActive" id="hdnStatus" />
			<form:hidden path="rateType" id="hdnRateType" value="" />

			<c:set var="addMode"
				value="${(corporateRate.id == 0) ? true : false}" />


			<%-- <form:hidden path="encryption" id="encryption_id" /> --%>
			<%-- <form:hidden path="id" id="discountId" /> --%>
			<%-- <form:hidden path="dateFormat" id="dateFormat" /> --%>
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">×</button>
				<c:choose>
					<c:when test="${corporateRate.id==0}">
						<h4 class="rate modal-title">Add ${moduleName}</h4>
					</c:when>
					<c:otherwise>
						<h4 class="rate modal-title">Edit ${moduleName}</h4>
					</c:otherwise>
				</c:choose>

			</div>
			<div class="modal-body modal_body_tabs">
				<header class="panel-heading">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#general" data-toggle="tab"
							aria-expanded="true">General</a></li>
						<li class=""><a href="#periods" data-toggle="tab"
							aria-expanded="false">Periods</a></li>
						<li class=""><a href="#tariff" data-toggle="tab"
							aria-expanded="false">Tariff</a></li>
						<li class=""><a href="#revenue_sharing" data-toggle="tab"
							aria-expanded="false">Revenue Sharing</a></li>
					</ul>
				</header>

				<div class="panel-body">
					<div class="tab-content" style="min-height: 406px">
						<div class="tab-pane active" id="general">
							<div class="form-horizontal tasi-form">
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="corporateRates.label.corporateTa" /> <span class="red">*</span>
									</label>
									<div class="col-md-4 col-xs-11">
										<input type="text" id="txtCorporateTa" maxlength="50"
											value="${corporate.name}"
											class="form-control form-control-inline input-medium validator refresh_corpta"
											disabled>
									</div>

									<div class="col-md-2 col-xs-11 tick_green align_close">
										<a id="btnSearch" class="btn btn-warning" data-toggle="modal"
											href="#corpSearchModal">Search</a>
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtCorporateTa_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtCorporateTa_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>

									<!-- Modal -->
									<div class="modal fade " id="corpSearchModal" tabindex="-1"
										role="dialog" aria-labelledby="myModalLabel"
										aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" id="btnCorpSearchClose"
														aria-hidden="true">&times;</button>
													<h4 class="modal-title">Corporate/TA</h4>
												</div>
												<div class="modal-body">
													<div id="searchResults">Loading...</div>
												</div>
												<div class="modal-footer">
													<button id="btnSearchCoprOk" class="btn btn-success"
														type="button">Ok</button>
													<button id="btnSearchCoprCancel" class="btn btn-default"
														type="button">Cancel</button>
												</div>
											</div>
										</div>
									</div>
									<!-- modal -->
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.code" /> <span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:input id="txtCode" path="code"
											class="form-control form-control-inline input-medium validator refresh_corpta"
											maxlength="15" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtCode_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtCode_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.name" /> <span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:input id="txtName" path="name"
											class="form-control form-control-inline input-medium validator refresh_corpta"
											maxlength="50" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtName_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtName_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporateRates.label.roomType" /><span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:select path="roomTypeId" id="ddlRoomType"
											data-prevval="${roomTypeId}" items="${roomTypes}"
											class="form-control m-bot15 validator"></form:select>
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="ddlRoomType_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="ddlRoomType_warning" style="display: none;"
											class="switch-right warning_red"><i
											class="fa fa-warning"></i></span>
									</div>

									<div class="col-md-3 col-xs-11 details">
										<label id="lblRoomTypeName"></label>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.description" /></label>
									<div class="col-md-4 col-xs-11">
										<form:textarea path="description"
											class="form-control form-control-inline input-medium refresh_corpta"
											maxlength="250" />
									</div>

								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporateRates.label.status" /></label>
									<div class="col-md-4 col-xs-11">
										<input type="text" id="txtStatus"
											class="form-control form-control-inline input-medium status-font"
											readonly="readonly">
									</div>

									<div class="col-md-2 col-xs-11 align_close">
										<a data-toggle="modal" href="#statusModal"
											class="btn btn-warning">Change</a>
									</div>

									<!-- Modal -->
									<div aria-hidden="true" aria-labelledby="myModalLabel"
										role="dialog" tabindex="-1" id="statusModal"
										class="modal fade">
										<div class="modal-dialog modal-sm">
											<div class="modal-content">
												<div class="modal-header">
													<button aria-hidden="true" id="btnStatusClose"
														class="close" type="button">&times;</button>
													<h4 class="modal-title">Change Status</h4>
												</div>
												<div class="modal-body">

													<div class="directory-list weekly_special_day chng_rating">
														<ul>
															<li><input type="radio" id="rdActive"
																name="rdActiveInactive" value="1"> <strong>Active</strong>
															</li>
															<li><input type="radio" id="rdInactive"
																name="rdActiveInactive" value="0"> <strong>Inactive</strong>
															</li>
														</ul>
													</div>
												</div>
												<div class="modal-footer">
													<button id="btnStatusOk" type="button"
														class="btn btn-success">Ok</button>
													<button id="btnStatusCancel" type="button"
														class="btn btn-default">Cancel</button>
												</div>
											</div>
										</div>
									</div>
									<!-- modal -->
								</div>
							</div>
						</div>
						<div class="tab-pane" id="periods">
							<div class="form-horizontal tasi-form">
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporateRates.label.agreementRef" /><span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:input id="txtAgreementRef" path="agreementRef"
											class="form-control form-control-inline input-medium validator refresh_corpta"
											maxlength="100" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtAgreementRef_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtAgreementRef_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="corporateRates.label.validityPeriod" /></span>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.from" /><span class="red">*</span></label>
									<div class="col-md-2 col-xs-11">
										<form:input id="txtValidityFrom" path="validityFrom"
											class="form-control form-control-inline input-medium datepicker validator refresh_corpta"
											readonly="true" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtValidityFrom_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtValidityFrom_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.to" /><span class="red">*</span></label>
									<div class="col-md-2 col-xs-11">
										<form:input id="txtValidityTo" path="validityTo"
											class="form-control form-control-inline input-medium datepicker validator refresh_corpta"
											readonly="true" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtValidityTo_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtValidityTo_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="tariff">
							<div class="form-horizontal tasi-form">
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporateRates.label.tariffIncludeTax" /><span
										class="red">*</span></label>
									<div class="col-md-2 col-xs-11">
										<form:select id="ddlIsTaxIncluded" path="isTaxIncluded"
											class="form-control m-bot15">
											<form:option value="1">Yes</form:option>
											<form:option value="0">No</form:option>
										</form:select>
									</div>
								</div>

								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="corporateRates.label.mealPlan" /></span>
								</div>
								<div class="form-group directory-list">
									<form:radiobuttons path="mealPlan" items="${mealPlans}"
										element="div class='col-sm-2' style='padding: 5px 0px 5px 15px;'" />
								</div>

								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="corporateRates.label.tariff" /></span> <span
										id="tariffHeading1"> <spring:message
											code="corporateRates.label.amountShownBelowIncludeTaxes" />
									</span> <span id="tariffHeading2"> <spring:message
											code="corporateRates.label.amountShownBelowDoNotIncludeTaxes" />
									</span>
								</div>
								<div class="table-responsive">
									<table id="tblTariff" class="table table-bordered tax_list_tbl">
										<thead>
											<tr>
												<th rowspan="2" class="first-col"><spring:message
														code="corporateRates.label.season" /></th>
												<th colspan="2"><spring:message
														code="corporateRates.label.single" /></th>
												<th colspan="2"><spring:message
														code="corporateRates.label.double" /></th>
												<th colspan="2"><spring:message
														code="corporateRates.label.triple" /></th>
												<th colspan="2"><spring:message
														code="corporateRates.label.quad" /></th>
											</tr>
											<tr>
												<th><spring:message code="corporateRates.label.normal" /></th>
												<th><spring:message code="corporateRates.label.special" /></th>
												<th><spring:message code="corporateRates.label.normal" /></th>
												<th><spring:message code="corporateRates.label.special" /></th>
												<th><spring:message code="corporateRates.label.normal" /></th>
												<th><spring:message code="corporateRates.label.special" /></th>
												<th><spring:message code="corporateRates.label.normal" /></th>
												<th><spring:message code="corporateRates.label.special" /></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${seasons}" var="season" varStatus="status">
												<c:set var="rtDtlIndex" value="${status.index}" />
												<c:set var="haveDtlRow" value="false" />

												<c:if test="${!addMode}">
													<c:forEach items="${corporateRate.rateDetails}"
														var="rateDet" varStatus="st">
														<c:if test="${rateDet.seasonId eq season[0]}">
															<c:set var="rtDtlIndex" value="${st.index}" />
															<c:set var="haveDtlRow" value="true" />
														</c:if>
													</c:forEach>
												</c:if>

												<c:if test="${haveDtlRow == 'false'}">
													<c:set var="rtDtlIndex"
														value="${corporateRate.rateDetails.size()}" />
												</c:if>

												<tr>
													<td>${season[1]}<form:hidden
															path="rateDetails[${rtDtlIndex}].id" /> <form:hidden
															path="rateDetails[${rtDtlIndex}].seasonId"
															value="${season[0]}" />
													</td>
													<td class="single"><form:input maxlength="6"
															class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].singleRate" /></td>
													<td class="single"><form:input maxlength="6"
															class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].singleSpecialRate" /></td>
													<td class="double"><form:input maxlength="6"
															class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].doubleRate" /></td>
													<td class="double"><form:input maxlength="6"
															class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].doubleSpecialRate" /></td>
													<td class="triple"><form:input maxlength="6"
															class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].tripleRate" /></td>
													<td class="triple"><form:input maxlength="6"
															class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].tripleSpecialRate" /></td>
													<td class="quad"><form:input maxlength="6"
															class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].quadRate" /></td>
													<td class="quad"><form:input maxlength="6"
															class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].quadSpecialRate" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="revenue_sharing">
							<div id="divRevnSharing" class="table-responsive">
								<table id="tblRevenue" class="table table-bordered tax_list_tbl">

									<thead>
										<tr>
											<th><spring:message
													code="corporateRates.label.department" /></th>
											<th><spring:message code="corporateRates.label.single" />
												(%)</th>
											<th><spring:message code="corporateRates.label.double" />
												(%)</th>
											<th><spring:message code="corporateRates.label.triple" />
												(%)</th>
											<th><spring:message code="corporateRates.label.quad" />
												(%)</th>
											<th>
												<button id="btnAddRevSharRow" type="button"
													class="btn label label-info label-mini" title="Add row"
													data-toggle="tooltip" data-placement="top">
													<i class="fa fa-plus"></i>
												</button>
											</th>
										</tr>
									</thead>
									<tbody id="tbodyRevenueSharing">
										<c:choose>
											<c:when test="${empty corporateRate.rateDistribution}">
												<tr id="rowRevenue0" class="revenue-row old-row">
													<td class="first-col"><form:hidden maxlength="3"
															path="rateDistribution[0].id" /> <form:select
															path="rateDistribution[0].departmentId"
															items="${departments}" class="form-control revn-dept"
															onchange="checkRevenueDepartmentDuplication();"></form:select></td>
													<td class="single"><form:input maxlength="3"
															class="form-control form-control-inline input-medium"
															path="rateDistribution[0].singlePc"
															onkeyup="checkTotalPerctg('single', this);" /></td>
													<td class="double"><form:input maxlength="3"
															class="form-control form-control-inline input-medium"
															path="rateDistribution[0].doublePc"
															onkeyup="checkTotalPerctg('double', this);" /></td>
													<td class="triple"><form:input maxlength="3"
															class="form-control form-control-inline input-medium"
															path="rateDistribution[0].triplePc"
															onkeyup="checkTotalPerctg('triple', this);" /></td>
													<td class="quad"><form:input maxlength="3"
															class="form-control form-control-inline input-medium"
															path="rateDistribution[0].quadPc"
															onkeyup="checkTotalPerctg('quad', this);" /></td>
													<td class="delete-btn">
														<button type="button" class="btn btn-danger btn-xs"
															onclick="deleteRevenueRow('', 'rowRevenue0');">
															<i class="fa fa-trash-o "></i>
														</button>
													</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach var="asd"
													items="${corporateRate.rateDistribution}" varStatus="stat">
													<tr id="rowRevenue${stat.index}"
														class="revenue-row old-row">
														<td class="first-col"><form:hidden
																path="rateDistribution[${stat.index}].id" /> <form:select
																path="rateDistribution[${stat.index}].departmentId"
																items="${departments}" class="form-control revn-dept"
																onchange="checkRevenueDepartmentDuplication();"></form:select>
															<form:hidden id="hdnRevnDel${stat.index}"
																path="rateDistribution[${stat.index}].isDeleted" /></td>
														<td class="single"><form:input maxlength="3"
																class="form-control form-control-inline input-medium"
																path="rateDistribution[${stat.index}].singlePc"
																onkeyup="checkTotalPerctg('single', this);" /></td>
														<td class="double"><form:input maxlength="3"
																class="form-control form-control-inline input-medium"
																path="rateDistribution[${stat.index}].doublePc"
																onkeyup="checkTotalPerctg('double', this);" /></td>
														<td class="triple"><form:input maxlength="3"
																class="form-control form-control-inline input-medium"
																path="rateDistribution[${stat.index}].triplePc"
																onkeyup="checkTotalPerctg('triple', this);" /></td>
														<td class="quad"><form:input maxlength="3"
																class="form-control form-control-inline input-medium"
																path="rateDistribution[${stat.index}].quadPc"
																onkeyup="checkTotalPerctg('quad', this);" /></td>
														<td class="delete-btn">
															<button type="button" class="btn btn-danger btn-xs"
																onclick="deleteRevenueRow('hdnRevnDel${stat.index}', 'rowRevenue${stat.index}');">
																<i class="fa fa-trash-o "></i>
															</button>
													</tr>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
							<div id="revenSharingValidator"></div>
						</div>
					</div>
				</div>

			</div>
			<div class="rate modal-footer">
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