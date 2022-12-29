

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

$("#btn-updateroominventorylist").on('click',function(){
	x = 0;
	nav(x);
})

$("#btn-updatelinearratelist").on('click',function(){
	x = 1;
	nav(x);
})

$("#btn-updatenonlinearratelist").on('click',function(){
	x = 2;
	nav(x);
})

$("#btn-retrieveroomratessdlist").on('click',function(){
	x = 3;
	nav(x);
	getroomrateswithsourcedetails();
})

$("#btn-updatemaxnightslist").on('click',function(){
	x = 4;
	nav(x);
})

$("#btn-updateminnightslist").on('click',function(){
	x = 5;
	nav(x);
})

$("#btn-updatestopselllist").on('click',function(){
	x = 6;
	nav(x);
})

$("#btn-updatecloseonarrivallist").on('click',function(){
	x = 7;
	nav(x);
})

$("#btn-updatecloseondeparturelist").on('click',function(){
	x = 8;
	nav(x);
})

$("#btn-retrieveroominventorylist").on('click',function(){
	x = 9;
	nav(x);
})

$("#btn-retrieveroomrateslist").on('click',function(){
	x = 10;
	nav(x);
})


function nav(x){
switch (x) {
case 0:
	$(".updateroominventorylist").show();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	
	break;
case 1:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").show();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	break;
	
case 2:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").show();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	break;
	
case 3:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").show();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	break;

case 4:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").show();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	break;
	
case 5:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").show();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	break;
	
case 6:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").show();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	break;
	
case 7:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").show();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	break;
	
case 8:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").show();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").hide();
	break;
	
case 9:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").show();
	$(".retrieveroomrateslist").hide();
	break;
	
case 10:
	$(".updateroominventorylist").hide();
	$(".updatelinearratelist").hide();
	$(".updatenonlinearratelist").hide();
	$(".retrieveroomratessdlist").hide();
	$(".updatemaxnightslist").hide();
	$(".updateminnightslist").hide();
	$(".updatestopselllist").hide();
	$(".updatecloseonarrivallist").hide();
	$(".updatecloseondeparturelist").hide();
	$(".retrieveroominventorylist").hide();
	$(".retrieveroomrateslist").show();
	break;
		
}
}


$("#btn-updateroominventory").on('click',function(){
	updateroominventory();
})


