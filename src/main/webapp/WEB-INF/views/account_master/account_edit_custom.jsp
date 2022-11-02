<jsp:directive.include file="../common/includes/page_directives.jsp" />

<div class="form_main_div">

	<div class="row">
		<!-- page content start-->
		<form:hidden path="encryption" id="encryption_id" />
		<form:hidden path="id" id="accountmaster_id" />

		<div class="pms_row_bottom_align">
			<div class="pms_label_div">
				<form:label path="Code" cssClass="pms_label_vertical ">
					<spring:message code="pms.label.code"></spring:message>
				</form:label>
				<span class="pms_required"></span>
			</div>

			<div class="pms_input_div">
				<form:input path="code" id="txtcode"
					cssClass="pms_text validator refresh_account" maxlength="15" />
				<div id="validator_txtcode" class='validator_cls'></div>
			</div>
		</div>

		<div class="pms_row_bottom_align">
			<div class="pms_label_div">
				<form:label path="Name" cssClass="pms_label_vertical">
					<spring:message code="pms.label.name"></spring:message>
				</form:label>

				<span class="pms_required"></span>
			</div>
			<div class="pms_input_div">
				<form:input path="name" id="txtname"
					cssClass="validator pms_text refresh_account" maxlength="40" />
				<div id="validator_txtname" class='validator_cls'></div>
			</div>
		</div>

		<div class="pms_row_bottom_align currency_textarea">

			<div class="pms_label_div">
				<form:label path="Description" cssClass="pms_label_vertical">
					<spring:message code="pms.label.description"></spring:message>
				</form:label>
			</div>
			<div class="pms_input_div">
				<form:textarea path="description"
					cssClass="pms_txtarea refresh_account" id="desciption"
					maxlength="200" />
			</div>
		</div>


		<div class="pms_row_bottom_align">
			<div class="pms_label_div">
				<form:label path="tax_id" cssClass="pms_label_vertical">
					<spring:message code="accountMaster.label.txnType"></spring:message>
				</form:label>
				<span class="pms_required"></span>
			</div>


			<div class="pms_input_div">

				<form:select path="sysdef_acc_type_id" id="sysDefAcc"
					cssClass="pms_text validator " onchange="withOutTax();">

					<form:options items="${typesList}" id="taxlist" />
				</form:select>


				<div id="validator_sysDefAcc" class='validator_cls'></div>
			</div>
		</div>

        <!-- hssn-code -->
        	<%-- <div class="pms_row_bottom_align">
			<div class="pms_label_div">
				<form:label path="hssnCode" cssClass="pms_label_vertical">
					<spring:message code="accountMaster.label.hssnCode"></spring:message>
				</form:label>
				<span class="pms_required"></span>
			</div>


			<div class="pms_input_div">

				<form:select path="sysdef_acc_type_id" id="sysDefAcc"
					cssClass="pms_text validator " onchange="withOutTax();">

					<form:options items="${typesList}" id="taxlist" />
				</form:select>


				<div id="validator_sysDefAcc" class='validator_cls'></div>
			</div>
		</div> --%>
        <!-- end -->

		<div class="pms_row_bottom_align">
			<div class="pms_label_div" id="tax_code">
				<form:label path="tax_id" cssClass="pms_label_vertical">
					<spring:message code="accountMaster.label.taxCode"></spring:message>

				</form:label>

				<span class="pms_required"></span>
			</div>
			<div class="pms_input_div">
				<form:select path="tax_id" id="tax" cssClass="pms_text validator ">

					<form:options items="${taxList}" />
				</form:select>


				<div id="validator_tax" class='validator_cls'></div>
			</div>
		</div>

		<!--ADD and DELETE button  -->
		<div id="sub_btn_div" class="pms_sub_btn_div">
			<input type="button" class="pms_action_button"
				value="<spring:message code="pms.btn.submit"></spring:message>"
				onclick="saveDatas()" /> <input type="button" id="delete_btn"
				value="<spring:message code="pms.btn.delete"></spring:message>"
				class="pms_delete_action_button" onclick="deleteData()" /> <input
				type="button" id="back_btn" class="pms_action_button"
				value="<spring:message code="pms.btn.reset"></spring:message>"
				onclick="refreshData()" /> <input type="button" id=""
				value="<spring:message code="pms.btn.back"></spring:message>"
				class="pms_nav_button" onclick="cancel()" />
		</div>
	</div>
</div>
