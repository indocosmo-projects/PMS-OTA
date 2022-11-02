pmsApp.controller('resvDtlController', ['$scope','$http','$mdDialog','$window',function ($scope, $http,$mdDialog,$window){
	$scope.dateFormat=$("#dateFormat").val();
	$scope.timeFormat = $("#timeFormat").val();
	$scope.countnoShowno = $("#countnoShowno").val();
	$scope.msgElm = angular.element("#pickUpBtn");
	$scope.hotelDate = new Date($("#hotelDate").val());
	
	 $scope.formatDate = function(date){
         var dateOut = new Date(date);
         return dateOut;
   };
	
	$scope.showAlert=function(title,msg){
		alert = $mdDialog.alert({
	          title: title,
	          textContent: msg,
	          ok: 'Close'
	        });

	        $mdDialog
	          .show( alert )
	          .finally(function() {
	            alert = undefined;
	          });	
	}	
	var resvNo=parseInt($('#resvNum').val());
	$scope.showDeposit=false;
	$scope.showChangeArr=false;
	$scope.showpickup=false;
	$scope.showEdit=false;
	$scope.showNoshow=false;
	$scope.showCancel=false;
	$scope.showConfirm=false;
	$scope.showCheckIn=false;
	var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}};
 

    
	
 $scope.getReservGuestData = function(resvNo){
	 $scope.datas;
	  	var data = $.param({
	  		resvNO:resvNo
	  	});
	$http.post('../reservation_test/getReservGuestData', data, config)
	.success(function (data, status, headers, config) {
		$scope.datas = data;
		$scope.departDate = new Date($scope.datas.departDate);
		
		if(data.pickupNeeded==1){
			$scope.msgElm.addClass("changeColor");
			$scope.datas.pickupNeeded = true;
			$scope.pickupNeeded = true;
			$("#pickup_div").show();
			$scope.myForm.$dirty=true;
			
		}else{
			$scope.msgElm.removeClass("changeColor");
			$scope.datas.pickupNeeded = false;
			$scope.pickupNeeded = false;
			$("#pickup_div").hide();	
			$scope.myForm.$dirty=false;
		}
		
		$scope.rDtl={cutOffDate:$scope.datas.cutOffDate,
				     resvByName:$scope.datas.resvByName,
				     resvByMail:$scope.datas.resvByEmail,
				     resvByPhone:$scope.datas.resvByPhone,
				     resvByAddress:$scope.datas.resvByAddres,
				     reservedFor:$scope.datas.resvFor,
				     resvDate:$scope.datas.resvDate,
				     arrDate:$scope.datas.arrDate,
				     numRooms:$scope.datas.numRooms,
				     numNights:$scope.datas.numNights,
				     status:$scope.datas.resvStatusXlt,
				     specialrequest:$scope.datas.specialrequest,
				     resvNum:resvNo};

		
		$('#pickup_time').datetimepicker({
			showClear: true,
	        format: 'LT'
	    });	
	    // pickup data close

		if($scope.datas.canConfirm==0){
			$scope.showConfirm=true;
		}
		if($scope.datas.canCancel==0){
			$scope.showCancel=true;
			
		}
		if($scope.datas.canDeposit==0){
			$scope.showDeposit=true;
		}
		if($scope.datas.canCheckin==0){
			$scope.showCheckIn=true;
		}
		if($scope.datas.canEdit==0){
			$scope.showEdit=true;
			$scope.showpickup=true;
		}
		if($scope.datas.canEdit==0){
			$scope.showChangeArr=true;
		}
		if($scope.datas.canNoshow==0){
			$scope.showNoshow=true;
			$scope.disableTagButton = false;
		}
		var statusClass="";
		switch ($scope.datas.resvStatus) {
		case 1:
			statusClass = 'res_stat-confirmed';
			break;
		case 0:
			statusClass = 'res_stat-unconfirmed';
			break;
		case 4:
			statusClass = 'res_stat-partcheckin';
			break;
		case 5:
			statusClass = 'res_stat-checkin';
			break;
		case 6:
			statusClass = 'res_stat-partcheckout';
			break;
		case 7:
			statusClass = 'res_stat-checkout';
			break;
		case 2:
			statusClass = 'res_stat-cancelled';
			break;
		case 3:
			statusClass = 'res_stat-noshow';
			break;
		}
		$("#divStatus").addClass(statusClass);
		if(statusClass=="res_stat-confirmed" || statusClass == "res_stat-cancelled" || statusClass == "res_stat-noshow"){
			$scope.loadCutOffDate = false;
		}

	}).error(function (data, status, header, config) {
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Unreccognised error.\nPlease contact Administrator.').textContent(data).ok('Ok').parent(angular.element(document.body)));
	});
	
}
	
	$scope.noshow=function(){
		window.location = "../reservation_test/reservationNoShow?reservationNo=" + resvNo;	
	}

	$scope.changeResvDate=function() {
		window.location = "../reservation_test/changeArrivalDate?reservationNo=" + resvNo;
	}

	$scope.editResv=function() {
		window.location = "../reservation_test/reservationEdit?reservationNo=" + resvNo;
	}

	$scope.deposit=function() {
		window.location = "../deposit/depositEdit?reservationId=" + resvNo;
	}

	$scope.cancel=function() {
		window.location = "../reservation_test/resvCancellation?resrvId=" + resvNo;
	}

	$scope.confirm=function() {
		window.location = "../reservation/resvConfirm?reservationId=" + resvNo;
	}

	$scope.checkIn=function () {
		window.location = "../checkIn/checkInEdit?resvId=" + resvNo +"&currentLocation=reservation";
	}
	$scope.cancelPage=function(){
		window.location = "../reservation_test/reservationList";
	}
    
    $scope.pickupNeededChange = function () {
    	if($scope.pickupLocation == ""){
				$scope.pickupDeleteBtn=false;
			}else{
				$scope.pickupDeleteBtn=true;
			}
	  	  if($scope.pickupNeeded){
				  $("#pickup_div").show();
				  $scope.myForm.$dirty=true;
				  
	  	  } else{
	  		
				 $("#pickup_div").hide();  
	  	  		 $scope.myForm.$dirty=false;
	  	  }
	 
	  	
      };
	
      
      /* method to delete pickup information */
       
      $scope.deletePickUp = function () {
    	  $http({
				url : '../reservation_test/deletePickUp',
				method : "POST",
				params : {resvNo:resvNo}
			}).then(function(response) {
				
				
			});
      };
      
      $scope.deleteConfirm = function(){   

		  var confirm = $mdDialog.confirm()
			.title("Delete this Pickup?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
	$('.modal-dialog').hide();
	 	 
			}, function() {	
			
	            }); 	
			$scope.deletePickUp();	
	  }; 

      
	/* ************ cutof date popup model	 ************ */
	
	$scope.loadCutOffDate=function(){	
		$scope.cutOffForm.$dirty=false;
		$scope.cutOffForm.$setPristine();
		var minDate = $("#hotelDate").val();
		$scope.minDate = new Date(minDate);
		var arrDate = new Date($scope.datas.arrDate)
		$scope.maxDate = new Date(
				arrDate.getFullYear(),
				arrDate.getMonth(),
				arrDate.getDate()-1 );
		console.log("2"+$scope.datas.cutOffDate);
		
		$scope.cutOffDate = new Date($scope.datas.cutOffDate);
		$scope.rDtl.cutOffDate = $scope.cutOffDate.format("yyyy-m-dd");	
		
		$("#cutOffDateModal").modal({backdrop: "static"});
	}
	
	
	
	/* ************  cut off date update function	 ************ */
    $scope.updateCutOffDate   = function(){

    	
    	
    	if(isNaN(new Date($scope.cutOffDate).getTime()) ){		 
			$scope.showAlert("Alert","Select cut off date ");
			return false;
		}
    	
    	
  	  var cutOffData = $.param({
  	  		resvNO:resvNo,
  	  		cutOffDate:new Date($scope.cutOffDate).getTime()
  	  	});

  	  var cutFDate =  new Date($scope.cutOffDate);

  	  
  	  $http.post('../reservation/updateCutOffDate', cutOffData, config)
  	  .success(function (data, status, headers, config) {
  		  if(data==1){		
  			   
  			  $scope.datas.cutOffDate = ""+cutFDate.format("yyyy-m-dd");
  			  $scope.rDtl.cutOffDate =""+cutFDate.format("yyyy-m-dd");	
  			  $scope.showAlert("Alert","Cut off date updated successfully");			  		  
  			  $("#cutOffDateModal").modal('toggle');			  
  		  }else{
  			$scope.showAlert("Error message","Update failed");
  		  }	  
  	  }).error(function (data, status, header, config) {
  		     $scope.showAlert("Error message","Update failed");
  		});
  	  
    };
    
    $scope.printBookingVoucher = function(){
    	window.open("../reservation_test/printBookingVoucher?resvNo="+resvNo);
    }
	
	
    $scope.printGrc = function(){
    	window.open("../reservation_test/printGrc?resvNo="+resvNo);
    }
	
    /*  ************  pick up Popup model	 ************ */
	$scope.loadPickup=function(){
		$scope.getReservGuestData(resvNo);
		$scope.arrivalNewMinDate= new Date(new Date($scope.rDtl.arrDate).getFullYear(),new Date($scope.rDtl.arrDate).getMonth(),new Date($scope.rDtl.arrDate).getDate());

		$scope.myForm.$dirty=false;
		$scope.myForm.$setPristine();
		var pickUpData = $.param({
	  		resvNO:resvNo 		 
	  	});
		
		$http.post('../reservation_test/getPickUpData', pickUpData, config)
	  	  .success(function (data, status, headers, config) {
	  			if(data.pickupNeeded==1){
	  				$scope.datas.pickupNeeded = true;
	  				$scope.pickupDate = new Date(data.pickupDate);
	  				$scope.pickupLocation = data.pickupLocation;
	  				$scope.pickupSeats = data.pickupSeats;
	  				$scope.pickupRemarks = data.pickupRemarks;
	  				$("#pickup_time_txt").val(data.pickupTime);
	  				$("#pickup_div").show();
	  				
	  				
	  				if($scope.pickupLocation == ""){
	  					$scope.pickupDeleteBtn=false;
	  				}else{
	  					$scope.pickupDeleteBtn=true;
	  				}
	  			}else{
	  				$scope.pickupDeleteBtn=false;
	  				$("#pickup_div").hide();
	  				$scope.datas.pickupNeeded = false;
	  				console.log(data.pickupDate);
	  				
	  				if(data.pickupDate=="" || data.pickupDate ==null || data.pickupDate =="null"){
	  					$scope.pickupDate = new Date(data.arrDate);
	  				}else{
	  					$scope.pickupDate =new Date(data.pickupDate);
	  				}
	  				
	  				$scope.pickupLocation = data.pickupLocation;
	  				$scope.pickupSeats = data.pickupSeats;
	  				$scope.pickupRemarks = data.pickupRemarks;
	  				$("#pickup_time_txt").val(data.pickupTime);	  					
	  			}	   
	  		   
	  	  }).error(function (data, status, header, config) {
	  			alert("error="+data);
	  		});

		$("#pickupModal").modal({backdrop: "static"});
		$scope.pickupDeleteBtn=false;
	}
	
	
	
	/* ************  update pick up details	 ************ */
	$scope.updatePickUp=function(){		
		
		$scope.msgElm.addClass("changeColor");
		
		if($("#pickupNeeded").is(':checked')){
		
			if(isNaN(new Date($scope.pickupDate).getTime()) ){
				$scope.showAlert("Alert","Select pickup date ");
				return false;
			}
    		if($("#pickup_time_txt").val()=="" || $("#pickup_time_txt").val()==null){
    			
    			$scope.showAlert("Alert","Select Pickup Time ");
				return false;
    		}
    		
            if($scope.pickupLocation=="" || $scope.pickupLocation==null){
    			
    			$scope.showAlert("Alert","Select pickup location ");
    			
    			/*
    			var a = document.getElementById('pickup_location');
    	        a.style.border = '1px solid red';*/
    			
				return false;
    		}
            
            if($scope.pickupSeats=="" || $scope.pickupSeats==null || $scope.pickupSeats==0 || $scope.pickupSeats <=0 ){
    			
    			$scope.showAlert("Alert","Enter pickup seats ");
				return false;
    		}
            
            if($scope.pickupSeats > 99 ){
				 $scope.showAlert("Alert"," No. of packs exceeds maximum limit 99 ");
					return false;
	    		}
            
            
            if($scope.pickupRemarks=="" || $scope.pickupRemarks==null){
    			
    			$scope.showAlert("Alert","Enter pickup remarks ");
				return false;
    		}
         
		}
		
		
		var pickUpData = $.param({
	  		resvNO:resvNo,
	  		pickupNeeded:$scope.pickupNeeded,
	  		pickupDate:new Date($scope.pickupDate).getTime(),
	  		pickupLocation:$scope.pickupLocation,
	  		pickupSeats:$scope.pickupSeats,
		    pickupRemarks:$scope.pickupRemarks,
		    pickupTime:$("#pickup_time_txt").val()
	  	});
		
	
	    $http.post('../reservation/updatePickUpDetails', pickUpData, config)
  	    .success(function (data, status, headers, config) {
  		  if(data==1){		  
  			  $scope.showAlert("Alert","Pickup date updated successfully");		   
  			  $("#pickupModal").modal('toggle');		  
  		  }else{
  			$scope.showAlert("Error message","Update failed");
  		  }	  
  	    }).error(function (data, status, header, config) {
  	    	$scope.showAlert("Error message","Update failed");
  		}); 	
	}
	
	$(document).ready(function(){
		$(".md-datepicker-input").mouseover(function(){
			$('.md-datepicker-input').attr('readonly', true);
		});
	});
	
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
}]);


