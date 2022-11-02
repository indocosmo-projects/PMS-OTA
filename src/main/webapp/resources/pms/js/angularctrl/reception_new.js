var maxRoom= 0;
var newRateChange=0;

pmsApp.requires.push('webcam');
pmsApp.service("ServiceReception",function(){
	var arvldate;
	this.setMinArrivalDate = function(arr){
		arvldate= arr;
	}
	this.getMinArrivalDate = function(){
		return arvldate;
	}	
});
pmsApp.controller('receptionCtrl', ['$scope','$q','$log','$http','$window','$mdDialog','$rootScope','$filter','discountService','$timeout','ServiceReception',function ($scope,$q,$log,$http,$window,$mdDialog,$rootScope,$filter,discountService,$timeout,ServiceReception){
	var hdateformat=$("#hotelDate").val();
	var arrdateFormat = "MM-dd-yyyy";
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
	$scope.hotelDateFormat=$filter('date')(new Date(hdateformat), arrdateFormat);
	$scope.hotelDate=new Date($("#hotelDate").val());
	
	var self = this;
	self.simulateQuery = false;
	self.isDisabled    = false;
	var _this = this;
	var hotalDateFormat = "mm/dd/yyyy"
	var newRateChange=0;
	$scope.countryList=[];
	var hdate=$("#hotelDate").val();
	var dateFormat =  $("#dateFormat").val();
	$scope.dateFormat = $("#dateFormat").val();
	$scope.maxNights=parseInt($("#maxNights").val());
	$scope.currency=$("#currency").val();
	var dateFormat = $("#dateFormat").val();
	$scope.data = {selectedIndex: 0,secondLocked:true,bottom:false,firstLocked:false,thirdLocked:true,fourthLocked:true};
	$scope.initial={chOutminDate:new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate()+1)};
	$scope.checkin={hdr:{minArrDate:new Date(hdate),maxDepartDate:new Date(new Date(hdate).getFullYear(),new Date(hdate).getMonth(),new Date(hdate).getDate()+1),numRooms:1,specialRequests:"",remarks:"",nights:1,type:0,payment_source:0}};
	$scope.initial.chOutmaxDate=new Date(new Date($scope.initial.chOutminDate).getFullYear(),new Date($scope.initial.chOutminDate).getMonth(),new Date($scope.initial.chOutminDate).getDate()+$scope.maxNights);
	$scope.trCrp={travelCorp:false,trvlGroup:"trvl",tvlcrpId:0};
	$scope.roomTypesAvailable={};
	$scope.assignedRoomRatesList = {};
	$scope.oneAtATime = true;
	var assignedRateIdList=[];
	$scope.totalRoomsAssigned=0;
	$scope.roomRates=[];
	$scope.roomRatesOccupancy=[];
	$scope.roomTypeId=0;
	$scope.showAssignedWidget=false;
	$scope.submit_click=false;
	$scope.fBox=false;
	$scope.disc={general:{},planBased:{},group:'general'};
	$scope.discApplied={};
	$scope.availDiscounts={};
	$scope.assignedArray={};
	$scope.assignedRoomNumbers={};
	$scope.countryArr=[];
	$scope.countryidArr=[];
	$scope.stateArr=[];
	$rootScope.india_Arr=[];
	$scope.originalRoomRate=[];
	$scope.indexChangeRate=0;
	$scope.customerArr=[]
	var lengthOfIndex=0;
	var numRoomOccupancyVal=0;
	$rootScope.country_state_array=[];
	$scope.country_states={country:"",states:[]};
	$scope.salutations = [];
	$scope.stateList=[];
	$scope.disableTa = true;
	$scope.disableCorp = true;
	
	$scope.mylocale = {
			  formatDate: function(date) {
			    var m = moment(date);
			    var datef = $("#dateFormat").val().toUpperCase();
			    return m.isValid() ? m.format(datef) : '';
			  }
	}

	/*$( "body" ).on( "keyup", '.occ1', function() {
		$scope.indexChangeRate=$(this).data('index');

		var newRateChange=$(this).val();
		var lengthOfIndex=$scope.indexChangeRate+$scope.checkin.hdr.nights;
		for(i=$scope.indexChangeRate;i<lengthOfIndex-1;i++){

			$scope.roomRatesOccupancy[$scope.indexChangeRate].occ1Rate=newRateChange;
			$scope.roomRatesOccupancy[i+1].occ1Rate=$scope.roomRatesOccupancy[$scope.indexChangeRate].occ1Rate;

		}
		for(j=0;j<$scope.roomRates.length;j++){
			if($scope.roomRates[j].rateId==$scope.roomRatesOccupancy[$scope.indexChangeRate].rateId){
				$scope.roomRates[j].totalOcc1Rate=newRateChange*$scope.checkin.hdr.nights;
			}
		}
	});
*/
	
	
	
	$scope.backPreviousTab=function()
	{
	
		 if($scope.data.firstLocked==false)
		{ 
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=false;
			$scope.data.thirdLocked=true;
			$scope.data.fourthLocked=true;
			
			//$scope.showAssignedWidget=true;
			window.location.href="../reception/receptionList";
			}
		 else if($scope.data.secondLocked==false)
			{
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=false;
			$scope.data.thirdLocked=true;
			$scope.data.fourthLocked=true;
			
			//$scope.showAssignedWidget=true;
			
			}
		else if($scope.data.thirdLocked==false)
		{
			$scope.data.secondLocked=false;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=true;
			$scope.data.fourthLocked=true;
			
			//$scope.showAssignedWidget=true;
			
			}
		else if($scope.data.fourthLocked==false)
		{
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=false;
			$scope.data.fourthLocked=true;
		
			//$scope.showAssignedWidget=true;
			
			}
		
	}
	
	
	
	
	
	
	
	$scope.getTotalRate=function(index){

		var totalRate=0;
		 $(".assgnRate").each(function() {
		        //add only if the value is number
		        if (!isNaN(this.value) && this.value.length != 0) {
		        	totalRate += parseFloat(this.value);		          
		        }
		        else if (this.value.length != 0){
		            $(this).css("background-color", "red");
		        }
		        for(j=0;j<$scope.roomRates.length;j++){
					if($scope.roomRates[j].rateId==$scope.roomRatesOccupancy[$scope.indexChangeRate].rateId){
						$scope.roomRates[j].totalOcc1Rate=totalRate;
					}
				}
		    });
	}
	$( "body" ).on( "keyup", '.occ2', function() {
		$scope.indexChangeRate=$(this).data('index');
		//var newRateChange=$(this).val();
		var lengthOfIndex=$scope.indexChangeRate+$scope.checkin.hdr.nights;
		for(i=$scope.indexChangeRate;i<lengthOfIndex-1;i++){
			$scope.roomRatesOccupancy[$scope.indexChangeRate].occ2Rate=newRateChange;

			$scope.roomRatesOccupancy[i+1].occ2Rate=$scope.roomRatesOccupancy[$scope.indexChangeRate].occ2Rate;
		}
		for(j=0;j<$scope.roomRates.length;j++){
			if($scope.roomRates[j].rateId==$scope.roomRatesOccupancy[$scope.indexChangeRate].rateId){
				$scope.roomRates[j].totalOcc2Rate=newRateChange*$scope.checkin.hdr.nights;
			}
		}
	});
	$( "body" ).on( "keyup", '.occ3', function() {
		$scope.indexChangeRate=$(this).data('index');
		var newRateChange=$(this).val();
		var lengthOfIndex=$scope.indexChangeRate+$scope.checkin.hdr.nights;
		for(i=$scope.indexChangeRate;i<lengthOfIndex-1;i++){
			$scope.roomRatesOccupancy[$scope.indexChangeRate].occ3Rate=newRateChange;

			$scope.roomRatesOccupancy[i+1].occ3Rate=$scope.roomRatesOccupancy[$scope.indexChangeRate].occ3Rate;
		}
		for(j=0;j<$scope.roomRates.length;j++){
			if($scope.roomRates[j].rateId==$scope.roomRatesOccupancy[$scope.indexChangeRate].rateId){
				$scope.roomRates[j].totalOcc3Rate=newRateChange*$scope.checkin.hdr.nights;
			}
		}
	});
	$( "body" ).on( "keyup", '.occ4', function() {
		$scope.indexChangeRate=$(this).data('index');
		var newRateChange=$(this).val();
		var lengthOfIndex=$scope.indexChangeRate+$scope.checkin.hdr.nights;
		for(i=$scope.indexChangeRate;i<lengthOfIndex-1;i++){
			$scope.roomRatesOccupancy[$scope.indexChangeRate].occ4Rate=newRateChange;

			$scope.roomRatesOccupancy[i+1].occ4Rate=$scope.roomRatesOccupancy[$scope.indexChangeRate].occ4Rate;
		}
		for(j=0;j<$scope.roomRates.length;j++){
			if($scope.roomRates[j].rateId==$scope.roomRatesOccupancy[$scope.indexChangeRate].rateId){
				$scope.roomRates[j].totalOcc4Rate=newRateChange*$scope.checkin.hdr.nights;
			}
		}
	});
	$("body").on("keyup",'.numRoomOccupancy1',function(){
		var numRoomOccupancyVal=$(this).val();
		var lengthOfIndex=$scope.indexChangeRate+$scope.checkin.hdr.nights;
		for(i=$scope.indexChangeRate;i<lengthOfIndex-1;i++){
			$scope.roomRatesOccupancy[i+1].occ1Val=numRoomOccupancyVal;
		}
	});
	$("body").on("keyup",'.numRoomOccupancy2',function(){
		var numRoomOccupancyVal=$(this).val();
		var lengthOfIndex=$scope.indexChangeRate+$scope.checkin.hdr.nights;
		for(i=$scope.indexChangeRate;i<lengthOfIndex-1;i++){
			$scope.roomRatesOccupancy[i+1].occ2Val=numRoomOccupancyVal;
		}
		

	});
	$("body").on("keyup",'.numRoomOccupancy3',function(){
		var numRoomOccupancyVal=$(this).val();
		var lengthOfIndex=$scope.indexChangeRate+$scope.checkin.hdr.nights;
		for(i=$scope.indexChangeRate;i<lengthOfIndex-1;i++){
			$scope.roomRatesOccupancy[i+1].occ3Val=numRoomOccupancyVal;
		}
		

	});
	$("body").on("keyup",'.numRoomOccupancy4',function(){
		var numRoomOccupancyVal=$(this).val();
		var lengthOfIndex=$scope.indexChangeRate+$scope.checkin.hdr.nights;
		for(i=$scope.indexChangeRate;i<lengthOfIndex-1;i++){
			$scope.roomRatesOccupancy[i+1].occ4Val=numRoomOccupancyVal;
		}
		
	});

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
		url : '../checkIn/getCheckinTypes',
		method : "POST"
	}).then(function(response) {

			$scope.checkin_type = response.data;
			$scope.checkin.hdr.type = $scope.checkin_type[0].id.toString();
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});
	
	$http({
		url:'../checkIn/getPaymentTypes',
		method:"POST"
	}).then(function(response){
		$scope.payment_type = response.data;
		$scope.checkin.hdr.payment_source = $scope.payment_type[0].id.toString();
	},function(response){
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)))
		
	});
	
	
	$scope.fBox=false;
	$scope.disc={general:{},planBased:{},group:'general'};
	$scope.discApplied={};
	$scope.availDiscounts={};
	$scope.assignedArray={};
	$scope.assignedRoomNumbers={};
	$scope.changeCheckout = function(){
		var chDt=$scope.checkin.hdr.minArrDate;
		if($scope.checkin.hdr.nights!=null && $scope.checkin.hdr.nights!=""){
			$scope.checkin.hdr.maxDepartDate=new Date(new Date(chDt).getFullYear(),new Date(chDt).getMonth(),new Date(chDt).getDate()+$scope.checkin.hdr.nights);
		}
	}


	/*
	function querySearch (query) {
		return query ? self.states.filter( createFilterFor(query) ) : self.states;
	}

	function querySearchState (query) {
		return query ? self.country_states.filter( createFilterFor(query) ) : self.country_states;
	}
	function searchTextChange(text,index) {
		if( $scope['is_old_customer_'+index]){
			return;
		}
		$scope.roomListData[index].nationality=text;
		$scope.roomListData[index].state='';
		_this.searchStateText='';
		_this.selectedState='';
		$scope.searchStateText='';
		self.country_states=[];
		for( var j=0;j<$scope.countryidArr.length;j++){
			if(text.toUpperCase()===$scope.countryArr[j].toUpperCase()){
				$scope.loadState(index);
				break;
			}
		}
	}
	function searchStateTextChange(text, index) {
		if( $scope['is_old_customer_'+index]){
			return;
		}
		$scope.roomListData[index].state=text;
	}

	function selectedItemChange(item,index) {
		if( $scope['is_old_customer_'+index]){
			return;
		}
		$log.info('Item changed to ' + JSON.stringify(item));
		$scope.roomListData[index].nationality=item.value;
		$scope.roomListData[index].state='';
		_this.searchStateText='';
		_this.selectedState='';
		$scope.loadState(index); 
	}
	function selectedStateItemChange(item,index) {
		if($scope['is_old_customer_'+index]){
			return;
		}
		$scope.roomListData[index]=item.value;
	}

	*//**
	 * Build `states` list of key/value pairs
	 *//*
	function loadAll() {
		var allStates = $scope.countryArr;
		return allStates.map( function (state) {
			return {
				value: state.toLowerCase(),
				display: state
			};
		});
	}
	function loadAllStates() {
		var allStates = $scope.stateArr;
		return allStates.map( function (state) {
			return {
				value: state.toLowerCase(),
				display: state
			};
		});
	}

	*//**
	 * Create filter function for a query string
	 *//*
	function createFilterFor(query) {
		var lowercaseQuery = angular.lowercase(query);

		return function filterFn(state) {
			return (state.value.indexOf(lowercaseQuery) === 0);
		};

	}
*/


	$scope.changeNight = function(){
	
		$scope.checkin.hdr.nights = Math.ceil(Math.abs($scope.checkin.hdr.maxDepartDate.getTime() - $scope.checkin.hdr.minArrDate.getTime()) / (1000 * 3600 * 24));
		if($scope.checkin.hdr.maxDepartDate<=$scope.checkin.hdr.minArrDate){
			$scope.checkin.hdr.nights=1;
			$scope.checkin.hdr.maxDepartDate=new Date(new Date($scope.checkin.hdr.minArrDate).getFullYear(),new Date($scope.checkin.hdr.minArrDate).getMonth(),new Date($scope.checkin.hdr.minArrDate).getDate()+$scope.checkin.hdr.nights);
		}
		$scope.initial.chOutminDate=new Date(new Date($scope.checkin.hdr.minArrDate).getFullYear(),new Date($scope.checkin.hdr.minArrDate).getMonth(),new Date($scope.checkin.hdr.minArrDate).getDate()+1);
		$scope.initial.chOutmaxDate=new Date(new Date($scope.checkin.hdr.minArrDate).getFullYear(),new Date($scope.checkin.hdr.minArrDate).getMonth(),new Date($scope.checkin.hdr.minArrDate).getDate()+$scope.maxNights);
	}

	$scope.changePaymentSource = function(){
		
		$scope.disableCorporate = false;
		$scope.disableTaName = false;
		
		if($scope.checkin.hdr.payment_source == 2){
			$scope.checkin.hdr.type = "2";
			$scope.getTaCorpList();
			$scope.disableCorporate = true;
			$scope.disableTaName = false;
		}
		
		if($scope.checkin.hdr.payment_source == 1){
			$scope.checkin.hdr.type = "3";
			$scope.getTaCorpList();
			$scope.disableTaName = true;
			$scope.disableCorporate = false;
			
		}
		
		if($scope.checkin.hdr.payment_source == 0){
			$scope.checkin.hdr.type = "1";
			$scope.disableCorporate = false;
			$scope.disableTaName = false;
			$scope.disableTa = true;
			$scope.disableCorp = true;
		}
		
	}

	$scope.getTaCorpList = function(){
			$http({
				url : '../reservation_test/getTACorpList',
				method : "POST",
				data : parseInt($scope.checkin.hdr.type)-1
			}).then(function(response) {
				//$scope.trCrp.ta = {};
				//$scope.trCrp.corp = {};
				if(  $scope.checkin.hdr.type==3){
					$scope.trCrp.ta = response.data;
					$scope.trCrp.tvlcrpId = ($scope.trCrp.ta[0].id).toString();
					$scope.disableTa = false;
					$scope.disableCorp = true;
				}else if($scope.checkin.hdr.type==2){
					$scope.trCrp.corp = response.data;
					$scope.trCrp.tvlcrpId = ($scope.trCrp.corp[0].id).toString();
					$scope.disableCorp = false;
					$scope.disableTa = true;
				}else{
					$scope.disableCorp = true;
					$scope.disableTa = true;
				}
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});
	}

	$scope.checkAvailability=function(){
		
		ServiceReception.setMinArrivalDate($scope.checkin.hdr.minArrDate);
		maxRoom=$scope.checkin.hdr.numRooms;
		$scope.minArrivalDate =$filter('date')(new Date($scope.checkin.hdr.minArrDate), arrdateFormat);
		if(new Date($scope.minArrivalDate) < new Date($scope.hotelDateFormat)) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Arrival date should not be less than Hotel date").ok('Ok').parent(angular.element(document.body)));
			return;
		}		
		
		if($scope.checkin.hdr.maxDepartDate==null){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please enter depature date").ok('Ok').parent(angular.element(document.body)));
			return;
			
		}else if($scope.checkin.hdr.numRooms ==null){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please enter number of rooms").ok('Ok').parent(angular.element(document.body)));
			return;
		}else if($scope.checkin.hdr.type ==0){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please enter Type").ok('Ok').parent(angular.element(document.body)));
			return;
		}
		else if($scope.checkin.hdr.type ==2 && $scope.trCrp.tvlcrpId==0  ){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please enter Corporate Name").ok('Ok').parent(angular.element(document.body)));
			return;
		}
		else if($scope.checkin.hdr.type==3 && $scope.trCrp.tvlcrpId==0  ){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Please enter TA Name").ok('Ok').parent(angular.element(document.body)));
			return;
		}
		$scope.checkin.hdr.minArrDate = $filter('date')(new Date($scope.checkin.hdr.minArrDate), dateFormat);
		//$scope.checkin.hdr.minArrDate = new Date($scope.checkin.hdr.minArrDate);
		//$('#imgloader').show();
		var data = $.param({resvHdr:JSON.stringify($scope.checkin.hdr)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomAvailability', data, config)
		.success(function (data, status, headers, config) {
			//$('#imgloader').hide();
			$scope.data.secondLocked=false;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=true;
			$scope.showAssignedWidget=true;
			$scope.roomTypesAvailable=data;
			$scope.checkin.hdr.minArrDate=ServiceReception.getMinArrivalDate();
		}).error(function (data, status, header, config){
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	function validateEmail(emailField){

		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

		if (emailField!="" && reg.test($scope.roomListData[0].email) == false) 
		{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Invalid Email Address").ok('Ok').parent(angular.element(document.body)));	


			return false;
		}

		return true;

	}
	
	$scope.refreshEdit=function(){
		$scope.rateOccupancy1=true;
		$scope.editRateOccupancy=false;
	}
//	function myFunction(){
//		  document.getElementById('myInput').removeAttribute('readonly');
//
//	}
	
//	document.getElementById('myButton').onclick = function() {
//	    document.getElementById('myInput').removeAttribute('readonly');
//	};
	
	
	//$scope.rateOccupancy1=true;
$scope.EditRoomRate=function(){
	//$scope.editRateBox=true;
	//$scope.rateOccupancy1=false;
	//$scope.editRateOccupancy=true;
	$("#currencyid").css("display", "none");
	$("#currencyidEdited").css("display","block");
	//$scope.roomChargeEditedRate=$scope.currencyNeed;


}

$scope.clearInput=function(){
	$("#currencyid").css("display", "block");
	$("#currencyidEdited").css("display","none");
}



	$scope.showDetails = function(roomTypeCode,typeId,availRooms){
		$scope.roomTypeId=typeId;
		$scope.roomRates=[];
		$scope.roomRatesOccupancy=[];
		$scope.originalRoomRate=[];
		newAssgned=[];
		$scope.roomMax = $scope.checkin.hdr.numRooms-$scope.totalRoomsAssigned;
		$scope.availableRooms=availRooms;
		//$('#imgloader').show();
		var data = $.param({roomTypeDtls:JSON.stringify({roomType:roomTypeCode,arrDate:$filter('date')(new Date($scope.checkin.hdr.minArrDate), dateFormat),nights:$scope.checkin.hdr.nights,rate_id:0,trCrp_id:$scope.trCrp.tvlcrpId})});
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
			$scope.nextPlanIndex=0;
			var data = $.param({occupancyRateDtl:JSON.stringify({rate_id:0,arrDate:$filter('date')(new Date($scope.checkin.hdr.minArrDate), dateFormat),nights:$scope.checkin.hdr.nights,roomType:roomTypeCode})});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../reservation_test/getRoomRateForAllOccupancy', data, config)
			.success(function (data, status, headers, config) {
				
				data.occupancyRate.forEach(function(entry) {
					if(entry.occ1){entry.occ1Val=0;}
					if(entry.occ2){entry.occ2Val=0;}
					if(entry.occ3){entry.occ3Val=0;}
					if(entry.occ4){entry.occ4Val=0;}
				});
				$scope.originalRoomRate=data.roomRateActualDetail;

				$scope.roomRatesOccupancy=data.occupancyRate;
				$scope.rateIdArray=[];
				for(i=0;i<	data.occupancyRate.length;i++){
					if(i==0){
						$scope.rateIdArray.push(data.occupancyRate[i].rateId);
					}
					else{
						if(data.occupancyRate[i].rateId!=data.occupancyRate[i-1].rateId){
							$scope.rateIdArray.push(data.occupancyRate[i].rateId);
						}
					}
				}
				$scope.nextPlanIndex=data.occupancyRate.length/$scope.rateIdArray.length



				$("#assignRoomsmyModal").modal({backdrop: "static"});
				if(Object.keys($scope.assignedRoomRatesList).length!=0){
					for (var key in $scope.assignedRoomRatesList) {
						if ($scope.assignedRoomRatesList.hasOwnProperty(key)) {
							if($scope.assignedRoomRatesList[key].roomTypeId==$scope.roomTypeId){
								$scope.roomRates.forEach(function(rRate) {
									if(key.substr(0,rRate.rateCode.length)==rRate.rateCode){
										if(rRate.occ1 && $scope.assignedRoomRatesList[key].occ=='occ1'){
											rRate.occ1Val=$scope.assignedRoomRatesList[key].value;
											rRate.totalOcc1Rate=$scope.assignedRoomRatesList[key].newAmount*$scope.checkin.hdr.nights
										/*rRate.totalOcc1Rate=$scope.assignedRoomRatesList[key].newAmount;*/}
										if(rRate.occ2 && $scope.assignedRoomRatesList[key].occ=='occ2'){
											rRate.totalOcc2Rate=$scope.assignedRoomRatesList[key].newAmount*$scope.checkin.hdr.nights
											/*rRate.totalOcc2Rate=$scope.assignedRoomRatesList[key].newAmount;*/
											rRate.occ2Val=$scope.assignedRoomRatesList[key].value;}
										if(rRate.occ3 && $scope.assignedRoomRatesList[key].occ=='occ3'){
											rRate.totalOcc3Rate=$scope.assignedRoomRatesList[key].newAmount*$scope.checkin.hdr.nights
											/*rRate.totalOcc3Rate=$scope.assignedRoomRatesList[key].newAmount;*/
											rRate.occ3Val=$scope.assignedRoomRatesList[key].value;}
										if(rRate.occ4 && $scope.assignedRoomRatesList[key].occ=='occ4'){
											rRate.totalOcc4Rate=$scope.assignedRoomRatesList[key].newAmount*$scope.checkin.hdr.nights
											/*rRate.totalOcc4Rate=$scope.assignedRoomRatesList[key].newAmount;*/
											rRate.occ4Val=$scope.assignedRoomRatesList[key].value;}
									}
								});


							}
						}
					}
				}
				if(Object.keys($scope.assignedRoomRatesList).length!=0){
					for (var key in $scope.assignedRoomRatesList) {
						if ($scope.assignedRoomRatesList.hasOwnProperty(key)) {
							if($scope.assignedRoomRatesList[key].roomTypeId==$scope.roomTypeId){
								$scope.roomRates.forEach(function(rRate) {
								$scope.roomRatesOccupancy.forEach(function(rRateOccupancy) {
									if(key.substr(0,rRateOccupancy.rateCode.length)==rRateOccupancy.rateCode){
										if($scope.assignedRoomRatesList[key].occ=='occ1'){
											
											rRateOccupancy.occ1Val=$scope.assignedRoomRatesList[key].value;
											rRateOccupancy.occ1Rate=$scope.assignedRoomRatesList[key].newAmount;}
										if($scope.assignedRoomRatesList[key].occ=='occ2'){
											//rRate.totalOcc2Rate=$scope.assignedRoomRatesList[key].newAmount*$scope.checkin.hdr.nights
											rRateOccupancy.occ2Val=$scope.assignedRoomRatesList[key].value;
											rRateOccupancy.occ2Rate=$scope.assignedRoomRatesList[key].newAmount;}
										if($scope.assignedRoomRatesList[key].occ=='occ3'){
											//rRate.totalOcc3Rate=$scope.assignedRoomRatesList[key].newAmount*$scope.checkin.hdr.nights
											rRateOccupancy.occ3Val=$scope.assignedRoomRatesList[key].value;
											rRateOccupancy.occ3Rate=$scope.assignedRoomRatesList[key].newAmount;}
										if($scope.assignedRoomRatesList[key].occ=='occ4'){
											//rRate.totalOcc4Rate=$scope.assignedRoomRatesList[key].newAmount*$scope.checkin.hdr.nights
											rRateOccupancy.occ4Val=$scope.assignedRoomRatesList[key].value;
											rRateOccupancy.occ4Rate=$scope.assignedRoomRatesList[key].newAmount;}
									}
								});
								});
							}
								
						}
					}
				}


			}).error(function (data, status, header, config){
				//$('#imgloader').hide();
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});	

		}).error(function (data, status, header, config){
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});		


	}


	$scope.assignedRoomChangedAmountList=[];
	$scope.assignRooms = function(){
		//$scope.assignedRoomChangedAmountList=[];
		$(".assgnRate").each(function(){
			$scope.assignedRoomAmount={id:"",currentAmount:0}
			if($(this).attr('id') in $scope.assignedRoomChangedAmountList){
				if(this.value=="" || this.value==0){
					delete $scope.assignedRoomChangedAmountList[$(this).attr('id')];
				}
			}
			$scope.assignedRoomAmount={id:$(this).attr('id'),currentAmount:this.value};
			$scope.assignedRoomChangedAmountList[$scope.assignedRoomAmount.id]=$scope.assignedRoomAmount;
		});

		$(".assgnRoom").each(function(){
			$scope.assignedRoom={id:"",value:0,roomTypeId:"",roomTypeCode:"",rateCode:"",rateId:"",occ:"",newAmount:0,oldAmount:0}

			if($(this).attr('id') in $scope.assignedRoomRatesList){
				if(this.value=="" || this.value==0){
					delete $scope.assignedRoomRatesList[$(this).attr('id')];
				}
			}
			if(this.value!="" && this.value!=0){


				for(i=0;i<$scope.originalRoomRate.length;i++){
					if($scope.originalRoomRate[i].rateId==$(this).attr('data-rateid')){
						switch ($(this).attr('data-occ')) {
						case "occ1":
							$scope.oldAmount=$scope.originalRoomRate[i].occ1Rate;
							break;
						case "occ2":
							$scope.oldAmount=$scope.originalRoomRate[i].occ2Rate;
							break;
						case "occ3":
							$scope.oldAmount=$scope.originalRoomRate[i].occ3Rate;	
							break;
						case "occ4":
							$scope.oldAmount=$scope.originalRoomRate[i].occ4Rate;

							break;
						}

					}
				}
				$scope.assignedRoom={id:$(this).attr('id'),value:parseInt(this.value),rateCode:$(this).attr('data-rateCode'),roomTypeId:$(this).attr('data-roomtypeid'),roomTypeCode:$(this).attr('data-roomTypeCode'),rateId:$(this).attr('data-rateid'),occ:$(this).attr('data-occ'),newAmount:$scope.assignedRoomChangedAmountList[$(this).attr('id')].currentAmount,oldAmount:$scope.oldAmount};
				$scope.assignedRoomRatesList[$scope.assignedRoom.id]=$scope.assignedRoom;
			}
		});	

		$scope.AssignedRoomData = $scope.buildData();
		$("#assignRoomsmyModal").modal("toggle");
	}
	$scope.roomListData=[];
	$scope.buildData = function() {
		$scope.roomListData=[];
		var returnArr = [];
		var arr1=[];
		var arr2=[];
		$scope.totalRoomsAssigned=0;
		for (var key in $scope.assignedRoomRatesList){
			var assgnd={roomTypeId:$scope.assignedRoomRatesList[key].roomTypeId,roomTypeCode:$scope.assignedRoomRatesList[key].roomTypeCode,rateCode:$scope.assignedRoomRatesList[key].rateCode,rateId:$scope.assignedRoomRatesList[key].rateId,value:$scope.assignedRoomRatesList[key].value,currentAmount:$scope.assignedRoomRatesList[key].newAmount,oldAmount:$scope.assignedRoomRatesList[key].oldAmount};
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
			for(var i =0;i<parseInt($scope.assignedRoomRatesList[key].value);i++){
				var roomdata={roomTypeId:$scope.assignedRoomRatesList[key].roomTypeId,roomTypeCode:$scope.assignedRoomRatesList[key].roomTypeCode,rateCode:$scope.assignedRoomRatesList[key].rateCode,rateId:$scope.assignedRoomRatesList[key].rateId,occupancy:$scope.assignedRoomRatesList[key].occ,currentAmount:$scope.assignedRoomRatesList[key].newAmount,
						salutation:"Mr.",firstName:"",lastName:"",guestName:"", gender:"Male",address:"",email:"",phone:"",nationality:"India",state:"",passportNo:"",passportExpiryOn:"",remarks:"",roomNumber:"",numAdults:0,numChildren:0,numInfants:0,image:"",idproof:"",gstno:""};
				$scope.roomListData.push(roomdata);
				$scope.loadState(i,$scope.roomListData[i].nationality);
				$scope.roomListData[i].state ="Kerala";
				$scope['is_old_customer_'+i]=false;
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



	$scope.guestPage = function(){
		$scope.salutations = ["Mr.","Mrs.","Ms.","M/s.","Dr.","C/o."];
		//$scope.roomListData.salutation = "Mr.";
		if($scope.checkin.hdr.numRooms==1){
			$("#applyall").hide();
		}
		if($scope.checkin.hdr.numRooms!=$scope.totalRoomsAssigned){
			var asRooms=$scope.checkin.hdr.numRooms-$scope.totalRoomsAssigned;
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please Assign Rooms,"+asRooms+" or more rooms are pending").ok('Ok').parent(angular.element(document.body)));
		}else{

			$scope.data.secondLocked=true;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=false;
			$scope.showAssignedWidget=false;

			var newRoomRateDetailsRequestData=[];
			for(var key in $scope.assignedRoomRatesList){
				if($scope.assignedRoomRatesList[key].oldAmount!=$scope.assignedRoomRatesList[key].newAmount){
					newRoomRateDetailsRequestData.push($scope.assignedRoomRatesList[key]);
				}
			}
			var jsonData=JSON.stringify({data:newRoomRateDetailsRequestData});
			$http({
				url : '../reservation_test/getnewRoomRateDetails',
				method : "POST",
				data:jsonData
			}).then(function(response) {
				$scope.newRateIds=response.data;
				for (var key in $scope.assignedRoomRatesList){
					for(i=0;i<$scope.newRateIds.length;i++){
						if($scope.assignedRoomRatesList[key].rateId==$scope.newRateIds[i].old_id){
							$scope.assignedRoomRatesList[key].rateId=$scope.newRateIds[i].new_id;
						}
					}
				}
				for(var key in $scope.roomListData){
					for(i=0;i<$scope.newRateIds.length;i++){
						if($scope.roomListData[key].rateId==$scope.newRateIds[i].old_id){
							$scope.roomListData[key].rateId=$scope.newRateIds[i].new_id;
						}
					}
				}
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});	
			$scope.roomListData = sortByKey($scope.roomListData, "roomTypeId");
			$timeout(function () { 	 $("#sameasabove-0").hide();

			}, 1);
		}
	}


	$scope.applyall=function(index){
		for(var i=index;i<($scope.checkin.hdr.numRooms);i++){
			$scope.roomListData[i+1].salutation=$scope.roomListData[0].salutation; 
			$scope.roomListData[i+1].firstName=$scope.roomListData[0].firstName;
			$scope.roomListData[i+1].lastName=$scope.roomListData[0].lastName;
			$scope.roomListData[i+1].guestName=$scope.roomListData[0].guestName;
			$scope.roomListData[i+1].email=$scope.roomListData[0].email;
			$scope.roomListData[i+1].phone=$scope.roomListData[0].phone;
			$scope.roomListData[i+1].address=$scope.roomListData[0].address;
			$scope.roomListData[i+1].remarks=$scope.roomListData[0].remarks;
			$scope.roomListData[i+1].gender=$scope.roomListData[0].gender;
			$scope.roomListData[i+1].nationality=$scope.roomListData[0].nationality;
			$scope.stateList[i+1]=$scope.stateList[0];
			$scope.roomListData[i+1].state=$scope.roomListData[0].state;
			$scope.roomListData[i+1].gstno=$scope.roomListData[0].gstno;
			$scope.roomListData[i+1].passportNo=$scope.roomListData[0].passportNo;
			$scope.roomListData[i+1].passportExpiryOn=$scope.roomListData[0].passportExpiryOn;
			
		}
	}

	$scope.sameasabove=function(index){
		$scope.roomListData[index].salutation=$scope.roomListData[index-1].salutation;
		$scope.roomListData[index].firstName=$scope.roomListData[index-1].firstName;
		$scope.roomListData[index].lastName=$scope.roomListData[index-1].lastName;
		$scope.roomListData[index].guestName=$scope.roomListData[index-1].guestName;
		$scope.roomListData[index].email=$scope.roomListData[index-1].email;
		$scope.roomListData[index].phone=$scope.roomListData[index-1].phone;
		$scope.roomListData[index].address=$scope.roomListData[index-1].address;
		$scope.roomListData[index].remarks=$scope.roomListData[index-1].remarks;
		$scope.roomListData[index].gender=$scope.roomListData[index-1].gender;
		$scope.roomListData[index].nationality=$scope.roomListData[index-1].nationality;
		$scope.stateList[index]=$scope.stateList[index-1];
		$scope.roomListData[index].state=$scope.roomListData[index-1].state;
		$scope.roomListData[index].gstno=$scope.roomListData[index-1].gstno;
		$scope.roomListData[index].passportNo=$scope.roomListData[index-1].passportNo;
		$scope.roomListData[index].passportExpiryOn=$scope.roomListData[index-1].passportExpiryOn;

	}
	
	$scope.customerHist=function(index){
		
		/*var custFirstName,custLastName;
		$scope.roomListData=$scope.roomListData[index-1].firstName;
		$scope.roomListData=$scope.roomListData[index-1].lastName;*/
		$scope.customerName={salutation:$scope.roomListData[index].salutation,firstName:$scope.roomListData[index].firstName,lastName:$scope.roomListData[index].lastName,guestName:$scope.roomListData[index].guestName};
		
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
	
	function sortByKey(array, key) {
		return array.sort(function(a, b) {
			var x = a[key]; var y = b[key];
			return ((x < y) ? -1 : ((x > y) ? 1 : 0));
		});
	}

	$scope.roomDetails={roomNumber:0};
	$scope.selectedRoomIndex=-1;

	$scope.getVacantRooms = function(roomtype,index){	
		//$('#imgloader').show();
		var data = $.param({minArrDate:$filter('date')(new Date($scope.checkin.hdr.minArrDate), dateFormat),maxDepartDate:$filter('date')(new Date($scope.checkin.hdr.maxDepartDate), dateFormat),roomTypeId:parseInt(roomtype),occupancy:1});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reception/getAvailableRooms', data, config)
		.success(function (data, status, headers, config) {
			$scope.roomDetails.roomNumber=0;
			$scope.availableRooms = data;
			$scope.selectedRoomIndex=index;
			$("#roomAssignmyModal").modal({backdrop: "static"});
			//$('#imgloader').hide();
		}).error(function (data, status, header, config){
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}

	$scope.changeRoomStatus=function(roomId){
		 	 
		 $http({
				url : '../reception/changeRoomStatus',
				method : "POST",
				params : {id:roomId}
			}).then(function(response) {
			
			});
	 }
	
	$scope.assignRoomNumber = function(){
		$scope.roomListData[$scope.selectedRoomIndex].roomNumber=$scope.roomDetails.roomNumber;
		$scope.assignedRoomNumbers[$scope.selectedRoomIndex]=$scope.roomDetails.roomNumber;
		$("#roomAssignmyModal").modal("toggle");
	}
	$scope.checkAssigned = function(number){
		var stat=true;
		for(var key in $scope.assignedRoomNumbers){
			if($scope.assignedRoomNumbers[key]===number) {
				stat=false;
				break;
			}
		}
		return stat;
	}

	$scope.simpleSearchReception=function($index){
		$scope.phoneNum=$scope.roomListData[$index].phone;

		$scope.phone=$scope.phoneNum;
		$http({
			url : '../reception/getDetailViaPhonenum',
			method : "POST",
			data:$scope.phone

		}).then(function(response) {
			//	$scope.roomListData[0].lastName=response.data[response.data.length-1].last_name;
			if(response.data[0].salutation !== null){
				$scope.roomListData[$index].salutation=response.data[0].salutation;
			}else{
				$scope.roomListData[$index].salutation="Mr.";
			}
			if(response.data[0].last_name !== null){
				$scope.roomListData[$index].lastName=response.data[0].last_name;
			}else{
				$scope.roomListData[$index].lastName="";
			}
			if(response.data[0].guest_name !== null){
				$scope.roomListData[$index].guestName=response.data[0].guest_name;
			}else{
				$scope.roomListData[$index].guestName="";
			}
			
			$scope.roomListData[$index].firstName=response.data[0].first_name
			if(response.data[0].email !== null){
				$scope.roomListData[$index].email=response.data[0].email;
			}else{
				$scope.roomListData[$index].email="";
			}
			$scope.roomListData[$index].address=response.data[0].address
			if(response.data[0].remarks !== null){
				$scope.roomListData[$index].remarks=response.data[0].remarks
			}else{
				$scope.roomListData[$index].remarks="";
			}
			$scope.roomListData[$index].gender=response.data[0].gender
			if(response.data[0].gstno !== null){
				$scope.roomListData[$index].gstno=response.data[0].gstno
		    }else{
		    	$scope.roomListData[$index].gstno="";
		    }
			$scope.roomListData[$index].nationality=response.data[0].nationality;
			$scope.loadState($index,$scope.roomListData[$index].nationality);
			$scope.roomListData[$index].state=response.data[0].state;
			
			if(response.data[0].passport_no !== null){
				$scope.roomListData[$index].passportNo=response.data[0].passport_no;
		    }else{
		    	$scope.roomListData[$index].passportNo="";
		    }
			if(response.data[0].passport_expiry_on !== null){
				$scope.roomListData[$index].passportExpiryOn=new Date(response.data[0].passport_expiry_on);
		    }else{
		    	$scope.roomListData[$index].passportExpiryOn="";
		    }
			if(!response.data[0].first_name){
				$scope['is_old_customer_'+$index]=false;
			}else{
				$scope['is_old_customer_'+$index]=true
				$scope['last_visit_'+$index]=response.data[0].last_visit;
				$scope['no_visit_'+$index]=response.data[0].no_visit;
			
			}

		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
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


	$scope.files={proofChosen:false,proofName:"",imageUploaded:false,imageSrc:""};
	function dataURItoBlob(dataURI) {
		var byteString;
		if (dataURI.split(',')[0].indexOf('base64') >= 0)
			byteString = atob(dataURI.split(',')[1]);
		else
			byteString = unescape(dataURI.split(',')[1]);
		var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
		var ia = new Uint8Array(byteString.length);
		for (var i = 0; i < byteString.length; i++) {
			ia[i] = byteString.charCodeAt(i);
		}
		return new Blob([ia], {type:mimeString});
	}

	$scope.finalPage = function(){
		$scope.validation=true;
		
			if(validateEmail($scope.roomListData[0].email)){
	
				for(var key in $scope.roomListData){
					if($scope.roomListData[key].roomNumber==""){
						$scope.validation=false;
					}
				}
	
				if($scope.validation){
	
					$scope.getRoomRateDetails();
					$scope.loadDiscounts();
	
				}else{
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent("Please assign room number for all rooms.").ok('Ok').parent(angular.element(document.body)));
				}
			}
		
	}

	$scope.checkin_discounts=[]
	$scope.getRoomRateDetails = function(){
		//$('#imgloader').show();
		$scope.roomRateChekArgs=[];
		//var checkin_discounts={};
		for (var key in $scope.assignedRoomRatesList){
			var occ=1;
			if($scope.assignedRoomRatesList[key].occ=="occ2"){occ=2;}else if($scope.assignedRoomRatesList[key].occ=="occ3"){occ=3;}else if($scope.assignedRoomRatesList[key].occ=="occ4"){occ=4;}
			var roomRateArgs={arrDate:$filter('date')(new Date($scope.checkin.hdr.minArrDate), dateFormat),numRooms:$scope.assignedRoomRatesList[key].value,numNights:$scope.checkin.hdr.nights,rateId:$scope.assignedRoomRatesList[key].rateId,occupancy:occ};
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
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=true;
			$scope.data.fourthLocked=false;
			$scope.showAssignedWidget=false;
			//$('#imgloader').hide();
		}, function(response) {
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	$scope.loadDiscounts=function(){
		discountService.getDiscounts(assignedRateIdList).then(function(response) {
			$scope.availDiscounts = response.data;			
			//$scope.availDiscountList = response.data;	
			/*$scope.generalDiscounts=[];
			$scope.planDiscounts=[];
			for(i=0;i<$scope.availDiscountList.general.length;i++){
				$scope.genValidityFrom = new Date($scope.availDiscountList.general[i].validFrom);
				$scope.genValidityTo = new Date($scope.availDiscountList.general[i].validTo);
				var checkGenValidTo = $scope.genValidityTo>=new Date($scope.checkin.hdr.maxDepartDate);
				var checkGenValidFrom = $scope.genValidityFrom<=new Date($scope.checkin.hdr.minArrDate);
				if(checkGenValidTo && checkGenValidFrom){
					if(checkGenValidTo==true && checkGenValidFrom ==true){
						$scope.generalDiscounts.push($scope.availDiscountList.general[i]);
					}
				}
			}

			for(i=0;i<$scope.availDiscountList.plan.length;i++){
				$scope.planValidityFrom = new Date($scope.availDiscountList.plan[i].validFrom);
				$scope.planValidityTo = new Date($scope.availDiscountList.plan[i].validTo);
				var checkPlanValidTo = $scope.planValidityTo>=new Date($scope.checkin.hdr.maxDepartDate);
				var checkPlanValidFrom = $scope.planValidityFrom<=new Date($scope.checkin.hdr.minArrDate);
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
		for(var a in $scope.availDiscounts.plan){
			$(".chk_"+$scope.availDiscounts.plan[a].id).prop("checked",false);

		}


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


					$(".chk_"+$scope.discApplied.value.discId[p]).prop("checked",true);

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

		$scope.roomRateChekArgs=[];
		
		for (var key in $scope.assignedRoomRatesList){
			var occ=1;
			if($scope.assignedRoomRatesList[key].occ=="occ2"){occ=2;}else if($scope.assignedRoomRatesList[key].occ=="occ3"){occ=3;}else if($scope.assignedRoomRatesList[key].occ=="occ4"){occ=4;}
			var roomRateArgs={arrDate:$filter('date')(new Date($scope.checkin.hdr.minArrDate), dateFormat),numRooms:$scope.assignedRoomRatesList[key].value,numNights:$scope.checkin.hdr.nights,rateId:$scope.assignedRoomRatesList[key].rateId,occupancy:occ};		
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
			$scope.data.secondLocked=true;
			$scope.data.firstLocked=true;
			$scope.data.thirdLocked=true;
			$scope.data.fourthLocked=false;
			$scope.showAssignedWidget=false;
			//$('#imgloader').hide();
		}, function(response) {
			//$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
		
		
		
		
		
	}
	
	
	
	$scope.discDispList=[];
	$scope.applyDiscount =function(){
		if(('generalDisc' in $scope.disc) || (!angular.equals({},$scope.disc.planBased))){
		//	$scope.discDispList=[];
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
				
			}
			if($scope.discDispList[$scope.discDispList.length-1].discountFor == 1){
				$scope.getRoomRateDetails();
			}
			
			$("#availDiscountsmyModal").modal("toggle");	
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("No Discount selected").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));	
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
	}




	var fileData = new FormData();
	$scope.fileupload=false;
	$scope.roomNumbers=[];
	$scope.saveReception = function(){
		
		var roomdis = $scope.discDispList.filter(a => a.discountFor === 1);
		var fooddis = $scope.discDispList.filter(a => a.discountFor === 2);
		if($scope.discDispList.length>2 || roomdis.length >1|| fooddis.legth>1){
			//alert("only possible to select 2 different type discount");
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('').textContent("Only Possible to Select 2 Different Type Discount.").ok('Ok').parent(angular.element(document.body)));
			return ;
		}
		
		$scope.submit_click=true;
		fileData = new FormData();
		$scope.imgs={}
		for(var key in $scope.roomListData){

			if($scope.roomListData[key].occupancy=="occ1"){$scope.roomListData[key].occupancy=1;}else if($scope.roomListData[key].occupancy=="occ2"){$scope.roomListData[key].occupancy=2;}else if($scope.roomListData[key].occupancy=="occ3"){$scope.roomListData[key].occupancy=3;}else if($scope.roomListData[key].occupancy=="occ4"){$scope.roomListData[key].occupancy=4;}
			if($scope.roomListData[key].hasOwnProperty("image")){
				if($scope.roomListData[key].image!=""){
					$scope.fileupload=true;
					fileData.append("imageFile",dataURItoBlob($scope.roomListData[key].image),$scope.roomListData[key].roomNumber);
				}
			}
			if($scope.roomListData[key].hasOwnProperty("idproof")){
				if($scope.roomListData[key].idproof!=""){
					var ext=$scope.roomListData[key].idproof.name.substring($scope.roomListData[key].idproof.name.lastIndexOf('.')+1);
					fileData.append("idproof",$scope.roomListData[key].idproof,$scope.roomListData[key].roomNumber+"."+ext);
					$scope.fileupload=true;
				}
			}

			delete $scope.roomListData[key].image;
			delete $scope.roomListData[key].idproof;
			for(var ckey in $scope.roomRateChekArgs){
				if(($scope.roomRateChekArgs[ckey].rateId==$scope.roomListData[key].rateId) && ($scope.roomRateChekArgs[ckey].occupancy==$scope.roomListData[key].occupancy)){
					if($scope.roomRateChekArgs[ckey].hasOwnProperty('discId')){
						$scope.roomListData[key].discId=$scope.roomRateChekArgs[ckey].discId;
					}
					if($scope.roomRateChekArgs[ckey].hasOwnProperty('openDiscount')){
						$scope.roomListData[key].openDiscount=$scope.roomRateChekArgs[ckey].openDiscount;
					}
				}
			}
			if($scope.roomListData[key].passportExpiryOn!=""){
				$scope.roomListData[key].passportExpiryOn=$filter('date')(new Date($scope.roomListData[key].passportExpiryOn), dateFormat);
			}
			if(!($scope.roomListData[key].guestName)){
				$scope.roomListData[key].guestName =""
			}

		}
		$scope.saveData={checkinHdr:$scope.checkin.hdr,checkinDtl:$scope.roomListData};
		/*for(var j=0;j<$scope.saveData.checkinDtl.length;j++){
			if(!($scope.saveData.checkinDtl[j].state.value)){
				
			}else{
				$scope.saveData.checkinDtl[j].state=$scope.saveData.checkinDtl[j].state.value;
			}
		}*/
		$scope.saveData.checkinHdr.maxDepartDate=$filter('date')(new Date($scope.saveData.checkinHdr.maxDepartDate), dateFormat);
		$scope.saveData.checkinHdr.minArrDate=$filter('date')(new Date($scope.saveData.checkinHdr.minArrDate), dateFormat);		
		$scope.saveData.checkinHdr.resvType=$scope.checkin.hdr.type;
		$scope.saveData.checkinHdr.corporateId=$scope.trCrp.tvlcrpId;
		$scope.saveData.checkinDiscnt=$scope.checkin_discounts;
		if(!angular.equals({},$scope.discApplied)){
			if($scope.discApplied.group=='general'){
				$scope.saveData.checkinHdr.discType=2;
			}else{
				$scope.saveData.checkinHdr.discType=1;
			}
		}
		//$('#imgloader').show();		
		$http.post("../reception/saveNewCheckin", JSON.stringify($scope.saveData))
		.then(function(response){
			$scope.checkInIds=[];
			var msg = response.data.substring(0).split("_");
			if(msg[0]=="success"){
				if($scope.fileupload){
					$scope.fileUpload();
				}else{	
					//$('#imgloader').hide();
					var confirm = $mdDialog.alert()
					.title("Checkin completed successfully").ok('OK').parent(angular.element(document.body));
					$mdDialog.show(confirm).then(function(){
						window.location  = "../reception/receptionList";
						for(i=1;i<=msg.length-1;i++){
							$scope.checkInIds.push(msg[i]);
						}
						$http({
							url: '../reception/mailCheckInSave',
							method: 'POST',
							data:{data:$scope.checkInIds}
						}).then(function(response){
							$mdDialog.show(confirm).then(function(){
								window.location  = "../reception/receptionList";
							});
						})
					})
				}
			}
		}).error(function(data, status, headers, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});

	}

	$scope.fileUpload = function(){
		$http.post("../reception/uploadFile",fileData,{
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			}
		}).success(function(data) {
			if(data.substring(7)=='success'){
				//$('#imgloader').hide();
				var confirm = $mdDialog.confirm()
				.title("Checkin Completed Successfully").ok('OK').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function(){
					window.location  = "../reception/receptionList";
				});
			}
		}).error(function(data) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});		
	}


	$scope.cancelRoomAssignPopUp = function(){
		$("#assignRoomsmyModal").modal("toggle");
	}
	$scope.cancelDiscPopUp  = function(){
		$("#availDiscountsmyModal").modal("toggle");
	}

	//webcam
	var _video = null,
	patData = null;
	$scope.patOpts = {x: 0, y: 0, w: 25, h: 25};
	$scope.channel = {};
	$scope.webcamError = false;
	$scope.onError = function (err) {$scope.$apply(function() {$scope.webcamError = err;});};
	$scope.onSuccess = function () {
		_video = $scope.channel.video;
		$scope.$apply(function() {
			$scope.patOpts.w = _video.width;
			$scope.patOpts.h = _video.height;
			$("#weberror").hide();

		});
	};

	$scope.onStream = function (stream) {//You could do something manually with the stream.
	};

	$scope.makeSnapshot = function() {
		$scope.divCapture=false;
		$("#divCapture_"+$scope.snp_id).addClass('disp_capt');
		if (_video) {
			var patCanvas = document.querySelector('#snapshot_'+$scope.snp_id);
			if (!patCanvas) return;
			patCanvas.width = _video.width;
			patCanvas.height = _video.height;
			var ctxPat = patCanvas.getContext('2d');
			var idata = getVideoData($scope.patOpts.x, $scope.patOpts.y, $scope.patOpts.w, $scope.patOpts.h);
			ctxPat.putImageData(idata, 0, 0);
			sendSnapshotToServer(patCanvas.toDataURL());
			patData = idata;
			$scope.files.imageUploaded=false;
			$("#snapshot_"+$scope.snp_id).css('display','block');
		}
	};

	/**
	 * Redirect the browser to the URL given.
	 * Used to download the image by passing a dataURL string
	 */
	$scope.downloadSnapshot = function downloadSnapshot(dataURL) {
		window.location.href = dataURL;
	};

	var getVideoData = function getVideoData(x, y, w, h) {
		var hiddenCanvas = document.createElement('canvas');
		hiddenCanvas.width = _video.width;
		hiddenCanvas.height = _video.height;
		var ctx = hiddenCanvas.getContext('2d');
		ctx.drawImage(_video, 0, 0, _video.width, _video.height);
		return ctx.getImageData(x, y, w, h);
	};

	/**
	 * This function could be used to send the image data
	 * to a backend server that expects base64 encoded images.
	 * In this example, we simply store it in the scope for display.
	 */
	var sendSnapshotToServer = function sendSnapshotToServer(imgBase64) {
		$scope.roomListData[$scope.snp_id].image = imgBase64;
	};
	$scope.divCapture=false;
	$scope.openCaptureDiv = function(id){
		if($scope.divCapture==true){
			$scope.divCapture=false;
		}else{$scope.divCapture=true;}
		$scope.snp_id=id;
		$(".captDiv").addClass('show_capt');
		$("#divCapture_"+id).removeClass('disp_capt');
	}


	$scope.showCalender= function(){
		$("#calendermyModal").modal({backdrop: "static"});	
		$rootScope.$emit("loadCalender",$scope.checkin.hdr.minArrDate);
	}
	$scope.cancelRoomNumberPopUp = function(){
		$("#roomAssignmyModal").modal("toggle");
	}

	$scope.cancelCalenderPopUp = function(){
		$("#calendermyModal").modal("toggle");
	}
	
	$scope.loadState = function(index,nationality){
		//if(!$scope['is_old_customer_'+index]){
			$scope.roomListData[index].state=""
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
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	
	}
	$http({
		url:'../checkIn/getCustomers',
		method:'POST'
	}).then(function(response){
		$scope.customerList = response.data;
		for(i=0;i<$scope.customerList.length;i++){
			$scope.customerArr.push({id:$scope.customerList[i].id,name:$scope.customerList[i].name});
			self.customers        = loadAllCustomers();
			self.queryCustomerSearch   = queryCustomerSearch;
			self.selectedCustomerItemChange = selectedCustomerItemChange;
			self.searchCustomerTextChange   = searchCustomerTextChange;

		}
	});
	
	function loadAllCustomers() {
		var allCustomer = $scope.customerArr;
		return allCustomer.map( function (customer) {
			return {
				value: customer.id,
				display: customer.name.toLowerCase()
			};
		});
	}
	
	function queryCustomerSearch (query) {
		return query ? self.customers.filter( createFilterForCustomers(query) ) : self.customers;
	}
	
	function searchCustomerTextChange(text,index) {
	    $scope['is_old_customer_'+index]=false;
	    $scope.roomListData[index].firstName=text;
		$scope['last_visit_'+index]="";
		$scope['no_visit_'+index]="";
	}
	
	
	function selectedCustomerItemChange(item,index) {
		 $scope.loadCustomerData(item,index);
		
	}
	
	 function createFilterForCustomers(query) {
         var lowercaseQuery = angular.lowercase(query);
         return function filterFn(customers) {
            return (customers.display.indexOf(lowercaseQuery) === 0);
         };
      }
 
	 $scope.loadCustomerData=function(index){
				var data = $.param({data:$scope.roomListData[index].firstName});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../checkIn/loadData', data, config)
				.success(function (data, status, headers, config) {
					$scope.customerIndex=index;
					$scope.customerList = data;
					if($scope.customerList.length >1){
						$('#myModal').modal('show');
					}else if($scope.customerList.length ==1) {
						$scope.copyData($scope.customerList[0],index)
					}else{
						$scope['is_old_customer_'+index]=false;
						$scope.roomListData[index].lastName="";
						$scope.roomListData[index].guestName="";
						$scope.roomListData[index].email="";
						$scope.roomListData[index].passportNo="";
						$scope.roomListData[index].passportExpiryOn="";
						$scope.roomListData[index].gstno="";
						$scope.roomListData[index].phone="";
						$scope.roomListData[index].address="";
						$scope.roomListData[index].nationality="";
						$scope.roomListData[index].state="";
					}
				}).error(function (data, status, header, config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
				});

		}
	 $scope.copyData= function(data,index){
		 if(!data){
			 
		 }else{
			 $scope['is_old_customer_'+index]=true;
		 }
		 if(data.salutation != null){
				$scope.roomListData[index].salutation=data.salutation;
			}
			if(data.last_name !== null){
				$scope.roomListData[index].lastName=data.last_name;
		    }else{
		    	$scope.roomListData[index].lastName="";
		    }
			if(data.guest_name !== null){
				$scope.roomListData[index].guestName=data.guest_name;
		    }else{
		    	$scope.roomListData[index].guestName="";
		    }
			if(data.mail !== null){
				$scope.roomListData[index].email=data.mail;
		    }else{
		    	$scope.roomListData[index].email="";
		    }
			if(data.passport_no !== null){
				$scope.roomListData[index].passportNo=data.passport_no;
		    }else{
		    	$scope.roomListData[index].passportNo="";
		    }
			if(data.passport_expiry_on !== null){
				$scope.roomListData[index].passportExpiryOn=new Date(data.passport_expiry_on);
		    }else{
		    	$scope.roomListData[index].passportExpiryOn="";
		    }
			if(data.gstno !== null){
				$scope.roomListData[index].gstno=data.gstno;
		    }else{
		    	$scope.roomListData[index].gstno="";
		    }
			$scope.roomListData[index].firstName=data.first_name;
			$scope.roomListData[index].gender=data.gender
			$scope.roomListData[index].phone=data.phone;
			$scope.roomListData[index].address=data.address;
			$scope.roomListData[index].nationality=data.nationality;
			$scope.loadState(index,$scope.roomListData[index].nationality);
			$scope.roomListData[index].state=data.state;
		
			$scope['last_visit_'+index]=data.last_visit;
			$scope['no_visit_'+index]=data.no_visit;
			$('#myModal').modal('hide');
	 }
	 $scope.is_old_customer = function(index){
		 $("#last_visit_"+index).text($scope['last_visit_'+index]);
		 $("#no_visit_"+index).text($scope['no_visit_'+index]);
		 return $scope['is_old_customer_'+index] ;
	 }
	 
	 $scope.simpleSearchByMail=function(index){
		 	var data = $.param({data:$scope.roomListData[index].email});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../checkIn/loadDataByMail', data, config)
			.success(function (data, status, headers, config) {	
				if(!data.first_name){
					$scope['is_old_customer_'+index]=false
					
				}else{
					$scope['is_old_customer_'+index]=true;
				}
				if(data.salutation != null){
					$scope.roomListData[index].salutation=data.salutation;
				}
				if(!data.last_name){
			    	$scope.roomListData[index].lastName="";
			    }else{
					$scope.roomListData[index].lastName=data.last_name;
			    }
				if(!data.guest_name){
			    	$scope.roomListData[index].guestName="";
			    }else{
					$scope.roomListData[index].guestName=data.guest_name;
			    }
				
				if(!data.passport_no){
			    	$scope.roomListData[index].passportNo="";
			    }else{
					$scope.roomListData[index].passportNo=data.passport_no;
			    }
				
				if(!data.passport_expiry_on){
			    	$scope.roomListData[index].passportExpiryOn="";

			    }else{
					$scope.roomListData[index].passportExpiryOn=new Date(data.passport_expiry_on);
			    }
				if(!data.gstno){
			    	$scope.roomListData[index].gstno="";
			    }else{
					$scope.roomListData[index].gstno=data.gstno;
			    }
				$scope.roomListData[index].firstName=data.first_name;
				$scope.roomListData[index].gender=data.gender
				$scope.roomListData[index].phone=data.phone;
				$scope.roomListData[index].address=data.address;
				$scope.roomListData[index].nationality=data.nationality;
				$scope.loadState(index,$scope.roomListData[index].nationality);
				$scope.roomListData[index].state=data.state;
				
				$scope['last_visit_'+index]=data.last_visit;
				$scope['no_visit_'+index]=data.no_visit;
				
				
			}).error(function (data, status, header, config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});

		}

	/*$scope.resvTypeChange = function(){
		if($scope.checkin.hdr.type == ''){
			
		}
	}*/
	 $scope.showTooltip = false;
	 $scope.showCountryError = false;
	 $scope.showStateError = false;
	 $scope.showAdultError = false;
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
		if($scope.roomListData[0].numAdults == "" || $scope.roomListData[0].numAdults == undefined){
			$scope.roomListData[0].numAdults = 1;
		}
	 }
}]);
pmsApp.controller('calCtrl', ['$scope','$http','$window','$mdDialog','$rootScope','$filter',function ($scope, $http,$window,$mdDialog,$rootScope,$filter){
	var hdate=$("#hotelDate").val();
	var dateFormat = "yyyy-MM-dd";
	var hotalDateFormat = "mm/dd/yyyy"
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
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
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
		$scope.startDate = $scope.startDate=$filter('date')(new Date(stDate), dateFormat);
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
				if (parseInt(this.value)>limit || totalAssigned>limit || (parseInt(this.value)+assignedPerRoomType)>limit  || totalAssigned>scope.roomMax || (scope.totalRoomsAssigned+parseInt(this.value))>scope.checkin.hdr.numRooms){
//					if(scope.roomMax<totalAssigned && scope.roomMax>=limit){
//					this.value=0;
//					newAssgned[this.id]=0;


//					}else if(scope.roomMax>limit){
//					this.value=limit;
//					newAssgned[this.id]=limit;
//					}else if(scope.roomMax<limit){
//					this.value=scope.roomMax;
//					newAssgned[this.id]=scope.roomMax;

//					}else{
					this.value=0;
					newAssgned[this.id]=0;
//					}
					e.preventDefault();
				}
			});
		}
	}
	
}]);

pmsApp.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function(){
				scope.$apply(function(){
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);
//$(document).ready(function(){
//$(".md-datepicker-input").mouseover(function(){
//$('.md-datepicker-input').attr('readonly', true);
//});
//});
//var newRateChange=0
//$(document).ready(function(){ 

//$( "body" ).on( "keyup", '.occ1', function() {
//var newRateChange=$(this).val();
//$('.occ1').val(newRateChange);

//});
//});

pmsApp.directive('onlyDigits', function () {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function (scope, element, attr, ctrl) {
          function inputValue(val) {
            if (val) {
              var digits = val.replace(/[^0-9]/g, '');

              if (digits !== val) {
                ctrl.$setViewValue(digits);
                ctrl.$render();
              }
              return parseInt(digits,10);
            }
            return undefined;
          }            
          ctrl.$parsers.push(inputValue);
        }
      };
  });
