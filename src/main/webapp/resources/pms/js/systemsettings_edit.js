var resvApp = angular.module('systemsettings', ['ngMaterial']);
/*< 2304 digna 20180620 begin */
resvApp.controller('systemsettingsController', ['$scope','$compile','$http','$mdDialog','$window',function ($scope,$compile,$http,$mdDialog,$window){
/* 2304 digna 20180620 end >*/
   /*< 2301 digna 20180620 begin */
	$scope.htlDate=$("#hotelDate").val();
	$scope.dateFormat=$("#dateFormat").val();
	/* 2301 digna 20180620 end >*/
	var txtTax1Name =$("#txtTax1Name").val();
	if(txtTax1Name==""){
		$("#checkTax1").prop('checked',false);
	}
	
	var txtTax2Name =$("#txtTax2Name").val();
	if(txtTax2Name==""){
		$("#checkTax2").prop('checked',false);
	}
	var txtTax3Name =$("#txtTax3Name").val();
	if(txtTax3Name==""){
		$("#checkTax3").prop('checked',false);
	}
	
	var txtTax4Name =$("#txtTax4Name").val();
	if(txtTax4Name==""){
		$("#checkTax4").prop('checked',false);
	}

	
	 $scope.show1 = true;
	    $scope.toggle1 = function() {
	        $scope.show1 = !$scope.show1;
	    }
	
	    $scope.show2 = true;
	    $scope.toggle2 = function() {
	        $scope.show2 = !$scope.show2;
	    }
	    
	    $scope.show3 = true;
	    $scope.toggle3 = function() {
	        $scope.show3 = !$scope.show3;
	    }
	    
	    $scope.show4 = true;
	    $scope.toggle4 = function() {
	        $scope.show4 = !$scope.show4;
	    }
	    
	    $scope.show5 = true;
	    $scope.toggle5 = function() {
	        $scope.show5 = !$scope.show5;
	    }
	    
	    $scope.show6 = true;
	    $scope.toggle6 = function() {
	        $scope.show6 = !$scope.show6;
	    }
	    
	    $scope.show7 = true;
	    $scope.toggle7 = function() {
	        $scope.show7 = !$scope.show7;
	    }
	    
	    $scope.show8 = true;
	    $scope.toggle8 = function() {
	        $scope.show8 = !$scope.show8;
	    }
	    
	    $scope.show9 = true;
	    $scope.toggle9 = function() {
	        $scope.show9 = !$scope.show9;
	    }
	    
	    $scope.show10 = true;
	    $scope.toggle10 = function() {
	        $scope.show10 = !$scope.show10;
	    }
	    
	    $scope.show11 = true;
	    $scope.toggle11 = function() {
	        $scope.show11 = !$scope.show11;
	    }
	    
	    $scope.show12 = true;
	    $scope.toggle12 = function() {
	        $scope.show12 = !$scope.show12;
	    }
	    
	    
	
	 /*  Confirm box */
	$scope.showConfirm = function(){   
		  var confirm = $mdDialog.confirm()
			.title("Do you want to save?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
				 
				$('#systemSettings').submit();				 
			}); 	 
	  }
	
	/*$scope.deleteRatePeriod = function(rowId, rowcode, addDelId, remove)
	{
		var text = rowId;
		var regex = /(\d+)/g;
		var text1=text.match(regex);
		$scope.text2=parseInt(text1)+1;
		$scope.newRaw = rowId.includes("rtPrdNewRow");
		var textrowcode=rowcode;
		var textrowcode1=textrowcode.substring(16, 21);
		$scope.textrowcode2=textrowcode1;
		var confirm = $mdDialog.confirm()
			.title("Do you want to delete this rate period?")
			.textContent("Rate Period will be deleted").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
		< 2304 digna 20180620 begin 
		if($scope.newRaw== false){
		 2304 digna 20180620 end >
			var data = $.param({
				code : $scope.textrowcode2			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../systemSettings/delete', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						if (remove) {
							$('#' + rowId).remove();
							
									} 
						else 
						{
							$('#' + addDelId).html('D');
							$('#' + rowId).hide();
							window.location = "";
						} 	
				
					}else{
						$mdDialog.show($mdDialog.alert()
								.clickOutsideToClose(true).title('Alert').textContent('Cannot delete. There are Active room-types in this group').ok('Ok').parent(angular.element(document.body)));
				}
				}).error(function (data, status, header, config){
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Unreccognised error. Please contact Administrator.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
		< 2304 digna 20180620 begin 
		}else {
			$('#addRate').show();
			if (remove) {
				$('#' + rowId).remove();
				
						} 
			else 
			{
				$('#' + addDelId).html('D');
				$('#' + rowId).hide();
				window.location = "";
			} 	
			
		}
		2304 digna 20180620 end >
	});*/
	

	/*var data = $.param({
		code : $scope.textrowcode2			
		});
	var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
	$http.post('../systemSettings/selectid', data, config)
	*//*
		var text = rowId;
		var regex = /(\d+)/g;
		var text1=text.match(regex);
		$scope.text2=parseInt(text1)+1;
	//	alert($scope.text2);
	
		
		   
		var confirm = $mdDialog.confirm()
		.title("Do you want to delete this Rate Period?")
		.textContent("Rate Period will be deleted!").ok('Yes').cancel('No').parent(angular.element(document.body));
		
		$mdDialog.show(confirm).then(function(){
			
			var data = $.param({
				id : $scope.text2
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../systemSettings/delete', data, config)
			.success(function (data, status, headers, config) {
				if(data.substring(7)=='success'){
					if (remove) {
								$('#' + rowId).remove();
										} 
					else 
					{
						
									$('#' + addDelId).html('D');
									$('#' + rowId).hide();
										} 	
					
				}else{alert("error!!");}
			}).error(function (data, status, header, config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Some Error Occured !!').textContent(data).ok('Ok!').parent(angular.element(document.body)));
			});
		});
		
	}*/
	
	
	
	
	
	$('#txtMailPassword').attr('type', 'password'); 
//	$scope.deleteRowConfirm = function(rowId, addDelId, remove){   
////		  var confirm = $mdDialog.confirm()
////			.title("Do you want to delete row ?")
////			.ariaLabel('PMS')		 
////			.ok('Yes')
////			.cancel('No')
////			.parent(angular.element(document.body));		  
//			$mdDialog.show(confirm).then(function() {
//				if (remove) {
//
//					$('#' + rowId).remove();
//				} else {
//
//					$('#' + addDelId).html('D');
//					$('#' + rowId).hide();
//				} 			 
//				  
//			}, function() {			      	             
//              }); 	 
//	  }
	
	/*$scope.addRatePeriodRow=function(){
		$('#addRate').hide();
	//	$scope.isdisabled = true;
		var lastDate = "", newLastDate = "";
		var addRow = true;
		
		$('#ratePeriodDiv').find('div.row').each(
				
				function() {
					
					var cols = $(this).find('div.tbl-cell');
					var deleted = cols.eq(1).text();
					
					if (deleted != 'D') {
						
						var newRow = (cols.eq(0).text() == 0);
						var code = $.trim(newRow ? cols.eq(2).find('input[type=text]').val() : cols.eq(2).text());
						var fromDate = $.trim(newRow ? cols.eq(3).find('input[type=text]').val() : cols.eq(3).text());
						var toDate = $.trim(newRow ? cols.eq(4).find('input[type=text]').val() : cols.eq(4).text());
						
						if(code.trim() == '' || fromDate == '' || toDate == '') {
							
							if(code == '') cols.eq(2).find('input[type=text]').focus();
							else if(fromDate == '') cols.eq(3).find('input[type=text]').focus();
							else cols.eq(4).find('input[type=text]').focus();
							
							addRow = false;
							
							return false;
						}
						
					var lastDate = toDate;
					var	newLastDate =toDate;
					$scope.lastDate = toDate;
					$scope.newLastDate =toDate;
					}
				}
		);
		if(addRow) {
			
			if (lastDate == "") {
				
				var lastDate1=$scope.lastDate;
				var lastDate;
				< 2301 digna 20180620 begin 
				if(null == lastDate1){
					//lastDate=$scope.htlDate;
					lastDate = new Date($scope.htlDate.replace(/-/g,"/"));
				}else{
				lastDate=new Date(lastDate1.split("-").reverse().join("-"));
				}
				 2301 digna 20180620 end> 
				var newLastDate=new Date(lastDate);
				
//				lastDate = new Date();
//				newLastDate = new Date(lastDate);
			}
			
			lastDate.setDate(lastDate.getDate() + 1);
		//	alert(lastDate);
			newLastDate.setMonth(newLastDate.getMonth() + 12);
	
			var code = (lastDate.getFullYear() + '').substring(2, 4) + "-"
				+ (newLastDate.getFullYear() + '').substring(2, 4);
	
			rowNum += 1;
	        < 2304 digna 20180620 begin 
			var row = $("<div id=\"rtPrdNewRow" + rowNum + "\" class=\"row pms-tbl-row pms_row_bottom_align\">"
			+ "<div class=\"col-sm-1 tbl-cell\" style=\"display: none;\">0</div>"
			+ "<div class=\"col-sm-1 tbl-cell\" style=\"display: none;\" id=\"rtPrdNewRowAddDel" + rowNum + "\">A</div>"
			+ "<div class=\"col-sm-3 tbl-cell\"><input type=\"text\" maxlength=\"5\" class=\"form-control form-control-inline input-medium\" value=\"" + code + "\" disabled/></div>"
			+ "<div class=\"col-sm-3 tbl-cell\">" + getDatePicker(lastDate) + "</div><div class=\"col-sm-3 tbl-cell\">"
			+ getDatePicker(newLastDate) + "</div>"
			+ "<div class=\"col-sm-1 tbl-cell\"><button type=\"button\" class=\"btn btn-danger btn-xs\" "
			+ "ng-click=\"deleteRatePeriod('rtPrdNewRow" + rowNum
			+"','rtPrdNewRowscode', 'rtPrdNewRowAddDel" + rowNum + "', true)\">"
			+ "<i class=\"fa fa-trash-o \"></i></button></div></div>").appendTo('#ratePeriodDiv');
			$compile(row)($scope);
//			$('#datepick').datepicker({
//				dateFormat: dateFormat
//			});
            2304 digna 20180620 end >
		}
	};*/
	
	
	$scope.addFinancialYearRow=function(){
		$('#passwordModal').modal('toggle');
	}
	
	$scope.cancelPopUpOpen =function(){
		$('#passwordModal').modal('toggle');
	}

	$scope.addRaw=function(){
		$('#passwordModal').modal('toggle');
		var data = $.param({
			passWord:$scope.passwrd
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../systemSettings/checkUser', data, config)
		.success(function (data, status, headers, config) {
			
				if(data=="success"){

					$('#addFinYear').hide();
					var lastDate = "", newLastDate = "";
					$('#finYearDiv').find('div.row').each(
							function() {
								var cols = $(this).find('div.tbl-cell');
								$scope.lastDate = $.trim(cols.eq(3).text());
							}
					);
				
					var lastDate1=$scope.lastDate;
					var lastDate;
					if(null == lastDate1){
						lastDate = new Date($scope.htlDate.replace(/-/g,"/"));
						if(lastDate.getMonth() <= 2){
							lastDate.setYear(lastDate.getFullYear()-1);
						}
						lastDate.setMonth(2);
						lastDate.setDate(31);
						
					}else{
						lastDate=new Date(lastDate1.split("-").reverse().join("-"));
					}
			
					var newLastDate=new Date(lastDate);
					lastDate.setDate(lastDate.getDate() + 1);
					newLastDate.setMonth(newLastDate.getMonth() + 12);
					var code = (lastDate.getFullYear() + '') + "-"+ (newLastDate.getFullYear() + '').substring(2, 4);
					rowNum += 1;
					
					var row = $("<div id=\"finYearNewRow" + rowNum + "\" class=\"row pms-tbl-row pms_row_bottom_align\">"
					+ "<div class=\"col-sm-1 tbl-cell\" style=\"display: none;\">0</div>"
					+ "<div class=\"col-sm-3 tbl-cell\"><input type=\"text\" maxlength=\"5\" class=\"form-control form-control-inline input-medium\" value=\"" + code + "\" disabled/></div>"
					+ "<div class=\"col-sm-2 tbl-cell\">" + getDatePicker(lastDate) + "</div>"
					+"<div class=\"col-sm-2 tbl-cell\">" + getDatePicker(newLastDate) + "</div>"
					/*+ "<div class=\"col-sm-2 tbl-cell\"><select class=\"form-control\"><option value=\"true\">true</option><option value=\"false\">false</option></select></div>"
					+ "<div class=\"col-sm-2 tbl-cell\"><select class=\"form-control\"><option value=\"true\">true</option><option value=\"false\">false</option></select></div>"*/
					+ "<div class=\"col-sm-2 tbl-cell\"><input type=\"checkbox\" id=\"finYearN1\" /><label for=\"finYearN1\"></label></div>"
					+ "<div class=\"col-sm-2 tbl-cell\"><input type=\"checkbox\" id=\"finYearN2\" /><label for=\"finYearN2\"></label></div>"
					).appendTo('#finYearDiv');
					$compile(row)($scope);
				
					$scope.passwrd=""
				}else{
					$scope.passwrd= ""
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Incorrect Password').ok('Ok').parent(angular.element(document.body)));
				}

			
		})
		.error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});		
	};
	
	
	  
	  /*  alert box */	
		 $scope.showAlert = function(msg){    
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Message').textContent(msg).ok('Ok').parent(angular.element(document.body)));
			};
			
			
            $scope.showConfirmm = function(event) {
                var confirm = $mdDialog.confirm()
                   .title('Alert')
                   .textContent('Do you want to save?')
                   .ariaLabel('PMS')
                   .targetEvent(event)
                   .ok('Yes')
                   .cancel('No');
                   $mdDialog.show(confirm).then(function() {
                      return true;
                      }, function() {
                    	  return false;
                         
                   });
             };	
	  
	  
}]);




