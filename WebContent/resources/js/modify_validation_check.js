(function($){
	$(document).ready(function() {

		var defaultEmail = $("#userEmail").val();
		var nickNameValid =1;
		var nameValid =1;
		var birthValid =1;
		var emailValid = 1;
		var phoneValid = 1;
		var certification = 1;

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


		var dateCheck = function(year,month,day){
			var regExpY =/^[0-9]{4}$/;
			var regExpD =/^[0-9]{1,2}$/;
			var result = false;

			if(regExpY.test(year)){
				if(month != ""){
					console.log(new Date(year, month, 0).getDate() >= day);
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

		var certificationEvent = function(){
			$("#certifiactionBtn").click(function() {
				if(emailValid == 1){
					$("#certificationMsg").empty();

					var userEmail = $("#userEmail").val();

					var data = {"user_email":userEmail};
					$("#certificationMsg").css("color", "#0000ff").text("인증코드 전송 중입니다.");
					$.ajax({
						url : "/mail/modify",
						type:"post",
						contentType : "application/json",
						data:JSON.stringify(data)
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
		}

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
				$("#nickNameMsg").css("color", "#ff0000").text("2~10자의 영문 대 소문자, 숫자, 한글만 사용 가능합니다.");
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

		$("#userBirth").focusout(function(event) {
			var target = $(event.target);

			if(target.is("input")){
				var year = $("#userBirthY").val();
				var month = $("#userBirthM").val();
				var day = $("#userBirthD").val();

				$("#birthMsg").empty();
				if(dateCheck(year,month,day)){
					birthValid = 1;
				}
				else{
					birthValid = 0;
				}
			}
		});

		$("#userEmail").focusout(function() {
			var regExp = /^[0-9a-z]([-_.]?[0-9a-z])*@[0-9a-z]([-_.]?[0-9a-z])*.[a-z]{2,3}$/i;
			var userEmail = $(this).val();

			$("#certifiactionBtn").off();
			$("#emailCertification").off();
			$(".certification_wrap").remove();
			$("#emailMsg").empty();
			if(regExp.test(userEmail)){
				if(userEmail != defaultEmail){
					overlapCheck("userEmail",userEmail).done(function(data) {
						if(data.trim() == "true"){
							emailValid = 1;
							certification = 0;
							$("#emailMsg").css("color", "#0000ff").text("이메일 변경시 인증이 필요합니다.");
							$("#emailMsg").after("<div class='certification_wrap'><input type='text' class='inp' id='emailCertification' placeholder='인증코드' />"+
								"<button type='button' id='certifiactionBtn' class='btn_major'>인증코드 받기</button>"+
								"<div id='certificationMsg' class='form_msg'></div></div>");

							certificationEvent();
						}
						else if(data.trim() == "false"){
							$("#emailMsg").css("color", "#ff0000").text("이미 사용중인 이메일 주소입니다.");
							emailValid=0;
						}
					});
				}else{
					emailValid = 1;
					certification = 1;
				}
			}
			else{
				$("#emailMsg").css("color", "#ff0000").text("올바르지 않은 이메일 주소입니다.");
				emailValid=0;
			}
		});

		$("#userPhone").focusout(function(event) {
			var target = $(event.target);

			if(target.is("input")){
				var regExp1 = /^01[016789]$/;
				var regExp2 = /^[0-9]{3,4}$/;
				var regExp3 = /^[0-9]{4}$/;
				var phoneNum1 = $("#phoneNum1").val();
				var phoneNum2 = $("#phoneNum2").val();
				var phoneNum3 = $("#phoneNum3").val();

				$("#phoneMsg").empty();
				if(regExp1.test(phoneNum1) && regExp2.test(phoneNum2) && regExp3.test(phoneNum3) ){
					phoneValid = 1;
				}
				else{
					$("#phoneMsg").css("color", "#ff0000").text("전화번호를 정확하게 입력하세요.");
					phoneValid=0;
				}
			}
		});

		$("#modifyFormSubmit").on("click",function() {
			var validator = nickNameValid + nameValid + birthValid +emailValid + phoneValid;

			if(validator != 5){
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