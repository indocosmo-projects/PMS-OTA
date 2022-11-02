var settlementApp = angular.module('settlement', ['ngMaterial','ui.bootstrap']);
settlementApp.controller('settlementController', ['$scope','$http','$mdDialog','$window', '$timeout', function ($scope,$http,$mdDialog,$window, $timeout){
	var hdate = $("#hotelDate").val();
	$scope.hotelDate =new Date(hdate);
	$scope.dateFormat = $("#dateFormat").val();
	$scope.hotelDate = new Date($scope.hotelDate.getFullYear(),$scope.hotelDate.getMonth(),$scope.hotelDate.getDate()).toLocaleDateString();
	$scope.startDate = new Date($scope.hotelDate);
	$scope.endDate = new Date($scope.hotelDate);
	$scope.pagination={offset:0, limit:'10'};
	$scope.voucherNo = 0;
	$scope.update = false;
	
	$scope.pageChanged = function() {
		$scope.pagination.offset=($scope.bigCurrentPage-1)*parseInt($scope.pagination.limit);
		$scope.getSettlementList();
	};
	$scope.changeLimit = function(limit) {
		$scope.pagination={offset:0, limit:limit};
		$scope.getSettlementList();
	};
	$scope.itemsPerPage =parseInt($scope.pagination.limit);
	$scope.maxSize=8;
	$scope.bigTotalItems = 0;
	$scope.bigCurrentPage = 1;
		
	$timeout(function(){
		$scope.corpList = [{id:0, name:'All'}];
		$http({
			url : '../settlement/getCorporates',
			method : "POST"
		}).then(function(response) {
			for(var i=0; i<response.data.length; i++){
				$scope.corpList.push(response.data[i]);
			}
			$scope.searchCorp = $scope.corpList[0].id.toString();
			$scope.getSettlementList($scope.startDate, $scope.endDate, $scope.searchCorp);
		});
	},250);
	
	$scope.getSettlementList = function(startDate, endDate, searchCorp){
		$http({
			url : "../settlement/settlementList",
			method : "POST",
			params : {searchData:{startDate:new Date($scope.startDate.getTime() + 86400000), endDate:new Date($scope.endDate.getTime() + 86400000), searchCorp:$scope.searchCorp, pagination:$scope.pagination}}
		}).then(function(response){
			$scope.settlementList = [];
			for(var i=0; i<response.data.length; i++){
				if(i != 0){
					$scope.settlementList.push(response.data[i]);
				}
			}
			$scope.totalItems=$scope.settlementList.length;
			$scope.bigTotalItems = parseInt(response.data[0].count);
			$scope.itemsPerPage =parseInt($scope.pagination.limit);
		});
	}

	$scope.newPayment = function(){
		$scope.update = false;
		$scope.resetPayment();
	}
	
	$scope.editPayment = function(index, event){
		$scope.rowIndex = index;
		if(new Date($scope.settlementList[index].payment_date).toDateString() == new Date($scope.hotelDate).toDateString()){
			if(!event.toElement.className.includes("td_alloc")){
				$scope.id = $scope.settlementList[index].id;
				$scope.voucherNo = $scope.settlementList[index].voucher_no;
				$scope.date = new Date($scope.settlementList[index].payment_date);
				$scope.corporate = $scope.settlementList[index].corporate_id.toString();
				$scope.payment_amount = $scope.settlementList[index].payment_amount;
				$scope.paymentMode = $scope.settlementList[index].payment_type.toString();
				$scope.refNo = $scope.settlementList[index].reference_no;
				$scope.refDate = new Date($scope.settlementList[index].reference_date);
				$scope.refName = $scope.settlementList[index].reference_name;
				$scope.remarks = $scope.settlementList[index].remarks;
				$scope.changeCorporate($scope.settlementList[index].corporate_id);
				$scope.update = true;
				
				angular.element('#newPaymentModal').modal('show');
			}
		}
	}
	
	$scope.selectAll = function(){
		if($scope.chk_all){
			for(var i=0; i<$scope.invoiceList.length; i++){
					$scope.invoiceList[i].chk_inv = true;
					$scope.invoiceList[i].disableNewPayment = false;
			}
		}else{
			for(var i=0; i<$scope.invoiceList.length; i++){
				$scope.invoiceList[i].chk_inv = false;
				$scope.invoiceList[i].settle_amount = 0;
				$scope.invoiceList[i].disableNewPayment = true;
				$scope.totalPayment = 0;
			}
		}
	}

	$scope.selectOneInv = function(index){
		if($scope.invoiceList[index].chk_inv){
			$scope.invoiceList[index].disableNewPayment = false;
		}else{
			$scope.chk_all = false;
			if($scope.totalPayment == undefined){
				$scope.totalPayment = 0;
			}
			$scope.totalPayment = (parseFloat($scope.totalPayment) - parseFloat($scope.invoiceList[index].settle_amount)).toFixed(2);
			$scope.invoiceList[index].settle_amount = 0;
			$scope.invoiceList[index].disableNewPayment = true;
		}
	}
	
	$scope.getTotalPayment = function(index, e){
		var flag = 0;
		if ($.inArray(e.keyCode, [ 46, 8, 9, 27, 13, 110, 190 ]) !== -1
            ||(e.keyCode == 65 && e.ctrlKey === true) || (e.keyCode >= 35 && e.keyCode <= 39)) {
			flag = 0;
		}
		if (e.shiftKey || e.keyCode < 48 || e.keyCode > 57
				&& e.keyCode < 96 || e.keyCode > 105) {
			flag = 1;
		}
		if(flag == 1){
			$scope.invoiceList[index].settle_amount = $scope.invoiceList[index].settle_amount.replace(/[^0-9.]/g, '');
		}
		
		$scope.totalPayment = 0;
		for(var i=0; i<$scope.invoiceList.length; i++){
			if($scope.invoiceList[i].settle_amount == undefined || $scope.invoiceList[i].settle_amount == ""){
				$scope.invoiceList[i].settle_amount = 0;
			}
			$scope.totalPayment += parseFloat($scope.invoiceList[i].settle_amount);
			if(parseFloat(parseFloat($scope.paymentAmount).toFixed(2)) < parseFloat($scope.totalPayment.toFixed(2))){
				alert("Enter amount not more than the payment amount");
				$scope.totalPayment = $scope.totalPayment - $scope.invoiceList[i].settle_amount;
				$scope.invoiceList[i].settle_amount = 0;
				break;
			}else if(parseFloat(parseFloat($scope.invoiceList[i].settle_amount).toFixed(2)) > parseFloat(parseFloat($scope.invoiceList[i].balance).toFixed(2))){
				alert("Enter amount not more than the invoice amount");
				$scope.totalPayment = $scope.totalPayment - $scope.invoiceList[i].settle_amount;
				$scope.invoiceList[i].settle_amount = 0;
				break;
			}
		}
			
		$scope.totalPayment = $scope.totalPayment.toFixed(2);
	}
	
	$scope.allocateAmount = function(index){
			$scope.chk_all = false;
			$scope.headerId = $scope.settlementList[index].id;
			$scope.customerName = $scope.settlementList[index].corporate_name;
			$scope.allocDate = $scope.settlementList[index].payment_date;
			$scope.voucher = $scope.settlementList[index].voucher_no;
			$scope.paymentAmount = parseFloat($scope.settlementList[index].payment_amount) - parseFloat($scope.settlementList[index].allocate_amount);
			$scope.totalInvAmt = 0;
			$scope.totalPaid = 0;
			$scope.totalPayable = 0;
			
			$scope.invoiceList = [];
			$http({
				url:"../settlement/settlementDetails",
				method:"POST",
				params:{corporate:$scope.settlementList[index].corporate_id}
			}).then(function(response){
				$scope.invoiceList = [];
				for(var i=0; i<response.data.length; i++){
					if(response.data[i].balance != 0){
						$scope.invoiceList.push(response.data[i]);
					}
				}
				for(var j=0; j<$scope.invoiceList.length; j++){
					if(!$scope.invoiceList[j].chk_inv){
						$scope.invoiceList[j].chk_inv = false;
						$scope.invoiceList[j].disableNewPayment = true;
					}
					if($scope.invoiceList[j].settle_amount == undefined){
						$scope.invoiceList[j].settle_amount = 0;
					}
					$scope.invoiceList[j].invoiceAmount = parseFloat($scope.invoiceList[j].invoiceAmount).toFixed(2);
					$scope.invoiceList[j].paidAmount = parseFloat($scope.invoiceList[j].paidAmount).toFixed(2);
					$scope.invoiceList[j].balance = parseFloat($scope.invoiceList[j].balance).toFixed(2);
					
					$scope.totalInvAmt += parseFloat($scope.invoiceList[j].invoiceAmount);
					$scope.totalPaid += parseFloat($scope.invoiceList[j].paidAmount);
					$scope.totalPayable += parseFloat($scope.invoiceList[j].balance);
				}
				$scope.totalInvAmt = $scope.totalInvAmt.toFixed(2);
				$scope.totalPaid = $scope.totalPaid.toFixed(2);
				$scope.totalPayable = $scope.totalPayable.toFixed(2);
			});
			
	}
	
	$scope.printReceipt = function(voucherNo){
		var confirm = $mdDialog.confirm()
		 .textContent("Do you want to print a receipt?").ok('Yes').cancel('No').parent(angular.element(document.body));
		 $mdDialog.show(confirm).then(function(){
			window.open("../settlement/printReceipt?voucherNo="+voucherNo);
		});
	}
	
	$scope.resetPayment = function(){
		if(!$scope.update){
			$scope.date = new Date($scope.hotelDate);
			$scope.corporate = $scope.corpList[1].id.toString();
			$scope.outstanding = 0;
			$scope.payment_amount = 0;
			$scope.paymentMode = "1";
			$scope.refNo = "";
			$scope.refDate = new Date($scope.hotelDate);
			$scope.refName = "";
			$scope.remarks = "";
			$scope.changeCorporate($scope.corpList[0].id);
			$scope.update = false;
		}else{
			var index = $scope.rowIndex;
			$scope.id = $scope.settlementList[index].id;
			$scope.voucherNo = $scope.settlementList[index].voucher_no;
			$scope.date = new Date($scope.settlementList[index].payment_date);
			$scope.corporate = $scope.settlementList[index].corporate_id.toString();
			$scope.payment_amount = $scope.settlementList[index].payment_amount;
			$scope.paymentMode = $scope.settlementList[index].payment_type.toString();
			$scope.refNo = $scope.settlementList[index].reference_no;
			$scope.refDate = new Date($scope.settlementList[index].reference_date);
			$scope.refName = $scope.settlementList[index].reference_name;
			$scope.remarks = $scope.settlementList[index].remarks;
			$scope.changeCorporate($scope.settlementList[index].corporate_id);
			$scope.update = true;
		}
	}
	
	$scope.changeCorporate = function(corporate){
		$http({
			url:"../settlement/getOutstanding",
			method:"GET",
			params:{corporate:corporate}
		}).then(function(response){
			$scope.outstanding = response.data;
		});
	}
	
	$scope.saveNew = function(){
		var corporate_name = "";
		for(var i=0; i<$scope.corpList.length; i++){
			if($scope.corpList[i].id == $scope.corporate){
				corporate_name = $scope.corpList[i].name;
			}
		}
			
		if(!$scope.update){
			if($scope.date == null){
				alert("Select a date");
			}
			$scope.saveData = {voucherNo:$scope.voucherNo, date:new Date($scope.date.getTime() + 86400000), corporateId:$scope.corporate, corporateName:corporate_name,
					paymentAmount:$scope.payment_amount, paymentMode:$scope.paymentMode, refNo:$scope.refNo, refDate:new Date($scope.refDate.getTime() + 86400000), refName:$scope.refName, remarks:$scope.remarks};
			if($scope.payment_amount > 0){
				$http({
					url:"../settlement/saveNew",
					method:"POST",
					params:{saveData:$scope.saveData}
				}).then(function(response){
					if(response.data){
						var confirm = $mdDialog.alert()
						.title("New payment added successfully").ok('OK').parent(angular.element(document.body));
						$mdDialog.show(confirm).then(function(){
							angular.element('#newPaymentModal').modal('hide');
							$scope.getSettlementList();
						});
					}
				});
			}else{
				alert("Payment amount can not be zero");
			}
			
		}else{
			if($scope.date == null){
				alert("Select a date");
			}
			$scope.saveData = {voucherNo:$scope.voucherNo, date:$scope.date, corporateId:$scope.corporate, corporateName:corporate_name,
					paymentAmount:$scope.payment_amount, paymentMode:$scope.paymentMode, refNo:$scope.refNo, refDate:$scope.refDate, refName:$scope.refName, remarks:$scope.remarks};
			$scope.saveData.id = $scope.id;
			
			if($scope.payment_amount > 0){
				$http({
					url:"../settlement/updatePayment",
					method:"POST",
					params:{saveData:$scope.saveData}
				}).then(function(response){
					if(response.data){
						var confirm = $mdDialog.alert()
						.title("New payment updated successfully").ok('OK').parent(angular.element(document.body));
						$mdDialog.show(confirm).then(function(){
							angular.element('#newPaymentModal').modal('hide');
							$scope.getSettlementList();
						});
					}
				});
			}else{
				alert("Payment amount can not be zero");
			}		
		}
	}
	
	$scope.disableMsg = false;
	$scope.saveAllocation = function(){
		$scope.allocationList = [];
		for(var i=0; $scope.invoiceList.length; i++){
			if($scope.invoiceList[i].chk_inv && $scope.invoiceList[i].settle_amount != "0"){
				$scope.allocationList.push($scope.invoiceList[i]);
				$scope.disableMsg = false;
			}else if($scope.invoiceList[i].chk_inv && $scope.invoiceList[i].settle_amount == "0"){
				$scope.disableMsg = true;
				break;
			}
			
			if(i == ($scope.invoiceList.length) - 1){
				break;
			}
		}

			if($scope.allocationList.length != 0){
				$scope.detailData = {detail:$scope.allocationList, hdrId:$scope.headerId};
				$http({
					url:"../settlement/saveAllocation",
					method:"POST",
					params:{settlement:$scope.detailData}
				}).then(function(response){
					if(response.data){
						var confirm = $mdDialog.alert()
						.title("Sttlement saved successfully").ok('OK').parent(angular.element(document.body));
						$mdDialog.show(confirm).then(function(){
							$scope.getSettlementList($scope.startDate, $scope.endDate, $scope.searchCorp);
						});
					}
				});
			}
	}
	
}]);


