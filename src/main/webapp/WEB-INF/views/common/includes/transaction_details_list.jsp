<jsp:directive.include file="page_directives.jsp" />


<section id="container" class="">
	<c:import url="../menu/topMenu.jsp" />
	<section id="main-content" class="21eee">
		<div class="">
			<section class="wrapper">
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12">
						<section class="panel padding_bottom_zrw">
							<div class="panel-body">
								<div class="col-md-12 task-progress module_head">
									<div class="col-md-6 ">
										<h1>${moduleName}</h1>
										</div>
										
										<div class="col-md-6" align="right">
										<button type="button" class="btn btn-default btn-xs"
												onclick="previousPage();">
												<i class="fa fa-arrow-left" aria-hidden="true"></i>
												BACK
											</button>
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
												<!-- <div class="main_search_fom_div_close_btn">
													<a id="search_form_close" href="#"></a>
												</div> -->
												<!-- <div< -->
												<section class="panel">
													<div class="panel-body">
														<div>
															
                                                               
                                                                
                                                                
                                                                
															<jsp:include page="${searchPageURL}"></jsp:include>




															<button type="button" class="btn btn-danger"
																onclick="contentSearch()">Submit</button>
															<button type="button" id="search_form_cancel" class="btn btn-default"
																onclick="">Close</button>
															
														</div>
														
														
														
													
							
					
														
														
														
														

													</div>
												</section>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6"  align="right">
								
								
								
									<div class="form-inline">
									<div class="buttons-searches"><!-- class in transaction.css for buttons and other search options -->
									
									
										<div class="form-group tab_add_btn"><!-- button div starts -->
										<button type="button" class="btn btn-primary" value="New Posting" onclick="${insertFunction}" > 
										<i class="fa fa-plus"></i>
										</button>
										<button type="button" class="btn btn-warning"
												onclick="refreshGrid()">
												<i class="fa fa-refresh"></i>
											</button>
										  
										</div><!-- button div ends -->
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
<div class="informatition_form_sub_title ">
<span class="title">ROOM NO : ${roomNum} &nbsp &nbsp &nbspFOLIO BALANCE :${folBlnc}  &nbsp &nbsp &nbsp  NAME :${customerName}</span>
</div>
					</div>
				</div>

				<input type="hidden" value="10" id="totalCount">
				<input type="hidden" id="dateFormat" value="${dateFormat}">
				
				<input type="hidden" id="folioNo_txn" value=" ${folioNo} "/>
				
				<!-- grid data comes here -->
				<div class="" id="form_content_div">
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
													onclick="pageSearch()">go</button>
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


<%-- <jsp:directive.include file="page_directives.jsp" /> --%>

<%-- <div id="container" class="">
<c:import url="../menu/topMenu.jsp" />

