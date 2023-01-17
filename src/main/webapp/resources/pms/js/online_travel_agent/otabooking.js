
getroominformationFromDB();
bookingId();

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
	getOtaReservationListFromDB();
	getOtaReservationList();
	setInterval(function () {
		getOtaReservationList();
		}, 86400000);

});

$("#btnrefresh").tooltip();


nav(x);

$("#btn-checkavailabilitylist").on('click',function(){
	x = 0;
	nav(x);
})

$("#btn-retrieveallbookinglist").on('click',function(){
	x = 1;
	nav(x);
})

$("#btn-retrievesinglebookinglist").on('click',function(){
	x = 2;
	nav(x);
})

$("#btn-bookingreceivednotificationlist").on('click',function(){
	x = 3;
	nav(x);
})

$("#btn-arrivalslist").on('click',function(){
	x = 4;
	nav(x);
})

$("#btn-departureslist").on('click',function(){
	x = 5;
	nav(x);
})

$("#btn-chargetoroomlist").on('click',function(){
	x = 6;
	nav(x);
})

$("#btn-chargeonroomlist").on('click',function(){
	x = 7;
	nav(x);
})

$("#btn-posreceiptnolist").on('click',function(){
	x = 8;
	nav(x);
})

$("#btn-postroominfolist").on('click',function(){
	x = 9;
	nav(x);
	getRetrieveposttoroominformation();
})


$("#btn-postroominfospecificlist").on('click',function(){
	x = 10;
	nav(x);
})

$("#btn-roomsalesdatalist").on('click',function(){
	x = 11;
	nav(x);
})

$("#btn-roomscalendarlist").on('click',function(){
	x = 12;
	nav(x);
})

$("#btn-physicalroomslist").on('click',function(){
	x = 13;
	nav(x);
	getRetrievephysicalrooms();
})

$("#btn-checkincheckoutlist").on('click',function(){
	x = 14;
	nav(x);
	getCheckincheckout();
})

$("#btn-reservationdetailslist").on('click',function(){
	x = 15;
	nav(x);
})

$("#btn-historicalbookingslist").on('click',function(){
	x = 16;
	nav(x);
})

$("#btn-createabookinglist").on('click',function(){
	x = 17;
	nav(x);
})

$("#btn-postcreatebookingactionslist").on('click',function(){
	x = 18;
	nav(x);
	getPostcreatebookingsactions();
})

$("#btn-bookingbasedonparamslist").on('click',function(){
	x = 19;
	nav(x);
})

$("#btn-readabookinglist").on('click',function(){
	x = 20;
	nav(x);
})

$("#btn-cancelabookinglist").on('click',function(){
	x = 21;
	nav(x);
})

$("#btn-autosynclist").on('click',function(){
	x = 22;
	nav(x);
	getAutosync();
})

$("#btn-guestdatalist").on('click',function(){
	x = 23;
	nav(x);
})

$("#btn-addpaymentlist").on('click',function(){
	x = 24;
	nav(x);
})

$("#btn-guestprofilelist").on('click',function(){
	x = 25;
	nav(x);
})

$("#btn-guestcheckinlist").on('click',function(){
	x = 26;
	nav(x);
})

$("#btn-roomassignmentlist").on('click',function(){
	x = 27;
	nav(x);
})

$("#btn-guestcheckoutlist").on('click',function(){
	x = 28;
	nav(x);
})

$("#btn-listofbillslist").on('click',function(){
	x = 29;
	nav(x);
})

$("#btn-transactiondetailslist").on('click',function(){
	x = 30;
	nav(x);
})



function nav(x){
switch (x) {

	case 0:	$(".checkavailabilitylist").show();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;
	
	case 1:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").show();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;
			
	case 2:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").show();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;
			
	case 3:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").show();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;
			
	case 4:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").show();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;		
			
	case 5:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").show();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;		
			
	case 6:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").show();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;		
			
	case 7:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").show();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;		
			
	case 8:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").show();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;		
			
	case 9:	$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").show();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;		
			
	case 10:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").show();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;		
			
	case 11:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").show();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;		
			
	case 12:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").show();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 13:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").show();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
	
			
	case 14:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").show();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
		
	case 15:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").show();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;			
			
	case 16:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").show();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 17:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").show();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 18:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").show();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 19:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").show();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 20:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").show();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
			
	case 21:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").show();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 22:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").show();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 23:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").show();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 24:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").show();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 25:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").show();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 26:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").show();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 27:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").show();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 28:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").show();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 29:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").show();
			$(".retrievetransactiondetailslist").hide();
			break;	
			
	case 30:$(".checkavailabilitylist").hide();
			$(".retrieveallbookingslist").hide();
			$(".retrievesinglebookingslist").hide();
			$(".bookingreceivednotificationlist").hide();
			$(".retrievearrivalslist").hide();
			$(".retrievedepartureslist").hide();
			$(".postchargetoroomlist").hide();
			$(".voidchargeonroomlist").hide();
			$(".updateposreceiptnolist").hide();
			$(".retrieveposttoroominformationlist").hide();
			$(".retrieveposttoroominformationspecificlist").hide();
			$(".roomsalesdatalist").hide();
			$(".reservedroomscalendarlist").hide();
			$(".retrievephysicalroomslist").hide();
			$(".todayscheckIn-checkoutlist").hide();
			$(".reservationdetailsofaroomlist").hide();
			$(".pullhistoricalbookingslist").hide();
			$(".createabookinglist").hide();
			$(".postcreatebookingsactionslist").hide();
			$(".retrieveabookingbasedonparameterslist").hide();
			$(".readabookinglist").hide();
			$(".cancelabookinglist").hide();
			$(".autosyncfuturebookingsanditsmodificationslist").hide();
			$(".guestdataupdatelist").hide();
			$(".addpaymentlist").hide();
			$(".addguestprofiletobookingslist").hide();
			$(".guestcheckinlist").hide();
			$(".roomassignmentlist").hide();
			$(".guestcheckoutlist").hide();
			$(".retrievelistofbillslist").hide();
			$(".retrievetransactiondetailslist").show();
			break;	
			
}
}



function getroominformationFromDB(){
	
	var rooms = $("#roomrequired").val();
	$.ajax({
		url: '/pms/otadata/otaroominformationFromDB',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   rateroomtypesdropdown(result.otaroomrateplans);
			   rateroomnamedropdown(result.otaroomroomtypes);
			},
			error: function(error) {

			}
		    });
	}



