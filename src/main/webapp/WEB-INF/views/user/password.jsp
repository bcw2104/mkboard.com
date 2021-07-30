<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/style/password_style.css" />

<jsp:include page="/WEB-INF/views/user/user_nav.jsp" flush="false" />
<div class="section">
	<div class="section-title">비밀번호 변경</div>
	<form action="/user/password" id="changeForm" method="post">
	 	<label>현재 비밀번호</label>
        <input type="password" id="oldUserPw" class="inp inp_self" name="old_user_pw" maxlength="16" />
        <div id="oldPwMsg" class="form_msg"></div>

		 <label>새 비밀번호</label>
        <input type="password" id="newUserPw" class="inp inp_self" name="new_user_pw" maxlength="16" />
        <div id="newPwMsg" class="form_msg"></div>

        <label>새 비밀번호 확인</label>
        <input type="password" id="newUserPwCk" class="inp inp_self" maxlength="16" />
        <div id="newPwCkMsg" class="form_msg"></div>

		<button type="submit" id="changeFormSubmit" class="btn_self btn_major">변경</button>
	</form>
</div>

<script type="text/javascript" src="/resources/js/password_action.js"></script>