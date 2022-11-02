var resvApp = angular.module('masterApp', ['ngMaterial']);
resvApp.controller('masterController', ['$scope','$http','$mdDialog','$window',function ($scope,$http,$mdDialog,$window){
	 
	
	 /*  Confirm box */
	$scope.deleteConfirm = function(id, url, redirectUrl){
		
		 
		  var confirm = $mdDialog.confirm()
			.title("Are you sure you want to delete this Room Rate?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
				$('.modal-dialog').hide();
				deleteMaster(id, url, redirectUrl);			 
			}, function() {		
				//$("#newPaymentmyModal").modal('toggle');
            }); 	 
	  }
	
	$scope.deleteTableRow = function(obj,curnt_rows){
		
		 
		  var confirm = $mdDialog.confirm()
			.title("Are you sure you want to delete revenue sharing row  ?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
				deleteTableRowCall(obj,curnt_rows)
				
			}, function() {		
				//$("#newPaymentmyModal").modal('toggle');
          }); 	 
	  } 
	
	
	 

	  
}]);


function onLoadSetup() {
	dateFormat=convertDateFormatToJqueryDateFormate($('#dateFormat').val());

	$( "#tabs" ).tabs();
	$('#tab_header').removeClass('ui-widget-header');
	
	if ($('#room_rate_Id').val() == 0) {
		$('#delete_btn').hide();
		$("#txtcode").attr("readonly", false);
		$('#txtcode').change(function(){
			codeExist($('#txtcode').val(),'../roomRate/codeExist','txtcode','Code exists.');
		});
	} else{
		$('.datepicker').each(function(){
			if($(this).val()!=''){
				var tempDate=$(this).val().split(' ');
				var date=new Date(tempDate[0]);
				$(this).val($.datepicker.formatDate(dateFormat,date)).attr("readonly", true);;			
			}
		});	
	}
	
	getRoomDetailsAndHideFields();
	onLoadBindEvents();
	onloadValidator('.validator');
	DatepickerWithCurrentDateFormat();
	numericalValidation('.t-table input , .d-table input');
	OnloadValidationEdit('.validator');
	$('[data-toggle="tooltip"]').tooltip(); 
}

/**
 * set events and callback function related with this module
 */
function onLoadBindEvents(){

	statusTagChanger(); // call status changer	statusTagChanger(); 
	$('.t-table input').attr("placeholder","0.00");
	$("#room_type").change(function() {
		getRoomDetailsAndHideFields();
	});
	/**popup for status*/
	$("#status-changer").click(function (e)
			{
		$("#status input:radio[name=status][value='" + $("#current_status").val() + "']").prop("checked",true);
		e.preventDefault();
			});
	$(".sbtnClose").click(function (e)
			{
		$("#statusModal").modal('toggle');
		e.preventDefault();
			});
	$("#sbtnSubmit").click(function (e)
			{
		$("#current_status").val($("#status input:radio:checked").val());
		if($('#current_status').val()!="true")
		{
			$('#cStatus').val("Inactive").addClass('inActive_style').removeClass('active_style');
		}
		else{
			$('#cStatus').val("Active").addClass('active_style').removeClass('inActive_style');
		}
		$("#statusModal").modal('toggle');
		e.preventDefault();
			});  		

	roomDetailsheaderChange();

	$('#tax_included').change(function(){roomDetailsheaderChange();});	
	if($(".t-content-body-row").size()>3){
		$('#t_style').addClass('t_table_horizontal_scroll');
	}	

	if($('.distribution_row').size()-$('.deleted_row').size()==1){
		$(".delete_detail_btn").addClass("del_disable");/**add disabled style for deletion button*/
	};

	$('.dtl_rate_validator').keyup(function(){
		var reg= /^\d{0,8}(\.\d{0,2})?$/;	
		if(reg.test($(this).val())){
			$(this).removeClass("error");
		}
	});
}

/**
 * change header of tarif table
 */
function roomDetailsheaderChange(){
	if($('#tax_included').val()=="YES")
	{
		$('#without_out_tax').hide();
		$('#without_tax').show();
	}
	else{
		$('#without_out_tax').show();
		$('#without_tax').hide();
	}
}

/**
 * save function
 */
