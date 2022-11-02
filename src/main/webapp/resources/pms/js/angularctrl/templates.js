pmsApp.requires.push('textAngular');
pmsApp.controller('templateCtrl', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	$scope.invoice=null;
	$scope.emailTmp=null;
	$scope.smsTmp=null;
	$scope.invIsDirty=false;
	$("#deleteEmailTemp").hide();
	$http({
		method: "GET",
		url:"../templates/getTemplateDetails"
	}).then(function(response) {
		$scope.invoice = JSON.parse(response.data.invoice);
		$scope.emailTmp=JSON.parse(response.data.email);
		$scope.smsTmp=JSON.parse(response.data.sms);
	});

	$scope.$watch("invoiceForm.$dirty", function(newValue) {
		$scope.invIsDirty = false || newValue;
	});
	$scope.save = function(){
		if($scope.invIsDirty){
			var data = $.param({
				invoice:JSON.stringify($scope.invoice)
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../templates/saveInvoice', data, config)
			.success(function (data, status, headers, config) {
				if(data.substring(7)=='success'){
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Saved successfully').ok('Ok').parent(angular.element(document.body)));
				}else{alert("error");}
			}).error(function (data, status, header, config) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Saved successfully').ok('Ok').parent(angular.element(document.body)));
		}		
	}


	//email template
	$scope.saveEmail = function(){
		$scope.emailPurposeChk=0;
		 angular.forEach($scope.emailTmp, function(item) {
	          if($scope.emailTempl.purpose == item['purpose'] && $scope.emailTempl.id==0) {
	        	  $scope.emailPurposeChk=1;    	  
	          }
	      });
		if($scope.emailPurposeChk==0){
		var data = $.param({
			email:JSON.stringify($scope.emailTempl)
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../templates/saveEmail', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='success'){
				$("#newEmailTemplmyModal").modal('toggle');
				var confirm = $mdDialog.confirm()
				.title("Saved successfully").ok('OK').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function(){
					window.location  = "";
				});
			}else{alert("error");}
		}).error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Template for the selected purpose already exists. Please choose another purpose').ok('Ok').parent(angular.element(document.body)));
		}
	}

	$scope.newEmailTemplate = function(){
		$scope.emailTempl={id:0,code:"",purpose:"1",group:"1",subject:"",is_active:true,content:""};
		$("#newEmailTemplmyModal").modal({backdrop: "static"});
	}

	$scope.showEmailPop = function(email){
		$scope.emailTempl=email;
		$scope.emailTempl.purpose=$scope.emailTempl.purpose.toString();
		$scope.emailTempl.group=$scope.emailTempl.group.toString();
		$("#deleteEmailTemp").show();
		$("#newEmailTemplmyModal").modal({backdrop: "static"});	
	}
	$scope.deleteEmail = function(){
		var data = $.param({
			id:$scope.emailTempl.id
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../templates/deleteEmail', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='success'){
				$("#newEmailTemplmyModal").modal('toggle');
				var confirm = $mdDialog.confirm()
				.title("Deleted successfully").ok('OK').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function(){
					window.location  = "";
				});
			}else{alert("error");}
		}).error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});
	}
	
	$scope.cancelEmailPopUp = function(){
		$("#newEmailTemplmyModal").modal('toggle');	
	}
	
	//sms template
	$scope.saveSms = function(){
		$scope.smsPurposeChk=0;
		 angular.forEach($scope.smsTmp, function(item) {
	          if($scope.smsTempl.purpose == item['purpose'] && $scope.smsTempl.id==0) {
	        	  $scope.smsPurposeChk=1;    	  
	          }
	      });
		if($scope.smsPurposeChk==0){
		var data = $.param({
			sms:JSON.stringify($scope.smsTempl)
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../templates/saveSms', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='success'){
				$("#newSmsTemplmyModal").modal('toggle');
				var confirm = $mdDialog.confirm()
				.title("Saved successfully").ok('OK').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function(){
					window.location  = "";
				});
			}else{alert("error");}
		}).error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Template for the selected purpose already exists. Please choose another purpose').ok('Ok').parent(angular.element(document.body)));
		}
	}

	$scope.newSmsTemplate = function(){
		$scope.smsTempl={id:0,code:"",purpose:"1",group:"1",is_active:true,content:""};
		$("#newSmsTemplmyModal").modal({backdrop: "static"});
	}

	$scope.showSmsPop = function(sms){
		$scope.smsTempl=sms;
		$scope.smsTempl.purpose=$scope.smsTempl.purpose.toString();
		$scope.smsTempl.group=$scope.smsTempl.group.toString();
		$("#deleteSmsTemp").show();
		$("#newSmsTemplmyModal").modal({backdrop: "static"});	
	}
	$scope.deleteSms = function(){
		var data = $.param({
			id:$scope.smsTempl.id
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}}
		$http.post('../templates/deleteSms', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='success'){
				$("#newSmsTemplmyModal").modal('toggle');
				var confirm = $mdDialog.confirm()
				.title("Deleted successfully").ok('OK').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function(){
					window.location  = "";
				});
			}else{alert("error");}
		}).error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok!').parent(angular.element(document.body)));
		});
	}
	$scope.cancelSmsPopUp = function(){
		$("#newSmsTemplmyModal").modal('toggle');	
	}
}]);