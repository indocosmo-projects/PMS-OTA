
$(document).ready(function() {

	dateFormat = convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	hotelDate = $.datepicker.formatDate(dateFormat,new Date($('#hotelDate').val()));
 		
    $('#reportBtn').click(function() {		
    	getReport1();
	});
   

	$('.datepicker').datepicker({
		minDate: hotelDate,
		defaultDate: hotelDate,
		dateFormat: dateFormat
	});
	 
	 
 

	
});


function getReport1(){

	/*if(validation()){
		getFilterValues();
		window.open('../dailysale/report?pdfExcel=pdf&reportName=dailysale&date='+date+'&shiftFrom='+shift+'&terminalFrom='+terminal+'&cashier_from='+cashier+'','_blank');
	}
	*/
	
	var fromDate         = $('#txtFromDate').datepicker('getDate').getTime();
	var toDate           = $('#txtToDate').datepicker('getDate').getTime();
	var rptDesignFormat  = $('#rptDesignFormat').val();
	var reportName       = $('#reportName').val();
	var rptDesignName    = $('#rptDesignName').val();
	
	
	
	window.open('../reservationReport?rptDesignName='+rptDesignName+'&rptDesignFormat='+rptDesignFormat+'&txtFromDate='+fromDate+'&txtToDate='+toDate+'');

	
}
 
 
 


 