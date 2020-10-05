<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/modify_style.css" />

<jsp:include page="/WEB-INF/views/user/user_nav.jsp" flush="false" />
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

	        <label>닉네임</label>
	        <input type="text" id="userNickName" class="inp inp_self" name="user_nick_name" value="${sessionScope.user.userNickName}" maxlength="10"/>
	        <div id="nickNameMsg" class="form_msg"></div>

	        <label>이름</label>
	        <input type="text" id="userName" class="inp inp_self" name="user_name" value="${sessionScope.user.userName}" autocomplete="off"/>
	        <div id="nameMsg" class="form_msg"></div>

	        <label>이메일</label>
	        <input type="text" class="inp inp_self" id="userEmail" name="user_email" value="${sessionScope.user.userEmail}"/>
	        <div id="emailMsg" class="form_msg"></div>

			<div class="btn_wrap">
		        <button type="button" id="modifyFormCancel" class="btn_self btn_major">취소</button>
		        <button type="submit" id="modifyFormSubmit" class="btn_self btn_major">변경</button>
	        </div>
	   	</div>
	</form>
</div>

<script type="text/javascript" src="/resources/js/modify_action.js" ></script>
<script type="text/javascript" src="/resources/js/modify_file_action.js" ></script>
<script type="text/javascript" src="/resources/js/modify_validation_check.js" ></script>