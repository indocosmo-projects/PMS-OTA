<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<input type="hidden" value="${imageDirectory}" id="imgDir" />
	<div class="panel-body">
		<div id="dataTableDiv" class="col-lg-12">
			<div>
				<div class="form-group align-btn-rgt topAddButtonDiv">
					<c:if test="${cp_isCanAdd}">

						<!-- <button type="button" class="btn btn-primary"  data-toggle="modal"
							data-target="#newroomtypeModal" ng-click="newRoomtype()">
							 <i class="fa fa-plus"></i>
						</button>
						<button type="button" class="btn btn-warning" onclick="refreshGrid()">
												<i class="fa fa-refresh"></i>
											</button> -->
						<button type="button" class="btn btn-primary"
							ng-click="newRoomtype()" data-target="#newroomtypeModal"
							id="btnShowAddModal">
							<i class="fa fa-plus"></i> NEW
						</button>
					</c:if>
				</div>
				<div class="cmn_tbl">
					<table id="entry-grid" datatable="" dt-options="dtOptions"
						dt-instance="dtInstance" dt-columns="dtColumns"
						style="width: 100%;" class="angDataTable table table-hover"></table>
				</div>
				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
					tabindex="-1" id="newProvidermyModal" class="modal fade">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
							<div class="modal-header">
								<button aria-hidden="true" data-dismiss="modal" class="close"
									type="button">x</button>
								<h4 class="modal-title">Room Type</h4>
							</div>
							<div class="modal-body md-inline-form" style="overflow-y: auto;">
								<md-content> <!-- <md-tabs md-dynamic-height md-border-bottom>
										 <md-tab>
											 <md-tab-label> Room Type </md-tab-label>
											 <md-tab-body>
											 	<md-content class="md-padding"> -->
								<div class="col-sm-12 raw_div">
									<label class="col-sm-3 label_padding"><spring:message
											code="pms.label.code" /><span class="red">*</span></label>
									<div class="col-sm-3">
										<input class="form-control input_margin" id="roomTypeCode"
											type="text" ng-model="roomType.code" ng-readonly="readOnly"
											required>

									</div>

									<label class="col-sm-3 label_padding"><spring:message
											code="pms.label.name" /><span class="red">*</span></label>
									<div class="col-sm-3">
										<input class="form-control input_margin refresh_roomtype"
											type="text" ng-model="roomType.name" required>
									</div>

								</div>
								<div class="col-sm-12 raw_div">
									<label class="col-sm-3 label_padding"><spring:message
											code="roomType.label.overBooking%" /></label>
									<div class="col-sm-3">
										<input class="form-control input_margin"
											ng-model="roomType.overBookingPrcntg" placeholder="0"
											ng-pattern="/^[0-9]$/">
									</div>

									<label class="col-sm-3 label_padding"><spring:message
											code="roomType.label.displayOrder" /></label>
									<div class="col-sm-3">
										<input class="form-control input_margin"
											ng-model="roomType.displayOrder" placeholder="0"
											ng-pattern="/^[0-9]$/">
									</div>
								</div>
								<div class="col-sm-12 raw_div">
									<label class="col-sm-3 label_padding"><spring:message
											code="rooms.label.description" /> </label>
									<div class="col-sm-9">
										<input class="form-control input_margin"
											ng-model="roomType.description">
									</div>
								</div>
								<div class=" col-sm-12 informatition_form_sub_title">
									<span class="title"><spring:message
											code="roomType.label.applicableOccupancies" />*</span>
								</div>
								<div class="directory-list weekly_special_day">
									<ul>
										<li><strong class="label_margin"><spring:message
													code="roomType.label.applicableOccupancies.single" /></strong> <md-checkbox
												class="occup" id="single" aria-label="single"
												ng-model="roomType.supportSingleOcc"></md-checkbox></li>
										<li><strong class="label_margin"><spring:message
													code="roomType.label.applicableOccupancies.double" /></strong> <md-checkbox
												class="occup" id="double" aria-label="double"
												ng-model="roomType.supportDoubleOcc"></md-checkbox></li>
										<li><strong class="label_margin"><spring:message
													code="roomType.label.applicableOccupancies.triple" /></strong> <md-checkbox
												class="occup" id="triple" aria-label="triple"
												ng-model="roomType.supportTripleOcc"></md-checkbox></li>
										<li><strong class="label_margin"><spring:message
													code="roomType.label.applicableOccupancies.quad" /></strong> <md-checkbox
												class="occup" id="quad" aria-label="quad"
												ng-model="roomType.supportQuadOcc"></md-checkbox></li>
									</ul>
								</div>
								<div layout-gt-xs="row" ng-show="editMode">
									<div flex="50">
										<label style="float: left">#<spring:message
												code="roomType.label.rooms" /></label>
										<div class="col-sm-6">
											<input class="form-control" ng-model="noOfRooms"
												placeholder="${noOfRooms}" disabled />
										</div>
									</div>
								</div>
								<!-- /md-content>
											 </md-tab-body>
										 </md-tab> --> <!-- <md-tab>
										 	<md-tab-label> Images </md-tab-label>
										 	<md-tab-body>
										 		<md-content class="md-padding">
										 			<form name="userForm1" method="post" enctype="multipart/form-data">
										 			<div col-sm-12>
										 				<div class="col-sm-6">
										 							<div id="img1div" data-provides="fileupload" class="fileupload fileupload-new">
													  			<input type="hidden"> 
													  				<div id="defaultImage1" style="width: 200px; height: 150px;" class="fileupload-new thumbnail">
																		<img alt="" src="{{roomType.image1}}">
																	</div>
																	<div id="imgshw1" style="max-width: 200px; max-height: 150px; line-height: 10px;"
																		class="fileupload-preview fileupload-exists thumbnail">
																		<img src="{{roomType.image1}}" />
																		</div>
																	<div>
																		<span class="btn btn-white btn-file" >
																			<span class="fileupload-new img1_upload_btn">
																				<i class="fa fa-paper-clip"></i> Select image</span> 
																			<span class="fileupload-exists img1_change_btn">
																				<i class="fa fa-undo"></i> Change</span> 
																				<input type="file" file-model="myFile1" name="image" value="" id="fileToUpload1" class="default" ng-disabled="disable_all">
																		</span>
																		<a data-dismiss="fileupload" class="btn btn-danger img1_change_btn fileupload-exists" ng-click="removeImage('img1')">
																		<i class="fa fa-trash"></i> Remove</a>
																	</div>
																</div>
															</div>
															<div class="col-sm-6">
																	<div id="img2div" data-provides="fileupload" class="fileupload fileupload-new">
																	<input type="hidden">
																	<div id="defaultImage2" style="width: 200px; height: 150px;" class="fileupload-new thumbnail">
																		<img alt="" src="{{roomType.image2}}">
																	</div>
																	<div id="imgshw2" style="max-width: 200px; max-height: 150px; line-height: 10px;"class="fileupload-preview fileupload-exists thumbnail"></div>
																	<div>
																		<span class="btn btn-white btn-file"> 
																			<span class="fileupload-new img2_upload_btn"><i class="fa fa-paper-clip"></i>
																				Select image</span>
																			<span class="fileupload-exists img2_change_btn"><i class="fa fa-undo"></i> Change</span> 
																			<input type="file" file-model="myFile2" name="image" value="" id="fileToUpload2" class="default">
																		</span> 
																		<a data-dismiss="fileupload" class="btn btn-danger img2_change_btn fileupload-exists" ng-click="removeImage('img2')">
																			<i class="fa fa-trash"></i> Remove</a>
																	</div>
																</div>
															</div>
															<div class="col-sm-6">
																		<div id="img3div" data-provides="fileupload" class="fileupload fileupload-new">
																	<input type="hidden">
																	<div id="defaultImage3" style="width: 200px; height: 150px;" class="fileupload-new thumbnail">
																		<img alt="" src="{{roomType.image3}}">
																	</div>
																	<div id="imgshw3" style="max-width: 200px; max-height: 150px; line-height: 10px;"
																		class="fileupload-preview fileupload-exists thumbnail"></div>
																	<div>
																		<span class="btn btn-white btn-file">
																			<span class="fileupload-new img3_upload_btn"><i class="fa fa-paper-clip"></i>
																				Select image</span> 
																			<span class="fileupload-exists img3_change_btn"><i class="fa fa-undo"></i> Change</span> 
																			<input type="file" file-model="myFile3" name="image" value="" id="fileToUpload3" class="default">
																		</span> 
																		<a data-dismiss="fileupload" class="btn btn-danger img3_change_btn fileupload-exists" ng-click="removeImage('img3')"><i class="fa fa-trash"></i> Remove</a>
																	</div>
																</div>
															</div>
															<div class="col-sm-6">
																		<div id="img4div" data-provides="fileupload" class="fileupload fileupload-new">
																<input type="hidden">
																	<div id="defaultImage4" style="width: 200px; height: 150px;" class="fileupload-new thumbnail">
																		<img alt="" src="{{roomType.image4}}">
																	</div>
																	<div id="imgshw4"
																		style="max-width: 200px; max-height: 150px; line-height: 10px;" class="fileupload-preview fileupload-exists thumbnail">
																		<img src="{{roomType.image4}}" />
																	</div>
																	<div>
																		<span class="btn btn-white btn-file"> 
																			<span class="fileupload-new img4_upload_btn"><i class="fa fa-paper-clip"></i> Select image</span> 
																			<span class="fileupload-exists img4_change_btn"><i class="fa fa-undo"></i> Change</span> 
																			<input type="file" file-model="myFile4" name="image" value="" id="fileToUpload4" class="default">
														                </span> 
														                <a data-dismiss="fileupload" class="btn btn-danger img4_change_btn fileupload-exists" ng-click="removeImage('img4')"><i class="fa fa-trash" ></i> Remove</a>
																	</div>
																</div>
															</div>
										 			</div>
														<div>
															 <div class="col-sm-6">
															<div id="img1div" data-provides="fileupload" class="fileupload fileupload-new">
													  			<input type="hidden"> 
													  				<div id="defaultImage1" style="width: 200px; height: 150px;" class="fileupload-new thumbnail">
																		<img alt="" src="{{roomType.image1}}">
																	</div>
																	<div id="imgshw1" style="max-width: 200px; max-height: 150px; line-height: 10px;"
																		class="fileupload-preview fileupload-exists thumbnail">
																		<img src="{{roomType.image1}}" />
																		</div>
																	<div>
																		<span class="btn btn-white btn-file" >
																			<span class="fileupload-new img1_upload_btn">
																				<i class="fa fa-paper-clip"></i> Select image</span> 
																			<span class="fileupload-exists img1_change_btn">
																				<i class="fa fa-undo"></i> Change</span> 
																				<input type="file" file-model="myFile1" name="image" value="" id="fileToUpload1" class="default" ng-disabled="disable_all">
																		</span>
																		<a data-dismiss="fileupload" class="btn btn-danger img1_change_btn fileupload-exists" ng-click="removeImage('img1')">
																		<i class="fa fa-trash"></i> Remove</a>
																	</div>
																</div>
															</div>
															<div class=col-sm-6">
																<div id="img2div" data-provides="fileupload" class="fileupload fileupload-new">
																	<input type="hidden">
																	<div id="defaultImage2" style="width: 200px; height: 150px;" class="fileupload-new thumbnail">
																		<img alt="" src="{{roomType.image2}}">
																	</div>
																	<div id="imgshw2" style="max-width: 200px; max-height: 150px; line-height: 10px;"class="fileupload-preview fileupload-exists thumbnail"></div>
																	<div>
																		<span class="btn btn-white btn-file"> 
																			<span class="fileupload-new img2_upload_btn"><i class="fa fa-paper-clip"></i>
																				Select image</span>
																			<span class="fileupload-exists img2_change_btn"><i class="fa fa-undo"></i> Change</span> 
																			<input type="file" file-model="myFile2" name="image" value="" id="fileToUpload2" class="default">
																		</span> 
																		<a data-dismiss="fileupload" class="btn btn-danger img2_change_btn fileupload-exists" ng-click="removeImage('img2')">
																			<i class="fa fa-trash"></i> Remove</a>
																	</div>
																</div>
															</div> 
															<div class="col-sm-6">
																<div id="img3div" data-provides="fileupload" class="fileupload fileupload-new">
																	<input type="hidden">
																	<div id="defaultImage3" style="width: 200px; height: 150px;" class="fileupload-new thumbnail">
																		<img alt="" src="{{roomType.image3}}">
																	</div>
																	<div id="imgshw3" style="max-width: 200px; max-height: 150px; line-height: 10px;"
																		class="fileupload-preview fileupload-exists thumbnail"></div>
																	<div>
																		<span class="btn btn-white btn-file">
																			<span class="fileupload-new img3_upload_btn"><i class="fa fa-paper-clip"></i>
																				Select image</span> 
																			<span class="fileupload-exists img3_change_btn"><i class="fa fa-undo"></i> Change</span> 
																			<input type="file" file-model="myFile3" name="image" value="" id="fileToUpload3" class="default">
																		</span> 
																		<a data-dismiss="fileupload" class="btn btn-danger img3_change_btn fileupload-exists" ng-click="removeImage('img3')"><i class="fa fa-trash"></i> Remove</a>
																	</div>
																</div>
															</div>
															<div class="col-sm-6">
																<div id="img4div" data-provides="fileupload" class="fileupload fileupload-new">
																<input type="hidden">
																	<div id="defaultImage4" style="width: 200px; height: 150px;" class="fileupload-new thumbnail">
																		<img alt="" src="{{roomType.image4}}">
																	</div>
																	<div id="imgshw4"
																		style="max-width: 200px; max-height: 150px; line-height: 10px;" class="fileupload-preview fileupload-exists thumbnail">
																		<img src="{{roomType.image4}}" />
																	</div>
																	<div>
																		<span class="btn btn-white btn-file"> 
																			<span class="fileupload-new img4_upload_btn"><i class="fa fa-paper-clip"></i> Select image</span> 
																			<span class="fileupload-exists img4_change_btn"><i class="fa fa-undo"></i> Change</span> 
																			<input type="file" file-model="myFile4" name="image" value="" id="fileToUpload4" class="default">
														                </span> 
														                <a data-dismiss="fileupload" class="btn btn-danger img4_change_btn fileupload-exists" ng-click="removeImage('img4')"><i class="fa fa-trash" ></i> Remove</a>
																	</div>
																</div>
															</div>
														</div>
													</form>
												</md-content>	
										 	</md-tab-body>
										 </md-tab>
								 	 </md-tabs>--> </md-content>
							</div>
							<div class="modal-footer">
								<input type="hidden" id="baction" value="save"
									ng-model="roomType.baction" />
								<button id="saveRoomTypeBtn" type="button"
									class="btn btn-success" ng-click="save()">
									<spring:message code="pms.btn.save" />
								</button>
								<input id="refresh_btn" class="btn btn-warning" type="button"
									ng-click="refreshData()"
									value="<spring:message code="pms.btn.reset"></spring:message>" />
								<c:if test="${cp_isCanDelete}">
									<button id="deleteRoomTypeBtn" type="button"
										class="btn btn-danger" ng-click="deleteRoomType()">
										<spring:message code="pms.btn.delete" />
									</button>
								</c:if>
								<button id="cancelRoomType" type="button"
									class="btn btn-default" data-dismiss="modal">
									<spring:message code="pms.btn.cancel" />
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
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
