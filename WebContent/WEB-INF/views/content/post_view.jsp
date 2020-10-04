<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="/resources/style/postview_style.css" />
<link rel="stylesheet" href="/resources/style/common/nav_style.css" />

<script src="/resources/js/tools/ckeditor.js"></script>

<div class="nav">
	<div class="nav-title">${requestScope.categoryName}</div>
	<ul class="nav-list">
		<li class="nav-item ${requestScope.postInfo.boardId == null ? 'selected' : ''}">
			<a class="nav-item_link" href="/content/${requestScope.categoryId}">전체 글</a></li>
		<c:forEach items="${requestScope.boardList}" var="n">
			<li class="nav-item ${requestScope.postInfo.boardName == n.boardName ? 'selected' : ''}">
				<a class="nav-item_link" href="/content/${n.categoryId}/${n.boardId}">${n.boardName}</a>
			</li>
		</c:forEach>
	</ul>
</div>
<div class="section">
	<div class='section-top'>
		<a class="board_link" href="/content/${requestScope.categoryId}/${requestScope.postInfo.boardId}">
			${requestScope.categoryName} > ${requestScope.postInfo.boardName}
		</a>
		<div class="post_title">${requestScope.postInfo.postTitle}</div>
		<div class="post_info" id="${requestScope.postInfo.postId}">
			<div class="post_info-item">
				<div class="author_img_wrap"></div>
			</div>
			<div class="post_info-item">
				<c:choose>
					<c:when test="${requestScope.postInfo.boardId != 'anonymous'}">
						<div>${requestScope.postInfo.userNickName}</div>
						<div id="author" hidden="hidden">${requestScope.postInfo.userId}</div>
					</c:when>
					<c:otherwise>
						<div id="author">비공개</div>
					</c:otherwise>
				</c:choose>
				<div class="reg_date"><fmt:formatDate value="${requestScope.postInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
			</div>
			<div class="post_info-item">조회 수 : ${requestScope.postInfo.hits }</div>
		</div>
	</div>

	<div class='section-middle'>
		<c:if test="${requestScope.attachedFileList != null}">
			<div class="post_attached_files">
					<a role="button" id="attachedFileListOpen">첨부파일 (${fn:length(requestScope.attachedFileList)})</a>
					<ul class="post_attached_files-list" style="display: none">
						<c:forEach items="${requestScope.attachedFileList}" var="n">
							<li class="post_attached_files-item">
								<span>${n.orgFileName}</span>
								<a role="button"  id="${n.storedFileName}" class="attached_file_download">저장</a>
							</li>
						</c:forEach>
					</ul>
			</div>
		</c:if>
		<div class="post_content" hidden="hidden">${requestScope.postInfo.postContent }</div>
		<div class="editor-wrap">
			<div id="editor"></div>
		</div>
		<c:if test="${sessionScope.user != null && (requestScope.postInfo.userId == sessionScope.user.userId || sessionScope.user.userId == 'admin')}">
			<div class="post_menu">
				<c:if test="${requestScope.postInfo.userId == sessionScope.user.userId}">
					<a class="post_menu-item_link" href="${requestScope['javax.servlet.forward.request_uri']}/modify">수정</a>
					<span class="v_bar">|</span>
				</c:if>
				<a class="post_menu-item_link" href="/content/${requestScope.postInfo.postId}/rmvpost">삭제</a>
			</div>
		</c:if>
		<div class="post_comment">
			<h3>댓글(${requestScope.postInfo.comments})</h3>
			<span role="button" class="tab_sort_item selected" id="sortType1">최신순</span>
			<span role="button" class="tab_sort_item" id="sortType2">등록순</span>
			<ul class="post_comment-list">
				<c:forEach items="${requestScope.commentList}" var="n">
				<li id="${n.commentId}" class="post_comment-item main">
					<div >${requestScope.postInfo.boardId != 'anonymous' ? n.userNickName : '비공개'}</div>
					<div>${n.commentContent}</div>
					<div>
						<fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						<span role="button" class="comment_reply">답글 쓰기</span>
					</div>
				</li>
					<c:forEach items="${n.subComment}" var="sub">
						<li id="sub_${sub.commentId}" class="post_comment-item sub">
							<div>${requestScope.postInfo.boardId != 'anonymous' ? sub.userNickName : '비공개'}</div>
							<div>${sub.commentContent}</div>
							<div>
								<fmt:formatDate value="${sub.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								<span role="button" class="comment_reply">답글 쓰기</span>
							</div>
						</li>
					</c:forEach>
				</c:forEach>
			</ul>
			<c:if test="${sessionScope.user != null}">
			<form class="comment_form" action="/content/${requestScope.postInfo.postId}/regcomment" method="post">
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
					<c:when test="${n.postId > requestScope.postInfo.postId}">
						<div>다음 글</div>
					</c:when>
					<c:otherwise>
						<div>이전 글</div>
					</c:otherwise>
				</c:choose>
				<div>
					<a class="jump_post_link" href="/content/${requestScope.categoryId}/${n.boardId}/${n.postId}">
						${n.postTitle}
					</a>
				</div>
				<div>
					<span>${n.userNickName}</span><span><fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<script type="text/javascript" src="/resources/js/postview_action.js" charset="UTF-8"></script>
<script type="text/javascript" src="/resources/js/comment_action.js"></script>