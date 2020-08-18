<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/style/register_style.css" />
<div class="section">
    <div class="title">회원가입</div>
    <form action="/account/register" method="post" id="registerForm">
        <label>아이디</label>
        <input type="text" id="userId" class="inp inp_self" name="user_id" maxlength="10" autocomplete="off" />
        <div id="idMsg" class="form_msg"></div>

        <label>비밀번호</label>
        <input type="password" id="userPw" class="inp inp_self" name="user_pw" maxlength="16" />
        <div id="pwMsg" class="form_msg"></div>

        <label>비밀번호 확인</label>
        <input type="password" id="userPwCk" class="inp inp_self" name="user_pw_ck" maxlength="16" />
        <div id="pwCkMsg" class="form_msg"></div>

        <label>이름</label>
        <input type="text" id="userName" class="inp inp_self" name="user_name" autocomplete="off"/>
        <div id="nameMsg"class="form_msg"></div>

        <label >생년월일</label>
        <div id="userBirth">
	        <input type="text" id="userBirthY" class="inp inp_group" name="user_birth"  autocomplete="off" maxlength="4" placeholder="년(4자리)" />
	        <select name="user_birth" id="userBirthM" class="inp inp_group">
	            <option value="">월</option>
	            <option value="01">1</option>
	            <option value="02">2</option>
	            <option value="03">3</option>
	            <option value="04">4</option>
	            <option value="05">5</option>
	            <option value="06">6</option>
	            <option value="07">7</option>
	            <option value="08">8</option>
	            <option value="09">9</option>
	            <option value="10">10</option>
	            <option value="11">11</option>
	            <option value="12">12</option>
	        </select>
	        <input type="text" id="userBirthD" class="inp inp_group" name="user_birth" maxlength="2"	 autocomplete="off" placeholder="일"/>
        </div>
        <div id="birthMsg" class="form_msg"></div>

        <label>성별</label>
        <select name="user_gender" class="inp inp_self">
            <option value="M">남성</option>
            <option value="F">여성</option>
        </select>
         <div class="form_msg"></div>

        <label>이메일</label>
        <input type="text" class="inp inp_self" id="userEmail" name="user_email"/>
        <div id="emailMsg" class="form_msg"></div>

        <label>전화번호</label>
        <div id="userPhone">
            <select name="user_phone" class="inp inp_txt_group">
                <option value="010">010</option>
                <option value="011">011</option>
                <option value="012">012</option>
                <option value="015">015</option>
                <option value="017">017</option>
            </select>
            -
            <input type="text" id="phoneNum1" class="inp inp_txt_group" name="user_phone" autocomplete="off" maxlength="4" />
            -
            <input type="text" id="phoneNum2" class="inp inp_txt_group" name="user_phone" autocomplete="off" maxlength="4" />
        </div>
        <div id="phoneMsg" class="form_msg"></div>
        <button type="submit" id="registerFormSubmit"class="btn_self btn_major">회원가입</button>
    </form>
</div>
<script type="text/javascript" src="/resources/js/register_validation.js"></script>