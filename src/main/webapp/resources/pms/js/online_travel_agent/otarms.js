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


nav(x);
roominfonav(y);
getOtaReservationListFromDB();

$("#btn-pushroominfolist").on('click',function(){
	x = 0;
	nav(x);
})

$("#btn-pushinventorylist").on('click',function(){
	x = 1;
	nav(x);
})


$("#btn-pushlinearrateslist").on('click',function(){
	x = 2;
	nav(x);
})


$("#btn-pushnonlinearrateslist").on('click',function(){
	x = 3;
	nav(x);
})


$("#btn-minimumnightslist").on('click',function(){
	x = 4;
	nav(x);
})

$("#btn-stopselllist").on('click',function(){
	x = 5;
	nav(x);
})


$("#btn-closeonarrivallist").on('click',function(){
	x = 6;
	nav(x);
})


$("#btn-closeondeparturelist").on('click',function(){
	x = 7;
	nav(x);
})


$("#btn-bookingstoezeelist").on('click',function(){
	x = 8;
	nav(x);
})



function nav(x){
switch (x) {
case 0:
	$(".pushroominfolist").show();
	$(".pushinventorylist").hide();
	$(".pushlinearrateslist").hide();
	$(".pushnonlinearrateslist").hide();
	$(".pushminimumnightslist").hide();
	$(".pushstopselllist").hide();
	$(".pushcloseonarrivallist").hide();
	$(".pushcloseondeparturelist").hide();
	$(".pushbookingstoezeelist").hide();
	
	
	break;
	
case 1:
	$(".pushroominfolist").hide();
	$(".pushinventorylist").show();
	$(".pushlinearrateslist").hide();
	$(".pushnonlinearrateslist").hide();
	$(".pushminimumnightslist").hide();
	$(".pushstopselllist").hide();
	$(".pushcloseonarrivallist").hide();
	$(".pushcloseondeparturelist").hide();
	$(".pushbookingstoezeelist").hide();
	
	break;

	
case 2:
	$(".pushroominfolist").hide();
	$(".pushinventorylist").hide();
	$(".pushlinearrateslist").show();
	$(".pushnonlinearrateslist").hide();
	$(".pushminimumnightslist").hide();
	$(".pushstopselllist").hide();
	$(".pushcloseonarrivallist").hide();
	$(".pushcloseondeparturelist").hide();
	$(".pushbookingstoezeelist").hide();
	
	break;
	
case 3:
	$(".pushroominfolist").hide();
	$(".pushinventorylist").hide();
	$(".pushlinearrateslist").hide();
	$(".pushnonlinearrateslist").show();
	$(".pushminimumnightslist").hide();
	$(".pushstopselllist").hide();
	$(".pushcloseonarrivallist").hide();
	$(".pushcloseondeparturelist").hide();
	$(".pushbookingstoezeelist").hide();
	
	break;

case 4:
	$(".pushroominfolist").hide();
	$(".pushinventorylist").hide();
	$(".pushlinearrateslist").hide();
	$(".pushnonlinearrateslist").hide();
	$(".pushminimumnightslist").show();
	$(".pushstopselllist").hide();
	$(".pushcloseonarrivallist").hide();
	$(".pushcloseondeparturelist").hide();
	$(".pushbookingstoezeelist").hide();
	
	break;
	
case 5:
	$(".pushroominfolist").hide();
	$(".pushinventorylist").hide();
	$(".pushlinearrateslist").hide();
	$(".pushnonlinearrateslist").hide();
	$(".pushminimumnightslist").hide();
	$(".pushstopselllist").show();
	$(".pushcloseonarrivallist").hide();
	$(".pushcloseondeparturelist").hide();
	$(".pushbookingstoezeelist").hide();
	
	break;
	
case 6:
	$(".pushroominfolist").hide();
	$(".pushinventorylist").hide();
	$(".pushlinearrateslist").hide();
	$(".pushnonlinearrateslist").hide();
	$(".pushminimumnightslist").hide();
	$(".pushstopselllist").hide();
	$(".pushcloseonarrivallist").show();
	$(".pushcloseondeparturelist").hide();
	$(".pushbookingstoezeelist").hide();
	
	break;
	
case 7:
	$(".pushroominfolist").hide();
	$(".pushinventorylist").hide();
	$(".pushlinearrateslist").hide();
	$(".pushnonlinearrateslist").hide();
	$(".pushminimumnightslist").hide();
	$(".pushstopselllist").hide();
	$(".pushcloseonarrivallist").hide();
	$(".pushcloseondeparturelist").show();
	$(".pushbookingstoezeelist").hide();
	
	break;
	
case 8:
	$(".pushroominfolist").hide();
	$(".pushinventorylist").hide();
	$(".pushlinearrateslist").hide();
	$(".pushnonlinearrateslist").hide();
	$(".pushminimumnightslist").hide();
	$(".pushstopselllist").hide();
	$(".pushcloseonarrivallist").hide();
	$(".pushcloseondeparturelist").hide();
	$(".pushbookingstoezeelist").show();
	
	break;

	}

}


