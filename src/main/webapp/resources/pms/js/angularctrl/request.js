pmsApp.controller('requestCtrl', ['$scope','$q','$http','DTOptionsBuilder','DTColumnBuilder','$mdDialog','$timeout','$compile','$filter',function ($scope,$q, $http, DTOptionsBuilder, DTColumnBuilder,$mdDialog,$timeout,$compile,$filter){
	var hdate = $("#hotelDate").val();
	$scope.hotelDate =new Date(hdate);
	var req = this;
	req.dtInstance = {};
	$scope.roomList=[];
	req.simulateQuery = false;
	req.isDisabled    = false;
	req.selectedItemChange = selectedItemChange;
	req.querySearch   = querySearch;
	req.editRequest = editRequest;
	req.processRequest = processRequest;
	$scope.requestData = {};
	$scope.hstep = 1;
	$scope.mstep = 1;
	$scope.ismeridian=true;
	$scope.checkinNo=0;
	$scope.selectedObj={exp_depart_date:new Date()};
	$scope.facilitySelected={};
	$scope.addons={};
	$scope.inactive={fromDate:new Date($scope.hotelDate),toDate: new Date($scope.hotelDate),minFromDate:new Date($scope.hotelDate),minToDate:new Date($scope.hotelDate)};

	$http({
		url : '../checkin_request/getRoomList',
		method : "POST"
	}).then(function(response) {
		$scope.roomList = response.data;
		req.repos         = loadAll();
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});

	
	
	function querySearch (query) {
		var results = query ? req.repos.filter( createFilterFor(query) ) : req.repos,
				deferred;
		if (req.simulateQuery) {
			deferred = $q.defer();
			$timeout(function () { deferred.resolve( results ); }, Math.random() * 1000, false);
			return deferred.promise;
		} else {
			return results;
		}
	}
	/**
	 * Build `components` list of key/value pairs
	 */
	function loadAll() {
		var repos = $scope.roomList ;
		return repos.map( function (repo) {
			repo.value = repo.room_number.toString();
			return repo;
		});
	}

	/**
	 * Create filter function for a query string
	 */
	function createFilterFor(query) {
		var lowercaseQuery = query;
		return function filterFn(item) {
			return (item.value.indexOf(lowercaseQuery) === 0);
		};
	}


	function selectedItemChange(item) {
		if(item!=null){
			$scope.selectedObj = item;
			$scope.checkinNo= item.checkin_no;
			$scope.selectedObj.exp_depart_date = new Date($scope.selectedObj.exp_depart_date);
			req.dtInstance.reloadData();
		}
	}
	req.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		var urlString="../checkin_request/getRoomRequestList?checkno="+$scope.checkinNo;
		if($scope.checkinNo!=0){
			$http.post(urlString).then(function(result) {
				defer.resolve(result.data);	
				for(var i = 0; i<result.data.length;i++){					
					$scope.requestData[result.data[i].chreq_id]=result.data[i];	
					if(result.data[i].facility_is_payable){
						$scope.requestData[result.data[i].chreq_id].payment='Payable';
					}else{
						$scope.requestData[result.data[i].chreq_id].payment='Not Payable';
					}
				}
			},function(response) {
				console.log(response);
			});
		}else{
			defer.resolve([]);
		}
		return defer.promise;
	}).withPaginationType('full_numbers').withOption('createdRow', function(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	});
	req.dtColumns = [DTColumnBuilder.newColumn("facility_code","Facility"),
	                 DTColumnBuilder.newColumn("chreq_req_date","Request Date & Time").renderWith(function(data, type, full) { return (full.chreq_is_one_time_request?data:'Daily')+' '+new Date(full.chreq_req_time).toLocaleTimeString('en-US')}),
	                 DTColumnBuilder.newColumn("is_req_completed","Addon Status").renderWith(function(data, type, full) { return (data?'Completed':(full.is_canceled?'Cancelled':'Active'))}),

	                 DTColumnBuilder.newColumn("process_status","Today's Status").renderWith(
	                		 function(data, type, full) {
	                			 var sRet="";
	                			 if(!data && new Date(full.process_date).getTime()==new Date($scope.hotelDate).getTime()){
	                				 sRet="<p style='color:#d90505;'>Inacive Today</p>";
	                			 }else if(data){
	                				 sRet="<p style='color:#006a40;'>Processed</p>";
	                			 }else{
	                				 sRet="<p style='color:#05316c;'>Not Processed</p>";
	                			 }
	                			 return sRet;}),
	                			 DTColumnBuilder.newColumn("process_status","").renderWith(function(data, type, full) { 
	                				 var ret="";
	                				 if(!full.is_req_completed && !full.is_canceled){
	                					 if(window.cp_isCanEdit){
	                					 ret=ret+"<a class='btn btn-warning' ng-click='ctrl.editRequest("+full.chreq_id+")'>edit</a>";}
	                					 if(!data && new Date(full.process_date).getTime()!=new Date($scope.hotelDate).getTime()){
	                						 if(window.cp_isCanExecute){
	                						 ret=ret+"<a class='btn btn-default' style='margin-left: 5px;' ng-click='ctrl.processRequest("+full.chreq_id+")'>process</a>";}
	                					 }
	                				 }
	                				 return ret}).withOption('width','140px')];

	function editRequest(id){
		if(window.cp_isCanEdit){
		$scope.requestEditData = $scope.requestData[id];
		$scope.requestEditData.chreq_req_date = new Date($scope.requestEditData.chreq_req_date);

		$http({
			url : '../checkin_request/getCheckinRequestStatusList',
			method : "POST",
			params: {checkinreqid:$scope.requestEditData.chreq_id}
		}).then(function(response) {
			$scope.processList={};
			$scope.processedList={};
			for(var i = 0;i<response.data.length;i++){
				if(!response.data[i].processStatus){
					if(new Date(response.data[i].date).getTime()<=new Date($scope.hotelDate).getTime()){
						$scope.processList[$filter('date')(new Date(response.data[i].date),'yyyy-MM-dd')]={status:false,id:response.data[i].id,is_deleted:false};
					}else{
						$scope.processList[$filter('date')(new Date(response.data[i].date),'yyyy-MM-dd')]={status:true,id:response.data[i].id,is_deleted:false};
					}
				}else{
					$scope.processedList[$filter('date')(new Date(response.data[i].date),'yyyy-MM-dd')]=true;
				}
			}
			$("#requestmyModal").modal({backdrop: "static"});			
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
		}
	}
	$scope.$watch('inactive.fromDate', function (newValue, oldValue, scope) {
		if(new Date($filter('date')($scope.inactive.fromDate,'yyyy-MM-dd'))<new Date($filter('date')($scope.hotelDate,'yyyy-MM-dd'))||new Date($filter('date')($scope.inactive.fromDate,'yyyy-MM-dd'))>new Date($filter('date')($scope.selectedObj.exp_depart_dates,'yyyy-MM-dd'))){
			$scope.inactive.fromDate=oldValue;
		}
		if(new Date($filter('date')($scope.inactive.fromDate,'yyyy-MM-dd'))>=new Date($filter('date')($scope.inactive.toDate,'yyyy-MM-dd'))){
			$scope.inactive.toDate=$scope.inactive.fromDate;
		}
		$scope.inactive.minToDate=$scope.inactive.fromDate;
	}, true);

	$scope.$watch('inactive.toDate', function (newValue, oldValue, scope) {
		if(new Date($filter('date')($scope.inactive.toDate,'yyyy-MM-dd'))<new Date($filter('date')($scope.inactive.fromDate,'yyyy-MM-dd'))|| new Date($filter('date')($scope.inactive.toDate,'yyyy-MM-dd'))>new Date($filter('date')($scope.selectedObj.exp_depart_date,'yyyy-MM-dd'))){
			$scope.inactive.toDate=$scope.inactive.fromDate;
		}
	}, true);

	$scope.addInactiveDate = function(){

		var newDate = $scope.inactive.fromDate;
		var status="success";
		for (var dt = new Date($scope.inactive.fromDate); dt <= new Date($scope.inactive.toDate); dt.setDate(dt.getDate() + 1)) {

			if(!angular.equals({},$scope.processList)){
				if(!$scope.processList.hasOwnProperty($filter('date')(new Date(dt),'yyyy-MM-dd'))){
					if(!$scope.processedList.hasOwnProperty($filter('date')(new Date(dt),'yyyy-MM-dd'))){
						$scope.processList[$filter('date')(new Date(dt),'yyyy-MM-dd')]={status:true,id:0,is_deleted:false};
					}else{
						status="selected date already processed";
						break;
					}
				}else{
					if($scope.processList[$filter('date')(new Date(dt),'yyyy-MM-dd')].is_deleted){
						$scope.processList[$filter('date')(new Date(dt),'yyyy-MM-dd')].is_deleted=false;
					}else{
						status="selected date already exists";
					}
					break;
				}
			}else{
				if(!$scope.processedList.hasOwnProperty($filter('date')(new Date($scope.inactive.fromDate),'yyyy-MM-dd'))){
					$scope.processList[$filter('date')(new Date(dt),'yyyy-MM-dd')]={status:true,id:0,is_deleted:false};
				}else{
					status="selected date already processed";
					break;
				}
			}
		}
		if(status!="success"){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent(status).ok('Ok').parent(angular.element(document.body)));
		}

	}

	$scope.deleteInactiveDate = function(pdate){
		if($scope.processList[pdate].id!=0){
			$scope.processList[pdate].is_deleted=true;
		}else{
			delete $scope.processList[pdate];
		}
	}

	$scope.save = function(){
		$scope.requestEditData.chreq_req_time=$filter('date')(new Date($scope.requestEditData.chreq_req_time),'HH:mm:ss');
		$scope.requestEditData.chreq_req_date=$filter('date')(new Date($scope.requestEditData.chreq_req_date),'yyyy-MM-dd hh:mm a');
		$scope.saveData={addonData:$scope.requestEditData,inactiveList:$scope.processList};
		$http({
			url : '../checkin_request/save',
			method : "POST",
			data:JSON.stringify($scope.saveData)
		}).then(function(response) {
			if(response.data.substring(7)=='success'){
				req.dtInstance.reloadData();
				$("#requestmyModal").modal("toggle");	
			}else{$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));}
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}

	function processRequest(id){
		$scope.reqToProcess = $scope.requestData[id];
		$scope.reqToProcess.canProcess=false;
		if((new Date($scope.reqToProcess.chreq_req_time).toLocaleTimeString()<=new Date().toLocaleTimeString()) && new Date($scope.reqToProcess.process_date).getTime()!=$scope.hotelDate.getTime()){
			$scope.reqToProcess.canProcess=true;
		}
		$scope.process={remarks:"",checkInReequestId:$scope.reqToProcess.chreq_id};
		$("#requestProcessmyModal").modal({backdrop: "static"});	
	}

	$scope.processSave=function(){
		$("#process_box").removeClass("open");
		var data = $.param({addon:JSON.stringify($scope.process)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../dashboard/processAddon', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=="success"){
				req.dtInstance.reloadData();
				$("#requestProcessmyModal").modal("toggle");
			}else{
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title(data.substring(7)).textContent(data.substring(7)).ok('Ok').parent(angular.element(document.body)));
			}
		}).error(function (data, status, header, config){

			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	$scope.cancelAddon=function(){
		$http({	
			url : '../checkin_request/cancelAddon',
			method : "POST",
			params: {checkinreqid:$scope.requestEditData.chreq_id}
		}).then(function(response) {
			if(response.data.substring(7)=="success"){
				req.dtInstance.reloadData();
				$("#guest_details").hide();
				$("#requestmyModal").modal("toggle");
			}else{
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title(response.data.substring(7)).textContent(response.data.substring(7)).ok('Ok!').parent(angular.element(document.body)));
			}
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}


	$scope.newAddon = function(){
		$scope.checkinRequest=[];
		$scope.facilitySelected={};
		if($scope.checkinNo!=0){
			$http({	
				url : '../checkin_request/getRequestList',
				method : "POST",
				params: {checkno:$scope.checkinNo}
			}).then(function(response) {
				$scope.checkinRequest=response.data;
				$scope.addons=$scope.checkinRequest;
				$("#requestAddNewmyModal").modal({backdrop: "static"});	
				$scope.loadProviders();
				$scope.loadFacilities();
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please select room").ok('Ok').parent(angular.element(document.body)));
		}
	}
	$scope.loadFacilities=function(){
		$http({
			url : '../facilities/facilityDetails',
			method : "POST"
		}).then(function(response) {
			$scope.availableFacilities = [];
			response.data.forEach(function(facility){
				facility.need="daily";
				facility.needDate=new Date();
				facility.needTime=new Date();
				facility.provider=0;
				facility.remarks="";
			});
			$scope.availableFacilities= response.data;
			if($scope.addons.length!=0){
				$scope.addons.forEach(function(addon){
					$scope.availableFacilities.forEach(function(facility){
						if(facility.id==addon.facilityId){
							if(!addon.canceled){
								$scope.facilitySelected[addon.facilityId]=true;
								facility.need="daily";
								if(addon.oneTimeReq){
									facility.need='ondate';
								}
								facility.needDate=new Date(addon.reqDate);
								facility.needTime=new Date(addon.reqTime);
								facility.provider=addon.arrangementBy;
								facility.remarks=addon.reqRemarks;
							}
						}
					});
				});
			}


		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	$scope.loadProviders = function(){
		$http({
			url : '../facilityProvider/facilityProviderDetails',
			method : "POST",
		}).then(function(response) {
			$scope.providers = response.data;
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	$scope.saveAddon= function(){
		if(!angular.equals({},$scope.facilitySelected)){
			var oldAddons=[];
			if(!angular.equals({},$scope.checkinRequest)){
				$scope.checkinRequest.forEach(function(checkInReq){
					if(!checkInReq.canceled)
						oldAddons[checkInReq.facilityId]=true;
				});
			}

			$scope.availableFacilities.forEach(function(facility){

				if(facility.id in $scope.facilitySelected){
					if(!(facility.id in oldAddons)){	
						if($scope.facilitySelected[facility.id]){
							var facil = {facilityId:0,oneTimeReq:true,reqDate:"",reqTime:"",arrangementBy:0,reqRemarks:"",isActive:true};
							facil.facilityId=facility.id;
							if(facility.need=="daily"){
								facil.oneTimeReq=false;
							}								
							facil.reqDate=$filter('date')(new Date(facility.needDate),"yyyy-MM-dd HH:mm:ss");
							facil.reqTime=$filter('date')(new Date(facility.needTime),"yyyy-MM-dd HH:mm:ss");
							facil.arrangementBy=facility.provider;
							facil.reqRemarks=facility.remarks;
							facil.active=true;
							$scope.checkinRequest.push(facil);
						}
					}else{
						$scope.checkinRequest.forEach(function(checkInReq){
							checkInReq.isDeleted=false;
							if(facility.need=="daily"){
								checkInReq.oneTimeReq=false;
							}else{checkInReq.oneTimeReq=true;}
							checkInReq.reqDate=$filter('date')(new Date(facility.needDate),"yyyy-MM-dd hh:mm:ss a");
							checkInReq.reqTime=$filter('date')(new Date(facility.needTime),"yyyy-MM-dd hh:mm:ss a");
							checkInReq.arrangementBy=facility.provider;
							checkInReq.reqRemarks=facility.remarks;
							checkInReq.reqTakenDate= $filter('date')(new Date(checkInReq.reqTakenDate),"yyyy-MM-dd hh:mm:ss a");
							checkInReq.active=true;
						});
					}
					$scope.checkinRequest.forEach(function(checkInReq,index,array){
						if(checkInReq.canceled){
							array.splice(index,1);
						}
					});
				}
			});
		}

		$http({
			url : '../checkin_request/saveNew',
			method : "POST",
			data:JSON.stringify({checkin_no:$scope.checkinNo,data:$scope.checkinRequest})
		}).then(function(response) {
			if(response.data.substring(7)=='success'){
				req.dtInstance.reloadData();
				$("#requestAddNewmyModal").modal("toggle");	
			}else{$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));}
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}
	
	$scope.cancelPopUp = function(){
		$("#requestmyModal").modal("toggle");
	}
	$scope.closeProcessModal = function(){
		$("#requestProcessmyModal").modal("toggle");
	}
	$scope.cancelAddNewPopUp = function(){
		$("#requestAddNewmyModal").modal("toggle");
	}
}]);
$(document).ready(function(){
	 $("#input-0").mouseover(function() {
		 $("#input-0").attr("readonly",true);
	 });
});
