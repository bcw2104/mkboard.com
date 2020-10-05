<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/postcreate_style.css" />
<link rel="stylesheet" href="/resources/style/common/nav_style.css" />

<script src="/resources/js/tools/ckeditor.js"></script>
<c:set var="modify" value="${requestScope.postInfo != null}"/>

<div class="nav">
	<div class="nav-title">${requestScope.categoryName}</div>
	<ul class="nav-list list_deco_none">
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
	<form class="post_form" id="postForm" action="/content/regpost" method="post" enctype="multipart/form-data">
		<c:if test="${modify}">
			<input type="hidden" name="post_id" value="${requestScope.postInfo.postId}">
		</c:if>
		<div class="form_control">
			<label>게시판</label>
			<select class="opt_board" name="board_id" required>
				<option value="">게시판 선택</option>
				<c:forEach items="${requestScope.boardList}" var="n">
					<option value="${n.boardId}"
						<c:if test="${requestScope.postInfo.boardId ==n.boardId }">
							selected="selected"
						</c:if> >
						${n.boardName}
					</option>
				</c:forEach>
			</select>
		</div>
		<c:if test="${sessionScope.user.admin == 1}">
			<label class=admin_control>공지로 등록<input type="checkbox" class="ckb_notice"  name="important"></label>
		</c:if>
		<div class="form_control">
			<label>제목</label>
			<input type="text" name="post_title" class="tf_title"
				<c:choose>
					<c:when test="${modify}">value = "${requestScope.postInfo.postTitle}"</c:when>
					<c:otherwise>placeholder="게시글 제목을 입력하세요."</c:otherwise>
				</c:choose>
			required/>
		</div>
		<div class="form_control">
			 <label>파일첨부</label>
			  <button type="button" class="btn_major" id="fileAddBtn">+</button>
			  <div id="attachedFileGroup">
				<c:forEach items="${requestScope.attachedFileList}" var="n">
				<div class="file_ele">
					<input type="hidden" name="org_attached_file" value="${n.storedFileName}">
					<span class="attached_file_name">${n.orgFileName}</span>
					<a href="#this" class="file_remove_btn" role="button">삭제</a>
				</div>
				</c:forEach>
			  </div>
		</div>
		<c:if test="${requestScope.postInfo != null}">
			<div id="preContent" hidden="hidden">${requestScope.postInfo.postContent}</div>
		</c:if>
		<div class="editor-wrap">
			<div id="editor"></div>
		</div>
		<div class="form_btn_group">
			<input type="submit" id="postSubmitBtn" class="btn_major btn_self" value="등록">
		</div>
	</form>
</div>
<script type="text/javascript" src="/resources/js/postcreate_action.js"></script>
<script type="text/javascript" src="/resources/js/postcreate_file_action.js"></script>