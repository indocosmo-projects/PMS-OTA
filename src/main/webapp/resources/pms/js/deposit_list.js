$(document).ready(function(){

	reservationSortoptionText();
	formInputs();
	//$('#sort_options_div').hide();
	//$('#sort_order_switch_btn_div').hide();
	$('#footer_btm_div').hide();
	   var dateFormat=convertDateFormatToJqueryDateFormate($('#dateFormat').val());
	    $('#txn_date').datepicker({
			dateFormat:dateFormat
		});
	    
	 
	    $('#reservation_list_sort_ascdsc').change(function(){
	    	     var advanceSearch=$('#stringContent').val();
	    	     list(advanceSearch)
	    });   
	    
	    
	    
	list();
	
	$("#myModal").on('hidden.bs.modal', function (e) {
		if(e.target.id=="myModal"){
			$('#').html('');
		}
	});
	
});



function list(advanceSearch){
	
	
	var txt="";
	
	var sortValueType="asc";
	var simpleSearch=$('#simpleSearch').val();
	
	
	
	if($('#sortStatus').val() !=""){
		sortValueType=$('#sortStatus').val();
		
	}
	
	if(advanceSearch != ""){
		simpleSearch="";
	}
	
	
	$.ajax({
			url : "../deposit/depositListJson" ,
			type : 'POST',
			async:false,
			data:{
				ids:1,//$('#folioBindNo').val(),
				advanceSearch:advanceSearch,
				rowLimit:$('#rowCounter option:selected').text(),
				simpleSearch:simpleSearch,
				sort:$('#reservation_list_sort_ascdsc option:selected').val(),
				sortValue:sortValueType,
			      },
			      
			      /*	success : function(response) {
					
					$("#myModal").modal({backdrop: "static"});
					$.ajax({
						url: "../transaction/newPosting?txnNo=" + txn_no,
						type : 'POST',
						success: function (response) {
							$("#content").html(response);
							onLoadTxn();
							$('#btnTxnDelete').hide();
						}
					});	
				}*/
			      
			success : function(response) {
				
				
				for(var i=0;i<response.length;i++){
					 txt+="<div class=paymentListDivSingle><div> Posted On : "+response[i].date +"</div><div> Transaction : "+response[i].transaction+"</div><div> Amount :"+response[i].amount+"</div></div>";
				}
				$("#deposit_list").html(txt);
				
			},
			error: function(xhr, status, err) {
           alert(xhr.responseText);
       }
		});
	
	/* $('#paymentListDiv').html(txt);*/
}




function advanceSearch() {
	
	
	item = {};
	var tagInput = '';
	var count = 0;

	
	$('.searchCls').each(
			function() {
				var ids = $(this).attr('id');
				var tag = $(this).attr('data-tag');
				var inputType = $(this).prop('tagName');
				var val = "";

				if(inputType == "INPUT") {
					val = $('#' + ids).val();
				} else if(inputType == "SELECT") {
					val = $('#' + ids + " option:selected").text();
				}

				if ($('#' + ids).val() != '') {
					item[ids] = $('#' + ids).val();
					count++;
					tagInput += '<div id="'
						+ ids
						+ '-'
						+ count
						+ '" class="inputTags">'
						+ tag
						+ ' : '
						+ val
						+ '<span class="inputclose" onclick="tagInputClose(this)">x</span></div>';
				}
			});

	$('#simpleSearchTxt').html(tagInput);
	var advanceSearch = JSON.stringify(item);
	$('#advance_search_box_div').hide();
	$('#loginButton').removeClass('active');
	
	var simpleSearch;
	
	$('#stringContent').val(advanceSearch);
	
	list(advanceSearch);
	

}





function tagInputClose(obj) {
	
	var id = $(obj).closest('div').attr('id');
	var temp = id.split('-');
	$('#' + id).remove();
	$('#stringContent').val('');
	$('#' + temp[0]).val('');
	$('#stringContent').val("");
	advanceSearch();
}




function advanceSearchClear(){
	
	$('.searchCls').each(function() {
	var ids = $(this).attr('id');
	$('#'+ids).val('');
    });
	$('#stringContent').val("");
	var advanceSearch="";
	$('#simpleSearch').val("");
	list(advanceSearch);

}

function searchBoxClear(){
	
	$('#simpleSearchTxt').html('');
	$('#stringContent').val("");
	advanceSearchClear();
	var advaceSearch;
	$('#simpleSearch').val("");
    list(advaceSearch)
}


function simpleSearch() {
	var simpleSearch = $('#simpleSearchTxt').text();
	simpleSearch=$.trim(simpleSearch);
    var advaceSearch="";
    $('#simpleSearch').val(simpleSearch);
	list(advaceSearch);
	
}   


function reservationSortoptionText(){
	
	var txt='<option value="txn_date">Posted Date</option><option value="nett_amount">Amount</option>';
	
	$('#reservation_list_sort_ascdsc').html(txt);
}

function formInputs(){
	
	var txt='<input type="hidden" id="stringContent" /> <input type="hidden" id="sortStatus" />  <input type="hidden" id="simpleSearch" />';
	
	   $('#form_content_div').html(txt);
}



function sort(clickedObj, id) {
	
	$('#' + id).removeClass("active");
	$('#' + id).removeClass("btn-success");
	
	$(clickedObj).addClass("active");
	$(clickedObj).addClass("btn-success");
	if(id == "reservation_asc"){
		$('#sortStatus').val("desc");
	}else if(id == "reservation_desc"){
		$('#sortStatus').val("asc");
	}
	var advaceSearch = $('#stringContent').val();
	var simpleSearch = "";
	list(advaceSearch);

}


