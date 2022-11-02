<jsp:directive.include file="../common/includes/page_directives.jsp" />
<input type="hidden" value="${dateFormat}" id="dateFormat">

<div class="row" ng-app="pmsApp"
	ng-controller="reservation_confirmController">
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">

			<div class="btnbackshift" id="btnback">
				<a href="<c:url value='${backUrl}' />"
					class="ios-back-button shiftback"
					data-text="<spring:message code="pms.btn.backtop" />"> </a>
				<button type="button" class="btn btn-warning backshift"
					style="display: none;" onclick="backToList();">
					<i class="fa fa-plus"></i>
				</button>
			</div>

			<div class="panel-body">
				<div class="dtls summury">
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.reservation" />
							</div>
							<div id="reser_id" class="col-md-12 s-body">
								${roomAvailability.reservation_No}</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.reservedDate" />
							</div>
							<div id="" class="col-md-12 s-body">
								<fmt:formatDate pattern="${dateFormat}"
									value="${roomAvailability.bookingDate}" />
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="reservation.searchFor.arrivalOn" />
							</div>
							<div id="divNights" class="col-md-12 s-body">
								<fmt:formatDate pattern="${dateFormat}"
									value="${roomAvailability.arrivalDate}" />
							</div>
						</div>
					</div>
					<div class="col-md-3 col-sm-3 s-col">
						<div class="summury_div">
							<div class="col-md-12 s-header">
								<spring:message code="checkin.label.reservedBy" />
							</div>
							<div id="divCorporateTA" class="col-md-12 s-body">
								<c:choose>
									<c:when test="${resHdr.corporateName !=null}">
								${resHdr.corporateName}
							</c:when>
									<c:otherwise>
										<spring:message code="checkin.label.fit" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>

				<%-- <div class="c-dtls">
					<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="col-md-5 c-header">
							<spring:message code="checkin.label.reservation" />
						</div>
						<div class="col-md-7 c-body " id="reser_id">${roomAvailability.reservation_No}</div>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="col-md-5 c-header">
							<spring:message code="checkin.label.reservedDate" />
						</div>
						<div class="col-md-7 c-body  ">
							<fmt:formatDate pattern="dd-MMM-yyyy"
								value="${roomAvailability.bookingDate}" />
						</div>
					</div>
				
				<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="col-md-5 c-header">
							<spring:message code="checkin.label.reservedFor" />
						</div>
						<div class="col-md-7 c-body  " id="confirmBy">${resHdr.resvByFirstName}</div>
					</div>
				
				
					<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="col-md-5 c-header">
							<spring:message code="checkin.label.reservedBy" />
						</div>
						<c:choose>
							<c:when test="${resHdr.corporateName !=null}">
								<div class="col-md-7 c-body">${resHdr.corporateName}</div>
							</c:when>
							<c:otherwise>
								<div class="col-md-7 c-body">
									<spring:message code="checkin.label.fit" />
								</div>
							</c:otherwise>
						</c:choose>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="col-md-5 c-header">
							<spring:message code="pms.label.email" />
						</div>
						<div class="col-md-7 c-body  " >${resHdr.resvByMail}</div>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="col-md-5 c-header">Arrival Date</div>
						<div class="col-md-7 c-body  "><fmt:formatDate pattern="dd-MMM-yyyy"
							value="${roomAvailability.arrivalDate}" /></div>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="col-md-5 c-header">Phone No:</div>
						<div class="col-md-7 c-body  ">${resHdr.resvByPhone}</div>
					</div>
	<div class="col-md-6 col-sm-6 col-xs-6">
						<div class="col-md-5 c-header">
							<spring:message code="checkin.label.status" />
						</div>
						<div class="col-md-7 c-body  ">

							<spring:message code="status.label.unconfirmed" />

						</div>
					</div>
				</div> --%>
			</div>
		</section>
	</div>
	<%-- 	<div class="col-lg-12">
		<section class="panel">
			<!-- SUMMARY -->
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="card.label.summary" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span>
			</header>
			
			<div class="panel-body">
				    <div class="col-md-1">
						<div class="row resv-lbl">Resv#</div>
						<div class="row resv-txt" id="reser_id">${roomAvailability.reservation_No}</div>
					</div>
					
					<div class="col-md-2">
						<div class="row resv-lbl">Name</div>
						<div class="row resv-txt" id="confirmBy">
						${resHdr.resvByFirstName}
						</div>
					
					
					</div>
					<div class="col-md-2">
						<div class="row resv-lbl">Phone</div>
						<div class="row resv-txt">
						${resHdr.resvByPhone}
						</div>
					</div>
					
					<div class="col-md-2">
						<div class="row resv-lbl">Booking Date</div>
						<div class="row resv-txt ">
						<fmt:formatDate pattern="dd-MMM-yyyy" value="${roomAvailability.bookingDate}" /></div></div>
					
					<div class="col-md-1">
						<div class="row resv-lbl">Arrival Date</div>
						<div class="row resv-txt">
						<fmt:formatDate pattern="dd-MMM-yyyy" value="${roomAvailability.arrivalDate}" /></div></div>
					
					
					
					<div class="col-md-2">
						<div class="row resv-lbl">Corporate/TA</div>
						<div class="row resv-txt">
						${roomAvailability.corporate_ta}
						</div>
					
					
					</div>
					
				
					
					<div class="col-md-2">
						<div class="row resv-lbl">status</div>
						<div id="status" class="row resv-txt col-md-12">${roomAvailability.status}</div>
					</div>
					
					
					
					
				</div>	
			
		</section>

	</div> --%>
	<!-- DEPOSITS -->
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">

			<%-- 	<header class="panel-heading module_caption">
				<h1>
					<spring:message code="confirm.label.details" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span>
			</header>
			 --%>
			<div class="panel-body">

				<div class="row">
					<div class="col-lg-12">
						<div class="detail_card col-md-6" id="leftDiv">
							<div class="row dtlHdr bg_colo_text" id="bgColour">
								<spring:message code="checkin.label.reservedFor" />
							</div>
							<div class="col-md-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="checkin.label.name" />
									</label> <label class="col-sm-2">:</label>
									<div class="resv-txt col-sm-7">${resHdr.resvByFirstName}
										${resHdr.resvByLastName}</div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="checkin.label.email" /></label> <label class="col-sm-2">:</label>
									<div class="resv-txt col-sm-7">${resHdr.resvByMail}</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="reservation.label.phone" /></label> <label class="col-sm-2">:</label>
									<div class="resv-txt col-sm-7">${resHdr.resvByPhone}</div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="reservation.label.address" /></label> <label class="col-sm-2">:</label>
									<div class="resv-txt col-sm-7">${resHdr.resvByAddress}</div>
								</div>
							</div>
						</div>
						<div class="detail_card col-md-6" id="rightDiv"
							style="margin-bottom: 5px;">
							<div class="row dtlHdr bg_colo_text" id="bgColour">
								<spring:message code="checkin.label.reservedBy" />
							</div>
							<div class="col-md-12">
								<div class="row">
									<label class="col-sm-3"> <spring:message
											code="checkin.label.name" /></label> <label class="col-sm-2">:</label>
									<div class="resv-txt col-sm-5">${resHdr.reservedFor}</div>
								</div>
							</div>
							<div class="col-sm-12">
								<div class="row">
									<label class="col-sm-3">No of Adults</label> <label
										class="col-sm-2">:</label>
									<div class="resv-txt col-sm-7">${resHdr.numAdults}</div>
								</div>
							</div>
							<div class="col-md-12">
								<div class="row">
									<label class="col-sm-3">Total Payable</label> <label
										class="col-sm-2">:</label>
									<div class="resv-txt col-sm-7">${totalPayable}</div>
								</div>
								<%-- <div class="row">
						  <div class="resv-lbl"><spring:message code="confirm.label.totaldeposits" /> </div>
                          <div class="resv-txt">${totalDeposit}  </div>
						</div> --%>
							</div>
						</div>
					</div>
				</div>







				<%--   
                  <div class="col-md-4" style="background-color:#fff"> 
                     <div class="resv-row">
                          <div class="resv-lbl"> <spring:message code="checkin.label.reservedFor" /> </div>
                          <div class="resv-txt"> ${resHdr.reservedFor} </div>
                     </div>
                     <div class="resv-row">
                         <div class="resv-lbl">  <spring:message code="checkin.label.reservedBy" />  </div>
                         <div class="resv-txt">  ${resHdr.resvByFirstName} ${resHdr.resvByLastName}  </div>
                    </div>
 
                   </div>
                   
                   <div class="col-md-4" style="background-color:#fff"> 
                     <div class="resv-row">
                          <div class="resv-lbl"><spring:message code="pms.label.email" /> </div>
                          <div class="resv-txt">  ${resHdr.resvByMail} </div>
                     </div>
                     <div class="resv-row">
                         <div class="resv-lbl">Phone No:  </div>
                         <div class="resv-txt">  ${resHdr.resvByPhone} </div>
                    </div>
 
                   </div>
                   
                   <div class="col-md-4" style="background-color:#fff"> 
                     <div class="resv-row">
                          <div class="resv-lbl"> <spring:message code="confirm.label.totalpayable" /> </div>
                          <div class="resv-txt">${totalPayable}</div>
                     </div>
                     <div class="resv-row">
                         <div class="resv-lbl"><spring:message code="confirm.label.totaldeposits" /> </div>
                         <div class="resv-txt">${totalDeposit}  <span style="float: right;"> <a  id="show_deposit"
								onclick="depositList(${roomAvailability.reservation_No},'','');"><spring:message
									code="confirm.label.showdeposits" /></a>   </span> </div>
                          
                         
                    </div>
                    
                    
 
                   </div>
               
                --%>

			</div>




			<%--  
			
		  	<div class="panel-body">
				<div class="form-horizontal tasi-form">

                    <div class="form-group">
						<label class="control-label col-md-3">
							<spring:message code="checkin.label.reservedFor" />
						</label>
						<div class="col-md-6 col-xs-11" id="confirmBy">${resHdr.reservedFor}  </div>
					</div>
					
					
					<div class="form-group">
						<label class="control-label col-md-3">
							<spring:message code="checkin.label.reservedBy" />
						</label>
						<div class="col-md-6 col-xs-11" id="confirmBy">${resHdr.resvByFirstName} ${resHdr.resvByLastName} </div>
					</div>
					
					

					<div class="form-group">
						<label class="control-label col-md-3">
							<spring:message code="pms.label.email" />  
						</label>
						<div class="col-md-6 col-xs-11">${resHdr.resvByMail}</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Phone No:  </label>
						<div class="col-md-6 col-xs-11">${resHdr.resvByPhone}</div>
					</div>

					<div class="form-group">
						<label class="control-label col-md-3"><spring:message
								code="confirm.label.totalpayable" /></label>
						<div class="col-md-6 col-xs-11">${totalPayable}</div>
					</div>

					<div class="form-group">
						<label class="control-label col-md-3"><spring:message
								code="confirm.label.totaldeposits" /></label>
						<div class="col-md-2 col-xs-11">${totalDeposit}</div>
						<div class="col-md-3 col-xs-11">
							<a id="show_deposit"
								onclick="depositList(${roomAvailability.reservation_No},'','');"><spring:message
									code="confirm.label.showdeposits" /></a>
						</div>
					</div>
				</div>
			</div>   
			
			--%>






		</section>
	</div>
	<!-- CONFIRM -->
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">

			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="confirm.label.changeReservationStatus" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span>
			</header>
			<div class="panel-body">
				<div class="panel-body">
					<div class="form-horizontal tasi-form">


						<div class="form-group">
							<label class="control-label col-md-1"> <spring:message
									code="confirm.label.references" /></label>
							<div class="col-md-6 col-xs-11">
								<input type="text"
									class="form-control form-control-inline input-medium "
									id="reference" name="reason">
							</div>
						</div>

						<div class="form-group">
							<div class=" col-md-6 ">
								<input id="forConfirm" type="checkbox" class="confirmation">
								<label for="forConfirm"></label>
								<spring:message code="confirm.label.checkboxhtml" />
							</div>
						</div>

					</div>
				</div>

			</div>
		</section>

	</div>


	<div class="col-lg-12">
		<section class="panel">
			<!-- btn section starts -->

			<div class="panel-body">
				<div class="row footer_pagination_main_div">


					<div class="align-btn-rgt">
						<%-- 	<button id="btnPrev" class="btn btn-default" type="button"
							onclick="backToList();">
							<spring:message code="card.label.backbutton"></spring:message>
						</button> --%>

						<%-- <button id="btnNext" class="btn btn-success" type="button"
							onclick="confirmValidation();">
							<spring:message code="confirm.label.confirmbutton"></spring:message>
						</button> --%>
						<button id="btnNext" class="btn btn-success" type="button"
							ng-click="confirmValidation();">
							<spring:message code="confirm.label.confirmbutton"></spring:message>
						</button>




						<%-- <a class="btn btn-success"  id="show_deposit" ng-click="showDeposit(${resHdr.folioBindNo},'','')" > <spring:message code="confirm.label.showdeposits" /></a> --%>


					</div>
				</div>
			</div>
		</section>
	</div>

	<input type="hidden" id="resvNo"
		value="${roomAvailability.reservation_No}">
	<c:import url="../deposit/deposit_list.jsp" />

</div>

<div id="dialog" title="Alert message" style="display: none">
	<div class="ui-dialog-content ui-widget-content">
		<p>
			<span class="ui-icon ui-icon-alert"
				style="float: left; margin: 0 7px 20px 0"></span> <label
				id="lblMessage"> </label>
		</p>
	</div>
</div>


<%-- 
<c:import url="../payment/payment_list.jsp" />
 --%>
<script>
function setHeightofDiv() {
	var height = document.getElementById('leftDiv').offsetHeight;
	$("#rightDiv").height(height);
}
setHeightofDiv();
</script>

