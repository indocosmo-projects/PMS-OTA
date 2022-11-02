var rtype = angular.module('roomTypeTestApp', ['datatables','ngMaterial']);
rtype.controller('roomTypeController',['$scope', '$http','DTOptionsBuilder','DTColumnBuilder','$mdDialog',function ($scope, $http, DTOptionsBuilder, DTColumnBuilder,$mdDialog){	
	$scope.dtInstance = {};
	var imageDir=$("#imgDir").val();
	$scope.editMode=false;
	
	$scope.dtColumns = [
	                    DTColumnBuilder.newColumn("code","Code"),
	                    DTColumnBuilder.newColumn("name","Name"),
	                    DTColumnBuilder.newColumn("description","Description"),
	                    DTColumnBuilder.newColumn("supportSingleOcc","Single Occ").renderWith(function(data,type,full){
	                    	if(data==true){
	                    		return '<i class="fa fa-check tick_grn" aria-hidden="true"></i>'
	                    	}else{
	                    		return '<i class="fa fa-times cros_red" aria-hidden="true"></i>'
	                    	}
	                    	
	                    }),
	                    DTColumnBuilder.newColumn("supportDoubleOcc","Double Occ").renderWith(function(data,type,full){
	                    	if(data==true){
	                    		return '<i class="fa fa-check tick_grn" aria-hidden="true"></i>'
	                    	}else{
	                    		return '<i class="fa fa-times cros_red" aria-hidden="true"></i>'
	                    	}
	                    	
	                    }),
	                    DTColumnBuilder.newColumn("supportTripleOcc","Triple Occ").renderWith(function(data,type,full){
	                    	if(data==true){
	                    		return '<i class="fa fa-check tick_grn" aria-hidden="true"></i>'
	                    	}else{
	                    		return '<i class="fa fa-times cros_red" aria-hidden="true"></i>'
	                    	}
	                    	
	                    }),
	                    DTColumnBuilder.newColumn("supportQuadOcc","Quad Occ").renderWith(function(data,type,full){
	                    	if(data==true){
	                    		return '<i class="fa fa-check tick_grn" aria-hidden="true"></i>'
	                    	}else{
	                    		return '<i class="fa fa-times cros_red" aria-hidden="true"></i>'
	                    	}
	                    	
	                    })
	                    ];
	$scope.dtOptions = DTOptionsBuilder.newOptions()
	.withPaginationType('full_numbers')
	.withDisplayLength(10)
	.withOption('rowCallback', rowCallback)
	.withOption('ajax', {
		url: "../roomType/roomTypeDetails",
		type: "POST",
		error: function(response){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(response.responseText).ok('Ok').parent(angular.element(document.body)));
		}
	});
	 function rowCallback(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
		 $scope.readOnly=false;
		 if(window.cp_isCanEdit){
			 $('td', nRow).unbind('click');
		        $('td', nRow).bind('click', function() {
		        	$scope.readOnly=true;
		        	 $scope.$apply(function() {
		        		 $("#imgshw1").children("img").attr("src","");
		        		 $("#imgshw2").children("img").attr("src","");
		        		 $("#imgshw3").children("img").attr("src","");
		        		 $("#imgshw4").children("img").attr("src","");
		        		
		        		 $scope.isselected=0;
		        		 $("#deleteRoomTypeBtn").show();
		        		 $scope.editMode=true;
		        	 $scope.roomType={id:aData.id,code:aData.code,name:aData.name,description:aData.description,overBookingPrcntg:aData.overBookingPrcntg,supportSingleOcc:aData.supportSingleOcc,supportDoubleOcc:aData.supportDoubleOcc,supportTripleOcc:aData.supportTripleOcc,supportQuadOcc:aData.supportQuadOcc,displayOrder:aData.displayOrder,image1:(aData.image1==undefined?null:aData.image1),image2:(aData.image2==undefined?null:aData.image2),image3:(aData.image3==undefined?null:aData.image3),image4:(aData.image4==undefined?null:aData.image4),baction:'update',disbl:true};
		        	 $scope.roomType.img1Deleted=false;
		        	 $scope.roomType.img2Deleted=false;
		        	 $scope.roomType.img3Deleted=false;
		        	 $scope.roomType.img4Deleted=false;
		        	 
		        	 if($scope.roomType.image1!=null){
		        		 $(".img1_upload_btn").hide();
		        		 $(".img1_change_btn").show();
		        	 }else{
		        		 $(".img1_upload_btn").show();
		        		 $(".img1_change_btn").hide(); 
		        	 }
		        	 if($scope.roomType.image2!=null){
		        		 $(".img2_upload_btn").hide();
		        		 $(".img2_change_btn").show();
		        	 }else{
		        		 $(".img2_upload_btn").show();
		        		 $(".img2_change_btn").hide(); 
		        	 }
		        	 if($scope.roomType.image3!=null){
		        		 $(".img3_upload_btn").hide();
		        		 $(".img3_change_btn").show();
		        	 }else{
		        		 $(".img3_upload_btn").show();
		        		 $(".img3_change_btn").hide(); 
		        	 }
		        	 if($scope.roomType.image4!=null){
		        		 $(".img4_upload_btn").hide();
		        		 $(".img4_change_btn").show();
		        	 }else{
		        		 $(".img4_upload_btn").show();
		        		 $(".img4_change_btn").hide(); 
		        	 }
		        	 var data=$.param({
		     				id: $scope.roomType.id
		     			});
		        		 var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		        			$http.post('../roomType/getAvailRooms', data, config)
		        			.success(function(data,status,headers,config){
		        				$scope.noOfRooms=data.noOfRooms;
		        			}).error(function(data,status,headers,config){
		        				$mdDialog.show($mdDialog.alert()
		        						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		        			});
		        			
		        		 $("#newProvidermyModal").modal({backdrop: "static"});
		        	 })
		        })
		        if($scope.editMode==true){
	       			 $('#roomTypeCode').attr('readonly', 'readonly');
	       		 	}

		 }
	 }
	
	$scope.newRoomtype =function(){
		$scope.readOnly=false;
		$scope.editMode=false;
		$scope.isselected=0;
		$("#deleteRoomTypeBtn").hide();
		$("#saveRoomTypeBtn").show();
		$scope.roomType={id:0,code:"",name:"",description:"",overBookingPrcntg:"0",supportSingleOcc:false,supportDoubleOcc:false,supportTripleOcc:false,supportQuadOcc:false,displayOrder:"0",image1:"",image2:"",image3:"",image4:"",baction:"save",disbl:false};
		$("#newProvidermyModal").modal({backdrop: "static"});
	}
	$scope.isCodeExist=function(){
		var data=$.param({
			code: $scope.roomType.code
		});
		if($scope.editMode == false){
		var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post('../roomType/codeExists', data, config)
		.success(function(data,status,headers,config){
			if(data==true){
				$scope.roomType.code="";
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Code Exists').ok('Ok'));
			$scope.dtInstance.reloadData();}
		}).error(function(data,status,headers,config){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
		});
	}
	}
	$scope.deleteRoomType=function(){
		var confirm=$mdDialog.confirm()
		.title("Alert")
		.textContent("Are you sure you want to delete this room type?").ok('Yes').cancel('No').parent(angular.element(document.body));
		$mdDialog.show(confirm).then(function(){
			var data=$.param({
				id: $scope.roomType.id
			});
			var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../roomType/delete', data, config)
			.success(function(data,status,headers,config){
				if(data.substring(7)=='success'){
					$scope.dtInstance.reloadData();
					$("#newProvidermyModal").modal('toggle');
				}else{
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent('Cannot delete this room type').ok('Ok').parent(angular.element(document.body)));
				}
			}).error(function(data,status,headers,config){
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Operation failed.').textContent(data).ok('Ok').parent(angular.element(document.body)));
			});
		});
			
	}
	
	$scope.refreshData = function(){
		
		var id = $scope.roomType.id;
		if (id == 0) {
			$scope.roomType.code = "";
			$scope.roomType.name = "";
			$scope.roomType.overBookingPrcntg = 0;
			$scope.roomType.displayOrder = 0;
			$scope.roomType.description = "";
			$scope.roomType.supportSingleOcc = false;
			$scope.roomType.supportDoubleOcc = false;
			$scope.roomType.supportTripleOcc = false;
			$scope.roomType.supportQuadOcc = false;
		} else {
			var data = $.param({
		  		id:id
		  	});
			 var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
			$http.post('../roomType/getAvailRooms', data, config)
				.success(function (data, status, headers, config) {
					if(data != ''){ 
						$scope.roomType.name = data.name;
						$scope.roomType.overBookingPrcntg = data.overBookingPrcntg;
						$scope.roomType.displayOrder = data.displayOrder;
						$scope.roomType.description = data.description;
						$scope.roomType.supportSingleOcc = data.supportSingleOcc;
						$scope.roomType.supportDoubleOcc = data.supportDoubleOcc;
						$scope.roomType.supportTripleOcc = data.supportTripleOcc;
						$scope.roomType.supportQuadOcc = data.supportQuadOcc;
					}
				});
		}
	}
	  
	
	$('#roomTypeCode').change(function() {
		
		var code=$('#roomTypeCode').val();
		if(code != '') {
			
			$scope.codeExist(code , "../roomType/codeExists", "roomTypeCode", "Code exists.");
		}
	});
	
	$scope.codeExist = function(value, serverUrl, codeInput, msg){
		
		var data = $.param({
			'code':value
	  	});
		 var config = {headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;','X-Requested-With':'XMLHttpRequest'}}
		$http.post(serverUrl, data, config)
			.success(function(response) {
					if (response == true) {
						$('#' + codeInput).val('');
						$('#' + codeInput + "_check").hide();
						$('#' + codeInput + "_warning").show();
						
						$('#alert_modal').modal('toggle');
						$('#alert_modal_close').click(function(){
							$('#alert_modal').modal('hide');
						});
						return false;
					}else{
						$('#' + codeInput + "_warning").hide();

						if($('#' + codeInput).val() != '') {
							$('#' + codeInput + "_check").show();
						}
					}
			});
	}
	
	$scope.save=function(){
/*		if($scope.roomType.displayOrder == " " || $scope.roomType.overBookingPrcntg == ""){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true)
					.title('Cannot Make Fields Empty !!')
					.textContent(data).ok('Ok')
					.parent(angular.element(document.body)));
		}*/


		if($scope.roomType.supportSingleOcc || $scope.roomType.supportDoubleOcc || $scope.roomType.supportTripleOcc || $scope.roomType.supportQuadOcc ){
			var file1 = $scope.myFile1;
			var file2 = $scope.myFile2;
			var file3 = $scope.myFile3;
			var file4 = $scope.myFile4;
			var data=JSON.stringify($scope.roomType);
			$scope.bAction=$scope.roomType.baction;
			if($scope.bAction=='save'){
				var uploadUrl = "../roomType/save";
			}
			else if($scope.bAction=='update'){
				var uploadUrl = "../roomType/update";
			}
			var fd = new FormData();
			fd.append('file1', file1);
			fd.append('file2', file2);
			fd.append('file3', file3);
			fd.append('file4', file4);
			fd.append('roomTypeJson',data);
			$http.post(uploadUrl,fd,{
				transformRequest : angular.identity,
				headers : {
				'Content-Type' : undefined
				}
				}).success(function(data) {
						if(data.substring(7)=='success'){	
							 $scope.dtInstance.rerender(); 
							$("#newProvidermyModal").modal('toggle');
							/*< 2094 digna 20180622 begin*/
							window.location="";
							/*2094 digna 20180622 end >*/
							$scope.dtInstance.reloadData();
						}
				}).error(function() {
					$mdDialog.show($mdDialog.alert()
							.clickOutsideToClose(true).title('Alert').textContent('Operation failed.').ok('Ok').parent(angular.element(document.body)));
				});
		}
/*		else{
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true).title('Select atleast one occupancy!!').ok('Ok'));
		}*/
		else if($scope.roomType.code == "" || $scope.roomType.name == "" || $scope.roomType.displayOrder == " " || $scope.roomType.overBookingPrcntg == ""){
			$mdDialog.show($mdDialog.alert()
					.clickOutsideToClose(true)
					.title('Please enter value for mandatory fields')
					.textContent(data).ok('Ok')
					.parent(angular.element(document.body)));
		}
		else{
			$mdDialog.show($mdDialog.alert()
				.clickOutsideToClose(true).title('Select atleast one occupancy').ok('Ok'));
		}
	}
	$scope.removeImage =function(img){
		
		if(img=="img1"){
			$scope.myFile1=null;
			$scope.roomType.img1Deleted=true;
			$("#imgshw1 img").remove();
			$(".img1_upload_btn").show();
			$(".img1_change_btn").hide();
		}
		if(img=="img2"){
			$scope.myFile2=null;
			$scope.roomType.img2Deleted=true;
			$("#imgshw2 img").remove();
			$(".img2_upload_btn").show();
			$(".img2_change_btn").hide();
		}
		if(img=="img3"){
			$scope.myFile3=null;
			$scope.roomType.img3Deleted=true;
			$("#imgshw3 img").remove();
			$(".img3_upload_btn").show();
			$(".img3_change_btn").hide();
		}
		if(img=="img4"){
			$scope.myFile4=null;
			$scope.roomType.img4Deleted=true;
			$("#imgshw4 img").remove();
			$(".img4_upload_btn").show();
			$(".img4_change_btn").hide();
		}
	}
	
	
}]);
rtype.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                   switch(element[0].id){
                   	case "fileToUpload1": if(scope.myFile1!=null){
            			$(".img1_upload_btn").hide();
            			$(".img1_change_btn").show();
                   	}
                   	case "fileToUpload2": if(scope.myFile2!=null){
            			$(".img2_upload_btn").hide();
            			$(".img2_change_btn").show();
                   	}
                   	case "fileToUpload3": if(scope.myFile3!=null){
            			$(".img3_upload_btn").hide();
            			$(".img3_change_btn").show();
                   	}
                   	case "fileToUpload4": if(scope.myFile4!=null){
            			$(".img4_upload_btn").hide();
            			$(".img4_change_btn").show();
                   	}
                   		
                   }
                });
            });
        }
    };
}]);