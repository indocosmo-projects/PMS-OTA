<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@ page import="com.indocosmo.pms.enumerator.CommunicationPurposes"%>
<input type="hidden" value="${group}" id="cmGroup" />
<section class="panel">
	<div class="panel-body mainDiv">
		<md-toolbar class="md-theme-light" style="z-index: 0;">
		<h4 class="md-toolbar-tools md-theme-light">
			<span>Guest Details</span>
		</h4>
		</md-toolbar>
		<md-content class="mainContent"> <md-list> <md-list-item
			class="md-3-line guestDtls"> <c:if
			test="${group eq 'resvn'}">
			<div class="md-list-item-text resvContent">
				<div class="col-md-12">
					<div class="col-md-6 dateDtls">
						<div class="datePanels row">
							<div class="col-sm-6">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">Reservation</h3>
									</div>
									<div class="panel-body">${resv_date}</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">Arrival Date</h3>
									</div>
									<div class="panel-body">${arr_date}</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="roomDtls col-sm-12">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">Room Details</h3>
									</div>
									<div class="panel-body">
										<div class="col-sm-2">
											<div class="rdhdr">Rooms</div>
											<div class="rddtl">${num_rooms}</div>
										</div>
										<div class="col-sm-2">
											<div class="rdhdr">Nights</div>
											<div class="rddtl">${num_nights}</div>
										</div>
										<div class="col-sm-4">
											<div class="rdhdr">Status</div>
											<div id="divStatus" class="rddtl">${resv_status}</div>
										</div>


										<div class="col-sm-4">
											<div class="rdhdr">Cut Off Date</div>
											<div class="rddtl">${cut_off_date}</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<label>Reserved For</label>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-user" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${reserved_by}</div>
						</div>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-at" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${resv_by_mail}</div>
							<input type="hidden" value="${resv_by_mail}" id="resvMail">

						</div>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-phone" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${resv_by_phone}</div>
							<input type="hidden" value="${resv_by_phone}" id="phoneCustmr">

						</div>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-envelope-o" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${resv_by_address}</div>
						</div>
						<label>Reserved By</label>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-user" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${resv_for}</div>
						</div>
					</div>
				</div>
			</div>
		</c:if> <c:if test="${group eq 'checkIn'}">
			<div class="md-list-item-text resvContent">
				<div class="col-md-12">
					<div class="col-md-6 dateDtls">
						<div class="datePanels row">
							<div class="col-sm-6">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">Arrival Date</h3>
									</div>
									<div class="panel-body">${arrivalDate}</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">Depart Date</h3>
									</div>
									<div class="panel-body">${departDate}</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="roomDtls col-sm-12">
								<div class="panel panel-primary">
									<div class="panel-heading">
										<h3 class="panel-title">Room Details</h3>
									</div>
									<div class="panel-body">
										<div class="col-sm-4">
											<div class="rdhdr">Room Type</div>
											<div class="rddtl">${roomType}</div>
										</div>
										<div class="col-sm-4">
											<div class="rdhdr">Room Number</div>
											<div class="rddtl">${roomNo}</div>
										</div>
										<div class="col-sm-4">
											<div class="rdhdr">Rate Plan</div>
											<div id="divStatus" class="rddtl">${ratePlan}</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<label>Reserved By</label>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-user" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${name}</div>
						</div>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-at" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${email}</div>
							<input type="hidden" value="${email}" id="checkinMail">

						</div>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-phone" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${phone}</div>
							<input type="hidden" value="${phone}" id="checkinPhone">

						</div>
						<div class="input-group userDtls">
							<span class="input-group-addon" id="sizing-addon2"><i
								class="fa fa-envelope-o" aria-hidden="true"></i></span>
							<div class="form-control dtlsInp"
								aria-describedby="sizing-addon2">${address}</div>
						</div>
					</div>
				</div>
			</div>
		</c:if> <md-divider ng-if="!$last"></md-divider> </md-list-item> </md-list> </md-content>
	</div>
