var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(deleteId, value, hdrId){   
		 
	
		  var confirm = $mdDialog.confirm()
			.title("Delete this Tax setting?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
			//	$('.modal-dialog').hide();
				deleteTax(deleteId, value, hdrId);			 
			}, function() {		
				//$("#newPaymentmyModal").modal('toggle');
            }); 	 
	  }
	
	$scope.showAlert = function(msg){    
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Message').textContent(msg).ok('Ok').parent(angular.element(document.body)));
	};
	 

	  
}]);

function deleteTax(deleteId, value, hdrId) {
console.log("call");
	var id;

	if (deleteId == "hiddenIdTaxHdr") {//passed from onClick function
		table = "TaxHdr";
		id = $("#hiddenIdTaxHdr").val();
	} else {
		table = "TaxDtl";
		id = value;
	}

$.ajax({
	url : '../TaxHdr/delete?id=' + id,
	type : 'post',
	data : {
		table : table
	},
	async : false,
	success : function(data) {
		if (data == "TaxHdr") {
			window.location = "../TaxHdr/taxList";
	
		} else {
			$('#'+hdrId).hide(); 
			var rowId=hdrId.match("detailDivId(.*)");
			$('#'+hdrId+' .tax_detail_table_head_date').prepend('<input id="taxDetails'+rowId[1]+'.isDeleted" name="taxDetails['+rowId[1]+'].isDeleted" class="divClass" value="1" type="hidden">');
		}
	},
	error : function(xhr, errorType, exception) {
		angular.element(document.body).scope().showAlert(JSON.stringify(xhr));
		angular.element(document.body).scope().showAlert(errorType);
		angular.element(document.body).scope().showAlert(exception);
		 
	}
});
}



function onLoadTax(){
	dateFormat=convertDateFormatToJqueryDateFormate($('#dateFormat').val());

	$(function() {
		$( "#taxTabs" ).tabs();
	});

	if ($('#hiddenIdTaxHdr').val() == 0) {
		$('#delete_btn').hide();
		$("#txtcode").attr("readonly", false);
	}else{
		$("#txtcode").attr("readonly", true);
	}

	$('.datepicker').each(function(){
		if  ($(this).val()!=""){
			tempDate=$(this).val().split(' ');
			date=new Date(tempDate[0]);
			$(this).val($.datepicker.formatDate(dateFormat,date));		
		}
	});
	$('.datepicker').datepicker({
		dateFormat : dateFormat
	});

	$('#txtcode').change(function() {
		isCodeExist();
	});

	$('#btnAddTaxRow').click(function() {

		addNewDivRow();
	});

	onloadValidator('.validator');
	OnloadValidationEdit('.validator'); 
	onloadTableValidator('.validTable');
	numericalValidation();
	systemSettingsTax();
	jstlDetailLoop($('.div_row').size());
}

function refreshData() {         /*Refresh function*/
	var id = $('#hiddenIdTaxHdr').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_tax, .validator_cls";
	var url = "../TaxHdr/getRecord?ids=";
	
	var txt=refresh(id, encryptedId, cls, url);
 
	
	
	
	if(txt!=''){
		$("#content").html(txt);
		onLoadTax();
	}else{
		
		$( ".new-row" ).each(function() {
			var ids = $(this).attr('id');
			   if(ids!="rowTax0"){
			      $('table#tblTax tr#'+ids).remove();
			   }else{
			      $("#dateRow0").val("");
			      $("#tax1Row0").val("");
			      $("#tax2Row0").val("");
			      $("#tax3Row0").val("");
			      $("#tax4Row0").val("");
			}
			 
			 
			});
		
	}
}


function saveData() {   /*submit function*/
	if (validation('.validator') != "true"){
		if($("#txtcode").val()==""){
			$("#txtcode").focus();
		}
		if($("#txtname").val()=="" && $("#txtcode").val()!==""){
			$("#txtname").focus();
		}
		return false;
	} else if(taxValidation()!='true') {
		return false;
	}else if(dateDuplicationCheck()!="true"){
		return false;
	}else{
		var data = SAVE_ACTION_INS;
		
		if ($('#hiddenIdTaxHdr').val()!='') {
			data = SAVE_ACTION_UPD;
		}
		
		sessionStorage.setItem("action", data);
		document.tax.submit();
	}	
}

function cancel() {  /*Cancel function*/
	window.location = '../TaxHdr/taxList';
}

