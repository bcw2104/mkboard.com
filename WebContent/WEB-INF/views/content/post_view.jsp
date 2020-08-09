<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/postview_style.css" />
<c:set var="categoryId" value="${thisCategory.categoryId}" />
<c:set var="boardId" value="${thisBoard.boardId}" />
<c:set var="boardName" value="${thisBoard.boardName}" />

<div class="nav">
	<div class="nav-title">${thisCategory.categoryName}</div>
	<ul class="nav-list">
		<li class="nav-item ${boardId == '' ? 'selected' : ''}">
			<a class="nav-item_link" href="/content/${categoryId}">전체 글</a></li>
		<c:forEach items="${boardList}" var="n">
			<li class="nav-item ${boardName == n.boardName ? 'selected' : ''}">
				<a class="nav-item_link" href="/content/${n.categoryId}/${n.boardId}">${n.boardName}</a>
			</li>
		</c:forEach>
	</ul>
</div>
<div class="section">
	<div class='section-top'>
		<a class="board_link" href="/content/${categoryId}/${boardId}">${thisCategory.categoryName} > ${thisBoard.boardName}</a>
		<div class="post_title">${thisPost.postTitle}</div>
		<div class="post_info" id="${thisPost.postId }">
			<div class="post_info-item">작성자 : ${thisPost.author }</div>
			<div class="post_info-item">조회 수 : ${thisPost.hits }</div>
		</div>
	</div>

	<div class='section-middle'>
		<div class="post_content">${thisPost.postContent }</div>
		<div class="post_comment">
			<h3>댓글(${thisPost.comments})</h3>
			<span role="button" class="tab_sort_item selected" id="sortType1">최신순</span>
			<span role="button" class="tab_sort_item" id="sortType2">등록순</span>
			<ul class="post_comment-list">
				<c:forEach items="${commentList}" var="n">
				<li id="${n.commentId}" class="post_comment-item main">
					<div >${n.userId}</div>
					<div>${n.commentContent}</div>
					<div>
						<fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						<span role="button" class="comment_reply">답글 쓰기</span>
					</div>
				</li>
				<c:forEach items="${n.subComment}" var="sub">
					<li id="sub_${sub.commentId}" class="post_comment-item sub">
						<div>${sub.userId}</div>
						<div>${sub.commentContent}</div>
						<div>
							<fmt:formatDate value="${sub.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							<span role="button" class="comment_reply">답글 쓰기</span>
						</div>
					</li>
				</c:forEach>
				</c:forEach>
			</ul>
			<c:if test="${login != null}">
			<form class="comment_form" action="/content/${thisPost.postId}/regcomment" method="post">
				<textarea cols="20" rows="3" name="comment_content" class="tf_content" placeholder="댓글을 작성해 보세요."></textarea>
				<button type="submit" class="btn_major btn_self">등록</button>
			</form>
			</c:if>
		</div>
	</div>

	<div class="section-bottom">
		<c:forEach items="${closestPostList}" var="n" >
			<div class="jump_post">
				<c:choose>
					<c:when test="${n.postId > thisPost.postId}">
						<div>다음 글</div>
					</c:when>
					<c:otherwise>
						<div>이전 글</div>
					</c:otherwise>
				</c:choose>
				<div><a class="jump_post_link" href="/content/${categoryId}/${boardId}/${n.postId}">${n.postTitle}</a></div>
				<div>
					<span>${n.author}</span><span><fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<script type="text/javascript" src="/resources/js/comment_action.js"></script>