getrequestroominformation();

function getrequestroominformation(){
	
	  $('#imgloader').show();
	$.ajax({
		url: '/pms/otarms/otarequestroominformation',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   getotaroomtypesSuccess(result.otaroomroomtypes);
			   getotaratetypesSuccess(result.otaroomratetypes);
			   getotarateplansSuccess(result.otaroomrateplans);
			   rateroomtypesdropdown(result.otaroomrateplans);
			   $(".roominfolist").show();
			   y = 0;
			   roominfonav(y);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}


getroominformationFromDB();

function getroominformationFromDB(){
	
	$.ajax({
		url: '/pms/otadata/otaroominformationFromDB',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   getotaroomtypesSuccess(result.otaroomroomtypes);
			   getotaratetypesSuccess(result.otaroomratetypes);
			   getotarateplansSuccess(result.otaroomrateplans);
			   rateroomtypesdropdown(result.otaroomrateplans);
			   $(".roominfolist").show();
			   y = 0;
			   roominfonav(y);
			},
			error: function(error) {

			}
		    });
	}


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




getroominformation();
getroominformationFromDB();

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
			   rateroomtypesdropdown(result.otaroomrateplans);
			   $(".roominfolist").show();
			   y = 0;
			   roominfonav(y);
			},
			error: function(error) {
				 $('#imgloader').hide();
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
			   y = 0;
			   roominfonav(y);
			},
			error: function(error) {

			}
		    });
	}



