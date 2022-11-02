<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="formId" value="rackRate" scope="request" />
<c:set var="formName" value="rackRate" scope="request" />
<c:set var="formAction" value="../rackRate/save" scope="request" />
<c:set var="formCommandName" value="rackRate" scope="request" />
<c:set var="moduleName" value="Rack Rate" scope="request" />
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
			<form:hidden path="rateType" id="hdnRateType" value="RACKRATE" />
			<form:hidden path="validityFrom" id="validityFrom" />
			<form:hidden path="validityTo" id="validityTo" />
			<input type="hidden" id="hdnCode" value="${rackRate.code}">

			<c:set var="addMode" value="${(rackRate.id == 0) ? true : false}" />

			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">×</button>
				<c:choose>
					<c:when test="${rackRate.id==0}">
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

								<!-- Code -->
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.code" /> <span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:input id="txtCode" path="code" readonly="true"
											class="form-control form-control-inline input-medium validator"
											maxlength="15" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtCode_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtCode_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<!-- Period -->
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="rackRate.label.ratePeriod" /> <span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:select path="ratePeriodId" id="ddlRatePeriod"
											items="${ratePeriods}" class="form-control m-bot15 validator" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="ddlRatePeriod_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="ddlRatePeriod_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>

									<div class="col-md-3 details" id="divPeriodDesc"></div>
								</div>

								<!-- Room Type -->
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

								<!-- Meal Plan -->
								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="corporateRates.label.mealPlan" /></span>
								</div>
								<div class="form-group directory-list">
									<form:radiobuttons path="mealPlan" items="${mealPlans}"
										cssClass="rdMealPlan"
										element="div class='col-sm-1' style='padding: 5px 0px 5px 15px;'" />
								</div>


							</div>
						</div>

						<!-- Tariff -->
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
													<c:forEach items="${rackRate.rateDetails}" var="rateDet"
														varStatus="st">
														<c:if test="${rateDet.seasonId eq season[0]}">
															<c:set var="rtDtlIndex" value="${st.index}" />
															<c:set var="haveDtlRow" value="true" />
														</c:if>
													</c:forEach>
												</c:if>

												<c:if test="${haveDtlRow == 'false'}">
													<c:set var="rtDtlIndex"
														value="${rackRate.rateDetails.size()}" />
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

						<!-- Revenue Sharing -->
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
											<c:when test="${empty rackRate.rateDistribution}">
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
												<c:forEach var="asd" items="${rackRate.rateDistribution}"
													varStatus="stat">
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





























<%-- <jsp:directive.include file="../common/includes/page_directives.jsp" />

<c:set var="moduleTitle" value="Rackrate" scope="request" />
<c:set var="formId" value="rackrate" scope="request" />
<c:set var="formName" value="rackrate" scope="request" />
<c:set var="formAction" value="/pms/rackrate/save" scope="request" />
<c:set var="formCommandName" value="rackrate" scope="request" />
<c:set var="moduleName" value="Rackrate" scope="request" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/rackrate.css' />" />

