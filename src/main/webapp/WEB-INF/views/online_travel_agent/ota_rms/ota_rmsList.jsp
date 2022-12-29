<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">OTA - RMS</h3>
	
	
	<div class="pushroominfolist display">
		<h3>Room Information </h3><br/>
		

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
	
	
	
	<div class="pushinventorylist display">
		<h3>Push Inventory </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgpushinventory" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="pushinventorysuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgpushinventory" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/><br/>
		
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="roomtypeidpush">Room Type Id</label> 
						<select class="form-control roomtypeidpush" id="roomtypeidpush">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="pushcount">Enter Room Availability</label> 
				  		  <select class="form-control pushcount" id="pushcount">
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="15">15</option>
							<option value="20">20</option>
							<option value="30">30</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushfromdate">From Date</label> 
						 <input type="date" class="form-control pushfromdate" id="pushfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushtoodate">To Date</label> 
						 <input type="date" class="form-control pushtoodate" id="pushtoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-pushinventory">Update</button>
     		</div>
		</div>
		
	</div>  
	
	
	
	<div class="pushlinearrateslist display">
		<h3>Push Linear Rates </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgpushlinearrates" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="pushlinearratessuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgpushlinearrates" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushlinearratesroomtypeid">Room Type Id</label> 
						<select class="form-control pushlinearratesroomtypeid" id="pushlinearratesroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushlinearratesratetypeid">Rate Type Id</label> 
						<select class="form-control pushlinearratesratetypeid" id="pushlinearratesratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="pushlinearratescount">Enter Base Rate</label> 
				  		  <select class="form-control pushlinearratescount" id="pushlinearratescount">
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
						<label for="pushlinearratesextraadult">Enter Adult Rate</label> 
				  		  <select class="form-control pushlinearratesextraadult" id="pushlinearratesextraadult">
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
						<label for="pushlinearratesextrachild">Enter Child Rate</label> 
				  		  <select class="form-control pushlinearratesextrachild" id="pushlinearratesextrachild">
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
						<label for="pushlinearratesfromdate">From Date</label> 
						 <input type="date" class="form-control pushlinearratesfromdate" id="pushlinearratesfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushlinearratestoodate">To Date</label> 
						 <input type="date" class="form-control pushlinearratestoodate" id="pushlinearratestoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-pushlinearrates">Update</button>
     		</div>
		</div>
	
		
	</div>  	
	
	
	
	<div class="pushnonlinearrateslist display">
		<h3>Push Non Linear Rates </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgpushnonlinearrates" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="pushnonlinearratessuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgpushnonlinearrates" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushnonlinearratesroomtypeid">Room Type Id</label> 
						<select class="form-control pushnonlinearratesroomtypeid" id="pushnonlinearratesroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushnonlinearratesratetypeid">Rate Type Id</label> 
						<select class="form-control pushnonlinearratesratetypeid" id="pushnonlinearratesratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushnonlinearratesfromdate">From Date</label> 
						 <input type="date" class="form-control pushnonlinearratesfromdate" id="pushnonlinearratesfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushnonlinearratestoodate">To Date</label> 
						 <input type="date" class="form-control pushnonlinearratestoodate" id="pushnonlinearratestoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<h4 class="header2">Adult Rates</h4>
					<div class="form-group">
						<label for="pushnonlinearratesadult1">Adult 1</label> 
						  <select class="form-control pushnonlinearratesadult1" id="pushnonlinearratesadult1">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushnonlinearratesadult2">Adult 2</label> 
						  <select class="form-control pushnonlinearratesadult2" id="pushnonlinearratesadult2">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushnonlinearratesadult3">Adult 3</label> 
						  <select class="form-control pushnonlinearratesadult3" id="pushnonlinearratesadult3">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushnonlinearratesadult4">Adult 4</label> 
						  <select class="form-control pushnonlinearratesadult4" id="pushnonlinearratesadult4">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushnonlinearratesadult5">Adult 5</label> 
						  <select class="form-control pushnonlinearratesadult5" id="pushnonlinearratesadult5">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushnonlinearratesadult6">Adult 6</label> 
						  <select class="form-control pushnonlinearratesadult6" id="pushnonlinearratesadult6">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
						<div class="form-group">
						<label for="pushnonlinearratesadult7">Adult 7</label> 
						  <select class="form-control pushnonlinearratesadult7" id="pushnonlinearratesadult7">
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
						<label for="pushlinearrateschild1">Child 1</label> 
				  		  <select class="form-control pushlinearrateschild1" id="pushlinearrateschild1">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushlinearrateschild2">Child 2</label> 
				  		  <select class="form-control pushlinearrateschild2" id="pushlinearrateschild2">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushlinearrateschild3">Child 3</label> 
				  		  <select class="form-control pushlinearrateschild3" id="pushlinearrateschild3">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushlinearrateschild4">Child 4</label> 
				  		  <select class="form-control pushlinearrateschild4" id="pushlinearrateschild4">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushlinearrateschild5">Child 5</label> 
				  		  <select class="form-control pushlinearrateschild5" id="pushlinearrateschild5">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushlinearrateschild6">Child 6</label> 
				  		  <select class="form-control pushlinearrateschild6" id="pushlinearrateschild6">
							<option value="500">500</option>
							<option value="1000">1000</option>
							<option value="2000">2000</option>
							<option value="3000">3000</option>
							<option value="4000">4000</option>
							<option value="5000">5000</option>
						</select>
					</div>
					<div class="form-group">
						<label for="pushlinearrateschild7">Child 7</label> 
				  		  <select class="form-control pushlinearrateschild7" id="pushlinearrateschild7">
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
          		<button type="button" class="btn btn-default" id="btn-pushnonlinearrates">Update</button>
     		</div>
		</div>
	
	</div>  	
	
	
	
	<div class="pushminimumnightslist display">
		<h3>Push Minimum Nights </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgpushminimumnights" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="pushminimumnightssuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgpushminimumnights" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushminimumnightsroomtypeid">Room Type Id</label> 
						<select class="form-control pushminimumnightsroomtypeid" id="pushminimumnightsroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushminimumnightsratetypeid">Rate Type Id</label> 
						<select class="form-control pushminimumnightsratetypeid" id="pushminimumnightsratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushminimumnightsrateplanid">Rate Plan Id</label> 
						<select class="form-control pushminimumnightsrateplanid" id="pushminimumnightsrateplanid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushminimumnightsfromdate">From Date</label> 
						 <input type="date" class="form-control pushminimumnightsfromdate" id="pushminimumnightsfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushminimumnightstoodate">To Date</label> 
						 <input type="date" class="form-control pushminimumnightstoodate" id="pushminimumnightstoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="pushminimumnightscount">Enter Minimum Nights</label> 
				  		  <select class="form-control pushminimumnightscount" id="pushminimumnightscount">
							<option value="2">2</option>
							<option value="5">5</option>
							<option value="10">10</option>
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
						</select>
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-minimumnights">Update</button>
     		</div>
		</div>
		
	</div>  
	
	
	
	
	<div class="pushstopselllist display">
		<h3>Push Stop Sell </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgpushstopsell" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="pushstopsellsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgpushstopsell" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushstopsellroomtypeid">Room Type Id</label> 
						<select class="form-control pushstopsellroomtypeid" id="pushstopsellroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushstopsellratetypeid">Rate Type Id</label> 
						<select class="form-control pushstopsellratetypeid" id="pushstopsellratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushstopsellrateplanid">Rate Plan Id</label> 
						<select class="form-control pushstopsellrateplanid" id="pushstopsellrateplanid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushstopsellfromdate">From Date</label> 
						 <input type="date" class="form-control pushstopsellfromdate" id="pushstopsellfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushstopselltoodate">To Date</label> 
						 <input type="date" class="form-control pushstopselltoodate" id="pushstopselltoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="pushstopsellcount">Stop Sell (Enable/Disable)</label> 
				  		  <select class="form-control pushstopsellcount" id="pushstopsellcount">
							<option value="0">Disable</option>
							<option value="1">Enable</option>
						</select>
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-stopsell">Update</button>
     		</div>
		</div>
		
	</div>  
	
	
	
	
	<div class="pushcloseonarrivallist display">
		<h3>Push Close On Arrival </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgpushcloseonarrival" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="pushcloseonarrivalsuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgpushcloseonarrival" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseonarrivalroomtypeid">Room Type Id</label> 
						<select class="form-control pushcloseonarrivalroomtypeid" id="pushcloseonarrivalroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseonarrivalratetypeid">Rate Type Id</label> 
						<select class="form-control pushcloseonarrivalratetypeid" id="pushcloseonarrivalratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseonarrivalrateplanid">Rate Plan Id</label> 
						<select class="form-control pushcloseonarrivalrateplanid" id="pushcloseonarrivalrateplanid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseonarrivalfromdate">From Date</label> 
						 <input type="date" class="form-control pushcloseonarrivalfromdate" id="pushcloseonarrivalfromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseonarrivaltoodate">To Date</label> 
						 <input type="date" class="form-control pushcloseonarrivaltoodate" id="pushcloseonarrivaltoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="pushcloseonarrivalcount">Close On Arrival (Enable/Disable)</label> 
				  		  <select class="form-control pushcloseonarrivalcount" id="pushcloseonarrivalcount">
							<option value="0">Disable</option>
							<option value="1">Enable</option>
						</select>
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-closeonarrival">Update</button>
     		</div>
		</div>
		
	</div>  
	
	
	

	
	<div class="pushcloseondeparturelist display">
		<h3>Push Close on Departure </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgpushcloseondeparture" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="pushcloseondeparturesuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgpushcloseondeparture" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseondepartureroomtypeid">Room Type Id</label> 
						<select class="form-control pushcloseondepartureroomtypeid" id="pushcloseondepartureroomtypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseondepartureratetypeid">Rate Type Id</label> 
						<select class="form-control pushcloseondepartureratetypeid" id="pushcloseondepartureratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseondeparturerateplanid">Rate Plan Id</label> 
						<select class="form-control pushcloseondeparturerateplanid" id="pushcloseondeparturerateplanid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseondeparturefromdate">From Date</label> 
						 <input type="date" class="form-control pushcloseondeparturefromdate" id="pushcloseondeparturefromdate" 
				  		  placeholder="Enter From Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="pushcloseondeparturetoodate">To Date</label> 
						 <input type="date" class="form-control pushcloseondeparturetoodate" id="pushcloseondeparturetoodate" 
				  		  placeholder="Enter To Date" >
					</div>
				</div>
				
				
				<div class="col-sm-6">
		         	<div class="form-group">
						<label for="pushcloseondeparturecount">Close On Departure (Enable/Disable)</label> 
				  		  <select class="form-control pushcloseondeparturecount" id="pushcloseondeparturecount">
							<option value="0">Disable</option>
							<option value="1">Enable</option>
						</select>
					</div>
				</div>
				
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-closeondeparture">Update</button>
     		</div>
		</div>
		
	</div>  
	
	
	
	
	
	<div class="pushbookingstoezeelist display">
		<h3> Get Bookings to eZee </h3><br/>
		
		<div style="margin-left:15px; width:700px;">
		
		<div class="bg-success" id="successmsgbookingstoezee" style="display:none;width:50%;padding:10px;">
			<strong><h5 id="pushbookingstoezeesuccess">Updated Successfully!</h5></strong> 
		</div>
		<div class="bg-danger" id="errormsgbookingstoezee" style="display:none;width:50%;padding:10px;">
			<strong>Booking Does not exists!</strong> 
		</div><br/><br/>
		
         <form>
         	<div class="row">
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="esubbookingid">Sub Booking Id</label> 
						<select class="form-control esubbookingid" id="esubbookingid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eratetypeid">Rate Type Id</label> 
						<select class="form-control eratetypeid" id="eratetypeid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eratetype">Rate Type</label> 
						<select class="form-control eratetype" id="eratetype">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eroomtypecode">Room Type Code</label> 
						<select class="form-control eroomtypecode" id="eroomtypecode">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eroomtypename">Room Type Name</label> 
						<select class="form-control eroomtypename" id="eroomtypename">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="estart">Start</label> 
						 <input type="date" class="form-control estart" id="estart" value="2022-11-11"
				  		  placeholder="Enter Start Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eend">End</label> 
						 <input type="date" class="form-control eend" id="eend" value="2022-12-11"
				  		  placeholder="Enter End Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="etotalrate">Total Rate</label> 
						<input type="text" class="form-control etotalrate" id="etotalrate" value="1000"
				  		  placeholder="Enter Total Rate" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="etotaldiscount">Total Discount</label> 
						<input type="text" class="form-control etotaldiscount" id="etotaldiscount" value="1000"
				  		  placeholder="Enter Total Discount" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="etotalextracharge">Total Extra Charge</label> 
						<input type="text" class="form-control etotalextracharge" id="etotalextracharge" value="1000"
				  		  placeholder="Enter Total Extra Charge" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="etotaltax">Total Tax</label> 
						<input type="text" class="form-control etotaltax" id="etotaltax" value="1000"
				  		  placeholder="Enter Total Tax" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="etotalpayment">Total Payment</label> 
						 <input type="text" class="form-control etotalpayment" id="etotalpayment" value="1000"
				  		  placeholder="Enter Total Payment" >
					</div>
				</div>
			
				<div class="col-sm-6">
					<div class="form-group">
						<label for="esalutation">Salutation</label> 
						 <select class="form-control esalutation" id="esalutation">
							<option value="Mr." selected>Mr.</option>
							<option value="Mrs.">Mrs.</option>
							<option value="Ms.">Ms.</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="efirstname">First Name</label> 
						 <input type="text" class="form-control efirstname" id="efirstname" value="#"
				  		  placeholder="Enter FirstName" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="elastname">Last Name</label> 
						 <input type="text" class="form-control elastname" id="elastname" value="#"
				  		  placeholder="Enter LastName" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="egender">Gender</label> 
						<select class="form-control egender" id="egender" >
							<option value="male">Male</option>
							<option value="female">Female</option>
							<option value="other">Other</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eaddress">Address</label> 
						<textarea class="form-control eaddress" id="eaddress" placeholder="Enter Address" >#</textarea>
					</div>
				</div>
			
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ecity">City</label> 
						 <input type="text" class="form-control ecity" id="ecity" value="#"
				  		  placeholder="Enter City" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="estate">State</label> 
						 <input type="text" class="form-control estate" id="estate" value="#"
				  		  placeholder="Enter State" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ecountry">Country</label> 
						 <input type="text" class="form-control ecountry" id="ecountry" value="#"
				  		  placeholder="Enter Country" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ezipcode">Zip Code</label> 
						 <input type="text" class="form-control ezipcode" id="ezipcode" value="#"
				  		  placeholder="Enter Zip Code" >
					</div>
				</div>
		
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ephone">Phone</label> 
						 <input type="text" class="form-control ephone" id="ephone" value="#"
				  		  placeholder="Enter Phone Number" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="emobile">Mobile</label> 
						 <input type="text" class="form-control emobile" id="emobile" value="#"
				  		  placeholder="Enter Mobile Number" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="efax">Fax</label> 
						 <input type="text" class="form-control efax" id="efax" value="#"
				  		  placeholder="Enter Fax" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eemail">Email</label> 
						 <input type="text" class="form-control eemail" id="eemail" value="#"
				  		  placeholder="Enter Email" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="etransportationmode">Transportation Mode</label> 
						 <input type="text" class="form-control etransportationmode" id="etransportationmode" value="#"
				  		  placeholder="Enter Transportation Mode" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="evehicle">Vehicle</label> 
						 <input type="text" class="form-control evehicle" id="evehicle" value="#"
				  		  placeholder="Enter Vehicle" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="epickupdate">Pick Up Date</label> 
						 <input type="date" class="form-control epickupdate" id="epickupdate" value="#"
				  		  placeholder="Enter Pick Up Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="epickuptime">Pick Up Time</label> 
						 <input type="time" class="form-control epickuptime" id="epickuptime" value="#"
				  		  placeholder="Enter Pick Up Time" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ecomment">Comment</label> 
						 <input type="text" class="form-control ecomment" id="ecomment" value="#"
				  		  placeholder="Enter Comment" >
					</div>
				</div>
			
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eeffectivedate">Effective Date</label> 
						 <input type="date" class="form-control eeffectivedate" id="eeffectivedate" value="#"
				  		  placeholder="Enter Effective Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eadult">Adult</label> 
						 <input type="number" class="form-control eadult" id="eadult" value="#"
				  		  placeholder="Enter Number Of Adults" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="echild">Children</label> 
						 <input type="number" class="form-control echild" id="echild" value="#"
				  		  placeholder="Enter Number Of Children" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="erent">Rent</label> 
						 <input type="number" class="form-control erent" id="erent" value="#"
				  		  placeholder="Enter Rent" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eextracharge">Extra Charge</label> 
						 <input type="number" class="form-control eextracharge" id="eextracharge" value="#"
				  		  placeholder="Enter Rent" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="etax">Tax</label> 
						 <input type="number" class="form-control etax" id="etax" value="#"
				  		  placeholder="Enter Tax" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ediscount">Discount</label> 
						 <input type="number" class="form-control ediscount" id="ediscount" value="#"
				  		  placeholder="Enter Discount" >
					</div>
				</div>
				
				
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ebookingid">Booking Id</label> 
						<select class="form-control ebookingid" id="ebookingid">
							<option>SELECT</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="estatus">Status</label> 
						<select class="form-control estatus" id="estatus">
							<option value="New" selected>New</option>
							<option value="Modify">Modify</option>
							<option value="Cancel">Cancel</option>
						</select>
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="esource">Source</label> 
						<input type="text" class="form-control esource" id="esource" value="#"
				  		  placeholder="Enter Source" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ecode">Code</label> 
						<input type="text" class="form-control ecode" id="ecode" value="#"
				  		  placeholder="Enter Code" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eccno">Ccno</label> 
						<input type="text" class="form-control eccno" id="eccno"  value="#"
				  		  placeholder="Enter Cctype" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ecctype">CcType</label> 
						<input type="text" class="form-control ecctype" id="ecctype" value="#"
				  		  placeholder="Enter Cctype" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="eccexpirydate">Cc Expiry Date</label> 
						 <input type="date" class="form-control eccexpirydate" id="eccexpirydate" value="#"
				  		  placeholder="Enter Expiry Date" >
					</div>
				</div>
				
				<div class="col-sm-6">
					<div class="form-group">
						<label for="ecardholdersname">Card Holders Name</label> 
						 <input type="text" class="form-control ecardholdersname" id="ecardholdersname" value="#"
				  		  placeholder="Enter Card holders Name" >
					</div>
				</div>
	
			</div>
         </form>
        	<div class="modal-footer">
          		<button type="button" class="btn btn-default" id="btn-eZee">Update</button>
     		</div>
		</div>
		
	</div>  
	
	
	
	
		
</div>
	