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
		<h3>Booking Received Notification</h3>
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
						<th class="tdwidth">Current Status</th>
						<th class="tdwidth">Booking Received Status</th>
						<th class="tdwidth"></th> 
					</tr>
				</thead>
				<tbody class="receivedbookingNotifybody">
				</tbody>
			</table>

		</div>
	</div>  
	
	
</div>