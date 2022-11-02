
/**
 * onload function
 */
$(document).ready(function() {
	var roomTypeId = sessionStorage.getItem('roomTypeId');
	
	masterFormContent();
    totalCount('../room/getCount');
    
    gridMethod();
    
    if(roomTypeId != null && roomTypeId != "") {
    	$('#room_type_id').val(roomTypeId);
		contentSearch();
	} else {
		simpleSearchEnter();
	}
    
	sessionStorage.removeItem('roomTypeId');
	clearSearchData();
	
	$('#btnShowAddModal').click(function() {
		addRoom();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id == "myModal") {
			$('#content').html('');
		}
	});	
});

/**
 * create and fill table for room list
 */
function gridMethod() {
	(window.cp_isCanEdit? "" : roomEditPageLink=null);
	var tableDef = {
			url: "../room/list",
			colModel: [
		           {label: 'id', name: 'id', align: 'left', width: '10%', hidden: true, sort: true, formatter: roomEditPageLink},

	           {label: 'Number', name: 'number', align: 'left', width: '10%', hidden: false, sort: true, formatter: roomEditPageLink},
	           {label: 'Name', name: 'name', align: 'left', width: '20%', hidden: false, sort: true},
	           {label: 'Room Type', name: 'roomType.name', align: 'left', width: '20%', hidden: false, sort: false},
	           {label: 'Inventory Status', name: 'invStatus', align: 'center', width: '5px', hidden: false, sort: false,formatter: roomEditPageLinkInv},
	           {label: 'Occupancy status', name: 'occStatus', align: 'center', width: '5px', hidden: false, sort: false,formatter: roomEditPageLinkOcc},
	           {label: 'Description', name: 'description', align: 'left', width: '', hidden: false, sort: false},
	           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
	           ],
	        tableWidth: "100%",
	        tableHeight: "",
	        bindingDivId: "pms_dynamic_grid",
	        id: "g_table"
	};
	
	generateGrid(tableDef);
}

function roomEditPageLink(cellvalue, rowObject){
	
	
	var txt ="";
	 
	if(rowObject.system){
		txt = 	cellvalue;
		
	}else{
		 txt = "<span class='jqGrid_showLinkSapan' onclick='edit(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
	}
	
	 return txt;
}

function roomEditPageLinkInv(cellvalue, rowObject){
 	if(cellvalue=='2'){
 		return "<i style='color:#73C857' class='fa fa-check'></i>"
 	}
 	else{
 		return "<i style='color:#EA4335' class='fa fa-close'></i>"
 	}
}

function roomEditPageLinkOcc(cellvalue, rowObject){
 	if(cellvalue=='2'){
 		return "<i style='color:#73C857' class='fa fa-check'></i>"
 	}
 	else{
 		return "<i style='color:#EA4335' class='fa fa-close'></i>"
 	}
}

/**
 * redirects to edit page with the selected room
 * @param obj
 */
//encodeURIComponent(obj)
function edit(obj) {
	$("#myModal").modal({backdrop: "static"});
	
	$.ajax({
		url: "../room/getRecord",
		data:{ids:obj},
		type : 'GET',
		contentType: "application/json",
		success: function (response) {
			$("#content").html(response);
			onPageLoad(); //defined in corporate_ta_edit.js
		}
	});
}

/**
 * redirects to the add page
 */
function addRoom() {
	$("#myModal").modal({backdrop: "static"});
	
	$.ajax({
		url: "../room/roomRedirect",
		type : 'GET',
		contentType: "application/json",
		success: function (response) {
			$("#content").html(response);
			onPageLoad(); //defined in corporate_ta_edit.js
		}
	});
}
