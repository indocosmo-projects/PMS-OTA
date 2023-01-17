<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">Bookings</h3>
	
	<div class="checkavailabilitylist display">
		<h3>Check Availability </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgcheckavailability" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="checkavailabilitysuccess">Success!</h5></strong> 
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
			<strong><h5 id="retrievesinglebookingssuccess">Success!</h5></strong> 
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
			<strong>Success!</strong> 
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
					<strong><h5 id="retrievearrivalssuccess">Success!</h5></strong> 
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
					<strong><h5 id="retrievedeparturesssuccess">Success!</h5></strong> 
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
					<strong><h5 id="postchargetoroomsuccess">Success!</h5></strong> 
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
					<strong><h5 id="voidchargeonroomsuccess">Success!</h5></strong> 
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
					<strong><h5 id="updateposreceiptnosuccess">Success!</h5></strong> 
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
					<strong><h5 id="retrieveposttoroominformationsuccess">Success!</h5></strong> 
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
					<strong><h5 id="retrieveposttoroominformationspecificsuccess">Success!</h5></strong> 
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
					<strong><h5 id="roomsalesdatasuccess">Success!</h5></strong> 
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
			
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgreservedroomscalendar" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="reservedroomscalendarsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgreservedroomscalendar" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
				 <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="reservedroomscalendarroomid">Room</label> 
						  		   <select class="form-control reservedroomscalendarroomid" id="reservedroomscalendarroomid">
										<option></option>
									</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="reservedroomscalendarroomcode">Room Code</label> 
						  		   <select class="form-control reservedroomscalendarroomcode" id="reservedroomscalendarroomcode">
										<option></option>
									</select>
							</div>
						</div>
						
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="reservedroomscalendarfromdate">From Date</label> 
						  		   <input type="date" class="form-control reservedroomscalendarfromdate" id="reservedroomscalendarfromdate" 
				  		  		 placeholder="Enter From Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="reservedroomscalendartodate">To Date</label> 
						  		   <input type="date" class="form-control reservedroomscalendartodate" id="reservedroomscalendartodate" 
				  		  		 placeholder="Enter To Date" >
							</div>
						</div>

					</div>
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-reservedroomscalendar">Search</button>
		     		</div>
			</div>	
				
			<div class="reservedroomscalendardivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Room Name</th>
								<th class="tdwidth">Room Code</th>
								<th class="tdwidth">Date</th>
								<th class="tdwidth">Status</th>
								<th class="tdwidth">Reservation Id</th>
								<th class="tdwidth">Guest Name</th>
								<th class="tdwidth">Check In</th>
								<th class="tdwidth">Check Out</th>
								<th class="tdwidth">Booking Status</th>
								<th class="tdwidth">Total Amount</th>
								<th class="tdwidth">Payment Type</th>
								<th class="tdwidth">Creation Date</th>
							</tr>
						</thead>
						<tbody class="reservedroomscalendarbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	
	
	<div class="retrievephysicalroomslist display">
		<h3>Retrieve Physical Rooms</h3><br/>
			
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievephysicalrooms" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievephysicalroomssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievephysicalrooms" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
		   </div>
			
			<div class="retrievephysicalroomsdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Room Id</th>
								<th class="tdwidth">Room Name</th>
								<th class="tdwidth">Room Code</th>
								<th class="tdwidth">Rooms</th>
							</tr>
						</thead>
						<tbody class="retrievephysicalroomsbody">
						</tbody>
					</table>
				</div>
			</div>
		
		
	</div>
	
	<div class="todayscheckIn-checkoutlist display">
		<h3>Todays CheckIn-Checkout</h3><br/>
			
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgtodayscheckincheckout" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="todayscheckincheckoutsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgtodayscheckincheckout" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
		   </div>
			
			<div class="todayscheckincheckoutdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Reservation Id</th>
								<th class="tdwidth">Room Code</th>
								<th class="tdwidth">Departure Date Time</th>
							</tr>
						</thead>
						<tbody class="todayscheckincheckoutbody">
						</tbody>
					</table>
				</div>
			</div>
			
	</div>
	
	<div class="reservationdetailsofaroomlist display">
		<h3>Reservation Details of a Room</h3><br/>
			
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgreservationdetailsofaroom" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="reservationdetailsofaroomsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgreservationdetailsofaroom" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
		 		<form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="reservationdetailsofaroomreseid">Reservation Id</label> 
						  		   <select class="form-control reservationdetailsofaroomreseid" id="reservationdetailsofaroomreseid">
										<option></option>
									</select>
							</div>
						</div>

					</div>
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-reservationdetailsofaroom">Search</button>
		     		</div>
			</div>	
			
			<div class="reservationdetailsofaroomdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Room Id</th>
								<th class="tdwidth">Room Name</th>
								<th class="tdwidth">Reservation Id</th>
								<th class="tdwidth">Booking Status</th>
								<th class="tdwidth">Guest Name</th>
								<th class="tdwidth">Check In</th>
								<th class="tdwidth">Check Out</th>
								<th class="tdwidth">Total Amount</th>
								<th class="tdwidth">Channel</th>
								<th class="tdwidth">Payment Type</th>
							</tr>
						</thead>
						<tbody class="reservationdetailsofaroombody">
						</tbody>
					</table>
				</div>
			</div>
		
	</div>

	
	<div class="pullhistoricalbookingslist display">
		<h3>Pull Historical Bookings</h3><br/>
			
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgpullhistoricalbookings" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="pullhistoricalbookingssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgpullhistoricalbookings" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
		 		<form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="pullhistoricalbookingsfromdate">From Date</label> 
						  		   <input type="date" class="form-control pullhistoricalbookingsfromdate" id="pullhistoricalbookingsfromdate" 
				  		  		 placeholder="Enter From Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="pullhistoricalbookingstodate">To Date</label> 
						  		   <input type="date" class="form-control pullhistoricalbookingstodate" id="pullhistoricalbookingstodate" 
				  		  		 placeholder="Enter To Date" >
							</div>
						</div>

					</div>
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-pullhistoricalbookings">Search</button>
		     		</div>
			</div>	
			
			<div class="pullhistoricalbookingsdivlist display">
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
						<tbody class="pullhistoricalbookingsbody">
						</tbody>
					</table>
				</div>
			</div>
			
	</div>
	
	
	
	
	<div class="createabookinglist display">
		<h3>Create a Booking</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgcreateabooking" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="createabookingsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgcreateabooking" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
		 		<form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingrateplanid">Rate Plan Id</label> 
						  		  <select class="form-control createabookingrateplanid" id="createabookingrateplanid">
									<option></option>
								  </select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingratetypeid">Rate Type Id</label> 
						  		  <select class="form-control createabookingratetypeid" id="createabookingratetypeid">
									<option></option>
								  </select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingroomtypeid">Room Type Id</label> 
						  		   <select class="form-control createabookingroomtypeid" id="createabookingroomtypeid">
									 <option></option>
								  </select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingbaserate">Base Rate</label> 
						  		   <select class="form-control createabookingbaserate" id="createabookingbaserate">
										 	<option value="500">500</option>
										 	<option value="1000">1000</option>
										  	<option value="2000">2000</option>
										   	<option value="3000">3000</option>
										    <option value="4000">4000</option>
									     	<option value="5000">5000</option>
								  </select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingextradultrate">Extra Adult Rate</label> 
						  		    <select class="form-control createabookingextradultrate" id="createabookingextradultrate">
										 	<option value="500">500</option>
										 	<option value="1000">1000</option>
										  	<option value="2000">2000</option>
										   	<option value="3000">3000</option>
										    <option value="4000">4000</option>
									     	<option value="5000">5000</option>
								  </select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingextrachildrate">Extra Child Rate</label> 
						  		    <select class="form-control createabookingextrachildrate" id="createabookingextrachildrate">
										 	<option value="500">500</option>
										 	<option value="1000">1000</option>
										  	<option value="2000">2000</option>
										   	<option value="3000">3000</option>
										    <option value="4000">4000</option>
									     	<option value="5000">5000</option>
								  </select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingnumberadults">Number Of Adults</label> 
						  		   <input type=""number"" class="form-control createabookingnumberadults" id="createabookingnumberadults" 
				  		  		 placeholder="Enter Number of Adults" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingnumberchildren">Number Of Children</label> 
						  		   <input type=""number"" class="form-control createabookingnumberchildren" id="createabookingnumberchildren" 
				  		  		 placeholder="Enter Number of Children" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingextrachildage">Extra Child Age</label> 
						  		   <input type="number" class="form-control createabookingextrachildage" id="createabookingextrachildage" 
				  		  		 placeholder="Enter Child Age" >
							</div>
						</div>
						
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingtitle">Title</label> 
						  		   <input type="text" class="form-control createabookingtitle" id="createabookingtitle" 
				  		  		 placeholder="Enter Title" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingfirstname">First Name</label> 
						  		   <input type="text" class="form-control createabookingfirstname" id="createabookingfirstname" 
				  		  		 placeholder="Enter First Name" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookinglastname">Last Name</label> 
						  		   <input type="text" class="form-control createabookinglastname" id="createabookinglastname" 
				  		  		 placeholder="Enter Last Name" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookinggender">Extra Child Rate</label> 
						  		    <select class="form-control createabookinggender" id="createabookinggender">
										 	<option value="Male">Male</option>
										 	<option value="Female">Female</option>
										  	<option value="Other">Other</option>
								  </select>
							</div>
						</div>
						
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingspecialrequest">Special Request</label> 
						  		   <input type="text" class="form-control createabookingspecialrequest" id="createabookingspecialrequest" 
				  		  		 placeholder="Enter Special Request" >
							</div>
						</div>
						
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingcheckindate">Check In Date</label> 
						  		   <input type="date" class="form-control createabookingcheckindate" id="createabookingcheckindate" 
				  		  		 placeholder="Enter Check Out Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingcheckoutdate">Check Out Date</label> 
						  		   <input type="date" class="form-control createabookingcheckoutdate" id="createabookingcheckoutdate" 
				  		  		 placeholder="Enter Check Out Date" >
							</div>
						</div>
						
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingbookingpaymentmode">Booking Payment Mode</label> 
						  		   <input type="text" class="form-control createabookingbookingpaymentmode" id="createabookingbookingpaymentmode" 
				  		  		 placeholder="Enter Booking Payment Mode" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingemailaddress">Email</label> 
						  		   <input type="email" class="form-control createabookingemailaddress" id="createabookingemailaddress" 
				  		  		 placeholder="Enter Email" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingsourceid">Source Id</label> 
						  		   <input type="text" class="form-control createabookingsourceid" id="createabookingsourceid" 
				  		  		 placeholder="Enter Source Id" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingmobileno">Mobile Number</label> 
						  		   <input type="text" class="form-control createabookingmobileno" id="createabookingmobileno" 
				  		  		 placeholder="Enter Mobile Number" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingaddress">Booking Address</label> 
						  		   <input type="text" class="form-control createabookingaddress" id="createabookingaddress" 
				  		  		 placeholder="Enter Booking Address" >
							</div>
						</div>
						
							<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingstate">State</label> 
						  		   <input type="text" class="form-control createabookingstate" id="createabookingstate" 
				  		  		 placeholder="Enter State" >
							</div>
						</div>
						
							<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingcountry">Country</label> 
						  		   <input type="text" class="form-control createabookingcountry" id="createabookingcountry" 
				  		  		 placeholder="Enter Country" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingcity">City</label> 
						  		   <input type="text" class="form-control createabookingcity" id="createabookingcity" 
				  		  		 placeholder="Enter City" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingzipcode">Zip Code</label> 
						  		   <input type="text" class="form-control createabookingzipcode" id="createabookingzipcode" 
				  		  		 placeholder="Enter City" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingfax">Fax</label> 
						  		   <input type="text" class="form-control createabookingfax" id="createabookingfax" 
				  		  		 placeholder="Enter Fax" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingdevice">Device</label> 
						  		   <input type="text" class="form-control createabookingdevice" id="createabookingdevice" 
				  		  		 placeholder="Enter Device" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookinglanguagekey">Language Key</label> 
						  		   <input type="text" class="form-control createabookinglanguagekey" id="createabookinglanguagekey" 
				  		  		 placeholder="Enter Language Key" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="createabookingpaymenttypeunkid">Payment Type Unkid</label> 
						  		   <input type="text" class="form-control createabookingpaymenttypeunkid" id="createabookingpaymenttypeunkid" 
				  		  		 placeholder="Enter Payment Type Unkid" >
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-createabooking">Create</button>
		     		</div>
			</div>	
	</div>
	
	
	
	<div class="postcreatebookingsactionslist display">
		<h3>Post Create Bookings Actions</h3><br/>
		
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgpostcreatebookingsactions" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="postcreatebookingsactionssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgpostcreatebookingsactions" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
			
		</div>      	         
	</div>
	
	
	
	<div class="retrieveabookingbasedonparameterslist display">
		<h3>Retrieve a Booking Based on Parameters</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrieveabookingbasedonparameters" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrieveabookingbasedonparameterssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrieveabookingbasedonparameters" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
				
				<form>
		         	<div class="row">
		         	
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveabookingbasedonparametersfromdate">From Date</label> 
						  		   <input type="date" class="form-control retrieveabookingbasedonparametersfromdate" id="retrieveabookingbasedonparametersfromdate" 
				  		  		 placeholder="Enter From Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveabookingbasedonparameterstodate">To Date</label> 
						  		   <input type="date" class="form-control retrieveabookingbasedonparameterstodate" id="retrieveabookingbasedonparameterstodate"	
						  		   	 placeholder="Enter To date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveabookingbasedonparametersemail">Email</label> 
						  		   <input type="email" class="form-control retrieveabookingbasedonparametersemail" id="retrieveabookingbasedonparametersemail" 
				  		  		 placeholder="Enter Email" >
							</div>
						</div>
						
					</div>
				</form>
				<div class="modal-footer">
		          	<button type="button" class="btn btn-primary" id="btn-retrieveabookingbasedonparameters">Search</button>
		     	</div>	
		</div>    
		
			<div class="retrieveabookingbasedonparametersbooktransdivlist display">
				<h3>Reserved Booking in Date Range</h3>
				
				<h5>Total Active Room In Hotel <span class="badge badge-primary totalactiveroominhotel">0</span></h5>
				<h5>Total Block Rooms <span class="badge badge-primary totalblockrooms">0</span></h5>
				<h5>Total Occupied Rooms <span class="badge badge-primary totaloccupiedrooms">0</span></h5>
				
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
								<th class="tdwidth">Reservation Date</th>
								<th class="tdwidth">Number Of Guests</th>
								<th class="tdwidth">Number Of Nights</th>
							</tr>
						</thead>
						<tbody class="retrieveabookingbasedonparametersbooktransbody">
						</tbody>
					</table>
				</div>
			</div>
		  	
	</div>
	
	
	
	<div class="readabookinglist display">
		<h3>Read a Booking</h3><br/>
		
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgreadabooking" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="readabookingsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgreadabooking" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="readabookingreservationnumber">Reservation Number</label> 
					  		  		<select class="form-control readabookingreservationnumber" id="readabookingreservationnumber">
										<option></option>
									</select>
								</div>
							</div>
	
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-readabooking">Search</button>
		     		</div>
		     </div> 		
		     <div class="readabookingdivlist display">
				
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
								<th class="tdwidth">Email</th>
								<th class="tdwidth">Phone</th>
								<th class="tdwidth">Current Status</th>
								<th class="tdwidth">Number Of Nights</th>
								<th class="tdwidth">Guest Id</th>
								<th class="tdwidth">Country</th>
							</tr>
						</thead>
						<tbody class="readabookingbody">
						</tbody>
					</table>
				</div>
			</div>
		   
	</div>
	
	
	<div class="cancelabookinglist display">
		<h3>Cancel a Booking</h3><br/>
		
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgcancelabooking" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="cancelabookingsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgcancelabooking" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="cancelabookingreservationnumber">Reservation Number</label> 
					  		  		<select class="form-control cancelabookingreservationnumber" id="cancelabookingreservationnumber">
										<option></option>
									</select>
								</div>
							</div>
	
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-cancelabooking">Search</button>
		     		</div>
		     </div> 		
	</div>
	
	
	
	<div class="autosyncfuturebookingsanditsmodificationslist display">
		<h3>Autosync Future Bookings and its modifications</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgautosyncfuturebookingsanditsmodifications" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="autosyncfuturebookingsanditsmodificationssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgautosyncfuturebookingsanditsmodifications" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
			
		</div>
	</div>
	
	
	
	
	<div class="guestdataupdatelist display">
		<h3>Guest Data Update</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgguestdataupdate" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="guestdataupdatesuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgguestdataupdate" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestdataupdatereservationid">Reservation Id</label> 
					  		  		<select class="form-control guestdataupdatereservationid" id="guestdataupdatereservationid">
										<option></option>
									</select>
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestdataupdatefirstname">First Name</label> 
					  		  		  <input type="text" class="form-control guestdataupdatefirstname" id="guestdataupdatefirstname" 
				  		  		       placeholder="Enter First Name" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestdataupdatelastname">Last Name</label> 
					  		  		  <input type="text" class="form-control guestdataupdatelastname" id="guestdataupdatelastname" 
				  		  		       placeholder="Enter Last Name" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestdataupdateemail">Email</label> 
					  		  		  <input type="email" class="form-control guestdataupdateemail" id="guestdataupdateemail" 
				  		  		       placeholder="Enter Email" >
								</div>
							</div>
	
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-guestdataupdate">Search</button>
		     		</div>
		     </div> 		
		     
	</div>
	
	
	
	
	<div class="addpaymentlist display">
		<h3>Add Payment</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgaddpayment" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="addpaymentsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgaddpayment" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addpaymentreservationid">Reservation Id</label> 
					  		  		<select class="form-control addpaymentreservationid" id="addpaymentreservationid">
										<option></option>
									</select>
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addpaymentpaymentid">Payment Id</label> 
					  		  		  <input type="text" class="form-control addpaymentpaymentid" id="addpaymentpaymentid" 
				  		  		       placeholder="Enter Payment Id" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addpaymentcurrencyid">Currency Id</label> 
					  		  		  <input type="text" class="form-control addpaymentcurrencyid" id="addpaymentcurrencyid" 
				  		  		       placeholder="Enter Currency Id" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addpaymentpayment">Payment</label> 
					  		  		  <input type="email" class="form-control addpaymentpayment" id="addpaymentpayment" 
				  		  		       placeholder="Enter Payment" >
								</div>
							</div>
	
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-addpayment">Pay</button>
		     		</div>
		     </div> 	
	</div>
	
	
	<div class="addguestprofiletobookingslist display">
		<h3>Add Guest Profile to Bookings</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgaddguestprofiletobookings" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="addguestprofiletobookingssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgaddguestprofiletobookings" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addguestprofiletobookingsreservationid">Reservation Id</label> 
					  		  		<select class="form-control addguestprofiletobookingsreservationid" id="addguestprofiletobookingsreservationid">
										<option></option>
									</select>
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addguestprofiletobookingsfirstname">First Name</label> 
					  		  		  <input type="text" class="form-control addguestprofiletobookingsfirstname" id="addguestprofiletobookingsfirstname" 
				  		  		       placeholder="Enter Payment Id" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addguestprofiletobookingslastname">Last Name</label> 
					  		  		  <input type="text" class="form-control addguestprofiletobookingslastname" id="addguestprofiletobookingslastname" 
				  		  		       placeholder="Enter Currency Id" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addguestprofiletobookingspaymentgender">Gender</label> 
					  		  		 <select class="form-control addguestprofiletobookingspaymentgender" id="addguestprofiletobookingspaymentgender">
										<option value="Male">Male</option>
										<option value="Female">Female</option>
										<option value="Other">Other</option>
									</select>
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="addguestprofiletobookingspaymenttype">Type</label> 
					  		  		 <select class="form-control addguestprofiletobookingspaymenttype" id="addguestprofiletobookingspaymenttype">
										<option value="Adult">Adult</option>
										<option value="Child">Child</option>
									</select>
								</div>
							</div>
	
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-addguestprofiletobookings">Add</button>
		     		</div>
		     </div> 	
	</div>
	
	
	<div class="guestcheckinlist display">
		<h3>Guest Check In</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgguestcheckin" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="guestcheckinsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgguestcheckin" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestcheckinreservationid">Reservation Id</label> 
					  		  		<select class="form-control guestcheckinreservationid" id="guestcheckinreservationid">
										<option></option>
									</select>
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestcheckinguestname">Guest Name</label> 
					  		  		  <input type="text" class="form-control guestcheckinguestname" id="guestcheckinguestname" 
				  		  		       placeholder="Enter Guest Name" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestcheckinemail">Email</label> 
					  		  		  <input type="email" class="form-control guestcheckinemail" id="guestcheckinemail" 
				  		  		       placeholder="Enter Email" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestcheckinaddress">Address</label> 
					  		  		<textarea class="form-control guestcheckinaddress" id="guestcheckinaddress" rows="3" cols="50" ></textarea>
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestcheckinphone">Phone</label> 
					  		  		  <input type="text" class="form-control guestcheckinphone" id="guestcheckinphone" 
				  		  		       placeholder="Enter Phone" >
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestcheckinmobile">Mobile</label> 
					  		  		  <input type="text" class="form-control guestcheckinmobile" id="guestcheckinmobile" 
				  		  		       placeholder="Enter Mobile" >
								</div>
							</div>
	
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-guestcheckin">Search</button>
		     		</div>
		     </div> 	
	</div>
	
	
	<div class="roomassignmentlist display">
		<h3>Room Assignment</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgroomassignment" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="roomassignmentsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgroomassignment" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="roomassignmentreservationid">Reservation Id</label> 
					  		  		<select class="form-control roomassignmentreservationid" id="roomassignmentreservationid">
										<option></option>
									</select>
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="roomassignmentroomtypeid">Room Type Id </label> 
					  		  		  <select class="form-control roomassignmentroomtypeid" id="roomassignmentroomtypeid">
										<option></option>
									  </select>
								</div>
							</div>
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="roomassignmentroomid">Room Id</label> 
					  		  		  <select class="form-control roomassignmentroomid" id="roomassignmentroomid">
										<option></option>
									  </select>
								</div>
							</div>
						
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-roomassignment">Search</button>
		     		</div>
		     </div> 
	</div>
	
	
	<div class="guestcheckoutlist display">
		<h3>Guest Check Out</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgguestcheckout" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="guestcheckoutsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgguestcheckout" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="guestcheckoutreservationid">Reservation Id</label> 
					  		  		<select class="form-control guestcheckoutreservationid" id="guestcheckoutreservationid">
										<option></option>
									</select>
								</div>
							</div>
							
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-guestcheckout">Search</button>
		     		</div>
		     </div> 
	</div>
	
	
	<div class="retrievelistofbillslist display">
		<h3>Retrieve List of Bills</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievelistofbills" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievelistofbillssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievelistofbills" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="retrievelistofbillsreservationid">Reservation Id</label> 
					  		  		<select class="form-control retrievelistofbillsreservationid" id="retrievelistofbillsreservationid">
										<option></option>
									</select>
								</div>
							</div>
							
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievelistofbills">Search</button>
		     		</div>
		     </div> 
		     
		     <div class="retrievelistofbillsdivlist display">
				
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Folio No</th>
								<th class="tdwidth">Bill To Contact</th>
								<th class="tdwidth">Guest Name</th>
								<th class="tdwidth">Currency Code</th>
								<th class="tdwidth">Total Charges</th>
								<th class="tdwidth">Paid Amount</th>
								<th class="tdwidth">Due Amount</th>
							</tr>
						</thead>
						<tbody class="retrievelistofbillsbody">
						</tbody>
					</table>
				</div>
			</div>
			
	</div>
	
	
	<div class="retrievetransactiondetailslist display">
		<h3>Retrieve Transaction Details</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievetransactiondetails" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievetransactiondetailssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievetransactiondetails" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					<form>
			         	<div class="row">
							
							<div class="col-sm-6">
					         	<div class="form-group">
									<label for="retrievetransactiondetailsreservationid">Reservation Id</label> 
					  		  		<select class="form-control retrievetransactiondetailsreservationid" id="retrievetransactiondetailsreservationid">
										<option></option>
									</select>
								</div>
							</div>
							
						</div>
		           </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievetransactiondetails">Search</button>
		     		</div>
		     </div> 
			
			 <div class="retrievetransactiondetailsdivbooktranslist display">
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
							<tbody class="retrievetransactiondetailsbooktransbody">
							</tbody>
						</table>
					</div>
			</div>
			
		</div>



</div>
	