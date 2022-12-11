<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">Others</h3>
		
	<div class="gueststaysstatisticslist display">
		<h3>Retrieve Guest Stays Statistics </h3><br/>

			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Guest Name</th>
						<th class="tdwidth">Guest Email</th>
						<th class="tdwidth">Total Number of Stays</th>
						<th class="tdwidth">First Stay</th>
						<th class="tdwidth">First Reservation No</th>
						<th class="tdwidth">First Folio No</th>
						<th class="tdwidth">Last Stay</th>
						<th class="tdwidth">Last Reservation No</th>
						<th class="tdwidth">Last Folio No</th>
						<th class="tdwidth">Next Stay</th>
						<th class="tdwidth">Next Reservation No</th>
						<th class="tdwidth">Next Folio No</th>
						<th class="tdwidth">Life Time Spending</th>
					</tr>
				</thead>
				<tbody class="guestbody">
				</tbody>
			</table>
		
	</div>  
	
	
	
	<div class="companylist display">
		<h3>Retrieve a Company </h3><br/>
			
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgcompanynames" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="companynamessuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgcompanynames" style="display:none;width:50%;padding:10px;">
			<strong>No data found!</strong> 
		</div><br/><br/>
		
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="companyid">Company Id</label> 
						<select class="form-control companyid" id="companyid">
							<option value="Abc4578lk">Abc4578lk</option>
							<option value="yzx7426kj">yzx7426kj</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="companynames">Enter Room Availability</label> 
				  		  <select class="form-control companynames" id="companynames">
							<option value="33Comp">33Comp</option>
							<option value="Abc Company">Abc Company</option>
						</select>
					</div>
				</div>
			
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="companycreatedfromdate">Created From Date</label> 
						 <input type="date" class="form-control companycreatedfromdate" id="companycreatedfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="companycreatedtoodate">Created To Date</label> 
						 <input type="date" class="form-control companycreatedtoodate" id="companycreatedtoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="companyupdatedfromdate">Updated From Date</label> 
						 <input type="date" class="form-control companyupdatedfromdate" id="companyupdatedfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="companyupdatedtoodate">Updated To Date</label> 
						 <input type="date" class="form-control companyupdatedtoodate" id="companyupdatedtoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="companyisactive">Is Active</label> 
						<select class="form-control companyisactive" id="companyisactive">
							<option value="0">Active</option>
							<option value="1">InActive</option>
						</select>
					</div>
				</div>
				
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-primary" id="btn-company">Search</button>
     		</div>
		</div>
			
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Guest Name</th>
						<th class="tdwidth">Guest Email</th>
						<th class="tdwidth">Total Number of Stays</th>
						<th class="tdwidth">First Stay</th>
						<th class="tdwidth">First Reservation No</th>
						<th class="tdwidth">First Folio No</th>
						<th class="tdwidth">Last Stay</th>
						<th class="tdwidth">Last Reservation No</th>
						<th class="tdwidth">Last Folio No</th>
						<th class="tdwidth">Next Stay</th>
						<th class="tdwidth">Next Reservation No</th>
						<th class="tdwidth">Next Folio No</th>
						<th class="tdwidth">Life Time Spending</th>
					</tr>
				</thead>
				<tbody class="companybody">
				</tbody>
			</table>
	
	</div>  
	
	
	
	<div class="retrievetalist display">
		<h3>Retrieve A Travel Agent </h3><br/>
		
		
		
		
	</div>  
	
	
	
	<div class="createtalist display">
		<h3>Create a Travel Agent </h3><br/>
		
		
		
		
	</div>  
	

	<div class="retrieveguestlist display">
		<h3>Retrieve Guest </h3><br/>
		
		
		
		
	</div>  
	
	
	
</div>
	