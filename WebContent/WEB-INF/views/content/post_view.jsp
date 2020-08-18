<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/postview_style.css" />
<link rel="stylesheet" href="/resources/style/common/nav_style.css" />

<div class="nav">
	<div class="nav-title">${requestScope.categoryName}</div>
	<ul class="nav-list">
		<li class="nav-item ${requestScope.boardId == null ? 'selected' : ''}">
			<a class="nav-item_link" href="/content/${requestScope.categoryId}">전체 글</a></li>
		<c:forEach items="${requestScope.boardList}" var="n">
			<li class="nav-item ${requestScope.boardName == n.boardName ? 'selected' : ''}">
				<a class="nav-item_link" href="/content/${n.categoryId}/${n.boardId}">${n.boardName}</a>
			</li>
		</c:forEach>
	</ul>
</div>
<div class="section">
	<div class='section-top'>
		<a class="board_link" href="/content/${requestScope.categoryId}/${requestScope.boardId}">
			${requestScope.categoryName} > ${requestScope.boardName}
		</a>
		<div class="post_title">${requestScope.thisPost.postTitle}</div>
		<div class="post_info" id="${requestScope.thisPost.postId}">
			<div class="post_info-item">작성자 : ${requestScope.thisPost.boardId != 'anonymous' ? requestScope.thisPost.author : '비공개'}</div>
			<div class="post_info-item">조회 수 : ${requestScope.thisPost.hits }</div>
		</div>
	</div>

	<div class='section-middle'>
		<div class="post_content">${requestScope.thisPost.postContent }</div>
		<c:if test="${requestScope.thisPost.author == requestScope.login}">
			<div class="post_menu">
				<a class="post_menu-item_link" href="${requestScope['javax.servlet.forward.request_uri']}/modify">수정</a>
				<span class="v_bar">|</span>
				<a class="post_menu-item_link" href="/content/${requestScope.thisPost.postId}/rmvpost">삭제</a>
			</div>
		</c:if>
		<div class="post_comment">
			<h3>댓글(${requestScope.thisPost.comments})</h3>
			<span role="button" class="tab_sort_item selected" id="sortType1">최신순</span>
			<span role="button" class="tab_sort_item" id="sortType2">등록순</span>
			<ul class="post_comment-list">
				<c:forEach items="${requestScope.commentList}" var="n">
				<li id="${n.commentId}" class="post_comment-item main">
					<div >${requestScope.thisPost.boardId != 'anonymous' ? n.userId : '비공개'}</div>
					<div>${n.commentContent}</div>
					<div>
						<fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						<span role="button" class="comment_reply">답글 쓰기</span>
					</div>
				</li>
					<c:forEach items="${n.subComment}" var="sub">
						<li id="sub_${sub.commentId}" class="post_comment-item sub">
							<div>${requestScope.thisPost.boardId != 'anonymous' ? sub.userId : '비공개'}</div>
							<div>${sub.commentContent}</div>
							<div>
								<fmt:formatDate value="${sub.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								<span role="button" class="comment_reply">답글 쓰기</span>
							</div>
						</li>
					</c:forEach>
				</c:forEach>
			</ul>
			<c:if test="${requestScope.login != null}">
			<form class="comment_form" action="/content/${requestScope.thisPost.postId}/regcomment" method="post">
				<textarea cols="20" rows="3" name="comment_content" class="tf_content" placeholder="댓글을 작성해 보세요."></textarea>
				<button type="submit" class="btn_major btn_self">등록</button>
			</form>
			</c:if>
		</div>
	</div>

	<div class="section-bottom">
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
				<div>
					<a class="jump_post_link" href="/content/${requestScope.categoryId}/${requestScope.boardId}/${n.postId}">
						${n.postTitle}
					</a>
				</div>
				<div>
					<span>${n.author}</span><span><fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<script type="text/javascript" src="/resources/js/comment_action.js"></script>