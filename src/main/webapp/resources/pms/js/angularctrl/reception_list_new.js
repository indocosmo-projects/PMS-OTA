pmsApp.service("hkService",function(){
		var rooms=[];
		this.setRooms = function(roomList){
			rooms=roomList; 
		}
		this.getRooms = function(){
			return rooms;
		}
	});
pmsApp.controller('receptionCtrl', ['$scope','$http','$window','$mdDialog','$log','hkService',function ($scope, $http,$window,$mdDialog,$log,hkService){
	


	var hk= this;
	$scope.editMode=false;
	$scope.accountMaster = [];

	$scope.hk_filter = {dirty:true,clean:true,cleaning:true};
	$('#imgloader').show();
	
	$scope.roomTypesList =[{id:0,name:'ALL'}]
	$scope.floorList =[{id:0,name:'ALL'}]
	$scope.floor =0;
	$scope.rmType = 0;
	

	$scope.htlDate=$("#hotelDate").val();
	$scope.roomDtlList =[];
	$scope.selectedIndex=0;
	$scope.systemTtxnType = "";
	
	$scope.dateFormat = $("#dateFormat").val();
	$scope.shiftCheck = $("#shiftCount").val();
	$scope.longStayDays = $("#longStayDays").val();
	//alert($scope.longStayDays)
	$scope.pagination={offset:0,limit:'50',selectedTab:'INH'}
	$scope.totalItems = 0;
	$scope.pageChanged = function() {
		$scope.pagination.offset=($scope.bigCurrentPage-1)*parseInt($scope.pagination.limit);
		$scope.loadDataList();
	};
	
	
	$scope.itemsPerPage =parseInt($scope.pagination.limit);
	$scope.maxSize=8;
	$scope.bigTotalItems = 0;
	$scope.bigCurrentPage = 1;
	$scope.search={comnSearch:false,advSearch:false,comnSearchValue:"",resvNo:{searchable:false,sortable:true,searchValue:""},resvBy:{searchable:false,sortable:true,searchValue:""},resvDate:{searchable:false,sortable:true,searchValue:null},arrDate:{searchable:false,sortable:true,searchValue:null},resvStatus:{searchable:false,sortable:true,searchValue:""}};
	$scope.searchInHouse={comnSearch:false,advSearch:false,comnSearchValue:"",roomNumber:{searchable:false,sortable:true,searchValue:""},customerName:{searchable:false,sortable:true,searchValue:""},checkoutDate:{searchable:false,sortable:true,searchValue:new Date($scope.htlDate)}};
	$scope.sort={sortColumn:'room_number',order:"desc"};
	$scope.$watch(
			"selectedIndex",
			function handleFooChange( newValue, oldValue ) {
				if($scope.selectedIndex==1){
					$scope.sort.sortColumn='resv_no';
				}else if($scope.selectedIndex==0){
					$scope.sort.sortColumn='checkin_no';
					
					
				}
				$scope.loadDataList();
			}
	);
	$scope.selectExpected = function(){
		$scope.selectedIndex = 1;
	}
	$scope.selectHouse = function(){
		$scope.selectedIndex = 0;
	}
	$scope.loadDataList = function(){
		$('#imgloader').show();
		$scope.searchItems={};
		if($scope.selectedIndex==0){
			$scope.pagination.selectedTab='INH';
			if($scope.searchInHouse.checkoutDate.searchable && !("ALL"===$scope.searchInHouse.checkoutDate.searchValue)){$scope.searchInHouse.checkoutDate.searchValue=new Date($scope.searchInHouse.checkoutDate.searchValue).getTime();}
			$scope.searchItems = $scope.searchInHouse;
			if(!$scope.searchInHouse.roomNumber.searchable && !$scope.searchInHouse.customerName.searchable
					&& !$scope.searchInHouse.checkoutDate.searchable){
				$scope.searchItems.advSearch=false;
			}
		}else if($scope.selectedIndex==1){
			$scope.pagination.selectedTab='EXP';
			if($scope.search.resvDate.searchable){$scope.search.resvDate.searchValue=new Date($scope.search.resvDate.searchValue).getTime();}
			if($scope.search.arrDate.searchable){$scope.search.arrDate.searchValue=new Date($scope.search.arrDate.searchValue).getTime();}
			$scope.searchItems = $scope.search;
			if(!$scope.search.resvNo.searchable && !$scope.search.resvBy.searchable
					&& !$scope.search.resvDate.searchable && !$scope.search.arrDate.searchable && !$scope.search.resvStatus.searchable){
				$scope.searchItems.advSearch=false;
			}
		}
		var data = $.param({listParams:JSON.stringify({search:$scope.searchItems,pagination:$scope.pagination,sort:$scope.sort})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reception/getListData', data, config)
		.success(function (data, status, headers, config) {
			if($scope.selectedIndex==1){
				$scope.resvList=data;
				$scope.totalItems=$scope.resvList.card.length;
				$scope.bigTotalItems = parseInt($scope.resvList.total);
				$scope.itemsPerPage =parseInt($scope.pagination.limit);
				if($scope.search.resvDate.searchable){$scope.search.resvDate.searchValue=new Date($scope.search.resvDate.searchValue);}
				if($scope.search.arrDate.searchable){$scope.search.arrDate.searchValue=new Date($scope.search.arrDate.searchValue);}
				$scope.resvList.card.forEach(function(resv) {
					$scope.resvList.card.stayedDays = -1;
					switch (resv.resvStatus) {
					case 1:
						resv.statusClass = 'res_stat-confirmed';
						break;
					case 0:
						resv.statusClass = 'res_stat-unconfirmed';
						break;
					case 4:
						resv.statusClass = 'res_stat-partcheckin';
						break;
					case 5:
						resv.statusClass = 'res_stat-checkin';
						break;
					case 6:
						resv.statusClass = 'res_stat-partcheckout';
						break;
					case 7:
						resv.statusClass = 'res_stat-checkout';
						break;
					case 2:
						resv.statusClass = 'res_stat-cancelled';
						break;
					case 3:
						resv.statusClass = 'res_stat-noshow';
						break;
					}
				});
			}else if($scope.selectedIndex==0){
				$scope.recpList=data;
				var end = $scope.htlDate.split(" ")[0];
				$scope.recpList.card.forEach(function(recp) {
					var start = recp.arrDate;
					// end - start returns difference in milliseconds 
					var diff = new Date(end) - new Date(start);
					// get days
					var days = diff/1000/60/60/24;
					if(parseInt(days)>=parseInt($scope.longStayDays))
						recp.stayedDays = 1;
					else 
						recp.stayedDays = -1;
				});
				$scope.totalItems=$scope.recpList.card.length;
				$scope.bigTotalItems = parseInt($scope.recpList.total);
				$scope.itemsPerPage =parseInt($scope.pagination.limit);
			}
			$('#imgloader').hide();
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});	
	}

	$scope.loadDataList();
	$scope.sortFun = function(){
		if($scope.sort.order == 'asc') {
			$(".list_sort").find('i').removeClass('fa-sort-amount-asc').addClass('fa-sort-amount-desc');
			$scope.sort.order = 'desc';
		} else {
			$(".list_sort").find('i').removeClass('fa-sort-amount-desc').addClass('fa-sort-amount-asc');
			$scope.sort.order = 'asc';
		}
		$scope.pagination.offset=0;
		$scope.bigCurrentPage=1;
		$scope.loadDataList();
	}
	$scope.sortColumnChange = function(){
		$scope.pagination.offset=0;
		$scope.bigCurrentPage=1;
		$scope.loadDataList();
	}
	$scope.advanceSearch = function() {
		if($scope.selectedIndex==1){
			if($scope.search.resvBy.searchValue!=null && $scope.search.resvBy.searchValue!=""){
				$scope.search.resvBy.searchable=true;
				$scope.search.advSearch=true;}else{$scope.search.resvBy.searchable=false;}
			if($scope.search.resvDate.searchValue!=null && $scope.search.resvDate.searchValue!=""){
				$scope.search.resvDate.searchable=true;$scope.search.advSearch=true;}else{$scope.search.resvDate.searchable=false;}
			if($scope.search.arrDate.searchValue!=null && $scope.search.arrDate.searchValue!=""){
				$scope.search.arrDate.searchable=true;$scope.search.advSearch=true;}else{$scope.search.arrDate.searchable=false;}
			if($scope.search.resvStatus.searchValue!=null && $scope.search.resvStatus.searchValue!=""){
				$scope.search.resvStatus.searchable=true;$scope.search.advSearch=true;}else{$scope.search.resvStatus.searchable=false;}
			$scope.search.comnSearch=false;
			$scope.search.comnSearchValue = "";
		}else if($scope.selectedIndex==0){
			if($scope.searchInHouse.roomNumber.searchValue!=null && $scope.searchInHouse.roomNumber.searchValue!=""){
				$scope.searchInHouse.roomNumber.searchable=true;$scope.searchInHouse.advSearch=true;}else{$scope.searchInHouse.roomNumber.searchable=false;}
			if($scope.searchInHouse.customerName.searchValue!=null && $scope.searchInHouse.customerName.searchValue!=""){
				$scope.searchInHouse.customerName.searchable=true;$scope.searchInHouse.advSearch=true;}else{$scope.searchInHouse.customerName.searchable=false;}
			
			if($scope.searchInHouse.checkoutDate.searchValue!=null && $scope.searchInHouse.checkoutDate.searchValue!=""){
				$scope.searchInHouse.checkoutDate.searchable=true;$scope.searchInHouse.advSearch=true;}
			else{
				$scope.searchInHouse.checkoutDate.searchValue = "ALL"
				}
			$scope.searchInHouse.comnSearch=false;
			$scope.searchInHouse.comnSearchValue = "";
			if($scope.searchInHouse.checkoutDate.searchValue != $scope.htlDate){
				$scope.searchInHouse.checkoutDate.searchable=true;
				$scope.searchInHouse.advSearch=true;
			}
		}
		$('#search_fom_div').hide();
		$scope.loadDataList();
	}
	$scope.tagInputClose = function(id) {
		if(id=='resvBy'){
			$scope.search.resvBy.searchValue="";
			$scope.search.resvBy.searchable=false;
		}else if(id=='resvDate'){
			$scope.search.resvDate.searchValue=null;
			$scope.search.resvDate.searchable=false;
		}else if(id=='arrDate'){
			$scope.search.arrDate.searchValue=null;
			$scope.search.arrDate.searchable=false;
		}else if(id=='resvStatus'){
			$scope.search.resvStatus.searchValue="";
			$scope.search.resvStatus.searchable=false;
		}else if(id=='roomNumber'){
			$scope.searchInHouse.roomNumber.searchValue="";
			$scope.searchInHouse.roomNumber.searchable=false;
		}else if(id=='customerName'){
			$scope.searchInHouse.customerName.searchValue="";
			$scope.searchInHouse.customerName.searchable=false;
		}else if(id=='checkOut_date'){
			$scope.searchInHouse.checkoutDate.searchValue=new Date($scope.htlDate);
			$scope.searchInHouse.checkoutDate.searchable=false;
		}
		$scope.loadDataList();
	}
	$scope.searchOnEnter = function(keyCode){
		if(keyCode == 13){
			$scope.simpleSearch();
		}
	}
	$scope.simpleSearch=function() {
		if($scope.selectedIndex==1){
			$scope.search.comnSearch=true;
		}else if($scope.selectedIndex==0){
			$scope.searchInHouse.comnSearch=true;
		}
		$scope.advanceSearchClear();
		$scope.loadDataList();
	}
	$scope.searchBoxClear=function(){
		if($scope.selectedIndex==1){
			$scope.search.comnSearchValue = "";
			if($scope.search.comnSearch){
				$scope.loadDataList();	
				$scope.search.comnSearch=false;
			}else if($scope.search.advSearch){
				$scope.advanceSearchClear();
				$scope.loadDataList();	
			}
		}else if($scope.selectedIndex==0){
			$scope.searchInHouse.comnSearchValue = "";
			if($scope.searchInHouse.comnSearch){
				$scope.loadDataList();	
				$scope.searchInHouse.comnSearch=false;
			}else if($scope.searchInHouse.advSearch){
				$scope.advanceSearchClear();
				$scope.loadDataList();	
			}
		}

	}
	$scope.advanceSearchClear=function(){
		$scope.search.advSearch=false;
		$scope.searchInHouse.advSearch=false;
		$scope.search.resvBy.searchValue="";
		$scope.search.resvBy.searchable=false;
		$scope.search.resvDate.searchValue=null;
		$scope.search.resvDate.searchable=false;
		$scope.search.arrDate.searchValue=null;
		$scope.search.arrDate.searchable=false;
		$scope.search.resvStatus.searchValue="";
		$scope.search.resvStatus.searchable=false;
		$scope.searchInHouse.roomNumber.searchValue="";
		$scope.searchInHouse.customerName.searchValue="";
		$scope.searchInHouse.checkoutDate.searchValue=null;
		$scope.searchInHouse.checkoutDate.searchable=false;
	}
	$scope.tools = function (id){

		if($scope.shiftCheck!=0){
			window.location = "../reservation_test/tools?reservationNo=" + id;
		}else{
			var confirm = $mdDialog.confirm()
			.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../shiftManagement/openshift";
			});
		}

	}
	$scope.add = function() {

		if($scope.shiftCheck!=0){
			location.href="../reception/newCheckin";
		}else{
			var confirm = $mdDialog.confirm()
			.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../shiftManagement/openshift";
			});
		}
	}


	$scope.editCheckin=function(checkinNo,status) {
		if($scope.shiftCheck!=0){
			window.location="../reception/receptionEdit?checkInNo="+checkinNo+"&status="+status;
		}else{
			var confirm = $mdDialog.confirm()
			.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../shiftManagement/openshift";
			});	
		}
	}

	$scope.depositreception=function(checkinNo) {
		if($scope.shiftCheck!=0){

			window.location="../deposit/depositEdit?checkInNo="+encodeURIComponent(checkinNo);
		}else{
			var confirm = $mdDialog.confirm()
			.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../shiftManagement/openshift";
			});	
		}
	}

	$scope.posting =function(checkinNo) {
		if($scope.shiftCheck!=0){
			window.location="../transaction/recieptDetails?checkinNo="+checkinNo;
		}else{
			var confirm = $mdDialog.confirm()
			.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../shiftManagement/openshift";
			});	
		}

	}
	$scope.checkout =function(checkinNo) {
		if($scope.shiftCheck!=0){
			window.location = "../checkOut/checkOutEdit?folioBindNo=" + checkinNo;
		}else{
			var confirm = $mdDialog.confirm()
			.title("Please open the shift").ok('OK').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
				window.location  = "../shiftManagement/openshift";
			});	
		}
	}
	
	//mailConfirmation
	$scope.mailConfirm=function(folioNo){
		var confirm = $mdDialog.confirm()
		.title("Do you want to send the invoice to your mail?").ok('OK').cancel('Cancel').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			
			var printMode=1;var billDtls=1;var mailType=1;
		
			$http({
				url : '../checkOut/invoice',
				method : "GET",
				params :{folioNo:folioNo,printMode:printMode,billDtls:billDtls,mailType:mailType}
			}).then(function(response) {
				console.log(response.data);
			});
	});
	}
	

