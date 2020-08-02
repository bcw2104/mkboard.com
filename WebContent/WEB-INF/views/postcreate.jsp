<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/style/postcreate_style.css" />
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
		<form action="/content/regpost" class="post_form" method="post">
			<div class="post_input">
				<label for="">게시판</label>
				<select name="board_id" class="board_selection" required>
					<option value="">게시판 선택</option>
					<c:forEach items="${requestScope.boardList}" var="n">
						<option value="${n.boardId}">${n.boardName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="post_input">
				<label for="">제목</label>
				<input type="text" name="post_title" class="input_control" required placeholder="게시글 제목을 입력하세요.">
			</div>
			<div class="post_input">
				<label for="">파일첨부</label>
			</div>
			<div class="post_content_box">
				<div class="font_option">
					<select class="font_size_option">
						<option value="10">크기</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="14">14</option>
						<option value="18">18</option>
						<option value="24">24</option>
						<option value="36">36</option>
					</select>
				</div>
				<div class="post_content_input" contenteditable="true"></div>
				<div class="box_resizer">
					<img alt="resizer" src="/images/scroll_icon.svg">
				</div>
			</div>
			<div class="form_btn">
				<input type="reset" class="button post_reset" value="초기화">
				<input type="submit" class="button post_submit" value="등록">
			</div>
		</form>
	</div>
</div>
<script type="text/javascript" src="/resource/postcreate_action.js"></script>