$(document).ready(function() {

	dateFormat = convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	masterFormContent();
	totalCount('../rackRate/getCount');	

	gridMethod();

	var roomTypeId = sessionStorage.getItem('roomTypeId');

	if(roomTypeId != null && roomTypeId != "") {

		$('#room_type_id').val(roomTypeId);
		contentSearch();
	} else {

		simpleSearchEnter();
	}

	sessionStorage.removeItem('roomTypeId');
	clearSearchData();

	$('#btnShowAddModal').click(function() {
		addRackRate();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
		
			$('#content').html('');
		}
	});

});

function gridMethod() {
	(window.cp_isCanEdit? "" : rackRateEditPageLink=null);
	var tableDef = {
			url: "../rackRate/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '20%', hidden: false, sort: true, formatter: rackRateEditPageLink},
			           {label: 'From', name: 'validityFrom', align: 'left', width: '', hidden: false, sort: false, formatter: dateFormater},
			           {label: 'To', name: 'validityTo', align: 'left', width: '', hidden: false, sort: false, formatter: dateFormater},
			           {label: 'Room Type', name: 'roomType.code', align: 'left', width: '', hidden: false, sort: false},
			           {label: 'Meal Plan', name: 'mealPlan', align: 'left', width: '', hidden: false, sort: false},
			           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
			           ],
			           tableWidth: "100%",
			           tableHeight: "",
			           bindingDivId: "pms_dynamic_grid",
			           id: "g_table"
	};

	generateGrid(tableDef);
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

function rackRateEditPageLink(cellvalue, rowObject) {
	
	var txt ="";
	 
	if(rowObject.isSystem){
		txt = 	cellvalue;
		
	}else{
		 txt = "<span class='jqGrid_showLinkSapan' onclick='editRackRate(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
	}
	
	return txt;
}


function editRackRate(obj) {

	$("#myModal").modal({backdrop: "static"});

	$.ajax({
		url:"../rackRate/getRecord?ids=" + encodeURIComponent(obj),
		success: function (response) {
			$("#content").html(response);
			onPageLoad(); /* load functions from rackrate_edit.js page*/
		}
	});	
}

function addRackRate() {
	$("#myModal").modal({backdrop: "static"});
	
	$.ajax({
		url: "../rackRate/editRedirect",
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onPageLoad(); /* load functions from rackrate_edit.js page*/
		}
	});	
}
