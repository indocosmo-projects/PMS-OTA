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

$("#btn-statistics").on('click',function(){
	x = 0;
	nav(x);
})

$("#btn-companyList").on('click',function(){
	x = 1;
	nav(x);
})


$("#btn-retrieveagentlist").on('click',function(){
	x = 2;
	nav(x);
})


$("#btn-createagentlist").on('click',function(){
	x = 3;
	nav(x);
})


$("#btn-guestlist").on('click',function(){
	x = 4;
	nav(x);
})




function nav(x){
switch (x) {
case 0:
	$(".gueststaysstatisticslist").show();
	$(".companylist").hide();
	$(".retrievetalist").hide();
	$(".createtalist").hide();
	$(".retrieveguestlist").hide();
	
	break;
	
case 1:
	$(".gueststaysstatisticslist").hide();
	$(".companylist").show();
	$(".retrievetalist").hide();
	$(".createtalist").hide();
	$(".retrieveguestlist").hide();
	
	break;

	
case 2:
	$(".gueststaysstatisticslist").hide();
	$(".companylist").hide();
	$(".retrievetalist").show();
	$(".createtalist").hide();
	$(".retrieveguestlist").hide();
	
	break;
	
case 3:
	$(".gueststaysstatisticslist").hide();
	$(".companylist").hide();
	$(".retrievetalist").hide();
	$(".createtalist").show();
	$(".retrieveguestlist").hide();
	
	break;

case 4:
	$(".gueststaysstatisticslist").hide();
	$(".companylist").hide();
	$(".retrievetalist").hide();
	$(".createtalist").hide();
	$(".retrieveguestlist").show();
	
	break;

	}

}

getgueststaticsFromDB();


$( document ).ready(function() {
	getgueststatics();
setInterval(function () {
	getgueststatics();
		}, 86400000);
});


