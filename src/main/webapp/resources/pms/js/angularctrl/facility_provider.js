
pmsApp.controller('facilityCtrl', ['$scope','$http','DTOptionsBuilder','DTColumnBuilder','$mdDialog',function ($scope, $http, DTOptionsBuilder, DTColumnBuilder,$mdDialog){
	onloadValidator(".validator");	
	OnloadValidationEdit('.validator');	
	$scope.dtInstance = {};
	$scope.codeCheck={};
	var facilityTypes=window.facilityTypeEnums;
	$scope.dtColumns = [
	                    DTColumnBuilder.newColumn("code","Code").renderWith(function(data, type, full) {$scope.codeCheck[full.id]=full.code; return '<a style="color:#0000ff;">'+data+'</a>';}),
	                    DTColumnBuilder.newColumn("name","Name"),
	                    DTColumnBuilder.newColumn("phone","Phone"),
	                    DTColumnBuilder.newColumn("facilityType","Facility").renderWith(function(data, type, full) { return facility(data,full);})
	                    ];
	$scope.dtOptions = DTOptionsBuilder.newOptions().withOption('ajax', {
		url: "../facilityProvider/facilityProviderDetails",
		type: "POST",
		error: function(response){
            $mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(response.responseText).ok('Ok').parent(angular.element(document.body)));
		}
	}).withPaginationType('full_numbers').withDisplayLength(10).withOption('rowCallback', rowCallback);

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		if(window.cp_isCanEdit){
			$('td', nRow).unbind('click');
			$('td',nRow).bind('click', function() {
				$scope.$apply(function() {
					resetValidators('.validator');
					$("#deleteProviderBtn").show();
					$scope.facilityProvider={id:aData.id,code:aData.code,name:aData.name,facilityType:aData.facilityType.toString(),description:aData.description,email:aData.email,phone:aData.phone,baction:"edit",disbl:true};
					$("#newProvidermyModal").modal({backdrop: "static"});
				})
			})
		}
	}

	function facility(data,full){
		var type="";
		type=facilityTypes[data];
		return type;
	}
	function validateEmail(emailField){
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (reg.test($scope.facilityProvider.email) == false) 
        {
        	$mdDialog.show($mdDialog.alert()
    				.clickOutsideToClose(true).title('Alert').textContent("Invalid Email Address").ok('Ok').parent(angular.element(document.body)));	
            return false;
        }

        return true;

}
	$scope.newProvider = function(){
		resetValidators('.validator');
		$("#deleteProviderBtn").hide();
		$("#saveProviderBtn").show();
		$scope.facilityProvider={id:0,code:"",name:"",facilityType:"1",description:"",email:"",phone:"",baction:"save",disbl:false};
		$("#newProvidermyModal").modal({backdrop: "static"});
	}

	$scope.save= function(){
		if (validation('.validator') == "false") {
			return false;
		}if($scope.facilityProvider.phone.length<10){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent('Invalid phone number').ok('ok').parent(angular.element(document.body)));
		}else if(validateEmail($scope.facilityProvider.email)){
			$scope.bAction=$scope.facilityProvider.baction;
			if($scope.bAction=='save'){
				var data = $.param({
					providerJson:JSON.stringify($scope.facilityProvider)
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../facilityProvider/save', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						$scope.dtInstance.reloadData();
						$("#newProvidermyModal").modal('toggle');
					}else{alert("error"+data);}
				}).error(function (data, status, header, config) {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});

			}else if($scope.bAction=='edit'){
				var data = $.param({
					providerJson:JSON.stringify($scope.facilityProvider)
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../facilityProvider/update', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						$scope.dtInstance.reloadData();
						$("#newProvidermyModal").modal('toggle');
					}else{alert("error");}
				}).error(function (data, status, header, config){
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			}
		}
	}

	$scope.deleteProvider = function(){
		var confirm = $mdDialog.confirm()
		.title("Alert")
		.textContent("Are you sure you want to delete this Facility?").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			var data = $.param({
				id : $scope.facilityProvider.id
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../facilityProvider/delete', data, config)
			.success(function (data, status, headers, config) {
				if(data.substring(7)=='success'){
					$scope.dtInstance.reloadData();
					$("#newProvidermyModal").modal('toggle');
				}else{alert("error");}
			}).error(function (data, status, header, config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
		});
	}

	$("#code").bind( "focusout",function(){
		var code=$scope.facilityProvider.code
		var stat="notExist";
		for (var id in $scope.codeCheck) {
			if ($scope.codeCheck.hasOwnProperty(id)) {
				if($scope.codeCheck[id]==code){
					stat="exists";
				}
			}
		}
		if(stat=='exists'){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Code exists").ok('Ok').parent(angular.element(document.body)));
			$scope.facilityProvider.code='';
			$('#code_check').hide();
			$('#code_warning').show();
		}else{
			$('#code_check').show();
			$('#code_warning').hide();
		}
	});
	$scope.cancelPopUp =function(){
		$("#newProvidermyModal").modal('toggle');
	}
}]);