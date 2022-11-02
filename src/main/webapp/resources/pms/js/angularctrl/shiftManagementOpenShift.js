pmsApp.controller('shiftCtrl',['$scope','$http','DTOptionsBuilder','DTColumnBuilder','$mdDialog','$filter','$window',function($scope,$http,DTOptionsBuilder,DTColumnBuilder,$mdDialog,$filter,$window){
	onloadValidator(".validator");	
	OnloadValidationEdit('.validator');	
	$scope.dateFormat = $("#dateFormat").val();
	$scope.dtInstance = {};
	$scope.OpenShift = {};
	$scope.passWord={};
	$scope.passWrd={};
	$scope.hotelDate=$filter('date')(window.hotelDate,'yyyy-MM-dd HH:mm:ss');
	$scope.loginId=window.loginId;
	$scope.openshifBtnAction=false;
	var urlString="../nightAudit/expDepartDetails";	
	$http.get(urlString).then(function(result) {
		//defer.resolve(result.data);
		$scope.checkOutStatus=result.data.length;
	});
	$scope.newOpenShift = function(){
		resetValidators('.validator');
		if($scope.Shifts == ""){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Sorry. No Shift Available').ok('Ok').parent(angular.element(document.body)));
		}else{
			$("#saveOpenShiftBtn").show();
			$scope.OpenShift={id:0,Shifts:"",floatingamount:"",passWrd:"",baction:"save",disbl:false};

			$("#newOpenShiftmyModal").modal({backdrop: "static"});
		}
	}

	$scope.cancelPopUpOpen =function(){
		$("#newOpenShiftmyModal").modal('toggle');
	}

	$http({
		method: "POST",
		url:"../shiftManagement/currentShiftDetails"
	}).then(function(response) {
		$scope.Shifts = response.data;	
		/*		$scope.Shifts.splice(0,0,{id : "" ,code : "SELECT"});
		 */		
	});

	$http({
		method:"POST",
		url:"../shiftManagement/getListShift"
	}).then(function(response) {
		$scope.ShiftLIst=response.data;
	});

	$http({
		method:"POST",
		url:"../shiftManagement/allShiftDetails"
	}).then(function(response){
		$scope.AllShift=response.data
	});

	$scope.saveShift=function(){
		
		if($scope.OpenShift.Shifts == "" || $scope.OpenShift.Shifts == undefined){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Select Shift to Open').ok('Ok').parent(angular.element(document.body)));
		}
		else if($scope.passWrd=="" || $scope.passwrd == undefined){
			$("#msg").show();
			$("#passWrd").click(function(){
				$("#msg").fadeOut();
			});
		}
		else if($scope.floatingamount=="" || $scope.floatingamount==undefined){
			$("#msg3").show();
			$("#floatingamount").click(function(){
				$("#msg3").fadeOut();
			});
		}
		else{
			$scope.save={Shifts:$scope.OpenShift.Shifts,floatingamount:$scope.floatingamount,passWord:$scope.passwrd,baction:"save",disbl:false}
			$scope.bAction=$scope.OpenShift.baction;
			if($scope.bAction=='save'){
				var data = $.param({
					openShiftJson:JSON.stringify($scope.save)
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../shiftManagement/openShiftsave', data, config)
				.success(function (data, status, headers, config) {
					if(data.tablDta != null){
						$("#newOpenShiftmyModal").modal('toggle');
						$scope.ShiftLIst=JSON.parse(data.tablDta);
						var confirm = $mdDialog.alert()
						.title("Shift opened successfully").ok('OK').parent(angular.element(document.body));
						$mdDialog.show(confirm).then(function(){
							window.location = "";
						});
					}
					else{
						if(data=="status_error_password"){
							$mdDialog.show($mdDialog.alert()
									.clickOutsideToClose(true).title('Incorrect Password').ok('Ok').parent(angular.element(document.body)));
						}else if(data=="status_error_shiftOpen"){
							$mdDialog.show($mdDialog.alert()
									.clickOutsideToClose(true).title('Shift already opened. Please refresh.').ok('Ok').parent(angular.element(document.body)));
						}

					}
				}).error(function (data, status, header, config) {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			}
		}
	}
	$scope.newCloseShift = function(){
		resetValidators('.validator');
		$scope.password = "";
		$("#saveCloseShiftBtn").show();
		$scope.closeShift={passWord:"",baction:"saveUpdate",disbl:false};
		$("#newCloseShiftmyModal").modal({backdrop: "static"});
	}

	$scope.cancelPopUpClose =function(){
		$("#newCloseShiftmyModal").modal('toggle');
	}

	$http({
		method: "POST",
		url:"../shiftManagement/getcurrentUserShift"
	}).then(function(response) {
		$scope.currentShifts = response.data;	
		/*		$scope.currentShifts.splice(0,0,{id : "" ,code : "Select"});
		 */		});

	$scope.saveUpdate=function(){
		if($scope.AllShift[0].code==$scope.currentShifts[0].shiftCode && $scope.checkOutStatus!=0){

			var confirm = $mdDialog.alert()
			.title("Pending Check-Outs are there").ok('OK').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../nightAudit/audit";
			});
		}else{
			$scope.save={id:$scope.currentShifts[0].usershiftid,user_id:$scope.currentShifts[0].user_id,shift_id:$scope.currentShifts[0].shift_id,opening_float:$scope.currentShifts[0].opening_float,opening_time:$scope.currentShifts[0].opening_time,opening_date:$scope.currentShifts[0].opening_date,passWord:$scope.password,baction:"saveUpdate",disbl:false};
			$scope.bAction=$scope.closeShift.baction;
			if($scope.bAction=='saveUpdate'){
				if($scope.password=="" || $scope.password==undefined){
					$("#msg2").show();
					$("#pass").click(function(){
						$("#msg2").fadeOut();
					});
				}else{
				var data = $.param({
					currentShiftJson:JSON.stringify($scope.save)
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../shiftManagement/currentShiftupdate', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						$("#newCloseShiftmyModal").modal('toggle');
						var confirm = $mdDialog.alert()
						.title("Shift Closed Successfully").ok('OK').parent(angular.element(document.body));
						$mdDialog.show(confirm).then(function(){
							window.location  = "../shiftManagement/openshift";
						});
					}else{
						$mdDialog.show($mdDialog.alert()
								.clickOutsideToClose(true).title('Incorrect Password').ok('Ok').parent(angular.element(document.body)));
					}
				}).error(function (data, status, header, config) {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			}
			}
		}
	}

	$scope.shouldEnable=function(closingTime){
		$scope.openshifBtnAction=true;

		var value=true;
		if(closingTime==null || closingTime==""){
			value=false;

		}else{
			value=true;
			$scope.openshifBtnAction=false;
		}
		return value;
	}

	$scope.numericalValidation = function($event){
		if(isNaN(String.fromCharCode($event.keyCode))){
			$event.preventDefault();
		}
	};
}]);