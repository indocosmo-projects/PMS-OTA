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
			
		<div class="companytablelist display">
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Compnay Id</th>
						<th class="tdwidth">Account Name</th>
						<th class="tdwidth">Account Code</th>
						<th class="tdwidth">Contact Person</th>
						<th class="tdwidth">Address</th>
						<th class="tdwidth">City</th>
						<th class="tdwidth">Postal Code</th>
						<th class="tdwidth">State</th>
						<th class="tdwidth">Country</th>
						<th class="tdwidth">Phone</th>
						<th class="tdwidth">Mobile</th>
						<th class="tdwidth">Fax</th>
						<th class="tdwidth">Email</th>
						<th class="tdwidth">Taxid</th>
						<th class="tdwidth">Registration No</th>
						<th class="tdwidth">Is Active</th>
					</tr>
				</thead>
				<tbody class="companybody">
				</tbody>
			</table>
		</div>
	
	</div>  
	
	
	
	<div class="retrievetalist display">
		<h3>Retrieve A Travel Agent </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgtravel" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="travelsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgtravel" style="display:none;width:50%;padding:10px;">
			<strong>No data found!</strong> 
		</div><br/><br/>
		
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="travelagentid">Company Id</label> 
						<select class="form-control travelagentid" id="travelagentid">
							<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
							<option value="3ed9e2f3-4bba-4df6-8d41-ab1b009b6425">3ed9e2f3-4bba-4df6-8d41-ab1b009b6425</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="travelagentname">Enter Room Availability</label> 
				  		  <select class="form-control travelagentname" id="travelagentname">
							<option value="33Comp">33Comp</option>
							<option value="Abc Company">Abc Company</option>
						</select>
					</div>
				</div>
			
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="travelagentcreatedfromdate">Created From Date</label> 
						 <input type="date" class="form-control travelagentcreatedfromdate" id="travelagentcreatedfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="travelagentcreatedtoodate">Created To Date</label> 
						 <input type="date" class="form-control travelagentcreatedtoodate" id="travelagentcreatedtoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="travelagentupdatedfromdate">Updated From Date</label> 
						 <input type="date" class="form-control travelagentupdatedfromdate" id="travelagentupdatedfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="travelagentupdatedtoodate">Updated To Date</label> 
						 <input type="date" class="form-control travelagentupdatedtoodate" id="travelagentupdatedtoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="travelagentisactive">Is Active</label> 
						<select class="form-control travelagentisactive" id="travelagentisactive">
							<option value="0">Active</option>
							<option value="1">InActive</option>
						</select>
					</div>
				</div>
				
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-primary" id="btn-travelagent">Search</button>
     		</div>
		</div>
			
		<div class="travelagenttablelist display">
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Compnay Id</th>
						<th class="tdwidth">Account Name</th>
						<th class="tdwidth">Account Code</th>
						<th class="tdwidth">Contact Person</th>
						<th class="tdwidth">Address</th>
						<th class="tdwidth">City</th>
						<th class="tdwidth">Postal Code</th>
						<th class="tdwidth">State</th>
						<th class="tdwidth">Country</th>
						<th class="tdwidth">Phone</th>
						<th class="tdwidth">Mobile</th>
						<th class="tdwidth">Fax</th>
						<th class="tdwidth">Email</th>
						<th class="tdwidth">Taxid</th>
						<th class="tdwidth">Registration No</th>
						<th class="tdwidth">Commission Plan</th>
						<th class="tdwidth">Commission Value</th>
						<th class="tdwidth">Discount</th>
						<th class="tdwidth">Is Active</th>
					</tr>
				</thead>
				<tbody class="travelagentbody">
				</tbody>
			</table>
		</div>
		
		
	</div>  
	
	
	
	<div class="createtalist display">
		<h3>Create a Travel Agent </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgcreatetravel" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="createtravelsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgcreatetravel" style="display:none;width:50%;padding:10px;">
			<strong>No data found!</strong> 
		</div><br/><br/>
		
		
         <form>
         	<div class="row">
				
			
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="createtravelagentuser">User</label> 
				  		  <select class="form-control createtravelagentuser" id="createtravelagentuser">
							<option value="user123">user123</option>
							<option value="user12345">user12345</option>
						</select>
					</div>
				</div>
			
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="createtravelagentbusiness">Business Name</label> 
						  <select class="form-control createtravelagentbusiness" id="createtravelagentbusiness">
							<option value="GreenTravel">GreenTravel</option>
							<option value="BlueTravel">BlueTravel</option>
						</select>
					</div>
				</div>
				
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="createtravelagentcountry">Country</label> 
						  <select class="form-control createtravelagentcountry" id="createtravelagentcountry">
							<option value="India">India</option>
							<option value="USA">USA</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="createtravelagentemail">Email</label> 
						  <select class="form-control createtravelagentemail" id="createtravelagentemail">
						  	<option value="XX">---SELECT----</option>
							<option value="abc@gmail.com">abc@gmail.com</option>
							<option value="dfg@gmail.com">dfg@gmail.com</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="createtravelagentpercentdiscount">Percent Discount</label> 
						  <select class="form-control createtravelagentpercentdiscount" id="createtravelagentpercentdiscount">
							<option value="10">10</option>
							<option value="20">20</option>
						</select>
					</div>
				</div>
				
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-primary" id="btn-createtravelagent">Create</button>
     		</div>
		</div>
		
		
	</div>  
	

	<div class="retrieveguestlist display">
		<h3>Retrieve Guest </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgguestlist" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="guestlistsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgguestlist" style="display:none;width:50%;padding:10px;">
			<strong>No data found!</strong> 
		</div><br/><br/>
		
			<div class="guestdetailtablelist display">
				<table class="table table-responsive table-bordered">
					<thead>
						<tr>
							<th class="tdwidth">SId</th>
							<th class="tdwidth">Id</th>
							<th class="tdwidth">Contact Person</th>
							<th class="tdwidth">Type</th>
						</tr>
					</thead>
					<tbody class="guestdetailbody">
					</tbody>
				</table>
			</div>
		
		
		
	</div>  
	
	
	
</div>
	