function getgueststatics(){

	$.ajax({
		url: '/pms/otaothers/otaretrievegueststatics',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			  getgueststaticsSuccess(result);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}


function getgueststaticsSuccess(response){
	
	$(".guestbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.guestname+''+'</td>'
		+'<td class="tdwidth">'+inv.guestemail+''+'</td>'
		+'<td class="tdwidth">'+inv.totalnumberofstays+''+'</td>'
		+'<td class="tdwidth">'+inv.firststay+''+'</td>'
		+'<td class="tdwidth">'+inv.firstreservationno+''+'</td>'
		+'<td class="tdwidth">'+inv.firstfoliono+''+'</td>'
		+'<td class="tdwidth">'+inv.laststay+''+'</td>'
		+'<td class="tdwidth">'+inv.lastreservationno+''+'</td>'
		+'<td class="tdwidth">'+inv.lastfoliono+''+'</td>'
		+'<td class="tdwidth">'+inv.nextstay+''+'</td>'
		+'<td class="tdwidth">'+inv.nextreservationno+''+'</td>'
		+'<td class="tdwidth">'+inv.nextfoliono+''+'</td>'
		+'<td class="tdwidth">'+inv.lifetimespending+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".guestbody").append(row);
	
}



function getgueststaticsFromDB(){

	$.ajax({
		url: '/pms/otaothers/otaretrievegueststaticsFromDB',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			  getgueststaticsSuccess(result);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}

$("#btn-company").on('click',function(){
	getotaretrievecompany();
})


function getotaretrievecompany(){

	$.ajax({
		url: '/pms/otaothers/otaretrievecompany',
		type: 'POST',
		data: {
			ids   			: $("#companyid").val(),
			names 			: $("#companynames").val(),
			createdfromdate : $("#companycreatedfromdate").val(),
			createdtodate   : $("#companycreatedtoodate").val(),
			updatedfromdate : $("#companyupdatedfromdate").val(),
			updatedtodate   : $("#companyupdatedtoodate").val(),
			isactive        : $("#companyisactive").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			  if(result.length == 1){
				  $("#errormsgcompanynames").show();
			  }else{
			  getotaretrievecompanySuccess(result);
			  }
			  setInterval(function () {
				   $("#errormsgcompanynames").hide();
					}, 5000);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}


	function getotaretrievecompanySuccess(response){
		
		$(".companytablelist").show();
		$(".companybody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.sid+''+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.accountname+''+'</td>'
			+'<td class="tdwidth">'+inv.accountcode+''+'</td>'
			+'<td class="tdwidth">'+inv.contact_person+''+'</td>'
			+'<td class="tdwidth">'+inv.address+''+'</td>'
			+'<td class="tdwidth">'+inv.city+''+'</td>'
			+'<td class="tdwidth">'+inv.postalcode+''+'</td>'
			+'<td class="tdwidth">'+inv.state+''+'</td>'
			+'<td class="tdwidth">'+inv.country+''+'</td>'
			+'<td class="tdwidth">'+inv.phone+''+'</td>'
			+'<td class="tdwidth">'+inv.mobile+''+'</td>'
			+'<td class="tdwidth">'+inv.fax+''+'</td>'
			+'<td class="tdwidth">'+inv.email+''+'</td>'
			+'<td class="tdwidth">'+inv.taxid+''+'</td>'
			+'<td class="tdwidth">'+inv.registrationno+''+'</td>'
			+'<td class="tdwidth">'+inv.isactive+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
	$(".companybody").append(row);
	
	}
	

	
	
	
	
	$("#btn-travelagent").on('click',function(){
		getotaretrievetravelagent();
	})


	function getotaretrievetravelagent(){

		$.ajax({
			url: '/pms/otaothers/otaretrieveta',
			type: 'POST',
			data: {
				id  			: $("#travelagentid").val(),
				name 			: $("#travelagentname").val(),
				createdfromdate : $("#travelagentcreatedfromdate").val(),
				createdtodate   : $("#travelagentcreatedtoodate").val(),
				updatedfromdate : $("#travelagentupdatedfromdate").val(),
				updatedtodate   : $("#travelagentupdatedtoodate").val(),
				isactive        : $("#travelagentisactive").val(),
			},
			success: function (result) {
				   $('#imgloader').hide();
				  if(result.length == 1){
					  $("#errormsgtravel").show();
				  }else{
					  getotaretrievetravelagentSuccess(result);
				  }
				  setInterval(function () {
					   $("#errormsgcompanynames").hide();
						}, 5000);
				},
				error: function(error) {
					 $('#imgloader').hide();
				}
			    });
		}


		function getotaretrievetravelagentSuccess(response){
			
			$(".travelagenttablelist").show();
			$(".travelagentbody").empty();
			var row = '';
			var ss = '';
			var c = 0;
			$.each(response,function(key,inv){
				c = c + 1;
				ss = '<tr>'+
				+'<td class="tdwidth">'+c+'</td>'
				+'<td class="tdwidth">'+inv.sid+''+'</td>'
				+'<td class="tdwidth">'+inv.id+''+'</td>'
				+'<td class="tdwidth">'+inv.accountname+''+'</td>'
				+'<td class="tdwidth">'+inv.accountcode+''+'</td>'
				+'<td class="tdwidth">'+inv.contact_person+''+'</td>'
				+'<td class="tdwidth">'+inv.address+''+'</td>'
				+'<td class="tdwidth">'+inv.city+''+'</td>'
				+'<td class="tdwidth">'+inv.postalcode+''+'</td>'
				+'<td class="tdwidth">'+inv.state+''+'</td>'
				+'<td class="tdwidth">'+inv.country+''+'</td>'
				+'<td class="tdwidth">'+inv.phone+''+'</td>'
				+'<td class="tdwidth">'+inv.mobile+''+'</td>'
				+'<td class="tdwidth">'+inv.fax+''+'</td>'
				+'<td class="tdwidth">'+inv.email+''+'</td>'
				+'<td class="tdwidth">'+inv.taxid+''+'</td>'
				+'<td class="tdwidth">'+inv.registrationno+''+'</td>'
				+'<td class="tdwidth">'+inv.commissionplan+''+'</td>'
				+'<td class="tdwidth">'+inv.commissionvalue+''+'</td>'
				+'<td class="tdwidth">'+inv.discount+''+'</td>'
				+'<td class="tdwidth">'+inv.isactive+''+'</td>'
				+'</tr>'
			
				row = row + ss ;
			});
		$(".travelagentbody").append(row);
		
		}
		

		$("#btn-createtravelagent").on('click',function(){
			getotacreateta();
		})


		function getotacreateta(){

			$.ajax({
				url: '/pms/otaothers/otacreateta',
				type: 'POST',
				data: {
					user  				: $("#createtravelagentuser").val(),
					businessname 		: $("#createtravelagentbusiness").val(),
					country   			: $("#createtravelagentcountry").val(),
					email 				: $("#createtravelagentemail").val(),
					percentdiscount   	: $("#createtravelagentpercentdiscount").val(),	
				},
				success: function (result) {
					   $('#imgloader').hide();
					   console.log("result===>"+result);
					   if(result.length > 0){
						   $("#createtravelsuccess").empty();
						   $("#createtravelsuccess").append(result);
						}
					   $('#imgloader').hide();
					   $("#successmsgcreatetravel").show();
					   setInterval(function () {
						   $("#successmsgcreatetravel").hide();
							}, 30000);
					},
					error: function(error) {
						 $('#imgloader').hide();
						 $("#errormsgcreatetravel").show();
						 setInterval(function () {
							   $("#errormsgcreatetravel").hide();
								}, 5000);
					}
				    });
			}

		getotaguestlist();

		function getotaguestlist(){

			$.ajax({
				url: '/pms/otaothers/otaguestlist',
				type: 'GET',
				success: function (result) {
					   $('#imgloader').hide();
					   console.log("result===>"+result);
					   if(result.length > 0){
						   $('#guestdetailtablelist').show();
						}
					   $('#imgloader').hide();
					   getotaguestlistSuccess(result);
					},
					error: function(error) {
						 $('#imgloader').hide();
						 $("#errormsgguestlist").show();
						 setInterval(function () {
							 $("#errormsgguestlist").hide();
						 }, 5000);
					}
				    });
			}
		
	function getotaguestlistSuccess(response){
			
			$(".guestdetailtablelist").show();
			$(".guestdetailbody").empty();
			var row = '';
			var ss = '';
			var c = 0;
			$.each(response,function(key,inv){
				c = c + 1;
				ss = '<tr>'+
				+'<td class="tdwidth">'+c+''+'</td>'
				+'<td class="tdwidth">'+inv.id+''+'</td>'
				+'<td class="tdwidth">'+inv.contact_person+''+'</td>'
				+'<td class="tdwidth">'+inv.address+''+'</td>'
				+'</tr>'
			
				row = row + ss ;
			});
		$(".guestdetailbody").append(row);
		
		}


