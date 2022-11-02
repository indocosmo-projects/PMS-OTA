pmsApp.controller('reportsCtrl', ['$q','$scope','$http','$mdDialog','$filter',function ($q,$scope, $http,$mdDialog,$filter){
	var dateFormat = $("#dateFormat").val();
	$scope.reportSelected={};
	$scope.OpenShift = {};
	$scope.cashier={};
	$scope.OpenShift.Shifts="";
	$scope.selectedRptId = "0";
	$scope.cashier.user="";
	$scope.getMonth="";
	$scope.getYear="";
	$scope.yearhide = true;
	$scope.initial={minDate:new Date(new Date($("#hotelDate").val()).getFullYear(),new Date($("#hotelDate").val()).getMonth(),new Date($("#hotelDate").val()).getDate()+1)};
	$scope.rad={inputGroup:'today',dateFrom:new Date($("#hotelDate").val()),dateTo:$scope.initial.minDate,dateon:new Date($("#hotelDate").val()),datefor:new Date($("#hotelDate").val())};
	$scope.months = [
		{id : "01", month : "JANUARY"},
		{id : "02", month : "FEBRUARY"},
		{id : "03", month : "MARCH"},
		{id : "04", month : "APRIL"},
		{id : "05", month : "MAY"},
		{id : "06", month : "JUNE"},
		{id : "07", month : "JULY"},
		{id : "08", month : "AUGUST"},
		{id : "09", month : "SEPTEMBER"},
		{id : "10", month : "OCTOBER"},
		{id : "11", month : "NOVEMBER"},
		{id : "12", month : "DECEMBER"}
		];
	$scope.isSummary = false;
	var hotel_year= new Date($("#hotelDate").val()).getFullYear();
	$scope.year = [hotel_year,hotel_year-1,hotel_year-2,hotel_year-3,
		hotel_year-4,hotel_year-5,hotel_year-6,hotel_year-7,hotel_year-8,hotel_year-9];
	$scope.reports = [];
	if(window.exp_arr_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:1,header:'Expected Arrival List'});
	}
	if(window.act_arr_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:2,header:'Actual Arrival List'});
	}
	if(window.exp_dep_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:3,header:'Expected Departure List'});
	}
	if(window.act_dep_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:4,header:'Actual Departure List'});
	}
	if(window.inhouse_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:5,header:'In-House guest List'});
	}
	if(window.occupancy_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:18,header:'Occupancy List'});
	}
	if(window.room_per_day_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:21,header:'Rooms Per Day List'});
	}
	if(window.resv_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:22,header:'Plan and Room List'});
	}
	if(window.nationality_statistics_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:11,header:'Country/Nationality Statistics by Month'})
	}
	if(window.Customer_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:23,header:'Guest History Report'})
	}
	if(window.Room_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:25,header:'Room Booking Frequency Report'})
	}
	if(window.CustomerGrading_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:27,header:'Customer Grading Report'});
	}
	if(window.CustomerGrading_isCanView){
		$scope.reports.push({category:1,cat_name:'Reception',id:30,header:'Frequent Guest Analysis Report'});
	}
	if(window.resv_isCanView){
		$scope.reports.push({category:2,cat_name:'Reservation',id:6,header:'Reservation List'});
	}
	if(window.resv_cancel_isCanView){
		$scope.reports.push({category:2,cat_name:'Reservation',id:7,header:'Cancellation Report'});
	}
	if(window.txn_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:8,header:'Transaction List'});
	}
	if(window.txn_trnsfr_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:9,header:'Transfer Transaction List'});
	}
	if(window.deleted_txn_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:14,header:'Transaction Deleted List'});
	}

	if(window.CashRegisterClosure_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:17,header:'Cash Register Closure Report'})
	}
	if(window.dailyRevenue_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:19,header:'Daily Revenue Report'})
	}
	if(window.monthly_closure_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:20,header:'Monthly Closure Report'})
	}
	if(window.Income_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:24,header:'Collection Register Report'})
	}
	if(window.TallyExport_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:28,header:'Tally Export'});
	}
	if(window.ContraList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:32,header:'Contra Export '});
	}
	if(window.CorporateList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:29,header:'Corporate List'});
	}
	if(window.PaymentList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:33,header:'Payment Export'});
	}
	if(window.PettyLedger_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:34,header:'Petty Ledger Export'});
	}
	if(window.PettyList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:31,header:'Petty Cash Expense Report '});
	}
	if(window.PaymenExporttList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:35,header:'Petty Cash Book '});
	}
	
	if(window.CreditCardList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:36,header:'Credit Card Export'});
	}
	
	if(window.CardExporttList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:37,header:'Credit Card Report '});
	}
	
	if(window.DetailRevenueList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:38,header:'Detail Revenue Report '});
	}
	
	if(window.FoodRevenueList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:39,header:'Detail Food Revenue Report '});
	}
	
	if(window.PosRevenueList_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:40,header:'Detail POS Revenue Report '});
	}
	
	
	if(window.B2Breport_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:41,header:'B2B Report'});
	}
	

	if(window.B2Creport_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:42,header:'B2C Report'});
	}
	
	//last added 43
	if(window.DAYENDreport_isCanView){
		$scope.reports.push({category:3,cat_name:'Transaction',id:43,header:'Day End Report'});
	}
	
	
	if(window.folio_isCanView){
		$scope.reports.push({category:4,cat_name:'Folio Balance',id:10,header:'Folio Balance Report'});
	}
	/*if(window.shift_isCanView){
		$scope.reports.push({category:6,cat_name:'Shift',id:12,header:'Shift report'});
	}*/
	if(window.shift_txn_isCanView){
		$scope.reports.push({category:6,cat_name:'Shift',id:13,header:'Shift wise Transaction Report'});
	}
	if(window.shift_txn_trnsfr_isCanView){
		$scope.reports.push({category:6,cat_name:'Shift',id:15,header:'Shift wise Transaction Transfer Report'});
	}
	if(window.Request_isCanView){
		$scope.reports.push({category:7,cat_name:'Request',id:16,header:'Request Report'});
	}
	if(window.CustomerOutstanding_isCanView){
		$scope.reports.push({category:8,cat_name:'Creditors',id:26,header:'Customer Outstanding Report'});
	}
	
	
	
	$scope.showInput = function(rep){
		$scope.reportSelected=rep;
		if($scope.reportSelected.id==6 || $scope.reportSelected.id==13){
			$scope.rad.inputGroup='ondate';
		}else if($scope.reportSelected.id==10){
			$scope.rad.inputGroup='inhouse';
		}else{
			$scope.rad.inputGroup='today';
		}

		if($scope.reportSelected.id==26){
			$scope.getTaCorpList();
		}
		
		if($scope.reportSelected.id==38){
			$scope.getSystemAccMstList();
		}
		

		if($scope.reportSelected.id==39){
			$scope.getAccMstList();
		}
		
		
		
		
		$("#reportmyModal").modal({backdrop: "static"});
	}

	$scope.printReport = function(reportType){
		$scope.validate=true;
		$scope.getMonth = $("#monthList").val();
		$scope.getYear = $("#yearList").val();
		if($scope.getYear==""){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please select year").ok('Ok').parent(angular.element(document.body)));
			return false;
		}
		if($scope.getMonth==""){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please select month").ok('Ok').parent(angular.element(document.body)));
			return false;
		}
		if($scope.rad.inputGroup=='today'){
			$scope.input='';
		}else if($scope.rad.inputGroup=='ondate'){
			$scope.input=$filter('date')(new Date($scope.rad.dateon), dateFormat);
		}else if($scope.rad.inputGroup=='fordate'){
			$scope.input=$filter('date')(new Date($scope.rad.datefor), dateFormat);
		}else if($scope.rad.inputGroup=='datebtwn'){
			$scope.input={dateFrom:$filter('date')(new Date($scope.rad.dateFrom), dateFormat),dateTo:$filter('date')(new Date($scope.rad.dateTo), dateFormat)};
		}else if($scope.rad.inputGroup=='inhouse'){
			$scope.input='';
		}else if($scope.rad.inputGroup=='room'){
			if($scope.rad.room=="" || $scope.rad.room==null){
				$scope.validate=false;
			}
			$scope.input=$scope.rad.room;
		}

		if($scope.reportSelected.id==23 || $scope.reportSelected.id==25 || $scope.reportSelected.id==29){

			$scope.input={dateFrom:$filter('date')(new Date($scope.rad.dateFrom), dateFormat),dateTo:$filter('date')(new Date($scope.rad.dateTo), dateFormat)};
		}
		
		/*if($scope.reportSelected.id==25){

			$scope.input={dateFrom:$filter('date')(new Date($scope.rad.dateFrom), dateFormat),dateTo:$filter('date')(new Date($scope.rad.dateTo), dateFormat)};
		}*/
		if($scope.validate){
			
			$scope.saveData={repId:$scope.reportSelected.id,inpGroup:$scope.rad.inputGroup,input:$scope.input,shiftid:$scope.OpenShift.Shifts,userid:$scope.cashier.user,monthId:$scope.getMonth,yearId:$scope.getYear,reportType:reportType,searchType:$scope.rad.name, corpId:$scope.crpId,reportSelected:$scope.selectedRptId};
			if($scope.crpId == undefined){
				$scope.saveData.corpId=0;
			}
			
			window.open("../report/print?inpData="+JSON.stringify($scope.saveData));
			$("#reportmyModal").modal("toggle");
			$scope.rad.name="";

		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please enter value for mandatory fields").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
		}
		$scope.reportSelected={};
	}
	
	/*document.getElementById("filepicker").addEventListener("change", function(event) {
		  var files = event.target.files;
		  $scope.exportPath = files[0].webkitRelativePath;
		  console.log($scope.exportPath);
		}, false);*/
	
	$scope.tallyExport = function(){
		console.log("here");
		 $('#assignroomBtn').prop('disabled',true);
		 $('#assignroomBtn').html("<i class='fa fa-spinner fa-spin'></i> Exporting");
	//$("#assignroomBtn").click(function(){
		$scope.validate=true;
		$scope.getMonth = $("#monthList").val();
		$scope.getYear = $("#yearList").val();
		if($scope.getYear==""){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please select year").ok('Ok').parent(angular.element(document.body)));
			return false;
		}
		if($scope.getMonth==""){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please select month").ok('Ok').parent(angular.element(document.body)));
			return false;
		}
		if($scope.rad.inputGroup=='today'){
			$scope.input='';
		}else if($scope.rad.inputGroup=='ondate'){
			$scope.input=$filter('date')(new Date($scope.rad.dateon), dateFormat);
		}else if($scope.rad.inputGroup=='fordate'){
			$scope.input=$filter('date')(new Date($scope.rad.datefor), dateFormat);
		}else if($scope.rad.inputGroup=='datebtwn'){
			$scope.input={dateFrom:$filter('date')(new Date($scope.rad.dateFrom), dateFormat),dateTo:$filter('date')(new Date($scope.rad.dateTo), dateFormat)};
		}else if($scope.rad.inputGroup=='inhouse'){
			$scope.input='';
		}else if($scope.rad.inputGroup=='room'){
			if($scope.rad.room=="" || $scope.rad.room==null){
				$scope.validate=false;
			}
			$scope.input=$scope.rad.room;
		}

		if($scope.reportSelected.id==23 || $scope.reportSelected.id==25){

			$scope.input={dateFrom:$filter('date')(new Date($scope.rad.dateFrom), dateFormat),dateTo:$filter('date')(new Date($scope.rad.dateTo), dateFormat)};
		}

		if($scope.validate){

			$scope.saveData={exportPath:$scope.exportPath, repId:$scope.reportSelected.id,inpGroup:$scope.rad.inputGroup,input:$scope.input,shiftid:$scope.OpenShift.Shifts,userid:$scope.cashier.user,monthId:$scope.getMonth,yearId:$scope.getYear,searchType:$scope.rad.name, corpId:$scope.crpId};
			if($scope.crpId == undefined){
				$scope.saveData.corpId=0;
			}
			if($scope.selectedRptId == undefined || $scope.selectedRptId == "" ){
				$scope.saveData.selectedRptId="0";
			}
      if($scope.reportSelected.id==28){
			$http({
				url :"../report/tallyExport",
				method :"GET",
				params : {inpData : JSON.stringify($scope.saveData)}
			}).then(function(response){
				if(response){
					download(response.data, "tallyExport.txt", "application/txt" );
					$("#saveAsModal").modal("hide");
					$("#reportmyModal").modal("hide");
					$scope.rad.name="";
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent("File downloaded successfully.").ok('Ok').parent(angular.element(document.body)));
					
					
				}
				
			});
      }
		if($scope.reportSelected.id==29){
			$http({
				url :"../report/tallyExport",
				method :"GET",
				params : {inpData : JSON.stringify($scope.saveData)}
			}).then(function(response){
				if(response){
				console.log(response);
					download(response.data, "corporate.txt", "application/txt" ); 
					$("#saveAsModal").modal("toggle");
					$("#reportmyModal").modal("toggle");
					$scope.rad.name="";
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent("File downloaded successfully.").ok('Ok').parent(angular.element(document.body)));
					
					
				}
				
			});
		}	
			//window.open("../report/tallyExport?inpData="+JSON.stringify($scope.saveData));
			
			//$("#saveAsModal").modal("toggle");
		//	$("#reportmyModal").modal("toggle");
			
		if($scope.reportSelected.id==32){
			$http({
				url :"../report/tallyExport",
				method :"GET",
				params : {inpData : JSON.stringify($scope.saveData)}
			}).then(function(response){
				if(response){
				console.log(response);
					download(response.data, "contra.txt", "application/txt" ); 
					$("#saveAsModal").modal("toggle");
					$("#reportmyModal").modal("toggle");
					$scope.rad.name="";
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent("File downloaded successfully.").ok('Ok').parent(angular.element(document.body)));
					
					
				}
				
			});
		}	
		if($scope.reportSelected.id==33){
			$http({
				url :"../report/tallyExport",
				method :"GET",
				params : {inpData : JSON.stringify($scope.saveData)}
			}).then(function(response){
				if(response){
				console.log(response);
					download(response.data, "payment.txt", "application/txt" ); 
					$("#saveAsModal").modal("toggle");
					$("#reportmyModal").modal("toggle");
					$scope.rad.name="";
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent("File downloaded successfully.").ok('Ok').parent(angular.element(document.body)));
					
					
				}
				
			});
		}
		if($scope.reportSelected.id==34){
			$http({
				url :"../report/tallyExport",
				method :"GET",
				params : {inpData : JSON.stringify($scope.saveData)}
			}).then(function(response){
				if(response){
				console.log(response);
					download(response.data, "petty_ledger.txt", "application/txt" ); 
					$("#saveAsModal").modal("toggle");
					$("#reportmyModal").modal("toggle");
					$scope.rad.name="";
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent("File downloaded successfully.").ok('Ok').parent(angular.element(document.body)));
					
					
				}
				
			});
		}
		
		
		if($scope.reportSelected.id==36){
			$http({
				url :"../report/tallyExport",
				method :"GET",
				params : {inpData : JSON.stringify($scope.saveData)}
			}).then(function(response){
				if(response){
				console.log(response);
					download(response.data, "icici_credit_card.txt", "application/txt" ); 
					$("#saveAsModal").modal("toggle");
					$("#reportmyModal").modal("toggle");
					$scope.rad.name="";
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent("File downloaded successfully.").ok('Ok').parent(angular.element(document.body)));
					
					
				}
				
			});
		}
		
		
		}else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent("Please enter value for mandatory fields").ok('Ok').cancel('Cancel').parent(angular.element(document.body)));
		}
		
		
		if($scope.reportSelected.id!=28 && $scope.reportSelected.id!=29){
			$scope.reportSelected={};
		}
	}
	
	
	
	
	$http({
		method: "POST",
		url:"../shift/shiftDetails"
	}).then(function(response) {
		$scope.ShiftsDetail = response.data;
		$scope.ShiftsDetail.splice(0,0,{id : "" ,code : "All"});

		$scope.OpenShift.Shifts="";

	});
	
	
	$http({
		method: "POST",
		url:"../users/getUser"
	}).then(function(response) {
		$scope.ShiftsCashier = response.data;
		$scope.ShiftsCashier.splice(0,0,{id : "" ,loginId : "All"});
		$scope.cashier.user="";
	});
	$scope.cancelReportPopUp = function(){
		$("#reportmyModal").modal("toggle");
		$scope.rad.inputGroup='today';
		$scope.reportSelected={};
	}
	$scope.testModel=function()
	{
		alert($scope.OpenShift.Shifts);
		alert($scope.cashier.user);
	}
	$scope.fromDateChange = function(){
		$scope.rad.dateTo = new Date(new Date($scope.rad.dateFrom).getFullYear(),new Date($scope.rad.dateFrom).getMonth(),new Date($scope.rad.dateFrom).getDate());
		$scope.initial.minDate=$scope.rad.dateTo;
	}
	$scope.selectMonth = function(){
		$scope.test=$scope.getMonth;
	}
	$scope.changeCorp = function(crpId) {
		$scope.crpId = crpId;
	}
	$scope.changeReportVal = function(selectedRptId) {
		$scope.selectedRptId = selectedRptId;
	}
	
	
	$scope.getSystemAccMstList = function(){
		$http({
			url : '../reception/getSystemAccMstList',
			method : "GET",
		}).then(function(response) {
			$scope.accMstList = [];
			$scope.systemAccList = [{id:0,name:'All'}];
			for(var i=0; i<response.data.length; i++){
				$scope.systemAcc = {id:response.data[i].id, name:response.data[i].description};
				$scope.systemAccList.push($scope.systemAcc);
			}
			$scope.reportType = $scope.systemAccList;
			$scope.selectedRptId = $scope.reportType[0].id.toString();
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});

	}
	
	
	
	$scope.getAccMstList = function(){
		$http({
			url : '../reception/getAccMstList',
			method : "GET",
		}).then(function(response) {
			$scope.systemAccList = [];
			$scope.year = [];
			$scope.months = [];
			$scope.accMstList = response.data;
			$scope.reportType = $scope.accMstList;
		/*	for(var i=0; i<response.data.length; i++){
				$scope.accMst = {id:response.data[i].id, name:response.data[i].name};
				$scope.accMstList.push($scope.accMst);
			}*/
			$scope.selectedRptId = $scope.accMstList[0].id.toString();
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});

	}
	
	
	$scope.getTaCorpList = function(){
		$http({
			url : '../reservation_test/getTACorpList',
			method : "POST",
			data : 1
		}).then(function(response) {
			$scope.corporates = [{id:0, name:'All'}];
			for(var i=0; i<response.data.length; i++){
				$scope.corp = {id:response.data[i].id, name:response.data[i].name};
				$scope.corporates.push($scope.corp);
			}
			$scope.crpId = $scope.corporates[0].id.toString();
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});

	}
}]);