<!--  <section id="main-content"> -->
	<!-- 	<div class="container"> -->
			<!-- <section class="wrapper"> -->
			<div class="wrapper">
				<!-- page start-->
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<div class="panel-body">
								<div class="col-md-12">
									<div class="task-progress module_head">
										<h1>${moduleName}</h1>
									</div>
								</div>
								<div class="col-md-6">
									<div
										class="input-group date form_datetime-adv form-control search_box_div">
										<div contenteditable="true" class="form-control serach_div"
											size="16" placeholder="Search...."></div>
										<div class="input-group-btn tools clear_btn">
											<div class="search_claear_btn_main_div">
												<button class="search_claear_btn">
													<i class="fa fa-times"></i>
												</button>
											</div>
											<div id="search_form_down"
												class="btn date-reset drop_dwn_btn">
												<i class="fa fa-angle-down down_form"></i>
											</div>
											<button type="button" class="search_button btn">Search</button>
										</div>
										<div style="display: none;" id="search_fom_div"
											class="search_fom_div">
											<div class="main_search_fom_div">
												<div class="main_search_fom_div_close_btn">
													<a id="search_form_close" href="#"></a>
												</div>
												<section class="panel">
													<div class="panel-body">
													
													
													
									<form method="get" class="advance_search_form form drp_dwn_form" id="loginForm" action="">
											
											<jsp:include page="${searchPageURL}"></jsp:include>
											
											<div class="advance_search_content_sub_div">
												<div>
												<!-- <button type="submit" class="btn btn-danger">Submit</button> -->
													<button class="advance_search_btn btn btn-danger" type="button" onclick="advanceSearch()">Search</button>
												</div>
											</div>
											<div onclick="javascript:document.getElementById('loginBox').style.display='none';$('#loginButton').removeClass('active');" id="close" class="advance_search_close_div">
												<span class="advance_serach_close_btn"></span>
											</div>
										</form>
													
													
														

													</div>
												</section>
											</div>
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<form class="form-inline" role="form">
									
										<div class="rgt_content_head_btm_rgt_div">
						
							 <div class="transaction_lst_btns">
						
							
							    <input type="checkbox" id="cbShowTrans" >SHOW-TRANSFERRED </br>
								<input type="checkbox" id="cbshowDelete">SHOW-DELETED
							
					<select id="search_drpdwn" class="search_drpdown"  onchange="sortingInGrid();">
					<option value="1">Amount</option>
  						<option value="2">Transaction Date</option>
  						<option value="3">Transaction Status</option>
					</select>
					
				

				
				
							
							<div class="form-group tab_asc page_tab_btn">
											<ul class="nav nav-tabs pull-right">
												<li class="active"><a aria-expanded="false"
													data-toggle="tab" onclick="sort(this,'txn_desc')"> Asc </a></li>
												<li class=""><a aria-expanded="false" data-toggle="tab"
													onclick="sort(this,'txn_asc')"> Desc </a></li>
											</ul>
										</div>
					
					<div class="form-group tab_add_btn">
											<button type="button" class="btn btn-danger" onclick="${insertFunction}">
												<i class="fa fa-plus"></i>Posting
											</button>
										</div>
					
				
								<ul class="rgt_menu_action_ul">
								
								</ul>
							</div> 
							
						</div>
										
									
									</form>
								</div>
							</div>
						</section>

					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<section class="panel">
							<div class="panel-body">
								<div class="tab-content">
									<div id="asc" class="tab-pane page_tab_content active">
										
										
										
										
										 <input type="hidden" id="totalCount" value="${recordCount}"/>
						<form id="${formId}" name="${formName}" action="${formAction}"></form>
						<div id="${mainDivid}" class="${mainDivclass}"></div>
					<div id="form_content_div"></div>
                    <div id="tempsession">${session}</div>
										
										
										
										
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<section class="panel">
							<div class="panel-body">
								<div class="row footer_pagination_main_div">
									<div class="col-lg-4">
										<div id="result"
											class="dataTables_info col-lg-8 footer_pagination"><!-- Showing
											1 to 10 of 44 entrie --></div>
									<!-- 		<div id="result" class="jqGrid_result"></div> -->
										<div class="col-lg-3">
											<select class="form-control m-bot15" id="rowCounter" onchange="pagination_rowLimit()">
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
				                <div class="dataTables_paginate paging_bootstrap pagination">
				                  <div class="numve_selct">
				
				</div>
			    <div id="pagination_div" class="dataTables_paginate paging_bootstrap pagination"></div>
				</div>
			    </div>
										
											</div>
										</div>
										<div class="col-lg-3">
											<form role="form">
												<div class="pag_input col-lg-7">
													<input type="text" name="page_slct" id="pageNos" class="form-control" value="01">
												</div>
												<button type="submit" class="btn btn-success" onclick="pageSearch()">go</button>
											</form>
										</div>
									</div>
								</div>
							</div>
						</section>
					</div>
				</div>
				<!-- page end-->
			<!-- </section> -->
			</div>
		<!-- </div> -->
	</div>
	
	
	
	
	
	
	<footer class="site-footer">
		<div class="text-center">
			2016 &copy; PMS Designed by Hashif Ali. <a class="go-top" href="#">
				<i class="fa fa-angle-up"></i>
			</a>
		</div>
	</footer> --%>
<!-- NEW LIST ENDS -->
<!-- </section> -->





<!--DONT DELETE BELOW PORTION(now in correcting design)  -->