function removeDiscountRow(rowId) {
	angular.element(document.body).scope().removeDiscount(rowId);
}


/**
 * this method will bind the currency details to dropdown from the json
 */
function bindCurrencyToDropDown() {
	dateFormat = convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	
	var selected = $('#hdnBaseCurrencyId').val();
	var cur;

	for ( var i in currency.currencies) {
	
		cur = currency.currencies[i];
		$('#cmbCurrency').append("<option value=\"" + cur.id + "\">" + cur.code + "</option>");
	}

	$('#cmbCurrency').val(selected);
}






/**
 * change the description and decimal places according to the change in currency dropdown
 */
function currencyChanged() {
	
	var selectedIndex = $('#cmbCurrency :selected').index();

	if(selectedIndex == -1) {
	
		$('select#cmbCurrency').prop('selectedIndex', 0);
		selectedIndex = 0;
	}
	
	var desc = currency.currencies[selectedIndex].description  
		+ " (" + currency.currencies[selectedIndex].symbol + ")";
	var decimal = currency.currencies[selectedIndex].decimalPlaces;
	
		
    $('#divCurrencyDescription').html(desc);
	$('#txtDecimalPlaces').val(decimal);
	
	
}

function setweeklySpecialDays() {
	
	var weeklySpecialDays = "";
	
	$('.weekSDays').each(function() {
		
		if ($(this).is(':checked')) {
			
			if (weeklySpecialDays == '') {
				
				weeklySpecialDays = $(this).attr('data-day');
			} else {
			 
				weeklySpecialDays += "," + $(this).attr('data-day');
			}
		}
	});
	
	$('#hdnWeeklySpecialDays').val(weeklySpecialDays);
}

