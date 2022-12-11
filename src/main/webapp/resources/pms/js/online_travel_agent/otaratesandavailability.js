

var x = 0;
var y = 0;

getroominformationFromDB();

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

$("#btn-roominventory").on('click',function(){
	x = 0;
	nav(x);
})


$("#btn-updatelinear").on('click',function(){
	x = 1;
	nav(x);
})





function nav(x){
switch (x) {
case 0:
	$(".retrieveroominventory").show();
	$(".updatelinearrateinventorylist").hide();
	break;
case 1:
	$(".retrieveroominventory").hide();
	$(".updatelinearrateinventorylist").show();
	break;
	
	
}
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
		url: '/pms/otaratesandavailability/otaretrieveroominventory',
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
			 $('#imgloader').hide();
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


$("#btn-updatelinearrateinventory").on('click',function(){
	updatelinearrateinventory();
})


function updatelinearrateinventory(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaratesandavailability/otaupdatelinearrateinventory',
		type: 'POST',
		data: {
			contactid  : $("#contactid").val(),
			roomtypeid : $("#roomtypeid").val(),
			ratetypeid : $("#ratetypeid").val(),
			fromdate   : $("#fromdate").val(),
			todate     : $("#toodate").val(),
			base       : $("#base").val(),
			extraadult : $("#extraadult").val(),
			extrachild : $("#extrachild").val(),
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#linearsuccess").empty();
			   $("#linearsuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsglinear").show();
		   setInterval(function () {
			   $("#successmsglinear").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsglinear").show();
			 setInterval(function () {
				   $("#errormsglinear").hide();
					}, 5000);
		}
	    });
	}





function getroominformationFromDB(){
	
	var rooms = $("#roomrequired").val();
	$.ajax({
		url: '/pms/otadata/otaroominformationFromDB',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   rateroomtypesdropdown(result.otaroomrateplans);
			   $(".roominfolist").show();
			},
			error: function(error) {

			}
		    });
	}


getroominformation();

function getroominformation(){
	
	var rooms = 1;
	$.ajax({
		url: '/pms/otadata/otaroominformation',
		type: 'POST',
		data: {
			roomrequired : rooms ,
		},
		success: function (result) {
			   $('#imgloader').hide();
			   rateroomtypesdropdown(result.otaroomrateplans);
			   $(".roominfolist").show();
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}




function rateroomtypesdropdown(response){
	
	$("#roomtypeid").empty();
	$("#ratetypeid").empty();
	$("#contactid").empty();
	$("#roomtypeidpush").empty();
	var rowroom = '';
	var ssroom = '';
	var rowrate = '';
	var ssrate = '';
	var rowcontact = '';
	var sscontact = '';
	$.each(response,function(key,inv){
		ssroom = '<option value="'+inv.roomtypeid+'">'+inv.roomtypeid+'</option>';
		rowroom = rowroom + ssroom ;
		ssrate = '<option value="'+inv.ratetypeid+'">'+inv.ratetypeid+'</option>';
		rowrate = rowrate + ssrate ;
		sscontact = '<option value="'+inv.roomtypeid+'">'+inv.roomtypeid+'</option>';
		rowcontact = rowcontact + sscontact ;
	});
	$("#roomtypeid").append(rowroom);
	$("#ratetypeid").append(rowrate);
	$("#contactid").append(rowcontact);
	$("#roomtypeidpush").append(rowroom);
	
}


