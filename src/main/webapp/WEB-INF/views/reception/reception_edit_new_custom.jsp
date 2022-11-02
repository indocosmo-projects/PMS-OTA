<jsp:directive.include file="../common/includes/page_directives.jsp" />
<script>
$(function(){
    var dtToday = new Date();
    
    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();
    
    var maxDate = year + '-' + month + '-' + day;

    // or instead:
    // var maxDate = dtToday.toISOString().substr(0, 10);

    //alert(maxDate);
    $('#txtDate').attr('min', maxDate);
});
</script>
<input type="hidden" id="checkNo" value="${checkInNo}" />
<input type="hidden" id="status" value="${status}" />
<input type="hidden" id="dateFormat" value="${dateFormat}">
<input type="hidden" id="hotelDate" value="${hotelDate}" />
<!-- start summary section -->

<div class="row" id="summary">
	<form name="hdrForm" ng-submit="hdrForm.$valid && save()">
		<div class="col-lg-12">
			<section class="panel">
				<%-- <header class="panel-heading module_caption">
					<h1>
						<spring:message code="reservation.label.deposit.summary" />
					</h1>
					<span class="tools pull-right"> <a
						class="fa fa-chevron-down" href="javascript:;"></a>
					</span>
				</header> --%>
				<div class="panel-body">
					<div class="dtls summury">
						<div class="col-md-3 col-sm-3 s-col">
							<div class="summury_div">
								<div class="col-md-12 s-header">
									<spring:message code="status.label.checkIn" />
								</div>
								<div id="divCheckInNo" class="col-md-12 s-body">
									<label id="ckeckInNoLabel">{{checkin.hdr.checkInNo}}</label>
								</div>
							</div>
						</div>
						<div class="col-md-3 col-sm-3 s-col">
							<div class="summury_div">
								<div class="col-md-12 s-header">
									<spring:message code="reservation.label.deposit.arivalDate" />
								</div>
								<div id="divBookingDate" class="col-md-12 s-body">
									<label id="arrivalDate">{{arrDate | date:dateFormat}}</label>
								</div>
							</div>
						</div>
						<div class="col-md-3 col-sm-3 s-col">
							<div class="summury_div">
								<div class="col-md-12 s-header">
									<spring:message code="reservation.label.folioBalance" />
								</div>
								<div id="divFolioBal" class="col-md-12 s-body">
									<label id="folioBal">{{folioBal}}</label>
								</div>
							</div>
						</div>
						<div class="col-md-3 col-sm-3 s-col">
							<div class="summury_div">
								<div class="col-md-12 s-header">
									<spring:message code="reservation.label.deposit.departDate" />
								</div>
								<div id="divDepartDate" class="col-md-12 s-body">
									<label id="DepartDate">{{expDepartDate |
										date:dateFormat}}</label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<section class="panel">
				<header class="panel-heading module_caption">
					<h1>
						<spring:message code="reservation.label.guestInfo" />
					</h1>

				</header>





				<div class="panel-body">
					<div class="col-md-9">
						<div cass="row">
							<div class="new_chk_in_lft_div_tbl">
								<div class="new_chk_in_lft_div_tbl_full_width_row">
									<div class="salutation_div">
										<label class="new_chk_in_lft_div_tbl_label">&nbsp;</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container> <md-select
														class="md_slect_div_edit"
														ng-model="checkin.dtl.salutation" required> <md-option
														ng-repeat="salutation in names" ng-value="salutation">{{salutation}}</md-option>
													</md-select> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="first_name">
										<label class="new_chk_in_lft_div_tbl_label">First name<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input required onclick="this.focus();this.select()"
														ng-model="checkin.dtl.firstName" maxlength="50"> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="last_name">
										<label class="new_chk_in_lft_div_tbl_label">Last name</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input onclick="this.focus();this.select()"
														ng-model="checkin.dtl.lastName" maxlength="50"> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="gust_name">
										<label class="new_chk_in_lft_div_tbl_label">Guest name</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input onclick="this.focus();this.select()"
														ng-model="checkin.dtl.guestName" maxlength="50"> </md-input-container>
												</div>
											</div>
										</div>
									</div>

								</div>
								
								
									<div class="new_chk_in_lft_div_tbl_full_width_row">
									<!-- for niko  -->
									<div class="email_div">
										<label class="new_chk_in_lft_div_tbl_label">
										
													
														<a ng-init="imgsrc={
   																   src: 'checkin.dtl.customerIdProof',
     																 show: false,
  																  };">
  														  <span
     														     ng-mouseover="imgsrc.show = true"
       															  ng-mouseout="imgsrc.show = false">
       															   Customer Image
   														</span>
   														 <img class = "imageCl" ng-src="{{checkin.dtl.customerImage}}" ng-show="imgsrc.show"/>
   														 </a>
														
												</label>	
												</div>
											
									
									
									<div class="phone_div">
										<label class="new_chk_in_lft_div_tbl_label">
										
										<a ng-init="imgsrc1={
   																   src: 'checkin.dtl.customerIdProof',
     																 show: false,
  																  };">
  														  <span
     														     ng-mouseover="imgsrc1.show = true"
       															  ng-mouseout="imgsrc1.show = false">
       															   Customer ID Proof
   														</span>
   														 <img class = "imageCl" ng-src="{{checkin.dtl.customerIdProof}}" ng-show="imgsrc1.show"/>
   														 </a>
   														 </label>
									</div>
								</div>
								
								


								<div class="new_chk_in_lft_div_tbl_full_width_row">
									<!-- for niko  -->
									<div class="email_div">
										<label class="new_chk_in_lft_div_tbl_label">Email</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input ng-model="checkin.dtl.email" type="text"
														onclick="this.focus();this.select()" type="email" 
														pattern="^[_\.0-9a-z-]+@([0-9a-z][0-9a-z-]+)+((\.)[a-z]{2,})+$">
													</md-input-container>
												</div>
											</div>
										</div>
									</div> 
									<!-- for Bahrain -->
									<!-- <div class="email_div">
										<label class="new_chk_in_lft_div_tbl_label">Email</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input ng-model="checkin.dtl.email" type="text"
														onclick="this.focus();this.select()" type="email" 
														pattern="^[_\.0-9a-z-]+@([0-9a-z][0-9a-z-]+)+((\.)[a-z]{2,})+$">
													</md-input-container>
												</div>
											</div>
										</div>
									</div> -->
									
									<div class="phone_div">
										<label class="new_chk_in_lft_div_tbl_label">Phone<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<!-- <input ng-model="checkin.dtl.phone" maxlength="15"
														name="phone" 
														required> -->
													<input ng-model="checkin.dtl.phone" maxlength="15"
														name="phone" oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" 
														required>	
														 </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="addree_div">
										<label class="new_chk_in_lft_div_tbl_label">Address<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input ng-model="checkin.dtl.address"
														onclick="this.focus();this.select()" maxlength="200"
														required> </md-input-container>
												</div>
											</div>
										</div>
									</div>


								</div>


								<div class="new_chk_in_lft_div_tbl_full_width_row">
									<div class="email_div">
										<label class="new_chk_in_lft_div_tbl_label">Country<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container> <md-select
														class="md_slect_div_edit"
														ng-model="checkin.dtl.nationality" placeholder="country"
														ng-change="loadState(checkin.dtl.nationality)" required>
													<md-option ng-repeat="country in countryList "
														ng-value="country.name">{{country.name}}</md-option> </md-select> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="phone_div">
										<label class="new_chk_in_lft_div_tbl_label">State<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container>
													<md-select class="md_slect_div_edit"
														ng-model="checkin.dtl.state" placeholder="state" required>
													<md-option ng-repeat="state in stateList"
														ng-value="state.name">{{state.name}}</md-option> </md-select> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="addree_div">
										<label class="new_chk_in_lft_div_tbl_label">GST</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input ng-model="checkin.dtl.gstno"
														onclick="this.focus();this.select()"> </md-input-container>
												</div>
											</div>
										</div>
									</div>


								</div>




								<div class="new_chk_in_lft_div_tbl_full_width_row">

									<div class="adults_div">
										<label class="new_chk_in_lft_div_tbl_label">Adults<span
											class="red">*</span></label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="searchFormInput" flex="50">
													<input required type="number" step="any"
														ng-disabled="${status}!= 5"
														ng-model="checkin.hdr.numAdults" min="1" limit-to="200" />
													</md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="children_div">
										<label class="new_chk_in_lft_div_tbl_label" id="childMar">Children</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="searchFormInput" id="marChild"
														flex="50"> <input type="number"
														step="any" ng-disabled="${status}!= 5"
														ng-model="checkin.hdr.numChildren" min="0" max="200"
														limit-zero="200" /> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="Infant_div">
										<label class="new_chk_in_lft_div_tbl_label" id="infantMar">Infants</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="searchFormInput" id="marInfant"
														flex="50"> <input type="number"
														step="any" ng-disabled="${status}!= 5"
														ng-model="checkin.hdr.numInfants" min="0" max="200"
														limit-zero="200" /> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="gender_div" id="genderMar">
										<label class="new_chk_in_lft_div_tbl_label">Gender</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">

												<md-input-container class="md-block chInp" flex-gt-sm>
												<md-radio-group ng-model="checkin.dtl.gender" required>
												<div class="col-md-6">
													<md-radio-button value="Male" class="md-primary">Male</md-radio-button>
												</div>
												<div class="col-md-6">
													<md-radio-button value="Female">Female</md-radio-button>
												</div>
												</md-radio-group> </md-input-container>
											</div>
										</div>
									</div>


								</div>


								<div class="new_chk_in_lft_div_tbl_full_width_row">
									<div class="email_div">
										<label class="new_chk_in_lft_div_tbl_label">Remarks</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input ng-model="checkin.dtl.remarks"
														onclick="this.focus();this.select()"> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="phone_div">
										<label class="new_chk_in_lft_div_tbl_label">ID
											</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input ng-model="checkin.dtl.passportNo"
														onclick="this.focus();this.select()"></md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="addree_div">
										<label class="new_chk_in_lft_div_tbl_label">Expiry</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<md-datepicker id="datePicker" readonly
														ng-required="checkin.dtl.passportNo!='' && checkin.dtl.passportNo!=undefined"
														ng-model="checkin.dtl.passportExpiryOn"
														md-min-date="currentDate"></md-datepicker> </md-input-container>
												</div>
											</div>
										</div>
									</div>
									<div class="last_name">
										<label class="new_chk_in_lft_div_tbl_label">Depart Date</label>
										<div class="new_chk_in_lft_div_tbl_input">
											<div class="col-sm-12">
												<div class="row">
													<md-input-container class="md-block chInp" flex-gt-sm>
													<input type="date" id="txtDate"
														ng-model="checkin.hdr.expDepartDate" maxlength="50"/> </md-input-container>
												</div>
											</div>
										</div>
									</div>


								</div>


							</div>
						</div>
					</div>

					<div class="col-md-3 roomsDiv">
						<div class="row roomdtlRow">
							<md-subheader class="md-primary rec_edit_sub_hdr">Change Room</md-subheader>

							<div layout-gt-xs="row" class="data_element">
								<md-input-container class="md-block" flex-gt-xs>
								<label>Room Type</label> <input
									ng-model="checkin.hdr.roomTypeCode" disabled> </md-input-container>

								<md-input-container> <a
									ng-if="roomDetails.removeRoomType" class="removeBtn"
									ng-click="changeToInitialRoomType()"><i class="fa fa-trash"
									aria-hidden="true"></i></a> <md-menu md-offset="0 -7"
									ng-if="${status}== 5"> <md-button
									aria-label="Open demo menu" class=" md-fab md-primary md-mini"
									ng-click="loadAvailableRoomTypes();$mdMenu.open($event)">
								<i class="fa fa-pencil" aria-hidden="true"></i> </md-button> <md-menu-content
									width="6"> <md-menu-item
									ng-repeat="(key,availRoomTypes) in roomTypesAvailable">
								<md-button
									ng-disabled="availRoomTypes.roomTypeId==checkin.hdr.roomTypeId"
									ng-click="showRoomTypeDetails(availRoomTypes.roomTypeId,availRoomTypes.roomTypeCode)">
								<span md-menu-align-target>{{availRoomTypes.roomTypeCode}}</span>
								<span class="available_span"> Available:
									{{availRoomTypes.availRoom}}</span> </md-button> </md-menu-item> </md-menu-content> </md-menu>
							</div>

							<div layout-gt-xs="row" class="data_element">
								<md-input-container class="md-block" flex-gt-xs>
								<label>Rate Plan</label> <input ng-model="checkin.hdr.rateCode"
									disabled> </md-input-container>

								<md-input-container> <a
									ng-if="roomDetails.removeRate" class="removeBtn"
									ng-click="changeToInitialRoomRate()"><i class="fa fa-trash"
									aria-hidden="true"></i></a> <md-button ng-if="${status}== 5"
									class="md-fab md-primary md-mini" ng-click="showRoomRates()"
									aria-label="edit room rate"> <i
									class="fa fa-pencil" aria-hidden="true"></i> </md-button> </md-input-container>
							</div>

							<div layout-gt-xs="row" class="data_element">
								<md-input-container class="md-block" flex-gt-xs>
								<label>Room Number</label> <input
									ng-model="checkin.hdr.roomNumber" required disabled> </md-input-container>


								<md-input-container> <a
									ng-if="roomDetails.removeRoom" class="removeBtn"
									ng-click="changeToInitialRoom()"><i class="fa fa-trash"
									aria-hidden="true"></i></a> <md-button ng-if="${status}== 5"
									class="md-fab md-primary md-mini"
									ng-click="getVacantRooms(checkin.hdr.roomTypeId)"
									aria-label="edit room"> <i class="fa fa-pencil"
									aria-hidden="true"></i> </md-button> </md-input-container>
							</div>


							<div layout-gt-xs="row" class="data_element">
								<div class="col-md-6">
									<md-input-container class="md-block" flex-gt-xs>
									<label>Nights</label> <md-select ng-disabled="${status}!= 5"
										ng-model="checkin.hdr.numNights" placeholder="Nights"
										class="md-no-underline"> <md-option
										ng-disabled="checkNight($index+1,diffDays)"
										ng-repeat="a in nightRange() track by $index"
										value="{{$index+1}}">{{$index+1}}</md-option> </md-select>
								</div>
								<div class="col-md-6">
									<md-input-container class="searchFormInput" flex="50">
									<label>Extend Stay</label> <input type="number" step="any"
										ng-if="${status}== 5" ng-model="checkin.hdr.dayNum" min="0"
										max="200" limit-zero="200" /> </md-input-container>
									<!-- <input type="number" step="any"
								 min="{{diffDays-1}}" max="{{checkin.hdr.numNights}}"
								ng-pattern="/^1234$/"> </md-input-container> -->
								</div>
							</div>
						</div>
						<div class="row discount_row">
							<md-subheader class="md-primary rec_edit_sub_hdr">Discount</md-subheader>
							<div layout-gt-xs="row" class="data_element" ng-repeat="discDisp in discDispList">
								<md-input-container class="md-block" flex-gt-xs>
								<label>Code</label> <input value = "{{discDisp.code}}({{discDisp.discountFor == 1? 'ROOM':'FOOD' }})" disabled>
								</md-input-container>
								<md-input-container class="md-block" flex-gt-xs>
								<label>Discount</label> <input ng-model="discDisp.value"
									disabled> </md-input-container>
								<md-input-container class="md-block" >	
								<div ng-if="${status}== 5 && disApliedcnt == 0 " id ="rmvbt"class="col-md-1  delete_room_btn ng-scope"  ng-click="deleteDiscountAplied(discDisp.id)" role="button">
									<i class="fa fa-trash" aria-hidden="true"></i>
								</div>	</md-input-container>
								
							</div>
							
							<md-input-container> <a
									ng-if="roomDetails.removeRate" class="removeBtn"
									ng-click="changeToInitialDiscount()"><i class="fa fa-trash"
									aria-hidden="true"></i></a> <md-button ng-click="showDiscounts()" 
									ng-if="${status}== 5 && disApliedcnt== 0" class="md-fab md-primary md-mini"
									aria-label="Eat cake"> <i class="fa fa-pencil"
									aria-hidden="true"></i> </md-button> </md-input-container>
					

						</div>
						
						
					</div>
					</md-content>
				</div>


			</section>
			<section class="panel" ng-if="${status}== 5">
				<header class="panel-heading module_caption">
					<h1>
						<spring:message code="reservation.label.sharerInfo" />
					</h1>

				</header>
				<div class="panel-body">
					<div class="form-group align-btn-rgt topAddButtonDiv">
						<button type="button" class="btn btn-warning"
							ng-click="newSharer()">
							New Sharer <i class="fa fa-plus"></i>
						</button>
					</div>

					<div ng-if="!recCtrl.isFetchingData">
						<table id="entry-grid" datatable="" dt-options="recCtrl.dtOptions"
							dt-instance="recCtrl.dtInstance" dt-columns="recCtrl.dtColumns"
							style="width: 100%;" class="angDataTable table table-hover"></table>
					</div>
				</div>
			</section>

			<section class="panel">
				<div class="panel-body">
					<div class="align-btn-rgt">
						<%-- 			<button id="btnPrev" class="btn btn-default" type="button"
				onclick="cancel()">
				<spring:message code="pms.btn.back"></spring:message>
			</button> --%>

						<button id="btnNext" ng-disabled="submit_click"
							class="btn btn-success" type="submit">
							<spring:message code="pms.btn.save"></spring:message>
						</button>
					</div>
				</div>
			</section>

		</div>
	</form>
