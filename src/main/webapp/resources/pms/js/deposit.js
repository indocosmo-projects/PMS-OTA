$(document).ready(function(){
	  
	dateFormat = convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	hotelDate = $.datepicker.formatDate(dateFormat,new Date($('#hotelDate').val()));
	
	console.log("hotelDate="+hotelDate);
	
	$('.datepicker').datepicker({
		minDate: hotelDate,
		defaultDate: hotelDate,
		dateFormat: dateFormat		 
	});
	
	
	$('#depositDate').datepicker('setDate', hotelDate);
	
	
	$('#folio_bind_no_txn').val($("#folio_bind_no").val());
	
	numericalValidation('#amount');
    modeValidation();
});

function modeValidation(){
	var mode = $('#txnmode').val();
	if(mode == 1){
		$('#rem_label').hide();
		$('#remarks').hide();
	}else{
		$('#rem_label').show();
		$('#remarks').show();
	}
}


function depositList(folioBindNo, sortCol, sortStatus) {
	$.ajax({
		url:'../deposit/paymentList',
		data: {
			folioBindNo: folioBindNo,
			sortCol: sortCol,
			sortStatus: sortStatus
		},
		success: function (response) {
			var trs = "";
			var count = 0;
			
			$.each(response, function(index, obj) {
				var dateTime = obj.txn_time.split(' ');
				var time;
				var date;
				if(obj.txn_time!=''){
					var tempDate=obj.txn_time.split(' ');
					 time=tempDate[1].split(':');
					 var timeSet=time[0]+":"+time[1];
					 date=$.datepicker.formatDate(dateFormat,new Date(tempDate[0]));			
				}
				
				
				trs += "<tr><td class='control-label pymnt-border-rit'>" 
					+ date + "</td><td class='control-label pymnt-border-rit'>" 
					+ timeSet + "</td><td class='control-label pymnt-border-rit amount-align'>" 
					+ obj.amount + "</td><td class='control-label pymnt-border-rit '>" 
					+ obj.remarks + "</td><td class='control-label pymnt-border-rit '>" 
					+ obj.receivedForm +	"</td>" +
							"</tr>";
				count++;
			});
			
			$('#tblTxnLst tbody').html(trs);
			$('#txnCount').html(count);
			
			$("#myModal").modal({backdrop:"static"});
		}
	});	
	
}
function reservationList(){
	window.location="../reservation_test/reservationList";
}

function newDeposit(){
	if($('#amount').val()==""){
		alert("Enter deposit amount");
	}
	else{
	var amount = $('#amount').val();
	var mode = $('#txnmode').val();
	var remarks = $('#remarks').val();
	//var resvNo = $('#resvNoforDeposit').val();
	var receivedForm = $('#receivedForm').val();
	var depositFrom  = $('#depositFrom').val();
	
	
	
	var depositObj=new Object();
	depositObj.amount = amount;
	depositObj.payment_mode = mode;
	depositObj.remarks = remarks;
	
	depositObj.receivedForm=receivedForm;
	depositObj.depositFrom=depositFrom;
	
	if(depositFrom==1){
		depositObj.resvNo = $('#resvNoforDeposit').val();
		depositObj.checkInNo=0;
	}else{
		
		depositObj.checkInNo = $('#checkInNoforDeposit').val();
		depositObj.resvNo = 0;
	}
	
	
	
	
	var deposit= JSON.stringify(depositObj);
	
	
	$.ajax({
		async:false,
		url : '../deposit/newDeposit',
		type : 'GET',
		data : {
			'deposit' : deposit
		},
		success : function(response) {
			 
			if(depositFrom==1){
				window.location  = "../reservation_test/reservationList";
			}else{
				
				window.location  = "../reception/receptionList";
			}	
			
		//	window.location  = "../reservation/reservationList";
		}
		
	});
	}
}