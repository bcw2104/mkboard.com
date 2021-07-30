(function($){
	$(document).ready(function() {

		var pwValid =0;

		$("#userPw").focusout(function(event) {
			var userPw = $(this).val();

			$("#pwMsg").empty();
			if(userPw != ""){
				pwValid = 1;
			}
			else{
				$("#pwMsg").css("color", "#ff0000").text("비밀번호를 입력하세요.");
				pwValid=0;
			}
		});


		$("#leaveFormSubmit").on("click",function(event) {

			if(pwValid != 1){
				return false;
			}
			else{
				return true;
			}
		});
	});
})(jQuery);