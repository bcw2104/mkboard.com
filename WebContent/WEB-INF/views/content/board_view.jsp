<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/resources/style/boardview_style.css" />
<link rel="stylesheet" href="/resources/style/common/nav_style.css" />


<c:set var="path" value="/content/${requestScope.categoryId}${requestScope.boardId != null ? '/'+=requestScope.boardId : ''}" />
<c:set var="sort" value="${param.sort != null ? 'sort='+=param.sort+='&' : ''}" />
<c:choose>
	<c:when test="${(param.field == 'title' || param.field== 'auth')  && param.query != null}">
		<c:set var="search" value="?field=${param.field}&query=${param.query}&" />
	</c:when>
	<c:otherwise>
		<c:set var="search" value="?" />
	</c:otherwise>
</c:choose>

<div class="nav">
	<div class="nav-title">${requestScope.categoryName}</div>
	<ul class="nav-list">
		<li class="nav-item ${requestScope.boardId == null ? 'selected' : ''}">
			<a class="nav-item_link" href="/content/${requestScope.categoryId}">전체 글</a></li>
		<c:forEach items="${requestScope.boardList}" var="n">
			<li class="nav-item ${requestScope.boardName == n.boardName ? 'selected' : ''}">
				<a class="nav-item_link" href="/content/${n.categoryId}/${n.boardId}">${n.boardName}</a>
			</li>
		</c:forEach>
	</ul>
</div>
<div class="section">
	<div class="board_header">
		<span class="board_title">${requestScope.boardName}</span>
		<a class="btn_write" href="${path}/create">
		<c:if test="${requestScope.categoryId != 'notice' || requestScope.login == 'admin'}">
		    <button class="btn_major btn_self">
				<img alt="write" src="/resources/images/pencil.svg">글쓰기
			</button>
		</c:if>
		</a>
	</div>
	<div class="board_control">
		<div class="tab_sort">
			<a role="button" class="tab_sort-item ${param.sort == null || param.sort == 'rec'? 'selected' : ''}" href="${path}${search}sort=rec">최신순</a>
			<a role="button" class="tab_sort-item ${param.sort == 'reg'? 'selected' : ''}" href="${path}${search}sort=reg">등록순</a>
			<a role="button" class="tab_sort-item ${param.sort == 'visit'? 'selected' : ''}" href="${path}${search}sort=visit">조회순</a>
			<a role="button" class="tab_sort-item ${param.sort == 'com'? 'selected' : ''}" href="${path}${search}sort=com">댓글순</a>
			<label class="tab_sort-item" id="hideNotice">공지 숨기기<input type="checkbox" id="hideNoticeCkb"></label>
		</div>
		<div class="box_search">
			<form action="${path}" method="get" class="search_form">
				<select name="field" class="opt_target">
					<option value="title" ${param.field == 'title' ? 'selected=selected' : ''}>제목</option>
					<option value="auth" ${param.field == 'auth' ? 'selected=selected' : ''}>작성자</option>
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
				<th class="tb_short"></th>
				<th class="tb_long">제목</th>
				<th class="tb_mid">작성자</th>
				<th class="tb_mid">작성일</th>
				<th class="tb_short">조회 수</th>
			</tr>
		</thead>
		<c:if test="${requestScope.categoryId != 'notice'}">
		<tbody id="noticePost">
			<c:forEach items="${requestScope.noticePostList}" var="n">
				<tr class="board_table-cell">
					<td>공지</td>
					<td>
						<a class="board_table-cell_link" href="/content/notice/${n.boardId}/${n.postId}">
							${n.postTitle}
							<c:if test="${n.comments!=0}">(${n.comments})</c:if>
						</a>
					</td>
					<td>${n.author}</td>
					<td><fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatNumber type="number" value="${n.hits}" /></td>
				</tr>
			</c:forEach>
		</tbody>
		</c:if>
		<tbody id="importantPost">
			<c:forEach items="${requestScope.importantPostList}" var="n">
				<tr class="board_table-cell">
					<td>안내</td>
					<td>
						<a class="board_table-cell_link" href="/content/${requestScope.categoryId}/${n.boardId}/${n.postId}">
							${n.postTitle}
							<c:if test="${n.comments!=0}">(${n.comments})</c:if>
						</a>
					</td>
					<td>${n.author}</td>
					<td><fmt:formatDate value="${n.createDate}" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatNumber type="number" value="${n.hits}" /></td>
				</tr>
			</c:forEach>
		</tbody>
		<tbody id="regulerPost">
			<c:forEach begin="0" end="17" items="${requestScope.postList}" var="n" varStatus="st">
				<tr class="board_table-cell">
					<td>${st.index+1}</td>
					<td>
						<a class="board_table-cell_link" href="/content/${requestScope.categoryId}/${n.boardId}/${n.postId}">
							${n.postTitle} <c:if test="${n.comments!=0}">(${n.comments})</c:if>
						</a>
					</td>
					<td>${n.boardId != 'anonymous' ? n.author : '비공개'}</td>
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
					<a class="pager-item_link changer" role="button" href="${path}${search}${sort}p=${startNum-5}">이전</a>
					<span class="v_bar">|</span>
				</li>
			</c:if>
			<c:forEach begin="0" end="${lastPage > 4+startNum ? 4 : lastPage - startNum }" var="i">
				<c:choose>
					<c:when test="${i+startNum == pageNum}">
						<li class="pager-item">
							<a class="pager-item_link num selected" href="${path}${search}${sort}p=${i+startNum}">${i+startNum}</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="pager-item">
							<a class="pager-item_link num" href="${path}${search}${sort}p=${i+startNum}">${i+startNum}</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<c:if test="${startNum+5 <= lastPage}">
				<li class="pager-item">
					<span class="v_bar">|</span>
					<a class="pager-item_link changer" role="button" href="${path}${search}${sort}p=${startNum+5}">다음</a>
					<div class="page_arrow right_page_arrow"></div>
				</li>
			</c:if>
		</ul>
	</div>
</div>

<script type="text/javascript" src="/resources/js/boardview_action.js" ></script>