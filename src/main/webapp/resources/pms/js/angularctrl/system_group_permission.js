pmsApp.controller('permissionController', function($scope,$http,$window,$mdDialog) {
	    
	   
		$scope.pageName       = "Setting Up Permission Lists";		 	
		$scope.groupid= $window.groupid;
		
		$scope.groups=[];
		
		/*  REST GET method for Group Permission List */
		$http.get("../permission/getpermissionlistbygroupid?gid="+$scope.groupid).success(function(response) {
			$scope.groups = response; 
			$scope.isview = $scope.isChecked(1);
			$scope.isadd = $scope.isChecked(2);
			$scope.isedit = $scope.isChecked(3);
			$scope.isdelete = $scope.isChecked(4);
			$scope.isexport = $scope.isChecked(5);
			$scope.isexecute = $scope.isChecked(6);
	  	  });
		
		
		
		
		
		
		/*  alert box */	
		 $scope.showAlert = function(msg){    
				$mdDialog.show($mdDialog.alert()
						.clickOutsideToClose(true).title('Saved').textContent(msg).ok('Ok').parent(angular.element(document.body)));
			};
		
		
		
		/*  onClick  view check_box  */	
		$scope.viewChange = function(fnName,index){			 
		 // console.log($scope.groups[index]['functionName']);
			 
			
		 }
		
		
		/*  onClick  Add check_box  */	
		$scope.AddChange = function(fnName,index){
			
			  if(fnName.canView==false){			  
				  fnName.canView=fnName.canAdd;
			  }	
			  
        }
		
		 
		
	
		/* print Check_box toggle function  */
	    $scope.toggleAll = function (id,booleanValue) {   
			  
              var i;
              for (i = 0; i < $scope.groups.length; i++) {
                
            	  for (j = 0; j < $scope.groups[i].functionName.length; j++) {
            	   if(id==1){
            		   console.log(booleanValue);
            		   $scope.groups[i].functionName[j].canView=booleanValue;
            	   }
            	   else if(id==2){
            		   $scope.groups[i].functionName[j].canAdd=booleanValue;
            	   }
            	   else if(id==3){
            		   $scope.groups[i].functionName[j].canEdit=booleanValue;
            	   }
            	   else if(id==4){
            		   $scope.groups[i].functionName[j].canDelete=booleanValue;
            	   }
            	   else if(id==5){
            		   $scope.groups[i].functionName[j].canExport=booleanValue;
            	   }
            	   else if(id==6){
            		   $scope.groups[i].functionName[j].canExecute=booleanValue;
            	   }
            	  }           	           	  
               } 
           };
           
           
           
           
           $scope.isChecked = function(id) {
        	  var chk =  true;
        	   
        	   var ci;
              for (ci = 0; ci < $scope.groups.length; ci++) {
                
            	  for (cj = 0; cj < $scope.groups[ci].functionName.length; cj++) {
            	   if(id==1){     		   
                 		  if( $scope.groups[ci].functionName[cj].canView==false)
                		  return false;    
            	  }
            	   else if(id==2){     		   
              		  if( $scope.groups[ci].functionName[cj].canAdd==false)
             		  return false;    
         	       }
            	   
            	   else if(id==3){     		   
               		  if( $scope.groups[ci].functionName[cj].canEdit==false)
              		  return false;    
          	       }
            	   
            	   else if(id==4){     		   
               		  if( $scope.groups[ci].functionName[cj].canDelete==false)
              		  return false;    
          	       }
            	   
            	   else if(id==5){     		   
               		  if( $scope.groups[ci].functionName[cj].canExport==false)
              		  return false;    
          	       }
            	   
            	   else if(id==6){     		   
               		  if( $scope.groups[ci].functionName[cj].canExecute==false)
              		  return false;    
          	       }
            	   
            	   
            	   
            	   
            	   
            	   
            	   
            	   
               }
              }  
        	   
   		    return chk;
   		  };
           
           
           $scope.submitPermission = function(){	
			    $http.post("../permission/postPermission",$scope.groups).success(function(response) {
				  if(response==1){
					  
					  $scope.showAlert("Permission saved successfully."); 
					// alert("Permission Successfully Saved")
					  $http.get("../permission/getpermissionlistbygroupid?gid="+$scope.groupid).success(function(response) {
							$scope.groups = response; 
							$scope.isview = $scope.isChecked(1);
							$scope.isadd = $scope.isChecked(2);
							$scope.isedit = $scope.isChecked(3);
							$scope.isdelete = $scope.isChecked(4);
							$scope.isexport = $scope.isChecked(5);
							$scope.isexecute = $scope.isChecked(6);
					  	  });
					  
					  
				  }else{
					  $scope.showAlert("You don't have permission to save");
					//  alert("You don't have permission to save");  
					  
				  }
				  
			    });
              
          } 
		
           
           
           /* Toggle group List (hide and show)  */

  		  $scope.hideGroup = function (id) {   		              
                 $("#collapse_"+id).toggle("slow");
             };  
           
           
          
           /* 
            * sample array
           		$scope.groups2 = [
           {"sysgrp_label":"MASTER",
            "functionName":
             [{"name":"System settings","code":"MST_SYST_SETNG","isview":0,"isadd":1},
              {"name":"Department","code":"MST_DEPTMNT","isview":false,"isadd":true}]
           },

           {"sysgrp_label":"RSERVATION",
            "functionName":
             [{"name":"Reservation","code":"MST_SYST_SETNG","isview":true,"isadd":true},
              {"name":"change Arrival Date","code":"MST_SYST_SETNG","isview":true,"isadd":false},
              {"name":"No show","code":"MST_SYST_SETNG","isview":false,"isadd":true},
              {"name":"Cancel","code":"MST_SYST_SETNG","isview":false,"isadd":false}]
           }
           ];*/		
	});













