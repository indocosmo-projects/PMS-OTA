
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

$("#btn-authenticationList").on('click',function(){
	x = 0;
	nav(x);
})

$("#btn-roominfoList").on('click',function(){
	x = 1;
	nav(x);
})

$("#btn-hotelinfoList").on('click',function(){
	x = 2;
	nav(x);
})

$("#btn-amenitiesList").on('click',function(){
	x = 3;
	nav(x);
})

$("#btn-roomtypesList").on('click',function(){
	x = 4;
	nav(x);
})

$("#btn-countryList").on('click',function(){
	x = 5;
	nav(x);
})

$("#btn-extrasrateList").on('click',function(){
	x = 6;
	nav(x);
})

$("#btn-verifyTAList").on('click',function(){
	x = 7;
	nav(x);
})

$("#btn-paymentList").on('click',function(){
	x = 8;
	nav(x);
})

$("#btn-currencyList").on('click',function(){
	x = 9;
	nav(x);
})

$("#btn-paymethodsList").on('click',function(){
	x = 10;
	nav(x);
})

$("#btn-identitytypeList").on('click',function(){
	x = 11;
	nav(x);
})

$("#btn-roomsList").on('click',function(){
	x = 12;
	nav(x);
})



function nav(x){
	switch (x) {

		case 0:	$(".checkhotelauthenticationlist").show();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 1:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").show();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 2:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").show();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 3:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").show();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 4:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").show();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 5:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").show();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 6:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").show();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 7:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").show();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 8:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").show();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 9:	$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").show();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 10:$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").show();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 11:$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").show();
				$(".retrieveavailableroomlist").hide();
				break;
				
		case 12:$(".checkhotelauthenticationlist").hide();
				$(".retrieveroominformationlist").hide();
				$(".retrievehotelinformationlist").hide();
				$(".retrievehotelamenitieslist").hide();
				$(".retrieveroomtypeslist").hide();
				$(".retrievesalutationsandcountrylist").hide();
				$(".retrieveextrasratebasedonparameterslist").hide();
				$(".verifytravelagentlist").hide();
				$(".retrievepaymentgatewayslist").hide();
				$(".retrievecurrencylist").hide();
				$(".retrievepaymethodslist").hide();
				$(".retrieveidentitytypelist").hide();
				$(".retrieveavailableroomlist").show();
				break;
		
		}
    }




$("#btn-checkhotelauthentication").on('click',function(){
	getCheckhotelauthentication();
});


function getCheckhotelauthentication(){
	
	  $('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otacheckhotelauthentication',
		type: 'POST',
		data: {
			authkey :  $(".checkhotelauthenticationauthkey").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].hotelname;
				   $("#checkhotelauthenticationsuccess").empty();
				   $("#checkhotelauthenticationsuccess").append(msg);
		   		}else{
		   			getCheckhotelauthenticationSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgcheckhotelauthentication").show();
		   setInterval(function () {
			     $("#successmsgcheckhotelauthentication").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgcheckhotelauthentication").show();
			 setInterval(function () {
				   $("#errormsgcheckhotelauthentication").hide();
					}, 5000);
		}
		});
	}


