(function($){
	$(document).ready(function() {
		$(".post_reset").on("click", function(event) {
			$(".post_content_input").html("내용을 입력해주세요.");
		});

		$(".post_form").on("submit", function(event) {
			if($(".post_content_input").html() == ""){
				alert("내용을 작성해주세요.");
				$(".post_content_input").focus();
				return false;
			}
			else{
				$(".post_form").append("<input type='hidden' name='post_content' value='"+$(".post_content_input").html()+"'>")
			}
		});
	});

})(jQuery);