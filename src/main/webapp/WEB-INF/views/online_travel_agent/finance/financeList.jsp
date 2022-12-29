<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">Finance</h3>
	
	
	<div class="extraslist display">
		<h3>Retrieve Extras </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgextras" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="extrassuccess">Please Wait</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgextras" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
		</div>
		
			<div class="extrasdivlist display">
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Extra Charge Id</th>
						<th class="tdwidth">Short Code</th>
						<th class="tdwidth">Charge</th>
						<th class="tdwidth">Description</th>
						<th class="tdwidth">Rate</th>
						<th class="tdwidth">Charge Rule</th>
						<th class="tdwidth">Posting Rule</th>
						<th class="tdwidth">Valid From</th>
						<th class="tdwidth">Valid To</th>
						<th class="tdwidth">Charge Always</th>
						<th class="tdwidth">Rate Plan</th>
						<th class="tdwidth">Special</th>
					</tr>
				</thead>
				<tbody class="extrasbody">
				</tbody>
			</table>
		</div>
		
		
		
	</div>  
	
	
	<div class="hotelexpenseslist display">
		<h3>Retrieve Hotel Expenses </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsghotelexpenses" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="hotelexpensessuccess">Success!</h5></strong> 
		</div>
		<div class="bg-danger" id="errorhotelexpenses" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
	
			  <form>
	         	<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="hotelexpensesfromdate">From Date</label> 
						 <input type="date" class="form-control hotelexpensesfromdate" id="hotelexpensesfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="hotelexpensestoodate">To Date</label> 
						 <input type="date" class="form-control hotelexpensestoodate" id="hotelexpensestoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				</div>
				
				</form>
	        	<div class="modal-footer">
	          		<button type="button" class="btn btn-primary" id="btn-hotelexpenses">Search</button>
	     		</div>
			</div>
		
		
		<div class="hotelexpensesdivlist display">
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Hotel Name</th>
						<th class="tdwidth">Hotel Code</th>
						<th class="tdwidth">Voucher Date</th>
						<th class="tdwidth">Voucher No</th>
						<th class="tdwidth">Contact Info</th>
						<th class="tdwidth">Expense Made To</th>
						<th class="tdwidth">Paid Out</th>
						<th class="tdwidth">Paid Out Charges</th>
						<th class="tdwidth">Paid Out Currency To</th>
						<th class="tdwidth">Payment Mode</th>
						<th class="tdwidth">Payment Charges</th>
						<th class="tdwidth">Payment Currency</th>
					</tr>
				</thead>
				<tbody class="hotelexpensesbody">
				</tbody>
			</table>
		</div>
		
	</div>  
	
	
	<div class="billslist display">
		<h3>Retrieve Bills </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgbills" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="billssuccess">Success!</h5></strong> 
		</div>
		<div class="bg-danger" id="errorbills" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
	
			  <form>
	         	<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="billsfromdate">From Date</label> 
						 <input type="date" class="form-control billsfromdate" id="billsfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="billstoodate">To Date</label> 
						 <input type="date" class="form-control billstoodate" id="billstoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				</div>
				
				</form>
	        	<div class="modal-footer">
	          		<button type="button" class="btn btn-primary" id="btn-bills">Search</button>
	     		</div>
			</div>
		
		
		<div class="billsdivlist display">
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Bill To name</th>
						<th class="tdwidth">Guest Name</th>
						<th class="tdwidth">Hotel Name</th>
						<th class="tdwidth">CGST</th>
						<th class="tdwidth">Amount</th>
						<th class="tdwidth">Date</th>
						<th class="tdwidth">Folio Number</th>
						<th class="tdwidth">Total</th>
					</tr>
				</thead>
				<tbody class="billsbody">
				</tbody>
			</table>
		</div>
		
		
	</div>  
	
	
	<div class="financialaccountslist display">
		<h3>Retrieve Financial Accounts </h3><br/>
			
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgfinancialaccounts" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="financialaccountssuccess">Success!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgfinancialaccounts" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
		
		
		
			<div class="financialaccountsdivlist display">
				<table class="table table-responsive table-bordered">
					<thead>
						<tr>
							<th class="tdwidth">Id</th>
							<th class="tdwidth">Description Un Kid</th>
							<th class="tdwidth">Description</th>
							<th class="tdwidth">Description Type Un kid</th>
							<th class="tdwidth">Description Type</th>
							<th class="tdwidth">Header Id</th>
							<th class="tdwidth">Header</th>
						</tr>
					</thead>
					<tbody class="financialaccountsbody">
					</tbody>
				</table>
		  	</div>
		</div>
		
		
	</div>  



	
	<div class="revenueslist display">
		<h3>Retrieve Revenues </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgrevenues" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="revenuessuccess">Success!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgrevenues" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
			
			
			  <form>
	         	<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="revenuesfromdate">From Date</label> 
						 <input type="date" class="form-control revenuesfromdate" id="revenuesfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="revenuestoodate">To Date</label> 
						 <input type="date" class="form-control revenuestoodate" id="revenuestoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				</div>
				
				</form>
	        	<div class="modal-footer">
	          		<button type="button" class="btn btn-primary btn-revenues" id="btn-revenues">Search</button>
	     		</div>
			
			
			<div class="revenuesdivlist display">
				<table class="table table-responsive table-bordered">
					<thead>
						<tr>
							<th class="tdwidth">Record Id</th>
							<th class="tdwidth">Record Date</th>
							<th class="tdwidth">Guest Name</th>
							<th class="tdwidth">Id Proof</th>
							<th class="tdwidth">Location</th>
							<th class="tdwidth">Amount</th>
						</tr>
					</thead>
					<tbody class="revenuesbody">
					</tbody>
				</table>
		  	</div>
		</div>
	</div>  
	
	
	<div class="outwardpaymentslist display">
		<h3>Retrieve Outwards Payments </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgoutwardpayments" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="outwardpaymentssuccess">Success!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgoutwardpayments" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
			
			
			  <form>
	         	<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="outwardpaymentsfromdate">From Date</label> 
						 <input type="date" class="form-control outwardpaymentsfromdate" id="outwardpaymentsfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="outwardpaymentstoodate">To Date</label> 
						 <input type="date" class="form-control outwardpaymentstoodate" id="outwardpaymentstoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				</div>
				
				</form>
	        	<div class="modal-footer">
	          		<button type="button" class="btn btn-primary" id="btn-outwardpayments">Search</button>
	     		</div>
			
			
			<div class="outwardpaymentsdivlist display">
				<table class="table table-responsive table-bordered">
					<thead>
						<tr>
							<th class="tdwidth">Id</th>
							<th class="tdwidth">Type</th>
							<th class="tdwidth">Trans Id</th>
							<th class="tdwidth">Date Time</th>
							<th class="tdwidth">Name</th>
							<th class="tdwidth">Email</th>
							<th class="tdwidth">Location</th>
							<th class="tdwidth">Gross Amount</th>
							<th class="tdwidth">Remark</th>
						</tr>
					</thead>
					<tbody class="outwardpaymentsbody">
					</tbody>
				</table>
		  	</div>
		</div>
		
	</div>  
	
	
	<div class="inwardspaymentslist display">
		<h3>Retrieve Inwards Payments </h3><br/>
			
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsginwardspayments" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="inwardspaymentssuccess">Success!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsginwardspayments" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
			
			
			  <form>
	         	<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="inwardspaymentsfromdate">From Date</label> 
						 <input type="date" class="form-control inwardspaymentsfromdate" id="inwardspaymentsfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="inwardspaymentstoodate">To Date</label> 
						 <input type="date" class="form-control inwardspaymentstoodate" id="inwardspaymentstoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				</div>
				
				</form>
	        	<div class="modal-footer">
	          		<button type="button" class="btn btn-primary " id="btn-inwardspayments">Search</button>
	     		</div>
			
			
			<div class="inwardspaymentsdivlist display">
				<table class="table table-responsive table-bordered">
					<thead>
						<tr>
							<th class="tdwidth">Id</th>
							<th class="tdwidth">Type</th>
							<th class="tdwidth">Trans Id</th>
							<th class="tdwidth">Date Time</th>
							<th class="tdwidth">Name</th>
							<th class="tdwidth">Email</th>
							<th class="tdwidth">Location</th>
							<th class="tdwidth">Gross Amount</th>
							<th class="tdwidth">Remark</th>
						</tr>
					</thead>
					<tbody class="inwardspaymentsbody">
					</tbody>
				</table>
		  	</div>
		</div>
		
	</div>  
	
	
	
	<div class="journalslist display">
		<h3>Retrieve Journals </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgjournals" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="journalssuccess">Success!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgjournals" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
			
			
			  <form>
	         	<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="journalsfromdate">From Date</label> 
						 <input type="date" class="form-control journalsfromdate" id="journalsfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="journalstoodate">To Date</label> 
						 <input type="date" class="form-control journalstoodate" id="journalstoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				</div>
				
				</form>
	        	<div class="modal-footer">
	          		<button type="button" class="btn btn-primary " id="btn-journals">Search</button>
	     		</div>
			
			
			<div class="journalsdivlist display">
				<table class="table table-responsive table-bordered">
					<thead>
						<tr>
							<th class="tdwidth">Id</th>
							<th class="tdwidth">Type</th>
							<th class="tdwidth">Trans Id</th>
							<th class="tdwidth">Date Time</th>
							<th class="tdwidth">Name</th>
							<th class="tdwidth">Email</th>
							<th class="tdwidth">Location</th>
							<th class="tdwidth">Gross Amount</th>
							<th class="tdwidth">Remark</th>
						</tr>
					</thead>
					<tbody class="journalsbody">
					</tbody>
				</table>
		  	</div>
		</div>
		
	</div>  
	
	
	
	<div class="incidentalinvoiceslist display">
		<h3>Retrieve Incidental Invoices</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgincidentalinvoices" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="incidentalinvoicessuccess">Success!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgincidentalinvoices" style="display:none;width:50%;padding:10px;">
			<strong>No Data Found!</strong> 
		</div><br/><br/>
			
			
			  <form>
	         	<div class="row">
				<div class="col-sm-6">
					<div class="form-group">
						<label for="incidentalinvoicesfromdate">From Date</label> 
						 <input type="date" class="form-control incidentalinvoicesfromdate" id="incidentalinvoicesfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="incidentalinvoicestoodate">To Date</label> 
						 <input type="date" class="form-control incidentalinvoicestoodate" id="incidentalinvoicestoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				</div>
				
				</form>
	        	<div class="modal-footer">
	          		<button type="button" class="btn btn-primary " id="btn-incidentalinvoices">Search</button>
	     		</div>
			
			
			<div class="incidentalinvoicesdivlist display">
				<table class="table table-responsive table-bordered">
					<thead>
						<tr>
							<th class="tdwidth">Id</th>
							<th class="tdwidth">Type</th>
							<th class="tdwidth">Trans Id</th>
							<th class="tdwidth">Date Time</th>
							<th class="tdwidth">Name</th>
							<th class="tdwidth">Email</th>
							<th class="tdwidth">Location</th>
							<th class="tdwidth">Gross Amount</th>
							<th class="tdwidth">Remark</th>
						</tr>
					</thead>
					<tbody class="incidentalinvoicesbody">
					</tbody>
				</table>
		  	</div>
		</div>
		
	</div>  
	
	
	
</div>
	