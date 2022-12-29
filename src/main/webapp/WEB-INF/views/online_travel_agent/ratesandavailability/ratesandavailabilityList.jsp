
<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">Rates And Availability</h3>
	
	
	<div class="updateroominventorylist display">
		<h3>Update Room Inventory </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgupdateroominventory" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="updateroominventorysuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgupdateroominventory" style="display:none;width:50%;padding:10px;">
			<strong>Rooms Does not exists!</strong> 
		</div><br/>
		
         <form>
         	<div class="row">
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="updateroominventoryroomtypeid">Room Type Id</label> 
						<select class="form-control updateroominventoryroomtypeid" id="updateroominventoryroomtypeid">
						<option>SELECT</option>
						</select>
					</div>
				</div>
				
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="updateroominventorybase">Availability</label> 
						<select class="form-control updateroominventorybase" id="updateroominventorybase">
							<option value="1" selected>1</option>
							<option value="2">2</option>
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="updateroominventoryfromdate">From Date</label> 
						 <input type="date" class="form-control updateroominventoryfromdate" id="updateroominventoryfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="updateroominventorytoodate">To Date</label> 
						 <input type="date" class="form-control updateroominventorytoodate" id="updateroominventorytoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-updateroominventory">Update</button>
     		</div>
		</div>
		
	</div>
	
	
  
  	<div class="updatelinearratelist display">
		<h3>Update Linear Rate </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsglinear" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="linearsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsglinear" style="display:none;width:50%;padding:10px;">
			<strong>Inventory Does not exists!</strong> 
		</div><br/>
		
         <form>
         	<div class="row">
         		<div class="col-sm-6">
					<div class="form-group">
						<label for="linearcontactid">Contact Id</label> 
						<select class="form-control linearcontactid" id="linearcontactid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="linearroomtypeid">Room Type Id</label> 
						<select class="form-control linearroomtypeid" id="linearroomtypeid">
						<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="linearratetypeid">Rate Type Id</label> 
						<select class="form-control linearratetypeid" id="linearratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="linearbase">Base</label> 
						<select class="form-control linearbase" id="linearbase">
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="linearfromdate">From Date</label> 
						 <input type="date" class="form-control linearfromdate" id="linearfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="lineartoodate">To Date</label> 
						 <input type="date" class="form-control lineartoodate" id="lineartoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="linearextraadult">Extra Adult</label> 
						<input type="number" class="form-control linearextraadult" id="linearextraadult" 
				  		  placeholder="Enter Extra Adults" min="0" value="0" >
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="linearextrachild">Extra Child</label> 
						<input type="number" class="form-control linearextrachild" id="linearextrachild" 
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
	
	
	
	<div class="updatenonlinearratelist display">
		<h3>Update Non - Linear Rate </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgnonlinear" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="nonlinearsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgnonlinear" style="display:none;width:50%;padding:10px;">
			<strong>Inventory Does not exists!</strong> 
		</div><br/>
		
			
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="nonlinearcontactid">Contact Id</label> 
						<select class="form-control nonlinearcontactid" id="nonlinearcontactid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="nonlinearroomtypeid">Room Type Id</label> 
						<select class="form-control nonlinearroomtypeid" id="nonlinearroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="nonlinearratetypeid">Rate Type Id</label> 
						<select class="form-control nonlinearratetypeid" id="nonlinearratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="nonlinearfromdate">From Date</label> 
						 <input type="date" class="form-control nonlinearfromdate" id="nonlinearfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="nonlineartoodate">To Date</label> 
						 <input type="date" class="form-control nonlineartoodate" id="nonlineartoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="nonlinearratesbase">Base</label> 
						  <select class="form-control nonlinearratesbase" id="nonlinearratesbase">
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
						<label for="nonlinearratesextraadult">Extra Adult</label> 
						  <select class="form-control nonlinearratesextraadult" id="nonlinearratesextraadult">
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
							<option value="500">500</option>
							<option value="1000">1000</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="nonlinearratesextrachild">Extra Child</label> 
						  <select class="form-control nonlinearratesextrachild" id="nonlinearratesextrachild">
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
							<option value="500">500</option>
							<option value="1000">1000</option>
						</select>
					</div>
				</div>
				
				</div>
				
				<div class="row">
				
				<div class="col-sm-6">
					<h4 class="header2">Adult Rates</h4>
					<div class="form-group">
						<label for="nonlinearratesadult1">Adult 1</label> 
						  <select class="form-control nonlinearratesadult1" id="nonlinearratesadult1">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearratesadult2">Adult 2</label> 
						  <select class="form-control nonlinearratesadult2" id="nonlinearratesadult2">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearratesadult3">Adult 3</label> 
						  <select class="form-control nonlinearratesadult3" id="nonlinearratesadult3">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearratesadult4">Adult 4</label> 
						  <select class="form-control nonlinearratesadult4" id="nonlinearratesadult4">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearratesadult5">Adult 5</label> 
						  <select class="form-control nonlinearratesadult5" id="nonlinearratesadult5">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearratesadult6">Adult 6</label> 
						  <select class="form-control nonlinearratesadult6" id="nonlinearratesadult6">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
						<div class="form-group">
						<label for="nonlinearratesadult7">Adult 7</label> 
						  <select class="form-control nonlinearratesadult7" id="nonlinearratesadult7">
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
				<h4 class="header2">Child Rates</h4>
		         	<div class="form-group">
						<label for="nonlinearrateschild1">Child 1</label> 
				  		  <select class="form-control nonlinearrateschild1" id="nonlinearrateschild1">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearrateschild2">Child 2</label> 
				  		  <select class="form-control nonlinearrateschild2" id="nonlinearrateschild2">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearrateschild3">Child 3</label> 
				  		  <select class="form-control nonlinearrateschild3" id="nonlinearrateschild3">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearrateschild4">Child 4</label> 
				  		  <select class="form-control nonlinearrateschild4" id="nonlinearrateschild4">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearrateschild5">Child 5</label> 
				  		  <select class="form-control nonlinearrateschild5" id="nonlinearrateschild5">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearrateschild6">Child 6</label> 
				  		  <select class="form-control nonlinearrateschild6" id="nonlinearrateschild6">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="nonlinearrateschild7">Child 7</label> 
				  		  <select class="form-control nonlinearrateschild7" id="nonlinearrateschild7">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
				</div>
				
			</div>
         </form>
			<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-nonlinearrate">Update</button>
     		</div>
		
		</div>
		
	</div>
	
	
	<div class="retrieveroomratessdlist display">
		<h3>Retrieve Room Rates With Source Details </h3><br/>
		
			<ul class="nav nav-pills roominforrssdlist" >
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnroomtypesrrssd">Room Types</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnratetypesrrssd">Rate Types</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnrateplansrrssd">Rate Plans</button>
			</li>
			<li class="nav-item">
				<button class="btn btn-secondary" id="btnseperatechannelrrssd">Channel Source Plans</button>
			</li>
			
		</ul>


		<div class="roomtypesrrssdlist display">
			<h4>Room Types</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Room Type Id</th>
						<th class="tdwidth">Room Type Name</th>
					</tr>
				</thead>
				<tbody class="roomtypesrrssdbody">
				</tbody>
			</table>
		</div>
		
		<div class="ratetypesrrssdlist display">
			<h4>Rate Types</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Rate Types Id</th>
						<th class="tdwidth">Rate Types Name</th>
					</tr>
				</thead>
				<tbody class="ratetypesrrssdbody">
				</tbody>
			</table>
		</div>
			
		<div class="rateplansrrssdlist display">
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
				<tbody class="rateplansrrssdbody">
				</tbody>
			</table>
		</div>
		
		<div class="separatechannelrrssdlist display">
			<h4>Separate Channel Plans</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Channel Id</th>
						<th class="tdwidth">Channel Name</th>
					</tr>
				</thead>
				<tbody class="separatechannelrrssdbody">
				</tbody>
			</table>
		</div>
		
		
	</div>
	
	
	<div class="updatemaxnightslist display">
		<h3>Update Max Nights </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgupdatemaxnights" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="updatemaxnightssuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgupdatemaxnights" style="display:none;width:50%;padding:10px;">
			<strong>Inventory Does not exists!</strong> 
		</div><br/>
		
	         <form>
	         	<div class="row">
	         		<div class="col-sm-6">
						<div class="form-group">
							<label for="updatemaxnightsrateplanid">Rate Plan Id</label> 
							<select class="form-control updatemaxnightsrateplanid" id="updatemaxnightsrateplanid">
								<option>SELECT</option>
							</select>
						</div>
					</div>
			
				    <div class="col-sm-6">
						<div class="form-group">
							<label for="updatemaxnightsfromdate">From Date</label> 
							 <input type="date" class="form-control updatemaxnightsfromdate" id="updatemaxnightsfromdate" 
					  		  placeholder="Enter From Date" >
						</div>
					</div>
				
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updatemaxnightstoodate">To Date</label> 
							 <input type="date" class="form-control updatemaxnightstoodate" id="updatemaxnightstoodate" 
					  		  placeholder="Enter To Date" >
						</div>
					</div>
					
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updatemaxnightsmaxnights">Max Nights</label> 
							<select class="form-control updatemaxnightsmaxnights" id="updatemaxnightsmaxnights">
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
								<option value="30">30</option>
								<option value="50">50</option>
								<option value="100">100</option>
							</select>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-updatemaxnights">Update</button>
     		</div>
		
		</div>
		
	</div>
	
	
	<div class="updateminnightslist display">
		<h3>Update Min Nights </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgupdateminnights" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="updateminnightssuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgupdateminnights" style="display:none;width:50%;padding:10px;">
			<strong>Inventory Does not exists!</strong> 
		</div><br/>
		
	         <form>
	         	<div class="row">
	         		<div class="col-sm-6">
						<div class="form-group">
							<label for="updateminnightsrateplanid">Rate Plan Id</label> 
							<select class="form-control updateminnightsrateplanid" id="updateminnightsrateplanid">
								<option>SELECT</option>
							</select>
						</div>
					</div>
			
				    <div class="col-sm-6">
						<div class="form-group">
							<label for="updateminnightsfromdate">From Date</label> 
							 <input type="date" class="form-control updateminnightsfromdate" id="updateminnightsfromdate" 
					  		  placeholder="Enter From Date" >
						</div>
					</div>
				
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updateminnightstoodate">To Date</label> 
							 <input type="date" class="form-control updateminnightstoodate" id="updateminnightstoodate" 
					  		  placeholder="Enter To Date" >
						</div>
					</div>
					
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updateminnightsminnights">Min Nights</label> 
							<select class="form-control updateminnightsminnights" id="updateminnightsminnights">
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
								<option value="30">30</option>
								<option value="50">50</option>
								<option value="100">100</option>
							</select>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-updateminnights">Update</button>
     		</div>
		</div>
	</div>
	
	
	
	<div class="updatestopselllist display">
		<h3>Update StopSell </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgupdatestopsell" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="updatestopsellsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgupdatestopsell" style="display:none;width:50%;padding:10px;">
			<strong>Inventory Does not exists!</strong> 
		</div><br/>
		
	         <form>
	         	<div class="row">
	         		<div class="col-sm-6">
						<div class="form-group">
							<label for="updatestopsellrateplanid">Rate Plan Id</label> 
							<select class="form-control updatestopsellrateplanid" id="updatestopsellrateplanid">
								<option>SELECT</option>
							</select>
						</div>
					</div>
			
				    <div class="col-sm-6">
						<div class="form-group">
							<label for="updatestopsellfromdate">From Date</label> 
							 <input type="date" class="form-control updatestopsellfromdate" id="updatestopsellfromdate" 
					  		  placeholder="Enter From Date" >
						</div>
					</div>
				
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updatestopselltoodate">To Date</label> 
							 <input type="date" class="form-control updatestopselltoodate" id="updatestopselltoodate" 
					  		  placeholder="Enter To Date" >
						</div>
					</div>
					
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updatestopsellstopsell">StopSell</label> 
							<select class="form-control updatestopsellstopsell" id="updatestopsellstopsell">
								<option value="1">Enable</option>
								<option value="0">Disable</option>
							</select>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-updatestopsell">Update</button>
     		</div>
		</div>
		
	</div>
	
	
	
	
	<div class="updatecloseonarrivallist display">
		<h3>Update Close On Arrival </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgupdatecoa" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="updatecoasuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgupdatecoa" style="display:none;width:50%;padding:10px;">
			<strong>Inventory Does not exists!</strong> 
		</div><br/>
		
	         <form>
	         	<div class="row">
	         		<div class="col-sm-6">
						<div class="form-group">
							<label for="updatecoarateplanid">Rate Plan Id</label> 
							<select class="form-control updatecoarateplanid" id="updatecoarateplanid">
								<option>SELECT</option>
							</select>
						</div>
					</div>
			
				    <div class="col-sm-6">
						<div class="form-group">
							<label for="updatecoafromdate">From Date</label> 
							 <input type="date" class="form-control updatecoafromdate" id="updatecoafromdate" 
					  		  placeholder="Enter From Date" >
						</div>
					</div>
				
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updatecoatoodate">To Date</label> 
							 <input type="date" class="form-control updatecoatoodate" id="updatecoatoodate" 
					  		  placeholder="Enter To Date" >
						</div>
					</div>
					
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updatecoacoa">COA</label> 
							<select class="form-control updatecoacoa" id="updatecoacoa">
								<option value="1">Enable</option>
								<option value="0">Disable</option>
							</select>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-updatecoa">Update</button>
     		</div>
		</div>
		
	</div>
	
	
	<div class="updatecloseondeparturelist display">
		<h3>Update Close On Departure </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgupdatecod" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="updatecodsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgupdatecod" style="display:none;width:50%;padding:10px;">
			<strong>Inventory Does not exists!</strong> 
		</div><br/>
		
	         <form>
	         	<div class="row">
	         		<div class="col-sm-6">
						<div class="form-group">
							<label for="updatecodrateplanid">Rate Plan Id</label> 
							<select class="form-control updatecodrateplanid" id="updatecodrateplanid">
								<option>SELECT</option>
							</select>
						</div>
					</div>
			
				    <div class="col-sm-6">
						<div class="form-group">
							<label for="updatecodfromdate">From Date</label> 
							 <input type="date" class="form-control updatecodfromdate" id="updatecodfromdate" 
					  		  placeholder="Enter From Date" >
						</div>
					</div>
				
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updatecodtoodate">To Date</label> 
							 <input type="date" class="form-control updatecodtoodate" id="updatecodtoodate" 
					  		  placeholder="Enter To Date" >
						</div>
					</div>
					
					<div class="col-sm-6">
						<div class="form-group">
							<label for="updatecodcod">COD</label> 
							<select class="form-control updatecodcod" id="updatecodcod">
								<option value="1">Enable</option>
								<option value="0">Disable</option>
							</select>
						</div>
					</div>
				</div>
			</form>
			<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-updatecod">Update</button>
     		</div>
		</div>
		
	</div>
	
	
	<div class="retrieveroominventorylist display" >
		<h3>Room Inventory</h3>
		
		<div style="margin-left:15px; width:700px;">
			<form>
				<div class="form-group">
					<label for="retrieveroominventoryfdate">From Date</label> 
					 <input type="date" class="form-control retrieveroominventoryfdate" id="retrieveroominventoryfdate" 
			  		  placeholder="Enter From Date" style="width:50%;">
				</div>
				<div class="form-group">
					<label for="retrieveroominventorytdate">To Date</label> 
					 <input type="date" class="form-control retrieveroominventorytdate" id="retrieveroominventorytdate" 
			  		  placeholder="Enter To Date" style="width:50%;">
				</div>
				<div>
					<button type="button" class="btn btn-primary" id="btn-searchroominventory">Search</button>
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
	
	
	<div class="retrieveroomrateslist display">
		<h3>Retrieve Room Rates</h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgretrieveroomrates" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="retrieveroomratessuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgretrieveroomrates" style="display:none;width:50%;padding:10px;">
			<strong>Inventory Does not exists!</strong> 
		</div><br/>
		
	         <form>
	         		<div class="row">
			   			<div class="col-sm-6">
							<div class="form-group">
								<label for="retrieveroomratesfromdate">From Date</label> 
								 <input type="date" class="form-control retrieveroomratesfromdate" id="retrieveroomratesfromdate" 
						  		  placeholder="Enter From Date" >
							</div>
						</div>
					
						<div class="col-sm-6">
							<div class="form-group">
								<label for="retrieveroomratestoodate">To Date</label> 
								 <input type="date" class="form-control retrieveroomratestoodate" id="retrieveroomratestoodate" 
						  		  placeholder="Enter To Date" >
							</div>
						</div>
				   </div>
			</form>
			<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-retrieveroomrates">Update</button>
     		</div>
     		
     		<div class="retrieveroomrateslist display">
			<h4>Room Rates</h4>
			<table class="table table-responsive table-bordered">
				<thead>
					<tr>
						<th class="tdwidth">Id</th>
						<th class="tdwidth">Room Type Id</th>
						<th class="tdwidth">Rate Type Id</th>
						<th class="tdwidth">From Date</th>
						<th class="tdwidth">To Date</th>
						<th class="tdwidth">Base</th>
						<th class="tdwidth">Extra Adult</th>
						<th class="tdwidth">Extra Child</th>
					</tr>
				</thead>
				<tbody class="retrieveroomratesbody">
				</tbody>
			</table>
		</div>
		
	</div>
	
	
	
</div>
	