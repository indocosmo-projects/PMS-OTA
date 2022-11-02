pmsApp.requires.push('simple-autocomplete');
pmsApp.controller('transferController', ['$compile','$scope','$http','$rootScope','$mdDialog',function ($compile,$scope,$http,$rootScope,$mdDialog){
	$scope.selectedFolio = 0;
	//$scope.room=$scope.searchRoom;
	$scope.sourceSelect = true
	
	//$scope.datasC=[];
	$scope.loadRooms = function(){
		if(window.folioNo!=0){
			$scope.sourceSelect = false
			$rootScope.$emit("loadDataTable",window.folioNo);
		
	
		}else{
			$http({
				method: "GET",
				url:"../transaction/roomDetails"
			}).then(function(response) {
				$scope.datas = response.data;
				/*if(window.folioNo!=0){
					for(y in $scope.datas){
						var idx = $scope.datas[y].folioNO.indexOf(window.folioNo.toString());
						if (idx == 0) {
							$scope.datasC.push($scope.datas[y]);
						}
					}
					$scope.$broadcast('simple-autocomplete:setInput',$scope.datasC[0]);
				}*/	
			});
		}
	}
	$scope.loadRooms();
	/*$scope.onSelect = function(selection) {
		$scope.selectedData = selection;
	};*/
	/*$scope.clearInput = function() {
		$scope.$broadcast('simple-autocomplete:clearInput');
		$scope.selectedData = null;
		$rootScope.$emit("loadDataTable",0);
	};*/
	$scope.search=function(data ){
		/*if($scope.selectedFolio==0){
			var a = 0
		}*/
		if(data==0){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Alert').textContent('Please select room number').ok('Ok').parent(angular.element(document.body)));
			$rootScope.$emit("loadDataTable",0);
		}else{
			$rootScope.$emit("loadDataTable",data);
		}
	}
}]);

