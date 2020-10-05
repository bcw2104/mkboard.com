<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/style/register_style.css" />
<div class="section">
    <div class="section-title">회원가입</div>
    <form action="/account/register" method="post" id="registerForm">
        <label>아이디</label>
        <input type="text" id="userId" class="inp inp_self" name="user_id" maxlength="15" autocomplete="off" />
        <div id="idMsg" class="form_msg"></div>

        <label>비밀번호</label>
        <input type="password" id="userPw" class="inp inp_self" name="user_pw" maxlength="16" />
        <div id="pwMsg" class="form_msg"></div>

        <label>비밀번호 확인</label>
        <input type="password" id="userPwCk" class="inp inp_self" maxlength="16" />
        <div id="pwCkMsg" class="form_msg"></div>

         <label>닉네임</label>
	     <input type="text" id="userNickName" class="inp inp_self" name="user_nick_name" maxlength="10"/>
	     <div id="nickNameMsg" class="form_msg"></div>

        <label>이름</label>
        <input type="text" id="userName" class="inp inp_self" name="user_name" autocomplete="off"/>
        <div id="nameMsg" class="form_msg"></div>

        <label>이메일</label>
        <input type="text" class="inp inp_self" id="userEmail" name="user_email"/>
        <div id="emailMsg" class="form_msg"></div>

	    <input type="text" class="inp" id="emailCertification" placeholder="인증코드" />
		<button type="button" id="certifiactionBtn" class="btn_major">인증코드 받기</button>
		<div id="certificationMsg" class="form_msg"></div>

        <button type="submit" id="registerFormSubmit" class="btn_self btn_major">회원가입</button>
    </form>
</div>
<script type="text/javascript" src="/resources/js/register_action.js"></script>