function getCheckhotelauthenticationSuccess(response){
	
	$(".checkhotelauthenticationdivlist").show();
	$(".checkhotelauthenticationbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.hotelcode+''+'</td>'
		+'<td class="tdwidth">'+inv.hotelname+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	
	$(".checkhotelauthenticationbody").append(row);
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



$("#btn-retrievehotelinformation").on('click',function(){
	getRetrievehotelinformation();
});


function getRetrievehotelinformation(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrievehotelinformation',
		type: 'POST',
		data: {
			authkey   :  $(".retrievehotelinformationauthkey").val(),
			hotelcode :  $(".retrievehotelinformationhotelcode").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].hotelname;
				   $("#retrievehotelinformationsuccess").empty();
				   $("#retrievehotelinformationsuccess").append(msg);
		   		}else{
		   			getRetrievehotelinformationSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrievehotelinformation").show();
		   setInterval(function () {
			     $("#successmsgretrievehotelinformation").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrievehotelinformation").show();
			 setInterval(function () {
				   $("#errormsgretrievehotelinformation").hide();
					}, 5000);
		}
		});
	}


function getRetrievehotelinformationSuccess(response){
	
	$(".retrievehotelinformationdivlist").show();
	$(".retrievehotelinformationbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.hotelcode+''+'</td>'
		+'<td class="tdwidth">'+inv.hotelname+''+'</td>'
		+'<td class="tdwidth">'+inv.hotelunkid+''+'</td>'
		+'<td class="tdwidth">'+inv.country+''+'</td>'
		+'<td class="tdwidth">'+inv.propertytype+''+'</td>'
		+'<td class="tdwidth">'+inv.bookingengineurl+''+'</td>'
		+'<td class="tdwidth">'+inv.phone+''+'</td>'
		+'<td class="tdwidth">'+inv.email+''+'</td>'
		+'<td class="tdwidth">'+inv.bookingenginefoldername+''+'</td>'
		+'<td class="tdwidth">'+inv.currencycode+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	
	$(".retrievehotelinformationbody").append(row);
}




$("#btn-retrievehotelamenities").on('click',function(){
	getRetrievehotelamenities();
});


function getRetrievehotelamenities(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrievehotelamenities',
		type: 'POST',
		data: {
			authkey   :  $(".retrievehotelamenitiesauthkey").val(),
			hotelcode :  $(".retrievehotelamenitieshotelcode").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].amenity;
				   $("#retrievehotelamenitiessuccess").empty();
				   $("#retrievehotelamenitiessuccess").append(msg);
		   		}else{
		   			getRetrievehotelamenitiesSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrievehotelamenities").show();
		   setInterval(function () {
			     $("#successmsgretrievehotelamenities").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrievehotelamenities").show();
			 setInterval(function () {
				   $("#errormsgretrievehotelamenities").hide();
					}, 5000);
		}
		});
	}


function getRetrievehotelamenitiesSuccess(response){
	
	$(".retrievehotelamenitiesdivlist").show();
	$(".retrievehotelamenitiesbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.amenity+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	
	$(".retrievehotelamenitiesbody").append(row);
}



$("#btn-retrieveroomtypes").on('click',function(){
	getRetrieveroomtypes();
});


function getRetrieveroomtypes(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrieveroomtypes',
		type: 'POST',
		data: {
			authkey  		:  $(".retrievehotelamenitiesauthkey").val(),
			hotelcode 		:  $(".retrievehotelamenitieshotelcode").val(),
			publishtoweb	:  $(".retrieveroomtypespublishtoweb").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].msg;
				   $("#retrieveroomtypessuccess").empty();
				   $("#retrieveroomtypessuccess").append(msg);
		   		}else{
		   			getRetrieveroomtypesSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrieveroomtypes").show();
		   setInterval(function () {
			     $("#successmsgretrieveroomtypes").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrieveroomtypes").show();
			 setInterval(function () {
				   $("#errormsgretrieveroomtypes").hide();
					}, 5000);
		}
		});
	}


function getRetrieveroomtypesSuccess(response){
	
	$(".retrieveroomtypesdivlist").show();
	$(".retrieveroomtypesbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.roomtypeunkid+''+'</td>'
		+'<td class="tdwidth">'+inv.roomtype+''+'</td>'
		+'<td class="tdwidth">'+inv.shortcode+''+'</td>'
		+'<td class="tdwidth">'+inv.base_adult_occupancy+''+'</td>'
		+'<td class="tdwidth">'+inv.base_child_occupancy+''+'</td>'
		+'<td class="tdwidth">'+inv.max_adult_occupancy+''+'</td>'
		+'<td class="tdwidth">'+inv.max_child_occupancy+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	
	$(".retrieveroomtypesbody").append(row);
}




$("#btn-retrievesalutationsandcountry").on('click',function(){
	getRetrievesalutationsandcountry();
});


function getRetrievesalutationsandcountry(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrievesalutationsandcountry',
		type: 'POST',
		data: {
			authkey  		:  $(".retrievesalutationsandcountryauthkey").val(),
			hotelcode 		:  $(".retrievesalutationsandcountryhotelcode").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].msg;
				   $("#retrievesalutationsandcountrysuccess").empty();
				   $("#retrievesalutationsandcountrysuccess").append(msg);
		   		}else{
		   			getRetrievesalutationsandcountrySuccess(result[0].salutation,result[0].country);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrievesalutationsandcountry").show();
		   setInterval(function () {
			     $("#successmsgretrievesalutationsandcountry").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrievesalutationsandcountry").show();
			 setInterval(function () {
				   $("#errormsgretrievesalutationsandcountry").hide();
					}, 5000);
		}
		});
	}


function getRetrievesalutationsandcountrySuccess(response1,response2){
	
	$(".retrievesalutationsandcountrydivlist").show();
	$(".retrievesalutationsandcountrybody").empty();
	var row = '';
	var ss = '<ul>';
	var salutation = '';
	var country = '';
	var c = 0;
	$.each(response1,function(key,inv){
		 ss = ss + '<li>' + inv + '</li>';
	});
	salutation = ss + '</ul>' ;
	
	ss = '<ul>';
	$.each(response2,function(key,inv){
		 ss = ss + '<li>' + inv + '</li>';
	});
	country =  ss + '</ul>' ;
	
	row = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">1</td>'
		+'<td class="tdwidth">'+salutation+''+'</td>'
		+'<td class="tdwidth">'+country+''+'</td>'
		+'</tr>'

	$(".retrievesalutationsandcountrybody").append(row);
}



$("#btn-retrieveextrasratebasedonparameters").on('click',function(){
	getRetrieveextrasratebasedonparameters();
});


function getRetrieveextrasratebasedonparameters(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrieveextrasratebasedonparameters',
		type: 'POST',
		data: {
			authkey  			:  $(".retrieveextrasratebasedonparametersauthkey").val(),
			hotelcode 			:  $(".retrieveextrasratebasedonparametershotelcode").val(),
			checkindate  		:  $(".retrieveextrasratebasedonparameterscheckindate").val(),
			checkoutdate 		:  $(".retrieveextrasratebasedonparameterscheckoutdate").val(),
			extrachargeid  		:  $(".retrieveextrasratebasedonparametersextrachargeid").val(),
			extraitem 			:  $(".retrieveextrasratebasedonparametersextraitem").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].msg;
				   $("#retrieveextrasratebasedonparameterssuccess").empty();
				   $("#retrieveextrasratebasedonparameterssuccess").append(msg);
		   		}else{
		   			getRetrieveextrasratebasedonparametersSuccess(result[0].individualcharge,result[0].totalcharge);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrieveextrasratebasedonparameters").show();
		   setInterval(function () {
			     $("#successmsgretrieveextrasratebasedonparameters").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrieveextrasratebasedonparameters").show();
			 setInterval(function () {
				   $("#errormsgretrieveextrasratebasedonparameters").hide();
					}, 5000);
		}
		});
	}


