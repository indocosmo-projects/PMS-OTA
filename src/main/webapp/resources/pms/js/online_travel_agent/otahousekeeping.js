
var x = 0;
var y = 0;


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


$( document ).ready(function() {
	getRoomidDB();
	getretrieveinhouseroomstatusDB();
	getretrieveinhouseroomstatus();
	setInterval(function () {
		getretrieveinhouseroomstatus();
		}, 86400000);
	
});


nav(x);
roominfonav(y);


$("#btn-roomstatus").on('click',function(){
	x = 0;
	nav(x);
})

$("#btn-update").on('click',function(){
	x = 1;
	nav(x);
})

$("#btn-block").on('click',function(){
	x = 2;
	nav(x);
})

$("#btn-unblock").on('click',function(){
	x = 3;
	nav(x);
})



function nav(x){
switch (x) {
case 0:
	$(".inhouseroomstatuslist").show();
	$(".updateroomstatuslist").hide();
	$(".blockroomlist").hide();
	$(".unblockroomlist").hide();
	
	break;
case 1:
	$(".inhouseroomstatuslist").hide();
	$(".updateroomstatuslist").show();
	$(".blockroomlist").hide();
	$(".unblockroomlist").hide();

	break;
	
case 2:
	$(".inhouseroomstatuslist").hide();
	$(".updateroomstatuslist").hide();
	$(".blockroomlist").show();
	$(".unblockroomlist").hide();

	break;
	
case 3:
	$(".inhouseroomstatuslist").hide();
	$(".updateroomstatuslist").hide();
	$(".blockroomlist").hide();
	$(".unblockroomlist").show();

	break;

	}
}


