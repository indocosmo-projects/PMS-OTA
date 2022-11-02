pmsApp.controller('shiftCtrl',['$scope','$http','DTOptionsBuilder','DTColumnBuilder','$mdDialog','$filter',function($scope,$http,DTOptionsBuilder,DTColumnBuilder,$mdDialog,$filter){
	onloadValidator(".validator");	
	OnloadValidationEdit('.validator');	
	$scope.dtInstance = {};
	$scope.shift = {};
	//$scope.ismeridian=true;
	$scope.hstep = 1;
	$scope.mstep = 1;
	$scope.codeCheck={};
$scope.dtColumns=[
    DTColumnBuilder.newColumn("code","Code").renderWith(function(data, type, full) {$scope.codeCheck[full.id]=full.code; return '<a style="color:#0000ff;">'+data+'</a>';}),
    DTColumnBuilder.newColumn("name","Name"),
    DTColumnBuilder.newColumn("startTime","Start Time").renderWith(function(data, type, full, meta) {
		data = $filter('date')(new Date(data)," HH:mm");
		return data;
			}),
    DTColumnBuilder.newColumn("endTime","End Time").renderWith(function(data, type, full, meta) {
		data = $filter('date')(new Date(data)," HH:mm");
		return data;
			}),	
];
$scope.dtOptions = DTOptionsBuilder.newOptions().withOption('ajax', {
	url: "../shift/shiftDetails",
	type: "POST",
	error: function(response){
        $mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Unreccognised error.\nPlease contact Administrator.').textContent(response.responseText).ok('Ok').parent(angular.element(document.body)));
	}
}).withPaginationType('full_numbers').withDisplayLength(10).withOption('rowCallback', rowCallback);

function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
	if(window.cp_isCanEdit){
		$('td', nRow).unbind('click');
		$('td',nRow).bind('click', function() {
			$scope.$apply(function() {
				resetValidators('.validator');
				$("#deleteShiftBtn").show();
				$scope.shift={id:aData.id,code:aData.code,name:aData.name,description:aData.description,startTime:aData.startTime,endTime:aData.endTime,baction:"edit",disbl:true};
				
				$("#newShiftmyModal").modal({backdrop: "static"});
			})
		})
	}
}

$scope.newShift = function(){
	$("#acc_check").css("display","none");			
	$('#acc_warning').css("display","none");
	resetValidators('.validator');
	$("#deleteShiftBtn").hide();
	$("#saveShiftBtn").show();
	$scope.previousTime=new Date();
    $scope.nextTime = new Date(new Date().setMinutes(new Date().getMinutes()+1));
	$scope.shift={id:0,code:"",name:"",description:"",startTime:$scope.previousTime,endTime:$scope.nextTime,baction:"save",disbl:false};
	//$scope.shift={id:0,code:"",name:"",description:"",startTime:new Date(),endTime:new Date($scope.nextTime.setMinutes($scope.nextTime.getMinutes()+2)),baction:"save",disbl:false};
	$("#newShiftmyModal").modal({backdrop: "static"});

}
$scope.save= function(){
	
	if (validation('.validator') == "false") {
		if($("#code").val()==""){
			$("#code").focus();
		}
		if($("#name").val()=="" && $("#code").val()!==""){
			$("#name").focus();
		}
		return false;
	} 
	if($scope.shift.startTime>$scope.shift.endTime){
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Alert').textContent('Shift End-time should be greater than shift Start-time').ok('Ok').parent(angular.element(document.body)));
	}
	else{
	$scope.shift.startTime=$filter('date')(new Date($scope.shift.startTime),'yyyy-MM-dd HH:mm');
		$scope.shift.endTime=$filter('date')(new Date($scope.shift.endTime),'yyyy-MM-dd HH:mm');
		if($scope.shift.startTime == $scope.shift.endTime){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Start time should not be equal to End time").ok('Ok').parent(angular.element(document.body)));
			return;
		}
		$scope.bAction=$scope.shift.baction;
		if($scope.bAction=='save'){
			var data = $.param({
				shiftJson:JSON.stringify($scope.shift)
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../shift/save', data, config)
			.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						$scope.dtInstance.reloadData();
						$("#newShiftmyModal").modal('toggle');
					}else{alert("error"+data);}
				}).error(function (data, status, header, config) {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Unreccognised error.\nPlease contact Administrator.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
		}else if($scope.bAction=='edit'){
			var data = $.param({
				shiftJson:JSON.stringify($scope.shift)
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../shift/update', data, config)
			.success(function (data, status, headers, config) {
				if(data.substring(7)=='success'){
					$scope.dtInstance.reloadData();
					$("#newShiftmyModal").modal('toggle');
				}else{alert("error");}
			}).error(function (data, status, header, config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Unreccognised error.\nPlease contact Administrator.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
		}
	}
}

$("#code").bind( "focusout",function(){
	var code=$scope.shift.code
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
				.clickOutsideToClose(true).title('Alert').textContent("Code exists.").ok('Ok').parent(angular.element(document.body)));
		$scope.shift.code='';
		$('#code_check').hide();
		$('#code_warning').show();
	}else{
		$('#code_check').show();
		$('#code_warning').hide();
	}
});
$scope.cancelPopUp =function(){
	$("#newShiftmyModal").modal('toggle');
}
$scope.deleteShift = function(){
	var confirm = $mdDialog.confirm()
	.title("Are you sure you want to delete this shift?")
	.textContent("Shift will be deleted").ok('Yes').cancel('No').parent(angular.element(document.body));
	$mdDialog.show(confirm).then(function(){
		$http({
			method:"POST",
			url:"../shiftManagement/getListShift"
		}).then(function(response) {
			$scope.ShiftLIst=response.data;
			if($scope.ShiftLIst[0].shift_id == $scope.shift.id){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('This shift is in use.').ok('Ok').parent(angular.element(document.body)));
				$("#newShiftmyModal").modal('toggle');
			}else{
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../shift/delete', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						$scope.dtInstance.reloadData();
						$("#newShiftmyModal").modal('toggle');
					}else{alert("error");}
				}).error(function (data, status, header, config){
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Unreccognised error.\nPlease contact Administrator.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			}
		});
		var data = $.param({
			id : $scope.shift.id
		});
		
	});
}

}]);