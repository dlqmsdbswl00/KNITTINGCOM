<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<title>회원가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<h2>회원가입</h2>

	<form action="/signup" method="post">
		<input type="text" name="userId" placeholder="아이디" required />
		<input type="password" name="userPassword" placeholder="비밀번호" required />
		<input type="text" name="userName" placeholder="이름" required />
		<input type="text" name="nickname" placeholder="닉네임" required />
		<input type="email" name="email" placeholder="이메일" required />
		<button type="submit">회원가입</button>
	</form>
</body>
</html>