function saveData() {
	var commonValidationResult=validation('.validator');
	var additionalValidationResult=additionalValidation();	
	if (commonValidationResult=="false" || additionalValidationResult=="false") {
		if($("#txtcode").val()==""){
			$("#txtcode").focus();
		}
		if($("#txtname").val()=="" && $("#txtcode").val()!==""){
			$("#txtname").focus();
		}
		if($("#valid_from").val()=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#valid_from").focus();
		}
		if($("#valid_to").val()=="" && $("#valid_from").val()!=="" && $("#txtname").val()!=="" && $("#txtcode").val()!==""){
			$("#valid_to").focus();	
		}
		return false;
	}
	else {
		var data = SAVE_ACTION_INS;
		if ($('#room_rate_Id').val() != 0) {
			data = SAVE_ACTION_UPD;
		}
		sessionStorage.setItem("action", data);
		document.room_rate.submit();
	}
}

/**
 * refreshData function
 */
function refreshData() {
	var id = $('#room_rate_Id').val();
	var encryptedId = $('#encryption_id').val();
	var cls = ".refresh_roomRate,.dtl_rate_validator";
	var url = "../roomRate/getRecord?ids=";
	$('.dtl_rate_validator').val('');
	
	$('#room_type').prop('selectedIndex',0);
	getRoomDetailsAndHideFields();
	
	var txt=refresh(id, encryptedId, cls, url);
	if(txt!=''){
		$("#content").html(txt);
		onLoadSetup();
	}	
	$('.distribution_row').each(function(index){
		if($('.distribution_row').size()>1){
			rearrangeTableRowIndex(0,$('.distribution_row').size());
			$(this).remove();
		}
		$('.distribution_row input[type="text"]').val('').removeClass('error');	
	})

	$('#valid_to').datepicker('option','minDate',null);
}

/**
 * cancel function
 */
function cancel() {
	$("#room_rate_div").dialog("close");
	$("#room_rate_div").html("");
}

/**
 * deleteData function
 */
function deleteData() {
	var id = $('#room_rate_Id').val();
	url = "../roomRate/delete";
	redirectUrl = "../roomRate/roomRateList";
	angular.element(document.body).scope().deleteConfirm(id, url, redirectUrl);
	/*var isConfirm = confirm("Are you sure do you want to delete ?");
	if (isConfirm == true) {
		deleteMaster(id, url, redirectUrl);
	}*/
}


/**
 * ajax call to get room details
 */
function getRoomDetailsAndHideFields() {
	var id=$("#room_type").val();
	$.ajax({
		url : '../roomRate/getRoomDetails?ids='+id,
		type : 'GET',
		success : function(response) {
			$("#room_type_name").html(response.name);
			if(response.supportSingleOcc==false)
			{
				hideWithRoomType('.single');
			}
			else{
				showWithRoomType('.single');					
			}

			if(response.supportDoubleOcc==false)
			{
				hideWithRoomType('.double');
			}
			else{
				showWithRoomType('.double');
			}								
			if(response.supportTripleOcc==false)
			{
				hideWithRoomType('.triple');
			}
			else{
				showWithRoomType('.triple');
			}
			if(response.supportQuadOcc==false)
			{			
				hideWithRoomType('.quad');
			}
			else{				
				showWithRoomType('.quad');
			}	
		}
	});
}

/**
 *function to hide fields correspond to the selected room type
 */
function hideWithRoomType(selector){
	$(selector).each(function() {
		var id = $(this).attr('id');
		$(this).addClass('h_column');
		$("#"+id+" input").addClass('h_input').val("");
		$("#"+id+" input").attr("disabled","disabled");
		$("#"+id+" span").addClass('h_input');
	} );
}

/**
 *function to show fields correspond to the selected room type
 */
function showWithRoomType(selector){
	$(selector).each(function(){
		var id = $(this).attr('id');
		$(this).removeClass('h_column');
		$("#"+id+" input").removeClass('h_input');
		$("#"+id+" span").removeClass('h_input');
		$("#"+id+" input").removeAttr("disabled");
	} );
}

/**
 *function to set status on page according to the current status
 */
function statusTagChanger(){
	if($('#current_status').val()=="true")
	{
		$('#cStatus').val("Active").addClass('active_style').removeClass('inActive_style');
	}
	else{
		$('#cStatus').val("Inactive").addClass('inActive_style').removeClass('active_style');
	}

}
/**
 *function to add new row in distribution table
 */