<%-- <div class="wrapper">
	
	<div class="con_wrapper">
		<div class="rgt_content_main_div">
		
		
		
			
		 <div class="main_wrappper">
		<div class="main_wrapper_in">
		
	 	
		
		</div></div>  

			<div class="rgt_content_div">
				<div class="rgt_content_head_div">
				
				

		
		

		
				
				<div>
					<c:import url="../menu/topMenu.jsp" />
				
						
					</div>
					<div class="rgt_content_head_top_div">
					<div class="main_wrappper">
		<div class="main_wrapper_in " >
				<h2>${moduleName}</h2></div>
				</div></div>
					
					
					<div class="rgt_content_head_btm_div">
					<div class="main_wrappper">
		<div class="main_wrapper_in">
						<div class="rgt_content_head_btm_lft_div">
							<div class="search_box_div">
								<div id="simpleSearchTxt" class="search_box_input medium" contenteditable="true" placeholder="Search...."></div>
								<!-- <!-- 
	  <input type="text" placeholder="Search..."class="" aria-controls="editable-sample"> -->
								<div class="search_clear_btn">
									<a href="#" onclick="searchBoxClear()"> X </a>
								</div>
								<div class="dropdown_advance_search_main_div">
									<a id="advance_search_dropdown_btn"> <span></span></a>
									<div id="advance_search_box_div">
										<form method="get" class="advance_search_form  drp_dwn_form" id="loginForm" action="">
											
											<jsp:include page="${searchPageURL}"></jsp:include>
											
											<div class="advance_search_content_sub_div">
												<div>
													<button class="advance_search_btn" type="button" onclick="advanceSearch()">Search</button>
												</div>
											</div>
											<div onclick="javascript:document.getElementById('loginBox').style.display='none';$('#loginButton').removeClass('active');" id="close" class="advance_search_close_div">
												<span class="advance_serach_close_btn"></span>
											</div>
										</form>
									</div>
								</div>
							</div>
							<button type="button" class="search_btn search_btn_info" onclick="simpleSearch()">Search</button>
						</div>
						
						<div class="rgt_content_head_btm_rgt_div">
						
							 <div class="transaction_lst_btns">
						
							
							    <input type="checkbox" id="cbShowTrans" >SHOW-TRANSFERRED </br>
								<input type="checkbox" id="cbshowDelete">SHOW-DELETED
							
					<select id="search_drpdwn" class="search_drpdown"  onchange="sortingInGrid();">
					<option value="1">Amount</option>
  						<option value="2">Transaction Date</option>
  						<option value="3">Transaction Status</option>
					</select>
					
				

				
				
                        <div class="btn-group" tabindex="0">
                      
                        
								<a class="btn active btn-default btn-success"
									onclick="sort(this,'txn_desc')"
									id="txn_asc">ASC</a> <a class="btn btn-default"
									onclick="sort(this,'txn_asc')"
									id="txn_desc">DESC</a>
									
							<!--   <input type="hidden" id="sortVal" class="sortVal"> -->
							</div>
					
					
					
					
					
					
								
								 <input type="button" class="pms_posting_btn_trnsction" value="New Posting" onclick="${insertFunction}" /> 
							
								<ul class="rgt_menu_action_ul">
								
								</ul>
							</div> 
							 
							
							
							<div class="reciept1_details">
							<table><tr><td class="reciept_table_def">ROOM:302</td><td class="reciept_table_def">FOLIO BALANCE:99,999.00</td><td class="reciept_table_def">VISHNU MOHAN</td></tr></table>
							</div>
						</div>
					</div>
					</div>
					</div>
			
			<div class="content_wrapper">
					<div class="main_wrappper">
					
                      <input type="hidden" id="totalCount" value="${recordCount}"/>
						<form id="${formId}" name="${formName}" action="${formAction}"></form>
						<div id="${mainDivid}" class="${mainDivclass}"></div>
					<div id="form_content_div"></div>
                    <div id="tempsession">${session}</div>
					</div>
		 
					
					
						<div class="footer_btm_div" id="footer_btm_div">
						<div class="main_wrappper">
		<div class="main_wrapper_in">
				<div class="jqGrid_pagination_btm_div">
					<div class="jqGrid_lft_pos_pagination_div">
						<div class="jqGrid_content_info_div" id="dynamic-table_info">
							<div id="result" class="jqGrid_result"></div>
							<select class="jqGrid_sort_dropdown" id="rowCounter" onchange="pagination_rowLimit()">
								<option>10</option>
								<option>20</option>
								<option>30</option>
								<option>40</option>
								<option>50</option>
								<option>100</option>
							</select>
						</div>
					</div>
					<div class="jqGrid_rgt_pos_pagination_div">
						<div class="dataTables_paginate paging_bootstrap pagination">
							<div class="numve_selct">
								<input type="text" name="page_slct" id="pageNos" class="page_slct" placeholder="Page" /> <input type="submit" name="go" value="Go" class="go_btn" onclick="pageSearch()" />
							</div>
							<div id="pagination_div" class="dataTables_paginate paging_bootstrap pagination"></div>
						</div>
					</div>
				</div>
				<!-- <footer class="site-footer">
					<div class="text-center">2015 &copy; Indocosmo Systems PVT. LTD.</div>
				</footer> -->
			</div> </div> </div> 
				</div>
			</div>

		
		</div>
	</div>
</div></div> --%>