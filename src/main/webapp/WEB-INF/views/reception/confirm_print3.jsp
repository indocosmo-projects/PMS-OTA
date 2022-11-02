<script type="text/ng-template" id="tabDialog.tmpl3.html"><md-dialog aria-label="Error" class="modal-dialog modal-sm">
  <form>
    <div class="col-sm-12" style="background-color: #00a8b3; color: #fff;">
      <div class="col-sm-10">
        <h4> print detailed bill?</h4> 
      </div>
	  <div class="col-sm-2" ng-click="cancel()" role="button" tabindex="0" style="font-size: 25px;">×</div>
    </div>
    <md-dialog-content class="error_dialog_box">
		<div class="col-sm-12" style="margin-top: 28px;">
			<label>Preprint Format</label> <md-checkbox ng-model="chk" aria-label="Checkbox 1">
          </md-checkbox>
		</div>
    </md-dialog-content>
	 <md-dialog-actions layout="row">
      <md-button ng-click="answer('ok',chk)" style="margin-right:20px;" >
        ok
      </md-button>
    </md-dialog-actions>
  </form>
</md-dialog>
</script>