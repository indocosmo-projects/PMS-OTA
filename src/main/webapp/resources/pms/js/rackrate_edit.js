var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){
		
		 
		  var confirm = $mdDialog.confirm()
			.title("Are you sure you want to delete this Rack Rate?")
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
	
	$scope.deleteTableRow = function(hdnDeletedId, rowId){
		
		 
		  var confirm = $mdDialog.confirm()
			.title("Are you sure you want to delete revenue sharing row  ?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
				 
				deleteTableRowCall(hdnDeletedId, rowId);
				
			}, function() {		
				//$("#newPaymentmyModal").modal('toggle');
          }); 	 
	  } 
	
	
	 

	  
}]);




var roomType;

/**
 * onload function
 */
function onPageLoad() {
	
	dateFormat = convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	
	$('#tabs').tabs();
	
	if($('#hdnId').val() == 0) {
	
		$('#delete_btn').hide();
		
		setCode();
		
		$('#ddlRatePeriod').change(function() {
			
			ratePeriodChange();
		});
		
		ratePeriodChange();
	} else {
		
		$('#txtCode').attr('readonly', true);
		
		var tempdate = $('#validityFrom').val().split(' ');
		var fromDate = new Date(tempdate[0]);
		
		tempdate = $('#validityTo').val().split(' ');
		var toDate = new Date(tempdate[0]);
		
		$('#validityFrom').val(fromDate);
		$('#validityTo').val(toDate);
		
		$('#divPeriodDesc').html("<label class=\"rtPrdDate\">" + $.datepicker.formatDate(dateFormat, fromDate) 
				+ "</label class=\"rtPrdDate\"> to <label>" + $.datepicker.formatDate(dateFormat, toDate) + "</label>");
	}

	onloadValidator(".validator");
	numericalValidation("#tblTariff input[type='text']");
	numericalValidation("#tblRevenue input[type='text']");
	
	$('#ddlRatePeriod, #ddlRoomType, .rdMealPlan').change(function() {
		
		setCode();
	});
	
	$('#btnAddRevSharRow').click(function() {
		
		addRevenueSharingRow();
	});
	
	
	$('#ddlRoomType').change(function() {
		
		setTableColumnVisibilty();
	});
	
	setTableColumnVisibilty();
	
	$('#revenSharingValidator').hide();
	
	/* for changing tariff table heading */
	setTariffTableHeading();
	
	$('#ddlIsTaxIncluded').change(function() {
		
		setTariffTableHeading();
	});
}

function setCode() {
	
	if($('#ddlRoomType').val() != '-1' && $('#ddlRatePeriod').val() != '-1') {
		$('#txtCode').val('RR' + "-" + $('#ddlRoomType option:selected').text()
				+ "-" + $('input[type=radio][name=mealPlan]:checked').val()
				+ "-" + $('#ddlRatePeriod option:selected').text());
	} else {
		$('#txtCode').val('');
	}
	
	if(($('#txtCode').val() != '') && ($('#hdnCode').val() != $('#txtCode').val())) {
		codeExist($('#txtCode').val(), '../rackRate/codeExist', 'txtCode', 'Code exists.');
	}
}


function ratePeriodChange() {
	
	var ratePeriodId = $('#ddlRatePeriod').val();
	
	if(ratePeriodId != '-1') {
		$.ajax({
			url : '../rackRate/getRatePeriod',
			type : 'POST',
			data : {
				id : ratePeriodId
			},
			success : function(response) {

				var fromDate = new Date(response.fromDate);
				var toDate = new Date(response.toDate);

				$('#divPeriodDesc').html("<label class=\"rtPrdDate\">" + $.datepicker.formatDate(dateFormat, fromDate) 
						+ "</label class=\"rtPrdDate\"> to <label>" + $.datepicker.formatDate(dateFormat, toDate) + "</label>");
				$('#validityFrom').val(fromDate);
				$('#validityTo').val(toDate);
			}
		});
	} else {
		$('#divPeriodDesc').val('');
	}
}

