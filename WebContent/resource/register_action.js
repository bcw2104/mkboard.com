(function($){
	
	var reg_msg = function(){
		var url = window.location.search;
		window.history.pushState("", "", window.location.href.substring(0, window.location.href.indexOf("?")));
		
		if(url.length > 0){
			url = url.substr(1).split("&");
			for(var i of url){
				if(i === "st=fail"){
					alert("회원가입 도중 오류가 발생했습니다.");
				}
			}
		}
	} 
	
	reg_msg();
	
	$(document).ready(function() {
		var id_ck = 0;
		
		var unique_ck = function(){
			$("#user_id").focusout(function(event) {
				$("#id_msg").remove();
				$.ajax({
					url : "/user/register?cmd=cio",
					type:"post",
					data:"user_id="+$("#user_id").val(),
					dataType:"text",
					success:function(data){
						if(data.trim() == "true"){
							$("#user_id").before("<span id='id_msg' style='color:blue; font-size:0.9rem'>사용 가능한 아이디입니다.</span>");
							id_ck = 1;
						}
						else if(data.trim() == "false"){
							$("#user_id").before("<span id='id_msg' style='color:red; font-size:0.9rem'>이미 존재하는 아이디입니다.</span>");
							id_ck=0;
						}	
						else if(data.trim() == "null"){
							id_ck=0;
						}	
					}
					
				});
			});
		}
		
		var date_ck = function(){
			$("#user_id").focusout(function(event) {
				$("#id_msg").remove();
				$.ajax({
					url : "/user/register?cmd=cio",
					type:"post",
					data:"user_id="+$("#user_id").val(),
					dataType:"text",
					success:function(data){
						if(data.trim() == "사용 가능"){
							$("#user_id").before("<span id='id_msg' style='color:blue; font-weight:bold; font-size:0.8rem'>"+data+"</span>");
							id_ck = 1;
						}
						else if(data.trim() == "사용 불가"){
							$("#user_id").before("<span id='id_msg' style='color:red; font-weight:bold; font-size:0.8rem'>"+data+"</span>");
							id_ck=0;
						}	
						else if(data.trim() == "null"){
							id_ck=0;
						}	
					}
					
				});
			});
		}
		
		var submit_ck = function(){
			console.log(id_ck);
			$(".submit_btn").on("click",function(event) {
				if(id_ck == 0){
					alert("아이디가 비어있거나 중복됩니다.");
					return false;
				}else{
					return true;
				}
			});
		}
		
		unique_ck();
		submit_ck();
	});
	
})(jQuery);