<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/boardview_style.css" />
<c:set var="categoryId" value="${requestScope.thisCategory.categoryId}" />
<c:set var="boardId" value="${requestScope.thisBoard.boardId}" />
<c:set var="boardName" value="${requestScope.thisBoard.boardName}" />
<c:set var="path"
	value="/content/${categoryId}${boardId != '' ? '/'+=boardId : ''}" />

<c:choose>
	<c:when
		test="${(param.tar == 'title' ||param.tar == 'auth')  && param.query != null}">
		<c:set var="params" value="?tar=${param.tar}&query=${param.query}&" />
	</c:when>
	<c:otherwise>
		<c:set var="params" value="?" />
	</c:otherwise>
</c:choose>
<c:set var="field" value="${param.f != null ? 'f='+=param.f+='&' : ''}" />

<div class="nav">
	<div class="nav-title">${thisCategory.categoryName}</div>
	<ul class="nav-list">
		<li class="nav-item ${boardId == '' ? 'selected' : ''}">
			<a class="nav-item_link" href="/content/${categoryId}">전체 글</a></li>
		<c:forEach items="${boardList}" var="n">
			<li class="nav-item ${boardName == n.boardName ? 'selected' : ''}">
				<a class="nav-item_link" href="/content/${n.categoryId}/${n.boardId}">${n.boardName}</a>
			</li>
		</c:forEach>
	</ul>
</div>
<div class="section">
	<div class="board_header">
		<span class="board_title">${boardName}</span>
		<a class="btn_write" href="${path}/create">
			<button class="btn_major btn_self">
				<img alt="write" src="/resources/images/pencil.svg">글쓰기
			</button>
		</a>
	</div>
	<div class="board_control">
		<div class="tab_sort">
			<a role="button" class="tab_sort_item ${param.f == null || param.f == 'rec'? 'selected' : ''}" href="${path}${params}f=rec">최신순</a>
			<a role="button" class="tab_sort_item ${param.f == 'reg'? 'selected' : ''}" href="${path}${params}f=reg">등록순</a>
			<a role="button" class="tab_sort_item ${param.f == 'visit'? 'selected' : ''}" href="${path}${params}f=visit">조회순</a>
			<a role="button" class="tab_sort_item ${param.f == 'com'? 'selected' : ''}" href="${path}${params}f=com">댓글순</a>
		</div>
		<div class="box_search">
			<form action="${path}" method="get" class="search_form">
				<select name="tar" class="opt_target">
					<option value="title"
						${param.tar == 'title' ? 'selected=selected' : ''}>제목</option>
					<option value="auth"
						${param.tar == 'auth' ? 'selected=selected' : ''}>작성자</option>
				</select>
				<input type="text" name="query" class="tf_query" value="${param.query}" placeholder="검색어" />
				<button type="submit" class="btn_self">
					<img alt="search" src="/resources/images/search_icon.svg">
				</button>
			</form>
		</div>
	</div>
	<table class="board_table">
		<thead>
			<tr class="board_table-field">
				<th class="tb_short">번호</th>
				<th class="tb_long">제목</th>
				<th class="tb_mid">작성자</th>
				<th class="tb_mid">작성일</th>
				<th class="tb_short">조회 수</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach begin="0" end="17" items="${requestScope.postList}" var="n" varStatus="st">
				<tr class="board_table-cell">
					<td>${st.index+1}</td>
					<td>
						<a class="board_table-cell_link" href="/content/${categoryId}/${n.boardId}/${n.postId}">
							${n.postTitle} <c:if test="${n.comments!=0}">(${n.comments})</c:if>
						</a>
					</td>
					<td>${n.author}</td>
					<td><fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatNumber type="number" value="${n.hits}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pager">
		<ul class="pager-list">
			<c:set var="pageNum" value="${requestScope.pageNum }" />
			<c:set var="lastPage" value="${(requestScope.totalCount-1)/18+1 }" />
			<c:set var="startNum" value="${pageNum-(pageNum-1)%5}" />

			<c:if test="${startNum-5 >=1}">
				<li class="pager-item">
					<div class="page_arrow left_page_arrow"></div>
					<a class="pager-item_link changer" role="button" href="${path}${params}${field}p=${startNum-5}">이전</a>
					<span class="v_bar">|</span>
				</li>
			</c:if>
			<c:forEach begin="0"
				end="${lastPage > 4+startNum ? 4 : lastPage - startNum }" var="i">
				<c:choose>
					<c:when test="${i+startNum == pageNum}">
						<li class="pager-item">
							<a class="pager-item_link num selected" href="${path}${params}${field}p=${i+startNum}">${i+startNum}</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="pager-item">
							<a class="pager-item_link num" href="${path}${params}${field}p=${i+startNum}">${i+startNum}</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${startNum+5 <= lastPage}">
				<li class="pager-item">
					<span class="v_bar">|</span>
					<a class="pager-item_link changer" role="button" href="${path}${params}${field}p=${startNum+5}">다음</a>
					<div class="page_arrow right_page_arrow"></div>
				</li>
			</c:if>
		</ul>
	</div>
</div>