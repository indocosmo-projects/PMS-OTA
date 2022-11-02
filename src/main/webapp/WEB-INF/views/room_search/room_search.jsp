 <div class="modal fade" id="room_Search_Modal" tabindex="-1"
	role="dialog" aria-labelledby="roomSearchModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id="btn_RoomAssign_Close"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">AVAILABLE ROOMS</h4>
			</div>
			<div class="modal-body">
				<div class="panel-body">
					<div class="row form-group">
						<div class="col-lg-4">
							<input type='text' placeholder='Search room' id="txt_roomassign"
								class='form-control form-control-inline input-medium'
								onkeyup='filterSearchResult(this);'>
						</div>
					</div>

					<div class="row form-group">
						<div class="col-lg-12" style="height: 350px; overflow-y: auto;">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th style="width: 12%; text-align: center;">Select</th>
										<th style="width: 25%;">Room No.</th>
										<th>Name</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<input type="button" id="btn_RoomAssign_Ok" class="btn btn-success"
					value="Ok"> <input type="button" id="btn_RoomAssign_Cancel"
					class="btn btn-default" value="Cancel">
			</div>
		</div>
	</div>
</div> 