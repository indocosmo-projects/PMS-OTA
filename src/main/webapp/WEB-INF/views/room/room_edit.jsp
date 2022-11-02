<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Rooms" scope="request" />
<c:set var="moduleName" value="Rooms" scope="request" />
<c:set var="formId" value="room" scope="request" />
<c:set var="formName" value="room" scope="request" />
<c:set var="formAction" value="../room/save" scope="request" />
<c:set var="formCommandName" value="room" scope="request" />
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
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden path="id" id="masterId" />
			<input type="hidden" id="hdnNewRecord" name="newRecord" value="false">
			<input type="hidden" id="hdnRoomNo" value="${room.number}">
			<input type="hidden" id="hdnRoomid" value="${room.id}">
			<form:hidden path="encryption" id="encryption_id" />
			<form:hidden id="hdnInvStatus" path="invStatus" />
			<form:hidden id="hdnHkStatus" path="hkStatus" />
			<form:hidden id="hdnOccStatus" path="occStatus" />

			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<c:choose>
					<c:when test="${room.id==0}">
						<%-- 					 <c:when test="${(empty room.id) or (room.id == '')}"> 
 --%>
						<h4 class="modal-title">Add ${moduleName}</h4>
					</c:when>

					<c:otherwise>
						<h4 class="modal-title">Edit ${moduleName}</h4>
					</c:otherwise>
				</c:choose>

			</div>
			<div class="modal-body modal_body_tabs">
				<header class="panel-heading">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#general" data-toggle="tab"
							aria-expanded="true">General</a></li>
						<li class=""><a href="#room_status" data-toggle="tab"
							aria-expanded="false">Room Status</a></li>
						<li class=""><a href="#room_features" data-toggle="tab"
							aria-expanded="false">Room Features</a></li>
					</ul>
				</header>
				<div class="panel-body">
					<div class="tab-content">
						<div class="tab-pane active" id="general">
							<div class="form-horizontal tasi-form">
								<div class="form-group">
									<label class="control-label col-md-3"><spring:message
											code="rooms.label.roomNo" /> <span class="red">*</span></label>
									<div class="col-md-3 col-xs-11">
										<form:input id="txtNumber" path="number" type="text"
											maxlength="5"
											class="form-control form-control-inline input-medium validator refresh_room"
											value="" />
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtNumber_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="txtNumber_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="rooms.label.roomName" /></label>
									<div class="col-md-6 col-xs-11">
										<form:input id="txtName" path="name" maxlength="50"
											class="form-control form-control-inline input-medium refresh_room"
											size="16" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="rooms.label.description" /></label>
									<div class="col-md-6 col-xs-11">
										<form:textarea path="description" maxlength="150"
											class="form-control form-control-inline input-medium refresh_room"
											size="16" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="rooms.label.roomType" /> <span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:select id="ddlRoomType" path="roomTypeId"
											class="form-control input-sm m-bot15 validator">
											<form:option value="">--- Select ---</form:option>
											<form:options items="${roomTypes}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="ddlRoomType_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="ddlRoomType_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>

									<div id="roomTypeDetail" class="col-md-3 col-xs-11 details">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="rooms.label.floor" /> <span class="red">*</span></label>
									<div class="col-md-4 col-xs-11">
										<form:select id="floorList" path="floorId"
											class="form-control input-sm m-bot15 validator">
											<form:option value="">--- Select ---</form:option>
											<form:options items="${floorList}" />
										</form:select>
									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="ddlfloorList_check" style="display: none;"
											class="switch-right"><i class=" fa fa-check"></i></span> <span
											id="ddlfloorList_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>

									<div id="roomTypeDetail" class="col-md-3 col-xs-11 details">
									</div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="room_status">
							<div class="form-horizontal tasi-form">
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="rooms.label.inventoryStatus" /> <span class="red">*</span>
									</label>
									<div class="col-md-6 col-xs-11">
										<input type="text" id="txtInvStatus"
											class="form-control form-control-inline input-medium validator"
											size="16" readonly="readonly">
									</div>

									<div class="col-md-2 col-xs-11 tick_green align_close">
										<a class="btn btn-warning" id="btnChange" data-toggle="modal"><spring:message
												code="rooms.btn.change" /></a>
									</div>

									<div class="col-md-1 col-xs-11 tick_green">
										<span id="txtInvStatus_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="txtInvStatus_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>


									<!-- Modal -->
									<div class="modal fade" id="roomInvStatModal" tabindex="-1"
										role="dialog" aria-labelledby="myModalLabel"
										aria-hidden="true">
										<div class="modal-dialog modal-sm">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" id="btnRoomInvClose"
														aria-hidden="true">&times;</button>
													<h4 class="modal-title">Change Inventory Status</h4>
												</div>
												<div class="modal-body">
													<c:forEach var="invSt" items="${invStatus}"
														varStatus="stat">
														<div class="radio">
															<label> <input type="radio"
																id="chkInvStat${stat.index}" name="invStat"
																class="chk_inv_stat"
																data-text="${invSt.roomInventoryStatus}"
																value="${invSt.code}">
																${invSt.roomInventoryStatus}
															</label>
														</div>
													</c:forEach>
												</div>
												<div class="modal-footer">
													<button id="btnRoomInvStatOk" class="btn btn-success"
														type="button">Ok</button>
													<button id="btnRoomInvStatCancel" class="btn btn-default"
														type="button">Cancel</button>
												</div>
											</div>
										</div>
									</div>
									<!-- modal -->
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="rooms.label.housekeepingStatus" />
									</label>
									<div class="col-md-6 col-xs-11">
										<form:input path="roomHkStatusEnum" size="16"
											class="form-control form-control-inline input-medium"
											readonly="true" />
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> <spring:message
											code="rooms.label.occupancyStatus" />
									</label>
									<div class="col-md-6 col-xs-11">
										<form:input path="roomOccupancyStatusEnum" size="16"
											class="form-control form-control-inline input-medium"
											readonly="true" />
									</div>
								</div>
							</div>
						</div>
						<div class="tab-pane" id="room_features">
							<div class="form-horizontal tasi-form">
								<div class="directory-list weekly_special_day room_features ">
									<input type="hidden" id="hdnRoomFeatures"
										value="${room.roomFeaturesIds}" />
									<div id="divRoomFeatures"></div>
								</div>
							</div>
							<div id="divAddFeature" align="right" class="col-sm-12">
								<br> <a href="#roomFeatureModal" class="btn btn-success"
									id="lnkAddFeature" data-toggle="modal"> Add feature <%-- <spring:message code="rooms.link.addAFeature" /> --%>
								</a>

							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="save_btn" class="btn btn-success" type="submit"
					onclick="return save()">
					<spring:message code="pms.btn.submit"></spring:message>
				</button>
				<%-- <input id="refresh_btn" class="btn btn-warning" type="button"
					onclick="refreshData()"
					value="<spring:message code="pms.btn.reset"></spring:message>" /> --%>
				<input id="refresh_btn" class="btn btn-warning" type="reset"
					onclick="refreshData()"
					value="<spring:message code="pms.btn.reset"></spring:message>" />
				<button id="delete_btn" class="btn btn-danger" type="button"
					onclick="deleteData()">
					<i class="fa fa-trash-o "></i>
					<spring:message code="pms.btn.delete"></spring:message>
				</button>
				<button id="back_btn" data-dismiss="modal" class="btn btn-default"
					type="button">
					<spring:message code="pms.btn.cancel"></spring:message>
				</button>
			</div>
		</form:form>
	</div>
