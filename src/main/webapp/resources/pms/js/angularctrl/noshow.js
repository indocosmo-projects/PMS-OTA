pmsApp.controller('noshow_ctrl', ['$q','$scope','$http','$mdDialog',function ($q,$scope, $http,$mdDialog){	
	$scope.dateFormat = $("#dateFormat").val();
	$scope.noShowDisable = true;
	$scope.percentage=0;
	$scope.penalty=0.0;
	$scope.isfine=false;
	$scope.deposit=window.depositAmnt;	  
	$scope.reservationNo = parseInt(angular.element( '#reservationNo' ).val());
	$scope.resv={hdr:{},dtl:[],room:[]};
	$scope.noShowCommonReason="";
	var dateFormat = $("#dateFormat").val();
	$scope.selectAll=false;
	$http({
		url : '../reservation_test/getReservationRecord',
		method : "POST",
		params:{reservationNo:$scope.reservationNo}
	}).then(function(response) {
		$scope.resv.hdr = JSON.parse(response.data.resvHdr); 
		$scope.deposit=$scope.resv.hdr.depositAmount;
		$scope.resv.hdr.maxDepartDate = new Date($scope.resv.hdr.maxDepartDate);
		$scope.resv.hdr.minArrDate = new Date($scope.resv.hdr.minArrDate);
		$scope.resv.hdr.cutOffDate = new Date($scope.resv.hdr.cutOffDate);
		$scope.resv.hdr.resvDate = new Date($scope.resv.hdr.resvDate);
		$scope.resv.dtl = response.data.resvDtl;
		$scope.resv.room = response.data.resvRoom;
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
			});
			
		});
		if($scope.checkNoShow()){
			$scope.selectAll=true;
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
		item.isNoShow = !item.isNoShow;
		$scope.noShowDisable = false;
		if($scope.checkNoShow()){
			$scope.selectAll=true;
		}
		else{
			$scope.selectAll=false;
			/*$scope.resv.room.forEach(function(resvroom) {
				if(resvroom.isNoShow == false){
					$scope.noShowDisable = true;
				}else{
					$scope.noShowDisable = false;
				}
			});*/
		}
	};
	$scope.toggleAll = function(){
		$scope.noShowDisable = false;
		if($scope.checkNoShow()){
			$scope.selectAll=false;
			$scope.resv.room.forEach(function(resvroom) {
				/*2321 digna 20180622 begin*/
				if(resvroom.roomStatus==0 || resvroom.roomStatus == 1){
			   /*2321 digna 20180622 end */
					resvroom.isNoShow=false;
					$scope.noShowDisable = true;
				}
			});
		}else{
			$scope.selectAll=true;
		    $scope.resv.room.forEach(function(resvroom) {
		    	/*2321 digna 20180622 begin*/
			if(resvroom.roomStatus == 0 || resvroom.roomStatus == 1){
				/*2321 digna 20180622 end*/
				resvroom.isNoShow=true;
				$scope.noShowDisable = false;
			}
		});
		}
	};

	$scope.checkNoShow=function(){
		var noshow=true;
		$("#backButn").show();
		$scope.resv.room.forEach(function(resvroom) {
			/*2321 digna 20180622 begin*/
			//if(resvroom.roomStatus==0 && resvroom.roomStatus==1){
			/*2321 digna 20180622 end*/
				if(!resvroom.isNoShow){
					noshow=false;
				}
			//}
		});
		return noshow;
	}
	$scope.back=function(){
		window.location = "../reservation_test/tools?reservationNo=" +$scope.reservationNo;
	}	
	$scope.saveNoShow = function(){
		$scope.noshowButton=0;
		if($scope.selectAll==true){
			$scope.noshowButton=3;
		}else{
			$scope.noshowButton=0;
		}
		var noShowCount=0;
		for(i=0;i<$scope.resv.room.length;i++){
			if($scope.resv.room[i].isNoShow==true){
				noShowCount++;
			}
		}
		var cancelCount=0;
		for(i=0;i<$scope.resv.room.length;i++){
			if($scope.resv.room[i].roomStatus==3){
				cancelCount++;
			}
		}
		var noShowArray=[];
		$scope.resv.room.forEach(function(resvroom) {
			/*2321 digna 20180622 begin*/
			if(resvroom.roomStatus ==0 || resvroom.roomStatus==1){
				/*2321 digna 20180622 end*/
				var noshowObj = {resvRoomNo:0,noShow:false,noshowReason:""};
				noshowObj.resvRoomNo=resvroom.resvRoomNo;
				noshowObj.noShow=resvroom.isNoShow;
				if(resvroom.noshowReason!="" && resvroom.hasOwnProperty("noshowReason")){
					noshowObj.noshowReason=resvroom.noshowReason;
				}else{
					if($scope.selectAll){
						noshowObj.noshowReason=$scope.noShowCommonReason;
					}
				}
				noShowArray.push(noshowObj);
			}
		});
		if(noShowArray.length!=0 && noShowCount!=0){
			$scope.countRoom = 0;
			$scope.chanceNoShow = false;
			for(i=0;i<noShowArray.length;i++){
				$scope.noShows = noShowArray[i].noShow;
				if(noShowArray[i].noShow==false){
					$scope.countRoom++;
					if($scope.countRoom!=0){
						$scope.chanceNoShow = true;
					}
				}
			}
			var dataToSave={resvNo:$scope.reservationNo,room:noShowArray,penalty:$scope.penalty,percentage:$scope.percentage,penalty:$scope.penalty,deposit:$scope.deposit,isfine:true,numberNoShow:$scope.countRoom,checkStatus:$scope.noshowButton};
			$('#imgloader').show();
			$http({
				url : '../reservation_test/saveNoShow',
				method : "POST",
				data:JSON.stringify(dataToSave)
			}).then(function(response) {
				var msg = response.data.substring(7).split("_");
				if(msg[0]=="success"){
					$('#imgloader').hide();
					var confirm = $mdDialog.alert()
					.title("Completed successfully").ok('OK').parent(angular.element(document.body));
					$mdDialog.show(confirm).then(function(){
					window.location  = "../reservation_test/reservationList";
						$http({
							url: '../reservation_test/mailNoshowSave',
							method: 'POST',
							params: {resvNum:msg[1]}
						}).then(function(response){
							window.location  = "../reservation_test/reservationList";
						})
					})
				}
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Please confirm before proceeding transactions").ok('Ok').parent(angular.element(document.body)));
			});
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please select atleast one room").ok('Ok').parent(angular.element(document.body)));
		}
	}
}]);

