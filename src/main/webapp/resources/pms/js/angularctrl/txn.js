
pmsApp.controller('postingCtrl', ['$scope','$http','DTOptionsBuilder','DTColumnBuilder','$mdDialog','$filter',function ($scope, $http, DTOptionsBuilder, DTColumnBuilder,$mdDialog,$filter){	
	$scope.folioNo_txn = parseInt(angular.element( document.querySelector( '#hdnfolioNo' ) ).val());
	$scope.dateFormat=$("#dateFormat").val();
	var hDate=$('#txtHDate').val();
	
	var chkInNo = $("#checkInNo").val();
	var txn_edit_after_payment =  $("#txn_edit_after_payment").val();
    var isFullySettled=  $("#isFullySettled").val();
	$scope.postTransactionBtn =false;
	$scope.dtInstance = {};
	$scope.posting={};
	$scope.count=window.countOpen;
	$scope.posAccMStName = "";
	$scope.txnId;
	$scope.postMode=[{id:1,code:"Base Amount"},{id:2,code:"Nett Amount"}];
	$scope.isAdjust=[{id:0,code:"No"},{id:1,code:"Yes"}];
	
	//hotel date
	var hdateformat=$('#txtHDate').val();
	var arrdateFormat = "MM-dd-yyyy";
	$scope.hotelDateFormat=$filter('date')(new Date(hdateformat), arrdateFormat);
	$scope.hotelDate=new Date($('#txtHDate').val());
	
	$scope.accMasterData = [];
	$http({
		url:'../transaction/getAccountTypeName',
		method:'POST'
	}).success(function(response){
		$scope.accMasterData = response;
	});
	
	$scope.dtOptions = DTOptionsBuilder.newOptions().withOption('ajax', {
		url: "../transaction/transactionList",
		type: "POST",
		data:{folioNo:$scope.folioNo_txn},
		error: function(response){
            $mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(response.responseText).ok('Ok').parent(angular.element(document.body)));
		}
	}).withPaginationType('full_numbers').withDisplayLength(10).withOption('rowCallback', rowCallback);

	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		//if(window.cp_isCanEdit && !window.isFullySettled){
			$('td', nRow).unbind('click');
			$('td:eq(5)', nRow).bind('click',function(){
				if(aData.accId==12){
					window.open("../transaction/posOrderDetails?txnNo="+aData.id);
				}
			});
			$('td:eq(2)', nRow).bind('click',function(){
				if(aData.accId==12){
					$scope.posEditAccName(aData.id);
				}
			});
			$('td',nRow).bind('click', function() {
				
				$scope.$apply(function() {
					if(txn_edit_after_payment=="NO" && isFullySettled == "true"){
						return;
					}
					//if(aData.is_disc_applied!=true &&aData.accId!=5 && aData.accId!=4 &&aData.accId!=6 && aData.accId!=8 && aData.accId!=9 && aData.accId!=10 && aData.accId!=12)
					if(aData.is_disc_applied!=true &&aData.accId!=5  &&aData.accId!=6 && aData.accId!=8 && aData.accId!=9 && aData.accId!=10 && aData.accId!=12)
					$("#newPostingmyModal").modal({backdrop: "static"});
					
					
					if(window.cp_isCanEdit || hDate==aData.txnDate){
						if(aData.txnStatus==0 || aData.txnStatus==2){
							$("#deletePostingBtn").hide();
							$("#savePostingBtn").hide();
							$(".disableDel").prop("disabled",true);
						}else{$("#savePostingBtn").show();$("#deletePostingBtn").show();
						$(".disableDel").prop("disabled",false);
						}	
					}else{
						$("#deletePostingBtn").hide();
					//	$("#savePostingBtn").hide();
						if($("#deletePostingBtn").hide()){
							$(".disableDel").prop("disabled",true);
						}else{
							$(".disableDel").prop("disabled",false);
						}
					}	
					
					
						//$("#deletePostingBtn").hide();
						//$("#savePostingBtn").hide();
						//$(".disableDel").prop("disabled",true);
						//$("#slctTxnMode").prop("disabled",true);
						//$("#amt").prop("disabled",true);
						//$("#txnDate").prop("disabled",false);
					
					
					if(aData.inclTax==true){
						$scope.tMode=2;
					}else{$scope.tMode=1;}
					
					if(aData.is_adjust==true){
						$scope.isAdj=1;
					}else{$scope.isAdj=0;}
					$scope.PrevDate = aData.txnDate;
					$scope.charges={baseAmnt:aData.baseAmount,nettAmount:aData.nettAmount,tax1amount:aData.tax1_amount,tax2amount:aData.tax2_amount,tax3amount:aData.tax3_amount,tax4amount:aData.tax4_amount,tax1pc:aData.tax1_pc,tax2pc:aData.tax2_pc,tax3pc:aData.tax3_pc,tax4pc:aData.tax4_pc,serviceCharge:aData.service_charge,serviceChargePc:aData.service_charge_pc};
					$scope.posting={txnDate:new Date(aData.txnDate),txnMode:$scope.tMode,txnType:aData.accId.toString(),isAdjust:$scope.isAdj,amount:aData.amount,remarks:aData.remarks,baction:"edit",txnId:aData.id};				});
			
			})
		//}
		if(aData.is_disc_applied==true || aData.accId==6 || aData.accId==5 || aData.accId==8 || aData.accId==9 || aData.accId==10){
			$(nRow).css('background-color','#e0e0e0');
		}
		if(aData.txnStatus == 0)
		{$('td:eq(4)', nRow).css('color', '#f00');}else if(aData.txnStatus == 1)
		{$('td:eq(4)', nRow).css('color', '#008000');}if(aData.txnStatus == 2)
		{$('td:eq(4)', nRow).css('color', '#04b0c3');}	
		
		return nRow;
	}
	$scope.dtColumns = [
						DTColumnBuilder.newColumn("txnDate","Txn Date").renderWith(function(data, type, full) { 
							data = $filter('date')(new Date(data),$scope.dateFormat);
							return data;
						}),
	                    DTColumnBuilder.newColumn("id","Txn ID").renderWith(function(data, type, full) {
	                    	$scope.txnId = data;
	                    		return data;
	                    	}),
	                    DTColumnBuilder.newColumn("accName","Transaction Type").renderWith(function(data, type, full) {
	                    		//data = $scope.getAccountTypeName(data);
	                    		
	                    		if(full.accId == 12){
	                    			data =data+" "+"<i class='fa fa-edit' aria-hidden='true' ></i>";
	                    		}
	                    		return data;
	                    		
	                    	}),
	                    DTColumnBuilder.newColumn("nettAmount","Amount"),
	                    DTColumnBuilder.newColumn("txnStatus","Status").renderWith(function(data, type, full) { return payStatus(data,full);}).withOption('width','20%'),
	               /* //it is not implemented correctly    DTColumnBuilder.newColumn("accId","Preview").renderWith(function(data, type, full) {
	                    	if(data == 12){
	                    		data = "<i class='fa fa-print' aria-hidden='true'></i>";
	                    	}else{
	                    		data = '';
	                    	}
	                    	return data;
	                    	})*/	                    ];
	function payStatus(data,full){
		var type="";
		if(data==0){type="DELETED";}
		else if(data==1){type="ACTIVE";}
		else if(data==2){type="TRANSFERED TO "+full.roomName;}
		 if (full.is_disc_applied == true ){type="DISCOUNT APPLIED";}
		return type;
	}
	$scope.txnIdPOS = 0;
	$scope.posEditAccName = function(txnId){
	//	alert("hi");
		$scope.txnIdPOS =txnId; 
		$("#newPostingmyModalPOS").modal({backdrop: "static"});
	}
	
	$scope.getAccountTypeName = function(code){
		
		var name = "";
		for(var i=0; i<$scope.accMasterData.length; i++){
			if($scope.accMasterData[i].code == code){
				name = $scope.accMasterData[i].name;
			}
		}
		return name;
	}
	
	$scope.newPosting = function(){
		
		$("#amt_check").css("display","none");
		if($scope.count!=0){
		$("#deletePostingBtn").hide();
		$("#savePostingBtn").show();
		$(".disableDel").prop("disabled",false);
		$scope.posting={txnDate:$scope.hotelDate,txnMode:2,txnType:"",isAdjust:0,amount:"",remarks:"",baction:"save",txnId:0};
		if($scope.posting.txnType == ""){
			$("#amt").attr('disabled',true);
			$("#slctTxnMode").attr('disabled',true);
		}
			$("#slctTxnType").change(function(){
			    $("#amt").attr('disabled',false);
			    $("#slctTxnMode").attr('disabled',false);
		  });

		$scope.charges={baseAmnt:"",nettAmount:"",tax1amount:"",tax2amount:"",tax3amount:"",tax4amount:"",tax1pc:"",tax2pc:"",tax3pc:"",tax4pc:"",serviceCharge:"",serviceChargePc:""};
		$("#newPostingmyModal").modal({backdrop: "static"});
		}
		else{
			var confirm = $mdDialog.confirm()
			.title("No shifts Available")
			.textContent("Please Open the  Shift.").ok('Ok').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function() {
				$window.location.href = "../shiftManagement/openshift";
			});
		}
		if($scope.posting.txnType==" "){
			$scope.posting.amount = disabled;	
		}
	}

	/*$('#slctTxnMode').on('focusout', function() {
		$( "#amt" ).trigger( "focusout" );
	});*/
	
	
	/*$("#slctTxnType").on('focusout', function() {
		var typeChage=$('option:selected',this).attr("data-taxinc");
		if(typeChage=="false"){
			$scope.posting.txnMode=1;		
		}else{
			$scope.posting.txnMode=2;
		}
		$( "#amt" ).trigger( "focusout" );
	});*/

	//$("#amt").bind( "focusout",function(){
	$scope.amountChange = function(){
		if($scope.posting.amount=="" || $scope.posting.amount==undefined || $scope.posting.amount=="." ){
			//$("#slctTxnType").focus();
			$scope.posting.amount=0;
		}
		var validNumber = new RegExp(/^\d*\.?\d*$/);

		 if (validNumber.test($scope.posting.amount)) {
			 lastValid =  $scope.posting.amount;
			  } else {
				  $scope.posting.amount = lastValid;
			  }
		//else{
			$('#imgloader').show();
			$scope.isBase=true;
			if($scope.posting.txnMode==2){$scope.isBase=false}
			var data = $.param({
				amount : $scope.posting.amount,
				accMstId : parseInt($scope.posting.txnType),
				isBase : $scope.isBase,
				chkInNo : chkInNo
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../transaction/getCharges', data, config)
			.success(function (data, status, headers, config) {
				$('#imgloader').hide();
			$scope.charges={baseAmnt:data.base_amount,nettAmount:data.nett_amount,tax1amount:data.tax1_amount,tax2amount:data.tax2_amount,tax3amount:data.tax3_amount,tax4amount:data.tax4_amount,tax1pc:data.tax1_pc,tax2pc:data.tax2_pc,tax3pc:data.tax3_pc,tax4pc:data.tax4_pc,serviceCharge:data.serviceCharge,serviceChargePc:data.serviceChargePc};
			}).error(function (data, status, header, config){
				$('#imgloader').hide();
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
		//}
	}

	$scope.previousPage=  function() {
		window.location.href = "../reception/receptionList";
	}

	$scope.showAlert = function(msg){    
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Alert').textContent(msg).ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
	};
	
	
	$scope.save=function() {
		
		$scope.postTransactionBtn = true;
		
		  
		
		var commonValidationResult=validation('.validator');
		if(commonValidationResult == "true"){
			if( $scope.posting.amount==0){
				$("#amt").addClass("pms_error");
				$("#amt").focus();
				return;
			}
			
			
			$("#newpostingmyModal").modal('toggle');
			var confirm = $mdDialog.confirm()
			.title("Are you sure you want to save Transaction?")
			.textContent("Transaction will be Saved").ok('Yes').cancel('No').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				$scope.isBase=true;
				if($scope.posting.txnMode==2){$scope.isBase=false}	
				
				

				if($scope.posting.baction=="save"){
					var postingDate =$scope.hotelDate;
					if($scope.posting.txnDate!= null && $scope.posting.txnDate!= undefined){
						 postingDate = $filter('date')(new Date($scope.posting.txnDate), "yyyy-MM-dd");
					}
					
					var data = $.param({
						txnDate:postingDate,
						amount : $scope.posting.amount,
						accMstId : parseInt($scope.posting.txnType),
						isBase : $scope.isBase,
						isAdj: $scope.posting.isAdjust,
						remarks:$scope.posting.remarks,
						chkInNo : chkInNo
					});
					var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../transaction/saveTxn', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						$scope.dtInstance.reloadData();
						$("#newPostingmyModal").modal('toggle');
					}else{alert("error");}
					$scope.postTransactionBtn =false;
					
				}).error(function (data, status, header, config){
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
					$scope.postTransactionBtn =false;
				});
				
				}
			
				
				if($scope.posting.baction=="edit"){
					var postingDate =$scope.PrevDate;
					if($scope.posting.txnDate!= null && $scope.posting.txnDate!= undefined){
						 postingDate = $filter('date')(new Date($scope.posting.txnDate), "yyyy-MM-dd");
					}
					
					var data = $.param({
						txnDate:postingDate,
						amount : $scope.posting.amount,
						accMstId : parseInt($scope.posting.txnType),
						isBase : $scope.isBase,
						isAdj: $scope.posting.isAdjust,
						remarks:$scope.posting.remarks,
						txnId:$scope.posting.txnId,
						chkInNo : chkInNo
					});
					var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
					$http.post('../transaction/updateTxn', data, config)
					.success(function (data, status, headers, config) {
						if(data.substring(7)=='success'){
							$scope.dtInstance.reloadData();
							$("#newPostingmyModal").modal('toggle');
						}else{alert("error!!");}
						$scope.postTransactionBtn =false;
					}).error(function (data, status, header, config){
						$mdDialog.show($mdDialog.alert()
								.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
						$scope.postTransactionBtn =false;
					});
					
					}

			},function(){				
				$("#newpostingmyModal").modal({backdrop: "static"});
			}

			);
		}
		
	}
	
	$scope.savePosAccNAme = function(){
		if($scope.posAccMStName != "" && $scope.posAccMStName != null)
		{
			var data = $.param({
			txnId:$scope.txnIdPOS ,
			posAccMStName : $scope.posAccMStName
		});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../transaction/updateTxnPos', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='success'){
				$scope.dtInstance.reloadData();
				$("#newPostingmyModalPOS").modal('toggle');
			}else{alert("error!!");}
		}).error(function (data, status, header, config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});
		}
		
	}

	$scope.numericalValidation = function($event){
       	if(isNaN(String.fromCharCode($event.keyCode))){
            $event.preventDefault();
        }
     };

//Delete

	$scope.deletePosting=function() {
		$("#newpostingmyModal").modal('toggle');
		var confirm = $mdDialog.confirm()
		.title("Alert")
		.textContent("Are you sure you want to delete this Transaction?").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			var data = $.param({
				txnNo:$scope.posting.txnId
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'}}
			$http.post('../transaction/deleteTxn', data, config)
			.success(function (data, status, headers, config) {
				if(data.substring(7)=='success'){
					$scope.dtInstance.reloadData();
					$("#newPostingmyModal").modal('toggle');
				}else{$scope.showAlert("Can't delete now.\nPlease try after night audit");
				$("#newPostingmyModal").modal('toggle');}
			});
		});

		return isExist;
	}

	$scope.cancelPopUp=function(){
		$("#newPostingmyModal").modal('toggle');
	}
	
	$scope.goToCheckOut = function(checkOutFolioBindNo){
		window.location="../checkOut/checkOutEdit?folioBindNo="+checkOutFolioBindNo;
	}
}]);

