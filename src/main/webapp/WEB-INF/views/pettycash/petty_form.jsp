
<jsp:directive.include file="../common/includes/page_directives.jsp" />
<%@ page import="com.indocosmo.pms.enumerator.PettyAmount"%>
<c:set var="paymentMode" value="<%=PettyAmount.values()%>"></c:set>
<section class="panel" id="show_table">
	<div class="panel-body petty_cash_div">
		<div class="form-group align-btn-rgt topAddButtonDiv"></div>
		<!-- search -->
		<div class="col-sm-12">
			<div class="row">
				<div class="col-md-11">
					<div
						class="input-group date form_datetime-adv form-control search_box_div">
						<div class="serach_div" id="simpleSearchTxt">
							<md-datepicker id="datepicker_id" ng-model="search.searchValue"
								md-min-date="search.resvDate.searchValue" md-open-on-focus ></md-datepicker>
							</md-input-container>

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



				<div class="col-md-1">
					<!-- <div class="form-inline"> -->

					<%--  ${cp_isCanAdd}  --%>

					<c:if test="${cp_isCanAdd}">
						<div class="form-group tab_add_btn">

							<div class="form-group tab_add_btn newreservation">
								<md-button class="md-raised md-primary" id="addPettyHead"
									ng-click="addPettyHead()"> <spring:message
									code="petty.label.add" /> <i class="fa fa-plus"></i></md-button>
							</div>
						</div>
					</c:if>
					<!-- </div> -->
				</div>
			</div>
		</div>

		<div class="cmn_tbl">
			<table class="table table-striped table-hover table-bordered">
				<thead>
					<tr>
						<th style="width: 10%; max-width: 270px; text-align: center;">Date</th>
						<th style="width: 20%; max-width: 270px; text-align: center;">Opening</th>
						<th style="width: 10%; max-width: 270px; text-align: center;">Contra
						</th>
						<th style="width: 5%; max-width: 270px; text-align: center;">Payment</th>
						<th style="width: 20%; max-width: 270px; text-align: center;">Balance</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-if="expenseDetails.length === 0">
						<td colspan="5">No data available</td>
					<tr ng-repeat="items in expenseDetails">
						<td><a href="#" ng-click="selectExp(items.entryDate)"
							style="text-align: center;"><input type="hidden"
								value="{{items.entryDate}}">{{items.entryDate |
								date:'dd-MM-yyyy'}}</a></td>
						<td style="text-align: center;">{{items.openingBalance}}</td>
						<td style="text-align: center;">{{items.reciept}}</td>
						<td style="text-align: right;">{{items.payment}}</td>
						<td>{{items.closingBalance}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!-- pagination -->
		<div class="panel-body footer_div">
			<div class="limit_drop col-md-2">
				<div class="list_info">Showing {{totalItems}} of
					{{bigTotalItems}} Items</div>
				<select id="sel_pag_id" ng-model="pagination.limit"
					class="selectpicker form-control" ng-change="loadDataList()">
					<option value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
				</select>

			</div>
			<div class="pagination_div col-md-5">
				<ul uib-pagination total-items="bigTotalItems"
					ng-model="bigCurrentPage" max-size="maxSize"
					items-per-page="itemsPerPage" ng-change="pageChanged()"
					class="pagination-sm" boundary-links="true" rotate="false"></ul>
			</div>
		</div>
	</div>
</section>
<section class="panel" id="show_form">
	<div class="panel-body">
		<div id="dataTableDiv" class="col-lg-12">
			<div class="error_msg" style="display: none"></div>
			<div class="form-group align-btn-rgt topAddButtonDiv">
				<c:if test="${cp_isCanAdd}">
					<button type="button"
						class="md-raised md-warn md-button md-ink-ripple" id="addCategory"
						ng-click="loadCategory()">
						CATEGORY <i class="fa fa-plus"></i>
					</button>
				</c:if>
				<c:if test="${cp_isCanEdit}">
					<button type="button"
						class="md-raised md-primary md-button md-ink-ripple" id="btnEdit"
						ng-click="editPetty()">EDIT</button>
				</c:if>
				<c:if test="${cp_isCanDelete}">
					<button type="button"
						class="md-raised md-warn md-button md-ink-ripple" id="btnDelete"
						ng-click="deletePetty()">
						Delete <i class="fa fa-minus"></i>
					</button>
				</c:if>
				<button type="button"
					class="md-raised md-primary md-button md-ink-ripple"
					ng-click="saveExpense($event)" id="btnSave">Save</button>
			</div>
		</div>

		<div class="form-horizontal tasi-form">
			<div class="form-group col-md-12">
				<label class="tableLabel col-md-2 col-xs-5"> <spring:message
						code="petty.label.date"></spring:message></label>
				<div class="col-md-2 col-xs-5">

					<%--  <input type="hidden" value="${dateFormat}" id="dateFormat" /> <input
						type="text" id="hotelDate" value="${hotelDate}"
						class="form-control  form-control-inline input-medium"
						ng-model="pettyDate" ng-disabled="true" />  --%>
					<!--  <md-datepicker ng-model="pettyDate" id="entry_date" path="entryDate" class="form-control form-control-inline input-medium"
						md-open-on-focus></md-datepicker>   -->
					<%-- <form:input path="entryDate" id="entry_date"
						cssClass="form-control form-control-inline input-medium datepicker validator "
						autocomplete="off" readonly="true" /> --%>
						
						<input
						type="text" id="entry_date"
						class="form-control  form-control-inline input-medium"
						ng-model="pettyDate" ng-disabled="true" />
				</div>
			</div>
			<div class="form-group col-md-12">
				<label class="tableLabel col-md-2 col-xs-5"> <spring:message
						code="petty.label.opening"></spring:message></label>
				<div class="col-md-2 col-xs-5">
					<input type="text" id="openingBalance" maxlength="10"
						ng-keyup="updateBal()"
						class="form-control validator form-control-inline input-medium"
						ng-model="openingBalance"
						onkeypress="return event.charCode === 0 || /\d/.test(String.fromCharCode(event.charCode));"
						ng-disabled="true" />
				</div>
			</div>
			<div class="form-group col-md-12">
				<label class="tableLabel col-md-2 col-xs-5"> <spring:message
						code="petty.label.closing"></spring:message></label>
				<div class="col-md-2 col-xs-5">
					<input type="text" id="name" maxlength="10"
						class="form-control validator form-control-inline input-medium"
						ng-model="closigBalance" ng-disabled="true" value="" />
				</div>
			</div>
		</div>
		<div class="col-sm-12">
			<div class="adv-table editable-table">
				<table
					class="table table-striped table-hover table-bordered petty_table"
					id="petty_table">
					<thead>
						<tr>
							<th>#</th>
							<th style="width: 20%; max-width: 270px; text-align: center;">Head</th>
							<th style="width: 15%; max-width: 270px; text-align: center;">Voucher
								Type</th>
							<th style="width: 10%; max-width: 270px; text-align: center;">Debit</th>
							<th style="width: 10%; max-width: 270px; text-align: center;">Credit</th>
							<th style="width: 10%; max-width: 270px; text-align: center;">Credit(Credit Card)</th>
							<th style="text-align: center;">Narration</th>
							<th></th>
						</tr>
					</thead>
					<tbody class="petty_details_body">

						<tr class="petty_list_row"
							ng:repeat="item in pettyExpenseList.item">


							<td>{{$index+1}}</td>
							<td ng-click="tableClicked($index)">
								<div class="form-group col-md-12">
									<input type="hidden" ng-model="item.id" value=""> <select
										class="form-control m-bot15" id="categoryId{{$index}}"
										ng-model="item.categoryId" ng-disabled="disable_all"
										ng-options="v.id as v.name for v in pettyList">
										<option></option>
									</select>
									<!-- <select class="form-control m-bot15" id="categoryId">
								<option ng-repeat="petty in pettyList" ng-model="item.categoryId" value="{{name}}">{{petty.name}}</option>
								</select> -->
								</div>

							</td>
							<td>
								<div class="resv-txt">
									<div class="form-group col-md-12">
										<select class="form-control paymentMode"
											id="paymentMode{{$index}}" ng-model="item.voucherType"
											ng-change="loadAmount($index)" name="paymentMode"
											ng-disabled="disable_all">
											<c:forEach items="${paymentMode}" var="paymentMode">
												<option>${paymentMode}</option>
											</c:forEach>
										</select>
									</div>

								</div>
							</td>
							<td class="amount">
								<div class="resv-txt">
									<div class="form-group col-md-12">
										<input class="form-control debitAmt"
											ng-keypress="closing_balance()" id="debitAmt{{$index}}"
											maxlength="10" ng-model="item.debitAmount" type="text"
											ng-disabled="disable_all"
											onkeypress="return event.charCode === 0 || /\d/.test(String.fromCharCode(event.charCode));">
									</div>
								</div>
							</td>
							<td class="amount">
								<div class="resv-txt">
									<div class="form-group col-md-12">
										<input class="form-control creditAmt"
											ng-keypress="closing_balance()" id="creditAmt{{$index}}"
											maxlength="10" ng-model="item.creditAmount" type="text"
											ng-disabled="disable_all"
											onkeypress="return event.charCode === 0 || /\d/.test(String.fromCharCode(event.charCode));"
											row-add="addItem($index)">
									</div>
								</div>
							</td>
							<td class="amount">
								<div class="resv-txt">
									<div class="form-group col-md-12">
										<input class="form-control creditAmt"
											ng-keypress="closing_balance()" id="creditCardAmt{{$index}}"
											maxlength="10" ng-model="item.creditCardAmount" type="text"
											ng-disabled="disable_all"
											onkeypress="return event.charCode === 0 || /\d/.test(String.fromCharCode(event.charCode));"
											row-add="addItem($index)">
									</div>
								</div>
							</td>
							<td>
								<div class="resv-txt">
									<div class="form-group col-md-12">
										<input class="form-control" ng-model="item.narration"
											maxlength="50" ng-disabled="disable_all" id="narration" type="text"
											row-add="addItem($index)" row-delete="removeItem($index)">
									</div>
								</div>
							</td>
							<td><a href ng:click="removeItem($index,item.id)"
								class="btn btn-small"><i class="fa fa-minus "></i> </a></td>
						</tr>
						<tr>
							<td colspan="2"></td>
							<td id="label_tot"><label class="tableLabel col-md-12">
									<spring:message code="petty.label.total"></spring:message>
							</label></td>

							<td id="debit_tot_td">{{debitTot()}}</td>

							<td id="credit_tot_td">{{creditTot()}}</td>
							<td id="credit_tot_td">{{creditCardAmtTot()}}</td>
							
							<td></td>
							<!-- <td colspan="6"></td>-->
							<td><a href ng:click="addItem()" id="row_add_id"
								class="btn btn-small"><i class="fa fa-plus"></i> </a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<!-- category modal -->
		<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog"
			tabindex="-1" id="categoryModal" class="modal fade">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button aria-hidden="true" data-dismiss="modal" class="close"
							type="button">x</button>
						<h4 class="modal-title">Petty Expense Head</h4>
					</div>
					<div class="modal-body" style="overflow-y: auto;">
						<!-- modal data comes here -->
						<div class="form-horizontal tasi-form" id="modalContent">
							<div class="form-group">
								<label class="control-label col-md-3"> Head <span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">
									<input type="text" id="pettyHead"
										class="form-control modalContent_class" ng-model="pettyHead"
										required />
								</div>
								<div class="col-md-1 col-xs-11 tick_green">
									<span><i class="fa fa-warning"></i></span>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-3"> Category <span
									class="red">*</span></label>
								<div class="col-md-6 col-xs-11">

									<select class="form-control  modalContent_class"
										ng-model="headId"
										ng-options="v.id as v.name for v in categoryList"
										id="category_head">
										<option value="0">Select</option>

									</select>
								</div>


							</div>

							<div class="form-group">
								<label class="control-label col-md-3"> Description</label>
								<div class="col-md-6 col-xs-11">
									<input type="text" id="decription"
										class="form-control modalContent_class" ng-model="description" />
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
							ng-click="saveCategory()">
							<spring:message code="pms.btn.save" />
						</button>

					</div>
				</div>
			</div>
		</div>


	</div>
</section>