<jsp:directive.include file="../common/includes/page_directives.jsp" />

<c:set var="moduleTitle" value="Floor" scope="request" />
<c:set var="formId" value="floor" scope="request" />
<c:set var="formName" value="floor" scope="request" />
<c:set var="formAction" value="../floor/save" scope="request" />
<c:set var="formCommandName" value="floor" scope="request" />
<c:set var="moduleName" value="Floor" scope="request" />
<c:set var="customEditIncludeFile"
	value="../floor/floor_edit_custom.jsp" scope="request" />

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
	src="<c:url value='/resources/pms/js/floor_edit.js' />"></script>
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/floor.css' />" />

<div class="modal-dialog" id="newFloorModal">
	<div class="modal-content">
		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="masterId" />
			<%-- <input type="hidden" id="floorcode" value="${floor.code}">
 --%>
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">&times;</button>
				<c:choose>
					<c:when test="${floor.id==0}">
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
								<!--  tab 1 content -->

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.code"></spring:message><span class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="validator form-control form-control-inline input-medium refresh_floor"
											maxlength="19" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtcode_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtcode_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.name"></spring:message><span class="red">*</span></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="name" id="txtname"
											cssClass="validator form-control form-control-inline input-medium refresh_floor"
											maxlength="19" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtname_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtname_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.label.description">
										</spring:message></label>
									<div class="col-md-6 col-xs-11">
										<form:textarea path="description"
											cssClass=" form-control form-control-inline input-medium  refresh_floor"
											id="description" maxlength="150" />

									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="description_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="description_warning" class="switch-right warning_red"
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