</div>
<!-- modal -->

<!-- Add Feature Modal -->
<div class="modal fade" id="roomFeatureModal" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<!-- <button type="button" class="close" id="btnRoomFeatureClose"
					aria-hidden="true">x</button> -->
				<h4 class="modal-title">Add Feature</h4>
			</div>
			<div class="modal-body">
				<div class="panel-body">

					<label class="col-md-12">Feature</label>
					<div class="form-group">
						<div class="col-md-11 col-xs-11">
							<input size="16" id="txtRoomFeature"
								class="form-control form-control-inline input-medium" /> <input
								id="hdnRoomFeatureId" type="hidden">
						</div>
						<div class="col-md-1 col-xs-11 tick_green">
							<span id="txtRoomFeature_check" style="display: none;"
								class="switch-right"><i class=" fa fa-check"></i></span> <span
								id="txtRoomFeature_warning" class="switch-right warning_red"
								style="display: none;"><i class="fa fa-warning"></i></span>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button id="btnSaveFeature" class="btn btn-success" type="button">Ok</button>
				<button id="btnCancelFeature" class="btn btn-default" type="button">Cancel</button>
				<button id="btnDeleteFeature" class="btn btn-danger" type="button">Delete</button>
			</div>
		</div>
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

<!-- Room feature already exist-->

<div class="modal fade" id="featureExist_modal" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">Alert</h4>
			</div>
			<div class="modal-body">
				<p>Feature already exists</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"
					id="featureExist_close">Close</button>
			</div>
		</div>
	</div>
</div>