<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://oxtv.com/functions" prefix="fnx"%>

<html>
<head>
<title>게시글 목록</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>게시글 목록</h2>
	<form action="/posts" method="get">
		<input type="text" name="keyword" placeholder="검색어 입력"
			value="${param.keyword}" />
		<button type="submit">검색</button>
	</form>

	<a href="/posts/new">게시글 작성</a>
	<table border="1">
		<thead>
			<tr>
				<th>카테고리</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="post" items="${postsPage.content}">
				<tr>
					<td>${post.category}</td>

					<!-- 제목 하이라이트 적용 -->
					<td><a href="/posts/${post.id}"> <c:out
								value="${fnx:highlight(post.title, param.keyword)}"
								escapeXml="false" />
					</a></td>

					<!-- 작성자 하이라이트 적용 -->
					<td><c:out
							value="${fnx:highlight(post.user.nickname, param.keyword)}"
							escapeXml="false" /></td>

					<!-- 작성일은 하이라이트 필요 없음 -->
					<td>${post.formattedCreatedAt}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${postsPage.hasPrevious()}">
		<a href="/posts?page=${postsPage.number - 1}&keyword=${param.keyword}">이전</a>
	</c:if>
	<c:if test="${postsPage.hasNext()}">
		<a href="/posts?page=${postsPage.number + 1}&keyword=${param.keyword}">다음</a>
	</c:if>


</body>
</html>
