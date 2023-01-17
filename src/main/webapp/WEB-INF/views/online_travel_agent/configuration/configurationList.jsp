<div class="default">
	<img src="/pms/resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
	
	<h3 class="header1">Configuration</h3>
	
	<div class="checkhotelauthenticationlist display">		
		<h3>Check Hotel Authentication</h3>
		
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgcheckhotelauthentication" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="checkhotelauthenticationsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgcheckhotelauthentication" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="checkhotelauthenticationauthkey">Auth Key</label> 
						  		  <select class="form-control checkhotelauthenticationauthkey" id="checkhotelauthenticationauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
				
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-checkhotelauthentication">Check</button>
		     		</div>
			</div>
			
			<div class="checkhotelauthenticationdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Hotel Code</th>
								<th class="tdwidth">Hotel Name</th>
							</tr>
						</thead>
						<tbody class="checkhotelauthenticationbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	<div class="retrieveroominformationlist display">		
		<h3>Retrieve Room Information</h3>

		<form>
			<div class="form-group">
			  <label for="roomrequired">Number Of Rooms:</label>
			  <input type="number" class="form-control roomrequired" id="roomrequired" min="1"
			  placeholder="Enter Required Number Of Rooms" value="1" disabled
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
	
	
	<div class="retrievehotelinformationlist display">		
		<h3>Retrieve Hotel Information</h3>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievehotelinformation" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievehotelinformationsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievehotelinformation" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievehotelinformationhotelcode">Hotel Code</label> 
						  		  <select class="form-control retrievehotelinformationhotelcode" id="retrievehotelinformationhotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievehotelinformationauthkey">Auth Key</label> 
						  		  <select class="form-control retrievehotelinformationauthkey" id="retrievehotelinformationauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
				
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievehotelinformation">Search</button>
		     		</div>
			</div>
			
			<div class="retrievehotelinformationdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Hotel Code</th>
								<th class="tdwidth">Hotel Name</th>
								<th class="tdwidth">Hotel Unkid</th>
								<th class="tdwidth">Country</th>
								<th class="tdwidth">Property Type</th>
								<th class="tdwidth">Booking Engine Url</th>
								<th class="tdwidth">Phone</th>
								<th class="tdwidth">Email</th>
								<th class="tdwidth">Booking Engine Folder Name</th>
								<th class="tdwidth">Currency Code</th>
							</tr>
						</thead>
						<tbody class="retrievehotelinformationbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	<div class="retrievehotelamenitieslist display">		
		<h3>Retrieve Hotel Amenities</h3>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievehotelamenities" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievehotelamenitiessuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievehotelamenities" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievehotelamenitieshotelcode">Hotel Code</label> 
						  		  <select class="form-control retrievehotelamenitieshotelcode" id="retrievehotelamenitieshotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievehotelamenitiesauthkey">Auth Key</label> 
						  		  <select class="form-control retrievehotelamenitiesauthkey" id="retrievehotelamenitiesauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
				
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievehotelamenities">Search</button>
		     		</div>
			</div>
			
			<div class="retrievehotelamenitiesdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Amenities</th>
							</tr>
						</thead>
						<tbody class="retrievehotelamenitiesbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	
	<div class="retrieveroomtypeslist display">		
		<h3>Retrieve Room Types</h3>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrieveroomtypes" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrieveroomtypessuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrieveroomtypes" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveroomtypeshotelcode">Hotel Code</label> 
						  		  <select class="form-control retrieveroomtypeshotelcode" id="retrieveroomtypeshotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveroomtypesauthkey">Auth Key</label> 
						  		  <select class="form-control retrieveroomtypesauthkey" id="retrieveroomtypesauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveroomtypespublishtoweb">Publish To Web(Room Types)</label> 
						  		  <select class="form-control retrieveroomtypespublishtoweb" id="retrieveroomtypespublishtoweb">
									<option value="0">Web Default</option>
									<option value="1">All Types</option>
								</select>
							</div>
						</div>
				
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrieveroomtypes">Search</button>
		     		</div>
			</div>
			
			<div class="retrieveroomtypesdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Room Type Unkid</th>
								<th class="tdwidth">Room Type </th>
								<th class="tdwidth">Short Code</th>
								<th class="tdwidth">Base Adult Occupancy</th>
								<th class="tdwidth">Base Child Occupancy</th>
								<th class="tdwidth">Max Adult Occupancy</th>
								<th class="tdwidth">Max Child Occupancy</th>
							</tr>
						</thead>
						<tbody class="retrieveroomtypesbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	
	<div class="retrievesalutationsandcountrylist display">		
		<h3>Retrieve Salutations and Country</h3>
		
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievesalutationsandcountry" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievesalutationsandcountrysuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievesalutationsandcountry" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievesalutationsandcountryhotelcode">Hotel Code</label> 
						  		  <select class="form-control retrievesalutationsandcountryhotelcode" id="retrievesalutationsandcountryhotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievesalutationsandcountryauthkey">Auth Key</label> 
						  		  <select class="form-control retrievesalutationsandcountryauthkey" id="retrievesalutationsandcountryauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievesalutationsandcountry">Search</button>
		     		</div>
			</div>
			
			<div class="retrievesalutationsandcountrydivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Salutation</th>
								<th class="tdwidth">Country</th>
							</tr>
						</thead>
						<tbody class="retrievesalutationsandcountrybody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	
	<div class="retrieveextrasratebasedonparameterslist display">		
		<h3>Retrieve Extras Rate Based on Parameters</h3>
		
		<div style="margin-left:15px; width:700px;">
			<div class="bg-success" id="successmsgretrieveextrasratebasedonparameters" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrieveextrasratebasedonparameterssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrieveextrasratebasedonparameters" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
				
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveextrasratebasedonparametershotelcode">Hotel Code</label> 
						  		  <select class="form-control retrieveextrasratebasedonparametershotelcode" id="retrieveextrasratebasedonparametershotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveextrasratebasedonparametersauthkey">Auth Key</label> 
						  		  <select class="form-control retrieveextrasratebasedonparametersauthkey" id="retrieveextrasratebasedonparametersauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveextrasratebasedonparameterscheckindate">Check In Date</label> 
						  		  <input type="date" class="form-control retrieveextrasratebasedonparameterscheckindate" id="retrieveextrasratebasedonparameterscheckindate" 
				  		  		  placeholder="Enter Check In Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveextrasratebasedonparameterscheckoutdate">Check Out Date</label> 
						  		  <input type="date" class="form-control retrieveextrasratebasedonparameterscheckoutdate" id="retrieveextrasratebasedonparameterscheckoutdate" 
				  		  		  placeholder="Enter Check Out Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveextrasratebasedonparametersextrachargeid">Extra Charge Id</label> 
						  		<select class="form-control retrieveextrasratebasedonparametersextrachargeid" id="retrieveextrasratebasedonparametersextrachargeid">
									<option value="2">Single</option>
									<option value="3">Multiple</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveextrasratebasedonparametersextraitem">Extra Item</label> 
						  		  <select class="form-control retrieveextrasratebasedonparametersextraitem" id="retrieveextrasratebasedonparametersextraitem">
									<option value="2">Single</option>
									<option value="3">Multiple</option>
								</select>
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrieveextrasratebasedonparameters">Search</button>
		     		</div>
			</div>
			
			<div class="retrieveextrasratebasedonparametersdivlist display">
					<div>
						<h4>Individual Charge : <strong> <span class="individualcharge">0</span></strong></h4>
						<h4>Total Charge      : <strong> <span class="totalcharge">0</span></strong></h4>
					</div>
			</div><br/>
	</div>
	
	
	
	<div class="verifytravelagentlist display">		
		<h3>Verify Travel Agent</h3>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgverifytravelagent" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="verifytravelagentsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgverifytravelagent" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="verifytravelagenthotelcode">Hotel Code</label> 
						  		  <select class="form-control verifytravelagenthotelcode" id="verifytravelagenthotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="verifytravelagentauthkey">Auth Key</label> 
						  		  <select class="form-control verifytravelagentauthkey" id="verifytravelagentauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="verifytravelagentusername">Username</label> 
						  		  <input type="text" class="form-control verifytravelagentusername" id="verifytravelagentusername" 
				  		  		  placeholder="Enter Username" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="verifytravelagentpassword">Password</label> 
						  		  <input type="password" class="form-control verifytravelagentpassword" id="verifytravelagentpassword" 
				  		  		  placeholder="Enter Password" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="verifytravelagentgroupcode">Group Code</label> 
						  		  <input type="text" class="form-control verifytravelagentgroupcode" id="verifytravelagentgroupcode" 
				  		  		  placeholder="Enter Group Code" >
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-verifytravelagent">Verify</button>
		     		</div>
			</div>
			
			<div class="verifytravelagentdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Contact Name</th>
								<th class="tdwidth">Business Name</th>
								<th class="tdwidth">Email</th>
							</tr>
						</thead>
						<tbody class="verifytravelagentbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	
	<div class="retrievepaymentgatewayslist display">		
		<h3>Retrieve Payment Gateways</h3>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievepaymentgateways" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievepaymentgatewayssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievepaymentgateways" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievepaymentgatewayshotelcode">Hotel Code</label> 
						  		  <select class="form-control retrievepaymentgatewayshotelcode" id="retrievepaymentgatewayshotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievepaymentgatewaysauthkey">Auth Key</label> 
						  		  <select class="form-control retrievepaymentgatewaysauthkey" id="retrievepaymentgatewaysauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievepaymentgateways">Search</button>
		     		</div>
			</div>
			
			<div class="retrievepaymentgatewaysdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Payment Type Unkid</th>
								<th class="tdwidth">Hotel Code</th>
								<th class="tdwidth">Short Code</th>
								<th class="tdwidth">Payment Type</th>
							</tr>
						</thead>
						<tbody class="retrievepaymentgatewaysbody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	
	<div class="retrievecurrencylist display">		
		<h3>Retrieve Currency</h3>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievecurrency" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievecurrencysuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievecurrency" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievecurrencyhotelcode">Hotel Code</label> 
						  		  <select class="form-control retrievecurrencyhotelcode" id="retrievecurrencyhotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievecurrencyauthkey">Auth Key</label> 
						  		  <select class="form-control retrievecurrencyauthkey" id="retrievecurrencyauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievecurrency">Search</button>
		     		</div>
			</div>
			
			<div class="retrievecurrencydivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Currency ID</th>
								<th class="tdwidth">Country</th>
								<th class="tdwidth">Currency</th>
								<th class="tdwidth">Currency Code</th>
								<th class="tdwidth">Sign</th>
								<th class="tdwidth">DigitsAfterDecimal</th>
								<th class="tdwidth">IsBaseCurrency</th>
								<th class="tdwidth">ExchangeRate</th>
							</tr>
						</thead>
						<tbody class="retrievecurrencybody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	
	<div class="retrievepaymethodslist display">		
		<h3>Retrieve Pay Methods</h3>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrievepaymethods" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrievepaymethodssuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrievepaymethods" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievepaymethodshotelcode">Hotel Code</label> 
						  		  <select class="form-control retrievepaymethodshotelcode" id="retrievepaymethodshotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrievepaymethodsauthkey">Auth Key</label> 
						  		  <select class="form-control retrievepaymethodsauthkey" id="retrievepaymethodsauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrievepaymethods">Search</button>
		     		</div>
			</div>
			
			<div class="retrievepaymethodsdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Paymethod Id</th>
								<th class="tdwidth">Payment Id</th>
								<th class="tdwidth">Name</th>
								<th class="tdwidth">Shortcode</th>
								<th class="tdwidth">Type</th>
								<th class="tdwidth">Card Processing</th>
								<th class="tdwidth">Surcharge Applicable</th>
								<th class="tdwidth">Surcharge Type</th>
								<th class="tdwidth">Surcharge Value</th>
								<th class="tdwidth">Surcharge Id</th>
								<th class="tdwidth">Surcharge Name</th>
							</tr>
						</thead>
						<tbody class="retrievepaymethodsbody">
						</tbody>
					</table>
				</div>
			</div>
		
	</div>
	
	
	<div class="retrieveidentitytypelist display">		
		<h3>Retrieve Identity Type</h3>
		
		<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrieveidentitytype" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrieveidentitytypesuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrieveidentitytype" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveidentitytypehotelcode">Hotel Code</label> 
						  		  <select class="form-control retrieveidentitytypehotelcode" id="retrieveidentitytypehotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveidentitytypeauthkey">Auth Key</label> 
						  		  <select class="form-control retrieveidentitytypeauthkey" id="retrieveidentitytypeauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
						
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrieveidentitytype">Search</button>
		     		</div>
			</div>
			
			<div class="retrieveidentitytypedivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Identity Type Id</th>
								<th class="tdwidth">Name</th>
							</tr>
						</thead>
						<tbody class="retrieveidentitytypebody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	
	
	<div class="retrieveavailableroomlist display">		
		<h3>Retrieve Available Room List</h3>
		
			<div style="margin-left:15px; width:700px;">
		
				<div class="bg-success" id="successmsgretrieveavailableroom" style="display:none;width:50%;padding:10px;">
					<strong><h5 id="retrieveavailableroomsuccess">Success!</h5></strong> 
				</div>
				<div class="bg-danger" id="errormsgretrieveavailableroom" style="display:none;width:50%;padding:10px;">
					<strong>No data found!</strong> 
				</div><br/><br/>
					
				  <form>
		         	<div class="row">
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveavailableroomhotelcode">Hotel Code</label> 
						  		  <select class="form-control retrieveavailableroomhotelcode" id="retrieveavailableroomhotelcode">
									<option value="18727">18727</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveavailableroomauthkey">Auth Key</label> 
						  		  <select class="form-control retrieveavailableroomauthkey" id="retrieveavailableroomauthkey">
									<option value="d44f6590f2c3@9148790807c57666de-bb8c-11ea-a">d44f6590f2c3@9148790807c57666de-bb8c-11ea-a</option>
								</select>
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveavailableroomfromdate">From Date</label> 
						  		  <input type="date" class="form-control retrieveavailableroomfromdate" id="retrieveavailableroomfromdate" 
				  		  		  placeholder="Enter From Date" >
							</div>
						</div>
						
						<div class="col-sm-6">
				         	<div class="form-group">
								<label for="retrieveavailableroomtodate">To Date</label> 
						  		  <input type="date" class="form-control retrieveavailableroomtodate" id="retrieveavailableroomtodate" 
				  		  		  placeholder="Enter To Date" >
							</div>
						</div>
					</div>
					
		         </form>
		        	<div class="modal-footer">
		          		<button type="button" class="btn btn-primary" id="btn-retrieveavailableroom">Search</button>
		     		</div>
			</div>
			
			<div class="retrieveavailableroomdivlist display">
				<div>
					<table class="table table-responsive table-bordered">
						<thead>
							<tr>
								<th class="tdwidth">Id</th>
								<th class="tdwidth">Room Types Id</th>
								<th class="tdwidth">Room Type Name</th>
								<th class="tdwidth">Room Id</th>
								<th class="tdwidth">Room Name</th>
							</tr>
						</thead>
						<tbody class="retrieveavailableroombody">
						</tbody>
					</table>
				</div>
			</div>
	</div>
	

	
	
</div>