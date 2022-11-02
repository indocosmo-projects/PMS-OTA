pmsApp.controller('resvCtrl', ['$scope','$http','$window','$mdDialog','$log', '$controller', function ($scope, $http,$window,$mdDialog,$log,$controller){
	
	$controller('resvDtlController', {$scope: $scope});
	$scope.dateFormat=$("#dateFormat").val();
	$scope.timeFormat=$("#timeFormat").val();
	$('#imgloader').show();
	$scope.pagination={offset:0,limit:'10'}
	$scope.totalItems = 0;
	$scope.shiftCheck = $("#shiftCount").val();
	$scope.pageChanged = function() {
		$scope.pagination.offset=($scope.bigCurrentPage-1)*parseInt($scope.pagination.limit);
		$scope.loadDataList();
	};
	$scope.itemsPerPage =parseInt($scope.pagination.limit);
	$scope.maxSize=8;
	$scope.bigTotalItems = 0;
	$scope.bigCurrentPage = 1;
	$scope.search={comnSearch:false,advSearch:false,comnSearchValue:"",resvNo:{searchable:false,sortable:true,searchValue:""},resvBy:{searchable:false,sortable:true,searchValue:""},resvDate:{searchable:false,sortable:true,searchValue:null},arrDate:{searchable:false,sortable:true,searchValue:null},resvStatus:{searchable:false,sortable:true,searchValue:""}};
	$scope.sort={sortColumn:'resv_no',order:"desc"};
	$scope.resvStatusCheck = [];
	 $scope.formatDate = function(date){
         var dateOut = new Date(date);
         return dateOut;
   };
	
	$http({
		url:'../reservation_test/reservationStatus',
		method:'POST'
	}).then(function(response){
		$scope.resvNumberStatus = response.data;
		for(i=0;i<$scope.resvNumberStatus.length;i++){
			$scope.resvStatusCheck.push($scope.resvNumberStatus[i].resvStatusNo);
		}
	});
	$scope.loadDataList = function(){
		$('#imgloader').show();
		if($scope.search.resvDate.searchable){$scope.search.resvDate.searchValue=new Date($scope.search.resvDate.searchValue).getTime();}
		if($scope.search.arrDate.searchable){$scope.search.arrDate.searchValue=new Date($scope.search.arrDate.searchValue).getTime();}
		if(!$scope.search.resvNo.searchable && !$scope.search.resvBy.searchable
				&& !$scope.search.resvDate.searchable && !$scope.search.arrDate.searchable && !$scope.search.resvStatus.searchable){
			$scope.search.advSearch=false;
		}
		var data = $.param({listParams:JSON.stringify({search:$scope.search,pagination:$scope.pagination,sort:$scope.sort})});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../reservation_test/getListData', data, config)
		.success(function (data, status, headers, config) {
			$scope.resvList=data;
			$scope.totalItems=$scope.resvList.card.length;
			$scope.bigTotalItems = parseInt($scope.resvList.total);
			$scope.itemsPerPage =parseInt($scope.pagination.limit);
			if($scope.search.resvDate.searchable){$scope.search.resvDate.searchValue=new Date($scope.search.resvDate.searchValue);}
			if($scope.search.arrDate.searchable){$scope.search.arrDate.searchValue=new Date($scope.search.arrDate.searchValue);}
			$scope.resvList.card.forEach(function(resv) {
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
				
				resv.daysLeft = Math.ceil(Math.abs(new Date(resv.arrivalDate).getTime() - new Date($('#hotelDate').val()).getTime()) / (1000 * 3600 * 24));
			});
			
			$('#imgloader').hide();
			
		}).error(function (data, status, header, config){
			$('#imgloader').hide();
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
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
		console.log(typeof $scope.search.resvDate.searchValue);
		if($scope.search.resvBy.searchValue!=null && $scope.search.resvBy.searchValue!=""){
			$scope.search.resvBy.searchable=true;
			$scope.search.advSearch=true;}else{$scope.search.resvBy.searchable=false}
		if($scope.search.resvDate.searchValue!=null && $scope.search.resvDate.searchValue!=""){
			$scope.search.resvDate.searchable=true;$scope.search.advSearch=true;}else{$scope.search.resvDate.searchable=false}
		if($scope.search.arrDate.searchValue!=null && $scope.search.arrDate.searchValue!=""){
			$scope.search.arrDate.searchable=true;$scope.search.advSearch=true;}else{$scope.search.arrDate.searchable=false}
		if($scope.search.resvStatus.searchValue!=null && $scope.search.resvStatus.searchValue!=""){
			$scope.search.resvStatus.searchable=true;$scope.search.advSearch=true;}else{$scope.search.resvStatus.searchable=false}
		$scope.search.comnSearch=false;
		$scope.search.comnSearchValue = "";
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
		}
		$scope.loadDataList();
	}
	$scope.searchOnEnter = function(keyCode){
		if(keyCode == 13){
			$scope.simpleSearch();
		}
	}
	$scope.simpleSearch=function() {
		$scope.search.comnSearch=true;
		$scope.advanceSearchClear();
		$scope.loadDataList();
	}
	$scope.searchBoxClear=function(){
		$scope.search.comnSearchValue = "";
		if($scope.search.comnSearch){
			$scope.loadDataList();	
			$scope.search.comnSearch=false;
		}else if($scope.search.advSearch){
			$scope.advanceSearchClear();
			$scope.loadDataList();	
		}

	}
	$scope.advanceSearchClear=function(){
		$scope.search.advSearch=false;
		$scope.search.resvBy.searchValue="";
		$scope.search.resvBy.searchable=false;
		$scope.search.resvDate.searchValue=null;
		$scope.search.resvDate.searchable=false;
		$scope.search.arrDate.searchValue=null;
		$scope.search.arrDate.searchable=false;
		$scope.search.resvStatus.searchValue="";
		$scope.search.resvStatus.searchable=false;
	}
	$scope.communication=function(resvNo){
		var data= $.param({group:'resvn',id:resvNo});
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../communication/getCommunicationDetail', data, config)
		.success(function (data, status, headers, config) {
			if(data.substring(7)=='success'){
				window.location.href = "../communication/getCommunicationDetailPage";
			}else{alert("error!!");}
		}).error(function (data, status, header, config) {
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Unreccognised error.\nPlease contact Administratorome error occured.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});

		
	}
	
	
	$scope.tools = function (id){
		if($scope.shiftCheck!=0 && $scope.shiftCheck!=undefined){
			window.location = "../reservation_test/tools?reservationNo=" + id;
			}else{
				var confirm = $mdDialog.confirm()
				.title("Shift should be opened before proceeding.").ok('OK').cancel('Cancel').parent(angular.element(document.body));
			$mdDialog.show(confirm).then(function(){
					window.location  = "../shiftManagement/openshift";
			});
			}
	}
	$scope.resvStatus=function(id){
		window.location="../reservation_test/resvCancellation?resrvId="+id;
	}
	$scope.add = function() {
		if($scope.shiftCheck!=0){
		location.href="../reservation_test/newReservation";
		}else{
			var confirm = $mdDialog.confirm()
			.title("Shift should be opened before proceeding.").ok('OK').cancel('Cancel').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
				window.location  = "../shiftManagement/openshift";
		});
		}
	}
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
					.clickOutsideToClose(true).title('Error').textContent("Unreccognised error.\nPlease contact Administrator.").ok('Ok').parent(angular.element(document.body)));
		});
	}

	
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
			}
			else if(roomData.resvStatus==1 && roomData.occStatus==2){
				it.background = "rgb(255, 102, 204)";
			}
			else if(roomData.occStatus==2){
				it.background = "rgb(61, 140, 186)";
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
	
	/*Drop Down menu table*/
	
	/*function determineDropDirection(){
		  $(".dropdown-menu12").each( function(){
		    $(this).css({
		      visibility: "hidden",
		      display: "block"
		    });
		    
		    $(this).parent().removeClass("dropup");
		    
		    if ($(this).offset().top + $(this).outerHeight() > $(window).innerHeight() + $(window).scrollTop()){
		      $(this).parent().addClass("dropup");
		    }
		    
		    $(this).removeAttr("style");
		  });
		}*/

		/*determineDropDirection();

		$(window).scroll(determineDropDirection);*/
	$scope.determineDropDirection=function(){
	var els = angular.element('.dropdown-menu12');
	if(els != undefined){
		/*console.log(els[0].offsetTop);
		console.log(els[0].clientHeight);
		console.log($window.innerHeight);
		console.log($window.pageYOffset);*/
		if(els[0].offsetTop + els[0].clientHeight > $window.innerHeight + $window.pageYOffset){
			els.addClass("dropup");
		}
		
		}
	}
	
	/*$scope.determineDropDirection();
	$(window).scroll($scope.determineDropDirection);*/
	
}]);

