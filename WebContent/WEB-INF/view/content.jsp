<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/style/content_style.css" />
<c:set var="path" value="${requestScope['javax.servlet.forward.request_uri']}" />
<c:choose>
	<c:when test="${(param.tar == 'title' ||param.tar == 'auth')  && param.query != null}">
		<c:set var="params" value="?tar=${param.tar}&query=${param.query}&"/>
	</c:when>
	<c:otherwise>
		<c:set var="params" value="?"/>
	</c:otherwise>
</c:choose>

<div class="content_container">
	<div class="content_menu border">
		<div class="menu_title">${requestScope.boardList[0].categoryName}</div>
		<ul class="menu_list">
			<li class="items ${requestScope.currentBoard.boardName == '' ? 'selected' : ''}">
				<a href="/content/${requestScope.currentBoard.categoryId}">전체 글</a>
			</li>
			<c:forEach items="${requestScope.boardList}" var="n">
				<li class="items ${requestScope.currentBoard.boardName == n.boardName ? 'selected' : ''}"><a href="/content/${n.categoryId}/${n.boardId}">${n.boardName}</a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="section">
		<div class="section_header">
			<c:choose>
				<c:when test="${requestScope.currentBoard.boardName == ''}">
					<div class="section_title">전체 글</div>
				</c:when>
				<c:otherwise>
					<div class="section_title">${requestScope.currentBoard.boardName}</div>
				</c:otherwise>
			</c:choose>
			<div class="board_search">
				<form action="${path}" method="get" class="search_form">
					<select name="tar" class="target_control">
						<option value="title" ${param.tar == 'title' ? 'selected=selected' : ''}>제목</option>
						<option value="auth" ${param.tar == 'auth' ? 'selected=selected' : ''}>작성자</option>
					</select>
					<input type="text" name="query" class="query_control" value="${param.query}"/>
					<input type="submit" value="검색" class="button submit_btn" />
				</form>
			</div>
		</div>
		<table class="content_table">
			<thead>
				<tr>
					<th class="tb_short">번호</th>
					<th class="tb_long">제목</th>
					<th class="tb_mid">작성자</th>
					<th class="tb_mid">작성일</th>
					<th class="tb_short">조회 수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach begin="0" end="17" items="${requestScope.postList}"
					var="n" varStatus="st">
					<tr>
						<td>${st.index+1}</td>
						<td>${n.postTitle}</td>
						<td>${n.author}</td>
						<td><fmt:formatDate value="${n.createDate}"
								pattern="yyyy-MM-dd" /></td>
						<td><fmt:formatNumber type="number" value="${n.hits}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pages">
			<ul class="page_list">
				<c:set var="pageNum" value="${requestScope.pageNum }" />
				<c:set var="lastPage" value="${(requestScope.totalCount-1)/18+1 }" />
				<c:set var="startNum" value="${pageNum-(pageNum-1)%5}" />

				<c:if test="${startNum-5 >=1}">
					<li><a href="${path}${params}p=${startNum-5}"><button>←</button></a></li>
				</c:if>
				<c:forEach begin="0" end="${lastPage > 4+startNum ? 4 : lastPage - startNum }" var="i">
					<c:choose>
						<c:when test="${i+startNum == pageNum}">
							<li><a class="selected" href="${path}${params}p=${i+startNum}">${i+startNum}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="${path}${params}p=${i+startNum}">${i+startNum}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${startNum+5 <= lastPage}">
					<li><a href="${path}${params}p=${startNum+5}"><button>→</button></a></li>
				</c:if>
			</ul>
		</div>
	</div>
</div>