function enableOrDisableTaxText(checkBox, textBox, taxName) {
	
	if (checkBox.checked) {
		
		$('#' + textBox).attr('disabled', false);
		$('#' + textBox).val(taxName);
	} else {
		
		$('#' + textBox).attr('disabled', true);
		$('#' + textBox).val('');
	}
}

//Return the date picker having the format 'DD-MMM-YYYY'
function getDatePicker(date) {
	
	function formatDate(date) {
	    var d = new Date(date),
	        month = '' + (d.getMonth() + 1),
	        day = '' + d.getDate(),
	        year = d.getFullYear();

	    if (month.length < 2) month = '0' + month;
	    if (day.length < 2) day = '0' + day;

	    return [year,month,day].join('-');
	}
//	document.getElementById('res').innerHTML =  formatDate('Sun May 11,2014') ;
	
//	$.datepicker.formatDate('yy-mm-dd', date)
	
	
	var datePicker = "<input id=\"datepick\" type=\"text\" class=\"form-control form-control-inline input-medium date_picker\" value=\""
		+ formatDate(date) + "\" disabled>";
	return datePicker;
}

var rowNum = 0;

/**
 * Add a new row to the RATE PERIODS table
 */
//function addRatePeriodRow() {
//	
//	var lastDate = "", newLastDate = "";
//	var addRow = true;
//	
//	$('#ratePeriodDiv').find('div.row').each(
//			
//			function() {
//				
//				var cols = $(this).find('div.tbl-cell');
//				var deleted = cols.eq(1).text();
//				
//				if (deleted != 'D') {
//					
//					var newRow = (cols.eq(0).text() == 0);
//					var code = $.trim(newRow ? cols.eq(2).find('input[type=text]').val() : cols.eq(2).text());
//					var fromDate = $.trim(newRow ? cols.eq(3).find('input[type=text]').val() : cols.eq(3).text());
//					var toDate = $.trim(newRow ? cols.eq(4).find('input[type=text]').val() : cols.eq(4).text());
//					
//					if(code.trim() == '' || fromDate == '' || toDate == '') {
//						
//						if(code == '') cols.eq(2).find('input[type=text]').focus();
//						else if(fromDate == '') cols.eq(3).find('input[type=text]').focus();
//						else cols.eq(4).find('input[type=text]').focus();
//						
//						addRow = false;
//						
//						return false;
//					}
//					
//				var lastDate = toDate;
//				var	newLastDate =toDate;
//				}
//			}
//	);
//
//	if(addRow) {
//		
//		if (lastDate == "") {
//		
//			lastDate = new Date();
//			newLastDate = new Date(lastDate);
//		}
//		
//		lastDate.setDate(lastDate.getDate() + 1);
//		newLastDate.setMonth(newLastDate.getMonth() + 12);
//
//		var code = (lastDate.getFullYear() + '').substring(2, 4) + "-"
//			+ (newLastDate.getFullYear() + '').substring(2, 4);
//
//		rowNum += 1;
//
//		var row = "<div id=\"rtPrdNewRow" + rowNum + "\" class=\"row pms-tbl-row pms_row_bottom_align\">"
//			+ "<div class=\"col-sm-1 tbl-cell\" style=\"display: none;\">0</div>"
//			+ "<div class=\"col-sm-1 tbl-cell\" style=\"display: none;\" id=\"rtPrdNewRowAddDel" + rowNum + "\">A</div>"
//			+ "<div class=\"col-sm-3 tbl-cell\"><input type=\"text\" maxlength=\"5\" class=\"form-control form-control-inline input-medium\" value=\"" + code + "\" /></div>"
//			+ "<div class=\"col-sm-3 tbl-cell\">" + getDatePicker(lastDate) + "</div><div class=\"col-sm-3 tbl-cell\">"
//			+ getDatePicker(newLastDate) + "</div>"
//			+ "<div class=\"col-sm-1 tbl-cell\"><button type=\"button\" class=\"btn btn-danger btn-xs\" "
//			+ "onclick=\"deleteRatePeriod('rtPrdNewRow" + rowNum
//			+ "', 'rtPrdNewRowAddDel" + rowNum + "', true);\">"
//			+ "<i class=\"fa fa-trash-o \"></i></button></div></div>";
//
//		$('#ratePeriodDiv').append(row);
//		
//		$('#datepick').datepicker({
//			dateFormat: dateFormat
//		});
//	}
//}