/**
 * change visibility of tariff table heading according to the value of tariff include taxes combo
 */
function setTariffTableHeading() {
	
	if($('#ddlIsTaxIncluded').val() == '1') {
		
		$('#tariffHeading1').show();
		$('#tariffHeading2').hide();
	} else {
		
		$('#tariffHeading2').show();
		$('#tariffHeading1').hide();
	}
}

/**
 * sets the column visibility of each column in tariff and revenue sharing table
 * according to the room type selected.
 */
function setTableColumnVisibilty() {

	var id = $('#ddlRoomType').val();

	if(id != -1) {
		$.ajax({

			url: '../corporateTaRate/occStat',
			type: 'POST',
			data: {

				'id': id
			},
			success: function(result) {

				roomType = JSON.parse(result);

				$('#lblRoomTypeName').html(roomType.name);
				enableDisableColumn(roomType.supportSingleOcc, '.single');
				enableDisableColumn(roomType.supportDoubleOcc, '.double');
				enableDisableColumn(roomType.supportTripleOcc, '.triple');
				enableDisableColumn(roomType.supportQuadOcc, '.quad');
			},
			error: function() {

				alert('Error occured.');
			}
		});
	}
}

/**
 * this function will enable or disable column
 * @param status true or false
 * @param col class of the column
 */
function enableDisableColumn(status, col) {
	
	if(!status) {
		
		$(col).addClass('disabled');
		$(col + ' > input[type=text]').addClass('disabled');
		$(col + ' > input[type=text]').val('');
		$(col + ' > input[type=text]').attr('disabled', true);
	} else {
		
		$(col).removeClass('disabled');
		$(col + '> input[type=text]').removeClass('disabled');
		$(col + ' > input[type=text]').attr('disabled', false);
	}
}

/**
 * 
 */
var rowRevenueCount = 0;
function addRevenueSharingRow() {
	
	var departmentCombo = $('.revn-dept').html();
	
	var row = "<tr id=\"newRowRevenue" + rowRevenueCount + "\" class=\"revenue-row new-row\">"
		+ "<td><select class=\"form-control revn-dept\" onchange=\"checkRevenueDepartmentDuplication();\">" + departmentCombo + "</select></td>"
		+ "<td class=\"single " + (roomType.supportSingleOcc ? "" : "disabled") 
		+ "\"><input type=\"text\" maxlength=\"3\" class=\"form-control form-control-inline input-medium\" " + (roomType.supportSingleOcc ? "" : "disabled=\"disabled\"") 
		+ " onkeyup=\"checkTotalPerctg('single', this);\"></td>"
		+ "<td class=\"double " + (roomType.supportDoubleOcc ? "" : "disabled") 
		+ "\"><input type=\"text\" maxlength=\"3\" class=\"form-control form-control-inline input-medium\" " + (roomType.supportDoubleOcc ? "" : "disabled=\"disabled\"") 
		+ " onkeyup=\"checkTotalPerctg('double', this);\"></td>"
		+ "<td class=\"triple " + (roomType.supportTripleOcc ? "" : "disabled") 
		+ "\"><input type=\"text\" maxlength=\"3\" class=\"form-control form-control-inline input-medium\" " + (roomType.supportTripleOcc ? "" : "disabled=\"disabled\"") 
		+ " onkeyup=\"checkTotalPerctg('triple', this);\"></td>"
		+ "<td class=\"quad " + (roomType.supportQuadOcc ? "" : "disabled") 
		+ "\"><input type=\"text\" maxlength=\"3\" class=\"form-control form-control-inline input-medium\" " + (roomType.supportQuadOcc ? "" : "disabled=\"disabled\"") 
		+ " onkeyup=\"checkTotalPerctg('quad', this);\"></td>"
		+ "<td class=\"delete-btn\"><button type=\"button\" class=\"btn btn-danger btn-xs\" onclick=\"deleteRevenueRow('', 'newRowRevenue" + rowRevenueCount + "');\"><i class=\"fa fa-trash-o \"></i></button></td>"
		+ "</tr>";
	
	if($('#tblRevenue tr').length == 8) {
		
		$('#divRevnSharing').addClass('revn-sharing-div');
	}
	
	$('#tbodyRevenueSharing').append(row);
	rowRevenueCount++;
	numericalValidation("#tblRevenue input[type='text']");
}

