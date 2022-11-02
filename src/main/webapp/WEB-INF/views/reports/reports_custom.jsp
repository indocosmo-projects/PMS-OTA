<jsp:directive.include file="../common/includes/page_directives.jsp" />
<div class="row">
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">

			<div class="panel-body">

				<div ng-repeat="rep in reports" class="report_custom">

					<div>
						<md-toolbar ng-if="reports[$index-1].category!=rep.category"
							class="md-theme-light">
						<div id="bgColour">
							<h2 class="md-toolbar-tools">

								<span>{{rep.cat_name}}</span>
							</h2>
						</div>
						</md-toolbar>
					</div>

					<div class="list_item">
						<md-content> <md-list> <md-list-item
							class="col-md-12" ng-click="showInput(rep);"> <a
							class="md-list-item-text">
							<h4>
								<i class="fa fa-file" aria-hidden="true"></i> {{rep.header}}
							</h4>
						</a> </md-list-item> </md-list> </md-content>
					</div>

				</div>
			</div>
		</section>
	</div>
</div>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="reportmyModal" class="modal fade">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">{{reportSelected.header}}</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">


				<div
					ng-if="reportSelected.id==1 || reportSelected.id==2 || reportSelected.id==3 || reportSelected.id==4 || reportSelected.id==5 || reportSelected.id==6|| reportSelected.id==7 || reportSelected.id==9 || reportSelected.id==14|| reportSelected.id==17 || reportSelected.id==24 || reportSelected.id==21 || reportSelected.id==28 || reportSelected.id==29 || reportSelected.id==30  || reportSelected.id==32 || reportSelected.id==33 || reportSelected.id==34|| reportSelected.id==35 || reportSelected.id==36 || reportSelected.id==37 || reportSelected.id == 31 || reportSelected.id == 40|| reportSelected.id == 41|| reportSelected.id == 42">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>
				
				
				<div
					ng-if="reportSelected.id == 43">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					</md-radio-group>
				</div>
				
				
				
				<!-- <div ng-if="reportSelected.id==5">
					<label>Print In-House List</label>
				</div> -->
				<!-- <!-- <div ng-if="reportSelected.id==6">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate">On Date</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="fordate">For Date</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<md-datepicker ng-model="rad.datefor" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					</md-radio-group>
				</div> -->
				<div ng-if="reportSelected.id==8">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>
				<div ng-if="reportSelected.id==27">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>
				<div ng-if="reportSelected.id==10">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-6 padding_new">
							<md-radio-button value="inhouse">IN-HOUSE</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-6 padding_new">
							<md-radio-button value="room">Room Number</md-radio-button>
						</div>
						<div class="col-md-6">
							<input ng-model="rad.room">
						</div>
					</div>
					</md-radio-group>
				</div>
				<div ng-if="reportSelected.id==12">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-5">
							<md-radio-button value="ondate">All Shifts on
							Date</md-radio-button>
						</div>
						<div class="col-md-7">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>
				<div ng-if="reportSelected.id==13">
					<div ng-model="rad.inputGroup">
						<div class="col-md-12">
							<div class="col-md-4 padding_new">
								<span value="ondate">Date</span>
							</div>
							<div class="col-md-8 padding_new">
								<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
						<div class="col-md-12">
							<div class="form-group">
								<label class="control-label col-md-4 padding_new"> Shift</label>
								<div class="col-md-6 col-xs-11">
									<select id="shiftsid" class="form-control input-sm"
										ng-options="x.id as x.code for x in ShiftsDetail"
										ng-model="OpenShift.Shifts">
									</select>
								</div>
							</div>
						</div>
						<div class="col-md-12">

							<div class="form-group">
								<label class="control-label col-md-4 padding_new">
									Cashier</label>
								<div class="col-md-6 col-xs-11 newsachier">
									<select id="shiftsid" class="form-control input-sm"
										ng-options="x.id as x.loginId for x in ShiftsCashier"
										ng-model="cashier.user">
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
			
				<div ng-if="reportSelected.id==15">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate">On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>

					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-md-4"> Shift </span></label>
							<div class="col-md-6 col-xs-11">
								<select id="shiftsid" class="form-control input-sm"
									ng-options="x.id as x.code for x in ShiftsDetail"
									ng-model="OpenShift.Shifts">
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-12">

						<div class="form-group">
							<label class="control-label col-md-4 "> Cashier </span></label>
							<div class="col-md-6 col-xs-11 newsachier">
								<select id="shiftsid" class="form-control input-sm"
									ng-options="x.id as x.loginId for x in ShiftsCashier"
									ng-model="cashier.user">
								</select>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>



				<div ng-if="reportSelected.id==16">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<!-- <div class="col-md-12">
						<div class="col-md-4">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"
									md-open-on-focus></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"
									md-open-on-focus></md-datepicker>
							</div>
						</div>
					</div> --> </md-radio-group>
				</div>

				<div ng-if="reportSelected.id==23">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<label class="control-label" value="room">Customer Name</label>
						</div>
						<div class="col-md-8">
							<input ng-model="rad.name" class="form-control">
						</div>
					</div>
					<div class="DateDiv">
						<div class="col-md-12">
							<div class="col-md-4 padding_new">
								<label value="datebtwn">Date Between</label>
							</div>
							<div class="col-md-8 padding_new">
								<div class="col-md-6">
									<div class="calenderdiv">
										<md-datepicker ng-model="rad.dateFrom"
											ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
									</div>
								</div>
								<div class="col-md-6">
									<div class="calenderdiv">
										<md-datepicker ng-model="rad.dateTo"
											md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
									</div>
								</div>

							</div>
						</div>
					</div>
					</md-radio-group>
				</div>

				<!-- - Room Request Report  -->


				<div ng-if="reportSelected.id==25 ">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<label value="datebtwn">Date Between</label>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>


				<!-- end room request report -->


				<div ng-if="reportSelected.id==18">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>

				<div ng-if="reportSelected.id==19">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>

				<div ng-if="reportSelected.id==20"  >
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-md-4 padding_new"> YEAR</label>
							<div class="col-md-6 col-xs-11">
								<select class="form-control input-sm" ng-model="getNumYear">
									<option value="">SELECT</option>
									<option ng-repeat="x in year" value="{{x}}">{{x}}</option>
								</select> <input id="yearList" value="{{getNumYear}}" type="hidden" />
							</div>
							<label class="control-label col-md-4 padding_new"> Month</label>
							<div class="col-md-6 col-xs-11">
								<select class="form-control input-sm" ng-model="getNumMonth"
									style="margin-top: 10px">
									<option value="">SELECT</option>
									<option ng-repeat="x in months" value="{{x.id}}">{{x.month}}</option>
								</select> <input id="monthList" value="{{getNumMonth}}" type="hidden" />
							</div>
						</div>
					</div>
				</div>
				
				
				
		
				
				
				
				<div ng-if="reportSelected.id==11 ">
					<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-md-4 padding_new"> YEAR</label>
							<div class="col-md-6 col-xs-11">
								<select class="form-control input-sm" ng-model="getNumYear">
									<option value="">SELECT</option>
									<option ng-repeat="x in year" value="{{x}}">{{x}}</option>
								</select> <input id="yearList" value="{{getNumYear}}" type="hidden" />
							</div>
							<label class="control-label col-md-4 padding_new"
								style="margin-top: 15px"> Month</label>
							<div class="col-md-6 col-xs-11">
								<select class="form-control input-sm" ng-model="getNumMonth"
									style="margin-top: 10px">
									<option value="">SELECT</option>
									<option ng-repeat="x in months" value="{{x.id}}">{{x.month}}</option>
								</select> <input id="monthList" value="{{getNumMonth}}" type="hidden" />
							</div>
						</div>
					</div>
				</div>

				<!-- <div ng-if="reportSelected.id==21">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div> -->

				<div ng-if="reportSelected.id==22" && ng-if="reportSelected.id == 28">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div>

				<div ng-if="reportSelected.id==26">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					<div class="col-md-12 div_rpt_outstanding_summary">
						<label class="col-md-4">Corporate</label>
						<div class="col-md-8">
							<select ng-model="crpId"
								class="form-control ctrl_rpt_outstanding_summary"
								ng-change="changeCorp(crpId)" required>
								<option ng-repeat="trvlCorp in corporates"
									value="{{trvlCorp.id}}">{{trvlCorp.name}}</option>
							</select>
						</div>
					</div>
					</md-radio-group>
				</div>
				
				
				<div ng-if="reportSelected.id==38 || reportSelected.id==39">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					
						<div class="col-md-12">
						<div class="form-group">
							<label class="control-label col-md-4"> REPORT TYPE </span></label>
							<div class="col-md-6 col-xs-11">
							
								<select ng-model="selectedRptId"
								class="form-control ctrl_rpt_outstanding_summary"
								ng-change="changeReportVal(selectedRptId)" required>
								<option ng-repeat="type in reportType"
									value="{{type.id}}">{{type.name}}</option>
							</select>
							
								<!-- <select id="shiftsid" class="form-control input-sm"
									ng-options="x.id as x.name for x in reportType"
									ng-model="selectedRptId" onChange="changeModelVal">
								</select> -->
							</div>
						</div>
					
				</div>
					
					
					
					</md-radio-group>
				</div>

				<div ng-if="reportSelected.id != 28 && reportSelected.id != 29  && reportSelected.id != 32 && reportSelected.id != 33 && reportSelected.id != 34 && reportSelected.id != 36"
					class="modal-footer border_design customer_outstanding">
					<!-- <button id="assignroomBtn" type="button" class="btn btn-success"
						ng-click="printReport()">Print</button> -->
					<button id="assignroomBtn" type="button" class="btn btn-success"
						ng-click="printReport(1)">PDF</button>
					<button id="assignroomBtn" type="button" class="btn btn-success"
						ng-click="printReport(2)">EXCEL</button>
					<button id="cancelExpDepartBtn" type="button"
						class="btn btn-default rbtnClose" ng-click="cancelReportPopUp();">
						<spring:message code="pms.btn.cancel" />
					</button>

				</div>


			 	<div ng-if="reportSelected.id == 28 || reportSelected.id == 32 || reportSelected.id == 33 || reportSelected.id == 34 || reportSelected.id ==36 "
					class="modal-footer border_design customer_outstanding">
					<button id="assignroomBtn" type="button" class="btn btn-success"
						ng-click="tallyExport()">EXPORT</button>
					<button id="cancelExpDepartBtn" type="button"
						class="btn btn-default rbtnClose" ng-click="cancelReportPopUp();">
						<spring:message code="pms.btn.cancel" />
					</button>

				</div> 
				
				
			<%-- 	<div ng-if="reportSelected.id == 32"
					class="modal-footer border_design customer_outstanding">
					<button id="assignroomBtn" type="button" class="btn btn-success"
						ng-click="contaExport()">EXPORT</button>
					<button id="cancelExpDepartBtn" type="button"
						class="btn btn-default rbtnClose" ng-click="cancelReportPopUp();">
						<spring:message code="pms.btn.cancel" />
					</button>

				</div> --%>
				
				<!-- petty -->
				<%-- <div ng-if="reportSelected.id == 31"
					class="modal-footer border_design customer_outstanding">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
					<button id="assignroomBtn" type="button" class="btn btn-success"
						ng-click="printReport(1)">PDF</button>
					<button id="cancelExpDepartBtn" type="button"
						class="btn btn-default rbtnClose" ng-click="cancelReportPopUp();">
						<spring:message code="pms.btn.cancel" />
					</button>

				</div> --%>

				<div ng-if="reportSelected.id==29"
					class="modal-footer border_design customer_outstanding">
					<button id="assignroomBtn" type="button" class="btn btn-success"
						ng-click="tallyExport()">EXPORT</button>
					<button id="cancelExpDepartBtn" type="button"
						class="btn btn-default rbtnClose" ng-click="cancelReportPopUp();">
						<spring:message code="pms.btn.cancel" />
					</button>

				</div>

                  <!-- <di v ng-if="reportSelected.id==31">
					<md-radio-group ng-model="rad.inputGroup">
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="today" class="md-primary">Today</md-radio-button>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="ondate"> On Date</md-radio-button>
						</div>
						<div class="col-md-8">
							<md-datepicker ng-model="rad.dateon" md-placeholder="Enter date"></md-datepicker>
						</div>
					</div>
					<div class="col-md-12">
						<div class="col-md-4 padding_new">
							<md-radio-button value="datebtwn">Date Between</md-radio-button>
						</div>
						<div class="col-md-8 padding_new">
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateFrom"
									ng-change="fromDateChange()" md-placeholder="Enter date"></md-datepicker>
							</div>
							<div class="col-md-6">
								<md-datepicker ng-model="rad.dateTo"
									md-min-date="initial.minDate" md-placeholder="Enter date"></md-datepicker>
							</div>
						</div>
					</div>
					</md-radio-group>
				</div> -->
			</div>
		</div>
	</div>
</div>