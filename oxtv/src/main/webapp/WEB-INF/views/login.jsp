<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>로그인</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">

</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<div class="main-content">
		<div class="user-container">
			<h2>로그인</h2>

			<form action="<c:url value='/login' />" method="post">
				<input type="hidden" name="redirect" value="${param.redirect}" />

				<div class="form-group">
					<label for="userId">아이디</label>
					<input type="text" id="userId" name="userId" required />
				</div>

				<div class="form-group">
					<label for="userPassword">비밀번호</label>
					<input type="password" id="userPassword" name="userPassword" required />
				</div>

				<button type="submit">로그인</button>
			</form>

			<c:if test="${not empty errorMessage}">
				<p class="error-message">${errorMessage}</p>
			</c:if>

			<p>
				계정이 없으신가요? <a href="<c:url value='/signup' />"><strong>회원가입</strong></a>
			</p>
		</div>
	</div>
</body>
</html>