pmsApp.directive('daterangePickerbft', [function() {
	
//	function get_date_format() {
//		var datesaperator = settings['DateSeparator'];
//		var date = {};
//		day = moment().format("DD");
//		month = moment().format("MM");
//		year = moment().format("YYYY");
//		if (settings['DateFormat'] == "0") {
//			date = {
//					format : "DD" + datesaperator + "MM" + datesaperator + "YYYY",
//					mask : "d" + datesaperator + "m" + datesaperator + "y",
//					mindate : '' + day + datesaperator + (month) + datesaperator + year
//					+ ''
//			}
//		} else if (settings['DateFormat'] == "1") {
//			date = {
//					format : "MM" + datesaperator + "DD" + datesaperator + "YYYY",
//					mask : "m" + datesaperator + "d" + datesaperator + "y",
//					mindate : '' + (month) + datesaperator + (day) + datesaperator
//					+ year + ''
//			}
//		} else if (settings['DateFormat'] == "2") {
//			date = {
//					format : "YYYY" + datesaperator + "MM" + datesaperator + "DD",
//					mask : "y" + datesaperator + "m" + datesaperator + "d",
//					mindate : '' + year + datesaperator + (month) + datesaperator + day
//					+ ''
//			}
//		}
//		return date;
//	}
//	
	
	
	return {
		controller: function ($scope,$http,$filter) {

			return $scope;
		},
		link: function(scope, elem, attrs ,controller) {
			//var dateFormat = get_date_format();
			var date = new Date(),y = date.getFullYear(), m = date.getMonth();
			var curDate = new Date();

			var todayTimeStamp = +new Date; // Unix timestamp in milliseconds
			var oneDayTimeStamp = 1000 * 60 * 60 * 24 * 7; // Milliseconds in a day
			var diff = todayTimeStamp - oneDayTimeStamp;
			var yesterdayDate = new Date(diff);
			var yr=new Date().getFullYear()-10;

			//controller.pickupDate = '26/02/2018';
			$(elem).inputmask({
				//mask : dateFormat.mask,
				//placeholder : dateFormat.format,
				mask : 'd/m/y',
				placeholder : 'DD/MM/YYYY',
				alias : "date",
			});

			$(elem).daterangepicker({
				//"format" : dateFormat.format,
				"format" : 'DD/MM/YYYY',
				"drops" : "down",
				"autoclose": true,
				"singleDatePicker" : true,
				"showDropdowns" : true,
				"autoApply" : false,
				"linkedCalendars" : false,
				//"maxDate": dateFormat.mindate,
				"maxDate": '26/02/2018',

				"minDate": new Date(yr, 0, 1)
			}, function(start, end, label) {
			});
		}
	};
}]);


