(function($){
	$(document).ready(function() {

		var sortComments = function(sortType){
			if(sortType == "sortType2"){
				sortType = "ASC"
			}
			else{
				sortType = "DESC"
			}

			$.ajax({
				url: "/content/"+$(".post_info").attr("id")+"/comment/sort/"+sortType,
				type:"get",
				dataType:"json",
				success: function(data){
					var list = JSON.parse(data);
					var comments = "";
					for(i of list){
						comments+= "<li id='"+i.commentId+"' class='post_comment-item main'>"
							+"<div >"+i.userId+"</div>"
							+"<div>"+i.commentContent+"</div>"
							+"<div>"
							+i.createDate.substring(0, 19)
							+" <span role='button' class='comment_reply'>답글 쓰기</span>"
							+"</div>"
							+"</li>"
						if(i.subCommentList != ""){
							for(j of i.subCommentList){
								comments+= "<li id='sub_"+j.commentId+"' class='post_comment-item sub'>"
									+"<div >"+j.userId+"</div>"
									+"<div>"+j.commentContent+"</div>"
									+"<div>"
									+j.createDate.substring(0, 19)
									+" <span role='button' class='comment_reply'>답글 쓰기</span>"
									+"</div>"
									+"</li>"
							}
						}
					}
					$(".post_comment-list").empty().html(comments);
				}
			});
		}
		var subCommentForm = function(parentId){
			return "<form class='comment_form sub' action='/content/"+$(".post_info").attr("id")+"/regcomment' method='post'>"
					+ "<input type='hidden' name='parent_id' value='"+parentId+"'>"
					+ "<textarea cols='20' rows='3' name='comment_content' class='tf_content' placeholder='댓글을 작성해 보세요.' ></textarea>"
					+ "<button type='submit' class='btn_major btn_self'>등록</button>"
					+ "</form>";
		}

		$(".tab_sort_item").on("click", function(event) {
			var target = $(event.target);
			$(".tab_sort_item.selected").removeClass("selected");
			target.addClass("selected");
			sortComments(target.attr("id"));
		});

		$(".post_comment-list").on("click", function(event) {
			if($(event.target).hasClass("comment_reply")){
				var target = $(event.target).parents("li");
				var parentId = target.attr("id");

				$.ajax({
					url:"/account/check",
					type:"get",
					success: function(data){
						if(data == "true"){
							$(".comment_form.sub").remove();
							var form = subCommentForm(parentId);
							target.append(form);
						}
						else{
							alert("로그인이 필요한 서비스입니다.");
						}
					}
				});
			}
		});
	});

})(jQuery);