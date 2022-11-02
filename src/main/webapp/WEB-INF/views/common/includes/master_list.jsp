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
								<div class="col-md-12">
									<div class="task-progress module_head">	
									    <%-- <a href="<c:url value='/dashboard' />" class="btn btn-success pull-right backbutna">	
										 <i class="fa fa-arrow-left backbtntop"  > <small> <spring:message code="pms.btn.backtop" /></small></i> 
										 </a> --%>
										  <%-- <a href="<c:url value='/dashboard' />"  class="ios-back-button" data-text="<spring:message code="pms.btn.backtop" />"></a> --%>
																										
										<div style="width:50%" > 
										     <h1> ${moduleName}
										            
										      </h1> 
										  </div>
										 
										 
										
																														
									</div>
								</div>
								<div class="col-md-6">
									<div maxlength="10"
										class="input-group date form_datetime-adv form-control search_box_div">
										<div contenteditable="true" class="form-control serach_div search_new" 
											 id="simpleSearchTxt"></div>
										<div class="input-group-btn tools clear_btn">
											<div class="search_claear_btn_main_div">
												<button class="search_claear_btn" onclick="searchBoxClear()">
													<i class="fa fa-times"></i>
												</button>
											</div>
											<!-- <div id="search_form_down"
												class="btn date-reset drop_dwn_btn">
												<i class="fa fa-angle-down down_form" id="drpdwn"></i>
											</div> -->
											<button type="button" class="search_button btn"
												onclick="simpleSearch()">Search</button>
										</div>
										<div style="display: none;" id="search_fom_div"
											class="search_fom_div">
											<div class="main_search_fom_div">
												 <!-- <div class="main_search_fom_div_close_btn">
													<a id="search_form_close" href="#"></a>
												</div> -->
												<!-- <div< -->
												<section class="panel">
													<div class="panel-body">
														<div>
															<!-- <div class="form-group">
                                                                <label for="exampleInputEmail1">Code:</label>
                                                                <input id="code" type="text" class="form-control form-control-inline input-medium default-date-picker searchCls" size="16" value="">
                                                                </div>
                                                                <div class="form-group">
                                                                <label for="exampleInputEmail1">Name:</label>
                                                                <input id="name" type="text" class="form-control searchCls"  placeholder="">
                                                                </div> -->
															<jsp:include page="${searchPageURL}"></jsp:include>

														<!-- 	<button type="button" class="btn btn-danger"
																onclick="contentSearch()">Submit</button>
															    <button type="button" id="search_form_cancel" class="btn btn-default"
																onclick="">Close</button> -->
															
														</div>

													</div>
												</section>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6" align="right">
									<div class="form-inline">
										<c:if test="${backBtnStatusVal==1}">
									
									 <a href="../season/seasonCalendar" class="btn pull-left clndr_btn_top" style="margin-right: 10px;">	
										
										<i class="fa fa-calendar" aria-hidden="true"></i> Calendar 
										  
									 </a> 
									</c:if>	
										<div class="form-group tab_add_btn">
										
										  <c:if test="${cp_isCanAdd}">
									          <button type="button" class="btn btn-primary"  id="btnShowAddModal">
												<i class="fa fa-plus"></i> NEW
											  </button> 
                                           </c:if>
										    
										<!-- 	<button type="button" class="btn btn-warning"
												onclick="refreshGrid()">
												<i class="fa fa-refresh"></i>
											</button> -->
										</div>
									</div>
								</div>

								
								<!-- Modal -->
								<div aria-hidden="true" aria-labelledby="myModalLabel"
									role="dialog" tabindex="-1" id="myModal" class="modal fade ">
									<!-- modal data comes here -->
									<div id="content"></div>
									<!-- modal data comes here -->
								</div>
							</div>
						</section>

					</div>
				</div>

				<input type="hidden" value="10" id="totalCount">
				<input type="hidden" id="dateFormat" value="${dateFormat}">
				
				<!-- grid data comes here -->
				<div class="panel" id="form_content_div">
              	<input type="hidden" id="dateFormat" value="${dateFormat}">
				</div>
               <!-- grid data -->

				<div class="row">
					<div class="col-md-12">
						<section class="panel">
							<div class="panel-body">
								<div class="row footer_pagination_main_div">
									<div class="col-lg-4">
										<div id="result"
											class="dataTables_info col-lg-8 footer_pagination">
											<!-- Showing
											1 to 10 of 44 entrie -->
										</div>
										<!-- 		<div id="result" class="jqGrid_result"></div> -->
										<div class="col-lg-3">
											<select class="form-control m-bot15" id="rowCounter"
												onchange="pagination_rowLimit()">
												<option>10</option>
												<option>20</option>
												<option>30</option>
												<option>40</option>
												<option>50</option>
												<option>100</option>
											</select>
										</div>
									</div>



									<div class="col-lg-8">
										<div class="col-lg-9">
											<div class="dataTables_paginate paging_bootstrap pagination">

												<div class="jqGrid_rgt_pos_pagination_div">
													<div
														class="dataTables_paginate paging_bootstrap pagination">
														<div class="numve_selct"></div>
														<div id="pagination_div"
															class="dataTables_paginate paging_bootstrap pagination"></div>
													</div>
												</div>


											</div>
										</div>
										<div class="col-lg-3">
											<div>
												<div class="pag_input col-lg-7">
													<input type="text" name="page_slct" id="pageNos"
														class="form-control" value="01">
												</div>
												<button type="button" class="btn btn-success"
													onclick="pageSearch()">Go</button>
										   </div>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>


			</section>
		</div>
	</section>
	<!-- <footer class="site-footer">
		<div class="text-center">
			2016 &copy; PMS Designed by Hashif Ali. <a class="go-top" href="#">
				<i class="fa fa-angle-up"></i>
			</a>
		</div>
	</footer> -->

</section>

