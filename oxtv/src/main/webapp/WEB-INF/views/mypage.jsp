<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">

</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<div class="main-content">
		<div class="user-container">
			<h2>마이페이지</h2>

			<form action="<c:url value='/mypage/update' />" method="post">
				<input type="hidden" name="id" value="${user.id}" />

				<div class="form-group">
					<label for="email">이메일</label>
					<input type="email" id="email" name="email" value="${user.email}" />
				</div>

				<div class="form-group">
					<label for="nickname">닉네임</label>
					<input type="text" id="nickname" name="nickname" value="${user.nickname}" />
				</div>

				<hr />

				<div class="form-group">
					<label for="currentPassword">기존 비밀번호</label>
					<input type="password" id="currentPassword" name="currentPassword" required />
				</div>

				<div class="form-group">
					<label for="userPassword">새 비밀번호</label>
					<input type="password" id="userPassword" name="userPassword" />
				</div>

				<div class="form-group">
					<label for="passwordConfirm">비밀번호 확인</label>
					<input type="password" id="passwordConfirm" name="passwordConfirm" />
				</div>

				<button type="submit">정보 수정</button>
			</form>

			<c:if test="${not empty errorMessage}">
				<p class="error-message">${errorMessage}</p>
			</c:if>

			<c:if test="${not empty successMessage}">
				<p class="success-message">${successMessage}</p>
			</c:if>
		</div>
	</div>


</body>
</html>