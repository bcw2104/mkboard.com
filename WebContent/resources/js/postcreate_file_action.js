(function($){

	var fileSizeCheck = function(fileSize){
		var maxSize = 3 * 1024 * 1024;

		if(fileSize > maxSize){
			return false;
		}
		else{
			return true;
		}
	}

	$(document).ready(function() {

		$("#attachedFileGroup").on("click", function(event) {
			var target = $(event.target);
			event.stopPropagation();

			if(target.is("a.file_remove_btn")){
				target.parent().remove();
			}
		});

		var fileAddEvent = function(target,fileSize){
			if(fileSizeCheck(fileSize)){
				var view = "<span class='attached_file_name'></span>"+
			     "<a href='#this' class='file_remove_btn' role='button'>삭제</a>";
				target.parent().append(view);
				target.attr("name","attached_file");
				target.removeClass("new_attached_file").addClass("attached_file");

				var path = target.val();
				target.next(".attached_file_name").text(path.split("\\")[path.split("\\").length-1]);

			}else{
				alert("첨부파일의 크기는 3MB 이내로 등록 가능합니다.");
				target.parent().remove();
			}
		}

		$("#fileAddBtn").on("click", function() {
			if($(".attached_file").length < 3){
				var fileElement;

				if($(".new_attached_file").length == 0){
					var element = "<div class='file_ele'><input type='file' class='new_attached_file' hidden='hidden' /></div>";
					$("#attachedFileGroup").prepend(element);

					fileElement = $("#attachedFileGroup .file_ele:first-child");
					var upload = fileElement.find(".new_attached_file");

					var agent = navigator.userAgent.toLowerCase();

					//IE
					if ( (navigator.appName == 'Netscape' && agent.indexOf('trident') != -1) || (agent.indexOf("msie") != -1)) {
						fileElement.find(".new_attached_file").on("click",function(event) {
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
						fileElement.find(".new_attached_file").on("change", function(event) {
							var target = event.target;
							if($(target).val().length > 0){
								var fileSize = target.files[0].size;
								fileAddEvent($(target),fileSize);
							}
						});
					}
				}
				else{
					fileElement = $("#attachedFileGroup .file_ele:first-child");
				}

				fileElement.find(".new_attached_file").click();
			}
			else{
				alert("첨부파일은 최대 3개까지 가능합니다.");
			}
		});

	});

})(jQuery);