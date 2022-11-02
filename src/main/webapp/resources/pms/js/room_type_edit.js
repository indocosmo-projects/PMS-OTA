var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){
		
		 
		  var confirm = $mdDialog.confirm()
			.title("Are you sure you want to delete this Room Type?")
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


/**
 * onload function
 */
function onPageLoad() {
	
	$('#tabs').tabs();
	
	if ($('#roomTypeId').val() == 0) {
		
		$('#divRooms').hide();
		$('#rackrate').hide();
		$('#delete_btn').hide();
		$('#liRackRate').hide();
	} else {
		
		$('#txtCode').attr("readonly", "readonly");
	}
	
	$('#txtCode').change(function() {
		
		var code = $('#txtCode').val();
		
		if(code != '') {

			codeExist(code, "../roomType/codeExists", "txtCode", "Code is already exist.");
		}
	});
	
	onloadValidator('.validator');
	numericalValidation('.pms_text_numbers');
	preventDecimalPoint('.pms_text_numbers');
	OnloadValidationEdit('.validator');
	
	$('#lnkRooms').click(function(){

		gotoRoomsPage();
	});
	
	$('#txtOverBookingPrcntg').keyup(function(e) {
		
		if(parseInt($(this).val()) > 100) {
			
			$(this).val('');
		}
	});
}

/**
 * reload the room type edit page with the selected id
 */
function refreshData() {
	
	var id = $('#roomTypeId').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_roomtype";
	var url = "../roomType/getRecord?ids=";
	var txt = refresh(id, encryptedId, cls, url);
	
	if(txt != '') {

		$("#content").html(txt);
		onPageLoad();
	} else if(id == 0) {
		
		$('#txtOverBookingPrcntg').val('0');
		$('#displayOrder').val('0');
		
		$('.chk_occ').each(function() {
			$(this).attr('checked', false);
		});
	}
}

/**
 * filling zero if the val is null
 * @param com
 */
function fillZeroIfNull(com) {
	if($(com).val() == '')
		$(com).val('0');
}

/**
 * validate and submit the form to save/update room type
 * @returns {Boolean}
 */
function saveData() {
	
	if (validation('.validator') == "false") {
		
		return false;
	} else {
	
		var data = SAVE_ACTION_INS;
		
		if($('#roomTypeId').val() != 0){
		
			data = SAVE_ACTION_UPD;
		}
		
		$("#rackRateDiv").html('');
		sessionStorage.setItem("action", data);
		document.roomType.submit();
	}
}

/**
 * redirect to list page
 */
function cancel() {
	
	window.location = '../roomType/roomTypeList';
}

/**
 * pass the id to server for soft deletion
 */
function deleteData() {
	
	var id = $('#roomTypeId').val();
	url = "../roomType/delete";
	redirectUrl = "../roomType/roomTypeList";
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	/*var isConfirm = confirm("Delete this Room Type?");
	
	if (isConfirm == true) {
		
		deleteMaster(id, url, redirectUrl);
	}*/

}

/**
 * redirect to room list page with the selected room type
 */
function gotoRoomsPage() {
	
	sessionStorage.removeItem('searchData');
	sessionStorage.removeItem('simpleSearch');
	
	sessionStorage.setItem('roomTypeId', $('#roomTypeId').val());
	window.location.href = "../room/roomList";
}
