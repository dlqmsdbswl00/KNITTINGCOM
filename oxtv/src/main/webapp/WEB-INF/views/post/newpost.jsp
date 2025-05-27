<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<html>
<head>
<title>게시글 작성</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>게시글 작성</h2>

	<p>DEBUG: role name = [${roleName}]</p>

	<form method="post" action="/posts/new">

		<br>카테고리:
		<select name="category" required>
			<option value="도안" ${post.category == '도안' ? 'selected' : ''}>도안</option>
			<option value="자유" ${post.category == '자유' ? 'selected' : ''}>자유</option>
			<option value="질문" ${post.category == '질문' ? 'selected' : ''}>질문</option>
			<c:if test="${isAdmin}">
				<option value="공지" ${post.category == '공지' ? 'selected' : ''}>공지</option>
			</c:if>
		</select>
		<br> 제목:
		<input type="text" name="title" required>
		<br> 내용:<br>
		<textarea name="content" rows="10" cols="50" required></textarea>




		<button type="submit">등록</button>
	</form>

</body>
</html>
