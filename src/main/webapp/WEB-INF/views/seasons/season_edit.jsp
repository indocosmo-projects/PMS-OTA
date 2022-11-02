<jsp:directive.include file="../common/includes/page_directives.jsp" />

<c:set var="moduleName" value="Season-Edit" scope="request" />
<c:set var="moduleTitle" value="season" scope="request" />
<c:set var="formId" value="season" scope="request" />
<c:set var="formName" value="season" scope="request" />
<c:set var="formAction" value="../season/save" scope="request" />
<c:set var="formCommandName" value="season" scope="request" />
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

<div class="modal-dialog">
	<div class="modal-content">

		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="season_id_test" />
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">&times;</button>
				<c:choose>
					<c:when test="${season.id==0}">
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
						<div id="general" class="tab-pane active">
							<div class="form-horizontal tasi-form">

								<!-- code -->
								<div class="form-group">
									<form:label path="Code" class="control-label col-md-4">
										<spring:message code="pms.label.code"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="form-control form-control-inline input-medium validator refresh_season"
											maxlength="15" />
									</div>
									<%-- <div class="col-md-6 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="form-control form-control-inline input-medium validator refresh_account"
											maxlength="15" />
									</div> --%>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtcode_check" class="switch-right"
											style="display: none;"> <i class=" fa fa-check"></i></span> <span
											class="switch-right warning_red" id="txtcode_warning"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>

								</div>
								<!-- name -->
								<div class="form-group">
									<form:label path="Name" class="control-label col-md-4">
										<spring:message code="pms.label.name"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:input path="name" id="txtname" type="text"
											maxlength="40"
											class="form-control form-control-inline input-medium validator refresh_season"
											size="16" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span class="switch-right" id="txtname_check"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											class="switch-right warning_red" id="txtname_warning"
											style="display: none;"> <i class="fa fa-warning"></i></span>
									</div>
								</div>
								<!-- description -->

								<div class="form-group">
									<form:label path="Description"
										cssClass="control-label col-md-4">
										<spring:message code="pms.label.description"></spring:message>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:textarea path="description"
											cssClass="form-control form-control-inline input-medium refresh_season"
											id="txtdescription" maxlength="200" />
									</div>
								</div>
								<!--color  -->
								<div class="form-group">
									<form:label path="colorCode" class="control-label col-md-4">
										<spring:message code="season.label.representingColor"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-6 col-xs-11">
										<form:select id="colorCode" path="colorCode"
											class="form-control m-bot15 validator"
											cssStyle="background-color:${season.colorCode}"
											onclick="this.style.backgroundColor=this.options[this.selectedIndex].style.backgroundColor">

											<c:forEach items="${color}" var="colorMap" varStatus="status">
												<form:option value="${colorMap.key}"
													style="background-color: ${colorMap.value}"></form:option>
											</c:forEach>
										</form:select>
									</div>

									<!-- <div class="col-md-1 col-xs-11 tick_green">
													<span class="switch-right" style="display: none;"><i class=" fa fa-check"></i></span>
													<span class="switch-right warning_red"><i class="fa fa-warning"></i></span>
											</div> -->
								</div>
								<!-- Start Month -->
								<div class="form-group sMonth">

									<form:label path="startMonth" class="control-label col-md-4">
										<spring:message code="season.label.startsOn"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-3 col-xs-11">
										<form:select path="startMonth" id="start_month"
											class="form-control seasonVal m-bot15 validator refresh_season "
											onchange="valOnChange();" data-old="${season.startMonth}">
											<form:option value="" label="month" />
											<form:options items="${month}" class="color" id="startMonth" />
										</form:select>
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="start_month_check" class="switch-right"
											style="display: none;"> <i class=" fa fa-check"></i></span> <span
											class="switch-right warning_red" id="start_month_warning"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>


									<!-- start day -->
									<div class="col-md-2 col-xs-11">
										<form:select path="startDay" id="start_day"
											class="form-control seasonVal m-bot15 validator refresh_season"
											onchange="valOnChange();" data-old="${season.startDay}">
											<form:option value="" label="day" />
											<form:options items="${days}" class="color" id="startDay" />
										</form:select>

									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="start_day_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											class="switch-right warning_red" id="start_day_warning"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
									<div id="validator_exch_rate_start" class='validator_cls'></div>
								</div>
								<!-- endMonth -->
								<div class="form-group">

									<form:label path="endMonth" class="control-label col-md-4 ">
										<spring:message code="season.label.endsOn"></spring:message>
										<span class="red">*</span>
									</form:label>

									<div class="col-md-3 col-xs-11">
										<form:select path="endMonth" id="end_month"
											class="form-control seasonVal m-bot15 seasonVal validator refresh_season"
											onchange="valOnChange();" data-old="${season.endMonth}">
											<form:option value="" label="month" />
											<form:options items="${month}" class="color" id="endMonth" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="end_month_check" class="switch-right"
											style="display: none;"> <i class=" fa fa-check"></i></span> <span
											class="switch-right warning_red" id="end_month_warning"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
									<!-- endday -->
									<div class="col-md-2 col-xs-11">
										<form:select path="endDay" id="end_day"
											class="form-control seasonVal m-bot15 seasonVal validator refresh_season"
											onchange="valOnChange();" data-old="${season.endDay}">
											<form:option value="" label="day" />
											<form:options items="${days}" class="color" id="endDay" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span class="switch-right" id="end_day_check"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											class="switch-right warning_red" id="end_day_warning"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
									<div id="validator_exch_rate_end" class='validator_cls'></div>
								</div>



							</div>
						</div>
					</div>


				</div>
			</div>
			<div class="modal-footer">
				<button id="save_btn" class="btn btn-success" type="submit"
					onclick="validate(); return false;">
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
					type="button" onclick="cancel()">
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

<!-- Season range is already exists  -->

<div class="modal fade" id="seasonRange_modal" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Alert</h4>
			</div>
			<div class="modal-body">
				<p>Error- This season range is already defined</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					id="seasonRange_modalClose">Close</button>
			</div>
		</div>
	</div>
</div>