function getretrieveinhouseroomstatus(){
	
	  $('#imgloader').show();
	$.ajax({
		url: '/pms/otahousekeeping/otaretrieveinhouseroomstatus',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   getretrieveinhouseroomstatusSuccess(result.roomlist,result.checkinguestList);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}


function getretrieveinhouseroomstatusDB(){
	
	  $('#imgloader').show();
	$.ajax({
		url: '/pms/otahousekeeping/otaretrieveinhouseroomstatusDB',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   getretrieveinhouseroomstatusSuccess(result.roomlist,result.checkinguestList);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}



function getretrieveinhouseroomstatusSuccess(roomlist,checkinguestList){
	
	$(".guestbody").empty();
	$(".roombody").empty();
	var rrow = '';
	var rss = '';
	var c = 0;
	$.each(roomlist,function(key,inv){
		c = c + 1;
		rss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.roomid+''+'</td>'
		+'<td class="tdwidth">'+inv.roomname+''+'</td>'
		+'<td class="tdwidth">'+inv.roomtypename+''+'</td>'
		+'<td class="tdwidth">'+inv.hkstatus+''+'</td>'
		+'<td class="tdwidth">'+inv.roomstatus+''+'</td>'
		+'</tr>'
	
		rrow = rrow + rss ;
	});
	$(".roombody").append(rrow);
   
	
	var crow = '';
	var css = '';
	var c = 0;
	$.each(checkinguestList,function(key,inv){
		c = c + 1;
		css = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.reservationno+''+'</td>'
		+'<td class="tdwidth">'+inv.guestname+''+'</td>'
		+'<td class="tdwidth">'+inv.email+''+'</td>'
		+'<td class="tdwidth">'+inv.address+''+'</td>'
		+'<td class="tdwidth">'+inv.room+''+'</td>'
		+'<td class="tdwidth">'+inv.bookingdate+''+'</td>'
		+'<td class="tdwidth">'+inv.checkindate+''+'</td>'
		+'<td class="tdwidth">'+inv.checkoutdate+'</td>'
		+'<td class="tdwidth">'+inv.bookingstatus+''+'</td>'
		+'</tr>'
	
		crow = crow + css ;
	});
	$(".guestbody").append(crow);
	
}


$("#btnroomlist").on('click',function(){
	y = 0;
	roominfonav(y);
})
$("#btnguestlist").on('click',function(){
	y = 1;
	roominfonav(y);
})


function roominfonav(y){
	switch (y) {
	case 0:
		$(".roomlist").show();
		$(".guestlist").hide();
		break;
	case 1:
		$(".roomlist").hide();
		$(".guestlist").show();
		break;
	}
	}


$("#btn-updateroomstatus").on('click',function(){
	getupdateroomstatus();
})

	function getupdateroomstatus(){
	
	 $('#imgloader').show();
	$.ajax({
		url: '/pms/otahousekeeping/otaupdateroomstatus',
		type: 'POST',
		data:{
			roomid    : $("#updateroomstatusroomid").val(),
			unitid    : $("#updateroomstatusunitid").val(),
			hkstatus  : $("#updateroomstatushkstatus").val(),
			hkremarks : $("#updateroomstatushkremarks").val(),	
		},
		success: function (result) {
			 if(result.length > 0){
				   $("#updateroomstatussuccess").empty();
				   $("#updateroomstatussuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgupdateroomstatus").show();
			   setInterval(function () {
				   $("#successmsgupdateroomstatus").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgupdateroomstatus").show();
				 setInterval(function () {
					   $("#errormsgupdateroomstatus").hide();
						}, 5000);
			}
		    });
	}



$("#btn-blockroom").on('click',function(){
	getotablockroom();
})

function getotablockroom(){
	
	  $('#imgloader').show();
	$.ajax({
		url: '/pms/otahousekeeping/otablockroom',
		type: 'POST',
		data:{
			roomid     :  $("#blockroomroomid").val(),
			roomtypeid :  $("#blockroomroomtypeid").val(),
			fromdate   :  $("#blockroomfromdate").val(),
			todate     :  $("#blockroomtodate").val(),
			reason     :  $("#blockroomreason").val(),	
		},
		success: function (result) {
			 if(result.length > 0){
				   $("#blockroomsuccess").empty();
				   $("#blockroomsuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgblockroom").show();
			   setInterval(function () {
				   $("#successmsgblockroom").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgblockroom").show();
				 setInterval(function () {
					   $("#errormsgblockroom").hide();
						}, 5000);
			}
		    });
	}




$("#btn-unblockroom").on('click',function(){
	getotaunblockroom();
})


function getotaunblockroom(){
	
	  $('#imgloader').show();
	$.ajax({
		url: '/pms/otahousekeeping/otaunblockroom',
		type: 'POST',
		data:{
			roomid     : $("#unblockroomroomid").val(),	
			roomtypeid : $("#unblockroomroomtypeid").val(),	
			fromdate   : $("#unblockroomfromdate").val(),	
			todate     : $("#unblockroomtodate").val(),	
		},
		success: function (result) {
			 if(result.length > 0){
				   $("#unblockroomsuccess").empty();
				   $("#unblockroomsuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgunblockroom").show();
			   setInterval(function () {
				   $("#successmsgunblockroom").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgunblockroom").show();
				 setInterval(function () {
					   $("#errormsgunblockroom").hide();
						}, 5000);
			}
		    });
	}



function getRoomidDB(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otahousekeeping/otaroomidDB',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   console.log("result==>"+result);
			   getRoomidDBSuccess(result);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}


function getRoomidDBSuccess(response){
	
	$(".updateroomstatusroomid").empty();
	$(".updateroomstatusunitid").empty();
	
	$(".blockroomroomid").empty();
	$(".blockroomroomtypeid").empty();
	
	$(".unblockroomroomid").empty();
	$(".unblockroomroomtypeid").empty();

	var roomrow = '';
	var roomss = '';
	var unitrow = '';
	var unitss = '';
	var roomtypeidrow = '';
	var roomtypeidss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		
		roomss = '<option value="'+inv.roomid+'">'+inv.roomid+'</option>';
		roomrow = roomrow + roomss ;
		unitss = '<option value="'+inv.unitid+'">'+inv.unitid+'</option>';
		unitrow = unitrow + unitss ;
		roomtypeidss = '<option value="'+inv.roomtypeid+'">'+inv.roomtypeid+'</option>';
		roomtypeidrow = roomtypeidrow + roomtypeidss ;
	

	});

	$(".updateroomstatusroomid").append(roomrow);
	$(".updateroomstatusunitid").append(unitrow);
	
	$(".blockroomroomid").append(roomrow);
	$(".blockroomroomtypeid").append(roomtypeidrow);
	
	$(".unblockroomroomid").append(roomrow);
	$(".unblockroomroomtypeid").append(roomtypeidrow);
	
}

