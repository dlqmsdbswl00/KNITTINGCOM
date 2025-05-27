<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
<title>게시글 작성</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>게시글 작성</h2>
	<form method="post" action="/posts/new">
		제목: <input type="text" name="title" required><br> 내용:<br>
		<textarea name="content" rows="10" cols="50" required></textarea>
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


		<button type="submit">등록</button>
	</form>
	<p>Role: ${sessionScope.loginUser.role}</p>

</body>
</html>