function getRetrieveextrasratebasedonparametersSuccess(response, totalcharge){
	
	$(".retrieveextrasratebasedonparametersdivlist").show();

	var ss = '<ul>';
	var individualcharge = '';
	var c = 0;
	$.each(response,function(key,inv){
		 ss = ss + '<li>' + inv + '</li>';
	});
	individualcharge = ss + '</ul>' ;
	
	
	$(".individualcharge").empty();
	$(".individualcharge").append(individualcharge);
	$(".totalcharge").empty();
	$(".totalcharge").append(totalcharge);

}




$("#btn-verifytravelagent").on('click',function(){
	getVerifytravelagent();
});


function getVerifytravelagent(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaverifytravelagent',
		type: 'POST',
		data: {
			authkey  		:  $(".verifytravelagentauthkey").val(),
			hotelcode 		:  $(".verifytravelagenthotelcode").val(),
			username		:  $(".verifytravelagentusername").val(),
			password		:  $(".verifytravelagentpassword").val(),
			groupcode		:  $(".verifytravelagentgroupcode").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].msg;
				   $("#verifytravelagentsuccess").empty();
				   $("#verifytravelagentsuccess").append(msg);
		   		}else{
		   			getVerifytravelagentSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgverifytravelagent").show();
		   setInterval(function () {
			     $("#successmsgverifytravelagent").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgverifytravelagent").show();
			 setInterval(function () {
				   $("#errormsgverifytravelagent").hide();
					}, 5000);
		}
		});
	}


