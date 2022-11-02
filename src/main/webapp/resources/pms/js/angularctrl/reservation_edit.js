var maxRoom= 0;
pmsApp.service("ServiceIntial",function(){

	
	var rooms;
	this.setRoom = function(numroom){
		rooms=numroom; 
	}
	this.getRoom = function(){
		return rooms; 
	}

	$(document).ready(function(){
		  $("button").click(function(){
		    $("#state_error,#country_error,#gender_error").fadeIn("slow");
		  });
		});
	

});



pmsApp.controller('reservationEditCtrl', ['$scope','$http','$window','$mdDialog','$rootScope','ServiceIntial','$timeout','$compile','$filter','$q','discountService',function ($scope, $http,$window,$mdDialog,$rootScope,ServiceIntial,$timeout,$compile,$filter,$q,discountService){
	var self = this;
    self.simulateQuery = false;
    self.isDisabled    = false;
	var hdate=$("#hotelDate").val();
	$scope.hotelDate = new Date(hdate);
	$scope.maxNights=parseInt($("#maxNights").val());
	var dateFormat = $("#dateFormat").val();
	$scope.currency=$("#currency").val();
	$scope.reservationNo=parseInt($("#reservationNo").val());
	$scope.resv={hdr:{},dtl:[],room:[]};
	$scope.assignedRoomRatesList = {};
	$scope.roomTypesAvailable={};
	$scope.roomRates=[];
	$scope.roomTypeId=0;
	$scope.oneAtATime = true;
	$scope.fBox=false;
	$scope.imageDiv=false;
	var assignedRateIdList=[];
	$scope.totalRoomsAssigned=0;
	$scope.data={selectedIndex:0,fourthLocked:true};
	$scope.resv_edit = true;
	$scope.add_room= false;
	$scope.showAssignedWidget=false;
	$scope.numRoomsPerType={};
	$scope.assignedRoomNumbers={};
	$scope.roomRatesNewRoomType={};
	$scope.disc={general:{},planBased:{},group:'general'};
	$scope.discApplied={};
	$scope.availDiscounts={};
	$scope.assignedArray={};
	$scope.discDispList=[];
	$scope.submit_click=false;
	$scope.trCrp={travelCorp:false,trvlGroup:"trvl",tvlcrpId:0};
	$scope.changeData = {hdrIsDirty:false,ratePlanIsDirty:false,roomTypeIsDirty:false,discountIsDirty:false,roomIsDirty:false,roomDataIsDirty:false};
	$scope.salutations = ["Mr.","Mrs.","Ms.","M/s.","Dr.","C/o."];
	$scope.stateList=[];
	$scope.resv.room.salutation = $scope.salutations[0]; 
	var old_rooms;
	$('#imgloader').show();
	$scope.countryArr=[];
	$http({
		url:'../reservation_test/getCountries',
		method:'POST'
	}).then(function(response){
		$scope.countryList = response.data;
		for(i=0;i<$scope.countryList.length;i++){
			$scope.countryArr.push($scope.countryList[i].name);
			self.states        = loadAll();
		    self.querySearch   = querySearch;
		    self.selectedItemChange = selectedItemChange;
		    self.searchTextChange   = searchTextChange;
		}
	})
	var rowIndex = 1;

	$http({
		url : '../reservation_test/getReservationRecord',
		method : "POST",
		params:{reservationNo:$scope.reservationNo}
	}).then(function(response) {
		$scope.resv.hdr = JSON.parse(response.data.resvHdr); 
		$scope.resv.hdr.maxDepartDate = new Date($scope.resv.hdr.maxDepartDate);
		$scope.resv.hdr.minArrDate = new Date($scope.resv.hdr.minArrDate);
		$scope.resv.hdr.cutOffDate = new Date($scope.resv.hdr.cutOffDate);
		$scope.resv.dtl = response.data.resvDtl;
		$scope.resv.room = response.data.resvRoom;
		$scope.checkinDiscountList = response.data.checkinDiscountList;
		$scope.resv.hdr.numNights = $scope.resv.dtl[0].numNights;
		ServiceIntial.setRoom($scope.resv.hdr.numRooms);
		rowIndex = 1;
		if($scope.resv.hdr.resvType==2){
			$scope.trCrp={travelCorp:true,trvlGroup:"corp",tvlcrpId:$scope.resv.hdr.corporateId.toString()};
			$scope.getTaCorpList();
		}else if($scope.resv.hdr.resvType==3){
			$scope.trCrp={travelCorp:true,trvlGroup:"trvl",tvlcrpId:$scope.resv.hdr.corporateId.toString()};
			$scope.getTaCorpList();
		}
		currentIndex = 0 ;
		$scope.resv.room.forEach(function(resvroom) {
			
			if(resvroom.salutation == ""){
				resvroom.salutation = $scope.salutations[0];
			}
			if(resvroom.gender == ""){
				resvroom.gender = "Male";
			}
			$scope.loadState(currentIndex, resvroom.nationality);
			$scope.resv.dtl.forEach(function(resvdtl) {
				if(resvroom.resvDtlNo==resvdtl.resvDtlNo){
					resvroom.rateId=resvdtl.rateId;
					resvroom.rateCode=resvdtl.rateCode;
					resvroom.roomTypeId=resvdtl.roomTypeId;
					resvroom.roomTypeCode=resvdtl.roomTypeCode;
					resvroom.occupancy=resvdtl.occupancy;
					resvroom.rowIndex = rowIndex;
					resvroom.isDeleted=false;
				}
				if(resvroom.hasOwnProperty("roomNumber")){
					$scope.assignedRoomNumbers[rowIndex]=resvroom.roomNumber;
				}
				rowIndex = rowIndex+1;
			});
			if($scope.numRoomsPerType[resvroom.roomTypeId]==null){
				$scope.numRoomsPerType[resvroom.roomTypeId]=1;
			}else{
				$scope.numRoomsPerType[resvroom.roomTypeId]=$scope.numRoomsPerType[resvroom.roomTypeId]+1;
			}
			if(resvroom.hasOwnProperty("passportExpiryOn")){
				resvroom.passportExpiryOn=new Date(resvroom.passportExpiryOn);
			}
			if(resvroom.hasOwnProperty("noshowDate")){
				delete resvroom.noshowDate;
				delete resvroom.noshowTime;
			}
			currentIndex++;
		});
		var generalDisc=false;
		$scope.resv.dtl.forEach(function(resvdtl) {
			if((assignedRateIdList.indexOf(resvdtl.rateId)==-1)){
				assignedRateIdList.push(resvdtl.rateId)
			}
			
			
			if(!generalDisc){
				$scope.checkin_discounts=[]
				if($scope.checkinDiscountList.length!=0){
					$scope.checkinDiscountList.forEach(function(checkDis) {
						var checkin_discounts={};
						var discDisp={code:"",value:""};
						var discVal;
						if(checkDis.discIsPc){
							discVal=checkDis.discPc;
						}else{
							discVal=checkDis.discAmount;
						}
						if(checkDis.discType==2){
							generalDisc=true;
							var opendisc=0;
							var isOpenDisc=false;
							discDisp.discountFor = checkDis.discountFor;
							discDisp.id = checkDis.discId;
							discDisp.code=checkDis.discCode;
							discDisp.value=discVal;
							if(checkDis.isOpen){
								opendisc=discVal;
							}
							$scope.discDispList.push(discDisp);
							$scope.discApplied={group:'general',value:{discId:checkDis.discId,isOpen:checkDis.isOpen,openValue:opendisc}};	
							checkin_discounts.discId=parseInt($scope.discApplied.value.discId);
							checkin_discounts.openDiscount=parseFloat($scope.discApplied.value.openValue);
						}else if(checkDis.discType==1){
							discDisp={};
							discDisp.discountFor = checkDis.discountFor;
							discDisp.id = checkDis.discId;
							discDisp.code=checkDis.discCode;
							discDisp.value=discVal;
							$scope.discDispList.push(discDisp); 
							$scope.disc.planBased[checkDis.discId]=resvdtl.rateId;
							$scope.discApplied={group:'plan',value:{discId:$scope.disc.planBased}};
							checkin_discounts.discId=discDisp.id;
						}
						$scope.checkin_discounts.push(checkin_discounts);
					});
				}
				
				
				
				/*if(resvdtl.discId!=0){
					var discDisp={code:"",value:""};
					var discVal;
					if(resvdtl.discIsPc){
						discVal=resvdtl.discPc;
					}else{
						discVal=resvdtl.discAmount;
					}
					if($scope.resv.hdr.discType==2){
						generalDisc=true;
						var opendisc=0;
						var isOpenDisc=false;
						discDisp.code=resvdtl.discCode;
						discDisp.value=discVal;
						if(resvdtl.discIsOpen){
							opendisc=discVal;
						}
						$scope.discDispList.push(discDisp);
						$scope.discApplied={group:'general',value:{discId:resvdtl.discId,isOpen:resvdtl.discIsOpen,openValue:opendisc}};	
					}else if($scope.resv.hdr.discType==1){
						discDisp={};
						discDisp.code=resvdtl.discCode;
						discDisp.value=discVal;
						$scope.discDispList.push(discDisp); 
						$scope.disc.planBased[resvdtl.discId]=resvdtl.rateId;
						$scope.discApplied={group:'plan',value:{discId:$scope.disc.planBased}};
					}
				}*/
			}
		});
		sortByKey($scope.resv.room,'rateId');
		sortByKey($scope.resv.room,'roomTypeId');
		$('#imgloader').hide();
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});

	
	$scope.loadState = function(index,nationality){
		//if(!$scope['is_old_customer_'+index]){
			//$scope.roomListData[index].state=""
		//}
		$scope.id="0";	
		for( var j=0;j<$scope.countryList.length;j++){
			if(nationality===$scope.countryList[j].name){
				$scope.id = $scope.countryList[j].id;
				break;
			}
		}
		var data = $.param({data:JSON.stringify($scope.id)});
		//var data = $.param({data:$scope.id});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../common/getState', data, config)
		.success(function (data, status, headers, config) {
			$scope.stateList[index]= data;
			//$scope.resv.room[index].state = $scope.stateList[index][0].name;
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	
	}
	
	
	$scope.getTaCorpList = function(){
		if($scope.trCrp.travelCorp){
			var data = 1;
			if($scope.trCrp.trvlGroup == "trvl"){
				data = 2;
			}
			
			$http({
				url : '../reservation_test/getTACorpList',
				method : "POST",
				//data : $scope.trCrp.trvlGroup
				data : data
			}).then(function(response) {
				$scope.trCrp.ta = {};
				$scope.trCrp.corp = {};
				//if( $scope.trCrp.trvlGroup=="trvl"){
				if( $scope.trCrp.trvlGroup=="trvl"){
					$scope.trCrp.ta = response.data;
					$scope.trCrp.tvlcrpId = $scope.trCrp.ta[0].id.toString();
				}else{
					$scope.trCrp.corp = response.data;
					$scope.trCrp.tvlcrpId = $scope.trCrp.corp[0].id.toString();
				}
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});
		}
	}

	$scope.changeRoomStatus=function(roomId){
	 	 
		 $http({
				url : '../reception/changeRoomStatus',
				method : "POST",
				params : {id:roomId}
			}).then(function(response) {
			
			});
	 }
	
	$scope.getroomDetails = function(){
		//$scope.gender="Male";
		for(var i=0; i<$scope.resv.room.length; i++){
			if($scope.resv.room[i].salutation == undefined){
				$scope.resv.room[i].salutation = $scope.salutations[0]; 
			}
		}
		
		oldRType=0;
		oldRPlan=0;
		$scope.data.selectedIndex = 1;
		$timeout(function () { 	 $("#sameasabove-0").hide();
		if($scope.resv.hdr.numRooms == 1){
			$("#applyall").hide();
			
		}
		
		}, 1);
		
		var nationality = $scope.countryList[0].name;
		for(var i=0; i<$scope.resv.room.length; i++){
			if($scope.resv.room[i].nationality == undefined){
				$scope.resv.room[i].nationality = nationality;
				$scope.loadState(i, nationality);
			}
		}
	}
	/*$scope.finalPage = function(){
		console.log($scope.resv.room);
	

	}*/
//	function validateEmail(emailField){
//		//if(emailField=="") return true;
//        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
//        if ($scope.roomDtl.email!="" && reg.test($scope.roomDtl.email) == false ) 
//        {
//     	$mdDialog.show($mdDialog.alert()
//				.clickOutsideToClose(true).title('Alert!!').textContent("Invalid Email Address").ok('Ok!').parent(angular.element(document.body)));	
//            return false;
//        }
//        return true;
//     }
//	
//	
	$scope.alertFinal=function(){
		
	}
	
	
	
	
$scope.saveRooms = function(){
		$scope.gender="Male";
		if($scope.extraRooms!=$scope.totalRoomsAssigned){
			var asRooms=$scope.resv.hdr.numRooms-$scope.totalRoomsAssigned;
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please assign rooms,"+asRooms+" or more rooms are pending assignment.").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
		}else{
			
			for (var key in $scope.assignedRoomRatesList){
				
				for(var i=1;i<=$scope.assignedRoomRatesList[key].value;i++){	
					if($scope.assignedRoomRatesList[key].occ=="occ1"){
						occ=1;
					}else if($scope.assignedRoomRatesList[key].occ=="occ2"){
						occ=2;
					}else if($scope.assignedRoomRatesList[key].occ=="occ3"){
						occ=3;
					}else if($scope.assignedRoomRatesList[key].occ=="occ4"){
						occ=4;
					}
					resvroom= {occupancy:occ,rateId:parseInt($scope.assignedRoomRatesList[key].rateId),rateCode:$scope.assignedRoomRatesList[key].rateCode,
							roomTypeId:parseInt($scope.assignedRoomRatesList[key].roomTypeId),roomTypeCode:$scope.assignedRoomRatesList[key].roomTypeCode,rowIndex : rowIndex,isDeleted:false,roomNumber:""};
					$scope.resv.room.push(resvroom);
					if($scope.numRoomsPerType[resvroom.roomTypeId]==null){
						$scope.numRoomsPerType[resvroom.roomTypeId]=1;
					}else{
						$scope.numRoomsPerType[resvroom.roomTypeId]=$scope.numRoomsPerType[resvroom.roomTypeId]+1;
						
					}
					rowIndex = rowIndex + 1;
					

				}
			}
			$scope.showAssignedWidget=false;
			$scope.add_room = false;
			$scope.resv_edit = true;
			$scope.resv.hdr.numRooms=old_rooms+$scope.extraRooms;
			sortByKey($scope.resv.room,'rateId');
			sortByKey($scope.resv.room,'roomTypeId');
			$timeout(function () { 	 $("#sameasabove-0").hide();
			if($scope.resv.hdr.numRooms>1){
				$("#applyall").show();
			}
			
			}, 1);


		}

	}
	
	$scope.sameasabove=function(index){		
		$scope.resv.room[index].salutation=$scope.resv.room[index-1].salutation;
		$scope.resv.room[index].firstName=$scope.resv.room[index-1].firstName;
		$scope.resv.room[index].lastName=$scope.resv.room[index-1].lastName;
		$scope.resv.room[index].email=$scope.resv.room[index-1].email;
		$scope.resv.room[index].phone=$scope.resv.room[index-1].phone;
		$scope.resv.room[index].address=$scope.resv.room[index-1].address;
		$scope.resv.room[index].nationality=$scope.resv.room[index-1].nationality;
		$scope.resv.room[index].remarks=$scope.resv.room[index-1].remarks;
		$scope.resv.room[index].gender=$scope.resv.room[index-1].gender;
		$scope.resv.room[index].passportNo=$scope.resv.room[index-1].passportNo;
		$scope.resv.room[index].passportExpiryOn=$scope.resv.room[index-1].passportExpiryOn;
		$scope.resv.room[index].gstno=$scope.resv.room[index-1].gstno;
		$scope.loadState(index, $scope.resv.room[index-1].nationality);
		$timeout(function(){
			$scope.resv.room[index].state="";
			$scope.resv.room[index].state=$scope.resv.room[index-1].state;
		}, 100);
	}
	
	$scope.applyall=function(index){
		var rooms = $scope.resv.hdr.numRooms;
		for(var i=0;i<rooms;i++){
			if($scope.resv.room[i].isDeleted){
				rooms++;
			}else{
				$scope.resv.room[i].salutation=$scope.resv.room[index].salutation;
				$scope.resv.room[i].firstName=$scope.resv.room[index].firstName;
				$scope.resv.room[i].lastName=$scope.resv.room[index].lastName;
				$scope.resv.room[i].email=$scope.resv.room[index].email;
				$scope.resv.room[i].phone=$scope.resv.room[index].phone;
				$scope.resv.room[i].address=$scope.resv.room[index].address;
				$scope.resv.room[i].nationality=$scope.resv.room[index].nationality;
				$scope.resv.room[i].remarks=$scope.resv.room[index].remarks;
				$scope.resv.room[i].gender=$scope.resv.room[index].gender;
				$scope.resv.room[i].passportNo=$scope.resv.room[index].passportNo;
				$scope.resv.room[i].passportExpiryOn=$scope.resv.room[index].passportExpiryOn;
				$scope.resv.room[i].gstno=$scope.resv.room[index].gstno;
				for(var j=0; j<$scope.stateList.length; j++){
					$scope.stateList[j] = $scope.stateList[index];
				}
				$scope.resv.room[i].state = $scope.resv.room[index].state;
				
				//angular.element('#state').blur();
				/*if($scope.resv.room[index].state != ""){
					for(var j=0; j<$scope.stateList.length; j++){
						if($scope.resv.room[index].state == $scope.stateList[index][j].name){
							$scope.resv.room[i].state = $scope.stateList[index][j].name;
						}
						$scope.resv.room[i].state
					}
				}*/
			}
		}
	}


	$scope.Statusroom=function(available){
		console.log(available);
		var status=false;
		if(available.room_status=="occupied"){
			status=true;
			
		}
		if(available.hk1_status=="DIRTY"||available.hk1_status=="CLEANING"){
			status=true;
		}
		
		return status;
		
	}
	

	$scope.addRoom = function(ev) {
		var parentEl = angular.element(document.body);
		$mdDialog.show({
			parent: parentEl,
			targetEvent: ev,
			template:
				'<md-dialog aria-label="List dialog" style="margin-left: 40%;margin-top: 25%;">' +
				'  <md-dialog-content> <md-content layout-padding>'+
				'    <div><md-input-container class="searchFormInput">'+
				'  <label>Extra Rooms</label>'+
				'<input type="number" step="1" min="1" ng-model="extraRooms"/> </md-input-container></div>'+
				'  </md-content> </md-dialog-content>' +
				'  <md-dialog-actions>' +
				'    <md-button  ng-click="setupNewRooms()" class="md-primary md-raised">' +
				'      Add' +
				'    </md-button>' +
				'    <md-button ng-click="closeDialog()" class="md-primary">' +
				'      Close' +
				'    </md-button>' +
				'  </md-dialog-actions>' +
				'</md-dialog>',
				locals: {
					items: $scope.items
				},
				controller: DialogController
		});
		function DialogController($scope, $mdDialog, items,$rootScope) {
			$scope.extraRooms = 1;
			$scope.items = items;
			$scope.closeDialog = function() {
				$mdDialog.hide();
			}
			$scope.setupNewRooms = function(){
				$mdDialog.hide();
				if($scope.extraRooms>0){
				  $rootScope.$broadcast("checkAvailabilityFn",$scope.extraRooms);
				}
			}
		}
	};
	$rootScope.$on("checkAvailabilityFn", function(event,numRooms){
		$scope.extraRooms= numRooms;
		$scope.checkAvailability();
	});
	$scope.checkAvailability=function(){
		old_rooms = $scope.resv.hdr.numRooms;
		$scope.add_room = true;
		$scope.resv_edit = false;
		maxRoom=$scope.extraRooms;
		$scope.checkAvailObj={minArrDate : $filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat),nights:$scope.resv.hdr.numNights,numRooms:$scope.extraRooms};
		$scope.resv.hdr.numRooms = $scope.extraRooms;
		$('#imgloader').show();
		var data = $.param({resvHdr:JSON.stringify($scope.checkAvailObj)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomAvailability', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			$scope.showAssignedWidget=true;
			$scope.roomTypesAvailable=data;
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error!!').textContent("Operation failed.").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
		});

	}
	function querySearch (query) {
		return query ? self.states.filter( createFilterFor(query) ) : self.states;
}

function searchTextChange(text) {
	$scope.TextSubject=text;
	$scope.roomDtl.nationality=$scope.TextSubject;
}

 function selectedItemChange(item) {
 	$scope.nation=item.value;
 	$scope.roomDtl.nationality=$scope.nation;
 }

 
/**
* Build `states` list of key/value pairs
*/
function loadAll() {
var allStates = $scope.countryArr;
return allStates.map( function (state) {
 return {
   value: state.toLowerCase(),
   display: state
 };
});
}

/**
* Create filter function for a query string
*/
function createFilterFor(query) {
var lowercaseQuery = angular.lowercase(query);

return function filterFn(state) {
 return (state.value.indexOf(lowercaseQuery) === 0);
};

}
	$scope.showDetails = function(roomTypeCode,typeId,availRooms,action){
		$scope.roomAssignAction=action;
		$scope.roomTypeId=typeId;
		$scope.roomRates=[];
		newAssgned=[];
		$scope.roomMax = $scope.extraRooms-$scope.totalRoomsAssigned;
		$scope.availableRooms=availRooms;
		$('#imgloader').show();
		var data = $.param({roomTypeDtls:JSON.stringify({roomType:roomTypeCode,arrDate:$filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat),nights:$scope.resv.hdr.numNights,rate_id:0})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomRatePlans', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
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
			if($scope.roomAssignAction==2){
				$scope.assignedRoomRatesList={};
				$scope.totalRoomsAssigned=0;
				$scope.roomMax = maxRoom;
			}
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});		
	}


	$scope.assignRooms = function(){
		var totalAssignedNew=0;
		$(".assgnRoom").each(function(){
			$scope.assignedRoom={id:"",value:0,roomTypeId:"",roomTypeCode:"",rateCode:"",rateId:"",occ:""}
			if($(this).attr('id') in $scope.assignedRoomRatesList){
				if(this.value=="" || this.value==0){
					delete $scope.assignedRoomRatesList[$(this).attr('id')];
				}
			}
			if(this.value!="" && this.value!=0){
				$scope.assignedRoom={id:$(this).attr('id'),value:parseInt(this.value),rateCode:$(this).attr('data-rateCode'),roomTypeId:$(this).attr('data-roomtypeid'),roomTypeCode:$(this).attr('data-roomTypeCode'),rateId:$(this).attr('data-rateid'),occ:$(this).attr('data-occ'),newRoom:true};
				$scope.assignedRoomRatesList[$scope.assignedRoom.id]=$scope.assignedRoom;
				totalAssignedNew=totalAssignedNew+parseInt(this.value);
			}
		});	
		$scope.changeData.roomIsDirty = true;
		$scope.AssignedRoomData = $scope.buildData();
		if($scope.roomAssignAction==2){
			if(!angular.equals({},$scope.assignedRoomRatesList)){
				if(totalAssignedNew!=maxRoom){
					//var pending= (maxRoom-totalAssignedNew)+" rooms not assigned.";
					var pending = (maxRoom-totalAssignedNew) + "please assign a room before proceeding"
					$mdDialog.show($mdDialog.alert().clickOutsideToClose(true).title('Assign rooms').textContent(pending).ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
				}else{
				$scope.assignRoomType();
				$scope.totalRoomsAssigned=0;
				$("#assignRoomsmyModal").modal("toggle");}
			}else{
				$mdDialog.show($mdDialog.alert().clickOutsideToClose(true).title('Alert').textContent("Assign rooms").ok('Ok').parent(angular.element(document.body)));				
			}		
		}else{
		$("#assignRoomsmyModal").modal("toggle");
		}
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
			if((assignedRateIdList.indexOf(arr1[key].rateId)==-1)){
				assignedRateIdList.push(arr1[key].rateId)
			}
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
		$scope.assignedArray=arr2;
		for (var key in arr2){
			returnArr.push(arr2[key]);
		}
		return returnArr;
	};

	$scope.deleteRoom = function(rindex){
		$scope.resv.room.forEach(function(resvroom,index) {
			if(resvroom.rowIndex ==rindex){
				if(resvroom.hasOwnProperty("resvRoomNo")){
					resvroom.isDeleted=true;
					$scope.changeData.roomIsDirty = true;
				}else{
					$scope.resv.room.splice(index,1);
				}
				old_rooms=old_rooms-1;
				$scope.resv.hdr.numRooms=$scope.resv.hdr.numRooms-1;
				oldRPlan=0;
				oldRType=0;
			}
		});
	}

	function sortByKey(array, key) {
		return array.sort(function(a, b) {
			var x = a[key]; var y = b[key];
			return ((x < y) ? -1 : ((x > y) ? 1 : 0));

		});
	}
	$scope.roomDetails={roomNumber:0};
	$scope.selectedRoomIndex=-1;
	$scope.getVacantRooms = function(roomtype,index){		
		var data = $.param({minArrDate:$filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat),maxDepartDate:$filter('date')(new Date($scope.resv.hdr.maxDepartDate), dateFormat),roomTypeId:parseInt(roomtype),occupancy:1});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reception/getAvailableRooms', data, config)
		.success(function (data, status, headers, config) {
			$scope.roomDetails.roomNumber=0;
			$scope.availableRooms = data;
			$scope.imageDiv=true;
			$scope.selectedRoomIndex=index;
			$("#roomAssignmyModal").modal({backdrop: "static"});
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}
	$scope.assignRoomNumber = function(){
		$scope.resv.room.forEach(function(resvroom) {
			if(resvroom.rowIndex ==$scope.selectedRoomIndex){
				resvroom.roomNumber=parseInt($scope.roomDetails.roomNumber);	
			}
		});
		$scope.assignedRoomNumbers[$scope.selectedRoomIndex]=parseInt($scope.roomDetails.roomNumber);
		$scope.changeData.roomIsDirty = true;
		$scope.changeData.roomDataIsDirty = true;
		$("#roomAssignmyModal").modal("toggle");
	}
	$scope.checkAssigned = function(number){
		var stat=true;
		for(var key in $scope.assignedRoomNumbers){
			if($scope.assignedRoomNumbers[key]==number) {
				stat=false;
				break;
			}
		}
		return stat;
	}


	$scope.roomRates={};
	$scope.showRoomRates = function(rateId,occ,roomTypeCode){
		$('#imgloader').show();
		$scope.roomRates={};
		var data = $.param({roomTypeDtls:JSON.stringify({roomType:roomTypeCode,arrDate:$filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat),nights:$scope.resv.hdr.numNights,rate_id:0})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomRatePlans', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			data.ratePlans.forEach(function(entry) {
				$scope.roomRates[entry.rateId]=entry;
			});
			$scope.roomTypeImages= data.images;
			$scope.roomDetails.ratePlanId=rateId;
			$scope.roomDetails.occupancy=occ;
			$scope.selectedRateId = rateId;
			$("#ratePlanmyModal").modal({backdrop: "static"});
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});		
	}

	$scope.assignRoomRate = function(){
		$scope.resv.room.forEach(function(resvroom) {
			if(resvroom.rateId==$scope.selectedRateId){
				resvroom.rateId = parseInt($scope.roomDetails.ratePlanId);
				resvroom.rateCode=$scope.roomRates[parseInt($scope.roomDetails.ratePlanId)].rateCode;
				resvroom.occupancy=$scope.roomDetails.occupancy;
				$scope.changeData.roomIsDirty = true;
				$scope.changeData.ratePlanIsDirty = true;
				if($scope.discApplied.group==='plan'){
					$scope.discApplied={};
					$scope.discDispList=[];
				}
			}
		});
		$("#ratePlanmyModal").modal("toggle");
	}

	$scope.roomTypesAvailable=[];
	$scope.loadAvailableRoomTypes = function(roomTypeId){
		$scope.selectedRoomTypeId =roomTypeId;
		maxRoom=$scope.numRoomsPerType[roomTypeId];
		//var availRoomParams ={minArrDate: $filter('date')(new Date($scope.hotelDate), dateFormat),nights:$scope.resv.hdr.numNights,numRooms:$scope.numRoomsPerType[roomTypeId]};
		var availRoomParams ={minArrDate: $filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat),nights:$scope.resv.hdr.numNights,numRooms:$scope.numRoomsPerType[roomTypeId]};

		$('#imgloader').show();
		var data = $.param({resvHdr:JSON.stringify(availRoomParams)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomAvailability', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			$scope.roomTypesAvailable=data;
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	
	
	
	
	$scope.assignRoomType = function(){

		/*for(var i = $scope.resv.room.length -1; i >= 0 ; i--){
			if( $scope.resv.room[i].roomTypeId==$scope.selectedRoomTypeId){
				$scope.resv.room.splice(i,1);
			}
		}*/
		for (var key in $scope.assignedRoomRatesList){
			if($scope.assignedRoomRatesList[key].newRoom){
				for(var i=1;i<=$scope.assignedRoomRatesList[key].value;i++){	
					if($scope.assignedRoomRatesList[key].occ=="occ1"){
						occ=1;
					}else if($scope.assignedRoomRatesList[key].occ=="occ2"){
						occ=2;
					}else if($scope.assignedRoomRatesList[key].occ=="occ3"){
						occ=3;
					}else if($scope.assignedRoomRatesList[key].occ=="occ4"){
						occ=4;
					}
					/*resvroom= {occupancy:occ,rateId:parseInt($scope.assignedRoomRatesList[key].rateId),rateCode:$scope.assignedRoomRatesList[key].rateCode,
							roomTypeId:parseInt($scope.assignedRoomRatesList[key].roomTypeId),roomTypeCode:$scope.assignedRoomRatesList[key].roomTypeCode,rowIndex : rowIndex,isDeleted:false};
					$scope.resv.room.push(resvroom);*/
					for(var j = $scope.resv.room.length -1; j >= 0 ; j--){
						if( $scope.resv.room[j].roomTypeId==$scope.selectedRoomTypeId){
							$scope.resv.room[j].occupancy = occ;
							$scope.resv.room[j].rateId = parseInt($scope.assignedRoomRatesList[key].rateId);
							$scope.resv.room[j].rateCode = $scope.assignedRoomRatesList[key].rateCode;
							$scope.resv.room[j].roomTypeId = parseInt($scope.assignedRoomRatesList[key].roomTypeId);
							$scope.resv.room[j].roomTypeCode = $scope.assignedRoomRatesList[key].roomTypeCode;
							$scope.resv.room[j].rowIndex = rowIndex;
							$scope.resv.room[j].isDeleted = false;
							$scope.resv.room[j].roomNumber = "";
							
							$scope.assignedRoomRatesList[key].newRoom=false;
							if($scope.numRoomsPerType[$scope.resv.room[j].roomTypeId]==null){
								$scope.numRoomsPerType[$scope.resv.room[j].roomTypeId]=1;
							}else{
								$scope.numRoomsPerType[$scope.resv.room[j].roomTypeId]=$scope.numRoomsPerType[$scope.resv.room[j].roomTypeId]+1;
							}
							rowIndex = rowIndex + 1;
						}
					}
				}
			}
		}
		sortByKey($scope.resv.room,'rateId');
		sortByKey($scope.resv.room,'roomTypeId');
		if($scope.discApplied.group==='plan'){
			$scope.discApplied={};
			$scope.discDispList=[];
		}
		$scope.changeData.roomTypeIsDirty = true;
		$scope.changeData.ratePlanIsDirty = true;
	}
	$scope.finalPage = function(){
		$('#imgloader').show();
		var drt =$("#gForm").val();
		if (!$scope.changeData.roomDataIsDirty){
			$scope.changeData.roomDataIsDirty = (drt.toLowerCase() == 'true');}

		if($scope.changeData.roomDataIsDirty ){
			$scope.changeData.roomIsDirty =true;}

		$scope.loadDiscounts();
		$scope.getRoomRateDetails();
	}

	$scope.getRoomRateDetails = function(){
		$('#imgloader').show();
		$scope.roomRateChekArgs=[];
		//var checkin_discounts={};
		$scope.resv.room.forEach(function(resvroom) {
			if(!resvroom.isDeleted){
				var roomRateArgs={arrDate:$filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat),numRooms:1,numNights:$scope.resv.hdr.numNights,rateId:resvroom.rateId,occupancy:resvroom.occupancy};

				if(!angular.equals({},$scope.discApplied)){
					if($scope.discApplied.group=='general'){
						roomRateArgs.discId=parseInt($scope.discApplied.value.discId);
						//checkin_discounts.discId=parseInt($scope.discApplied.value.discId);
						if($scope.discApplied.value.isOpen){
							//checkin_discounts.openDiscount=parseFloat($scope.discApplied.value.openValue);
							roomRateArgs.openDiscount=parseFloat($scope.discApplied.value.openValue);
						}
						/*else{
							checkin_discounts.openDiscount="";
						}*/
					}else{
						for (var d in $scope.discApplied.value.discId){
							if(roomRateArgs.rateId==$scope.discApplied.value.discId[d]){
								roomRateArgs.discId=d;
								//checkin_discounts.discId=d;
							}
						}
					}
					/*var index = $scope.checkin_discounts.findIndex(a => a.discId === checkin_discounts.discId);
					if(index == -1){
						$scope.checkin_discounts.push(checkin_discounts);
					}*/
					
				}		
				if($scope.roomRateChekArgs.length!=0){
					var found = $scope.roomRateChekArgs.some(function (el) {
						return (el.rateId===roomRateArgs.rateId) && (el.occupancy===roomRateArgs.occupancy);
					});
					if (!found){
						$scope.roomRateChekArgs.push(roomRateArgs);
					}else{
						$scope.roomRateChekArgs.forEach(function(roomRate) {
							if(roomRate.rateId===roomRateArgs.rateId && roomRate.occupancy===roomRateArgs.occupancy){
								roomRate.numRooms=roomRate.numRooms+1;
							}
						});
					}
				}else{
					$scope.roomRateChekArgs.push(roomRateArgs);
				}
				if(resvroom.passportExpiryOn!=null){
					//resvroom.passportExpiryOn = new Date(resvroom.passportExpiryOn);
					resvroom.passportExpiryOn = $filter('date')(new Date(resvroom.passportExpiryOn),dateFormat);
				}
			}
		});

		$http({
			url : '../reservation_test/getRoomRateDetails',
			method : "POST",
			data : $scope.roomRateChekArgs
		}).then(function(response) {
			$scope.detailedRateSummary=response.data;
			var rateDtlArr=[];
			var arrayRate=[];
			var subtotal=0.0,taxTotal=0.0,discTotal=0.0,sTaxTotal=0.0,sChargeTotal=0.0,grantTotal=0.0,subtotalCharges=0.0,taxPc=0.0,discTax=0.0,discTaxTotal=0.0;
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
			$scope.data.fourthLocked=false;
			$scope.data.selectedIndex=2;
			$('#imgloader').hide();
		}, function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	$scope.loadDiscounts=function(){
		discountService.getDiscounts(assignedRateIdList).then(function(response) {
			$scope.availDiscounts = response.data;	
			/*$scope.availDiscountList = response.data;	
			$scope.generalDiscounts=[];
			$scope.planDiscounts=[];
			for(i=0;i<$scope.availDiscountList.general.length;i++){
				$scope.genValidityFrom = new Date($scope.availDiscountList.general[i].validFrom);
				$scope.genValidityTo = new Date($scope.availDiscountList.general[i].validTo);
				var checkGenValidTo = $scope.genValidityTo>=$scope.resv.hdr.minArrDate;
				var checkGenValidFrom = $scope.genValidityFrom<=$scope.resv.hdr.maxDepartDate;
				if(checkGenValidTo && checkGenValidFrom){
					if(checkGenValidTo==true && checkGenValidFrom ==true){
						$scope.generalDiscounts.push($scope.availDiscountList.general[i]);
					}
				}
			}

			for(i=0;i<$scope.availDiscountList.plan.length;i++){
				$scope.planValidityFrom = new Date($scope.availDiscountList.plan[i].validFrom);
				$scope.planValidityTo = new Date($scope.availDiscountList.plan[i].validTo);
				var checkPlanValidTo = $scope.planValidityTo>=$scope.resv.hdr.minArrDate;
				var checkPlanValidFrom = $scope.planValidityFrom<=$scope.resv.hdr.maxDepartDate;
				if(checkPlanValidTo && checkPlanValidFrom){
					if(checkPlanValidTo==true && checkPlanValidFrom==true){
						$scope.planDiscounts.push($scope.availDiscountList.plan[i]);
					}
				}
			}*/
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.showDiscounts =function(){
		if(!angular.equals({},$scope.discApplied)){
			$scope.disc.group=$scope.discApplied.group;
			if($scope.disc.group=="general"){
				$scope.disc.generalDisc=$scope.discApplied.value.discId;
				if($scope.discApplied.value.isOpen){
					$("#open"+$scope.disc.generalDisc).value=$scope.discApplied.value.openValue;
				}
			}else{
				$scope.disc.planBased=$scope.discApplied.value.discId;
			}				
		}
		$("#availDiscountsmyModal").modal({backdrop: "static"});	
	}
	
	$scope.deleteDiscountAplied = function(id){
		$scope.discDispList.splice($scope.discDispList.findIndex(a => a.id === id) , 1);
		$scope.checkin_discounts.splice($scope.checkin_discounts.findIndex(a => a.discId === id) , 1);
		//call roomrateDetails
		if($scope.discDispList.length ==0 || $scope.discDispList[$scope.discDispList.length-1].discountFor == 2){
			
			$scope.roomrateNodataDetails();
			
		}
	}

	
	$scope.roomrateNodataDetails = function(){
		
		$('#imgloader').show();
		$scope.roomRateChekArgs=[];
		//var checkin_discounts={};
		$scope.resv.room.forEach(function(resvroom) {
			if(!resvroom.isDeleted){
				var roomRateArgs={arrDate:$filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat),numRooms:1,numNights:$scope.resv.hdr.numNights,rateId:resvroom.rateId,occupancy:resvroom.occupancy};	
				if($scope.roomRateChekArgs.length!=0){
					var found = $scope.roomRateChekArgs.some(function (el) {
						return (el.rateId===roomRateArgs.rateId) && (el.occupancy===roomRateArgs.occupancy);
					});
					if (!found){
						$scope.roomRateChekArgs.push(roomRateArgs);
					}else{
						$scope.roomRateChekArgs.forEach(function(roomRate) {
							if(roomRate.rateId===roomRateArgs.rateId && roomRate.occupancy===roomRateArgs.occupancy){
								roomRate.numRooms=roomRate.numRooms+1;
							}
						});
					}
				}else{
					$scope.roomRateChekArgs.push(roomRateArgs);
				}
				if(resvroom.passportExpiryOn!=null){
					//resvroom.passportExpiryOn = new Date(resvroom.passportExpiryOn);
					resvroom.passportExpiryOn = $filter('date')(new Date(resvroom.passportExpiryOn),dateFormat);
				}
			}
		});

		$http({
			url : '../reservation_test/getRoomRateDetails',
			method : "POST",
			data : $scope.roomRateChekArgs
		}).then(function(response) {
			$scope.detailedRateSummary=response.data;
			var rateDtlArr=[];
			var arrayRate=[];
			var subtotal=0.0,taxTotal=0.0,discTotal=0.0,sTaxTotal=0.0,sChargeTotal=0.0,grantTotal=0.0,subtotalCharges=0.0,taxPc=0.0,discTax=0.0,discTaxTotal=0.0;
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
			$scope.data.fourthLocked=false;
			$scope.data.selectedIndex=2;
			$('#imgloader').hide();
		}, function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
		
	}
	
	
//	$scope.discDispList=[];
	$scope.applyDiscount =function(){
		//$('#imgloader').show();
		if(('generalDisc' in $scope.disc) || (!angular.equals({},$scope.disc.planBased))){
			var checkin_discounts={};
			var discDisp={code:"",value:""};
			if($scope.disc.group=='general'){
				var opendisc=0;
				var isOpenDisc=false;
				discDisp.id=$scope.disc.generalDisc;
				discDisp.code=$(".rad_"+$scope.disc.generalDisc).attr("data-discCode");
				discDisp.value=$(".rad_"+$scope.disc.generalDisc).attr("data-discValue");
				discDisp.discountFor = parseInt($(".rad_"+$scope.disc.generalDisc).attr("data-discountFor"));
				if($(".rad_"+$scope.disc.generalDisc).attr("data-isOpen")=="true"){
					opendisc=$("#open"+$scope.disc.generalDisc).val();
					isOpenDisc=true;
					discDisp.value=opendisc;
				}
				
				var index = $scope.discDispList.findIndex(a => a.id === discDisp.id);
				var indexRmFd = $scope.discDispList.findIndex(a => a.discountFor === discDisp.discountFor);
				if(index == -1 && indexRmFd == -1){
					$scope.discDispList.push(discDisp);
					$scope.discApplied={group:'general',value:{discId:$scope.disc.generalDisc,isOpen:isOpenDisc,openValue:opendisc}};	
					checkin_discounts.discId=parseInt(discDisp.id);
					if(isOpenDisc){
						checkin_discounts.openDiscount=parseFloat($scope.discApplied.value.openValue);
					}
					else{
						checkin_discounts.openDiscount="";
					}
					$scope.checkin_discounts.push(checkin_discounts);
					
				}
				//$scope.discApplied={group:'general',value:{discId:$scope.disc.generalDisc,isOpen:isOpenDisc,openValue:opendisc}};	
			}else{
				for (var p in $scope.disc.planBased) {
					discDisp={};
					if( $scope.disc.planBased.hasOwnProperty(p)){
						if($scope.disc.planBased[p]!="" && $scope.disc.planBased[p]!=null){
							discDisp.id=p;
							discDisp.code=$(".chk_"+p).attr("data-discCode");
							discDisp.value=$(".chk_"+p).attr("data-discValue");
							discDisp.discountFor =parseInt($(".chk_"+p).attr("data-discountFor"));
							var index = $scope.discDispList.findIndex(a => a.id === discDisp.id);
							var indexRmFd = $scope.discDispList.findIndex(a => a.discountFor === discDisp.discountFor);
							var Roomindex = $scope.discDispList.findIndex(a => a.discountFor === 1);
							if(index == -1 && indexRmFd == -1){
								$scope.discDispList.push(discDisp);
								$scope.discApplied={group:'plan',value:{discId:$scope.disc.planBased}};
								checkin_discounts.discId=p;
								$scope.checkin_discounts.push(checkin_discounts);
							}
						}else{
							delete $scope.disc.planBased[p];
						}
					} 
				}    
				//$scope.discApplied={group:'plan',value:{discId:$scope.disc.planBased}};
			}
			//$scope.getRoomRateDetails();
			if($scope.discDispList[$scope.discDispList.length-1].discountFor == 1){
				$scope.getRoomRateDetails();
			}
			$("#availDiscountsmyModal").modal("toggle");	
			$scope.changeData.discountIsDirty = true;
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("No discount selected.").ok('Ok').parent(angular.element(document.body)));	
		}
	}

	$scope.saveReservation = function(){
		var roomdis = $scope.discDispList.filter(a => a.discountFor === 1);
		var fooddis = $scope.discDispList.filter(a => a.discountFor === 2);
		if($scope.discDispList.length>2 || roomdis.length >1|| fooddis.legth>1){
			//alert("only possible to select 2 different type discount");
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('').textContent("Only Possible to Select 2 Different Type Discount.").ok('Ok').parent(angular.element(document.body)));
			return ;
		}
		
		$scope.submit_click=true;
		$scope.resv.hdr.resvType=1;
		if($scope.trCrp.travelCorp){
			if( $scope.trCrp.trvlGroup=="trvl"){
				$scope.resv.hdr.resvType=3;		
			}else if( $scope.trCrp.trvlGroup=="corp"){
				$scope.resv.hdr.resvType=2;
			}else{
				$scope.resv.hdr.resvType=1;
			}
			$scope.resv.hdr.corporateId=parseInt($scope.trCrp.tvlcrpId);
		}
		if(!angular.equals({},$scope.discApplied)){
			if($scope.discApplied.group=='general'){
				$scope.resv.hdr.discType=2;
			}else{
				$scope.resv.hdr.discType=1;
			}
		}
		delete $scope.resv.hdr.confDate;
		delete $scope.resv.hdr.confTime;
		delete $scope.resv.hdr.cancelDate;
		delete $scope.resv.hdr.cancelTime;
		delete $scope.resv.hdr.pickupDate;
		delete $scope.resv.hdr.pickupTime;
		$scope.resv.hdr.minArrDate= $filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat);
		$scope.resv.hdr.maxDepartDate= $filter('date')(new Date($scope.resv.hdr.maxDepartDate), dateFormat);
		$scope.resv.hdr.cutOffDate= $filter('date')(new Date($scope.resv.hdr.cutOffDate), dateFormat);
		delete $scope.resv.hdr.resvDate;
		$scope.saveData={resv:$scope.resv,resvDtls:$scope.roomRateChekArgs,changes:$scope.changeData,checkinDiscnt:$scope.checkin_discounts};
		$('#imgloader').show();
		$http({
			url : '../reservation_test/saveReservationEdit',
			method : "POST",
			data:JSON.stringify($scope.saveData)
		}).then(function(response) {
			if(response.data.substring(7)=='success'){
				$('#imgloader').hide();
				var confirm = $mdDialog.alert()
				.title("Completed successfully").ok('OK').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function(){
					window.location  = "../reservation_test/reservationList";
				});
			}
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	
	$scope.showApplyAll = function(index){
		console.log("index :" +index);
		var deletedRooms = 0;
		if($scope.resv.room[index-1].isDeleted ){
			for(var i=0;i<index;i++){
				if($scope.resv.room[i].isDeleted){
					deletedRooms = deletedRooms +1;
				}
			}
		}
		if(deletedRooms==1 ){
			return true;
		}else{
		return false;
		}
		
	}
	
	
	$scope.changeArrival  = function(){
		window.location  = "../reservation_test/changeArrivalDate?reservationNo="+$scope.reservationNo;
	}
	$scope.cancelRoomAssignPopUp = function(){
		$("#assignRoomsmyModal").modal("toggle");
	}
	$scope.cancelRoomNumberPopUp = function(){
		$("#roomAssignmyModal").modal("toggle");
	}
	$scope.cancelRatePlanPopUp = function(){
		$("#ratePlanmyModal").modal("toggle");
	}
	$scope.cancelDiscPopUp = function(){
		$("#availDiscountsmyModal").modal("toggle");
	}	
	
}]);



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
				if (parseInt(this.value)>limit || totalAssigned>limit || (parseInt(this.value)+assignedPerRoomType)>limit  || totalAssigned>scope.roomMax || (scope.totalRoomsAssigned+parseInt(this.value))>scope.resv.hdr.numRooms){
					this.value=0;
					newAssgned[this.id]="0";
					e.preventDefault();
				}
			});
		}
	}
}]);

