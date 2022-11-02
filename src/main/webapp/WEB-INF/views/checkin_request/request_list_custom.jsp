<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<div class="panel-body">
		<div id="dataTableDiv" class="col-lg-12">
			<div>
				<div class="searchDiv">
					<label class="col-md-2">Room Number</label>
					<div class="col-md-4">
						<md-autocomplete ng-disabled="ctrl.isDisabled"
							md-no-cache="ctrl.noCache" md-selected-item="ctrl.selectedItem"
							md-search-text-change="ctrl.searchTextChange(ctrl.searchText)"
							md-search-text="ctrl.searchText"
							md-selected-item-change="ctrl.selectedItemChange(item)"
							md-items="item in ctrl.querySearch(ctrl.searchText)"
							md-item-text="item.room_number" md-min-length="0"
							placeholder="Pick a room number to search..."
							md-menu-class="autocomplete-custom-template"> <md-item-template>
						<span class="item-title"> <md-icon
								md-svg-icon="img/icons/octicon-repo.svg"></md-icon> <i
							class="fa fa-bed" aria-hidden="true"></i> <strong
							class="room_num"> {{item.room_number}} </strong>
						</span> <span class="item-metadata"> <span> <i
								class="fa fa-user" aria-hidden="true"></i> <span>{{item.first_name}}</span>
						</span> <span> <i class="fa fa-mobile" aria-hidden="true"></i> <span>{{item.phone}}</span>
						</span>
						</span> </md-item-template> </md-autocomplete>
					</div>
					<div class="col-md-6">
						<div class="col-md-4 room_dtl"
							ng-if="selectedObj.room_number!=null">
							<i class="fa fa-bed" aria-hidden="true"></i>
							{{selectedObj.room_number}}
						</div>
						<div class="col-md-4 room_dtl"
							ng-if="selectedObj.first_name!=null">
							<i class="fa fa-user" aria-hidden="true"></i>
							{{selectedObj.first_name}}
						</div>
						<div class="col-md-4 room_dtl" ng-if="selectedObj.phone!=null">
							<i class="fa fa-mobile" aria-hidden="true"></i>
							{{selectedObj.phone}}
						</div>
					</div>
				</div>
				<div class="form-group align-btn-rgt topAddButtonDiv"
					ng-if="${cp_isCanAdd}">

					<button type="button" class="btn btn-warning" ng-click="newAddon()">
						New Request <i class="fa fa-plus"></i>
					</button>
				</div>
				<div class="tableDiv">
					<table id="entry-grid" datatable="" dt-options="ctrl.dtOptions"
						dt-instance="ctrl.dtInstance" dt-columns="ctrl.dtColumns"
						style="width: 100%;" class="angDataTable table table-hover"></table>
				</div>

				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
					tabindex="-1" id="requestmyModal" class="modal fade">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button aria-hidden="true" data-dismiss="modal" class="close"
									type="button">�</button>
								<h4 class="modal-title">Edit Addon</h4>
							</div>
							<div class="modal-body" style="overflow-y: auto;">
								<!-- modal data comes here -->
								<div class="form-horizontal tasi-form">
									<div class="row mod_desc">
										<div class="col-md-3">
											<label>Facility</label><span class="row">{{requestEditData.facility_code}}</span>
										</div>
										<div class="col-md-3">
											<!-- 										requestType
 -->
											<label>One Time Request</label><span class="row">{{requestEditData.chreq_is_one_time_request}}</span>
										</div>
										<div class="col-md-3">
											<label>Payment</label><span class="row">{{(requestEditData.facility_is_payable)?'Payable':'Free'}}</span>
										</div>
										<div class="col-md-3">
											<label>Amount</label><span class="row">{{(requestEditData.facility_is_payable)?
												requestEditData.facility_amount:'Free'}}<label
												ng-if="requestEditData.facility_is_payable">${currencySymbol}</label>
											</span>
										</div>
									</div>
									<div class="row mod_edit_time">
										<div class="col-md-6">
											<label class="col-md-4">Request Time</label>
											<div class="col-md-8">
												<div uib-timepicker
													ng-model="requestEditData.chreq_req_time"
													class="facility_timePick"
													ng-disabled="requestEditData.is_req_completed"
													ng-change="changed()" hour-step="hstep" minute-step="mstep"
													show-meridian="ismeridian"></div>
											</div>
										</div>

										<div class="col-md-6"
											ng-if="requestEditData.chreq_is_one_time_request">
											<label class="col-md-4">Request Date</label>
											<div class="col-md-8">
												<md-datepicker ng-model="requestEditData.chreq_req_date"
													md-placeholder="Enter date" md-open-on-focus></md-datepicker>
											</div>

										</div>
									</div>
									<div class="row mod_inactive"
										ng-if="!requestEditData.chreq_is_one_time_request">
										<md-subheader class="md-primary">Choose Dates
										to set add-on inactive</md-subheader>
										<div class="col-md-8">
											<md-input-container> <label>From
												Date</label> <md-datepicker ng-model="inactive.fromDate"
												md-min-date="inactive.minFromDate"
												md-max-date="selectedObj.exp_depart_date" md-open-on-focus></md-datepicker>
											</md-input-container>
											<md-input-container> <label>To
												Date</label> <md-datepicker ng-model="inactive.toDate"
												md-min-date="inactive.minToDate"
												md-max-date="selectedObj.exp_depart_date" md-open-on-focus></md-datepicker>
											</md-input-container>
											<md-button class="md-raised md-primary"
												ng-click="addInactiveDate()">Add</md-button>
										</div>
										<div class="col-md-4">
											<div class="inactive_dates_div">
												<md-content style="max-height: 200px;" md-theme="altTheme">

												<section>
													<md-subheader class="md-primary">Inactive
													Dates</md-subheader>
													<md-list layout-padding> <md-list-item
														class="md-3-line" ng-repeat="(pdate,stat) in processList"
														ng-if="!stat.is_deleted"> <span><i
														class="fa fa-calendar-o" aria-hidden="true"></i> {{pdate}}
													</span> <a ng-click="deleteInactiveDate(pdate)" class="stat_del"><i
														class="fa fa-trash" aria-hidden="true"></i></a> </md-list-item> </md-list>
												</section>
											</div>
										</div>
									</div>
									<!-- modal data comes here -->
								</div>
								<div class="modal-footer">
									<input type="hidden" id="baction" value="save"
										ng-model="facility.baction" />
									<button id="cancelProvider" type="button"
										class="btn btn-default rbtnClose" ng-click="cancelPopUp();">
										<spring:message code="pms.btn.cancel" />
									</button>
									<button id="saveFacilityBtn" type="button"
										class="btn btn-success" ng-click="save()">
										<spring:message code="pms.btn.save" />
									</button>

									<button id="deleteProviderBtn" type="button"
										class="btn btn-warning" ng-click="cancelAddon()">
										<spring:message code="pms.btn.delete" />
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>


				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
					tabindex="-1" id="requestProcessmyModal" class="modal fade">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button aria-hidden="true" data-dismiss="modal" class="close"
									type="button">�</button>
								<h4 class="modal-title">Process Addon</h4>
							</div>
							<div class="modal-body" style="overflow-y: auto;">
								<div class="mb-container">

									<div class="row processHdr">
										<div class="proc_box_div">
											<span class="contacts-title">Room</span>
											<p>{{reqToProcess.room_number}}</p>
										</div>
										<div class="proc_box_div">
											<span class="contacts-title">Request</span>
											<p>{{reqToProcess.facility_name}}</p>
										</div>
										<div class="proc_box_div">
											<span class="contacts-title">Time</span>
											<p>{{reqToProcess.chreq_req_time | date:'h:mm:ss a'}}</p>
										</div>
										<div class="proc_box_div">
											<span class="contacts-title">Remarks</span>
											<p>{{reqToProcess.chreq_req_remarks}}</p>
										</div>
										<div class="proc_box_div">
											<span class="contacts-title">Payment</span>
											<p>{{reqToProcess.payment}}
												{{reqToProcess.facility_is_payable ? '('+
												reqToProcess.facility_amount+ ' ${currencySymbol} )': ''}}</p>
										</div>
										<div class="proc_box_div">
											<span class="contacts-title">Status</span>
											<p>{{reqToProcess.process_status ? 'Processed' :
												(reqToProcess.setAsInactive?'Inactive Today':'Not
												Processed')}}</p>
										</div>
										<div class="proc_box_div">
											<span class="contacts-title">Request Status</span>
											<p>{{reqToProcess.is_req_completed ? 'Completed' : 'Not
												Completed'}}</p>
										</div>
									</div>
									<div class="row processFooter">

										<div ng-if="reqToProcess.canProcess">

											<div class="col-md-4">
												<md-input-container class="md-block" flex-gt-sm>
												<label>Remarks</label> <input ng-model="process.remarks">
												</md-input-container>
											</div>
										</div>
										<md-button ng-if="reqToProcess.canProcess"
											class="md-warn md-raised col-md-4" ng-click="processSave()">Process
										Request</md-button>
										<md-button class="md-primary md-raised pull-right"
											ng-click="closeProcessModal()">Close</md-button>

									</div>
								</div>
							</div>
						</div>
					</div>
				</div>


				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
					tabindex="-1" id="requestAddNewmyModal" class="modal fade">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button aria-hidden="true" data-dismiss="modal" class="close"
									type="button">�</button>
								<h4 class="modal-title">New Request</h4>
							</div>
							<div class="modal-body" style="overflow-y: auto;">
								<div class="facilityDiv">
									<md-content class="md-padding roomTab"> <md-content
										style="height: 300px;"> <c:forEach
										var="fcltyTypes" items="${facilityTypes}">
										<section>
											<md-subheader class="md-primary">${fcltyTypes.name}</md-subheader>
											<div class="facilityList"
												ng-repeat="facility in availableFacilities"
												ng-if="facility.facilityType == ${fcltyTypes.code}">
												<div class="col-md-3 fcchkBox">
													<md-checkbox ng-model="facilitySelected[facility.id]">{{facility.name}}</md-checkbox>
												</div>
												<div class="row col-md-9">
													<div class="row">
														<div class="col-md-8">
															<md-radio-group ng-model="facility.need"
																ng-disabled="!facilitySelected[facility.id]">
															<md-radio-button value="daily" class="md-primary">Daily</md-radio-button>
															<md-radio-button value="ondate"> On
															Date </md-radio-button> </md-radio-group>
															<md-datepicker ng-model="facility.needDate"
																ng-if="facility.need=='ondate'"
																ng-disabled="!facilitySelected[facility.id]"
																md-placeholder="Enter date"></md-datepicker>
														</div>
														<div class="col-md-3">
															<div uib-timepicker ng-model="facility.needTime"
																class="facility_timePick"
																ng-disabled="!facilitySelected[facility.id]"
																ng-change="changed()" hour-step="hstep"
																minute-step="mstep" show-meridian="ismeridian"></div>
														</div>
													</div>


													<div class="row">
														<div class="col-md-6">
															<md-input-container class="facilInput" flex="70">
															<label>Provider</label> <md-select
																ng-disabled="!facilitySelected[facility.id]"
																ng-model="facility.provider"> <md-option
																ng-repeat="pvdr in providers" value="{{pvdr.id}}">{{pvdr.name}}</md-option></md-select>
															</md-input-container>
														</div>
														<div class="col-md-6">
															<md-input-container class="md-block facilInput"
																flex-gt-sm> <label>Remarks</label>
															<input ng-disabled="!facilitySelected[facility.id]"
																ng-model="facility.remarks"> </md-input-container>
														</div>
													</div>
												</div>
												<md-divider ng-if="!$last"></md-divider>
											</div>
										</section>

									</c:forEach> </md-content> </md-content>
								</div>
							</div>
							<div class="modal-footer">
								<button id="contExpDepartBtn" type="button"
									class="btn btn-success" ng-click="saveAddon()">Add</button>
								<button id="cancelExpDepartBtn" type="button"
									class="btn btn-default rbtnClose"
									ng-click="cancelAddNewPopUp();">
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