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


<c:import url="../common/includes/master_includes.jsp" />

<script type="text/javascript"
	src="<c:url value='/resources/pms/js/angularctrl/system_permission.js' />"></script>


</head>
<body class="full-width">

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

							<section class="panel">
								<header class="panel-heading module_caption">
									<h1>
										<%-- ${moduleName}
					 --%>

										{{ pageName }}
									</h1>
									<span class="tools pull-right"> <a
										class="fa fa-chevron-down" href="javascript:;"></a>
									</span>
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



										<div class="list-group col-md-12">

											<div class="list-group-item active col-md-12 "
												style="background: rgb(43, 104, 156); color: #ffffff">
												<div class="col-md-2 ">Sl</div>
												<div class="col-md-4 ">Function</div>
												<div class="col-md-1 ">
													<label for="isview">view</label> <br> <input
														id="isview" name="isview" class="checkbox" type="checkbox"
														data-ng-model="isview" ng-change="checkItems(1,isview)">
													<label for="isview"></label>

												</div>
												<div class="col-md-1 ">
													<label for="isadd">Add</label><br> <input id="isadd"
														name="isadd" class="checkbox" type="checkbox"
														data-ng-model="isadd" ng-change="checkItems(2,isadd)">
													<label for="isadd"></label>

												</div>
												<div class="col-md-1 ">
													<label for="isedit">Edit</label><br> <input
														id="isedit" name="isedit" class="checkbox" type="checkbox"
														data-ng-model="isedit" ng-change="checkItems(3,isedit)">
													<label for="isedit"></label>

												</div>
												<div class="col-md-1 ">
													<label for="isdelete">Delete</label><br> <input
														id="isdelete" name="isdelete" class="checkbox"
														type="checkbox" data-ng-model="isdelete"
														ng-change="checkItems(4,isdelete)"> <label
														for="isdelete"></label>

												</div>
												<div class="col-md-1 ">

													<label for="isexport">Export</label><br> <input
														id="isexport" name="isexport" class="checkbox"
														type="checkbox" data-ng-model="isexport"
														ng-change="checkItems(5,isexport)"> <label
														for="isexport"></label>

												</div>
												<div class="col-md-1 ">
													<label for="isexecute">Execute</label><br> <input
														id="isexecute" name="isexecute" class="checkbox"
														type="checkbox" data-ng-model="isexecute"
														ng-change="checkItems(6,isexecute)"> <label
														for="isexecute"></label>

												</div>
											</div>

											<div ng-repeat="group in groups">
												<!-- <a data-toggle="collapse" href="#collapse_{{ group.sysgrpLabel.split(' ').join('') }}" >  -->
												<a ng-click="hideGroup($index)">
													<div class="list-group-item col-md-12"
														style="background: #00a8b3; color: #000000">
														<strong> {{ group.sysgrpLabel }} </strong>

													</div>
												</a>
												<!--   <div id="collapse_{{ group.sysgrpLabel.split(' ').join('') }}" class="panel-collapse collapse"> -->
												<div id="collapse_{{$index}}">
													<div class="list-group-item col-md-12 "
														ng-repeat="fnName in group.functionName">

														<div class="col-md-2 ">{{ $index+1}}</div>
														<div class="col-md-4 ">{{ fnName.name }}</div>
														<div class="col-md-1 ">
															<input
																id="isview_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																name="isview_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																class="checkbox" type="checkbox"
																data-ng-model="fnName.isViewApplicable"
																ng-checked="fnName.isViewApplicable"
																ng-change="viewChange(fnName,$index)">
															<!-- ng-checked="v_all" -->
															<label
																for="isview_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
														</div>
														<div class="col-md-1 ">
															<input
																id="isadd_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																name="isadd_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																class="checkbox" type="checkbox"
																data-ng-model="fnName.isAddApplicable"
																ng-checked="fnName.isAddApplicable"
																ng-change="AddChange(fnName,$index)"> <label
																for="isadd_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
														</div>
														<div class="col-md-1 ">
															<input
																id="isedit_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																name="isedit_{ {group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																class="checkbox" type="checkbox"
																data-ng-model="fnName.isEditApplicable"
																ng-checked="fnName.isEditApplicable"
																ng-change="AddChange(fnName,$index)"> <label
																for="isedit_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
														</div>
														<div class="col-md-1 ">
															<input
																id="isdelete_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																name="isdelete_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																class="checkbox" type="checkbox"
																data-ng-model="fnName.isDeleteApplicable"
																ng-checked="fnName.isDeleteApplicable"
																ng-change="AddChange(fnName,$index)"> <label
																for="isdelete_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
														</div>
														<div class="col-md-1 ">
															<input
																id="export_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																name="export_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																class="checkbox" type="checkbox"
																data-ng-model="fnName.isExportApplicable"
																ng-checked="fnName.isExportApplicable"
																ng-change="AddChange(fnName,$index)"> <label
																for="export_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
														</div>
														<div class="col-md-1 ">
															<input
																id="execute_{{group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																name="execute_{ {group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"
																class="checkbox" type="checkbox"
																data-ng-model="fnName.isExecuteApplicable"
																ng-checked="fnName.isExecuteApplicable"
																ng-change="AddChange(fnName,$index)"> <label
																for="execute_{{ group.sysgrpLabel.split(' ').join('') }}{{$index+1}}"></label>
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
					</div>


					<!-- custom page is included here -->
				</section>
			</div>
		</section>

	</section>


</body>
</html>
