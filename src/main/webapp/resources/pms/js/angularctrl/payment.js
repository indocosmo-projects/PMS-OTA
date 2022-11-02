pmsApp.requires.push('simple-autocomplete');
pmsApp.controller('paymentCtrl', ['$scope','$http','$window','$mdDialog',function ($scope, $http,$window,$mdDialog){
	var checkInNo = $("#checkInNo").val();
	var fNo = parseInt(angular.element( document.querySelector( '#hdnfolioNo' ) ).val());
	//$scope.payType=[{pTypeId:8,pmType:'PAID-IN'},{pTypeId:5,pmType:'REFUND'},{pTypeId:6,pmType:'DISCOUNT'}];
	
	//var canPay = parseInt(angular.element( document.querySelector( '#canPay' ) ).val());
	var canPay = angular.element("#canPay").val()=="true";

		if(canPay){
			//old
			$scope.payType=[{pTypeId:8,pmType:'PAID-IN'},{pTypeId:6,pmType:'DISCOUNT'},{pTypeId:14,pmType:'COMPLEMENTARY'}];
			//new
			$scope.payType=[{pTypeId:8,pmType:'PAID-IN'},{pTypeId:14,pmType:'COMPLEMENTARY'}];
		}else{
			//old
			$scope.payType=[{pTypeId:5,pmType:'REFUND'},{pTypeId:6,pmType:'DISCOUNT'}];
			//new 
			$scope.payType=[{pTypeId:5,pmType:'REFUND'},{pTypeId:6,pmType:'DISCOUNT'}];
		}
	
	$scope.submitClick=true;
	$("#bankCardTypeDiv").hide();
	$("#bankName").show();
	$("#companyName").hide();
	$scope.nameBank=true;
	$scope.card=[];
	$scope.bank=[];
	$scope.company=[];
	$scope.nameBank=[];
	$scope.deleteShow =[];
	$scope.card[0]=false;
	$scope.bank[0]=true;
	$scope.paidBal="";
	$scope.deleteShow[0]=false;
	$scope.company[0]=false;
	$scope.nameBank[0]=true;
	$scope.addShow=false;
	$scope.payments=[];
	$scope.discountSubmit =false;
	
	
	//for discount
/*	$scope.discounts = [{pmtType:6,acc_mst_code:"DISCOUNT",discountFor:"ROOM",baseAmount:0,isPcDiscount:true,discPC:0,discAmount:0,pmtMode:"1",remarks:"ROOM",checkindis_id:0},
						{pmtType:6,acc_mst_code:"DISCOUNT",discountFor:"FOOD",baseAmount:0,isPcDiscount:true,discPC:0,discAmount:0,pmtMode:"1",remarks:"FOOD",checkindis_id:0}];*/
	//var discount = {discountId:6,discountCode:"DISCOUNT",discountFor:"",baseAmount:0,isPcDiscount:true,discPC:0,discAmount:0,pmtMode:"1",remarks:""}
	$scope.discount_dataOriginal = [];
	$scope.checkno =  $("#checkInNo").val();
	$http({
		url:'../payment/getDiscountDetails',
		method : "GET",
		params : {"checkinNo":$scope.checkno}
	}).then(function(response) {
		var discount_data = response.data;
		if(response.data.length > 0 ){
			//$scope.discount_dataOriginal = discount_data;
			$scope.discounts = discount_data;
			//$scope.ChangeDiscountData($scope.discount_dataOriginal );
	}
	});
	$scope.ChangeDiscountData = function(discount_data){
	angular.forEach(discount_data,function(data,index){
		$scope.changeValByuse(data);
	});
	}
	
	$scope.DeleteDisCount = function($index){
		 if ($index > -1 && $scope.discounts.length>1) {
			    $scope.discounts.splice($index, 1);
			  }
		 else{
			 $mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('').textContent("Not Possible").ok('Ok!').parent(angular.element(document.body)));
		 }
	}
	$scope.changeValByuse = function (obj){
		angular.forEach($scope.discounts,function(value,index){
			if(value.discountFor == obj.sysdef_acc_type_id){
				value.baseAmount = obj.total_amount
				value.isPcDiscount = obj.is_pc
				value.discPC = obj.disc_pc
				value.discAmount = obj.disc_amount
				value.checkindis_id =  obj.checkindis_id;
			}
		});
		
	}
	$scope.changeDiscPc = function (obj){
		if(obj.isPcDiscount == false){
			obj.discPC = 0;
			obj.isPcDiscount = true;
			$scope.changeDiscAmt(obj);
		}
		else{
			obj.isPcDiscount = false;
			obj.discPC = 0;
			obj.discAmount = 0;
			
		}
		
	}
	$scope.changeDiscAmt = function (obj){
		if(obj.isPcDiscount == true){
			obj.discAmount = obj.baseAmount*obj.discPC/100;
		} 
		
		
	}
	$scope.saveDiscount = function(){
		if($scope.DiscRemarks == undefined || $scope.DiscRemarks == "")
			{
			$('#disc_reason_warning').show();
			$("#disc_reason").focus();
			return;
			}
		$("#disc_reason_warning").hide();
		$scope.discountSubmit =true;
		var isSubmit = true;
		for(var i =$scope.discounts.length-1;i>=0;i--){
			if($scope.discounts[i].discAmount!=0 && $scope.discounts[i].baseAmount == 0){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('').textContent("Please Apply Discount Before Complete All Posting").ok('Ok!').parent(angular.element(document.body)));
				isSubmit = false
				break;
			
				
			}
			if($scope.discounts[i].discAmount==0||$scope.discounts[i].discAmount ==""||$scope.discounts[i].discAmount==null){
				
				$scope.discounts.splice(i,1);
			}
			
			if($scope.discounts[i].discRemarks == "" || $scope.discounts[i].discRemarks == null ){
				$scope.discounts[i].discRemarks = $scope.DiscRemarks;
			}
		}
		if(isSubmit){
		var data = $.param({payments:JSON.stringify($scope.discounts)});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../payment/saveDiscount', data, config)
		.success(function (data, status, headers, config) {
			if(data=='success'){
				$scope.discountSubmit = false;
				var alert = $mdDialog.alert()
				.title("Discount Applied Successfully").ok('Ok').parent(angular.element(document.body));
				$mdDialog.show(alert).then(function(){
					window.location="../checkOut/checkOutEdit?folioBindNo="+window.folioBindNo;
				}, function() {
					window.location="../reception/receptionList";
				});
			}
		}).error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Some Error Occured !!').textContent(data).ok('Ok!').parent(angular.element(document.body)));
		});
		}
		
	}
	var payment={pmtType:$scope.payType[0].pTypeId,amount:"",remarks:"",pmtMode:"1",bankCardType:1,bankName:"",companyName:""};
	if($("#corporate").val() != 0){
		$http({
			//url : '../reservation_test/getTACorpList',
			url:'../settlement/getCorporates',
			method : "POST",
			//data : parseInt($scope.payments[index].pmtMode)
		}).then(function(response) {
			$scope.corpList = response.data;
			payment.corporateId = $("#corporate").val().toString();
		});
		payment.pmtMode = "5";
		$scope.bank[0]=false;
		$scope.company[0] = true;
	}
	$scope.payments.push(payment);
	$scope.payments.pmtMode = $("#payment_source").val();
	//for discount
	//$scope.discounts.push(discount);
	
	
	$( function() {
		$scope.datasBank=[];
		$http({
			method: "POST",
			url:"../payment/bankDetails"
		}).then(function(response) {
			$scope.datas = response.data;
			for(i=0;i<$scope.datas.length;i++){
				$scope.datasBank.push($scope.datas[i].paymentoption);
			}
			var availableTags = $scope.datasBank;
		    $( "#bankNames" ).autocomplete({
		      source: availableTags
		    });
		});
	    
	  } );
	
	$( function() {
		$scope.datasCompany=[];

		$http({
			method: "POST",
			url:"../payment/companydetails"
		}).then(function(response) {
			$scope.datasCompny = response.data;
			for(i=0;i<$scope.datasCompny.length;i++){
				$scope.datasCompany.push($scope.datasCompny[i].paymentoption);
			}
			var availableTags = $scope.datasCompany;
		    $( "#companyNames" ).autocomplete({
		      source: availableTags
		    });
		});
			
		});
	   
	
	$scope.save = function(){
		$scope.submitClick=true;
		
		for(var i =$scope.payments.length-1;i>=0;i--){
			if($scope.payments[i].amount==0||$scope.payments[i].amount==""||$scope.payments[i].amount==null){
				
				$scope.payments.splice(i,1);
			}
			
			if($scope.payments[i].chkInNo == undefined){
				$scope.payments[i].chkInNo = checkInNo;
			}
		}
		
		if($scope.payments.corporateId == ""){
			$scope.payments.corporateId =0;
		}
		
			var data = $.param({payments:JSON.stringify($scope.payments)});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../payment/save', data, config)
			.success(function (data, status, headers, config) {
				//if(data.substring(7)=='success'){
				if(data=='success'){
					var alert = $mdDialog.alert()
					.title("Payment done successfully").ok('Ok').parent(angular.element(document.body));
					$mdDialog.show(alert).then(function(){
						window.location="../checkOut/checkOutEdit?folioBindNo="+window.folioBindNo;
					}, function() {
						//window.location="../checkOut/checkOutEdit?folioBindNo="+window.folioBindNo;
						window.location="../reception/receptionList";
					});
				}
				/*else{

					var confirm = $mdDialog.confirm()
					.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
					$mdDialog.show(confirm).then(function(){
						window.location  = "../shiftManagement/openshift";
					});
				}*/
			}).error(function (data, status, header, config) {
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Some Error Occured !!').textContent(data).ok('Ok!').parent(angular.element(document.body)));
			});
		//}
	}
	
	
	
	
	
	
	$scope.showRateDtls= function(){
		$("#rateDtlmyModal").modal('toggle');	
	}
	
	$scope.paymentMode=function(index){
		$scope.card[index]=false;
		$scope.bank[index]=true;
		$scope.company[index]=false;
		$scope.nameBank[index]=true;
		if($scope.payments[index].pmtMode==2){
			/*$("#bankCardTypeDiv").show();
			$("#bankName").hide();
			$("#companyName").hide();*/
			$scope.card[index]=true;
			$scope.bank[index]=false;
			$scope.company[index]=false;

		}
		if($scope.payments[index].pmtMode==3){
			$scope.card[index]=false;
			$scope.bank[index]=true;
			$scope.company[index]=false;
			$scope.nameBank[index]=false;
		}
		
		$scope.corpList = [];
		if($scope.payments[index].pmtMode==5){
		
			$scope.card[index]=false;
			$scope.bank[index]=false;
			$scope.company[index]=true;
			
			$http({
				//url : '../reservation_test/getTACorpList',
				url:'../settlement/getCorporates',
				method : "POST",
				//data : parseInt($scope.payments[index].pmtMode)
			}).then(function(response) {
				$scope.corpList = response.data;
				$scope.payments[index].corporateId = $scope.corpList[0].id.toString();
			});
		}
	}
	$scope.add=function(){
		$scope.deleteShow[($scope.payments.length-1)] =true
		$scope.card[$scope.payments.length]=false;
		$scope.bank[$scope.payments.length]=true;
		$scope.company[$scope.payments.length]=false;
		$scope.nameBank[$scope.payments.length]=true;
		
		var payment={pmtType:$scope.payType[0].pTypeId,amount:"",remarks:"",pmtMode:"1",bankCardType:1,bankName:"",companyName:""};
		$scope.payments.push(payment);
		$scope.deleteShow[($scope.payments.length-1)] =true
		
	}
	$scope.remove=function(index){
/*		if($scope.payments.length ==1){
			return
		}*/
		
		$scope.payments.splice(index,1);
		$scope.card.splice(index,1);
		$scope.bank.splice(index,1);
		$scope.company.splice(index,1);
		$scope.nameBank.splice(index,1);
		if($scope.payments.length ==1){
			$scope.deleteShow[0] = false;
		}
		$scope.amountChange();
	}
	