function deleteData(deleteId, value, hdrId) {  /* Static delete function*/
	
	angular.element(document.body).scope().deleteConfirm(deleteId, value, hdrId);
/*	var id;
	var result = confirm("Are you sure want to delete this record?");
	
	if (result == true) {
		if (deleteId == "hiddenIdTaxHdr") {//passed from onClick function
			table = "TaxHdr";
			id = $("#hiddenIdTaxHdr").val();
		} else {
			table = "TaxDtl";
			id = value;
		}
	}
	
	$.ajax({
		url : '../TaxHdr/delete?id=' + id,
		type : 'post',
		data : {
			table : table
		},
		async : false,
		success : function(data) {
			if (data == "TaxHdr") {
				window.location = "../TaxHdr/taxList";
			} else {
				$('#'+hdrId).hide(); 
				var rowId=hdrId.match("detailDivId(.*)");
				$('#'+hdrId+' .tax_detail_table_head_date').prepend('<input id="taxDetails'+rowId[1]+'.isDeleted" name="taxDetails['+rowId[1]+'].isDeleted" class="divClass" value="1" type="hidden">');
			}
		},
		error : function(xhr, errorType, exception) {
			alert(JSON.stringify(xhr));
			alert(errorType);
			alert(exception);

		}
	});*/
}

var temp =0;
function addNewDivRow() {  /*Add new div function*/
	temp=$('.tax-row').size();
	var row = "<tr id=\"rowTax" + temp + "\" class=\"tax-row new-row\">"
	+ "<td><input type=\"text\" data-date-format=\"dd-MM-yy\" id=\"dateRow"+temp+"\"class=\"datepicker divClass input_valid_date refresh_currency validTable\" readonly=\"readonly\" name=\"taxDetails["+temp+"].validFrom\" onchange=\"dateDuplicationCheck()\"></td>"
	+ "<td class=\"tax1" 
	+ "\"><input type=\"text\" maxlength=\"5\" id=\"tax1Row"+temp+"\"class=\"taxTableText divClass refresh_currency taxOne validTable\" name=\"taxDetails["+temp+"].tax1Pc\" onkeypress=\"return isNumberKey(event)\"></td>"
	+ "<td class=\"tax2"  
	+ "\"><input type=\"text\" maxlength=\"5\" id=\"tax2Row"+temp+"\"class=\"taxTableText divClass refresh_currency taxTwo validTable\" name=\"taxDetails["+temp+"].tax2Pc\" onkeypress=\"return isNumberKey(event)\"></td>"
	+ "<td class=\"tax3" 
	+ "\"><input type=\"text\" maxlength=\"5\" id=\"tax3Row"+temp+"\"class=\"taxTableText divClass refresh_currency taxThree validTable\" name=\"taxDetails["+temp+"].tax3Pc\" onkeypress=\"return isNumberKey(event)\"></td>"
	+ "<td class=\"tax4"  
	+ "\"><input type=\"text\" maxlength=\"5\" id=\"tax4Row"+temp+"\"class=\"taxTableText divClass refresh_currency taxFour validTable\" name=\"taxDetails["+temp+"].tax4Pc\" onkeypress=\"return isNumberKey(event)\"></td>"
	+ "<td class=\"delete-btn\"><button type=\"button\" id=\"del"+temp+"\"class=\"btn btn-danger btn-xs\" onclick=\"deleteRow(this,'new-row');\"><i class=\"fa fa-trash-o \"></i></button></td>"
	+ "</tr>";

	if($('#tblTax tr').length ==4) {
		$('#divTaxSettings').addClass('tax-settings-div');
	}

	$('#tbodyTaxSettings').append(row);
	temp++;
	systemSettingsTax();
	
	$('.datepicker').datepicker({
		dateFormat : dateFormat
	});
}

function isNumberKey(evt) {
	var charCode = (evt.which) ? evt.which : event.keyCode;
	
	if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else {

		if (evt.target.value.search(/\./) > -1 && charCode == 46) {
			return false;
		}
		return true;
	}
}

function deleteRow(obj,className) {
	var r=$('.tax-row').size();
	var did=$(obj).attr('id');
	var rowNo=parseInt(did.substr(3,did.length));
	
	if(r!=1){
			if(className=="old-row"){
				$("#rowTax"+rowNo).hide();
				$("#hdnRevnDel"+rowNo).val('true');
			}
			else{
				var total_Newrows=$('.new-row').size();
				$("#rowTax"+rowNo).remove();

				rearrangeTableRowIndex(rowNo,total_Newrows);

			}
	}
}

/**
 *function to rearrange distribution table row index 
 */
function rearrangeTableRowIndex(start,end){	
	for(i=start;i<end;i++){
		$("#rowTax"+(i+1)).attr("id","rowTax"+i);
		$("#dateRow"+(i+1)).attr("name","taxDetails["+i+"].validFrom").attr("id","dateRow"+i);

		$("#tax1Row"+(i+1)).attr("name","taxDetails["+i+"].tax1Pc").attr("id","tax1Row"+i);
		$("#tax2Row"+(i+1)).attr("name","taxDetails["+i+"].tax2Pc").attr("id","tax2Row"+i);	
		$("#tax3Row"+(i+1)).attr("name","taxDetails["+i+"].tax3Pc").attr("id","tax3Row"+i);	
		$("#tax4Row"+(i+1)).attr("name","taxDetails["+i+"].tax4Pc").attr("id","tax4Row"+i);
		$("#del"+(i+1)).attr("id","del"+i);
	}	
}

