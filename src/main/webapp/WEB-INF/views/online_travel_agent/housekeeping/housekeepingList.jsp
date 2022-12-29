<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">House Keeping</h3>
	
	
	<div class="inhouseroomstatuslist display">
		<h3>Retrieve Inhouse Room Status </h3><br/>
		
		
		<ul class="nav nav-pills roominfolist" >
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnroomlist">Room List</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnguestlist">Check In Guest List</button>
			</li>
		</ul>


		<div class="roomlist display">
			<h4>Room List</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Room Id</th>
						<th class="tdwidth">Room Name</th>
						<th class="tdwidth">Room Type Name</th>
						<th class="tdwidth">HK Status</th>
						<th class="tdwidth">Room Status</th>
					</tr>
				</thead>
				<tbody class="roombody">
				</tbody>
			</table>
		</div>
		
		<div class="guestlist display">
			<h4>Check In Guest List</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Reservation No</th>
						<th class="tdwidth">Guest Name</th>
						<th class="tdwidth">Email</th>
						<th class="tdwidth">Address</th>
						<th class="tdwidth">Room</th>
						<th class="tdwidth">Booking Date</th>
						<th class="tdwidth">Checkin Date</th>
						<th class="tdwidth">Checkout Date</th>
						<th class="tdwidth">Booking Status</th>	
					</tr>
				</thead>
				<tbody class="guestbody">
				</tbody>
			</table>
		</div>
			
	</div>  
	
	
	<div class="updateroomstatuslist display">
		<h3>Update Room Status </h3><br/>
		
	    <div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgupdateroomstatus" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="updateroomstatussuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgupdateroomstatus" style="display:none;width:50%;padding:10px;">
			<strong>Room Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="updateroomstatusroomid">Room Id</label> 
						<select class="form-control updateroomstatusroomid" id="updateroomstatusroomid">
							<option>SELECT</option>
						</select>
					</div>
				</div>

				<div class="col-sm-6">
					<div class="form-group">
						<label for="updateroomstatusunitid">Unit Id</label> 
						<select class="form-control updateroomstatusunitid" id="updateroomstatusunitid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="updateroomstatushkstatus">HK Status</label> 
						<select class="form-control updateroomstatushkstatus" id="updateroomstatushkstatus">
							<option value="Cleaned">Cleaned</option>
							<option value="Dirty">Dirty</option>
						</select>
					</div>
				</div>
				
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="updateroomstatushkremarks">HK Remarks</label> 
						 <input type="text" class="form-control updateroomstatushkremarks" id="updateroomstatushkremarks" 
				  		  placeholder="Enter Remarks" >
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-updateroomstatus">Update</button>
     		</div>
		</div>
		
	</div>  
	
	
	
	<div class="blockroomlist display">
		<h3>Set out of Order (Block Room) </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgblockroom" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="blockroomsuccess">Blocked Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgblockroom" style="display:none;width:50%;padding:10px;">
			<strong>Room Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="blockroomroomid">Room Id</label> 
						<select class="form-control blockroomroomid" id="blockroomroomid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="blockroomroomtypeid">Room Type Id</label> 
						<select class="form-control blockroomroomtypeid" id="blockroomroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="blockroomfromdate">From Date</label> 
						 <input type="date" class="form-control blockroomfromdate" id="blockroomfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="blockroomtodate">To Date</label> 
						 <input type="date" class="form-control blockroomtodate" id="blockroomtodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="blockroomreason">Reason</label> 
						 <input type="text" class="form-control blockroomreason" id="blockroomreason" 
				  		  placeholder="Enter Reason" >
					</div>
				</div>
				
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-blockroom">Block Room</button>
     		</div>
		</div>
		
	</div>  
	
	
	
	<div class="unblockroomlist display">
		<h3>Unblock room </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgunblockroom" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="unblockroomsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgunblockroom" style="display:none;width:50%;padding:10px;">
			<strong>Room Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
					<div class="col-sm-6">
					<div class="form-group">
						<label for="unblockroomroomid">Room Id</label> 
						<select class="form-control unblockroomroomid" id="unblockroomroomid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="unblockroomroomtypeid">Room Type Id</label> 
						<select class="form-control unblockroomroomtypeid" id="unblockroomroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="unblockroomfromdate">From Date</label> 
						 <input type="date" class="form-control unblockroomfromdate" id="unblockroomfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="unblockroomtodate">To Date</label> 
						 <input type="date" class="form-control unblockroomtodate" id="unblockroomtodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
			
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-unblockroom">UnBlock Room</button>
     		</div>
		</div>
		
	</div>  
	
	
	
	
	
	
</div>
	