</div>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="roomAssignmyModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Available Rooms</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<div class="col-md-12">
					<div class="col-sm-6 roomsDiv">
						<md-content style="max-height: 240px;">
						<section>
							<md-subheader class="md-primary">Rooms
							{{roomTypeCode}} <label class="asgnd_room_label">Assigned
								: {{roomDetails.roomNumber}}</label></md-subheader>
							<md-list> <md-radio-group
								ng-model="roomDetails.roomNumber"> <md-list-item
								ng-repeat="available in availableRooms">
							<p>
								<label>{{ available.name}}</label> <span class="room_no_span">
									No : {{ available.number}} </span>
							</p>
							<md-radio-button class="md-primary"
								aria-label="{{ available.name}}" value="{{available.number}}"></md-radio-button>
							</md-list-item></md-radio-group> </md-list>
						</section>
						</md-content>


					</div>
					<div class="col-sm-6">
						<div class="roomFeaturesDiv">
							<md-content style="height: 240px;">
							<section>
								<md-subheader class="md-primary">Features</md-subheader>
								<div ng-repeat="avail in availableRooms"
									ng-if="avail.number==roomDetails.roomNumber">
									<div class="col-sm-6" ng-repeat="ftr in avail.roomFeatureList ">
										<label><i class="fa fa-hand-o-right"
											aria-hidden="true"></i> {{ftr.feature}}</label>
									</div>
								</div>
							</section>
							</md-content>
						</div>
					</div>

					<!-- <div class="col-sm-4 col-md-6 pull-right imageDiv" ng-if="imageDiv">
						<div id="carousel-example-generic" class="carousel slide"
							data-ride="carousel">
							Indicators
							<ol class="carousel-indicators">
								<li data-target="#carousel-example-generic" data-slide-to="0"
									class="active"></li>
								<li data-target="#carousel-example-generic" data-slide-to="1"></li>
								<li data-target="#carousel-example-generic" data-slide-to="2"></li>
							</ol>

							Wrapper for slides
							<div class="carousel-inner" role="listbox">
								<div ng-if="roomType.image1" class="item active">
									<img src="{{roomType.image1}}" alt="...">
									<div class="carousel-caption">...</div>
								</div>
								<div ng-if="roomType.image2"
									class="item {{!roomType.image1?'active':''}}">
									<img src="{{roomType.image2}}" alt="...">
									<div class="carousel-caption">...</div>
								</div>
								<div ng-if="roomType.image3"
									class="item {{(!roomType.image1 && !roomType.image2)?'active':''}}">
									<img src="{{roomType.image3}}" alt="...">
									<div class="carousel-caption">...</div>
								</div>
								<div ng-if="roomType.image4"
									class="item {{(!roomType.image1 && !roomType.image2 && !roomType.image3)?'active':''}}">
									<img src="{{roomType.image4}}" alt="...">
									<div class="carousel-caption">...</div>
								</div>



							</div>

							Controls
							<a class="left carousel-control" href="#carousel-example-generic"
								role="button" data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							</a> <a class="right carousel-control"
								href="#carousel-example-generic" role="button" data-slide="next">
								<span class="glyphicon glyphicon-chevron-right"
								aria-hidden="true"></span> <span class="sr-only">Next</span>
							</a>
						</div>

					</div> -->
				</div>


			</div>
			<div class="modal-footer">
				<button id="assignroomBtn" type="button" class="btn btn-success"
					ng-click="assignRoomNumber()">Assign</button>
				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose"
					ng-click="cancelRoomAssignPopUp();">
					<spring:message code="pms.btn.cancel" />
				</button>

			</div>
		</div>
	</div>