function getVerifytravelagentSuccess(response){
	
	$(".verifytravelagentdivlist").show();
	$(".verifytravelagentbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.contactname+''+'</td>'
		+'<td class="tdwidth">'+inv.businessname+''+'</td>'
		+'<td class="tdwidth">'+inv.email+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	
	$(".verifytravelagentbody").append(row);
}




$("#btn-retrievepaymentgateways").on('click',function(){
	getRetrievepaymentgateways();
});


function getRetrievepaymentgateways(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otapaymentgateways',
		type: 'POST',
		data: {
			authkey  		:  $(".retrievepaymentgatewaysauthkey").val(),
			hotelcode 		:  $(".retrievepaymentgatewayshotelcode").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].shortcode;
				   $("#retrievepaymentgatewayssuccess").empty();
				   $("#retrievepaymentgatewayssuccess").append(msg);
		   		}else{
		   			getRetrievepaymentgatewaysSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrievepaymentgateways").show();
		   setInterval(function () {
			     $("#successmsgretrievepaymentgateways").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrievepaymentgateways").show();
			 setInterval(function () {
				   $("#errormsgretrievepaymentgateways").hide();
					}, 5000);
		}
		});
	}


function getRetrievepaymentgatewaysSuccess(response){
	
	$(".retrievepaymentgatewaysdivlist").show();
	$(".retrievepaymentgatewaysbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.paymenttypeunkid+''+'</td>'
		+'<td class="tdwidth">'+inv.hotel_code+''+'</td>'
		+'<td class="tdwidth">'+inv.shortcode+''+'</td>'
		+'<td class="tdwidth">'+inv.paymenttype+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	
	$(".retrievepaymentgatewaysbody").append(row);
}




$("#btn-retrievecurrency").on('click',function(){
	getRetrievecurrency();
});


function getRetrievecurrency(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrievecurrency',
		type: 'POST',
		data: {
			authkey  		:  $(".retrievecurrencyauthkey").val(),
			hotelcode 		:  $(".retrievecurrencyhotelcode").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].country;
				   $("#retrievecurrencysuccess").empty();
				   $("#retrievecurrencysuccess").append(msg);
		   		}else{
		   			getRetrievecurrencySuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrievecurrency").show();
		   setInterval(function () {
			     $("#successmsgretrievecurrency").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrievecurrency").show();
			 setInterval(function () {
				   $("#errormsgretrievecurrency").hide();
					}, 5000);
		}
		});
	}


function getRetrievecurrencySuccess(response){
	
	$(".retrievecurrencydivlist").show();
	$(".retrievecurrencybody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.currencyid+''+'</td>'
		+'<td class="tdwidth">'+inv.country+''+'</td>'
		+'<td class="tdwidth">'+inv.currency+''+'</td>'
		+'<td class="tdwidth">'+inv.currencycode+''+'</td>'
		+'<td class="tdwidth">'+inv.sign+''+'</td>'
		+'<td class="tdwidth">'+inv.digitsafterdecimal+''+'</td>'
		+'<td class="tdwidth">'+inv.isbasecurrency+''+'</td>'
		+'<td class="tdwidth">'+inv.exchangerate+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	
	$(".retrievecurrencybody").append(row);
}





$("#btn-retrievepaymethods").on('click',function(){
	getRetrievepaymethods();
});


function getRetrievepaymethods(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrievepaymethods',
		type: 'POST',
		data: {
			authkey  		:  $(".retrievepaymethodsauthkey").val(),
			hotelcode 		:  $(".retrievepaymethodshotelcode").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].surchargename;
				   $("#retrievepaymethodssuccess").empty();
				   $("#retrievepaymethodssuccess").append(msg);
		   		}else{
		   			getRetrievepaymethodsSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrievepaymethods").show();
		   setInterval(function () {
			     $("#successmsgretrievepaymethods").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrievepaymethods").show();
			 setInterval(function () {
				   $("#errormsgretrievepaymethods").hide();
					}, 5000);
		}
		});
	}


