(function($){
	$(document).ready(function() {

		var pwValid =0;
		var pwCheck = 0;

		var passwordCheck = function(){
			var userPw = $("#userPw").val();
			var userPwCk = $("#userPwCk").val();

			$("#pwCkMsg").empty();
			if(userPwCk != ""){
				if(userPw == userPwCk){
					$("#pwCkMsg").css("color", "#0000ff").text("일치");
					pwCheck = 1;
				}
				else{
					$("#pwCkMsg").css("color", "#ff0000").text("비밀번호가 일치하지 않습니다");
					pwCheck=0;
				}
			}else{
				pwCheck=0;
			}
		}

		$("#userPw").focusout(function(event) {
			var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*-+])(?=.*[0-9]).{8,16}$/;
			var userPw = $(this).val();

			$("#pwMsg").empty();
			if(regExp.test(userPw)){
				$("#pwMsg").css("color", "#0000ff").text("사용 가능한 비밀번호입니다.");
				pwValid = 1;
			}
			else{
				$("#pwMsg").css("color", "#ff0000").text("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
				pwValid=0;
			}
			passwordCheck();
		});

		$("#userPwCk").focusout(function(event) {
			passwordCheck();
		});

		$("#changeFormSubmit").on("click",function(event) {
			var validator = pwValid +pwCheck;

			if(validator != 2){
				alert("항목을 전부 확인해주세요.");
				return false;
			}
			else{
				return true;
			}
		});
	});
})(jQuery);