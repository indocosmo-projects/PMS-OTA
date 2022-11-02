pmsApp.controller('changeArrCtrl', ChangeArrivalCtrl);
pmsApp.config(function($mdDateLocaleProvider) {
	$mdDateLocaleProvider.formatDate = function(date) {
		return moment(date).format('YYYY-MM-DD');
	};
});
function ChangeArrivalCtrl($scope, $http,$window,$mdDialog,$timeout,$compile,$filter,$q,DTOptionsBuilder,DTColumnBuilder,$rootScope) {
	var hdate=$("#hotelDate").val();
	$scope.dateFormat = $("#dateFormat").val();
	$scope.hoteldate= new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate());
	var charvl = this;
	var resvNo=parseInt($("#resvNo").val());
	var dateFormat = $("#dateFormat").val();
	$scope.contentLoaded = false;
	$scope.resvDtlList = [];
	$scope.planDataArray = [];
	$scope.resvHdr = {};
	$scope.roomRateChekArgs=[];
	$scope.discList = [];
	charvl.dtInstance = {};
	charvl.dtInstanceDisc = {};
	$scope.showDiscTable = false;
	$scope.newArrivalDate = null;
	$scope.changeInRoomType  = false;
	$scope.changeInRatePlan  = false;
	$scope.showStatus=false;
	$scope.submit_click=false;
	$scope.roomsNotAvail = 0;
	$scope.roomTypesAvailable={};
	$scope.assignedRoomRatesList = {};
	$scope.oneAtATime = true;
	$scope.discDispList=[];
	var assignedRateIdList=[];
	$scope.showAssignedWidget=false;
	$scope.fBox=false;
	$scope.avail_check_status = true;
	$scope.dataChanged = false;
	$scope.assignedArray={};
	$scope.totalRoomsAssigned=0;
	$scope.data = {selectedIndex: 0,secondLocked:true,bottom:false,firstLocked:false,thirdLocked:true};
	$http({
		url : '../reservation_test/getResvDtlList',
		method : "POST",
		params:{reservationNo:resvNo}
	}).then(function(response) {
		$scope.resvDtlList = response.data.resvDtl;
		$scope.resvHdr = JSON.parse(response.data.resvHdr);
		$scope.resvHdr.minArrDate =  new Date($scope.resvHdr.minArrDate);
		$scope.newArrivalDate = $scope.resvHdr.minArrDate; 
	$scope.arrivalNewMinDate= new Date(new Date($scope.hoteldate).getFullYear(),new Date($scope.hoteldate).getMonth(),new Date($scope.hoteldate).getDate());
		$scope.discArray={};
		$scope.resvDtlList.forEach(function(resvdtl) {
			var planData = {resvDtlId:resvdtl.resvDtlId,roomTypeCode:resvdtl.roomTypeCode,numRooms:resvdtl.numRooms,occupancy:resvdtl.occupancy,rateId:resvdtl.rateId,rateCode:resvdtl.rateCode,status:0};
			$scope.planDataArray.push(planData);
			if($scope.resvHdr.discType==1){
				if(resvdtl.discId!=0){
					var disc={id:resvdtl.discId,discCode:resvdtl.discCode,isPc:resvdtl.discIsPc,discPc:resvdtl.discPc,discAmount:resvdtl.discAmount,rateId:resvdtl.rateId,rateCode:resvdtl.rateCode,dtlNo:resvdtl.resvDtlNo,status:0}
					$scope.discArray[resvdtl.rateId]=disc; 
				}
				if((assignedRateIdList.indexOf(resvdtl.rateId)==-1)){
					assignedRateIdList.push(resvdtl.rateId)
				}
			}
		});
		if($scope.resvHdr.discType==1){
			if(!angular.equals({},$scope.discArray)){
				$scope.showDiscTable = true;
				for(var key in $scope.discArray){
					$scope.discList.push($scope.discArray[key]);
				}
			}
		}
		$scope.contentLoaded = true;
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});  

	charvl.dtOptions = DTOptionsBuilder.fromFnPromise(getPlanDataTableData).withOption('createdRow', function(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	})
	.withOption('headerCallback', function(header) {
		if (!charvl.headerCompiled) {
			charvl.headerCompiled = true;
			$compile(angular.element(header).contents())($scope);
		}
	}).withDOM('lfrti').withScroller().withOption('deferRender', true).withOption('scrollY', 150).withDisplayLength(10000).withOption('rowCallback', rowCallback);
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		if(aData.status ==2){
			$('td', nRow).css({"color": "#ca0e0e","text-decoration":"line-through"});
			$('td:nth-child(5)',nRow).bind('click', function() {
				$scope.$apply(function() {
					$scope.planDataArray.forEach(function(pData,index){
						if(pData.resvDtlId==aData.resvDtlId){
							$scope.planDataArray.splice(index,1);
							charvl.dtInstance.reloadData();
						}
					});
				});
			});
		}
		if(aData.status==1){
			$('td:nth-child(4)', nRow).css({"color": "#ca0e0e"});
			$('td:nth-child(4)',nRow).bind('click', function() {
				$scope.$apply(function() {
					$scope.selectedDtlId = aData.resvDtlId;
					var parentEl = angular.element(document.body);
					$scope.availRoomRatePlans=aData.availableRooms;
					$scope.newRatePlan = aData.availableRooms[0];
					$scope.newRateCode = "";
					var options = '';
					$scope.availRoomRatePlans.forEach(function(rate_plan){
						options = options+'<md-option value="'+rate_plan.rateId+'">'+rate_plan.rateCode+
						'</md-option>';
					});
					$mdDialog.show({
						parent: parentEl,
						template:
							'<md-dialog aria-label="List dialog">' +
							'  <md-dialog-content> <md-content layout-padding>'+
							'    <div><md-input-container class="searchFormInput">'+
							'  <label>Rate Plan</label>'+
							'<md-select ng-model="newRatePlan">'+
							options+
							'</md-select>'+
							'</md-input-container></div>'+
							'  </md-content> </md-dialog-content>' +
							'  <md-dialog-actions>' +
							'    <md-button ng-click="setupNewRatePlan()" class="md-primary md-raised">' +
							'      Assign' +
							'    </md-button>' +
							'    <md-button ng-click="closeDialog()" class="md-primary">' +
							'      Close' +
							'    </md-button>' +
							'  </md-dialog-actions>' +
							'</md-dialog>',
							locals: {
								items: $scope.availRoomRatePlans
							},
							controller: DialogController
					});
					function DialogController($scope, $mdDialog, items,$rootScope) {
						$scope.closeDialog = function() {
							$mdDialog.hide();
						}
						$scope.setupNewRatePlan = function(){
							items.forEach(function(availRate){
								if(availRate.rateId==$scope.newRatePlan){
									$scope.newRateCode=availRate.rateCode;
								}
							});
							$rootScope.$broadcast("assignNewRatePlan",$scope.newRatePlan,$scope.newRateCode);
							$mdDialog.hide();
						}					
					}
				});
			});
		}
		return nRow;
	};
	charvl.dtColumns = [DTColumnBuilder.newColumn("roomTypeCode","Room Type"),
	                    DTColumnBuilder.newColumn("numRooms","Number Of Rooms"),
	                    DTColumnBuilder.newColumn("occupancy","Occupancy"),
	                    DTColumnBuilder.newColumn("rateCode","Plan").renderWith(function(data,type,full,meta){
	                    	if(full.status==1){
	                    		return "assign rate plan";
	                    	}else {return data;}
	                    }),
	                    DTColumnBuilder.newColumn("status","").renderWith(function(data, type, full,meta) {
	                    	if(data == 2){
	                    		return '<i class="fa fa-trash-o" aria-hidden="true"></i>';
	                    	}else{return "";}
	                    }).withClass('status_col').notSortable()];
	charvl.dtColumns[4].visible = false;
	function getPlanDataTableData() {
		var defer = $q.defer();
		defer.resolve($scope.planDataArray);
		return defer.promise;
	}
	$rootScope.$on("assignNewRatePlan", function(event,ratePlan,rateCode){
		var ratePlanChangeStatus = true;
		$scope.planDataArray.forEach(function(pData,index){
			if(pData.resvDtlId==$scope.selectedDtlId){
				$scope.planDataArray[index].rateId=parseInt(ratePlan);
				$scope.planDataArray[index].rateCode=rateCode;
				$scope.planDataArray[index].status=0;
				charvl.dtInstance.reloadData();
			}
			if($scope.planDataArray[index]==1){
				ratePlanChangeStatus=false
			}
		});
		if(ratePlanChangeStatus){
			$scope.changeInRatePlan  = false;}
	});

	charvl.dtOptionsDisc = DTOptionsBuilder.fromFnPromise(getDiscDataTableData).withOption('createdRow', function(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}).withOption('headerCallback', function(header) {
		if (!charvl.headerCompiledDisc) {
			charvl.headerCompiledDisc = true;
			$compile(angular.element(header).contents())($scope);
		}
	}).withDOM('lfrti').withScroller().withOption('deferRender', true).withOption('scrollY', 150).withDisplayLength(10000).withOption('rowCallback', rowCallbackDisc);
	function rowCallbackDisc(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		if(aData.status ==1){
			$('td', nRow).css({"color": "#ca0e0e","text-decoration":"line-through"});
			$('td:nth-child(4)',nRow).bind('click', function() {
				$scope.$apply(function() {
					$scope.discList.forEach(function(dData,index){
						if(dData.rateId==aData.rateId){
							$scope.discList.splice(index,1);
							charvl.dtInstanceDisc.reloadData();
						}
					});
				});
			});	
		}		
		return nRow;
	};
	var discAddTitle = '<a class="btn label label-info label-mini add_detail_btn" ng-click="showDiscounts()"><i class="fa fa-plus"></i></a>';
	charvl.dtColumnsDisc = [DTColumnBuilder.newColumn("rateCode","Rate Code").withOption('width','30%'),
	                        DTColumnBuilder.newColumn("discCode","Discount Code").withOption('width','30%'),
	                        DTColumnBuilder.newColumn(null,"Discount").withOption('width','30%').renderWith(function(data, type, full,meta) {
	                        	if(data.isPc){
	                        		return data.discPc;
	                        	}else{return data.discAmount;}
	                        }),
	                        DTColumnBuilder.newColumn(null).withTitle(discAddTitle)
	                        .renderWith(function(data, type, full,meta) {
	                        	if(data.status == 1){
	                        		return '<i class="fa fa-trash-o" aria-hidden="true"></i>';
	                        	}else{return "";}
	                        }).withOption('width','10%').notSortable().withClass('status_col')];

	function getDiscDataTableData(){
		var defer = $q.defer();
		defer.resolve($scope.discList);
		return defer.promise;
	}

	$scope.cancelRoomAssignPopUp=function(){
		$("#assignRoomsmyModal").modal("toggle");
		
	}
	
	
	$scope.arrivalDateChange = function(){
		$scope.avail_check_status = true;
		if($scope.dataChanged){
			var confirm = $mdDialog.confirm()
			.title('Do you want to check another date?')
			.textContent('All changes you have made will lose')
			.ok('OK')
			.cancel('Cancel').parent(angular.element(document.body));

			$mdDialog.show(confirm).then(function() {
				$scope.checkNewArrivalDate();
			});
		}else{
			$scope.checkNewArrivalDate();
		}
	}
	$scope.checkNewArrivalDate = function(){
		$scope.showStatus=true;
		$scope.changeInRoomType = false;
		$scope.changeInRatePlan  = false;
		var planData = {};
		$scope.roomsNotAvail=0;
		$scope.planDataArray=[];
		$scope.discList = [];
		$scope.discArray={};
		$http({
			url : '../reservation_test/getNewArrivalDateData',
			method : "POST",
			params: {resvNo:resvNo,newArrivalDate:$filter('date')(new Date($scope.newArrivalDate), dateFormat),discType:$scope.resvHdr.discType,noNight:$scope.resvHdr.numNights,resvType:$scope.resvHdr.resvType}
		}).then(function(response) {
			response.data.availableRooms.forEach(function(newData){
				var planData = {resvDtlId:newData.resv_dtl_no,roomTypeCode:newData.room_type_code,numRooms:newData.num_rooms,occupancy:newData.occupancy,rateId:newData.rate_id,rateCode:newData.rate_code,status:newData.status};

				if(newData.status!=0){
					if(newData.status==2){
						$scope.roomsNotAvail=$scope.roomsNotAvail+newData.num_rooms;
						$scope.changeInRoomType = true;
					}
					if(newData.status==1){
						$scope.changeInRatePlan  = true;
						planData.availableRooms=newData.availableRooms;
					}

				}
				$scope.planDataArray.push(planData);
				if($scope.resvHdr.discType==1){
					if(newData.disc_id!=0){
						var disc={id:newData.disc_id,discCode:newData.disc_code,isPc:newData.disc_is_pc,discPc:newData.disc_pc,discAmount:newData.disc_amount,rateId:newData.rate_id,rateCode:newData.rate_code,dtlNo:newData.resv_dtl_no,status:newData.disStatus}
						$scope.discArray[newData.rateId]=disc;
					}
				}
			});
			if(!angular.equals({},$scope.discArray)){
				$scope.showDiscTable = true;
				for(var key in $scope.discArray){
					$scope.discList.push($scope.discArray[key]);
				}
			}
			if($scope.changeInRoomType){
				charvl.dtColumns[4].visible = true;
			}else{
				charvl.dtColumns[4].visible = false;
			}

			charvl.dtInstance.reloadData();
			charvl.dtInstanceDisc.reloadData();
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	/*
	 * $scope.$watch('newArrivalDate', function (newValue, oldValue, scope) {
	 * $scope.avail_check_status = false; });
	 */
	$scope.checkAvailability=function(){
		$scope.check_avail = true;
		$scope.avail_check_status = true;
		maxRoom=$scope.roomsNotAvail;
		$scope.checkAvailObj={minArrDate : $filter('date')(new Date($scope.newArrivalDate), dateFormat),nights:$scope.resvHdr.numNights,numRooms:$scope.roomsNotAvail};
		$('#imgloader').show();
		var data = $.param({resvHdr:JSON.stringify($scope.checkAvailObj)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomAvailability', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			$scope.roomTypesAvailable=data;
			$scope.data.firstLocked = true;
			$scope.data.secondLocked = false;
			$scope.showAssignedWidget=true;
			$scope.data.selectedIndex = 1;
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});

	}
	$scope.showDetails = function(roomTypeCode,typeId,availRooms){
		$scope.roomTypeId=typeId;
		$scope.roomRates=[];
		newAssgned=[];
		$scope.roomMax = $scope.roomsNotAvail-$scope.totalRoomsAssigned;
		$scope.availableRooms=availRooms;
		$('#imgloader').show();
		var data = $.param({roomTypeDtls:JSON.stringify({roomType:roomTypeCode,arrDate:$filter('date')(new Date($scope.newArrivalDate), dateFormat),nights:$scope.resvHdr.numNights})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomRatePlans', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			if(data.ratePlans.length!=0){

			data.ratePlans.forEach(function(entry) {
				if(entry.occ1){entry.occ1Val=0;}
				if(entry.occ2){entry.occ2Val=0;}
				if(entry.occ3){entry.occ3Val=0;}
				if(entry.occ4){entry.occ4Val=0;}
			});
			$scope.roomRates=data.ratePlans;
			$scope.roomTypeImages= data.images;
			$("#assignRoomsmyModal").modal({backdrop: "static"});
			if(Object.keys($scope.assignedRoomRatesList).length!=0){
				for (var key in $scope.assignedRoomRatesList) {
					if ($scope.assignedRoomRatesList.hasOwnProperty(key)) {
						if($scope.assignedRoomRatesList[key].roomTypeId==$scope.roomTypeId){
							$scope.roomRates.forEach(function(rRate) {
								if(key.substr(0,rRate.rateCode.length)==rRate.rateCode){
									if(rRate.occ1 && $scope.assignedRoomRatesList[key].occ=='occ1'){
										rRate.occ1Val=$scope.assignedRoomRatesList[key].value;}
									if(rRate.occ2 && $scope.assignedRoomRatesList[key].occ=='occ2'){
										rRate.occ2Val=$scope.assignedRoomRatesList[key].value;}
									if(rRate.occ3 && $scope.assignedRoomRatesList[key].occ=='occ3'){
										rRate.occ3Val=$scope.assignedRoomRatesList[key].value;}
									if(rRate.occ4 && $scope.assignedRoomRatesList[key].occ=='occ4'){
										rRate.occ4Val=$scope.assignedRoomRatesList[key].value;}
								}
							});
						}
					}
				}
			}
			}else{
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Sorry  Rate plans are not available').ok('Ok!').parent(angular.element(document.body)));
				}
			
			
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});		
	}

	$scope.assignRooms = function(){
		$(".assgnRoom").each(function(){
			$scope.assignedRoom={id:"",value:0,roomTypeId:"",roomTypeCode:"",rateCode:"",rateId:"",occ:""}
			if($(this).attr('id') in $scope.assignedRoomRatesList){
				if(this.value=="" || this.value==0){
					delete $scope.assignedRoomRatesList[$(this).attr('id')];
				}
			}
			if(this.value!="" && this.value!=0){
				$scope.assignedRoom={id:$(this).attr('id'),value:parseInt(this.value),rateCode:$(this).attr('data-rateCode'),roomTypeId:$(this).attr('data-roomtypeid'),roomTypeCode:$(this).attr('data-roomTypeCode'),rateId:$(this).attr('data-rateid'),occ:$(this).attr('data-occ')};
				$scope.assignedRoomRatesList[$scope.assignedRoom.id]=$scope.assignedRoom;
			}
		});	
		$scope.dataChanged = true;
		$scope.AssignedRoomData = $scope.buildData();
		$("#assignRoomsmyModal").modal("toggle");
	}

	$scope.buildData = function() {
		var returnArr = [];
		var arr1=[];
		var arr2=[];
		$scope.totalRoomsAssigned=0;
		for (var key in $scope.assignedRoomRatesList){
			var resvroom={};
			var occ;
			var assgnd={roomTypeId:$scope.assignedRoomRatesList[key].roomTypeId,roomTypeCode:$scope.assignedRoomRatesList[key].roomTypeCode,rateCode:$scope.assignedRoomRatesList[key].rateCode,rateId:$scope.assignedRoomRatesList[key].rateId,value:$scope.assignedRoomRatesList[key].value};
			if($scope.assignedRoomRatesList[key].occ=="occ1"){
				occ=1;
				assgnd.occ1=$scope.assignedRoomRatesList[key].value;
			}else if($scope.assignedRoomRatesList[key].occ=="occ2"){
				occ=2;
				assgnd.occ2=$scope.assignedRoomRatesList[key].value;
			}else if($scope.assignedRoomRatesList[key].occ=="occ3"){
				occ=3;
				assgnd.occ3=$scope.assignedRoomRatesList[key].value;
			}else if($scope.assignedRoomRatesList[key].occ=="occ4"){
				occ=4;
				assgnd.occ4=$scope.assignedRoomRatesList[key].value;
			}
			if($scope.assignedRoomRatesList[key].rateCode in arr1){
				if($scope.assignedRoomRatesList[key].occ=="occ1"){
					arr1[$scope.assignedRoomRatesList[key].rateCode].occ1=$scope.assignedRoomRatesList[key].value;
				}else if($scope.assignedRoomRatesList[key].occ=="occ2"){
					arr1[$scope.assignedRoomRatesList[key].rateCode].occ2=$scope.assignedRoomRatesList[key].value;
				}else if($scope.assignedRoomRatesList[key].occ=="occ3"){
					arr1[$scope.assignedRoomRatesList[key].rateCode].occ3=$scope.assignedRoomRatesList[key].value;
				}else if($scope.assignedRoomRatesList[key].occ=="occ4"){
					arr1[$scope.assignedRoomRatesList[key].rateCode].occ4=$scope.assignedRoomRatesList[key].value;
				}
				arr1[$scope.assignedRoomRatesList[key].rateCode].value=arr1[$scope.assignedRoomRatesList[key].rateCode].value+$scope.assignedRoomRatesList[key].value;
			}else{
				arr1[$scope.assignedRoomRatesList[key].rateCode]=assgnd;
			}
		}
		for (var key in arr1){		
			var arr1n=[];
			arr1n.push(arr1[key]);
			var assgnd2={roomTypeCode:arr1[key].roomTypeCode,assigned:arr1n,total:arr1[key].value}
			if(arr1[key].roomTypeCode in arr2){
				arr1n=arr2[arr1[key].roomTypeCode].assigned;
				if(arr1[key].roomTypeCode=arr1n[0].roomTypeCode){
					arr1n.push(arr1[key]);
				}
				arr2[arr1[key].roomTypeCode].total=arr2[arr1[key].roomTypeCode].total+arr1[key].value;
				arr2[arr1[key].roomTypeCode].assigned=arr1n;
			}else{
				arr2[arr1[key].roomTypeCode]=assgnd2;
			}  
			$scope.totalRoomsAssigned=$scope.totalRoomsAssigned+parseInt(arr1[key].value);
		}
		$scope.assignedArray = arr2;
		for (var key in arr2){
			returnArr.push(arr2[key]);
		}
		return returnArr;
	};

	$scope.changeToNewRoom = function(){
		if($scope.roomsNotAvail!=$scope.totalRoomsAssigned){
			var asRooms=$scope.roomsNotAvail-$scope.totalRoomsAssigned;
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please Assign Rooms,"+asRooms+" rooms are pending").ok('Ok').parent(angular.element(document.body)));
		}else{
			$scope.planDataArray.forEach(function(pData,index){
				if(pData.status==2){
					$scope.planDataArray.splice(index,1);
					charvl.dtInstance.reloadData();
				}
			});
			for (var key in $scope.assignedRoomRatesList) {
				var dataExist = false;
				var occ = 1;
				if($scope.assignedRoomRatesList[key].occ=="occ2"){occ=2;}else if($scope.assignedRoomRatesList[key].occ=="occ3"){occ=3;}else if($scope.assignedRoomRatesList[key].occ=="occ4"){occ=4;}
				$scope.planDataArray.forEach(function(pData){
					if(pData.rateId==$scope.assignedRoomRatesList[key].rateId && pData.occupancy==occ){
						dataExist = true;
						pData.numRooms= pData.numRooms+parseInt($scope.assignedRoomRatesList[key].value);
					}
				});
				if(!dataExist){
					var planData = {resvDtlId:0,roomTypeCode:$scope.assignedRoomRatesList[key].roomTypeCode,numRooms:parseInt($scope.assignedRoomRatesList[key].value),occupancy:occ,rateCode:$scope.assignedRoomRatesList[key].rateCode,rateId:parseInt($scope.assignedRoomRatesList[key].rateId),status:0};
					$scope.planDataArray.push(planData);	
				}
			}
			assignedRateIdList=[];
			$scope.planDataArray.forEach(function(pData){
				if((assignedRateIdList.indexOf(pData.rateId)==-1)){
					assignedRateIdList.push(pData.rateId)
				}
			});
			$scope.data.firstLocked = false;
			$scope.data.secondLocked = true;
			$scope.showAssignedWidget=false;
			$scope.check_avail = false;
			$scope.changeInRoomType = false;
			$scope.data.selectedIndex = 0;
		}
	}

	$scope.getRoomRateDetails = function(){
		if(!$scope.changeInRatePlan){
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=false;
			$scope.data.selectedIndex = 2;
			$scope.discDispList=[];
			var discDisp={code:"",value:""};
			if($scope.resvHdr.discType==2){
				discDisp = {code:$scope.resvDtlList[0].discCode,value:""};
				if(discDisp.code!=""){
					$("#discountCheck").show();
				}
				if($scope.resvDtlList[0].discIsPc){
					discDisp.value=$scope.resvDtlList[0].discPc +"%";
				}else{
					discDisp.value=$scope.resvDtlList[0].discAmount;	
				}
				$scope.discDispList.push(discDisp);
			}
			$scope.roomRateChekArgs=[]; 

			$scope.planDataArray.forEach(function(pData){

				var roomRateArgs={resvDtlNo:pData.resvDtlId,arrDate:$filter('date')(new Date($scope.newArrivalDate), dateFormat),numRooms:pData.numRooms,numNights:$scope.resvHdr.numNights,rateId:pData.rateId,occupancy:pData.occupancy};
				if($scope.discList.length!=-1){
					if($scope.resvHdr.discType==2){
						roomRateArgs.discId=$scope.resvDtlList[0].discId;
						if($scope.resvDtlList[0].discIsOpen){
							if($scope.resvDtlList[0].discIsPc){
								roomRateArgs.openDiscount=$scope.resvDtlList[0].discPc;
							}else{
								roomRateArgs.openDiscount=$scope.resvDtlList[0].discAmount;	
							}
						}
					}else{
						$scope.discList.forEach(function(disc,index){
							if(disc.status!=1){
								if(pData.rateId==disc.rateId){
									roomRateArgs.discId=disc.id;
									discDisp={};
									discDisp.code = disc.discCode;
									if(disc.isPc){
										discDisp.value = disc.discPc;
									}else{discDisp.value = disc.discAmount;}
									$scope.discDispList.push(discDisp);
								}
							}else if(disc.status==1){
								$scope.discList.splice(index,1);
							}

						});
					}
				}			
				$scope.roomRateChekArgs.push(roomRateArgs);
			});
			$http({
				url : '../reservation_test/getRoomRateDetails',
				method : "POST",
				data : $scope.roomRateChekArgs
			}).then(function(response) {
				$scope.detailedRateSummary=response.data;
				var rateDtlArr=[];
				var arrayRate=[];
				var subtotal=0.0,taxTotal=0.0,discTotal=0.0,sTaxTotal=0.0,sChargeTotal=0.0,grantTotal=0.0,discTax=0.0,discTaxTotal=0.0,taxTotal=0.0,taxPc=0.0,subtotalCharges=0.0;
				for(var i=0;i<response.data.length;i++){
					var rateDtlObj={roomType:response.data[i].roomTypeCode,total:response.data[i].totalRate,numRooms:response.data[i].numRooms,numNights:response.data[i].numNights}
					if(response.data[i].roomTypeCode in arrayRate){
						arrayRate[response.data[i].roomTypeCode].total=arrayRate[response.data[i].roomTypeCode].total+response.data[i].totalRate;
						arrayRate[response.data[i].roomTypeCode].numRooms=arrayRate[response.data[i].roomTypeCode].numRooms+response.data[i].numRooms;
					}else{
						arrayRate[response.data[i].roomTypeCode]=rateDtlObj;
					}
					
					taxPc=(response.data[i].ratePerOcc[0].tax1_pc+response.data[i].ratePerOcc[0].tax2_pc+response.data[i].ratePerOcc[0].tax3_pc+response.data[i].ratePerOcc[0].tax4_pc);
					discTotal=discTotal+response.data[i].totalDisc;
					discTax=((taxPc/100)*response.data[i].totalDisc);
					discTaxTotal=discTaxTotal+discTax;
					taxTotal=taxTotal+response.data[i].totalTax-discTax;
					sTaxTotal=sTaxTotal+response.data[i].totalSTax;
					sChargeTotal=sChargeTotal+response.data[i].totalSCharge;
					subtotalCharges=subtotalCharges+response.data[i].totalRate;
				}
				grantTotal=subtotalCharges-discTotal-discTaxTotal+sTaxTotal;
				subtotal=grantTotal-taxTotal+discTotal;

				for (var key in arrayRate){
					rateDtlArr.push(arrayRate[key]);
				}
				$scope.rateSummary={subTotal:Math.round(subtotal * 100) / 100,taxTotal:Math.round(taxTotal * 100) / 100,discTotal:Math.round(discTotal * 100) / 100,sTaxTotal:Math.round(sTaxTotal * 100) / 100,totalSCharge:Math.round(sChargeTotal * 100) / 100,grantTotal:Math.round(grantTotal * 100) / 100,rateArray:rateDtlArr};
				if($filter('date')(new Date($scope.newArrivalDate), dateFormat) !=$filter('date')(new Date($scope.resvHdr.minArrDate), dateFormat)){
					$scope.resvHdr.maxDepartDate=new Date(new Date($scope.newArrivalDate).getFullYear(),new Date($scope.newArrivalDate).getMonth(),new Date($scope.newArrivalDate).getDate()+$scope.resvHdr.numNights);
				}
				$scope.newDapartDate = new Date($scope.resvHdr.maxDepartDate);
			}, function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please Assign RatePlan ").ok('Ok').parent(angular.element(document.body)));
		}
	}
	$scope.showDiscounts = function(){
		$http({
			url : '../reservation_test/getDiscountDetails',
			method : "POST",
			data : assignedRateIdList
		}).then(function(response) {
			$scope.availDiscounts = response.data;
			$scope.availDiscounts.plan.forEach(function(pDisc){
				pDisc.selected=false;
				$scope.discList.forEach(function(disc){
					if(disc.id==pDisc.id){
						pDisc.selected=true;
					}
				});
			});			
			$("#availDiscountsmyModal").modal({backdrop: "static"});

		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	$scope.applyDiscount =function(){
		if(!angular.equals({},$scope.availDiscounts.plan)){
			$scope.discList=[];			
			$scope.availDiscounts.plan.forEach(function(planBased){
				if(planBased.selected){

					$scope.newdisc={id:planBased.id,discCode:planBased.code,isPc:planBased.isPc,discPc:planBased.discPc,discAmount:planBased.discAmount,rateId:planBased.rateId,rateCode:"",status:0}
					$scope.planDataArray.forEach(function(planData){
						if($scope.newdisc.rateId==planData.rateId){
							$scope.newdisc.rateCode=planData.rateCode;
						}
					});					
					$scope.discList.push($scope.newdisc);
				}
			});	
			$scope.dataChanged = true;
			charvl.dtInstanceDisc.reloadData();
			$("#availDiscountsmyModal").modal("toggle");
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("No Discount selected").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));	
		}
	}
	$scope.toggle = function(item) {
		item.selected = !item.selected;
	};
	$scope.save = function(){
		$scope.submit_click=true;
		if($filter('date')(new Date($scope.newArrivalDate), dateFormat)!=$filter('date')(new Date($scope.resvHdr.minArrDate), dateFormat) && $scope.avail_check_status){
			$http({
				url : '../reservation_test/saveChangeArrivalDate',
				method : "POST",
				data : JSON.stringify({resvNo:resvNo,arrDate:$filter('date')(new Date($scope.newArrivalDate), dateFormat),rateDtl:$scope.roomRateChekArgs,disc:$scope.discList})
			}).then(function(response) {
				var confirm = $mdDialog.alert()
				.title('Success')
				.textContent('Arrival date changed successfully!')
				.ok('OK').parent(angular.element(document.body));

				$mdDialog.show(confirm).then(function() {
					window.location="../reservation_test/reservationList";
				});
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});
		}else{
			var confirm = $mdDialog.alert()
			// .title('No change in arrival date')
			.textContent('No Change made.')
			.ok('OK').parent(angular.element(document.body));

			$mdDialog.show(confirm).then(function() {
				window.location="../reservation_test/reservationList";
			});
		}
	}
	
	$scope.cancelDiscPopUp = function(){
		$("#availDiscountsmyModal").modal("toggle");
	}

	$scope.backFunction = function(){
		window.location="../reservation_test/tools?reservationNo="+resvNo;
	}

}
var newAssgned=[];
pmsApp.directive("limitRoom", [function() {
	return {
		restrict: "A",
		link: function(scope, elem, attrs) {
			var limit = parseInt(attrs.limitRoom);
			angular.element(elem).on("keyup change focusout", function(e) {
				newAssgned[this.id]=this.value;
				var totalAssigned=0;
				for (var p in newAssgned){
					totalAssigned=totalAssigned+parseInt(newAssgned[p]);
				}
				var assignedPerRoomType=0;
				if(!angular.equals({},scope.assignedArray)){
					if(scope.assignedArray.hasOwnProperty($(this).attr("data-roomtypecode"))){
						assignedPerRoomType = scope.assignedArray[$(this).attr("data-roomtypecode")].total;
					}
				}
				if (parseInt(this.value)>limit || totalAssigned>limit || (parseInt(this.value)+assignedPerRoomType)>limit || totalAssigned>scope.roomMax || (scope.totalRoomsAssigned+parseInt(this.value))>scope.roomsNotAvail){
					this.value=0;
					newAssgned[this.id]="0";
					e.preventDefault();
				}
			});
		}
	}
}]);
$(document).ready(function(){
	$(".md-datepicker-input").mouseover(function(){
		$('.md-datepicker-input').attr('readonly', true);
	});
});
