pmsApp.controller('auditPage4Controller', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	$scope.txnList;
	$scope.folioList=[];
	$scope.outputFolio=[];
	$('#imgloader').show();
	$http({
		method: "GET",
		url:"../nightAudit/getNATransactions"
	}).then(function(response) {
		$('#imgloader').hide();
		$scope.txnList = response.data;
		 angular.forEach($scope.txnList, function(item) {
	          var key = item['folioNo'];
	          if($scope.folioList.indexOf(key) === -1) {
	        	  $scope.folioList.push(key); 
	        	  $scope.outputFolio.push(item);
	          }
	      });
	});
	
	$scope.next=function(){
		$window.location.href = "../nightAudit/finishNightAudit";
	}
	
}]);