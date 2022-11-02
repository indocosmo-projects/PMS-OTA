pmsApp.controller('pettyCashheadController',pettyCashheadController);
function pettyCashheadController($scope,$compile,$http,DTOptionsBuilder,DTColumnBuilder,$mdDialog,$filter,$rootScope){
	
	$scope.pettyHdList = { item : [] };
	$scope.pettyHdList.item.push({id:"",categoryId:"",name:"",parent_name:""});
	$scope.pettyList=[];
	
	$http({
		method:'GET',
		url:'../pettycash/pettyHeadList'
	}).success(function(response){
		$scope.pettyHdList.item=response;
	})
	
	$scope.loadPettyHead=function(){
		$http({
			method:'GET',
			url:'../pettycash/selCategory'
		}).success(function(response){
			$scope.pettyList=response;
		})
	}
	
	$scope.petty_edit=function(item){
		$scope.catList=[];
		$("#categoryeditModal").modal('show');
		$(".petty_head_id").prop('disabled',true);
		$scope.loadPettyHead();
		$http({
			method:'GET',
			url:'../pettycash/loadHeadList',
			params:{id:item}
		}).success(function(response){
			angular.forEach(response,function(value,index){
				 $scope.pettyId=value.id;
				 $scope.pettyHeadSub=value.name;
				 $scope.categoryId=value.parentId;
				 $scope.name=value.parent_name;
			})
			
			/* $scope.pettyHdList.item.categoryId=value.parentId;
			 $scope.pettyHdList.item.pettyHeadSub=value.name;
			 $scope.pettyHdList.item.name=value.parent_name;*/
			 //$scope.pettyHdList.item=response;
		/*angular.forEach($scope.pettyHdList.item,function(value,index){
			 if($scope.pettyHdList.item[index].id==item){
				 $scope.pettyHdList.item.pettyId=item;
				 $scope.pettyHdList.item.categoryId=value.parentId;
				 $scope.pettyHdList.item.pettyHeadSub=value.name;
				 $scope.pettyHdList.item.name=value.parent_name;
				// $scope.pettyHdList.item[index].parent_name=value.parent_name;
				
			 }
		});*/
		
		});
	}
	
	$scope.editPetty=function(){
		$(".petty_head_id").prop('disabled',false);
	}
	
	$scope.saveCategory=function(){
		//$scope.loadCategory($scope.name);
		if($scope.description==undefined){
			$scope.description="";
		}
		$scope.pettyHead={id:$scope.pettyId,categoryId:$scope.categoryId,description:$scope.description,head:$scope.pettyHeadSub,parentId:1};
		$http({
			method:'POST',
			url:'../pettycash/saveCategory',
			params:{saveJson:JSON.stringify($scope.pettyHead)}
		}).success(function(response){
			if(response=="success"){
				$("#categoryeditModal").modal('hide');
				location.reload();
			}
		})
	}
	
	
	$scope.loadCategory=function(name){
		angular.forEach($scope.pettyList,function(value,index){
			 if($scope.pettyList[index].name==name){
				 $scope.categoryId=$scope.pettyList[index].id;
			 }
		})	 
	}
	
	$scope.deletePetty=function(item){
		 var confirm = $mdDialog.confirm()
			.title("Delete this Petty Head?")
			.ariaLabel('PMS')		 
			.ok('Yes')
			.cancel('No')
			.parent(angular.element(document.body));		  
			$mdDialog.show(confirm).then(function() {
				$http({
					method:'POST',
					url:'../pettycash/deleteHead',
					params:{id:item}
				}).success(function(response){
					if(response=="success"){
						$("#categoryeditModal").modal('hide');
						
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
												.element(document.body)));
						
						//$('.modal-dialog').hide();
					}
					location.reload();
				})
				
							 
			}, function() {		
				//$("#newPaymentmyModal").modal('toggle');
         }); 	
		
	}
	
	$scope.simpleSearch=function(){
		
		$scope.pettyHdList = { item : [] };
		var searchCritrea=$scope.searchValue;
		$http({
			method:'POST',
			url:'../pettycash/searchCategory',
			data:searchCritrea
		}).success(function(response){
			$scope.pettyHdList.item=response;
			
		})
	}
	
	$scope.searchBoxClear=function(){
		location.reload();
	}
}