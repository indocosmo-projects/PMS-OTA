$(document).ready(function(){
2
  $("#logId").keydown(function(event) {
3
     if (event.keyCode == 32) {
4
         event.preventDefault();
5
     }
6
  });
7
});



pmsApp.controller('userController', ['$scope','$q','$http','$mdDialog','$window','DTOptionsBuilder','DTColumnBuilder',function ($scope,$q,$http,$mdDialog,$window,DTOptionsBuilder,DTColumnBuilder){
	var us=this;
	$scope.userGroups;
	var gId,gCode,gName,gDesc,gAction;
	$("#delete_btn").hide();
	onloadValidator(".validator");	
	OnloadValidationEdit('.validator');	
	us.dtInstance = {};
	
	 
	
	var urlString="../users/getUserList";
	us.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString).then(function(result) {
			defer.resolve(result.data);	       
		});
		return defer.promise;
	}).withPaginationType('full_numbers').withOption('rowCallback', rowCallback);
	us.dtColumns = [DTColumnBuilder.newColumn("loginId","Login Id").renderWith(function(data, type, full) { return '<a style="color:#0000ff;">'+data+'</a>';}),
	                DTColumnBuilder.newColumn("name","Name"),
	                DTColumnBuilder.newColumn("email","email"),
	                DTColumnBuilder.newColumn("userGroup","User Group"),
	                DTColumnBuilder.newColumn("isActive","Active").renderWith(function(data, type, full) { 
	                	if(data==true){
	                 		return "<i style='color:#73C857' class='fa fa-check'></i>"
	                 	}
	                 	else{
	                 		return "<i style='color:#EA4335' class='fa fa-close'></i>"
	                 	}
	                })];

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		$('td', nRow).unbind('click');
		$('td',nRow).bind('click', function() {
			if(window.cp_isCanEdit){
			$scope.$apply(function() {	
				$scope.master={id:aData.id,logId:aData.loginId,
						name:aData.name,
						userGroup:aData.userGroupId,
						baction:'edit',
						password:aData.password,
						email:aData.email,
						isCashier:aData.isCashier,
						disbl:true,
						showpass:false,
						status:aData.isActive};
				$scope.reset();
				resetValidators('.validator');
				$("#delete_btn").show();
				$("#usermyModal").modal({backdrop: "static"});
			});
			}
			
		})		
		return nRow;
	}
	$scope.reset = function() {
		resetValidators('.validator');
		$scope.user = angular.copy($scope.master);
	};
	$scope.reset();

	$scope.newUser = function(){
		$("#delete_btn").hide();
		resetValidators('.validator');
		$scope.master={id:0,logId:"",name:"",userGroup:1,baction:'save',email:"",password:"",disbl:false,showpass:true,status:true,isCashier:true};
		$scope.reset();
		$("#usermyModal").modal({backdrop: "static"});	
	}
	$scope.cancelPopUp = function(){
		$("#usermyModal").modal('toggle');	
	}

	
	function validateEmail(emailField){
        var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

        if (reg.test(uEmail) == false) 
        {
        	
        	$mdDialog.show($mdDialog.alert()
    				.clickOutsideToClose(true).title('Alert').textContent("Invalid Email Address").ok('Ok').parent(angular.element(document.body)));	
            
            return false;
        }

        return true;

}
	
	
	
	$scope.save = function(){
		if (validation('.validator') == "false") {
			return false;
		} else {
			uId=$scope.user.id;
			uLoginId=$scope.user.logId;
			uName=$scope.user.name;
			uGroup=$scope.user.userGroup;
			uEmail=$scope.user.email;
			uPassword=$scope.user.password;
			gAction=$scope.user.baction;
			gStatus=$scope.user.status;
			gisCashier=$scope.user.isCashier;
			
			
			if(gAction=='save'){
				 if(validateEmail(uEmail)){
				var data = $.param({
					uLoginId : uLoginId,
					uName : uName,
					uGroup : uGroup,
					uEmail : uEmail,
					uPassword : uPassword,
					gStatus : gStatus,
					gisCashier:gisCashier
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../users/save', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						us.dtInstance.reloadData();
						$("#usermyModal").modal('toggle');
					}else{alert("error");}
				}).error(function (data, status, header, config) {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			}
			
			}
			 else if(gAction=='edit'){
				 if(validateEmail(uEmail)){
				var data = $.param({
					uId : uId,
					uLoginId : uLoginId,
					uName : uName,
					uGroup : uGroup,
					uEmail : uEmail,
					uPassword : uPassword,
					gStatus : gStatus,
					gisCashier:gisCashier
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../users/edit', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						us.dtInstance.reloadData();
						$("#usermyModal").modal('toggle');
					}else{alert("error");}
				}).error(function (data, status, header, config){
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			}
		
		}
		}
	}

	$scope.deleteUser = function(){
		$("#usermyModal").modal('toggle');
		var confirm = $mdDialog.confirm()
		.title("Alert")
		.textContent("Are you sure you want to delete this User?").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
		var data = $.param({
			uId : $scope.user.id
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../users/delete', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='success'){
				us.dtInstance.reloadData();
			}else{alert("error");}
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});
		});
	}

	$http({
		method: "GET",
		url:"../userGroup/getUserGroupList"
	}).then(function(response) {
		$scope.userGroups = response.data;	
		if($scope.userGroups.length==0){
			var confirm = $mdDialog.confirm()
			.title("No User Groups available.")
			.textContent("Define User Group to add new users.").ok('Ok').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function() {
				$window.location.href = "../userGroup/list";
			});
		}	
	});
	$("#logId").bind( "focusout",function(){
		var data = $.param({
			userId : $scope.user.logId
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../users/userExist', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='exists'){
				//$("#usermyModal").modal('toggle');	
				var confirm = $mdDialog.confirm()
				.title("Alert")
				.textContent("Login Id exists. Please use a different login id").ok('Ok').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function() {
					$("#usermyModal").modal({backdrop: "static"});
				});
				$scope.user.logId='';
				$('#logId_check').hide();
				$('#logId_warning').show();
			}else{
				if($scope.user.logId!=''){
				$('#logId_check').show();
				$('#logId_warning').hide();}}
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});	
	});
	$('#txtUserPassword').on('click', function () {
		$(this).attr('type', 'password'); 
	});
}]);