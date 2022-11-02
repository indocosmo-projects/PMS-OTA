<script type="text/ng-template" id="tabDialog.tmpl2.html"><md-dialog aria-label="Error" class="modal-dialog modal-sm">
  <form>
    <div class="col-sm-12" style="background-color: #00a8b3; color: #fff;">
      <div class="col-sm-10">
        <h4>Invoice with Options</h4> 
      </div>
	  <div class="col-sm-2" ng-click="cancel()" role="button" tabindex="0" style="font-size: 25px;">×</div>
    </div>
    <md-dialog-content class="error_dialog_box">
		<div class="col-sm-12" style="margin-top: 28px;">
			<label>Preprint Format</label> <md-checkbox ng-model="chk" aria-label="Checkbox 1">
          </md-checkbox>
		</div>
<div class="col-sm-12" >
			<label>Print Invoice</label> <md-checkbox ng-model="chkInv" aria-label="Checkbox 2" ng-click = "showOption(chkInv)">
          </md-checkbox>
		</div>
<div class="col-sm-12" ng-show="hideOption">
	<div class="form-group">
		<label class="control-label col-sm-4">
		Invoice Type</label>
		<div class="col-sm-8 ">
			<select id="slctTxnType" 
					class="form-control input-sm disableDel"
					ng-model="systemTtxnType" ng-change="txnChange()">
					<option value = "" selected="selected">select</option>
					
					<option ng-repeat="type in accountMaster"
						value="{{type.id}}">{{type.name}}</option>

		    </select>
		</div>										
	</div>		
</div>							
    </md-dialog-content>
	 <md-dialog-actions layout="row">
      <md-button ng-click="answer('ok',chk,systemTtxnType)" style="margin-right:20px;" >
        ok
      </md-button>
    </md-dialog-actions>
  </form>
</md-dialog>
</script>