$(document).ready(function() {
	masterFormContent();
	simpleSearchEnter()
	totalCount('../department/getCount');	
	gridMethod();
	
	$('#btnShowAddModal').click(function() {
		addCurrency();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});	
});

function gridMethod() {

	(window.cp_isCanEdit? "" : currencyEditPageLink=null);
	var tableDef = {
			url: "../currency/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '15%', hidden: false, sort: true, formatter: currencyEditPageLink},
			           {label: 'Name', name: 'name', align: 'left', width: '25%', hidden: false, sort: true},
			           {label: 'Symbol', name: 'symbol', align:'center', width: '10%',hidden: false, sort : false},
			           {label: 'Decimal', name: 'decimalPlaces', align: 'center', width: '10%',hidden: false, sort : false},
			           {label: 'Exchange rate', name: 'exchangeRate', align: 'center', width: '10%',hidden: false, sort : false},
			           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
			           {label: 'System', name: 'system', align: 'left', width: '', hidden: true, sort: false},
			           {label: 'Description', name: 'description', align: 'left', width: '', hidden: false, sort: false},
			           ],
			           tableWidth: "100%",
			           tableHeight: "",
			           bindingDivId: "pms_dynamic_grid",
			           id: "g_table"
	};

	generateGrid(tableDef);
}

function edit(obj) {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url:"../currency/getRecord?ids=" + encodeURIComponent(obj),
		success: function (response) {

			$("#content").html(response);

			onload(); 

		}
	})
}

function addCurrency() {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url: '../currency/editRedirect',
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onload(); 
		}
	});
}

function currencyEditPageLink(cellvalue, rowObject){
	
	var txt ="";
	 
	if(rowObject.is_System){
		txt = 	cellvalue;
		
	}else{
		txt="<span class='jqGrid_showLinkSapan' onclick='edit(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
	}
	
	  return txt;
}
