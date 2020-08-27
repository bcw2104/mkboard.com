(function($){

	var fileSizeCheck = function(fileSize){
		var maxSize = 10 * 1024 * 1024;

		if(fileSize > maxSize){
			return false;
		}
		else{
			return true;
		}
	}

	$(document).ready(function() {

		var fileAddEvent = function(target,fileSize){
			if(fileSizeCheck(fileSize)){
				var view = "<span class='file_upload_msg'></span>"+
			     "<a href='#this' class='file_remove_btn' role='button'>삭제</a>";
				target.parent().append(view);

				target.siblings(".file_remove_btn").first().on("click", function() {
					target.parent().remove();
				});

				var path = target.val();
				if(path){
					target.next(".file_upload_msg").text(path.split("\\")[path.split("\\").length-1]);
				}
			}else{
				alert("첨부파일의 크기는 10MB 이내로 등록 가능합니다.");
				target.parent().remove();
			}
		}

		$("#fileAddBtn").on("click", function() {
			console.log($(".file_upload").length);
			if($(".file_upload").length < 3){
				var element = "<div class='file_ele'><input type='file' class='file_upload' hidden='hidden' name='upload_file' /></div>";

				$("#fileGroup").prepend(element);

				var fileElement = $("#fileGroup .file_ele:first-child");
				var upload = fileElement.find(".file_upload");

				var agent = navigator.userAgent.toLowerCase();

				//IE
				if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
					fileElement.find(".file_upload").on("click",function(event) {
						var target = event.target;
						setTimeout(function(){
							if($(target).val().length > 0){
								var fileSize = target.files[0].size;
								fileAddEvent($(target),fileSize);
							}
						 }, 0);
					});
				}
				else{
					fileElement.find(".file_upload").on("change", function(event) {
						var target = event.target;
						if($(target).val().length > 0){
							var fileSize = target.files[0].size;
							fileAddEvent($(target),fileSize);
						}
					});
				}

				fileElement.find(".file_upload").click();
			}
			else{
				alert("첨부파일은 최대 3개까지 가능합니다.");
			}
		});

		$(".post_reset").on("click", function(event) {
			$(".tf_content").html("내용을 입력해주세요.");
		});

		$(".post_form").on("submit", function(event) {
			$(".file_upload").each(function(i, element) {
				if(!$(element).val()){
					$(element).parent().remove();
				}
			});

			if($(".tf_content").html() == ""){
				alert("내용을 작성해주세요.");
				$(".tf_content").focus();
				return false;
			}
			else{
				$(".post_form").append("<input type='hidden' name='post_content' value='"+$(".tf_content").html()+"'>");
				return true;
			}
		});
	});

})(jQuery);