function rateroomtypesdropdown(response){
	
	$("#checkavailabilityroomtypeid").empty();
	$("#roomsalesdataroomid").empty();
	$("#reservedroomscalendarroomid").empty();
	$("#createabookingrateplanid").empty();
	$("#createabookingratetypeid").empty();
	$("#createabookingroomtypeid").empty();
	$("#roomassignmentroomtypeid").empty();
	
	var rowroom = '';
	var ssroom = '';
	var rowrate = '';
	var ssrate = '';
	var rowplan = '';
	var ssplan = '';
	var rowcontact = '';
	var sscontact = '';
	$.each(response,function(key,inv){
		ssroom = '<option value="'+inv.roomtypeid+'">'+inv.roomtypeid+'</option>';
		rowroom = rowroom + ssroom ;
		ssrate = '<option value="'+inv.ratetypeid+'">'+inv.ratetypeid+'</option>';
		rowrate = rowrate + ssrate ;
		sscontact = '<option value="'+inv.roomtypeid+'">'+inv.roomtypeid+'</option>';
		rowcontact = rowcontact + sscontact ;
		ssplan = '<option value="'+inv.rateplanid+'">'+inv.rateplanid+'</option>';
		rowplan = rowplan + ssplan ;
	});
	
	ss = '<option value="1872700000000000001">1872700000000000001</option>';
	rowroom = rowroom + ss ;
	rowrate = rowrate + ss ;
	rowcontact = rowcontact + ss ;
	rowplan = rowplan + ss ;
	
	$("#checkavailabilityroomtypeid").append(rowrate);
	$("#roomsalesdataroomid").append(rowroom);
	$("#reservedroomscalendarroomid").append(rowroom);
	$("#createabookingrateplanid").append(rowplan);
	$("#createabookingratetypeid").append(rowrate);
	$("#createabookingroomtypeid").append(rowroom);
	$("#roomassignmentroomtypeid").append(rowroom);
	
}


function rateroomnamedropdown(response){
	
	$("#reservedroomscalendarroomcode").empty();
	$("#roomassignmentroomid").empty();
	
	var rowroomname = '';
	var ssroomname = '';
	
	var rowroomid = '';
	var ssroomid = '';

	$.each(response,function(key,inv){
		ssroomname = '<option value="'+inv.roomname+'">'+inv.roomname+'</option>';
		rowroomname = rowroomname + ssroomname ;
		
		ssroomid = '<option value="'+inv.roomid+'">'+inv.roomid+'</option>';
		rowroomid = rowroomid + ssroomid ;

	});
	ss = '<option value="1872700000000000001">1872700000000000001</option>';
	rowroomname = rowroomname + ss ;
	rowroomid = rowroomid + ss ;
	
	$("#reservedroomscalendarroomcode").append(rowroomname);
	$("#roomassignmentroomid").append(rowroomid);

}






$("#btn-checkavailability").on('click',function(){
	getCheckAvailability();
});


function getCheckAvailability(){
	
	  $('#imgloader').show();
	$.ajax({
		url: '/pms/otabooking/otacheckavailability',
		type: 'POST',
		data: {
			checkindate 	:  $(".checkavailabilitycheckindate").val(),
			checkoutdate	:  $(".checkavailabilitycheckoutdate").val(),
			nights			:  "", //  $(".checkavailabilitynights").val(),
			adults			:  $(".checkavailabilityadults").val(),
			child			:  $(".checkavailabilitychild").val(),
			rooms			:  $(".checkavailabilityrooms").val(),
			roomtypeid		:  $(".checkavailabilityroomtypeid").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   var msg = result[0].minavarooms;
				   $("#checkavailabilitysuccess").empty();
				   $("#checkavailabilitysuccess").append(msg);
				   $(".checkavailabilitydivlist").show();
		   		}else{
		   			getCheckAvailabilitySuccess(result);
		   		}
		   $('#imgloader').hide();
		   $("#successmsgcheckavailability").show();
		   setInterval(function () {
			     $("#successmsgcheckavailability").hide();
				}, 5000);
		},
		error: function() {
			 $('#imgloader').hide();
			 $("#errormsgcheckavailability").show();
			 setInterval(function () {
				   $("#errormsgcheckavailability").hide();
					}, 5000);
		}
		});
	}



