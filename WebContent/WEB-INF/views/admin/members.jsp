<%@page import="com.mkboard.entity.UserDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/resources/style/members_style.css">

<c:set var="type" value="${requestScope.type == 1 ? 's=adm&' : '' }"></c:set>
<jsp:include page="/WEB-INF/views/user/user_nav.jsp" flush="false" />
<div class="section">
	<div class="section-top">
		<span class="title">
			<c:choose>
				<c:when test="${requestScope.type == 1}">관리자 정보</c:when>
				<c:otherwise>회원 정보</c:otherwise>
			</c:choose>
		</span>
		<span class="info">전체 수 : ${requestScope.totalCount }명</span>
	</div>
	<table class="user_table">
		<thead>
			<tr class="user_table-field">
				<th>번호</th>
				<th>아이디</th>
				<th>닉네임</th>
				<th>이름</th>
				<th>이메일</th>
				<th>가입날짜</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.userList}" var="n" begin="0" end="7" varStatus="st">
				<tr class="user_table-cell">
					<td>${st.index+1}</td>
					<td>${n.userId}</td>
					<td>${n.userNickName}</td>
					<td>${n.userName}</td>
					<td>${n.userEmail}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${n.userRegdate}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="pager">
		<ul class="pager-list list_deco_none">
			<c:set var="pageNum" value="${requestScope.pageNum }" />
			<c:set var="lastPage" value="${(requestScope.totalCount-1)/8+1 }" />
			<c:set var="startNum" value="${pageNum-(pageNum-1)%5}" />

			<c:if test="${startNum-5 >=1}">
				<li class="pager-item">
					<div class="page_arrow left_page_arrow"></div>
					<a class="pager-item_link changer" href="?p=${startNum-5}">이전</a>
					<span class="v_bar">|</span>
				</li>
			</c:if>
			<c:forEach begin="0"
				end="${lastPage > 4+startNum ? 4 : lastPage - startNum }" var="i">
				<c:choose>
					<c:when test="${i+startNum == pageNum}">
						<li class="pager-item"><a class="pager-item_link num selected" href="?${type}p=${i+startNum}">${i+startNum}</a></li>
					</c:when>
					<c:otherwise>
						<li class="pager-item"><a class="pager-item_link num" href="?${type}p=${i+startNum}">${i+startNum}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${startNum+5 <= lastPage}">
				<li class="pager-item">
					<span class="v_bar">|</span>
					<a class="pager-item_link changer" href="?${type}p=${startNum+5}">다음</a>
					<div class="page_arrow right_page_arrow"></div>
				</li>
			</c:if>
		</ul>
	</div>
</div>