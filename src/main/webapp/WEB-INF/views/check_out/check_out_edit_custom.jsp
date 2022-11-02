<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@ page import="com.indocosmo.pms.enumerator.ReservationStatus"%>
<input type="hidden" value="${dateFormat}" id="dateFormat">
<input type="hidden" value="${folioBindNo}" id="hdnFolioBindNo">
<div class="row">
	<div class="col-lg-12">
		<section class="panel">
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="checkOut.label.roomAndGuest" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span>
			</header>
			<div class="panel-body">
				<div class="col-xs-12 col-md-6"
					ng-repeat="checkOut in checkOutDetails">
					<div class="row check-out-cart">
						<div class="col-xs-3 check-out-cart-left-part">
							<div
								ng-if="checkOut.status=='${ ReservationStatus.CHECKOUT.code}'">
								<div class="row status-section status-checkOut">CHECK-OUT</div>
							</div>
							<div
								ng-if="checkOut.status=='${ ReservationStatus.CHECKIN.code}'">
								<div class="row status-section status-checkIn">IN HOUSE</div>
							</div>
							<div class="row num-section">{{checkOut.roomNumber}}</div>
						</div>
						<div class="col-sm-9">
							<div class="row">
								<div class="row">
									<div class="col-xs-12 check-name">
										<label class="col-sm-3">Name:</label><span class="col-sm-9">{{checkOut.firstName}}&nbsp{{checkOut.lastName}}</span>
									</div>
								</div>
								<div class="col-xs-9 check-out-cart-right-part">
									<div class="row check-out-cart-right-part-dtls">

										<div class="col-xs-2 check-head">
											<i class="fa fa-check tick-style" aria-hidden="true"></i>
										</div>
										<div class="col-xs-12">{{checkOut.corporateName}}</div>
										<div class="col-xs-12 lft_div_con">
											<label class="col-sm-4"><b>Balance:</b></label><span
												class="col-sm-8">${currencySymbol}&nbsp;
												{{checkOut.folioBalance==null ? '0.00' :
												checkOut.folioBalance}}</span>
										</div>
										<div class="col-xs-12 lft_div_con">
											<label class="col-sm-4"><b>Departure:</b></label><span
												class="col-sm-8"> {{expDepartDate | date:dateFormat}}</span>
										</div>
										<div class="col-xs-12 lft_div_con">
											<%-- <div class="row check-section"
										ng-if="checkOut.status=='${ ReservationStatus.CHECKIN.code}'"> --%>
											<label class="col-sm-4"><b>Status:</b></label>
											<div class="check-section col-sm-8"
												ng-if="checkOut.status=='${ ReservationStatus.CHECKIN.code}'"
												ng-switch on="checkoutCheck(checkOut)">
												<!-- <div ng-switch-when="1" class="col-sm-10 status_div" id="marginAlign"> -->
												<span ng-switch-when="1" class="errmsg">Payment
													Pending.</span>
												<!-- </div> -->
												<!-- <div ng-switch-when="2" class="col-sm-10" id="marginAlign" > -->
												<span ng-switch-when="2" class="errmsg">No room
													charges posted.</span>
												<!-- </div> -->
												<div ng-switch-default>
													<div class="col-sm-10" id="marginAlign">
														<label>Add to CheckOut List</label>
													</div>
													<div class="col-sm-2 checkout_cb">
														<md-checkbox id="boxMargin"
															ng-checked="selected.indexOf(checkOut.encryCheckinNo) > -1"
															ng-click="toggleSelection(checkOut)"></md-checkbox>
													</div>
												</div>
											</div>
											<!-- </div> -->
										</div>
										<div class="col-xs-12 lft_div_con" ng-if="checkOut.status=='${ ReservationStatus.CHECKIN.code}' ">
											<label class="col-sm-4"><b>Discount:</b></label>
											<div class="check-section col-sm-8" >
									
													<div class="col-sm-2 checkout_cb">
														<md-checkbox id="isDiscount" ng-disabled = "true"
															ng-checked="checkOut.isDiscount"
															></md-checkbox>
													</div>
													<div class="col-sm-10" id="marginAlign">
														<label>Is Applicable</label>
													</div>
													
												
											</div>
										</div>
									</div>

									<%-- <div class="row check-section"
								ng-if="checkOut.status=='${ ReservationStatus.CHECKIN.code}'">
								<span>Status: </span>
								<div ng-switch on="checkoutCheck(checkOut)">
									<div ng-switch-when="1">
										<span class="errmsg">Payment Pending.</span>
									</div>
									<div ng-switch-when="2">
										<span class="errmsg">No room charges posted.</span>
									</div>
									 <div ng-switch-default >
									 <div class="col-sm-6">
									 <label>Add to CheckOut List</label>
									 </div><div class="col-sm-2 checkout_cb">
										<md-checkbox
											ng-checked="selected.indexOf(checkOut.encryCheckinNo) > -1"
											ng-click="toggleSelection(checkOut)"></md-checkbox>
										</div>
									</div>
								</div>

							</div> --%>
								</div>
								<div class="col-xs-3 check-out-cart-right-btns">
									<div
										ng-if="checkOut.status=='${ ReservationStatus.CHECKIN.code}'">
										<div class="row rightBtns">
											<div class="tool_tip_con">
												<label>Payment</label>
											</div>
											<!-- <label class="col-sm-4">Make Payment</label> -->
											<button class="btn btn-primary"
												ng-disabled="payment{{checkOut.checkinNo}}.pay"
												ng-click="goTopayment(checkOut.checkinNo,checkOut.folioBindNo);">
												<i class="fa fa-money" aria-hidden="true"></i>
											</button>
										</div>
										<div class="row rightBtns">
											<div class="tool_tip_con">
												<label>Merge Room</label>
											</div>
											<!-- <label class="col-sm-4">Transfer</label> -->
											<button class="btn btn-primary"
												ng-disabled="payment{{checkOut.checkinNo}}.transfer || checkOut.rcDiscntStatus"
												ng-click="goTotransfer(checkOut.folioNo,checkOut.folioBindNo);">
												<i class="fa fa-exchange" aria-hidden="true"></i>
											</button>
										</div>

										<div class="row rightBtns">
											<div class="tool_tip_con">
												<label>Posting</label>
											</div>
											<!-- <label class="col-sm-4">Check Out</label> -->
											<button class="btn btn-primary"
												ng-disabled="payment{{checkOut.checkinNo}}.post"
												ng-click="goToposting(checkOut.checkinNo,checkOut.folioBindNo);">
												<i class="fa fa-edit" aria-hidden="true"></i>

											</button>

										</div>
										
										<div class="row rightBtns">
											<div class="tool_tip_con">
												<label>Discount</label>
											</div>
											<!-- <label class="col-sm-4">Check Out</label> -->
											<button class="btn btn-primary"
												ng-disabled="!checkOut.isDiscount || payment{{checkOut.checkinNo}}.pay || checkOut.rcDiscntStatus"
												ng-click="goToDiscount(checkOut.checkinNo,checkOut.folioBindNo);">
												<i class="fa fa-tag" aria-hidden="true"></i>

											</button>

										</div>
									</div>
									<div
										ng-if="checkOut.status=='${ ReservationStatus.CHECKOUT.code}'">
										<div class="row rightBtns printbtn">
											<button class="btn btn-primary" style="margin-top: -20px;"
												ng-click="printOut(checkOut.folioNo);">
												<i class="fa fa-print" aria-hidden="true"></i>
												<div class="tool_tip_con">
													<label>Print Bill</label>
												</div>
											</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>

