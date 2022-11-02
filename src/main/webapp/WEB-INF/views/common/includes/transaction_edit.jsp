<jsp:directive.include file="page_directives.jsp" />
<section id="container" class="">
	<c:import url="../menu/topMenu.jsp" />
	<section id="main-content">
		<div class="">
			<section class="wrapper">
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12">
						<section class="panel padding_bottom_zrw">
							<div class="panel-body">
								<div class="row">
									<div class="col-md-12">
										<div class="task-progress trans_module_head">

											<c:if test="${backBtnStatusVal==1}">

												<div class="btnbackshift">
													<%-- <a href="<c:url value='${backUrl}' />" class="btn btn-success pull-right backbutna">	
										   <i class="fa fa-arrow-left backbtntop"  > <small> <spring:message code="pms.btn.backtop" /></small></i> 
										 </a>	 --%>
													<a href="<c:url value='${backUrl}' />"
														class="ios-back-button shiftback"
														data-text="<spring:message code="pms.btn.backtop" />"></a>
													<button type="button" class="btn btn-warning backshift"
														style="display: none;" ng-click="newOpenShift()"
														ng-disabled="openshifBtnAction">
														Open Shift <i class="fa fa-plus"></i>
													</button>


												</div>
											</c:if>



											<div style="width: 50%">
												<h1>${moduleName}</h1>
											</div>



										</div>
									</div>
								</div>

							</div>
						</section>

					</div>
				</div>

				<!-- custom page is included here -->
				<c:import url="${customEditIncludeFile}" />
				<!-- custom page is included here -->
			</section>
		</div>
	</section>

</section>