</section>
<section class="panel">
	<header class="panel-heading module_caption">
		<h1>Communication Details</h1>
	</header>
	<div class="panel-body">

		<div class="dataTableDiv">
			<div class="mail_phone_div">
				<div class="form-group align-btn-rgt topAddButtonMailDiv">

					<button type="button" class="btn btn-warning"
						ng-click="newCommunicationMail()">
						New <i class="fa fa-envelope-square btnPhone" aria-hidden="true"></i>
					</button>
				</div>
				<div class="form-group align-btn-rgt topAddButtonDiv">
					<c:if test="${cp_isCanAdd}">


						<button type="button" class="btn btn-warning"
							ng-click="newCommunication()">
							New <i class="fa fa-phone-square btnPhone" aria-hidden="true"></i>
						</button>



					</c:if>
				</div>
			</div>
			<div id="dataTableDiv">
				<table id="entry-grid" datatable=""
					dt-options="commtnEdit.dtOptions"
					dt-instance="commtnEdit.dtInstance"
					dt-columns="commtnEdit.dtColumns" style="width: 100%;"
					class="angDataTable table table-hover"></table>
			</div>

			<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
				tabindex="-1" id="newCommunicationmyModal" class="modal fade">
				<div class="modal-dialog modal-md">
					<div class="modal-content">
						<div class="modal-header">
							<button aria-hidden="true" data-dismiss="modal" class="close"
								type="button">×</button>
							<h4 class="modal-title">Communication - Telephone</h4>
						</div>
						<div class="modal-body" style="overflow-y: auto;">
							<!-- modal data comes here -->
							<div class="form-horizontal tasi-form">

								<div class="form-group">
									<label class="control-label col-md-3"> Purpose <span
										class="red">*</span></label>
									<div class="col-md-6 col-xs-11" style="display: block;"
										id="codePurpose">
										<select id="slctTxnType" ng-change="showCriterias()"
											class="form-control input-sm" ng-model="commtnEdit.purpose">
											<c:forEach items="${CommunicationPurposes}" var="purpose">
												<c:if test="${group eq 'resvn'}">
													<c:if test="${purpose ne 'WELCOME' && purpose ne 'THANKS'}">
														<option value="${purpose.code}">${purpose}</option>

													</c:if>
												</c:if>
												<c:if test="${group eq 'checkIn'}">
													<c:if test="${purpose eq 'WELCOME' || purpose eq 'THANKS'}">
														<option value="${purpose.code}">${purpose}</option>

													</c:if>
												</c:if>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-8">
										<label for="MyField" id="purposecode">{{ purposecode}}</label>

									</div>
									<div class="col-md-1 col-xs-11 tick_green">
										<span id="name_check" class="switch-right"
											style="display: none;"><i class=" fa fa-check"></i></span> <span
											id="name_warning" class="switch-right warning_red"
											style="display: none;"><i class="fa fa-warning"></i></span>
									</div>
								</div>


								<div class="form-group">
									<label class="control-label col-md-3">Status</label>
									<div class="col-md-6 col-xs-11 statusClass">
										<div class="slideThree">
											<input type="checkbox" id="slideThree"
												ng-model="commtnEdit.status" name="check" /> <label
												for="slideThree"></label>
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-3"> Description </label>
									<div class="col-md-6 col-xs-11">
										<input type="text" maxlength="100"
											class="form-control form-control-inline input-medium"
											ng-model="commtnEdit.description" />
									</div>
								</div>
							</div>
							<!-- modal data comes here -->
						</div>
						<div class="modal-footer">
							<button id="cancelProvider" type="button"
								class="btn btn-default rbtnClose" ng-click="cancelPopUp();">
								<spring:message code="pms.btn.cancel" />
							</button>
							<button id="saveProviderBtn" type="button"
								class="btn btn-success" ng-click="saveCommunication()">
								<spring:message code="pms.btn.save" />
							</button>
							<%-- <c:if test="${cp_isCanDelete}">
									<button id="deleteProviderBtn" type="button"
										class="btn btn-warning" ng-click="deleteProvider()">
										<spring:message code="pms.btn.delete" />
									</button>
								</c:if> --%>
						</div>
					</div>
				</div>
			</div>
		</div>






		<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
			tabindex="-1" id="newEmailmyModalCommunication" class="modal fade">
			<div class="modal-dialog-email modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button aria-hidden="true" data-dismiss="modal" class="close"
							type="button">×</button>
						<h4 class="modal-title">Communication - Email</h4>
					</div>
					<div class="modal-body" style="overflow-y: auto;">
						<!-- modal data comes here -->
						<div class="form-horizontal tasi-form">
							<div class="form-group">
								<label class="control-label col-sm-2"><spring:message
										code="communication.label.To" /> <span class="red">*</span></label>


								<div class="col-sm-8">
									<input type="text" id="txtTo"
										class="form-control validator form-control-inline input-medium"
										ng-model="ToEmail.txtToEmail" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2"> Cc</label>
								<div class="col-sm-8">
									<input type="text" id="txtCc"
										class="form-control validator form-control-inline input-medium"
										ng-model="ToEmail.txtCcEmail" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2"> Subject</label>
								<div class="col-sm-8">
									<input type="text" id="txtSubjectMail"
										class="form-control validator form-control-inline input-medium"
										ng-model="ToEmail.SubjectMail" />
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-2"> Purpose</label>
								<div class="col-sm-8" style="display: block" id="purposeText">



									<md-autocomplete md-selected-item="commtnEdit.selectedItem"
										ng-model="ToEmail.selectedSubject"
										md-search-text-change="commtnEdit.searchTextChange(commtnEdit.searchText)"
										md-search-text="commtnEdit.searchText"
										md-items="item in commtnEdit.querySearch(commtnEdit.searchText)"
										md-item-text="item.display" md-min-length="0"
										md-selected-item-change="commtnEdit.selectedItemChange(item)"
										placeholder="Select Purpose" md-autofocus> <md-item-template>
									<span md-highlight-text="commtnEdit.searchText"
										md-highlight-flags="^i">{{item.display}}</span> </md-item-template> <md-not-found>
									No Purpose matching "{{commtnEdit.searchText}}" were found. </md-not-found> </md-autocomplete>


								</div>
								<div class="col-md-8" style="display: block" id="purposeLabelId">
									<label for="MyField" id="purpLabel">{{ purposeLabel}}</label>

								</div>
							</div>





							<div class="textEditorDiv">
								<form name="mailContentFrm">
									<div class="form-group">
										<text-angular name="textBox" class="templateText"
											ng-model="ToEmail.content" required></text-angular>
									</div>
								</form>
							</div>






						</div>
						<!-- modal data comes here -->
					</div>
					<div class="modal-footer">
						<input type="hidden" id="baction" value="save"
							ng-model="ToEmail.baction" />
						<button id="cancelEmailTemp" type="button"
							class="btn btn-default rbtnClose" ng-click="cancelEmailPopUp();">
							<spring:message code="pms.btn.cancel" />
						</button>


						<button id="saveSmsTemp" type="button" class="btn btn-success"
							ng-click="saveEmail()">Send</button>
						<!-- <button id="resendemilTemp" type="button" class="btn btn-success"
								ng-click="resendEmail()">
								Resend
							</button> -->
					</div>




				</div>
			</div>
		</div>









	</div>
</section>
<section class="panel">
	<div class="panel-body">
		<div class="align-btn-rgt">
			<button class="btn btn-default" ng-click="cancel()">
				<spring:message code="pms.btn.cancel"></spring:message>
			</button>
		</div>
	</div>
</section>