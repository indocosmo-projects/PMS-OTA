var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){   
		 
	
		  var confirm = $mdDialog.confirm()
			.title("Delete this Season Range?")
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


function onLoadSeason(){

	if ($('#season_id_test').val() == '0') {
		$('#delete_btn').hide();
	}else{
		$('#txtcode').attr('readonly',true);

	}

	onloadValidator(".validator");
	OnloadValidationEdit('.validator');

	$('#txtcode').change(function(){
		codeExist($('#txtcode').val(),'../season/codeExist', 'txtcode', 'Code exists.');
	});

	if($('#colorCode').val()!=""){
		$('#colorCode').prop('readonly',true);
		$('#colorCode').css("background-color",$('#colorCode').val());
	}

	$('#colorCode').change(function(){
		$('#colorCode').prop('readonly',true);
		$('#colorCode').css("background-color",$('#colorCode').val());
	});


	isDirty=false;
	$('.seasonVal').change(function(){

		if($(this).val()!=$(this).attr("data-old")){

			isDirty=true;
		}
	});
}
/**
 * validation of dropdownlist for days and months
 */
function validate(){
	var idv = $(this).attr('id');
	if($('#' + idv).val()==""){
		$('#'+idv+'_check').css("display","none");
		$('#'+idv+'_warning').css("display","block");
		$('#'+idv).addClass('pms_error');

	}

	else{
		saveDatas();
	}
}
function ValOnChange(){
	var idv1 = $(this).attr('id');
	$('#'+idv1+'_check').css("display","block");
	$('#'+idv1+'_warning').css("display","none");
	$('#'+idv1).addClass('pms_error');

}


/**
 * page refresh
 */
function refreshData() {
	var id = $('#season_id_test').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_season";
	var url = "../season/getSeason?ids=";
	var txt=refresh(id, encryptedId, cls, url);

	$('#colorCode').prop('selectedIndex',0);
	$('#colorCode').find('option:selected').css('background-color', '#00BF99').trigger('change');
	$('#start_month').prop('selectedIndex',0);
	$('#start_day').prop('selectedIndex',0);
	$('#end_month').prop('selectedIndex',0);
	$('#end_day').prop('selectedIndex',0);
	
	if(txt!=''){
		$("#content").html(txt);
		onLoadSeason();
	}
}

/**
 * season save function
 * @returns {Boolean} when the validation fails
 */
function saveDatas() {
	if (validation(".validator") == "false") {
		if($("#txtcode").val()==""){
			$("#txtcode").focus();
		}
		if($("#txtname").val()=="" && $("#txtcode").val()!==""){
			$("#txtname").focus();
		}
		if($("#start_month").val()=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#start_month").focus();
		}
		if($("#start_day").val()=="" && $("#start_month").val()!=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#start_day").focus();
		}
		if($("#end_month").val()=="" && $("#start_day").val()!=="" && $("#start_month").val()!=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#end_month").focus();
		}
		if($("#end_day").val()=="" && $("#end_month").val()!=="" && $("#start_day").val()!=="" && $("#start_month").val()!=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#end_day").focus();
		}
		return false;
	} else {

		if(backendValidation() == "false"){
			var data = SAVE_ACTION_INS;
			if($('#season_id_test').val() != 0){
				data = SAVE_ACTION_UPD;
			}
			sessionStorage.setItem("action", data);		
			document.season.submit();
		}
	}
}


/**
 * page cancel function
 * redirect to account master list
 */
function cancel() {

	/*window.location = '../season';*/
	$("#seasondiv").dialog("close");
	$("#seasondiv").html("");
}


/**
 * delete record function
 */
function deleteData() {

	var id = $('#season_id_test').val();
	url = "../season/Delete";
	redirectUrl = "../season";
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	/*var isConfirm = confirm("Are you sure do you want to delete ?");
	if (isConfirm == true) {
		deleteMaster(id, url, redirectUrl);
	}*/
}


/**
 * date range validation
 * @returns isExist true when date range is exist/false is not exist
 */
function backendValidation(){
	var isExist="false";

	if(isDirty==true){
		var startMonth=$('#start_month').val();
		var startDay=$('#start_day').val();
		var endMonth=$('#end_month').val();
		var endDay=$('#end_day').val();
		var id = $('#season_id_test').val();
		var formObject=new Object();
		formObject.startmonth=startMonth;
		formObject.startday=startDay;
		formObject.endmonth=endMonth;
		formObject.endday=endDay;
		formObject.id=id;

		var jsonDate= JSON.stringify(formObject);

		$.ajax({
			async:false,
			url : '../season/dateVerification',
			type : 'POST',
			data : {
				'jsonDate' : jsonDate
			},
			success : function(response) {
				if("FAILURE" == response){
					
					$("#seasonRange_modal").modal("show");
					$("#seasonRange_modalClose").click(function(){
						$("#seasonRange_modal").modal("hide");
					});
					isExist="true";
				}			
			}
		});
	}
	return isExist;
}