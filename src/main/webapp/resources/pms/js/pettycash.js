pmsApp.controller('pettyCashController',pettyCashController);
function pettyCashController($scope,$compile,$http,DTOptionsBuilder,DTColumnBuilder,$mdDialog,$filter,$rootScope,$timeout){

	$scope.pettyExpenseList = { item : [] };
	dateFormat = $filter('date')(new Date($('#hotelDate').val()), "yyyy-MM-dd");
	//hotelDate = $.datepicker.formatDate(dateFormat,new Date($('#hotelDate').val()));
	hotelDate=$filter('date')(new Date($('#hotelDate').val()), "yyyy-MM-dd");
	$scope.pettyDate=hotelDate;
	var credit_total=0;
	var debit_total=0;
	$scope.closing_bal={};
	$('#addCategory').click(function(){
		$('#categoryModal').modal('show');
	});
	$scope.formData={};
	var amount=0;
	var opening=0;
	var currDate=$filter('date')(new Date($("#hotelDate").val()), "yyyy-MM-dd");
	$('#openingBalance').attr('readonly', true);
	//$('.footer_div').show();
	$scope.pettyExpenseList.item.push({id:"",entryDate:$scope.pettyDate,openingBalance:0,categoryId:"",voucherType:"",creditAmount:0,debitAmount:0,creditCardAmount:0,narration:""});
	$scope.pagination={offset:0,limit:'10'}
	$scope.totalItems = 0;
	$scope.bigTotalItems = 0;
	$scope.pageChanged = function() {
		$scope.pagination.offset=($scope.bigCurrentPage-1)*parseInt($scope.pagination.limit);
		$scope.loadDataList();
	};
	$scope.itemsPerPage =parseInt($scope.pagination.limit);
	$scope.maxSize=8;
	$scope.bigTotalItems = 0;
	$scope.bigCurrentPage = 1;
	$scope.search={comnSearch:false,advSearch:false,comnSearchValue:"",resvNo:{searchable:false,sortable:true,searchValue:""},resvBy:{searchable:false,sortable:true,searchValue:""},resvDate:{searchable:false,sortable:true,searchValue:null},arrDate:{searchable:false,sortable:true,searchValue:null},resvStatus:{searchable:false,sortable:true,searchValue:""}};
	$scope.sort={sortColumn:'entryDate',order:"desc"};

	$scope.pettyList=[];
	$scope.loadPettyHead=function(){
		$http({
			method:'GET',
			url:'../pettycash/pettyHead'
		}).success(function(response){
			$scope.pettyList=response;
		})
	}

	$http({
		url : '../pettycash/expenseDetails',
		method : 'POST'
	}).success(function(response){
		if(response.length==0)
			$scope.expenseDetails=[];
		for(var i=0;i<response.length;i++){
			//$filter('date')(response[i].entrty_date,'yyyy-MM-dd');
			response[i].entryDate=$filter('date')(new Date(response[i].entryDate), "yyyy-MM-dd");
			response[i].openingBalance=parseFloat(response[i].openingBalance).toFixed(2);
			response[i].closingBalance=parseFloat(response[i].closingBalance).toFixed(2);
			response[i].reciept=parseFloat(response[i].reciept).toFixed(2);
			response[i].payment=parseFloat(response[i].payment).toFixed(2);
			$scope.expenseDetails=response;
			
		}

	})


	$scope.openingBal=function(){
		$http({
			method:'POST',
			url:'../pettycash/openingBalance',
		}).then(function(response){
			$scope.openingBalance=response.data;
			opening=response.data;
			//$scope.formData.opening=$scope.openingBalance;

		});
		//return $scope.openingBalance;
	}
	$scope.addPettyHead=function(){
		$("#show_form").toggle();
		//$('.footer_div').hide();
		$("#show_table").hide();
		$("#btnEdit").hide();
		$("#btnDelete").hide();
		$("#btnSave").attr("disabled", false);
		$scope.pettyExpenseList = { item : [] };
		$scope.pettyExpenseList.item.push({id:"",entryDate:$scope.pettyDate,openingBalance:0,categoryId:"",voucherType:"",creditAmount:0,debitAmount:0,creditCardAmount:0,narration:""});
		$scope.openingBal();
		if(currDate==$scope.pettyDate){
			$('#openingBalance').attr('readonly', false);
		}
		$scope.loadPettyHead();
	}

	/*$(".modal").on("hidden.bs.modal", function(){
	    $(".modalContent_class").val("");
	});*/

	$(document).ready(function() {
		$('.modal').on('hidden.bs.modal', function() {
			$(':input', this).val('');
			$("#category_head").val([]).trigger("change"); 
			$("#pettyHead").css('border-color','');
			// $('#category_head').val('');
		});
	});
	
	$(document).ready(function(){
		$(".md-datepicker-input").mouseover(function(){
			$('.md-datepicker-input').attr('readonly', true);
		});
	});

	$scope.loadCategory=function(){
		//	$scope.categoryList=[];
		$http({
			method:'GET',
			url:'../pettycash/selCategory'
		}).success(function(response){
			$scope.categoryList=response;
		})
	}
	$scope.saveCategory=function(){
		var status=$scope.validate();
		if($('#pettyHead').val() ==''){
			$("#pettyHead").css('border-color','red');
			return false;
		}
		$scope.isCategory=$scope.headId;
		if($scope.isCategory=="" || $scope.isCategory==undefined){
			$scope.isParent=0;
		}else{			
			$scope.isParent=1;
		}
		if($scope.description==undefined){
			$scope.description="";
		}
		$scope.pettyHead={categoryId:$scope.isCategory,parentId:$scope.isParent,head:$scope.pettyHead,description:$scope.description};
		$http({
			method:'POST',
			url:'../pettycash/saveCategory',
			params:{saveJson:JSON.stringify($scope.pettyHead)}
		}).success(function(response){
			if(response=="success"){
				$("#categoryModal").modal('hide');
				$scope.loadPettyHead();
			}
		})
	}

	$scope.validate=function(){
		if($('#pettyHead').val() ==''){
			$("#pettyHead").css('border-color','red');
			return false;
		}
	}

	$scope.saveExpense=function(event){
		var status=validation();
		$scope.pettyLists=[];
		//$scope.formData={};
		//if($('#openingBalance').attr('readonly', false))
		if(status==true){
			// var readonly = $('#openingBalance').attr("readonly");
			/*if($('#openingBalance').is(':disabled')){
			//if( $('#openingBalance').prop('readonly',false)){
				$scope.formData.openingb=0.00;

			}else{
				$scope.formData.openingb=$scope.openingBalance;
			}*/
			if(opening==0 && currDate==$scope.pettyDate){
				$scope.formData.openingb=$scope.openingBalance;
				//	updateBal($scope.formData.openingb);
			}else{
				$scope.formData.openingb=0.00;
			}
			$scope.formData.entryDate=$scope.pettyDate;
			for(var i=0;i<$scope.pettyExpenseList.item.length;i++){

				if($scope.pettyExpenseList.item[i].id==undefined)
					$scope.pettyExpenseList.item[i].id="";
				$scope.pettyLists.push($scope.pettyExpenseList.item[i]);
			}		
			$scope.formData.saveExpense=JSON.stringify($scope.pettyLists);
			$http({
				method:'POST',
				url:'../pettycash/saveExpense',
				data:$scope.formData
			}).success(function(response){
				if(response.length>0){
					angular.forEach(response,function(value,index){

						$scope.pettyExpenseList.item[index].id=value.id;
						$scope.pettyExpenseList.item[index].categoryId= value.categoryId;
						$scope.pettyExpenseList.item[index].categoryName=value.categoryName;
						$scope.pettyExpenseList.item[index].voucherType=value.voucherType;
						$scope.pettyExpenseList.item[index].narration=value.narration;
					});
					$mdDialog
					.show($mdDialog
							.alert()
							.clickOutsideToClose(
									true)
									.title(
									'Data Saved Successfully!!!')
									.textContent(
									"")
									.ok(
									'Ok')
									.parent(
											angular
											.element(document.body)));
					$scope.pettyLists=[];
					$("#btnEdit").show();
					$("#btnDelete").show();
					$("#petty_table").find("input,select").attr("disabled", "disabled");
					$("#openingBalance").prop('disabled',true);
					$("#row_add_id").attr("disabled", true);
					$("#btnSave").attr("disabled", true);
					//$scope.pettyExpenseList = { item : [] };
					//$scope.pettyExpenseList.item.push({id:"",entryDate:$scope.pettyDate,openingBalance:0,categoryId:"",voucherType:"",creditAmount:0,debitAmount:0,narration:""});
					//$("#petty_table tbody tr input:text select").prop("disabled", true);
					/*	 $("#show_table").toggle();
				 $("#show_form").hide();*/
				}else{
					$mdDialog
					.show($mdDialog
							.alert()
							.clickOutsideToClose(
									true)
									.title(
									'Data Save Failed')
									.textContent(
									"")
									.ok(
									'Ok')
									.parent(
											angular
											.element(document.body)));
				}
			})
		}
	}

	function checkOpening(){
		/*if ($("#openingBalance").is(":disabled")){
			  // do stuff
			}*/
		var btnval =  document.getElementById('openingBalance');
		if(btnval.disabled){
			alert("Disabled");
		}else{
			alert("Enabled");
		}
	}

	$("#btnEdit").click(function(){
		$scope.disable_all=false;
		$("#btnSave").attr("disabled", false);
		$("#openingBalance").attr("disabled", true);
		$("#petty_table").find("input,select").removeAttr("disabled", "disabled");
		$("#row_add_id").attr("disabled", false);
		setTimeout(function(){ 
				for(var i = 0;i<$scope.pettyExpenseList.item.length;i++)
				{
					if($('#paymentMode'+i).val()=="PAYMENT"){
						$('#debitAmt'+i).prop('disabled', true);
						$('#creditAmt'+i).prop('disabled', false);
						$('#creditCardAmt'+i).prop('disabled', true);
						
					}else if($('#paymentMode'+i).val()=="CONTRA"){
						$('#creditAmt'+i).prop('disabled',+ true);
						$('#creditCardAmt'+i).prop('disabled', true);
						$('#debitAmt'+i).prop('disabled', false);
						
					}
					else{
						$('#creditAmt'+i).prop('disabled',+ true);
						$('#creditCardAmt'+i).prop('disabled', false);
						$('#debitAmt'+i).prop('disabled', true);
					}
				}
		}, 100);
	});

	$scope.deletePetty=function(){
		var confirm = $mdDialog.confirm()
		.title("Delete this Petty Expense?")
		.ariaLabel('PMS')		 
		.ok('Yes')
		.cancel('No')
		.parent(angular.element(document.body));		  
		$mdDialog.show(confirm).then(function() {
			$http({
				url:"../pettycash/deletePetty",
				method:'POST',
				data:{deletePetty:JSON.stringify($scope.pettyExpenseList.item)}
			}).success(function(response){
				if(response=="success"){
					$mdDialog
					.show($mdDialog
							.alert()
							.clickOutsideToClose(
									true)
									.title(
									'Deleted Successfully!!!')
									.textContent(
									"")
									.ok(
									'Ok')
									.parent(
											angular
											.element(document.body)),15000);

					location.reload();
					$("#show_table").toggle();
					$("#show_form").hide();
				}
			});
		})
	}
	$scope.loadAmount=function(index){
		if($('#paymentMode'+index).val()=="PAYMENT"){
			$('#debitAmt'+index).prop('disabled', true);
			$('#creditAmt'+index).prop('disabled', false);
			$('#creditCardAmt'+index).prop('disabled', true);
			$('#debitAmt').val("");
			$scope.pettyExpenseList.item[index].debitAmount=0.00;
			$scope.pettyExpenseList.item[index].creditCardAmount=0.00;
			$scope.creditTot();
		}else if( $('#paymentMode'+index).val()=="CONTRA")
			{
			$('#creditAmt'+index).prop('disabled', true);
			$('#debitAmt'+index).prop('disabled', false);
			$('#creditCardAmt'+index).prop('disabled', true);
			
			$('#creditAmt').val("");
			$scope.pettyExpenseList.item[index].creditAmount=0.00;
			$scope.pettyExpenseList.item[index].creditCardAmount=0.00;
			$scope.debitTot();
		}
		else if( $('#paymentMode'+index).val()=="JOURNAL")
		{
		$('#creditCardAmt'+index).prop('disabled', false);
		$('#creditAmt'+index).prop('disabled', true);
		$('#debitAmt'+index).prop('disabled', true);
		
		
		$('#creditCardAmt').val("");
		
		$scope.pettyExpenseList.item[index].debitAmount=0.00;
		$scope.pettyExpenseList.item[index].creditAmount=0.00;
		$scope.creditCardAmtTot();
	}
		else{
				$('#creditAmt'+index).prop('disabled', true);
				$('#debitAmt'+index).prop('disabled', false);
				$('#creditCardAmt'+index).prop('disabled', true);
				
				$('#creditCardAmt').val("");
				$scope.pettyExpenseList.item[index].creditCardAmount=0.00;
				$scope.debitTot();
				
			}
      // alert(index);
	/*	$("tr.petty_list_row").each(function() {
			var type= $(this).closest('tr').find('.paymentMode').val();
			if(type=="PAYMENT"){
				$(this).closest('tr').find('#debitAmt').prop('disabled', true);
				$(this).closest('tr').find('#creditAmt').prop('disabled', false);
				$(this).closest('tr').find('#debitAmt').val("");
				$scope.pettyExpenseList.item[index].debitAmount=0.00;
				$scope.creditTot();
				//$("#debitAmt"+id).attr("disabled", "disabled");
			}else{
				$(this).closest('tr').find('#creditAmt').val("");
				$(this).closest('tr').find('#creditAmt').prop('disabled', true);
				$(this).closest('tr').find('#debitAmt').prop('disabled', false);

				$scope.pettyExpenseList.item[index].creditAmount=0.00;
				$scope.debitTot();
			}

		});*/
	}

	$scope.tableClicked = function (index) {
		$scope.table_itemsearch_rowindex= index;
	};


	$scope.addItem=function(){
		if($scope.pettyExpenseList.item.length>0){
			if($scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].categoryId==undefined || $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].categoryId==""){
				$("#petty_table tr:nth-child("+($scope.pettyExpenseList.item.length)+") td:nth-child("+(1)+")").find("#categoryId").focus();
				//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
				$(".error_msg").css("display", "block")
				$(".error_msg").html("Please Enter Head!!!");
				return false;
			}else if($scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].voucherType=="" || $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].voucherType==undefined){
				$("#petty_table tr:nth-child("+($scope.pettyExpenseList.item.length)+") td:nth-child("+(3)+")").find("#paymentMode").select();
				$(".error_msg").css("display", "block")
				$(".error_msg").html("Please Enter Category!!!");
				return false;
				//$(this).closest('tr').find('#paymentMode').css('border-color', 'rgb(234 14 14)');
				//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
			}else if($scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].debitAmount==0 && $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].creditAmount==0
					    && $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].creditCardAmount == 0){
				$("#petty_table tr:nth-child("+($scope.pettyExpenseList.item.length)+") td:nth-child("+(3)+")").find("#paymentMode").select();
				//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
				$(".error_msg").css("display", "block")
				$(".error_msg").html("Please Enter Amount!!!");
				return false;
				//$(this).closest('tr').find('.amount').css('border-color', 'rgb(234 14 14)');
			}else{
				$(".error_msg").css("display", "none");
				$scope.pettyExpenseList.item.push({id:"",categoryId:"",voucherType:"",creditAmount:0,creditCardAmount:0,debitAmount:0,narration:""});
				//$scope.disable_all=false;
				
				return true;
				
			}

		}

	}

	$rootScope.$on("on_AlertMessage_ERR",function(event,msg){	
		setErr_AlertMessage(event,msg);	
	});

	function setErr_AlertMessage(event,msg){
		$scope.err_alertMessageStatus=false; 
		$scope.err_alertMeaasge    =  msg;
		$timeout(function () { $scope.err_alertMessageStatus = true; }, 1500); 		 	
	}

	$scope.setSiNo = function(){
		var siNo=0;
		for(var i = 0;i < $scope.pettyExpenseList.item.length;i++){
			//if(!$scope.invoice.items[i].isDeleted){
			siNo++;
			$scope.pettyExpenseList.item[i].sino = siNo;
			//}
		}
	}

	$scope.removeItem=function(index,id){
		var length=0;
		for(var i=0;i<$scope.pettyExpenseList.item.length;i++){
			length++;

		}
		if(length!=1){
			//	if($scope.pettyExpenseList.item[index].hasOwnProperty('isSet')){
			//$scope.invoice.items[index].isDeleted = true;
			//	}else{
		//	$scope.pettyExpenseList.item.splice(index, 1);
			$("#petty_table tbody tr:nth-child("+$scope.pettyExpenseList.item.length+") td").find("#narration").focus();
			var confirm = $mdDialog.confirm()
			.title("Delete this Petty Expense?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
			$http({
				method:'POST',
				url:'../pettycash/deleteRow',
				params:{id:id}
			}).success(function(response){
				
				$scope.setSiNo();
				$scope.pettyExpenseList.item.splice(index, 1);
			})
			});
			
			//$scope.deletePetty(id);
		}else{
			//$rootScope.$broadcast('on_AlertMessage_ERR',"Atleast one item is required.");
			$mdDialog
			.show($mdDialog
					.alert()
					.clickOutsideToClose(
							true)
							.title(
							'Atleast one expense is required')
							.textContent(
							"")
							.ok(
							'Ok')
							.parent(
									angular
									.element(document.body)));
		}
	}

	$scope.debitTot=function(){
		var tot=0.00;
		for(var i=0;i<$scope.pettyExpenseList.item.length;i++){
			/*	if($scope.pettyExpenseList.item[i].debitAmount==""){
				tot=0.00;
			}else{*/
			if($scope.pettyExpenseList.item[i].debitAmount!="")
				tot+=parseFloat($scope.pettyExpenseList.item[i].debitAmount);
			//}
		}
		$scope.closing_bal.debit_total=parseFloat(tot).toFixed(2);
		$scope.closing_balance();
		return parseFloat(tot).toFixed(2);
	}
	$scope.creditCardAmtTot=function(){
		var tot=0.00;
		for(var i=0;i<$scope.pettyExpenseList.item.length;i++){
			/*	if($scope.pettyExpenseList.item[i].debitAmount==""){
				tot=0.00;
			}else{*/
			if($scope.pettyExpenseList.item[i].creditCardAmount!="")
				tot+=parseFloat($scope.pettyExpenseList.item[i].creditCardAmount);
			//}
		}
//		$scope.closing_bal.creditCardAmt=parseFloat(tot).toFixed(2);
		return parseFloat(tot).toFixed(2);
	}
	
	

	$scope.creditTot=function(){
		var tot=0.00;
		for(var i=0;i<$scope.pettyExpenseList.item.length;i++){
			/*	if($scope.pettyExpenseList.item[i].creditAmount==""){
				tot=0.00;
			}else{*/
			if($scope.pettyExpenseList.item[i].creditAmount!="")
				tot+=parseFloat($scope.pettyExpenseList.item[i].creditAmount);
			//	}
		}

		$scope.closing_bal.credit_total=parseFloat(tot).toFixed(2);
		$scope.closing_balance();
		return parseFloat(tot).toFixed(2);
	}


	$scope.closing_balance=function(){
		var closBal=0;
		var openingBal=$scope.openingBalance;
		if(openingBal!=undefined){
			if($scope.closing_bal.credit_total==0.00 && $scope.closing_bal.debit_total==0.00){
				$scope.closigBalance=0;
			}else{
				$scope.closigBalance=Math.abs(parseFloat(openingBal)+parseFloat($scope.closing_bal.debit_total)-parseFloat($scope.closing_bal.credit_total));
			}
		}
	}
	$scope.showTable=function(){
		$("#show_form").toggle();
		$("#show_table").hide();
	}

	$scope.simpleSearch=function(){
		
		$scope.expenseDetails=[];
		var searchDate=$filter('date')(new Date($scope.search.searchValue), "yyyy-MM-dd");
		$http({
			method:'POST',
			url:'../pettycash/searchExpense',
			params:{searchCritrea:searchDate}
		}).success(function(response){
			
			$scope.expenseDetails=[];
			for(var i=0;i<response.length;i++){
				//$filter('date')(response[i].entrty_date,'yyyy-MM-dd');
				response[i].entryDate=$filter('date')(new Date(response[i].entryDate), "yyyy-MM-dd");
				response[i].openingBalance=parseFloat(response[i].openingBalance).toFixed(2);
				response[i].closingBalance=parseFloat(response[i].closingBalance).toFixed(2);
				response[i].reciept=parseFloat(response[i].reciept).toFixed(2);
				response[i].payment=parseFloat(response[i].payment).toFixed(2);
				$scope.expenseDetails=response;
				
			}
			
			
		})
	}

	$scope.searchBoxClear=function(){
		location.reload();
	}
	$scope.cancelPopUp = function(){
		$("#categoryModal").modal('hide');	
	}

	$(function () {
		$("#narration").keypress(function (e) {
			var keyCode = e.keyCode || e.which;



			//Regex for Valid Characters i.e. Alphabets and Numbers.
			var regex = /^[A-Za-z0-9]+$/;

			//Validate TextBox value against the Regex.
			var isValid = regex.test(String.fromCharCode(keyCode));
			if (!isValid) {
				$("#narration").html("Only Alphabets and Numbers allowed.");
			}

			return isValid;
		});
	});

	function validation(){
		if($scope.pettyExpenseList.item.length>0){
			/*	if($scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].openingBalance==undefined || $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].openingBalance==0 ||
					$scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].openingBalance==""){
				$("#petty_table tr:nth-child("+($scope.pettyExpenseList.item.length)+") td:nth-child("+(1)+")").find("#openingBalance").focus();
				//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
				$(".error_msg").css("display", "block")
				$(".error_msg").html("Please Enter Opening!!!");
				return false;
			}*/
			if($scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].categoryId==undefined || $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].categoryId==""){
				$("#petty_table tr:nth-child("+($scope.pettyExpenseList.item.length)+") td:nth-child("+(1)+")").find("#categoryId").focus();
				//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
				$(".error_msg").css("display", "block")
				$(".error_msg").html("Please Enter Head!!!");
				return false;
			}else if($scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].voucherType=="" || $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].voucherType==undefined){
				$("#petty_table tr:nth-child("+($scope.pettyExpenseList.item.length)+") td:nth-child("+(3)+")").find("#paymentMode").select();
				$(".error_msg").css("display", "block")
				$(".error_msg").html("Please Enter Category!!!");
				return false;
				//$(this).closest('tr').find('#paymentMode').css('border-color', 'rgb(234 14 14)');
				//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
			}else if($scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].debitAmount==0 && $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].creditAmount==0
					&& $scope.pettyExpenseList.item[$scope.pettyExpenseList.item.length-1].creditCardAmount == 0){
				$("#petty_table tr:nth-child("+($scope.pettyExpenseList.item.length)+") td:nth-child("+(3)+")").find("#paymentMode").select();
				//$rootScope.$broadcast('on_AlertMessage_ERR',"Please Enter RecQty");
				$(".error_msg").css("display", "block")
				$(".error_msg").html("Please Enter Amount!!!");
				return false;
				//$(this).closest('tr').find('.amount').css('border-color', 'rgb(234 14 14)');
			}else{
				$(".error_msg").css("display", "none");
				return true;
			}

		}
	}
	$(".shiftback").click(function(){
		window.location.href='../reservation_test/reservationList';
	});
	$scope.selectExp=function(date){
		///alert(date);
		var amount=0;
		$("#show_form").toggle();
		
		$("#show_table").hide();
		$("#addCategory").hide();
		
		if(window.cp_cuserGrpId != 3)//accounts
			{
			if(date!=$scope.pettyDate){
				$("#btnEdit").hide();
				$("#btnDelete").hide();
				$("#btnSave").hide();
			}
			
			}
	
		
		
		$("#row_add_id").attr("disabled", true);
		$scope.disable_all=false;

		$scope.pettyExpenseList = { item : [] };
		$http({
			method:'POST',
			url:'../pettycash/editExpense',
			params:{searchCritrea:date}
		}).success(function(response){
			 $scope.expenseArray=response;
			// $scope.openingBalance=$scope.expenseDetails
			 for(var j=0;j<$scope.expenseDetails.length;j++){
				 if(date==$scope.expenseDetails[j].entryDate){
					 $scope.openingBalance=$scope.expenseDetails[j].openingBalance;
					 $scope.closigBalance=$scope.expenseDetails[j].closingBalance;
				 }
			 }
		for(var i=0;i<$scope.expenseArray.length;i++){
			//if($scope.expenseDetails[i].id==id){
				$scope.pettyExpenseList.item.push({id:$scope.expenseArray[i].id,
					categoryId:$scope.expenseArray[i].categoryId,voucherType:$scope.expenseArray[i].voucherType,creditAmount:0,creditCardAmount:0,debitAmount:0,narration:$scope.expenseArray[i].narration});
				if($scope.expenseArray[i].voucherType=="PAYMENT"){
					$scope.pettyExpenseList.item[i].creditAmount=$scope.expenseArray[i].amount;
					$("#creditAmt").prop("disabled",true);
				}else if($scope.expenseArray[i].voucherType=="JOURNAL"){
					$scope.pettyExpenseList.item[i].creditCardAmount=$scope.expenseArray[i].amount;
					$("#creditCardAmt").prop("disabled",true);
					
				}else{
					$scope.pettyExpenseList.item[i].debitAmount=$scope.expenseArray[i].amount; 
					$("#debitAmt").prop("disabled",true);   
				}
				$scope.pettyDate=$scope.expenseArray[i].entryDate;
			//}

		}
		
		//$scope.openingBal();
		///$scope.closigBalance=Math.abs(parseFloat($scope.openingBal())+parseFloat($scope.closing_bal.credit_total)-parseFloat($scope.closing_bal.debit_total));
		$scope.loadPettyHead();
		$scope.disable_all=true;
		if(currDate==$scope.pettyDate){
			$('#openingBalance').attr('readonly', false);
		}
		})
	}

	$scope.updateBal=function(){
		var status=validation();
		if(status==true){
			if(opening!=0){
				var opening_bal=$scope.openingBalance;
				$http({
					method:'POST',
					url:'../pettycash/updateOpng',
					data:opening_bal
				}).success(function(response){
					console.log(response);
				})
			}
		}
	}

}