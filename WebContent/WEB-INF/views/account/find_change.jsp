<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/style/change_style.css" />
<div class="section">
	<div class="section-title">비밀번호 변경</div>
	<form action="/account/change" id="changeForm" method="post">
		 <label>비밀번호</label>
        <input type="password" id="userPw" class="inp inp_self" name="user_pw" maxlength="16" />
        <div id="pwMsg" class="form_msg"></div>

        <label>비밀번호 확인</label>
        <input type="password" id="userPwCk" class="inp inp_self" maxlength="16" />
        <div id="pwCkMsg" class="form_msg"></div>

		<button type="submit" id="changeFormSubmit"class="btn_self btn_major">변경</button>
	</form>
</div>

<script type="text/javascript" src="/resources/js/change_action.js"></script>