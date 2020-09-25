
(function($){

	$(document).ready(function() {
		var element = $("<span>").attr("contenteditable","true");
		var change = false;

		var changeFontSize = function(target){
			$(".opt_font_size").val(target.css("font-size").substr(0,target.css("font-size").indexOf("p")));
		}

		$(".opt_font_size").change(function() {
			change = true;

			var size = $(this).val();
			element.css("font-size", size+"px");
		});

		var removeEvent = function(selection){
			if(selection.focusOffset == 0){
				var prev;

				if($(selection.focusNode).is("span")){
					prev= $(selection.focusNode).prev();
				}else{
					prev= $(selection.focusNode).parent().prev();
				}

				if(prev.is("span")){
					changeFontSize(prev);

					if($(selection.focusNode).is("span")){
						$(selection.focusNode).remove();
					}
					prev.focus();
					selection.setPosition(selection.focusNode,selection.focusNode.length);
				}
			}
		}

		//키보드 이벤트
		$(".tf_content").on("keydown keypress",function(event) {
			var target = $(event.target);
			event.stopPropagation();

			if(target.is("span")){
				var selection = window.getSelection();

				if(event.keyCode == 8){
					removeEvent(selection);
				}
				else if(event.keyCode == 37){
					if(selection.focusOffset == 0){
						var prev = $(selection.focusNode).parent().prev();
						prev.focus();
						selection.setPosition(selection.focusNode,selection.focusNode.length);

						changeFontSize(prev);
					}
				}
				else if(event.keyCode == 39){
					if(selection.focusOffset == selection.focusNode.length){
						var next = $(selection.focusNode).parent().next();
						next.focus();
						changeFontSize(next);
					}
				}

			}
		});

		//마우스 이벤트
		$(".tf_content").click(function(event) {
			var target = $(event.target);
			event.stopPropagation();

			if(target.is("div")){
				target = target.find("span:last-child");
				if(change){
					target.after(element);
					element.focus();
					element = $("<span>").attr("contenteditable","true");
					change = false;
				}else{
					target.focus();
				}
			}
			else{
				if(change){
					var selection = window.getSelection();
					var offset = selection.focusOffset;
					var text = target.text();
					target.html(text.substr(0, offset)).append(element).append(text.substr(offset));

					element.focus();

					element = $("<span>").attr("contenteditable","true");
					change = false;
				}else{
					changeFontSize(target);
				}
			}
		});

		$("#postResetBtn").on("click", function(event) {
			var selection = window.getSelection();

			$(".tf_content").html("<span contenteditable='true'>내용을 입력해주세요.</span>").find("span").focus();

			element = $("<span>").attr("contenteditable","true");
			selection.setPosition(selection.focusNode,selection.focusNode.length);
		});

		$("#postSubmitBtn").on("click", function(event) {

			if($(".tf_content").html() == ""){
				alert("내용을 작성해주세요.");
				$(".tf_content").find("span").focus();
				return false;
			}
			else{
				$("#postForm").append("<input type='hidden' name='post_content' value='"+$(".tf_content").html()+"'>");
				return true;
			}
		});
	});

})(jQuery);