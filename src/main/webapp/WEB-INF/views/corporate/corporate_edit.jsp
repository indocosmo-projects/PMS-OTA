<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Corporate" scope="request" />
<c:set var="moduleName" value="Corporate" scope="request" />
<c:set var="formId" value="corporate" scope="request" />
<c:set var="formName" value="corporate" scope="request" />
<c:set var="formAction" value="../corporate/save" scope="request" />
<c:set var="formCommandName" value="corporate" scope="request" />
<c:set var="customEditIncludeFile"
	value="../corporate/corporate_edit_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Corporate" scope="request" />

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
	src="<c:url value='/resources/common/js/pms_edit_common.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/pms/js/corporate_edit.js' />"></script>
<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="corporateId" />
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">×</button>
				<c:choose>
					<c:when test="${corporate.id==0}">
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
							href="#contactDtls">Contact Details</a></li>
					</ul>
				</header>
				<div class="panel-body">
					<div class="tab-content">
						<div id="general" class="tab-pane active">
							<div class="form-horizontal tasi-form">
								<!--  tab 1 content -->
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="pms.label.code"></spring:message> <span class="red">*</span>
									</label>
									<div class="col-md-6 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="form-control form-control-inline input-medium validator refresh_corporate code "
											maxlength="15" readonly="true" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtcode_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="txtcode_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="pms.label.name"></spring:message> <span class="red">*</span>
									</label>
									<div class="col-md-6 col-xs-11">
										<form:input path="name" id="txtname"
											cssClass="form-control form-control-inline input-medium validator refresh_corporate"
											maxlength="80" />
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtname_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="txtname_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="corporate.label.address"></spring:message>
									</label>
									<div class="col-md-6 col-xs-11">
										<form:textarea path="address"
											cssClass="form-control form-control-inline input-medium  refresh_corporate"
											id="txtdescription" maxlength="250" />
									</div>
								</div>
								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="corporate.label.classificationAndStatus.header" /></span>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporate.label.classificationAndStatus.customerClassification"></spring:message><span
										class="red">*</span> </label>
									<div class="col-md-6 col-xs-11">
										<form:select path="corporateClass" id="corporate_class"
											cssClass="form-control input-sm m-bot15 validator clft_field"
											maxlength="4">
											<form:options items="${corporateClass}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="corporate_class_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="corporate_class_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>

								<!--start customer rating section -->
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporate.label.classificationAndStatus.customerRating"></spring:message></label>
									<div class="col-md-2 col-xs-11">

										<form:input path="corporateRating" id="current_rating"
											cssClass="form-control form-control-inline input-medium validator status-field  refresh_corporate"
											readonly="true"
											style="color: green !important;font-weight: bold;" />
										<!-- <input type="text" id="cRating"
											class="form-control form-control-inline input-medium"
											size="16" value="" style="color: green !important"
											readonly="readonly"> -->
									</div>
									<div class="col-md-2 col-xs-11 tick_green align_close">
										<a id="rating_change" class="btn btn-warning"
											href="#ratingModal" data-toggle="modal"><spring:message
												code="corporate.label.classificationAndStatus.changeButton"></spring:message></a>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="current_rating_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="current_rating_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>


									<!-- Modal of customer rating section start-->
									<div class="modal fade" id="ratingModal" tabindex="-1"
										role="dialog" aria-labelledby="myModalLabel"
										aria-hidden="true">
										<div class="modal-dialog modal-sm">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close rbtnClose"
														aria-hidden="true">&times;</button>
													<h4 class="modal-title">Change Rating</h4>
												</div>
												<div class="modal-body">
													<div id="ratings"
														class="directory-list weekly_special_day chng_rating">
														<ul>
															<li><input type="radio" checked="true" id="rating1"
																name="rating" value="A"><strong>A</strong></li>
															<li><input type="radio" id="rating2" name="rating"
																value="B"><strong>B</strong></li>
															<li><input type="radio" id="rating3" name="rating"
																value="C"><strong>C</strong></li>
															<li><input type="radio" id="rating4" name="rating"
																value="D"><strong>D</strong></li>
															<li><input type="radio" id="rating5" name="rating"
																value="E"><strong>E</strong></li>
															<li><input type="radio" id="rating6" name="rating"
																value="F"><strong>F</strong></li>
														</ul>
													</div>
												</div>
												<div class="modal-footer">
													<button id="rbtnSubmit" type="button"
														class="btn btn-success">Ok</button>
													<button id="scanButton" type="button"
														class="btn btn-default rbtnClose">Cancel</button>
												</div>
											</div>
										</div>
									</div>
									<!-- Modal of customer rating section end-->
								</div>
								<!--end customer rating section -->

								<!-- start customer status section-->
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporate.label.classificationAndStatus.customerStatus"></spring:message></label>
									<div class="col-md-2 col-xs-11">
										<form:hidden path="status" id="current_status"
											cssClass="validator status-field  refresh_corporate "
											style="float:left;" readonly="true" />
										<input type="text" id="cStatus" readonly="readonly"
											class="form-control form-control-inline input-medium"
											size="16">
									</div>
									<div class="col-md-2 col-xs-11 tick_green align_close">
										<a id="status-change" data-toggle="modal" href="#statusModal"
											class="btn btn-warning"><spring:message
												code="corporate.label.classificationAndStatus.changeButton"></spring:message></a>
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="current_status_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="current_status_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>

									<!-- modal of customer status section end-->
									<div role="dialog" tabindex="-1" id="statusModal"
										class="modal fade">
										<div class="modal-dialog modal-sm">
											<div class="modal-content">
												<div class="modal-header">
													<button aria-hidden="true" class="close sbtnClose"
														type="button">×</button>
													<h4 class="modal-title">Change Status</h4>
												</div>
												<div class="modal-body">

													<div id="status"
														class="directory-list weekly_special_day chng_rating">
														<ul>
															<li><input type="radio" name="status" id="status1"
																value="1" checked="true"><strong>Active</strong></li>
															<li><input type="radio" name="status" id="status2"
																value="0"><strong>In Active</strong></li>
														</ul>
													</div>
												</div>
												<div class="modal-footer">
													<button id="sbtnSubmit" type="button"
														class="btn btn-success">Ok</button>
													<button id="scanButton" type="button"
														class="btn btn-default sbtnClose">Cancel</button>
												</div>
											</div>
										</div>
									</div>
									<!-- modal of customer status section end-->
								</div>
								<!-- end customer status section-->
							</div>
						</div>
						<div id="contactDtls" class="tab-pane">
							<div class="form-horizontal tasi-form">
								<!-- tab 2  content-->

								<div class="form-group">
									<label class="control-label col-md-3 "> <spring:message
											code="corporate.label.contactDetails.contactPerson"></spring:message>
										<!-- <span class="red">*</span> -->
									</label>
									<div class="col-md-6 col-xs-11">
										<form:input path="contactPerson" id="contact_person"
											cssClass="form-control form-control-inline input-medium refresh_corporate"
											maxlength="100" />
									</div>
									<!-- <div class="col-md-1 col-xs-11 tick_green">
										<span id="contact_person_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="contact_person_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div> -->
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporate.label.contactDetails.email"></spring:message>
										<!-- <span
										class="red">*</span> --></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="contactEmail" id="contact_email"  pattern="^[_\.0-9a-z-]+@([0-9a-z][0-9a-z-]+)+((\.)[a-z]{2,})+$"
											cssClass="form-control form-control-inline input-medium refresh_corporate "
											maxlength="100" />
									</div>
									 <div class="col-md-1 col-xs-11 tick_green">
										<span id="contact_email_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="contact_email_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div> 
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporate.label.contactDetails.mobile"></spring:message>
										<!-- <span
										class="red">*</span> --></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="contactMobile" id="contact_mobile"
											cssClass="form-control form-control-inline input-medium  refresh_corporate num_field"
											maxlength="12" />
									</div>
									<!-- <div class="col-md-1 col-xs-11 tick_green">
										<span id="contact_mobile_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="contact_mobile_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div> -->
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporate.label.contactDetails.officePhone"></spring:message>
										<!-- <span
										class="red">*</span> --></label>
									<div class="col-md-2 col-xs-11">
										<form:hidden path="contactOffice" id="contact_office"
											cssClass="pms_text refresh_corporate num_field"
											maxlength="11" />
										<input type=text id="contact_office_code"
											class="form-control form-control-inline input-medium refresh_corporate num_field s_ph_no"
											maxlength="4" placeholder="  Code" />
									</div>
									<div class="col-md-4 col-xs-11">
										<input type=text id="contact_office_no"
											class="form-control form-control-inline input-medium refresh_corporate num_field s_ph_no"
											maxlength="7" placeholder="  Number" />
									</div>
									<!-- <div class="col-md-1 col-xs-11 tick_green">
										<span id="contact_office_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="contact_office_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div> -->
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporate.label.contactDetails.fax"></spring:message></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="contactFax" id="contact_fax"
											cssClass="form-control form-control-inline input-medium  refresh_corporate"
											maxlength="15" />
									</div>
								</div>
								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="corporate.label.backOffice.header" /></span>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="corporate.label.backOffice.accountNo"></spring:message></label>
									<div class="col-md-6 col-xs-11">
										<form:input path="backOfficeAC" id="back_office_ac"
											cssClass="form-control form-control-inline input-medium  refresh_corporate"
											maxlength="100" />
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