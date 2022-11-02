$(document).ready(function() {
	masterFormContent();
	totalCount('../corporate/getCount');	
	gridMethod();
	simpleSearchEnter();

	$('#btnShowAddModal').click(function() {
		addCorporate();
	});

	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});	
});

function gridMethod() {	
	(window.cp_isCanEdit? "" : corporateEditPageLink=null);
	var tableDef = {
			url: "../corporate/list",
			colModel: [
	           {label: 'Code', name: 'code', align: 'left', width: '10%', hidden: false, sort: true, formatter: corporateEditPageLink},
	           {label: 'Name', name: 'name', align: 'left', width: '20%', hidden: false, sort: true},
	           {label: 'Customer', name: 'corporateClass', align: 'left', width: '', hidden: false, sort: false},
	           {label: 'Contact Person', name:'contactPerson', align: 'left', width: '',hidden: false, sort: false},
	           {label: 'Email', name: 'contactEmail', align: 'left', width: '', hidden:false, sort: false},
	           {label: 'Mobile', name: 'contactMobile', align: 'left', width: '',hidden:false, sort: false},
	           {label: 'Status', name: 'status', align: 'center', width: '5px', hidden: false, sort: false,formatter: corporateEditPageLink1},
	           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false},
	           ],
	        tableWidth: "100%",
	        tableHeight: "",
	        bindingDivId: "pms_dynamic_grid",
	        id: "g_table"
	};
	
	generateGrid(tableDef);
}

function corporateEdit(obj) {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
   	$.ajax({
   		url:"../corporate/getRecord?ids=" + encodeURIComponent(obj),
           success: function (response) {
          	$("#content").html(response);
             onLoadSetup(); /* load functions from discount_edit.js page*/
             }
         });	      
}

function addCorporate() {
	var data = $('#PageNo').val();
	sessionStorage.setItem("currentPage", data);
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
        url: '../corporate/editRedirect',
        type : 'GET',
		 async:false,
        success: function (response) {
       	$("#content").html(response);
            onLoadSetup(); /* load functions from discount_edit.js page*/          
          }
      });
}

function corporateEditPageLink(cellvalue, rowObject){
	var txt ="";
	 
	if(rowObject.system){
		txt = 	cellvalue;
		
	}else{
	 txt="<span class='jqGrid_showLinkSapan' onclick='corporateEdit(\""+rowObject.encryption+"\")'>"+cellvalue+"</span>";
	}
 	return txt;
}

function corporateEditPageLink1(cellvalue, rowObject){
 	if(cellvalue==1){
 		return "<i style='color:#73C857' class='fa fa-check'></i>"
 	}
 	else{
 		return "<i style='color:#EA4335' class='fa fa-close'></i>"
 	}
}