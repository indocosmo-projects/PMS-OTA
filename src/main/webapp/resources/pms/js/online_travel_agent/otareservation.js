
var x = 0;
var y = 0;

$( document ).ready(function() {
	getOtaReservationListFromDB();
	getOtaReservationList();
	setInterval(function () {
		getOtaReservationList();
		}, 86400000);
	
	
});

function logout(){
	window.location.href = "/pms/onlinetravelagent";

	$.ajax({
		url: '/pms/onlinetravelagent/otalogout',
		type: 'POST',
		success : function (result) {
			
		},
		error: function(data){
		},
	    });
	}



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

$("#btn-roominfo").on('click',function(){
	x = 6;
	nav(x);
})

$("#btn-reservationsingle").on('click',function(){
	x = 7;
	nav(x);
})


$("#btn-roominventory").on('click',function(){
	x = 8;
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
	$(".roominformation").hide();
	$(".reservationsingle").hide();
	$(".retrieveroominventory").hide();
	break;
case 1:
	$(".reservelist").hide();
	$(".rentallist").show();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	$(".roominformation").hide();
	$(".reservationsingle").hide();
	$(".retrieveroominventory").hide();
	break;
case 2:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").show();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	$(".roominformation").hide();
	$(".reservationsingle").hide();
	$(".retrieveroominventory").hide();
	break;
case 3:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").show();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	$(".roominformation").hide();
	$(".reservationsingle").hide();
	$(".retrieveroominventory").hide();
	break;
case 4:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").show();
	$(".receivedbookingNotifylist").hide();
	$(".roominformation").hide();
	$(".reservationsingle").hide();
	$(".retrieveroominventory").hide();
	break;
case 5:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").show();
	$(".roominformation").hide();
	$(".reservationsingle").hide();
	$(".retrieveroominventory").hide();
	break;
	
case 6:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	$(".roominformation").show();
	$(".reservationsingle").hide();
	$(".retrieveroominventory").hide();
	break;
	
case 7:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	$(".roominformation").hide();
	$(".reservationsingle").show();
	$(".retrieveroominventory").hide();
	break;
	
case 8:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	$(".receivedbookingNotifylist").hide();
	$(".roominformation").hide();
	$(".reservationsingle").hide();
	$(".retrieveroominventory").show();
	break;
	
}
}



function getOtaReservationListFromDB(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otadata/otareservationlistFromDB',
		type: 'GET',
		success: function (result) {
		   getotareservationSuccess(result.otareservation);
		   getrentalinfoSuccess(result.otarentalinfo);
    	   getotacancelreservationSuccess(result.otacancelreservation);
		   getotabookingtransSuccess(result.otabookingtrans);
		   getotataxdeatilSuccess(result.otataxdeatil);
		   $('#imgloader').hide();
		},
		error: function() {
			
		}
	    });
	
}
  