/*	$scope.balance = parseFloat($("#amtpayable").val());
	$scope.amountChange=function(){
		$scope.addShow=false
		$scope.submitClick=true;
		var amount = 0;
		var payable = window.payment;
		var paymentMode = angular.element("#slctPaymentMode");
		angular.forEach($scope.payments,function(value,index){
			if(value.pmtType == 6 && !canPay){
				payable +=  +value.amount;
			}else if(value.pmtType == 14){
				$scope.payments[index].amount = payable;
				amount = payable;
			    angular.forEach(paymentMode.find("option"), function(value, index){
			        var elem = angular.element(value);
			        if(elem[0].value == "6"){
			        	elem[0].selected = true;
			        	elem[0].disabled = false;
			        }else{
			        	elem[0].disabled = true;
			        }
			      });
			}else{
				if(value.amount !=undefined){
					amount += +value.amount;
				}
				angular.forEach(paymentMode.find("option"), function(value, index){
					var elem = angular.element(value);
					if(elem[0].value == "6"){
						elem[0].selected = false;
						elem[0].disabled = true;
					}else{
						elem[0].disabled = false;
					}
				});
			}
		});
		
		if(amount>payable && canPay){
			$scope.addShow=false
			$scope.submitClick=true;
			$mdDialog.show($mdDialog.alert().clickOutsideToClose(true).title('Alert!!').textContent("Entered amount should less than payable amount").ok('Ok!').parent(angular.element(document.body)));
		}
		else if(amount<payable && canPay){
			$scope.submitClick=true;
			$scope.addShow=true
			$scope.balance = parseFloat($("#amtpayable").val()) - parseFloat(amount);
		}else if(amount==payable){
			$scope.submitClick=false;
			$scope.addShow=false;
			$scope.balance = parseFloat($("#amtpayable").val()) - parseFloat(amount);
		}else if(!canPay){
			$scope.submitClick=true;
			$scope.addShow=true
		}
	}*/
	
