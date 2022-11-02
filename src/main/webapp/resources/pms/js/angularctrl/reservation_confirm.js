pmsApp.controller('reservation_confirmController', function($scope,$http,$window,$mdDialog) {
	
		$scope.pageName       = "Deposit";		 			 
		$scope.sortType     = 'txn_date'; // set the default sort type
		$scope.sortReverse  = false;  // set the default sort order
		  	   
	    //numericalValidation()
	    $scope.timeFormat = function(txn_time){
	    	 
	         return new Date(txn_time);	
	         }
	    $scope.showDeposit = function(folioBindNo, sortCol, sortStatus) {
			   
			 $http.get("../deposit/paymentList?folioBindNo="+folioBindNo+"&sortCol=&sortStatus=").success(function(response) {
				 $scope.depositList = response;      	  
		  	  });
			 
			// call bootstrap model
			$("#myModal").modal({backdrop:"static"});	 
				 
		 }
	     
		 $scope.showAlert = function(msg){    
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Alert').textContent(msg).ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
			};
			
			
			
			
			$scope.confirmValidation=function(){
				if ($('input.confirmation').prop('checked')) {
					var confirmedBy=$('#confirmBy').val();
					var confirmReference=$('#reference').val();
					var reserId=$('#reser_id').html();
					
					var confirmedObject=new Object();
					confirmedObject.confirmedBy=confirmedBy;
					confirmedObject.confirmReference=confirmReference;
					confirmedObject.reserId=reserId;
					
					
					var confirmationDetails= JSON.stringify(confirmedObject);
					$.ajax({
						async:false,
						url : '../reservation/confirmSubmit',
						type : 'POST',
						data : {'confirmationDetails' : confirmationDetails},
						success : function(response) {
							var msg = response.substring(0).split("_");
							if(msg[0]=="success"){
								var confirm = $mdDialog.alert()
								.title("Confirmation done successfully").ok('Ok').parent(angular.element(document.body));
								$mdDialog.show(confirm).then(function(){
									window.location  = "../reservation_test/tools?reservationNo="+reserId;
									$http({
										url:'../reservation/mailReservationConfirm',
										method:'POST',
										params:{reservationNo:msg[1]}
									}).then(function(response){
										/*window.location  = "../reservation_test/tools?reservationNo="+reserId;*/
									})
								})
							}
//						$scope.backToList();
						}
					});
				}else{
					
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Please confirm before proceeding ').ok('Ok').parent(angular.element(document.body)));
				}
			}
	     
//	     //showDeposit()	
//		 $scope.showDeposit = function(resvId, sortCol, sortStatus) {
//			 
//			 $http.get("../deposit/paymentList?resvNo="+resvId+"&sortCol=&sortStatus=").success(function(response) {
//				 
//				 $scope.depositList = response;      	  
//		  	  });
//			 
//			 // call bootstrap model pop-up
//			 $("#myModal").modal({backdrop:"static"});	 
//				 
//		 }
	});




 

 
  