/**
 * Check whether there are any duplication in revenue department in revenue sharing table.
 * @returns {Boolean} true if there is no duplication
 */
function checkRevenueDepartmentDuplication() {
	
	var departments = new Array();
	var count = 0;
	var isOk = "true";
	
	$('.revenue-row .revn-dept').each(function() {
		
		var val = $(this).val();
		count++;
		
		if($.inArray(val, departments) != -1) {
			
			isOk = "false";
			showMessageForRevenueSharing('Duplicate department in row ' + count);
			
			return false;
		}
		
		departments.push(val);
	});
	
	if(isOk == "false") return false;
	
	return true;
}

/**
 * Remove all rows having every cell value is null.
 */
function removeNullRowInRevenue() {
	
	var filled = false;
	
	$('#tblRevenue .revenue-row').each(function() {
		
		var rowId = $(this).attr('id');
		var inputs = $(this).find('input[type=text]:enabled');
		
		for(var i = 0; i < inputs.length; i++) {
			
			if($(inputs[i]).val() != '' && parseFloat($(inputs[i]).val()) != 0) {
				
				filled = true;
				break;
			}
		}
		
		if(!filled) {
			$(this).find('a').click();
		}
		
		filled = false;
	});
}

/**
 * Check total percentage in revenue sharing table.
 * return false if the total for one column is not 100
 * @param col
 * @param com
 * @returns {Boolean}
 */
function checkTotalPerctg(col, com) {
	
	var sum = 0;
	var val;
	var isOk = "true";
	
	$('#tblRevenue .revenue-row .' + col + ' input[type=text]').each(function() {
		
		val = $(this).val();
		
		if(val != '') {
			
			sum += parseFloat(val);
			
			if(sum > 100) {
				
				showMessageForRevenueSharing('Total percentage exceeded 100 in column ' + col);
				
				if(com != null) {
					
					$(com).addClass('error-txt');
					
					setTimeout(function() {
						
						$(com).removeClass('error-txt');
					}, 3000);
				}
				
				isOk = "false";
				
				return false;
			}
		}
	});
	
	if(com == null && sum < 100) {
		
		showMessageForRevenueSharing('Total percentage is not 100 in ' + col);
		
		return false;
	} else if(com == null && sum > 100) {
		
		showMessageForRevenueSharing('Total percentage exceeded 100 in column ' + col);
		
		return false;
	}
	
	if(isOk == "false") {
		
		return false;
	}
	
	return true;
}

/**
 * Delete row from revenue sharing table
 */
function deleteRevenueRow(hdnDeletedId, rowId) {
	
	if($('.revenue-row').length != 1) {
		
		angular.element(document.body).scope().deleteTableRow(hdnDeletedId, rowId);
		
		/*if(confirm("Delete this row?") == 1) {
			
			if(hdnDeletedId != '') {

				$('#' + hdnDeletedId).val('true');
				$('#' + rowId).removeClass('revenue-row');
				$('#' + rowId).hide();
			} else {

				$('#' + rowId).remove();
			}
			
			if($('#tblRevenue tr').length == 7) {
				
				$('#divRevnSharing').removeClass('revn-sharing-div');
			}
		}*/
	} else {
		
		showMessageForRevenueSharing('At least one row is required');
	}
}


function deleteTableRowCall(hdnDeletedId, rowId){
 
	if(hdnDeletedId != '') {

		$('#' + hdnDeletedId).val('true');
		$('#' + rowId).removeClass('revenue-row');
		$('#' + rowId).hide();
	} else {

		$('#' + rowId).remove();
	}
	
	if($('#tblRevenue tr').length == 7) {
		
		$('#divRevnSharing').removeClass('revn-sharing-div');
	}
}




