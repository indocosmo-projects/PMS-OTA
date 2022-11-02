pmsApp.controller('userGroupController', ['$scope','$q','$http','$mdDialog','$window','DTOptionsBuilder','DTColumnBuilder',function ($scope,$q,$http,$mdDialog,$window,DTOptionsBuilder,DTColumnBuilder){	
	var gp=this;
	var gId,gCode,gName,gDesc,gAction;
	$("#delete_btn").hide();
	onloadValidator(".validator");	
	OnloadValidationEdit('.validator');	
	gp.dtInstance = {};
	var urlString="../userGroup/getUserGroupList";
	gp.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString).then(function(result) {
			defer.resolve(result.data);	       
		});
		return defer.promise;
	}).withPaginationType('full_numbers').withOption('rowCallback', rowCallback);
	gp.dtColumns = [DTColumnBuilder.newColumn("code","Code").renderWith(function(data, type, full) { return (window.cp_isCanEdit? '<a style="color:#0000ff;">'+data+'</a>' :data);}),
	                DTColumnBuilder.newColumn("name","Name"),
	                DTColumnBuilder.newColumn("description","Description"),
	                DTColumnBuilder.newColumn("id","Permission").renderWith(function(data, type, full) { return (window.cp_isCanEdit? '<a style="color:#0000ff;">Assign Permissions</a>' :"-");}).notSortable()];

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		$('td', nRow).unbind('click');
		$('td:nth-child(1)',nRow).bind('click', function() {
			if(window.cp_isCanEdit){
			$scope.$apply(function() {				
				$scope.master={id:aData.id,code:aData.code,name:aData.name,description:aData.description,baction:'edit',disbl:true};
				$scope.reset();
				resetValidators('.validator');
				$("#delete_btn").show();
				$("#userGroupmyModal").modal({backdrop: "static"});
			});
			}
		});		
		$('td:nth-child(4)',nRow).bind('click', function() {
			$scope.$apply(function() {
				if(window.cp_isCanEdit){
			 	  window.location="../permission/grouppermission?groupid="+aData.id;
				}
			});
		});	
		return nRow;
	}

	$scope.reset = function() {
		resetValidators('.validator');
		$scope.uGroup = angular.copy($scope.master);
	};
	$scope.reset();

	$scope.newGroup = function(){
		resetValidators('.validator');
		$("#delete_btn").hide();
		$scope.master={id:0,code:"",name:"",description:"",baction:'save',disbl:false};
		$scope.reset();
		$("#userGroupmyModal").modal({backdrop: "static"});	
	}
	$scope.cancelPopUp = function(){
		$("#userGroupmyModal").modal('toggle');	
	}

	$scope.save = function(){
		if (validation('.validator') == "false") {
			return false;
		} else {
			gId=$scope.uGroup.id;
			gCode=$scope.uGroup.code;
			gName=$scope.uGroup.name;
			gDesc=$scope.uGroup.description;
			gAction=$scope.uGroup.baction;
			if(gAction=='save'){
				var data = $.param({
					groupCode : gCode,
					groupName : gName,
					groupDesc : gDesc
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}}
				$http.post('../userGroup/save', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						gp.dtInstance.reloadData();
						$("#userGroupmyModal").modal('toggle');
					}else{alert("error");}
				}).error(function (data, status, header, config) {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});

			}else if(gAction=='edit'){
				var data = $.param({
					groupId : gId,
					groupCode : gCode,
					groupName : gName,
					groupDesc : gDesc
				});
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}}
				$http.post('../userGroup/edit', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						gp.dtInstance.reloadData();
						$("#userGroupmyModal").modal('toggle');
					}else{alert("error");}
				}).error(function (data, status, header, config){
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			}
		}
	}

	$scope.deleteGroup = function(){
		$("#userGroupmyModal").modal('toggle');
		var confirm = $mdDialog.confirm()
		.title("Alert")
		.textContent("Are you sure you want to delete this User group?").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			var data = $.param({
				groupId : $scope.uGroup.id
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}}
			$http.post('../userGroup/delete', data, config)
			.success(function (data, status, headers, config) {
				if(data.substring(7)=='success'){
					gp.dtInstance.reloadData();
				}else{
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent('Cannot delete. Active users are in this group').ok('Ok').parent(angular.element(document.body)));
				}
			}).error(function (data, status, header, config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
		});
	}

	$("#txtCode").bind( "focusout",function(){
		var data = $.param({
			code : $scope.uGroup.code
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}}
		$http.post('../userGroup/codeExist', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='exists'){
				$("#userGroupmyModal").modal('toggle');	
				var confirm = $mdDialog.confirm()
				.title("Alert")
				.textContent("Code exists").ok('Ok').parent(angular.element(document.body));
				$mdDialog.show(confirm).then(function() {
					$("#userGroupmyModal").modal({backdrop: "static"});
				});
				$scope.uGroup.code='';
				$('#txtCode_check').hide();
				$('#txtCode_warning').show();
			}else{
				if($scope.uGroup.code!=''){
					$('#txtCode_check').show();
					$('#txtCode_warning').hide();
				}}
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});	
	});

}]);