/**
 * Delete a row from RATE PERIODS table 
 */
//function deleteRatePeriod(rowId, addDelId, remove) {
//	
//	
//	angular.element(document.body).scope().deleteRowConfirm(rowId, addDelId, remove);
//	
//	/*if(confirm("Delete this row?") == 1) {
//		
//		if (remove) {
//
//			$('#' + rowId).remove();
//		} else {
//
//			$('#' + addDelId).html('D');
//			$('#' + rowId).hide();
//		}
//	}*/
//}

/**
 * fetch data from RATE PERIODS table to save and delete.
 */
/*function getRatePeriodData() {
	
	var tbody = $('#ratePeriodDiv');

	var ratePeriods = [];
	tbody.find('div.row').each(
			
			function() {
				
				var cols = $(this).find('div.tbl-cell');
				var id = cols.eq(0).text();
				var rowStatus = cols.eq(1).text(); //Deleted(D), New Row(A) or No Change(NC)
				var newRow = (id == 0);

				if ((id != 0 && rowStatus != 'NC')
						|| (newRow && rowStatus != 'D')) {
					
					ratePeriods.push({
						
						"id" : id,
						"code" : newRow ? cols.eq(2).find('input[type=text]').val() : cols.eq(2).text(),
						"fromDate" :$.trim(newRow ? cols.eq(3).find('input[type=text]').val() : cols.eq(3).text()),
						"toDate" :$.trim(newRow ? cols.eq(4).find('input[type=text]').val() : cols.eq(4).text()),
						"system" : false,
						"deleted" : rowStatus == 'D' ? true : false,
						"lastUpdTs" : new Date()
					});
				}
			});

	var jsonString = JSON.stringify(ratePeriods);
	$('#ratePeriodAsJson').val(jsonString);
}*/



