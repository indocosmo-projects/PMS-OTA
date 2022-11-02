//pmsApp.controller('shiftCtrl',['$scope','$http','DTOptionsBuilder','DTColumnBuilder','$mdDialog','$filter',function($scope,$http,DTOptionsBuilder,DTColumnBuilder,$mdDialog,$filter){
//	onloadValidator(".validator");	
//	OnloadValidationEdit('.validator');	
//	$scope.dtInstance = {};
//	$scope.shift = {};
//
//
//$scope.newCloseShift = function(){
//	
//	
//	resetValidators('.validator');
//	$("#saveCloseShiftBtn").show();
//	$scope.closeShift={passWord:"",baction:"saveUpdate",disbl:false};
//	
//	$("#newCloseShiftmyModal").modal({backdrop: "static"});
//}
//
//$scope.cancelPopUp =function(){
//	$("#newCloseShiftmyModal").modal('toggle');
//}
//
//$http({
//	method: "POST",
//	url:"../shiftManagement/getcurrentUserShift"
//}).then(function(response) {
//	$scope.currentShifts = response.data;	
//	if($scope.currentShifts.length==0){
//		var confirm = $mdDialog.confirm()
//		.title("No Shifts Available !")
//		.textContent("Define Shifts ").ok('Ok').parent(angular.element(document.body));
//	}	
//});
//
//$scope.saveUpdate=function(){
//	if (validation('.validator') == "false") {
//		return false;
//	}else{
//		$scope.save={id:$scope.currentShifts[0].usershiftid,user_id:$scope.currentShifts[0].user_id,shift_id:$scope.currentShifts[0].shift_id,opening_float:$scope.currentShifts[0].opening_float,opening_time:$scope.currentShifts[0].opening_time,opening_date:$scope.currentShifts[0].opening_date,passWord:$scope.password,baction:"saveUpdate",disbl:false};
//		$scope.bAction=$scope.closeShift.baction;
//		if($scope.bAction=='saveUpdate'){
//			var data = $.param({
//				currentShiftJson:JSON.stringify($scope.save)
//			});
//			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
//			$http.post('../shiftManagement/currentShiftupdate', data, config)
//			.success(function (data, status, headers, config) {
//					if(data.substring(7)=='success'){
//						
//						$("#newCloseShiftmyModal").modal('toggle');
//						var confirm = $mdDialog.confirm()
//						.title("Shift Closed Successfully").ok('OK').parent(angular.element(document.body));
//						$mdDialog.show(confirm).then(function(){
//							window.location  = "../shiftManagement/closeshift";
//						});
//					}else{alert("error!!"+data);}
//				}).error(function (data, status, header, config) {
//					$mdDialog.show($mdDialog.alert()
//							.clickOutsideToClose(true).title('Some Error Occured !!').textContent(data).ok('Ok!').parent(angular.element(document.body)));
//				});
//		}
//	}
//}
//}]);