<%@ page language="java" pageEncoding="UTF-8"%>
<link rel='stylesheet' href='https://cdn-uicons.flaticon.com/3.0.0/uicons-regular-rounded/css/uicons-regular-rounded.css'>
<header>
	<nav id="main-nav">
		<c:set var="loginUser" value="${sessionScope.loginUser}" />
		<a href="/">OXTV </a> <a href="/posts">게시판</a> <a href="/posts/notice">공지사항</a> <a href="/tutorials">튜토리얼</a>

		<c:choose>
			<c:when test="${not empty loginUser}">
				<span>${loginUser.nickname} 님</span>
				<a href="/mypage">마이페이지</a>
				<a href="/logout">로그아웃</a>
			</c:when>
			<c:otherwise>
				<a href="/signup">회원가입</a>
				<a href="/login">로그인</a>
			</c:otherwise>
		</c:choose>
	</nav>
</header>
