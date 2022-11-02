$(document).ready(function() {
	masterFormContent();
	totalCount('../floor/getCount');	
	simpleSearchEnter();
	gridMethod();
	
	$('#btnShowAddModal').click(function() {
		addFloor();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});
});

function gridMethod() {

	  
	
	// {label: 'Code', name: 'code', align: 'left', width: '15%', hidden: false, sort: true, formatter: floorEditPageLink},
	
	(window.cp_isCanEdit? "" : floorEditPageLink=null);
	
	var tableDef = {
			url: "../floor/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '15%', hidden: false, sort: true ,formatter: floorEditPageLink},
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
		url:"../floor/getRecord?ids=" + encodeURIComponent(obj),
		success: function (response) {

			$("#content").html(response);   
			onload();          
		}
	});	
}

function addFloor() {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url: '../floor/floorRedirect',
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onload(); 
		}
	});	
}

function floorEditPageLink(cellvalue, rowObject){
	
	var txt ="";
	 
	if(rowObject.is_System){
		txt = 	cellvalue;
		
	}else{
		var  txt="<span class='jqGrid_showLinkSapan' onclick='edit(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
	}
	
	 
	return txt;
}
