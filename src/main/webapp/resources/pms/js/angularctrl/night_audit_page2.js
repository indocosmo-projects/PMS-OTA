
pmsApp.controller('auditPage2Controller', ['$scope','$http','$mdDialog','$window','$filter',function ($scope,$http,$mdDialog,$window,$filter){
	var dt=$('#currHdate').val();
	$scope.count=window.countShift;
	
	$scope.countshiftRemain=window.closecount;
	 $scope.myDate = new Date(dt);
	 $scope.newHotDate = new Date(
		      $scope.myDate.getFullYear(),
		      $scope.myDate.getMonth(),
		      $scope.myDate.getDate()+1);
	 
	  $scope.minDate = new Date(
	      $scope.myDate.getFullYear(),
	      $scope.myDate.getMonth() - 2,
	      $scope.myDate.getDate());

	  $scope.maxDate = new Date(
	      $scope.myDate.getFullYear(),
	      $scope.myDate.getMonth() + 2,
	      $scope.myDate.getDate());
	 

	  $scope.onlyWeekendsPredicate = function(date) {
	    var day = date.getDay();
	    return day === 0 || day === 6;
	  };
	

	  
//	  $scope.isds=function(){
//	  var value=false;
//		  
//
//	  if($scope.count==0){
//		  $scope.accptDate=true;
//			  value=true;
//	  return value;
//		  
//	  }else{
//			  $scope.accptDate=false;
//			  value=false;
//			  return value;
//		  
//		  }
//	  
//	  }
//  
//	  
	  
	  
//  $scope.dateAccept=function(){
//	  if($scope.count!=0){
//			  var confirm = $mdDialog.confirm()
//		.title("Please close the shift").ok('OK').parent(angular.element(document.body));
//				$mdDialog.show(confirm).then(function(){
//				window.location  = "../shiftManagement/openshift";
//			});
//	  }
//	  
//  }
	  
	  
	$scope.next= function(){
		if($scope.accptDate==true){
			
//			if($scope.count!=0 || $scope.countshiftRemain==0){
				
		$window.location.href = "../nightAudit/getWizardPage3?newHotelDate="+$filter('date')(new Date($scope.newHotDate), "yyyy-MM-dd");	
//			}else{
//
//				var confirm = $mdDialog.confirm()
//				.title("Please open the shift").ok('OK').parent(angular.element(document.body));
//			$mdDialog.show(confirm).then(function(){
//					window.location  = "../shiftManagement/openshift";
//			});
//			}
		
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please confirm before before proceeding").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
		}		
	}
}]);
