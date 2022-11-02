pmsApp.controller('auditPage3Controller', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	var page3=this;	
	$scope.inHouse;
	$scope.rateDetails;
	var cur=$("#currency").val();
	page3.loadDataTable=loadDataTable;
	page3.next=next;
	$('#imgloader').show();
	$http({
		method: "GET",
		url:"../nightAudit/inHouseList"
	}).then(function(response) {
		$scope.inHouse = response.data;	
		if($scope.inHouse.length==0){
			var confirm = $mdDialog.confirm()
			.title("No postings available")
			.textContent("Night Audit process completed.").ok('Ok').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function() {
				$window.location.href = "../nightAudit/finishNightAudit";
			});
		}
		$('#imgloader').hide();
	});
	function loadDataTable(checkinNo){
		$scope.checkinNo=parseInt(checkinNo);
		$('#imgloader').show();

		var data = $.param({
			checkinNo : $scope.checkinNo
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}}
		$http.post('../nightAudit/getCurrentRoomRate', data, config)
		.success(function (data, status, headers, config) {
			$('#imgloader').hide();
			$("#txnInfomyModal").modal({backdrop: "static"});
			$scope.discType="%";
			$scope.taxInc="(inc)";
			$scope.todaysPostings=data.txnToday;
			$scope.rateDetails=data.expected;
			if($scope.rateDetails.isTaxInc==0){
				$scope.taxInc="(not inc)";
			}
			$scope.totalAmount=$scope.rateDetails.finalRoomCharge;
			if(	$scope.rateDetails.disc!=null && $scope.rateDetails.disc!=" "){
				if($scope.rateDetails.isDiscPc!=1){
					$scope.discType=cur;
				}
					$scope.totalAmount=$scope.rateDetails.finalRoomCharge-$scope.rateDetails.finalDiscAmt-$scope.rateDetails.disc_tax;
			}
			
			$scope.rate={roomNum:$scope.rateDetails.roomNum,roomCharge:Math.round($scope.rateDetails.roomCharge * 100) / 100,tax:(Math.round($scope.rateDetails.tax * 100) / 100)-$scope.rateDetails.disc_tax,nettAmount:Math.round($scope.totalAmount* 100) / 100,disc:$scope.rateDetails.disc,roomType:$scope.rateDetails.roomTypeCode,rateCode:$scope.rateDetails.rateCode,discType:$scope.discType,taxIn:$scope.taxInc,serviceCharge:Math.round($scope.rateDetails.serviceCharge* 100) / 100};
		})
		.error(function (data, status, header, config) {
			$('#imgloader').hide();
			alert("error="+data);
		});
	}
	
	function next(){
		if($scope.cnfrmChkBox==true){
			$('#imgloader').show();
			$http({
				method: "GET",
				url:"../nightAudit/postNightAudit"
			}).then(function(response) {
				$('#imgloader').hide();
				if(response.data.substring(7)=='success' || response.data.substring(7)=="posting"){
					$window.location.href = "../nightAudit/getWizardPage4";
				}else{
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent("Operation failed.").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
				}
			});
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please confirm before proceeding transactions").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
		}
	}
	$scope.cancelPopUp=function(){
		$("#txnInfomyModal").modal('toggle');	
	}
}]);