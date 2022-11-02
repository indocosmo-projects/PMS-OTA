<jsp:directive.include file="../common/includes/page_directives.jsp" />
<section class="panel">
	<div class="panel-body">
		<div class="hs_filter col-md-3">
			<div class="col-md-4 col-sm-4 col-xs-4">
				<md-checkbox ng-change="filterFunction()" ng-model="hk_filter.dirty"
					class="md-primary" aria-label="Checkbox 1">DIRTY</md-checkbox>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<md-checkbox ng-change="filterFunction()" ng-model="hk_filter.clean"
					class="md-primary" aria-label="Checkbox 1">CLEAN</md-checkbox>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<md-checkbox ng-change="filterFunction()"
					ng-model="hk_filter.cleaning" class="md-primary"
					aria-label="Checkbox 1">CLEANING</md-checkbox>
			</div>
		</div>


		<div class="col-md-7">
			<div class="roomType col-md-2 hkedit_label">Room Type</div>
			<div class="roomOption col-md-4">
				<select class="form-control selectOption" ng-model="rmType"
					ng-change="loadRoomDetails()">
					<option ng-repeat="type in roomTypesList" value="{{type.id}}">{{type.name}}
					</option>
				</select>
			</div>
			<div class="floor_rgt_div col-md-6">
				<div class="col-md-3 hkedit_label">Floor</div>
				<div class="floorOption col-md-9 ">
					<select class="form-control selectfloorOption" ng-model="floor"
						ng-change="loadRoomDetails()">
						<option ng-repeat="type in floorList" value="{{type.id}}">{{type.name}}</option>
					</select>
					<!-- <md-select class="form-control" ng-model="floor"  ng-change="loadRoomDetails()"> 
										<md-option ng-repeat="type in floorList" value="{{type.id}}">{{type.name}} 
										</md-option>
								</md-select> -->
				</div>
			</div>
		</div>




		<div class="grid_list_div">
			<div class="grid_div col-md-10 col-sm-9 col-xs-9">
				<md-grid-list md-cols="1" md-cols-sm="2" md-cols-md="3"
					md-cols-gt-md="6" md-row-height-gt-md="1:1" md-row-height="4:2"
					md-gutter="8px" md-gutter-gt-sm="4px"> <md-grid-tile
					ng-repeat="room in roomDtlList" md-rowspan="{{room.tile.span.row}}"
					md-colspan="{{room.tile.span.col}}" md-colspan-sm="1"
					md-colspan-xs="1" ng-style="{'background': room.tile.background}"
					ng-class="room.tile.background"
					ng-click="editStatus(room.roomid,room.roomTypeCode,room.roomNumber,room.hkStatus)">
				<md-grid-tile-header>
				<div class="tile_room_type_code">
					<div class="room_grid_header_left">{{room.roomTypeCode}}</div>
					<div
						class="room_grid_header_right {{room.hkStatus==1?'dirty':''}} {{room.hkStatus==2?'cleaning':''}} {{room.hkStatus==3?'cleaned':''}}">
						<i class="fa fa-bed" aria-hidden="true"></i>
					</div>
				</div>
				</md-grid-tile-header>
				
				<div class="tile_details">{{room.roomNumber}}</div>
				<md-grid-tile-footer>
				<div class="tile_footer_edit"
					ng-if="room.hkStatus==1 && room.checkinStatus==7">
					<span class="room_grid_footer_label">Last Checkout</span><span
						class="room_grid_footer_desc">{{room.actualDepartDate |
						date:'yyyy-MM-dd'}}</span>
				</div>
				<div class="tile_footer_edit" ng-if="room.occStatus==1">
					<span class="room_grid_footer_label">CheckIn Date</span><span
						class="room_grid_footer_desc">{{room.arrivalDate |
						date:'yyyy-MM-dd'}}</span>
				</div>
				<div class="tile_footer_edit"
					ng-if="room.hkStatus!=1 && room.occStatus==2">VACANT</div>

				<div class="tile_footer_edit"
					ng-if="room.hkStatus!=1 && room.resvStatus==1 && room.occStatus==2">RESERVED</div>

				</md-grid-tile-footer> </md-grid-tile> </md-grid-list>
			</div>
			<div class="room_sub_hdr col-md-2 col-sm-3 col-xs-3">
				<div class="room_info occ_stat row">
					<div class="rm_st">
						<span class="dtl_span">OCCUPIED</span> <span
							class="color_span occupied"></span>
					</div>
					<div class="rm_st">
						<span class="dtl_span">VACANT</span> <span
							class="color_span vacant"></span>
					</div>
					<div class="rm_st">
						<span class="dtl_span">RESERVED</span> <span
							class="color_span reserved"></span>
					</div>
					<div class="rm_st">
						<span class="dtl_span">OUT OF INV</span> <span
							class="color_span outOfInv"></span>
					</div>
				</div>

				<div class="room_info hk_stat row">
					<div class="hk_status_label">
						<i class="fa fa-bed" aria-hidden="true"></i> HK
					</div>
					<div class="hk_status_colors">
						<div class="rm_st">
							<span class="dtl_span">DIRTY</span> <span
								class="color_span dirty"></span>
						</div>
						<div class="rm_st">
							<span class="dtl_span">CLEAN</span> <span
								class="color_span cleaned"></span>
						</div>
						<div class="rm_st">
							<span class="dtl_span">CLEANING</span> <span
								class="color_span cleaning"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
			tabindex="-1" id="newHKStatusmyModal" class="modal fade">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button aria-hidden="true" data-dismiss="modal" class="close"
							type="button">×</button>
						<h4 class="modal-title">
							<div class="room_name_div">
								<div class="room_edit_rtype">{{roomType}}</div>
								<div class="room_edit_number">{{roomNum}}</div>
							</div>
						</h4>
					</div>
					<div class="modal-body md-inline-form" style="overflow-y: auto;">


						<div layout-gt-xs="row">
							<md-radio-group class="status" ng-model="status">
							<md-radio-button value="1" class="md-primary rd_btn">DIRTY</md-radio-button>
							<br />
							<md-radio-button value="2" class="md-primary rd_btn">
							CLEANING </md-radio-button> <br />
							<md-radio-button value="3" class="md-primary rd_btn">CLEAN</md-radio-button>
							</md-radio-group>
						</div>
					</div>
					<div class="modal-footer">
						<input type="hidden" id="update" value="save" ng-model="hk.update" />
						<button id="saveHkStatusBtn" type="button" class="btn btn-success"
							ng-click="saveHk()">
							<spring:message code="pms.btn.save" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>