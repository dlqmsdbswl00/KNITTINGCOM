<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OXTV 커뮤니티</title>
</head>
<body>
	<h1>뜨개 OXTV 커뮤니티에 오신 것을 환영합니다</h1>
	<p>여기가 니가 만들 세상의 시작이다.</p>
	<form action="/signup" method="get">
		<button type="submit">회원가입</button>
	</form>


	<%
	Object loginUser = session.getAttribute("loginUser");
	if (loginUser != null) {
	%>
	<%= ((com.oxtv.model.User)loginUser).getUserName() %> 님 환영합니다!

	<a href="/logout"><button>로그아웃</button></a>
	<%
	} else {
	%>
	<a href="/signup"><button>회원가입</button></a>
	<a href="/login"><button>로그인</button></a>
	<%
	}
	%>

</body>
</html>