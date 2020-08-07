<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="/resources/style/register_style.css" />
<div class="section">
    <div class="title">회원가입</div>
    <form action="/user/register" method="post" id="regi_form">
        <label>아이디</label>
        <input type="text" class="input" id="user_id" name="user_id" maxlength="10" autocomplete="off" required />
        <label>비밀번호</label>
        <input type="password" class="input" name="user_pw" maxlength="15" required />
        <label>이름</label>
        <input type="text" class="input" name="user_name" autocomplete="off" required />
        <label >생년월일</label>
        <div>
	        <input type="text" class="b_input" name="user_birth" pattern="^[0-9]{4}$" autocomplete="off" maxlength="4" placeholder="년(4자리)" required />
	        <select name="user_birth" class="b_input" required>
	            <option>월</option>
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
	        <input type="text" class="b_input" name="user_birth" pattern="^[0-9]{1,2}$" autocomplete="off" maxlength="4" placeholder="일" required/>
        </div>
        <label>성별</label>
        <select name="user_gender" class="input" required>
            <option value="M">남성</option>
            <option value="F">여성</option>
        </select>
        <label>전화번호</label>
        <div>
            <select name="user_phone" class="p_input" required>
                <option value="010">010</option>
                <option value="011">011</option>
                <option value="012">012</option>
                <option value="015">015</option>
                <option value="017">017</option>
            </select>
            -
            <input type="tel" class="p_input" name="user_phone" pattern="^[0-9]{3,4}$" required autocomplete="off" maxlength="4" />
            -
            <input type="tel" class="p_input" name="user_phone" pattern="^[0-9]{4}$" required autocomplete="off" maxlength="4" />
        </div>
        <input type="submit" class="submit_btn button" value="회원가입" />
    </form>
</div>
<script type="text/javascript" src="/resources/js/register_action.js"></script>