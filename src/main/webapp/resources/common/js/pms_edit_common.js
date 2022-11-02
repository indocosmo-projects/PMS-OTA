function deleteMaster(id,url,redirectUrl) {
	if (id == 0) {
		alert('Cant delete');
		return false;
	} else {

		$.ajax({
			url : url ,//',
			type : 'POST',
			data : {
				ids : id
			},
			success : function(response) {
				if (response == true) {
					window.location = redirectUrl;
				} else {
					alert('Can not delete.');
				}
			}
		});
	}
}


function numericalValidation(id) {
	$(id).keydown(function(e) {
		// Allow: backspace, delete, tab, escape, enter and .
		if ($.inArray(e.keyCode, [ 46, 8, 9, 27, 13, 110,
		                           190 ]) !== -1
		                           ||
		                           // Allow: Ctrl+A
		                           (e.keyCode == 65 && e.ctrlKey === true) ||
		                           // Allow: home, end, left, right
		                           (e.keyCode >= 35 && e.keyCode <= 39)) {
			// let it happen, don't do anything
			return;
		}
		// Ensure that it is a number and stop the keypress
		if (e.shiftKey || e.keyCode < 48 || e.keyCode > 57
				&& e.keyCode < 96 || e.keyCode > 105) {
			e.preventDefault();
		}
	});
}

function validation(className) {

	var isRequired = "true";
	var count = 0;

	$(className).each(function() {

		var ids = $(this).attr('id');

		if ($('#' + ids).val() == '' || $('#' + ids).val() == -1 ) {

			$('#'+ids+'_check').css("display","none");
			$('#'+ids+'_warning').css("display","block");

			isRequired = "false";
			$('#'+ids).addClass('pms_error verified');

		}else{
			$('#'+ids+'_warning').css("display","none");
			$('#'+ids+'_check').css("display","block");
			$('#'+ids).removeClass('pms_error');
			$('#'+ids).addClass('verified');

		}
	});

	if(isRequired == 'false') {

		jumpToErrorTab(true ,'pms_error');
	}

	return isRequired;
}

function jumpToErrorTab(isClass, str) {
	if(isClass)
		$('.nav-tabs a[href="#' + $('.' + str + ':first').closest('.tab-pane').attr('id') + '"]').tab('show');
	else
		$('.nav-tabs a[href="#' + str + '"]').tab('show');
}


function onloadValidator(cls){

	$(cls).bind('keyup change focusout', function(e) {

		var ids = e.target.id;

		if (e.which != 9) {

			if ($('#' + ids).val() == '') {

				if($('#'+ids).hasClass('verified')){

					$('#'+ids+'_warning').css("display","block");
					$('#'+ids).addClass('pms_error');
				}

				$('#'+ids+'_check').css("display","none");

			} else {

				if($('#'+ids).hasClass('verified')){

					$('#'+ids+'_check').css("display","block");
				}

				$('#'+ids+'_warning').css("display","none");
				$('#'+ids).removeClass('pms_error');
			}
		}
	}); 
}

function refresh(id,encryptedId,cls,url){

	var txt='';

	if (id == 0) {
		$(cls).each(function() {
			var ids = $(this).attr('id');
			if(! $(this).is('select')){
				$('#' + ids).val('');
				$('#' + ids).text('');
			}
			$('#'+ids +'_warning').css("display","none");
			$('#'+ids +'_check').css("display","none");
			$('#'+ids).removeClass('pms_error');
			$('#'+ids).removeClass('verified');
		});
	} else {

		$.ajax({
			url: url+ encodeURIComponent(encryptedId),
			async:false,
			success: function (response) {
				txt=response;
			}
		});
	}
	return txt;
}

function codeExist(value, serverUrl, codeInput, msg){
	$.ajax({
		url :serverUrl ,
		type : 'GET',
		data : {
			'code' : value},
			success : function(response) {
				if (response == true) {
					$('#' + codeInput).val('');
					$('#' + codeInput + "_check").hide();
					$('#' + codeInput + "_warning").show();
					
					$('#alert_modal').modal('toggle');
					$('#alert_modal_close').click(function(){
						$('#alert_modal').modal('hide');
					});
					return false;
				}else{
					$('#' + codeInput + "_warning").hide();

					if($('#' + codeInput).val() != '') {
						$('#' + codeInput + "_check").show();
					}
				}
			}
	});
}

function validateEmail(className) {
   
	var isRequired = "true";
	var count = 0;

	$(className).each(function() {

		var ids = $(this).attr('id');
		var sEmail=$('#' + ids).val();
		var emailFilter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		if (!emailFilter.test(sEmail)) {

			$('#'+ids+'_check').css("display","none");
			$('#'+ids+'_warning').css("display","block");

			isRequired = "false";
			$('#'+ids).addClass('pms_error verified');

		}else{
			$('#'+ids+'_warning').css("display","none");
			$('#'+ids+'_check').css("display","block");
			$('#'+ids).removeClass('pms_error');
			$('#'+ids).addClass('verified');

		}
	});
	return isRequired;
}


/**
 * prevent decimal point
 * @param id
 */
function preventDecimalPoint(id) {
	$(id).keydown(
			function(e) {
				if(e.keyCode == 110) {
					e.preventDefault();
				}
			}
	);
}

function OnloadValidationEdit(className) {
	var isRequired = "true";
	var count=0;
	$(className).each(function() {
		var ids = $(this).attr('id');
		if ($('#' + ids).val() == '' || $('#' + ids).val() == -1 ) {
			$('#validator_' + ids).html("");
		}else{
			$('#validator_' + ids).removeClass('validator_cls_img');
			$('#validator_' + ids).addClass('dataenter_cls_img');
		}
	});


	return isRequired;
}

function resetValidators(className){
	$(className).each(function() {
		var ids = $(this).attr('id');
		$('#'+ids+'_warning').css("display","none");
		$('#'+ids+'_check').css("display","none");
	});		
}

