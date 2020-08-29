<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/style/leave_style.css" />

<jsp:include page="/WEB-INF/views/user/user_nav.jsp" flush="false" />
<div class="section">
	<div class="section-title">회원탈퇴</div>
	<div class="section-content">현재 계정의 비밀번호를 입력하시면 회원탈퇴가 진행됩니다.<br/>
													 <span>단, 회원탈퇴시 해당 계정으로 작성하신 게시물이 전부 삭제됩니다.</span></div>
	<form action="/user/leave" id="leaveForm" method="post">
	 	<label>현재 비밀번호</label>
        <input type="password" id="userPw" class="inp inp_self" name="user_pw" maxlength="16" />
        <div id="pwMsg" class="form_msg"></div>
		<button type="submit" id="leaveFormSubmit" class="btn_self btn_major">회원탈퇴</button>
	</form>
</div>

<script type="text/javascript" src="/resources/js/leave_action.js"></script>