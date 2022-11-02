var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){
		
		 
		  var confirm = $mdDialog.confirm()
			.title("Are you sure you want to delete this Corporate?")
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


function onLoadSetup(){
	$( "#tabs" ).tabs();
	
	if ($('#corporateId').val() == 0) {
		$('#delete_btn').hide();
		$('#txtcode').prop("readonly", false);
		$('#txtcode').change(function(){
			codeExist($('#txtcode').val(),'../corporate/codeExist','txtcode','Code exists.');
		});
	}
	
	statusTagChanger(); // call status changer	
	onLoadBindEvents();
	onloadValidator('.validator');
	numericalValidation('.num_field');
	OnloadValidationEdit('.validator');
}

/**
 * set events and callback function related with this module
 */
function onLoadBindEvents(){
	var code_no=$("#contact_office").val().split('-');//spliting ph no and assigning
	$("#contact_office_code").val(code_no[0]);
	$("#contact_office_no").val(code_no[1]);

	/**	add change event and set call back function
	 */
	$(".s_ph_no" ).bind( "change focusout", function(){
		if($(".s_ph_no").hasClass('verified')){
			$("#contact_office").val($("#contact_office_code").val()+"-"+$("#contact_office_no").val());
			/*if($("#contact_office_code").val()=="" || $("#contact_office_no").val()==""){
				$('#contact_office_warning').css("display","block");
				$('#contact_office_check').css("display","none");
			}
			else{
				$('#contact_office_warning').css("display","none");
				$('#contact_office_check').css("display","block");
			}*/
		}
	});
	
	$(".additional" ).bind( "change keyup focusout", function(e){
		var id = e.target.id;
		
		if($("#"+id).hasClass('verified')){
			if(id=="contact_email"){
				
				if((!isEmail($('#'+id).val()))){
					
					$('#'+id+'_check').css("display","none");
					$('#'+id+'_warning').css("display","block");
					$('#'+id).addClass('pms_error');
				}
				else{
					$('#'+id+'_warning').css("display","none");
					$('#'+id+'_check').css("display","block");
					$('#'+id).removeClass('pms_error');
				}
			}
			else if(id=="contact_mobile"){
				if(! isMobile($('#contact_mobile').val())){
					$('#'+id+'_check').css("display","none");
					$('#'+id+'_warning').css("display","block");
					$('#'+id).addClass('pms_error');
				}
				else{
					$('#'+id+'_warning').css("display","none");
					$('#'+id+'_check').css("display","block");
					$('#'+id).removeClass('pms_error');
				}
			}
/*			else if(id=="contact_office_code" || id=="contact_office_no"){
				if($("#contact_office_code").val()=="" || $("#contact_office_no").val()==""){
					$("#contact_office_check").css("display","none");
					$("#contact_office_warning").css("display","block");
					if($("#contact_office_code").val()==""){
						$("#contact_office_code").addClass('pms_error');
					}
					else{
						$("#contact_office_no").addClass('pms_error');
					}
				}
				else{
					$("#contact_office_check").css("display","block");
					$("#contact_office_warning").css("display","none");
					$("#contact_office_code").removeClass('pms_error');
					$("#contact_office_no").removeClass('pms_error');
				}
			}*/
		}
	});

	/**pop up for rating */	
	$("#rating_change").click(function (e)
			{
		$("#ratings input:radio[name=rating][value='" + $("#current_rating").val() + "']").prop("checked",true);
		e.preventDefault();
			});
	$(".rbtnClose").click(function (e)
			{	 
		e.preventDefault();
		$("#ratingModal").modal('toggle');
			});

	$("#rbtnSubmit").click(function (e)
			{
		$("#current_rating").val($("#ratings input:radio:checked").val());	
		$("#ratingModal").modal('toggle');
		e.preventDefault();
			});

	/**popup for status*/
	$("#status-change").click(function (e)
			{
		$("#status input:radio[name=status][value='" + $("#current_status").val() + "']").prop("checked",true);
		e.preventDefault();
			});

	$(".sbtnClose").click(function (e)
			{
		$("#statusModal").modal('toggle');
		e.preventDefault();
			});

	$("#sbtnSubmit").click(function (e)
			{
		$("#current_status").val($("#status input:radio:checked").val());
		if($('#current_status').val()!="1")
		{
			$('#cStatus').val("Inactive").addClass('inActive_style').removeClass('active_style');
		}
		else{
			$('#cStatus').val("Active").addClass('active_style').removeClass('inActive_style');
		}
		$("#statusModal").modal('toggle');
		e.preventDefault();
			});  				      				      
}

function ShowDialog(modal, val, effvar)
{	
	$("#overlay").show();
	$("#"+val).fadeIn(250);
	if (modal)
	{
		$("#overlay").unbind("click");
	}
	else
	{
		$("#overlay").click(function (e)
				{
			HideDialog(val);
				});
	}
}

function HideDialog(val,effvar)
{
	$("#overlay").hide();
	$("#"+val).fadeOut(200,function(){
		$("#"+effvar+" input:radio[name="+effvar+"][value='" + $("#current_"+effvar).val() + "']").prop("checked",true);} );    
} 

/** save function*/
function saveData() {
	
	if($('#contact_email').val()==""){
		$('#contact_email_warning').css("display","none");
		$('#contact_email_check').css("display","block");
		$('#contact_email').removeClass('pms_error');
		
	} 
	
	var commonValidationResult=validation('.validator');
	var additionalEmptyCheckResult=validation('.additional');
	var additionalValidationResult=additionalValidation();	
	
	
	if (commonValidationResult=="false" || additionalValidationResult=="false" || additionalEmptyCheckResult=="false") {
		
		return false;
		
	}
	else {
			
		var data = SAVE_ACTION_INS;
		if ($('#corporateId').val() != 0) {
			data = SAVE_ACTION_UPD;
		}
		sessionStorage.setItem("action", data);
		if($("#contact_office_code").val() != "" || $("#contact_office_no").val() != ""){
			$("#contact_office").val($("#contact_office_code").val()+"-"+$("#contact_office_no").val());
		}
		document.corporate.submit();
	}
}

/** refreshData function */
function refreshData() {
	var id = $('#corporateId').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_corporate,.corporate_validator_cls";
	var url = "../corporate/getRecord?ids=";
	var txt=refresh(id, encryptedId, cls, url);
	if(txt!=''){

		$("#content").html(txt);
		onLoadSetup();
	}
}

/**
 * cancel function
 */
function cancel() {
	$("#corporate_div").dialog("close");
	$("#corporate_div").html("");
}

/**
 * deleteData function
 */
function deleteData() {
	var id = $('#corporateId').val();
	url = "../corporate/delete";
	redirectUrl = "../corporate/corporateList";
	
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	
	/*var isConfirm = confirm("Are you sure do you want to delete ?");
	if (isConfirm == true) {
		deleteMaster(id, url, redirectUrl);
	}*/
}
/**
 *function to set status on page according to the current status
 */
function statusTagChanger(){
	if($('#current_status').val()=="1")
	{
		$('#cStatus').val("Active").addClass('active_style').removeClass('inActive_style');
	}
	else{
		$('#cStatus').val("Inactive").addClass('inActive_style').removeClass('active_style');
	}

}

/**
 * custom validation for this module
 */
function additionalValidation(){
	var isRequired ="true";	
	if($('#contact_email').val()!='' && !isEmail($('#contact_email').val())){ 
		
		$('#contact_email_check').css("display","none");
		$('#contact_email_warning').css("display","block");
		$('#contact_email').addClass('pms_error');
		isRequired="false";
		jumpToErrorTab(false ,'contactDtls');
		//alert("please enter required format");
		
	}
	

	if(! isMobile($('#contact_mobile').val()) && $('#contact_mobile').val()!=''){
		$('#contact_mobile_check').css("display","none");
		$('#contact_mobile_warning').css("display","block");
		$('#contact_mobile').addClass('pms_error');
		isRequired="false";
		jumpToErrorTab(false ,'contactDtls');
	}

	/*if($("#contact_office_code").val()=="" || $("#contact_office_no").val()==""){
		$('#contact_office_check').css("display","none");
		$('#contact_office_warning').css("display","block");
		if($("#contact_office_code").val()==""){
			$("#contact_office_code").addClass('pms_error');
		}
		else{
			$("#contact_office_no").addClass('pms_error');
		}	 
		jumpToErrorTab(false ,'contactDtls');
		isRequired="false";
	}*/

	return isRequired;
}

function isEmail(email) {
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	return regex.test(email);
}

function isMobile(no) {
	var regex = /^(\+\d{2})?\d{10}$/;

	return regex.test(no);
}