<div class="modal-dialog modal-xtralg">
	<div class="modal-content">

		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">

			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="rateId" cssClass="rateHdrCls" />
			<form:hidden path="rateType" id="rate_type" value="RACKRATE" />
			<form:hidden path="name" id="name" />
			<form:hidden path="description" id="description" />
			<form:hidden path="rateType" />
			<form:hidden path="corporateId" />
			<form:hidden path="companyName" />
			<form:hidden path="validityFrom" id="rateValidityFrom" />
			<form:hidden path="validityTo" id="rateValidityTo" />
			
			<c:set var="addMode"
				value="${(rackrate.id == 0) ? true : false}" />

			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">&times;</button>
				<c:choose>
					<c:when test="${rackrate.id==0}">
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
							href="#tarrif">Tarrif</a></li>
						<li class=""><a aria-expanded="false" data-toggle="tab"
							href="#revenueSharing">Revenue Sharing</a></li>
					</ul>
				</header>

				<div class="panel-body">
					<div class="tab-content">
						<div id="general" class="tab-pane active">
							<div class="form-horizontal tasi-form">
								<!-- GENERAL TAB -->
								<div class="form-group">
									<form:label path="Code" cssClass="control-label col-md-3">
										<spring:message code="pms.label.code"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-3 col-xs-11">
										<form:input path="code" id="txtCode" readonly="true"
											cssClass="form-control form-control-inline input-medium validator refresh_rackrate"
											maxlength="15" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtcode_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="txtcode_warning" class="switch-right warning_red"
											style="display: none;"> <i class="fa fa-warning"></i>
										</span>
									</div>
								</div>

								<!-- Period -->
								<div class="form-group">
									<form:label path="ratePeriodId"
										cssClass="control-label col-md-3">
										<spring:message code="rackRate.label.ratePeriod"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-3 col-xs-11">
										<form:select path="ratePeriodId" id="rate_period_id"
											cssClass="d-content-body-left-most-column input-sm m-bot15 validator refresh_rackrate"
											onchange="ratePeriodChange();">
											<form:options items="${ratePeriodMapList}" />
										</form:select>
									</div>

									<div class="col-md-1 col-xs-11 tick_green_rackRate">
										<span id="rackrate_check" style="display: none;"
											class="switch-right"> <i class=" fa fa-check"></i></span> <span
											id="rackrate_warning" class="switch-right warning_red"
											style="display: none;"> <i class="fa fa-warning"></i>
										</span> <label id="ratePeriod"></label>
									</div>
								</div>

								<!-- Room Type -->
								<div class="form-group">
									<form:label path="roomTypeId" cssClass="control-label col-md-3">
										<spring:message code="rackRate.label.roomType"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-3 col-xs-11">
										<form:select path="roomTypeId" id="rackrate_room_id"
											onchange="getRoomDetailsAndHideFields();"
											cssClass="d-content-body-left-most-column input-sm m-bot15 validator refresh_rackrate">
											<form:options items="${roomMapList}" />
										</form:select>
									</div>

									<div
										class="col-md-1 col-xs-11 tick_green_rackRate details_rackRate">
										<!-- col-md-1 col-xs-11 tick_green -->
										<label id="room_type_name"></label>
									</div>

									<div class="col-md-1 col-xs-11 tick_green ">
										<span id="rackrate_check" style="display: none;"
											class="switch-right"> <i class=" fa fa-check"></i>
										</span> <span id="rackrate_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<!-- MEAL PLAN -->
								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="rackRate.label.mealPlan" /></span>
								</div>

								<div class="directory-list weekly_special_day chng_rating">
									<form:radiobuttons path="mealPlan" items="${MealPlanList}"
										element="div class='col-sm-1' style='padding: 5px 0px 5px 15px;'"
										id="meal_plan_id" />
								</div>
							</div>
						</div>

						<!-- TARRIF -->
						<div id="tarrif" class="tab-pane">
							<div class="form-horizontal tasi-form">
								<div class="form-group">
									<form:label path="isTaxIncluded"
										cssClass="control-label col-md-3">
										<spring:message code="rackRate.label.taxInclude"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-1 col-xs-11">
										<form:select path="isTaxIncluded" id="taxIncluded"
											cssClass="form-control input-sm m-bot15 validator refresh_rackrate">
											<form:options items="${taxInclude}" />
										</form:select>

									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="rackrate_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="rackrate_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="informatition_form_sub_title">
									<span class="title"><spring:message code="corporateRates.label.tariff" /></span>
									
									<span id="tariffHeading1">
										<spring:message code="corporateRates.label.amountShownBelowIncludeTaxes" />
									</span> 
									<span id="tariffHeading2">
										<spring:message code="corporateRates.label.amountShownBelowDoNotIncludeTaxes" />
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
													<c:forEach items="${rackrate.rateDetails}"
														var="rateDet" varStatus="st">
														<c:if test="${rateDet.seasonId eq season[0]}">
															<c:set var="rtDtlIndex" value="${st.index}" />
															<c:set var="haveDtlRow" value="true" />
														</c:if>
													</c:forEach>
												</c:if>

												<c:if test="${haveDtlRow == 'false'}">
													<c:set var="rtDtlIndex"
														value="${rackrate.rateDetails.size()}" />
												</c:if>

												<tr>
													<td>${season[1]}<form:hidden
															path="rateDetails[${rtDtlIndex}].id" /> <form:hidden
															path="rateDetails[${rtDtlIndex}].seasonId"
															value="${season[0]}" />
													</td>
													<td class="single"><form:input maxlength="6" class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].singleRate" /></td>
													<td class="single"><form:input maxlength="6" class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].singleSpecialRate" /></td>
													<td class="double"><form:input maxlength="6" class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].doubleRate" /></td>
													<td class="double"><form:input maxlength="6" class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].doubleSpecialRate" /></td>
													<td class="triple"><form:input maxlength="6" class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].tripleRate" /></td>
													<td class="triple"><form:input maxlength="6" class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].tripleSpecialRate" /></td>
													<td class="quad"><form:input maxlength="6" class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].quadRate" /></td>
													<td class="quad"><form:input maxlength="6" class="form-control form-control-inline input-medium"
															path="rateDetails[${rtDtlIndex}].quadSpecialRate" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>

							</div>
						</div>

						<div id="revenueSharing" class="tab-pane">
							<div class="form-horizontal tasi-form">
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
											<c:when test="${empty rackrate.rateDistribution}">
												<tr id="rowRevenue0" class="revenue-row old-row">
													<td class="first-col"><form:hidden maxlength="3"
															path="rateDistribution[0].id" /> <form:select
															path="rateDistribution[0].departmentId"
															items="${departments}" class="form-control revn-dept"
															onchange="checkRevenueDepartmentDuplication();"></form:select></td>
													<td class="single"><form:input maxlength="3" class="form-control form-control-inline input-medium"
															path="rateDistribution[0].singlePc"
															onkeyup="checkTotalPerctg('single', this);" /></td>
													<td class="double"><form:input maxlength="3" class="form-control form-control-inline input-medium"
															path="rateDistribution[0].doublePc"
															onkeyup="checkTotalPerctg('double', this);" /></td>
													<td class="triple"><form:input maxlength="3" class="form-control form-control-inline input-medium"
															path="rateDistribution[0].triplePc"
															onkeyup="checkTotalPerctg('triple', this);" /></td>
													<td class="quad"><form:input maxlength="3" class="form-control form-control-inline input-medium"
															path="rateDistribution[0].quadPc"
															onkeyup="checkTotalPerctg('quad', this);" /></td>
													<td class="delete-btn">
														<button type="button" class="btn btn-danger btn-xs" onclick="deleteRevenueRow('', 'rowRevenue0');"><i class="fa fa-trash-o "></i></button>
													</td>
												</tr>
											</c:when>
											<c:otherwise>
												<c:forEach var="asd"
													items="${rackrate.rateDistribution}" varStatus="stat">
													<tr id="rowRevenue${stat.index}"
														class="revenue-row old-row">
														<td class="first-col"><form:hidden
																path="rateDistribution[${stat.index}].id" /> <form:select
																path="rateDistribution[${stat.index}].departmentId"
																items="${departments}" class="form-control revn-dept"
																onchange="checkRevenueDepartmentDuplication();"></form:select>
															<form:hidden id="hdnRevnDel${stat.index}"
																path="rateDistribution[${stat.index}].isDeleted" /></td>
														<td class="single"><form:input maxlength="3" class="form-control form-control-inline input-medium"
																path="rateDistribution[${stat.index}].singlePc"
																onkeyup="checkTotalPerctg('single', this);" /></td>
														<td class="double"><form:input maxlength="3" class="form-control form-control-inline input-medium"
																path="rateDistribution[${stat.index}].doublePc"
																onkeyup="checkTotalPerctg('double', this);" /></td>
														<td class="triple"><form:input maxlength="3" class="form-control form-control-inline input-medium"
																path="rateDistribution[${stat.index}].triplePc"
																onkeyup="checkTotalPerctg('triple', this);" /></td>
														<td class="quad"><form:input maxlength="3" class="form-control form-control-inline input-medium"
																path="rateDistribution[${stat.index}].quadPc"
																onkeyup="checkTotalPerctg('quad', this);" /></td>
														<td class="delete-btn">
											<script>
window.cp_isCanView=${cp_isCanView}
window.cp_isCanAdd=${cp_isCanAdd}
window.cp_isCanEdit=${cp_isCanEdit}
</script>				<button type="button" class="btn btn-danger btn-xs" onclick="deleteRevenueRow('hdnRevnDel${stat.index}', 'rowRevenue${stat.index}');"><i class="fa fa-trash-o "></i></button>
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
			</div>

			<div class="modal-footer">
				<button id="save_btn" class="btn btn-success" type="button"
					onclick="saveDataSubmit();">
					<spring:message code="pms.btn.submit"></spring:message>
				</button>
				<input id="refresh_btn" class="btn btn-warning" type="button"
					onclick="refreshData()"
					value="<spring:message code="pms.btn.reset"></spring:message>" />
				<button id="delete_btn" class="btn btn-danger" type="button"
					onclick="deleteData('rateId')">
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
</div> --%>

