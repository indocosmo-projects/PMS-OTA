var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){
		
		 
		  var confirm = $mdDialog.confirm()
			.title("Are you sure you want to delete this Room?")
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
	
	$('#tab').tabs();
	
	if ($('#hdnRoomNo').val() == '') {
		
		$('#delete_btn').hide();
		$('#hdnNewRecord').val('true');
		$('#ddlHkStatus').val('2');
		$('#ddlOccStatus').val('1');
		
		$('#txtNumber').change(function() {
			
			var number = $('#txtNumber').val();
		
			if(number != '') {
			
				codeExist(number, "../room/roomExist", "txtNumber", "Room No. is already exist.");
				
			}
		});
	} else {
		
		$('#txtNumber').attr('readonly', 'readonly');
		$('#ddlInvStatus').val($('#hdnInvStatus').val());
		$('#ddlHkStatus').val($('#hdnHkStatus').val());
		$('#ddlOccStatus').val($('#hdnOccStatus').val());
	}
	
	$('#btnChange').click(function() {
		
		setInvStatus();
		$('#roomInvStatModal').modal('toggle');
	});
	
	$('#btnRoomInvStatOk').click(function() {
		
		changeInvStatus();
	});
	
	$('#btnRoomInvStatCancel, #btnRoomInvClose').click(function() {
		
		$('#roomInvStatModal').modal('toggle');
	});
	
	$('#btnRoomFeatureClose').click(function(){
		
		$('#roomFeatureModal').modal('toggle');
		
	});

	setInvStatus();
	
	$('#lnkAddFeature').on("click", function() {
		
		$('#txtRoomFeature').val('');
		$('#hdnRoomFeatureId').val('0');
		$('#txtRoomFeature_check').hide();
		$('#txtRoomFeature_warning').hide();
		$('#btnDeleteFeature').hide();
	});
	
	$('#btnSaveFeature').click(function() {
		
		addRoomFeature();
	});
	
	$('#btnCancelFeature, #btnRoomFeagetRecordtureClose').click(function() {
		
		$('#txtRoomFeature').val('');
		$('#roomFeatureModal').modal('toggle');
		$('#btnRoomFeatureClose').modal('toggle');
		
	});
	
	$('#btnDeleteFeature').click(function() {
		
		deleteRoomFeature();
	});
	
	
	
	
	$('#ddlRoomType').change(function() {
		
		$.ajax({
			url: "../roomType/getRoomType",
			type: 'POST',
			data: {
				id: $('#ddlRoomType').val()
			},
			success: function(result) {
				
				$('#roomTypeDetail').html(result.name);
			}
		});
		
	});
	
	
	//Fetch room features
	$.ajax({
		url: "getRoomFeatures",
		method: "POST",
		success: function(result) {
			
			var checkedFeatures = $('#hdnRoomFeatures').val().split(",");
			setFeatures(result, checkedFeatures);
		} 
	});
	
	onloadValidator(".validator");
}

/**
 * this method will check the invoice status saved in the db.
 */
function setInvStatus() {

	var id = "";
	var val = $('#hdnInvStatus').val();
	
	$('.chk_inv_stat').each(function() {
		
		id = $(this).attr('id');
		
		if($('#' + id).val() == val) {
			
			$('#' + id).prop('checked', 'checked');
			$('#txtInvStatus').val($('#' + id).attr('data-text'));
			
			return;
		}
	});
}

/**
 * this method will change the hidden invoice status for saving.
 */
function changeInvStatus() {
	
	var val = "", id = "";
	
	$('.chk_inv_stat').each(function() {
		
		id = $(this).attr('id');
		
		if($('#' + id).is(':checked')) {
			
			val = $('#' + id).val();
			$('#hdnInvStatus').val(val);
			$('#txtInvStatus').val($('#' + id).attr('data-text'));
			
			return;
		}
	});
	
	$('#roomInvStatModal').modal('toggle');
}

/**
 * refreh the edit page
 */
function refreshData() {
	var id = $('#hdnRoomid').val();
	
	
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_room";
	var url = "../room/getRecord?ids=";
	var txt = refresh(id, encryptedId, cls, url);
	
	if(txt != '') {

		$("#content").html(txt);
		onPageLoad();
	} 
	else if(id == 0) {
		
		$('#room_features input[type=checkbox]').each(function() {
			$(this).attr('checked', false);
		});
	}
	
}

