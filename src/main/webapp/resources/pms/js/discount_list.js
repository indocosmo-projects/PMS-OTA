$(document).ready(function() {
	dateFormat = convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	masterFormContent();	
	gridMethod();
	simpleSearchEnter()
	
	$('#btnShowAddModal').click(function() {
		addDiscount();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {		
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});		
});

function gridMethod() {	
    (window.cp_isCanEdit? "" :discountEditPageLink=null);
	var tableDef = {
			url: "../discount/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '', hidden: false, sort: true, formatter: discountEditPageLink},
			           {label: 'Name', name: 'name', align: 'left', width: '', hidden: false, sort: true},
			           {label: 'Valid from', name: 'validFrom', align: '', width: '', hidden: false, sort: false, formatter: dateFormater},
			           {label: 'Valid to', name: 'validTo', align: '', width: '', hidden: false, sort: false, formatter: dateFormater},
			           {label: 'Calculation mode', name: 'calcMode', align: '10%', width: '', hidden: false, sort: false},
			           {label: 'Discount amount', name: 'calAmount', align: 'right', width: '', hidden: false, sort: false},
			           {label: 'Description', name: 'description', align: 'left', width: '', hidden: false, sort: false},
			           {label: 'Discount For', name: 'discountForName', align: 'left', width: '', hidden: false, sort: false},
			           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
			           ],
			           tableWidth: "100%",
			           tableHeight: "",
			           bindingDivId: "pms_dynamic_grid",
			           id: "g_table"
	};

	generateGrid(tableDef);
}

function discountEdit(obj) {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);	
	$.ajax({
		url:"../discount/getRecord?ids=" + encodeURIComponent(obj),
		success: function (response) {
			$("#content").html(response);
			onLoadSetup(); /* load functions from discount_edit.js page*/
			$("#myModal").modal({backdrop:"static"});
		}
	});	
	
}

function addDiscount() {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$.ajax({
		url: '../discount/editRedirect',
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onLoadSetup(); /* load functions from discount_edit.js page*/   
			$("#myModal").modal({backdrop:"static"});
		}
	});	
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

function discountEditPageLink(cellvalue/*, options*/, rowObject){
	
	if(rowObject.system){
		txt = 	cellvalue;
		
	}else{
		 
		txt="<span class='jqGrid_showLinkSapan' onclick='discountEdit(\""+rowObject.encryption+"\")'>"+cellvalue+"</span>";

		
	}

	
	
	return txt;
}