pmsApp.controller('transferDataTable', ['$compile','$scope','$q','$http','DTOptionsBuilder','DTColumnBuilder','$rootScope','$mdDialog','$window',function ($compile,$scope,$q,$http, DTOptionsBuilder, DTColumnBuilder,$rootScope,$mdDialog,$window){
	$scope.dateFormat = $("#dateFormat").val();
	$scope.count=window.count;
	$scope.showAlert = function(msg){    
		$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Alert').textContent(msg).ok('Ok').parent(angular.element(document.body)));
	};
	var orgDatas =[]
	var vm = this;
	vm.selected = {};
	vm.selectedStatus = {};
	vm.selectAll = false;
	vm.toggleAll = toggleAll;
	vm.toggleOne = toggleOne;
	vm.dtInstance = {};
	$scope.folioNo=window.folioNo;
	var urlString="../transaction/transactionDetails?folioNo="+$scope.folioNo;
	var titleHtml= '<input type="checkbox" ng-model="showCase.selectAll" ng-click="showCase.toggleAll(showCase.selectAll)"/>';
	$rootScope.$on("loadDataTable", function(event, folioNum){
		$scope.folioNo=folioNum;
		urlString="../transaction/transactionDetails?folioNo="+$scope.folioNo;
		vm.dtInstance.reloadData();
		$scope.destDatas = angular.copy(orgDatas)
		$scope.selectedDataD=null;
		angular.forEach($scope.destDatas,function(value,index){
			if($scope.folioNo==value.folioNO){
				$scope.destDatas.splice(index,1);
			}
			
		});
	});
	vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
		var defer = $q.defer();
		$http.get(urlString).then(function(result) {
			defer.resolve(result.data);	       
		},function(response) {
            $mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed. ').textContent(response.responseText).ok('Ok').parent(angular.element(document.body)));
		});
		return defer.promise;
	}).withOption('createdRow', function(row, data, dataIndex) {
		$compile(angular.element(row).contents())($scope);
	})
	.withOption('headerCallback', function(header) {
		if (!vm.headerCompiled) {
			vm.headerCompiled = true;
			$compile(angular.element(header).contents())($scope);
		}
	}).withDOM('lfrti').withScroller().withOption('deferRender', true).withOption('scrollY', 200).withDisplayLength(10000).withOption('rowCallback', rowCallback);
	function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		if(aData.txnStatus == 0)
		{$('td:eq(5)', nRow).css('color', '#f00');}else if(aData.txnStatus == 1)
		{$('td:eq(5)', nRow).css('color', '#008000');}if(aData.txnStatus == 2)
		{$('td:eq(5)', nRow).css('color', '#04b0c3');}		
		return nRow;
	}
	vm.dtColumns = [DTColumnBuilder.newColumn(null).withTitle(titleHtml).notSortable().renderWith(function(data, type, full,meta) {
		vm.selected[full.id] = false;$scope.count=window.count;
		vm.selectedStatus[full.id]=data.txnStatus;
		if(data.txnStatus == 2||data.txnStatus == 0){
			return '<input type="checkbox" disabled="disabled" ng-model="showCase.selected[' + data.id + ']" ng-click="showCase.toggleOne()" class="newbox">';
		}else{vm.selected[full.id] = false; return '<input type="checkbox"  ng-model="showCase.selected[' + data.id + ']" ng-click="showCase.toggleOne()" class="newbox" >';}
	}).notSortable(),
	DTColumnBuilder.newColumn("id","Txn ID"),
	DTColumnBuilder.newColumn("accName","Transaction Type"),
	DTColumnBuilder.newColumn("txnDate","Txn Date"),
	DTColumnBuilder.newColumn("nettAmount","Amount"),
	DTColumnBuilder.newColumn("txnStatus","Status").renderWith(function(data, type, full) { return payStatus(data,full);}).withOption('width','20%')
	];
	function payStatus(data,full){
		var type="";
		if(data==0){type="DELETED";}
		else if(data==1){type="ACTIVE";}
		else if(data==2){type="TRANSFERED TO "+full.roomName;}
		return type;
	}
	function toggleAll(selectAll) {
		for (var id in vm.selected) {
			if (vm.selected.hasOwnProperty(id)) {
				if(vm.selectedStatus[id]==2||vm.selectedStatus[id]==0){
					vm.selected[id] = false;
				}else{vm.selected[id] = selectAll;}
			}
		}
	}
	function toggleOne () {
		for (var id in vm.selected) {
			if (vm.selected.hasOwnProperty(id)) {
				if(!vm.selected[id]) {
					return;
				}
			}
		}
		vm.selectAll = false;
	}
	$scope.destDatas=[];
	$scope.selectedDataD = null;
	/*$http({
		method: "GET",
		url:"../transaction/roomDetails"
	}).then(function(response) {
		$scope.tempData=response.data;
		if(window.folioNo!=0){
			for(y in $scope.tempData){
				var idx = $scope.tempData[y].folioBindNo.toString().indexOf(window.folioBindNo.toString());
				
				if (idx == 0) {
					$scope.destDatas.push($scope.tempData[y]);
				}
			}
		}else{
			$scope.destDatas = $scope.tempData;
		}
	});*/
	
	$http({
		method: "GET",
		url:"../transaction/roomDetails"
	}).then(function(response) {
		orgDatas = angular.copy(response.data);
		$scope.destDatas=response.data;
		if(window.folioNo!=0){
			angular.forEach($scope.destDatas,function(value,index){
				if(window.folioNo==value.folioNO ){
					$scope.destDatas.splice(index,1);
				}
				
			});
		}
	});
	/*$scope.onSelect = function(selection) {
		$scope.selectedDataD = selection;
	};*/

	/*$scope.clearInput1 = function() {
		$scope.$broadcast('simple-autocomplete:clearInput');
		$scope.selectedDataD = null;
	};*/

	$scope.transfer=function(){
		var selCount="fail";
		for (var id in vm.selected) {
			if (vm.selected.hasOwnProperty(id)) {
				if(vm.selected[id]) {
					selCount="ok"
				}
			}
		}
		if($scope.folioNo==0){
			$scope.showAlert("Select Source Room.");}
		else if(selCount=="fail"){
			$scope.showAlert("Select Atleast One Txn.");}
		else if($scope.count==0){
			$scope.showAlert("Please Open the shift.");
		}
		else if($scope.selectedDataD==null){
			$scope.showAlert("Destination Room Can't be empty.");} 
		else if( $scope.folioNo==$scope.selectedDataD.folioNO){	
			$scope.showAlert("Select Different Destination Room.");
		}else{
			var confirm = $mdDialog.confirm()
			.title("Do you want to Transfer selected Transactions?")
			.textContent("selected Transactions will be Transfered to destination Room").ok('Yes').cancel('No').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function() {
				var data = $.param({
					sourceFolio : $scope.folioNo,
					destFolio : $scope.selectedDataD.folioNO,
					destFolioBind:$scope.selectedDataD.folioBindNo,
					selectedData :JSON.stringify(vm.selected)
				});
		
				var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
				$http.post('../transaction/newTransafer', data, config)
				.success(function (data, status, headers, config) {
					if(data.substring(7)=='success'){
						vm.dtInstance.reloadData();
					}else{alert("error");}
				})
				.error(function (data, status, header, config) {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
				});
			});

		
		}

	}

}]);
function cancel(){
	window.location = "../reception/receptionList";
}