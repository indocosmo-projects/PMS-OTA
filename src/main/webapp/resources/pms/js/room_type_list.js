/**
 * onload function
 */
$(document).ready(function() {

	masterFormContent();
	totalCount('../roomType/getCount');	
	gridMethod();
	simpleSearchEnter()

	$('#btnShowAddModal').click(function() {
		addRoomType();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id == "myModal") {
			$('#content').html('');
		}
	});	
	
});

/**
 * create and list the room type list
 */
function gridMethod() {
	(window.cp_isCanEdit? "" : roomTypeEditPageLink=null);
	var tableDef = {
			url: "../roomType/list",
			colModel: [
	           {label: 'Code', name: 'code', align: 'left', width: '10%', hidden: false, sort: true, formatter: roomTypeEditPageLink},
	           {label: 'Name', name: 'name', align: 'left', width: '20%', hidden: false, sort: true},
	           {label: 'Single Occ', name: 'single', align: 'left', width: '9%', hidden: false, sort: false},
	           {label: 'Double Occ', name: 'dble', align: 'left', width: '9%', hidden: false, sort: false},
	           {label: 'Triple Occ', name: 'triple', align: 'left', width: '8%', hidden: false, sort: false},
	           {label: 'Quad Occ', name: 'quad', align: 'left', width: '8%', hidden: false, sort: false},
	           {label: 'Rack Rate', name: '', align: 'left', width: '8%', hidden: false, sort: false, formatter: rackRateFmter},
	           {label: 'Rooms', name: '', align: 'left', width: '', hidden: false, sort: false, formatter: roomFmter},
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

function rackRateFmter(cellvalue, rowObject) {
	
    return '<span class="jqGrid_showLinkSapan" onclick="gotoRackRate(' + rowObject.id + ');">Rack rate</span>';
}

function roomFmter(cellvalue, rowObject) {
	
    return '<span class="jqGrid_showLinkSapan" onclick="gotoRooms(' + rowObject.id + ')">Rooms</span>';
}

function roomTypeEditPageLink(cellvalue, rowObject){
 	
	
	var txt ="";
	 
	if(rowObject.system){
		txt = 	cellvalue;
		
	}else{
		 
		txt = "<span class='jqGrid_showLinkSapan' onclick='edit(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
		  
	}
	
	
	   return txt;
}

function gotoRackRate(id) {
	
	clearSearchData();
	
	sessionStorage.setItem('roomTypeId', id);
/*	window.location.href = "../rackrate/rateList";
*/window.location.href = "../rackRate/rackRateList"
	
}

function gotoRooms(id) {
	
	clearSearchData();
	
	sessionStorage.setItem('roomTypeId', id);
	window.location.href = "../room/roomList";
}

/**
 * redirect to edit page with the selected room type
 * @param obj
 */
function edit(obj) {

	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);

	$("#myModal").modal({backdrop: "static"});
	
	$.ajax({
		url: "../roomType/getRecord?ids=" + encodeURIComponent(obj),
		type : 'GET',
		contentType: "application/json",
		success: function (response) {
			$("#content").html(response);
			onPageLoad(); //defined in corporate_ta_edit.js
		}
	});
}

/**
 * redirect to edit page.
 */
function addRoomType() {

	$("#myModal").modal({backdrop: "static"});
	
	$.ajax({

		url: "../roomType/editRedirect",
		type : 'GET',
		contentType: "application/json",
		success: function (response) {
			$("#content").html(response);
			onPageLoad(); //defined in corporate_ta_edit.js
		}
	});
}
