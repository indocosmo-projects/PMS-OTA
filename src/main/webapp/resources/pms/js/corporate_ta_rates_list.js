/**
 * onload function
 */
$(document).ready(function() {
	dateFormat = convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	masterFormContent();
	totalCount('../corporateTaRate/getCount');	
	gridMethod();
	simpleSearchEnter();

	$('#btnShowAddModal').click(function() {
		addCorporateRate();
	});

	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id == "myModal") {
			$('#content').html('');
		}
	});
}); 

/**
 * list function
 */
function gridMethod() {
	(window.cp_isCanEdit? "" : corporateRateEditPageLink=null);
	var tableDef = {
			url: "../corporateTaRate/list",
			colModel: [
	           {label: 'Code', name: 'code', align: 'left', width: '', hidden: false, sort: true, formatter: corporateRateEditPageLink},
	           {label: 'Name', name: 'name', align: 'left', width: '', hidden: false, sort: true},
	           {label: 'Corporate', name: 'corporateCode', align: 'left', width: '', hidden: false, sort:false},
	           {label: 'Room Type', name: 'roomType.code', align: 'left', width: '', hidden: false, sort: false},
	           {label: 'Valid From', name: 'validityFrom', align: 'left', width: '', hidden: false, sort: false, formatter: dateFormater},
	           {label: 'Valid To', name: 'validityTo', align: 'left', width: '', hidden: false, sort: false, formatter: dateFormater},
	           {label: 'Meal Plan', name: 'mealPlan', align: 'left', width: '', hidden: false, sort: false},
	           {label: 'Status', name: 'isActive', align: 'center', width: '', hidden: false, sort: false,formatter: corporateRateEditPageLink1},
	           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
	           ],
	        tableWidth: "100%",
	        tableHeight: "",
	        bindingDivId: "pms_dynamic_grid",
	        id: "g_table"
	};
	
	generateGrid(tableDef);
}


function corporateRateEditPageLink(cellvalue, rowObject) {
	
	var txt ="";
	 
	if(rowObject.isSystem){
		txt = 	cellvalue;
		
	}else{
	
		 txt = "<span class='jqGrid_showLinkSapan' onclick='edit(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
	    
	
	}
	
 	
	return txt;
}
function corporateRateEditPageLink1(cellvalue, rowObject){
 	if(cellvalue==1){
 		return "<i style='color:#73C857' class='fa fa-check'></i>"
 	}
 	else{
 		return "<i style='color:#EA4335' class='fa fa-close'></i>"
 	}
}

function dateFormater(cellValue, rowObject) {
	
	if(cellValue != '') {
		var date = new Date(cellValue);
		date = $.datepicker.formatDate(dateFormat, date);
		
		return date;
	} else {
		return '';
	}
}


/**
 * redirects to edit page
 * @param obj
 */
function edit(obj) {

	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);

	$("#myModal").modal({backdrop: "static"});

	$.ajax({
		url: "../corporateTaRate/getRecord?ids=" + encodeURIComponent(obj),
		type : 'GET',
		contentType: "application/json",
		success: function (response) {
			$("#content").html(response);
			onPageLoad(); //defined in corporate_ta_edit.js
		}
	});
}

/**
 * redirects to add page
 */
function addCorporateRate() {

	$("#myModal").modal({backdrop: "static"});
	$.ajax({

		url: "../corporateTaRate/editRedirect",
		type : 'GET',
		contentType: "application/json",
		success: function (response) {
			$("#content").html(response);
			onPageLoad(); //defined in corporate_ta_edit.js
		}
	});
}
