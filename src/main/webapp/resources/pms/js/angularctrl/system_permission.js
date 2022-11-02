
pmsApp.controller('permissionController', function($scope,$http,$window) {
	    
	   
		$scope.pageName       = "Setting Up Permission Lists";		 			
 	
/*		$scope.groups = [
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
];
		*/
		
		$scope.groups = [];
		
		$http.get("../permission/getpermissionlist").success(function(response) {
			$scope.groups = response;      	  
	  	  });
		
		
		
			
			$scope.viewChange = function(fnName,index, event){		
		/* console.log(fnName.isview);
		 fnName.isadd=fnName.isview;
		 console.log(fnName);
		 console.log($scope.groups[index]['functionName']);*/             
		 }
		
		$scope.AddChange = function(fnName,index, event){
			
			  if(fnName.isViewApplicable==false){
				  
				  fnName.isViewApplicable=fnName.isAddApplicable;
			  }			 
        }
		
		  /* print all items on console  */

		  $scope.checkItems = function (id,booleanValue) {   
			  
              var i;
              for (i = 0; i < $scope.groups.length; i++) {
               /* console.log($scope.groups[i]);
                $scope.groups[i].isViewApplicable=isview;*/
            	  for (j = 0; j < $scope.groups[i].functionName.length; j++) {
            	   if(id==1){
            		   $scope.groups[i].functionName[j].isViewApplicable=booleanValue;
            	   }
            	   else if(id==2){
            		   $scope.groups[i].functionName[j].isAddApplicable=booleanValue;
            	   }
            	   else if(id==3){
            		   $scope.groups[i].functionName[j].isEditApplicable=booleanValue;
            	   }
            	   else if(id==4){
            		   $scope.groups[i].functionName[j].isDeleteApplicable=booleanValue;
            	   }
            	   else if(id==5){
            		   $scope.groups[i].functionName[j].isExportApplicable=booleanValue;
            	   }
            	   else if(id==6){
            		   $scope.groups[i].functionName[j].isExecuteApplicable=booleanValue;
            	   }
            	  
            	   //isExportApplicable
            	  }
            	  
            	  
               } 
           };
		
           
           
           /* hide  */

 		  $scope.hideGroup = function (id) {   		              
                $("#collapse_"+id).toggle("slow");
            };
		
		
	});













