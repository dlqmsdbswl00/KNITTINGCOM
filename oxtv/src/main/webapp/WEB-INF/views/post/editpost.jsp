<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
<title>게시글 수정</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>게시글 수정</h2>
	<form method="post" action="/posts/${post.id}/edit">
		제목: <input type="text" name="title" value="${post.title}" required><br>
		내용:<br>
		<textarea name="content" rows="10" cols="50" required>${post.content}</textarea>
		<br>카테고리: <select name="category" required>
			<c:choose>
				<c:when test="${sessionScope.loginUser.role.toString() == 'ADMIN'}">
					<option value="DOAN"
						<c:if test="${post.category == 'DOAN'}">selected</c:if>>도안</option>
					<option value="FREE"
						<c:if test="${post.category == 'FREE'}">selected</c:if>>자유</option>
					<option value="QUESTION"
						<c:if test="${post.category == 'QUESTION'}">selected</c:if>>질문</option>
					<option value="NOTICE"
						<c:if test="${post.category == 'NOTICE'}">selected</c:if>>공지</option>
				</c:when>
				<c:otherwise>
					<option value="DOAN"
						<c:if test="${post.category == 'DOAN'}">selected</c:if>>도안</option>
					<option value="FREE"
						<c:if test="${post.category == 'FREE'}">selected</c:if>>자유</option>
					<option value="QUESTION"
						<c:if test="${post.category == 'QUESTION'}">selected</c:if>>질문</option>
				</c:otherwise>
			</c:choose>
		</select><br>


		<button type="submit">수정</button>
	</form>
	<form method="post" action="/posts/${post.id}/delete"
		style="display: inline;">
		<button type="submit" onclick="return confirm('게시글을 삭제할까요?')">삭제</button>
	</form>

	<a href="/posts/${post.id}">취소</a>
</body>
</html>
