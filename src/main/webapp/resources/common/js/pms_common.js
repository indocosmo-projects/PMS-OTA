$(document).ready(function() {
    
	$('#logoutBox').hide();
	
	$('body').on('click', function(e){
	    if(e.target.id == 'logout'){
	    	$('#logoutBox').show();
	    }else{
	    	$('#logoutBox').hide();
	    }    
	    
	    
	    /**/
	});
    
	/* start section related with advanced search  */
	$('#search_form_down').click(function() {
		$('#search_fom_div').slideToggle('fast');
        $("#search_fom_div").css("display", "block");
        $('#search_fom_div input:visible:first').focus();
        $("#drpdwn").toggleClass('fa-angle-down fa-angle-up');
        
    });
	$('#search_form_cancel').click(function() {
    	
    	$('#search_fom_div').css('display', 'none');
    	$('#search_fom_div input[type=text]').val('');
    	$('#drpdwn').addClass('fa-angle-down').removeClass('fa-angle-up');
    });
	
	/*end section related with advanced search*/
		
    var x=window.location.pathname; 
    var tempPath=$("a[href='"+window.location.pathname+"']");  
    if($("#"+tempPath.attr('class')).hasClass('menu_top')){
    	sessionStorage.setItem("activeUrl",x);
    }else{
    	if(sessionStorage.getItem('activeUrl')!=null){
    	var tempPath=$("a[href='"+sessionStorage.getItem('activeUrl')+"']");
        }else{
        	sessionStorage.setItem("activeUrl","../reservation/reservationList");
        	var tempPath=$("a[href='"+sessionStorage.getItem('activeUrl')+"']");
        }
    	}
     
    $('#'+tempPath.attr('class')).addClass('drp_menu_active');
    
   
   
    if($('#'+tempPath.attr('class')).find('a').hasClass('reservation_menu')){
    	
    	$('#'+tempPath.attr('class')).find('a').addClass('reservation_menu_active');
    }else if($('#'+tempPath.attr('class')).find('a').hasClass('drop_menu_href')){
    	$('#'+tempPath.attr('class')).find('#setup_menu_top').addClass('reservation_menu_active');
    }
    else if($('#'+tempPath.attr('class')).find('a').hasClass('reception_menu')){
    	$('#'+tempPath.attr('class')).find('a').addClass('reception_menu_active');
    }
     /* alert($('#'+tempPath.attr('class')).find('a').hasClass('setup_menu'));

    if($('#'+tempPath.attr('class')).find('a').hasClass('setup_menu')){
    	
    	$('#'+tempPath.attr('class')).find('a').addClass('setup_menu_active');
    }*/
 
    
/*    if($('#'+tempPath.attr('class')).hasClass("drp_menu_active")){
	
	   $('#'+tempPath.attr('class')).removeClass('reservation_menu');
   }
*/  
    
    $('#top_menu a').click(function() {
    	
    	clearSearchData();
    });
    
   // customScrollBar();// scroll bar
    
    /*panel slide start*/
    jQuery('.panel .tools .fa-chevron-down, .panel .tools .fa-chevron-up').click(function () {
        var el = jQuery(this).parents(".panel").children(".panel-body");
        if (jQuery(this).hasClass("fa-chevron-down")) {
            jQuery(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
            el.slideUp(200);
        } else {
            jQuery(this).addClass("fa-chevron-down").removeClass("fa-chevron-up");
            el.slideDown(200);
        }
    });
    
    /*panel slide end*/
    
    
}); 
      
      
/*function customScrollBar(){
	$(".sidebar").niceScroll({styler:"fb",cursorcolor:"#e8403f", cursorwidth: '3', cursorborderradius: '10px', background: '#404040', spacebarenabled:false, cursorborder: '',autohidemode: false});
    $("html").niceScroll({styler:"fb",cursorcolor:"#e8403f", cursorwidth: '10', cursorborderradius: '10px', background: '#404040', spacebarenabled:false,  cursorborder: '', zindex: '1000',autohidemode: false}); 
}
*/

      
  /*    
      
      
      var path,parent;
      
      
      
 
       if(tempPath.parents().is("h3")){
    	   path=tempPath;
    	   path.parent().css("background-color","#4D5865");
    	   parent=tempPath.parents().eq(1);
    	   //alert(parent.prop('nodeName'));
  	      }
       else{
    	   path=$("a[href^='"+y+"/']");
    	   parent=path.parents().eq(2);
    	   //alert(parent.prop('nodeName')+"parent");
          }
      
      path.addClass("active").attr("href","#");
     menuUpDown(parent,path);

$("#accordian a").click(function(){
	$("#accordian a").removeClass("active");
	$("#accordian h3").css("background-color", "#2a3542");
	$(this).addClass("active");	
}); 
$("#accordian h3").click(function(){
	$("#accordian a").removeClass("active");
	$("#accordian h3").css("background-color", "#2a3542");
	$(this).css("background-color", "#4D5865");
	window.location.href=$(this).children().attr("href");
	if(parent.attr("id")==$(this).parent().attr("id")){
	menuUpDown($(this).parent());
	}
	else{
	window.location.href=$(this).children().attr("href");
	}
});

});

$(window).unload(function(){ alert('do unload stuff here'); });
function menuUpDown(obj,path){
	var child=obj.children('ul');
	$("#accordian ul ul").slideUp();
	$('#accordian li').removeClass("active");
	//slide down the link list below the h3 clicked - only if its closed
    
	if(!child.is(":visible")) {		
		child.slideDown();
		path.addClass("active");
	}

}*/

function logout(){
	sessionStorage.setItem("activeUrl","../reservation/reservationList");
	//window.location="logout";
}

function setupRedirect(){
	window.location="setup/setup";
}

function reservationRedirect(){
	window.location="../reservation/reservation_list";
}


/**
 * converting database date format date format to database date format
*/
function convertDateFormatToJqueryDateFormate(serverDate){
	var clientdateFormat=null;
	if(serverDate=="dd/MMM/yyyy" || serverDate=="dd-MMM-yyyy" || serverDate=="yyyy/MMM/dd" || serverDate=="yyyy-MMM-dd"){
		clientdateFormat=(serverDate.replace('MMM','M')).replace('yyyy','yy');
	}
	else if(serverDate=="dd/MMM/yy" || serverDate=="dd-MMM-yy" || serverDate=="yy/MMM/dd" || serverDate=="yy-MMM-dd"){
		clientdateFormat=(serverDate.replace('MMM','M')).replace('yy','y');
	}

	else if(serverDate=="dd/MM/yyyy" || serverDate=="dd-MM-yyyy" || serverDate=="yyyy/MM/dd" || serverDate=="yyyy-MM-dd"){
		clientdateFormat=serverDate.replace('yyyy','yy');
	}
	else if(serverDate=="dd/MM/yy" || serverDate=="dd-MM-yy" || serverDate=="yy/MM/dd" || serverDate=="yy-MM-dd"){
		clientdateFormat=serverDate.replace('yy','y');
	}else if(serverDate == 'MM/dd/YYYY'){
		clientdateFormat=(serverDate.replace('YYYY','y').replace('MM','mm'));
	}
	/*alert(clientdateFormat);*/
	return clientdateFormat;
}


/**
 * get current currency symbol 
*/
function currencySymbol(){
	
	var symbol="";
	$.ajax({
			url :'../currency/getCurrencySymbol' ,
			type : 'GET',
			async: false,
			data : {},
			success : function(response) {
				symbol=response;				
				}
			});
	/*alert(symbol+" currencySymbol");*/
	return symbol;
}

/*

$(document).ready(function() {
	
	var menu_active=sessionStorage.getItem("id");
	if(menu_active==null){
	menu_active="sub_menu_item_1_1";
	}
	$('#'+menu_active).addClass("active").attr("href","#")
	if($('#'+menu_active).parent().is("h3")){
		$('#'+menu_active).parent().css("background-color", "#4D5865");
	}

	
	$('#'+menu_active).parent().removeAttr("display");
	var parent_menu=menu_active.substr(4,11);
	menuUpDown(parent_menu);
	
	$("#accordian a").click(function(){
		$("#accordian a").removeClass("active");
		$("#accordian h3").css("background-color", "#2a3542");
		$(this).addClass("active");
		sessionStorage.setItem("id",$(this).attr("id"));
		//$("#accordian ul ul").slideDown();
		
	}); 
	$("#accordian h3").click(function(){
		$("#accordian a").removeClass("active");
		$("#accordian h3").css("background-color", "#2a3542");
		$(this).css("background-color", "#4D5865");
		if(parent_menu==$(this).parent().attr("id")){
		menuUpDown($(this).parent().attr("id"));
		}
	
		sessionStorage.setItem("id",$(this).children().attr("id"));
		window.location.href=$(this).children().attr("href");
	});

	

});

function menuUpDown(id){
	$("#accordian ul ul").slideUp();
	$('#accordian li').removeClass("active");
	
	//alert("after");
	//slide down the link list below the h3 clicked - only if its closed

	if(!$('#'+id+" ul").is(":visible")) {
		
		$('#'+id+" ul").slideDown();
		$('#'+id).addClass("active");
		//$('#'+id+" h3").css("background-color", "#4D5865");
	}
}
*/

/**
 * Clear the session storage data for search
 */
function clearSearchData() {
	
	sessionStorage.removeItem('searchData');
	sessionStorage.removeItem('simpleSearch');
}


/** CORPOARATE TA SEARCH MODAL **/

function setCorporateTaRateSearchModal(okCallback, cancelCallback) {
	$.ajax({
		url: "../reservation/getCorpSearchModal",
		type: "POST",
		success: function(response) {
			
			$('body').append(response);
			
			$('#btn_corp_search_close').click(function() {
				
				$('#corp_search_modal').modal('toggle');
			});
			
			$('#btn_corp_search_ok').click(function() {
				
				var selectedCorpTa = $('#corp_search_modal input[type=radio]:checked');
				var corpId = $(selectedCorpTa).attr('data-corpId');
				var corpName = $(selectedCorpTa).attr('data-corpName');
				var corpAddr = $(selectedCorpTa).attr('data-corpAddr');
				
				if(okCallback != null) {
					
					okCallback($('#hdn_corp_search_name').val(), corpId, corpName, corpAddr);
				}
				
				$('#corp_search_modal').modal('toggle');
			});

			$('#btn_corp_search_cancel').click(function() {
				
				if(cancelCallback != null) {
					
					cancelCallback();
				}
				
				$('#corp_search_modal').modal('toggle');
			});
		},
		error: function(response) {
			
		}
	});
}

function showCorporateTaRateSearchModal(id, name) {
	
	$('#txt_corp_search').val('');
	$('#hdn_corp_search_name').val(name);
	
	if(name == 'travel') {
		$('#corp_search_title').html("TRAVEL AGENTS");
	} else {
		$('#corp_search_title').html("CORPORATES");
	}
	
	$.ajax({
		url : '../reservation/CorporateTaInfo',
		type : 'POST',
		async:false,
		data:{
			id:id,
			name: name
		},
		success : function(data) {
			
			if(data.length == 0) {

				$('#corp_search_modal tbody').html("<tr><td colspan='3'>No data found.</td></tr>");
			} else {
				var corpTas = '';
				
				for(var i in data) {
					
					corpTas += '<tr><td><input type="radio" name="corporateta" value="' + data[i].code 
						+ '" data-corpId="' + data[i].id + '" data-corpName="' + data[i].name + '" data-corpAddr="' + data[i].address + '" /></td><td>' 
						+ data[i].code +'</td><td>'+data[i].name +'</td></tr>';
				}
				
				$('#corp_search_modal tbody').html(corpTas);
			}
		}
	});
	
	$("#corp_search_modal").modal({backdrop: "static"});
}

/** END **/



/*** ROOM ASSIGN ****/

/**
 * This function will create the popup.
 * @param okCallback user defined function that get call when clicking Ok button.
 * format of handler function is okCallback(roomNumber);
 * The selected room number is then available in roomNumber parameter
 */
function setRoomAssignPopup(okCallback, cancelCallback) {
	
	$.ajax({
		url: "../reservation/getRoomSearchModal",
		type: "POST",
		success: function(response) {
			
			$('body').append(response);
			
			$('#btn_RoomAssign_Close').click(function() {
				
				$('#room_Search_Modal').modal('toggle');
			});
			
			$('#btn_RoomAssign_Ok').click(function() {
				
				var selectedRoom = $('#room_Search_Modal input[type=radio]:checked').val();
				
				if(okCallback != null) {
					
					okCallback(selectedRoom);
				}
				
				$('#room_Search_Modal').modal('toggle');
			});

			$('#btn_RoomAssign_Cancel').click(function() {
				
				if(cancelCallback != null) {
					
					cancelCallback();
				}
				
				$('#room_Search_Modal').modal('toggle');
			});
		},
		error: function(response) {
			
		}
	});
}

/**
 * This function will fetch the rooms and shows the popup.
 * @param arrivalDate in millisecond
 * @param departDate in millisecond
 * @param roomType
 * @param occupancy
 */
function showRoomAssignPopup(arrivalDate, departDate, roomType, occupancy, assignedRooms) {

	$('#txt_roomassign').val('');
	$.ajax({
		url: "../reservation/getAvailableRooms",
		type: "POST",
		data: {
			arrivalDate: arrivalDate,
			departDate: departDate,
			roomType: roomType,
			occupancy: occupancy
		},
		success: function(response) {

			if(response.length == 0) {

				$('#room_Search_Modal tbody').html("<tr><td colspan='3'>No data found.</td></tr>");
			} else {

				var rooms = "";
				
				for (var i = 0; i < response.length; i++) {
					

					var room = response[i];
					var aroom = assignedRooms.indexOf(room.number);
					
					if(aroom == -1) {
						rooms += "<tr><td style='width: 12%;text-align: center;'><input type='radio' class='with-font' name='availRooms' id='availroom_"+room.number+"'  value='" + room.number + "'/>" +
								"<label style='width: 100%;'	for='availroom_"+room.number+"'></label></td><td>"
							+ room.number + "</td><td>" + room.name + "</td></tr>"
					} else {
						rooms += "<tr style='background-color: #DDDDDD;'><td style='width: 12%;text-align: center;'><input type='radio' class='with-font' name='availRooms'  id='availroom_"+room.number+"' disabled='disabled' value='" + room.number + "'>" +
								"<label	style='width: 100%;' for='availroom_"+room.number+"'></label></td><td>"
							+ room.number + "</td><td>" + room.name + "</td></tr>"
					}
				}

				$('#room_Search_Modal tbody').html(rooms);
			}

		}
	});

	$("#room_Search_Modal").modal({backdrop: "static"});
}

/**
 * Filter the result
 * @param com
 */
function filterSearchResult(com) {
	
	var val = $(com).val().toUpperCase();
	
	$('tbody input[type=radio]').each(function(){
		
		if($(this).val().toUpperCase().search(val) == -1) {
			
			$(this).closest('tr').hide();
		} else {
			$(this).closest('tr').show();
		}
	});
}

/*function getSelectedRoom(room) {
	
	return $('#roomAssignPopup input[type=radio]:checked').val();
}*/

/*** END ***/

/*** SEARCH GUEST HISTORY ***/

/**
 * 
 * 
 */
/*function setGuestHistoryPopup(okCallback, cancelCallback) {
	
	$.ajax({
		url: "../reservation/guestHistoryPage",
		type: "POST",
		success: function(response) {
			
			$('body').append(response);
			
			$('#btn_guest_search_close').click(function() {
				
				$('#guestModal').modal('toggle');
			});
			
			$('#btn_guest_search_ok').click(function() {
				
				if(okCallback != null) {
					
					okCallback(selected);
				}
				
				$('#guestModal').modal('toggle');
			});

			$('#btn_guest_search_cancel').click(function() {
				
				if(cancelCallback != null) {
					
					cancelCallback();
				}
				
				$('#guestModal').modal('toggle');
			});
		},
		error: function(response) {
			
		}
	});
}

function showGuestHistoryPopup() {

	$('#txt_guest_search').val('');
	
	$("#guestModal").modal({backdrop: "static"});
	
}*/


function resetGuestPopup() {
	
	$('#guestHistoryPopup input[type=text]').val('');
	$('#guestHistoryPopup select').prop('selectedIndex', 0);
	searchGuestClick();
}

function getSelectedGuest() {
	
	return $('#tblGuestHistory input[type=radio]:checked').val();
}



function validateRoomEdit(className) {

	var isRequired = "true";
	var count = 0;

	$(className).each(function() {

		var ids = $(this).attr('id');

		if ($('#' + ids).val() == '' || $('#' + ids).val() == -1 ) {

			isRequired = "false";
			$('#'+ids).parent().addClass('has-error');

		}else{

			$('#'+ids).parent().removeClass('has-error');
		}
	});
	return isRequired;
}

/*** END ***/