function getFinancialYearData() {
	
	var tbody = $('#finYearDiv');

	var ratePeriod
	tbody.find('div.row').each(
			
			function() {
				
				var cols = $(this).find('div.tbl-cell');
				var id = cols.eq(0).text();
				var newRow = (id == 0);
				if(newRow){
					ratePeriod = {
						
						"id" : id,
						"finCode" : $.trim(cols.eq(1).find('input[type=text]').val()),
						"fromDate" :$.trim(cols.eq(2).find('input[type=text]').val()),
						"toDate" :$.trim(cols.eq(3).find('input[type=text]').val()),
						"useCode" :$.trim(cols.eq(4).find('input[type=checkbox]')[0].checked),
						"usePrefix" :$.trim(cols.eq(5).find('input[type=checkbox]')[0].checked)
					};
				}
				
			});

	var jsonString = JSON.stringify(ratePeriod);
	$('#finYearAsJson').val(jsonString);
}

var company=[];
function getCompany(){
	var txtCompanyName=$("#txtCompanyName").val();
	var txtBuildingName=$("#txtBuildingName").val();
	var txtstreet=$("#txtstreet").val();
	var txtcity=$("#txtcity").val();
	var txtState=$("#txtState").val();
	var txtcountry=$("#txtcountry").val();
	var txtgstNo=$("#txtgstNo").val();
	var txtemail=$("#txtemail").val();
	var txturl=$("#txturl").val();
	var txtphone=$("#txtphone").val();
	var txtlogoFolder=$("#txtlogoFolder").val();
	var txtlongStay=$("#txtlongStay").val();
	if(txtlongStay == ""){
		txtlongStay = 0;
	}
	var txtresvConfirmHead=$("#txtresvConfirmHead").val();
	var txtresvConfirmText1=$("#txtresvConfirmText1").val();
	var txtresvConfirmText2=$("#txtresvConfirmText2").val();
	var txtresvConfirmText3=$("#txtresvConfirmText3").val();
	var txtresvConfirmText4=$("#txtresvConfirmText4").val();
	var txtresvConfirmText5=$("#txtresvConfirmText5").val();
	
	company.push({id:1,companyName:txtCompanyName,buildingName:txtBuildingName,streetName:txtstreet,cityName:txtcity,stateName:txtState,countryName:txtcountry,gstNo:txtgstNo,email:txtemail,url:txturl,phone:txtphone,logoFolder:txtlogoFolder
		,longStay:txtlongStay,resvConfirmHead:txtresvConfirmHead,resvConfirmText1:txtresvConfirmText1,resvConfirmText2:txtresvConfirmText2,resvConfirmText3:txtresvConfirmText3,resvConfirmText4:txtresvConfirmText4,resvConfirmText5:txtresvConfirmText5});
	
	var jsonCompany=JSON.stringify(company);
	$('#companyAsJson').val(jsonCompany);

}

