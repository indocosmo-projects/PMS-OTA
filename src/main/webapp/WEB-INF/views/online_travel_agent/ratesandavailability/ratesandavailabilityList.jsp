
<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">Rates And Availability</h3>
	
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
	

  
  	<div class="updatelinearrateinventorylist display">
		<h3>Linear Rate </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsglinear" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="linearsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsglinear" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/>
		
         <form>
         	<div class="row">
         		<div class="col-sm-6">
					<div class="form-group">
						<label for="contactid">Contact Id</label> 
						<select class="form-control contactid" id="contactid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="roomtypeid">Room Type Id</label> 
						<select class="form-control roomtypeid" id="roomtypeid">
						<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ratetypeid">Rate Type Id</label> 
						<select class="form-control ratetypeid" id="ratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="base">Base</label> 
						<select class="form-control base" id="base">
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="fromdate">From Date</label> 
						 <input type="date" class="form-control fromdate" id="fromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="toodate">To Date</label> 
						 <input type="date" class="form-control toodate" id="toodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="extraadult">Extra Adult</label> 
						<input type="number" class="form-control extraadult" id="extraadult" 
				  		  placeholder="Enter Extra Adults" min="0" value="0" >
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="extrachild">Extra Child</label> 
						<input type="number" class="form-control extrachild" id="extrachild" 
				  		  placeholder="Enter Extra Child" min="0" value="0" >
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-updatelinearrateinventory">Update</button>
     		</div>
		</div>
		
		
		
	</div>  
	
	
	
	
	
</div>
	