</div>


<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="ratePlanmyModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Available Rate Plans</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<div class="col-md-12">
					<div class="col-sm-6 roomsDiv">
						<md-content style="max-height: 350px;">
						<section>

							<md-subheader class="md-primary">Rate Plans<span
								ng-if="checkin.hdr.roomTypeCode.length>0">
								[{{checkin.hdr.roomTypeCode}}]</span></md-subheader>
							<md-list> <md-radio-group
								ng-model="roomDetails.ratePlanId"> <md-list-item
								ng-repeat="(key,roomrate) in roomRates">
							<p>
								<label>{{ roomrate.rateCode}}</label>
							</p>
							<md-radio-button class="md-primary"
								aria-label="{{ roomrate.rateCode}}" value="{{roomrate.rateId}}"></md-radio-button>
							</md-list-item></md-radio-group> </md-list>
						</section>
						</md-content>


					</div>

					<div class="col-sm-4 col-md-6 pull-right">
						<div class="roomFeaturesDiv">
							<md-content>
							<section>
								<md-subheader class="md-primary">Occupancy</md-subheader>
								<div ng-repeat="(key,rate) in roomRates"
									ng-if="rate.rateId==roomDetails.ratePlanId">
									<md-list> <md-radio-group
										ng-model="roomDetails.occupancy"> <md-list-item
										ng-if="rate.occ1">
									<p>
										<label>Single</label><span ng-if="rate.isOpen==true">${currencySymbol}</span><label
											ng-if="rate.isOpen==true" class="rateoccupancyBind">${currencySymbol}
											{{rate.totalOcc1Rate}}</label>
										<md-input-container ng-if="rate.isOpen==true"
											class="singleOccupancy"> <label>Rate
											per day(<span>${currencySymbol} </span>)
										</label> <input
											id="{{rate.rateCode}}_{{rate.rateId}}_occ1_{{rate.source_rate_hdr_id}}"
											class="assgnRate rateperDay" type="text"
											ng-click="currencyNeed1(rate.rateId,rate.totalOcc1Rate)"
											ng-value="rate.totalOcc1Rate/checkin.hdr.numNights">
										</md-input-container>
										<md-input-container ng-if="rate.isOpen==false"
											class="singleOccupancy"> <input
											id="{{rate.rateCode}}_{{rate.rateId}}_occ1_{{rate.source_rate_hdr_id}}"
											class="assgnRate" type="text"
											ng-click="currencyNeed1(rate.rateId,rate.totalOcc1Rate)"
											ng-value="rate.totalOcc1Rate/checkin.hdr.numNights" readonly>
										</md-input-container>


									</p>
									<md-radio-button class="md-primary" aria-label="Single"
										value="1"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ2">
									<p>
										<label>Double </label><span ng-if="rate.isOpen==true">${currencySymbol}</span><label
											ng-if="rate.isOpen==true" class="rateoccupancyBind">${currencySymbol}
											{{rate.totalOcc2Rate}}</label>
										<md-input-container ng-if="rate.isOpen==true"
											class="singleOccupancy"> <label>Rate
											per day(<span>${currencySymbol} </span>)
										</label> <input
											id="{{rate.rateCode}}_{{rate.rateId}}_occ2_{{rate.source_rate_hdr_id}}"
											class="assgnRate rateperDay" type="text"
											ng-click="currencyNeed2(rate.rateId,rate.totalOcc2Rate)"
											ng-value="rate.totalOcc2Rate/checkin.hdr.numNights">
										</md-input-container>
										<md-input-container ng-if="rate.isOpen==false"
											class="singleOccupancy"> <input
											id="{{rate.rateCode}}_{{rate.rateId}}_occ2_{{rate.source_rate_hdr_id}}"
											class="assgnRate" type="text"
											ng-click="currencyNeed2(rate.rateId,rate.totalOcc2Rate)"
											ng-value="rate.totalOcc2Rate/checkin.hdr.numNights" readonly>
										</md-input-container>

									</p>
									<md-radio-button class="md-primary" aria-label="Double"
										value="2"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ3">
									<p>
										<label>Tripple</label><span ng-if="rate.isOpen==true">${currencySymbol}</span><label
											ng-if="rate.isOpen==true" class="rateoccupancyBind">${currencySymbol}
											{{rate.totalOcc3Rate}}</label>
										<md-input-container ng-if="rate.isOpen==true"
											class="singleOccupancy"> <label>Rate
											per day(<span>${currencySymbol} </span>)
										</label> <input
											id="{{rate.rateCode}}_{{rate.rateId}}_occ3_{{rate.source_rate_hdr_id}}"
											class="assgnRate rateperDay" type="text"
											ng-click="currencyNeed3(rate.rateId,rate.totalOcc3Rate)"
											ng-value="rate.totalOcc3Rate/checkin.hdr.numNights">
										</md-input-container>
										<md-input-container ng-if="rate.isOpen==false"
											class="singleOccupancy"> <input
											id="{{rate.rateCode}}_{{rate.rateId}}_occ3_{{rate.source_rate_hdr_id}}"
											class="assgnRate" type="text"
											ng-click="currencyNeed3(rate.rateId,rate.totalOcc3Rate)"
											ng-value="rate.totalOcc3Rate/checkin.hdr.numNights" readonly>
										</md-input-container>
									</p>
									<md-radio-button class="md-primary" aria-label="Tripple"
										value="3"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ4">
									<p>
										<label>Quad</label><span ng-if="rate.isOpen==true">${currencySymbol}</span><label
											ng-if="rate.isOpen==true" class="rateoccupancyBind">${currencySymbol}
											{{rate.totalOcc4Rate}}</label>
										<md-input-container ng-if="rate.isOpen==true"
											class="singleOccupancy"> <label>Rate
											(<span>${currencySymbol} </span>)
										</label> <input
											id="{{rate.rateCode}}_{{rate.rateId}}_occ4_{{rate.source_rate_hdr_id}}"
											class="assgnRate rateperDay" type="text"
											ng-click="currencyNeed4(rate.rateId,rate.totalOcc4Rate)"
											ng-value="rate.totalOcc4Rate/checkin.hdr.numNights">
										</md-input-container>
										<md-input-container ng-if="rate.isOpen==false"
											class="singleOccupancy"> <input
											id="{{rate.rateCode}}_{{rate.rateId}}_occ4_{{rate.source_rate_hdr_id}}"
											class="assgnRate" type="text"
											ng-click="currencyNeed4(rate.rateId,rate.totalOcc4Rate)"
											ng-value="rate.totalOcc4Rate/checkin.hdr.numNights" readonly>
										</md-input-container>
									</p>
									<md-radio-button class="md-primary" aria-label="Quad" value="4"></md-radio-button>
									</md-list-item> </md-radio-group> </md-list>
								</div>

							</section>
							</md-content>
						</div>

					</div>
				</div>


			</div>
			<div class="modal-footer">
				<button id="assignroomBtn" type="button" class="btn btn-success"
					ng-click="assignRoomRate()">Assign</button>
				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose" ng-click="cancelRatePlanPopUp();">
					<spring:message code="pms.btn.cancel" />
				</button>

			</div>
		</div>
	</div>