/**
 * validate rate period table
 * @returns {String}
 */
/*function validateRatePeriods() {
	
	var tbody = $('#ratePeriodDiv');
	var prevToDate = "", rowCount = 0;
	var valid = 'VALID';
	var codes = [];
	
	tbody.find('div.row').each(
			
			function() {
				
				var cols = $(this).find('div.tbl-cell');
				var newRow = (cols.eq(0).text() == 0);
				var rowStatus = cols.eq(1).text();
				var fromDate = "", toDate = "";
				
				if(rowStatus != 'D') {
					
					rowCount++;
					var code = $.trim(newRow ? cols.eq(2).find('input[type=text]').val() : cols.eq(2).text());
					fromDate = $.trim(newRow ? cols.eq(3).find('input[type=text]').val() : cols.eq(3).text());
					toDate = $.trim(newRow ? cols.eq(4).find('input[type=text]').val() : cols.eq(4).text());
					
//					if(newRow) {
//						
//						if(code == '' || fromDate == '' || toDate == '') {
//							
//							alert('Enter values in Rate Period[Row ' + rowCount + '].');
//							valid = 'NOT_VALID';
//							
//							return valid;
//						} else {
//							
//							var fmDate = fromDate;
//							var tDate = toDate;
//							var ind = codes.indexOf(code);
//							
//							if(ind != -1) {
//								
//								alert('Duplicate Codes in Rate Period[Row ' + rowCount + '].');
//								valid = 'NOT_VALID';
//								return false;
//							} else if(tDate <= fmDate) {
//								
//								alert('Enter valid date range in Rate Period[Row ' + rowCount + ']');
//								valid = 'NOT_VALID';
//								
//								return false;
//							} else if(prevToDate != '' && fmDate <= $.datepicker.parseDate(dateFormat, prevToDate)) {
//								
//								alert('Invalid date range in Rate Period[Row ' + rowCount + ']');
//								valid = 'NOT_VALID';
//								
//								return false;
//							}
//						}
//					}
					
					codes.push(code);
					
					if(rowStatus != 'D') {
						
						prevToDate = toDate;
					}
				}
			});
		
	return valid;
}
*/
/**
 * form validation method
 * @returns {String}
 */
