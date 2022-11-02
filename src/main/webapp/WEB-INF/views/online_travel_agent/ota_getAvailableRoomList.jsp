<jsp:directive.include file="../common/includes/page_directives.jsp" />

<img src="resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
<div class="container">
        <div class="row ">
            <div class="col-sm-12 form">
            	<div >
                	<h3 class="text-center text" >SIGN IN NOW</h3>
                </div>
                <form  method="POST">
                      <div class="form-group">
                        <input type="text" class="form-control" id="name"
                        id="name" name="name" required="true"
						class="form-control" placeholder="${namePlaceHolder}"
						onblur="if(this.value == '') this.value = 'Username'"
						onfocus="if(this.value == 'Username') this.value = ''" required
						autofocus >
                      </div>
                      <div class="form-group">
                        <input type="password" class="form-control" id="password" name="password" class="form-control" required="true"
						placeholder="${passwordPlaceHolder}"
						onblur="if(this.value == '') this.value = 'Password'"
						onfocus="if(this.value == 'Password') this.value = ''" required>
                      </div>
                      <div >
                        <button type="button" id="btlogin" class="btn btn-info btlogin" >Submit</button>
                      </div>
                      <div id="errorDiv" style="color: red; margin-left: 40px;">Incorrect
						Username/Password</div>
                </form>
            </div>
            <div class="col-sm-3"></div>
        </div>
    </div>
