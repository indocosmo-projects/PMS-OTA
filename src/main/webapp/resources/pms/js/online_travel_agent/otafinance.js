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

$("#btn-extraslist").on('click',function(){
	x = 0;
	nav(x);
})

$("#btn-hotelexpenseslist").on('click',function(){
	x = 1;
	nav(x);
})


$("#btn-billslist").on('click',function(){
	x = 2;
	nav(x);
})


$("#btn-financialaccountslist").on('click',function(){
	x = 3;
	nav(x);
	getFinancialAccounts();
})


$("#btn-revenueslist").on('click',function(){
	x = 4;
	nav(x);
})

$("#btn-outwardspaymentlist").on('click',function(){
	x = 5;
	nav(x);
})

$("#btn-inwardspaymentlist").on('click',function(){
	x = 6;
	nav(x);
})


$("#btn-journallist").on('click',function(){
	x = 7;
	nav(x);
})



$("#btn-incidentalinvoiceslist").on('click',function(){
	x = 8;
	nav(x);
})



function nav(x){
switch (x) {
case 0:
	$(".extraslist").show();
	$(".hotelexpenseslist").hide();
	$(".billslist").hide();
	$(".financialaccountslist").hide();
	$(".revenueslist").hide();
	$(".outwardpaymentslist").hide();
	$(".inwardspaymentslist").hide();
	$(".journalslist").hide();
	$(".incidentalinvoiceslist").hide();
	break;
	
case 1:
	$(".extraslist").hide();
	$(".hotelexpenseslist").show();
	$(".billslist").hide();
	$(".financialaccountslist").hide();
	$(".revenueslist").hide();
	$(".outwardpaymentslist").hide();
	$(".inwardspaymentslist").hide();
	$(".journalslist").hide();
	$(".incidentalinvoiceslist").hide();
	
	break;

	
case 2:
	$(".extraslist").hide();
	$(".hotelexpenseslist").hide();
	$(".billslist").show();
	$(".financialaccountslist").hide();
	$(".revenueslist").hide();
	$(".outwardpaymentslist").hide();
	$(".inwardspaymentslist").hide();
	$(".journalslist").hide();
	$(".incidentalinvoiceslist").hide();
	
	break;
	
case 3:
	$(".extraslist").hide();
	$(".hotelexpenseslist").hide();
	$(".billslist").hide();
	$(".financialaccountslist").show();
	$(".revenueslist").hide();
	$(".outwardpaymentslist").hide();
	$(".inwardspaymentslist").hide();
	$(".journalslist").hide();
	$(".incidentalinvoiceslist").hide();
	
	break;

case 4:
	$(".extraslist").hide();
	$(".hotelexpenseslist").hide();
	$(".billslist").hide();
	$(".financialaccountslist").hide();
	$(".revenueslist").show();
	$(".outwardpaymentslist").hide();
	$(".inwardspaymentslist").hide();
	$(".journalslist").hide();
	$(".incidentalinvoiceslist").hide();
	
	break;
	
case 5:
	$(".extraslist").hide();
	$(".hotelexpenseslist").hide();
	$(".billslist").hide();
	$(".financialaccountslist").hide();
	$(".revenueslist").hide();
	$(".outwardpaymentslist").show();
	$(".inwardspaymentslist").hide();
	$(".journalslist").hide();
	$(".incidentalinvoiceslist").hide();
	break;
	
case 6:
	$(".extraslist").hide();
	$(".hotelexpenseslist").hide();
	$(".billslist").hide();
	$(".financialaccountslist").hide();
	$(".revenueslist").hide();
	$(".outwardpaymentslist").hide();
	$(".inwardspaymentslist").show();
	$(".journalslist").hide();
	$(".incidentalinvoiceslist").hide();
	break;

	
case 7:
	$(".extraslist").hide();
	$(".hotelexpenseslist").hide();
	$(".billslist").hide();
	$(".financialaccountslist").hide();
	$(".revenueslist").hide();
	$(".outwardpaymentslist").hide();
	$(".inwardspaymentslist").hide();
	$(".journalslist").show();
	$(".incidentalinvoiceslist").hide();
	break;
	
case 8:
	$(".extraslist").hide();
	$(".hotelexpenseslist").hide();
	$(".billslist").hide();
	$(".financialaccountslist").hide();
	$(".revenueslist").hide();
	$(".outwardpaymentslist").hide();
	$(".inwardspaymentslist").hide();
	$(".journalslist").hide();
	$(".incidentalinvoiceslist").show();
	break;

	}
}