function rateroomtypesdropdown(response){
	
	$("#roomtypeid").empty();
	$("#ratetypeid").empty();
	$("#contactid").empty();
	$("#roomtypeidpush").empty();
	
	$("#pushlinearratesroomtypeid").empty();
	$("#pushlinearratesratetypeid").empty();

	$("#pushnonlinearratesroomtypeid").empty();
	$("#pushnonlinearratesratetypeid").empty();
	
	$("#pushminimumnightsroomtypeid").empty();
	$("#pushminimumnightsratetypeid").empty();
	$("#pushminimumnightsrateplanid").empty();
	
	$("#pushstopsellroomtypeid").empty();
	$("#pushstopsellratetypeid").empty();
	$("#pushstopsellrateplanid").empty();
	
	$("#pushcloseonarrivalroomtypeid").empty();
	$("#pushcloseonarrivalratetypeid").empty();
	$("#pushcloseonarrivalrateplanid").empty();
	
	$("#pushcloseondepartureroomtypeid").empty();
	$("#pushcloseondepartureratetypeid").empty();
	$("#pushcloseondeparturerateplanid").empty();
	
	$("#eratetypeid").empty();
	$("#eratetype").empty();
	$("#eroomtypename").empty();
	$("#eroomtypecode").empty();
	
	var rowroom = '';
	var ssroom = '';
	var rowrate = '';
	var ssrate = '';
	var rowcontact = '';
	var sscontact = '';
	var rowplan = '';
	var ssrateplan = '';
	var rowroomname = '';
	var ssroomname = '';
	var rowratetype = '';
	var ssratetype = '';
	$.each(response,function(key,inv){
		ssroom = '<option value="'+inv.roomtypeid+'">'+inv.roomtypeid+'</option>';
		rowroom = rowroom + ssroom ;
		ssrate = '<option value="'+inv.ratetypeid+'">'+inv.ratetypeid+'</option>';
		rowrate = rowrate + ssrate ;
		ssrateplan = '<option value="'+inv.rateplanid+'">'+inv.rateplanid+'</option>';
		rowplan = rowplan + ssrateplan ;
		sscontact = '<option value="'+inv.roomtypeid+'">'+inv.roomtypeid+'</option>';
		rowcontact = rowcontact + sscontact ;
		ssroomname = '<option value="'+inv.roomtype+'">'+inv.roomtype+'</option>';
		rowroomname = rowroomname + ssroomname ;
		ssratetype = '<option value="'+inv.ratetype+'">'+inv.ratetype+'</option>';
		rowratetype = rowratetype + ssratetype ;
	});
	
    var ss = '<option value="1872700000000000001">1872700000000000001</option>';
	rowroom = rowroom + ss ;
	rowrate = rowrate + ss ;
	rowcontact = rowcontact + ss ;
	rowplan = rowplan + ss ;
	rowratetype = rowratetype + '<option value="4000">4000</option>';
	rowroomname = rowroomname + '<option value="DELUXE">DELUXE</option>';
	
	$("#roomtypeid").append(rowroom);
	$("#ratetypeid").append(rowrate);
	$("#contactid").append(rowcontact);
	$("#roomtypeidpush").append(rowroom);
	
	$("#pushlinearratesroomtypeid").append(rowroom);
	$("#pushlinearratesratetypeid").append(rowrate);
	
	$("#pushnonlinearratesroomtypeid").append(rowroom);
	$("#pushnonlinearratesratetypeid").append(rowrate);
	
	$("#pushminimumnightsroomtypeid").append(rowroom);
	$("#pushminimumnightsratetypeid").append(rowrate);
	$("#pushminimumnightsrateplanid").append(rowplan);
	
	$("#pushstopsellroomtypeid").append(rowroom);
	$("#pushstopsellratetypeid").append(rowrate);
	$("#pushstopsellrateplanid").append(rowplan);
	
	$("#pushcloseonarrivalroomtypeid").append(rowroom);
	$("#pushcloseonarrivalratetypeid").append(rowrate);
	$("#pushcloseonarrivalrateplanid").append(rowplan);
	
	$("#pushcloseondepartureroomtypeid").append(rowroom);
	$("#pushcloseondepartureratetypeid").append(rowrate);
	$("#pushcloseondeparturerateplanid").append(rowplan);
	
	
	$("#eratetypeid").append(rowrate);
	$("#eratetype").append(rowratetype);
	$("#eroomtypename").append(rowroomname);
	$("#eroomtypecode").append(rowroom);
	
}





$("#btn-pushinventory").on('click',function(){
	getpushinventory();
})


