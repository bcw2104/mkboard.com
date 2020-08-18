<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Code Playground</title>
    <link rel="stylesheet" href="/resources/style/common/common_setting.css">
	<link rel="stylesheet" href="/resources/style/common/layout_style.css">
	<link rel="stylesheet" href="/resources/style/common/frame.css">
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</head>

<body>
    <div class="layout-container">
        <div class="header">
        	<div class="gnb">
        		<c:choose>
        			<c:when test="${requestScope.login == null}">
        				<a class="gnb-item_link" href="/account/login">로그인</a>
        				<span class="v_bar">|</span>
	        			<a class="gnb-item_link" href="/account/register">회원가입</a>
        			</c:when>
        			<c:otherwise>
        				<span>${requestScope.login}님 환영합니다.</span>
        				<span class="v_bar">|</span>
        				<a class="gnb-item_link" href="/account/logout">로그아웃</a>
        				<span class="v_bar">|</span>
        				<c:choose>
	        				<c:when test="${requestScope.login == 'admin'}">
		        				<a class="gnb-item_link" href="/admin/members">관리자 메뉴</a>
		        			</c:when>
		        			<c:otherwise>
		        				<a class="gnb-item_link" href="/user/profile">설정</a>
		        			</c:otherwise>
	        			</c:choose>
        			</c:otherwise>
        		</c:choose>
        	</div>
        	<div class="header-title"><a class="header-title_link" href="/">Code Playground</a></div>
        </div>
        <div class="lnb">
			<ul class="lnb-list">
				<c:forEach items="${requestScope.categoryList}" var="n">
					<li class="lnb-item"><a class="lnb-item_link" href="/content/${n.categoryId}">${n.categoryName}</a></li>
				</c:forEach>
			</ul>
        </div>
        <div class="container">
	        <jsp:include page="${requestScope.requestPage}" flush="false" />
        </div>
        <div class="footer">
        	<a class="footer-item_link">Contact |</a>
        	<a class="footer-item_link" href="https://www.facebook.com/profile.php?id=100006654306781">Facebook |</a>
        	<a class="footer-item_link" href="https://github.com/bcw2104">Github</a><br/>
        	<hr class="hr_light">
        	<span>제작자 : bcw2104 |</span>
        	<span>대표명 : 방철우 |</span>
        	<span>문의전화 : 010-5026-7047</span><br/>
        	<span>주소 : 경기도 고양시 일산동구 숲속마을2로 51-57 101동 402호 (엘리시아)</span>
        </div>
    </div>
</body>

</html>