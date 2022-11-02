var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){   
		 
	
		  var confirm = $mdDialog.confirm()
			.title("Delete this Discount setting?")
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

function onLoadSetup() {
	$( "#tabs" ).tabs();
	$('#tab_header').removeClass('ui-widget-header');
	dateFormat=convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	if ($('#discountId').val() == 0) {
		$('#delete_btn').hide();
		$("#txtcode").attr("readonly", false);
		$('#txtcode').change(function(){
			codeExist($('#txtcode').val(),'../discount/codeExist','txtcode','Code exists.');
		});
	}
	else{
		$('#txtcode').attr('readonly', true);
		$('.datepicker').each(function(){
			if($(this).val()!=''){
				var tempDate=$(this).val().split(' ');
				var date=new Date(tempDate[0]);
				$(this).val($.datepicker.formatDate(dateFormat,date)).attr("readonly", true);			
			}
		});
	}
	$('[data-toggle="tooltip"]').tooltip(); 
	DatepickerWithCurrentDateFormat();
	onLoadAddConstrains();
	onloadValidator('.validator');
	numericalValidation('#disc_amount,#disc_pc');
	alterFieldsWithPlan();
	alterFieldsWithCalcMethod();
	OnloadValidationEdit('.validator');
	function checkPerctg(com){

		val = $(this).val();

		if(val>100) {
			return false;
		}
	}

	/**
	 * setting input fields with plan type and with calculation type
	 */	
	$('#disc_type').change(alterFieldsWithPlan);
	$('#calc_mode').change(alterFieldsWithCalcMethod);
};

/**
 * function for settting  fields with plan type
 */
function alterFieldsWithPlan() {
	var selected = $('#disc_type').val();
	if (selected == '1') {
		$('#isopen').attr("disabled", true);
		$('.alter_with_general').hide();
		$('#rate_plan').removeAttr("disabled");
		$('.alter_with_plan').show();
	}
	if (selected == '2') {
		$('#isopen').removeAttr("disabled");
		$('.alter_with_general').show();
		$('#rate_plan').attr("disabled", true);
		$('.alter_with_plan').hide();
	}
}

/**
 * function  setting  fields with Calculation Method
 */
function alterFieldsWithCalcMethod() {
	var selected = $('#calc_mode').val();
	if (selected == FALSE) {
		$('#disc_pc_section').hide();
		$('#disc_pc').val(null).removeClass("validator additional");
		$('#disc_amount').addClass("validator additional");
		if($('#disc_pc').hasClass('verified')){
			$('#disc_amount').addClass("pms_error");
			$('#disc_amount_warning').css("display","block");
		}
		$('#disc_amt_section').show();
	}
	if (selected == TRUE) {
		$('#disc_pc_section').show();
		$('#disc_amt_section').hide();
		$('#disc_pc').addClass("validator additional");
		$('#disc_amount').val(null).removeClass("validator additional");
		if($('#disc_amount').hasClass('verified')){
			$('#disc_pc').addClass("pms_error");
			$('#disc_pc_warning').css("display","block");
		}
	}
	if($('#disc_pc').hasClass('verified') || $('#disc_amount').hasClass('verified')){
		$('#disc_pc').addClass('verified');
		$('#disc_amount').addClass('verified');
	}
}

function refreshData() {
	var id = $('#discountId').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_discount,.discount_validator_cls";
	var url = "../discount/getRecord?ids=";
	
	$('#disc_type').prop('selectedIndex',0);
	$('#rate_plan').prop('selectedIndex',0);
	$('#calc_mode').prop('selectedIndex',0);
	alterFieldsWithPlan();
	alterFieldsWithCalcMethod();
	
	var txt=refresh(id, encryptedId, cls, url);
	if(txt!=''){
		$("#content").html(txt);
		onLoadSetup();
	}
}

function saveData() {
	var commonValidationResult=validation('.validator');
	var additionalValidationResult=additionalValidation('.additional');	
	if (commonValidationResult==FALSE|| additionalValidationResult==FALSE ) {
		return false;
	}
	else {
		 $('#save_btn').attr("disabled", true);

		var data = SAVE_ACTION_INS;
		if ($('#valid_to').val()==""){
			$('#valid_to').prop( "disabled", true ); 
		}
		if ($('#discountId').val() != 0) {
			data = SAVE_ACTION_UPD;
		}
		sessionStorage.setItem("action", data);
		document.discount.submit();
	}
}

function cancel() {
	$("#discount_div").dialog("close");
	$("#discount_div").html("");
}

function deleteData() {
	var id = $('#discountId').val();
	url = "../discount/delete";
	redirectUrl = "../discount/discountList";
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	/*var isConfirm = confirm("Are you sure do you want to delete ?");
	if (isConfirm == true) {
		deleteMaster(id, url, redirectUrl);
	}*/
}

/**
 * setting on load constrains to discount page 
 */
function onLoadAddConstrains(){
	/**
	 * bind validation
	 */
	$('.additional').bind('keyup change focusout ',function(e) {	
		var id = e.target.id;
		if($('#'+id).hasClass('verified')){

			if (id=='disc_pc' && $('#calc_mode').val()=='true' ) {
				if($('#'+id).val() >100 || $('#'+id).val()==''){
					$('#'+id+'_warning').css("display","block");
					$('#'+id).addClass('pms_error');
					$('#'+id+'_check').css("display","none");
				}
				else{
					$('#'+id+'_check').css("display","block");
					$('#'+id+'_warning').css("display","none");
					$('#'+id).removeClass('pms_error');
				}
			}
			if(id=='disc_amount' && $('#calc_mode').val()=='false'){
				if($('#'+id).val()==''){
					$('#'+id+'_warning').css("display","block");
					$('#'+id).addClass('pms_error');
					$('#'+id+'_check').css("display","none");
				}
				else{
					$('#'+id+'_check').css("display","block");
					$('#'+id+'_warning').css("display","none");
					$('#'+id).removeClass('pms_error');
				}				
			}

			if (id =='valid_to') {
				if($('#'+id).val() !=''){
					if($('#valid-from').val()!=''){
						var fromDate=$.datepicker.parseDate(dateFormat,$('#valid_from').val());
						var toDate=$.datepicker.parseDate(dateFormat,$('#valid_to').val());
						if(toDate<fromDate){
							datePeriodWrngAlert();
						}
						else{
							datePeriodCrctAlert();
						}
					}
					else{
						datePeriodCrctAlert();
					}
				}
				else{
					datePeriodAlertReset();
				}
			}
		}
	});

	/**
	 * add reset for todate field
	 */	
	$('#reset_to_date').click(function(e){
		datePeriodAlertReset();
		$('#temp_valid_to').val('');
		$('#valid_to').val('');
	});
}

function additionalValidation(selector){
	var isRequired = TRUE;
	var ids = $(this).attr('id');
	if ($('#calc_mode').val() == TRUE && $('#disc_pc').val() >100) {
		$('#disc_pc_warning').css("display","block");
		$('#disc_pc_check').css("display","none");
		$('#disc_pc').addClass('pms_error');
		jumpToErrorTab(false ,'more');
		isRequired=FALSE;
	}
	if ($('#valid_to').val()!='' && $('#valid-from').val()!='') {
		var fromDate=$.datepicker.parseDate(dateFormat,$('#valid_from').val());
		var toDate=$.datepicker.parseDate(dateFormat,$('#valid_to').val());
		if(toDate<fromDate){
			datePeriodWrngAlert();
			jumpToErrorTab(false ,'general');
			isRequired=FALSE;
		}
		else{
			datePeriodCrctAlert();
			$('#valid_to').addClass("verified");
		}
	}	
	if($('#valid_to').val() !='' && $('#valid-from').val()==''){
		datePeriodCrctAlert();
		$('#valid_to').addClass("verified");
	}
	else{
		$('#valid_to').addClass("verified");
	}
	return isRequired;
}

/**
 * Initializing datepicker and setting CurrentDateFormat from System Setting Module
 */
function DatepickerWithCurrentDateFormat() {
	$("#valid_to").datepicker({dateFormat:dateFormat});		
	$('#valid_from').datepicker({
		minDate: null,
		dateFormat:dateFormat,
		onSelect: function(date) {
			$('#valid_to').datepicker('option',{
				minDate:date,
				dateFormat:dateFormat
			}
			);
			datePeriodAlertReset();
			$('#valid_to').val('');
		}
	});
}

function datePeriodCrctAlert(){
	$('#valid_to_warning').css("display","none");
	$('#valid_to_check').css("display","block");
	$('#valid_to').removeClass('pms_error');
}
function datePeriodWrngAlert(){
	$('#valid_to_warning').css("display","block");
	$('#valid_to_check').css("display","none");
	$('#valid_to').addClass('pms_error');
}

function datePeriodAlertReset(){
	$('#valid_to_warning').css("display","none");
	$('#valid_to_check').css("display","none");
	$('#valid_to').removeClass('pms_error');
}