(function($){

	$(document).ready(function() {
		var idCheck = 0;

		var onlyEnAndNumber = function(target){
			var regExp = /^[a-zA-Z][a-zA-Z0-9]{5,20}/;

			return regExp.test(target);
		}
		var idValidationCheck = function(){
			$("#userId").focusout(function(event) {
				var userId = $("#userId").val();

				$("#idMsg").empty();
				if(userId.length >= 5){
					if(onlyEnAndNumber(userId)){
						$.ajax({
							url : "/account/overlap",
							type:"get",
							data:"user_id="+userId,
							dataType:"text",
							success:function(data){
								if(data.trim() == "true"){
									$("#idMsg").css("color", "#0000ff").text("사용 가능한 아이디입니다.");
									idCheck = 1;
								}
								else if(data.trim() == "false"){
									$("#idMsg").css("color", "#ff0000").text("이미 존재하는 아이디입니다.");
									idCheck=0;
								}
							}
						});
					}
					else{
						$("#idMsg").css("color", "#ff0000").text("아이디는 영문 및 숫자로 구성되어야 합니다.");
						idCheck=0;
					}
				}else{
					$("#idMsg").css("color", "#ff0000").text("아이디는 5글자 이상이어야 합니다.");
					idCheck=0;
				}
			});
		}

		var validationCheck = function(){
			$(".submit_btn").on("click",function(event) {
				if(idCheck == 0){
					alert("아이디를 확인해주세요.");
					return false;
				}else{
					return true;
				}
			});
		}

		idValidationCheck();
		validationCheck();
	});

})(jQuery);