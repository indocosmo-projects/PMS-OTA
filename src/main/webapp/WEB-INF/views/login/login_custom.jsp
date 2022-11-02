<jsp:directive.include file="../common/includes/page_directives.jsp" />
<spring:message code="login.placeholder.name" var="namePlaceHolder" />
<spring:message code="login.placeholder.password"
	var="passwordPlaceHolder" />
<img src="resources/common/images/loading.gif" id="imgloader"
	style="display: none; position: fixed; top: 39%; left: 39%; z-index: 9999" />
<h2 class="form-signin-heading">sign in now</h2>
<div class="login-wrap">
	<input type="text" id="name" name="name" required="true"
		class="form-control" placeholder="${namePlaceHolder}"
		onblur="if(this.value == '') this.value = 'Username'"
		onfocus="if(this.value == 'Username') this.value = ''" required
		autofocus> <input type="password" id="password"
		name="password" class="form-control" required="true"
		placeholder="${passwordPlaceHolder}"
		onblur="if(this.value == '') this.value = 'Password'"
		onfocus="if(this.value == 'Password') this.value = ''" required>

	<!-- <label class="checkbox">
                <input type="checkbox" value="remember-me"> Remember me
            </label> -->
	<%-- <input type="submit" class="btn btn-lg btn-login btn-block" value="<spring:message code="login.btn.signin"/>"> --%>
	<button class="btn btn-lg btn-login btn-block" type="button"
		id="btnLogin">
		<spring:message code="login.btn.signin" />
	</button>
	<div id="errorDiv" style="color: red; margin-left: 40px;">Incorrect
		Username/Password</div>
</div>