function getCheckAvailabilitySuccess(response){
	
	$(".checkavailabilitydivlist").show();
	$(".checkavailabilitybody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.roomname+''+'</td>'
		+'<td class="tdwidth">'+inv.roomtypeid+''+'</td>'
		+'<td class="tdwidth">'+inv.rackrate+''+'</td>'
		+'<td class="tdwidth">'+inv.totalpriceroomonly+''+'</td>'
		+'<td class="tdwidth">'+inv.totalpriceinclusiveall+''+'</td>'
		+'<td class="tdwidth">'+inv.avgpernightafterdiscount+''+'</td>'
		+'<td class="tdwidth">'+inv.avgpernightwithouttax+''+'</td>'
		+'<td class="tdwidth">'+inv.minavarooms+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	
	$(".checkavailabilitybody").append(row);
}





allbookingsnav(y);

$("#btnrefresh").on('click',function(){
	getOtaReservationList();
})

$("#btn-reservation").on('click',function(){
	y = 0;
	allbookingsnav(y);
})
$("#btn-rental").on('click',function(){
	y = 1;
	allbookingsnav(y);
})
$("#btn-booktrans").on('click',function(){
	y = 2;
	allbookingsnav(y);
})
$("#btn-taxdetil").on('click',function(){
	y = 3;
	allbookingsnav(y);
})
$("#btn-cancelreservation").on('click',function(){
	y = 4;
	allbookingsnav(y);
})





function allbookingsnav(y){
switch (y) {
case 0:
	$(".reservelist").show();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	
	break;
case 1:
	$(".reservelist").hide();
	$(".rentallist").show();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	
	break;
case 2:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").show();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").hide();
	
	break;
case 3:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").show();
	$(".cancelreservelist").hide();
	
	break;
case 4:
	$(".reservelist").hide();
	$(".rentallist").hide();
	$(".booktranslist").hide();
	$(".taxdeatillist").hide();
	$(".cancelreservelist").show();

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
			 $('#imgloader').hide();
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
			 $('#imgloader').hide();
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



	function bookingId(){
	
	$.ajax({
		url: '/pms/otadata/bookingid',
		type: 'GET',
		success: function (result) {
			   bookingiddropdown(result.otareservation,result.otacancelreservation);
			   pmsbookingiddropdown(result.otabookingtrans,result.otacancelreservation);
			},
			error: function(error) {
				
			}
		    });
	}
	
	
	function bookingiddropdown(response1,response2){
		
		$("#retrievesinglebookingbookingId").empty();
		$("#retrievearrivalsbookingId").empty();
		$("#retrievedeparturesbookingId").empty();
		$("#bookingid").empty();
		$("#reservationdetailsofaroomreseid").empty();
		$("#postcreatebookingsactionsbookingId").empty();
		$("#readabookingreservationnumber").empty();
		$("#cancelabookingreservationnumber").empty();
		$("#guestdataupdatereservationid").empty();
		$("#addpaymentreservationid").empty();
		$("#addguestprofiletobookingsreservationid").empty();
		$("#guestcheckinreservationid").empty();
		$("#roomassignmentreservationid").empty();
		$("#guestcheckoutreservationid").empty();
		$("#retrievelistofbillsreservationid").empty();
		$("#retrievetransactiondetailsreservationid").empty();
		
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
		ss = '<option value="820">820</option>';
		row = row + ss ;
		$("#bookingid").append(row);
		$("#retrievesinglebookingbookingId").append(row);
		$("#retrievearrivalsbookingId").append(row);
		$("#retrievedeparturesbookingId").append(row);
		$("#reservationdetailsofaroomreseid").append(row);
		$("#postcreatebookingsactionsbookingId").append(row);
		$("#readabookingreservationnumber").append(row);
		$("#cancelabookingreservationnumber").append(row);
		$("#guestdataupdatereservationid").append(row);
		$("#addpaymentreservationid").append(row);
		$("#addguestprofiletobookingsreservationid").append(row);
		$("#guestcheckinreservationid").append(row);
		$("#roomassignmentreservationid").append(row);
		$("#guestcheckoutreservationid").append(row);
		$("#retrievelistofbillsreservationid").append(row);
		$("#retrievetransactiondetailsreservationid").append(row);
		
		
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
		ss = '<option value="820">820</option>';
		row = row + ss ;
		$("#pmsbookingid").append(row);
		
	}



$("#btn-singlereserve").on('click',function(){
	getSingleBooking();
})


function getSingleBooking(){
	
	$.ajax({
		url: '/pms/otabooking/otaretrievesinglebooking',
		type: 'POST',
		data: {
			bookingid  	: $("#retrievesinglebookingbookingId").val(), 	         
		},
		success: function (result) {
			   $('#imgloader').hide();
			   setInterval(function () {
				   $("#successmsg").hide();
					}, 5000);
			   setInterval(function () {
				   $("#errormsg").hide();
					}, 5000);
			   getotasinglereservationSuccess(result);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsg").show();
				 setInterval(function () {
					   $("#errormsg").hide();
						}, 5000);
			}
		    });
	}


function getotasinglereservationSuccess(response){
	
	$(".singlereservationbody").empty();
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
	$(".singlereservationbody").append(row);
	$(".singlereservelist").show();
	
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
			if(result === "1 booking(s) updated"){
				 $("#successmsg").show();
			}else{
				 $("#errormsg").show();
			} 
			   $('#imgloader').hide();
			   setInterval(function () {
				   $("#successmsg").hide();
					}, 5000);
			   setInterval(function () {
				   $("#errormsg").hide();
					}, 5000);
			},
			error: function(error) {
				 $('#imgloader').hide();
				 $("#errormsg").show();
				 setInterval(function () {
					   $("#errormsg").hide();
						}, 5000);
			}
		    });
	}




$("#btn-retrievearrivals").on('click',function(){
	getRetrieveArrivals();
})

