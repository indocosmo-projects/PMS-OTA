<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">Bookings</h3>
	
	<div class="checkavailabilitylist display">
		<h3>Check Availability </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgcheckavailability" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="checkavailabilitysuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgcheckavailability" style="display:none;width:50%;padding:10px;">
			<strong>No data found!</strong> 
		</div><br/><br/>
		
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="checkavailabilitycheckindate">Check In Date</label> 
						 <input type="date" class="form-control checkavailabilitycheckindate" id="checkavailabilitycheckindate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="checkavailabilitycheckoutdate">Check Out Date</label> 
						 <input type="date" class="form-control checkavailabilitycheckoutdate" id="checkavailabilitycheckoutdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<!-- 
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="checkavailabilitynights">Nights</label> 
				  		  <select class="form-control checkavailabilitynights" id="checkavailabilitynights">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
					</div>
				</div>
				 -->
				
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="checkavailabilityadults">Adults</label> 
				  		  <select class="form-control checkavailabilityadults" id="checkavailabilityadults">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="checkavailabilitychild">Children</label> 
				  		  <select class="form-control checkavailabilitychild" id="checkavailabilitychild">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="checkavailabilityrooms">Rooms</label> 
				  		  <select class="form-control checkavailabilityrooms" id="checkavailabilityrooms">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
			         	<div class="form-group">
							<label for="checkavailabilityroomtypeid">Room Type Id</label> 
					  		  <select class="form-control checkavailabilityroomtypeid" id="checkavailabilityroomtypeid">
								<option ></option>
							</select>
						</div>
					</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-primary" id="btn-checkavailability">Search</button>
     		</div>
		</div>
		
		
			<div class="checkavailabilitydivlist display">
				<table class="table table-responsive table-bordered">
					<thead>
						<tr>
							<th class="tdwidth">Id</th>
							<th class="tdwidth">Room Name</th>
							<th class="tdwidth">Room Type Id</th>
							<th class="tdwidth">Rack Rate</th>
							<th class="tdwidth">Total Price Room Only</th>
							<th class="tdwidth">Total Price Inclusive All</th>
							<th class="tdwidth">Avg Per Night After Discount</th>
							<th class="tdwidth">Avg Per Night Without Tax</th>
							<th class="tdwidth">Min Available Rooms</th>
						</tr>
					</thead>
					<tbody class="checkavailabilitybody">
					</tbody>
				</table>
		  	</div>

	</div>
	
	
	
	
	
	<div class="retrieveallbookingslist display">
		<h3>Retrieve all Bookings </h3><br/>
		
			<div style="text-align : right;">
				<button class="btn btn-warning" id="btnrefresh" data-toggle="tooltip" title=${refreshdatetime}>Refresh</button>
			</div>
		
					<ul class="nav flex-column" >
						<li class="nav-item " style="display: inline-block;"><button class="btn btn-outline " id="btn-reservation" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Reservations</a></button></li>
						
						<li class="nav-item " style="display: inline-block;"><button class="btn btn-outline" id="btn-rental" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Rental Info</a></button></li>
						
						<li class="nav-item " style="display: inline-block;"><button class="btn btn-outline" id="btn-booktrans" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Booking Trans</a></button></li>
						
						<li class="nav-item " style="display: inline-block;"><button class="btn btn-outline" id="btn-taxdetil" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Tax Details</a></button></li>
						
						<li class="nav-item " style="display: inline-block;"><button class="btn btn-outline" id="btn-cancelreservation" 	style="width : 100%;"> 
						<a class="nav-link linkcolor" href="#">Cancelled</a></button></li>	
						
					</ul>
		
		<div class="reservelist display">
		<h3>Reservations</h3>
		<div>
			<table class="table table-dark table-responsive table-bordered">
				<thead class="thead-dark">
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
		
		
		
	</div>
	
	
	
	<div class="retrievesinglebookingslist display">
		<h3>Retrieve a Booking </h3><br/>
			
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgretrievesinglebookings" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="retrievesinglebookingssuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgretrievesinglebookings" style="display:none;width:50%;padding:10px;">
			<strong>No data found!</strong> 
		</div><br/><br/>
			
		  <form>
         	<div class="row">
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="retrievesinglebookingbookingId">Booking Id</label> 
				  		  <select class="form-control retrievesinglebookingbookingId" id="retrievesinglebookingbookingId">
							<option></option>
						</select>
					</div>
				</div>
				
			</div>
			
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-primary" id="btn-singlereserve">Search</button>
     		</div>
		</div>
			
		<div class="singlereservelist display">
		<h3>Reservations</h3>
			<div>
				<table class="table table-dark table-responsive table-bordered">
					<thead class="thead-dark">
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
					<tbody class="singlereservationbody">
					</tbody>
				</table>
	
			</div>
		</div>
		
	</div>
	
	
	
	
	<div class="bookingreceivednotificationlist display">		
		<h3>Booking Received Notification</h3><br/><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsg" style="display:none;width:50%;padding:10px;">
			<strong>Updated Successfully!</strong> 
		</div>
		<div class="bg-danger" id="errormsg" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/>
		
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
		
		
	</div>
	
	<div class="retrievearrivalslist display">
		<h3>Retrieve Arrivals</h3><br/>
			
				<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievearrivals" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievearrivalssuccess">Updated Successfully!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievearrivals" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievearrivalsbookingId">Booking Id</label> 
						  		  <select class="form-control retrievearrivalsbookingId" id="retrievearrivalsbookingId">
									<option></option>
								</select>
							</div>
						</div>
						
							<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievearrivalsfromdate">From Date</label> 
						  		  <input type="date" class="form-control retrievearrivalsfromdate" id="retrievearrivalsfromdate" 
				  		  			placeholder="Enter From Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievearrivalstodate">To Date</label> 
						  		  <input type="date" class="form-control retrievearrivalstodate" id="retrievearrivalstodate" 
				  		  		   placeholder="Enter To Date" >
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievearrivals">Search</button>
		     		</div>
				</div>
			
			
			<div class="retrievearrivalsbooktranslist display">
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
						<tbody class="retrievearrivalsbooktransbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	
	<div class="retrievedepartureslist display">
		<h3>Retrieve Departures</h3><br/>
		
				<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievedepartures" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievedeparturesssuccess">Updated Successfully!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievedepartures" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievedeparturesbookingId">Booking Id</label> 
						  		  <select class="form-control retrievedeparturesbookingId" id="retrievedeparturesbookingId">
									<option></option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievedeparturesfromdate">From Date</label> 
						  		 <input type="date" class="form-control retrievedeparturesfromdate" id="retrievedeparturesfromdate" 
				  		  		 placeholder="Enter From Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievedeparturestodate">To Date</label> 
						  		  <input type="date" class="form-control retrievedeparturestodate" id="retrievedeparturestodate" 
				  		  		  placeholder="Enter To Date" >
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievedepartures">Search</button>
		     		</div>
				</div>
			
			
			<div class="retrievedeparturesbooktranslist display">
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
						<tbody class="retrievedeparturesbooktransbody">
						</tbody>
					</table>
				</div>
			</div>
		
	</div>

	<div class="postchargetoroomlist display">
		<h3>Post Charge To Room</h3><br/>
				
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgpostchargetoroom" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="postchargetoroomsuccess">Updated Successfully!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgpostchargetoroom" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetoroom">Room</label> 
						  		   <input type="number" class="form-control postchargetoroom" id="postchargetoroom" 
				  		  		 placeholder="Enter Room" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetofolio">Folio</label> 
						  		  <input type="number" class="form-control postchargetofolio" id="postchargetofolio" 
				  		  		 placeholder="Enter Folio" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetotable">Table</label> 
						  		   <input type="number" class="form-control postchargetotable" id="postchargetotable" 
				  		  		 	placeholder="Enter Table Number" >
							</div>
						</div>
						
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetooutlet">Outlet</label> 
						  		  <input type="text" class="form-control postchargetooutlet" id="postchargetooutlet" 
				  		  		 placeholder="Enter OutLet Name" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetocharge">Charge</label> 
						  		  <select class="form-control postchargetocharge" id="postchargetocharge">
									<option value="Breakfast">Breakfast</option>
									<option value="Lunch">Lunch</option>
									<option value="Dinner">Dinner</option>
								</select>
							</div>
						</div>
						
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetoroompostingdate">Posting Date</label> 
						  		 <input type="date" class="form-control postchargetoroompostingdate" id="postchargetoroompostingdate" 
				  		  		 placeholder="Enter Posting Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetoroomtrandate">Tran Date</label> 
						  		  <input type="date" class="form-control postchargetoroomtrandate" id="postchargetoroomtrandate" 
				  		  		  placeholder="Enter Trans Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetoroomamount">Amount</label> 
						  		  <input type="number" class="form-control postchargetoroomamount" id="postchargetoroomamount" 
				  		  		  placeholder="Enter Amount" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetoroomtax">Tax</label> 
						  		  <input type="number" class="form-control postchargetoroomtax" id="postchargetoroomtax" 
				  		  		  placeholder="Enter Tax" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetogrossamount">Gross Amount</label> 
						  		  <input type="number" class="form-control postchargetogrossamount" id="postchargetogrossamount" 
				  		  		  placeholder="Enter Gross Amount" >
							</div>
						</div>
						
							
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetovoucherno">Voucher Number</label> 
						  		  <input type="number" class="form-control postchargetovoucherno" id="postchargetovoucherno" 
				  		  		  placeholder="Enter Voucher No" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="postchargetoposuser">User</label> 
						  		  <select class="form-control postchargetoposuser" id="postchargetoposuser">
									<option value="admin">Admin</option>
								</select>
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-postchargeto">Add</button>
		     		</div>
				</div>	
		</div>
	
	
	
	<div class="voidchargeonroomlist display">
		<h3>Void Charge on Room</h3><br/>
		
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgvoidchargeonroom" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="voidchargeonroomsuccess">Updated Successfully!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgvoidchargeonroom" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="voidchargeonroomrequestid">Request Id</label> 
						  		   <input type="number" class="form-control voidchargeonroomrequestid" id="voidchargeonroomrequestid" 
				  		  		 placeholder="Enter Request Id" >
							</div>
						</div>
							
					</div>
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-voidchargeonroom">Delete</button>
		     		</div>
				</div>	
	</div>

	
	<div class="updateposreceiptnolist display">
		<h3>Update POS Receipt No</h3><br/>
		
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgupdateposreceiptno" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="updateposreceiptnosuccess">Updated Successfully!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgupdateposreceiptno" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="updateposreceiptnorequestid">Request Id</label> 
						  		   <input type="number" class="form-control updateposreceiptnorequestid" id="updateposreceiptnorequestid" 
				  		  		 placeholder="Enter Request Id" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="updateposreceiptnovoucherno">Voucher No</label> 
						  		   <input type="text" class="form-control updateposreceiptnovoucherno" id="updateposreceiptnovoucherno" 
				  		  		 placeholder="Enter Voucher No" >
							</div>
						</div>
							
					</div>
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-updateposreceiptno">Update</button>
		     		</div>
				</div>	
	</div>
	
	
	<div class="retrieveposttoroominformationlist display">
		<h3>Retrieve Post to Room Information</h3><br/>
			
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrieveposttoroominformation" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrieveposttoroominformationsuccess">Updated Successfully!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrieveposttoroominformation" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
			</div>	
				
			<div class="retrieveposttoroominformationdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Guest Name</th>
								<th class="tdwidth">Arrival</th>
								<th class="tdwidth">Departure</th>
								<th class="tdwidth">Master Folio</th>
								<th class="tdwidth">Room</th>
								<th class="tdwidth">Room Type</th>
								<th class="tdwidth">Rate Type</th>
								<th class="tdwidth">Remarks</th>
								<th class="tdwidth">Reservation No</th>
							</tr>
						</thead>
						<tbody class="retrieveposttoroominformationbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	<div class="retrieveposttoroominformationspecificlist display">
		<h3>Retrieve Post to Room Information for specific room</h3><br/>
			
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrieveposttoroominformationspecific" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrieveposttoroominformationspecificsuccess">Updated Successfully!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrieveposttoroominformationspecific" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
				 <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveposttoroominformationspecificroom">Room</label> 
						  		   <input type="text" class="form-control retrieveposttoroominformationspecificroom" id="retrieveposttoroominformationspecificroom" 
				  		  		 placeholder="Enter Room" >
							</div>
						</div>
										
					</div>
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrieveposttoroominformationspecific">Search</button>
		     		</div>
				
			</div>	
				
			<div class="retrieveposttoroominformationspecificdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Guest Name</th>
								<th class="tdwidth">Arrival</th>
								<th class="tdwidth">Departure</th>
								<th class="tdwidth">Master Folio</th>
								<th class="tdwidth">Room</th>
								<th class="tdwidth">Room Type</th>
								<th class="tdwidth">Rate Type</th>
								<th class="tdwidth">Remarks</th>
								<th class="tdwidth">Reservation No</th>
							</tr>
						</thead>
						<tbody class="retrieveposttoroominformationspecificbody">
						</tbody>
					</table>
				</div>
			</div>
			
	</div>



	<div class="roomsalesdatalist display">
		<h3>Room Sales Data</h3><br/>
			
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgroomsalesdata" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="roomsalesdatasuccess">Updated Successfully!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgroomsalesdata" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
				 <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="roomsalesdataroomid">Room</label> 
						  		   <select class="form-control roomsalesdataroomid" id="roomsalesdataroomid">
										<option></option>
									</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="roomsalesdatafromdate">From Date</label> 
						  		   <input type="date" class="form-control roomsalesdatafromdate" id="roomsalesdatafromdate" 
				  		  		 placeholder="Enter From Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="roomsalesdataroomtodate">To Date</label> 
						  		   <input type="date" class="form-control roomsalesdataroomtodate" id="roomsalesdataroomtodate" 
				  		  		 placeholder="Enter To Date" >
							</div>
						</div>

					</div>
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-roomsalesdata">Search</button>
		     		</div>
			</div>	
				
			<div class="roomsalesdatadivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Room Nights</th>
								<th class="tdwidth">Room Sold</th>
								<th class="tdwidth">Adr</th>
								<th class="tdwidth">Room Charges</th>
							</tr>
						</thead>
						<tbody class="roomsalesdatabody">
						</tbody>
					</table>
				</div>
			</div>
			
	</div>
	
	
	
	<div class="reservedroomscalendarlist display">
		<h3>Reserved Rooms Calendar</h3><br/>
		
	</div>
	
	<div class="retrievephysicalroomslist display">
		<h3>Retrieve Physical Rooms</h3><br/>
		
	</div>
	
	<div class="todayscheckIn-checkoutlist display">
		<h3>Todays CheckIn-Checkout</h3><br/>
		
	</div>
	
	<div class="reservationdetailsofaroomlist display">
		<h3>Reservation Details of a Room</h3><br/>
		
	</div>

	
	<div class="pullhistoricalbookingslist display">
		<h3>Pull Historical Bookings</h3><br/>
		
	</div>
	
	<div class="createabookinglist display">
		<h3>Create a Booking</h3><br/>
		
	</div>
	
	
	<div class="postcreatebookingsactionslist display">
		<h3>Post Create Bookings Actions</h3><br/>
		
	</div>
	
	<div class="retrieveabookingbasedonparameterslist display">
		<h3>Retrieve a Booking Based on Parameters</h3><br/>
		
	</div>
	
	<div class="readabookinglist display">
		<h3>Read a Booking</h3><br/>
		
	</div>
	
	
	<div class="cancelabookinglist display">
		<h3>Cancel a Booking</h3><br/>
		
	</div>
	
	<div class="autosyncfuturebookingsanditsmodificationslist display">
		<h3>Autosync Future Bookings and its modifications</h3><br/>
		
	</div>
	
	<div class="guestdataupdatelist display">
		<h3>Guest Data Update</h3><br/>
		
	</div>
	
	<div class="addpaymentlist display">
		<h3>Add Payment</h3><br/>
		
	</div>
	
	
	<div class="addguestprofiletobookingslist display">
		<h3>Add Guest Profile to Bookings</h3><br/>
		
	</div>
	
	
	<div class="guestcheckinlist display">
		<h3>Guest Check In</h3><br/>
		
	</div>
	
	
	<div class="roomassignmentlist display">
		<h3>Room Assignment</h3><br/>
		
	</div>
	
	<div class="guestcheckoutlist display">
		<h3>Guest Check Out</h3><br/>
		
	</div>
	
	
	<div class="retrievelistofbillslist display">
		<h3>Retrieve List of Bills</h3><br/>
		
	</div>
	
	
	<div class="retrievetransactiondetailslist display">
		<h3>Retrieve Transaction Details</h3><br/>
		
	</div>





</div>
	