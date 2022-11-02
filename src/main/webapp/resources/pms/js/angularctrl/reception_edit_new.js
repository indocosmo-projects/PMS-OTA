pmsApp.service("ServiceIntial",function(){
	var room;
	var roomRateDtls;
	var roomTypeDtls;
	var discount;
	var nights;
	this.setRoom = function(newRoom){
		room=newRoom; 
	}
	this.getRoom = function(){
		return room; 
	}
	this.setRoomRate = function(newRoomRate,newOcc){
		roomRateDtls={ratePlan:newRoomRate,occ:newOcc};
	}
	this.getRoomRate = function(){
		return roomRateDtls; 
	}

	this.setRoomType = function(oldRoomType,oldRoomRate,oldRateCode,oldOcc){
		roomTypeDtls={roomType:oldRoomType,ratePlan:oldRoomRate,rateCode:oldRateCode,occ:oldOcc};
	}
	this.getRoomType = function(){
		return roomTypeDtls; 
	}

	this.setDiscount = function(oldDiscType,oldDiscId,oldDiscCode,isPc,oldDiscVal,isOpen){
		discount={discType:oldDiscType,discId:oldDiscId,discCode:oldDiscCode,isPc:isPc,discVal:oldDiscVal,isOpen:isOpen};
	}
	this.getDiscount = function(){
		return discount; 
	}
	this.setNight = function(night){
		nights=night; 
	}
	this.getNight = function(){
		return nights; 
	}
});
pmsApp.controller('receptionEditController', ['$scope','$q','$http','DTOptionsBuilder','DTColumnBuilder','$mdDialog','$timeout','$compile','$filter','ServiceIntial','discountService',function ($scope,$q, $http, DTOptionsBuilder, DTColumnBuilder,$mdDialog,$timeout,$compile,$filter,ServiceIntial,discountService){
	var hdate = $("#hotelDate").val();
	$scope.hotelDate =new Date(hdate);
	$scope.dateFormat = $("#dateFormat").val();
	var rec = this;
	rec.isFetchingData = true;
	$scope.checkin={hdr:{},dtl:{},sharer:[],editedAmount:{}};
	$scope.names = ["Mr.","Mrs.","Ms.","M/s.","Dr.","C/o."];
	$scope.imageDiv=false;
	$scope.submit_click=false;
	$scope.discDispList = [];
	$scope.roomDetails={roomType:0,roomNumber:"",ratePlanId:0,occupancy:0,removeRoom:false,removeRate:false,removeRoomType:false,isRoomNumberDirty:false,isRatePlanDirty:false,isRoomTypeDirty:false,isSharerDirty:false,isDiscountDirty:false,isNightDirty:false};
	$scope.disc={general:null,planBased:null,group:'general'};
	$scope.discDisp={code:"",value:""};
	$scope.discApplied={};
	$scope.availDiscounts={};
	$scope.disApliedcnt = 0;
	var dateFormat = $("#dateFormat").val();
	$scope.flagNewSharer=true;
	$scope.todaysDate=new Date();
	$scope.currentDate=new Date(new Date($scope.todaysDate.getFullYear()),new Date($scope.todaysDate.getMonth()),new Date($scope.todaysDate.getDate()+1));
	var sharerIndex=0;
	$('#imgloader').show();
	rec.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		defer.resolve($scope.checkin.sharer);
		return defer.promise;
	}).withPaginationType('full_numbers').withOption('createdRow', function(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	}).withOption('rowCallback', rowCallback);
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {

		$('td', nRow).unbind('click');
		$('td',nRow).bind('click', function() {
			$scope.$apply(function() {
				if(!aData.isDeleted){
					$scope.flagNewSharer=false;
					$scope.sharer = {checkinDtlNo:aData.checkinDtlNo,salutation:aData.salutation,firstName:aData.firstName,lastName:aData.lastName,gender:aData.gender,address:aData.address,email:aData.email,phone:aData.phone,nationality:aData.nationality,state:aData.state,passportNo:aData.passportNo,passportExpiryOn:new Date(aData.passportExpiryOn),remarks:aData.remarks,arrayIndex:aData.arrayIndex};
					$scope.loadState($scope.sharer.nationality);
					$("#addsharermyModal").modal({backdrop: "static"});
				}
			})
		})

	}
	rec.dtColumns = [DTColumnBuilder.newColumn("salutation","salutation"),
		             DTColumnBuilder.newColumn("firstName","Name").renderWith(function(data, type, full){return data + " "+full.lastName }),
	                 DTColumnBuilder.newColumn("email","e-mail"),
	                 DTColumnBuilder.newColumn("phone","Phone"),
	                 DTColumnBuilder.newColumn("address","Address"),
	                 DTColumnBuilder.newColumn("isDeleted","Status").renderWith(function(data, type, full){var r = 'Active'; if(data)r="Deleted"; return r;})];
	rec.dtInstance = {};
	var data = $.param({checkinNo:parseInt($("#checkNo").val())});
	var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
	$http.post('../reception/getReceptionEditData', data, config)
	.success(function (data, status, headers, config) {
		$('#imgloader').hide();
		$scope.checkin={hdr:JSON.parse(data.checkinHdr),dtl:{},sharer:[],editedAmount:{}};
		
		var timeDiff = Math.abs(new Date($scope.checkin.hdr.arrDate).getTime() -new Date($scope.hotelDate).getTime());
		$scope.diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
		$scope.folioBal = data.folBal;
		$scope.disApliedcnt = data.disApliedcnt;
		$scope.expDepartDate = new Date($scope.checkin.hdr.expDepartDate);
		$scope.arrDate = new Date($scope.checkin.hdr.arrDate);
		$scope.checkinDiscountList = data.checkinDiscountList;
		rec.isFetchingData = false;
		ServiceIntial.setRoom($scope.checkin.hdr.roomNumber);
		ServiceIntial.setRoomRate($scope.checkin.hdr.rateId,$scope.checkin.hdr.occupancy);
		ServiceIntial.setRoomType($scope.checkin.hdr.roomTypeId,$scope.checkin.hdr.rateId,$scope.checkin.hdr.rateCode,$scope.checkin.hdr.occupancy);
		ServiceIntial.setNight($scope.checkin.hdr.numNights);
		var discVal;
		if($scope.checkin.hdr.discIsPc){
			discVal=$scope.checkin.hdr.discPc;
		}else{
			discVal=$scope.checkin.hdr.discAmount;
		}
		ServiceIntial.setDiscount($scope.checkin.hdr.discType,$scope.checkin.hdr.discId,$scope.checkin.hdr.discCode,$scope.checkin.hdr.discIsPc,discVal,$scope.checkin.hdr.discIsOpen);

		/*if($scope.checkin.hdr.discType==2){
			var opendisc=0;
			var isOpenDisc=false;
			$scope.discDisp.code=$scope.checkin.hdr.discCode;
			$scope.discDisp.value=discVal;
			if($scope.checkin.hdr.discIsOpen){
				opendisc=discVal;
				isOpenDisc=true;
				$scope.discDisp.value=opendisc;
			}
			$scope.discApplied={group:'general',value:{discId:$scope.checkin.hdr.discId,isOpen:isOpenDisc,openValue:opendisc}};	
		}else if($scope.checkin.hdr.discType==1){
			$scope.discDisp={};				
			$scope.discDisp.code=$scope.checkin.hdr.discCode;
			$scope.discDisp.value=discVal;
			$scope.discApplied={group:'plan',value:{discId:$scope.checkin.hdr.discId}};
		}
		*/
		
		
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
		
		data.checkinDtls.forEach(function(checkinDtl) {
			if(checkinDtl.isSharer){
				checkinDtl.isDirty=false;
				checkinDtl.arrayIndex=sharerIndex;
				if(checkinDtl.hasOwnProperty("passportExpiryOn"))
				checkinDtl.passportExpiryOn = new Date(checkinDtl.passportExpiryOn);
				
				$scope.checkin.sharer.push(checkinDtl);
				sharerIndex=sharerIndex+1;
			}else{
				$scope.checkin.dtl.isDirty=false;
				if(checkinDtl.hasOwnProperty("passportExpiryOn"))
				checkinDtl.passportExpiryOn = new Date(checkinDtl.passportExpiryOn);
				
				$scope.checkin.dtl=checkinDtl;
				$timeout(function(){$scope.loadState($scope.checkin.dtl.nationality)},100);
			}
		});


	}).error(function (data, status, header, config){
		$('#imgloader').hide();
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
	});

	$scope.getVacantRooms = function(roomtype){		
		var data = $.param({minArrDate:$filter('date')(new Date($scope.checkin.hdr.arrDate), dateFormat),maxDepartDate:$filter('date')(new Date($scope.checkin.hdr.expDepartDate), dateFormat),roomTypeId:parseInt(roomtype),occupancy:1});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reception/getAvailableRooms', data, config)
		.success(function (data, status, headers, config) {
			$scope.roomDetails.roomNumber="";
			$scope.availableRooms = data;
			$scope.roomType=$scope.availableRooms[0].roomType;
			$scope.imageDiv=true;
			$("#roomAssignmyModal").modal({backdrop: "static"});
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}
	$scope.assignRoomNumber = function(){
		$scope.assignedRoomNumber=$scope.roomDetails.roomNumber;
		if($scope.roomDetails.roomNumber!=$scope.checkin.hdr.roomNumber && $scope.roomDetails.roomNumber!="" && !$scope.roomDetails.isRoomTypeDirty)
			$scope.roomDetails.removeRoom = true;

		$scope.roomDetails.isRoomNumberDirty=true;
		$scope.checkin.hdr.roomNumber = $scope.roomDetails.roomNumber;
		$("#roomAssignmyModal").modal("toggle");
	}
	$scope.changeToInitialRoom =function (){
		$scope.checkin.hdr.roomNumber=ServiceIntial.getRoom();
		$scope.roomDetails.removeRoom = false;
		$scope.roomDetails.isRoomNumberDirty=false;
	}
	$scope.roomRates={};
	$scope.showRoomRates = function(){
		$('#imgloader').show();
		var data = $.param({roomTypeDtls:JSON.stringify({roomType:$scope.checkin.hdr.roomTypeCode,arrDate:$filter('date')(new Date($scope.checkin.hdr.arrDate), dateFormat),nights:$scope.checkin.hdr.numNights,rate_id:$scope.checkin.hdr.rateId})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomRatePlans', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			data.ratePlans.forEach(function(entry) {
				$scope.roomRates[entry.rateId]=entry;
			});
			$scope.roomTypeImages= data.images;
			$scope.roomDetails.ratePlanId=$scope.checkin.hdr.rateId;
			$scope.roomDetails.occupancy=$scope.checkin.hdr.occupancy;
			$("#ratePlanmyModal").modal({backdrop: "static"});
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});		
	}

	$scope.assignRoomRate = function(){
		
		$scope.assignedRoomChangedAmountList=[];
		$(".assgnRate").each(function(){
			$scope.assignedRoomAmount={id:"",currentAmount:0,occ:"",old_id:0,old_amount:0,parent_id:0}
			if($(this).attr('id') in $scope.assignedRoomChangedAmountList){
				if(this.value=="" || this.value==0){
					delete $scope.assignedRoomChangedAmountList[$(this).attr('id')];
				}
			}
			$scope.assignedRoomAmount={id:$(this).attr('id'),currentAmount:this.value,occ:($(this).attr('id')).split('_')[2],old_id:($(this).attr('id')).split('_')[1],old_amount:0,parent_id:($(this).attr('id')).split('_')[3]};
			switch ($scope.assignedRoomAmount.occ) {
			case "occ1":
				$scope.assignedRoomAmount.old_amount=$scope.roomRates[$scope.assignedRoomAmount.old_id].totalOcc1Rate;
				break;
			case "occ2":
				$scope.assignedRoomAmount.old_amount=$scope.roomRates[$scope.assignedRoomAmount.old_id].totalOcc2Rate;
				break;
			case "occ3":
				$scope.assignedRoomAmount.old_amount=$scope.roomRates[$scope.assignedRoomAmount.old_id].totalOcc3Rate;	
				break;
			case "occ4":
				$scope.assignedRoomAmount.old_amount=$scope.roomRates[$scope.assignedRoomAmount.old_id].totalOcc4Rate;

				break;
			}
			if($scope.assignedRoomAmount.old_amount!=$scope.assignedRoomAmount.currentAmount){
			$scope.assignedRoomChangedAmountList[$scope.assignedRoomAmount.id]=$scope.assignedRoomAmount;
			}
		});
		
		
		
		
		$scope.checkin.hdr.rateId = parseInt($scope.roomDetails.ratePlanId);
		$scope.checkin.hdr.rateCode=$scope.roomRates[parseInt($scope.roomDetails.ratePlanId)].rateCode;
		$scope.checkin.hdr.occupancy=$scope.roomDetails.occupancy;
		if(!$scope.roomDetails.isRoomTypeDirty)
			$scope.roomDetails.removeRate = true;

		$scope.roomDetails.isRatePlanDirty=true;
		
		
		
		
		
		
		
		
		$("#ratePlanmyModal").modal("toggle");
	}
	$scope.changeToInitialRoomRate =function (){
		var oldRate=ServiceIntial.getRoomRate();
		$scope.checkin.hdr.rateId=oldRate.ratePlan;
		$scope.checkin.hdr.occupancy = oldRate.occ;
		$scope.checkin.hdr.rateCode=$scope.roomRates[$scope.checkin.hdr.rateId].rateCode;
		$scope.roomDetails.removeRate = false;
		$scope.roomDetails.isRatePlanDirty=false;
	}
	$scope.newSharer = function(){
		rec.dtInstance.reloadData();
	}
	$scope.roomTypesAvailable={};
	$scope.loadAvailableRoomTypes = function(){
		var availRoomParams ={minArrDate: $filter('date')(new Date($scope.hotelDate), dateFormat),nights:$scope.checkin.hdr.numNights,numRooms:1};
		$('#imgloader').show();
		var data = $.param({resvHdr:JSON.stringify(availRoomParams)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomAvailability', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			data.forEach(function(entry) {
				$scope.roomTypesAvailable[entry.roomTypeId]=entry;
			});
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	$scope.showRoomTypeDetails = function(roomTypeid,roomTypeCode){
		$scope.roomRatesNewRoomType={};
		$('#imgloader').show();
		var data = $.param({roomTypeDtls:JSON.stringify({roomType:roomTypeCode,arrDate:$filter('date')(new Date($scope.checkin.hdr.arrDate), dateFormat),nights:$scope.checkin.hdr.numNights,rate_id:0})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getRoomRatePlans', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			data.ratePlans.forEach(function(entry) {
				$scope.roomRatesNewRoomType[entry.rateId]=entry;
			});
			$scope.selectedRoomType=roomTypeid;
			$scope.roomTypeImages= data.images;
			$scope.roomDetails.roomType=$scope.checkin.hdr.roomTypeId;
			$scope.roomDetails.ratePlanId=$scope.checkin.hdr.rateId;
			$scope.roomDetails.occupancy=$scope.checkin.hdr.occupancy;
			$("#roomTypemyModal").modal({backdrop: "static"});
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}

	$scope.assignType = function(){
		$scope.checkin.hdr.rateId = parseInt($scope.roomDetails.ratePlanId);
		$scope.checkin.hdr.rateCode=$scope.roomRatesNewRoomType[parseInt($scope.roomDetails.ratePlanId)].rateCode;
		$scope.checkin.hdr.occupancy=$scope.roomDetails.occupancy;
		$scope.checkin.hdr.roomTypeId=$scope.selectedRoomType;
		$scope.checkin.hdr.roomTypeCode=$scope.roomTypesAvailable[$scope.checkin.hdr.roomTypeId].roomTypeCode;
		$scope.roomDetails.removeRoomType = true;
		$scope.roomDetails.isRoomTypeDirty=true;
		$scope.roomDetails.isRatePlanDirty=true;
		$scope.checkin.hdr.roomNumber="";
		$scope.roomDetails.roomNumber="";
		$("#roomTypemyModal").modal("toggle");
	}
	$scope.changeToInitialRoomType =function (){
		var oldRoomType=ServiceIntial.getRoomType();
		$scope.changeToInitialRoom();
		$scope.checkin.hdr.rateId=oldRoomType.ratePlan;
		$scope.checkin.hdr.occupancy = oldRoomType.occ;
		$scope.checkin.hdr.rateCode=oldRoomType.rateCode;
		$scope.checkin.hdr.roomTypeId=oldRoomType.roomType;
		$scope.checkin.hdr.roomTypeCode=$scope.roomTypesAvailable[$scope.checkin.hdr.roomTypeId].roomTypeCode;
		$scope.roomDetails.removeRoomType = false;
		$scope.roomDetails.isRoomTypeDirty=false;
		$scope.roomDetails.isRatePlanDirty=false;
	}


	$scope.loadDiscounts=function(){
		$scope.discParam=[$scope.checkin.hdr.rateId];
		discountService.getDiscounts($scope.discParam).then(function(response) {
			$scope.availDiscounts = response.data;			
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	$scope.showDiscounts =function(){
		$scope.loadDiscounts();
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
	}
	$scope.applyDiscount =function(){
		if(($scope.disc.group=='general' && $scope.disc.generalDisc!=null) || ($scope.disc.group=='plan'&& $scope.disc.planBased!=null)){
			$scope.discDisp={code:"",value:""};
			var checkin_discounts={};
			if($scope.disc.group=='general'){
				var opendisc=0;
				var isOpenDisc=false;
				$scope.discDisp.id=$scope.disc.generalDisc;
				$scope.discDisp.code=$(".rad_"+$scope.disc.generalDisc).attr("data-discCode");
				$scope.discDisp.value=$(".rad_"+$scope.disc.generalDisc).attr("data-discValue");
				$scope.discDisp.discountFor = parseInt($(".rad_"+$scope.disc.generalDisc).attr("data-discountFor"));
				if($(".rad_"+$scope.disc.generalDisc).attr("data-isOpen")=="true"){
					opendisc=$("#open"+$scope.disc.generalDisc).val();
					isOpenDisc=true;
					$scope.discDisp.value=opendisc;
				}
				
				var index = $scope.discDispList.findIndex(a => a.id === $scope.discDisp.id);
				var indexRmFd = $scope.discDispList.findIndex(a => a.discountFor === $scope.discDisp.discountFor);
				if(index == -1 && indexRmFd == -1){
					$scope.discDispList.push($scope.discDisp);
					$scope.discApplied={group:'general',value:{discId:$scope.disc.generalDisc,isOpen:isOpenDisc,openValue:opendisc}};	
					checkin_discounts.discId=parseInt($scope.discDisp.id);
					if(isOpenDisc){
						checkin_discounts.openDiscount=parseFloat($scope.discApplied.value.openValue);
					}
					else{
						checkin_discounts.openDiscount="";
					}
					$scope.checkin_discounts.push(checkin_discounts);
					
				}

				
			}else{
				$scope.discDisp.id=p;
				$scope.discDisp.code=$(".chk_"+p).attr("data-discCode");
				$scope.discDisp.value=$(".chk_"+p).attr("data-discValue");
				$scope.discDisp.discountFor =parseInt($(".chk_"+p).attr("data-discountFor"));
				var index = $scope.discDispList.findIndex(a => a.id === $scope.discDisp.id);
				var indexRmFd = $scope.discDispList.findIndex(a => a.discountFor === $scope.discDisp.discountFor);
				//var Roomindex = $scope.discDispList.findIndex(a => a.discountFor === 1);
				if(index == -1 && indexRmFd == -1){
					$scope.discDispList.push($scope.discDisp);
					$scope.discApplied={group:'plan',value:{discId:$scope.disc.planBased}};
					checkin_discounts.discId=p;
					$scope.checkin_discounts.push(checkin_discounts);
					
				}
			}
			$scope.roomDetails.isDiscountDirty=true;
			$("#availDiscountsmyModal").modal("toggle");	
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("No Discount selected").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));	
		}
	}

	$scope.newSharer = function(){
		$scope.flagNewSharer=true;
		$scope.sharer = {salutation:"Mr.",firstName:"",lastName:"",gender:"Male",address:"",email:"",phone:"",nationality:"",state:"",passportNo:"",passportExpiryOn:"",remarks:""};
		$("#addsharermyModal").modal({backdrop: "static"});	
	}


	$scope.saveSharer = function(){
		
		if($scope.flagNewSharer){
			$scope.sharer.arrayIndex=sharerIndex;
			$scope.sharer.isDeleted=false;
			$scope.sharer.isSharer=true;
			$scope.checkin.sharer.push($scope.sharer);
			sharerIndex = sharerIndex+1;
		}else{
			$scope.checkin.sharer.forEach(function(entry,index) {
				if($scope.sharer.arrayIndex==entry.arrayIndex){
					$scope.checkin.sharer[index] =$scope.sharer;
				}
			});
		}
		rec.dtInstance.reloadData();
		$scope.roomDetails.isSharerDirty=true;
		$("#addsharermyModal").modal("toggle");	
	}

	$scope.deleteSharer = function(){
		$scope.checkin.sharer.forEach(function(entry,index) {
			if($scope.sharer.arrayIndex==entry.arrayIndex){
				if(entry.hasOwnProperty("checkinDtlNo")){
					entry.isDirty=true;
					entry.isDeleted=true;
				}else{
					$scope.checkin.sharer.splice(index,1);
				} 
			}
		});

		rec.dtInstance.reloadData();
		sharerIndex=sharerIndex-1;
		$scope.roomDetails.isSharerDirty=true;
		$("#addsharermyModal").modal("toggle");	
	}
	$scope.nightRange = function(){
		return new Array(parseInt($scope.checkin.hdr.numNights)); 
	}
	$scope.checkNight = function(inx,diffDays){
		if(inx<=diffDays){return true;}else{return false;}
	}


	$scope.save = function(){
		
		var roomdis = $scope.discDispList.filter(a => a.discountFor === 1);
		var fooddis = $scope.discDispList.filter(a => a.discountFor === 2);
		if($scope.discDispList.length>2 || roomdis.length >1|| fooddis.legth>1){
			//alert("only possible to select 2 different type discount");
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('').textContent("Only Possible to Select 2 Different Type Discount.").ok('Ok').parent(angular.element(document.body)));
			return ;
		}
		
		$scope.daynum=$scope.checkin.hdr.dayNum;
		$scope.submit_click=true;
		if($scope.hdrForm.$valid){
			if($scope.hdrForm.$dirty){
				$scope.checkin.dtl.isDirty=true;
			}
			
			//$scope.salutation=$scope.checkin.dtl.selectedSalutation;
			$scope.receptionData = {checkinHdr:$scope.checkin.hdr,checkinDtl:null,sharer:null,roomDtls:$scope.roomDetails,discount:null,editedAmount:null,checkinDiscnt:$scope.checkin_discounts};
       for(var key in $scope.assignedRoomChangedAmountList){
    	  if($scope.receptionData.checkinHdr.rateId== $scope.assignedRoomChangedAmountList[key].old_id){
    		  if($scope.receptionData.checkinHdr.occupancy== $scope.assignedRoomChangedAmountList[key].occ.substring(3)){
	     $scope.receptionData.editedAmount=$scope.assignedRoomChangedAmountList[key];	
    		  }
    	  }
			}
			if($scope.checkin.dtl.isDirty){
				delete $scope.checkin.dtl.isDirty;
				$scope.receptionData.checkinDtl=$scope.checkin.dtl;
			}
			if($scope.roomDetails.isSharerDirty){
				$scope.checkin.sharer.forEach(function(entry) {
					delete entry.isDirty;
					delete entry.arrayIndex;
				});
				$scope.receptionData.sharer=$scope.checkin.sharer;
			}
			if($scope.roomDetails.isDiscountDirty){
				$scope.receptionData.discount=$scope.discApplied;
			}

			if($scope.checkin.hdr.numNights!=ServiceIntial.getNight()){
				$scope.roomDetails.isNightDirty=true;
			}
			$scope.roomDetails.checkinDtlNo = $scope.checkin.dtl.checkinDtlNo;
           // $scope.receptionData.salutation=$scope.sharer.salutation;
            
			$http({
				url : '../reception/updateReceptionEdit',
				method : "POST",
				data : $scope.receptionData
			}).then(function(response) {
				var msg=[];
				var msg=response.data.substr(7).split('_');
				
				if(msg[0]=='success'){
					$scope.newRateId=msg[1];
					$('#imgloader').hide();
					var confirm = $mdDialog.alert()
					.title("Details updated successfully").ok('OK').parent(angular.element(document.body));
					$mdDialog.show(confirm).then(function(){
						window.location  = "../reception/receptionList";
					});
				}else{$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));}			
			},function(response) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
			});
			console.log($scope.hdrForm.$dirty);
		}
	}
	$scope.$watch('checkin.hdr.numAdults', function(newValue,oldValue) {
		if(newValue>parseInt($scope.checkin.hdr.occupancy)){
			$scope.$apply(function() {
			$scope.checkin.hdr.numAdults = oldValue;
			});
		}
	});
	$scope.cancelRoomAssignPopUp = function(){
		$("#roomAssignmyModal").modal("toggle");
	}
	$scope.cancelRatePlanPopUp = function(){
		$("#ratePlanmyModal").modal("toggle");
	}
	$scope.cancelRoomTypePopUp = function(){
		$("#roomTypemyModal").modal("toggle");
	}
	$scope.cancelDiscPopUp = function(){
		$("#availDiscountsmyModal").modal("toggle");
	}
	$scope.cancelSharerPopUp = function(){
		$("#addsharermyModal").modal("toggle");
	}
	
	//$scope.loadCountry = [];
	$scope.resvByStateList = [];
	$scope.resvForStateList = [];
	$scope.loadCountry = function(){
		$http({
			url : "../reservation_test/getCountries",
		    method : "POST",
		}).then(function(response){
			$scope.countryList = response.data;
			/*$scope.resv_by_country = $scope.countryList[0].id;
			$scope.resv_for_country = $scope.countryList[0].id;
			for(var i=0; i<$scope.states.length; i++){
				if($scope.states[i].countryId == $scope.countryList[0].id){
					$scope.countryList.push($scope.states[i]);
					$scope.stateList.push($scope.states[i]);
				}
			}*/
			
		});
	}
	$scope.loadCountry();
	$scope.loadState = function(nationality){
		/*if($scope.countryList.length==0){
			$scope.loadCountry();
		}*/
		$timeout(function(){

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
			$scope.stateList= data;
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
		}, 100);
	}
	
}]);

pmsApp.directive('readonly',function(){
   return {
    restrict: 'EAC',
    link: function(scope, elem, attr) {
      document.querySelectorAll("#datePicker input")[0].setAttribute("readonly","readonly");
       angular.element(".md-datepicker-button").each(function(){
            var el = this;
            var ip = angular.element(el).parent().find("input").bind('click', function(e){
                angular.element(el).click();
            });
            angular.element(this).css('visibility', 'hidden');
        });
    }
   }
  
});