function addRowInTable(){
	$(".delete_detail_btn").removeClass("del_disable");
	var total_rows=$('.distribution_row').size();
	if((total_rows-$('.deleted_row').size())==6){
		$('#revenue_table').addClass('horizontal_scroll');
	}
	var newRowIndex = $('.distribution_row').size();
	var singleTotal=0.0;
	var doubleTotal=0.0;
	var tripleTotal=0.0;
	var quadTotal=0.0;
	var filldeRow;

	for(i=0;i<newRowIndex;i++){
		if(!$("#distribution_row"+i).hasClass("deleted_row")){
			filldeRow="false";
			if(!$('#d-single'+i+' input').hasClass("h_input")){

				if($('#d-single'+i+' input').val()!=""){
					var s_val=parseFloat($('#d-single'+i+' input').val());
					if((s_val!=0) || $('#d-single'+i+' input').val()!="" ){filldeRow="true";}
					singleTotal=singleTotal+s_val;
				}
			}

			if(!$('#d-double'+i+' input').hasClass("h_input")){
				if($('#d-double'+i+' input').val()!=""){
					var d_val=parseFloat($('#d-double'+i+' input').val());
					if((d_val!=0) || $('#d-double'+i+' input').val()!=""){filldeRow="true";}			
					doubleTotal=doubleTotal+d_val;
				}
			}

			if(!$('#d-triple'+i+' input').hasClass("h_input")){
				if($('#d-triple'+i+' input').val()!=""){
					var t_val=parseFloat($('#d-triple'+i+' input').val());
					if((t_val!=0) || $('#d-triple'+i+' input').val()!=""){filldeRow="true";}
					tripleTotal=tripleTotal+t_val;
				}
			}

			if(!$('#d-quad'+i+' input').hasClass("h_input")){
				if($('#d-quad'+i+' input').val()!=""){
					var q_val=parseFloat($('#d-quad'+i+' input').val());
					if((q_val!=0) || $('#d-quad'+i+' input').val()!=""){filldeRow="true";}
					quadTotal=quadTotal+q_val;
				}	 
			}
		}
	}   

	var row="";
	var column='';
	column =column +'<div class="d-content-body-row distribution_row n_row" id="distribution_row'+newRowIndex+'"><div class="d-content-body-left-most-column"><select id="department_type_'+newRowIndex+'" name="rateDistribution['+newRowIndex+'].departmentId" class="form-control m-bot15 validator "><select></div>';
	column =column + '<div id="d-single'+newRowIndex+'" class="d-content-body-column single"><input id="rateDistribution'+newRowIndex+'_singlePc" name="rateDistribution['+newRowIndex+'].singlePc" type="text"></div>';
	column =column +'<div id="d-double'+newRowIndex+'" class="d-content-body-column double" ><input id="rateDistribution'+newRowIndex+'_doublePc" name="rateDistribution['+newRowIndex+'].doublePc" type="text"></div>';
	column =column +'<div id="d-triple'+newRowIndex+'" class="d-content-body-column triple" ><input id="rateDistribution'+newRowIndex+'_triplePc" name="rateDistribution['+newRowIndex+'].triplePc" type="text"></div>';
	column =column +'<div id="d-quad'+newRowIndex+'" class="d-content-body-column quad" ><input id="rateDistribution'+newRowIndex+'_quadPc" name="rateDistribution['+newRowIndex+'].quadPc" type="text"></div>';

	row =column + '<div class="d-content-body-right-most-column"><button type="button" id="del'+newRowIndex+'" class="add_new tax_button delete_detail_btn r_del_btn btn btn-danger btn-xs" onclick="deleteTableRow(this)" title="delete Row" data-toggle="tooltip" data-placement="top"><i class="fa fa-trash-o "></i></button></div></div>';
	$('#revenue_table').append($.parseHTML( row ));
	getRoomDetailsAndHideFields();
	$("#department_type_0 option").clone().appendTo("#department_type_"+newRowIndex);
	numericalValidation('.d-table input');
	$('[data-toggle="tooltip"]').tooltip(); 
}


/**
 *function to delete  row from distribution table
 */
function deleteTableRow(obj){

	var total_rows=$('.distribution_row').size();
	var curnt_rows=total_rows-$('.deleted_row').size();
	if(curnt_rows!=1){
		
		angular.element(document.body).scope().deleteTableRow(obj,curnt_rows);
		/*if(confirm("Delete the row..?"))
		{ 
			var rid=$(obj).parent().parent().attr('id');
			var did=$(obj).attr('id');
			deleteRowWithId(rid,did);		

			if(curnt_rows<5){
				$('#revenue_table').removeClass("horizontal_scroll");
			}

			if(curnt_rows==2){
				$(".delete_detail_btn").addClass("del_disable");*//**add  disabled style for deletion button*//*
			}

		}*/
	}

	return false;
}

