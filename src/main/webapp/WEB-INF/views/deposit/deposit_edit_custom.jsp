<jsp:directive.include file="../common/includes/page_directives.jsp" />



<c:choose>
	<c:when test="${depositFrom == 1}">

	</c:when>
	<c:otherwise>
		<style>
.dtls .s-body {
	border-color: #39b7ac;
}
</style>
	</c:otherwise>
</c:choose>


<input type="hidden" value="${dateFormat}" id="dateFormat">
<input type="hidden" id="hotelDate" value="${hotelDate}">
<div class="row" ng-app="pmsApp" ng-controller="depositController">

	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<%--  <c:if test="${backBtnStatusVal==1}"> --%>

			<div class="btnbackshift" id="btnback">
				<%-- <a href="<c:url value='${backUrl}' />" class="btn btn-success pull-right backbutna">	
										   <i class="fa fa-arrow-left backbtntop"  > <small> <spring:message code="pms.btn.backtop" /></small></i> 
										 </a>	 --%>
				<a href="<c:url value='${backUrl}' />"
					class="ios-back-button shiftback"
					data-text="<spring:message code="pms.btn.backtop" />"></a>
				<button type="button" class="btn btn-warning backshift"
					style="display: none;"
					ng-click="cancelDeposit(${depositFrom},${reservationId });"
					ng-disabled="openshifBtnAction">
					<i class="fa fa-plus"></i>
				</button>


			</div>
			<%-- </c:if> --%>
			<div class="panel-body">
				<div class="dtls summury">
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<c:choose>
									<c:when test="${depositFrom == 1}">
										<spring:message code="checkin.label.reservation" />
									</c:when>
									<c:otherwise>
										<spring:message code="status.label.checkIn" />
									</c:otherwise>
								</c:choose>

							</div>
							<div id="reser_id" class="col-md-12 s-body">
								<c:choose>
									<c:when test="${depositFrom == 1}">
								${resHdr.resvNo}
								<%--    ${roomAvailability.reservation_No}  --%>
									</c:when>
									<c:otherwise>
									${checkInHdr.checkInNo}
								</c:otherwise>
								</c:choose>

							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">


								<c:choose>
									<c:when test="${depositFrom == 1}">
										<%-- <spring:message code="checkin.label.reservedDate" /> --%>
										Date
									</c:when>
									<c:otherwise>
										<spring:message code="reservation.label.deposit.arivalDate" />
									</c:otherwise>
								</c:choose>


							</div>
							<div id="" class="col-md-12 s-body">

								<c:choose>
									<c:when test="${depositFrom == 1}">
										<fmt:formatDate pattern="${dateFormat}"
											value="${resHdr.resvDate}" />

										<%--     <fmt:formatDate pattern="dd-MMM-yyyy" value="${roomAvailability.bookingDate}" />  --%>
									</c:when>
									<c:otherwise>

										<fmt:formatDate pattern="${dateFormat}"
											value="${checkInHdr.arrDate}" />


									</c:otherwise>
								</c:choose>



							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">

								<c:choose>
									<c:when test="${depositFrom == 1}">
										<spring:message code="reservation.searchFor.arrivalOn" />
									</c:when>
									<c:otherwise>
										<spring:message code="reservation.label.folioBalance" />
									</c:otherwise>
								</c:choose>


							</div>
							<div id="divNights" class="col-md-12 s-body">

								<c:choose>
									<c:when test="${depositFrom == 1}">
										<%--  <fmt:formatDate pattern="dd-MMM-yyyy" value="${roomAvailability.arrivalDate}" /> --%>
										<fmt:formatDate pattern="${dateFormat}"
											value="${resHdr.minArrDate}" />
									</c:when>
									<c:otherwise>
								    ${folioBalance}
								 <%--     <fmt:formatDate pattern="dd-MMM-yyyy" value="${checkInHdr.expDepartDate}" />  --%>


									</c:otherwise>
								</c:choose>


							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">


								<c:choose>
									<c:when test="${depositFrom == 1}">

										<spring:message code="checkin.label.reservedType" />

									</c:when>
									<c:otherwise>
										<spring:message code="reservation.label.deposit.departDate" />



									</c:otherwise>
								</c:choose>


							</div>
							<div id="divCorporateTA" class="col-md-12 s-body">



								<c:choose>
									<c:when test="${depositFrom == 1}">

										<c:choose>
											<c:when test="${resHdr.corporateName !=null}">
								${resHdr.corporateName}
							</c:when>
											<c:otherwise>
												<spring:message code="checkin.label.fit" />
											</c:otherwise>
										</c:choose>

									</c:when>
									<c:otherwise>

										<fmt:formatDate pattern="${dateFormat}"
											value="${checkInHdr.expDepartDate}" />

									</c:otherwise>
								</c:choose>



							</div>
						</div>
					</div>
				</div>


			</div>
		</section>
	</div>



	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">


			<div class="panel-body">

				<div class="row">
					<div class="col-lg-12">
						<div class="detail_card col-md-6" id="leftDiv">
							<div class="row dtlHdr bg_colo_text" id="bgColour">
								<div class="col-sm-12">
									<c:choose>
										<c:when test="${depositFrom == 1}">
											<spring:message code="checkin.label.reservedBy" />
										</c:when>
										<c:otherwise>
								     CheckIn 
								</c:otherwise>
									</c:choose>
								</div>

							</div>
							<div class="col-md-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="checkin.label.name" />
									</label> <label class="col-sm-2">:</label> <label
										class="resv-txt col-sm-7">${resHdr.resvByFirstName}
										${resHdr.resvByLastName}</label>
								</div>

							</div>
							<div class="col-sm-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="checkin.label.email" />
									</label> <label class="col-sm-2">:</label> <label
										class="resv-txt col-sm-7">${resHdr.resvByMail}</label>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="reservation.label.phone" /></label> <label class="col-sm-2">:</label>
									<label class="resv-txt col-sm-7">${resHdr.resvByPhone}</label>
								</div>

							</div>
							<div class="col-sm-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="reservation.label.address" /></label> <label class="col-sm-2">:</label>
									<label class="resv-txt col-sm-7">${resHdr.resvByAddress}</label>
								</div>
							</div>
						</div>
						<div class="detail_card col-md-6" id="rightDiv">
							<!-- id="boxHigh" -->

							<c:choose>
								<c:when test="${depositFrom == 1}">
									<div class="row dtlHdr  bg_colo_text" id="bgColour">
										<div class="col-sm-12">
											<spring:message code="checkin.label.reservedFor" />
										</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="row dtlHdr bg_colo_text" id="bgColour">Room
										Details</div>
								</c:otherwise>
							</c:choose>

							<div class="col-md-12">
								<div class="row">
									<label class="col-sm-3"> <c:choose>
											<c:when test="${depositFrom == 1}">
												<spring:message code="checkin.label.name" />
											</c:when>
											<c:otherwise>
								    Room No
								</c:otherwise>
										</c:choose>



									</label> <label class="col-sm-2">:</label> <label
										class="resv-txt col-sm-7"> <c:choose>
											<c:when test="${depositFrom == 1}">
								      ${resHdr.reservedFor}
								</c:when>
											<c:otherwise>
								    ${checkInHdr.roomNumber}  
								</c:otherwise>
										</c:choose>



									</label>
								</div>
							</div>
							<div class="col-sm-12">

								<div class="row">
									<label class="col-sm-3">No of Adults</label> <label
										class="col-sm-2">:</label> <label class="resv-txt col-sm-7">${resHdr.numAdults}</label>
								</div>
							</div>

							<c:choose>
								<c:when test="${depositFrom == 1}">
									<div class="col-md-12">
										<div class="row">
											<%-- <div class="resv-lbl">
												<spring:message code="confirm.label.totalpayable" />:
											</div> --%>
											<label class="col-sm-3">Total Payable</label> <label
												class="col-sm-2">:</label> <label class="resv-txt col-sm-7">${totalPayable}</label>
										</div>
										<%-- <div class="row">
						  <div class="resv-lbl"><spring:message code="confirm.label.totaldeposits" /> </div>
                          <div class="resv-txt">${totalDeposit}  </div>
						</div> --%>
									</div>
									<div class="col-sm-3"></div>
								</c:when>
								<c:otherwise>

									<div class="col-md-12">
										<div class="row">
											<label class="col-sm-3"> <spring:message
													code="reservation.label.nights"></spring:message>

											</label><label class="col-sm-2">:</label> <label
												class="resv-txt col-sm-7">${numNights}</label>
										</div>
									</div>

								</c:otherwise>
							</c:choose>





						</div>
					</div>
				</div>






			</div>

		</section>
	</div>

	<c:if test="${cp_isCanAdd}">

		<div class="col-lg-12">
		<input type="text" value="${folioNo}"  ng-hide="true" id = "show_deposit_folio">
			<section class="panel padding_bottom_zrw">
				<header class="panel-heading module_caption">
					<label class="newdeposit"> <spring:message
							code="reservation.deposit.label.newdeposit" />
					</label> <span class="col-md-2"> <a
						class="btn btn-success showDeposit" id="show_deposit"
						ng-click="showDeposit('','','')"> <spring:message
								code="confirm.label.showdeposits" /></a>
					</span> <span class="tools pull-right"> <a
						class="fa fa-chevron-down" href="javascript:;"></a>
					</span>
				</header>
				<div class="panel-body">
					<div class="form-horizontal tasi-form headerDiv">





						<!-- Deposit dateDate -->

						<div class="form-group">
							<label class="control-label col-md-2"> <spring:message
									code="reservation.deposit.label.mode" /> <span class="red">*</span>
							</label>

							<script>  window.depositFrom1 = ${depositFrom}; </script>

							<c:choose>
								<c:when test="${depositFrom == 1}">

									<script>  window.resvNoforDeposit = ${resHdr.resvNo}; </script>
									<input type="hidden" id="resvNoforDeposit"
										value="${resHdr.resvNo}" />
								</c:when>
								<c:otherwise>
									<script>  window.checkInNoforDeposit = ${checkInHdr.checkInNo}; </script>
									<input type="hidden" id="checkInNoforDeposit"
										value="${checkInHdr.checkInNo}" />
								</c:otherwise>
							</c:choose>




							<div class="col-md-2">


								<select id="txnmode" name="txnmode"
									class="form-control input-sm m-bot15 validator"
									onchange="modeValidation();" ng-model="deposit.payment_mode">
									<option value="">SELECT</option>
									<c:forEach var="item" items="${transactionMode}">
										<option value="${item.key}">${item.value}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-1 checkValid">
								<span id="txnmode_warning" class="switch-right warning_red"
									style="display: none;"><i class="fa fa-warning"></i></span>
							</div>
							<label class="control-label col-md-2" id="rem_label"> <spring:message
									code="reservation.deposit.label.reference" /> <span
								class="red">*</span>
							</label>
							<div class="col-md-3">
								<input type="text"
									class="form-control form-control-inline input-medium"
									id="remarks" ng-model="deposit.remarks">
							</div>
						</div>


						<!-- Amount # -->
						<div class="form-group">
							<label class="control-label col-md-2"> <%-- <spring:message code="reservation.deposit.label.amount" />  --%>
								Security Deposit Amount <span class="red">*</span>
							</label>
							<div class="col-md-2">
								<input type="text" id="depositAmount" min="1"
									class="form-control form-control-inline input-medium validator"
									ng-model="deposit.amount"
									ng-keypress="numericalValidation($event)">
							</div>
							<div class="col-md-1 checkValid">
								<span id="depositAmount_warning"
									class="switch-right warning_red" style="display: none;"><i
									class="fa fa-warning"></i></span>
							</div>
						</div>


						<!-- Received Form # -->
						<div class="form-group">
							<label class="control-label col-md-2"> Received From </label>
							<div class="col-md-2">
								<input type="text"
									class="form-control form-control-inline input-medium"
									ng-model="deposit.receivedForm" id="receivedFrom">
							</div>
							<div class="col-md-1 checkValid">
								<span id="receivedFrom_warning" class="switch-right warning_red"
									style="display: none;"><i class="fa fa-warning"></i></span>
							</div>
						</div>

						<!-- Received Form # -->
						<div class="form-group">
							<label class="control-label col-md-2"> Received By <span
								class="red">*</span>
							</label>
							<div class="col-md-2">
								<input type="text"
									class="form-control form-control-inline input-medium validator"
									id="received Form" value="${userForm.name}" readonly="readonly">
							</div>
							<div class="col-md-1 checkValid">
								<span id="received Form_warning"
									class="switch-right warning_red" style="display: none;"><i
									class="fa fa-warning"></i></span>
							</div>
						</div>


					</div>


				</div>
				<!--  panel body close  -->
			</section>
		</div>

	</c:if>

	<div class="col-lg-12">
		<section class="panel">
			<div class="panel-body">
				<div class="row footer_pagination_main_div">
					<div class="modal-footer align-btn-rgt">
						<!-- <div > -->
						<label class="depositPos">Total Deposit</label>
						<div class="col-md-2" id="depofont">${totalNetDeposit}</div>
						<!-- </div> -->
						<%-- <div class="col-md-2" >	<a class="btn btn-success" id="show_deposit"
								ng-click="showDeposit(${resHdr.folioBindNo},'','')"> <spring:message
									code="confirm.label.showdeposits" /></a></div> --%>
						<%-- <button id="btnPrev" class="btn btn-default" type="button"
							ng-click="cancelDeposit(${depositFrom},${reservationId });">
							<spring:message code="pms.button.back"></spring:message>
						</button> --%>

						<c:if test="${cp_isCanAdd}">
							<button id="btnNext" class="btn btn-success" type="button"
								ng-click="newDeposit(deposit)">
								<spring:message code="pms.btn.update"></spring:message>
							</button>
							<%-- 	<a class="btn btn-success" id="show_deposit"
								ng-click="showDeposit(${resHdr.folioBindNo},'','')"> <spring:message
									code="confirm.label.showdeposits" /></a> --%>
						</c:if>
					</div>
				</div>
			</div>
		</section>
	</div>


	<input type="hidden" id="resvNo"
		value="${roomAvailabilty.reservation_No}">
	<c:import url="../deposit/deposit_list.jsp" />
</div>

<script>
function setHeightofDiv() {
	var height = document.getElementById('leftDiv').offsetHeight;
	$("#rightDiv").height(height);
}
setHeightofDiv();
</script>
