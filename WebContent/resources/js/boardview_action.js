(function($){
	$(document).ready(function() {

		var setCookie = function(name,value){
			$.ajax({
				url:"/util/cookie?n="+name+"&v=" + value,
				type:"get"
			});
		}

		var getCookieValue = function(cookies,name){
			var cookie;
			for(var i=0; i<cookies.length; i++){
				cookie = cookies[i].split("=");

				if(cookie[0] == name){
					return cookie[1];
				}
			}
			return null;
		}

		var setDefaultNoticeCkb = function(){
			var value = getCookieValue(document.cookie.split(";"),"hideNotice");

			if(value == "true"){
				$("#hideNotice").addClass("selected");
				$("#hideNoticeCkb").attr("checked","checked");
				$("#noticePost").css("display","none");
				$("#importantPost").css("display","none");
			}
		}

		$("#hideNoticeCkb").on('change', function() {
			var status;
			$("#hideNotice").toggleClass("selected");
			if ($(this).is(':checked')) {
				$("#noticePost").css("display","none");
				$("#importantPost").css("display","none");
				status = true;
			}
			else {
				$("#noticePost").css("display", "");
				$("#importantPost").css("display", "");
				status = false;
			}

			setCookie("hideNotice",status);
		});

		setDefaultNoticeCkb();
	});
})(jQuery);