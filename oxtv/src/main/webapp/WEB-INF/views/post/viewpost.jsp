<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head><title>게시글 보기</title></head>
<body>
<h2>${post.title}</h2>
<p><strong>작성자:</strong> ${post.user.nickname}</p>
<p><strong>작성일:</strong> ${formattedCreatedAt}</p>
<p>${post.content}</p>

<a href="/posts/${post.id}/edit">수정</a>
<form method="post" action="/posts/${post.id}/delete" style="display:inline;">
    <button type="submit">삭제</button>
</form>
<a href="/posts">목록</a>
</body>
</html>
