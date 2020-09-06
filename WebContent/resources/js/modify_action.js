(function($){
	$(document).ready(function() {

		$("#modifyFormCancel").click(function() {
			var choice = confirm("변경을 취소하시겠습니까?");
			if(choice){
				window.location.href="/user/profile";
			}
		});
	});
})(jQuery);