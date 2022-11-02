var resvApp = angular.module('resvCancel', ['ngMaterial']);
resvApp.controller('resvCancelController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	$scope.alertFn = function (msg) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Alert').textContent(msg).ok('Ok').parent(angular.element(document.body)));  
	}
	onloadValidator(".validator");	
	OnloadValidationEdit('.validator');	
	$scope.cancelDisable=true;
	$scope.resvNo=window.reservationNo
	$scope.percentage=0;
	$scope.penalty=0.0;
	$scope.isfine=false;
	$scope.reservationNo = parseInt(angular.element('#reservationNo' ).val());
	$scope.deposit=window.depositAmnt;	  
	$scope.resv={hdr:{},dtl:[],room:[]};
	$scope.cancelCommonReason="";
	$scope.selectAll=false;
	$scope.$watch('percentage', function() {
		$scope.penalty=$scope.deposit*($scope.percentage/100);
	});
	
	$scope.backToList=function(){
		window.location = "../reservation_test/tools?reservationNo=" +$('#reser_id').html();
	}
	
	$http({
		url: '../reservation_test/getReservationRecord',
		method: 'POST',
		params:{reservationNo:$scope.reservationNo}
	}).then(function(response){
		$scope.resv.hdr = JSON.parse(response.data.resvHdr);
		$scope.resv.dtl = response.data.resvDtl;
		$scope.resv.room = response.data.resvRoom;
		for(i=0;i<$scope.resv.room.length;i++){
			$scope.reservationRoom=$scope.resv.room[i].roomStatus;
			if($scope.reservationRoom==1){
				$scope.toggleAll()==false;
			}
		}
		$scope.resv.hdr.numNights = $scope.resv.dtl[0].numNights;
		$scope.resv.room.forEach(function(resvroom) {
			$scope.resv.dtl.forEach(function(resvdtl) {
				if(resvroom.resvDtlNo==resvdtl.resvDtlNo){
					resvroom.rateId=resvdtl.rateId;
					resvroom.rateCode=resvdtl.rateCode;
					resvroom.roomTypeId=resvdtl.roomTypeId;
					resvroom.roomTypeCode=resvdtl.roomTypeCode;
					resvroom.occupancy=resvdtl.occupancy;
				}
			})
		})
		if($scope.checkCancel()){
			$scope.selectAll=true;
			$scope.cancelDisable=true;	
		}else{
			$scope.selectAll=false;
		}
		sortByKey($scope.resv.room,'rateId');
		sortByKey($scope.resv.room,'roomTypeId');
	});
	$scope.calcPenality=function(){
		$scope.percent=$("#percentage").val();
		$scope.penalty=$scope.deposit*($scope.percent/100);
	}
	function sortByKey(array, key) {
		return array.sort(function(a, b) {
			var x = a[key]; var y = b[key];
			return ((x < y) ? -1 : ((x > y) ? 1 : 0));
		});
	}
	$scope.toggle = function(item) {
		item.roomStatus = !item.roomStatus;
		$scope.cancelDisable = false;
		if($scope.checkCancel()){
			$scope.selectAll=true;
			$scope.cancelDisable=false;
		}
		else{
			$scope.selectAll=false;
		}
	};
	$scope.toggleAll = function(){
		$scope.cancelDisable = false;
		if($scope.checkCancel()){
			$scope.selectAll=false;
			$scope.resv.room.forEach(function(resvroom) {
				if(resvroom.roomStatus!=5 && resvroom.roomStatus!=2 && resvroom.roomStatus!=3 && resvroom.roomStatus!=7){
					resvroom.roomStatus=0;
				}
			});
		}else{$scope.selectAll=true;
		$scope.resv.room.forEach(function(resvroom) {
			if(resvroom.roomStatus!=5 && resvroom.roomStatus!=2 && resvroom.roomStatus!=3 && resvroom.roomStatus!=7){
				resvroom.roomStatus=1;
			}
		});
		}
	};
	$scope.checkCancel=function(){
		var roomStatus=1;
		$scope.resv.room.forEach(function(resvroom) {
			if(resvroom.roomStatus!=5 && resvroom.roomStatus!=2 && resvroom.roomStatus!=3 && resvroom.roomStatus!=7){
				if(!resvroom.roomStatus){
					roomStatus=0;
				}
			}
		});
		return roomStatus;
	}
	
	$scope.Cancellation = function(){
		if (validation('.validator') == "false") {
			return false;
		}
		$scope.cancelButton=0;
		if($scope.selectAll==true){
			$scope.cancelButton=2;
		}else{
			$scope.cancelButton=0;
		}
		$scope.checkCount=0;
		for(i=0;i<$scope.resv.room.length;i++){
			if($scope.resv.room[i].roomStatus==true){
				$scope.checkCount++;
			}
		}
		var cancelArray=[];
		$scope.resv.room.forEach(function(resvroom) {
			if(resvroom.roomStatus!=5){
				var cancelObj = {resvRoomNo:0,roomStatus:0,cancelReason:""};
				cancelObj.resvRoomNo=resvroom.resvRoomNo;
				cancelObj.cancelReason=resvroom.cancelReason;
				
				if(resvroom.roomStatus==true || resvroom.roomStatus==2){
					$scope.statusRoom=2; // cancelled data saved as 2 in resv_room
				}else{
					$scope.statusRoom=0;
				}
				cancelObj.roomStatus=$scope.statusRoom;
				cancelArray.push(cancelObj);
			}
		});
		if($scope.checkCount!=0){
			$scope.chanceCancel = 0;
			$scope.notCancelCount = 0;
			for(i=0;i<cancelArray.length;i++){
				if(cancelArray[i].cancel==0){
					$scope.chanceCancel++;
				}
				if(cancelArray[i].roomStatus==0){
					$scope.notCancelCount++;
				}
			}
			$scope.cancel={resvNo:$scope.resvNo,room:cancelArray,reason:$scope.cancelCommonReason,isfine:true,penalty:$scope.penalty,baction:"cancel",disbl:false,percentage:$scope.percentage,deposit:$scope.deposit,numberCancel:$scope.chanceCancel,noNoCancel:$scope.notCancelCount,nightCount:$scope.resv.hdr.numNights,checkStatus:$scope.cancelButton};
				$('#imgloader').show();	
				$http({
					url:'../reservation_test/resvCancellationSave',
					method:'POST',
					params:{cancelJson:JSON.stringify($scope.cancel)}
				}).success(function(response){
					if(response.includes("success")){
						var msg = response.substring(0).split("_");
						if(msg[0]=="success"){
							var confirm = $mdDialog.alert()
							.title("Cancelled successfully").ok('OK').parent(angular.element(document.body));
							$mdDialog.show(confirm).then(function(){
								window.location  = "../reservation_test/tools?reservationNo="+$scope.cancel.resvNo;
								$http({
									url:'../reservation_test/mailCancellation',
									method:"POST",
									params:{resvNum:msg[1],cancelJson:JSON.stringify($scope.cancel)}
								}).then(function(response){
									window.location = "../reservation_test/tools?reservationNo=" +$scope.cancel.resvNo;
								})
							})
						}
					}
				})
			.error(function (data, status, header, config) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});

		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent('Please select room').ok('Ok').parent(angular.element(document.body)));
		}
			
	}
	
}]);
