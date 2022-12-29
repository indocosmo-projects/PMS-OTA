

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


getroominformationFromDB();
bookingId();


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
})

$("#btn-checkincheckoutlist").on('click',function(){
	x = 14;
	nav(x);
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
			},
			error: function(error) {

			}
		    });
	}



function rateroomtypesdropdown(response){
	
	$("#checkavailabilityroomtypeid").empty();
	$("#roomsalesdataroomid").empty();
	
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
	
	$("#checkavailabilityroomtypeid").append(rowrate);
	$("#roomsalesdataroomid").append(rowroom);
	
	
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
		$("#retrievesinglebookingbookingId").append(row);
		$("#retrievearrivalsbookingId").append(row);
		$("#retrievedeparturesbookingId").append(row);
		
		
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
			   console.log("result"+result);
			   if(result[0].id < 0){
				   $("#roomsalesdatasuccess").empty();
				   $("#roomsalesdatasuccess").append(result[0].remarks);
				   $(".roomsalesdatadivlist").show();
			   }else{
				   getRoomSalesDataSuccess(result);
			   }
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