function getRetrieveArrivals(){
	
	$.ajax({
		url: '/pms/otabooking/otaretrievearrivallist',
		type: 'POST',
		data: {
			bookingid  	: $("#retrievearrivalsbookingId").val(), 	       
			fromdate    : $("#retrievearrivalsfromdate").val(),
			todate      : $("#retrievearrivalstodate").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   $("#retrievearrivalssuccess").empty();
				   $("#retrievearrivalssuccess").append(result[0].comment);
			   }else{
				   getRetrieveArrivalsSuccess(result);
			   }
			   $("#successmsgretrievearrivals").show();
			   setInterval(function () {
				   $("#successmsgretrievearrivals").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgretrievearrivals").show();
				 setInterval(function () {
					   $("#errormsgretrievearrivals").hide();
						}, 5000);
			}
		    });
	}


function getRetrieveArrivalsSuccess(response){
	
	$(".retrievearrivalsbooktranslist").show();
	$(".retrievearrivalsbooktransbody").empty();
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
	$(".retrievearrivalsbooktransbody").append(row);
}



$("#btn-retrievedepartures").on('click',function(){
	getRetrieveDepartures();
})

function getRetrieveDepartures(){
	
	$.ajax({
		url: '/pms/otabooking/otaretrievedeparturelist',
		type: 'POST',
		data: {
			bookingid  	: $("#retrievedeparturesbookingId").val(), 	   
			fromdate    : $("#retrievedeparturesfromdate").val(),
			todate      : $("#retrievedeparturestodate").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   $("#retrievedeparturesssuccess").empty();
				   $("#retrievedeparturesssuccess").append(result[0].comment);
			   }else{
				   getRetrieveDeparturesSuccess(result);
			   }
			   $("#successmsgretrievedepartures").show();
			   setInterval(function () {
				   $("#successmsgretrievedepartures").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgretrievedepartures").show();
				 setInterval(function () {
					   $("#errormsgretrievedepartures").hide();
						}, 5000);
			}
		    });
	}



function getRetrieveDeparturesSuccess(response){
	
	$(".retrievedeparturesbooktranslist").show();
	$(".retrievedeparturesbooktransbody").empty();
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
	$(".retrievedeparturesbooktransbody").append(row);
}



$("#btn-postchargeto").on('click',function(){
	getChargePost();
})

function getChargePost(){
	
	$.ajax({
		url: '/pms/otabooking/otachargepost',
		type: 'POST',
		data: {
			room			: $(".postchargetoroom").val(),
			folio			: $(".postchargetofolio").val(), 
			table			: $(".postchargetotable").val(),
			outlet			: $(".postchargetooutlet").val(),
			charge      	: $(".postchargetocharge").val(), 
			postingdate 	: $(".postchargetoroompostingdate").val(),
			trandate    	: $(".postchargetoroomtrandate").val(),
			amount      	: $(".postchargetoroomamount").val(),
			tax         	: $(".postchargetoroomtax").val(),
			gross_amount 	: $(".postchargetogrossamount").val(),
			voucherno       : $(".postchargetovoucherno").val(),
			posuser         : $(".postchargetoposuser").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   $("#postchargetoroomsuccess").empty();
			   $("#postchargetoroomsuccess").append(result);
			   $("#successmsgpostchargetoroom").show();
			   setInterval(function () {
				   $("#successmsgpostchargetoroom").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgpostchargetoroom").show();
				 setInterval(function () {
					   $("#errormsgpostchargetoroom").hide();
						}, 5000);
			}
		    });
	}



$("#btn-voidchargeonroom").on('click',function(){
	getChargeOnRoom();
})

function getChargeOnRoom(){
	
	$.ajax({
		url: '/pms/otabooking/otachargeonroom',
		type: 'POST',
		data: {
			requestid	: $(".voidchargeonroomrequestid").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   $("#voidchargeonroomsuccess").empty();
			   $("#voidchargeonroomsuccess").append(result);
			   $("#successmsgvoidchargeonroom").show();
			   setInterval(function () {
				   $("#successmsgvoidchargeonroom").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgvoidchargeonroom").show();
				 setInterval(function () {
					   $("#errormsgvoidchargeonroom").hide();
						}, 5000);
			}
		    });
	}




$("#btn-updateposreceiptno").on('click',function(){
	getPosreceiptno();
})

function getPosreceiptno(){
	
	$.ajax({
		url: '/pms/otabooking/otaposreceiptno',
		type: 'POST',
		data: {
			requestid	: $(".updateposreceiptnorequestid").val(),
			voucherno	: $(".updateposreceiptnovoucherno").val()
		},
		success: function (result) {
			   $('#imgloader').hide();
			   $("#updateposreceiptnosuccess").empty();
			   $("#updateposreceiptnosuccess").append(result);
			   $("#successmsgupdateposreceiptno").show();
			   setInterval(function () {
				   $("#successmsgupdateposreceiptno").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgupdateposreceiptno").show();
				 setInterval(function () {
					   $("#errormsgupdateposreceiptno").hide();
						}, 5000);
			}
		    });
	}



function getRetrieveposttoroominformation(){
	
	$.ajax({
		url: '/pms/otabooking/otaretrieveposttoroominformation',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   $("#retrieveposttoroominformationsuccess").empty();
				   $("#retrieveposttoroominformationsuccess").append(result[0].remarks);
				   $(".retrieveposttoroominformationdivlist").show();
			   }else{
				   getRetrieveposttoroominformationSuccess(result);
			   }
			   $("#successmsgretrieveposttoroominformation").show();
			   setInterval(function () {
				   $("#successmsgretrieveposttoroominformation").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgretrieveposttoroominformation").show();
				 setInterval(function () {
					   $("#errormsgretrieveposttoroominformation").hide();
						}, 5000);
			}
		    });
	}



function getRetrieveposttoroominformationSuccess(response){
	
	$(".retrieveposttoroominformationdivlist").show();
	$(".retrieveposttoroominformationbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.guestname+''+'</td>'
		+'<td class="tdwidth">'+inv.arrival+'</td>'
		+'<td class="tdwidth">'+inv.departure+''+'</td>'
		+'<td class="tdwidth">'+inv.masterfolio+''+'</td>'
		+'<td class="tdwidth">'+inv.room+''+'</td>'
		+'<td class="tdwidth">'+inv.roomtype+''+'</td>'
		+'<td class="tdwidth">'+inv.ratetype+''+'</td>'
		+'<td class="tdwidth">'+inv.remarks+''+'</td>'
		+'<td class="tdwidth">'+inv.resno+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".retrieveposttoroominformationbody").append(row);
}


$("#btn-retrieveposttoroominformationspecific").on('click',function (){
	getRetrieveposttoroominformationSpecific();
})


function getRetrieveposttoroominformationSpecific(){
	
	$.ajax({
		url: '/pms/otabooking/otaretrieveposttoroominformationspecific',
		type: 'POST',
		data: {
			room : $(".retrieveposttoroominformationspecificroom").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   $("#retrieveposttoroominformationspecificsuccess").empty();
				   $("#retrieveposttoroominformationspecificsuccess").append(result[0].remarks);
				   $(".retrieveposttoroominformationspecificdivlist").show();
			   }else{
				   getRetrieveposttoroominformationSuccess(result);
			   }
			   $("#successmsgretrieveposttoroominformationspecific").show();
			   setInterval(function () {
				   $("#successmsgretrieveposttoroominformationspecific").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgretrieveposttoroominformationspecific").show();
				 setInterval(function () {
					   $("#errormsgretrieveposttoroominformationspecific").hide();
						}, 5000);
			}
		    });
	}



function getRetrieveposttoroominformationSpecificSuccess(response){
	
	$(".retrieveposttoroominformationspecificdivlist").show();
	$(".retrieveposttoroominformationspecificbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.guestname+''+'</td>'
		+'<td class="tdwidth">'+inv.arrival+'</td>'
		+'<td class="tdwidth">'+inv.departure+''+'</td>'
		+'<td class="tdwidth">'+inv.masterfolio+''+'</td>'
		+'<td class="tdwidth">'+inv.room+''+'</td>'
		+'<td class="tdwidth">'+inv.roomtype+''+'</td>'
		+'<td class="tdwidth">'+inv.ratetype+''+'</td>'
		+'<td class="tdwidth">'+inv.remarks+''+'</td>'
		+'<td class="tdwidth">'+inv.resno+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".retrieveposttoroominformationspecificbody").append(row);
}



$("#btn-roomsalesdata").on('click',function (){
	getRoomSalesData();
})

function getRoomSalesData(){
	
	$.ajax({
		url: '/pms/otabooking/otaroomsalesdata',
		type: 'POST',
		data: {
			roomid 		: $(".roomsalesdataroomid").val(),
			fromdate 	: $(".roomsalesdatafromdate").val(),
			todate 		: $(".roomsalesdataroomtodate").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
				   $("#roomsalesdatasuccess").empty();
				   $("#roomsalesdatasuccess").append(result);
			   $("#successmsgroomsalesdata").show();
			   setInterval(function () {
				   $("#successmsgroomsalesdata").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgroomsalesdata").show();
				 setInterval(function () {
					   $("#errormsgroomsalesdata").hide();
						}, 5000);
			}
		    });
	}


function getRoomSalesDataSuccess(result){
	
	$(".roomsalesdatadivlist").show();
	$(".roomsalesdatabody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.room_nights+''+'</td>'
		+'<td class="tdwidth">'+inv.room_sold+'</td>'
		+'<td class="tdwidth">'+inv.adr+''+'</td>'
		+'<td class="tdwidth">'+inv.room_charges+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".roomsalesdatabody").append(row);
	
}



$("#btn-reservedroomscalendar").on('click',function (){
	getReservedroomscalendar();
})

function getReservedroomscalendar(){
	
	$.ajax({
		url: '/pms/otabooking/otareservedroomscalendar',
		type: 'POST',
		data: {
			roomid 		: $(".reservedroomscalendarroomid").val(),
			roomcode 	: $(".reservedroomscalendarroomcode").val(),
			fromdate 	: $(".reservedroomscalendarfromdate").val(),
			todate 		: $(".reservedroomscalendartodate").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   $("#reservedroomscalendarsuccess").empty();
				   $("#reservedroomscalendarsuccess").append(result[0].status);
			   }else{
				   getReservedroomscalendarSuccess(result);
			   }
			   $("#successmsgreservedroomscalendar").show();
			   setInterval(function () {
				   $("#successmsgreservedroomscalendar").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgreservedroomscalendar").show();
				 setInterval(function () {
					   $("#errormsgreservedroomscalendar").hide();
						}, 5000);
			}
		    });
	}


function getReservedroomscalendarSuccess(response){
	
	$(".reservedroomscalendardivlist").show();
	$(".reservedroomscalendarbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.room_name+''+'</td>'
		+'<td class="tdwidth">'+inv.room_code+'</td>'
		+'<td class="tdwidth">'+inv.date+''+'</td>'
		+'<td class="tdwidth">'+inv.status+''+'</td>'
		+'<td class="tdwidth">'+inv.reservation_id+''+'</td>'
		+'<td class="tdwidth">'+inv.guest_name+'</td>'
		+'<td class="tdwidth">'+inv.check_in+''+'</td>'
		+'<td class="tdwidth">'+inv.check_out+''+'</td>'
		+'<td class="tdwidth">'+inv.booking_status+''+'</td>'
		+'<td class="tdwidth">'+inv.total_amount+'</td>'
		+'<td class="tdwidth">'+inv.payment_type+''+'</td>'
		+'<td class="tdwidth">'+inv.creation_date+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".reservedroomscalendarbody").append(row);
	
}




function getRetrievephysicalrooms(){
	
	$.ajax({
		url: '/pms/otabooking/otaretrievephysicalrooms',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   $("#retrievephysicalroomssuccess").empty();
				   $("#retrievephysicalroomssuccess").append(result[0].status);
			   }else{
				   getRetrievephysicalroomsSuccess(result);
			   }
			   $("#successmsgretrievephysicalrooms").show();
			   setInterval(function () {
				   $("#successmsgretrievephysicalrooms").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgretrievephysicalrooms").show();
				 setInterval(function () {
					   $("#errormsgretrievephysicalrooms").hide();
						}, 5000);
			}
		    });
	}


function getRetrievephysicalroomsSuccess(response){
	
	$(".retrievephysicalroomsdivlist").show();
	$(".retrievephysicalroomsbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.roomid+''+'</td>'
		+'<td class="tdwidth">'+inv.room_name+'</td>'
		+'<td class="tdwidth">'+inv.room_code+'</td>'
		+'<td class="tdwidth">'+inv.rooms+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".retrievephysicalroomsbody").append(row);
	
}



function getCheckincheckout(){
	
	$.ajax({
		url: '/pms/otabooking/otacheckincheckout',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				   $("#todayscheckincheckoutsuccess").empty();
				   $("#todayscheckincheckoutsuccess").append(result[0].status);
			   }else{
				   getCheckincheckoutSuccess(result);
			   }
			   $("#successmsgtodayscheckincheckout").show();
			   setInterval(function () {
				   $("#successmsgtodayscheckincheckout").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgtodayscheckincheckout").show();
				 setInterval(function () {
					   $("#errormsgtodayscheckincheckout").hide();
						}, 5000);
			}
		    });
	}


function getCheckincheckoutSuccess(response){
	
	$(".todayscheckincheckoutdivlist").show();
	$(".todayscheckincheckoutbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.reservation_id+''+'</td>'
		+'<td class="tdwidth">'+inv.room_code+'</td>'
		+'<td class="tdwidth">'+inv.departuredate+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".todayscheckincheckoutbody").append(row);
	
}


$("#btn-reservationdetailsofaroom").on('click',function (){
	getReservationdetailsofaroom();
})


function getReservationdetailsofaroom(){
	
	$.ajax({
		url: '/pms/otabooking/otareservationdetailsofaroom',
		type: 'POST',
		data: {
			reservation_id :   $(".reservationdetailsofaroomreseid").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   console.log("result"+result);
			   $(".reservationdetailsofaroomdivlist").show();
			   if(result[0].id < 0){
				   $("#todayscheckincheckoutsuccess").empty();
				   $("#todayscheckincheckoutsuccess").append(result[0].status);
				   $(".reservationdetailsofaroomdivlist").hide();
			   }else{
				   getReservationdetailsofaroomSuccess(result);
			   }
			   $("#successmsgreservationdetailsofaroom").show();
			   setInterval(function () {
				   $("#successmsgreservationdetailsofaroom").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgreservationdetailsofaroom").show();
				 setInterval(function () {
					   $("#errormsgreservationdetailsofaroom").hide();
						}, 5000);
			}
		    });
	}


function getReservationdetailsofaroomSuccess(response){
	
	$(".reservationdetailsofaroomdivlist").show();
	$(".reservationdetailsofaroombody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.roomid+''+'</td>'
		+'<td class="tdwidth">'+inv.room_name+'</td>'
		+'<td class="tdwidth">'+inv.reservation_id+'</td>'
		+'<td class="tdwidth">'+inv.booking_status+''+'</td>'
		+'<td class="tdwidth">'+inv.guest_name+''+'</td>'
		+'<td class="tdwidth">'+inv.check_in+'</td>'
		+'<td class="tdwidth">'+inv.check_out+'</td>'
		+'<td class="tdwidth">'+inv.total_amount+''+'</td>'
		+'<td class="tdwidth">'+inv.channel+''+'</td>'
		+'<td class="tdwidth">'+inv.payment_type+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".reservationdetailsofaroombody").append(row);
	
}


$("#btn-pullhistoricalbookings").on('click',function (){
	getPullhistoricalbookings();
})


function getPullhistoricalbookings(){
	
	$.ajax({
		url: '/pms/otabooking/otapullhistoricalbookings',
		type: 'POST',
		data: {
			fromdate :   $(".pullhistoricalbookingsfromdate").val(),
			todate   :   $(".pullhistoricalbookingstodate").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   console.log("result"+result);
			   $(".pullhistoricalbookingsdivlist").show();
			   if(result[0].id < 0){
				   $("#pullhistoricalbookingssuccess").empty();
				   $("#pullhistoricalbookingssuccess").append(result[0].source);
				   $(".pullhistoricalbookingsdivlist").hide();
			   }else{
				   getPullhistoricalbookingsSuccess(result);
			   }
			   $("#successmsgpullhistoricalbookings").show();
			   setInterval(function () {
				   $("#successmsgpullhistoricalbookings").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgpullhistoricalbookings").show();
				 setInterval(function () {
					   $("#errormsgpullhistoricalbookings").hide();
						}, 5000);
			}
		    });
	}


function getPullhistoricalbookingsSuccess(response){
	
	$(".pullhistoricalbookingsdivlist").show();
	$(".pullhistoricalbookingsbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
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
	$(".pullhistoricalbookingsbody").append(row);
	
}



$("#btn-createabooking").on('click',function (){
	getCreateabooking();
})


function getCreateabooking(){
	
	$.ajax({
		url: '/pms/otabooking/otacreateabooking',
		type: 'POST',
		data: {
			rateplanid      	:   $(".createabookingrateplanid").val(),
			ratetypeid      	:   $(".createabookingratetypeid").val(), 
			roomtypeid     		:   $(".createabookingroomtypeid").val(),
			baserate        	:   $(".createabookingbaserate").val(), 
			extradultrate   	:   $(".createabookingextradultrate").val(),
			extrachildrate      :   $(".createabookingextrachildrate").val(),
			numberadults        :   $(".createabookingnumberadults").val(),
			numberchildren      :   $(".createabookingnumberchildren").val(), 
			extrachildage       :   $(".createabookingextrachildage").val(),
			title     			:   $(".createabookingtitle").val(), 
			firstname      		:   $(".createabookingfirstname").val(),
			lastname       		:   $(".createabookinglastname").val(),
			gender      		:   $(".createabookinggender").val(),
			specialrequest      :   $(".createabookingspecialrequest").val(),
			checkindate         :   $(".createabookingcheckindate").val(),
			checkoutdate        :   $(".createabookingcheckoutdate").val(),
			bookingpaymentmode  :   $(".createabookingbookingpaymentmode").val(),
			emailaddress        :   $(".createabookingemailaddress").val(),
			sourceid            :   $(".createabookingsourceid").val(),
			mobileno            :   $(".createabookingmobileno").val(), 
			address             :   $(".createabookingaddress").val(), 
			state               :   $(".createabookingstate").val(),  
			country             :   $(".createabookingcountry").val(),
		    city                :   $(".createabookingcity").val(),
		    zipcode             :   $(".createabookingzipcode").val(),
		    fax                 :   $(".createabookingfax").val(),
		    device              :   $(".createabookingdevice").val(),
		    languagekey         :   $(".createabookinglanguagekey").val(), 
		    paymenttypeunkid    :   $(".createabookingpaymenttypeunkid").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
				   $("#createabookingsuccess").empty();
				   $("#createabookingsuccess").append(result);
			   $("#successmsgcreateabooking").show();
			   setInterval(function () {
				   $("#successmsgcreateabooking").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgcreateabooking").show();
				 setInterval(function () {
					   $("#errormsgcreateabooking").hide();
						}, 5000);
			}
		    });
	}




function getPostcreatebookingsactions(){
	
	$.ajax({
		url: '/pms/otabooking/otapostcreatebookingsactions',
		type: 'GET',
		success: function (result) {
			   $('#imgloader').hide();
				$("#postcreatebookingsactionssuccess").empty();
				$("#postcreatebookingsactionssuccess").append(result);
			   $("#successmsgpostcreatebookingsactions").show();
			   setInterval(function () {
				   $("#successmsgpostcreatebookingsactions").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgpostcreatebookingsactions").show();
				 setInterval(function () {
					   $("#errormsgpostcreatebookingsactions").hide();
						}, 5000);
			}
		    });
	}



$("#btn-retrieveabookingbasedonparameters").on('click',function (){
	getRetrieveabookingbasedonparameters();
})



function getRetrieveabookingbasedonparameters(){
	
	$.ajax({
		url  : '/pms/otabooking/otaretrieveabookingbasedonparameters',
		type : 'POST',
		data : {
			fromdate    : $("#retrieveabookingbasedonparametersfromdate").val(),
			todate		: $("#retrieveabookingbasedonparameterstodate").val(),
			email       : $("#retrieveabookingbasedonparametersemail").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			    $(".totalactiveroominhotel").empty();
				$(".totalblockrooms").empty();
				$(".totaloccupiedrooms").empty();
				$(".totalactiveroominhotel").append(result[0].totalactiveroominhotel);
				$(".totalblockrooms").append(result[0].totalblockrooms);
				$(".totaloccupiedrooms").append(result[0].totaloccupiedrooms);
			   if(result[0].id < 0){
					$("#retrieveabookingbasedonparameterssuccess").empty();
					$("#retrieveabookingbasedonparameterssuccess").append(result[0].comment);
			   }else{
				   result.splice(0,1);
				   getRetrieveabookingbasedonparametersSuccess(result);
			   }
			   $("#successmsgretrieveabookingbasedonparameters").show();
			   setInterval(function () {
				   $("#successmsgretrieveabookingbasedonparameters").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgretrieveabookingbasedonparameters").show();
				 setInterval(function () {
					   $("#errormsgretrieveabookingbasedonparameters").hide();
				  }, 5000);
			}
		    });
	}



function getRetrieveabookingbasedonparametersSuccess(response){
	
	$(".retrieveabookingbasedonparametersbooktransdivlist").show();
	$(".retrieveabookingbasedonparametersbooktransbody").empty();
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
		+'<td class="tdwidth">'+inv.reservationdate+''+'</td>'
		+'<td class="tdwidth">'+inv.noofguest+''+'</td>'
		+'<td class="tdwidth">'+inv.noofnights+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".retrieveabookingbasedonparametersbooktransbody").append(row);
	
}


$("#btn-readabooking").on('click',function (){
	getReadabooking();
})

function getReadabooking(){
	
	$.ajax({
		url  : '/pms/otabooking/otareadabooking',
		type : 'POST',
		data : {
			reservationnumber  :  $("#readabookingreservationnumber").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
					$("#readabookingsuccess").empty();
					$("#readabookingsuccess").append(result[0].comment);
			   }else{
				   getReadabookingSuccess(result);
			   }
			   $("#successmsgreadabooking").show();
			   setInterval(function () {
				   $("#successmsgreadabooking").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgreadabooking").show();
				 setInterval(function () {
					   $("#errormsgreadabooking").hide();
				  }, 5000);
			}
		    });
	}


function getReadabookingSuccess(response){
	
	$(".readabookingdivlist").show();
	$(".readabookingbody").empty();
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
		+'<td class="tdwidth">'+inv.email+''+'</td>'
		+'<td class="tdwidth">'+inv.phone+''+'</td>'
		+'<td class="tdwidth">'+inv.currentstatus+''+'</td>'
		+'<td class="tdwidth">'+inv.noofnights+''+'</td>'
		+'<td class="tdwidth">'+inv.affiliatecode+''+'</td>'
		+'<td class="tdwidth">'+inv.country+''+'</td>'
		+'</tr>'
	
		row = row + ss ;
	});
	$(".readabookingbody").append(row);
	
}


$("#btn-cancelabooking").on('click',function (){
	getCancelabooking();
})

function getCancelabooking(){
	
	$.ajax({
		url  : '/pms/otabooking/otacancelabooking',
		type : 'POST',
		data : {
			reservationnumber  :  $("#cancelabookingreservationnumber").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
				$("#cancelabookingsuccess").empty();
				$("#cancelabookingsuccess").append(result);
			   $("#successmsgcancelabooking").show();
			   setInterval(function () {
				   $("#successmsgcancelabooking").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgcancelabooking").show();
				 setInterval(function () {
					   $("#errormsgcancelabooking").hide();
				  }, 5000);
			}
		    });
	}



function getAutosync(){
	
	$.ajax({
		url  : '/pms/otabooking/otaautosync',
		type : 'POST',
		data : {
			reservationnumber  :  "",
		},
		success: function (result) {
			   $('#imgloader').hide();
				$("#autosyncfuturebookingsanditsmodificationssuccess").empty();
				$("#autosyncfuturebookingsanditsmodificationssuccess").append(result);
			   $("#successmsgautosyncfuturebookingsanditsmodifications").show();
			   setInterval(function () {
				   $("#successmsgautosyncfuturebookingsanditsmodifications").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgautosyncfuturebookingsanditsmodifications").show();
				 setInterval(function () {
					   $("#errormsgautosyncfuturebookingsanditsmodifications").hide();
				  }, 5000);
			}
		    });
	}




$("#btn-guestdataupdate").on('click',function (){
	getGuestdataupdate();
})

function getGuestdataupdate(){
	
	$.ajax({
		url  : '/pms/otabooking/otaguestdataupdate',
		type : 'POST',
		data : {
			reservationid   :  $("#guestdataupdatereservationid").val(),
			firstname  		:  $("#guestdataupdatefirstname").val(),
			lastname  		:  $("#guestdataupdatelastname").val(),
			email  			:  $("#guestdataupdateemail").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
				$("#guestdataupdatesuccess").empty();
				$("#guestdataupdatesuccess").append(result);
			   $("#successmsgguestdataupdate").show();
			   setInterval(function () {
				   $("#successmsgguestdataupdate").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgguestdataupdate").show();
				 setInterval(function () {
					   $("#errormsgguestdataupdate").hide();
				  }, 5000);
			}
		    });
	}





$("#btn-addpayment").on('click',function (){
	getAddPayment();
})

function getAddPayment(){
	
	$.ajax({
		url  : '/pms/otabooking/otaaddpayment',
		type : 'POST',
		data : {
			reservationid  :  $("#addpaymentreservationid").val(),
			paymentid      :  $("#addpaymentpaymentid").val(),
			currencyid     :  $("#addpaymentcurrencyid").val(),
			payment        :  $("#addpaymentpayment").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
				$("#addpaymentsuccess").empty();
				$("#addpaymentsuccess").append(result);
			   $("#successmsgaddpayment").show();
			   setInterval(function () {
				   $("#successmsgaddpayment").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgaddpayment").show();
				 setInterval(function () {
					   $("#errormsgaddpayment").hide();
				  }, 5000);
			}
		    });
	}




$("#btn-addguestprofiletobookings").on('click',function (){
	getAddguestprofiletobookings();
})

function getAddguestprofiletobookings(){
	
	$.ajax({
		url  : '/pms/otabooking/otaguestprofile',
		type : 'POST',
		data : {
			reservationid  	:  $("#addguestprofiletobookingsreservationid").val(),
			firstname      	:  $("#addguestprofiletobookingsfirstname").val(),
			lastname     	:  $("#addguestprofiletobookingslastname").val(),
			gender        	:  $("#addguestprofiletobookingspaymentgender").val(),
			type       		:  $("#addguestprofiletobookingspaymenttype").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
				$("#addguestprofiletobookingssuccess").empty();
				$("#addguestprofiletobookingssuccess").append(result);
			   $("#successmsgaddguestprofiletobookings").show();
			   setInterval(function () {
				   $("#successmsgaddguestprofiletobookings").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgaddguestprofiletobookings").show();
				 setInterval(function () {
					   $("#errormsgaddguestprofiletobookings").hide();
				  }, 5000);
			}
		    });
	}





$("#btn-guestcheckin").on('click',function (){
	guestCheckin();
})

function guestCheckin(){
	
	$.ajax({
		url  : '/pms/otabooking/otaguestcheckIn',
		type : 'POST',
		data : {
			reservationid  	:  $("#guestcheckinreservationid").val(),
			guestname      	:  $("#guestcheckinguestname").val(),
			email     		:  $("#guestcheckinemail").val(),
			address        	:  $("#guestcheckinaddress").val(),
			phone       	:  $("#guestcheckinphone").val(),
			mobile 			:  $("#guestcheckinmobile").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
				$("#guestcheckinsuccess").empty();
				$("#guestcheckinsuccess").append(result);
			   $("#successmsgguestcheckin").show();
			   setInterval(function () {
				   $("#successmsgguestcheckin").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgguestcheckin").show();
				 setInterval(function () {
					   $("#errormsgguestcheckin").hide();
				  }, 5000);
			}
		    });
	}



$("#btn-roomassignment").on('click',function (){
	roomassignment();
})

function roomassignment(){
	
	$.ajax({
		url  : '/pms/otabooking/otaroomassignment',
		type : 'POST',
		data : {
			reservationid  	:  $("#roomassignmentreservationid").val(),
			roomtypeid      :  $("#roomassignmentroomtypeid").val(),
			roomid     		:  $("#roomassignmentroomid").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
				$("#roomassignmentsuccess").empty();
				$("#roomassignmentsuccess").append(result);
			   $("#successmsgroomassignment").show();
			   setInterval(function () {
				   $("#successmsgroomassignment").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgroomassignment").show();
				 setInterval(function () {
					   $("#errormsgroomassignment").hide();
				  }, 5000);
			}
		    });
	}




$("#btn-guestcheckout").on('click',function (){
	guestcheckout();
})

function guestcheckout(){
	
	$.ajax({
		url  : '/pms/otabooking/otaguestcheckout',
		type : 'POST',
		data : {
			reservationid  	:  $("#guestcheckoutreservationid").val()
		},
		success: function (result) {
			   $('#imgloader').hide();
				$("#guestcheckoutsuccess").empty();
				$("#guestcheckoutsuccess").append(result);
			   $("#successmsgguestcheckout").show();
			   setInterval(function () {
				   $("#successmsgguestcheckout").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgguestcheckout").show();
				 setInterval(function () {
					   $("#errormsgguestcheckout").hide();
				  }, 5000);
			}
		    });
	}



$("#btn-retrievelistofbills").on('click',function (){
	retrievelistofbills();
})

function retrievelistofbills(){
	
	$.ajax({
		url  : '/pms/otabooking/otaretrievelistofbills',
		type : 'POST',
		data : {
			reservationid  	:  $("#retrievelistofbillsreservationid").val()
		},
		success: function (result) {
			   $('#imgloader').hide();
			   if(result[0].id < 0){
				$("#retrievelistofbillssuccess").empty();
				$("#retrievelistofbillssuccess").append(result[0].guestname);
			   }else{
				   retrievelistofbillsSuccess(result);
			   }
			   $("#successmsgretrievelistofbills").show();
			   setInterval(function () {
				   $("#successmsgretrievelistofbills").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgretrievelistofbills").show();
				 setInterval(function () {
					   $("#errormsgretrievelistofbills").hide();
				  }, 5000);
			}
		    });
	}


function retrievelistofbillsSuccess(response){
	$(".retrievelistofbillsdivlist").show();
	$(".retrievelistofbillsbody").empty();
	var row = '';
	var ss = '';
	var c = 0;
	$.each(response,function(key,inv){
		c = c + 1;
		ss = '<tr>'+
		+'<td class="tdwidth">'+c+'</td>'
		+'<td class="tdwidth">'+inv.id+''+'</td>'
		+'<td class="tdwidth">'+inv.foliono+''+'</td>'
		+'<td class="tdwidth">'+inv.billtocontact+'</td>'
		+'<td class="tdwidth">'+inv.guestname+''+'</td>'
		+'<td class="tdwidth">'+inv.currencycode+''+'</td>'
		+'<td class="tdwidth">'+inv.totalcharges+''+'</td>'
		+'<td class="tdwidth">'+inv.paidamount+''+'</td>'
		+'<td class="tdwidth">'+inv.dueamount+''+'</td>'
		+'</tr>'
		row = row + ss ;
	});
	$(".retrievelistofbillsbody").append(row);
}



$("#btn-retrievetransactiondetails").on('click',function (){
	retrievertransactiondetails();
})

function retrievertransactiondetails(){
	
	$.ajax({
		url  : '/pms/otabooking/otaretrievertransactiondetails',
		type : 'POST',
		data : {
			tranunkid  	:  $("#retrievetransactiondetailsreservationid").val(),
		},
		success: function (result) {
			   $('#imgloader').hide();
			   console.log(result);
			   if(result[0].id < 0){
				$("#retrievetransactiondetailssuccess").empty();
				$("#retrievetransactiondetailssuccess").append(result[0].comment);
			   }else{
				   retrievertransactiondetailsSuccess(result);
			   }
			   $("#successmsgretrievetransactiondetails").show();
			   setInterval(function () {
				   $("#successmsgretrievetransactiondetails").hide();
					}, 5000);
			},
			error: function(error) {
			   $('#imgloader').hide();
			   $("#errormsgretrievetransactiondetails").show();
				 setInterval(function () {
					   $("#errormsgretrievetransactiondetails").hide();
				  }, 5000);
			}
		    });
	}



function  retrievertransactiondetailsSuccess(response){
	
	$(".retrievetransactiondetailsdivbooktranslist").show();
	$(".retrievetransactiondetailsbooktransbody").empty();
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
	$(".retrievetransactiondetailsbooktransbody").append(row);
	
}


