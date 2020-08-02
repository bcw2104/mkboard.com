(function($){
	$(document).ready(function() {

		var sortComments = function(sortType){
			if(sortType == "sort_2"){
				sortType = "ASC"
			}
			else{
				sortType = "DESC"
			}

			$.ajax({
				url:window.location.pathname+"?cmd=sort",
				type:"get",
				data:"sort="+sortType,
				success: function(data){
					var list = JSON.parse(data);
					var comments = "";
					for(i of list){
						comments+= "<li id='"+i.commentId+"' class='main_comment'>"
							+"<div >"+i.userId+"</div>"
							+"<div>"+i.commentContent+"</div>"
							+"<div>"
							+i.createDate.substring(0, 19)
							+"<span role='button' class='comment_reply'>답글 쓰기</span>"
							+"</div>"
							+"</li>"
						if(i.subCommentList != ""){
							for(j of i.subCommentList){
								comments+= "<li id='sub_"+j.commentId+"' class='sub_comment'>"
									+"<div >"+j.userId+"</div>"
									+"<div>"+j.commentContent+"</div>"
									+"<div>"
									+j.createDate.substring(0, 19)
									+"<span role='button' class='comment_reply'>답글 쓰기</span>"
									+"</div>"
									+"</li>"
							}
						}
					}
					$(".comment_list").empty().html(comments);
				}
			});
		}
		var subCommentForm = function(parentId){
			return "<form class='comment_form sub' action='?cmd=sub' method='post'>"
					+ "<input type='hidden' name='parent_id' value='"+parentId+"'>"
					+ "<textarea cols='20' rows='3' name='comment_content' class='comment_txt'  placeholder='댓글을 작성해 보세요.' ></textarea>"
					+ "<input type='submit' class='button submit_btn' value='등록'>"
					+ "</form>";
		}

		$(".comment_tab").on("click", function(event) {
			var target = $(event.target);
			$(".comment_tab.selected").removeClass("selected");
			target.addClass("selected");
			sortComments(target.attr("id"));
		});

		$(".comment_list").on("click", function(event) {
			if($(event.target).hasClass("comment_reply")){
				var target = $(event.target).parents("li");
				var parentId = target.attr("id");

				$.ajax({
					url:"/login?cmd=check",
					type:"get",
					success: function(data){
						if(data == "true"){
							$(".comment_form.sub").remove();
							var form = subCommentForm(parentId);
							target.append(form);
						}
						else{
							alert("로그인을 해주세요.");
						}
					}
				});
			}
		});
	});

})(jQuery);