function validateForm() {
	
	

	valid = validation('.validator');
	
	if(valid == 'true')
		var valid = 'VALID';
	
	if(valid == 'VALID')
		valid = checkGroupForNull('mail-form');
	
	if(valid == 'VALID')
		valid = checkEmail('txtMailId');
	
	if(valid == 'VALID')
		valid = checkMaxRoom('txtMaxRmPerBkng');
	
	if(valid == 'VALID')
		valid = checkMaxRoom('txtMaxNghtsPerBkng');
	
	if(valid == 'VALID')
		valid = checkGroupForNull('sms-form');
	
	if(valid == 'VALID'){
		 
		setweeklySpecialDays();
		//getRatePeriodData();
		getFinancialYearData();
		getCompany();
		return "true";
	}
	 
	
	/*if(valid == 'VALID' && confirm('Do you want to save this settings?')){
 
		setweeklySpecialDays();
		getRatePeriodData();
		
		return "true";
	}*/
	
	return "false";
}


/**
 * For checking null string in input
 * @param id id of the textbox or textarea
 * @returns {Boolean} true if not null
 */
function checkNull(id) {
	
	var com = $('#' + id);
	
	if($.trim(com.val()) == '') {
		
		$('#' + id + '_warning').show();
		com.val('');
		
		return false;
	} else {
		$('#' + id + '_warning').hide();
	}
	
	return true;
}


/**
 * For checking null value for a group of inputs
 * @param cls class of the inputs
 * @returns {String}
 */
