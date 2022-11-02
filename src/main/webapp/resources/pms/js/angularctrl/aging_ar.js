
var agingApp = angular.module('agingApp', ['ngMaterial']);
agingApp.controller('agingARController', ['$scope','$http','$mdDialog','$window', '$timeout', function ($scope,$http,$mdDialog,$window, $timeout){
	
	$scope.getAgingARList = function(index){
		$http({
			url : "../agingAR/agingARList",
			method : "POST"
		}).then(function(response){
			$scope.agingARList = [];
			$scope.agingARList = response.data;
		/*for(var i=0; i<$scope.agingARList.length; i++){
			$scope.bal30 = $scope.agingARList[i].balance30;
			$scope.bal60 = $scope.agingARList[i].balance60;
			$scope.bal90 = $scope.agingARList[i].balance90;
		}*/
			$scope.getInvoiceDetails(index,startday,endday);
			
			});
		}
	
	
$scope.getInvoiceDetails = function(index, id){
		
		$scope.corporateName = $scope.agingARList[index].corporate_name;
		$scope.corporate_id = $scope.agingARList[index].corporate_id;
		$scope.id = id;
				
		$scope.aginARDetails = [];
		$http({
			url : "../agingAR/agingARDetails",
			method : "POST",
			params: {corporate: {corporate_id:$scope.corporate_id, id: $scope.id}}
		}).then(function(response){
			$scope.invoiceDetails = [];
			$scope.invoiceDetails = response.data;
		});
			
	}
	
/*	$scope.printSummary = function(){
		window.open('../agingAR/printSummary');
	}
	
	$scope.print = function(){
		window.open('../agingAR/print');
	}*/
	
	$scope.reportPrint = function(){
		window.open('../agingAR/reportPrint');
	}
	
	
}]);