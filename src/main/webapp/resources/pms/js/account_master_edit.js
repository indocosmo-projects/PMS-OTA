$(document).ready(function(){
2
  $("#txtcode").keydown(function(event) {
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



var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$window','$mdDialog',function ($scope,$http,$window,$mdDialog){
	 
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){   
		 
	
		  var confirm = $mdDialog.confirm()
			.title("Delete this Account Head?")
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
	
	
	  $scope.showAlert =   function (ttl,msg) {
	      alert = $mdDialog.alert({
	          title: ttl,
	          textContent: msg,
	          ok: 'Close'
	        });

	        $mdDialog
	          .show( alert )
	          .finally(function() {
	            alert = undefined;
	          });
	      }
	  
}]);



function onLoadAccMaster(){
	if ($('#accountmaster_id').val() == 0) {
		$('#delete_btn').hide();	
		document.getElementById('service_charge').value = '0';
	}else{
		$('#txtcode').prop('readonly',true);
	}
	
	onloadValidator(".validator");
	
	$('#txtcode').change(function(){
		codeExist($('#txtcode').val(),'../accountMaster/codeExist','txtcode','Code exists.');
	});	
	withOutTax();
}

/**
 * disable and enable taxcode w.r.t txntype 
 */
function withOutTax(){
	var taxType=$('#sysDefAcc').val();
	
	
	
	$.ajax({
		url:'../accountMaster/getTaxStatus',
		data: {
			taxTypeId: taxType	 
		},
		success: function (response) {
			 
			if(response){
				$('#tax').show();
				$('#tax_code').show();
				$('#tax_inc').show();
				$("#service_chargediv").show();
				$('#credit_days').show();
				
			}else{
				
				$('#tax').hide();
				$('#tax_code').hide();
				$('#tax_inc').hide();
				$("#service_chargediv").hide();
				$('#credit_days').hide();
				
			}
			
			
		},error: function(response){
			angular.element(document.body).scope().showAlert("Operation failed.",response.responseText);
		}
	});
	
	 
/*	
	console.log(taxType);
	
	if(taxType==4||taxType==5||taxType==6||taxType==8){
		$('#tax').hide();
		$('#tax_code').hide();
		$('#tax_inc').hide();
		$("#service_chargediv").hide();
	} else{
		$('#tax').show();
		$('#tax_code').show();
		$('#tax_inc').show();
		$("#service_chargediv").show();
	}*/
}

/**
 * page refresh
 */
function refreshData() {
	var id = $('#accountmaster_id').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_account,.validator_cls";
	var url = "../accountMaster/getRecord?ids=";
	
	$('#sysDefAcc').prop('selectedIndex',0);
	$('#tax').prop('selectedIndex',0);
	$('#chkIsTaxInc').attr('checked', false); // Unchecks it
	withOutTax();
	var txt=refresh(id, encryptedId, cls, url);
	
	if(txt!=''){
		$("#content").html(txt);
		onLoadAccMaster();
	}
}


/**
 * account master save function
 * @returns {Boolean} when the validation fails
 */
function saveDatas() {
	
	if (validation('.validator') == "false") {
		if($("#txtcode").val()==""){
			$("#txtcode").focus();
		}
		if($("#txtname").val()=="" && $("#txtcode").val()!==""){
			$("#txtname").focus();
		}
		if($("#service_charge").val()=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#service_charge").focus();
		}
		return false;
	}else{
		
		var taxType=$('#sysDefAcc').val();
		var accountmasterId = 0;
		if($('#accountmaster_id').val() != 0){
			accountmasterId = $('#accountmaster_id').val() ;
		}

		$.ajax({
			url:'../accountMaster/getInheritStatus',
			data: {
				taxTypeId: taxType,
				acmId:accountmasterId
			},
			success: function (response) {
				if(response){				
					var data = SAVE_ACTION_INS;
					
					if($('#accountmaster_id').val() != 0){
						data = SAVE_ACTION_UPD;	
					}
					$("#save_btn").prop("disabled", true).addClass("disabled");
					sessionStorage.setItem("action", data);
					document.accountMaster.submit();
					
				}else{
					
					var msg = "Cannot inherit. Transaction Type already exists";					
					angular.element(document.body).scope().showAlert("Alert !",msg);					 
					return false;
				}
				
				
			},error: function(response){
				angular.element(document.body).scope().showAlert("Operation failed.",response.responseText);
			}
		});
		
		
		/*
		
		var data = SAVE_ACTION_INS;
		
		if($('#accountmaster_id').val() != 0){
			data = SAVE_ACTION_UPD;	
		}
		$("#save_btn").prop("disabled", true).addClass("disabled");
		sessionStorage.setItem("action", data);
		document.accountMaster.submit();*/
	}
}


/**
 * page cancel function
 * redirect to account master list
 */
function cancel() {
	window.location = '../accountMaster/accountMasterList';
}


/**
 * delete record function
 */
function deleteData() {
	var id = $('#accountmaster_id').val();
	url = "../accountMaster/delete";
	redirectUrl = "../accountMaster/accountMasterList";
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	/*var isConfirm = confirm("Are you sure do you want to delete ?");
	
	if (isConfirm == true) {
		deleteMaster(id, url, redirectUrl);
	}*/
}