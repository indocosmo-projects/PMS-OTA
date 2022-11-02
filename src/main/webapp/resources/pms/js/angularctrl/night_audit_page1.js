var checkOutStatus=0;
pmsApp.controller('audit1Controller', ['$compile','$scope','$q','$http','DTOptionsBuilder','DTColumnBuilder','$window','$mdDialog','$rootScope','$filter','$compile',function ($compile,$scope,$q,$http, DTOptionsBuilder, DTColumnBuilder,$window,$mdDialog,$rootScope, $filter,$compile){
	$scope.dateFormat = $("#dateFormat").val();
	var na = this;
	na.dtInstance = {};
	$scope.countshiftRemain=window.closecount;
	$scope.count=window.countclose;
	na.setNoShow=setNoShow;
	na.dtInstance1 = {};
	na.goToCheckOut=goToCheckOut;
	na.toggleOne=toggleOne;
	na.hasExtendStay={};
	$scope.stayExtnd=true;
	$rootScope.stay=[];
	var urlString1="../nightAudit/expectedArrivals";
	na.dtOptions1 = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer(); 
		$scope.unconfirmedCount=0;
		$scope.confirmed=0;
		$http.get(urlString1).then(function(result) {
			defer.resolve(result.data);
			for(i=0;i<result.data.length;i++){
				$scope.roomStatus = result.data[i].status;
				if($scope.roomStatus==0){
					$scope.unconfirmedCount++;
				}
				if($scope.roomStatus==1){
					$scope.confirmed++;
				}
			}
		});
		return defer.promise;
		alert(data);
	}).withDOM('lfrti').withScroller().withOption('deferRender', true).withOption('scrollY', 200).withDisplayLength(10000).withOption('rowCallback', rowCallback1);
	na.dtColumns1 = [DTColumnBuilder.newColumn("firstName","Name").renderWith(function(data, type, full) { return '<a style="color:#0000ff;">'+data+" "+full.lastName+'</a>';}),
	                DTColumnBuilder.newColumn("resvDate","Reserv Date").renderWith(function(data, type, full){data = $filter('date')(new Date(data),$scope.dateFormat);return data;}),
	                DTColumnBuilder.newColumn("roomTypeCode","Room Type"),
	                DTColumnBuilder.newColumn("phone","Phone"),
	                DTColumnBuilder.newColumn("status","Reserv Status").renderWith(function(data, type, full) { 
	               if(full.status==0){return 'UNCONFIRMED'}else{return 'CONFIRMED'}})
	                ];

	function rowCallback1(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		$('td', nRow).unbind('click');
		if(aData.status==0 || aData.status==1 || aData.status==4){
			$('td',nRow).bind('click', function() {
				$scope.$apply(function() {
					var rno="[No Room]";
					var occ="Occupancy";
					$scope.resvNo=aData.resvNo
					if(aData.roomName!="0" && aData.roomName!="" && aData.roomName!=null){rno=aData.roomName;}
					if(aData.occupancy==1){occ="Single"}else if(aData.occupancy==2){occ="Double"}else if(aData.occupancy==3){occ="Tripple"}else if(aData.occupancy==4){occ="Quad"}
					$scope.expArr={name:aData.firstName+" "+aData.lastName,email:aData.email,phone:aData.phone,address:aData.address,roomType:aData.roomTypeCode,roomNo:rno,occupancy:occ,ratePlan:aData.rateCode,resvRoomNo:aData.resvRoomNo,ResvByname:aData.resvByFirstName,arrDate:aData.arrivalDate,bookDate:aData.resvDate,status:aData.status};
					$("#expArrivalmyModal").modal({backdrop: "static"});
				});
			})
		}
		if(aData.isNoShow!=1){$('td:eq(4)', nRow).css('color', '#008000');}
		else if(aData.isNoShow==1){$('td:eq(4)', nRow).css('color', '#7986cb');}
		return nRow;
	}

	function setNoShow(){
		window.location = "../reservation_test/reservationNoShow?reservationNo=" + $scope.resvNo;
	}
	
	$scope.confirmCheckIn=function(){
		window.location = "../checkIn/checkInEdit?resvId=" + $scope.resvNo +"&currentLocation=nightAudit";
	}
	
	$scope.cancelEAPopUp=function(){
		$("#expArrivalmyModal").modal('toggle');	
	}
	
	$scope.cancelResv=function(){
		window.location = "../reservation_test/resvCancellation?resrvId=" + $scope.resvNo;
	}

	
	var urlString="../nightAudit/expDepartDetails";
	na.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString).then(function(result) {
			defer.resolve(result.data);
			checkOutStatus=result.data.length;
			$rootScope.checkoutStatusLength=checkOutStatus;
		});
		return defer.promise;
	}).withDOM('lfrti').withScroller().withOption('deferRender', true).withOption('scrollY', 200).withDisplayLength(10000).withOption('rowCallback', rowCallback).withOption('createdRow', function(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	});
	na.dtColumns = [
	                DTColumnBuilder.newColumn("roomName","Room").renderWith(function(data, type, full) { return '<a id="room" style="color:#0000ff;">'+data+'</a>';}),
	                DTColumnBuilder.newColumn("arrivalDate","Arrival Date").renderWith(function(data, type, full){data = $filter('date')(new Date(data),$scope.dateFormat);return data;}),,
	                DTColumnBuilder.newColumn("firstName","Name").renderWith(function(data, type, full) {return data+" "+full.lastName;}),
	                DTColumnBuilder.newColumn("phone","Phone"),	                
	                DTColumnBuilder.newColumn("folioBal","Folio Balance"),
	                DTColumnBuilder.newColumn("","Extend Stay").renderWith(function(data, type, full) {
	                	var sRet="";
	                	/*if(full.folioBal==0.00){
		                	sRet='<input type="checkbox"  ng-disabled="true"  class="newbox" >';
	                	}else{
	                		sRet='<input type="checkbox" ng-model="auditCtr.hasExtendStay[' + full.checkinNo+ ']"  ng-click="auditCtr.toggleOne('+full.checkinNo+','+full.roomName+');"  class="newbox" >';
	                	}*/
	                	//<input type="checkbox" ng-model="auditCtr.hasExtendStay[' + full.checkinNo+ ']"  ng-click="auditCtr.toggleOne('+full.checkinNo+','+full.roomName+');"  class="newbox" >';
	                	sRet='<input type="checkbox" ng-model="auditCtr.hasExtendStay[' + full.checkinNo+ ']"  ng-click="auditCtr.toggleOne('+full.checkinNo+');"  class="newbox" >';
	                	
	                	return sRet;
	                })
	                ];


	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		$('td',nRow).bind('click', function(event) {
			$scope.$apply(function() {
				if(event.target.id == "room"){
					$scope.roomClick(aData);
				}
			});
		})
		return nRow;
	}
	 
	$scope.roomClick=function(aData){
		var occ="Occupancy";
		$scope.folioBindNo=aData.folioBindNo;
		if(aData.occupancy==1){occ="Single"}else if(aData.occupancy==2){occ="Double"}else if(aData.occupancy==3){occ="Tripple"}else if(aData.occupancy==4){occ="Quad"}
		$scope.exp={checkinNo:aData.checkinNo,name:aData.firstName+" "+aData.lastName,email:aData.email,phone:aData.phone,address:aData.address,roomType:aData.roomTypeCode,roomNo:aData.roomName,occupancy:occ,ratePlan:aData.rateCode,arrDate:aData.arrivalDate,depDate:aData.expDepartDate,folioBal:aData.folioBal};
		$("#expCheckoutmyModal").modal({backdrop: "static"});	
	}
	
	
	
	function toggleOne(checkinNum,roomName) {
		$rootScope.countStay=0;
		$rootScope.checkinNum=checkinNum;
		angular.forEach(na.hasExtendStay, function(value, key){
			if (na.hasExtendStay.hasOwnProperty(key)) {
				if(value==true){
					$rootScope.countStay++;
					$scope.stayExtnd=false;
				}else{
					 delete na.hasExtendStay[key];
					 $rootScope.stay.push(na.hasExtendStay);
				}
				
			}
		});
	}

	$scope.ExtendStay=function(){
		if($rootScope.countStay>0){
			$http({
				url : '../nightAudit/extendOneDay',
				method : "POST",
				data:JSON.stringify(na.hasExtendStay)
			}).then(function(response) {

				if(response.data.substring(7)=='success'){
					var confirm = $mdDialog.alert()
					.title("Extended to one day successfully").ok('OK').parent(angular.element(document.body));
					$mdDialog.show(confirm).then(function(){
						na.dtInstance.reloadData();
						window.location = "";	
						$rootScope.countStay=0;
						$scope.stayExtnd=true;
					});

				}
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});

		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Select atleast one room").ok('Ok').parent(angular.element(document.body)));

		}

	}
	function goToCheckOut(){
		window.location = "../checkOut/checkOutEdit?folioBindNo=" + $scope.folioBindNo;
	}
	$scope.cancelEDPopUp=function(){
		$("#expCheckoutmyModal").modal('toggle');	
	}
}]);

pmsApp.controller('auditPage1Controller', ['$scope','$http','$window','$mdDialog','$rootScope',function ($scope,$http,$window,$mdDialog,$rootScope){

	$scope.next = function(){
		var checkinNum=$rootScope.checkinNum;
		$scope.pendingStay=$rootScope.checkoutStatusLength-$rootScope.countStay;
//		&& $scope.pendingStay!=0
		if(checkOutStatus!=0  ){

			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("You have pending check-outs").ok('Ok').parent(angular.element(document.body)));

		}else if($scope.unconfirmedCount!=0 || $scope.confirmed!=0){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Reservation(s) are pending, make it Check-In/No-show/Cancel before proceeding.").ok('Ok').parent(angular.element(document.body)));
		}else{

			if($scope.count==0){

				$window.location.href = "../nightAudit/getWizardPage2";

			}else{
				var confirm = $mdDialog.alert()
				.title("Please close the shift before night audit").ok('OK').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function(){
					window.location  = "../shiftManagement/openshift";
				});
			}


		}
	}

	$scope.cancel=function(){
		window.history.back();
	}
}]);