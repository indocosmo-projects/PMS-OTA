<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	<div style="text-align : right;">
		<button class="btn btn-warning" id="btnrefresh">Refresh</button>
	</div>
	
	<div class="reservelist display">
		<h3>Reservations</h3>
		<div>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Reservation Id</th>
						<th class="tdwidth">Booked By</th>
						<th class="tdwidth">Name</th>
						<th class="tdwidth">Location Id</th>
						<th class="tdwidth">Source</th>
						<th class="tdwidth">Email</th>
					</tr>
				</thead>
				<tbody class="reservationbody">
				</tbody>
			</table>


		</div>
	</div>

	<div class="rentallist display">
		<h3>Rental Info</h3>
		<div>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Reservation Id</th>
						<th class="tdwidth">Effective Date</th>
						<th class="tdwidth">Room Type</th>
						<th class="tdwidth">Rent</th>
						<th class="tdwidth">Rent Pre Tax</th>
						<th class="tdwidth">Discount</th>
						<th class="tdwidth">Adult</th>
						<th class="tdwidth">Child</th>
					</tr>
				</thead>
				<tbody class="rentalbody">
				</tbody>
			</table>


		</div>
	</div>

	<div class="booktranslist display">
		<h3>Booking Trans</h3>
		<div>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Reservation Id</th>
						<th class="tdwidth">Name</th>
						<th class="tdwidth">Rate Plan</th>
						<th class="tdwidth">Start</th>
						<th class="tdwidth">End</th>
						<th class="tdwidth">Arrival Time</th>
						<th class="tdwidth">Departure Time</th>
						<th class="tdwidth">Email</th>
						<th class="tdwidth">Current Status</th>
					</tr>
				</thead>
				<tbody class="booktransbody">
				</tbody>
			</table>


		</div>
	</div>


	<div class="taxdeatillist display">
		<h3>Tax Details</h3>
		<div>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Reservation Id</th>
						<th class="tdwidth">TaxCode</th>
						<th class="tdwidth">TaxName</th>
						<th class="tdwidth">TaxAmount</th>
					</tr>
				</thead>
				<tbody class="taxdeatilbody">
				</tbody>
			</table>


		</div>
	</div>


	<div class="cancelreservelist display">
		<h3>Cancel Reservation</h3>
		<div>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Reservation Id</th>
						<th class="tdwidth">Location Id</th>
						<th class="tdwidth">Status</th>
						<th class="tdwidth">Remark</th>
					</tr>
				</thead>
				<tbody class="cancelreservationbody">
				</tbody>
			</table>


		</div>
	</div>  
	
	
	
	<div class="receivedbookingNotifylist display">
		<h3>Booking Received Notification</h3><br/><br/>
		
		<div style="margin-left:15px; width:700px;">
         <form>
			<div class="form-group">
				<label for="bookingid">Booking Id</label> 
				<select class="form-control bookingid" id="bookingid">
					<option>SELECT</option>
				</select>
			</div>
	
	
			<div class="form-group">
				<label for="pmsbookingid">PMS Booking Id</label> 
				<select class="form-control pmsbookingid" id="pmsbookingid">
				<option>SELECT</option>
				</select>
			</div>
			
         	<div class="form-group">
				<label for="status">Status:</label> 
				<select
					class="form-control status" id="status">
					<option value="New">New</option>
					<option value="Modify">Modify</option>
					<option value="Cancel">Cancel</option>
				</select>
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default btn-notify" >Notify</button>
     		</div>
		</div>
		
		<div class="bg-success" id="successmsg" style="display:none;width:50%;padding:10px;">
			<strong>Updated Successfully!</strong> 
		</div>
		<div class="bg-danger" id="errormsg" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div>
		
	</div>  
	
	
	
	<div class="roominformation display">
		<h3>Room Information</h3>
		<form>
			<div class="form-group">
			  <label for="roomrequired">Number Of Rooms:</label>
			  <input type="number" class="form-control roomrequired" id="roomrequired" min="1"
			  placeholder="Enter Required Number Of Rooms" value="1"
			  style="width:50%;">
			</div>
			<div>
				<button type="button" class="btn btn-primary" id="searchrooms">Search</button>
			</div>
		</form>

		<ul class="nav nav-pills roominfolist" >
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnroomtypes">Room Types</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnratetypes">Rate Types</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnrateplans">Rate Plans</button>
			</li>
		</ul>


		<div class="roomtypeslist display">
			<h4>Room Types</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Room Types Id</th>
						<th class="tdwidth">Room Types Name</th>
						<th class="tdwidth">Room Id</th>
						<th class="tdwidth">Room Name</th>
					</tr>
				</thead>
				<tbody class="roomtypes">
				</tbody>
			</table>
		</div>
		
		<div class="ratetypeslist display">
			<h4>Rate Types</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Room Types Id</th>
						<th class="tdwidth">Room Types Name</th>
					</tr>
				</thead>
				<tbody class="ratetypes">
				</tbody>
			</table>
		</div>
			
		<div class="rateplanslist display">
			<h4>Rate Plans</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Rate Plan Id</th>
						<th class="tdwidth">Room Name</th>
						<th class="tdwidth">Room Type Id</th>
						<th class="tdwidth">Room Type</th>
						<th class="tdwidth">Rate Type Id</th>
						<th class="tdwidth">Rate Type</th>
					</tr>
				</thead>
				<tbody class="rateplans">
				</tbody>
			</table>
		</div>
		
		
	</div>  
  
  
  
  
  	<div class="reservationsingle display" >
		<h3>Reservation Details of a Room</h3>
		
		<div style="margin-left:15px; width:700px;">
			<form>
				<div class="form-group">
					<label for="reservationid">Enter Reservation Id</label> 
					<select class="form-control reservationid" id="reservationid">
						<option>SELECT</option>
					</select>
				</div>
				<div>
					<button type="button" class="btn btn-primary" id="searchreservationid">Search</button>
				</div>
			</form>
		
		</div>
		

		<div class="singlelist display">
			<h4>Reservation </h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Room Name</th>
						<th class="tdwidth">Room Code</th>
						<th class="tdwidth">Reservation Id</th>
						<th class="tdwidth">Booking Status</th>
						<th class="tdwidth">Guest Name</th>
						<th class="tdwidth">Check In</th>
						<th class="tdwidth">Check Out</th>
						<th class="tdwidth">Total Amount</th>
						<th class="tdwidth">Payment Type</th>
					</tr>
				</thead>
				<tbody class="reservationsinglebody">
				</tbody>
			</table>
		</div>
		
		
	</div>  
	
	
	
	
	<div class="retrieveroominventory display" >
		<h3>Room Inventory</h3>
		
		<div style="margin-left:15px; width:700px;">
			<form>
				<div class="form-group">
					<label for="fdate">From Date</label> 
					 <input type="date" class="form-control fdate" id="fdate" 
			  		  placeholder="Enter From Date" style="width:50%;">
				</div>
				<div class="form-group">
					<label for="tdate">To Date</label> 
					 <input type="date" class="form-control tdate" id="tdate" 
			  		  placeholder="Enter To Date" style="width:50%;">
				</div>
				<div>
					<button type="button" class="btn btn-primary" id="searchroominventory">Search</button>
				</div>
			</form>
		</div>
		
		<div class="retrieveroominventorylist display">
			<h4>Room </h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Room Type Id</th>
						<th class="tdwidth">Availability</th>
						<th class="tdwidth">From Date</th>
						<th class="tdwidth">To Date</th>
					</tr>
				</thead>
				<tbody class="retrieveroominventorybody">
				</tbody>
			</table>
		</div>
		
		
	</div>  
	
	
	
  

	
