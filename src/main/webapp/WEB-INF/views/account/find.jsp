<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/style/find_style.css" />
<div class="section">
	<div class="section-title">계정 찾기</div>
	<div class="section-content">
		<p>회원 가입시 입력하신 이메일 주소를 입력하시면<br/>해당 이메일로 아이디 및 비밀번호 변경 링크를 보내드립니다.</p>
	</div>
	<div class="section-control">
		<input type="text" class="inp inp_self" id="userEmail" name="user_email" placeholder="이메일 주소" />
		<div id="emailMsg" class="wrong_msg"></div>
		<button type="button" id="findSubmit" class="btn_self btn_major">메일 전송</button>
	</div>
</div>

<script type="text/javascript" src="/resources/js/find_action.js"></script>