function deleteTableRowCall(obj,curnt_rows){
	var rid=$(obj).parent().parent().attr('id');
	var did=$(obj).attr('id');
	deleteRowWithId(rid,did);		

	if(curnt_rows<5){
		$('#revenue_table').removeClass("horizontal_scroll");
	}

	if(curnt_rows==2){
		$(".delete_detail_btn").addClass("del_disable"); 
	}
}
 


/**
 *function to delete selected row from distribution table
 */
function deleteRowWithId(rid,did){
	var isNewRow=false;
	var total_rows=$('.distribution_row').size();
	if((total_rows-$('.deleted_row').size())==6){
		$('#revenue_table').removeClass("horizontal_scroll");
	}
	var rowIdno = parseInt(rid.substr(16,rid.length));
	var delIdno = parseInt(did.substr(3,did.length));
	if($("#"+rid).hasClass("c_row")){
		var setDeletedHidenField='<input id="deleted'+delIdno+'.isDeleted" name="rateDistribution['+delIdno+'].isDeleted" type="hidden" value="true">';
		$("#"+rid).append(setDeletedHidenField);
		$("#"+rid).addClass("deleted_row");
		$("#"+rid).hide();
	}
	else{
		$("#"+rid).remove();
		rearrangeTableRowIndex(rowIdno,total_rows);
		isNewRow=true;
	}
	return isNewRow;
}

/**
 *function to rearrange distribution table row index 
 */
function rearrangeTableRowIndex(start,end){	
	for(i=start;i<end;i++){
		$("#distribution_row"+(i+1)).attr("id","distribution_row"+i);
		$("#department_type_"+(i+1)).attr("name","rateDistribution["+i+"].departmentId").attr("id","department_type_"+i);

		$("#rateDistribution"+(i+1)+"_singlePc").attr("name","rateDistribution["+i+"].singlePc").attr("id","rateDistribution"+i+"_singlePc");
		$("#d-single"+(i+1)).attr("id","d-single"+i);

		$("#rateDistribution"+(i+1)+"_doublePc").attr("name","rateDistribution["+i+"].doublePc").attr("id","rateDistribution"+i+"_doublePc");
		$("#d-double"+(i+1)).attr("id","d-double"+i);

		$("#rateDistribution"+(i+1)+"_triplePc").attr("name","rateDistribution["+i+"].triplePc").attr("id","rateDistribution"+i+"_triplePc");
		$("#d-triple"+(i+1)).attr("id","d-triple"+i);

		$("#rateDistribution"+(i+1)+"_quadPc").attr("name","rateDistribution["+i+"].quadPc").attr("id","rateDistribution"+i+"_quadPc");
		$("#d-quad"+(i+1)).attr("id","d-quad"+i);	
	}	
}

/**rate picker section start
 * Initializing datepicker and setting CurrentDateFormat from System Setting Module
 */

function DatepickerWithCurrentDateFormat() {

	$("#valid_to").datepicker({dateFormat:dateFormat});		

	$('#valid_from').datepicker({
		minDate: null,
		dateFormat:dateFormat,
		onSelect: function(date) {

			$('#valid_to').datepicker('option',{
				minDate:date,
				dateFormat:dateFormat}
			);
			$('#valid_to').val('');
			$('#valid_to_warning').css("display","none");
			$('#valid_to_check').css("display","none");
			$('#valid_to').removeClass('pms_error');
		}
	});
}
/**end date picker*/

/**
 * custom validation for this module
 */
function additionalValidation(){
	var isRequired ="true";	
	if ($('#valid_to').val()!='' && $('#valid-from').val()!='') {

		var fromDate=$.datepicker.parseDate(dateFormat,$('#valid_from').val());
		var toDate=$.datepicker.parseDate(dateFormat,$('#valid_to').val());
		if(toDate<fromDate){
			$("#valid_to_check").css("display","none");
			$("#valid_to_warning").css("display","block");
			$("#valid_to").addClass('pms_error');
			isRequired=FALSE;
		}
	}

	if(distributionTableValidation()==false){
		var temp=$('#totalPercentageErrorId').attr('class').split(' ');
		jumpToErrorTab(false ,'revnShare');
		isRequired="false";
	}

	if(rateDetailsValidation()=="false"){
		isRequired="false";
	}

	return isRequired;
}