</div>

<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="roomTypemyModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Available Rate Plans</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<div class="col-md-12">
					<div class="col-sm-2 col-md-6  roomsDiv">
						<md-content style="max-height: 350px;">
						<section>
							<md-subheader class="md-primary">Rate Plans
							[{{checkin.hdr.roomTypeCode}}]</md-subheader>
							<md-list> <md-radio-group
								ng-model="roomDetails.ratePlanId"> <md-list-item
								ng-repeat="(key,roomrate) in roomRatesNewRoomType">
							<p>
								<label>{{ roomrate.rateCode}}</label>
							</p>
							<md-radio-button class="md-primary"
								aria-label="{{ roomrate.rateCode}}" value="{{roomrate.rateId}}"></md-radio-button>
							</md-list-item></md-radio-group> </md-list>
						</section>
						</md-content>


					</div>

					<div class="col-sm-2 col-md-6 ">
						<div class="roomFeaturesDiv">
							<md-content>
							<section>
								<md-subheader class="md-primary">Occupancy</md-subheader>
								<div ng-repeat="(key,rate) in roomRatesNewRoomType"
									ng-if="rate.rateId==roomDetails.ratePlanId">

									<md-list> <md-radio-group
										ng-model="roomDetails.occupancy"> <md-list-item
										ng-if="rate.occ1">
									<p>
										<label>Single <span class="occRate">{{rate.totalOcc1Rate}}
												${currencySymbol}</span></label>
									</p>
									<md-radio-button class="md-primary" aria-label="Single"
										value="1"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ2">
									<p>
										<label>Double <span class="occRate">{{rate.totalOcc2Rate}}
												${currencySymbol}</span></label>
									</p>
									<md-radio-button class="md-primary" aria-label="Double"
										value="2"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ3">
									<p>
										<label>Tripple <span class="occRate">{{rate.totalOcc3Rate}}
												${currencySymbol}</span></label>
									</p>
									<md-radio-button class="md-primary" aria-label="Tripple"
										value="3"></md-radio-button> </md-list-item> <md-list-item ng-if="rate.occ4">
									<p>
										<label>Quad <span class="occRate">{{rate.totalOcc4Rate}}
												${currencySymbol}</span></label>
									</p>
									<md-radio-button class="md-primary" aria-label="Quad" value="4"></md-radio-button>
									</md-list-item> </md-radio-group> </md-list>

								</div>
							</section>
							</md-content>
						</div>

					</div>
					<!-- <div class="col-sm-2 col-md-4 pull-right imageDiv">
						<div id="carousel-example-generic" class="carousel slide"
							data-ride="carousel">
							Indicators
							<ol class="carousel-indicators">
								<li data-target="#carousel-example-generic" data-slide-to="0"
									class="active"></li>
								<li data-target="#carousel-example-generic" data-slide-to="1"></li>
								<li data-target="#carousel-example-generic" data-slide-to="2"></li>
							</ol>

							Wrapper for slides
							<div class="carousel-inner" role="listbox">
								<div ng-if="roomTypeImages.image1" class="item active">
									<img src="{{roomTypeImages.image1}}" alt="...">
									<div class="carousel-caption">...</div>
								</div>
								<div ng-if="roomTypeImages.image2"
									class="item {{!roomTypeImages.image1?'active':''}}">
									<img src="{{roomTypeImages.image2}}" alt="...">
									<div class="carousel-caption">...</div>
								</div>
								<div ng-if="roomTypeImages.image3"
									class="item {{(!roomTypeImages.image1 && !roomTypeImages.image2)?'active':''}}">
									<img src="{{roomTypeImages.image3}}" alt="...">
									<div class="carousel-caption">...</div>
								</div>
								<div ng-if="roomTypeImages.image4"
									class="item {{(!roomTypeImages.image1 && !roomTypeImages.image2 && !roomTypeImages.image3)?'active':''}}">
									<img src="{{roomTypeImages.image4}}" alt="...">
									<div class="carousel-caption">...</div>
								</div>



							</div>

							Controls
							<a class="left carousel-control" href="#carousel-example-generic"
								role="button" data-slide="prev"> <span
								class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
								<span class="sr-only">Previous</span>
							</a> <a class="right carousel-control"
								href="#carousel-example-generic" role="button" data-slide="next">
								<span class="glyphicon glyphicon-chevron-right"
								aria-hidden="true"></span> <span class="sr-only">Next</span>
							</a>
						</div>

					</div> -->
				</div>


			</div>
			<div class="modal-footer">
				<button id="assignroomBtn" type="button" class="btn btn-success"
					ng-click="assignType()">Assign</button>
				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose" ng-click="cancelRoomTypePopUp();">
					<spring:message code="pms.btn.cancel" />
				</button>

			</div>
		</div>
	</div>