<%-- <div class="row">
	<div class="col-lg-12">
		<section class="panel" id="billInst">
			<header
				class="panel-heading module_caption panel-heading pointer_style"
				id="billInstButn">
				<h1>
					<spring:message code="checkOut.label.billingInstructions" />
				</h1>
				<!-- <span class="opt_butn"></span> -->
				<span class="tools pull-right"> <a class="fa fa-chevron-up"
					href="javascript:;"></a>
				</span>
			</header>
			<div class="panel-body temp_hide" id="billInstTxt">
				<textarea id="billingInstruction" name="billingInstruction"
					class="form-control form-control-inline input-medium resv_txtarea"></textarea>
			</div>
		</section>
	</div>
</div>
 --%>
<div class="row">
	<div class="col-lg-12">
		<section class="panel" ng-show="showBillAdrs">
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="checkOut.billingAddress.header.label" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-up"
					href="javascript:;"></a>
				</span>
			</header>
			<div class="panel-body" style="display: none;">
				<div class="col-sm-12">
					<div class="new_chk_in_lft_div_tbl">
						<div class="new_chk_in_lft_div_tbl_full_width_row">
							<div class="salutation_div">
								<label class="new_chk_in_lft_div_tbl_label">Title</label>
								<div class="new_chk_in_lft_div_tbl_input">
									<div class="col-sm-12">
										<div class="row">
											<md-input-container> <select
												ng-model="billingAddress.salutation"
												ng-options="salute for salute in salutations"
												class="form-control salutation">
											</select> </md-input-container>
										</div>
									</div>
								</div>
							</div>
							<div class="first_name">
								<label class="new_chk_in_lft_div_tbl_label">First name<span
									class="req_star_red">*</span></label>
								<div class="new_chk_in_lft_div_tbl_input">
									<div class="col-sm-12">
										<div class="row">
											<md-input-container>

											<div class="last_btn_input rcption_new_custo_first_input_div">
												<input required onclick="this.focus();this.select()"
													ng-model="billingAddress.firstName" maxlength="50"
													class=" rcption_new_custo_first_input">
												<button
													class="search_button btn reception reception_page_frst_name_search"
													ng-click="loadCustomerData()"></button>
											</div>
											</md-input-container>
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
											<input ng-model="billingAddress.lastName"
												ng-pattern="/^$|^[A-Za-z ]+$/" maxlength="50"> </md-input-container>
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
											<input ng-model="billingAddress.guestName"
												ng-pattern="/^$|^[A-Za-z ]+$/" maxlength="50"> </md-input-container>
										</div>
									</div>
								</div>
							</div>

						</div>


						<div class="new_chk_in_lft_div_tbl_full_width_row">
							<div class="email_div">
								<label class="new_chk_in_lft_div_tbl_label">Email</label>
								<div class="new_chk_in_lft_div_tbl_input">
									<div class="col-sm-12">
										<div class="row">
											<md-input-container
												class="md-block chInp reception_page_email" flex-gt-sm>
											<input type="text" ng-model="billingAddress.email"
												class="new_chk_email" required>
											<button
												class="search_button btn reception  reception_page_email_search"
												ng-click="simpleSearchByMail()" /></button>
											</md-input-container>
										</div>
									</div>
								</div>
							</div>
							<div class="phone_div">
								<label class="new_chk_in_lft_div_tbl_label">Phone</label>
								<div class="new_chk_in_lft_div_tbl_input">
									<div class="col-sm-12">
										<div class="row">
											<md-input-container
												class="md-block chInp reception_page_phone" flex-gt-sm>
											<input ng-model="billingAddress.phone" maxlength="15"
												onclick="this.focus();this.select()" only-digits required>
											<input type="button"
												class="search_button btn reception  reception_page_phone_search"
												ng-click="simpleSearchReception()" /> </md-input-container>
										</div>
									</div>
								</div>
							</div>
							<div class="addree_div">
								<label class="new_chk_in_lft_div_tbl_label">Address</label>
								<div class="new_chk_in_lft_div_tbl_input">
									<div class="col-sm-12">
										<div class="row">
											<md-input-container
												class="md-block chInp  reception_page_address" flex-gt-sm>
											<input ng-model="billingAddress.address" maxlength="200"
												required> </md-input-container>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="new_chk_in_lft_div_tbl_full_width_row">
							<div class="country">
								<label class="new_chk_in_lft_div_tbl_label">Country</label>
								<div class="new_chk_in_lft_div_tbl_input">
									<div class="col-sm-12">
										<div class="row">
											<md-input-container> <!-- <form ng-submit="$event.preventDefault()"> -->
											<md-select ng-model="billingAddress.nationality"
												class="md_slect_div_edit" name="provinceOrState"
												ng-change="loadState(billingAddress.nationality)" required>
											<md-option ng-repeat="country in countryList "
												ng-value="country.name">{{country.name}}</md-option> </md-select> </md-input-container>
											<!-- <span ng-cloak="" class="error ng-cloak" ng-if="!guestForm.provinceOrState.$valid">Please select an option</span> -->
											<div style="color: red" ng-show="showCountryError">Please
												select a country</div>
										</div>
									</div>
								</div>
							</div>
							<div class="state_div">
								<label class="new_chk_in_lft_div_tbl_label">State</label>
								<div class="new_chk_in_lft_div_tbl_input">
									<div class="col-sm-12">
										<div class="row">
											<md-input-container> <md-select
												ng-model="billingAddress.state" class="md_slect_div_edit"
												required> <md-option
												ng-repeat="state in stateList" ng-value="state.name">{{state.name}}</md-option>
											</md-select> </md-input-container>
											<div style="color: red" ng-show="showStateError">Please
												select a state</div>
										</div>
									</div>
								</div>
							</div>
							<div class="gst_num_div" id="margst">
								<label class="new_chk_in_lft_div_tbl_label" id="gstLabel">GST
									No</label>
								<div class="new_chk_in_lft_div_tbl_input">
									<div class="col-sm-12">
										<div class="row">
											<md-input-container class="md-block chInp reception_page_gst"
												flex-gt-sm> <input
												ng-model="billingAddress.gstno"
												onclick="this.focus();this.select()"> </md-input-container>
										</div>
									</div>
								</div>
							</div>

						</div>

					</div>
				</div>
			</div>
			<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
				tabindex="-1" id="myModal" class="modal fade">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button aria-hidden="true" data-dismiss="modal" class="close"
								type="button">x</button>
							<h4 class="modal-title">GUEST HISTORY</h4>
						</div>
						<div class="modal-body" style="overflow-y: auto;">

							<div class="table-responsive">
								<!-- modal data comes here -->
								<table class="table table-fixed">
									<thead id="table_head">
										<tr>

											<th></th>
											<th>Guest Name</th>
											<th>Mobile</th>
											<th>Email</th>
											<th>Address</th>
											<th>VAT Account No</th>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="data in customerList">

											<td><input type="radio" name="inlineRadioOptions"
												id="inlineRadio1" ng-model="$parent.radioData"
												value="{{$index}}" /></td>

											<td class="filterable-cell">{{data.salutation}}
												{{data.first_name}} {{data.Last_name}}</td>
											<td class="filterable-cell">{{data.phone}}</td>
											<td class="filterable-cell">{{data.mail}}</td>
											<td class="filterable-cell">{{data.address}}</td>
											<td class="filterable-cell">{{data.gstno}}</td>
										</tr>

									</tbody>
								</table>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary"
								ng-click="copyData(customerList[radioData])">Select
								Guest</button>

						</div>

					</div>
				</div>
			</div>
		</section>
		<section class="panel" id="billInst">

			<div class="panel-body temp_hide" id="">
				<%-- <div class="informatition_form_sub_title">
					<span class="title"><spring:message
							code="checkOut.label.print" /></span>
				</div>

				<div class="col-sm-12">

					<div class="col-sm-5 col-sm-offset-1">
						<input class="gender with-font" checked="checked" id="rdGenderM"
							type="radio" value="Male" name="gender"> <label
							for="rdGenderM"><spring:message
								code="checkOut.label.printTotal" /></label>
					</div>
					
				</div>
				<div class="col-sm-12">
					<div class="col-sm-5 col-sm-offset-1">
						<input class="gender with-font" checked="checked" id="rdGenderF"
							type="radio" value="Male" name="gender"> <label
							for="rdGenderF"><spring:message
								code="checkOut.label.printEach" /></label>
					</div>
					<div class="col-sm-2">
						<input id="printPreview" name="printPreview" class="checkbox"
							type="checkbox" value="true"> <label for="printPreview"></label>
						<label for="printPreview"><spring:message
								code="checkOut.label.preview" /></label>
					</div>
				</div> --%>




				<div class="informatition_form_sub_title col-sm-12">
					<span class="title"><spring:message
							code="checkOut.confirmation.header.label" /></span>
				</div>
				<div class="col-sm-11 col-sm-offset-1">
					<md-checkbox ng-model="confirmCheck" aria-label="Checkbox 1">
					<spring:message code="checkOut.confirmation.confirmation.text" />
					</md-checkbox>
				</div>
				<div class="row footer_pagination_main_div">
					<div class="modal-footer align-btn-rgt next-prev">
						<%-- <button id="backButn" data-dismiss="modal" class="btn btn-default"
							type="button">
							<i class="fa fa-arrow-left" aria-hidden="true"></i>
							<spring:message code="pms.btn.back" />
						</button>
						<button id="" type="button" class="btn btn-success"
							ng-click="printOut();">
							<i class="fa fa-print" aria-hidden="true"></i>
							<spring:message code="checkOut.button.label.print" />
						</button> --%>
						<button id="saveDataButn" type="button" class="btn btn-success"
							ng-disabled="btn_Disable" ng-click="updateCheckIn();">
							<i class="fa fa-sign-out" aria-hidden="true"></i>
							<spring:message code="checkOut.button.label.checkOut" />
						</button>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>


