pmsApp.service("hkService",function(){
	var rooms=[];
	this.setRooms = function(roomList){
		rooms=roomList; 
	}
	this.getRooms = function(){
		return rooms; 
	}
});
pmsApp.controller('hkController',['$scope','$http','$mdDialog','$compile','$q','hkService', function($scope,$http,$mdDialog,$compile,$q,hkService){
	var hk= this;
	$scope.editMode=false;

	$scope.hk_filter = {dirty:true,clean:true,cleaning:true};
	$('#imgloader').show();
	
	$scope.roomTypesList =[{id:0,name:'ALL'}]
	$scope.floorList =[{id:0,name:'ALL'}]
	$scope.floor ="0";
	$scope.rmType = "0";
	$scope.loadRoomType = function(){
		$http({
			url : '../roomType/getRoomTypes',
			method : "GET",
		}).then(function(response) {
			//$scope.room_type=response.data;	
			angular.forEach(response.data,function(value,index){
				var type= {id:value.id,name:value.name};
				$scope.room_type = response.data;
				//$scope.room_type.push(type);
				$scope.roomTypesList.push(type);
			/*	$scope.resv.hdr.room_type = $scope.room_type[0].id.toString();*/			
					
			});
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.loadRoomType();
	
	$scope.loadFloor = function(){
		$http({
			url : '../room/getFloor',
			method : "GET",
		}).then(function(response) {
			//$scope.room_type=response.data;	
			angular.forEach(response.data,function(value,index){
				var type= {id:value.id,name:value.name};
				$scope.floorList.push(type);
				/*$scope.resv.hdr.floorList = $scope.floorList[0].id.toString();*/
			});
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.loadFloor();
	
	$scope.loadRoomDetails = function(){
		$http({
			url : '../housekeeping/hkStatus',
			method : "POST",
			data :{roomType:$scope.rmType,floor:$scope.floor}
		}).then(function(response) {
			hkService.setRooms(response.data);
			$scope.roomDtlList = response.data;
			$scope.buildGridModel({
				background: ""
			});
			$scope.filterFunction();
			$("#roomsmyModal").modal({backdrop: "static"});
			$('#imgloader').hide();
			
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.loadRoomDetails();

	$scope.buildGridModel = function(tileTmpl){
		var it;
		var j=0;
		$scope.roomDtlList.forEach(function(roomData){
			roomData.actualDepartDate=new Date(roomData.actualDepartDate);
			roomData.arrivalDate=new Date(roomData.arrivalDate);
			it = angular.extend({},tileTmpl);
			it.span  = { row :1, col : 1 };
			if(roomData.occStatus==1){
				it.background = "rgb(255, 138, 128)";
			}
			//else if(statusClass=="res_stat-confirmed"){
				//it.background = "rgb(255, 102, 204)";//magentaa
			//}
			else if(roomData.occStatus==2){
				it.background = "rgb(61, 140, 186)";//blue
			}
			
			

			if(roomData.invStatus==1){
				it.background = "rgb(179, 157, 219)";
			}
			roomData.tile=it;
			j=j+1;
		});
	}

	$scope.saveHk=function(){
		$('#imgloader').show();
		var data = $.param({id:$scope.roomid,number:$scope.roomNum,status:$scope.status});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../housekeeping/update', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='success'){
				$("#newHKStatusmyModal").modal('toggle');
				$scope.loadRoomDetails();
				$('#imgloader').hide();
			}
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.filterFunction = function(){
		$('#imgloader').show();
		$scope.selectAll=true;
		if(!$scope.hk_filter.dirty || !$scope.hk_filter.clean || !$scope.hk_filter.cleaning){
			$scope.selectAll=false;
			if(!$scope.hk_filter.dirty && !$scope.hk_filter.clean && !$scope.hk_filter.cleaning){
				$scope.hk_filter = {dirty:true,clean:true,cleaning:true};
				$scope.selectAll=true;
			}
		}
		if($scope.selectAll){
			$scope.roomDtlList=hkService.getRooms();
		}else{
			$scope.rooms=hkService.getRooms();
			$scope.roomDtlList=[];
			$scope.rooms.forEach(function(rooms){
				if($scope.hk_filter.dirty){
					if(rooms.hkStatus==1){
					$scope.roomDtlList.push(rooms);
					}
				}
				if($scope.hk_filter.clean){
					if(rooms.hkStatus==3){
					$scope.roomDtlList.push(rooms);
					}
				}
				if($scope.hk_filter.cleaning){
					if(rooms.hkStatus==2){
					$scope.roomDtlList.push(rooms);
					}
				}
			})
		}
		$('#imgloader').hide();
	};
	
	$scope.editStatus= function(roomid,roomType,roomNum,status){
	$scope.roomid=roomid;
	$scope.roomNum = roomNum;
	$scope.roomType = roomType;
	$scope.status = status;
		$("#newHKStatusmyModal").modal({backdrop: "static"});
	}
}]);
