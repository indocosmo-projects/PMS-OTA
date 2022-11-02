

var maxRoom= 0;
pmsApp.service("ServiceReservation",function(){
	var arvldate;
	this.setMinArrivalDate = function(arr){
		arvldate= arr;
	}
	this.getMinArrivalDate = function(){
		return arvldate;
	}	
	$(document).ready(function(){
		$("#last_name").on('input', function(event) {
			this.value = this.value.replace(/[^a-z\s]/gi, '');
		});
	});
});
pmsApp.controller('reservationCtrl', ['$scope','$http','$window','$mdDialog','$rootScope','$filter','discountService','ServiceReservation',function ($scope, $http,$window,$mdDialog,$rootScope,$filter,discountService,ServiceReservation){

	var self = this;
	$scope.customerArr=[];
	$scope.countryList = [];
	var hdate=$("#hotelDate").val();
	var dateFormat = $("#dateFormat").val();
	$scope.dateFormat = dateFormat;
	$scope.maxNights=parseInt($("#maxNights").val());
	$scope.confirmbefore=parseInt($("#confirmbefore").val());
	$scope.currency=$("#currency").val();	
	$scope.names = ["Mr.","Mrs.","Ms.","M/s.","Dr.","C/o."];
	 $scope.formatDate = function(date){
         var dateOut = new Date(date);
         return dateOut;
   };
	$scope.initial={chInminDate:formatDate(new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate()+$scope.confirmbefore)),chOutminDate:formatDate(new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate()+$scope.confirmbefore+1)),cutoffminDate:new Date(hdate)};
	$scope.minArr=formatDate(new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate()));
	$scope.initial.cutoffmaxDate=$scope.initial.cutoffminDate;
	//$scope.pickupMinDate=new Date(new Date($scope.resv.hdr.minArrDate).getFullYear(),new Date($scope.resv.hdr.minArrDate).getMonth(),new Date($scope.resv.hdr.minArrDate).getDate());
	$scope.resv={hdr:{minArrDate:formatDate($scope.initial.chInminDate),maxDepartDate:formatDate($scope.initial.chOutminDate),cutOffDate:$scope.initial.cutoffminDate,nights:1,numRooms:1,numAdults:1,numChildren:0,numInfants:0,selectedSalutation:"",resvByFirstName:"",resvByLastName:"",resvByMail:"",resvByPhone:"",resvByAddress:"",resvFor:"",specialRequests:"",remarks:"",resvType:0,payment_source:0,minArrTime:"",maxDepTime:"",gender:"Male",mealPlan:1,dob:$filter('date')(Date.now(), 'yyyy-MM-dd')}};
	//$scope.resv.hdr.minArrDate=$filter("date")(Date.now(), 'yyyy-MM-dd');
	//$scope.resv.hdr.nights = Math.ceil(Math.abs(formatDate($scope.resv.hdr.maxDepartDate) - formatDate($scope.resv.hdr.minArrDate)) / (1000 * 3600 * 24));
	
	$scope.resv.hdr.nights = 1;
	$scope.initial.chOutmaxDate=new Date(new Date($scope.initial.chInminDate).getFullYear(),new Date($scope.initial.chInminDate).getMonth(),new Date($scope.initial.chInminDate).getDate()+$scope.maxNights);
	$scope.pickUp={pickupDate:$scope.resv.hdr.minArrDate,pickupTime:new Date(),location:"",seats:1,pickupProvider:0,pickupRemarks:""}
	$scope.oneAtATime = true;
	$scope.pickupReq=false;
	$scope.roomTypesAvailable={};
	$scope.roomRates=[];
	$scope.roomTypeId=0;
	$scope.showAssignedWidget=false;
	$scope.fBox=false;
	$scope.rateSummary={};
	$scope.detailedRateSummary={};
	$scope.disc={general:{},planBased:{},group:'general'};
	$scope.discApplied={};
	$scope.assignedRoomRatesList = {};
	$scope.availDiscounts={};
	var assignedRateIdList=[];
	$scope.totalRoomsAssigned=0;
	$scope.assignedArray={};
	$scope.submit_click=false;
	$scope.trCrp={travelCorp:false,trvlGroup:"trvl",tvlcrpId:0}
	$scope.data = {selectedIndex: 0,secondLocked:  true,secondLabel:"ROOMS & RATES",bottom:false,firstLocked:false,thirdLocked:true};
	$scope.disableCorp = true;
	$scope.disableTa = true;
	$scope.resv.hdr.selectedSalutation = $scope.names[0].toString();
	$scope.resv.hdr.dob = null ;
	$scope.showRoomMsg = false;
	$scope.resv.hdr.nationality = "India";
	
	$scope.resv.hdr.state = "Kerala";

	$('#maleRadio').attr('checked',true);
	$scope.next = function() {
		$scope.data.selectedIndex = Math.min($scope.data.selectedIndex + 1, 2) ;
	};
	$scope.previous = function() {
		$scope.data.selectedIndex = Math.max($scope.data.selectedIndex - 1, 0);
	};
	$scope.finalPage = function(){
		if($scope.resv.hdr.numRooms!=$scope.totalRoomsAssigned){
			var asRooms=$scope.resv.hdr.numRooms-$scope.totalRoomsAssigned;
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please Assign Rooms,"+asRooms+" rooms are pending").ok('Ok').parent(angular.element(document.body)));
		}else{
			//$('#imgloader').show();
			$scope.loadDiscounts();
			$scope.getRoomRateDetails();
		}
	}


	$http({
		url : '../checkIn/getCheckinTypes',
		method : "POST"
	}).then(function(response) {
		$scope.checkin_type = response.data;
		$scope.resv.hdr.resvType = $scope.checkin_type[0].id.toString();	
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
	});

	$http({
		url:'../checkIn/getPaymentTypes',
		method:"POST"
	}).then(function(response){
		$scope.payment_type = response.data;
		angular.element('#payment_source').blur();
		$scope.resv.hdr.payment_source = $scope.payment_type[0].id.toString();
	},function(response){
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)))

	});



	
	
	$scope.backPreviousTab=function()
	{
		if($scope.data.firstLocked==false)
		{
		
		$scope.data.secondLocked=true;
		$scope.data.firstLocked=false;
		$scope.data.thirdLocked=true;
		window.location.href="../reservation_test/reservationList";
		//$scope.showAssignedWidget=true;
		
		}
		if($scope.data.secondLocked==false)
			{
			
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=false;
			$scope.data.thirdLocked=true;
			//$scope.data.fourthLocked=true;
			//$scope.showAssignedWidget=true;
			
			}
		else if($scope.data.thirdLocked==false)
		{
		
		$scope.data.secondLocked=false;
		$scope.data.firstLocked=true;
		$scope.data.thirdLocked=true;
		//$scope.showAssignedWidget=true;
		}
		
	}
	
	
	
	
	
	$scope.changeCheckout = function(){
		var chDt=$scope.resv.hdr.minArrDate;
		if($scope.resv.hdr.nights!=null && $scope.resv.hdr.nights!=""){
			//$scope.resv.hdr.maxDepartDate=formatDate(chDt)+$scope.resv.hdr.nights;
			//$scope.resv.hdr.maxDepartDate=formatDate(chDt)+$scope.resv.hdr.nights;
			var maxDepDateCalc=new Date(new Date(chDt).getFullYear(),new Date(chDt).getMonth(),new Date(chDt).getDate()+$scope.resv.hdr.nights);
			$scope.resv.hdr.maxDepartDate=formatDate(maxDepDateCalc);
		}
		$().setDateRangeFn($scope.resv.hdr.minArrDate,$scope.resv.hdr.maxDepartDate);
	}
	$scope.changeNight = function(){
		var deptDateCalc=formatDate($scope.resv.hdr.maxDepartDate);
		var arrDateCal=formatDate($scope.resv.hdr.minArrDate);
		var deptCalc= moment(deptDateCalc);
		var arrCalc=moment(arrDateCal);
		var days = moment.duration(arrCalc.diff(deptCalc)).asDays();
		$scope.resv.hdr.nights = Math.ceil(Math.abs(days));
		if(deptDateCalc<=arrDateCal){

			$scope.resv.hdr.nights=1;
			var deptNight=new Date(new Date($scope.resv.hdr.minArrDate).getFullYear(),new Date($scope.resv.hdr.minArrDate).getMonth(),new Date($scope.resv.hdr.minArrDate).getDate()+$scope.resv.hdr.nights);
			$scope.resv.hdr.maxDepartDate=formatDate(deptNight);
		}
		if($scope.resv.hdr.nights > $scope.maxNights){
			$scope.resv.hdr.nights=1;
			var deptNight=new Date(new Date($scope.resv.hdr.minArrDate).getFullYear(),new Date($scope.resv.hdr.minArrDate).getMonth(),new Date($scope.resv.hdr.minArrDate).getDate()+$scope.resv.hdr.nights);
			$scope.resv.hdr.maxDepartDate=formatDate(deptNight);
		}
		$scope.resv.hdr.cutOffDate=new Date(new Date($scope.resv.hdr.minArrDate).getFullYear(),new Date($scope.resv.hdr.minArrDate).getMonth(),new Date($scope.resv.hdr.minArrDate).getDate()-$scope.confirmbefore);
		if($scope.resv.hdr.cutOffDate<new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate())){	
			$scope.resv.hdr.cutOffDate=new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate());
		}
		$scope.initial.cutoffmaxDate=new Date(new Date($scope.resv.hdr.minArrDate).getFullYear(),new Date($scope.resv.hdr.minArrDate).getMonth(),new Date($scope.resv.hdr.minArrDate).getDate());
		$scope.initial.chOutminDate=new Date(new Date($scope.resv.hdr.minArrDate).getFullYear(),new Date($scope.resv.hdr.minArrDate).getMonth(),new Date($scope.resv.hdr.minArrDate).getDate()+1);
		$scope.initial.chOutmaxDate=new Date(new Date($scope.resv.hdr.minArrDate).getFullYear(),new Date($scope.resv.hdr.minArrDate).getMonth(),new Date($scope.resv.hdr.minArrDate).getDate()+$scope.maxNights);
		$scope.pickUp.pickupDate=$scope.resv.hdr.minArrDate;
		$().setDateRangeFn($scope.resv.hdr.minArrDate,$scope.resv.hdr.maxDepartDate);
		
	}
	$scope.changeDateRage = function(date1,date2){

		var sdate = new Date(date1);
		$scope.resv.hdr.minArrDate = new Date(sdate);	
		var endDate = new Date(date2);
		$scope.resv.hdr.maxDepartDate = new Date(endDate);
		$scope.changeNight();
		$scope.$digest();
	}
	$scope.loadState = function(nationality){
		//if(!$scope['is_old_customer_'+index]){
		//	$scope.roomListData[index].state=""
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
			//$scope.stateList[index]= data;
			$scope.stateList= data;
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});

	}
	
	$scope.checkAvailability=function(){
		$scope.resv.hdr.minArrDate=$("#datetimepicker").val();
		$scope.resv.hdr.maxDepartDate=$("#datetimepickerDept").val();
		if($scope.resv.hdr.resvType==0){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please enter Type").ok('Ok').parent(angular.element(document.body)));
			return;

		}else if($scope.resv.hdr.resvType ==2 && $scope.trCrp.tvlcrpId==0  ){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please enter Corporate Name").ok('Ok').parent(angular.element(document.body)));
			return;
		}
		else if($scope.resv.hdr.resvType==3 && $scope.trCrp.tvlcrpId==0  ){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please enter TA Name").ok('Ok').parent(angular.element(document.body)));
			return;
		}else if((formatDate($scope.resv.hdr.maxDepartDate))< formatDate($scope.resv.hdr.minArrDate)){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Departure date must be greater than Arrival Date").ok('Ok').parent(angular.element(document.body)));
			return;
		}

		var totalavailrooms = $("#totalavailrooms").val();
		if(totalavailrooms < $scope.resv.hdr.numRooms){
			$scope.showRoomMsg = true;
			return;
		}

		ServiceReservation.setMinArrivalDate($scope.resv.hdr.minArrDate);
		maxRoom=$scope.resv.hdr.numRooms;
		//$('#imgloader').show();
		$scope.resv.hdr.minArrDate = $filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat);
		$scope.resv.hdr.minArrTime=$filter('date')(new Date($scope.resv.hdr.minArrDate), 'hh:mm:ss');
		var data = $.param({resvHdr:JSON.stringify($scope.resv.hdr)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomAvailability', data, config)
		.success(function (data, status, headers, config) {
			//$('#imgloader').hide();
			$scope.data.secondLocked=false;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=true;
			$scope.showAssignedWidget=true;
			$scope.roomTypesAvailable=data;
			$scope.resv.hdr.minArrDate = ServiceReservation.getMinArrivalDate();
		}).error(function (data, status, header, config){
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});

	}

	$scope.changePaymentSource = function(){

		$scope.disableCorporate = false;
		$scope.disableTaName = false;

		if($scope.resv.hdr.payment_source == 2){
			$scope.resv.hdr.resvType = "2";
			$scope.getTaCorpList();
			$scope.disableCorporate = true;
			$scope.disableTaName = false;
		}

		if($scope.resv.hdr.payment_source == 1){
			$scope.resv.hdr.resvType = "3";
			$scope.getTaCorpList();
			$scope.disableTaName = true;
			$scope.disableCorporate = false;

		}

		if($scope.resv.hdr.payment_source == 0){
			$scope.resv.hdr.resvType = "1";
			$scope.disableCorporate = false;
			$scope.disableTaName = false;
			$scope.disableTa = true;
			$scope.disableCorp = true;
		}

	}


	$scope.showDetails = function(roomTypeCode,typeId,availRooms){
		$scope.roomTypeId=typeId;
		$scope.roomRates=[];
		newAssgned=[];
		$scope.roomMax = $scope.resv.hdr.numRooms-$scope.totalRoomsAssigned;
		$scope.availableRooms=availRooms;
		//$('#imgloader').show();
		//$scope.resv.hdr.minArrDate = $filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat);
		$scope.resv.hdr.minArrDate=$filter('date')(new Date($("#datetimepicker").val()),'dd-MM-yyyy');
		var data = $.param({roomTypeDtls:JSON.stringify({roomType:roomTypeCode,arrDate:$scope.resv.hdr.minArrDate,nights:$scope.resv.hdr.nights,rate_id:0,trCrp_id:$scope.trCrp.tvlcrpId})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomRatePlans', data, config)
		.success(function (data, status, headers, config) {
			//$('#imgloader').hide();
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
			$scope.resv.hdr.minArrDate = ServiceReservation.getMinArrivalDate();
		}).error(function (data, status, header, config){
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
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

		$scope.AssignedRoomData = $scope.buildData();
		$("#assignRoomsmyModal").modal("toggle");
	}
	$scope.checkin_discounts=[]
	$scope.getRoomRateDetails = function(){
		//$('#imgloader').show();
		$scope.roomRateChekArgs=[];
		//var checkin_discounts={};
		$scope.resv.hdr.minArrDate = $filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat); 
		for (var key in $scope.assignedRoomRatesList){
			var occ=1;
			if($scope.assignedRoomRatesList[key].occ=="occ2"){occ=2;}else if($scope.assignedRoomRatesList[key].occ=="occ3"){occ=3;}else if($scope.assignedRoomRatesList[key].occ=="occ4"){occ=4;}
			var roomRateArgs={arrDate:$scope.resv.hdr.minArrDate,numRooms:$scope.assignedRoomRatesList[key].value,numNights:$scope.resv.hdr.nights,rateId:$scope.assignedRoomRatesList[key].rateId,occupancy:occ};
			if(!angular.equals({},$scope.discApplied)){
				if($scope.discApplied.group=='general'){
					roomRateArgs.discId=parseInt($scope.discApplied.value.discId);
					//checkin_discounts.discId=parseInt($scope.discApplied.value.discId);
					if($scope.discApplied.value.isOpen){
					//	checkin_discounts.openDiscount=parseFloat($scope.discApplied.value.openValue);
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
				if(index = -1){
					$scope.checkin_discounts.push(checkin_discounts);
				}*/
			}			
			$scope.roomRateChekArgs.push(roomRateArgs);
			
		}

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
			$scope.resv.hdr.minArrDate = ServiceReservation.getMinArrivalDate();
			$scope.loadState($scope.resv.hdr.nationality);
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=false;
			$scope.showAssignedWidget=false;
			//$('#imgloader').hide();
		}, function(response) {
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});
	}


	$scope.simpleSearchReservation=function(){
		$scope.phoneNum=$scope.resv.hdr.resvByPhone
		$http({
			url : '../reception/getDetailViaPhonenum',
			method : "POST",
			data:$scope.phoneNum

		}).then(function(response) {
			if(!response.data[0].first_name){
				$scope.is_old_customer=false;	
			}else {
				$scope.is_old_customer=true;
			}
			//	$scope.roomListData[0].lastName=response.data[response.data.length-1].last_name;
			$scope.resv.hdr.resvByLastName=response.data[0].last_name;
			//this.searchText = response.data[0].resv_by_first_name;
			//this.selectedItem = response.data[0].resv_by_first_name;
			$scope.resv.hdr.resvByFirstName=response.data[0].first_name
			$scope.resv.hdr.resvByMail=response.data[0].email;
			$scope.resv.hdr.resvByAddress=response.data[0].address
			/*$scope.resv.hdr.remarks=response.data[0].remarks
			$scope.resv.hdr.specialRequests=response.data[0].special_requests*/
			$scope.last_visit=response.data[0].last_visit;
			$scope.no_visit=response.data[0].no_visit;


		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});

	}




	$scope.buildData = function() {	      
		var returnArr = [];
		var arr1=[];
		var arr2=[];
		$scope.totalRoomsAssigned=0;
		for (var key in $scope.assignedRoomRatesList){
			var assgnd={roomTypeId:$scope.assignedRoomRatesList[key].roomTypeId,roomTypeCode:$scope.assignedRoomRatesList[key].roomTypeCode,rateCode:$scope.assignedRoomRatesList[key].rateCode,rateId:$scope.assignedRoomRatesList[key].rateId,value:$scope.assignedRoomRatesList[key].value};
			if($scope.assignedRoomRatesList[key].occ=="occ1"){
				assgnd.occ1=$scope.assignedRoomRatesList[key].value;
			}else if($scope.assignedRoomRatesList[key].occ=="occ2"){
				assgnd.occ2=$scope.assignedRoomRatesList[key].value;
			}else if($scope.assignedRoomRatesList[key].occ=="occ3"){
				assgnd.occ3=$scope.assignedRoomRatesList[key].value;
			}else if($scope.assignedRoomRatesList[key].occ=="occ4"){
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
		$scope.assignedArray = arr2;
		for (var key in arr2){
			returnArr.push(arr2[key]);
		}
		return returnArr;
	};

	$scope.loadDiscounts=function(){
		discountService.getDiscounts(assignedRateIdList).then(function(response) {
			$scope.availDiscounts = response.data;
			/*$scope.availDiscountList = response.data;
			var dateString = $scope.resv.hdr.minArrDate;
			var dateParts = dateString.split("-");
			$scope.minDate = new Date(dateParts[2],dateParts[1] - 1,dateParts[0]);
			$scope.resv.hdr.maxDepartDate;
			$scope.generalDiscounts=[];
			$scope.planDiscounts=[];
			for(i=0;i<$scope.availDiscountList.general.length;i++){
				$scope.genValidityFrom = new Date($scope.availDiscountList.general[i].validFrom);
				$scope.genValidityTo = new Date($scope.availDiscountList.general[i].validTo);
				var checkGenValidTo = new Date($scope.genValidityTo)>=$scope.resv.hdr.maxDepartDate;
				var checkGenValidFrom = $scope.genValidityFrom<=$scope.minDate;
				if(checkGenValidTo && checkGenValidFrom){
					if(checkGenValidTo==true && checkGenValidFrom ==true){
						$scope.generalDiscounts.push($scope.availDiscountList.general[i]);
					}
				}
			}

			for(i=0;i<$scope.availDiscountList.plan.length;i++){
				$scope.planValidityFrom = new Date($scope.availDiscountList.plan[i].validFrom);
				$scope.planValidityTo = $scope.availDiscountList.plan[i].validTo;
				var checkPlanValidTo = new Date($scope.planValidityTo)>=$scope.resv.hdr.maxDepartDate;
				var checkPlanValidFrom = $scope.planValidityFrom<=$scope.minDate;
				if(checkPlanValidTo && checkPlanValidFrom){
					if(checkPlanValidTo==true && checkPlanValidFrom==true){
						$scope.planDiscounts.push($scope.availDiscountList.plan[i]);
					}
				}
			}*/
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
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
				for(var p in $scope.discApplied.value.discId){
					$(".chk_"+$scope.discApplied.value.discId[p]).css("checked","checked");
				}
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
		
		
		//$('#imgloader').show();
		$scope.roomRateChekArgs=[];
		//var checkin_discounts={};
		$scope.resv.hdr.minArrDate = $filter('date')(new Date($scope.resv.hdr.minArrDate), dateFormat); 
		for (var key in $scope.assignedRoomRatesList){
			var occ=1;
			if($scope.assignedRoomRatesList[key].occ=="occ2"){occ=2;}else if($scope.assignedRoomRatesList[key].occ=="occ3"){occ=3;}else if($scope.assignedRoomRatesList[key].occ=="occ4"){occ=4;}
			var roomRateArgs={arrDate:$scope.resv.hdr.minArrDate,numRooms:$scope.assignedRoomRatesList[key].value,numNights:$scope.resv.hdr.nights,rateId:$scope.assignedRoomRatesList[key].rateId,occupancy:occ};			
			$scope.roomRateChekArgs.push(roomRateArgs);
			
		}

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
			$scope.resv.hdr.minArrDate = ServiceReservation.getMinArrivalDate();
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=false;
			$scope.showAssignedWidget=false;
			//$('#imgloader').hide();
		}, function(response) {
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});

		
	}
	
	
	$scope.discDispList=[];
	$scope.applyDiscount =function(){
		//if(('generalDisc' in $scope.disc) || (!angular.equals({},$scope.disc.planBased))){
		if(($scope.disc.generalDisc) || (!angular.equals({},$scope.disc.planBased))){
			//$scope.discDispList=[];
			var checkin_discounts={};
			var discDisp={code:"",value:""};
			if($scope.disc.group=='general'){
				var opendisc=0;
				var isOpenDisc=false;
				discDisp.id = $scope.disc.generalDisc;
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
				
			}else{
				for (var p in $scope.disc.planBased) {
					discDisp={};
					if( $scope.disc.planBased.hasOwnProperty(p)){
						if($scope.disc.planBased[p]!="" && $scope.disc.planBased[p]!=null){
							discDisp.id = p;
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
			//	$scope.discApplied={group:'plan',value:{discId:$scope.disc.planBased}};
			}
			//$scope.getRoomRateDetails();
			if($scope.discDispList[$scope.discDispList.length-1].discountFor == 1){
				$scope.getRoomRateDetails();
			}
			$("#availDiscountsmyModal").modal("toggle");	
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("No discount selected").ok('Ok').parent(angular.element(document.body)));	
		}
	}


	$scope.changeDiscGroup = function(){
		if($scope.disc.group=='general'){
			angular.forEach($scope.availDiscounts.plan,function(item)
					{
				$scope.disc.planBased[item.id]=false;
					});
		}
		if($scope.disc.group=='plan'){
			angular.forEach($scope.availDiscounts.general,function()
					{
				$scope.disc.generalDisc=false;
				/*$scope.disc.planBased[item.id]=true;*/
					});
		}

	};



	/*$scope.getTaCorpList = function(){
		if($scope.trCrp.travelCorp){
			$http({
				url : '../reservation_test/getTACorpList',
				method : "POST",
				data : $scope.trCrp.trvlGroup
			}).then(function(response) {
				$scope.trCrp.ta = {};
				$scope.trCrp.corp = {};
				if( $scope.trCrp.trvlGroup=="trvl"){
					$scope.trCrp.ta = response.data;
				}else{
					$scope.trCrp.corp = response.data;
				}
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error!!').textContent("Please contact Administrator").ok('Ok!').parent(angular.element(document.body)));
			});
		}
	}*/

	$scope.getTaCorpList = function(){
		$http({
			url : '../reservation_test/getTACorpList',
			method : "POST",
			data : parseInt($scope.resv.hdr.resvType)-1
		}).then(function(response) {
			$scope.trCrp.ta = {};
			$scope.trCrp.corp = {};
			if(  $scope.resv.hdr.resvType==3){
				$scope.trCrp.ta = response.data;
				$scope.disableTa = false;
				$scope.disableCorp = true;
				$scope.trCrp.tvlcrpId = $scope.trCrp.ta[0].id.toString();
			}else if($scope.resv.hdr.resvType==2){
				$scope.trCrp.corp = response.data;
				$scope.disableCorp = false;
				$scope.disableTa = true;
				$scope.trCrp.tvlcrpId = $scope.trCrp.corp[0].id.toString();
			}
			else{
				$scope.disableCorp = true;
				$scope.disableTa = true;
			}
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});
	}




	$scope.loadProviders = function(){
		if($scope.pickupReq){
			$http({
				url : '../facilityProvider/facilityProviderDetails',
				method : "POST",
			}).then(function(response) {
				$scope.providers = response.data;
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
			});
		}
	}


	function validateEmail(emailField){
		//if(emailField=="") return true;
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
		if ($scope.resv.hdr.resvByMail!="" && reg.test($scope.resv.hdr.resvByMail) == false ) 
		{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Invalid Email address").ok('Ok').parent(angular.element(document.body)));	
			return false;
		}
		return true;
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

		if(validateEmail($scope.resv.hdr.resvByMail)){
			$scope.submit_click=true;
			$scope.resv.hdr.pickupNeeded=$scope.pickupReq;
			$scope.mealPlan=$scope.resv.hdr.mealPlan;
			if($scope.resv.hdr.pickupNeeded){
				$scope.resv.hdr.pickupDate=$filter('date')(new Date($scope.pickUp.pickupDate), dateFormat);
				//$scope.resv.hdr.pickupTime=$filter('date')(new Date($scope.pickUp.pickupTime), "hh:mm a");
				$scope.resv.hdr.pickupTime=$("#pickuptime").val();
				$scope.resv.hdr.pickupLocation=$scope.pickUp.location;
				$scope.resv.hdr.pickupSeats=$scope.pickUp.seats;
				$scope.resv.hdr.pickupProvider=$scope.pickUp.pickupProvider;
				$scope.resv.hdr.pickupRemarks=$scope.pickUp.pickupRemarks;
			}
			/*if($scope.trCrp.travelCorp){
			if( $scope.trCrp.trvlGroup=="trvl"){
				$scope.resv.hdr.resvType=3;		
			}else if( $scope.trCrp.trvlGroup=="corp"){
				$scope.resv.hdr.resvType=2;
			}else{
				$scope.resv.hdr.resvType=1;
			}
			$scope.resv.hdr.corporateId=$scope.trCrp.tvlcrpId;
		}*/
			$scope.resv.hdr.corporateId=$scope.trCrp.tvlcrpId;
			if(!angular.equals({},$scope.discApplied)){
				if($scope.discApplied.group=='general'){
					$scope.resv.hdr.discType=2;
				}else{
					$scope.resv.hdr.discType=1;
				}
			}
			$scope.salutation=$scope.resv.hdr.selectedSalutation;
			$scope.resv.hdr.minArrDate=$("#datetimepicker").val();
			$scope.resv.hdr.maxDepartDate=$("#datetimepickerDept").val();
			$scope.resv.hdr.minArrTime=$filter('date')(new Date($scope.resv.hdr.minArrDate), 'hh:mm:ss');
			$scope.resv.hdr.minArrDate=$filter('date')(new Date($scope.resv.hdr.minArrDate), "yyyy-MM-dd hh:mm:ss");
			$scope.resv.hdr.maxDepTime=$filter('date')(new Date($scope.resv.hdr.maxDepartDate), 'hh:mm:ss');
			$scope.resv.hdr.maxDepartDate=$filter('date')(new Date($scope.resv.hdr.maxDepartDate), "yyyy-MM-dd hh:mm:ss"); 
			$scope.resv.hdr.cutOffDate= $filter('date')(new Date($scope.resv.hdr.cutOffDate), "yyyy-MM-dd");		
			if($scope.resv.hdr.dob!=null && $scope.resv.hdr.dob!="")
				{
			$scope.resv.hdr.dob= $filter('date')(new Date($scope.resv.hdr.dob), "yyyy-MM-dd");
				}
			if($scope.resv.hdr.dob==null){
				$scope.resv.hdr.dob="";
			}
			$scope.saveData={resvHdr:$scope.resv.hdr,resvDtls:$scope.roomRateChekArgs,checkinDiscnt:$scope.checkin_discounts};
			//$('#imgloader').show();
			$http({
				url : '../reservation_test/saveNewReservation',
				method : "POST",
				data:JSON.stringify($scope.saveData)
			}).then(function(response) {

				var msg=response.data.substring(7).split("_");
				if(msg[0]=='success'){
					//$('#imgloader').hide();
					var confirm = $mdDialog.alert()
					.title("Reservation completed successfully").ok('OK').parent(angular.element(document.body));
					$mdDialog.show(confirm).then(function(){
						window.location  = "../reservation_test/reservationList";
						$http({
							url:'../reservation_test/mailReservationSave',
							method:"POST",
							params:{reservationNo:msg[1]}
						}).then(function(response){
							window.location  = "../reservation_test/reservationList";
						})
					});
				}

			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
			});
		}

	}
	$scope.cancelRoomAssignPopUp = function(){
		$("#assignRoomsmyModal").modal("toggle");
	}
	$scope.cancelDiscPopUp  = function(){
		$("#availDiscountsmyModal").modal("toggle");
	}
	/*time picker*/
	$scope.hstep = 1;
	$scope.mstep = 1;
	$scope.ismeridian=true;
	/*time picker*/

	$scope.showCalender= function(){
		$("#calendermyModal").modal({backdrop: "static"});	
		$rootScope.$emit("loadCalender",$scope.resv.hdr.minArrDate);
	}
	$scope.cancelCalenderPopUp = function(){
		$("#calendermyModal").modal("toggle");
	}


	$http({
		url:'../reservation_test/getCountries',
		method:'POST'
	}).then(function(response){
		$scope.countryList = response.data;
		for(i=0;i<$scope.countryList.length;i++){
			$scope.countryArr.push($scope.countryList[i].name);
			$scope.countryidArr.push($scope.countryList[i].id);
			/*self.simulateQuery = false;*/
			self.states        = loadAll();
			self.querySearch   = querySearch;
			self.selectedItemChange = selectedItemChange;
			self.searchTextChange   = searchTextChange;

		}
	});



	$http({
		url:'../checkIn/getCustomers',
		method:'POST'
	}).then(function(response){
		$scope.customerList = response.data;
		for(i=0;i<$scope.customerList.length;i++){
			$scope.customerArr.push({id:$scope.customerList[i].id,name:$scope.customerList[i].name});
			//$scope.customerIdArr.push($scope.countryList[i].id);
			/*self.simulateQuery = false;*/
			self.customers        = loadAll();
			self.querySearch   = querySearch;
			self.selectedItemChange = selectedItemChange;
			self.searchTextChange   = searchTextChange;

		}
	});

	function loadAll() {
		var allCustomer = $scope.customerArr;
		return allCustomer.map( function (customer) {
			return {
				value: customer.id,
				display: customer.name.toLowerCase()
			};
		});
	}

	function querySearch (query) {
		return query ? self.customers.filter( createFilterFor(query) ) : self.customers;
	}

	function searchTextChange(text) {
		$scope.is_old_customer=false;
		$scope.resv.hdr.resvByFirstName=text
		/*$scope.resv.hdr.resvByLastName= "";
		$scope.resv.hdr.resvByMail="";
		$scope.resv.hdr.resvByPhone="";
		$scope.resv.hdr.resvByAddress="";*/
		$scope.last_visit="";
		$scope.no_visit="";
	}


	function selectedItemChange(item) {
		$scope.loadData(item);

	}

	function createFilterFor(query) {
		var lowercaseQuery = angular.lowercase(query);
		return function filterFn(customers) {
			return (customers.display.indexOf(lowercaseQuery) === 0);
		};
	}


	$scope.loadData=function(item){
		if(!item.value){
			return;
		}


		var data = $.param({data:JSON.stringify(item.value)});
		//var data = $.param({data:$scope.id});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../checkIn/loadData', data, config)
		.success(function (data, status, headers, config) {	
			$scope.is_old_customer=true;

			if(data.last_name !== null){
				$scope.resv.hdr.resvByLastName= data.last_name;
			}else{
				$scope.resv.hdr.resvByLastName= "";
			}
			if(data.mail !== null){
				$scope.resv.hdr.resvByMail=data.mail;
			}else{
				$scope.resv.hdr.resvByMail="";
			}
			if(data.phone !== null){
				$scope.resv.hdr.resvByPhone=data.phone;
			}else{
				$scope.resv.hdr.resvByPhone="";
			}
			if($scope.resv.hdr.resvByAddress!== null){
				$scope.resv.hdr.resvByAddress=data.address;
			}else{
				$scope.resv.hdr.resvByAddress=""
			}
			$scope.last_visit=data.last_visit;
			$scope.no_visit=data.no_visit;
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});
	}


	$scope.simpleSearchByMail=function(){
		var data = $.param({data:$scope.resv.hdr.resvByMail});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../checkIn/loadDataByMail', data, config)
		.success(function (data, status, headers, config) {	
			if(!data.first_name){
				$scope.is_old_customer=false;
			}else{
				$scope.is_old_customer=true;
			}
			$scope.resv.hdr.resvByFirstName= data.first_name;
			if(data.last_name !== null){
				$scope.resv.hdr.resvByLastName= data.last_name;
			}else{
				$scope.resv.hdr.resvByLastName="";
			}

			$scope.resv.hdr.resvByPhone=data.phone;
			$scope.resv.hdr.resvByAddress=data.address;
			$scope.last_visit=data.last_visit;
			$scope.no_visit=data.no_visit;
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});

	}

	$scope.loadCustomerData=function(){
		var data = $.param({data:$scope.resv.hdr.resvByFirstName});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../checkIn/loadData', data, config)
		.success(function (data, status, headers, config) {
			$scope.customerList = data;
			if($scope.customerList.length >1){
				$('#myModal').modal('show');
			}else if($scope.customerList.length ==1) {
				$scope.copyData($scope.customerList[0])
			}else{
				$scope.is_old_customer=false;
				$scope.resv.hdr.resvByLastName= "";
				$scope.resv.hdr.resvByMail="";
				$scope.resv.hdr.resvByPhone="";
				$scope.resv.hdr.resvByAddress=""
			}
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});

	}
	$scope.copyData= function(data){
		$scope.is_old_customer=true;

		$scope.resv.hdr.resvByFirstName= data.first_name;
		if(!data.last_name){
			$scope.resv.hdr.resvByLastName= "";
		}else{
			$scope.resv.hdr.resvByLastName= data.last_name;
		}
		if(!data.mail){
			$scope.resv.hdr.resvByMail="";
		}else{
			$scope.resv.hdr.resvByMail=data.mail;
		}
		if(!data.phone){
			$scope.resv.hdr.resvByPhone="";
		}else{
			$scope.resv.hdr.resvByPhone=data.phone;
		}
		if(!data.address){
			$scope.resv.hdr.resvByAddress=""
		}else{
			$scope.resv.hdr.resvByAddress=data.address;
		}
		$scope.last_visit=data.last_visit;
		$scope.no_visit=data.no_visit;
		$('#myModal').modal('hide');
	}

	/* to get Customer Data */
	$scope.getCustomerDetails=function(){

		$scope.customerName={salutation:$scope.resv.hdr.selectedSalutation,firstName:$scope.resv.hdr.resvByFirstName,lastName:$scope.resv.hdr.resvByLastName};
		$http({
			method:'post',
			url:'../reservation_test/getCustomerData',
			data:$scope.customerName
		}).then(function(response){
			console.log(response.data);
			$scope.customerData=response.data[0];
			$scope.customerDetails=response.data;
			$("#customerDetailsModel").modal("toggle");
		})
	}

	/*$scope.mylocale = {
			  formatDate: function(date) {
			    var m = moment(date);
			    return m.isValid() ? m.format($filter('uppercase')($scope.dateFormat)) : '';
			  }
	}*/

	$scope.showTooltip = false;
	$scope.showCountryError = false;
	$scope.showStateError = false;
	$scope.next = function(){
		if($scope.roomListData[0].passportNo != "" && $scope.roomListData[0].passportNo != undefined){
			if($scope.roomListData[0].passportExpiryOn == "" || $scope.roomListData[0].passportExpiryOn == undefined){
				$scope.showTooltip = true;
				$timeout(function(){$scope.showTooltip = false;},3000);
			}
		}

		if($scope.roomListData[0].nationality == "" || $scope.roomListData[0].nationality == undefined){
			$scope.showCountryError = true;
			$timeout(function(){$scope.showCountryError = false;},3000);
		}

		if($scope.roomListData[0].state == "" || $scope.roomListData[0].state == undefined){
			$scope.showStateError = true;
			$timeout(function(){$scope.showStateError = false;},3000);
		}
	}


	/* $('#datetimepicker').datetimepicker({
		     format:'Y-m-d',timepicker: true,
		 });*/

	$('#dobSelected').keyup(function(e) {
	    if(e.keyCode == 8 || e.keyCode == 46) {
	        $.datepicker._clearDate(this);
	    }
	});
	

}]);

pmsApp.controller('calCtrl', ['$scope','$http','$window','$mdDialog','$rootScope','$filter',function ($scope, $http,$window,$mdDialog,$rootScope,$filter){
	var hdate=$("#hotelDate").val();
	var dateFormat = "yyyy-MM-dd HH:mm:ss Z";
	var weekday = new Array(7);
	weekday[0] =  "SUN";
	weekday[1] = "MON";
	weekday[2] = "TUE";
	weekday[3] = "WED";
	weekday[4] = "THU";
	weekday[5] = "FRI";
	weekday[6] = "SAT";
	var vm = this;
	$scope.dateArray=[];
	$scope.startDate=$filter('date')(new Date(hdate), dateFormat);
	$scope.calendarData=[];
	$scope.loadData = function(){
		$scope.roomTypes={};
		$http({
			url : '../reservation_test/getCalendarData',
			method : "POST",
			params:{startDate:$scope.startDate}
		}).then(function(response) {
			response.data.forEach(function(calData){
				$scope.roomTypes[calData.roomTypeId]=calData.roomTypeCode;
			});	
			$scope.calendarData = response.data;
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.loadDate=function(){
		$scope.dateArray=[];
		for(var i=0;i<7;i++){
			var newDate=new Date(new Date($scope.startDate).getFullYear(),new Date($scope.startDate).getMonth(),new Date($scope.startDate).getDate()+i);
			var cal={day:"",date:""};
			cal.day=weekday[new Date(newDate).getDay()];
			cal.date=newDate;
			$scope.dateArray.push(cal);
		}
	}
	$rootScope.$on("loadCalender", function(event,stDate){
		$scope.startDate = $filter('date')(new Date(stDate), dateFormat);
		$scope.loadDate();
		$scope.loadData();
	});
	$scope.calGoRight = function(){
		$scope.startDate= new Date(new Date($scope.startDate).getFullYear(),new Date($scope.startDate).getMonth(),new Date($scope.startDate).getDate()+7).getTime();
		$scope.startDate=$filter('date')(new Date($scope.startDate), dateFormat);
		$scope.loadDate();
		$scope.loadData();	
	}
	$scope.calGoLeft = function(){
		if(new Date(new Date($scope.startDate).getFullYear(),new Date($scope.startDate).getMonth(),new Date($scope.startDate).getDate()-7).getTime()>new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate()).getTime()){
			$scope.startDate= new Date(new Date($scope.startDate).getFullYear(),new Date($scope.startDate).getMonth(),new Date($scope.startDate).getDate()-7).getTime();
		}
		else{
			$scope.startDate=new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate()).getTime();
		}
		$scope.startDate=$filter('date')(new Date($scope.startDate), dateFormat);
		$scope.loadDate();
		$scope.loadData();
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
				if (parseInt(this.value)>limit || totalAssigned>limit || (parseInt(this.value)+assignedPerRoomType)>limit || totalAssigned>scope.roomMax || (scope.totalRoomsAssigned+parseInt(this.value))>scope.resv.hdr.numRooms){
//					if(scope.roomMax>limit){
//					this.value=limit;
//					newAssgned[this.id]=limit;
//					}else if(scope.roomMax<limit){
//					this.value=scope.roomMax;
//					newAssgned[this.id]=scope.roomMax;
//					}else{
//					this.value=limit;
//					newAssgned[this.id]=limit;
//					}
////					this.value=0;
////					newAssgned[this.id]="0";
//					e.preventDefault();
					this.value=0;
					newAssgned[this.id]=0;
//					}
					e.preventDefault();

				}
			});
		}
	}
}]);

$(function(){
	var day=1;
	var hdate=$("#hotelDate").val();
	var stdate= formatDate(new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate())+parseInt($("#confirmbefore").val()));
	var enDate=formatDate(new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate()+(parseInt($("#confirmbefore").val()))+day));
	$('#date-range12').dateRangePicker({
		inline:true,
		container: '#date-range12-container', 
		alwaysOpen:true,
		startDate:formatDate(stdate),
		minDays:1,
		maxDays:parseInt($("#maxNights").val())+1
	}).bind('datepicker-change', function(evt, obj) {
		angular.element('#daterange_pickerDiv').scope().changeDateRage(obj.date1,obj.date2);
	});
	$('#date-range12').data('dateRangePicker').setDateRange(stdate,enDate, true);
	$.fn.setDateRangeFn = function(startDate,endingDate){
		$('#date-range12').data('dateRangePicker').setDateRange(startDate,endingDate,true);
	}
	function formatDate(date) {
		var d = new Date(date),
		month = '' + (d.getMonth() + 1),
		day = '' + d.getDate(),
		year = d.getFullYear();
		if (month.length < 2) month = '0' + month;
		if (day.length < 2) day = '0' + day;
		return [year, month, day].join('-');
	}
});
$(document).ready(function(){
	$(".md-datepicker-input").mouseover(function(){
		$('.md-datepicker-input').attr('readonly', true);
	});
});

function formatDate(date) {
	var d = new Date(date),
	month = '' + (d.getMonth() + 1),
	day = '' + d.getDate(),
	year = d.getFullYear();
	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;
	return [year, month, day].join('-');
}
//$(document).ready(function(){


//});
