$(document).ready(function() {
	$("#errorDiv").hide();
	$("#btnLogin").click(function(){
		login();
	});
	
	$("#name, #password").keypress(function(event){
		if(event.charCode == '13'){
			login();
		}
	});
	
	function login(){
		$('#imgloader').show();
		var value = "";
		var loginData = {name:$("#name").val(), password:$("#password").val()};
		$.post( "login", loginData)
		  .done(function( data ) {
			  if(data == "redirect:/"){
				  $("#errorDiv").show();
			  }
			  else{
				  window.location.reload(); 	 
			  }
				$('#imgloader').hide();
		  })
		  .fail(function(){
				alert("Error Occured");
		  });
	
	}
});