function rateDetailsValidation(){
	var isValid="true";
	var reg= /^\d{0,8}(\.\d{0,2})?$/;
	$('.dtl_rate_validator').each(function(){
		if(! reg.test($(this).val())){
			$(this).addClass("error");
			isValid= "false";
			jumpToErrorTab(false ,'tariff');
		}		
	});

	if(isValid=="false"){
		var errorDiv=$('#dtlsRateErrorId');
		jumpToErrorTab(false ,'tariff');
		errorDiv.html('Cannot have huge values for Room rate').show();
		setTimeout(function() {
			errorDiv.hide();
		},3000);
	}

	return isValid;
}

/**
 * check the each column total
 */
function distributionTableValidation(){

	var valid=true;
	$('.distribution_row input').removeClass('error');
	var temp = $('.distribution_row').size();
	var singleTotal=0.0;
	var doubleTotal=0.0;
	var tripleTotal=0.0;
	var quadTotal=0.0;
	var lSingle=0,lDouble=0,lTriple=0,lQuad=0;
	for(i=0;i<temp;i++){

		if(!$("#distribution_row"+i).hasClass("deleted_row")){
			if(!$('#d-single'+i+' input').hasClass("h_input") && $('#d-single'+i+' input').val()!=""){
				lSingle=parseFloat($('#d-single'+i+' input').val());
				singleTotal=singleTotal+lSingle;
			}
			else
			{
				lSingle="";
			}
			if(!$('#d-double'+i+' input').hasClass("h_input") && $('#d-double'+i+' input').val()!=""){
				lDouble=parseFloat($('#d-double'+i+' input').val());
				doubleTotal=doubleTotal+lDouble;
			}else
			{
				lDouble="";
			}
			if(!$('#d-triple'+i+' input').hasClass("h_input") && $('#d-triple'+i+ ' input').val()!=""){
				lTriple=parseFloat($('#d-triple'+i+ ' input').val());
				tripleTotal=tripleTotal+lTriple;
			}else
			{
				lTriple="";
			}
			if(!$('#d-quad'+i+' input').hasClass("h_input") && $('#d-quad'+i+' input').val()!="" ){
				lQuad=parseFloat($('#d-quad'+i+' input').val());
				quadTotal=quadTotal+lQuad;
			}else
			{
				lQuad="";
			}
		}
		if(valid==true && (lSingle==0 || lSingle=="") && (lDouble==0 || lDouble=="") && (lTriple==0 || lTriple=="") && (lQuad==0 || lQuad=="") ){
			if(($('.distribution_row').size()-$('.deleted_row').size())!=1){
				if(deleteRowWithId("distribution_row"+i,"del"+i)){
					i--;
					temp--;
				}				
			}
		}
	}  

	if(($('.distribution_row').size()-$('.deleted_row').size())==1){ /**add disable style to row delete button*/
		$(".delete_detail_btn").addClass("del_disable");
	}	
	if(singleTotal!=100 && singleTotal>0)
	{
		$('.distribution_row .single input').addClass("error");
		valid=false;
	}
	if(doubleTotal!=100 && doubleTotal>0)
	{
		$('.distribution_row .double input').addClass("error");
		valid=false;
	}
	if(tripleTotal!=100 && tripleTotal>0)
	{
		$('.distribution_row .triple input').addClass("error");
		valid=false;
	}
	if(quadTotal!=100 && quadTotal>0)
	{
		$('.distribution_row .quad input').addClass("error");
		valid=false;
	}	

	if(valid!=true){
		$("#totalPercentageErrorId").show();
		$('#totalPercentageErrorId').html('Total Percentage Should Be 100');
		setTimeout(function() {
			$("#totalPercentageErrorId").hide();
		}, 3000);
		return valid;
	}

	for(var i=0;i<temp;i++){ 
		if(!$("#distribution_row"+i).hasClass("deleted_row")){
			var dType=$("#department_type_"+i).val();
			for(var j=(i+1);j<temp;j++){
				if($("#department_type_"+j).val()==dType && !$("#distribution_row"+j).is(':hidden')) {
					valid=false;
					$("#totalPercentageErrorId").show();
					$('#totalPercentageErrorId').html('More than one rows contains same department');
					setTimeout(function() {
						$("#totalPercentageErrorId").hide();
					}, 3000);
					return valid;
				}
			}
		}
	}
	return valid;
}