(function($){
	$(document).ready(function() {

		$(".post_reset").on("click", function(event) {
			$(".tf_content").html("내용을 입력해주세요.");
		});

		$(".post_form").on("submit", function(event) {
			if($(".tf_content").html() == ""){
				alert("내용을 작성해주세요.");
				$(".tf_content").focus();
				return false;
			}
			else{
				$(".post_form").append("<input type='hidden' name='post_content' value='"+$(".tf_content").html()+"'>")
			}
		});
	});

})(jQuery);