function getRetrievepaymethodsSuccess(response){
	
	$(".retrievepaymethodsdivlist").show();
	$(".retrievepaymethodsbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.paymethodid+''+'</td>'
		+'<td class="tdwidth">'+inv.paymentid+''+'</td>'
		+'<td class="tdwidth">'+inv.name+''+'</td>'
		+'<td class="tdwidth">'+inv.shortcode+''+'</td>'
		+'<td class="tdwidth">'+inv.type+''+'</td>'
		+'<td class="tdwidth">'+inv.cardprocessing+''+'</td>'
		+'<td class="tdwidth">'+inv.surchargeapplicable+''+'</td>'
		+'<td class="tdwidth">'+inv.surchargetype+''+'</td>'
		+'<td class="tdwidth">'+inv.surchargevalue+''+'</td>'
		+'<td class="tdwidth">'+inv.surchargeid+''+'</td>'
		+'<td class="tdwidth">'+inv.surchargename+''+'</td>'
		+'</tr>'
		
		row = row + ss ;
	});
	
	$(".retrievepaymethodsbody").append(row);
}





$("#btn-retrieveidentitytype").on('click',function(){
	getRetrieveidentitytype();
});


function getRetrieveidentitytype(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrieveidentitytype',
		type: 'POST',
		data: {
			authkey  		:  $(".retrieveidentitytypeauthkey").val(),
			hotelcode 		:  $(".retrieveidentitytypehotelcode").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].surchargename;
				   $("#retrieveidentitytypesuccess").empty();
				   $("#retrieveidentitytypesuccess").append(msg);
		   		}else{
		   			getRetrieveidentitytypeSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrieveidentitytype").show();
		   setInterval(function () {
			     $("#successmsgretrieveidentitytype").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrieveidentitytype").show();
			 setInterval(function () {
				   $("#errormsgretrieveidentitytype").hide();
					}, 5000);
		}
		});
	}


function getRetrieveidentitytypeSuccess(response){
	
	$(".retrieveidentitytypedivlist").show();
	$(".retrieveidentitytypebody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.identitytypeid+''+'</td>'
		+'<td class="tdwidth">'+inv.name+''+'</td>'
		+'</tr>'
		
		row = row + ss ;
	});
	
	$(".retrieveidentitytypebody").append(row);
}






$("#btn-retrieveavailableroom").on('click',function(){
	getRetrieveavailableroom();
});


function getRetrieveavailableroom(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otaconfiguration/otaretrieveavailableroomlist',
		type: 'POST',
		data: {
			authkey  		:  $(".retrieveavailableroomauthkey").val(),
			hotelcode 		:  $(".retrieveavailableroomhotelcode").val(),
			fromdate  		:  $(".retrieveavailableroomfromdate").val(),
			todate 			:  $(".retrieveavailableroomtodate").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].roomname;
				   $("#retrieveavailableroomsuccess").empty();
				   $("#retrieveavailableroomsuccess").append(msg);
		   		}else{
		   			getRetrieveavailableroomSuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgretrieveavailableroom").show();
		   setInterval(function () {
			     $("#successmsgretrieveavailableroom").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgretrieveavailableroom").show();
			 setInterval(function () {
				   $("#errormsgretrieveavailableroom").hide();
					}, 5000);
		}
		});
	}


function getRetrieveavailableroomSuccess(response){
	
	$(".retrieveavailableroomdivlist").show();
	$(".retrieveavailableroombody").empty();
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
	
	$(".retrieveavailableroombody").append(row);
}




