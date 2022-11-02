$(document).ready(function(){
	//userInfoBackground();
	dateFormat=convertDateFormatToJqueryDateFormate($('#dateFormat').val());
});

/**
 * for userInfo background setting
 */
function userInfoBackground(){
	var status=$("#status").text();
	if(status == "CONFIRMED"){
		$('#status').removeClass('s-body-canUnConfirm');
		$('#status').removeClass('stat-unconfirmed');
		$('#status').addClass('s-body-canUnConfirm');
		$('#status').addClass('stat-confirmed');
	}
	else if(status=="UNCONFIRMED"){
		$('#status').removeClass('s-body-canConfirm');
		$('#status').removeClass('stat-confirmed');
		$('#status').addClass('s-body-canConfirm');
		$('#status').addClass('stat-unconfirmed');
	}
	else{
}
}
 


function depositList(resvId, sortCol, sortStatus) {
	$.ajax({
		url:'../deposit/paymentList',
		data: {
			resvNo: resvId,
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
					+ obj.remarks + "</td></tr>";
				count++;
			});
			
			$('#tblTxnLst tbody').html(trs);
			$('#txnCount').html(count);
			
			$("#myModal").modal({backdrop:"static"});
		}
	});	
	
}
/**
 * confirm
 */
function confirmValidation(){
	if ($('input.confirmation').prop('checked')) {
		var confirmedBy=$('#confirmBy').val();
		var confirmReference=$('#reference').val();
		var reserId=$('#reser_id').html();
		
		var confirmedObject=new Object();
		confirmedObject.confirmedBy=confirmedBy;
		confirmedObject.confirmReference=confirmReference;
		confirmedObject.reserId=reserId;
		
		
		var confirmationDetails= JSON.stringify(confirmedObject);
		$.ajax({
			async:false,
			url : '../reservation/confirmSubmit',
			type : 'POST',
			data : {
				'confirmationDetails' : confirmationDetails
			},
			success : function(response) {
				window.location  = "../reservation_test/tools?reservationNo="+reserId;
				$scope.backToList();
			}
		});
	}else{
		
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Please confirmation. ').ok('Ok').parent(angular.element(document.body)));
	}
}




function backToList(){
	window.location = "../reservation_test/tools?reservationNo=" +$('#reser_id').html();
}

function ShowDialogBox(title, content, btn1text, btn2text, functionText, parameterList) {
    var btn1css;
    var btn2css;

    if (btn1text == '') {
        btn1css = "hidecss";
    } else {
        btn1css = "showcss";
    }

    if (btn2text == '') {
        btn2css = "hidecss";
    } else {
        btn2css = "showcss";
    }
    $("#lblMessage").html(content);
   
    $("#dialog").dialog({
        resizable: false,
        title: title,
        modal: true,
        width: '400px',
        height: 'auto',
        bgiframe: false,
        hide: { effect: 'scale', duration: 400 },

        buttons: [
                        {
                            text: btn1text,
                            "class": btn1css,
                            click: function () {
                                                                        
                                $("#dialog").dialog('close');

                            }
                        },
                        {
                            text: btn2text,
                            "class": btn2css,
                            click: function () {
                                $("#dialog").dialog('close');
                            }
                        }
                    ]
    });
}
