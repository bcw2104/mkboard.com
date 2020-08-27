<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/modify_style.css" />
<link rel="stylesheet" href="/resources/style/common/nav_style.css" />
<c:set var="path" value="${requestScope['javax.servlet.forward.request_uri']}" />

<div class="nav">
	<div class="nav-title">마이페이지</div>
	<ul class="nav-list">
		<li class="nav-item ${path == '/user/profile' ? 'selected' : ''}">
			<a class="nav-item_link" href="/user/profile">프로필</a>
		</li>
		<li class="nav-item ${path == '/user/modify' ? 'selected' : ''}">
			<a class="nav-item_link" href="/user/modify">정보 수정</a>
		</li>
		<li class="nav-item ${path == '/user/leave' ? 'selected' : ''}">
			<a class="nav-item_link" href="/user/leave">회원 탈퇴</a>
		</li>
	</ul>
</div>
<div class="section">
	<div class="section-title">정보수정</div>
	<form action="/user/modify" id="modifyForm" method="post" enctype="multipart/form-data">
		<div class="user-info_img">
			<div class="profile_img_wrap"></div>
			<button type="button" id="changeImageBtn" class="btn_major btn_self">사진변경</button>
		</div>
		<div class="user-info_data">
			<label>아이디</label>
	        <input type="text" id="userId" class="inp inp_self" name="user_id"  value="${sessionScope.user.userId}" readonly="readonly" />
	        <div class="form_msg"></div>

	        <label>이름</label>
	        <input type="text" id="userName" class="inp inp_self" name="user_name" value="${sessionScope.user.userName}" autocomplete="off"/>
	        <div id="nameMsg"class="form_msg"></div>

	        <label >생년월일</label>
	        <div id="userBirth">
		        <input type="text" id="userBirthY" class="inp inp_group" name="user_birth" autocomplete="off" maxlength="4" placeholder="년(4자리)"
		        value='<fmt:formatDate value="${sessionScope.user.userBirth}" pattern="yyyy"/>' />
		        <input type="text" id="userBirthM" class="inp inp_group" name="user_birth"  autocomplete="off" maxlength="2" placeholder="월"
		        value='<fmt:formatDate value="${sessionScope.user.userBirth}" pattern="MM"/>' />
		        <input type="text" id="userBirthD" class="inp inp_group" name="user_birth" maxlength="2"	 autocomplete="off" placeholder="일"
		        value='<fmt:formatDate value="${sessionScope.user.userBirth}" pattern="dd"/>' />
	        </div>
	        <div id="birthMsg" class="form_msg"></div>

	        <label>이메일</label>
	        <input type="text" class="inp inp_self" id="userEmail" name="user_email" value="${sessionScope.user.userEmail}"/>
	        <div id="emailMsg" class="form_msg"></div>

	        <label>전화번호</label>
	        <div id="userPhone">
	            <input type="text" id="phoneNum1" class="inp inp_txt_group" name="user_phone" autocomplete="off" maxlength="3"
	            value="${sessionScope.user.userPhone.split('-')[0]}"/>
	            -
	            <input type="text" id="phoneNum2" class="inp inp_txt_group" name="user_phone" autocomplete="off" maxlength="4"
	             value="${sessionScope.user.userPhone.split('-')[1]}"/>
	            -
	            <input type="text" id="phoneNum3" class="inp inp_txt_group" name="user_phone" autocomplete="off" maxlength="4"
	             value="${sessionScope.user.userPhone.split('-')[2]}"/>
	        </div>
	        <div id="phoneMsg" class="form_msg"></div>
			<div class="btn_wrap">
		        <button type="button" id="modifyFormCancel"class="btn_self btn_major">취소</button>
		        <button type="submit" id="modifyFormSubmit"class="btn_self btn_major">변경</button>
	        </div>
	   	</div>
	</form>
</div>

<script type="text/javascript" src="/resources/js/modify_action.js" ></script>
<script type="text/javascript" src="/resources/js/modify_validation_check.js" ></script>