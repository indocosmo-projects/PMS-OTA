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

$("#btn-company").on('click',function(){
	x = 1;
	nav(x);
})


$("#btn-retrieveagent").on('click',function(){
	x = 2;
	nav(x);
})


$("#btn-createagent").on('click',function(){
	x = 3;
	nav(x);
})


$("#btn-guest").on('click',function(){
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
			  console.log("result==>"+result);
			  getgueststaticsSuccess(result);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}

getotaretrievecompany();

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
			  console.log("result==>"+result);
			  getgueststaticsSuccess(result);
			},
			error: function(error) {
				 $('#imgloader').hide();
			}
		    });
	}



