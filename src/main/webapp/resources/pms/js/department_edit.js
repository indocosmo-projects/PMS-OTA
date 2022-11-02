var resvApp = angular.module('department', ['ngMaterial']);
resvApp.controller('departmentController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){   
		 
		
		
		//$('.modal-dialog').hide();
		//$('.modal-dialog').show();
	//	$('.modal-dialog').draggable(); //for drag
		//$('.modal-content').resizable({}); //for resize
		//$(".modal-dialog").modal("show");
	//	$(".md-dialog-container ng-scope").css("z-index", "1500");
		
	//	ng-scope modal-open modal-open
	//	$('.modal-dialog').hide();
	//	$( "body" ).removeClass( "modal-open" )

		
	//	$("#newPaymentmyModal").modal('toggle');
	//	$("#newPaymentmyModal").modal('toggle');
		  var confirm = $mdDialog.confirm()
			.title("Delete this Department?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
				$('.modal-dialog').hide();
				deleteMaster(id, url, redirectUrl);			 
			}, function() {		
				//$("#newPaymentmyModal").modal('toggle');
            }); 	 
	  }
	
	 

	  
}]);




function refreshData() {
	var id = $('#masterId').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_department,.validator_cls";
	var url = "../department/getRecord?ids=";
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
		document.department.submit();
	}
}

function cancel() {
	$("#departmentdiv").dialog('close');
	$("#departmentdiv").dialog(' ');
}

function deleteData() {
	var id = $('#masterId').val();
	url = "../department/delete";
	redirectUrl = "../department/departmentList";
	
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	
	/*var isConfirm = confirm("Are you sure do you want to delete ?");
	if (isConfirm == true) {
		deleteMaster(id, url, redirectUrl);
	}*/
}

//function onload(){
//	
//	if ($('#departmentcode').val() == 0) {
//		$('#delete_btn').hide();
//		onloadValidator(".validator");	
//		OnloadValidationEdit('.validator');	
//			$('#txtcode').change(function(){
//				if($('#txtcode').val()!= 0){
//				codeExist($('#txtcode').val(),'../department/codeExist','txtcode','Code exists.');
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
			
		codeExist(code , "../department/codeExist", "txtcode", "Code exists.");
		}
	});
}   