/*	$scope.balance = parseFloat($("#amtpayable").val());
	$scope.amountChange=function(index){
		$scope.addShow=false
		$scope.submitClick=true;
		var amount = 0;
		var payable = window.payment;
		var paymentMode = angular.element("#slctPaymentMode");
		 
		if($scope.payments[index].pmtType == 6 && !canPay){
			payable +=  +value.amount;
		}else if($scope.payments[index].pmtType == 14){
			$scope.payments[index].amount = payable;
			amount = payable;
		    angular.forEach(paymentMode.find("option"), function(value, index){
		        var elem = angular.element(value);
		        if(elem[0].value == "6"){
		        	elem[0].selected = true;
		        	elem[0].disabled = false;
		        }else{
		        	elem[0].disabled = true;
		        }
		      });
		}else{
			//$scope.payments[index].amount = 0;
			if($scope.payments[index].amount !=undefined){
				amount += +$scope.payments[index].amount;
			}
			angular.forEach(paymentMode.find("option"), function(value, index){
				var elem = angular.element(value);
				if(elem[0].value == "6"){
					elem[0].selected = false;
					elem[0].disabled = true;
				}else{
					elem[0].disabled = false;
				}
			});
		}
		
		if(amount>payable && canPay){
			$scope.addShow=false
			$scope.submitClick=true;
			$mdDialog.show($mdDialog.alert().clickOutsideToClose(true).title('Alert!!').textContent("Entered amount should less than payable amount").ok('Ok!').parent(angular.element(document.body)));
		}
		else if(amount<payable && canPay){
			$scope.submitClick=true;
			$scope.addShow=true
			$scope.balance = parseFloat($("#amtpayable").val()) - parseFloat(amount);
		}else if(amount==payable){
			$scope.submitClick=false;
			$scope.addShow=false;
			$scope.balance = parseFloat($("#amtpayable").val()) - parseFloat(amount);
		}else if(!canPay){
			$scope.submitClick=true;
			$scope.addShow=true
		}
	}*/
	
	$scope.balance = parseFloat($("#amtpayable").val());
	$scope.amountChange=function(){
		$scope.addShow=false
		$scope.submitClick=true;
		var amount = 0;
		var payable = window.payment;
		var paymentMode = angular.element("#slctPaymentMode");
		angular.forEach($scope.payments,function(value,index){
			if(value.pmtType == 6 && !canPay){
				payable +=  +value.amount;
			}else if(value.pmtType == 14){
				var payment={pmtType:value.pmtType,amount:payable,remarks:"",pmtMode:"6",bankCardType:1,bankName:"",companyName:""};
				$scope.payments= [];
				$scope.payments.push(payment);
				$scope.deleteShow[0]=false;
				//$scope.payments[index].amount = payable;
				amount = payable;
			    angular.forEach(paymentMode.find("option"), function(value, index){
			        var elem = angular.element(value);
			        if(elem[0].value == "6"){
			        	elem[0].selected = true;
			        	elem[0].disabled = false;
			        }else{
			        	elem[0].disabled = true;
			        }
			      });
			}else{
				if(value.amount !=undefined){
					amount += +value.amount;
				}
				angular.forEach(paymentMode.find("option"), function(value, index){
					var elem = angular.element(value);
					if(elem[0].value == "6"){
						elem[0].selected = false;
						elem[0].disabled = true;
					}else{
						elem[0].disabled = false;
					}
				});
			}
		});
		
		if(amount>payable && canPay){
			$scope.addShow=false
			$scope.submitClick=true;
			$mdDialog.show($mdDialog.alert().clickOutsideToClose(true).title('Alert!!').textContent("Entered amount should less than payable amount").ok('Ok!').parent(angular.element(document.body)));
		}
		else if(amount<payable && canPay){
			$scope.submitClick=true;
			$scope.addShow=true
			//$scope.balance = parseFloat($("#amtpayable").val()) - parseFloat(amount);
			$scope.balance =parseFloat(parseFloat($("#amtpayable").val()).toFixed(2) - parseFloat(amount).toFixed(2)).toFixed(2);
		}else if(amount==payable){
			$scope.submitClick=false;
			$scope.addShow=false;
			$scope.balance = parseFloat($("#amtpayable").val()) - parseFloat(amount);
		}else if(!canPay){
			$scope.submitClick=true;
			$scope.addShow=true
		}
	}
	
	$scope.bankCardTypeSelected=function(){
		$scope.selectedBankCard=$('#bankCardTypeid').find(":selected").text();

	}

	
	$http({
		method: "POST",
		url:"../payment/bankCardTypes"
	}).then(function(response) {
		$scope.bankCardTypes = response.data;	
		$scope.bankCardsTypeLength=response.data.length;
		 
	});	
	
	$scope.cancelPopUp = function(){
		$("#rateDtlmyModal").modal('toggle');
	}

}]);



function checkout(folio){
	window.location="../checkOut/checkOutEdit?folioBindNo="+folio;
}