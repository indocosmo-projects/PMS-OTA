$(document).ready(function() {
	masterFormContent();
	totalCount('../TaxHdr/getCount');	
	gridMethod();
	simpleSearchEnter();

	$('#btnShowAddModal').click(function() {
		addTax();
	});

	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});

});

function gridMethod() {
	(window.cp_isCanEdit? "" : taxEditPageLink=null);
	var tableDef = {
			url: "../TaxHdr/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '15%', hidden: false, sort: true, formatter: taxEditPageLink},
			           {label: 'Name', name: 'name', align: 'left', width: '20%', hidden: false, sort: true},
			           {label: 'Indicator', name: 'indicator', align: 'center', width: '10%',hidden: false, sort: false},
			           {label: 'Description', name: 'description', align: 'left', width: '', hidden: false, sort: false},
			           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
			           {label: 'System', name: 'system', align: 'left', width: '', hidden: true, sort: false},
			           ],
			           tableWidth: "100%",
			           tableHeight: "",
			           bindingDivId: "pms_dynamic_grid",
			           id: "g_table"
	};

	generateGrid(tableDef);
}

function jqGridMethod() {
	var jsonData = {
			"model" : [ {
				"name" : "code",
				"index" : "code",
				"width" : "120",
				"sortable" : false,
				"editable" : false,
				formatter : "showlink",
				formatter : taxEditPageLink
			}, {
				name : "name",
				index : "name",
				sortable : false,
				width : "120"
			}, {
				name : "description",
				index : "description",
				sortable : false,
				width : "120"
			}, {
				name : "encryption",
				index : "encryption",
				sortable : false,
				width : "120",
				hidden : true,
			} ],
			sortColumn: {
				name: "code"
			},
			"sort" : {
				"sortcount" : "2",
				"sortColumn1" : "#jqgh_jqGrid_list_grid_code",
				"sortColumn2" : "#jqgh_jqGrid_list_grid_name"
			},
			"column" : [ "Code", "Name", "Description", "Encryption" ],
			"url" : {
				"serverurl" : "../TaxHdr/list"
			},
	};
	jqGridData(jsonData);
}

function taxEdit(obj) {
	$("#myModal").modal({backdrop: "static"});

	$.ajax({
		url:  "../TaxHdr/getRecord?ids=" + encodeURIComponent(obj),
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onLoadTax();
		}
	});
}

function addTax() {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url: '../TaxHdr/editRedirect',
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onLoadTax(); 

		}
	});	
}

function taxEditPageLink(cellvalue, rowObject){
	var txt ="";
	
	 
	if(rowObject.system){
		txt = 	cellvalue;
		
	}else{
		var txt="<span class='jqGrid_showLinkSapan' onclick='taxEdit(\"" + rowObject.encryption + "\")'>"+cellvalue+"</span>";
	}
	
	return txt;
}