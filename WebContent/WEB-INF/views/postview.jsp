<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/style/postview_style.css" />
<c:set var="categoryId" value="${requestScope.thisCategory.categoryId}" />
<c:set var="boardId" value="${requestScope.thisBoard.boardId}" />
<c:set var="boardName" value="${requestScope.thisBoard.boardName}" />

<div class="content_container">
	<div class="content_menu border">
		<div class="menu_title">${requestScope.thisCategory.categoryName}</div>
		<ul class="menu_list">
			<li class="items ${boardId == '' ? 'selected' : ''}"><a
				href="/content/${categoryId}">전체 글</a></li>
			<c:forEach items="${requestScope.boardList}" var="n">
				<li class="items ${boardName == n.boardName ? 'selected' : ''}">
					<a href="/content/${n.categoryId}/${n.boardId}">${n.boardName}</a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="section">
		<div class='section_header'>
			<div class="section_top">
				<a href="/content/${categoryId}/${boardId}">${requestScope.thisCategory.categoryName} > ${requestScope.thisBoard.boardName}</a>
			</div>
			<div class="post_title">${requestScope.thisPost.postTitle }</div>
			<div class="post_info">
				<div>작성자 : ${requestScope.thisPost.author }</div>
				<div>조회 수 : ${requestScope.thisPost.hits }</div>
			</div>
		</div>

		<div class='section_body'>
			<div class="post_content">${requestScope.thisPost.postContent }</div>
			<div class="post_comment">
				<h3>댓글(${requestScope.thisPost.comments})</h3>
				<span  role="button" class="comment_tab selected" id="sort_1">최신순</span>
				<span  role="button" class="comment_tab" id="sort_2">등록순</span>
				<ul class="comment_list">
					<c:forEach items="${requestScope.commentList}" var="n">
					<li id="${n.commentId}" class="main_comment">
						<div >${n.userId}</div>
						<div>${n.commentContent}</div>
						<div>
							<fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/>
							<span role="button" class="comment_reply">답글 쓰기</span>
						</div>
					</li>
					<c:forEach items="${n.subComment}" var="sub">
						<li id="sub_${sub.commentId}" class="sub_comment">
							<div>${sub.userId}</div>
							<div>${sub.commentContent}</div>
							<div>
								<fmt:formatDate value="${sub.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/>
								<span role="button" class="comment_reply">답글 쓰기</span>
							</div>
						</li>
					</c:forEach>
					</c:forEach>
				</ul>
				<c:if test="${login != null}">
				<form class="comment_form" action="?cmd=main" method="post">
					<textarea cols="20" rows="3" name="comment_content" class="comment_txt" placeholder="댓글을 작성해 보세요."></textarea>
					<input type="submit" class="button submit_btn" value="등록">
				</form>
				</c:if>
			</div>
		</div>

		<div class="section_footer">
			<c:forEach items="${requestScope.closestPostList}" var="n" >
				<div class="jump_post">
					<c:choose>
						<c:when test="${n.postId > requestScope.thisPost.postId}">
							<div>다음 글</div>
						</c:when>
						<c:otherwise>
							<div>이전 글</div>
						</c:otherwise>
					</c:choose>
					<div><a href="/content/${categoryId}/${boardId}/${n.postId}">${n.postTitle}</a></div>
					<div>
						<span>${n.author}</span><span><fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd hh:mm:ss"/></span>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>

<script type="text/javascript" src="/resource/comment_action.js"></script>