/**
 * Showing validation message for Revenue Sharing table
 * @param msg
 */
function showMessageForRevenueSharing(msg) {
	
	$('#revenSharingValidator').html(msg);
	$('#revenSharingValidator').slideDown();
	
	setTimeout(function() {
	
		$('#revenSharingValidator').slideUp();
	}, 3000);
}

/**
 * This function will set the names for Revenue Sharing table components for correct mapping at server side
 */
function setNameForRevSharing() {
	
	var index = $('#tblRevenue .old-row').length;
	
	assignName('.revn-dept', 'departmentId', index);
	
	if(roomType.supportSingleOcc)
		assignName('.single input[type=text]', 'singlePc', index);
	
	if(roomType.supportDoubleOcc)
		assignName('.double input[type=text]', 'doublePc', index);
	
	if(roomType.supportTripleOcc)
		assignName('.triple input[type=text]', 'triplePc', index);
	
	if(roomType.supportQuadOcc)
		assignName('.quad input[type=text]', 'quadPc', index);
}

/**
 * iterate the elements specified by the class and assign names according to 
 * the field and index. Format: rateDistribution[index].field.
 * This is for mapping elements in server side.
 * @param cls
 * @param field
 * @param index
 */
function assignName(cls, field, index) {
	
	$('#tblRevenue .new-row ' + cls).each(function() {
		
		$(this).attr('name', 'rateDistribution[' + index + '].' + field);
		index++;
	});
}

/**
 * save function
 */
function saveData() {
	
	if (validation('.validator') == "false") {
		
		return false;
	} else if(!checkRevenueDepartmentDuplication()) {
		
		jumpToErrorTab(false ,'revenue_sharing');
		return false;
	} else if((roomType.supportSingleOcc && !checkTotalPerctg('single', null)) 
			|| (roomType.supportDoubleOcc && !checkTotalPerctg('double', null)) 
			|| (roomType.supportTripleOcc && !checkTotalPerctg('triple', null)) 
			|| (roomType.supportQuadOcc && !checkTotalPerctg('quad', null))) {
		
		jumpToErrorTab(false ,'revenue_sharing');
		return false;
	} else {
		
		removeNullRowInRevenue();
		setNameForRevSharing();
		
		var data = SAVE_ACTION_INS;
		
		if($('#hdnId').val() != 0) {
		
			data = SAVE_ACTION_UPD;
		}
		
		sessionStorage.setItem("action", data);
		
		$("#rackRate").submit();
	}
}

/**
 * delete function
 */
function deleteData() {
	
	var id = $('#hdnId').val();
	url = "../rackRate/delete";
	redirectUrl = "../rackRate/rackRateList";
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	
	/*var isConfirm = confirm("Delete this Rack Rate?");
	
	if (isConfirm == true) {
		
		deleteMaster(id, url, redirectUrl);
	}*/
}

/**
 * refreshData function
 */
function refreshData() {
	
	var id = $('#hdnId').val();
	var encryptedId = $('#hdnEncryption').val();
	var cls = ".refresh_rack";
	var url = "../rackRate/getRecord?ids=";
	var txt = refresh(id, encryptedId, cls, url);
	
	if(txt != '') {

		$("#content").html(txt);
		onPageLoad();
	} else if(id == 0) {
		
		$('#tblTariff input[type=text]').each(function() {
			$(this).val('');
		});
		
		$('#tblRevenue input[type=text]').each(function() {
			$(this).val('');
		});
		
		var revRows = $('#tblRevenue tbody tr');
		if(revRows.length > 1) {
			for(var i = 1; i < revRows.length; i++) {
				$(revRows[i]).remove();
			}
		}
		
		$('#divRevnSharing').removeClass('revn-sharing-div');
	}
}

/**
 * cancel function
 */
function cancel() {
	
	window.location = '../rackRate/rackRateList';
}