</div>


<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="availDiscountsmyModal" class="modal fade">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button aria-hidden="true" data-dismiss="modal" class="close"
					type="button">x</button>
				<h4 class="modal-title">Available Discounts</h4>
			</div>
			<div class="modal-body" style="overflow-y: auto;">
				<div layout="column" class="md-inline-form">

					<div>

						<div class="discTypeDiv row">
							<md-radio-group ng-model="disc.group">
							<div class="col-md-3">
								<md-radio-button value="general" class="md-primary">General</md-radio-button>
							</div>
							<div class="col-md-3">
								<md-radio-button value="plan" class="md-primary">Plan
								Based </md-radio-button>
							</div>
							</md-radio-group>
						</div>
						<div class="discountsList row">
							<md-subheader class="md-no-sticky availRatePlans">Applicable
							Discounts</md-subheader>
							<div class="generalDiscDiv" ng-if="disc.group=='general'">
								<md-content> <md-list> <md-radio-group
									ng-model="disc.generalDisc"> <md-list-item
									class="md-1-line" ng-repeat="general in availDiscounts.general">
								<div class="md-list-item-text rateByRoomList">
										<div class="col-md-6">
										<md-radio-button class="rad_{{general.id}}" data-discountFor = {{general.discountFor}}
											data-isOpen={{general.isOpen}}
											data-discCode="{{general.code}}"
											data-discValue="{{general.isPc ?general.discPc:general.discAmount}} {{general.isPc ? '%' : currency }}"
											value="{{general.id}}" class="md-primary">
										<label>{{general.code}}</label> <span class="md-subhead">(
											{{general.name}} )</span></md-radio-button>
									</div>
									<div class="col-md-2">
										<label>{{general.discountFor == 1? "ROOM":"FOOD" }}</label>
									</div>
									<div class="col-md-4" ng-if="!general.isOpen">{{general.isPc
										?general.discPc:general.discAmount}} {{general.isPc ? "%" :
										currency }}</div>
									<div class="col-md-4 openDiscDiv" ng-if="general.isOpen">
										<md-input-container class="md-block" flex-gt-xs>
										<label>discout value ( {{general.isPc ? "%" : currency
											}} )</label> <input id="open{{general.id}}"
											value="{{general.isPc ?general.discPc:general.discAmount}}">
										</md-input-container>
									</div>
								</div>
								<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-radio-group></md-list> </md-content>
							</div>

							<div class="planDiscDiv" ng-if="disc.group=='plan'">

								<md-content> <md-list> <md-radio-group
									ng-model="disc.planBased"> <md-list-item
									class="md-1-line" ng-repeat="plan in availDiscounts.plan">
								<div class="md-list-item-text rateByRoomList">
									<div class="col-md-8">
										<md-radio-button class="discPlanCheckBox chk_{{plan.id}}"
											data-discCode="{{plan.code}}"
											data-discValue="{{plan.isPc?plan.discPc:plan.discAmount}} {{plan.isPc ? '%' : currency }}"
											ng-true-value="{{plan.rateId}}" ng-false-value=""
											value="{{plan.id}}"> <label>{{plan.code}}</label>
										<span class="md-subhead">( {{plan.name}} )</span> </md-radio-button>

									</div>
									<div class="col-md-4">{{plan.isPc
										?plan.discPc:plan.discAmount}} {{plan.isPc ? "%" : currency}}</div>
								</div>
								<md-divider ng-if="!$last"></md-divider> </md-list-item> </md-radio-group></md-list> </md-content>
							</div>

						</div>
					</div>


				</div>
			</div>
			<div class="modal-footer">
				<button id="contExpDepartBtn" type="button" class="btn btn-success"
					ng-click="applyDiscount()">Apply</button>
				<button id="cancelExpDepartBtn" type="button"
					class="btn btn-default rbtnClose" ng-click="cancelDiscPopUp();">
					<spring:message code="pms.btn.cancel" />
				</button>

			</div>
		</div>
	</div>
