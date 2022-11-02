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
									<c:if test="${backBtnStatusVal==1}">
									
									
									   <%--  <a href="<c:url value='${backUrl}' />" class="btn btn-success pull-right backbutna">	
										 <i class="fa fa-arrow-left backbtntop"  > <small> <spring:message code="pms.btn.backtop" /></small></i> 
										 </a>	 --%>
										 <a href="<c:url value='${backUrl}' />"  class="ios-back-button" data-text="<spring:message code="pms.btn.backtop" />"></a>
									</c:if>	 																	
										<div style="width:50%" > <h1> ${moduleName}</h1>  </div>
										 
										
									</div>
								</div>
								<div class="col-md-6">
									<div
										class="input-group date form_datetime-adv form-control search_box_div">
										<div contenteditable="true" class="form-control serach_div"
											size="16" placeholder="Search...." id="simpleSearchTxt"></div>
										<div class="input-group-btn tools clear_btn">
											<div class="search_claear_btn_main_div">
												<button class="search_claear_btn" onclick="searchBoxClear()">
													<i class="fa fa-times"></i>
												</button>
											</div>
											<div id="search_form_down"
												class="btn date-reset drop_dwn_btn">
												<i class="fa fa-angle-down down_form"></i>
											</div>
											<button type="button" class="search_button btn"
												onclick="simpleSearch()">Search</button>
										</div>
										<div style="display: none;" id="search_fom_div"
											class="search_fom_div">
											<div class="main_search_fom_div">
												<div class="main_search_fom_div_close_btn">
													<a id="search_form_close" href="#"></a>
												</div>
												<section class="panel">
													<div class="panel-body">



														<form method="get"
															class="advance_search_form form drp_dwn_form"
															id="loginForm" action="">

															<jsp:include page="${searchPageURL}"></jsp:include>

															<c:if test="${moduleName == 'Reception'}">
																<jsp:include page="${searchPageURL2}"></jsp:include>
															</c:if>

															<div class="advance_search_content_sub_div">
																<div>
																	<!-- <button type="submit" class="btn btn-danger">Submit</button> -->
																	<button class="advance_search_btn btn btn-danger"
																		type="button" onclick="advanceSearch()">Search</button>
																	<button type="button" id="search_form_cancel"
																		class="btn btn-default" onclick="">Close</button>
																</div>
															</div>
															<div
																onclick="javascript:document.getElementById('loginBox').style.display='none';$('#loginButton').removeClass('active');"
																id="close" class="advance_search_close_div">
																<span class="advance_serach_close_btn"></span>
															</div>
														</form>
													</div>
												</section>
											</div>
										</div>
									</div>
								</div>

								<div class="form-group col-md-2">
									<select class="form-control m-bot15"
										id="reservation_list_sort_ascdsc">
										<option value="resv_date">Reservation Date</option>
										<option value="arr_date">Arrival Date</option>
										<option value="reserved_by">Reserved By</option>
										<option value="resv_status_xlt">Reservation Status</option>
									</select>
								</div>
								<div class="col-md-1">
									<button type="button" class="btn btn-primary list_sort">
										<i class="fa fa-sort-amount-asc" aria-hidden="true"></i>
									</button>
								</div>
								<div class="col-md-3" style="text-align: right;">
									<div class="form-inline">
									 
									<%--  ${cp_isCanAdd}  --%>
									  
									<c:if test="${cp_isCanAdd}">
									   <div class="form-group tab_add_btn">
											<button type="button" class="btn btn-warning"
												onclick="${insertFunction}">
												<i class="fa fa-plus"></i>
											</button>
										</div>
   
                                     </c:if>
									

										
									</div>
								</div>
							</div>
						</section>

					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
					<input type="hidden" id="totalCount" value="${recordCount}" />
					<form id="${formId}" name="${formName}" action="${formAction}"></form>

						<c:if test="${moduleName == 'Reception'}">
							<section class="panel">
								<div class="modal-body">
									<header class="panel-heading">
										<ul class="nav nav-tabs">
											<li class="active"><a href="#tab" data-toggle="tab"
												data-tabname="inHouse" aria-expanded="true">IN-HOUSE</a></li>
											<li class=""><a href="#tab" data-toggle="tab"
												data-tabname="expected" aria-expanded="false">EXPECTED
													ARRIVALS</a></li>
											<li class=""><a href="#tab" data-toggle="tab"
												data-tabname="checkOut" aria-expanded="false">CHECK-OUT</a></li>
													
										</ul>
									</header>
								</div>
							</section>
						</c:if>

						<div id="${mainDivid}"></div>
					<div id="form_content_div" style="display: none;"></div>
					<div id="tempsession">${session}</div>
				
					<%--
						<section class="panel">
							<div class="modal-body modal_body_tabs">

								<c:if test="${moduleName == 'Reception'}">
									<header class="panel-heading">
										<ul class="nav nav-tabs">
											<li class="active"><a href="#tab" data-toggle="tab"
												data-tabname="inHouse" aria-expanded="true">IN-HOUSE</a></li>
											<li class=""><a href="#tab" data-toggle="tab"
												data-tabname="expected" aria-expanded="false">EXPECTED
													ARRIVALS</a></li>
										</ul>
									</header>
								</c:if>
								
								<div class="panel-body">
									<div class="tab-content">
										<div id="tab" class="tab-pane page_tab_content active">
											<input type="hidden" id="totalCount" value="${recordCount}" />
											<form id="${formId}" name="${formName}"
												action="${formAction}"></form>
											<div id="${mainDivid}" class="${mainDivclass}"></div>
											<div id="form_content_div"></div>
											<div id="tempsession">${session}</div>
										</div>
					<!-- 			<div class="page_tab_content_table_div">
											<table class="table">
												<tbody>
													<tr>
														<td>
															<p>
																<button type="button" class="btn btn-success">
																	<i class="fa fa-check list-n-fa-1"></i>Confirmed
																</button>
															</p>
															<p id="id_tag" class="id_tag">
																<span>1</span>
															</p>
															<p id="resv_date_tag" class="resv_date_tag">
																<span>2015-01-01</span>
															</p>
															<p id="corp_name_tag" class="corp_name_tag">
																<strong>Ranjit </strong>
															</p>
															<p id="resv_by_name_tag" class="resv_by_name_tag">
																<span>Ranjith k </span>
															</p>
														</td>
														<td>
															<p id="resvfor_tag" class="resvfor_tag">
																<strong>q</strong>
															</p>
															<p id="arrival_date_tag" class="arrival_date_tag">
																<span>2016-02-17 </span>
															</p>
															<p id="num_rooms_tag" class="num_rooms_tag">
																<span>4Rooms /-7 Night </span>
															</p>
															<p id="folio_balance_tag" class="folio_balance_tag">
																<span>0.00 </span>
															</p>
															<p id="guest_info_tag" class="guest_info_tag">
																<a href="#">Guest info </a>
															</p>
														</td>
														<td><a class="deposit-bg" href="new_reservation.html">
																<i class="fa fa-money list-n-fa-1"></i> <strong>Deposit</strong>
														</a></td>
														<td><a class="remove-bg" href="#"> <i
																class="fa fa-remove list-n-fa-1"></i> <strong>Cancel</strong>
														</a></td>
														<td><a class="confirm-bg" href="#"> <i
																class="fa fa-check list-n-fa-1"></i> <strong>Confirm</strong>
														</a></td>
														<td><a class="chk_in-bg" href="#"> <i
																class="fa fa-check-square list-n-fa-1"></i> <strong>Check-In</strong>
														</a></td>
													</tr>
												</tbody>
											</table>
										</div> -->
									<!-- 	<div class="page_tab_content_table_div">
											<table class="table">
												<tbody>
													<tr>
														<td>
															<p>
																<button type="button" class="btn btn-warning">
																	<i class="fa fa-thumbs-down list-n-fa-1"></i>Confirmed
																</button>
															</p>
															<p id="id_tag" class="id_tag">
																<span>1</span>
															</p>
															<p id="resv_date_tag" class="resv_date_tag">
																<span>2015-01-01</span>
															</p>
															<p id="corp_name_tag" class="corp_name_tag">
																<strong>Ranjit </strong>
															</p>
															<p id="resv_by_name_tag" class="resv_by_name_tag">
																<span>Ranjith k </span>
															</p>
														</td>
														<td>
															<p id="resvfor_tag" class="resvfor_tag">
																<strong>q</strong>
															</p>
															<p id="arrival_date_tag" class="arrival_date_tag">
																<span>2016-02-17 </span>
															</p>
															<p id="num_rooms_tag" class="num_rooms_tag">
																<span>4Rooms /-7 Night </span>
															</p>
															<p id="folio_balance_tag" class="folio_balance_tag">
																<span>0.00 </span>
															</p>
															<p id="guest_info_tag" class="guest_info_tag">
																<a href="#">Guest info </a>
															</p>
														</td>
														<td><a class="deposit-bg" href="new_reservation.html">
																<i class="fa fa-money list-n-fa-1"></i> <strong>Deposit</strong>
														</a></td>
														<td><a class="remove-bg" href="#"> <i
																class="fa fa-remove list-n-fa-1"></i> <strong>Cancel</strong>
														</a></td>
														<td><a class="confirm-bg" href="#"> <i
																class="fa fa-check list-n-fa-1"></i> <strong>Confirm</strong>
														</a></td>
														<td><a class="chk_in-bg" href="#"> <i
																class="fa fa-check-square list-n-fa-1"></i> <strong>Check-In</strong>
														</a></td>
													</tr>
												</tbody>
											</table>
										</div> --> 
							<!-- 			<div class="page_tab_content_table_div">
											<table class="table">
												<tbody>
													<tr>
														<td>
															<p>
																<button type="button" class="btn btn-success">
																	<i class="fa fa-check list-n-fa-1"></i>Confirmed
																</button>
															</p>
															<p id="id_tag" class="id_tag">
																<span>1</span>
															</p>
															<p id="resv_date_tag" class="resv_date_tag">
																<span>2015-01-01</span>
															</p>
															<p id="corp_name_tag" class="corp_name_tag">
																<strong>Ranjit </strong>
															</p>
															<p id="resv_by_name_tag" class="resv_by_name_tag">
																<span>Ranjith k </span>
															</p>
														</td>
														<td>
															<p id="resvfor_tag" class="resvfor_tag">
																<strong>q</strong>
															</p>
															<p id="arrival_date_tag" class="arrival_date_tag">
																<span>2016-02-17 </span>
															</p>
															<p id="num_rooms_tag" class="num_rooms_tag">
																<span>4Rooms /-7 Night </span>
															</p>
															<p id="folio_balance_tag" class="folio_balance_tag">
																<span>0.00 </span>
															</p>
															<p id="guest_info_tag" class="guest_info_tag">
																<a href="#">Guest info </a>
															</p>
														</td>
														<td><a class="deposit-bg" href="new_reservation.html">
																<i class="fa fa-money list-n-fa-1"></i> <strong>Deposit</strong>
														</a></td>
														<td><a class="remove-bg" href="#"> <i
																class="fa fa-remove list-n-fa-1"></i> <strong>Cancel</strong>
														</a></td>
														<td><a class="confirm-bg" href="#"> <i
																class="fa fa-check list-n-fa-1"></i> <strong>Confirm</strong>
														</a></td>
														<td><a class="chk_in-bg" href="#"> <i
																class="fa fa-check-square list-n-fa-1"></i> <strong>Check-In</strong>
														</a></td>
													</tr>
												</tbody>
											</table>
										</div>
									</div> -->
							<!-- 		<div id="desc" class="tab-pane page_tab_content active">
										<div class="page_tab_content_table_div">
											<table class="table">
												<tbody>
													<tr>
														<td>
															<p>
																<button type="button" class="btn btn-warning">
																	<i class="fa fa-thumbs-down list-n-fa-1"></i>Confirmed
																</button>
															</p>
															<p id="id_tag" class="id_tag">
																<span>1</span>
															</p>
															<p id="resv_date_tag" class="resv_date_tag">
																<span>2015-01-01</span>
															</p>
															<p id="corp_name_tag" class="corp_name_tag">
																<strong>Ranjit </strong>
															</p>
															<p id="resv_by_name_tag" class="resv_by_name_tag">
																<span>Ranjith k </span>
															</p>
														</td>
														<td>
															<p id="resvfor_tag" class="resvfor_tag">
																<strong>q</strong>
															</p>
															<p id="arrival_date_tag" class="arrival_date_tag">
																<span>2016-02-17 </span>
															</p>
															<p id="num_rooms_tag" class="num_rooms_tag">
																<span>4Rooms /-7 Night </span>
															</p>
															<p id="folio_balance_tag" class="folio_balance_tag">
																<span>0.00 </span>
															</p>
															<p id="guest_info_tag" class="guest_info_tag">
																<a href="#">Guest info </a>
															</p>
														</td>
														<td><a class="deposit-bg" href="new_reservation.html">
																<i class="fa fa-money list-n-fa-1"></i> <strong>Deposit</strong>
														</a></td>
														<td><a class="remove-bg" href="#"> <i
																class="fa fa-remove list-n-fa-1"></i> <strong>Cancel</strong>
														</a></td>
														<td><a class="confirm-bg" href="#"> <i
																class="fa fa-check list-n-fa-1"></i> <strong>Confirm</strong>
														</a></td>
														<td><a class="chk_in-bg" href="#"> <i
																class="fa fa-check-square list-n-fa-1"></i> <strong>Check-In</strong>
														</a></td>
													</tr>
												</tbody>
											</table>
										</div> -->
							<!-- 			<div class="page_tab_content_table_div">
											<table class="table">
												<tbody>
													<tr>
														<td>
															<p>
																<button type="button" class="btn btn-success">
																	<i class="fa fa-check list-n-fa-1"></i>Confirmed
																</button>
															</p>
															<p id="id_tag" class="id_tag">
																<span>1</span>
															</p>
															<p id="resv_date_tag" class="resv_date_tag">
																<span>2015-01-01</span>
															</p>
															<p id="corp_name_tag" class="corp_name_tag">
																<strong>Ranjit </strong>
															</p>
															<p id="resv_by_name_tag" class="resv_by_name_tag">
																<span>Ranjith k </span>
															</p>
														</td>
														<td>
															<p id="resvfor_tag" class="resvfor_tag">
																<strong>q</strong>
															</p>
															<p id="arrival_date_tag" class="arrival_date_tag">
																<span>2016-02-17 </span>
															</p>
															<p id="num_rooms_tag" class="num_rooms_tag">
																<span>4Rooms /-7 Night </span>
															</p>
															<p id="folio_balance_tag" class="folio_balance_tag">
																<span>0.00 </span>
															</p>
															<p id="guest_info_tag" class="guest_info_tag">
																<a href="#">Guest info </a>
															</p>
														</td>
														<td><a class="deposit-bg" href="new_reservation.html">
																<i class="fa fa-money list-n-fa-1"></i> <strong>Deposit</strong>
														</a></td>
														<td><a class="remove-bg" href="#"> <i
																class="fa fa-remove list-n-fa-1"></i> <strong>Cancel</strong>
														</a></td>
														<td><a class="confirm-bg" href="#"> <i
																class="fa fa-check list-n-fa-1"></i> <strong>Confirm</strong>
														</a></td>
														<td><a class="chk_in-bg" href="#"> <i
																class="fa fa-check-square list-n-fa-1"></i> <strong>Check-In</strong>
														</a></td>
													</tr>
												</tbody>
											</table>
										</div> -->
								<!-- 		<div class="page_tab_content_table_div">
											<table class="table">
												<tbody>
													<tr>
														<td>
															<p>
																<button type="button" class="btn btn-warning">
																	<i class="fa fa-thumbs-down list-n-fa-1"></i>Confirmed
																</button>
															</p>
															<p id="id_tag" class="id_tag">
																<span>1</span>
															</p>
															<p id="resv_date_tag" class="resv_date_tag">
																<span>2015-01-01</span>
															</p>
															<p id="corp_name_tag" class="corp_name_tag">
																<strong>Ranjit </strong>
															</p>
															<p id="resv_by_name_tag" class="resv_by_name_tag">
																<span>Ranjith k </span>
															</p>
														</td>
														<td>
															<p id="resvfor_tag" class="resvfor_tag">
																<strong>q</strong>
															</p>
															<p id="arrival_date_tag" class="arrival_date_tag">
																<span>2016-02-17 </span>
															</p>
															<p id="num_rooms_tag" class="num_rooms_tag">
																<span>4Rooms /-7 Night </span>
															</p>
															<p id="folio_balance_tag" class="folio_balance_tag">
																<span>0.00 </span>
															</p>
															<p id="guest_info_tag" class="guest_info_tag">
																<a href="#">Guest info </a>
															</p>
														</td>
														<td><a class="deposit-bg" href="new_reservation.html">
																<i class="fa fa-money list-n-fa-1"></i> <strong>Deposit</strong>
														</a></td>
														<td><a class="remove-bg" href="#"> <i
																class="fa fa-remove list-n-fa-1"></i> <strong>Cancel</strong>
														</a></td>
														<td><a class="confirm-bg" href="#"> <i
																class="fa fa-check list-n-fa-1"></i> <strong>Confirm</strong>
														</a></td>
														<td><a class="chk_in-bg" href="#"> <i
																class="fa fa-check-square list-n-fa-1"></i> <strong>Check-In</strong>
														</a></td>
													</tr>
												</tbody>
											</table>
										</div> -->
					<!-- 					<div class="page_tab_content_table_div">
											<table class="table">
												<tbody>
													<tr>
														<td>
															<p>
																<button type="button" class="btn btn-success">
																	<i class="fa fa-check list-n-fa-1"></i>Confirmed
																</button>
															</p>
															<p id="id_tag" class="id_tag">
																<span>1</span>
															</p>
															<p id="resv_date_tag" class="resv_date_tag">
																<span>2015-01-01</span>
															</p>
															<p id="corp_name_tag" class="corp_name_tag">
																<strong>Ranjit </strong>
															</p>
															<p id="resv_by_name_tag" class="resv_by_name_tag">
																<span>Ranjith k </span>
															</p>
														</td>
														<td>
															<p id="resvfor_tag" class="resvfor_tag">
																<strong>q</strong>
															</p>
															<p id="arrival_date_tag" class="arrival_date_tag">
																<span>2016-02-17 </span>
															</p>
															<p id="num_rooms_tag" class="num_rooms_tag">
																<span>4Rooms /-7 Night </span>
															</p>
															<p id="folio_balance_tag" class="folio_balance_tag">
																<span>0.00 </span>
															</p>
															<p id="guest_info_tag" class="guest_info_tag">
																<a href="#">Guest info </a>
															</p>
														</td>
														<td><a class="deposit-bg" href="new_reservation.html">
																<i class="fa fa-money list-n-fa-1"></i> <strong>Deposit</strong>
														</a></td>
														<td><a class="remove-bg" href="#"> <i
																class="fa fa-remove list-n-fa-1"></i> <strong>Cancel</strong>
														</a></td>
														<td><a class="confirm-bg" href="#"> <i
																class="fa fa-check list-n-fa-1"></i> <strong>Confirm</strong>
														</a></td>
														<td><a class="chk_in-bg" href="#"> <i
																class="fa fa-check-square list-n-fa-1"></i> <strong>Check-In</strong>
														</a></td>
													</tr>
												</tbody>
											</table>
										</div> -->
									</div>
								</div>
							</div>
						</section>
					 --%>
					 </div>
				</div>
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
											<form role="form">
												<div class="pag_input col-lg-7">
													<input type="text" name="page_slct" id="pageNos"
														class="form-control" value="01">
												</div>
												<button type="submit" class="btn btn-success"
													onclick="pageSearch()">go</button>
											</form>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>
				<!-- page end-->
			</section>
		</div>
	</section>
	
</section>

