(function($){

	var log_msg = function(){
		var url = window.location.search;
		window.history.pushState("", "", window.location.href.substring(0, window.location.href.indexOf("?")));

		if(url.length > 0){
			url = url.substr(1).split("&");
			for(var i of url){
				if(i === "st=fail"){
					alert("아이디 또는 비밀번호가 일치하지 않습니다.");
				}
				if(i === "reg=su"){
					alert("회원가입이 완료되었습니다.");
				}
			}
		}
	}

	log_msg();
})(jQuery);