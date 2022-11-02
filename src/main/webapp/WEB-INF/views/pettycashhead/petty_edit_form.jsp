
<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<div class="panel-body petty_head_div">
		<div class="col-sm-12">
			<div class="row">
				<div class="col-md-11">
					<div
						class="input-group date form_datetime-adv form-control search_box_div">
						<div class="serach_div" id="simpleSearchTxt">
						    <input class="form-control cat_serach_div" type="text" ng-model="searchValue">

						</div>
						<div class="input-group-btn tools clear_btn">
							<div class="search_claear_btn_main_div">
								<button class="search_claear_btn" ng-click="searchBoxClear()">
									<i class="fa fa-times"></i>
								</button>
							</div>

							<button type="button" class="search_button btn"
								ng-click="simpleSearch()">Search</button>
						</div>

					</div>
				</div>
			</div>
		</div>
		<div class="cmn_tbl">
			<table class="table table-striped table-hover table-bordered">
				<thead>
					<tr>
						<th style="width: 10%; max-width: 270px; text-align: center;">#</th>
						<th style="width: 20%; max-width: 270px; text-align: center;">Name</th>
						<th style="width: 10%; max-width: 270px; text-align: center;">Description</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-if="pettyHdList.item.length === 0">
						<td colspan="5">No data available</td>
					</tr>
					<tr ng-repeat="item  in pettyHdList.item">
						<td style="text-align: center;"><input type="hidden"
							value="{{item.id}}"> <a ng-click="petty_edit(item.id)"
							style="cursor: pointer;" ng-href="#">{{$index+1}}</a></td>
						<td style="text-align: center;">{{item.name}}</td>
						<td style="text-align: center;">{{item.description}}</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
			tabindex="-1" id="categoryeditModal" class="modal fade">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button aria-hidden="true" data-dismiss="modal" class="close"
							type="button">x</button>
						<h4 class="modal-title">Petty Head</h4>
					</div>
					<div class="modal-body" style="overflow-y: auto;">
						<!-- modal data comes here -->
						<div class="form-horizontal tasi-form">
							<div class="form-group">
								<label class="control-label col-md-3"> Head <span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">
									<input type="hidden" ng-model="pettyId" value=""> <input
										type="text" id="pettyHead" class="form-control petty_head_id"
										ng-model="pettyHeadSub" required disabled />
								</div>
								<div class="col-md-1 col-xs-11 tick_green">
									<span><i class="fa fa-warning"></i></span>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3"> Category <span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">
									<input type="hidden" ng-model="categoryId" value="categoryId">
									<!--   <select class="form-control m-bot15" ng-model="name"
										ng-options="v.id as v.name for v in pettyList" ng-selected="name == value.id">
									<option value="{{value.id}}">{{value.name}}</option>
									</select>   -->
									<select ng-model="name"
										class="form-control m-bot15 petty_head_id"
										ng-change="loadCategory(name)">
										<option ng-repeat="value in pettyList" value="{{value.name}}"
											ng-selected="name == value.id">{{value.name}}</option>
									</select>
								</div>


							</div>

							<div class="form-group">
								<label class="control-label col-md-3"> Description</label>
								<div class="col-md-6 col-xs-11">
									<input type="text" id="description"
										class="form-control petty_head_id" ng-model="description"
										disabled />
								</div>

							</div>
						</div>
						<!-- modal data comes here -->
					</div>
					<div class="modal-footer">
						<button id="cancelProvider" type="button"
							class="btn btn-default rbtnClose" ng-click="cancelPopUp();">
							<spring:message code="pms.btn.cancel" />
						</button>
						<button type="button" class="btn btn-success"
							ng-click="saveCategory($index)">
							<spring:message code="pms.btn.save" />
						</button>
						<c:if test="${cp_isCanEdit}">
							<button id=edit_petty type="button" class="btn btn-warning"
								ng-click="editPetty()">EDIT</button>
						</c:if>
						<c:if test="${cp_isCanDelete}">
							<button id=delete_petty type="button" class="btn btn-warning"
								ng-click="deletePetty(pettyId)">
								<spring:message code="pms.btn.delete" />
							</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
