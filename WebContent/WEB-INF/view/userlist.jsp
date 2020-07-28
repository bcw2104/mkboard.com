<%@page import="com.codeplayground.database.UserDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="/style/userlist_style.css">

<div class="section">
	<table class="user_table">
		<caption>
			회원 정보<span>전체 회원수 : ${requestScope.totalCount }명</span>
		</caption>
		<thead>
			<tr>
				<th>번호</th>
				<th>아이디</th>
				<th>이름</th>
				<th>성별</th>
				<th>생년월일</th>
				<th>전화번호</th>
				<th>가입날짜</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.userList}" var="n" begin="0"
				end="7" varStatus="st">
				<tr>
					<td>${st.index+1}</td>
					<td>${n.userId}</td>
					<td>${n.userName}</td>
					<td>${n.userGender}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${n.userBirth}" /></td>
					<td>${n.userPhone}</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss"
							value="${n.userRegdate}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="pages">
	<ul class="page_list">
		<c:set var="pageNum" value="${requestScope.pageNum }" />
		<c:set var="lastPage" value="${(requestScope.totalCount-1)/8+1 }" />
		<c:set var="startNum" value="${pageNum-(pageNum-1)%5}" />

		<c:if test="${startNum-5 >=1}">
			<li><a href="?p=${startNum-5}"><button>←</button></a></li>
		</c:if>
		<c:forEach begin="0"
			end="${lastPage > 4+startNum ? 4 : lastPage - startNum }" var="i">
			<c:choose>
				<c:when test="${i+startNum == pageNum}">
					<li><a class="selected" href="?p=${i+startNum}">${i+startNum}</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="?p=${i+startNum}">${i+startNum}</a></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<c:if test="${startNum+5 <= lastPage}">
			<li><a href="?p=${startNum+5}"><button>→</button></a></li>
		</c:if>
	</ul>
</div>