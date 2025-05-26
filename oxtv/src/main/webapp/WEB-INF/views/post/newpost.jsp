<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>게시글 작성</title>
</head>
<body>
	<h2>게시글 작성</h2>
	<form method="post" action="/posts/new">
		제목: <input type="text" name="title" required><br> 내용:<br>
		<textarea name="content" rows="10" cols="50" required></textarea>
		<br>
		<button type="submit">등록</button>
	</form>
</body>
</html>