function getOtaReservationList(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otadata/otareservationlist',
		type: 'GET',
		success: function (result) {
		   getotareservationSuccess(result.otareservation);
		   getrentalinfoSuccess(result.otarentalinfo);
    	   getotacancelreservationSuccess(result.otacancelreservation);
		   getotabookingtransSuccess(result.otabookingtrans);
		   getotataxdeatilSuccess(result.otataxdeatil);
		   $('#imgloader').hide();
		},
		error: function() {

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


	bookingId();

	function bookingId(){
	
	$.ajax({
		url: '/pms/otadata/bookingid',
		type: 'GET',
		success: function (result) {
			   bookingiddropdown(result.otareservation,result.otacancelreservation);
			   pmsbookingiddropdown(result.otabookingtrans,result.otacancelreservation);
			   reservationiddropdown(result.otareservation);
			},
			error: function(error) {
				
			}
		    });
	}
	
	
	function bookingiddropdown(response1,response2){
		
		$("#bookingid").empty();
		var row = '';
		var ss = '';
		$.each(response1,function(key,inv){
			ss = '<option value="'+inv.uniquereservationid+'">'+inv.uniquereservationid+'</option>';
			row = row + ss ;
		});
		$.each(response2,function(key,inv){
			ss = '<option value="'+inv.reservationid+'">'+inv.reservationid+'</option>';
			row = row + ss ;
		});
		$("#bookingid").append(row);
		
	}
	

	function pmsbookingiddropdown(response1,response2){
		
		$("#pmsbookingid").empty();
		var row = '';
		var ss = '';
		$.each(response1,function(key,inv){
			ss = '<option value="'+inv.subbookingid+'">'+inv.subbookingid+'</option>';
			row = row + ss ;
		});
		$.each(response2,function(key,inv){
			ss = '<option value="'+inv.reservationid+'">'+inv.reservationid+'</option>';
			row = row + ss ;
		});
		$("#pmsbookingid").append(row);
		
	}
	
	
	function reservationiddropdown(response){
		
		$("#reservationid").empty();
		var row = '';
		var ss = '';
		$.each(response,function(key,inv){
			ss = '<option value="'+inv.uniquereservationid+'">'+inv.uniquereservationid+'</option>';
			row = row + ss ;
		});
		$("#reservationid").append(row);
		
	}
	
	
	
	$(".btn-notify").on('click',function(){
		bookingreceived($(".status").val(),$(".bookingId").val(),$(".pmsbookingid").val());
		$("#myModal").css("display","none");
	})


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
				   $('#imgloader').hide();
				   $("#successmsg").show();
				   setInterval(function () {
					   $("#successmsg").hide();
						}, 5000);
				},
				error: function(error) {
					 $("#errormsg").show();
					 setInterval(function () {
						   $("#errormsg").hide();
							}, 5000);
				}
			    });
		}
	

	$("#searchrooms").on('click',function(){
		getroominformation();
	})
	

	function getroominformation(){
		
		var rooms = $("#roomrequired").val();
		$.ajax({
			url: '/pms/otadata/otaroominformation',
			type: 'POST',
			data: {
				roomrequired : rooms ,
			},
			success: function (result) {
				   $('#imgloader').hide();
				   getotaroomtypesSuccess(result.otaroomroomtypes);
				   getotaratetypesSuccess(result.otaroomratetypes);
				   getotarateplansSuccess(result.otaroomrateplans);
				   $(".roominfolist").show();
				   y = 0;
				   roominfonav(y);
				},
				error: function(error) {

				}
			    });
		}
	
	getroominformationFromDB();
	
	function getroominformationFromDB(){
		
		var rooms = $("#roomrequired").val();
		$.ajax({
			url: '/pms/otadata/otaroominformationFromDB',
			type: 'GET',
			success: function (result) {
				   $('#imgloader').hide();
				   getotaroomtypesSuccess(result.otaroomroomtypes);
				   getotaratetypesSuccess(result.otaroomratetypes);
				   getotarateplansSuccess(result.otaroomrateplans);
				   $(".roominfolist").show();
				   y = 0;
				   roominfonav(y);
				},
				error: function(error) {

				}
			    });
		}
	
	y = 0;
	roominfonav(y);
	
	function getotaroomtypesSuccess(response){
		
		$(".roomtypes").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.roomtypesid+''+'</td>'
			+'<td class="tdwidth">'+inv.roomtypename+''+'</td>'
			+'<td class="tdwidth">'+inv.roomid+''+'</td>'
			+'<td class="tdwidth">'+inv.roomname+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		$(".roomtypes").append(row);
		
	}
	
	function getotaratetypesSuccess(response){
		
		$(".ratetypes").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.roomtypesid+''+'</td>'
			+'<td class="tdwidth">'+inv.roomtypesname+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		$(".ratetypes").append(row);
		
	}
	
	function getotarateplansSuccess(response){
		
		$(".rateplans").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.rateplanid+''+'</td>'
			+'<td class="tdwidth">'+inv.roomname+''+'</td>'
			+'<td class="tdwidth">'+inv.roomtypeid+''+'</td>'
			+'<td class="tdwidth">'+inv.roomtype+''+'</td>'
			+'<td class="tdwidth">'+inv.ratetypeid+''+'</td>'
			+'<td class="tdwidth">'+inv.ratetype+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		$(".rateplans").append(row);
		
	}
	
	

	$("#btnroomtypes").on('click',function(){
		y = 0;
		roominfonav(y);
	})
	$("#btnratetypes").on('click',function(){
		y = 1;
		roominfonav(y);
	})
	$("#btnrateplans").on('click',function(){
		y = 2;
		roominfonav(y);
	})
	
	
	function roominfonav(y){
		switch (y) {
		case 0:
			$(".roomtypeslist").show();
			$(".ratetypeslist").hide();
			$(".rateplanslist").hide();
			break;
		case 1:
			$(".roomtypeslist").hide();
			$(".ratetypeslist").show();
			$(".rateplanslist").hide();
			break;
		case 2:
			$(".roomtypeslist").hide();
			$(".ratetypeslist").hide();
			$(".rateplanslist").show();
			break;
		}
		}
	
	
	$("#searchreservationid").on('click',function(){
		getotareservationsingleroom();
		$(".singlelist").show();
	})
	
	
	
	function getotareservationsingleroom(){
		
		var reseid = $(".reservationid").val();
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otadata/otareservationsingleroom',
			type: 'POST',
			data: {
				reservationid : reseid ,
			},
			success: function (result) {
			   getotareservationsingleroomSuccess(result);
			   $('#imgloader').hide();
			},
			error: function() {
				
			}
		    });
		
	}
	
	

	function getotareservationsingleroomSuccess(response){
			
			$(".reservationsinglebody").empty();
			var row = '';
			var ss = '';
			var c = 0;
			$.each(response,function(key,inv){
				c = c + 1;
				ss = '<tr>'+
				+'<td class="tdwidth">'+c+'</td>'
				+'<td class="tdwidth">'+inv.id+''+'</td>'
				+'<td class="tdwidth">'+inv.room_name+''+'</td>'
				+'<td class="tdwidth">'+inv.room_code+''+'</td>'
				+'<td class="tdwidth">'+inv.reservation_id+''+'</td>'
				+'<td class="tdwidth">'+inv.booking_status+''+'</td>'
				+'<td class="tdwidth">'+inv.guest_name+''+'</td>'
				+'<td class="tdwidth">'+inv.check_in+''+'</td>'
				+'<td class="tdwidth">'+inv.check_out+''+'</td>'
				+'<td class="tdwidth">Rs. '+inv.total_amount+'</td>'
				+'<td class="tdwidth">'+inv.payment_type+''+'</td>'
				+'</tr>'
			
				row = row + ss ;
			});
			$(".reservationsinglebody").append(row);
			
		}
	
	
	$("#searchroominventory").on('click',function(){
		getotaretrieveroominventory();
		$(".retrieveroominventorylist").show();
	})
	
	
	function getotaretrieveroominventory(){
		
		var fromdate = $(".fdate").val();
		var todate =  $(".tdate").val();
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otadata/otaretrieveroominventory',
			type: 'POST',
			data: {
				fdate : fromdate ,
				tdate : todate,
			},
			success: function (result) {
				getotaretrieveroominventorySuccess(result);
			   $('#imgloader').hide();
			},
			error: function() {
				
			}
		    });
		
		}
	
	
	function getotaretrieveroominventorySuccess(response){
		
		$(".retrieveroominventorybody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.roomtypeid+''+'</td>'
			+'<td class="tdwidth">'+inv.availability+''+'</td>'
			+'<td class="tdwidth">'+inv.fromdate+''+'</td>'
			+'<td class="tdwidth">'+inv.todate+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		$(".retrieveroominventorybody").append(row);
		
	}
	
		
	
		