<jsp:directive.include file="../common/includes/page_directives.jsp" />
<div class="row" id="divID" ng-app="resvCancel"
	ng-controller="resvCancelController">
	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="checkin.label.summary" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span>
			</header>

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
								<fmt:formatDate pattern="dd-MMM-yyyy"
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
								<fmt:formatDate pattern="dd-MMM-yyyy"
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
			</div>
		</section>
	</div>

	<div class="col-lg-12">
		<section class="panel padding_bottom_zrw">
			<header class="panel-heading module_caption">
				<h1>
					<spring:message code="cancel.label.title" />
				</h1>
				<span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span>
			</header>
			<div class="panel-body">
				<div class="form-horizontal tasi-form">
					<div class="form-group">
						<label class="control-label col-md-3"> <spring:message
								code="checkin.label.reservedFor" />
						</label>
						<div class="col-md-6 col-xs-11" id="confirmBy">${resHdr.resvByFirstName}</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3"> <spring:message
								code="pms.label.email" />
						</label>
						<div class="col-md-6 col-xs-11">${resHdr.resvByMail}</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3">Phone No:</label>
						<div class="col-md-6 col-xs-11">${resHdr.resvByPhone}</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3"><spring:message
								code="cancel.label.cancelledByandDateTime" /></label>
						<div class="col-md-3 col-xs-11">
							<input type="text"
								class="form-control form-control-inline input-medium "
								id="cancelledBy" value="${userForm.name}" disabled="disabled">
						</div>
						<div class="col-md-3 col-xs-11">
							<input type="text"
								class="form-control form-control-inline input-medium "
								id="dateortime" value="${dateTime}" disabled="disabled">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3"><spring:message
								code="cancel.label.reason" /></label>
						<div class="col-md-3">
							<select name="reason" onclick="callReason();"
								class="form-control input-sm m-bot15" id="rea_drpDown">
								<option value="reason1">reason1</option>
								<option value="reason2">reason2</option>
								<option value="reason3">Other</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3"></label>
						<div class="col-md-5 col-xs-11">
							<textarea class="form-control form-control-inline input-medium "
								id="reason" name="reason"></textarea>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>

	<div class="col-lg-12">
		<section class="panel">
			<div class="panel-body">
				<div class="row footer_pagination_main_div">
					<div class="align-btn-rgt">
						<button id="btnPrev" class="btn btn-default" type="button"
							onclick="backToList();">
							<spring:message code="card.label.backbutton"></spring:message>
						</button>
						<button id="btnNext" class="btn btn-success" type="button"
							onclick="validationCancellation();">
							<spring:message code="cancel.label.cancelreservation"></spring:message>
						</button>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>