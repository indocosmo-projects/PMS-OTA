<jsp:directive.include file="../common/includes/page_directives.jsp" />
<c:set var="moduleTitle" value="Permission List" scope="request" />
<c:set var="moduleName" value="Setting Up Permission Lists"
	scope="request" />
<c:set var="formId" value="Permission List" scope="request" />
<c:set var="formName" value="Permission List" scope="request" />
<c:set var="formAction" value="" scope="request" />
<c:set var="customEditIncludeFile"
	value="../syspermission/per_list_custom.jsp" scope="request" />
<c:set var="masterListHeaderName" value="Deposit" scope="request" />
<html>
<head>
<title>${moduleTitle}</title>
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/status_color_code.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/common/css/pms_grid.css' />" />
<link rel="stylesheet"
	href="<c:url value='/resources/pms/css/group_permission.css' />" />

<c:import url="../common/includes/master_includes.jsp" />

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/system_group_permission.js' />"></script>


</head>
<body class="full-width" id="tools">

	<section id="container" class="">
		<c:import url="../menu/topMenu.jsp" />

		<section id="main-content">
			<div class="container">
				<section class="wrapper">
					<!-- page start 
				<div class="row">
					<div class="col-lg-12">
						<section class="panel">
							<div class="panel-body">
								<div class="row">
									<div class="col-md-12">
										<div class="task-progress trans_module_head">
											<h1>${moduleName}</h1>
										</div>
									</div>
								</div>

							</div>
						</section>

					</div>
				</div>

				  custom page is included here -->


					<input type="hidden" value="${dateFormat}" id="dateFormat">
					<input type="hidden" id="hotelDate" value="${hotelDate}">


					<div class="row" ng-app="pmsApp"
						ng-controller="permissionController">

						<div class="col-lg-12">

							<section class="panel pannel_btm_class">
								<header class="panel-heading module_caption">
									<h1>
										<%-- ${moduleName}
					 --%>
										<script>  window.groupid = ${groupid}; </script>
										{{ pageName }}
									</h1>

									<a href="<c:url value='/userGroup/list' />"
										class="ios-back-button"
										data-text="<spring:message code="pms.btn.backtop" />"></a>

									<!-- <span class="tools pull-right"> <a class="fa fa-chevron-down"
					href="javascript:;"></a>
				</span> -->
								</header>

								<div class="panel-body">
									<div class="dtls">
										<!--  o -->


										<!--  <div class="resv-card">
     
         
             <div class="row">
                  <div class="col-md-6 resv-col-in"> 1 </div>
                  <div class="col-md-6 resv-col-in"> 1 </div>
                  <div class="col-md-6 resv-col-in"> 1 </div>
              </div>
          
       
   </div>  -->



										<div class="list-group">

											<div id="staticDiv"
												class="list-group-item active col-md-12 col-sm-12 ">
												<!-- style="background: rgb(43, 104, 156);color:#ffffff;position: fixed;" >   -->
												<div class="col-md-1 col-sm-1 ">Sl</div>
												<div class="col-md-4 col-sm-4 ">Function</div>
												<div class="col-md-1 col-sm-1">
													<!-- <label for="isview">view</label>	 <br>				       
						       <input id="isview" name="isview" class="checkbox" type="checkbox" 
                                data-ng-model="isview"  ng-change="checkItems(1,isview)"  >       
                                <label for="isview"></label> -->

													<label for="isview">view</label> <br> <input
														id="isview" name="isview" class="checkbox" type="checkbox"
														data-ng-model="isview" ng-checked="isChecked(1)"
														ng-click="toggleAll(1,isview)"> <label
														for="isview"></label>





												</div>
												<div class="col-md-1 col-sm-1">
													<label for="isadd">Add</label><br> <input id="isadd"
														name="isadd" class="checkbox" type="checkbox"
														data-ng-model="isadd" ng-checked="isChecked(2)"
														ng-click="toggleAll(2,isadd)"> <label
														for="isadd"></label>

												</div>
												<div class="col-md-1 col-sm-1">
													<label for="isedit">Edit</label><br> <input
														id="isedit" name="isedit" class="checkbox" type="checkbox"
														data-ng-model="isedit" ng-checked="isChecked(3)"
														ng-click="toggleAll(3,isedit)"> <label
														for="isedit"></label>

												</div>
												<div class="col-md-1 col-sm-1">
													<label for="isdelete">Delete</label><br> <input
														id="isdelete" name="isdelete" class="checkbox"
														type="checkbox" data-ng-model="isdelete"
														ng-checked="isChecked(4)" ng-click="toggleAll(4,isdelete)">
													<label for="isdelete"></label>

												</div>
												<div class="col-md-1 col-sm-1">

													<label for="isexport">Export</label><br> <input
														id="isexport" name="isexport" class="checkbox"
														type="checkbox" data-ng-model="isexport"
														ng-checked="isChecked(5)" ng-click="toggleAll(5,isexport)">
													<label for="isexport"></label>

												</div>
												<div class="col-md-1 col-sm-1">
													<label for="isexecute">Execute</label><br> <input
														id="isexecute" name="isexecute" class="checkbox"
														type="checkbox" data-ng-model="isexecute"
														ng-checked="isChecked(6)"
														ng-click="toggleAll(6,isexecute)"> <label
														for="isexecute"></label>

												</div>
											</div>
											<div class="body_div_class">
												<div id="bodyDiv" ng-repeat="group in groups">
													<!-- <a data-toggle="collapse" href="#collapse_{{ group.sysgrpLabel.split(' ').join('') }}" >  -->
													<a ng-click="hideGroup($index)">
														<div
															class="list-group-item col-md-12 list_group_strip_div">
															<strong> {{ group.sysgrpLabel }} </strong>

														</div>
													</a>
													<!--   <div id="collapse_{{ group.sysgrpLabel.split(' ').join('') }}" class="panel-collapse collapse"> -->
													<div id="collapse_{{$index}}">
														<div class="list-group-item col-md-12"
															ng-repeat="fnName in group.functionName">

															<div class="col-md-1 col-sm-1">{{ $index+1}}</div>
															<div class="col-md-4 col-sm-4">{{ fnName.name }}</div>
															<div class="col-md-1 col-sm-1">
																<input
																	id="isview_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	name="isview_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	class="checkbox" type="checkbox"
																	data-ng-model="fnName.canView"
																	ng-checked="fnName.canView"
																	ng-change="viewChange(fnName,$index)">
																<!-- ng-checked="v_all" -->
																<label
																	for="isview_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
															</div>
															<div class="col-md-1 col-sm-1">
																<input
																	id="isadd_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	name="isadd_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	class="checkbox" type="checkbox"
																	data-ng-model="fnName.canAdd"
																	ng-checked="fnName.canAdd"
																	ng-change="AddChange(fnName,$index)"> <label
																	for="isadd_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
															</div>
															<div class="col-md-1 col-sm-1">
																<input
																	id="isedit_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	name="isedit_{ {group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	class="checkbox" type="checkbox"
																	data-ng-model="fnName.canEdit"
																	ng-checked="fnName.canEdit"> <label
																	for="isedit_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
															</div>
															<div class="col-md-1 col-sm-1">
																<input
																	id="isdelete_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	name="isdelete_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	class="checkbox" type="checkbox"
																	data-ng-model="fnName.canDelete"
																	ng-checked="fnName.canDelete"> <label
																	for="isdelete_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
															</div>
															<div class="col-md-1 col-sm-1">
																<input
																	id="export_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	name="export_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	class="checkbox" type="checkbox"
																	data-ng-model="fnName.canExport"
																	ng-checked="fnName.canExport"> <label
																	for="export_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
															</div>
															<div class="col-md-1 col-sm-1">
																<input
																	id="execute_{{group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	name="execute_{ {group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																	class="checkbox" type="checkbox"
																	data-ng-model="fnName.canExecute"
																	ng-checked="fnName.canExecute"> <label
																	for="execute_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
															</div>
														</div>
													</div>
												</div>

											</div>

										</div>





										<!--  c  -->
									</div>

								</div>
							</section>
						</div>

						<div class="col-lg-12">
							<section class="panel">
								<div class="panel">
									<div class="panel-body">
										<div class="row">
											<div class="col-md-12" align="right">
												<input type="button" class="btn btn-default"
													onclick="location.href='../userGroup/list'" value="Back">
												<input type="button" class="btn btn-danger"
													onclick="location.reload();" value="Reset"> <input
													type="button" class="btn btn-success" value="Save"
													ng-click="submitPermission()">
											</div>
										</div>
									</div>
								</div>
							</section>
						</div>
					</div>


					<!-- custom page is included here -->
				</section>
			</div>
		</section>

	</section>


</body>
</html>
