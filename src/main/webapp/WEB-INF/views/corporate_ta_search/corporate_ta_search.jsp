<div class="modal fade" id="corp_search_modal" tabindex="-1"
	role="dialog" aria-labelledby="roomSearchModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" id="btn_corp_search_close"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="corp_search_title">CORPORATES</h4>
				<input type="hidden" id="hdn_corp_search_name" value="">
			</div>
			<div class="modal-body">
				<div class="panel-body">
					<div class="row form-group">
						<div class="col-lg-4">
							<input type='text' placeholder='Search here' id="txt_corp_search"
								class='form-control form-control-inline input-medium'
								onkeyup='filterSearchResult(this);'>
						</div>
					</div>

					<div class="row form-group">
						<div class="col-lg-12" style="height: 350px; overflow-y: auto;">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th style="width: 15%;">Select</th>
										<th style="width: 25%;">Code</th>
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
				<input type="button" id="btn_corp_search_ok" class="btn btn-success"
					value="Ok"> <input type="button"
					id="btn_corp_search_cancel" class="btn btn-default" value="Cancel">
			</div>
		</div>
	</div>
</div>