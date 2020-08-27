<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/profile_style.css" />
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
	<div class="profile">
		<div class="profile_img_wrap"></div>
		<div class="profile-info">
			<h1 id="userId">${requestScope.userId}</h1>
			<table class="profile-info_tb">
				<thead>
					<tr>
						<th>활동점수</th>
						<th>작성글</th>
						<th>작성댓글</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>${requestScope.score}</td>
						<td>${requestScope.postCount}</td>
						<td>${requestScope.commentCount}</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="mypost">
		<div class="mypost-top">
			<img class="mypost-img" alt="pencil" src="/resources/images/pencil_black.svg">
			<span class="mypost-title">내가 쓴 글</span>
		</div>
		<table class="board_table">
			<thead>
				<tr class="board_table-field">
					<th class="tb_short"></th>
					<th class="tb_long">제목</th>
					<th class="tb_mid">게시판</th>
					<th class="tb_mid">작성일</th>
					<th class="tb_short">조회 수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach begin="0" end="17" items="${requestScope.postList}" var="n" varStatus="st">
					<tr class="board_table-cell">
						<td>${st.index+1}</td>
						<td>
							<a class="board_table-cell_link" href="/content/${n.categoryId}/${n.boardId}/${n.postId}">
								${n.postTitle} <c:if test="${n.comments!=0}">(${n.comments})</c:if>
							</a>
						</td>
						<td>${n.boardName}</td>
						<td><fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatNumber type="number" value="${n.hits}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pager">
			<ul class="pager-list">
				<c:set var="pageNum" value="${requestScope.pageNum }" />
				<c:set var="lastPage" value="${(requestScope.postCount-1)/8+1 }" />
				<c:set var="startNum" value="${pageNum-(pageNum-1)%5}" />

				<c:if test="${startNum-5 >=1}">
					<li class="pager-item">
						<div class="page_arrow left_page_arrow"></div>
						<a class="pager-item_link changer" role="button" href="${path}?p=${startNum-5}">이전</a>
						<span class="v_bar">|</span>
					</li>
				</c:if>
				<c:forEach begin="0" end="${lastPage > 4+startNum ? 4 : lastPage - startNum }" var="i">
					<c:choose>
						<c:when test="${i+startNum == pageNum}">
							<li class="pager-item">
								<a class="pager-item_link num selected" href="${path}?p=${i+startNum}">${i+startNum}</a>
							</li>
						</c:when>
						<c:otherwise>
							<li class="pager-item">
								<a class="pager-item_link num" href="${path}?p=${i+startNum}">${i+startNum}</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${startNum+5 <= lastPage}">
					<li class="pager-item">
						<span class="v_bar">|</span>
						<a class="pager-item_link changer" role="button" href="${path}?p=${startNum+5}">다음</a>
						<div class="page_arrow right_page_arrow"></div>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</div>

<script type="text/javascript" src="/resources/js/profile_action.js" ></script>