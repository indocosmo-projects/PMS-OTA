<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Room Rate" scope="request" />
<c:set var="moduleName" value="Room Rate" scope="request" />
<c:set var="formId" value="room_rate" scope="request" />
<c:set var="formName" value="room_rate" scope="request" />
<c:set var="formAction" value="../roomRate/save" scope="request" />
<c:set var="formCommandName" value="rateHdr" scope="request" />
<c:set var="customEditIncludeFile"
	value="../room_rate/room_rate_edit_custom.jsp" scope="request" />
<c:set var="masterListHeaderName ." value="Room Rate" scope="request" />

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
	src="<c:url value='/resources/pms/js/room_rate_edit.js' />"></script>
<div class="modal-dialog modal-lg">
	<div class="modal-content">
		<form:form id="${formId}" name="${formName}"
			commandName="${formCommandName}" action="${formAction}" method="POST">
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="room_rate_Id" />
			<form:hidden path="rateType" id="rate_type" value="OTHERROOMRATES" />
			<form:hidden path="dateFormat" id="dateFormat" />
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<c:choose>
					<c:when test="${rateHdr.id==0}">
						<h4 class="rate modal-title">Add ${moduleName}</h4>
					</c:when>
					<c:otherwise>
						<h4 class="rate modal-title">Edit ${moduleName}</h4>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="modal-body modal_body_tabs">
				<header class="panel-heading ">
					<ul class="nav nav-tabs">
						<li class="active"><a aria-expanded="true" data-toggle="tab"
							href="#general">General</a></li>
						<li class=""><a aria-expanded="false" data-toggle="tab"
							href="#tariff">Tariff</a></li>
						<li class=""><a aria-expanded="false" data-toggle="tab"
							href="#revnShare">Revenue Sharing</a></li>
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
									<div class="col-md-4 col-xs-11">
										<form:input path="code" id="txtcode"
											cssClass="form-control form-control-inline input-medium validator refresh_roomRate"
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
									<label class="control-label col-md-3"><spring:message
											code="pms.label.name"></spring:message><span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:input path="name" id="txtname"
											cssClass="validator form-control form-control-inline input-medium refresh_roomRate"
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
									<div class="col-md-4 col-xs-11">
										<form:textarea path="description"
											cssClass=" form-control form-control-inline input-medium refresh_roomRate"
											id="description" maxlength="250" />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="roomRate.label.roomType"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:select path="roomTypeId" id="room_type"
											data_prev="${rateHdr.roomTypeId}"
											cssClass="form-control m-bot15 validator ">
											<form:options items="${roomTypeListMap}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="room_type_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="room_type_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
									<div class="col-md-3 col-xs-11 tick_green details">
										<label id="room_type_name"></label>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="roomRate.label.status"></spring:message></label>
									<div class="col-md-2 col-xs-11">
										<form:hidden path="isActive" id="current_status"
											cssClass="validator status-field  refresh_roomRate pms_nonrequired"
											style="float:left;" readonly="true" />
										<input type="text" id="cStatus"
											class="form-control form-control-inline input-medium status-font "
											readonly="readonly">
									</div>
									<div class="col-md-2 col-xs-11 align_close">
										<a id="status-changer" data-toggle="modal" href="#statusModal"
											class="btn btn-warning "><spring:message
												code="corporate.label.classificationAndStatus.changeButton"></spring:message></a>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="current_status_check" style="display: none;"
											class="switch-right"> <i class=" fa fa-check"></i></span> <span
											id="current_status_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>


									<!-- Modal -->
									<div role="dialog" tabindex="-1" id="statusModal"
										class="modal fade">
										<div class="modal-dialog modal-sm">
											<div class="modal-content">
												<div class="modal-header">
													<button aria-hidden="true" class="close sbtnClose"
														type="button">x</button>
													<h4 class="modal-title">Change Status</h4>
												</div>
												<div class="modal-body">
													<div id="status"
														class="directory-list weekly_special_day chng_rating">
														<ul>
															<li><input type="radio" name="status" id="status1"
																value="true" checked="true"><strong>Active</strong></li>
															<li><input type="radio" name="status" id="status2"
																value="false"><strong>In Active</strong></li>
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
									<!-- modal -->
								</div>

								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="pms.label.validityPeriod"></spring:message></span>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="discount.placeholder.dateFrom"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-2 col-xs-11">
										<form:input path="validityFrom" id="valid_from"
											cssClass="datepicker form-control form-control-inline input-medium validator refresh_roomRate additional"
											autocomplete="off" readonly="true" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="valid_from_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="valid_from_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="pms.placeholder.validityDateTo"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-2 col-xs-11">
										<form:input path="validityTo" id="valid_to"
											cssClass="form-control form-control-inline input-medium datepicker validator refresh_roomRate additional"
											placeholder="        ${placeHolderTo}" autocomplete="off"
											readonly="true" />
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

						<div id="tariff" class="tab-pane">
							<div class="form-horizontal tasi-form">
								<!-- tab 2  content-->
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="roomRate.label.tariffInclude"></spring:message><span
										class="red">*</span></label>
									<div class="col-md-2 col-xs-11">
										<form:select path="isTaxIncluded" id="tax_included"
											cssClass="form-control m-bot15 validator">
											<form:options items="${taxIncludeOption}" />
										</form:select>
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="tax_included_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="tax_included_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="informatition_form_sub_title">
									<span class="title">MEAL PLAN </span>
								</div>
								<div class="directory-list weekly_special_day chng_rating">
									<form:radiobuttons path="mealPlan" id="meal_plans"
										items="${MealPlanList}"
										cssClass="radio_button_alignment validator" />
								</div>

								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="roomRate.label.tariff.header" /></span> <span
										class="t-main-header-second" id="without_tax"><spring:message
											code="roomRate.label.tariff.header.discriptionWithTax" /></span> <span
										class="t-main-header-second" id="without_out_tax"><spring:message
											code="roomRate.label.tariff.header.discriptionWithoutTax" /></span>
								</div>
								<div class="t-table">
									<!-- sub heading start  -->
									<div id="t_style">
										<div class="t-sub-header-body-row">
											<!-- sub heading  columns-->
											<div class="t-sub-header-body-left-most-column">
												<spring:message code="roomRate.label.tariff.season" />
											</div>
											<div class="t-sub-header-body-column">
												<div class="t-sub-header-body-column-row-first">
													<spring:message code="roomRate.label.single" />
												</div>
												<div class="t-sub-header-body-column-row-second">
													<div class="t-sub-header-body-column-part1">
														<spring:message code="roomRate.label.roomType.normal" />
													</div>
													<div class="t-sub-header-body-column-part2">
														<spring:message code="roomRate.label.roomType.special" />
													</div>
												</div>
											</div>

											<div class="t-sub-header-body-column">
												<div class="t-sub-header-body-column-row-first">
													<spring:message code="roomRate.label.double" />
												</div>
												<div class="t-sub-header-body-column-row-second">
													<div class="t-sub-header-body-column-part1">
														<spring:message code="roomRate.label.roomType.normal" />
													</div>
													<div class="t-sub-header-body-column-part2">
														<spring:message code="roomRate.label.roomType.special" />
													</div>
												</div>
											</div>

											<div class="t-sub-header-body-column">
												<div class="t-sub-header-body-column-row-first">
													<spring:message code="roomRate.label.triple" />
												</div>
												<div class="t-sub-header-body-column-row-second">
													<div class="t-sub-header-body-column-part1">
														<spring:message code="roomRate.label.roomType.normal" />
													</div>
													<div class="t-sub-header-body-column-part2">
														<spring:message code="roomRate.label.roomType.special" />
													</div>
												</div>
											</div>

											<div class="t-sub-header-body-right-most-column">
												<div class="t-sub-header-body-column-row-first">
													<spring:message code="roomRate.label.quad" />
												</div>
												<div class="t-sub-header-body-column-row-second">
													<div class="t-sub-header-body-column-part1">
														<spring:message code="roomRate.label.roomType.normal" />
													</div>
													<div class="t-sub-header-body-column-part2">
														<spring:message code="roomRate.label.roomType.special" />
													</div>
												</div>
											</div>
										</div>
										<!-- sub heading end  -->

										<!-- body start  -->
										<c:set var="rowCount" value="0" />
										<c:set var="rowCount" value="${roomTypeListSize}" />
										<c:set var="activeRows" value=""></c:set>
										<c:forEach items="${seasonList}" var="season"
											varStatus="seasonCount">
											<c:set var="seasonId" value="${season.id}" />
											<c:set var="filled" value="false" />
											<div class="t-content-body-row">
												<c:forEach items="${rateHdr.rateDetails}" var="roomrate"
													varStatus="count">
													<c:choose>
														<c:when test="${roomrate.seasonId==seasonId}">
															<div class="t-content-body-left-most-column">
																<c:out value="${season.name}"></c:out>
																<form:hidden path="rateDetails[${count.index}].seasonId" />
															</div>
															<form:hidden path="rateDetails[${count.index}].id"
																value="${roomrate.id}" />
															<div id="single${seasonCount.index}"
																class="t-content-body-column single">
																<div class="t-content-body-column-row-first ">
																	<div class="t-content-body-column-part1 ">
																		<form:input
																			path="rateDetails[${count.index}].singleRate"
																			class="dtl_rate_validator" />
																	</div>
																	<div class="t-content-body-column-part2 ">
																		<form:input
																			path="rateDetails[${count.index}].singleSpecialRate"
																			class="dtl_rate_validator" />
																	</div>
																</div>
															</div>
															<div id="double${seasonCount.index}"
																class="t-content-body-column double">
																<div class="t-content-body-column-row-first">
																	<div class="t-content-body-column-part1 ">
																		<form:input
																			path="rateDetails[${count.index}].doubleRate"
																			class="dtl_rate_validator" />
																	</div>
																	<div class="t-content-body-column-part2 ">
																		<form:input
																			path="rateDetails[${count.index}].doubleSpecialRate"
																			class="dtl_rate_validator" />
																	</div>
																</div>
															</div>
															<div id="triple${seasonCount.index}"
																class="t-content-body-column triple">
																<div class="t-content-body-column-row-first">
																	<div class="t-content-body-column-part1 ">
																		<form:input
																			path="rateDetails[${count.index}].tripleRate"
																			class="dtl_rate_validator" />
																	</div>
																	<div class="t-content-body-column-part2 ">
																		<form:input
																			path="rateDetails[${count.index}].tripleSpecialRate"
																			class="dtl_rate_validator" />
																	</div>
																</div>
															</div>

															<div id="quad${seasonCount.index}"
																class="t-content-body-right-most-column quad">
																<div class="t-content-body-column-row-first">
																	<div class="t-content-body-column-part1 ">
																		<form:input
																			path="rateDetails[${count.index}].quadRate"
																			class="dtl_rate_validator" />
																	</div>
																	<div class="t-content-body-column-part2 ">
																		<form:input
																			path="rateDetails[${count.index}].quadSpecialRate"
																			class="dtl_rate_validator" />
																	</div>
																</div>
															</div>
															<c:set var="filled" value="true" />
															<c:set var="activeRows"
																value="${roomrate.seasonId},${activeRows}"></c:set>
															<!-- <script>alert("${activeRows}"/* , "hai" */);</script> -->
														</c:when>
													</c:choose>
												</c:forEach>
												<c:if test="${filled != true}">
													<div class="t-content-body-left-most-column">
														<c:out value="${season.name}"></c:out>
														<form:hidden path="rateDetails[${rowCount}].seasonId"
															value="${seasonId}" />
													</div>
													<div id="single${seasonCount.index}"
														class="t-content-body-column single">
														<div class="t-content-body-column-row-first ">
															<div class="t-content-body-column-part1 ">
																<form:input path="rateDetails[${rowCount}].singleRate"
																	class="dtl_rate_validator" />
															</div>
															<div class="t-content-body-column-part2 ">
																<form:input
																	path="rateDetails[${rowCount}].singleSpecialRate"
																	class="dtl_rate_validator" />
															</div>
														</div>
													</div>

													<div id="double${seasonCount.index}"
														class="t-content-body-column double">
														<div class="t-content-body-column-row-first">
															<div class="t-content-body-column-part1 ">
																<form:input path="rateDetails[${rowCount}].doubleRate"
																	maxlength="10" class="dtl_rate_validator" />
															</div>
															<div class="t-content-body-column-part2 ">
																<form:input
																	path="rateDetails[${rowCount}].doubleSpecialRate"
																	maxlength="10" class="dtl_rate_validator" />
															</div>
														</div>
													</div>

													<div id="triple${seasonCount.index}"
														class="t-content-body-column triple">
														<div class="t-content-body-column-row-first">
															<div class="t-content-body-column-part1 ">
																<form:input path="rateDetails[${rowCount}].tripleRate"
																	class="dtl_rate_validator" />
															</div>
															<div class="t-content-body-column-part2 ">
																<form:input
																	path="rateDetails[${rowCount}].tripleSpecialRate"
																	class="dtl_rate_validator" />
															</div>
														</div>
													</div>

													<div id="quad${seasonCount.index}"
														class="t-content-body-right-most-column quad">
														<div class="t-content-body-column-row-first">
															<div class="t-content-body-column-part1 ">
																<form:input path="rateDetails[${rowCount}].quadRate"
																	class="dtl_rate_validator" />
															</div>
															<div class="t-content-body-column-part2 ">
																<form:input
																	path="rateDetails[${rowCount}].quadSpecialRate"
																	class="dtl_rate_validator" />
															</div>
														</div>
													</div>
													<c:set var="rowCount" value="${rowCount+1}" />
												</c:if>
											</div>
										</c:forEach>

										<!-- marking the rows that are associated with a deleted season, as deleted -->
										<div>
											<c:forEach items="${rateHdr.rateDetails}" var="roomrateDtls"
												varStatus="count">
												<c:set var="flag" value="false" />
												<c:forTokens items="${activeRows}" delims=","
													var="detailsId">
													<c:if test="${roomrateDtls.seasonId == detailsId}">
														<c:set var="flag" value="true" />
													</c:if>
												</c:forTokens>
												<c:if test="${flag==false && roomrateDtls.seasonId!=0}">
													<form:hidden path="rateDetails[${count.index}].id" />
													<form:hidden path="rateDetails[${count.index}].seasonId" />
													<form:hidden path="rateDetails[${count.index}].singleRate" />
													<form:hidden
														path="rateDetails[${count.index}].singleSpecialRate" />
													<form:hidden path="rateDetails[${count.index}].doubleRate" />
													<form:hidden
														path="rateDetails[${count.index}].doubleSpecialRate" />
													<form:hidden path="rateDetails[${count.index}].tripleRate" />
													<form:hidden path="rateDetails[${count.index}].quadRate" />
													<form:hidden
														path="rateDetails[${count.index}].quadSpecialRate" />
													<form:hidden path="rateDetails[${count.index}].isDeleted"
														value="1" />
												</c:if>
											</c:forEach>
										</div>
										<!-- end marking the rows that are associated with a deleted season, as deleted -->

										<!-- body end  -->
									</div>
								</div>
								<label id="dtlsRateErrorId" class="error tabs-2"></label>
							</div>
						</div>

						<div id="revnShare" class="tab-pane">
							<div class="form-horizontal tasi-form">
								<!-- tab 3  content-->

								<div class="informatition_form_sub_title">
									<span class="title"><spring:message
											code="roomRate.label.revenueSharing.header" /></span>
								</div>
								<div class="d-table sidebar" id="revenue_table">
									<!--main header end  -->

									<!-- sub heading start  -->
									<div class="d-sub-header-body-row">
										<!-- sub heading  columns-->
										<div class="d-sub-header-body-left-most-column">
											<spring:message
												code="roomRate.label.revenueSharing.department" />
										</div>
										<div class="d-sub-header-body-column">
											<div class="d-sub-header-body-column-row-first">
												<spring:message code="roomRate.label.single_with_percentage" />
											</div>
										</div>

										<div class="d-sub-header-body-column">
											<div class="d-sub-header-body-column-row-first">
												<spring:message code="roomRate.label.double_with_percentage" />
											</div>
										</div>

										<div class="d-sub-header-body-column">
											<div class="d-sub-header-body-column-row-first">
												<spring:message code="roomRate.label.triple_with_percentage" />
											</div>
										</div>

										<div class="d-sub-header-body-column">
											<div class="d-sub-header-body-column-row-first">
												<spring:message code="roomRate.label.quad_with_percentage" />
											</div>
										</div>

										<div class="d-sub-header-body-right-most-column">
											<div class="d-sub-header-body-column-row-first">
												<button type="button"
													class="btn label label-info label-mini add_detail_btn"
													onclick="addRowInTable();" data-toggle="tooltip"
													data-placement="top" title="Add Row">
													<i class="fa fa-plus"></i>
												</button>
												<!-- <div class=" add_detail_btn btn btn-danger btn-xs" onclick="addRowInTable();"></div> -->
											</div>
										</div>
									</div>
									<!-- sub heading end  -->

									<!-- body start  -->
									<c:set var="rateDistributionrowCount"
										value="${rateDistributionListSize}" />
									<c:choose>
										<c:when test="${rateDistributionrowCount!=0}">
											<c:set var="index" value="0" />
											<c:forEach items="${rateHdr.rateDistribution}" var="item1"
												varStatus="count">
												<c:choose>
													<c:when test="${item1.isDeleted==false}">
														<div id="distribution_row${index}"
															class="d-content-body-row distribution_row c_row">
															<form:hidden path="rateDistribution[${count.index}].id"
																value="${item1.id}" />
															<div class="d-content-body-left-most-column">
																<form:select
																	path="rateDistribution[${count.index}].departmentId"
																	id="department_type_${index}"
																	cssClass="form-control m-bot15 validator ">
																	<form:options items="${departmentTypeListMap}" />
																</form:select>
															</div>
															<div id="d-single${index}"
																class="d-content-body-column single">
																<form:input
																	path="rateDistribution[${count.index}].singlePc" />
															</div>
															<div id="d-double${index}"
																class="d-content-body-column double">
																<form:input
																	path="rateDistribution[${count.index}].doublePc" />
															</div>
															<div id="d-triple${index}"
																class="d-content-body-column triple">
																<form:input
																	path="rateDistribution[${count.index}].triplePc" />
															</div>
															<div id="d-quad${index}"
																class="d-content-body-column quad">
																<form:input
																	path="rateDistribution[${count.index}].quadPc" />
															</div>
															<div class="d-content-body-right-most-column">
																<!-- <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o "></i></button> -->
																<button id="del${count.index}" type="button"
																	class="add_new tax_button delete_detail_btn btn btn-danger btn-xs"
																	onclick="deleteTableRow(this)" title="delete Row"
																	data-toggle="tooltip" data-placement="top">
																	<i class="fa fa-trash-o "></i>
																</button>
															</div>
														</div>
														<c:set var="index" value="${index+1}" />
													</c:when>
													<c:otherwise>
														<form:hidden path="rateDistribution[${count.index}].id"
															value="${item1.id}" />
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<div id="distribution_row0"
												class="d-content-body-row distribution_row n_row">

												<div class="d-content-body-left-most-column ">
													<form:select path="rateDistribution[0].departmentId"
														id="department_type_0"
														cssClass="form-control m-bot15 validator">
														<form:options items="${departmentTypeListMap}" />
													</form:select>
												</div>

												<div id="d-single0" class="d-content-body-column single">
													<form:input path="rateDistribution[0].singlePc"
														id="rateDistribution[0]_singlePc" />

												</div>
												<div id="d-double0" class="d-content-body-column double">
													<form:input path="rateDistribution[0].doublePc"
														id="rateDistribution[0]_doublePc" />

												</div>
												<div id="d-triple0" class="d-content-body-column triple">
													<form:input path="rateDistribution[0].triplePc"
														id="rateDistribution[0]_triplePc" />

												</div>
												<div id="d-quad0" class="d-content-body-column quad">
													<form:input path="rateDistribution[0].quadPc"
														id="rateDistribution[0]_quadPc" />

												</div>
												<div class="d-content-body-right-most-column">
													<button id="del0" type="button"
														class="add_new tax_button delete_detail_btn btn btn-danger btn-xs"
														onclick="deleteTableRow(this)" title=""
														data-toggle="tooltip" data-placement="top">
														<i class="fa fa-trash-o "></i>
													</button>
												</div>
											</div>
										</c:otherwise>
									</c:choose>
									<!-- body end  -->
								</div>
								<label id="totalPercentageErrorId" class="error tabs-3 "></label>
							</div>
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