$scope.printOut = function(folioNo) {
		$mdDialog.show({
			locals:{folioNo:folioNo, chk:false},
			controller: dialogController,
		    templateUrl: 'tabDialog.tmpl.html',
		    parent: angular.element(document.body),
		    clickOutsideToClose:true
		  })
		    
	}

$scope.printOutSeparate = function(folioNo) {
	
	$scope.systemTtxnType = "";
	
	
	
	$http({
		url : '../reception/invoiceTypeList',
		method : "POST",
		params :{folioNo:folioNo}
	}).then(function(response) {
		$scope.accountMaster = response.data;
		
		
		$mdDialog.show({
			locals:{folioNo:folioNo,systemTtxnType:$scope.systemTtxnType, chk:false,accountMaster:$scope.accountMaster,chkInv:true,hideOption:false},
			controller: dialogController2,
		    templateUrl: 'tabDialog.tmpl2.html',
		    parent: angular.element(document.body),
		    clickOutsideToClose:true
		  })
	});
	
	
	    
}


function dialogController2($scope, $mdDialog, folioNo, chk,systemTtxnType,accountMaster,chkInv,hideOption) {
	  $scope.chk = chk;
	  $scope.hideOption = hideOption;
	  $scope.chkInv = chkInv;
	  $scope.accountMaster = accountMaster;
	  $scope.cancel = function() {
	        $mdDialog.cancel();
	    };
	    $scope.showOption = function(chkInv) {
	        $scope.hideOption = chkInv;
	    };
	   
	    $scope.answer = function(answer, prePrint) {
	    	var mailType=0;
	    	var billSeparate = 1
	    	var printMode = 1;
	    	if(prePrint){
	    		printMode = 0;
	    	}
	    	var setsystemTtxnType = systemTtxnType
	    	if($scope.systemTtxnType != "" && $scope.systemTtxnType != undefined){
	    		var type = parseInt($scope.systemTtxnType);
	    	}
	    	
	        if( $scope.hideOption  == false){
	        	$mdDialog.hide(answer);
	        	type = 0;
	        	window.open("../checkOut/invoice2?folioNo="+folioNo+"&printMode="+printMode+"&sysAccType="+type+"&billDtls=1&billSeparate=0&mailType="+mailType+"");
	        }else{
	        	if($scope.systemTtxnType == "" ||$scope.systemTtxnType == undefined){
	        		$("#slctTxnType").addClass('errorType');
	        	}
	        	else{
	        		
	        		$mdDialog.hide(answer);
	        		$("#slctTxnType").removeClass('errorType');
	        		window.open("../checkOut/invoice2?folioNo="+folioNo+"&printMode="+printMode+"&sysAccType="+type+"&billDtls=1&billSeparate=1&mailType="+mailType+"");
	        	}
	        	
	        }
	       
	    };
}


