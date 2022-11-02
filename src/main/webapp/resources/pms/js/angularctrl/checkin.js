pmsApp.requires.push('webcam');
$(document).ready(function(){
	  $(".button").click(function(){
	    $("#state_error,#country_error").fadeIn("slow");
	  });
	});


pmsApp.controller('checkInCtrl', ['$compile','$scope','$q','$http','$rootScope','$window','$mdDialog',function ($compile,$scope,$q,$http,$rootScope,$window,$mdDialog){
	var self = this;
    self.simulateQuery = false;
    self.isDisabled    = false;
	$scope.shiftCheck=$("#shiftCount").val();
	$scope.checkinData=[];
	$scope.showDetail=false;
	$scope.multipleType = 1;
	var chin=this;
	dateFormat=convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	var assignedRooms=[];
	$scope.files={proofChosen:false,proofName:"",imageUploaded:false,imageSrc:""};
	$scope.hstep = 1;
	$scope.mstep = 1;
	$scope.ismeridian=true;
	var imgCount=0;
	$scope.selected={};
	$scope.roomSelected={};
	$scope.roomNumberArray = [];
	$scope.addons={};
	$scope.rmDtls={};
	$scope.rmStatus=[];
	$scope.selectedBaground = [];
	$scope.facilitySelected={};
	$scope.checkinSubmit=false;
	$scope.countryArr=[];
	$scope.salutations = ["Mr.","Mrs.","Ms.","M/s.","Dr.","C/o."];
	$scope.loadCheckinData = function(){
	$http({
		url : "../checkIn/getCheckInData?resvId="+window.resvNo,
		method : "POST",
		params:{reservationNo:$scope.reservationNo}
	}).then(function(response) {
		$scope.checkinData=response.data;
		 $scope.buildGridModel({
	        background: ""
	      });
	},function(response) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});
	}
	
	$scope.loadCheckinData();
 $scope.buildGridModel = function(tileTmpl){
  var it;
  var j=0;
  $scope.checkinData.forEach(function(chData){
	  $scope.rmDtls[chData.resv_room_no]=chData;
    it = angular.extend({},tileTmpl);
    it.span  = { row : 2, col : 2 };
      it.background = "rgb(62, 103, 123)";
      chData.tile=it;
    j=j+1;
  });
}
 
 $scope.changeRoomStatus=function(roomId){
 	 
	 $http({
			url : '../checkIn/changeRoomStatus',
			method : "POST",
			params : {id:roomId}
		}).then(function(response) {
		
		});
 }
 $scope.showDetails = function(chin){
	 $('#imgloader').show();
	 if(!$scope.selectedBaground.includes(chin.resv_room_no))
		 {
		 $scope.selectedBaground.push(chin.resv_room_no);
		 
		 }
	 else{
		 //pop that number
		var i =  $scope.selectedBaground.indexOf(chin.resv_room_no);
		 if(i >= 0) {
			 $scope.selectedBaground.splice(i,1);
		 }
		 
	 }
	 $scope.countnochecked =0;
	 $scope.reserv_roomNo_array =[];
	 $scope.roomTypeArray=[];
	 $http({
			url : "../checkIn/getReservationRateDetails",
			method : "POST",
			params:{resvRoomNo:chin.resv_room_no}
		}).then(function(response) {
			$scope.customerData={};
			//if(Object.keys($scope.selected).length)
			$scope.checkinData.forEach(function(chData){
				
				if(chData.resv_room_no==chin.resv_room_no){
					 $scope.customerData=chData;
				}
				 if( $scope.selectedBaground.includes(chData.resv_room_no)){
					 chData.tile.background ="rgb(4, 138, 187)";
				 }else{
					 chData.tile.background = "rgb(62, 103, 123)";
				 } 
				
				
					 
					 
			 
			 });
			
			$scope.detailedRateSummary=response.data;
			var rateDtlArr=[];
			var arrayRate=[];
			var subtotal=0.0,taxTotal=0.0,discTotal=0.0,sTaxTotal=0.0,sChargeTotal=0.0,grantTotal=0.0,subtotalCharges=0.0,taxPc=0.0,discTax=0.0,discTaxTotal=0.0;
			var rateDtlObj={roomType:$scope.detailedRateSummary.roomTypeCode,total:$scope.detailedRateSummary.totalRate,numRooms:$scope.detailedRateSummary.numRooms,numNights:$scope.detailedRateSummary.numNights};
			taxPc=($scope.detailedRateSummary.ratePerOcc[0].tax1_pc+$scope.detailedRateSummary.ratePerOcc[0].tax2_pc+$scope.detailedRateSummary.ratePerOcc[0].tax3_pc+$scope.detailedRateSummary.ratePerOcc[0].tax4_pc);
			discTotal=$scope.detailedRateSummary.totalDisc;
			subtotalCharges=$scope.detailedRateSummary.totalRate;
			discTax=((taxPc/100)*$scope.detailedRateSummary.totalDisc);
			discTaxTotal=discTaxTotal+discTax;
			taxTotal=$scope.detailedRateSummary.totalTax-discTax;
			sTaxTotal=$scope.detailedRateSummary.totalSTax;
			sChargeTotal=$scope.detailedRateSummary.totalSCharge;
			grantTotal=subtotalCharges-discTotal-discTaxTotal+sTaxTotal;
			subtotal=grantTotal-taxTotal+discTotal;
	
		$scope.rateSummary={subTotal:Math.round(subtotal * 100) / 100,taxTotal:Math.round(taxTotal * 100) / 100,discTotal:Math.round(discTotal * 100) / 100,sTaxTotal:Math.round(sTaxTotal * 100) / 100,totalSCharge:Math.round(sChargeTotal * 100) / 100,grantTotal:Math.round(grantTotal * 100) / 100,rateArray:rateDtlArr};
		
		
		var selectedid = 0;
		
		for(var i = 0;i<$scope.selectedBaground.length;i++){
			selectedid = $scope.selectedBaground[i];
			$scope.reserv_roomNo_array.push(parseInt($scope.selectedBaground[i]));
		}
		$scope.countnochecked = $scope.reserv_roomNo_array.length;
		/*var selectedid = 0;
		for (var id in $scope.selected) {
			if ($scope.selected[id]) {
				$scope.countnochecked = $scope.countnochecked +1;
				selectedid = id;
				 $scope.reserv_roomNo_array.push(parseInt(id));
			}
		}*/
		$scope.checkinData.forEach(function(chData){
			if( $scope.reserv_roomNo_array.includes(chData.resv_room_no) && !$scope.roomTypeArray.includes(chData.room_type_id)){
				 $scope.roomTypeArray.push(chData.room_type_id);
			}
			 
			 });

			if($scope.countnochecked  == 1)
			{
				$scope.multipleType = 0;
					$scope.showDetail = true;
					$scope.checkinData.forEach(function(chData){
						 if(chData.resv_room_no==selectedid){
							 $scope.customerData=chData;
						 }
						 
						 });
			}
			else{
				 $scope.showDetail=false;
				 if($scope.roomTypeArray.length >1){
					 $scope.multipleType = 1;
				 }
				 else{
					 $scope.multipleType = 0;
				 }
			}
		
		 $('#imgloader').hide();
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	 
 }
 
 
 
	/*
	 * function querySearch (query) { return query ? self.states.filter(
	 * createFilterFor(query) ) : self.states; }
	 * 
	 * function searchTextChange(text) { $scope.TextSubject=text;
	 * $scope.roomDetails.nationality=$scope.TextSubject; }
	 * 
	 * function selectedItemChange(item) { $scope.nation=item.value;
	 * $scope.roomDetails.nationality=$scope.nation; }
	 * 
	 *//**
	 * Build `states` list of key/value pairs
	 *//*
	 * function loadAll() { var allStates = $scope.countryArr; return
	 * allStates.map( function (state) { return { value: state.toLowerCase(),
	 * display: state }; }); }
	 * 
	 *//**
	 * Create filter function for a query string
	 *//*
	 * function createFilterFor(query) { var lowercaseQuery =
	 * angular.lowercase(query);
	 * 
	 * return function filterFn(state) { return
	 * (state.value.indexOf(lowercaseQuery) === 0); };
	 *  }
	 */
 
 
 
 
 
 
 
 
 
 $scope.checkAssigned=function(number){
	 var stat=true;
	 
	 $scope.assignedRoomNumbers=$scope.roomDetails.roomNumber;
	 for(var key in $scope.assignedRoomNumbers){
			if($scope.assignedRoomNumbers[key]==number) {
				stat=false;
				break;
			}
		}

	 
	 return stat;
	 
 }
 $scope.countRoomCheck = function(roomNumber){
	 
	 
	 var countroom = 0;
	
	 for (var id in $scope.roomSelected) {
			if ($scope.roomSelected[id]) {
				countroom = countroom + 1;
			}
		}
	 if(countroom > $scope.countnochecked){
		alert("Maximum Room selection Reached");
		$scope.roomSelected[roomNumber] = false;
		
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
 

 $scope.editCheckin= function(aData){
	$scope.selectedIndex = 1;
	 if(aData == undefined){
		 return;
		 
	 }
	 else if($scope.countnochecked  == 0){
		 return;
	 }
	 else if($scope.countnochecked  >1){
		 $scope.disbleforMultiple=true;
		 	$('#imgloader').show();
		 	var data = $.param({resvRoomNo:aData.resv_room_no,minArrDate:new Date(aData.min_arr_date).getTime(),maxDepartDate:new Date(aData.max_depart_date).getTime(),roomTypeId:$scope.roomTypeArray[0],occupancy:aData.occupancy});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../checkIn/getCheckInDetails', data, config)
			.success(function (data, status, headers, config) {
				$scope.facilitySelected={};
				imgCount= imgCount+1;
				$scope.files={proofChosen:false,proofName:"",imageUploaded:false,imageSrc:""};
				$("#snapshot").css('display','none');
				$('#imgloader').hide();
				$scope.roomDetails={salutation:"Mr.",firstName:"",lastName:"",guestName:"",email:"",phone:"",address:"",nationality:"",state:"",passportNo:"",gender:"Male",passportExpiryOn:null,gstno:"",remarks:"",roomNumber:"",numAdults:1,numChildren:0,numInfants:0};
				var rsvRoom = JSON.parse(data.roomData);
				$scope.roomDetails=JSON.parse(data.roomData);
				$scope.loadState($scope.roomDetails.nationality);
				
				if($scope.roomDetails.salutation == '' || !$scope.roomDetails.salutation){
					$scope.roomDetails.salutation = "Mr.";
				}
				if($scope.roomDetails.hasOwnProperty('passportExpiryOn') && $scope.roomDetails.passportExpiryOn!=null){
					$scope.roomDetails.passportExpiryOn=new Date($scope.roomDetails.passportExpiryOn);
				}
				if($scope.roomDetails.numAdults==0){
					$scope.roomDetails.numAdults=1;
				}
				if($scope.roomDetails.gender == '' || !$scope.roomDetails.gender){
					$scope.roomDetails.gender= "Male";
				}

				if($scope.roomDetails.hasOwnProperty('customerIdProof') && $scope.roomDetails.customerIdProof!=""){
					$scope.files.proofChosen=true;
					$scope.files.proofName=$scope.roomDetails.customerIdProof.substring(25);
				}
				if($scope.roomDetails.hasOwnProperty('customerImage') && $scope.roomDetails.customerIdProof!=""){
					$scope.files.imageUploaded=true;
					$scope.files.imageSrc=$scope.roomDetails.customerImage+"?"+imgCount.toString();
				}
				
				$scope.availableRooms=data.availableRooms;
				$scope.addons = $scope.roomDetails.checkinRequest;
				$scope.loadProviders();
				$scope.loadFacilities();
				if($scope.availableRooms.length!=0){
					$scope.roomTypeCode="Type:"+ $scope.availableRooms[0].roomType.code;
					$scope.roomTypeDtls=$scope.availableRooms[0].roomType;
				}else{
					$scope.roomTypeCode="No Rooms Available";
				}
				$("#roomEditModal").modal({backdrop: "static"});
			})
			.error(function (data, status, header, config){
				$('#imgloader').hide();
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});
			
	 }
	 
	 else if(aData.status!=5 && aData.status!=7){
		 $scope.disbleforMultiple=false;
			$scope.occMax=aData.occupancy;
			$scope.min_arr_date=aData.min_arr_date;
			$scope.max_depart_date=aData.max_depart_date;
			$scope.room_type_code=aData.room_type_code;
			$scope.room_type_id=aData.room_type_id;
			$scope.occupancy=aData.occupancy;
			$('#imgloader').show();
			var data = $.param({resvRoomNo:aData.resv_room_no,minArrDate:new Date(aData.min_arr_date).getTime(),maxDepartDate:new Date(aData.max_depart_date).getTime(),roomTypeId:aData.room_type_id,occupancy:aData.occupancy});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../checkIn/getCheckInDetails', data, config)
			.success(function (data, status, headers, config) {
				
				$scope.facilitySelected={};
				imgCount= imgCount+1;
				$scope.files={proofChosen:false,proofName:"",imageUploaded:false,imageSrc:""};
				$("#snapshot").css('display','none');
				$('#imgloader').hide();
				$scope.roomDetails={salutation:"Mr.",firstName:"",lastName:"",guestName:"",email:"",phone:"",address:"",nationality:"",state:"",passportNo:"",gender:"Male",passportExpiryOn:null,gstno:"",remarks:"",roomNumber:"",numAdults:1,numChildren:0,numInfants:0};
				var rsvRoom = JSON.parse(data.roomData);
				$scope.roomDetails=JSON.parse(data.roomData);
				if($scope.roomDetails.nationality == '' || !$scope.roomDetails.nationality){
					$scope.roomDetails.nationality = "India";
				}
				$scope.loadState($scope.roomDetails.nationality);
				
				if($scope.roomDetails.state == '' || !$scope.roomDetails.state){
					$scope.roomDetails.state = "Kerala";
				}
				if($scope.roomDetails.salutation == '' || !$scope.roomDetails.salutation){
					$scope.roomDetails.salutation = "Mr.";
				}
			
				if($scope.roomDetails.hasOwnProperty('passportExpiryOn') && $scope.roomDetails.passportExpiryOn!=null){
					$scope.roomDetails.passportExpiryOn=new Date($scope.roomDetails.passportExpiryOn);
				}
				if($scope.roomDetails.numAdults==0){
					$scope.roomDetails.numAdults=1;
				}
				if($scope.roomDetails.gender == '' || !$scope.roomDetails.gender){
					$scope.roomDetails.gender= "Male";
				}

				if($scope.roomDetails.hasOwnProperty('customerIdProof') && $scope.roomDetails.customerIdProof!=""){
					$scope.files.proofChosen=true;
					$scope.files.proofName=$scope.roomDetails.customerIdProof.substring(25);
				}
				if($scope.roomDetails.hasOwnProperty('customerImage') && $scope.roomDetails.customerIdProof!=""){
					$scope.files.imageUploaded=true;
					$scope.files.imageSrc=$scope.roomDetails.customerImage+"?"+imgCount.toString();
				}
				/*
				 * if(!($scope.roomDetails.hasOwnProperty('gender'))){
				 * $scope.roomDetails.gender="Male"; }
				 */
				$scope.availableRooms=data.availableRooms;
				$scope.addons = $scope.roomDetails.checkinRequest;
				$scope.loadProviders();
				$scope.loadFacilities();
				if($scope.availableRooms.length!=0){
					$scope.roomTypeCode="Type:"+ $scope.availableRooms[0].roomType.code;
					$scope.roomTypeDtls=$scope.availableRooms[0].roomType;
				}else{
					$scope.roomTypeCode="No Rooms Available";
				}
				$("#roomEditModal").modal({backdrop: "static"});

			}).error(function (data, status, header, config){
				$('#imgloader').hide();
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});
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
							$scope.facilitySelected[addon.facilityId]=true;
							facility.need="daily";
							if(addon.oneTimeReq == 1){
								facility.need='ondate';
							}
							facility.needDate=new Date(addon.reqDate);
							facility.needTime=new Date(addon.reqTime);
							facility.provider=addon.arrangementBy;
							facility.remarks=addon.reqRemarks;
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

	$scope.updateRoomData = function(){	
		
		if($scope.countnochecked>1){
			
			
			
			 $scope.roomNumberArray = [];
			 for (var id in $scope.roomSelected) {
					if ($scope.roomSelected[id]) {
						 $scope.roomNumberArray.push(id);
					}
				}
			 if( $scope.roomNumberArray.length == $scope.countnochecked){
			 
			 var data=JSON.stringify($scope.roomNumberArray);
			 var data2 = JSON.stringify($scope.reserv_roomNo_array);
				var fileData = new FormData();
				fileData.append("roomNumber",data);
				fileData.append("reservRoomId",data2);
			
			
			// var data = JSON.stringify({roomNumber:$scope.roomNumberArray,reservRoomId:$scope.reserv_roomNo_array});
			
				$http.post("../checkIn/updateGroupCheckin",fileData,{
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined
					}
				}).success(function(data) {
					if(data.substring(7)=='success'){

				 	
						$("#roomEditModal").modal('hide');
						$scope.loadCheckinData();
						
						$scope.selectedBaground =[];
						$scope.reserv_roomNo_array=[];
						
						$scope.checkinData.forEach(function(chData){
						 chData.tile.background = "rgb(62, 103, 123)";
						});
						$scope.roomSelected={};
				 	
				}
			}).error(function(data) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
			}
			else{
				alert("Please select the rooms needed");
			}
			
			
		}
		else
			{
					if(!$scope.roomDetails.lastName){
						$scope.roomDetails.lastName="";
					}
					$scope.roomDetails.passportExpiryOn = new Date($scope.roomDetails.passportExpiryOn).getTime();
					$scope.uploadData={resvRoom:$scope.roomDetails};
					var proofFile=$scope.idproof;
					var imageFile;
					if($scope.snapshotData!=null){
						imageFile= dataURItoBlob($scope.snapshotData);	
					}
			
					
					
					
					if(!angular.equals({},$scope.facilitySelected)){
						var oldAddons=[];
						if(!angular.equals({},$scope.uploadData.resvRoom.checkinRequest)){
							$scope.uploadData.resvRoom.checkinRequest.forEach(function(checkInReq){
								oldAddons[checkInReq.facilityId]=true;
							});
						}
			
						$scope.availableFacilities.forEach(function(facility){
			
							if(facility.id in $scope.facilitySelected){
								if(!(facility.id in oldAddons)){	
									if($scope.facilitySelected[facility.id]){
										var facil = {facilityId:0,oneTimeReq:1,reqDate:"",reqTime:"",arrangementBy:0,reqRemarks:"",isActive:true};
										facil.facilityId=facility.id;
										if(facility.need=="daily"){
											facil.oneTimeReq=0;
										}								
										facil.reqDate=new Date(facility.needDate).getTime();
										facil.reqTime=new Date(facility.needTime).getTime();
										facil.arrangementBy=facility.provider;
										facil.reqRemarks=facility.remarks;
										facil.active=true;
										$scope.uploadData.resvRoom.checkinRequest.push(facil);
									}
								}else{
			
									$scope.uploadData.resvRoom.checkinRequest.forEach(function(checkInReq){
										if(facility.id == checkInReq.facilityId){
											checkInReq.isDeleted=false;
											if(!($scope.facilitySelected[facility.id])){
												checkInReq.isDeleted=true;
											}
											if(facility.need=="daily"){
												checkInReq.oneTimeReq=0;
											}else{checkInReq.oneTimeReq=1;}
											checkInReq.reqDate=new Date(facility.needDate).getTime();
											checkInReq.reqTime=new Date(facility.needTime).getTime();
											checkInReq.arrangementBy=facility.provider;
											checkInReq.reqRemarks=facility.remarks;
											checkInReq.reqTakenDate= new Date(checkInReq.reqTakenDate).getTime();
											checkInReq.active=true;
										}	
									});
								}
							}
						});
					}
			
			
			
					var data=JSON.stringify($scope.uploadData);
					var fileData = new FormData();
					fileData.append("idproof",proofFile);
					fileData.append("customerimg",imageFile);
					fileData.append("editData",data);
			
			
					$http.post("../checkIn/updateCheckInRoom",fileData,{
						transformRequest : angular.identity,
						headers : {
							'Content-Type' : undefined
						}
					}).success(function(data) {
						if(data.substring(7)=='success'){
						 	if(!$scope.roomDetails.firstName
						 			|| !$scope.roomDetails.address || !$scope.roomDetails.nationality ||
						 			!$scope.roomDetails.state||(!(!$scope.roomDetails.passportNo) && !$scope.roomDetails.passportExpiryOn)){
						 			$("#roomEditModal").modal('show');
						 	}else if(!$scope.roomDetails.roomNumber || $scope.roomDetails.roomNumber==''){
						 		$mdDialog.show($mdDialog.alert()
										.clickOutsideToClose(true).title('Alert').textContent('Please select room(s)').ok('Ok').parent(angular.element(document.body)));
						 		$("#roomEditModal").modal('show');
						 	}
						 	else{
								$("#roomEditModal").modal('hide');
								$scope.loadCheckinData();
								
								$scope.selectedBaground =[];
								$scope.reserv_roomNo_array=[];
								
								$scope.checkinData.forEach(function(chData){
								 chData.tile.background = "rgb(62, 103, 123)";
								});
						 	}
						}
					}).error(function(data) {
						$mdDialog.show($mdDialog.alert()
								.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
					});
			}
	}

	$scope.cancelPopUp = function(){
		$("#roomEditModal").modal('toggle');	
	}
	
	$scope.saveData = function() {
		$scope.checkinSubmit=true;
		var checkInRoomArray = [];
		chkResRoom=true;
		var a=$scope.rmDtls;
		for (var id in $scope.selected) {
			if ($scope.selected[id]) {
				checkInRoomArray.push(parseInt(id));
			}
		}
		for(var i=0;i<checkInRoomArray.length;i++){
			if($scope.rmDtls[checkInRoomArray[i]].room_number=="" || $scope.rmDtls[checkInRoomArray[i]].room_number==null){
				chkResRoom=false;
			}
			if($scope.rmDtls[checkInRoomArray[i]].nationality == '' || !$scope.rmDtls[checkInRoomArray[i]].nationality){
				$scope.rmDtls[checkInRoomArray[i]].nationality = "India";
			}
			
			if($scope.rmDtls[checkInRoomArray[i]].state == '' || !$scope.rmDtls[checkInRoomArray[i]].state){
				$scope.rmDtls[checkInRoomArray[i]].state = "Kerala";
			}
			if($scope.rmDtls[checkInRoomArray[i]].first_name=="" || $scope.rmDtls[checkInRoomArray[i]].first_name==null
					|| !$scope.rmDtls[checkInRoomArray[i]].first_name
		 			|| !$scope.rmDtls[checkInRoomArray[i]].address || !$scope.rmDtls[checkInRoomArray[i]].nationality ||
		 			!$scope.rmDtls[checkInRoomArray[i]].state||
		 			(!(!$scope.rmDtls[checkInRoomArray[i]].passport_no) && !$scope.rmDtls[checkInRoomArray[i]].passport_expiry_on)){
				chkResRoom=false;
			}
		}
		if($scope.shiftCheck!=0){
			if(checkInRoomArray.length>0){
				if(chkResRoom ){
					$.ajax({
						url: "../checkIn/saveRecord",
						type: "POST",
						data: {
							checkInDtls: JSON.stringify({selected:checkInRoomArray}),
							resvId:window.resvNo
						},
						success: function(response) {
							if(response=="success"){
								$scope.alertFnCheckIn();
							}
							else if(response=="error"){
								$scope.alertFn("Operation failed.");
							}
							else{
								$scope.alertFn("Not possible now");
								$scope.checkinSubmit=false;
							}
						}
					});
				}else{
					$scope.alertFn("Please fill in details for selected room");
					$scope.checkinSubmit=false;
				}
			}
			else{
				$scope.alertFn("Select atleast one reserved room");
				$scope.checkinSubmit=false;
			}
		}else{
				var confirm = $mdDialog.confirm()
				.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function(){
					window.location  = "../shiftManagement/openshift";
			});
		}
	}

	// webcam
	var _video = null,
	patData = null;
	$scope.patOpts = {x: 0, y: 0, w: 25, h: 25};
	$scope.channel = {};
	$scope.webcamError = false;
	$scope.onError = function (err) {
		$scope.$apply(
				function() {
					$scope.webcamError = err;
				}
		);
	};

	$scope.onSuccess = function () {
		_video = $scope.channel.video;
		$scope.$apply(function() {
			$scope.patOpts.w = _video.width;
			$scope.patOpts.h = _video.height;
		});
	};

	$scope.onStream = function (stream) {
		// You could do something manually with the stream.
	};

	$scope.makeSnapshot = function() {
		$scope.divCapture=false;
		if (_video) {
			var patCanvas = document.querySelector('#snapshot');
			if (!patCanvas) return;

			patCanvas.width = _video.width;
			patCanvas.height = _video.height;
			var ctxPat = patCanvas.getContext('2d');

			var idata = getVideoData($scope.patOpts.x, $scope.patOpts.y, $scope.patOpts.w, $scope.patOpts.h);
			ctxPat.putImageData(idata, 0, 0);

			sendSnapshotToServer(patCanvas.toDataURL());

			patData = idata;
			$scope.files.imageUploaded=false;
			$("#snapshot").css('display','block');
		}
	};

	/**
	 * Redirect the browser to the URL given. Used to download the image by
	 * passing a dataURL string
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
	 * This function could be used to send the image data to a backend server
	 * that expects base64 encoded images.
	 * 
	 * In this example, we simply store it in the scope for display.
	 */
	var sendSnapshotToServer = function sendSnapshotToServer(imgBase64) {
		$scope.snapshotData = imgBase64;
	};
	$scope.divCapture=false;
	$scope.openCaptureDiv = function(){
		if($scope.divCapture==true){
			$scope.divCapture=false;
		}else{$scope.divCapture=true;}
	}
	
	$scope.alertFn = function (msg) {
		$mdDialog.show($mdDialog.alert().clickOutsideToClose(true).title('Alert').textContent(msg).ok('Ok').parent(angular.element(document.body)));  
	}
	$scope.alertFnCheckIn = function () {
		$scope.loadCheckinData();
		var confirm = $mdDialog.alert()
		.title("CheckIn done successfully").ok('OK').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			// window.location = "../checkIn/checkInEdit?resvId="+window.resvNo;
			window.location  = "../reservation_test/reservationList";

		});
	}	
	$("#backButn").click(backFrmCheckIn);
	function backFrmCheckIn(){
		window.location  = "../reservation_test/reservationList";
	}
	
	
	
	
	
	
	$http({
		url:'../reservation_test/getCountries',
		method:'POST'
	}).then(function(response){
		$scope.countryList = response.data;
	});
	

	$scope.loadState = function(nationality){
		// $scope.roomDetails.state=""
		$scope.id="0";	
		for( var j=0;j<$scope.countryList.length;j++){
			if(nationality===$scope.countryList[j].name){
				$scope.id = $scope.countryList[j].id;
				break;
			}
		}
		var data = $.param({data:JSON.stringify($scope.id)});
		// var data = $.param({data:$scope.id});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../common/getState', data, config)
		.success(function (data, status, headers, config) {
			$scope.stateList= data;
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	
	}
	
	 $scope.loadCustomerData=function(){
			var data = $.param({data:$scope.roomDetails.firstName});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../checkIn/loadData', data, config)
			.success(function (data, status, headers, config) {
				$scope.customerList = data;
				if($scope.customerList.length >1){
					$('#myModal').modal('show');
				}else if($scope.customerList.length ==1) {
					$scope.copyData($scope.customerList[0])
				}else{
					$scope.roomDetails.is_old_customer =false;
					$scope.roomDetails.salutation="Mr."
					$scope.roomDetails.lastName="";
					$scope.roomDetails.email="";
					$scope.roomDetails.passportNo="";
					$scope.roomDetails.passportExpiryOn="";
					$scope.roomDetails.gstno="";
					$scope.roomDetails.phone="";
					$scope.roomDetails.address="";
					$scope.roomDetails.nationality="";
					$scope.roomDetails.state="";
					
				}
			}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});

	}
$scope.copyData= function(data){
	$scope.roomDetails.is_old_customer =true;
		if(data.salutation != null){
			$scope.roomDetails.salutation=data.salutation;
		}else{
			$scope.roomDetails.salutation="Mr."
		}
		if(data.last_name !== null){
			$scope.roomDetails.lastName=data.last_name;
	    }else{
	    	$scope.roomDetails.lastName="";
	    }
		if(data.mail !== null){
			$scope.roomDetails.email=data.mail;
	    }else{
	    	$scope.roomDetails.email="";
	    }
		if(data.passport_no !== null){
			$scope.roomDetails.passportNo=data.passport_no;
	    }else{
	    	$scope.roomDetails.passportNo="";
	    }
		if(data.passport_expiry_on !== null){
			$scope.roomDetails.passportExpiryOn=new Date(data.passport_expiry_on);
	    }else{
	    	$scope.roomDetails.passportExpiryOn="";
	    }
		if(data.gstno !== null){
			$scope.roomDetails.gstno=data.gstno;
	    }else{
	    	$scope.roomDetails.gstno="";
	    }
		$scope.roomDetails.firstName=data.first_name;
		$scope.roomDetails.gender=data.gender
		$scope.roomDetails.phone=data.phone;
		$scope.roomDetails.address=data.address;
		$scope.roomDetails.nationality=data.nationality;
		$scope.loadState($scope.roomDetails.nationality);
		$scope.roomDetails.state=data.state;
	
		$scope.roomDetails.last_visit=data.last_visit;
		$scope.roomDetails.no_visit=data.no_visit;
		$('#myModal').modal('hide');
}

	$scope.clodeModal=function(){
		$('#myModal').modal('hide');
	}


	$scope.is_old_customer = function(){
		 $("#last_visit").text($scope.roomDetails.last_visit);
		 $("#no_visit").text($scope.roomDetails.no_visit);
		 return $scope.roomDetails.is_old_customer ;
	}

	$scope.simpleSearchByMail=function(){
	 	var data = $.param({data:$scope.roomDetails.email});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../checkIn/loadDataByMail', data, config)
		.success(function (data, status, headers, config) {	
			 	if(!data.salutation){
					$scope.roomDetails.salutation="Mr."
				}else{
			 		$scope.roomDetails.salutation=data.salutation;
				}
				if(!data.last_name){
			    	$scope.roomDetails.lastName="";
			    }else{
					$scope.roomDetails.lastName=data.last_name;
			    }
				if(!data.passport_no){
			    	$scope.roomDetails.passportNo="";
			    }else{
					$scope.roomDetails.passportNo=data.passport_no;
			    }
				if(!data.passport_expiry_on){
			    	$scope.roomDetails.passportExpiryOn="";
			    }else{
					$scope.roomDetails.passportExpiryOn=new Date(data.passport_expiry_on);
			    }
				if(!data.gstno){
			    	$scope.roomDetails.gstno="";
			    }else{
			    	$scope.roomDetails.gstno=data.gstno;
			    }
				if(!data.first_name){
					$scope.roomDetails.is_old_customer =false;
				}else{
					$scope.roomDetails.is_old_customer =true;
				}
					$scope.roomDetails.firstName=data.first_name;
					$scope.roomDetails.gender=data.gender
					$scope.roomDetails.phone=data.phone;
					$scope.roomDetails.address=data.address;
					$scope.roomDetails.nationality=data.nationality;
					$scope.loadState($scope.roomDetails.nationality);
					$scope.roomDetails.state=data.state;
				
					$scope.roomDetails.last_visit=data.last_visit;
					$scope.roomDetails.no_visit=data.no_visit;
	
			
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});

	}
	
	$scope.simpleSearchReception=function(){
		$scope.phoneNum=$scope.roomDetails.phone;

		$scope.phone=$scope.phoneNum;
		$http({
			url : '../reception/getDetailViaPhonenum',
			method : "POST",
			data:$scope.phone

		}).then(function(response) {
			if(!response.data[0].salutation){
				$scope.roomDetails.salutation="Mr.";
			}else{
				$scope.roomDetails.salutation=response.data[0].salutation;
			}
			
			if(!response.data[0].last_name){
				$scope.roomDetails.lastName="";
			}else{
				$scope.roomDetails.lastName=response.data[0].last_name;
			}
			
			if(!response.data[0].email){
				$scope.roomDetails.email="";
			}else{
				$scope.roomDetails.email=response.data[0].email;
			}
			if(!response.data[0].gstno){
		    	$scope.roomDetails.gstno="";
		    }else{
				$scope.roomDetails.gstno=response.data[0].gstno
		    }
			
			if(!response.data[0].passport_no){
		    	$scope.roomDetails.passportNo="";
		    }else{
				$scope.roomDetails.passportNo=response.data[0].passport_no;
		    }
			if(!response.data[0].passport_expiry_on){
		    	$scope.roomDetails.passportExpiryOn="";
		    }else{
				$scope.roomDetails.passportExpiryOn=new Date(response.data[0].passport_expiry_on);
		    }
			if(!response.data[0].first_name){
				$scope.roomDetails.is_old_customer=false;
			}else{
				$scope.roomDetails.is_old_customer=true
			}
				$scope.roomDetails.firstName=response.data[0].first_name
				$scope.roomDetails.address=response.data[0].address
				$scope.roomDetails.gender=response.data[0].gender
				$scope.roomDetails.nationality=response.data[0].nationality;
				$scope.loadState($scope.roomDetails.nationality);
				$scope.roomDetails.state=response.data[0].state;
				$scope.roomDetails.last_visit=response.data[0].last_visit;
				$scope.roomDetails.no_visit=response.data[0].no_visit;
			

		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
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


pmsApp.directive("limitTo", [function() {
	return {
		restrict: "A",
		link: function(scope, elem, attrs) {
			var limit = parseInt(attrs.limitTo);
			angular.element(elem).on("focusout", function(e) {
				if (parseInt(this.value) > limit || parseInt(this.value) <=0 || this.value=="") {e.preventDefault();
				this.value=1;
				};
			});
		}
	}
}]);