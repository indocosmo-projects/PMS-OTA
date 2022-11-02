
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
								<section class="panel">
									<header class="panel-heading module_caption">
										<h1>${moduleName}</h1>
										<span class="tools pull-right">
											<a href="javascript:;" class="fa fa-chevron-down"></a>
										</span>
									</header>
									<c:import url="${customEditIncludeFile}" />
									
								</section>
							</section>
						</div>
                    </div>
                    <!-- page end-->
			</section>
		</div>
	</section>
	<footer class="site-footer">
		<div class="text-center">
			2016 &copy; PMS Designed by Hashif Ali. <a class="go-top" href="#">
				<i class="fa fa-angle-up"></i>
			</a>
		</div>
	</footer>

</section>






































































































<%-- <jsp:directive.include file="page_directives.jsp" />
<div class="wrapper">
	
	<div class="con_wrapper">
		<c:import url="../menu/leftMenu.jsp" />
		<div class="rgt_content_main_div">
		
		
		
	<!-- 	
		<div class="main_wrappper">
		<div class="main_wrapper_in">
		
		<div id="logoutBox" class="login_box">
		<div class="login_box_main_warp">
		<div class="login_box_left_name">Name</div>
		<div class="login_box_left_img"></div>  </div>
		<div class="login_box_main_warp_bottom">
		<div class="profle_div"> <div class="logout_img_span"></div>Profile</div> 
		<div class="logout_right">  <div class="logout_img_span"></div>Logout</div> </div> 
		
		</div>
		
		</div></div>  -->

			<div class="rgt_content_div">
				<div class="rgt_content_head_div">
				<!-- <div></div> -->
				
				<!-- <div class="rgt_content_head_top_div">  -->
				<div>
					<c:import url="../menu/topMenu.jsp" />
						 <h2>${moduleName}</h2>
						
					</div>
					<div class="rgt_content_head_top_div">
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
													<button class="advance_search_btn" type="button" onclick="contentSearch()">Search</button>
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
						</div><div class="rgt_content_head_btm_rgt_div">
							 <div class="rgt_menu_top_action_div">
								<ul class="rgt_menu_action_ul">
									<!-- settings start -->
									<li class="banner_btn"><input type="submit" class="add_btn" name="text" onclick="${insertFunction}"></li>
									<!-- settings end -->
									<!-- inbox dropdown start-->
									<li class="banner_btn" id="refresh"><a href="#" class="refresh" onclick="refreshGrid()">Refresh</a></li>
									<!-- inbox dropdown end -->
									<!-- notification dropdown start-->
									<li class="banner_btn" id="notification"><a href="#" class="notification" data-toggle="dropdown"></a></li>
									<!-- notification dropdown end -->
								</ul>
							</div> 
						</div>
					</div>
					
					
					
					</div>
					</div>
			<!-- 	</div> -->
				
				<div>
				
					<c:import url="${customEditIncludeFile}" />
				
				</div>
				</div>
				
				
				
				
				
			</div>

		
		</div>
	</div>
</div></div>


























 --%>