</div>



<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
	tabindex="-1" id="addsharermyModal" class="modal fade">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form name="sharerForm" ng-submit="sharerForm.$valid && saveSharer()">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button">x</button>
					<h4 class="modal-title">
						<spring:message code="reception.label.sharer" />
					</h4>
				</div>
				<div class="modal-body">
					<!-- modal data comes here -->
					<md-content class="guest_body">
					<div class="col-md-12">
						<div layout-gt-sm="row" class="input_div">
							<!-- <md-input-container> <md-select
								ng-model="sharer.salutation" required> <md-option
								ng-repeat="salutations in name" ng-value="salutations">{{salutations}}</md-option>
							</md-select> </md-input-container> -->
							<md-input-container> <select
								ng-model="sharer.salutation" required
								ng-options="salute for salute in names" id="salute"
								ng-value="salute " class="salute">
							</select> </md-input-container>
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>First name</label> <input required type="text"
								onclick="this.focus();this.select()" ng-model="sharer.firstName">
							</md-input-container>
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>Last Name</label> <input required type="text"
								onclick="this.focus();this.select()" ng-model="sharer.lastName">
							</md-input-container>
						</div>

						<div layout-gt-sm="row" class="input_div">
							<md-input-container class="md-block chInp" flex-gt-sm>
							<!-- 2246 digna 20180628 begin --> <label>Email</label> <input
								ng-model="sharer.email" type="email"
								ng-pattern="/^$|^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/"
								onclick="this.focus();this.select()"> </md-input-container>
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>Phone</label> <input ng-model="sharer.phone"
								onclick="this.focus();this.select()"
								ng-pattern="/^$|^[0-9]{10}$|^[0-9]{12}$/"></md-input-container>
							<!-- 2246 digna 20180628 end -->
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>Address</label> <input ng-model="sharer.address"
								onclick="this.focus();this.select()" required> </md-input-container>
						</div>


						<div layout-gt-sm="row" class="input_div">
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>Nationality</label> <!--  <input ng-model="sharer.nationality"
								onclick="this.focus();this.select()"> --> <md-select
								ng-model="sharer.nationality" placeholder="country"
								ng-change="loadState(sharer.nationality)">
							<md-option ng-repeat="country in countryList "
								ng-value="country.name">{{country.name}}</md-option> </md-select> <!-- <select 
									ng-model="sharer.nationality" ng-change="loadState(sharer.nationality)"
									ng-options="country.name for country in countryList" id="salute" ng-value="country.name " class="salute">
								</select> --> </md-input-container>
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>State</label> <md-select ng-model="sharer.state"
								placeholder="state"> <md-option
								ng-repeat="state in stateList" ng-value="state.name">{{state.name}}</md-option>
							</md-select> <!-- <select 
									ng-model="sharer.state"
									ng-options="state.name for state in stateList" id="salute" ng-value="state.name " class="salute">
								</select> --> </md-input-container>
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>Passport No.</label> <input ng-model="sharer.passportNo"
								onclick="this.focus();this.select()"></md-input-container>

							<md-input-container ng-if="sharer.passportNo!=''"
								class="md-block chInp" flex-gt-sm> <label>Expiry</label>
							<md-datepicker ng-model="sharer.passportExpiryOn"></md-datepicker>
							</md-input-container>
						</div>
						<div layout-gt-sm="row" class="input_div">
							<md-input-container class="md-block chInp" flex-gt-sm>
							<label>Remarks</label> <input ng-model="sharer.remarks"
								onclick="this.focus();this.select()"> </md-input-container>
							<md-input-container class="md-block chInp" flex-gt-sm>
							<md-radio-group required ng-model="sharer.gender">
							<div class="col-md-3">
								<md-radio-button value="Male" class="md-primary">Male</md-radio-button>
							</div>
							<div class="col-md-3">
								<md-radio-button value="Female">Female</md-radio-button>
							</div>
							</md-radio-group> </md-input-container>
						</div>

					</div>
					</md-content>
					<!-- modal data comes here -->
				</div>
				<div class="modal-footer">
					<button id="popUpsaveButn" class="btn btn-success" type="submit">
						<spring:message code="pms.label.save" />
					</button>
					<button type="button" id="delete_btn" ng-if="!flagNewSharer"
						class="btn btn-danger" ng-click="deleteSharer()">
						<spring:message code='pms.btn.delete'></spring:message>
					</button>

					<button id="canceladdsharer" type="button"
						class="btn btn-default rbtnClose" ng-click="cancelSharerPopUp()">
						<spring:message code="pms.btn.cancel" />
					</button>
				</div>
			</form>
		</div>
	</div>
</div>