/**
 * validate and submit the form
 * @returns {Boolean}
 */
function save() {
	
	if (validation('.validator') == "false") {
		if($("#txtNumber").val()==""){
			$("#txtNumber").focus();
		}
		if($("#ddlRoomType").val($("#ddlRoomType option:first").val()) && $("#txtNumber").val()!==""){
			$("#ddlRoomType").focus();
		}
		if($("#floorList").val($("#floorList option:first").val()) && $("#txtNumber").val()!==""){
			$("#floorList").focus();
		}
		return false;
	} else {
		
		var data = SAVE_ACTION_INS;
		
		if($('#hdnRoomNo').val() != '') {
			
			data = SAVE_ACTION_UPD;
		}
		
		sessionStorage.setItem("action", data);
		
		document.room.submit();
	}
}

/**
 * redirect to list pahe
 */
function cancel() {
	
	window.location = '../room/roomList';
}

/**
 * make an ajax call to server for deleting the selected room
 */
function deleteData() {
	
	var id = $('#masterId').val();
	url = "../room/delete";
	redirectUrl = "../room/roomList";
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	/*var isConfirm = confirm("Delete this room?");
	
	if (isConfirm == true) {
		
		deleteMaster(id, url, redirectUrl);
	}*/

}

/**
 * pass the new room feature to server for saving to db. 
 * reload the same page after saving.
 */
function addRoomFeature() {
	
	var roomFeature = $('#txtRoomFeature').val();
	var roomFeatureId = $('#hdnRoomFeatureId').val();;
	
	if(roomFeature != '') {
	
		$.ajax({
			
			url: '../room/addFeature',
			type: 'GET',
			data: {
				'id': roomFeatureId,
				'roomFeature': roomFeature
				},
			success: function(result) {
				
				if(result == 'exist') {
					
					$('#txtRoomFeature').val('');
					$("#featureExist_modal").modal('show');
					$("#featureExist_close").click(function(){
						$("#featureExist_modal").modal('hide');
					})
				} else {
					
					$('#roomFeatureModal').modal('toggle');
					
					var checkedFeatures = new Array();
					
					$('#divRoomFeatures input[type=checkbox]:checked').each(function() {
						checkedFeatures.push($(this).val());
					});
					
					setFeatures($.parseJSON(result), checkedFeatures);
				}
			},
			error: function() {
				
				$('#roomFeatureModal').modal('toggle');
				alert('Operation failed.');
			}
		});
	} else {
	
		$('#txtRoomFeature_warning').show();
	}
}

function deleteRoomFeature() {
	
	var roomFeatureId = $('#hdnRoomFeatureId').val();
	
	$.ajax({
		
		url: '../room/deleteRoomFeature',
		type: 'POST',
		data: {
			'id': roomFeatureId
		},
		success: function(result) {

			$('#roomFeatureModal').modal('toggle');

			var checkedFeatures = new Array();

			$('#divRoomFeatures input[type=checkbox]:checked').each(function() {
				checkedFeatures.push($(this).val());
			});

			setFeatures(result, checkedFeatures);
		}
	});
}

function setFeatures(features, checkedFeatures) {
	
	var roomFeatures = features;
	var features = "";
	var roomFeature;
	var checked = "";
	
	for (var i = 0; i < roomFeatures.length; i++) {
		
		roomFeature = roomFeatures[i];
		checked = "";
		
		if($.inArray(roomFeature.id.toString(), checkedFeatures) != -1) {
			
			checked = "checked=\"checked\"";
		}
		
		features += "<div class=\"col-sm-4 feature-div\"><div class=\"col-md-2 col-xs-2 check-box-area\"><input id='"+roomFeature.id+"' type=\"checkbox\" name=\"roomFeatures\" value=\""
			+ roomFeature.id + "\" " + checked + "><label for='"+roomFeature.id+"'></label></div><div class=\"col-md-9 col-xs-9 no-padding\"> <label class=\"feature-link\" onclick=\"featureClicked('"
			+ roomFeature.id + "','" + roomFeature.feature + "');\">" + roomFeature.feature + "</label></div></div>"
	}
	
	$('#divRoomFeatures').html(features);
}

function featureClicked(id, feature) {
	
	$('#hdnRoomFeatureId').val(id);
	$('#txtRoomFeature').val(feature);
	$('#validator_txtRoomFeature').html('');
	$('#btnDeleteFeature').show();
	$('#roomFeatureModal').modal('toggle');
}