function getpushinventory(){

	$('#imgloader').show();
	$.ajax({
		url: '/pms/otarms/otapushinventory',
		type: 'POST',
		data: {
			roomtypeId  : $("#roomtypeidpush").val(),
			fromdate   : $("#pushfromdate").val(),
			todate     : $("#pushtoodate").val(),
			count       : $("#pushcount").val()
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#pushinventorysuccess").empty();
			   $("#pushinventorysuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgpushinventory").show();
		   setInterval(function () {
			   $("#successmsgpushinventory").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgpushinventory").show();
			 setInterval(function () {
				   $("#errormsgpushinventory").hide();
					}, 5000);
		}
	    });
	}
	

$("#btn-pushlinearrates").on('click',function(){
	getpushlinearrates();
})


function getpushlinearrates(){

	$('#imgloader').show();
	$.ajax({
		url: '/pms/otarms/otapushlinearrates',
		type: 'POST',
		data: {
			roomtypeId    : $("#pushlinearratesroomtypeid").val(),
			ratetypeId    : $("#pushlinearratesratetypeid").val(),
			fromdate      : $("#pushlinearratesfromdate").val(),
			todate        : $("#pushlinearratestoodate").val(),
			count         : $("#pushlinearratescount").val(),
			extraadult    : $("#pushlinearratesextraadult").val(),
			extrachild    : $("#pushlinearratesextrachild").val()
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#pushlinearratessuccess").empty();
			   $("#pushlinearratessuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgpushlinearrates").show();
		   setInterval(function () {
			   $("#successmsgpushlinearrates").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgpushlinearrates").show();
			 setInterval(function () {
				   $("#errormsgpushlinearrates").hide();
					}, 5000);
		}
	    });
	}



$("#btn-pushnonlinearrates").on('click',function(){
	getpushnonlinearrates();
})


function getpushnonlinearrates(){
	
	var adults = [] ;
	var childs = [] ;
	
	adults.push($("#pushnonlinearratesadult1").val());
	adults.push($("#pushnonlinearratesadult2").val());
	adults.push($("#pushnonlinearratesadult3").val());
	adults.push($("#pushnonlinearratesadult4").val());
	adults.push($("#pushnonlinearratesadult5").val());
	adults.push($("#pushnonlinearratesadult6").val());
	adults.push($("#pushnonlinearratesadult7").val());
	
	childs.push($("#pushlinearrateschild1").val());
	childs.push($("#pushlinearrateschild2").val());
	childs.push($("#pushlinearrateschild3").val());
	childs.push($("#pushlinearrateschild4").val());
	childs.push($("#pushlinearrateschild5").val());
	childs.push($("#pushlinearrateschild6").val());
	childs.push($("#pushlinearrateschild7").val());
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otarms/otapushnonlinearrates',
		type: 'POST',
		data: {
			roomtypeId    : $("#pushnonlinearratesroomtypeid").val(),
			ratetypeId    : $("#pushnonlinearratesratetypeid").val(),
			fromdate      : $("#pushnonlinearratesfromdate").val(),
			todate        : $("#pushnonlinearratestoodate").val(),
			adults        : adults.toString(),
			childs        : childs.toString()
		},
		success: function (result) {
		   if(result.length > 0){
			   $("#pushnonlinearratessuccess").empty();
			   $("#pushnonlinearratessuccess").append(result);
			}
		   $('#imgloader').hide();
		   $("#successmsgpushnonlinearrates").show();
		   setInterval(function () {
			   $("#successmsgpushnonlinearrates").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgpushnonlinearrates").show();
			 setInterval(function () {
				   $("#errormsgpushnonlinearrates").hide();
					}, 5000);
		}
	    });
	}

	
	$("#btn-minimumnights").on('click',function(){
		getPushminimumnights();
	})

	function getPushminimumnights(){
	
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otarms/otapushminimumnights',
			type: 'POST',
			data: {
				roomtypeId    : $("#pushminimumnightsroomtypeid").val(),
				ratetypeId    : $("#pushminimumnightsratetypeid").val(),
				rateplanId    : $("#pushminimumnightsrateplanid").val(),
				count         : $("#pushminimumnightscount").val(),
				todate        : $("#pushminimumnightstoodate").val(),
				fromdate      : $("#pushminimumnightsfromdate").val()
			},
			success: function (result) {
			   if(result.length > 0){
				   $("#pushminimumnightssuccess").empty();
				   $("#pushminimumnightssuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgpushminimumnights").show();
			   setInterval(function () {
				   $("#successmsgpushminimumnights").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgpushminimumnights").show();
				 setInterval(function () {
					   $("#errormsgpushminimumnights").hide();
						}, 5000);
			}
		    });
		}

	
	

	$("#btn-stopsell").on('click',function(){
		getPushstopsell();
	})

	function getPushstopsell(){
	
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otarms/otapushstopsell',
			type: 'POST',
			data: {
				roomtypeId    : $("#pushstopsellroomtypeid").val(),
				ratetypeId    : $("#pushstopsellratetypeid").val(),
				rateplanId    : $("#pushstopsellrateplanid").val(),
				count         : $("#pushstopsellcount").val(),
				todate        : $("#pushstopselltoodate").val(),
				fromdate      : $("#pushstopsellfromdate").val()
			},
			success: function (result) {
			   if(result.length > 0){
				   $("#pushstopsellsuccess").empty();
				   $("#pushstopsellsuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgpushstopsell").show();
			   setInterval(function () {
				   $("#successmsgpushstopsell").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgpushstopsell").show();
				 setInterval(function () {
					   $("#errormsgpushstopsell").hide();
						}, 5000);
			}
		    });
		}

	
	
	$("#btn-closeonarrival").on('click',function(){
		getPushcloseonarrival();
	})

	function getPushcloseonarrival(){
	
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otarms/otapushcloseonarrival',
			type: 'POST',
			data: {
				roomtypeId    : $("#pushcloseonarrivalroomtypeid").val(),
				ratetypeId    : $("#pushcloseonarrivalratetypeid").val(),
				rateplanId    : $("#pushcloseonarrivalrateplanid").val(),
				count         : $("#pushcloseonarrivalcount").val(),
				todate        : $("#pushcloseonarrivaltoodate").val(),
				fromdate      : $("#pushcloseonarrivalfromdate").val()
			},
			success: function (result) {
			   if(result.length > 0){
				   $("#pushcloseonarrivalsuccess").empty();
				   $("#pushcloseonarrivalsuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgpushcloseonarrival").show();
			   setInterval(function () {
				   $("#successmsgpushcloseonarrival").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgpushcloseonarrival").show();
				 setInterval(function () {
					   $("#errormsgpushcloseonarrival").hide();
						}, 5000);
			}
		    });
		}

	
	
	
	$("#btn-closeondeparture").on('click',function(){
		getPushcloseondeparture();
	})

	function getPushcloseondeparture(){
	
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otarms/otapushcloseondeparture',
			type: 'POST',
			data: {
				roomtypeId    : $("#pushcloseondepartureroomtypeid").val(),
				ratetypeId    : $("#pushcloseondepartureratetypeid").val(),
				rateplanId    : $("#pushcloseondeparturerateplanid").val(),
				count         : $("#pushcloseondeparturecount").val(),
				todate        : $("#pushcloseondeparturetoodate").val(),
				fromdate      : $("#pushcloseondeparturefromdate").val()
			},
			success: function (result) {
			   if(result.length > 0){
				   $("#pushcloseondeparturesuccess").empty();
				   $("#pushcloseondeparturesuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgpushcloseondeparture").show();
			   setInterval(function () {
				   $("#successmsgpushcloseondeparture").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgpushcloseondeparture").show();
				 setInterval(function () {
					   $("#errormsgpushcloseondeparture").hide();
						}, 5000);
			}
		    });
		}
	
	
	
	
	$("#btn-eZee").on('click',function(){
		getPusheZee();
	})

	function getPusheZee(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otarms/otagetbookingstoezee',
			type: 'POST',
			data: {
				subbookingid     : $("#esubbookingid").val(),
				ratetypeid       : $("#eratetypeid").val(),
				ratetype         : $("#eratetype").val(),
				roomtypecode     : $("#eroomtypecode").val(),
				roomtypename     : $("#eroomtypename").val(),
				start            : $("#estart").val(),
				end              : $("#eend").val(),
				totalrate        : $("#etotalrate").val(),
				totaldiscount    : $("#etotaldiscount").val(),
				totalextracharge : $("#etotalextracharge").val(),
				totaltax         : $("#etotaltax").val(),
				totalpayment     : $("#etotalpayment").val(),
				salutation       : $("#esalutation").val(),
				firstname        : $("#efirstname").val(),
				lastname         : $("#elastname").val(),
				gender           : $("#egender").val(),
				address          : $("#eaddress").val(),
				city             : $("#ecity").val(),
				state            : $("#estate").val(),
				country 		 : $("#ecountry").val(),
				zipcode			 : $("#ezipcode").val(),
				phone			 : $("#ephone").val(),
				mobile			 : $("#emobile").val(),
				fax				 : $("#efax").val(),
				email			 : $("#eemail").val(),
				transportationmode  : $("#etransportationmode").val(),
	 		    vehicle			 : $("#evehicle").val(),
				pickupdate		 : $("#epickupdate").val(),
				pickuptime		 : $("#epickuptime").val(),
				comment			 : $("#ecomment").val(),
				
				effectivedate	 : $("#eeffectivedate").val(),
				adult			 : $("#eadult").val(),
				child			 : $("#echild").val(),
				rent			 : $("#erent").val(),
				extracharge		 : $("#eextracharge").val(),
				tax				 : $("#etax").val(),
				discount		 : $("#ediscount").val(),
				
				bookingid		 : $("#ebookingid").val(),
				status			 : $("#estatus").val(),
				source			 : $("#esource").val(),
				code			 : $("#ecode").val(),
				ccno			 : $("#eccno").val(),
				cctype			 : $("#ecctype").val(),
				ccexpirydate	 : $("#eccexpirydate").val(),
				cardholdersname  : $("#ecardholdersname").val(),   
			},
			success: function (result) {
				console.log("result==>"+result);
			   if(result.length > 0){
				   $("#pushbookingstoezeesuccess").empty();
				   $("#pushbookingstoezeesuccess").append(result);
				}
			   $('#imgloader').hide();
			   $("#successmsgbookingstoezee").show();
			   setInterval(function () {
				   $("#successmsgbookingstoezee").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgbookingstoezee").show();
				 setInterval(function () {
					   $("#errormsgbookingstoezee").hide();
						}, 5000);
			}
		    });
		}


	
	function getOtaReservationListFromDB(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otadata/otareservationlistFromDB',
			type: 'GET',
			success: function (result) {
			   getOtaReservationListFromDBSuccess(result.otareservation,result.otarentalinfo,result.otacancelreservation,
					   result.otabookingtrans,result.otataxdeatil);
			   $('#imgloader').hide();
			},
			error: function() {
				 $('#imgloader').hide();
			}
		    });
		
	}
	
	function getOtaReservationListFromDBSuccess(otareservation,otarentalinfo,otacancelreservation,otabookingtrans,otataxdeatil){
		
		$("#esubbookingid").empty();
		$("#ebookingid").empty();
		
		var bookingid = '';
		var rowbookingid = '';
		var subbookingid = '';
		var rowsubbookingid = '';
		
		$.each(otabookingtrans,function(key,inv){
			
			subbookingid = '<option value="'+inv.subbookingid+'">'+inv.subbookingid+'</option>';
			rowsubbookingid = rowsubbookingid + subbookingid ;

			bookingid = '<option value="'+inv.reservationid+'">'+inv.reservationid+'</option>';
			rowbookingid  = rowbookingid + bookingid ;
		});
		ss = '<option value="820">820</option>';
		rowbookingid = rowbookingid + ss ;
		rowsubbookingid = rowsubbookingid + ss;
		
		$("#esubbookingid").append(rowsubbookingid);
		$("#ebookingid").append(rowbookingid);
		
	}
	
	