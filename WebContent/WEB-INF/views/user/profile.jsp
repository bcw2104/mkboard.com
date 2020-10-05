<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/profile_style.css" />
<c:set var="path" value="${requestScope['javax.servlet.forward.request_uri']}" />

<jsp:include page="/WEB-INF/views/user/user_nav.jsp" flush="false" />
<div class="section">
	<div class="profile">
		<div class="profile_img_wrap"></div>
		<div class="profile-info">
			<h1>${requestScope.userNickName}</h1>
			<h1 id="userId">(${requestScope.userId})</h1>
			<table class="profile-info_tb">
				<thead>
					<tr>
						<th><img src="/resources/images/medal.svg" class="profile-info_tb-img" style="width: 15px;">활동점수</th>
						<th><img src="/resources/images/pencil_pink.svg" class="profile-info_tb-img">작성글</th>
						<th><img src="/resources/images/pencil_pink.svg" class="profile-info_tb-img">작성댓글</th>
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
						<c:choose>
							<c:when test="${n.permission == 1}">
								<a class="board_table-cell_link" href="/content/${n.categoryId}/${n.boardId}/${n.postId}">
									${n.postTitle} <c:if test="${n.comments!=0}">(${n.comments})</c:if>
								</a>
							</c:when>
							<c:otherwise>관리자에 의해 블라인드 처리된 게시글입니다.</c:otherwise>
						</c:choose>
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