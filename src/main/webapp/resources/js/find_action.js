(function($){
	$(document).ready(function() {

		var overlapCheck = function(name,value){
			var data = {"name": name , "value" : value};

			return $.ajax({
				url : "/account/overlap?cmd=send",
				type:"post",
				data:JSON.stringify(data),
				contentType:"application/json",
				dataType:"text"
			});
		}

		var emailValidation = function(email){
			var regExp = /^[0-9a-z]([-_.]?[0-9a-z])*@[0-9a-z]([-_.]?[0-9a-z])*.[a-z]{2,3}$/i;

			return regExp.test(email);
		}

		var sendMail = function(email){
			var data = {"user_email": email};
			$.ajax({
				url:"/mail/find",
				type:"post",
				async:false,
				data:JSON.stringify(data),
				contentType:"application/json"
			});
		}

		$("#findSubmit").on("click",function(event) {
			var userEmail = $("#userEmail").val();
			$("#emailMsg").empty();

			if(emailValidation(userEmail)){
				overlapCheck("userEmail",userEmail).done(function(data) {
					if(data.trim() == "true"){
						$("#emailMsg").css("color", "#ff0000").text( "가입되지 않은 이메일 주소입니다.");
					}
					else if(data.trim() == "false"){
						sendMail(userEmail);
						window.location.href="/account/find/complete";
					}
				});
			}
			else{
				$("#emailMsg").css("color", "#ff0000").text("올바르지 않은 이메일 주소입니다.");
			}
		});
	});
})(jQuery);