function taxValidation() { /*Tax Detail table null validation*/
	var isRequired = "true";
	$('.validTable').each(function() {
		var ids = $(this).attr('id');
		if ($('#' + ids).val() == '' && $('#' + ids).attr("disabled") != 'disabled' ) {
			$('#taxDetail_able_rror').html('Enter tax details');
			$('#' + ids).focus();
			isRequired = "false";
		} 
	});
	return isRequired;
}

function onloadTableValidator(cls){ 
	$(cls).bind('keyup change focusout', function(e) {
		var id = e.target.id;
		if (e.whitaxTableTextch != 9) {
			if ($('#' + id).val() == '') {
				$('#taxDetail_able_rror').html('Enter tax fields');
			} else {
				$('#taxDetail_able_rror').html('');
			}
		}

		var value = $('#' + id).val();
		value = value.replace(/[^0-9.-]/g, "");
		var pieces = value.split('.');
		var integer = pieces[0].replace('-', '');
		var decPlaces = ""

			if (pieces.length > 1)
			{
				pieces.shift();
				decPlaces = pieces.join('').replace('-', '');
			}
		if (integer > 99.9999 || integer === 99.9999 && parseInt(decPlaces) > 0)
		{
			integer = "99";
			decPlaces = getZeroedDecPlaces(decPlaces);
			$('#taxDetail_able_rror').html('Number must be between 0 and 99');
		} // ...and less than 0:
		else if (parseInt(integer) < 0)
		{
			integer = "0";
			decPlaces = getZeroedDecPlaces(decPlaces);
			$('#taxDetail_able_rror').html('Number must be between 0 and 99');
		}

		if (decPlaces.length > 2)
		{
			decPlaces = decPlaces.substr(0, 4);
			$('#taxDetail_able_rror').html('Number cannot have more than four decimal places');
		} 

	}); 
}

//duplicate date validation
function dateDuplicationCheck(){
	var valdate = new Array();
	var count = 0;
	var isOk = "true";
	$('.datepicker').each(function() {

		var val = $(this).val();
		count++;
		if($.inArray(val, valdate) != -1) {

			isOk = "false";

			$('#taxDetail_able_rror').html('Date already exists.');

			return false;
		}
		else {
			$('#taxDetail_able_rror').html('');
		}

		valdate.push(val);
	});

	if(isOk == "false") {
		return "false";
	}else{
		return "true";
	}
}

function getZeroedDecPlaces(decPlaces) {
	if (decPlaces === '') return '';
	else if (decPlaces.length === 1) return '0';
	else if (decPlaces.length >= 2) return '00';
}

function isCodeExist() {
	var code = $.trim($('#txtcode').val());
	if (code != '') {
		$.ajax({
			url : '../TaxHdr/checkCodeForDuplicate',
			type : 'POST',
			data : {
				'code' : code
			},
			success : function(response) {
				if (response == true) {
					alert('Code Exist');
					$('#txtcode').val('');
				}
			}
		});
	}
}

function systemSettingsTax()  /*Tax detail table header name function*/
{
	var systemSettings;
	$.ajax({
		url : '../TaxHdr/systemSettingsData',
		async : false,
		dataType : 'json',
		success : function(data) {
			systemSettings = data;
		},
		error : function(data) {
			alert("error");
		}
	});
	for (i in systemSettings) {

		if (systemSettings[i][0] =='' ) {

			$(".taxOne").attr("readonly", true);
			$('.taxOne').addClass("inputHidden");
			$('.tax1').css({
				"background-color" : "#eeeeec"
			});
			$('.taxOne').val('');
		}else{
			$('#tax1Setting').html(systemSettings[i][0]+'(%)');
		}
		if (systemSettings[i][1] == '') {
			$(".taxTwo").attr("disabled", true);
			$('.taxTwo').addClass("inputHidden");
			$('.tax2').css({
				"background-color" : "#eeeeec"
			});
			$('.taxTwo').val('');

		}else{
			$('#tax2Setting').html(systemSettings[i][1]+'(%)');
		}
		if (systemSettings[i][2] == '') {

			$(".taxThree").attr("disabled", true);
			$('.taxThree').addClass("inputHidden");
			$('.tax3').css({
				"background-color" : "#eeeeec"
			});
			$('.taxThree').val('');
		}else{
			$('#tax3Setting').html(systemSettings[i][2]+'(%)');
		}

		if (systemSettings[i][3] == '') {
			$(".taxFour").attr("disabled", true);
			$('.taxFour').addClass("inputHidden");
			$('.tax4').css({
				"background-color" : "#eeeeec"
			});
			$('.taxFour').val('');
		}else{
			$('#tax4Setting').html(systemSettings[i][3]+'(%)');
		}
	}
}

function jstlDetailLoop(row) { /*Jstl is deleted loop function*/
	var length = 0;
	length = row - 1;
	for (i = 0; i <= length; i++) {
		if ($('#isDeleteCount' + i).val() == 'true') {
			$("#detailDivId" + i).remove();
		}
	}
}
$(document).ready(function(){
	$("#dateRow0").attr('readonly', true);
})