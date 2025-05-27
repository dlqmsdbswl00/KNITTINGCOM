<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
<title>게시글 목록</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>게시글 목록</h2>
	<a href="/posts/new">게시글 작성</a>
	<table border="1">
		<thead>
			<tr>
				<th>ID</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="post" items="${postsPage.content}">
				<tr>
					<td>${post.id}</td>
					<td><a href="/posts/${post.id}">${post.title}</a></td>
					<td>${post.user.nickname}</td>
					<td>${post.formattedCreatedAt}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${postsPage.hasPrevious()}">
		<a href="/posts?page=${postsPage.number - 1}">이전</a>
	</c:if>
	<c:if test="${postsPage.hasNext()}">
		<a href="/posts?page=${postsPage.number + 1}">다음</a>
	</c:if>

</body>
</html>