function updateroominventory(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaratesandavailability/otaupdateroominventory',
		type: 'POST',
		data: {
			roomtypeid : $("#updateroominventoryroomtypeid").val(),
			fromdate   : $("#updateroominventoryfromdate").val(),
			todate     : $("#updateroominventorytoodate").val(),
			base       : $("#updateroominventorybase").val(),
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#updateroominventorysuccess").empty();
			   $("#updateroominventorysuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgupdateroominventory").show();
		   setInterval(function () {
			   $("#successmsgupdateroominventory").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgupdateroominventory").show();
			 setInterval(function () {
				   $("#errormsgupdateroominventory").hide();
					}, 5000);
		}
	    });
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
			contactid  : $("#linearcontactid").val(),
			roomtypeid : $("#linearroomtypeid").val(),
			ratetypeid : $("#linearratetypeid").val(),
			fromdate   : $("#linearfromdate").val(),
			todate     : $("#lineartoodate").val(),
			base       : $("#linearbase").val(),
			extraadult : $("#linearextraadult").val(),
			extrachild : $("#linearextrachild").val(),
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



$("#btn-nonlinearrate").on('click',function(){
	updatenonlinearrate();
})

function updatenonlinearrate(){
	
	var adults = [] ;
	var childs = [] ;
	
	adults.push($("#nonlinearratesadult1").val());
	adults.push($("#nonlinearratesadult2").val());
	adults.push($("#nonlinearratesadult3").val());
	adults.push($("#nonlinearratesadult4").val());
	adults.push($("#nonlinearratesadult5").val());
	adults.push($("#nonlinearratesadult6").val());
	adults.push($("#nonlinearratesadult6").val());
	
	childs.push($("#nonlinearrateschild1").val());
	childs.push($("#nonlinearrateschild2").val());
	childs.push($("#nonlinearrateschild3").val());
	childs.push($("#nonlinearrateschild4").val());
	childs.push($("#nonlinearrateschild5").val());
	childs.push($("#nonlinearrateschild6").val());
	childs.push($("#nonlinearrateschild7").val());
	
	$.ajax({
		url: '/pms/otaratesandavailability/otaupdatenonlinearrate',
		type: 'POST',
		data: {
			 contactid 		: $("#nonlinearcontactid").val(),
			 roomtypeid 	: $("#nonlinearroomtypeid").val(),
			 ratetypeid 	: $("#nonlinearratetypeid").val(),
			 fromdate   	: $("#nonlinearfromdate").val(),
			 todate     	: $("#nonlineartoodate").val(),
			 base       	: $("#nonlinearratesbase").val(),
			 extraadult 	: $("#nonlinearratesextraadult").val(),
			 extrachild 	: $("#nonlinearratesextrachild").val(),
			 adultrates     : adults.toString(),
			 childrates     : childs.toString()
		},
		success: function (result) {
			   if(result.length > 0){
				   $("#nonlinearsuccess").empty();
				   $("#nonlinearsuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgnonlinear").show();
			   setInterval(function () {
				   $("#successmsgnonlinear").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgnonlinear").show();
				 setInterval(function () {
					   $("#errormsgnonlinear").hide();
						}, 5000);
			}
		    });
	}




	
function getroomrateswithsourcedetails(){
	
	$.ajax({
		url: '/pms/otaratesandavailability/otaretrieveroomratessourcedetails',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   getotaroomtypesSuccess(result.otaroomroomtypes);
			   getotaratetypesSuccess(result.otaroomratetypes);
			   getotarateplansSuccess(result.otaroomrateplans);
			   getseparatechplansSuccess(result.separatechannels);
			   $(".roominforrssdlist").show();
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
	
	$(".roomtypesrrssdbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.roomid+''+'</td>'
		+'<td class="tdwidth">'+inv.roomname+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".roomtypesrrssdbody").append(row);
	
}

function getotaratetypesSuccess(response){
	
	$(".ratetypesrrssdbody").empty();
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
	$(".ratetypesrrssdbody").append(row);
	
}

function getotarateplansSuccess(response){
	
	$(".rateplansrrssdbody").empty();
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
	$(".rateplansrrssdbody").append(row);
	
}


function getseparatechplansSuccess(response){
	
	$(".separatechannelrrssdbody").empty();
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
	$(".separatechannelrrssdbody").append(row);
	
}



$("#btnroomtypesrrssd").on('click',function(){
	y = 0;
	roominfonav(y);
})
$("#btnratetypesrrssd").on('click',function(){
	y = 1;
	roominfonav(y);
})
$("#btnrateplansrrssd").on('click',function(){
	y = 2;
	roominfonav(y);
})
$("#btnseperatechannelrrssd").on('click',function(){
	y = 3;
	roominfonav(y);
})

function roominfonav(y){
	switch (y) {
	case 0:
		$(".roomtypesrrssdlist").show();
		$(".ratetypesrrssdlist").hide();
		$(".rateplansrrssdlist").hide();
		$(".separatechannelrrssdlist").hide();
		break;
	case 1:
		$(".roomtypesrrssdlist").hide();
		$(".ratetypesrrssdlist").show();
		$(".rateplansrrssdlist").hide();
		$(".separatechannelrrssdlist").hide();
		break;
	case 2:
		$(".roomtypesrrssdlist").hide();
		$(".ratetypesrrssdlist").hide();
		$(".rateplansrrssdlist").show();
		$(".separatechannelrrssdlist").hide();
		break;
	case 3:
		$(".roomtypesrrssdlist").hide();
		$(".ratetypesrrssdlist").hide();
		$(".rateplansrrssdlist").hide();
		$(".separatechannelrrssdlist").show();
		break;
	}
	}

$("#btn-updatemaxnights").on('click',function(){
	getupdatemaxnights();
})

function getupdatemaxnights(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaratesandavailability/otaupdatemaxnights',
		type: 'POST',
		data: {
			rateplanid 		: $("#updatemaxnightsrateplanid").val(),
			fromdate   		: $("#updatemaxnightsfromdate").val(),
			todate     		: $("#updatemaxnightstoodate").val(),
			maxnight       	: $("#updatemaxnightsmaxnights").val(),
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#updatemaxnightssuccess").empty();
			   $("#updatemaxnightssuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgupdatemaxnights").show();
		   setInterval(function () {
			   $("#successmsgupdatemaxnights").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgupdatemaxnights").show();
			 setInterval(function () {
				   $("#errormsgupdatemaxnights").hide();
					}, 5000);
		}
	    });
	}




$("#btn-updateminnights").on('click',function(){
	getupdateminnights();
})

function getupdateminnights(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaratesandavailability/otaupdateminnights',
		type: 'POST',
		data: {
			rateplanid 		: $("#updateminnightsrateplanid").val(),
			fromdate   		: $("#updateminnightsfromdate").val(),
			todate     		: $("#updateminnightstoodate").val(),
			minnight       	: $("#updateminnightsminnights").val(),
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#updateminnightssuccess").empty();
			   $("#updateminnightssuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgupdateminnights").show();
		   setInterval(function () {
			   $("#successmsgupdateminnights").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgupdateminnights").show();
			 setInterval(function () {
				   $("#errormsgupdateminnights").hide();
					}, 5000);
		}
	    });
	}


$("#btn-updatestopsell").on('click',function(){
	getupdatestopsell();
})

function getupdatestopsell(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaratesandavailability/otaupdatestopsell',
		type: 'POST',
		data: {
			rateplanid 		: $("#updatestopsellrateplanid").val(),
			fromdate   		: $("#updatestopsellfromdate").val(),
			todate     		: $("#updatestopselltoodate").val(),
			stopsell       	: $("#updatestopsellstopsell").val(),
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#updatestopsellsuccess").empty();
			   $("#updatestopsellsuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgupdatestopsell").show();
		   setInterval(function () {
			   $("#successmsgupdatestopsell").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgupdatestopsell").show();
			 setInterval(function () {
				   $("#errormsgupdatestopsell").hide();
					}, 5000);
		}
	    });
	}



$("#btn-updatecoa").on('click',function(){
	getupdatecoa();
})

function getupdatecoa(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaratesandavailability/otaupdatecoa',
		type: 'POST',
		data: {
			rateplanid 		: $("#updatecoarateplanid").val(),
			fromdate   		: $("#updatecoafromdate").val(),
			todate     		: $("#updatecoatoodate").val(),
			coa       		: $("#updatecoacoa").val(),
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#updatecoasuccess").empty();
			   $("#updatecoasuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgupdatecoa").show();
		   setInterval(function () {
			   $("#successmsgupdatecoa").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgupdatecoa").show();
			 setInterval(function () {
				   $("#errormsgupdatecoa").hide();
					}, 5000);
		}
	    });
	}



$("#btn-updatecod").on('click',function(){
	getupdatecod();
})

function getupdatecod(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaratesandavailability/otaupdatecod',
		type: 'POST',
		data: {
			rateplanid 		: $("#updatecodrateplanid").val(),
			fromdate   		: $("#updatecodfromdate").val(),
			todate     		: $("#updatecodtoodate").val(),
			cod       		: $("#updatecodcod").val(),
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#updatecodsuccess").empty();
			   $("#updatecodsuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgupdatecod").show();
		   setInterval(function () {
			   $("#successmsgupdatecod").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgupdatecod").show();
			 setInterval(function () {
				   $("#errormsgupdatecod").hide();
					}, 5000);
		}
	    });
	}



$("#btn-searchroominventory").on('click',function(){
	getotaretrieveroominventory();
	$(".retrieveroominventorylist").show();
})


function getotaretrieveroominventory(){
	
	var fromdate = $(".retrieveroominventoryfdate").val();
	var todate =  $(".retrieveroominventorytdate").val();
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
	
	$("#updatestopsellrateplanid").empty();
	$("#updatemaxnightsrateplanid").empty();
	$("#updateminnightsrateplanid").empty();
	$("#updatecoarateplanid").empty();
	$("#updatecodrateplanid").empty();
	
	$("#updateroominventoryroomtypeid").empty();
	$("#linearroomtypeid").empty();
	$("#linearratetypeid").empty();
	$("#linearcontactid").empty();
	
	
	$("#nonlinearcontactid").empty();
	$("#nonlinearroomtypeid").empty();
	$("#nonlinearratetypeid").empty();
	
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
	$("#linearroomtypeid").append(rowroom);
	$("#linearratetypeid").append(rowrate);
	$("#linearcontactid").append(rowcontact);
	$("#updateroominventoryroomtypeid").append(rowroom);
	
	$("#nonlinearcontactid").append(rowrate);
	$("#nonlinearroomtypeid").append(rowroom);
	$("#nonlinearratetypeid").append(rowrate);
	
	$("#updatemaxnightsrateplanid").append(rowrate);
	$("#updateminnightsrateplanid").append(rowrate);
	$("#updatestopsellrateplanid").append(rowrate);
	$("#updatecoarateplanid").append(rowrate);
	$("#updatecodrateplanid").append(rowrate);
	
}



$("#btn-retrieveroomrates").on('click',function(){
	getretrieveroomrates();
})

function getretrieveroomrates(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaratesandavailability/otaretrieveroomrates',
		type: 'POST',
		data: {
			fromdate   		: $("#retrieveroomratesfromdate").val(),
			todate     		: $("#retrieveroomratestoodate").val(),
		},
		success: function (result) {
			$("#retrieveroomrateslist").show();
		   if(result.length > 0){
			   getotaretrieveroomratesSuccess(result);
			}
		   $('#imgloader').hide();
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrieveroomrates").show();
			 setInterval(function () {
				   $("#errormsgretrieveroomrates").hide();
					}, 5000);
		}
	    });
	}

	
function getotaretrieveroomratesSuccess(response){
	
	$(".retrieveroomratesbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.roomtypeid+''+'</td>'
		+'<td class="tdwidth">'+inv.ratetypeid+''+'</td>'
		+'<td class="tdwidth">'+inv.fromdate+''+'</td>'
		+'<td class="tdwidth">'+inv.todate+''+'</td>'
		+'<td class="tdwidth">'+inv.base+''+'</td>'
		+'<td class="tdwidth">'+inv.extraadult+''+'</td>'
		+'<td class="tdwidth">'+inv.extrachild+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".retrieveroomratesbody").append(row);
	
}
