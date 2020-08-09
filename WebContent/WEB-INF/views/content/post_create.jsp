<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/postcreate_style.css" />
<c:set var="categoryId" value="${requestScope.thisCategory.categoryId}" />
<c:set var="boardId" value="${requestScope.thisBoard.boardId}" />
<c:set var="boardName" value="${requestScope.thisBoard.boardName}" />

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
	<form class="post_form" action="/content/regpost" method="post">
		<div class="form_control">
			<label>게시판</label>
			<select class="opt_board" name="board_id" required>
				<option value="">게시판 선택</option>
				<c:forEach items="${requestScope.boardList}" var="n">
					<option value="${n.boardId}">${n.boardName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form_control">
			<label for="">제목</label>
			<input type="text" name="post_title" class="tf_title" required placeholder="게시글 제목을 입력하세요.">
		</div>
		<div class="form_control">
			<label for="">파일첨부</label>
		</div>
		<div class="box_content">
			<div class="font_option">
				<select class="opt_font_size">
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
				<div class="box_font_style btn_group">
					<button type="button" id="f_bold" title="굵게"><img alt="font_B" src="/resources/images/font_B.svg"></button>
					<button type="button" id="f_italy" title="기울임"><img alt="font_I" src="/resources/images/font_I.svg"></button>
					<button type="button" id="f_underline" title="밑줄"><img alt="font_U" src="/resources/images/font_U.svg"></button>
					<button type="button" id="f_strike" title="Strikethrough"><img alt="font_S" src="/resources/images/font_S.svg"></button>
					<button type="button" id="f_none" title="글자 효과 삭제"><img alt="eraser" src="/resources/images/eraser.svg"></button>
				</div>
				<div class="box_font_color btn_group">
					<button type="button" id="f_color_recent"><img alt="font_A" src="/resources/images/font_A.svg"></button>
					<button type="button" id="f_color_option"><img alt="arrow_bottom" src="/resources/images/arrow_bottom.svg"></button>
				</div>
			</div>
			<div class="tf_content" contenteditable="true"></div>
			<div class="box_resizer">
				<img alt="resizer" src="/resources/images/scroll_icon.svg">
			</div>
		</div>
		<div class="form_btn_group">
			<input type="reset" class="btn btn_major post_reset" value="초기화">
			<input type="submit" class="btn btn_major post_submit" value="등록">
		</div>
	</form>
</div>
<script type="text/javascript" src="/resources/js/postcreate_action.js"></script>