$(document).ready(function() {
	masterFormContent();
	totalCount('../department/getCount');	
	simpleSearchEnter();
	gridMethod();
	
	$('#btnShowAddModal').click(function() {
		addDepartment();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});
});

function gridMethod() {

	  
	
	// {label: 'Code', name: 'code', align: 'left', width: '15%', hidden: false, sort: true, formatter: departmentEditPageLink},
	
	(window.cp_isCanEdit? "" : departmentEditPageLink=null);
	
	var tableDef = {
			url: "../department/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '15%', hidden: false, sort: true ,formatter: departmentEditPageLink},
			           {label: 'Name', name: 'name', align: 'left', width: '25%', hidden: false, sort: true},
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

function edit(obj) {
	 
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url:"../department/getRecord?ids=" + encodeURIComponent(obj),
		success: function (response) {

			$("#content").html(response);   
			onload();          
		}
	});	
}

function addDepartment() {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url: '../department/departmentRedirect',
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onload(); 
		}
	});	
}

function departmentEditPageLink(cellvalue, rowObject){
	
	var txt ="";
	 
	if(rowObject.is_System){
		txt = 	cellvalue;
		
	}else{
		var  txt="<span class='jqGrid_showLinkSapan' onclick='edit(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
	}
	
	 
	return txt;
}
