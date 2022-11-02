pmsApp.controller('requestChkinCtrl', ['$scope','$q','$http','$window','DTOptionsBuilder','DTColumnBuilder','$mdDialog','$filter','$log','$compile',function ($scope,$q,$http,$window,DTOptionsBuilder,DTColumnBuilder,$mdDialog,$filter,$log,$compile){	
	onloadValidator(".validator");	
	OnloadValidationEdit('.validator');
	var checkOutStatus=0;
	$scope.selectedIndex=0;
	var hdate = $("#hotelDate").val();
	$scope.dateFormat = $("#dateFormat").val();
	$scope.hotelDate =new Date(hdate);
	$scope.reqDate=$scope.hotelDate;
	$scope.reqTodate=new Date(new Date($scope.reqDate).getFullYear(),new Date($scope.reqDate).getMonth(),new Date($scope.reqDate).getDate()+1);
	$scope.reqTime=new Date();
	$scope.hstep = 1;
	$scope.mstep = 1;
	$scope.maxSize=8;
	$scope.pagination={offset:0,limit:'5',selectedTab:" "};
	$scope.paginationData1={offset:0,limit:'5',bigTotalItems:0,totalItems:0};
	$scope.paginationData={offset:0,limit:'5',bigTotalItems:0,totalItems:0};
	$scope.shiftCheck=window.count;
	$scope.pageChanged = function() {
		$scope.pagination.offset=($scope.bigCurrentPage-1)*parseInt($scope.pagination.limit);
		$scope.getSearchData();
	};
	$scope.totalItems = 0;
	$scope.bigCurrentPage = 1;
	$scope.itemsPerPage =parseInt($scope.pagination.limit);
	$scope.bigTotalItems = 0;
	$scope.ismeridian=true;
	$scope.selectedIcons=null;
	$.fn.dataTable.ext.errMode = 'none';
	var na = this;
	na.deleteRow=deleteRow;
	na.dtInstance = {};
	na.dtInstance1 = {};
	na.dtInstance2 = {};
	$scope.searchResultData=[];
	$scope.searchResultData1=[];
	$scope.listData=[{roomno:"",facility:"",providr:"",typereq:"",fromdate:"",reqTime:"",remarks:"",facilityId:"",checkInNo:"",provider:"",oneTimeReq:"",arrangementBy:"",isActive:"",facilityid:"",inactiveDateRequest:""}];
	na.getListData = getListData;
	$scope.pagination={offset:0,limit:'5'}
	$scope.listData=[];
	$scope.inActiveDate=[];
	$scope.search={comnSearch:false,advSearch:false,comnSearchValue:"",roomNo:{searchable:false,sortable:true,searchValue:""},reqStatus:{searchable:false,sortable:true,searchValue:""},reqDate:{searchable:false,sortable:true,searchValue:""},name:{searchable:false,sortable:false,searchValue:""},phone:{searchable:false,sortable:false,searchValue:""},facility:{searchable:false,sortable:false,searchValue:""},addonStatus:{searchable:false,sortable:false,searchValue:""}};
	$scope.searchUnprocedTab={comnSearch:false,advSearch:false,comnSearchValue:"",roomNo:{searchable:false,sortable:true,searchValue:""},reqDate:{searchable:false,sortable:true,searchValue:""},reqStatus:{searchable:false,sortable:true,searchValue:""},name:{searchable:false,sortable:false,searchValue:""},phone:{searchable:false,sortable:false,searchValue:""},facility:{searchable:false,sortable:false,searchValue:""},addonStatus:{searchable:false,sortable:false,searchValue:""}};
	$scope.sort={sortColumn:'room_number',order:"desc"};
	$scope.$watch(
			"selectedIndex",
			function handleFooChange( newValue, oldValue ) {


				if($scope.selectedIndex==0){
					$scope.sort.sortColumn='room_number';
					$scope.pagination.selectedTab='UNPRO';
					$scope.bigTotalItems=$scope.paginationData.bigTotalItems;
					$scope.totalItems=$scope.paginationData.totalItems;

				}
				else if($scope.selectedIndex==1){
					$scope.sort.sortColumn='room_number';
					$scope.pagination.selectedTab='ALLREQ';
					$scope.bigTotalItems=$scope.paginationData1.bigTotalItems;
					$scope.totalItems=$scope.paginationData1.totalItems;
				}
			}
	);
	$scope.selectUnprocessed = function(){
		$scope.selectedIndex = 0;
	}
	$scope.selectAllRequest = function(){
		$scope.selectedIndex = 1;
	}
	na.dtOptions1 = DTOptionsBuilder.fromFnPromise(function() {
		var pagination={offset:$scope.pagination.offset,limit:$scope.pagination.limit,selectedTab:'ALLREQ'}
		var defer = $q.defer();
		$scope.searchItem=$scope.search;
		var data = $.param({listParams:JSON.stringify({search:$scope.searchItem,pagination:pagination,sort:$scope.sort})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../requestCheckin/getSearchData', data, config)
		.success(function (data, status, headers, config) {
			$scope.itemsPerPage =parseInt($scope.pagination.limit);
			$scope.paginationData1.bigTotalItems=data.totalRowCount;
			$scope.paginationData1.totalItems=data.requestData.length;
			$scope.searchResultData1=data.requestData;
			defer.resolve($scope.searchResultData1);
			checkOutStatus=$scope.searchResultData1.length;
			getPagination();
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
		return defer.promise;
	}).withDOM('lfrtie').withScroller().withOption('deferRender', true).withOption('scrollY', 200).withDisplayLength(10000).withOption('rowCallback', rowCallback1);
	na.dtColumns1 = [DTColumnBuilder.newColumn("chkhdrroom_number","Room").renderWith(function(data, type, full) { return '<a style="color:#0000ff;">'+data+'</a>';}),
	                 DTColumnBuilder.newColumn("first_name","Name"),
	                 DTColumnBuilder.newColumn("phone","Phone"),
	                 DTColumnBuilder.newColumn("facilityCode","Facility"),
	                 DTColumnBuilder.newColumn("chkReqDate","Request Date & Time").renderWith(function(data, type, full) { 
							data = $filter('date')(new Date(data),$scope.dateFormat) +' '+ new Date(full.chkReqTime).toLocaleTimeString('en-us');
							return data;
						}),
	                 DTColumnBuilder.newColumn("chkReqis_req_completed","Addon Status").renderWith(function(data, type, full) { return ((full.chkStatusProcessStatus==0)?'inactive':'Active')}),
	                 DTColumnBuilder.newColumn("chkStatusProcessStatus","Current Status").renderWith(function(data,type,full){return ((full.chkStatusProcessStatus==1)?'Processed':'Unprocessed')})
	                 ];
	function rowCallback1(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		$('td', nRow).unbind('click');
		$('td',nRow).bind('click', function() {
			$scope.$apply(function() {
				$("#ddlRequestType").val(aData.chkinrqst_is_one_time_request);
				$scope.roomNumberDisbl=true;
				$("#Todate").hide();
				$("#dataTableShow").hide();
				$("#ddlStatusType").show();
				$("#reqlistbtn").hide();
				$("#multiCalandrInactive").hide();
				$("#deleteRequestBtn").show();
				$("#contRequestBtn").hide();
				$("#contRequestUpdateBtn").show();
				$("#requestAddNewmyModal").modal({backdrop: "static"});
				$("#ddlStatusType").val(aData.process_status);
				$scope.reqStatusId=aData.requst_status_id;
				$scope.checkinRequestid=aData.checkinRequest_id;
				$scope.reqRemarks=aData.chkReqRemarks;
				$scope.reqTime=aData.chkReqTime;
				$scope.selectedIcons=Number(aData.chkHdrcheckin_no);
				$scope.facilityId=Number(aData.chkReqFacId);
				$scope.provider=aData.facProvId;
				$scope.reqCompleted=aData.chkReqis_req_completed;
				$scope.oneTimeReq=aData.chkinrqst_is_one_time_request;
				$scope.reqDate=new Date(aData.chkreqStatusDate);
				$("#requestAddNewmyModal").modal({backdrop: "static"});
			});	
		})
		return nRow;	
	}


	$scope.searchBoxClear=function(){
		if($scope.selectedIndex==1){
			$scope.search.comnSearchValue = "";
		//	$scope.searchUnprocedTab.roomNo.searchValue ="";
			if($scope.search.comnSearch){
				$scope.getSearchData();
				$scope.search.comnSearch=false;
			}else if($scope.search.advSearch){
				$scope.advanceSearchClear();
				$scope.getSearchData();
			}
		}else if($scope.selectedIndex==0){
			$scope.searchUnprocedTab.comnSearchValue = "";
			if($scope.searchUnprocedTab.comnSearch){
				$scope.getSearchData();
				$scope.searchUnprocedTab.comnSearch=false;
			}else if($scope.searchUnprocedTab.advSearch){
				$scope.advanceSearchClear();
				$scope.getSearchData();
			}
		}

	}

	$scope.advanceSearchClear=function(){
		$scope.search.advSearch=false;
		$scope.searchUnprocedTab.advSearch=false;
		$scope.search.roomNo.searchValue="";
		/*$scope.searchUnprocedTab.roomNo.searchValue = null;
		$scope.searchUnprocedTab.reqStatus.searchValue = null;*/
		$scope.search.roomNo.searchable=false;
		$scope.search.reqDate.searchValue="";
		$scope.search.reqDate.searchable=false;
		$scope.search.reqStatus.searchValue="";
		$scope.search.reqStatus.searchable=false;
		$scope.search.name.searchValue="";
		$scope.search.name.searchable=false;
		$scope.search.phone.searchValue="";
		$scope.search.phone.searchable=false;
		$scope.search.facility.searchValue="";
		$scope.search.facility.searchable=false;
		$scope.search.addonStatus.searchValue="";
		$scope.search.addonStatus.searchable=false;
		$scope.searchUnprocedTab.roomNo.searchValue="";
		$scope.searchUnprocedTab.reqStatus.searchValue="";
		$scope.searchUnprocedTab.name.searchValue=null;
		$scope.searchUnprocedTab.name.searchable=false;
		$scope.searchUnprocedTab.phone.searchValue="";
		$scope.searchUnprocedTab.phone.searchable=false;
		$scope.searchUnprocedTab.facility.searchValue="";
		$scope.searchUnprocedTab.facility.searchable=false;
		$scope.searchUnprocedTab.addonStatus.searchValue="";
		$scope.searchUnprocedTab.addonStatus.searchable=false;
	}

	$scope.dateBoxClear=function(){
		$("#outerDiv").empty();
	}

	$scope.showHideDiv = function (selectedIcons) {
		for(i=0;i<($scope.roomLists.length);i++){
			if($scope.selectedIcons==$scope.roomLists[i].checkin_no){
				$scope.numberroom=$scope.roomLists[i].room_number;
				$scope.first_name=$scope.roomLists[i].first_name;
				$scope.phone=$scope.roomLists[i].phone;
				$scope.minDepartDate=$scope.roomLists[i].exp_depart_date;
				$scope.minDeparture=new Date($scope.minDepartDate);
			}
		};
	}

	$scope.newAddon = function(){
		if($scope.shiftCheck!=0){
			$scope.selectedIcons=" ";$scope.facilityId=" ";$scope.provider=" ";$scope.reqRemarks="";
			$scope.numberroom = null;
			$scope.first_name = null;
			$scope.phone = null;
			$('#ddlRequestType').val(1);
			resetValidators('.validator');
			$("#reqlistbtn").show();
			$("#requestAddNewmyModal").modal({backdrop: "static"});	
			$scope.roomNumberDisbl=false;
			$("#Todate").hide();
			$("#multiCalandrInactive").show();
			$("#dataTableShow").show();
			$("#contRequestBtn").show();
			$("#ddlStatusType").hide();
			$("#deleteRequestBtn").hide();
			$("#contRequestUpdateBtn").hide();
			$("#multiCalandrInactive").hide();
			if($scope.listData.length==1){
				$scope.listData=[];
			}
		}else{
			var confirm = $mdDialog.confirm()
			.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../shiftManagement/openshift";
			});
		}
	}

	$scope.tagInputClose = function(id) {
		if(id=='roomNum'){
			$scope.search.roomNo.searchValue="";
			$scope.search.roomNo.searchable=false;
		}else if(id=='reqdate'){
			$scope.search.reqdate.searchValue="";
			$scope.search.reqdate.searchable=false;
		}else if(id=='reqStatus'){
			$scope.search.reqStatus.searchValue="";
			$scope.search.reqStatus.searchable=false;
		}else if(id=='roomNumber'){
			$scope.searchUnprocedTab.roomNo.searchValue="";
			$scope.searchUnprocedTab.roomNo.searchable=false;
		}else if(id=='reqStatusunpro'){
			$scope.searchUnprocedTab.reqStatus.searchValue="";
			$scope.searchUnprocedTab.reqStatus.searchable=false;
		}
	}

	$scope.sortFun = function(){
		if($scope.sort.order == 'asc') {
			$(".list_sort").find('i').removeClass('fa-sort-amount-asc').addClass('fa-sort-amount-desc');
			$scope.sort.order = 'desc';
		} else {
			$(".list_sort").find('i').removeClass('fa-sort-amount-desc').addClass('fa-sort-amount-asc');
			$scope.sort.order = 'asc';
		}
		$scope.pagination.offset=0;
		$scope.bigCurrentPage=1;
	}

	$scope.sortColumnChange = function(){
		$scope.pagination.offset=0;
		$scope.bigCurrentPage=1;
	}

	$scope.advanceSearch = function() {
		if($scope.selectedIndex==1){
			$scope.search.advSearch=true;
			$scope.search.comnSearch=false;
			if($scope.search.roomNo.searchValue=="" && $scope.search.reqStatus.searchValue==""	&& $scope.search.reqDate.searchValue=="") return;
			$scope.search.roomNo.searchable=($scope.search.roomNo.searchValue!="");
			$scope.search.reqStatus.searchable=($scope.search.reqStatus.searchValue!="");
			$scope.search.reqDate.searchable=($scope.search.reqDate.searchValue!="");
			if($scope.search.reqDate.searchable){
				$scope.search.reqDate.searchValue=$filter('date')(new Date($scope.search.reqDate.searchValue),"yyyy-MM-dd")
			}
		}else if($scope.selectedIndex==0){
			$scope.searchUnprocedTab.advSearch=true;
			$scope.searchUnprocedTab.comnSearch=false;
			if($scope.searchUnprocedTab.roomNo.searchValue=="" && $scope.searchUnprocedTab.reqStatus.searchValue=="") return;
			$scope.searchUnprocedTab.roomNo.searchable=($scope.searchUnprocedTab.roomNo.searchValue!="");
			$scope.searchUnprocedTab.reqStatus.searchable=($scope.searchUnprocedTab.reqStatus.searchValue!="");
		}
		$('#search_fom_div').hide();
		$scope.getSearchData();
	}
	
	/*$scope.simpleSearch=function() {
		if($scope.selectedIndex==1){
			$scope.search.comnSearch=true;
		}else if($scope.selectedIndex==0){
			//$scope.searchInHouse.comnSearch=true;
			$scope.searchUnprocedTab.comnSearch=true;
		}
		$scope.advanceSearchClear();
		reloadData();
	}*/

	$scope.searchOnEnter = function(keyCode){
		if(keyCode == 13){
			$scope.simpleSearch();
		}
	}
	
	$scope.simpleSearch=function() {
		if($scope.selectedIndex==1){
			$scope.search.comnSearch=true;
			$scope.search.advSearch=false;
			$scope.search.roomNo.searchable=true;
			$scope.search.roomNo.searchValue=$scope.search.comnSearchValue; 
			$scope.search.reqStatus.searchable=true;
			$scope.search.reqStatus.searchValue=$scope.search.comnSearchValue;
			$scope.search.name.searchable=true;
			$scope.search.name.searchValue=$scope.search.comnSearchValue;
			$scope.search.phone.searchable=true;
			$scope.search.phone.searchValue=$scope.search.comnSearchValue;
			$scope.search.facility.searchable=true;
			$scope.search.facility.searchValue=$scope.search.comnSearchValue;
			$scope.search.reqDate.searchable=true;
			$scope.search.reqDate.searchValue=$scope.search.comnSearchValue;
			$scope.search.addonStatus.searchable=true;
			$scope.search.addonStatus.searchValue=$scope.search.comnSearchValue;
		}else if($scope.selectedIndex==0){
			$scope.searchUnprocedTab.comnSearch=true;
			$scope.searchUnprocedTab.advSearch=false;
			$scope.searchUnprocedTab.roomNo.searchable=true;
			$scope.searchUnprocedTab.roomNo.searchValue=$scope.searchUnprocedTab.comnSearchValue; 
			$scope.searchUnprocedTab.reqStatus.searchable=true;
			$scope.searchUnprocedTab.reqStatus.searchValue=$scope.searchUnprocedTab.comnSearchValue;
			$scope.searchUnprocedTab.name.searchable=true;
			$scope.searchUnprocedTab.name.searchValue=$scope.searchUnprocedTab.comnSearchValue;
			$scope.searchUnprocedTab.phone.searchable=true;
			$scope.searchUnprocedTab.phone.searchValue=$scope.searchUnprocedTab.comnSearchValue;
			$scope.searchUnprocedTab.facility.searchable=true;
			$scope.searchUnprocedTab.facility.searchValue=$scope.searchUnprocedTab.comnSearchValue;
			$scope.searchUnprocedTab.addonStatus.searchable=true;
			$scope.searchUnprocedTab.addonStatus.searchValue=$scope.searchUnprocedTab.comnSearchValue;	
		}
		$scope.advanceSearchClear();
		reloadData();
	}

	function reloadData(){
		if($scope.selectedIndex==1){
			na.dtInstance1.reloadData();
		}else if($scope.selectedIndex==0){
			na.dtInstance.reloadData();
		}
	}
	
	/*$scope.getSearchData=function(){
		$scope.searchItems={};
		if($scope.selectedIndex==0){
			$scope.pagination.selectedTab='UNPRO';
			if($scope.searchUnprocedTab.reqDate.searchable && !("ALL"===$scope.searchUnprocedTab.reqDate.searchValue)){
				$scope.searchUnprocedTab.reqDate.searchValue=new Date($scope.searchUnprocedTab.reqDate.searchValue).getTime();
			}
			$scope.searchItems = $scope.searchUnprocedTab;
			if(!$scope.searchUnprocedTab.roomNumber.searchable && !$scope.searchUnprocedTab.addonStatus.searchable){
				$scope.searchItems.advSearch=false;
			}
		}
		else if($scope.selectedIndex==1){
			$scope.pagination.selectedTab='ALLREQ';
			if($scope.search.reqDate.searchable){
				$scope.search.reqDate.searchValue=new Date($scope.search.reqDate.searchValue).getTime();
			}
			$scope.searchItems = $scope.search;
			if(!$scope.search.roomNumber.searchable && !$scope.search.addonStatus.searchable
					&& !$scope.search.reqDate.searchable){
				$scope.searchItems.advSearch=false;
			}
		}
		var data = $.param({listParams:JSON.stringify({search:$scope.searchItem,pagination:$scope.pagination,sort:$scope.sort})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../requestCheckin/getSearchData', data, config)
		.success(function (data, status, headers, config) {
			if($scope.selectedIndex==1){
				$scope.reqList=data;
				$scope.totalItems=$scope.reqList.card.length;
				$scope.bigTotalItems = parseInt($scope.reqList.total);
				$scope.itemsPerPage =parseInt($scope.pagination.limit);
			}
		})
	}*/ 
	
	
	
	

	$scope.getSearchData=function(){
		if($scope.selectedIndex==0){	
			$scope.searchItem=$scope.searchUnprocedTab;
		}else if($scope.selectedIndex==1){
			$scope.searchItem=$scope.search;
		}
		var data = $.param({listParams:JSON.stringify({search:$scope.searchItem,pagination:$scope.pagination,sort:$scope.sort})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../requestCheckin/getSearchData', data, config)
		.success(function (data, status, headers, config) {
			$scope.totalItems=data.requestData.length;
			$scope.bigTotalItems=data.totalRowCount;
			$scope.itemsPerPage =parseInt($scope.pagination.limit);
			if($scope.selectedIndex==1){
				$scope.searchResultData1=data.requestData;
				na.dtInstance1.reloadData();
			}else{
				$scope.searchResultData=data.requestData;
				na.dtInstance.reloadData();
			}
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}

	na.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var pagination={offset:$scope.pagination.offset,limit:$scope.pagination.limit,selectedTab:'UNPRO'}
		var defer = $q.defer();
		$scope.searchItem=$scope.searchUnprocedTab;
		var data = $.param({listParams:JSON.stringify({search:$scope.searchItem,pagination:pagination,sort:$scope.sort})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../requestCheckin/getSearchData', data, config)
		.success(function (data, status, headers, config) {
			$scope.itemsPerPage =parseInt($scope.pagination.limit);
			$scope.paginationData.bigTotalItems=data.totalRowCount;
			$scope.paginationData.totalItems=data.requestData.length;
			$scope.searchResultData=data.requestData;
			defer.resolve($scope.searchResultData);
			checkOutStatus=$scope.searchResultData.length;
			getPagination();
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
		return defer.promise;
	}).withDOM('lfrti').withScroller().withOption('deferRender', true).withOption('scrollY', 200).withDisplayLength(10000).withOption('rowCallback', rowCallback);
	na.dtColumns = [
	                DTColumnBuilder.newColumn("chkhdrroom_number","Room").renderWith(function(data, type, full) { return '<a style="color:#0000ff;">'+data+'</a>';}),
	                DTColumnBuilder.newColumn("first_name","Name"),
	                DTColumnBuilder.newColumn("phone","Phone"),
	                DTColumnBuilder.newColumn("facilityCode","Facility"),	                
	                DTColumnBuilder.newColumn("chkReqDate","Request Date & Time").renderWith(function(data, type, full) { 
	                	data = $filter('date')(new Date(data),$scope.dateFormat) +' '+ new Date(full.chkReqTime).toLocaleTimeString('en-us');
							return data;
						}),
	                DTColumnBuilder.newColumn("chkReqis_req_completed","Addon Status").renderWith(function(data, type, full) { return ((full.chkStatusProcessStatus==0)?'Inactive':'Active')}),
	                DTColumnBuilder.newColumn("chkStatusProcessStatus","Current Status").renderWith(function(data,type,full){return ((full.chkStatusProcessStatus==1)?'Processed':'Unprocessed')})

	                ];
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		$('td', nRow).unbind('click');
		$('td',nRow).bind('click', function() {
			$scope.$apply(function() {
				$("#ddlRequestType").val(aData.chkinrqst_is_one_time_request);
				$scope.roomNumberDisbl=true;
				$("#Todate").hide();
				$("#dataTableShow").hide();
				$("#ddlStatusType").show();
				$("#reqlistbtn").hide();
				$("#multiCalandrInactive").hide();
				$("#deleteRequestBtn").show();
				$("#contRequestBtn").hide();
				$("#contRequestUpdateBtn").show();
				$("#requestAddNewmyModal").modal({backdrop: "static"});
				$("#ddlStatusType").val(aData.process_status);
				$scope.reqStatusId=aData.requst_status_id;
				$scope.checkinRequestid=aData.checkinRequest_id;
				$scope.reqRemarks=aData.chkReqRemarks;
				$scope.reqTime=aData.chkReqTime;
				$scope.selectedIcons=Number(aData.chkHdrcheckin_no);
				$scope.facilityId=Number(aData.chkReqFacId);
				$scope.provider=aData.facProvId;
				$scope.reqCompleted=aData.chkReqis_req_completed;
				$scope.oneTimeReq=aData.chkinrqst_is_one_time_request;
				$scope.reqDate=new Date(aData.chkReqDate);
				resetValidators('.validator');
				$("#requestAddNewmyModal").modal({backdrop: "static"});

			});	
		})
		return nRow;	
	}


	$http({
		url : '../requestCheckin/getFacilities',
		method : "POST"
	}).then(function(response) {
		$scope.facilitiesList = response.data;
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});


	function setNoShow(){window.location = "../reservation_test/reservationNoShow?reservationNo=" + $scope.resvNo;}	
	function goToCheckOut(){window.location = "../checkOut/checkOutEdit?folioBindNo=" + $scope.folioBindNo;}

	$http({
		url : '../requestCheckin/getRoomLists',
		method : "POST"
	}).then(function(response) {
		$scope.roomLists = response.data;
		$scope.selectedIcons=$scope.roomLists.checkin_no;
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});

	$http({
		url : '../facilityProvider/facilityProviderDetails',
		method : "POST",
	}).then(function(response) {
		$scope.providers = response.data;
		$scope.provider=$scope.providers.id;
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});

	$scope.cancelAddNewPopUp = function(){
		$("#requestAddNewmyModal").modal("toggle");
		
	}

	$scope.saveAddonRequest=function(){
		if($scope.listData.length!=0){
			$scope.processreq=[];
			$scope.reqTime=$filter('date')(new Date($scope.reqTime),"yyyy-MM-dd HH:mm:ss");
			$scope.oneTimeReq=$('#ddlRequestType').val();
			$scope.reqDate=$filter('date')(new Date($scope.reqDate),"yyyy-MM-dd HH:mm:ss");;
			$scope.reqTodate=$filter('date')(new Date($scope.reqTodate),"yyyy-MM-dd HH:mm:ss");
			if($scope.oneTimeReq=="1"){
				$scope.oneTimeReq=true;
			}else{
				$scope.oneTimeReq=false;
			}

			$scope.process={checkInNo:$scope.selectedIcons,faciltyid:$scope.facilityId,provider:$scope.provider,oneTimeReq:$scope.oneTimeReq,reqDate:$scope.reqDate,reqTodate:$scope.reqTodate,reqTime:$scope.reqTime,arrangementBy:0,reqRemarks:$scope.reqRemarks,isActive:true};
			$scope.processreq.push($scope.process);
			$http({
				url : '../requestCheckin/saveNewRequest',
				method : "POST",
				data:JSON.stringify({data:$scope.listData})
			}).then(function(response) {
				if(response.data.substring(7)=='success'){
					$("#contRequestBtn").hide();
					na.dtInstance.reloadData();
					$("#requestAddNewmyModal").modal("toggle");
					var confirm = $mdDialog.alert()
					.title("Request Completed Successfully").ok('OK').parent(angular.element(document.body));
					$mdDialog.show(confirm).then(function(){
						window.location  = "../requestCheckin/listaddon";
					});

				}else{$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));}
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});	
		}
		else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Please add to request list').ok('Ok').parent(angular.element(document.body))); 
		}	
	}

	$scope.saveAddonUpdate=function(){
		$scope.reqTime=$filter('date')(new Date($scope.reqTime),"yyyy-MM-dd HH:mm:ss");
		$scope.reqDate=$filter('date')(new Date($scope.reqDate),"yyyy-MM-dd HH:mm:ss");
		$scope.DataUpdate={id:$scope.reqStatusId,checkinrequestid:$scope.checkinRequestid,date:$scope.reqDate,reqTime:$scope.reqTime,provider:$scope.provider,reqTime:$scope.reqTime,reqCompleted:$scope.reqCompleted,checkinhdrremark:$scope.reqRemarks,processStatus:$("#ddlStatusType").val()};
		var data = $.param({
			updateJson:JSON.stringify($scope.DataUpdate)
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../requestCheckin/update', data, config)
		.success(function (data, status, headers, config) {
			na.dtInstance.reloadData();
			$("#requestAddNewmyModal").modal("toggle");
			var confirm = $mdDialog.confirm()
			.title("Request Updated Successfully").ok('OK').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../requestCheckin/listaddon";
			});
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}

	$(document).ready(function(){
		$scope.tpeRequest=1;
		$("#ddlRequestType").change(function () {
			$scope.oneTimeReq = $("#ddlRequestType").val();
			if ($(this).val() == 1) {
				$("#Todate").hide();
				$("#enterDate").show();
				$scope.tpeRequest=1;
			} else if($(this).val() == 0){
				$scope.tpeRequest=0;
				$("#Todate").show();
				$("#enterDate").show();
			}

		});
	});

	$('#ddlRequestType').on('change', function () {
		if ($(this).val() == 0) {
			$("#multiCalandrInactive").show();
		}
		else{
			$("#multiCalandrInactive").hide();

		}
		getPagination();
	});

	function getPagination(){
		if($scope.selectedIndex==0){
			$scope.sort.sortColumn='room_number';
			$scope.pagination.selectedTab='UNPRO';
			$scope.bigTotalItems=$scope.paginationData.bigTotalItems;
			$scope.totalItems=$scope.paginationData.totalItems;
		}
		else if($scope.selectedIndex==1){
			$scope.sort.sortColumn='room_number';
			$scope.pagination.selectedTab='ALLREQ';
			$scope.bigTotalItems=$scope.paginationData1.bigTotalItems;
			$scope.totalItems=$scope.paginationData1.totalItems;
		}
	}

	na.dtOptions2 = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		defer.resolve($scope.listData);	       
		return defer.promise;
	}).withPaginationType('full_numbers').withOption('rowCallback', rowCallback2).withOption('createdRow', function(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	});
	na.dtColumns2 = [DTColumnBuilder.newColumn("sl","Id").withOption('visible',false),
	                 DTColumnBuilder.newColumn("roomno","Room").renderWith(function(data, type, full) { return '<a style="color:#0000ff;">'+data+'</a>';}),
	                 DTColumnBuilder.newColumn("facility","Facility"),
	                 DTColumnBuilder.newColumn("providr","Provider"),
	                 DTColumnBuilder.newColumn("typereq","Type"),
	                 DTColumnBuilder.newColumn("fromdate","Date").renderWith(function(data, type, full) {
							data = $filter('date')(new Date(data),$scope.dateFormat);
							return data;
					}),
	                 DTColumnBuilder.newColumn("reqTime","Time").renderWith(function(data, type, full) { return (new Date(full.reqTime).toLocaleTimeString('en-US'))}),
	                 DTColumnBuilder.newColumn("remarks","Remarks"),
	                 DTColumnBuilder.newColumn("","Del").renderWith(
	                		 function(data, type, full) {
	                			 var sRet="";
	                			 sRet='<button type="button" class="btn btn-danger btn-xs"  ng-click="ctrlreq.deleteRow('+full.sl+');"><i class="fa fa-trash-o "></i></button>';


	                			 return sRet;}),
	                			 DTColumnBuilder.newColumn("faciltyid","idfacility").withOption('visible', false),
	                			 DTColumnBuilder.newColumn("checkInNo","checkInNo").withOption('visible', false),
	                			 DTColumnBuilder.newColumn("facilityId","facilityId").withOption('visible', false),
	                			 DTColumnBuilder.newColumn("provider","provider").withOption('visible', false),
	                			 DTColumnBuilder.newColumn("oneTimeReq","oneTimeReq").withOption('visible', false),
	                			 DTColumnBuilder.newColumn("arrangementBy","arrangementBy").withOption('visible', false),
	                			 DTColumnBuilder.newColumn("inactiveDateRequest","inactiveDateRequest").withOption('visible', false),
	                			 ];
	function rowCallback2(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		$('td', nRow).unbind('click');
		$('td',nRow).bind('click', function() {
		})
		return nRow;
	}
	function deleteRow(ids){
		var confirm = $mdDialog.confirm()
		.title("Alert")
		.textContent("Are you sure you want to delete this Request?").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			for(i=0;i<$scope.listData.length;i++){
				if(ids==$scope.listData[i].sl){
					$scope.listData.splice(i,1);
					break;
				}
			}
			na.dtInstance2.reloadData();
		});
	}

	function getListData() {
		var defer = $q.defer();
		defer.resolve($scope.listData);
		return defer.promise;
	}


	$scope.disToDate = function(){
		$scope.reqDate=$filter('date')(new Date($scope.reqDate),"yyyy-MM-dd HH:mm:ss");
		$scope.minArr = new Date(new Date($scope.reqDate).getFullYear(),new Date($scope.reqDate).getMonth(),new Date($scope.reqDate).getDate()+1);
	} 


	$scope.AddOnList=function(){
		if (validation('.validator') == "false") {
			return false;
		}
		if($scope.provider==" "){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Add new facility provider').ok('Ok').parent(angular.element(document.body)));
			return false;
		}
		if($scope.provider==" "){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Add new facility provider').ok('Ok').parent(angular.element(document.body)));
			return false;
		}
		for(i=0;i<($scope.roomLists.length);i++){
			if($scope.selectedIcons==$scope.roomLists[i].checkin_no){$scope.room_number=$scope.roomLists[i].room_number;}
		};
		for(i=0;i<($scope.facilitiesList.length);i++){
			if($scope.facilityId==$scope.facilitiesList[i].id){$scope.facilitySelected=$scope.facilitiesList[i].code;}
		}
		for(i=0;i<($scope.providers.length);i++){
			if($scope.provider==$scope.providers[i].id){$scope.provdr=$scope.providers[i].code;}
		}	
		$scope.reqDate=$filter('date')(new Date($scope.reqDate),"yyyy-MM-dd HH:mm:ss");
		$scope.reqTodate=($scope.tpeRequest==1)?$scope.reqDate:$filter('date')(new Date($scope.reqTodate),"yyyy-MM-dd HH:mm:ss");
		$scope.reqTime=$filter('date')(new Date($scope.reqTime),"yyyy-MM-dd HH:mm:ss");
		$scope.oneTimeReq=$('#ddlRequestType').val();
		var reqDatetime = $scope.reqTime.split(' ');
		  var timeTokens = reqDatetime[1].split(':');
		  var newDate= new Date(1970, 0, 1, timeTokens[0], timeTokens[1], timeTokens[2]);
		  $scope.reqHourAndMin = $filter('date')(newDate, 'HH:mm');
		$scope.listDatasreq={sl:"",roomno:$scope.room_number,facility:$scope.facilitySelected,providr:$scope.provdr,typereq:$scope.tpeRequest,date:$scope.reqDate,fromdate:$scope.reqDate,toDate:$scope.reqTodate,reqTime:reqDatetime[0]+" "+$scope.reqHourAndMin,remarks:$scope.reqRemarks,facilityId:$scope.facilityId,checkInNo:$scope.selectedIcons,provider:$scope.provider,oneTimeReq:$scope.oneTimeReq,reqDate:$scope.reqDate,reqTodate:$scope.reqTodate,arrangementBy:0,reqRemarks:$scope.reqRemarks,isActive:true,inactiveDateRequest:$scope.inActiveDate};
		var data = $.param({isRequestExistJson:JSON.stringify($scope.listDatasreq)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../requestCheckin/isRequstExist', data, config)
		.success(function (data, status, headers, config) {
			if(data=="status_newRequest"){			
				if($scope.listData.length!=0){
					for(j=0;j<$scope.listData.length;j++){
						if((($scope.listDatasreq.roomno==$scope.listData[j].roomno)&&($scope.listDatasreq.facility==$scope.listData[j].facility)&&($scope.listDatasreq.reqDate==$scope.listData[j].reqDate)&&($scope.listDatasreq.reqTime==$scope.listData[j].reqTime)))
						{
							$mdDialog.show($mdDialog.alert()
									.clickOutsideToClose(true).title('Request Already Exist').ok('Ok').parent(angular.element(document.body))); 
							break;
						}  else{
							$scope.listData.push(($scope.listDatasreq));
							na.dtInstance2.reloadData();
							break;
						}

					}	
				}
				else{	
					$scope.listData.push(($scope.listDatasreq));
					na.dtInstance2.reloadData();
				}
				for(i=0;i<$scope.listData.length;i++){$scope.listDatasreq.sl=i;}
			}else{
				if(data=="status_requestExist"){
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Request Already Exist.').ok('Ok').parent(angular.element(document.body)));
				}
			}
		}).error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});
	}
	
	$scope.clickOutSide=function(){
		if(!$(".md-select-menu-container").is(':visible')){
			$(document).on('mouseup.dohide',function(e){
				if($(e.target).closest('.md-select-menu-container').length ===0 ) {
					$(".md-select-menu-container").hide();
					$(document).off('dohide');
				}else{
					$(".md-select-menu-container").show();
				}
			});
		}
	}


	$scope.bottondownRequest=function(){
		if(!$("#search_fom_div_Request").is(':visible')){
			$("#search_fom_div_Request").show();
			$("#down").hide();
			$("#up").show();
			$(document).on('mouseup.dohide',function(e){
				if($(e.target).closest('#search_fom_div_Request').length ===0 ) {
					$("#search_fom_div_Request").hide();
					$(document).off('dohide');
					$("#down").show();
					$("#up").hide();
				}
			});
		}
	}
	
	$scope.bottonhideRequest=function(){
		$("#down").show();
		$("#up").hide();
	}

	$scope.closeInactive=function(){
		$("#search_fom_div_Request").toggle();
	}
//	$scope.tagInputRequestClose = function(idIndex) {
//	$("#innerDiv_"+idIndex).remove();
//	$scope.inActiveDate.splice(idIndex, 1);
//	alert("hi");
//	}
	$scope.$watch('myArrayOfDates', function(newValue, oldValue){
		if(newValue){
			$scope.inActiveDate=[];
			for(i=0;i<newValue.length;i++){
				$scope.inActiveDate.push($filter('date')(new Date(newValue[i]._d),"yyyy-MM-dd"));	
			}
			addToDate();
		}

	}, true);

	function addToDate(){
		if($("#outerDiv").length){
			$("#outerDiv").remove();
		}
		$("#dateReqstInput").append("<div id='outerDiv'></div>");
		for(i=0;i<$scope.inActiveDate.length;i++){
			$("#outerDiv").append($compile("<div id='innerDiv_"+i+"' style='background:#C6000B;' class='inputTags ng-scope'>"+$scope.inActiveDate[i]+"<span class='inputclose' ng-click='tagInputRequestClose("+i+");' role='button'></span></div>")($scope));

		}
	}

	$scope.deleteRequest=function(){
		var confirm = $mdDialog.confirm()
		.title("Alert")
		.textContent("Are you sure you want to delete this Request?").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			var data = $.param({
				id : $scope.reqStatusId
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../requestCheckin/delete', data, config)
			.success(function (data, status, headers, config) {
				if(data.substring(7)=='success'){
					na.dtInstance.reloadData();
					$("#requestAddNewmyModal").modal("toggle");		
				}else{alert("error!!");}
			}).error(function (data, status, header, config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
		});
	}

}]);