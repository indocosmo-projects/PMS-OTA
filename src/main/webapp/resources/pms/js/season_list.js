$(document).ready(function() {

	masterFormContent();
	totalCount('../seasons/getCount');
	gridMethod();
	simpleSearchEnter();

	$('#btnShowAddModal').click(function() {
		addSeason();
	});
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#content').html('');
		}
	});
});


function gridMethod() {
	(window.cp_isCanEdit? "" : seasonEditPageLink=null);
	var tableDef = {
			url: "../season/list",
			colModel: [
			           {label: 'Code', name: 'code', align: 'left', width: '10%', hidden: false, sort: true, formatter: seasonEditPageLink},
			           {label: 'Name', name: 'name', align: 'left', width: '20%', hidden: false, sort: true},
			           {label: 'Color', name: 'colorCode', align: 'left', width: '20%', hidden: false, sort: false, formatter: colorfmatter},
			           {label: 'Start Day', name: 'startDay', align: 'left', width: '', hidden: false, sort: false, formatter:startDay},
			           {label: 'End Day', name: 'endDay', align: 'left', width: '', hidden: false, sort: false,  formatter:endDay},
			           {label: 'Encryption', name: 'encryption', align: 'left', width: '', hidden: true, sort: false}
			           ],
			           tableWidth: "100%",
			           tableHeight: "",
			           bindingDivId: "pms_dynamic_grid",
			           id: "g_table"
	};


	generateGrid(tableDef);
}

/**
 * function for getting startDay with month. 
 * @param cellvalue cellvalue
 * @returns {String} startMonth
 */
function startDay(cellvalue,rowObject){
	switch (rowObject.startMonth) {
	case 1:
		var month="January";
		break;
	case 2:
		var month="February";
		break;
	case 3:
		var month="March";
		break;
	case 4:
		var month="April";
		break;
	case 5:	
		var month="May";
		break;
	case 6:
		var month="June";
		break;
	case 7:
		var month="July";
		break;
	case 8:
		var month="August";
		break;
	case 9:
		var month="September";
		break;
	case 10:
		var month="October";
		break;
	case 11:
		var month="November"
		break;
	case 12:
		var month="December";
		break;
	}
	var endDay=""+cellvalue + "-" +month//appending date with month
	return endDay;
}

/**
 * function for getting endDay with month. 
 * @param cellvalue rowObject
 * @returns {String} endMonth
 */
function endDay(cellvalue,rowObject){
	switch (rowObject.endMonth) {
	case 1:
		var month="January";
		break;
	case 2:
		var month="February";
		break;
	case 3:
		var month="March";
		break;
	case 4:
		var month="April";
		break;
	case 5:	
		var month="May";
		break;
	case 6:
		var month="June";
		break;
	case 7:
		var month="July";
		break;
	case 8:
		var month="August";
		break;
	case 9:
		var month="September";
		break;
	case 10:
		var month="October";
		break;
	case 11:
		var month="November"
		break;
	case 12:
		var month="December";
		break;
	}
	var startDay=""+cellvalue + "-" +month//appending date with month
	return startDay;
}

/**
 * display color in grid
 * @param cellvalue
 * @returns {String} span for displaying color 
 */
function colorfmatter(cellvalue, rowObject)
{	
	return '<span class="cellWithoutBackground"  style="background-color:' +
	cellvalue + ';">' + cellvalue + '</span>';
}


/**
 *season edit page redirect function 
 *@param obj encrypted record id
 */
function seasonEdit(obj) {


	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url: "../season/getSeason?ids=" + encodeURIComponent(obj),
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onLoadSeason();/* function for onLoad on season_edit.js*/

		}
	});	

}

/**
 * function for adding new record
 */
function addSeason() {
	
	$("#myModal").modal({backdrop: "static"});
	$.ajax({
		url: '../season/editRedirect',
		type : 'GET',
		success: function (response) {
			$("#content").html(response);
			onLoadSeason();/* function for onLoad on season_edit.js*/
		}
	});	
}

function seasonEditPageLink(cellvalue, rowObject){
	
	var txt ="";
	 
	if(rowObject.system){
		txt = 	cellvalue;
		
	}else{
		txt="<span class='jqGrid_showLinkSapan' onclick='seasonEdit(\"" + rowObject.encryption + "\")'>" + cellvalue + "</span>";
			 
	}
	 return txt;
}