function checkForNullTexts(cls) {
	
	var valid = "VALID";
	
	$('.' + cls).each(function() {
		
		var id = $(this).attr('id');
		
		if(!checkNull(id)) {
			
			valid = "NOT_VALID";
			
			return valid;
		}
	});
	
	return valid;
}


/**
 * This will check whether all of the fields in the specified class is filled.
 * @param cls
 * @returns {Boolean} true if all of the field is filled
 */
function isAllFilled(cls) {
	
	var filled = "FILLED";
	
	$('.' + cls).each(function() {
		
		var id = $(this).attr('id');
		
		if ($('#' + id).val() != '') {
			
			filled = "NOT_FILLED";
			
			return filled;
		}
	});
	
	return filled;
}


/**
 * For checking null value for a group only if any of the
 * input field is filled in the group.
 * @param cls class of the inputs
 * @returns {String}
 */
function checkGroupForNull(cls) {
	
	if(isAllFilled(cls) == 'NOT_FILLED')
		return checkForNullTexts(cls);
	else {
		
		$('.msg-' + cls).hide();
	}
	
	return "VALID";
}


function checkEmail(id) {
	
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	var val = $.trim($('#' + id).val());
	
	if (val != '' && !filter.test(val)) {

		$('#' + id + '_warning').show();

		return "NOT_VALID";
	} else {

		$('#' + id + '_warning').hide();
	}
	
	$('#' + id).val(val);
	
	return "VALID";
}

/**
 * onload method
 */
$(document).ready(function() {
	
	bindCurrencyToDropDown();
	currencyChanged();
	onloadValidator('.validator');
	numericalValidation(".pms_text_numbers");
	OnloadValidationEdit('.validator');
	
	$('.mail-form').bind("keyup change", function() {
		
		checkGroupForNull('mail-form');
	});
	
	$('.sms-form').bind("keyup change", function() {
		
		checkGroupForNull('sms-form');
	});
	
	$('#cmbCurrency').change(function() {
		
		currencyChanged();
	});
	
	$('#txtMailId').bind("keyup change", function() {
		
		checkEmail('txtMailId');
	});
	
	$('#txtMaxRmPerBkng').bind("keyup change focusout", function() {
		
		checkMaxRoom('txtMaxRmPerBkng');
	});
	
	$('#txtMaxNghtsPerBkng').bind("keyup change focusout", function() {
		
		checkMaxRoom('txtMaxNghtsPerBkng');
	});
});

/**
 * redirect to system settings page
 */
function refreshData() {
	
	window.location = "../systemSettings/settings";
}

/**
 * validate and submit the form if validation is ok
 * @returns {Boolean}
 */
function saveData() {
	var tax1 = $("#checkTax1").prop('checked') && $("#txtTax1Name").val()=="";
	
	var tax2 = $("#checkTax2").prop('checked') && $("#txtTax2Name").val()=="";
	
	var tax3 = $("#checkTax3").prop('checked') && $("#txtTax3Name").val()=="";
	
	var tax4 = $("#checkTax4").prop('checked') && $("#txtTax4Name").val()=="";
	
	if(tax1 || tax2 || tax3 || tax4){
		angular.element(document.body).scope().showAlert("Please enter tax name");
		return false;
	}
	if($('#txtCheckOutTime').val()== "" || $('#txtCheckInTime').val()== "")
	{
		angular.element(document.body).scope().showAlert("Please enter value for mandatory fields");
		return false;
	}
	else if (validateForm() == "false") {
		return false;
	} else {
		
		angular.element(document.body).scope().showConfirm();
		return false;
	//	$('#systemSettings').submit();
	}
}

/**
 * redirect to system settings page
 */
function cancel() {
	var cfm=confirm("Discard Changes?");
	if(cfm==true){
		window.location.href='../systemSettings/settings'
	}
	
}

//max room/ night booking validation
function checkMaxRoom(id){
	if ($('#' + id).val() >127 || $('#' + id).val()=="")  {
		$('#' + id + '_check').hide();
		$('#' + id + '_warning').show();
		$('#' + id).focus();
		return "NOT_VALID";
	} else {
		$('#' + id + '_warning').hide();
		$('#' + id + '_check').show();
		return "VALID";
	}
	
	
}

