var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){
		
		 
		  var confirm = $mdDialog.confirm()
			.title("Delete this Currency?")
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


function refreshData(){
	var id = $('#currencyId').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_currency,.validator_cls";
	var url = "../currency/getRecord?ids=";
	var txt=refresh(id, encryptedId, cls, url);

	if(txt!=''){
		$("#content").html(txt);
		onload();
	}	
	$('#dec_plc').val('0');
	$('#exch_rate').val('0.0');	
}

function save(){
	if (validation('.validator') == "false") {
		var clss="";
		var count=0;
		if($("#txtcode").val()==""){
			$("#txtcode").focus();
		}
		if($("#txtname").val()=="" && $("#txtcode").val()!==""){
			$("#txtname").focus();
		}
		if($("#exch_rate").val()=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#exch_rate").focus();
		}
		if($("#upd_date").val()=="" && $("#exch_rate").val()!=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#upd_date").focus();
		}
		return false;
	} else {

		if(decimalCheck() == "false"){
			$('#validator_exch_rate').removeClass('dataenter_cls_img');
			$('#validator_exch_rate').text("please check the value");
			return false;
		}else{
			var data = SAVE_ACTION_INS;
			if($('#currencyId').val() != 0){
				data = SAVE_ACTION_UPD;
			}
			sessionStorage.setItem("action", data);
			document.currency.submit();
		}
	}
}

function cancel(){
	$('#currencydiv').dialog('close');
	$('#currencydiv').html("");
}

function deleteData(){
	var id = $('#currencyId').val();
	url = "../currency/delete";
	redirectUrl = "../currency/currencyList";
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	/*var isConfirm = confirm("Are you sure do you want to delete ?");
	if (isConfirm == true) {
		deleteMaster(id, url, redirectUrl);
	}*/
}

function onload(){

	$( "#tabs" ).tabs();
	$('#tab_header').removeClass('ui-widget-header');
	var dateFormat=convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	onloadValidator('.validator');
	numericalValidation('#dec_plc,#exch_rate');
	OnloadValidationEdit('.validator');
	$('#exch_rate').keyup(function(){
		var temp=$('#exch_rate').val();
		var reg= /^\d{0,4}(\.\d{0,4})?$/;
		if(reg.test(temp)){
			$('#validator_exch_rate').addClass('dataenter_cls_img');
		}else{

			$('#validator_exch_rate').removeClass('dataenter_cls_img');
			$('#validator_exch_rate').addClass('validator_cls_img');
		}
	})
	$('#upd_date').datepicker({
		dateFormat:dateFormat
	});
	$('#txtcode').change(function(){
		codeExist($('#txtcode').val(),'../currency/codeExist','txtcode','Code exists.');
	});

	if ($('#currencyId').val() == 0) {
		$('#delete_btn').hide();
		$("#upd_date").attr("readonly", true);
	}else{
		var tempDate=$('#upd_date').val().split(' ');
		var date=new Date(tempDate[0]);
		$('#upd_date').val($.datepicker.formatDate(dateFormat,date));
		$("#txtcode").attr("readonly", true);
		$("#upd_date").attr("readonly", true);
	}
}

function decimalCheck(){

	isDecimal="false";
	var temp=$('#exch_rate').val();
	var reg= /^\d{0,4}(\.\d{0,4})?$/;

	if(reg.test(temp)){
		isDecimal="true";
	}

	return isDecimal;
}

