$(document).ready(function() {
	$("#errorDiv").hide();
	$("#btlogin").click(function(){
		login();
	});
	
	function disableBack() {
        window.history.forward()
    }
    window.onload = disableBack();
    window.onpageshow = function(e) {
        if (e.persisted)
            disableBack();
    }
	
	$("#name, #password").keypress(function(event){
		if(event.charCode == '13'){
			login();
		}
	});
	
	function login(){
		$('#imgloader').show();
		var value = "";
		var loginData = {
				name     : $("#name").val(),
				password : $("#password").val(),
		};

		$.ajax({
			url: '/pms/onlinetravelagent/onlinetravelagentLogin',
			type: 'POST',
			data: {
				name     : $("#name").val(),
				password : $("#password").val(),
			},
			success : function (result) {
				
				if(result == "/online_travel_agent_dashboard"){
					window.location.href = "/pms/onlinetravelagent"+result;
				  }else if(result == "/"){
					  $("#errorDiv").show();
				  }
					$('#imgloader').hide();
			},
			error: function(data){
				$('#imgloader').hide();
				$("#errorDiv").show();
			},
		    });
		}	
	
}); 


function logout(){
	window.location.href = "/pms/onlinetravelagent";

	$.ajax({
		url: '/pms/onlinetravelagent/otalogout',
		type: 'POST',
		success : function (result) {
			
		},
		error: function(data){
		},
	    });
	}


$("#btn-dashboard").on('click', function(){
	window.location.href = "/pms/dashboard";
});

