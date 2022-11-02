var resvApp = angular.module('floor', ['ngMaterial']);
resvApp.controller('floorController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm=function(id, redirectUrl){
		var confirm=$mdDialog.confirm()
		.title("Alert")
		.textContent("Are you sure you want to delete this Floor?").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			var data=$.param({
				id:id
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../floor/delete', data, config)
			.success(function(data,status,headers,config){
				if(data.substring(7)=='success'){
					window.location = redirectUrl;
					$("#newFloorModal").modal('toggle');
				}else{
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent('Cannot delete this Floor').ok('Ok').parent(angular.element(document.body)));
				}
			}).error(function(data,status,headers,config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
		});
			
	}
}]);

function refreshData() {
	var id = $('#masterId').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_floor,.validator_cls";
	var url = "../floor/getRecord?ids=";
	var txt=refresh(id, encryptedId, cls, url);

	if(txt != ''){ 

		$("#content").html(txt);
		onload();
	}
}

function save() {
	if (validation('.validator') == "false") {
		if($("#txtcode").val()==""){
			$("#txtcode").focus();
		}
		if($("#txtname").val()=="" && $("#txtcode").val()!==""){
			$("#txtname").focus();
		}
		return false;
	} else {
		var data = SAVE_ACTION_INS;
		if($('#masterId').val() != 0){
			data = SAVE_ACTION_UPD;
		}
		sessionStorage.setItem("action", data);
		//codeExist();
		document.floor.submit();
	}
}

function cancel() {
	$("#floordiv").dialog('close');
	$("#floordiv").dialog(' ');
}

function deleteData() {
	console.log("delete");
	var id = $('#masterId').val();
	console.log("id :"+id);
	//url = "/pms/floor/delete";
	redirectUrl = "../floor/floorList";
	
	angular.element(document.body).scope().deleteConfirm(id, redirectUrl);
}

//function onload(){
//	
//	if ($('#floorcode').val() == 0) {
//		$('#delete_btn').hide();
//		onloadValidator(".validator");	
//		OnloadValidationEdit('.validator');	
//			$('#txtcode').change(function(){
//				if($('#txtcode').val()!= 0){
//				codeExist($('#txtcode').val(),'/pms/floor/codeExist','txtcode','Code exists.');
//				}
//			});
//	}else{
//		$('#txtcode').attr('readonly','readonly');
//	}
//		
//		
//	
//}

function onload(){

	if ($('#masterId').val() == 0) {
		$('#delete_btn').hide();	
	}else{
		$('#txtcode').attr('readonly',true);
	}
	onloadValidator(".validator");	
	OnloadValidationEdit('.validator');	
	$('#txtcode').change(function() {
		
		var code=$('#txtcode').val();
		if(code != '') {
			
		codeExist(code , "../floor/codeExist", "txtcode", "Code exists.");
		}
	});
}   