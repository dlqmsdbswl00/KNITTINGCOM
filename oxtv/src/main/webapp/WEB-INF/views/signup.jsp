<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<title>회원가입</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">

</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>

	<div class="main-content">
		<div class="user-container">
			<h2>회원가입</h2>
			<form action="/signup" method="post">
				<div class="form-group">
					<label for="userId">아이디</label>
					<input type="text" id="userId" name="userId" required />
				</div>

				<div class="form-group">
					<label for="userPassword">비밀번호</label>
					<input type="password" id="userPassword" name="userPassword" required />
				</div>

				<div class="form-group">
					<label for="userName">이름</label>
					<input type="text" id="userName" name="userName" required />
				</div>

				<div class="form-group">
					<label for="nickname">닉네임</label>
					<input type="text" id="nickname" name="nickname" required />
				</div>

				<div class="form-group">
					<label for="email">이메일</label>
					<input type="email" id="email" name="email" required />
				</div>

				<button type="submit">회원가입</button>
			</form>
		</div>
	</div>
</body>
</html>
