<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<h2>마이페이지</h2>
	<form action="/mypage/update" method="post">
		<input type="hidden" name="id" value="${user.id}" />

		이메일:
		<input type="email" name="email" value="${user.email}" />
		<br /> 닉네임 :
		<input type="text" name="nickname" value="${user.nickname}" />
		<br /> 비밀번호:
		<input type="password" name="userPassword" />
		<br /> 비밀번호 확인:
		<input type="password" name="passwordConfirm" />
		<br />

		<button type="submit">정보 수정</button>
	</form>

</body>
</html>