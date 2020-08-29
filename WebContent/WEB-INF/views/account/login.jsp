<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/style/login_style.css" />
<div class="section">
	<div class="section-title">로그인</div>
	<form action="/account/login" method="post" id="loginForm">
		<input type="text" class="inp inp_self" name="user_id" maxlength="20" placeholder="아이디" />
		<input type="password" class="inp inp_self" name="user_pw" maxlength="20" placeholder="비밀번호" />
		<button type="submit" class="btn_self btn_major">로그인</button>
	</form>
	<div class="nav">
		<div class="nav-item">
			<a class="nav-item_link" href="/account/register">회원가입</a>
		</div>
		<span class="v_bar">|</span>
		<div class="nav-item">
			<a class="nav-item_link" href="/account/find">계정 찾기</a>
		</div>
	</div>
</div>