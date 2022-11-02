pmsApp.directive('allowOnlyNumbers', function () {  
    return {  
        restrict: 'A',  
        link: function (scope, elm, attrs, ctrl) {  
            elm.on('keydown', function (event) {  
                if (event.which == 64 || event.which == 16) {  
                    // to allow numbers  
                    return false;  
                } else if (event.which >= 48 && event.which <= 57) {  
                    // to allow numbers  
                    return true;  
                } else if (event.which >= 96 && event.which <= 105) {  
                    // to allow numpad number  
                    return true;  
                } else if ([8,190,110].indexOf(event.which) > -1) {  
                    // to allow backspace, dot
                    return true;  
                } else {  
                    event.preventDefault();  
                    // to stop others  
                    return false;  
                }  
            });  
        }  
    }  
});  
pmsApp.controller('serviceTaxCtrl', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	$scope.srvTax=null;
	$http({
		method: "POST",
		url:"../serviceTax/getServiceTaxDetails"
	}).then(function(response) {
		$scope.srvTax = response.data;
	});

	$scope.$watch("serviceTaxForm.$dirty", function(newValue) {
		$scope.invIsDirty = false || newValue;
	});
	$scope.save = function(){

		if($scope.invIsDirty){
			var confirm = $mdDialog.confirm()
			.title("Alert")
			.textContent("Do you want to save changes you have made?").ok('Yes').cancel('No').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function() {
				var data = $.param({
					servicetax:JSON.stringify($scope.srvTax)
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../serviceTax/save', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						$mdDialog.show($mdDialog.alert()
								.clickOutsideToClose(true).title('saved successfully').ok('Ok').parent(angular.element(document.body)));
					}else{alert("error");}
				}).error(function (data, status, header, config) {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Unreccognised error.\nPlease contact Administrator.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			});
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('saved successfully').ok('Ok').parent(angular.element(document.body)));
		}	

	}

	$scope.removeCess=function(row){
		switch(row) {
		case 1:
			$scope.srvTax.cess1_pc=0;
			$scope.srvTax.cess1Name="";
			break;
		case 2:
			$scope.srvTax.cess2_pc=0;
			$scope.srvTax.cess2Name="";
			break;
		case 3:
			$scope.srvTax.cess3_pc=0;
			$scope.srvTax.cess3Name="";
			break;
		case 4:
			$scope.srvTax.cess4_pc=0;
			$scope.srvTax.cess4Name="";
			break;
		case 5:
			$scope.srvTax.cess5_pc=0;
			$scope.srvTax.cess5Name="";
			break;
		}

		$scope.invIsDirty=true;
	}
}]);