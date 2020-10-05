(function($){
	$(document).ready(function() {

		var idValid = 0;
		var pwValid =0;
		var pwCheck = 0;
		var nickNameValid =0;
		var nameValid =0;
		var emailValid = 0;
		var certification = 0;

		var overlapCheck = function(name,value){
			var data = {"name": name , "value" : value};

			return $.ajax({
				url : "/account/overlap",
				type:"post",
				data:JSON.stringify(data),
				contentType:"application/json",
				dataType:"text"
			});
		}

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

		var dateCheck = function(year,month,day){
			var regExpY =/^[0-9]{4}$/;
			var regExpD =/^[0-9]{1,2}$/;
			var result = false;

			if(regExpY.test(year)){
				if(month != ""){
					if(regExpD.test(day)
													&& new Date(year, month, 0).getDate() >= day
													&& day >= 1
													&& new Date(year,month-1,day) <= new Date()
													&& new Date(year,month-1,day) >= new Date(1900,0,1)){
						result = true;
					}
					else{
						$("#birthMsg").css("color", "#ff0000").text("생년월일을 다시 확인해주세요.");
					}
				}
				else{
					$("#birthMsg").css("color", "#ff0000").text("출생 월을 선택하세요.");
				}
			}
			else{
				$("#birthMsg").css("color", "#ff0000").text("출생년도 4자리를 정확하게 입력하세요.");
			}

			 return result;
		}

		$("#userId").focusout(function(event) {
			var userId = $(this).val();
			var regExp = /^[a-z0-9]{5,20}$/;

			$("#idMsg").empty();
			if(regExp.test(userId)){
				overlapCheck("userId",userId).done(function(data) {
					if(data.trim() == "true"){
						$("#idMsg").css("color", "#0000ff").text("사용 가능한 아이디입니다.");
						idValid = 1;
					}
					else if(data.trim() == "false"){
						$("#idMsg").css("color", "#ff0000").text("이미 존재하는 아이디입니다.");
						idValid=0;
					}
				});
			}
			else{
				$("#idMsg").css("color", "#ff0000").text("5~20자의 영문 소문자, 숫자만 사용 가능합니다.");
				idValid=0;
			}
		});

		$("#userPw").focusout(function(event) {
			var regExp = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*-+])(?=.*[0-9]).{8,16}$/;
			var userPw = $(this).val();

			$("#pwMsg").empty();
			if(regExp.test(userPw)){
				$("#pwMsg").css("color", "#0000ff").text("사용 가능한 비밀번호입니다.");
				pwValid = 1;
			}
			else{
				$("#pwMsg").css("color", "#ff0000").text("8~16자의 영문, 숫자, 특수문자를 사용하세요.");
				pwValid=0;
			}
			passwordCheck();
		});

		$("#userPwCk").focusout(function(event) {
			passwordCheck();
		});

		$("#userNickName").focusout(function(event) {
			var regExp = /^[a-zA-Z가-힣0-9]{2,10}$/;
			var userNickName = $(this).val();

			$("#nickNameMsg").empty();
			if(regExp.test(userNickName)){
				overlapCheck("userNickName",userNickName).done(function(data) {
					if(data.trim() == "true"){
						$("#nickNameMsg").css("color", "#0000ff").text("사용 가능한 닉네임입니다.");
						nickNameValid = 1;
					}
					else if(data.trim() == "false"){
						$("#nickNameMsg").css("color", "#ff0000").text("이미 존재하는 닉네임입니다.");
						nickNameValid=0;
					}
				});
			}
			else{
				$("#nickNameMsg").css("color", "#ff0000").text("2~10자의 영문, 숫자, 한글만 사용 가능합니다.");
				nickNameValid=0;
			}
		});

		$("#userName").focusout(function(event) {
			var regExp = /^(=?[a-z]+.)+[a-z]$|^[가-힣]+$/i;
			var userName = $(this).val();

			$("#nameMsg").empty();
			if(regExp.test(userName)){
				nameValid = 1;
			}
			else{
				$("#nameMsg").css("color", "#ff0000").text("이름을 확인하세요.");
				nameValid=0;
			}
		});

		$("#userEmail").focusout(function() {
			var regExp = /^[0-9a-z]([-_.]?[0-9a-z])*@[0-9a-z]([-_.]?[0-9a-z])*.[a-z]{2,3}$/i;
			var userEmail = $(this).val();

			$("#emailMsg").empty();
			if(regExp.test(userEmail)){
				overlapCheck("userEmail",userEmail).done(function(data) {
					if(data.trim() == "true"){
						emailValid = 1;
					}
					else if(data.trim() == "false"){
						$("#emailMsg").css("color", "#ff0000").text("이미 사용중인 이메일 주소입니다.");
						emailValid=0;
					}
				});
			}
			else{
				$("#emailMsg").css("color", "#ff0000").text("올바르지 않은 이메일 주소입니다.");
				emailValid=0;
			}
		});

		$("#certifiactionBtn").click(function() {

			if(emailValid == 1){
				$("#certificationMsg").empty();

				var email = $("#userEmail").val();

				var data = {"user_email":email};
				$.ajax({
					url : "/mail/register",
					type:"post",
					contentType : "application/json",
					data:JSON.stringify(data),
					dataType:"text"
				});

				$("#certificationMsg").css("color", "#0000ff").text("전송된 인증코드를 입력하세요.");
			}
		});

		$("#emailCertification").focusout(function(event) {
			var value = $(this).val();
			$("#certificationMsg").empty();

			if(value != ""){
				if(emailValid == 1){
					var data = {"user_email":$("#userEmail").val(), "val":value};

					$.ajax({
						url : "/account/certification",
						type:"post",
						contentType : "application/json",
						data:JSON.stringify(data),
						dataType:"text",
						success: function(data){
							if(data ==="true"){
								$("#certificationMsg").css("color", "#0000ff").text("인증 완료");
								certification = 1;
							}else{
								$("#certificationMsg").css("color", "#ff0000").text("인증코드가 일치하지 않습니다.");
								certification = 0;
							}
						}
					});
				}
				else{
					$("#certificationMsg").css("color", "#ff0000").text("이메일을 확인하세요.");
					certification = 0;
				}
			}
			else{
				$("#certificationMsg").css("color", "#ff0000").text("인증코드를 입력하세요.");
			}
		});


		$("#registerFormSubmit").on("click",function(event) {
			var validator = idValid+pwValid +pwCheck + nickNameValid + nameValid +emailValid;

			if(validator != 6){
				alert("항목을 전부 확인해주세요.");
				return false;
			}
			if(certification == 0){
				alert("이메일 인증을 해주세요.");
				return false;
			}
			else{
				return true;
			}
		});
	});

})(jQuery);