getextrasFromDB();


$( document ).ready(function() {
	getextras();
setInterval(function () {
	getextras();
		}, 86400000);
});



function getextras(){
	
	$('#imgloader').show();
	$.ajax({
		url: '/pms/otafinance/otaretrieveextras',
		type: 'GET',
		success: function (result) {
		   if(result.length > 0){
			   if(result[0].id < 0){
				   var msg = result[0].description;
				   $("#extrassuccess").empty();
				   $("#extrassuccess").append(msg);
				   $(".extrasdivlist").show();
		   		}else{
		   			getextrasSuccess(result);
		   		}
		   }
		   $('#imgloader').hide();
		   $("#successmsgextras").show();
		   setInterval(function () {
			   $("#successmsgextras").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgextras").show();
			 setInterval(function () {
				   $("#errormsgextras").hide();
					}, 5000);
		}
	    });
	}


	function getextrasSuccess(response){
		
		$(".extrasdivlist").show();
		$(".extrasbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.extrachargeid+''+'</td>'
			+'<td class="tdwidth">'+inv.shortcode+''+'</td>'
			+'<td class="tdwidth">'+inv.charge+''+'</td>'
			+'<td class="tdwidth">'+inv.description+''+'</td>'
			+'<td class="tdwidth">'+inv.rate+''+'</td>'
			+'<td class="tdwidth">'+inv.chargerule+''+'</td>'
			+'<td class="tdwidth">'+inv.postingrule+''+'</td>'
			+'<td class="tdwidth">'+inv.validfrom+''+'</td>'
			+'<td class="tdwidth">'+inv.validto+''+'</td>'
			+'<td class="tdwidth">'+inv.ischargealways+''+'</td>'
			+'<td class="tdwidth">'+inv.applyon_rateplan+''+'</td>'
			+'<td class="tdwidth">'+inv.applyon_special+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".extrasbody").append(row);
	}
	
	
	
	function getextrasFromDB(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrieveextrasFromDB',
			type: 'GET',
			success: function (result) {
			   if(result.length > 0){
			   		getextrasSuccess(result);
			   }
			   $('#imgloader').hide();
			   $("#successmsgextras").show();
			   setInterval(function () {
				   $("#successmsgextras").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgextras").show();
				 setInterval(function () {
					   $("#errormsgextras").hide();
						}, 5000);
			}
		    });
		}
	
	$("#btn-hotelexpenses").on('click',function(){
		getHotelExpenses();
	})	
	
		
	function getHotelExpenses(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrievehotelexpenses',
			type: 'POST',
			data: {
				fromdate    : $("#hotelexpensesfromdate").val(),
				todate      : $("#hotelexpensestoodate").val(),
			},
			success: function (result) {
				 if(result[0].id < 0){
					   var msg = result[0].errormsg;
					   $("#hotelexpensessuccess").empty();
					   $("#hotelexpensessuccess").append(msg);
					   $(".hotelexpensesdivlist").show();
			   		}else{
			   			getHotelExpensesSuccess(result);
			   		}
			   $('#imgloader').hide();
			   $("#successmsghotelexpenses").show();
			   setInterval(function () {
				     $("#successmsghotelexpenses").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errorhotelexpenses").show();
				 setInterval(function () {
					   $("#errorhotelexpenses").hide();
						}, 5000);
			}
		    });
		}
	
	

	function getHotelExpensesSuccess(response){
		
		$(".hotelexpensestablelist").show();
		$(".hotelexpensesbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.hotelname+''+'</td>'
			+'<td class="tdwidth">'+inv.hotelcode+''+'</td>'
			+'<td class="tdwidth">'+inv.voucherdate+''+'</td>'
			+'<td class="tdwidth">'+inv.voucherno+''+'</td>'
			+'<td class="tdwidth">'+inv.contactinfo+''+'</td>'
			+'<td class="tdwidth">'+inv.expensemadeto+''+'</td>'
			+'<td class="tdwidth">'+inv.paidout+''+'</td>'
			+'<td class="tdwidth">'+inv.paidoutcharges+''+'</td>'
			+'<td class="tdwidth">'+inv.paidoutcurrency+''+'</td>'
			+'<td class="tdwidth">'+inv.paymentmode+''+'</td>'
			+'<td class="tdwidth">'+inv.paymentcharges+''+'</td>'
			+'<td class="tdwidth">'+inv.paymentcurrency+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".hotelexpensesbody").append(row);
	}
	
	
	
	
	$("#btn-bills").on('click',function(){
		getBills();
	})	
	
		
	function getBills(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrievebills',
			type: 'POST',
			data: {
				fromdate    : $("#billsfromdate").val(),
				todate      : $("#billstoodate").val(),
			},
			success: function (result) {
				 if(result[0].id < 0){
					   var msg = result[0].errormsg;
					   $("#billssuccess").empty();
					   $("#billssuccess").append(msg);
					   $(".billsdivlist").show();
			   		}else{
			   			getBillsSuccess(result);
			   		}
			   $('#imgloader').hide();
			   $("#successmsgbills").show();
			   setInterval(function () {
				     $("#successmsgbills").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errorbills").show();
				 setInterval(function () {
					   $("#errorbills").hide();
						}, 5000);
			}
		    });
		}
	
	

	function getBillsSuccess(response){
		
		$(".billsdivlist").show();
		$(".billsbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.billtoname+''+'</td>'
			+'<td class="tdwidth">'+inv.guestname+''+'</td>'
			+'<td class="tdwidth">'+inv.hotelname+''+'</td>'
			+'<td class="tdwidth">'+inv.cgsttaxamount+''+'</td>'
			+'<td class="tdwidth">'+inv.amount+''+'</td>'
			+'<td class="tdwidth">'+inv.date+''+'</td>'
			+'<td class="tdwidth">'+inv.foliono+''+'</td>'
			+'<td class="tdwidth">'+inv.total+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".billsbody").append(row);
	}
	

		
	function getFinancialAccounts(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrievefinancialaccounts',
			type: 'GET',
			success: function (result) {
			   getFinancialAccountsSuccess(result);
			   $('#imgloader').hide();
			   $("#successmsgfinancialaccounts").show();
			   setInterval(function () {
				     $("#successmsgfinancialaccounts").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgfinancialaccounts").show();
				 setInterval(function () {
					   $("#errormsgfinancialaccounts").hide();
						}, 5000);
			}
		    });
		}
	
	

	function getFinancialAccountsSuccess(response){
		
		$(".financialaccountsdivlist").show();
		$(".financialaccountsbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.descriptionunkid+''+'</td>'
			+'<td class="tdwidth">'+inv.description+''+'</td>'
			+'<td class="tdwidth">'+inv.descriptiontypeunkid+''+'</td>'
			+'<td class="tdwidth">'+inv.descriptiontype+''+'</td>'
			+'<td class="tdwidth">'+inv.headerid+''+'</td>'
			+'<td class="tdwidth">'+inv.header+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".financialaccountsbody").append(row);
	}
	
	
	
	$("#btn-revenues").on('click',function(){
		getRevenues();
	})
	
	
	function getRevenues(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrieverevenues',
			type: 'POST',
			data: {
				fromdate : $("#revenuesfromdate").val(),
				todate   : $("#revenuestoodate").val(),
			},
			success: function (result) {
				console.log("result"+result);
		
				 if(result[0].id < 0){
					   var msg = result[0].reference15;
					   $("#revenuessuccess").empty();
					   $("#revenuessuccess").append(msg);
					   $(".revenuesdivlist").show();
			   		}else{
			   			getRevenuesSuccess(result);
			   		}
			   $('#imgloader').hide();
			   $("#successmsgrevenues").show();
			   setInterval(function () {
				     $("#successmsgrevenues").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgrevenues").show();
				 setInterval(function () {
					   $("#errormsgrevenues").hide();
						}, 5000);
			}
		    });
		}
	
	
	
	function getRevenuesSuccess(response){
		
		$(".revenuesdivlist").show();
		$(".revenuesbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.record_id+''+'</td>'
			+'<td class="tdwidth">'+inv.record_date+''+'</td>'
			+'<td class="tdwidth">'+inv.reference5+''+'</td>'
			+'<td class="tdwidth">'+inv.reference17+''+'</td>'
			+'<td class="tdwidth">'+inv.reference18+''+'</td>'
			+'<td class="tdwidth">'+inv.reference20+''+'</td>'
			+'<td class="tdwidth">'+inv.amount_paid+''+'</td>'
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".revenuesbody").append(row);
	}


	$("#btn-outwardpayments").on('click',function(){
		getOutwardspayments();
	})
	
	function getOutwardspayments(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrieveoutwardspayments',
			type: 'POST',
			data: {
				fromdate : $("#outwardpaymentsfromdate").val(),
				todate   : $("#outwardpaymentstoodate").val(),
			},
			success: function (result) {
				   console.log("result===>getOutwardspayments"+result);
				   getOutwardspaymentsSuccess(result);

				   $('#imgloader').hide();
				   $("#successmsgoutwardpayments").show();
			   setInterval(function () {
				     $("#successmsgoutwardpayments").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgoutwardpayments").show();
				 setInterval(function () {
					   $("#errormsgoutwardpayments").hide();
						}, 5000);
			}
		    });
		}
	
	function getOutwardspaymentsSuccess(response){
		
		$(".outwardpaymentsdivlist").show();
		$(".outwardpaymentsbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.type+''+'</td>'
			+'<td class="tdwidth">'+inv.transid+''+'</td>'
			+'<td class="tdwidth">'+inv.tran_datetime+''+'</td>'
			+'<td class="tdwidth">'+inv.name+''+'</td>'
			+'<td class="tdwidth">'+inv.email+''+'</td>'
			+'<td class="tdwidth">'+inv.location+''+'</td>'
			+'<td class="tdwidth">'+inv.grossamount+''+'</td>'
			+'<td class="tdwidth">'+inv.remark+''+'</td>' 
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".outwardpaymentsbody").append(row);
		
	}
	
	
	$("#btn-inwardspayments").on('click',function(){
		getInwardspayments();
	})
		
	function getInwardspayments(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrieveinwardspayments',
			type: 'POST',
			data: {
				fromdate : $("#inwardspaymentsfromdate").val(),
				todate   : $("#inwardspaymentstoodate").val(),
			},
			success: function (result) {
				   console.log("result==>getInwardspayments"+result);
				   getInwardspaymentsSuccess(result);

			   $('#imgloader').hide();
			   $("#successmsginwardspayments").show();
			   setInterval(function () {
				     $("#successmsginwardspayments").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsginwardspayments").show();
				 setInterval(function () {
					   $("#errormsginwardspayments").hide();
						}, 5000);
			}
		    });
		}
	
	
	function getInwardspaymentsSuccess(response){
		
		$(".inwardspaymentsdivlist").show();
		$(".inwardspaymentsbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.type+''+'</td>'
			+'<td class="tdwidth">'+inv.transid+''+'</td>'
			+'<td class="tdwidth">'+inv.tran_datetime+''+'</td>'
			+'<td class="tdwidth">'+inv.name+''+'</td>'
			+'<td class="tdwidth">'+inv.email+''+'</td>'
			+'<td class="tdwidth">'+inv.location+''+'</td>'
			+'<td class="tdwidth">'+inv.grossamount+''+'</td>'
			+'<td class="tdwidth">'+inv.remark+''+'</td>' 
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".inwardspaymentsbody").append(row);
		
	}
	
	
	
	$("#btn-journals").on('click',function(){
		getJournals();
	})
		
	function getJournals(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrievejournals',
			type: 'POST',
			data: {
				fromdate : $("#journalsfromdate").val(),
				todate   : $("#journalstoodate").val(),
			},
			success: function (result) {
				   console.log("result==>journals"+result);
				   getJournalsSuccess(result);

			   $('#imgloader').hide();
			   $("#successmsgjournals").show();
			   setInterval(function () {
				     $("#successmsgjournals").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgjournals").show();
				 setInterval(function () {
					   $("#errormsgjournals").hide();
						}, 5000);
			}
		    });
		}
	
	
	function getJournalsSuccess(response){
		
		$(".journalsdivlist").show();
		$(".journalsbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.type+''+'</td>'
			+'<td class="tdwidth">'+inv.transid+''+'</td>'
			+'<td class="tdwidth">'+inv.tran_datetime+''+'</td>'
			+'<td class="tdwidth">'+inv.name+''+'</td>'
			+'<td class="tdwidth">'+inv.email+''+'</td>'
			+'<td class="tdwidth">'+inv.location+''+'</td>'
			+'<td class="tdwidth">'+inv.grossamount+''+'</td>'
			+'<td class="tdwidth">'+inv.remark+''+'</td>' 
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".journalsbody").append(row);
		
	}
	
	
	
	$("#btn-incidentalinvoices").on('click',function(){
		getIncidentalInvoices();
	})
		
	function getIncidentalInvoices(){
		
		$('#imgloader').show();
		$.ajax({
			url: '/pms/otafinance/otaretrieveincidentalinvoices',
			type: 'POST',
			data: {
				fromdate : $("#incidentalinvoicesfromdate").val(),
				todate   : $("#incidentalinvoicestoodate").val(),
			},
			success: function (result) {
				   console.log("result==>journals"+result);
				   getIncidentalInvoicesSuccess(result);

			   $('#imgloader').hide();
			   $("#successmsgincidentalinvoices").show();
			   setInterval(function () {
				     $("#successmsgincidentalinvoices").hide();
					}, 5000);
			},
			error: function() {
				 $('#imgloader').hide();
				 $("#errormsgincidentalinvoices").show();
				 setInterval(function () {
					   $("#errormsgincidentalinvoices").hide();
						}, 5000);
			}
		    });
		}
	
	
	function getIncidentalInvoicesSuccess(response){
		
		$(".incidentalinvoicesdivlist").show();
		$(".incidentalinvoicesbody").empty();
		var row = '';
		var ss = '';
		var c = 0;
		$.each(response,function(key,inv){
			c = c + 1;
			ss = '<tr>'+
			+'<td class="tdwidth">'+c+'</td>'
			+'<td class="tdwidth">'+inv.id+''+'</td>'
			+'<td class="tdwidth">'+inv.type+''+'</td>'
			+'<td class="tdwidth">'+inv.transid+''+'</td>'
			+'<td class="tdwidth">'+inv.tran_datetime+''+'</td>'
			+'<td class="tdwidth">'+inv.name+''+'</td>'
			+'<td class="tdwidth">'+inv.email+''+'</td>'
			+'<td class="tdwidth">'+inv.location+''+'</td>'
			+'<td class="tdwidth">'+inv.grossamount+''+'</td>'
			+'<td class="tdwidth">'+inv.remark+''+'</td>' 
			+'</tr>'
		
			row = row + ss ;
		});
		
		$(".incidentalinvoicesbody").append(row);
		
	}
	
	
	
	
	
	