(function($){
	$(document).ready(function() {

		var oldPwValid =0;
		var newPwValid =0;
		var newPwCheck = 0;

		var passwordCheck = function(){
			var userPw = $("#newUserPw").val();
			var userPwCk = $("#newUserPwCk").val();

			$("#newPwCkMsg").empty();
			if(userPwCk != ""){
				if(userPw == userPwCk){
					$("#newPwCkMsg").css("color", "#0000ff").text("일치");
					newPwCheck = 1;
				}
				else{
					$("#newPwCkMsg").css("color", "#ff0000").text("비밀번호가 일치하지 않습니다");
					newPwCheck=0;
				}
			}else{
				newPwCheck=0;
			}
		}

		$("#oldUserPw").focusout(function(event) {
			var userPw = $(this).val();

			$("#oldPwMsg").empty();
			if(userPw != ""){
				oldPwValid = 1;
			}
			else{
				$("#oldPwMsg").css("color", "#ff0000").text("기존 비밀번호를 입력하세요.");
				oldPwValid=0;
			}
		});

		$("#newUserPw").focusout(function(event) {
			var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*-+])(?=.*[0-9]).{8,16}$/;
			var userPw = $(this).val();

			$("#newPwMsg").empty();
			if(regExp.test(userPw)){
				$("#newPwMsg").css("color", "#0000ff").text("사용 가능한 비밀번호입니다.");
				newPwValid = 1;
			}
			else{
				$("#newPwMsg").css("color", "#ff0000").text("8~16자의 영문, 숫자, 특수문자를 사용하세요.");
				newPwValid=0;
			}
			passwordCheck();
		});

		$("#newUserPwCk").focusout(function(event) {
			passwordCheck();
		});

		$("#changeFormSubmit").on("click",function(event) {
			var validator = oldPwValid+newPwValid +newPwCheck;

			if(validator != 3){
				alert("항목을 전부 확인해주세요.");
				return false;
			}
			else{
				return true;
			}
		});
	});
})(jQuery);