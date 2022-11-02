<jsp:directive.include file="../common/includes/page_directives.jsp" />

<link rel="shortcut icon"
	href="../resources/common/images/logos_${companyN}/favicon_niko_logo.ico"
	type="image/x-icon">
<img src="../resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />


<header class="header dark_blue-bg">

	<div class="col-lg-12">
		<div class="navbar-header">
			<button data-target=".navbar-collapse" data-toggle="collapse"
				class="navbar-toggle" type="button">
				<span class="fa fa-bars"></span>
			</button>

			<!--logo start-->
			<!-- 			<a class="logo" href="/pms/dashboard"> <span> Logo </span> </a>
 -->
			<!--logo end-->

			<%-- <a class="logo" href="${rootPath}/dashboard"> --%> 
			<a class="logo" ><img
				src="${rootPath}/resources/common/images/logos_${companyN}/Niko Logo.png"
				style="width: 50px;" id="nikko">
			</a>


			<div class="horizontal-menu navbar-collapse collapse ">
				<c:set var="permissions" scope="request"
					value="${userSession.permissionsList}"></c:set>
				<ul id="top_menu" class="nav navbar-nav">
					<!-- <li class="active" ><a href="/pms/dashboard">Home</a></li> -->
				
					<c:if test="${permissions['DASHBOARD'].canView}">
						<li><a href="${rootPath}/dashboard" id="homenav">Home</a></li>
					</c:if>
					
					<%-- <c:if test="${permissions['DASHBOARD'].canView}">
						<li><a href="/pms/dashboard2"
							id="dashboardnav">Dashboard</a></li>
					</c:if>
					 --%>
					<c:if test="${permissions['PMS_RESV'].canView}">
						<li><a href="${rootPath}/reservation_test/reservationList"
							id="reservationnav">Reservation</a></li>
					</c:if>
					<c:if test="${permissions['PMS_RECPN'].canView}">
						<li><a href="${rootPath}/reception/receptionList"
							id="receptionnav">Reception</a></li>
					</c:if>






					<c:if test="${isCashier}">
						<li class="dropdown"><a href="../setup/tools"
							class="dropdown-toggle" data-hover="dropdown"
							data-toggle="dropdown" id="templatenav">Shift<b
								class=" fa fa-angle-down"></b></a>
							<ul class="dropdown-menu multi-column ">
								<div class="row">
									<div class="col-sm-12">

										<ul class="multi-column-dropdown">

											<c:if
												test="${permissions['PMS_OPENSHIFT'].canView || permissions['PMS_CLOSESHIFT'].canView}">
												<li>
													<div class="mega-nav title_subnav_nav  ">
														<a href="${rootPath}/shiftManagement/openshift"
															class="drop_menu_href">Shift Management</a>
													</div> <c:if test="${permissions['PMS_OPENSHIFT'].canView}">

													</c:if> <!-- 											  data-toggle="modal" data-target="#newOpenShiftmyModal" 
 --> <c:if test="${permissions['PMS_CLOSESHIFT'].canView}">
														<a href="../shiftManagement/closeshift"
															class="drop_menu_href">Close Shift</a>
													</c:if>

												</li>
											</c:if>



										</ul>


									</div>
								</div>
							</ul></li>

					</c:if>
					<%-- 	<c:if test="${permissions['PMS_RECPN_REQUEST'].canView}">
					<li><a href="/pms/checkin_request/addonlist" id="addonnav">Requests</a></li>
					</c:if>
 --%>
					<c:if test="${permissions['PMS_CHKIN_REQUEST'].canView}">
						<li><a href="${rootPath}/requestCheckin/listaddon" id="adnav">Requests</a></li>
					</c:if>


					<c:if
						test="${permissions['REPORTS_EXP_ARR'].canView || permissions['REPORTS_ACT_ARR'].canView
					|| permissions['REPORTS_EXP_DEP'].canView || permissions['REPORTS_ACT_DEP'].canView || permissions['REPORTS_INHOUSE'].canView || permissions['REPORTS_RESERV'].canView 
					|| permissions['REPORTS_RESV_CANCEL'].canView || permissions['REPORTS_TXN'].canView || permissions['REPORTS_TXN_TRNSFR'].canView || permissions['REPORTS_FOLIO'].canView 
					|| permissions['REPORTS_SHIFT'].canView || permissions['REPORTS_SHIFT_TXN'].canView||permissions['REPORTS_REQUEST'].canView}">
						<li><a href="${rootPath}/report/reportList" id="reportsnav">Reports</a></li>
					</c:if>
 
					<%-- <!-- settlement -->
					<li class="dropdown"><a href="../setup/settlement" class="dropdown-toggle"
						data-hover="dropdown" data-toggle="dropdown" id="settlenav" >Settlement
							<b class=" fa fa-angle-down"></b>
					</a>
						
						
						<ul class="dropdown-menu multi-column ">
							<div class="row">
								<div class="col-sm-12">
									<ul class="multi-column-dropdown">
										<c:if test="${permissions['SETTLEMENT'].canView}">
											<li>
												<div class="mega-nav title_subnav_nav ">
													<a href="${rootPath}/settlement/list"
														class="drop_menu_href">Settlement</a>
												</div>
											</li>
										</c:if>
										<c:if test="${permissions['AGING_AR'].canView}">
											<li>
												<div class="mega-nav title_subnav_nav ">
													<a href="${rootPath}/agingAR/list" class="drop_menu_href">AR
														Aging</a>
												</div>
											</li>
										</c:if>
									</ul>
								</div>
							</div>
						</ul> 
						</li>  --%>

					<li class="dropdown"><a href="../setup/tools"
						class="dropdown-toggle" data-hover="dropdown"
						data-toggle="dropdown" id="toolsnav">Tools <b
							class=" fa fa-angle-down"></b></a>
						<ul class="dropdown-menu multi-column ">
							<div class="row">
								<div class="col-sm-12">
									<ul class="multi-column-dropdown">
										<c:if test="${permissions['PMS_NIGHT_AUDT'].canView}">
											<li>
												<div class="mega-nav title_subnav_nav ">
													<a href="${rootPath}/nightAudit/audit"
														class="drop_menu_href">Night Audit</a>
												</div>
											</li>
										</c:if>
										<c:if test="${permissions['PMS_TRANSATION'].canView}">
											<li>
												<div class="mega-nav title_subnav_nav ">
													<a href="${rootPath}/transaction/transfer"
														class="drop_menu_href">Merge Room</a>
												</div>
											</li>
										</c:if>
										<c:if
											test="${permissions['PMS_USR_LIST'].canView || permissions['PMS_USR_GRP'].canView}">
											<li>
												<div class="mega-nav title_subnav ">
													<p class="title">User Management</p>
												</div> <c:if test="${permissions['PMS_USR_LIST'].canView}">
													<a href="${rootPath}/users/list" class="drop_menu_href">Users</a>
												</c:if> <c:if test="${permissions['PMS_USR_GRP'].canView}">
													<a href="${rootPath}/userGroup/list" class="drop_menu_href">User
														Groups</a>
												</c:if>
											</li>
										</c:if>
										<c:if
											test="${permissions['MST_FLTY'].canView || permissions['PMS_RECPN_FLTY_PVDR'].canView}">
											<li>
												<div class="mega-nav title_subnav ">
													<p class="title">Facility</p>
												</div> <c:if test="${permissions['MST_FLTY'].canView}">
													<a href="${rootPath}/facilities/list"
														class="drop_menu_href">Facilities</a>
												</c:if> <c:if test="${permissions['PMS_RECPN_FLTY_PVDR'].canView}">
													<a href="${rootPath}/facilityProvider/list"
														class="drop_menu_href">Facility Provider</a>
												</c:if>
											</li>
										</c:if>
										<!-- for petty cash -->
										<c:if test="${permissions['PETTY_CASH'].canView}">
										<li>
										 <div class="mega-nav title_subnav ">
										 <p class="title">Petty Cash</p>
										 </div>
										 <c:if test="${permissions['PETTY_CASH'].canView}">
										   <a href="${rootPath}/pettycash/list">Petty Cash</a>
										 </c:if>
										</li>
										</c:if>
									<%-- 	<c:if test="${permissions['PMS_COMMTN'].canView}">
											<li>
												<div class="mega-nav title_subnav_nav">
													<a href="${rootPath}/communication/list"
														class="drop_menu_href">Communications</a>
												</div>
											</li>
										</c:if> --%>
									</ul>
								</div>
							</div>
						</ul></li>
					<c:if test="${permissions['PMS_HK'].canView}">
						<li><a href="${rootPath}/housekeeping/houseKeepingList"
							id="housekepingnav"> HK </a></li>
					</c:if>

					<li class="dropdown"><a href="../setup/setup"
						class="dropdown-toggle" data-hover="dropdown"
						data-toggle="dropdown" id="setupnav">Setup <b
							class=" fa fa-angle-down"></b></a>
						<ul class="dropdown-menu multi-column columns-2 ">
							<div class="row">
								<div class="col-sm-6">
									<ul class="multi-column-dropdown">

										<li><div class="mega-nav title_subnav ">
												<p class="title">General</p>
											</div></li>
										<c:if test="${permissions['MST_SYST_SETNG'].canView}">
											<li><a href="${rootPath}/systemSettings/settings"
												class="drop_menu_href">System Settings</a></li>
										</c:if>
										<c:if test="${permissions['MST_DEPTMNT'].canView}">
											<li><a href="${rootPath}/department/departmentList"
												class="drop_menu_href ">Department</a></li>
										</c:if>
										<c:if test="${permissions['MST_CURENCY'].canView}">
											<li><a href="${rootPath}/currency/currencyList"
												class="drop_menu_href ">Currency</a></li>
										</c:if>
										<c:if test="${permissions['MST_ACOUNT_MSTER'].canView}">
											<li><a
												href="${rootPath}/accountMaster/accountMasterList"
												class="drop_menu_href ">Account Master</a></li>
										</c:if>
										<c:if test="${permissions['MST_SEASON'].canView}">
											<li><a href="${rootPath}/season/"
												class="drop_menu_href ">Season</a></li>
										</c:if>
										<c:if test="${permissions['MST_DISCUNT'].canView}">
											<li><a href="${rootPath}/discount/discountList"
												class="drop_menu_href ">Discount</a></li>
										</c:if>
										<c:if test="${permissions['MST_TAX'].canView}">
											<li><a href="${rootPath}/TaxHdr/taxList"
												class="drop_menu_href ">Tax</a></li>
										</c:if>
										<c:if test="${permissions['MST_TEMPLATE'].canView}">
											<li><a href="${rootPath}/templates/list"
												class="drop_menu_href ">Templates</a></li>
										</c:if>
										<%-- <c:if test="${permissions['MST_SRV_TAX'].canView}">
										<li><a href="/pms/serviceTax/edit"
											class="drop_menu_href ">Service Tax</a></li></c:if> --%>
										<%-- <c:if test="${isCashier}"> --%>

										<c:if test="${permissions['MST_SHIFT'].canView}">
											<li><a href="${rootPath}/shift/list"
												class="drop_menu_href ">Shift</a></li>
										</c:if>
										<%-- 											</c:if>
 --%>
									</ul>
								</div>
								<div class="col-sm-6">
									<ul class="multi-column-dropdown">
										<c:if
											test="${permissions['MST_ROOM'].canView || permissions['MST_ROM_TYP'].canView}">
											<li>
												<div class="mega-nav title_subnav ">
													<p class="title">Rooms</p>
												</div> <c:if test="${permissions['MST_ROM_TYP'].canView}">
													<a href="${rootPath}/roomType/roomTypeList"
														class="drop_menu_href">Room Type</a>
												</c:if> <c:if test="${permissions['MST_ROOM'].canView}">
													<a href="${rootPath}/room/roomList" class="drop_menu_href">Room</a>
												</c:if> <c:if test="${permissions['MST_FLR'].canView}">
													<a href="${rootPath}/floor/floorList"
														class="drop_menu_href">Floor</a>
												</c:if>
											</li>
										</c:if>
										<li>
											<div class="mega-nav title_subnav ">
												<p class="title">Tarrifs &amp; Promotions</p>
											</div> <c:if test="${permissions['MST_CORP_RATE'].canView}">
												<a href="${rootPath}/corporateTaRate/corporateRateList"
													class="drop_menu_href">Corporate ta Rates</a>
											</c:if> <c:if test="${permissions['MST_ROOM_RATE'].canView}">
												<a href="${rootPath}/roomRate/roomRateList"
													class="drop_menu_href ">Room Rates</a>
											</c:if> <c:if test="${permissions['MST_CORP'].canView}">
												<a href="${rootPath}/corporate/corporateList"
													class="drop_menu_href ">Corporate</a>
											</c:if> 
											<%-- <c:if test="${permissions['MST_RACK_RATE'].canView}">
												<a href="${rootPath}/rackRate/rackRateList"
													class="drop_menu_href ">Rack Rate</a>
											</c:if> --%>
										</li>
										
										<li>
											<div class="mega-nav title_subnav ">
												<p class="title">Petty Cash</p>
											</div> <c:if test="${permissions['MST_PETTY_HEAD'].canView}">
												<a href="${rootPath}/pettycash/headList"
													class="drop_menu_href">Petty Cash Head</a>
											</c:if> 
											
										
										</li>
									</ul>
								</div>
							</div>
						</ul></li>
				</ul>
			</div>

			<div>
				<a class="logo date_top_menu"
					style="float: left; font-size: 20px; margin-top: 13px;"><span>
						<fmt:formatDate pattern="dd-MMM-yyyy" value="${hotelDateEpoch}" />
				</span> </a>
			</div>
			<div class="top-nav ">
				<ul class="nav pull-right top-menu">
					<!-- user login dropdown start-->
					<li class="dropdown" id="logout_drpdwn"><a
						aria-expanded="false" data-toggle="dropdown"
						class="dropdown-toggle profile_pic" href="#"> <img alt=""
							src="/pms/resources/common/images/profile_pic.svg" /> <span
							class="username">${userForm.name}</span> <b class="caret"></b>
					</a>
						<ul class="dropdown-menu extended logout">
							<div class="log-arrow-up"></div>
							<li><a href="${rootPath}/users/list"><i class="fa fa-suitcase"></i>Profile</a></li>
							<li><a href="${rootPath}/systemSettings/settings"><i
									class="fa fa-cog"></i> Settings</a></li>
							<li><a> ${VERSION}</a></li>		
							<li><a href="${rootPath}/logout" onclick="logout()"><i
									class="fa fa-key"></i> Log Out</a></li>
						</ul></li>
					<!-- user login dropdown end -->
				</ul>
			</div>
		</div>
	</div>


</header>

