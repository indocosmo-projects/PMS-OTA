$(document).ready(function() {	
	masterFormContent();
	totalCount('../accountMaster/getCount');	
	simpleSearchEnter();
	gridMethod();
	
	$('#btnShowAddModal').click(function() {
		addAccountMaster();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});
});

/**
 * generate grid
 */
function gridMethod() {
     
	$('#img').show();
	
	(window.cp_isCanEdit? "" : accMasterEditPageLink=null);
	var tableDef = {
			url: "../accountMaster/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '10%', hidden: false, sort: true, formatter: accMasterEditPageLink},
			           {label: 'Name', name: 'name', align: 'left', width: '20%', hidden: false, sort: true},
			           {label: 'HSN/SAC', name: 'hsn_code', align: 'left', width: '10%', hidden: false, sort: true},
			           {label: 'Account type', name: 'sysdef_acc_type', align: 'left', width: '10%', hidden: false, sort: false},
			           {label: 'Tax', name: 'tax_code', align: 'left',width: '10%', hidden: false, sort: false},
			           {label: 'Service charge', name: 'serviceCharge', align: 'right',width: '11%', hidden: false, sort: false},
			           {label: 'Credit days', name: 'creditDays', align: 'right',width: '11%', hidden: false, sort: false},
			           {label: 'Tax include', name: 'is_tax_included', align: 'center', width:'8%', hidden: false, sort: false, formatter : checkTaxIncludedStatus},
			           {label: 'Description', name: 'description', align: 'left', width: '', hidden: false, sort: false},
			           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
			           ],
			           tableWidth: "100%",
			           tableHeight: "",
			           bindingDivId: "pms_dynamic_grid",
			           id: "g_table"
	};

	generateGrid(tableDef);
	
	$('#img').hide();
}

/**
 * function for changing txntypeId(from database) to txntypeName in grid . 
 * 
 * @param cellvalue txntypeId
 * @param options 
 * @param rowObject
 * @returns {String} txntypeName;
 */
function txntype(cellvalue)
{
	switch (cellvalue) {
	case 1:
		var taxationtype="Room Revenue";
		break;
	case 2:
		var taxationtype="Outlet Revenue";
		break;
	case 3:
		var taxationtype="Other Revenue";
		break;
	case 4:
		var taxationtype="Deposit";
		break;
	case 5:	
		var taxationtype="Refund";
		break;
	case 6:
		var taxationtype="Discount";
		break;
	case 7:
		var taxationtype="Forfeit";
		break;
	default:
		var taxationtype="Nil";
	break;
	}

	return taxationtype;
}
function isNumber(evt) {
    
	evt = (evt) ? evt : window.event;
	    
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	    
	if (charCode != 46 && charCode > 31 
	      && (charCode < 48 || charCode > 57))
	  
	     return false;
}

/**
 *account master edit page redirect function 
 *@param obj encrypted record id
 */
function edit(obj) {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url:"../accountMaster/getRecord?ids=" + encodeURIComponent(obj),
		success: function (response) {

			$("#content").html(response);

			onLoadAccMaster();

		}
	})
}

/**
 *function for adding new record
 */
function addAccountMaster() {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url: '../accountMaster/editRedirect',
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onLoadAccMaster();
		}
	});	
}

function accMasterEditPageLink(cellvalue, rowObject){
	var txt ="";
	 
	if(rowObject.is_system){
		txt = 	cellvalue;
		
	}else{
		var  txt="<span class='jqGrid_showLinkSapan' onclick='edit(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
	}
	return txt;
}
function checkTaxIncludedStatus(cellvalue, rowObject){
 	if(cellvalue==true){
 		return "<i style='color:#73C857' class='fa fa-check'></i>"
 	}
 	else{
 		return "<i style='color:#EA4335' class='fa fa-close'></i>"
 	}
}