function dialogController3($scope, $mdDialog, folioNo, chk) {
	  $scope.chk = chk;
	  $scope.cancel = function() {
	        $mdDialog.cancel();
	    };
	    
	    $scope.answer = function(answer, prePrint) {
	    	var mailType=0;
	    	var printMode = 1;
	    	if(prePrint){
	    		printMode = 0;
	    	}
	    	
	    	$mdDialog.hide(answer);
	        if(answer == "ok"){
	        	var type = 0;
	        	window.open("../checkOut/invoice2?folioNo="+folioNo+"&printMode="+printMode+"&sysAccType="+type+"&billDtls=1&billSeparate=0&mailType="+mailType+"");
	        }
	    };
}
	
	  function dialogController($scope, $mdDialog, folioNo, chk) {
		  $scope.chk = chk;
		  $scope.cancel = function() {
		        $mdDialog.cancel();
		    };
		    
		    $scope.answer = function(answer, prePrint) {
		    	var mailType=0;
		    	var printMode = 1;
		    	if(prePrint){
		    		printMode = 0;
		    	}
		    	
		    	$mdDialog.hide(answer);
		        if(answer == "no"){
		        	window.open("../checkOut/invoice?folioNo="+folioNo+"&printMode="+printMode+"&billDtls=0&mailType="+mailType+"");
		        }else{
		        	window.open("../checkOut/invoice?folioNo="+folioNo+"&printMode="+printMode+"&billDtls=1&mailType="+mailType+"");
		        }
		    };
	  }
	  
	  $scope.previewPrint=function(folioNo){
		  $mdDialog.show({
				locals:{folioNo:folioNo, chk:true},
				controller: dialogController,
			    templateUrl: 'tabDialog.tmpl.html',
			    parent: angular.element(document.body),
			    clickOutsideToClose:true
			  })
	  }
	  
	  $scope.previewPrintSeparate=function(folioNo){
		  $mdDialog.show({
				locals:{folioNo:folioNo, chk:true},
				controller: dialogController3,
			    templateUrl: 'tabDialog.tmpl3.html',
			    parent: angular.element(document.body),
			    clickOutsideToClose:true
			  })
	  }
	  
	  
	
	$scope.formPrint=function(folioNo){

		window.open("../cform/cformList?folioNo="+folioNo);

	}
	
	//print GRC form
	$scope.printGrcForm=function(recpNo){
		
		window.open("../reception/printGrcForm?recpNo="+recpNo);
	}
	$scope.filterFunction = function(){
		$('#imgloader').show();
		$scope.selectAll=true;
		if(!$scope.hk_filter.dirty || !$scope.hk_filter.clean || !$scope.hk_filter.cleaning){
			$scope.selectAll=false;
			if(!$scope.hk_filter.dirty && !$scope.hk_filter.clean && !$scope.hk_filter.cleaning){
				$scope.hk_filter = {dirty:true,clean:true,cleaning:true};
				$scope.selectAll=true;
			}
		}
		if($scope.selectAll){
			$scope.roomDtlList=hkService.getRooms();
		}else{
			$scope.rooms=hkService.getRooms();
			$scope.roomDtlList=[];
			$scope.rooms.forEach(function(rooms){
				if($scope.hk_filter.dirty){
					if(rooms.hkStatus==1){
					$scope.roomDtlList.push(rooms);
					}
				}
				if($scope.hk_filter.clean){
					if(rooms.hkStatus==3){
					$scope.roomDtlList.push(rooms);
					}
				}
				if($scope.hk_filter.cleaning){
					if(rooms.hkStatus==2){
					$scope.roomDtlList.push(rooms);
					}
				}
			})
		}
		$('#imgloader').hide();
	};
	
	
	$scope.loadRoom = function(){
		$http({
			url : '../housekeeping/hkStatus',
			method : "POST",
			data :{roomType:$scope.rmType,floor:$scope.floor}
		}).then(function(response) {
			hkService.setRooms(response.data);
			$scope.roomDtlList = response.data;
			$scope.buildGridModel({
				background: ""
			});
			$scope.filterFunction();
			$('#imgloader').hide();
			
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.loadRoom();
	


	
	
	

	$scope.loadRoomDetails = function(){
		$http({
			url : '../dashboard/getRoomDetails',
			method : "POST",
		}).then(function(response) {
			$scope.roomDtlList = response.data;
			$scope.buildGridModel({
				background: ""
			});
			$("#roomsmyModal").modal({backdrop: "static"});
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}


	$scope.loadRoomType = function(){
		$http({
			url : '../roomType/getRoomTypes',
			method : "GET",
		}).then(function(response) {
			//$scope.room_type=response.data;	
			angular.forEach(response.data,function(value,index){
				var type= {id:value.id,name:value.name};
				$scope.room_type = response.data;
				//$scope.room_type.push(type);
				$scope.roomTypesList.push(type);
			/*	$scope.resv.hdr.room_type = $scope.room_type[0].id.toString();*/			
					
			});
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.loadRoomType();
	
	$scope.loadFloor = function(){
		$http({
			url : '../room/getFloor',
			method : "GET",
		}).then(function(response) {
			//$scope.room_type=response.data;	
			angular.forEach(response.data,function(value,index){
				var type= {id:value.id,name:value.name};
				$scope.floorList.push(type);
				/*$scope.resv.hdr.floorList = $scope.floorList[0].id.toString();*/
			});
		},function(response) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Operation failed.").ok('Ok').parent(angular.element(document.body)));
		});
	}
	$scope.loadFloor();
	
	
	
	
	$scope.buildGridModel = function(tileTmpl){
		var it;
		var j=0;
		$scope.roomDtlList.forEach(function(roomData){
			roomData.actualDepartDate=new Date(roomData.actualDepartDate);
			roomData.arrivalDate=new Date(roomData.arrivalDate);
			it = angular.extend({},tileTmpl);
			it.span  = { row :1, col : 1 };
			if(roomData.occStatus==1){
				it.background = "rgb(255, 138, 128)";
			}else if(roomData.occStatus==2){
				/*		      it.background = "rgb(62, 103, 123)";
				 */		      it.background = "rgb(61, 140, 186)";

			}

			if(roomData.invStatus==1){
				it.background = "rgb(179, 157, 219)";
			}
			roomData.tile=it;
			j=j+1;
		});
	}	
	$scope.cancelRoomPopUp= function(){
		$("#roomsmyModal").modal("toggle");
	}
}]);

