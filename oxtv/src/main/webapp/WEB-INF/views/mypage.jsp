<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">

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
		<br /> 기존 비밀번호:
		<input type="password" name="currentPassword" required />
		<br /> 새 비밀번호:
		<input type="password" name="userPassword" />
		<br /> 비밀번호 확인:
		<input type="password" name="passwordConfirm" />
		<br />
		<button type="submit">정보 수정</button>

		<c:if test="${not empty errorMessage}">
			<p style="color: red;">${errorMessage}</p>
		</c:if>
		<c:if test="${not empty successMessage}">
			<p style="color: green;">${successMessage}</p>
		</c:if>

	</form>

</body>
</html>