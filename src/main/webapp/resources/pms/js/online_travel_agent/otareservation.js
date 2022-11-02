
var x = 0;

$( document ).ready(function() {
	getOtaReservationListFromDB();
	bookingreceived("New","787","787");
});

nav(x);

$("#btnrefresh").on('click',function(){
	getOtaReservationList();
})

$("#btn-reservation").on('click',function(){
	x = 0;
	nav(x);
})
$("#btn-rental").on('click',function(){
	x = 1;
	nav(x);
})
$("#btn-booktrans").on('click',function(){
	x = 2;
	nav(x);
})
$("#btn-taxdetil").on('click',function(){
	x = 3;
	nav(x);
})
$("#btn-cancelreservation").on('click',function(){
	x = 4;
	nav(x);
})
$("#btn-notification").on('click',function(){
	x = 5;
	nav(x);
})


function nav(x){
switch (x) {
case 0:
	$(".reservelist").show();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	
	break;
case 1:
	$(".reservelist").hide();
	$(".rentallist").show();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	break;
case 2:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").show();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	break;
case 3:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").show();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	break;
case 4:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").show();
	$(".receivedbookingNotifylist").hide();
	break;
case 5:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").show();
	break;
	
}
}



function getOtaReservationListFromDB(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otadata/otareservationlistFromDB',
		type: 'GET',
		success: function (result) {
		   console.log(result);
		   getotareservationSuccess(result.otareservation);
		   getrentalinfoSuccess(result.otarentalinfo);
    	   getotacancelreservationSuccess(result.otacancelreservation);
		   getotabookingtransSuccess(result.otabookingtrans);
		   getotataxdeatilSuccess(result.otataxdeatil);
		   getBookingReceivedNotificationSuccess(result.otabookingtrans,result.otacancelreservation);
		   $('#imgloader').hide();
		},
		error: function() {
			alert("Error Occurred");
		}
	    });
	
}
  
function getOtaReservationList(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otadata/otareservationlist',
		type: 'GET',
		success: function (result) {
		   console.log(result);
		   getotareservationSuccess(result.otareservation);
		   getrentalinfoSuccess(result.otarentalinfo);
    	   getotacancelreservationSuccess(result.otacancelreservation);
		   getotabookingtransSuccess(result.otabookingtrans);
		   getotataxdeatilSuccess(result.otataxdeatil);
		   getBookingReceivedNotificationSuccess(result.otabookingtrans,result.otacancelreservation);
		   $('#imgloader').hide();
		},
		error: function() {
			alert("Error Occurred");
		}
	    });
	
}

function getotareservationSuccess(response){
	
	$(".reservationbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		ss = '<tr>'+
		+'<td class="tdwidth"></td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.uniquereservationid+''+'</td>'
		+'<td class="tdwidth">'+inv.bookedby+''+'</td>'
		+'<td class="tdwidth">'+inv.firstname+''+'</td>'
		+'<td class="tdwidth">'+inv.locationid+''+'</td>'
		+'<td class="tdwidth">'+inv.source+''+'</td>'
		+'<td class="tdwidth">'+inv.email+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".reservationbody").append(row);
	
}


function getrentalinfoSuccess(response){
	
	$(".rentalbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.reservationid+'</td>'
		+'<td class="tdwidth">'+inv.effectivedate+''+'</td>'
		+'<td class="tdwidth">'+inv.roomtypename+''+'</td>'
		+'<td class="tdwidth">'+inv.rent+''+'</td>'
		+'<td class="tdwidth">'+inv.rentpretax+''+'</td>'
		+'<td class="tdwidth">'+inv.discount+''+'</td>'
		+'<td class="tdwidth">'+inv.adult+''+'</td>'
		+'<td class="tdwidth">'+inv.child+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".rentalbody").append(row);
	
}

function  getotabookingtransSuccess(response){
	
	$(".booktransbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.reservationid+''+'</td>'
		+'<td class="tdwidth">'+inv.firstname+''+inv.lastname+'</td>'
		+'<td class="tdwidth">'+inv.rateplanname+''+'</td>'
		+'<td class="tdwidth">'+inv.start+''+'</td>'
		+'<td class="tdwidth">'+inv.end+''+'</td>'
		+'<td class="tdwidth">'+inv.arrivaltime+''+'</td>'
		+'<td class="tdwidth">'+inv.departuretime+''+'</td>'
		+'<td class="tdwidth">'+inv.email+''+'</td>'
		+'<td class="tdwidth">'+inv.currentstatus+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".booktransbody").append(row);
	
}


function getotataxdeatilSuccess(response){
	
	$(".taxdeatilbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.reservationid+''+'</td>'
		+'<td class="tdwidth">'+inv.taxcode+''+'</td>'
		+'<td class="tdwidth">'+inv.taxname+''+'</td>'
		+'<td class="tdwidth">'+inv.taxamount+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".taxdeatilbody").append(row);
	
}


function getotacancelreservationSuccess(response){
		
	$(".cancelreservationbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.reservationid+''+'</td>'
		+'<td class="tdwidth">'+inv.locationid+''+'</td>'
		+'<td class="tdwidth">'+inv.status+''+'</td>'
		+'<td class="tdwidth">'+inv.remark+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".cancelreservationbody").append(row);
	
}



function getBookingReceivedNotificationSuccess(response,response1){
	
	$(".receivedbookingNotifybody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.reservationid+''+'</td>'
		+'<td class="tdwidth">'+inv.status+''+'</td>'
		+'<td class="tdwidth"><button type="button" class="btn btn-danger checkstatus" status="'+inv.status
		+'" subid="'+inv.subbookingid+'" reseid="'+inv.reservationid+'">Received Status</button></td>'
		+'</tr>'
		row = row + ss ;
	});
	
	$.each(response1,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.reservationid+''+'</td>'
		+'<td class="tdwidth">'+inv.status+''+'</td>'
		+'<td class="tdwidth"><button type="button" class="btn btn-danger checkstatus" status="'+inv.status
		+'" subid="'+inv.reservationid+'" reseid="'+inv.reservationid+'">Received Status</button></td>'
		+'</tr>'
		row = row + ss ;
	});
	$(".receivedbookingNotifybody").append(row);
	
}


//$(".checkstatus").on('click',function(){
//	
//	alert("working");
//	var status = $(this).attr("status");
//	var bookingid = $(this).attr("reseid");
//	var pmsbookingid = $(this).attr("subid");
//	bookingreceived(status,bookingid,pmsbookingid);
//	
//})



function bookingreceived(status,bookingid,pmsbookingid){
	
	$.ajax({
		url: '/pms/otadata/otabookingreceived',
		type: 'POST',
		data: {
			 BookingId: bookingid,
             PMS_BookingId: pmsbookingid,
             Status: status,
		},
		success: function (result) {
			   console.log(result);
			   $('#imgloader').hide();
			},
			error: function() {
				alert("Error Occurred");
			}
		    });
	}

