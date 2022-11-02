$(document).ready(function() {
	dateFormat = convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	masterFormContent();
	totalCount('../roomRate/getCount');	
	gridMethod();
	simpleSearchEnter();
	
	$('#btnShowAddModal').click(function() {
		addRoomRate();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});
});

function gridMethod() {
    (window.cp_isCanEdit? "" : roomRateEditPageLink=null);
	var tableDef = {
			url: "../roomRate/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '25%', hidden: false, sort: true, formatter: roomRateEditPageLink},
			           {label: 'Name', name: 'name', align: 'left', width: '30%', hidden: false, sort: true},
			           {label: 'Room Type', name: 'roomType.code', align: 'left', width: '', hidden: false, sort: false},
			           {label: 'Valid From', name: 'validityFrom', align: 'left', width: '', hidden: false, sort: false , formatter: dateFormater},
			           {label: 'Valid To', name: 'validityTo', align: 'left', width: '', hidden: false, sort: false , formatter: dateFormater},
			           {label: 'Meal Plan', name: 'mealPlan', align: 'left', width: '', hidden: false, sort: false},
			           {label: 'Status', name: 'isActive', align: 'center', width: '', hidden: false, sort: false,formatter: roomRateEditPageLink1},
			           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
			           ],
			           tableWidth: "100%",
			           tableHeight: "",
			           bindingDivId: "pms_dynamic_grid",
			           id: "g_table"
	};

	generateGrid(tableDef);
}

function roomRateEdit(obj) {

	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url:"../roomRate/getRecord?ids=" + encodeURIComponent(obj),
		success: function (response) {
			$("#content").html(response);
			onLoadSetup();           
		}
	});
}

function addRoomRate() {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url: '../roomRate/editRedirect',
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onLoadSetup(); 
		}
	});
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
}


function roomRateEditPageLink(cellvalue, rowObject){
	
	var txt ="";
	 
	if(rowObject.isSystem){
		txt = 	cellvalue;
		
	}else{
		txt="<span class='jqGrid_showLinkSapan' onclick='roomRateEdit(\""+rowObject.encryption+"\")'>"+cellvalue+"</span>";
		}
	return txt;
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

function roomRateEditPageLink1(cellvalue, rowObject){
 	if(cellvalue==true){
 		return "<i style='color:#73C857' class='fa fa-check'></i>"
 	}
 	else{
 		return "<i style='color:#EA4335' class='fa fa-close'></i>"
 	}
}
