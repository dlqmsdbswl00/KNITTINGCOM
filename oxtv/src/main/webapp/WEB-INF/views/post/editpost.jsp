<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<title>게시글 수정</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>게시글 수정</h2>

	<p>DEBUG: role name = [${roleName}]</p>

	<form method="post" action="/posts/${post.id}/edit">

		<br>카테고리:
		<select name="category" required>
			<option value="도안">도안</option>
			<option value="자유">자유</option>
			<option value="질문">질문</option>
			<c:if test="${isAdmin}">
				<option value="공지">공지</option>
			</c:if>
		</select>
		<br> 제목:
		<input type="text" name="title" value="${post.title}" required>
		<br> 내용:<br>
		<textarea name="content" rows="10" cols="50" required>${post.content}</textarea>

		<button type="submit">수정</button>

	</form>
	<form method="post" action="/posts/${post.id}/delete" style="display: inline;">
		<button type="submit" onclick="return confirm('게시글을 삭제할까요?')">삭제</button>
	</form>


	<a href="/posts/${post.id}">취소</a>
</body>
</html>
