<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<li class="nav-item ${path == '/user/password' ? 'selected' : ''}">
			<a class="nav-item_link" href="/user/password">비밀번호 변경</a>
		</li>
		<li class="nav-item ${path == '/user/leave' ? 'selected' : ''}">
			<a class="nav-item_link" href="/user/leave">회원 탈퇴</a>
		</li>
	</ul>
</div>